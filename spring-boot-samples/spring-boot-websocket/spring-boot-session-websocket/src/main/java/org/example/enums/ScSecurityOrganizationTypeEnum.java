package org.example.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.ArrayList;
import java.util.List;

/**
 * 组织机构类型枚举
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ScSecurityOrganizationTypeEnum {

    USERPOOL(-1, "用户池"),
    /**
     * 其他
     */
    NA(0, "其他"),
    /**
     * 根组织
     */
    ROOT(1, "根组织"),
    /**
     * 组织机构
     */
    ORGANIZATION(20, "组织机构"),

    ORGNODE(21, "组织机构节点"),

    ORGCOMPANY(22, "组织机构公司"),
    /**
     * 业务系统
     */
    BIZSYSTEM(30, "业务系统"),

    BIZGROUP(31, "业务系统分组"),

    BIZSERVICE(32, "业务系统实例"),

    BIZORG(33, "业务系统组织机构"),
    ;

    private int code;

    private String desc;

    ScSecurityOrganizationTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
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
        for (ScSecurityOrganizationTypeEnum organizationTypeEnum : ScSecurityOrganizationTypeEnum.values()) {
            if (organizationTypeEnum.getCode() == code) {
                return organizationTypeEnum.getDesc();
            }
        }

        return "";
    }

    public static ScSecurityOrganizationTypeEnum getInstance(int code) {
        for (ScSecurityOrganizationTypeEnum organizationTypeEnum : ScSecurityOrganizationTypeEnum.values()) {
            if (organizationTypeEnum.getCode() == code) {
                return organizationTypeEnum;
            }
        }

        return ScSecurityOrganizationTypeEnum.NA;
    }

    /**
     * 判断传入的组织机构ID是否组织机构分类
     *
     * @param orgId 组织机构ID
     *
     * @return 如果是返回true
     */
    public static boolean isOrganizationType(String orgId) {
        for (ScSecurityOrganizationTypeEnum organizationTypeEnum : ScSecurityOrganizationTypeEnum.values()) {
            if (organizationTypeEnum.toString().equals(orgId)) {
                return true;
            }
        }

        return false;
    }

    public static List<ScSecurityOrganizationTypeEnum> getValidValues() {
        List<ScSecurityOrganizationTypeEnum> values = new ArrayList<>();

        for (ScSecurityOrganizationTypeEnum organizationTypeEnum : ScSecurityOrganizationTypeEnum.values()) {
            if (organizationTypeEnum.getCode() > 1) {
                values.add(organizationTypeEnum);
            }
        }

        return values;
    }

    /**
     * 获取 业务系统 相关的枚举集合
     *
     * @return 业务系统 相关的枚举集合
     */
    public static List<ScSecurityOrganizationTypeEnum> getValidBizValues() {
        List<ScSecurityOrganizationTypeEnum> values = new ArrayList<>();

        for (ScSecurityOrganizationTypeEnum organizationTypeEnum : ScSecurityOrganizationTypeEnum.values()) {
            if (organizationTypeEnum.toString().startsWith("BIZ")) {
                values.add(organizationTypeEnum);
            }
        }

        return values;
    }

    /**
     * 获取 组织机构 相关的所有枚举集合
     *
     * @return 组织机构 相关的所有枚举集合
     */
    public static List<ScSecurityOrganizationTypeEnum> getValidOrgValues() {
        List<ScSecurityOrganizationTypeEnum> values = new ArrayList<>();

        for (ScSecurityOrganizationTypeEnum organizationTypeEnum : ScSecurityOrganizationTypeEnum.values()) {
            if (organizationTypeEnum.toString().startsWith("ORG")) {
                values.add(organizationTypeEnum);
            }
        }

        return values;
    }

    /**
     * 获取 大分类 下相关的所有枚举集合
     *
     * @return 大分类 相关的所有枚举集合
     */
    public static List<ScSecurityOrganizationTypeEnum> getAllChildValues(ScSecurityOrganizationTypeEnum typeEnum) {
        // 目前大分类只有：组织机构和业务系统
        if (typeEnum == ORGANIZATION) {
            return getValidOrgValues();
        } else if (typeEnum == BIZSYSTEM) {
            return getValidBizValues();
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 得到系统平台初始化的组织机构类型列表
     *
     * @return 系统平台初始化的组织机构类型列表
     */
    public static List<ScSecurityOrganizationTypeEnum> getPlatformOrgTypeEnum() {
        List<ScSecurityOrganizationTypeEnum> typeEnumList = new ArrayList<>();
        typeEnumList.add(USERPOOL);
        typeEnumList.add(ROOT);
        typeEnumList.add(ORGANIZATION);
        typeEnumList.add(BIZSYSTEM);
        return typeEnumList;
    }
}
