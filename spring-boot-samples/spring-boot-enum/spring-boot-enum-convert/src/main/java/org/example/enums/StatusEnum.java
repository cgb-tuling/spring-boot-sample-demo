package org.example.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import lombok.Getter;

@Getter//实现getValue
public enum StatusEnum implements IEnum<Integer> {
    VALID(1, "有效"),
    INVALID(0, "无效");

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
}
