package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.entity.ShareConnectUser;
import org.example.redis.WebMessageCacheService;
import org.example.redis.WebMessageRedisCacheSupport;
import org.example.repository.ShareConnectUserDbRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Set;

/**
 * @author admin
 * @date 2021-06-21
 * @description
 */
@Service
@Slf4j
@Validated
@Transactional(rollbackFor = Exception.class)
public class ShareConnectUserDbService{

    private final ShareConnectUserDbRepository repository;
    private final WebMessageCacheService cacheService;
    @Value("${scmsa.web.message.online.user.database:db}")
    private String dataBaseType;

    public ShareConnectUserDbService(ShareConnectUserDbRepository repository,
                                     WebMessageCacheService cacheService) {
        this.repository = repository;
        this.cacheService = cacheService;
    }

    public Set<ShareConnectUser> findAllByUserId(String userId){
        //TenantProperties.getInstance().setEnableIntercept(false);
        Set<ShareConnectUser> result = repository.findAllByUserId(userId);
        //TenantProperties.getInstance().setEnableIntercept(true);
        return result;
    }

    public synchronized void deleteBySessionId(@NotEmpty String sessionId){
        //TenantProperties.getInstance().setEnableIntercept(false);
        repository.deleteBySessionId(sessionId);
       // TenantProperties.getInstance().setEnableIntercept(true);
    }

    public synchronized void deleteByUserId(@NotEmpty String userId){
        //TenantProperties.getInstance().setEnableIntercept(false);
        repository.deleteByUserId(userId);
        //TenantProperties.getInstance().setEnableIntercept(true);
    }

    //删除所有在线列表，系统登录时程序自动调用
    @PostConstruct
    public void deleteAllConnect(){
        log.info(">>>>>>web-message程序启动...");
        log.info(">>>>>>删除所有websocket缓存的在线用户列表...");
        if("redis".equals(dataBaseType)){
            log.info(">>>>>>开启redis缓存...");
            WebMessageRedisCacheSupport.setClosed(false);
            cacheService.clear();
        }else{
            // TenantProperties.getInstance().setEnableIntercept(false);
            repository.deleteAll();
            // TenantProperties.getInstance().setEnableIntercept(true);
            WebMessageRedisCacheSupport.setClosed(true);
        }
    }

    public ShareConnectUser save(ShareConnectUser build) {
        return repository.save(build);
    }

    public List<ShareConnectUser> findAll() {
        return repository.findAll();
    }
}
