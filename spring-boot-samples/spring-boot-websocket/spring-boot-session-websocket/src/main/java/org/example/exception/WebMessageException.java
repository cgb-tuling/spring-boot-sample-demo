package org.example.exception;

/**
 * @author admin
 * @date 2020-03-25 14:51
 * @description
 */
public class WebMessageException extends RuntimeException {

    public WebMessageException(String msg) {
        super(msg);
    }

    public WebMessageException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
