package org.example.quartz.memory.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BatchSchedule {
    public static final String SCHEDULE_KEY = "scheduleJob";
    public static final String DEFAULT_CODE_PREFIX = "BS";
    private static final int RUN_ONE_TIME = 1;

    /**
     * 计划编码
     */
    private String code;

    /**
     * 计划名称
     */
    private String name;

    /**
     * 计划状态: 整个生命周期状态
     */
    private Integer status;

    /**
     * 执行表达式类型
     */
    private Integer cronType;

    /**
     * 执行表达式
     */
    private String cronExpression;

    /**
     * 描述
     */
    private String description;

    /**
     * 处理业务类
     */
    private String interfaceName;

    /**
     * 任务编码（任务组的概念）
     */
    private String taskCode;

    /**
     * 开始时间（最近）
     */
    private Date startDate;

    /**
     * 结束时间（最近）
     */
    private Date endDate;

    /**
     * 前置计划列表
     */
    private List<BatchSchedule> dependencies;

    /**
     * 参数列表
     */
    private List<BatchScheduleParam> params;
}
