package org.example.controller;


import org.example.entity.User;
import org.example.enums.EducationEnum;
import org.example.enums.SexEnum;
import org.example.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @RequestMapping("findUsers")
    public List<User> findUsers() {
        return userService.findUsers();
    }


    @RequestMapping("finUserById/{id}")
    public User finUserById(@PathVariable("id") Long id) {
        return userService.findUserById(id);
    }

    @RequestMapping("insert")
    public Long insert(){
        User user = new User("AnsweAIL", SexEnum.MAN, new Date(), EducationEnum.HIGH_SCHOOL, new Date(), new Date());

        return userService.insertUer(user);
    }


    @PostMapping("save")
    public Long save(@RequestBody User user){
        return userService.insertUer(user);
    }

}
