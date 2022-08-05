package org.example.controller;

import org.example.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
public class HelloController {
    
    @ResponseBody
    @RequestMapping("/hello")
    public String hello() {
        return "Hello World";
    }

    @RequestMapping("/")
    public String index(ModelMap map) {
        map.addAttribute("host", "http://blog.didispace.com");
        return "index";
    }

    @PostMapping("/ampTest")
    @ResponseBody
    public User ampTest(@RequestBody User user) {
        System.out.println(user);
        return user;
    }
}