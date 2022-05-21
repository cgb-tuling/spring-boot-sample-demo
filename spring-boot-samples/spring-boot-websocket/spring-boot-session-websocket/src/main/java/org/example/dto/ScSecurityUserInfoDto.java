package org.example.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.time.Instant;

/**
 * 用户实体DTO
 *
 * @author JiangYS
 */
@Slf4j
@Data
@Builder
@ToString
public class ScSecurityUserInfoDto implements Serializable {

    private static final long serialVersionUID = -2414192353558194697L;

    /**
     * Id
     */
    private String id;

    /**
     * 用户账号
     */
    private String username;

    /**
     * 用户真实名字
     */
    private String realName;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户性别
     */
    private String gender;

    /**
     * 用户手机号
     */
    private String telephoneNumber;

    /**
     * 电子邮件
     */
    private String mail;

    /**
     * 用户创建时间
     */
    private Instant created;

    /**
     * 激活状态
     */
    private boolean disabled;
}
