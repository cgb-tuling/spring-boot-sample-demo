package com.test.webservice.domain;

import lombok.Data;

@Data
public class SysUser {
    private String account;
    private String salt;
    private String password;
    private String isDelete;

}
