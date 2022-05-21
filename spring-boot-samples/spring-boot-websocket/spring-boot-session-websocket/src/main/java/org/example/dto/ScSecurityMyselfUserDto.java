package org.example.dto;

import lombok.Data;
import org.example.enums.ScSecurityUserStateEnum;
import org.example.enums.ScSecurityUserStateEnumConvert;


import javax.persistence.Convert;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 用于Myself服务中UserDto
 */
@Data
public class ScSecurityMyselfUserDto implements Serializable {

    private static final long serialVersionUID = -4006665052814596530L;

    private String id;

    private String username;

    private String realName;

    private String telephoneNumber;

    private String mail;

    @Convert(converter = ScSecurityUserStateEnumConvert.class)
    private ScSecurityUserStateEnum disabled = ScSecurityUserStateEnum.DISABLE;

    private int isFreeze;

    private String remark;

    private Set<ScSecurityMyselfRoleDto> roles = new HashSet<>();

    private Set<ScSecurityMyselfOrgDto> organizations = new HashSet<>();
}
