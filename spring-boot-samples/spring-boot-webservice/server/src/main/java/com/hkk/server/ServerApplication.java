package com.hkk.server;

import com.hkk.server.impl.BillServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.xml.ws.Endpoint;

@SpringBootApplication
public class ServerApplication {

    public static void main(String[] args) {

        SpringApplication.run(ServerApplication.class, args);

        String url = "http://localhost:8082/webservice";
        Endpoint.publish(url,new BillServiceImpl());
        System.out.println("发布webService成功！");
    }

}
