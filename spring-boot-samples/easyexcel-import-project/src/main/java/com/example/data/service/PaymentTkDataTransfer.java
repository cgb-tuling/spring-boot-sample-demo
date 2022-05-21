package com.example.data.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 实收退款明细
 */
@Service("payment-tk")
public class PaymentTkDataTransfer extends AbstractDataTransfer {

    public PaymentTkDataTransfer(JdbcTemplate jdbcTemplate, DataSourceTransactionManager transactionManager) {
        super(jdbcTemplate, transactionManager);
    }

    @Override
    @Transactional
    public boolean doImport(List<Object[]> datas) {
        if (CollectionUtils.isEmpty(datas)) {
            log.warn("导入的数据为空，不作处理。");
            return false;
        }
        start = System.currentTimeMillis();
        log.info("导入数据前清除数据开始");
        String executeSql = "TRUNCATE TABLE `tf_payment_tk`";
        execute(executeSql);
        String tableName = "tf_payment_tk";
        log.info("开始导入实收退款数据，表名为 {}", tableName);
        String insertSql = "INSERT INTO `tf_payment_tk`(`客户名称`, `房屋编号`, `房屋名称`, `建筑面积`, `收款时间`, `收据号码`, `应收日期`, `开始日期`, `结束日期`, `费用名称`, `收款金额`, `冲抵金额`, `合同违约金`, `收款人`, `退款时间`, `退款人`, `已退金额`, `退款票号`, `退款方式`, `备注`, `项目名称`) ";
        String[] columns = insertSql.substring(insertSql.indexOf("(") + 1, insertSql.lastIndexOf(")"))
                .split(",");
        StringBuffer sb = new StringBuffer(" VALUES (");
/*        List<Object[]> objs = new ArrayList<>();
        for (LinkedHashMap<String, String> data : datas) {
            LinkedList<String> values = new LinkedList<>();
            for (String column : columns) {
                String newStr = column.replace("`", "").trim();
                String dd = data.get(newStr);
                values.add(dd);
            }
            objs.add(values.toArray(new Object[0]));
        }*/
        for (int i = 0; i < columns.length; i++) {
            if (i == columns.length - 1) {
                sb.append("?)");
            } else {
                sb.append("?,");
            }
        }
        String sql = insertSql + sb;
        batchSizeUpdate(sql, datas);
        log.info("导入实收退款数据成功,耗时{}s", (System.currentTimeMillis() - start) / 1000L);
        return true;
    }

    @Override
    @Transactional
    public void doClean() {
        start = System.currentTimeMillis();
        long begin = start;
        String tableName = "tf_payment_tk";
        log.info("开始清理实收退款数据，表名为 {}", tableName);
        String sql = "update tf_payment_tk a set  a.`房屋编号`=REPLACE(a.`房屋编号`,'\\'','')";
        int update = update(sql);
        long executeSeconds = (System.currentTimeMillis() - start) / 1000L;
        log.info("更新 tf_payment_tk 表的字段[房屋编号格式]成功，执行时间{}，受影响的条数{}", executeSeconds, update);
        String insertSql = "INSERT INTO `tf_data_log` (`table_name`,`start_time`,`update_columns`, `execute_time`, `update_num`) VALUES (?,?,?,?,?)";
        if (update > 0)
            update(insertSql, "tf_payment_tk", new Date(), "房屋编号格式", executeSeconds, update);

        start = System.currentTimeMillis();
        String updateSql1 = "UPDATE `tf_payment_tk` a\n" +
                "LEFT JOIN tf_base b ON a.`房屋编号` = b.`房产备注` \n" +
                "SET a.`房屋编号` = b.`房产简称`,\n" +
                "a.`项目名称` = b.`项目名称`,\n" +
                "a.`应收日期` = date_format(\n" +
                "\tdate_add( str_to_date( a.`应收日期`, '%Y-%m-%d' ),\n" +
                "\tINTERVAL - DAY ( str_to_date( a.`应收日期`, '%Y-%m-%d' )) + 1 DAY ),\n" +
                "\t'%Y年%m月%d日' \n" +
                ")";
        update = update(updateSql1);
        executeSeconds = (System.currentTimeMillis() - start) / 1000L;
        log.info("更新 tf_payment_tk 表的字段[房屋编号，项目名称，应收日期]成功，执行时间{}，受影响的条数{}", executeSeconds, update);
        insertSql = "INSERT INTO `tf_data_log` (`table_name`,`start_time`,`update_columns`, `execute_time`, `update_num`) VALUES (?,?,?,?,?)";
        if (update > 0) {
            update(insertSql, "tf_payment_tk", new Date(), "房屋编号，项目名称，应收日期", executeSeconds, update);
        }

        log.info("清理实收退款数据成功,总耗时{}s", (System.currentTimeMillis() - begin) / 1000L);
    }
}
