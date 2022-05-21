package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Sets;
import org.example.config.WebMessageConstant;
import org.example.controller.ScMsaWebMessageController;
import org.example.controller.SocketServer;
import org.example.entity.ScMsaWebMessage;
import org.example.entity.ShareConnectUser;
import org.example.enums.WebMessageCategory;
import org.example.redis.WebMessageCacheService;
import org.example.result.ScResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author admin
 * @date 2021-06-21
 * @description
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class WebMessageSend implements WebMessageSendI {

    @Autowired
    private SocketServer socketServer;

    @Autowired
    private WebMessageCacheService cacheService;

    @Autowired
    private WebMessageConstant constant;

    @Autowired
    private ScMsaWebMessageService scMsaWebMessageService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ShareConnectUserDbService shareConnectUserDbService;

    @Autowired
    private CheckMySelf checkMySelf;

    @Override
    public ScResult send(String title, String context, String url, Set<String> username, WebMessageCategory category) {
        String sourceUserName = StringUtils.isEmpty(constant.getUserId())
                ? constant.getFullName()
                : checkMySelf.getUserInfoByUsername().getRealName();
        username.forEach(userId ->{
            ScMsaWebMessage message = scMsaWebMessageService.save(sourceUserName,title,context,url,userId,category);
            push(userId,message);

        });
        return ScResult.set(null);
    }

    private void push(String userId, ScMsaWebMessage message){
        //总连接数
        Set<ShareConnectUser> totalUsers = null;

        if(socketServer.isRedisCache()){
            Object obj = cacheService.get(userId + SocketServer.SHARE_CONNECT_SUFFIX);
            if(obj != null){
                totalUsers = (CopyOnWriteArraySet<ShareConnectUser>) obj;
            }
        }else{
            totalUsers = shareConnectUserDbService.findAllByUserId(userId);
        }

        //本台服务器的连接用户：
        Set<ShareConnectUser> currentUsers = currentSystemConnectUser(totalUsers);
        //另一台服务器的连接用户：
        Set<ShareConnectUser> notCurrentUsers = notCurrentSystemConnectUser(totalUsers);

        restTemplateSend(message,currentUsers);
        restTemplateSend(message,notCurrentUsers);
    }

    //获取本台服务器的连接用户
    public static Set<ShareConnectUser> currentSystemConnectUser(Set<ShareConnectUser> shareUsers){
        Set<ShareConnectUser> result = Sets.newHashSet();
        String currentIp = WebMessageConstant.getIp();
        if(!CollectionUtils.isEmpty(shareUsers)){
            shareUsers.forEach(u ->{
                if(u.getIp().equals(currentIp)){
                    result.add(u);
                }
            });
        }
        return result;
    }

    //获取另外一台服务器的连接用户
    public static Set<ShareConnectUser> notCurrentSystemConnectUser(Set<ShareConnectUser> shareUsers){
        Set<ShareConnectUser> result = Sets.newHashSet();
        String currentIp = WebMessageConstant.getIp();
        if(!CollectionUtils.isEmpty(shareUsers)){
            shareUsers.forEach(u ->{
                if(!u.getIp().equals(currentIp)){
                    result.add(u);
                }
            });
        }
        return result;
    }

    public void restTemplateSend(ScMsaWebMessage message, Set<ShareConnectUser> users){
        if(!CollectionUtils.isEmpty(users)){
            restTemplate.postForObject(
                    shareUser2HttpUrl((ShareConnectUser)new ArrayList(users).get(0))
                    ,new ScMsaWebMessageController.SendFormRepeat().setMessage(message).setUsername(users)
                    ,ScResult.class);
        }
    }

    public String shareUser2HttpUrl(ShareConnectUser shareConnectUserCache){
        //http://10.128.64.253:17019/scmsa-web-message-server/pushMessage/sendRepeat
        return "http://"
                + shareConnectUserCache.getIp()
                + ":" + shareConnectUserCache.getPort()
                + "/" +  constant.getApplicationName()
                + "/pushMessage/sendRepeat";
    }
}
