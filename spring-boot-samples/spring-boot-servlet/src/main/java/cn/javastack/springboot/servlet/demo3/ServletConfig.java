package cn.javastack.springboot.servlet.demo3;

import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

/**
 * @author Java技术栈
 */
@Component
public class ServletConfig implements ServletContextInitializer {

    @Override
    public void onStartup(ServletContext servletContext) {
        ServletRegistration initServlet = servletContext
                .addServlet("initServlet", InitServlet.class);
        initServlet.addMapping("/initServlet");
        initServlet.setInitParameter("name", "javastack");
        initServlet.setInitParameter("sex", "man");
    }

}