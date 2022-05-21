package org.example.enums;

import javax.persistence.AttributeConverter;

/**
 * 组织机构类型枚举 数据库转换器
 * 在数据库字段中，将存储枚举中的Code值，而不是原始值
 */
public class ScSecurityOrganizationTypeEnumConvert implements AttributeConverter<ScSecurityOrganizationTypeEnum, Integer> {
    @Override
    public Integer convertToDatabaseColumn(ScSecurityOrganizationTypeEnum scSecurityOrganizationTypeEnum) {
        return scSecurityOrganizationTypeEnum.getCode();
    }

    @Override
    public ScSecurityOrganizationTypeEnum convertToEntityAttribute(Integer integer) {
        return ScSecurityOrganizationTypeEnum.getInstance(integer);
    }
}
