package org.example.controller;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.example.callback.WebMessageCallBack;
import org.example.config.CustomSpringConfigurator;
import org.example.config.WebMessageApplicationHandle;
import org.example.config.WebMessageConstant;
import org.example.entity.Client;
import org.example.entity.ScMsaWebMessage;
import org.example.entity.ShareConnectUser;
import org.example.enums.WebMessageCategory;
import org.example.enums.WebMessageState;
import org.example.redis.WebMessageCacheService;
import org.example.result.ScResult;
import org.example.service.CheckMySelf;
import org.example.service.ScMsaWebMessageService;
import org.example.service.ShareConnectUserDbService;
import org.example.service.WebMessageSend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@ServerEndpoint(value = "/socketServer/{token}",configurator = CustomSpringConfigurator.class)
@Slf4j
public class SocketServer {

    //websocket封装的session,通过它来信息推送(这个session没办法存进缓存或数据库)
    private Session session;

    //记录本地(当前机器)的在线用户
    private static CopyOnWriteArraySet<Client> clients = new CopyOnWriteArraySet<>();

    //当前连接的userId
    private String currentUserId;

    @Autowired
    private ScMsaWebMessageService scMsaPushMessageService;
    @Autowired
    private WebMessageConstant constant;
    @Autowired
    private CheckMySelf checkMySelf;
    @Autowired
    private WebMessageCacheService cacheService;
    @Autowired
    private ShareConnectUserDbService shareConnectUserDbService;
    @Autowired
    private WebMessageCallBack webMessageCallBack;
    private WebMessageSend webMessageSend;
    public static final String SHARE_CONNECT_SUFFIX = "_share_connects";


    /**
     * 用户连接时触发
     * 连接成功时，需要主动将未读消息推送给当前用户
     *
     * @param session
     * @param token
     */
    @OnOpen
    public void open(Session session, @PathParam(value = "token") String token){
        Client client = checkMySelf.getClient(token);
        client.setSession(session);
        this.currentUserId = client.getUserId();
        if (session != null) {
            //将client存入本地
            this.session = session;
            clients.add(client);
            //将用户连接信息存入集群redis，包括ip、端口号
            putConnectUser(client);
        }
        log.info("用户【{}】连接成功,sessionId:{}", client.getRealName(),session.getId());
        //推送未读消息
        pushMessage(client);

    }

    //登录时，将用户的连接信息存进redis缓存或数据库，包括ip、端口号
    public synchronized void putConnectUser(Client client){
        ShareConnectUser build = ShareConnectUser.builder()
                .userId(client.getUserId())
                .ip(WebMessageConstant.getIp())
                .port(constant.getPort())
                .realName(client.getRealName())
                .sessionId(client.getSession().getId())
                .build();
        if(isRedisCache()){
            Object sessionIdSet = cacheService.get(client.getUserId() + SHARE_CONNECT_SUFFIX);
            CopyOnWriteArraySet<ShareConnectUser> shareUsers =
                    sessionIdSet == null ? new CopyOnWriteArraySet<>() : (CopyOnWriteArraySet)sessionIdSet;
            shareUsers.add(build);
            cacheService.set(client.getUserId() + SHARE_CONNECT_SUFFIX,shareUsers);
        }else{
            shareConnectUserDbService.save(build);
        }
    }

    //登出时，从缓存列表删除用户的用户的连接信息
    public synchronized void removeConnectUser(String userId,String sessionId){
        if(isRedisCache()){
            Object connectUsersObj = cacheService.get(userId + SHARE_CONNECT_SUFFIX);
            if(connectUsersObj != null){
                CopyOnWriteArraySet<ShareConnectUser> connectUsers = (CopyOnWriteArraySet)connectUsersObj;
                CopyOnWriteArraySet<ShareConnectUser> newConnectUsers = new CopyOnWriteArraySet();
                connectUsers.forEach(c ->{
                    if(!c.getSessionId().equals(sessionId)){
                        newConnectUsers.add(c);
                    }
                });
                if(CollectionUtils.isEmpty(newConnectUsers)){
                    //没有在线的session时，直接删除该用户的所有在线设备缓存
                    cacheService.delete(userId + SHARE_CONNECT_SUFFIX);
                }else{
                    cacheService.set(userId + SHARE_CONNECT_SUFFIX,newConnectUsers);
                }
            }
        }else{
            shareConnectUserDbService.deleteBySessionId(sessionId);
        }

    }

    /**
     * 收到客户端发送信息时触发
     * @param message 客户端发来的消息
     */
    @OnMessage
    public void onMessage(String message){
        try {
            session.getBasicRemote().sendText(
                    JSON.toJSONString(ScResult.set(webMessageCallBack.callBack(message)))
            );
        } catch (IOException e) {
            log.error(">>>>>>websocket服务器响应失败！");
            e.printStackTrace();
        }
    }

    /**
     * 连接关闭触发，通过sessionId来移除
     * socketServers中客户端连接信息
     */
    @OnClose
    public void onClose(Session session) {
        log.info(">>>>>>进入关闭时监听器....");
        clients.forEach(client -> {
            if (client.getSession().getId().equals(session.getId())) {
                log.info("客户端:【{}】断开连接,sessionId:【{}】", client.getRealName(),session.getId());
                clients.remove(client);
                removeConnectUser(client.getUserId(),client.getSession().getId());
            }
        });
    }

