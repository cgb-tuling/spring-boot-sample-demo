package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;

/**
 *   curl --location --request POST 'http://localhost:8080/actuator/startup'
 *   必须依赖为  springboot 2.4.0 以上
 * @author yct
 */
@SpringBootApplication
public class ActuatorApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ActuatorApplication.class);
        application.setApplicationStartup(new BufferingApplicationStartup(10000));
        application.run(args);
    }
}
