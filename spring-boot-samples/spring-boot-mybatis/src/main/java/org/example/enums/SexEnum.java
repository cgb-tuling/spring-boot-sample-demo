package org.example.enums;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author MSI-NB
 */

public enum SexEnum implements IEnum<EducationEnum, Integer> {

    /** 男 */
    MAN(0),

    /** 女 */
    WOMAN(1);

    private int value;

    SexEnum(int value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public Integer getValue() {
        return value;
    }

    @JsonCreator
    public void setValue(int value) {
        this.value = value;
    }
}

