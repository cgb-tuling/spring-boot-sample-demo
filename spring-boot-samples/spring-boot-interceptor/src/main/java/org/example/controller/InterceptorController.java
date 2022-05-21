package org.example.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.annotation.LoginRequire;
import org.example.annotation.UnInterception;
import org.example.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/interceptor")
public class InterceptorController {

    @RequestMapping("/test")
    public String test() {
        return "hello";
    }

    @UnInterception
    @RequestMapping("/test2")
    @ResponseBody
    public String test2() {
        return "我没有被拦截";
    }

    @UnInterception
    @LoginRequire
    @PostMapping("/test3")
    @ResponseBody
    public String test3(@RequestBody Map<String, Object> map) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(map);
        System.out.println(s);
        return "我没有被拦截";
    }

    @PostMapping("/test4")
    @ResponseBody
    public String test4(@RequestBody User user) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(user);
        System.out.println(s);
        return "我没有被拦截";
    }

}
