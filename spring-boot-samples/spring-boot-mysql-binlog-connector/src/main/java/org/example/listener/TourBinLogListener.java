package org.example.listener;

import cn.hutool.core.collection.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.example.config.BinLogConstants;
import org.example.config.Conf;
import org.example.utils.BinLogUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 乐游监听器
 * SpringBoot启动成功后的执行业务线程操作
 * CommandLineRunner去实现此操作
 * 在有多个可被执行的业务时，通过使用 @Order 注解，设置各个线程的启动顺序（value值由小到大表示启动顺序）。
 * 多个实现CommandLineRunner接口的类必须要设置启动顺序，不让程序启动会报错！
 *
 * @author yuancetian
 * @since 2021/7/27
 **/
@Slf4j
@Component
@Order(value = 1)
public class TourBinLogListener implements CommandLineRunner {

    @Resource
    private BinLogConstants binLogConstants;

    @Override
    public void run(String... args) throws Exception {
        log.info("初始化配置信息：" + binLogConstants.toString());

        // 初始化配置信息
        Conf conf = new Conf(binLogConstants.getHost(), binLogConstants.getPort(), binLogConstants.getUsername(), binLogConstants.getPasswd());

        // 初始化监听器
        MysqlBinLogListener mysqlBinLogListener = new MysqlBinLogListener(conf);

        // 获取table集合
        List<String> tableList = BinLogUtils.getListByStr(binLogConstants.getTable());
        if (CollectionUtil.isEmpty(tableList)) {
            return;
        }
        // 注册监听
        tableList.forEach(table -> {
            log.info("注册监听信息，注册DB：" + binLogConstants.getDb() + "，注册表：" + table);
            try {
                mysqlBinLogListener.regListener(binLogConstants.getDb(), table, item -> {
                    log.info("监听逻辑处理");
                });
            } catch (Exception e) {
                log.error("BinLog监听异常：" + e);
            }
        });
        // 多线程消费
        mysqlBinLogListener.parse();
    }
}

