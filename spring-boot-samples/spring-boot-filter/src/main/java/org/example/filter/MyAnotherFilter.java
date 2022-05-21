package org.example.filter;

import javax.servlet.*;
import java.io.IOException;

public class MyAnotherFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("进入 MyAnotherFilter");
        chain.doFilter(request, response);
    }
}
