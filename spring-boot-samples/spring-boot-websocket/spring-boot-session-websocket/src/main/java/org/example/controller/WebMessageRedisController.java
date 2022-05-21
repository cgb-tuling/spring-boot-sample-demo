package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.config.WebMessageConstant;
import org.example.redis.WebMessageCacheService;
import org.example.result.ScResult;
import org.example.service.ShareConnectUserDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author admin
 * @date 2021-06-21
 * @description
 */
@Slf4j
//@ScMsaDocument
@RestController
@RequestMapping("/cache")
//@Api(tags = {"WebMessage-缓存管理"}, value = "WebMessage-缓存管理")
public class WebMessageRedisController {

    @Autowired
    private WebMessageCacheService cacheService;
    @Autowired
    private WebMessageConstant constant;
    @Autowired
    private ShareConnectUserDbService shareConnectUserDbService;

//    @ApiOperation(value = "清除redis指定key的缓存", notes = "清除redis指定key的缓存")
    @DeleteMapping(value = "/clean")
    public ScResult clean(@RequestParam String key) {
        return ScResult.set(cacheService.delete(key));
    }

//    @ApiOperation(value = "获取redis指定key的缓存", notes = "获取redis指定key的缓存")
    @GetMapping(value = "/get")
    public ScResult get(@RequestParam String key) {
        return ScResult.set(cacheService.get(key));
    }

//    @ApiOperation(value = "清除用户的websocket在线列表信息", notes = "清除用户的websocket在线列表信息")
    @DeleteMapping(value = "/cleanOnlineUser")
    public ScResult cleanOnlineUser(@RequestParam String userId) {
        if(constant.isRedisCache()){
            return ScResult.set(cacheService.delete(userId + SocketServer.SHARE_CONNECT_SUFFIX));
        }
        shareConnectUserDbService.deleteByUserId(userId);
        return ScResult.set(null);
    }

//    @ApiOperation(value = "查看用户的websocket在线列表信息", notes = "查看用户的websocket在线列表信息")
    @GetMapping(value = "/getOnlineUser")
    public ScResult getOnlineUser(@RequestParam String userId) {
        if(constant.isRedisCache()){
            return ScResult.set(cacheService.get(userId + SocketServer.SHARE_CONNECT_SUFFIX));
        }
        return ScResult.set(shareConnectUserDbService.findAllByUserId(userId));
    }

//    @ApiOperation(value = "删除所有用户的在线列表", notes = "系统启动时程序会自动调用,此接口为手动调用方式")
    @DeleteMapping(value = "/deleteAllConnect")
    public ScResult deleteAllConnect() {
        shareConnectUserDbService.deleteAllConnect();
        return ScResult.set(null);
    }
}
