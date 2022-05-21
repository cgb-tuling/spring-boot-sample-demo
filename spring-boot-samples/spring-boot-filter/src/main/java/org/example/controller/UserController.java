package org.example.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/user", produces = "application/json; charset=UTF-8")
public class UserController {
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public Map<String, Object> user() {
        Map<String, Object> map = new HashMap<>();
        map.put("username","zhangsan");
        map.put("password","123456");
        return map;
    }
}
