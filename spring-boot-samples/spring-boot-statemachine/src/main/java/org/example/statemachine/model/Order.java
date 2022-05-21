package org.example.statemachine.model;

import lombok.Data;
import org.example.statemachine.enums.OrderStatus;

import java.util.Date;

@Data
public class Order {
    private Integer id;
    private OrderStatus status;
    private Date createDate;
    private String payUser;
    private Date payDate;
}
