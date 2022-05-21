package org.example.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.example.annotation.Authorized;
import org.example.domain.Order;
import org.springframework.web.bind.annotation.*;

/**
 * @author yct
 */
@RestController
@RequestMapping("/aop")
public class OrderController {
    @Authorized
    @RequestMapping(value = "/getinfobyid", method = RequestMethod.POST)
    @ApiOperation("根据商品Id查询商品信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "authtoken", required = true, value = "authtoken", dataType =
                    "String"),
    })
    public String getGoodsByGoodsId(@RequestHeader String authtoken, @RequestBody Order order) {

        //todo 业务逻辑
        return "success";
    }
}
