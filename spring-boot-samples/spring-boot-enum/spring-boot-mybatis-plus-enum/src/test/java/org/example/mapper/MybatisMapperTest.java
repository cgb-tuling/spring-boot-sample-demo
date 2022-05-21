package org.example.mapper;


import lombok.extern.slf4j.Slf4j;
import org.example.domain.TestDO;
import org.example.enums.SexEnum;
import org.example.enums.StatusEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class MybatisMapperTest {
    @Autowired
    private TestMapper testMapper;


    @Test
    public void test01() {
        TestDO testDO1 = testMapper.getById(1);
        TestDO testDO2 = testMapper.getById(2);
        StatusEnum status1 = testDO1.getStatus();
        Assertions.assertEquals(status1, StatusEnum.VALID);
        Assertions.assertEquals(testDO1.getSex(), SexEnum.MALE);
        StatusEnum status2 = testDO2.getStatus();
        Assertions.assertEquals(status2, StatusEnum.INVALID);
        Assertions.assertEquals(testDO2.getSex(), SexEnum.FEMALE);
        log.info("status1: {} sex: {}", status1,testDO1.getSex());
        log.info("status2: {} sex: {} ", status2,testDO2.getSex());
    }
}
