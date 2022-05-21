package com.example.data.listener.test;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.example.data.domain.DemoData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

// 如果没有特殊说明，下面的案例将默认使用这个监听器
@Slf4j
public class ConverterDataListener extends AnalysisEventListener<DemoData> {
    private ObjectMapper mapper = new ObjectMapper();

    List<DemoData> list = new ArrayList<DemoData>();

    /**
     * 如果使用了spring,请使用这个构造方法。每次创建Listener的时候需要把spring管理的类传进来
     */
    public ConverterDataListener() {
    }

    /**
     * 这个每一条数据解析都会来调用
     *
     * @param data
     * @param context
     */
    @Override
    public void invoke(DemoData data, AnalysisContext context) {
        try {
            log.info("解析到一条数据:{}", mapper.writeValueAsString(data));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        list.add(data);
    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        try {
            log.info("所有数据解析完成:{}", mapper.writeValueAsString(list));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
