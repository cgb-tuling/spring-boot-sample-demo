package org.example.callback;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

/**
 * @author admin
 */
@Validated
public interface WebMessageCallBack {

    /**
     * @description     需要实现WebMessageCallBack类，重写callBack方法
     * @param  message  客户端发来的消息
     * @return Object   响应给客户端的信息
     */
    Object callBack(@NotEmpty String message);
}
