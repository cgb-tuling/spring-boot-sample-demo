package com.hkk.server.impl;

import com.hkk.server.service.BillService;

import javax.jws.WebService;

@WebService
public class BillServiceImpl implements BillService {

    @Override
    public String getName(String name) {
        return "hello:" + name;
    }
}
