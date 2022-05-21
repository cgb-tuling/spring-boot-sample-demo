package org.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author yct
 */
@Configuration
public class PatchRestTemplateConfig {

    @Bean
    public RestTemplate patchRestTemplate() {
        return new RestTemplate(simpleClientHttpRequestFactory());
    }

    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
//        RestTemplate工厂类的默认实现中，不支持使用PATCH方法，需要将RestTemplate配置类的工厂对象修改为HttpComponentsClientHttpRequestFactory
//       SimpleClientHttpRequestFactory factory=new SimpleClientHttpRequestFactory();
//      上一行被注释掉的是Spring自己的实现，下面是依赖了httpclient包后的实现
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(5000);
        factory.setReadTimeout(5000);
        return factory;
    }
}