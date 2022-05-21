package com.example.data.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ExcelTemplate {
    /**
     * 第一列，第二列
     * x0,x1...
     */
    @ExcelProperty(index = 0)
    private String x0;
    @ExcelProperty(index = 1)
    private String x1;
    @ExcelProperty(index = 2)
    private String x2;
    @ExcelProperty(index = 3)
    private String x3;
    @ExcelProperty(index = 4)
    private String x4;
    @ExcelProperty(index = 5)
    private String x5;
    @ExcelProperty(index = 6)
    private String x6;
    @ExcelProperty(index = 7)
    private String x7;
    @ExcelProperty(index = 8)
    private String x8;
    @ExcelProperty(index = 9)
    private String x9;
}

