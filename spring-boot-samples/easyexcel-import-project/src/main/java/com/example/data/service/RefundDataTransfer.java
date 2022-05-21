package com.example.data.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 退款数据
 */
@Service("refund")
public class RefundDataTransfer extends AbstractDataTransfer {

    public RefundDataTransfer(JdbcTemplate jdbcTemplate, DataSourceTransactionManager transactionManager) {
        super(jdbcTemplate, transactionManager);
    }

    @Override
    @Transactional
    public boolean doImport(List<Object[]> datas) {
        if (CollectionUtils.isEmpty(datas)){
            log.warn("导入的数据为空，不作处理。");
            return false;
        }
        start = System.currentTimeMillis();
        log.info("导入数据前清除数据开始");
        String executeSql = "TRUNCATE TABLE `tf_tuikuan`";
        execute(executeSql);

        String tableName = "tf_tuikuan";
        log.info("开始导入退款数据，表名为 {}", tableName);
//        String insertSql = "INSERT INTO `tf_tuikuan`(`客户名称`, `房屋编号`, `费用名称`, `收款时间`, `收据号码`, `收款人`, `收款金额`, `冲抵金额`, `合同违约金`, `付款金额`, `已退金额`, `未退金额`, `费用性质`, `费用时间`, `备注`) " +
//                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        String insertSql = "INSERT INTO `tf_tuikuan`(`客户名称`, `房屋编号`, `费用名称`, `收款时间`, `收据号码`, `收款人`, `收款金额`, `冲抵金额`, `合同违约金`, `付款金额`, `已退金额`, `未退金额`, `费用性质`, `费用时间`, `备注`)";
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
        log.info("导入退款数据成功,耗时{}s", (System.currentTimeMillis() - start) / 1000L);
        return true;
    }

    @Override
    @Transactional
    public void doClean() {
        start = System.currentTimeMillis();
        long begin = start;
        String tableName = "tf_tuikuan";
        log.info("开始清理退款数据，表名为 {}", tableName);
        String sql = "update tf_tuikuan a set  a.`房屋编号`=REPLACE(a.`房屋编号`,'\\'','')";
        int update = update(sql);
        long executeSeconds = (System.currentTimeMillis() - start) / 1000L;
        log.info("更新 tf_tuikuan 表的字段[房屋编号格式]成功，执行时间{}，受影响的条数{}", executeSeconds, update);
        String insertSql = "INSERT INTO `tf_data_log` (`table_name`,`start_time`,`update_columns`, `execute_time`, `update_num`) VALUES (?,?,?,?,?)";
        if (update>0)
        update(insertSql, "tf_tuikuan", new Date(), "房屋编号格式", executeSeconds, update);

        start = System.currentTimeMillis();
        String sql1 = "UPDATE tf_tuikuan a\n" +
                "LEFT JOIN tf_base b ON a.`房屋编号` = b.`房产备注` \n" +
                "SET a.`项目名称` = b.`项目名称`,\n" +
                "a.`房屋编号` = b.`房产简称`";
        int update1 = update(sql1);
        executeSeconds = (System.currentTimeMillis() - start) / 1000L;
        log.info("更新 tf_tuikuan 表的字段[项目名称,房屋编号]成功，执行时间{}，受影响的条数{}", executeSeconds, update1);
        String insertSql1 = "INSERT INTO `tf_data_log` (`table_name`,`start_time`,`update_columns`, `execute_time`, `update_num`) VALUES (?,?,?,?,?)";
        if (update1>0)
        update(insertSql1, "tf_tuikuan", new Date(), "项目名称,房屋编号", executeSeconds, update1);

        start = System.currentTimeMillis();
        List<String> sqls = new ArrayList<>();
        sqls.add("UPDATE tf_tuikuan a SET a.`客户名称` = trim( a.`客户名称` )");
        sqls.add("UPDATE tf_tuikuan a SET a.`客户名称` = REPLACE ( a.`客户名称`, '，', '_' )");
        sqls.add("UPDATE tf_tuikuan a SET a.`客户名称` = REPLACE ( a.`客户名称`, ',', '_' )");
        sqls.add("UPDATE tf_tuikuan a SET a.`客户名称` = REPLACE ( a.`客户名称`, ',', '_' )");
        sqls.add("UPDATE tf_tuikuan a SET a.`客户名称` = REPLACE ( a.`客户名称`, '、', '_' )");
        sqls.add("UPDATE tf_tuikuan a SET a.`客户名称` = REPLACE ( a.`客户名称`, '/', '_' )");
        sqls.add("UPDATE tf_tuikuan a SET a.`客户名称` = REPLACE ( a.`客户名称`, ' ', '_' )");
        int[] ints = batchUpdate(sqls.toArray(new String[0]));
        executeSeconds = (System.currentTimeMillis() - start) / 1000L;
        int count = 0;
        for (int i : ints) {
            count = count + i;
        }
        log.info("更新 tf_tuikuan 表的客户名称字段格式成功，执行时间{}，受影响的条数{}", executeSeconds, count);
        String insertSql2 = "INSERT INTO `tf_data_log` (`table_name`,`start_time`,`update_columns`, `execute_time`, `update_num`) VALUES (?,?,?,?,?)";
        if (count>0)
        update(insertSql2, "tf_tuikuan", new Date(), "转换客户名称数据格式", executeSeconds, count);
        log.info("清理退款数据成功,总耗时{}s", (System.currentTimeMillis() - begin) / 1000L);
    }
}