    /**
     * 发生错误时触发
     *
     * @param error
     */
    @OnError
    public void onError(Session session,Throwable error) {
        log.info(">>>>>>进入错误时监听器....");
        clients.forEach(client -> {
            if (client.getSession().getId().equals(session.getId())) {
                clients.remove(client);
                removeConnectUser(client.getUserId(),client.getSession().getId());
                log.error("客户端:【{}】发生异常,sessionId:", client.getRealName(),session.getId());
                error.printStackTrace();
            }
        });
    }

    /**
     * 推送所有未读消息至连接的session用户
     * 拿到其对应的session，调用信息推送的方法
     * 如果用户不在线，不进行推送，等到用户上线后再推送
     *
     * @param currentClient 当前Client
     */
    public synchronized void pushMessage(Client currentClient) {
        try {
            //获取所有未读消息：
            List<ScMsaWebMessage> unreads = scMsaPushMessageService.findMessage(currentClient.getUserId(), null, WebMessageState.unread);
            if(!CollectionUtils.isEmpty(unreads)){
                unreads.forEach(m -> {
                    m.setCreated(m.getCreated().plusMillis(TimeUnit.HOURS.toMillis(8)));
                    m.setUpdated(m.getUpdated().plusMillis(TimeUnit.HOURS.toMillis(8)));
                });
            }
            String message = JSON.toJSONString(ScResult.set(unreads));
            currentClient.getSession().getBasicRemote().sendText(message);
            log.info("服务端给用户【{}-sessionId:{}】推送未读消息:【{}】", currentClient.getRealName(),session.getId(),message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 推送一条消息至用户
     * 拿到其对应的session，调用信息推送的方法
     * 如果用户不在线，不进行推送，等到用户上线后再推送
     *
     * @param message           消息信息
     */
    public synchronized void pushMessage(ScMsaWebMessage message,Set<ShareConnectUser> shareUsers) {
        clients.forEach(client -> {
            if(!CollectionUtils.isEmpty(shareUsers)){
                for (ShareConnectUser shareUser : shareUsers) {
                    if(shareUser.getSessionId().equals(client.getSession().getId())){
                        message.setCreated(message.getCreated().plusMillis(TimeUnit.HOURS.toMillis(8)));
                        message.setUpdated(message.getUpdated().plusMillis(TimeUnit.HOURS.toMillis(8)));
                        String messageJsonStr = JSON.toJSONString(ScResult.set(message));
                        try {
                            client.getSession().getBasicRemote().sendText(messageJsonStr);
                            log.info(">>>>>>给用户【{}-sessionId:{}】推送web消息成功，消息内容：{}",client.getRealName(),session.getId(),messageJsonStr);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    /**
     * 获取服务端当前客户端的连接数量
     * @return
     */
    public synchronized int getOnlineNum() {
        if(isRedisCache()){
            Set<String> keys = cacheService.getKeysLike(SHARE_CONNECT_SUFFIX);
            if(CollectionUtils.isEmpty(keys)){
                return 0;
            }
            return keys.size();
        }

        List<ShareConnectUser> all = shareConnectUserDbService.findAll();
        if(CollectionUtils.isEmpty(all)){
            return 0;
        }
        return all.size();
    }

    /**
     * 获取所有在线用户设备，前端界面需要用到
     *
     * @return
     */
    public synchronized List<ShareConnectUser> getOnlineUsers() {
        List<ShareConnectUser> result = Lists.newArrayList();
        if(isRedisCache()){
            Set<String> keys = cacheService.getKeysLike(SHARE_CONNECT_SUFFIX);
            if(!CollectionUtils.isEmpty(keys)){
                keys.forEach(k -> {
                    Object sessionIdSet = cacheService.get(k);
                    CopyOnWriteArraySet<ShareConnectUser> shareUsers =
                            sessionIdSet == null ? new CopyOnWriteArraySet<>() : (CopyOnWriteArraySet)sessionIdSet;
                    shareUsers.forEach(s -> result.add(s));
                });
            }
            return result;
        }
        // TenantProperties.getInstance().setEnableIntercept(false);
        List<ShareConnectUser> dbAll = shareConnectUserDbService.findAll();
        // TenantProperties.getInstance().setEnableIntercept(true);
        return dbAll;
    }

    /**
     * 消息群发(只发送给在线用户)
     * @param title     消息标题
     * @param context   消息内容
     * @param url       消息附带的url
     * @param category  消息类型
     */
    public synchronized void sendAll(String title, String context, String url, WebMessageCategory category) {
        log.info(">>>>>>正在进行web消息群发......");
        Set<String> userNames = getOnlineUsers().stream().map(ShareConnectUser::getUserId).collect(Collectors.toSet());
        log.info(">>>>>>在线用户列表：" + userNames.toString());
        if(!CollectionUtils.isEmpty(userNames)){
            if(webMessageSend == null){
                WebMessageApplicationHandle.getBean(WebMessageSend.class);
            }
            webMessageSend.send(title, context, url, userNames, category);
        }
    }

    public boolean isRedisCache(){
        if("redis".equals(constant.getDataBaseType())){
            return true;
        }
        return false;
    }
}
