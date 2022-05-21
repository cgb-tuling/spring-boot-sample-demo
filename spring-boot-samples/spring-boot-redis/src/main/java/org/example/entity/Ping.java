package org.example.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yuancetian
 */
@Data
public class Ping {
    private String applicationName;
    private Date createTime;
    private long expire;
}
