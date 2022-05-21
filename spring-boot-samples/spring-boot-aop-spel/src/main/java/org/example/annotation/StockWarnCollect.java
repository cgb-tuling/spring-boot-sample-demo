package org.example.annotation;

import java.lang.annotation.*;

/**
 * 库存不足等信息监控
 * Created by xdc on 2019/4/16 15:43
 *
 * @author MSI-NB
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface StockWarnCollect {

    /**
     * 客户id
     */
    String customerId();

    String username();

    /**
     * 来源
     */
    String source();

    /**
     * 请求类型 1:详情页 2:购物车去结算 3:提交订单
     */
    String pageType();
}