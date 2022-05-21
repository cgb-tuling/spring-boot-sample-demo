package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Indexed;

@SpringBootApplication
@Indexed
public class SpringIndexApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringIndexApplication.class, args);
	}
}
