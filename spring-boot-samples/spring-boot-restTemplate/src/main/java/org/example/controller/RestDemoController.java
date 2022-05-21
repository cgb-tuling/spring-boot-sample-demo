package org.example.controller;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RequestMapping("rest")
@RestController
@Slf4j
public class RestDemoController {
    @Autowired
    private RestTemplate restTemplate;

    @PostMapping(value = "/form/demo",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Object form(String name, int age, String content) {
        log.info("form param name {}",name);
        log.info("form param age {}",age);
        log.info("form param content {}",content);
        return "success";
    }

    @PostMapping(value = "/form1/demo",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Object form1(UserModel userModel) {
        log.info("form param name {}",userModel.getName());
        log.info("form param age {}",userModel.getAge());
        log.info("form param content {}",userModel.getContent());
        return "success";
    }


    @PostMapping(value = "/json/demo",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Object json(@RequestBody Map<String, Object> map) {
        log.info("json param {}", JSON.toJSONString(map));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, Object> params= new LinkedMultiValueMap<>();
        params.add("name", "用户名");
        params.add("age", 100);
        params.add("content", "this is ");
        HttpEntity<?> httpEntity = new HttpEntity<>(params, headers);
        return restTemplate.postForObject("http://localhost:8080/rest/form/demo", httpEntity, String.class);
    }
}
