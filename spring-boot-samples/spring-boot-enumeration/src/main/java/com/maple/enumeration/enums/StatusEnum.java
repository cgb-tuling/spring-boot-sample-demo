package com.maple.enumeration.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.ToString;

/**
 * 状态枚举 1:是 0否
 *
 * @author maple
 * @version 1.0
 * @since 2020-06-21 20:07
 */
@ToString
// 优先测试 @JsonSerialize(using = JacksonEnumSerializer2.class)
@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum StatusEnum {
    /**
     * 枚举值
     */
    VALID(1, "有效"),
    INVALID(0, "无效"),
    GG(99, "99");

    StatusEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

//    @JsonCreator
//    public static StatusEnum getItem(int code){
//        for(StatusEnum item : values()){
//            if(item.getValue() == code){
//                return item;
//            }
//        }
//        return null;
//    }

    /**
     * 值
     */
    @EnumValue
//    @JsonValue
    private final Integer value;
    /**
     * 描述
     */
//    @JsonValue
    private final String desc;

    public String getDesc() {
        return desc + "getter";
    }

    public Integer getValue() {
        return value;
    }
}
