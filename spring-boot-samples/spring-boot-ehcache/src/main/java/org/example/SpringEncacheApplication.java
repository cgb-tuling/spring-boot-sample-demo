package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SpringEncacheApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringEncacheApplication.class, args);
	}

}
