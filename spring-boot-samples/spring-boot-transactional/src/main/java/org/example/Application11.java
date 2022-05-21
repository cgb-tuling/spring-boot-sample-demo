package org.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.example.dao")
public class Application11 {

	public static void main(String[] args) {
		SpringApplication.run(Application11.class, args);
	}
}
