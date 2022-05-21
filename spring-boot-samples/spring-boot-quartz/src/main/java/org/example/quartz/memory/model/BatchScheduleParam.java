package org.example.quartz.memory.model;

import lombok.Data;

@Data
public class BatchScheduleParam {

    /**
     * 任务计划ID
     */
    private String scheduleId;

    /**
     * 任务计划code
     */
    private String scheduleCode;

    /**
     * 参数名
     */
    private String paramName;

    /**
     * 参数值
     */
    private String paramValue;
}
