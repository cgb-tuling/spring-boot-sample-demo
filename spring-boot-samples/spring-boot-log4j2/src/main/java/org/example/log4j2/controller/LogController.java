package org.example.log4j2.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Properties;

/**
 * @author dax
 * @since 2019/10/9 0:03
 */
@Slf4j
@RestController
@RequestMapping("/logging")
public class LogController {


    @GetMapping("/do")
    public String log() {

        log.info("log4j2 test date: {}  info: {}", LocalDate.now(), "请关注公众号：Felordcn");

        return "log4j2";
    }

}