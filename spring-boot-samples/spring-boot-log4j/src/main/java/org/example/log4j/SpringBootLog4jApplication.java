package org.example.log4j;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class SpringBootLog4jApplication {
	public static void main(String[] args) {
		log.info("spring boot 开始启动");
		SpringApplication.run(SpringBootLog4jApplication.class, args);
		log.info("spring boot 启动结束");
	}

}

