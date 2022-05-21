package org.example.config;

import org.example.scope.BeanScopeModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @author yuancetian
 */
@Configuration
public class AppConfig {
    //    <bean id="threadBean" class="com.javacode2018.lesson001.demo4.BeanScopeModel" scope="thread">
//        <constructor-arg index="0" value="thread"/>
//    </bean>
    @Bean("threadBean")
    @Scope("thread")
    public BeanScopeModel threadBean() {
        return new BeanScopeModel("thread");
    }
}
