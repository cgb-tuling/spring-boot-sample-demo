package com.example.data.service;

import com.google.common.collect.Lists;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 已收款数据
 */
@Service("pay")
public class PayDataTransfer extends AbstractDataTransfer {

    public PayDataTransfer(JdbcTemplate jdbcTemplate, DataSourceTransactionManager transactionManager) {
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
        String executeSql = "TRUNCATE TABLE `tf_paymentdetail`";
        execute(executeSql);

        String tableName = "tf_paymentdetail";
        log.info("开始导入已收款数据，表名为 {}", tableName);
//        String insertSql = "INSERT INTO `tf_paymentdetail`(`费用ID`, `客户名称`, `房屋编号`, `房屋名称`, `客户类别`, `管家`, `组团区域`, `楼宇名称`, `建筑面积`, `套内面积`, `公摊面积`, `花园面积`, `地下室面积`, `计算面积`, `产权性质`, `楼宇类型`, `使用性质`, `使用状态`, `交房状态`, `实际交房时间`, `考核条件`, `车位区域`, `车位路址`, `车位编号`, `车位面积`, `计费面积`, `车位类别`, `车位类型`, `产权性质 (1)`, `使用状态 (1)`, `实际交付时间`, `考核条件 (1)`, `费用名称`, `计费方式`, `计算方式`, `收费标准`, `计算周期`, `数量1`, `数量2`, `起度`, `止度`, `使用量`, `税率`, `费用日期`, `应收日期`, `开始日期`, `结束日期`, `收款日期`, `票据类别`, `票据号码`, `天问单号`, `含税金额`, `税费`, `不含税金额`, `佣金项目`, `收入含税金额`, `收入税费`, `收入不含税金额`, `合同含税金额`, `合同税费`, `合同不含税金额`, `收款人`, `收款方式`, `备注`) " +
//                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
//                "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        String insertSql = "INSERT INTO `tf_paymentdetail`(`费用ID`, `客户名称`, `房屋编号`, `房屋名称`, `客户类别`, `管家`, `组团区域`, `楼宇名称`, `建筑面积`, `套内面积`, `公摊面积`, `花园面积`, `地下室面积`, `计算面积`, `产权性质`, `楼宇类型`, `使用性质`, `使用状态`, `交房状态`, `实际交房时间`, `考核条件`, `车位区域`, `车位路址`, `车位编号`, `车位面积`, `计费面积`, `车位类别`, `车位类型`, `产权性质 (1)`, `使用状态 (1)`, `实际交付时间`, `考核条件 (1)`, `费用名称`, `计费方式`, `计算方式`, `收费标准`, `计算周期`, `数量1`, `数量2`, `起度`, `止度`, `使用量`, `税率`, `费用日期`, `应收日期`, `开始日期`, `结束日期`, `收款日期`, `票据类别`, `票据号码`, `天问单号`, `含税金额`, `税费`, `不含税金额`, `佣金项目`, `收入含税金额`, `收入税费`, `收入不含税金额`, `合同含税金额`, `合同税费`, `合同不含税金额`, `收款人`, `收款方式`, `备注`) ";
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
        LinkedList<Object[]> values = Lists.newLinkedList();
        for (Object[] data : datas) {
            LinkedList<Object> objects = Lists.newLinkedList();
            objects.addAll(Arrays.asList(data));
            if (data.length > columns.length) {
                int index = data.length - columns.length;
                for (int i = 0; i < index; i++) {
                    objects.removeLast();
                }
            } else {
                int index = columns.length - data.length;
                for (int i = 0; i < index; i++) {
                    objects.addLast("");
                }
            }
            values.add(objects.toArray(new Object[0]));
        }

        for (int i = 0; i < columns.length; i++) {
            if (i == columns.length - 1) {
                sb.append("?)");
            } else {
                sb.append("?,");
            }
        }
        String sql = insertSql + sb;
        threadBatchSizeUpdate(sql, values);
        log.info("导入已收款数据成功,耗时{}s", (System.currentTimeMillis() - start) / 1000L);
        return true;
    }

