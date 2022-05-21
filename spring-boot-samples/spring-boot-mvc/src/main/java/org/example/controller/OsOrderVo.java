package org.example.controller;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author yct
 */
@Data
public class OsOrderVo implements Serializable {
    private static final long serialVersionUID = -8985454113782370854L;
    /**
     * 问题标题
     */
    @NotBlank
    private String title;
    /**
     * 所属系统
     * 数据字典维护
     */
    @NotBlank
    private String system;
    /**
     * 附件
     * 保存在OSS的文件id
     */
    private String attachment;
    /**
     * 问题描述
     */
    @NotBlank
    private String issueDescription;
    /**
     * 问题状态
     * 数据字典维护
     */
    private Integer issueStatus;
    /**
     * 问题紧急性
     */
    private Integer issueUrgent;
}