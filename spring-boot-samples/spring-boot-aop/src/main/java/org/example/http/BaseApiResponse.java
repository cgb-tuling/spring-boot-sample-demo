package org.example.http;

import lombok.Data;

/**
 * @author yct
 */
@Data
public class BaseApiResponse {
    private boolean ok;
    private String message;
    private Object data;
}
