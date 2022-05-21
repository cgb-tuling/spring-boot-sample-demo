package org.example.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 用于Myself服务中RoleDto
 */
@Data
public class ScSecurityMyselfRoleDto implements Serializable {

    private static final long serialVersionUID = 8003617553935864730L;

    private String id;

    private String roleId;

    private String roleName;

    private String roleHostOrgId;
}
