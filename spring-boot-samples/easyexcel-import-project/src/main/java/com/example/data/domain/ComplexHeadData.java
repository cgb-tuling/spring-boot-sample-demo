package com.example.data.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * 复杂头导入
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ComplexHeadData {
    /**
     * 主标题 将整合为一个单元格效果如下：
     * —————————————————————————
     * |          主标题        |
     * —————————————————————————
     * |字符串标题|日期标题|数字标题|
     * —————————————————————————
     */
    @ExcelProperty({"主标题", "字符串标题"})
    private String string;
    @ExcelProperty({"主标题", "日期标题"})
    private Date date;
    @ExcelProperty({"主标题", "数字标题"})
    private Double doubleData;
}