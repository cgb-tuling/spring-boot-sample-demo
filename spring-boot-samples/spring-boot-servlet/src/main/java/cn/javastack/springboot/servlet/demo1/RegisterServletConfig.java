package cn.javastack.springboot.servlet.demo1;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RegisterServletConfig {
    @Bean
    public ServletRegistrationBean registerServlet() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(
                new RegisterServlet(), "/registerServlet");
        servletRegistrationBean.addInitParameter("name", "javastack");
        servletRegistrationBean.addInitParameter("sex", "man");
        return servletRegistrationBean;
    }
}
