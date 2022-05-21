package org.example.validation.controller;

import org.example.validation.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description: spring-boot2
 * User: jeikerxiao
 * Date: 2018/12/21 2:47 PM
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping("/update")
    public User updateUser(@Validated @RequestBody User userVo) {
        User user = new User();
        BeanUtils.copyProperties(userVo, user);
        return user;
    }
}
