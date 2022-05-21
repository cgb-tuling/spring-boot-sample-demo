package org.example.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.collect.Maps;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Sets;
import org.example.config.WebMessageConstant;
import org.example.entity.ScMsaWebMessage;
import org.example.entity.ShareConnectUser;
import org.example.enums.WebMessageCategory;
import org.example.enums.WebMessageState;
import org.example.result.ScPageResult;
import org.example.result.ScResult;
import org.example.service.ScMsaWebMessageService;
import org.example.service.WebMessageSend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * @author admin
 * @date 2021-05-27
 * @description 基于websocket的站内消息推送
 */
@Slf4j
@RestController
@RequestMapping("/pushMessage")
//@Api(tags = {"WebMessage-站内消息推送"}, value = "WebMessage-站内消息推送")
public class ScMsaWebMessageController {

    @Autowired
    private SocketServer socketServer;

    @Autowired
    private WebMessageSend webMessageSend;

    @Autowired
    private WebMessageConstant constant;

    private final ScMsaWebMessageService scMsaWebMessageService;

    public ScMsaWebMessageController(ScMsaWebMessageService scMsaWebMessageService) {
        this.scMsaWebMessageService = scMsaWebMessageService;
    }

//    @ApiOperation(value = "发送web消息", notes = "发送成功后，由websocket服务端进行统一推送")
    @PostMapping(value = "/send")
    public ScResult send(@RequestBody SendForm form) {
        return webMessageSend.send(form.getTitle()
                ,form.getContext()
                ,form.getUrl()
                ,form.getUsername()
                , WebMessageCategory.getInstance(form.getCategory()));
    }

//    @ApiOperation(value = "消息分发", notes = "只供程序内部用",hidden = true)
    @PostMapping(value = "/sendRepeat")
    public ScResult sendRepeat(@RequestBody SendFormRepeat form) {
        socketServer.pushMessage(form.getMessage(),form.getUsername());
        return ScResult.set("消息发送成功!将交由websocket服务端进行统一推送。");
    }


//    @ApiOperation(value = "查看所有在线设备", notes = "查看所有在线设备")
    @GetMapping(value = "/getOnlineUsers")
    public ScResult getOnlineUsers() {
        return ScResult.set(socketServer.getOnlineUsers());
    }

//    @ApiOperation(value = "主动拉取消息", notes = "主动拉取消息")
    @GetMapping(value = "/pull")
    public ScResult pull(@RequestParam(required = false,defaultValue = "-1") /*@ApiParam(value = "消息类别",defaultValue = "-1")*/Integer category
            , @RequestParam(defaultValue = "0") /*@ApiParam(value = "消息状态(0:查未读，1:查已读,-1:查所有)",defaultValue = "0")*/int state
            , @RequestParam(required = false) /*@ApiParam(value = "页数",defaultValue = "1")*/Integer page
            , @RequestParam(required = false)/* @ApiParam(value = "每页条数",defaultValue = "10")*/Integer limit) {

        if(page == null || limit == null || page == 0 || limit == 0){
            return ScResult.set(scMsaWebMessageService.findMessage(
                    constant.getUserId(), WebMessageCategory.getInstance(category), WebMessageState.getInstance(state)
            ));
        }

        Page<ScMsaWebMessage> pageResult = scMsaWebMessageService.findMessage(constant.getUserId()
                , WebMessageCategory.getInstance(category)
                , WebMessageState.getInstance(state)
                , page
                , limit
        );
        return ScPageResult.set(pageResult.getContent(),pageResult.getTotalPages(),pageResult.getTotalElements());
    }

//    @ApiOperation(value = "消息确认", notes = "将消息置为已读")
    @PostMapping(value = "/ack")
    public ScResult ack(@RequestBody Set<String> ids) {
        scMsaWebMessageService.ack(ids);
        return ScResult.set(null);
    }

//    @ApiOperation(value = "全部已读", notes = "将我的所有消息置为已读")
    @PostMapping(value = "/ackAll")
    public ScResult ackAll() {
        scMsaWebMessageService.ackAll(constant.getUserId());
        return ScResult.set(null);
    }

//    @ApiOperation(value = "消息删除", notes = "消息删除")
    @DeleteMapping(value = "/delete")
    public ScResult delete(@RequestBody Set<String> ids) {
        scMsaWebMessageService.removeMessage(ids);
        return ScResult.set(null);
    }

//    @ApiOperation(value = "全部删除", notes = "删除我的所有消息")
    @DeleteMapping(value = "/deleteAll")
    public ScResult delete() {
        scMsaWebMessageService.removeAll(constant.getUserId());
        return ScResult.set(null);
    }

//    @ApiOperation(value = "获取消息类别", notes = "获取消息类别")
    @GetMapping(value = "/findAllWebCategory")
    public ScResult findAllWebCategory(){
        Map<Integer,String> resultMap = Maps.newLinkedHashMap();
        for (WebMessageCategory webMessageCategory : WebMessageCategory.values()) {
            resultMap.put(webMessageCategory.getCode(),webMessageCategory.getDesc());
        }
        return ScResult.set(resultMap);
    }


    @JsonIgnoreProperties(ignoreUnknown = true)
//    @ApiModel(value = "发送web消息请求对象")
    @Accessors(chain = true)
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @ToString
    public static class SendForm implements Serializable {

//        @ApiModelProperty(value = "标题")
        private String title;

//        @ApiModelProperty(value = "消息体")
        @NotEmpty(message = "消息体不允许为空！")
        private String context;

//        @ApiModelProperty(value = "消息附带的链接")
        private String url;

//        @ApiModelProperty(value = "消息类别(缺省值为99)")
        private Integer category = 99;

//        @ApiModelProperty(value = "消息接收方用户名")
        @NotEmpty(message = "消息接收方用户名不允许为空！")
        private Set<String> username;
    }


    @JsonIgnoreProperties(ignoreUnknown = true)
//    @ApiModel(value = "用户发送web消息分发请求对象")
    @Accessors(chain = true)
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @ToString
    public static class SendFormRepeat implements Serializable {

//        @ApiModelProperty(value = "消息详细信息")
        @NotNull
        private ScMsaWebMessage message;

//        @ApiModelProperty(value = "在线用户列表")
        private Set<ShareConnectUser> username = Sets.newHashSet();
    }
}
