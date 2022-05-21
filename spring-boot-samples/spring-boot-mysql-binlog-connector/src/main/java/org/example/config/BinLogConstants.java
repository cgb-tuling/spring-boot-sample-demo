package org.example.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 监听配置信息
 *
 * @author yuancetian
 * @since 2021/7/27
 **/
@Data
@Component
public class BinLogConstants {
    @Value("${binlog.datasource.host}")
    private String host;

    @Value("${binlog.datasource.port}")
    private int port;

    @Value("${binlog.datasource.username}")
    private String username;

    @Value("${binlog.datasource.passwd}")
    private String passwd;

    @Value("${binlog.db}")
    private String db;

    @Value("${binlog.table}")
    private String table;

    public static final int consumerThreads = 5;

    public static final long queueSleep = 1000;

}

