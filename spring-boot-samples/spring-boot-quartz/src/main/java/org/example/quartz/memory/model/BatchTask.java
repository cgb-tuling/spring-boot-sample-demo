package org.example.quartz.memory.model;

import lombok.Data;

import java.util.List;

@Data
public class BatchTask{
    /**
     * 任务编码：唯一
     */
    private String code;

    /**
     * 任务名称
     */
    private String name;

    /**
     * 任务描述
     */
    private String description;

    /**
     * 前置任务
     */
    private List<BatchTask> previous;
}
