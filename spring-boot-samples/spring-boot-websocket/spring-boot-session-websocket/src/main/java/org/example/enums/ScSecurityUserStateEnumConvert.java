package org.example.enums;

import javax.persistence.AttributeConverter;

/**
 * 用户状态类型枚举 数据库转换器
 * 在数据库字段中，将存储枚举中的Code值，而不是原始值
 *
 * @author JiangYS
 */
public class ScSecurityUserStateEnumConvert implements AttributeConverter<ScSecurityUserStateEnum, Integer> {
    @Override
    public Integer convertToDatabaseColumn(ScSecurityUserStateEnum scSecurityUserStateEnum) {
        return scSecurityUserStateEnum.getCode();
    }

    @Override
    public ScSecurityUserStateEnum convertToEntityAttribute(Integer integer) {
        return ScSecurityUserStateEnum.getInstance(integer);
    }
}
