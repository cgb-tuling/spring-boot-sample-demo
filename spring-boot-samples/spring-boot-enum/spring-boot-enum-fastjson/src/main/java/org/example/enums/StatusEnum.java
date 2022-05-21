package org.example.enums;

import com.alibaba.fastjson.annotation.JSONType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import org.example.serialize.FastJsonEnumDeserializer;

@JSONType(deserializer = FastJsonEnumDeserializer.class)
@Getter//实现getValue
public enum StatusEnum {
    VALID(1, "有效"),
    INVALID(0, "无效");
//
//    @JsonCreator
//    StatusEnum(@JsonProperty("value") Integer value) {
//        this.value = value;
//    }

    StatusEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    //标记数据库存的值是value
    private final Integer value;
    private final String desc;

    public Integer getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static StatusEnum getItem(int code) {
        for (StatusEnum item : values()) {
            if (item.getValue() == code) {
                return item;
            }
        }
        return null;
    }

}
