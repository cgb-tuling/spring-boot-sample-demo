package com.zhouyu.springboot;

public class JettyWebServer implements WebServer{
    @Override
    public void start() {
        System.out.println("启动Jetty");
    }
}
