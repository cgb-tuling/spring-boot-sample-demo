package org.example.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author MSI-NB
 */
public enum EducationEnum implements IEnum<EducationEnum, String> {

    /** 小学 */
    PRIMARY_SCHOOL("PRIMARY"),

    /**
     * 初中
     * */
    JUNIOR_SCHOOL("JUNIOR"),

    /**
     * 高中
     * */
    HIGH_SCHOOL("HIGH"),

    /**
     * 大学
     * */
    UNIVERSITY_SCHOOL("UNIVERSITY")
    ;

    private String value;

    EducationEnum(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public void setValue(String value) {
        this.value = value;
    }
}

