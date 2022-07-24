package com.zhouyu.springboot;

import org.apache.catalina.*;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardEngine;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.startup.Tomcat;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import java.util.Map;

public class ZhouyuSpringApplication {

    public static void run(Class clazz){

        // Spring容器
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.register(clazz);
        applicationContext.refresh();

        WebServer webServer = getWebServer(applicationContext);
        webServer.start();

    }

    public static WebServer getWebServer(WebApplicationContext applicationContext){
        Map<String, WebServer> beansOfType = applicationContext.getBeansOfType(WebServer.class);

        if (beansOfType.isEmpty()) {
            throw new NullPointerException();
        }

        if (beansOfType.size() > 1) {
            throw new IllegalStateException();
        }

        return beansOfType.values().stream().findFirst().get();
    }










}
