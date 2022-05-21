package org.example.enums;

import lombok.Getter;

/**
 * @author admin
 * @date 2021-05-25
 * @description 消息状态
 */
@Getter
public enum WebMessageState {
    //未读
    unread(0),

    //已读
    read(1);


    private int code;

    WebMessageState(int code) {
        this.code = code;
    }

    public static WebMessageState getInstance(int code){
        for (WebMessageState value : WebMessageState.values()) {
            if(code == value.getCode()){
                return value;
            }
        }
        return null;
    }
}
