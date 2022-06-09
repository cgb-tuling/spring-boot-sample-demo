package org.example.exception;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;

/**
 * @author yuancetian
 */
public class CustomErrorHandler extends DefaultResponseErrorHandler {
    
    public void handleError(ClientHttpResponse response) throws IOException {
        // todo
    }
}