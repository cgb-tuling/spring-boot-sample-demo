package org.example.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yuancetian
 */
@Data
public class Order {
    private String orderNo;
    private String orderKey;
    private Date createTime;
    private BigDecimal price;
    private long expire;
}
