package org.example.enums;

import lombok.Getter;

/**
 * @author admin
 * @date 2021-06-09
 * @description 消息类别
 */
@Getter
public enum WebMessageCategory {

    SYSTEM(0,"系统")
    ,USER(1,"用户")
    ,WORK_FLOW(2,"流程")
    ,PUBLIC_NOTICE(3,"公告")
    ,ADVERTISEMENT(4,"广告")
    ,OTHER(99,"其他");

    private int code;
    private String desc;

    WebMessageCategory(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static WebMessageCategory getInstance(int code){
        for (WebMessageCategory value : WebMessageCategory.values()) {
            if(code == value.getCode()){
                return value;
            }
        }
        return null;
    }
}
