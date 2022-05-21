package org.example.domain;

import lombok.Data;
import org.example.annotation.NeedSetValue;

import java.io.Serializable;

/**
 * @author yct
 */
@Data
public class Order implements Serializable {
    private static final long serialVersionUID = -3302550031336015747L;
    private String id;
    private String customerId;
    @NeedSetValue(beanClass = UserDao.class, param = "customerId", method = "query", targetFiled = "customerName")
    private String customerName;
}