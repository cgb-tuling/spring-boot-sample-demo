package org.example.controller;

import org.example.manager.WsSessionManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

@Controller
public class TestController {

    @RequestMapping("/sendMsg")
    @ResponseBody
    public Object sendMsg(String token, String msg) throws IOException {
        WebSocketSession webSocketSession = WsSessionManager.get(token);
        if (webSocketSession == null) {
            return "用户登录已失效";
        }
        webSocketSession.sendMessage(new TextMessage(msg));
        return "消息发送成功";
    }
}