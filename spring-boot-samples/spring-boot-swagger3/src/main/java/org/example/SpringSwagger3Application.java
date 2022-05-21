package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

@SpringBootApplication
@EnableOpenApi
public class SpringSwagger3Application {
    public static void main(String[] args) {
        SpringApplication.run(SpringSwagger3Application.class, args);
    }
}
