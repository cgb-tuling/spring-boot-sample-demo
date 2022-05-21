package cn.javastack.springboot.druid.controller;


import cn.javastack.springboot.druid.entity.User;
import cn.javastack.springboot.druid.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author itmuch.com
 */
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/{id}")
    public User findById(@PathVariable Long id) {
        return this.userMapper.selectById(id)
                .orElseThrow(() -> new IllegalArgumentException("This user does not exit!"));
    }

    @GetMapping("")
    public List<User> findAll() {
        return this.userMapper.selectList();
    }
}

