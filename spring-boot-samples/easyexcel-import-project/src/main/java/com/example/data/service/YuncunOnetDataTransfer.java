package com.example.data.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 第一次预交数据
 */
@Service("yucun-one")
public class YuncunOnetDataTransfer extends AbstractDataTransfer {

    public YuncunOnetDataTransfer(JdbcTemplate jdbcTemplate, DataSourceTransactionManager transactionManager) {
        super(jdbcTemplate, transactionManager);
    }

    @Override
    @Transactional
    public boolean doImport(List<Object[]> datas) {
        if (CollectionUtils.isEmpty(datas)) {
            log.warn("导入的数据为空，不作处理。");
            return false;
        }
        //删除最后一个元素  因为最后一行为 合计
        datas.remove(datas.size() - 1);
        start = System.currentTimeMillis();
        log.info("导入数据前清除数据开始");
        String executeSql = "TRUNCATE TABLE `tf_yucun_one`";
        execute(executeSql);

        String tableName = "tf_yucun_one";
        log.info("开始导入第一次预交数据，表名为 {}", tableName);
//        String insertSql = "INSERT INTO `tf_yucun_one`(`费用ID`, `组团区域`, `客户名称`, `车位`, `房屋编号`, `房屋名称`, `建筑面积`, `预交收款时间`, `收据号码`, `天问单号`, `冲抵费用项目`, `预交金额`, `预交操作人`, `处理方式`, `收款方式`, `备注`)" +
//                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        String insertSql = "INSERT INTO `tf_yucun_one`(`费用ID`, `组团区域`, `客户名称`, `车位`, `房屋编号`, `房屋名称`, `建筑面积`, `预交收款时间`, `收据号码`, `天问单号`, `冲抵费用项目`, `预交金额`, `预交操作人`, `处理方式`, `收款方式`, `备注`)";
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
        log.info("导入第一次预交数据成功,耗时{}s", (System.currentTimeMillis() - start) / 1000L);
        return true;
    }

    @Override
    @Transactional
    public void doClean() {
        start = System.currentTimeMillis();
        long begin = start;
        String tableName = "tf_yucun_one";
        log.info("开始清理第一次预交数据，表名为 {}", tableName);
        String sql = "update `tf_yucun_one` a set  a.`房屋编号`=REPLACE(a.`房屋编号`,'\\'','')";
        int update = update(sql);
        long executeSeconds = (System.currentTimeMillis() - start) / 1000L;
        log.info("更新 tf_payment_tk 表的字段[房屋编号格式]成功，执行时间{}，受影响的条数{}", executeSeconds, update);
        String insertSql = "INSERT INTO `tf_data_log` (`table_name`,`start_time`,`update_columns`, `execute_time`, `update_num`) VALUES (?,?,?,?,?)";
        if (update > 0)
            update(insertSql, "tf_yucun_one", new Date(), "房屋编号格式", executeSeconds, update);

        start = System.currentTimeMillis();
        String updateSql = "UPDATE `tf_yucun_one` a\n" +
                "LEFT JOIN tf_base b ON a.`房屋编号` = b.`房产备注` \n" +
                "\tSET a.`房屋编号` =\n" +
                "CASE\n" +
                "\t\ta.`冲抵费用项目` \n" +
                "\t\tWHEN '车位物管费' THEN\n" +
                "\t\ta.`车位` \n" +
                "\t\tWHEN '车位物管费-昆明耍街项目' THEN\n" +
                "\t\ta.`车位` \n" +
                "\t\tWHEN '地下车位租金' THEN\n" +
                "\t\ta.`车位` \n" +
                "\t\tWHEN '车位使用费' THEN\n" +
                "\t\ta.`车位` \n" +
                "\t\tWHEN '【停用】车位费' THEN\n" +
                "\t\ta.`车位` \n" +
                "\t\tWHEN '车位使用费（停用）' THEN\n" +
                "\t\ta.`车位` \n" +
                "\t\tWHEN '【停用】车位使用费' THEN\n" +
                "\t\ta.`车位` ELSE b.`房产简称` \n" +
                "\tEND,\n" +
                "a.`组团区域` = b.`项目名称`";

        int update1 = update(updateSql);
        executeSeconds = (System.currentTimeMillis() - start) / 1000L;
        log.info("更新 tf_yucun_one 表的字段[房屋编号,组团区域]成功，执行时间{}，受影响的条数{}", executeSeconds, update1);
        String insertSql1 = "INSERT INTO `tf_data_log` (`table_name`,`start_time`,`update_columns`, `execute_time`, `update_num`) VALUES (?,?,?,?,?)";
        if (update1 > 0) {
            update(insertSql1, "tf_yucun_one", new Date(), "房屋编号,组团区域", executeSeconds, update1);
        }

        log.info("开始转换第一次预交数据格式");
        start = System.currentTimeMillis();
        List<String> sqls = new ArrayList<>();
        sqls.add("update  tf_yucun_one a   set a.`客户名称`=trim(a.`客户名称`)");
        sqls.add("update  tf_yucun_one a   set a.`客户名称`=replace(a.`客户名称`,'，','_')");
        sqls.add("update  tf_yucun_one a   set a.`客户名称`=replace(a.`客户名称`,',','_')");
        sqls.add("update  tf_yucun_one a   set a.`客户名称`=replace(a.`客户名称`,'、','_')");
        sqls.add("update  tf_yucun_one a   set a.`客户名称`=replace(a.`客户名称`,'/','_')");
        sqls.add("update  tf_yucun_one a   set a.`客户名称`=replace(a.`客户名称`,' ','_');");
        int[] ints = batchUpdate(sqls.toArray(new String[0]));
        executeSeconds = (System.currentTimeMillis() - start) / 1000L;
        int count = 0;
        for (int i : ints) {
            count = count + i;
        }
        log.info("更新 tf_yucun_one 表的客户名称字段格式成功，执行时间{}，受影响的条数{}", executeSeconds, count);
        String insertSql2 = "INSERT INTO `tf_data_log` (`table_name`,`start_time`,`update_columns`, `execute_time`, `update_num`) VALUES (?,?,?,?,?)";
        if (count > 0) {
            update(insertSql2, "tf_yucun_one", new Date(), "转换客户名称数据格式", executeSeconds, count);
        }
        log.info("清理第一次预交数据成功,总耗时{}s", (System.currentTimeMillis() - begin) / 1000L);
    }
}
