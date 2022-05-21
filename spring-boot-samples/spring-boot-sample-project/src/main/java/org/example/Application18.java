package org.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * spring boot启动类
 * @author tian
 */
@SpringBootApplication
@MapperScan("org.example.dao")
public class Application18 {

	public static void main(String[] args) {
		SpringApplication.run(Application18.class, args);
	}
}
