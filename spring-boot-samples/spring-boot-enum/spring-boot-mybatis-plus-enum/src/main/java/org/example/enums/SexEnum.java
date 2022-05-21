package org.example.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
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
    @EnumValue
    private final Integer value;
    private final String desc;
}
