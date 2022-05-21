package org.example.encrypt.util;

import org.assertj.core.api.Assert;
import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Description: spring-boot-encrypt
 * User: yuanct
 * Date: 2019/7/19 2:11 PM
 */
@DisplayName("测试类")
@SpringBootTest
public class DatabaseTest {

    @Autowired
    private StringEncryptor encryptor;

    @Test
    public void getPass() {
        String url = encryptor.encrypt("jdbc:mysql://localhost:3306/mydb?autoReconnect=true&serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf-8");
        String name = encryptor.encrypt("root");
        String password = encryptor.encrypt("123456");
        System.out.println("database url: " + url);
        System.out.println("database name: " + name);
        System.out.println("database password: " + password);
        Assertions.assertTrue(url.length() > 0);
        Assertions.assertTrue(name.length() > 0);
        Assertions.assertTrue(password.length() > 0);
    }
}
