package org.example.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 用户类型 枚举
 *
 * @author JiangYS
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ScSecurityUserStateEnum {
    ENABLE(0, "正常"),
    DISABLE(1, "无效"),
    LOCKED(2, "锁定"),
    NA(-1, "其他");

    @JsonProperty
    private int code;

    @JsonProperty
    private String desc;

    ScSecurityUserStateEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @JsonCreator
    public static ScSecurityUserStateEnum fromJson(@JsonProperty("code") int code) {
        return getInstance(code);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static String getDescString(int code) {
        for (ScSecurityUserStateEnum scSecurityUserStateEnum : ScSecurityUserStateEnum.values()) {
            if (scSecurityUserStateEnum.getCode() == code) {
                return scSecurityUserStateEnum.getDesc();
            }
        }

        return "";
    }

    public static ScSecurityUserStateEnum getInstance(int code) {
        for (ScSecurityUserStateEnum scSecurityUserStateEnum : ScSecurityUserStateEnum.values()) {
            if (scSecurityUserStateEnum.getCode() == code) {
                return scSecurityUserStateEnum;
            }
        }

        return ScSecurityUserStateEnum.NA;
    }
}
