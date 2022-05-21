package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.tio.websocket.starter.EnableTioWebSocketServer;

@SpringBootApplication
@EnableTioWebSocketServer
public class TioApplication {
    public static void main(String[] args) {
        SpringApplication.run(TioApplication.class, args);
    }
}
