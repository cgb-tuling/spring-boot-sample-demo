package org.example;

import org.assertj.core.util.Sets;
import org.example.domain.User;
import org.example.service.SpelService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;

/**
 * @author yct
 */
@SpringBootTest(classes = SpringSpelApplication.class)
public class SpelServiceTest {
    @Autowired
    private SpelService spelService;
    @Test
    public void test1(){
        HashSet<Long> objects = Sets.newHashSet();
        objects.add(200L);
        objects.add(300L);

        spelService.validateCarts(100L,objects,new User("zhangsan111","11232a"));
    }
}
