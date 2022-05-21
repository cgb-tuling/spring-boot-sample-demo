package org.example.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.example.interceptor.ContentCachingRequestWrapper;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@Slf4j
@WebFilter(filterName = "test",urlPatterns = "/*")
public class SignValidateFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper((HttpServletRequest) request);
        String body = IOUtils.toString(requestWrapper.getBody(),request.getCharacterEncoding());
        log.info(body);
        chain.doFilter(requestWrapper, response);
    }

    @Override
    public void destroy() {

    }
}
