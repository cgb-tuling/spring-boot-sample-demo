package com.maple.enumeration.mapper;

import com.maple.enumeration.BaseTest;
import com.maple.enumeration.enums.StatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * mapper测试
 *
 * @author maple
 * @version 1.0
 * @since 2020-06-21 20:19
 */
@Slf4j
public class TestMapperTest extends BaseTest {
    @Autowired
    private TestMapper testMapper;

    @Test
    void getById() {
        StatusEnum status1 = testMapper.getById(1).getStatus();
        Assertions.assertEquals(status1, StatusEnum.VALID);
        StatusEnum status2 = testMapper.getById(2).getStatus();
        Assertions.assertEquals(status2, StatusEnum.INVALID);
        log.info("status1: {}", status1);
        log.info("status2: {}", status2);
    }
}
