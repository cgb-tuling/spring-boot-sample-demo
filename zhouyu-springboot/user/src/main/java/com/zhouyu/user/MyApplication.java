package com.zhouyu.user;

import com.zhouyu.springboot.WebServerAutoConfiguration;
import com.zhouyu.springboot.ZhouyuSpringApplication;
import com.zhouyu.springboot.ZhouyuSpringBootApplication;
import org.springframework.context.annotation.Import;

@ZhouyuSpringBootApplication
public class MyApplication { // spring.factories

    public static void main(String[] args) {
        ZhouyuSpringApplication.run(MyApplication.class);
    }
}
