package com.example.data.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.example.data.listener.DataImportListener;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Api(tags = "数据导入")
@RestController
@RequestMapping("import")
public class ExcelController {


/*    *
            *基础数据

    @ApiOperation(value = "基础数据导入", notes = "基础数据导入")
    @PostMapping(value = "index", headers = "content-type=multipart/form-data")
    @ResponseBody

    public String index(@ApiParam(value = "excel文件", required = true) @RequestPart("file") MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        Assert.notNull(originalFilename, "file name must not be null.");
        ExcelAnalysisHelper<LinkedHashMap> excelAnalysisHelper = new ExcelAnalysisHelper<>();
        List<LinkedHashMap> list = excelAnalysisHelper.getList(file, LinkedHashMap.class, 0, 3);

        return "success";
    }*/

    /**
     * 基础数据
     */
    @ApiOperation(value = "基础数据导入", notes = "基础数据导入")
    @PostMapping(value = "base", headers = "content-type=multipart/form-data")
    @ResponseBody
    public String baseImport(@ApiParam(value = "excel文件", required = true) @RequestPart("file") MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        Assert.notNull(originalFilename, "file name must not be null.");
        String fileExt = originalFilename.substring(originalFilename.indexOf(".") + 1);
        ExcelReaderBuilder excelReaderBuilder = EasyExcel.read(file.getInputStream(), new DataImportListener("base", 1));
        if ("xlsx".equals(fileExt)) {
            excelReaderBuilder.excelType(ExcelTypeEnum.XLSX);
        } else {
            excelReaderBuilder.excelType(ExcelTypeEnum.XLS);
        }
        excelReaderBuilder
                .autoCloseStream(true)
                .sheet()
                .headRowNumber(1)
                .doRead();
        return "success";
    }


    /**
     * 应收款数据导入
     */
    @ApiOperation(value = "应收款数据导入", notes = "应收款数据导入")
    @PostMapping(value = "arr", headers = "content-type=multipart/form-data")
    @ResponseBody
    public String arrearImport(@ApiParam(value = "excel文件", required = true) @RequestPart("file") MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        Assert.notNull(originalFilename, "file name must not be null.");
        String fileExt = originalFilename.substring(originalFilename.indexOf(".") + 1);
        DataImportListener listener = new DataImportListener("arr", 3);
        ExcelReaderBuilder excelReaderBuilder = EasyExcel.read(file.getInputStream(), listener);
        if ("xlsx".equals(fileExt)) {
            excelReaderBuilder.excelType(ExcelTypeEnum.XLSX);
        } else {
            excelReaderBuilder.excelType(ExcelTypeEnum.XLS);
        }
        excelReaderBuilder
                .autoCloseStream(true)
                .sheet()
                .headRowNumber(3)
                .doRead();
        return "success";
    }

    /**
     * 已收款数据导入
     */
    @ApiOperation(value = "已收款数据导入", notes = "已收款数据导入")
    @PostMapping(value = "pay", headers = "content-type=multipart/form-data")
    @ResponseBody
    public String paymentdetailImport(@ApiParam(value = "excel文件", required = true) @RequestPart("file") MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        Assert.notNull(originalFilename, "file name must not be null.");
        String fileExt = originalFilename.substring(originalFilename.indexOf(".") + 1);
        ExcelReaderBuilder excelReaderBuilder = EasyExcel.read(file.getInputStream(), new DataImportListener("pay", 3));
        if ("xlsx".equals(fileExt)) {
            excelReaderBuilder.excelType(ExcelTypeEnum.XLSX);
        } else {
            excelReaderBuilder.excelType(ExcelTypeEnum.XLS);
        }
        excelReaderBuilder
                .autoCloseStream(true)
                .sheet()
                .headRowNumber(3)
                .doRead();
        return "success";
    }

    /**
     * 退款余额数据导入
     */
    @ApiOperation(value = "退款余额数据导入", notes = "退款余额数据导入")
    @PostMapping(value = "tuikuan", headers = "content-type=multipart/form-data")
    @ResponseBody
    public String tuikuanImport(@ApiParam(value = "excel文件", required = true) @RequestPart("file") MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        Assert.notNull(originalFilename, "file name must not be null.");
        String fileExt = originalFilename.substring(originalFilename.indexOf(".") + 1);

        ExcelReaderBuilder excelReaderBuilder = EasyExcel.read(file.getInputStream(), new DataImportListener("refund", 1));
        excelReaderBuilder.ignoreEmptyRow(false);
        if ("xlsx".equals(fileExt)) {
            excelReaderBuilder.excelType(ExcelTypeEnum.XLSX);
        } else {
            excelReaderBuilder.excelType(ExcelTypeEnum.XLS);
        }
        excelReaderBuilder
                .autoCloseStream(true)
                .sheet()
                .headRowNumber(1)
                .doRead();
        return "success";
    }

