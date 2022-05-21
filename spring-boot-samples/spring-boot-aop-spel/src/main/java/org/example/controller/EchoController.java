package org.example.controller;

import org.example.annotation.StockWarnCollect;
import org.example.annotation.TimeMeasure;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author yct
 */
@RequestMapping
@RestController
public class EchoController {
    @SuppressWarnings("MVCPathVariableInspection")
    @TimeMeasure("text1=#{#a0}, text2=#{#a1}, result=#{#result}")
    @GetMapping("/demo/echo")
    @ResponseBody
    public String echo(String text1, String text2) {
        return "ECHO: " + text1 + "," + text2;
    }
}