    @Override
    @Transactional
    public void doClean() {
        start = System.currentTimeMillis();
        long begin = start;
        String tableName = "tf_paymentdetail";
        log.info("开始清理已收款数据，表名为 {}", tableName);
        String updateSql1 = "UPDATE `tf_paymentdetail` a\n" +
                "LEFT JOIN tf_base b ON a.`房屋编号` = b.`房产备注` \n" +
                "SET a.`房屋编号` =\n" +
                "CASE\n" +
                "\t\ta.`费用名称` \n" +
                "\t\tWHEN '车位物管费' THEN\n" +
                "\t\ta.`车位编号` \n" +
                "\t\tWHEN '车位物管费-昆明耍街项目' THEN\n" +
                "\t\ta.`车位编号` \n" +
                "\t\tWHEN '地下车位租金' THEN\n" +
                "\t\ta.`车位编号` \n" +
                "\t\tWHEN '车位使用费' THEN\n" +
                "\t\ta.`车位编号` \n" +
                "\t\tWHEN '【停用】车位费' THEN\n" +
                "\t\ta.`车位编号` \n" +
                "\t\tWHEN '车位使用费（停用）' THEN\n" +
                "\t\ta.`车位编号` \n" +
                "\t\tWHEN '【停用】车位使用费' THEN\n" +
                "\t\ta.`车位编号` ELSE b.`房产简称` \n" +
                "\tEND,\n" +
                "\ta.`组团区域` = b.`项目名称`,\n" +
                "\ta.`应收日期` = CONCAT( a.`费用日期`, '01日' ),\n" +
                "\ta.`票据类别` = '电子收据'";
        int update = update(updateSql1);
        long executeSeconds = (System.currentTimeMillis() - start) / 1000L;
        log.info("更新 tf_paymentdetail 表的字段[房屋编号，组图区域，应收日期]成功，执行时间{}，受影响的条数{}", executeSeconds, update);
        String insertSql = "INSERT INTO `tf_data_log` (`table_name`,`start_time`,`update_columns`, `execute_time`, `update_num`) VALUES (?,?,?,?,?)";
        if (update > 0) {
            update(insertSql, "tf_paymentdetail", new Date(), "房屋编号，组图区域，应收日期", executeSeconds, update);
        }

        ArrayList<String> updatesList = Lists.newArrayList();
        updatesList.add("update  tf_paymentdetail a set a.`状态`='暂收' where a.`费用名称` in ( select b.accountName from tf_account b )");
        updatesList.add("update  tf_paymentdetail a set a.`状态`='非暂收' where a.`费用名称` not in ( select b.accountName from tf_account b )");
        int[] ints = batchUpdate(updatesList);
        int updateCount = 0;
        for (int i : ints) {
            updateCount = updateCount + i;
        }
        String insertSql1 = "INSERT INTO `tf_data_log` (`table_name`,`start_time`,`update_columns`, `execute_time`, `update_num`) VALUES (?,?,?,?,?)";
        if (updateCount > 0) {
            update(insertSql1, "tf_paymentdetail", new Date(), "状态", executeSeconds, update);
        }

        start = System.currentTimeMillis();
        List<String> sqls = new ArrayList<>();
        sqls.add("update  tf_paymentdetail a   set a.`客户名称`=trim(a.`客户名称`)");
        sqls.add("update  tf_paymentdetail a   set a.`客户名称`=replace(a.`客户名称`,'，','_')");
        sqls.add("update  tf_paymentdetail a   set a.`客户名称`=replace(a.`客户名称`,',','_')");
        sqls.add("update  tf_paymentdetail a   set a.`客户名称`=replace(a.`客户名称`,',','_')");
        sqls.add("update  tf_paymentdetail a   set a.`客户名称`=replace(a.`客户名称`,'、','_')");
        sqls.add("update  tf_paymentdetail a   set a.`客户名称`=replace(a.`客户名称`,'/','_')");
        sqls.add("update  tf_paymentdetail a   set a.`客户名称`=replace(a.`客户名称`,' ','_')");
        int[] updates = batchUpdate(sqls.toArray(new String[0]));
        executeSeconds = (System.currentTimeMillis() - start) / 1000L;
        int count = 0;
        for (int i : updates) {
            count = count + i;
        }
        log.info("更新 tf_paymentdetail 表的客户名称字段格式成功，执行时间{}，受影响的条数{}", executeSeconds, count);
        String insertSql2 = "INSERT INTO `tf_data_log` (`table_name`,`start_time`,`update_columns`, `execute_time`, `update_num`) VALUES (?,?,?,?,?)";
        if (count > 0) {
            update(insertSql2, "tf_paymentdetail", new Date(), "转换客户名称数据格式", executeSeconds, count);
        }
        start = System.currentTimeMillis();
        String updateSql = "UPDATE tf_paymentdetail a  set a.`开始日期`=a.`应收日期`,  a.`结束日期`=a.`应收日期`  where a.`开始日期` is null or  a.`结束日期` is null";
        int update1 = update(updateSql);
        if (update1 > 0) {
            insertSql2 = "INSERT INTO `tf_data_log` (`table_name`,`start_time`,`update_columns`, `execute_time`, `update_num`) VALUES (?,?,?,?,?)";
            update(insertSql2, "tf_paymentdetail", new Date(), "开始日期,结束日期", executeSeconds, count);
        }
        String updateSql2 = "UPDATE tf_paymentdetail a  set  a.`收款方式`='天问预收' where a.`收款方式`  is null";
        int update2 = update(updateSql2);
        if (update2 > 0) {
            insertSql2 = "INSERT INTO `tf_data_log` (`table_name`,`start_time`,`update_columns`, `execute_time`, `update_num`) VALUES (?,?,?,?,?)";
            update(insertSql2, "tf_paymentdetail", new Date(), "收款方式", executeSeconds, update2);
        }
        log.info("清理已收款数据成功,总耗时{}s", (System.currentTimeMillis() - begin) / 1000L);
    }
}
