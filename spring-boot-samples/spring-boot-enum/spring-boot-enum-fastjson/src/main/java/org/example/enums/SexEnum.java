package org.example.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter//实现getValue
public enum SexEnum {
    MALE(0, "男"),
    FEMALE(1, "女");

    SexEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }
    //标记数据库存的值是value
    private final Integer value;
    private final String desc;

    public static SexEnum getItem(int code){
        for(SexEnum item : values()){
            if(item.getValue() == code){
                return item;
            }
        }
        return null;
    }

    public Integer getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}
