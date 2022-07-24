package com.zhouyu.springboot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebServerAutoConfiguration implements AutoConfiguration {

    @Bean
    @ConditionalOnClass("org.apache.catalina.startup.Tomcat")
    public TomcatWebServer tomcatWebServer(){
        return new TomcatWebServer();
    }

    @Bean
    @ConditionalOnClass("org.eclipse.jetty.server.Server")
    public JettyWebServer jettyWebServer(){
        return new JettyWebServer();
    }
}
