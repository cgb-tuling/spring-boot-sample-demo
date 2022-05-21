package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.task.configuration.EnableTask;

@EnableTask
@SpringBootApplication
public class SpringBatchTaskApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringBatchTaskApplication.class, args);
  }
}
