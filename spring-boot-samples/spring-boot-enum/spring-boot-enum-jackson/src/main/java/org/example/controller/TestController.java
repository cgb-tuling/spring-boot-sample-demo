package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.domain.TestDO;
import org.example.enums.StatusEnum;
import org.springframework.web.bind.annotation.*;

@Slf4j
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


    /**
     * 对枚举的默认行为为按枚举名或其所在的位置（从0开始计算），例如当传入0时获取的是枚举类中的第一个对象。不满足我们的需求
     */
    @PostMapping("postjson")
    public TestDO postJsonEnum(@RequestBody TestDO testDO) {
        log.info("json body {}", testDO);
        return testDO;
    }
}
