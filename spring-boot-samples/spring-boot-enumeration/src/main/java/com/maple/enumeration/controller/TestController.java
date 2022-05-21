package com.maple.enumeration.controller;


import com.maple.enumeration.entity.TestDO;
import com.maple.enumeration.enums.StatusEnum;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 测试表 前端控制器
 * </p>
 *
 * @author maple
 * @since 2020-06-21
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping
    public String getEnum(StatusEnum status) {
        return status.getDesc();
    }

    @PostMapping("post")
    public String postEnum(StatusEnum status) {
        return status.getDesc();
    }

    @PostMapping("json")
    public StatusEnum jsonEnum(@RequestBody TestDO testDO) {
        return testDO.getStatus();
    }

    @PostMapping("jsonEntity")
    public TestDO jsonEntity(@RequestBody TestDO testDO) {
        return testDO;
    }

    @PostMapping("fastEntity")
    public TestDO fastJsonEntity(@RequestBody TestDO testDO) {
        return testDO;
    }
}