    /**
     * 预存款数据导入
     */
    @ApiOperation(value = "预存款数据导入", notes = "预存款数据导入")
    @PostMapping(value = "yucun", headers = "content-type=multipart/form-data")
    @ResponseBody
    public String yucunImport(@ApiParam(value = "excel文件", required = true) @RequestPart("file") MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        Assert.notNull(originalFilename, "file name must not be null.");
        String fileExt = originalFilename.substring(originalFilename.indexOf(".") + 1);

        ExcelReaderBuilder excelReaderBuilder = EasyExcel.read(file.getInputStream(), new DataImportListener("yucun", 1));
        excelReaderBuilder.ignoreEmptyRow(false);
        if ("xlsx".equals(fileExt)) {
            excelReaderBuilder.excelType(ExcelTypeEnum.XLSX);
        } else {
            excelReaderBuilder.excelType(ExcelTypeEnum.XLS);
        }
        excelReaderBuilder
                .autoCloseStream(true)
                .sheet()
                .headRowNumber(1)
                .doRead();
        return "success";
    }

    /**
     * 冲抵明细
     */
    @ApiOperation(value = "冲抵明细数据导入", notes = "冲抵明细数据导入")
    @PostMapping(value = "offset", headers = "content-type=multipart/form-data")
    @ResponseBody
    public String offsetImport(@ApiParam(value = "excel文件", required = true) @RequestPart("file") MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        Assert.notNull(originalFilename, "file name must not be null.");
        String fileExt = originalFilename.substring(originalFilename.indexOf(".") + 1);

        ExcelReaderBuilder excelReaderBuilder = EasyExcel.read(file.getInputStream(), new DataImportListener("offset", 3));
        excelReaderBuilder.ignoreEmptyRow(false);
        if ("xlsx".equals(fileExt)) {
            excelReaderBuilder.excelType(ExcelTypeEnum.XLSX);
        } else {
            excelReaderBuilder.excelType(ExcelTypeEnum.XLS);
        }
        excelReaderBuilder
                .autoCloseStream(true)
                .sheet()
                .headRowNumber(3)
                .doRead();
        return "success";
    }

    /**
     * 第一次预交
     */
    @ApiOperation(value = "第一次预交金额数据导入", notes = "第一次预交金额数据导入")
    @PostMapping(value = "yucun-one", headers = "content-type=multipart/form-data")
    @ResponseBody
    public String yucunOneImport(@ApiParam(value = "excel文件", required = true) @RequestPart("file") MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        Assert.notNull(originalFilename, "file name must not be null.");
        String fileExt = originalFilename.substring(originalFilename.indexOf(".") + 1);

        ExcelReaderBuilder excelReaderBuilder = EasyExcel.read(file.getInputStream(), new DataImportListener("yucun-one", 1));
        excelReaderBuilder.ignoreEmptyRow(false);
        if ("xlsx".equals(fileExt)) {
            excelReaderBuilder.excelType(ExcelTypeEnum.XLSX);
        } else {
            excelReaderBuilder.excelType(ExcelTypeEnum.XLS);
        }
        excelReaderBuilder
                .autoCloseStream(true)
                .sheet()
                .headRowNumber(1)
                .doRead();
        return "success";
    }

    /**
     * 实收退款明细
     */
    @ApiOperation(value = "实收退款明细数据导入", notes = "实收退款明细数据导入")
    @PostMapping(value = "payment-tk", headers = "content-type=multipart/form-data")
    @ResponseBody
    public String paymentTkImport(@ApiParam(value = "excel文件", required = true) @RequestPart("file") MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        Assert.notNull(originalFilename, "file name must not be null.");
        String fileExt = originalFilename.substring(originalFilename.indexOf(".") + 1);

        ExcelReaderBuilder excelReaderBuilder = EasyExcel.read(file.getInputStream(), new DataImportListener("payment-tk", 1));
        excelReaderBuilder.ignoreEmptyRow(false);
        if ("xlsx".equals(fileExt)) {
            excelReaderBuilder.excelType(ExcelTypeEnum.XLSX);
        } else {
            excelReaderBuilder.excelType(ExcelTypeEnum.XLS);
        }
        excelReaderBuilder
                .autoCloseStream(true)
                .sheet()
                .headRowNumber(1)
                .doRead();
        return "success";
    }

}
