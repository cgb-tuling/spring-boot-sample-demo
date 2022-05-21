package org.example.controller;

import org.springframework.web.bind.annotation.*;

/**
 * @author tian
 */
@RestController
@RequestMapping(value = "/hello", produces = "application/json; charset=UTF-8")
public class HelloController {

    /**
     * 测试@RequestMapping
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String testGet() {
        return "success";
    }

}
