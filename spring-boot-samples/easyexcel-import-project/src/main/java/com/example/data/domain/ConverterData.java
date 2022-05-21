package com.example.data.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.format.NumberFormat;
import com.example.data.convert.CustomStringConverter;
import lombok.Data;

@Data
public class ConverterData {
    /**
     * converter属性定义自己的字符串转换器
     */
    @ExcelProperty(converter = CustomStringConverter.class)
    private String string;
    /**
     * 这里用string 去接日期才能格式化
     */
    @DateTimeFormat("yyyy年MM月dd日 HH时mm分ss秒")
    private String date;
    /**
     * 我想接收百分比的数字
     */
    @NumberFormat("#.##%")
    private String doubleData;
}