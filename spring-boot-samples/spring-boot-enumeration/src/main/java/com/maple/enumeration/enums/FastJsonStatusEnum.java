package com.maple.enumeration.enums;

import com.alibaba.fastjson.annotation.JSONType;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.maple.enumeration.priority.FastJsonEnumSerializer2;
import lombok.Getter;
import lombok.ToString;

/**
 * 状态枚举 1:是 0否
 *
 * @author maple
 * @version 1.0
 * @since 2020-06-21 20:07
 */
@Getter
@ToString
//@JSONType(deserializer = FastJsonEnumDeserializer.class)
@JSONType(serializer = FastJsonEnumSerializer2.class)
public enum FastJsonStatusEnum {
    /**
     * 枚举值
     */
    VALID(1, "有效1"),
    INVALID(0, "无效1");

    FastJsonStatusEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    /**
     * 值
     */
    @EnumValue
    private final Integer value;
    /**
     * 描述
     */
    private final String desc;
}
