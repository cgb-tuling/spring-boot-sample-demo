package com.example.data.controller;

import com.example.data.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "数据清洗")
@RestController
@RequestMapping("data")
public class DataController {
    @Autowired
    private ArrearDataTransfer arrearDataTransfer;
    @Autowired
    private PayDataTransfer payDataTransfer;

    @Autowired
    private RefundDataTransfer refundDataTransfer;

    @Autowired
    private YucunDataTransfer yucunDataTransfer;

    @Autowired
    private OffsetDataTransfer offsetDataTransfer;

    @Autowired
    private YuncunOnetDataTransfer yuncunOnetDataTransfer;

    @Autowired
    private PaymentTkDataTransfer paymentTkDataTransfer;

    @ApiOperation(value = "应收款数据清洗", notes = "应收款数据清洗")
    @GetMapping("arr")
    public String arrear() {
        arrearDataTransfer.doClean();
        return "SUCCESS";
    }

    @ApiOperation(value = "已收款数据清洗", notes = "已收款数据清洗")
    @GetMapping("pay")
    public String paymentDetail() {
        payDataTransfer.doClean();
        return "SUCCESS";
    }

    @ApiOperation(value = "退款数据清洗", notes = "退款数据清洗")
    @GetMapping("tuikuan")
    public String tuikuan() {
        refundDataTransfer.doClean();
        return "SUCCESS";
    }

    @ApiOperation(value = "预存款余额数据清洗", notes = "预存款余额数据清洗")
    @GetMapping("yucun")
    public String yucun() {
        yucunDataTransfer.doClean();
        return "SUCCESS";
    }

    @ApiOperation(value = "冲抵明细数据清洗", notes = "冲抵明细数据清洗")
    @GetMapping("offset")
    public String offsetImport() {
        offsetDataTransfer.doClean();
        return "SUCCESS";
    }

    @ApiOperation(value = "第一次预交金额数据清洗", notes = "第一次预交金额数据清洗")
    @GetMapping("yucun-one")
    public String offsetOne() {
        yuncunOnetDataTransfer.doClean();
        return "SUCCESS";
    }

    @ApiOperation(value = "实收退款明细数据清洗", notes = "实收退款明细数据清洗")
    @GetMapping("payment-tk")
    public String paymentTk() {
        paymentTkDataTransfer.doClean();
        return "SUCCESS";
    }
}
