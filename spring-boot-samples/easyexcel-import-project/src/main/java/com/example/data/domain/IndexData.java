package com.example.data.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IndexData {
    /**
    * 导出的excel第二列和第四列将空置
    */
    @ExcelProperty(value = "字符串标题", index = 0)
    private String string;
    @ExcelProperty(value = "日期标题", index = 2)
    private Date date;
    @ExcelProperty(value = "数字标题", index = 4)
    private Double doubleData;
}