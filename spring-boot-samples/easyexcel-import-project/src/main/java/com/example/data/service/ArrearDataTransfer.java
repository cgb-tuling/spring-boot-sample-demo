package com.example.data.service;

import com.google.common.collect.Lists;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 应收款数据
 */
@Service("arr")
public class ArrearDataTransfer extends AbstractDataTransfer {

    public ArrearDataTransfer(JdbcTemplate jdbcTemplate, DataSourceTransactionManager transactionManager) {
        super(jdbcTemplate, transactionManager);
    }


    @Override
//    @Transactional
    public boolean doImport(List<Object[]> datas) {
        if (CollectionUtils.isEmpty(datas)) {
            log.warn("导入的数据为空，不作处理。");
            return false;
        }
        start = System.currentTimeMillis();
        log.info("导入数据前清除数据开始");
        String executeSql = "TRUNCATE TABLE `tf_arrear`";
        execute(executeSql);

        String tableName = "tf_arrear";
        log.info("开始导入应收款数据，表名为 {}", tableName);
//        String insertSql = "INSERT INTO `tf_arrear`(`费用ID`, `客户名称`, `房屋编号`, `房屋名称`, `客户类别`, `移动电话`, `管家`, `组团区域`, `楼宇名称`, `建筑面积`, `套内面积`, `公摊面积`, `花园面积`, `地下室面积`, `计算面积`, `产权性质`, `楼宇类型`, `使用性质`, `使用状态`, `交房状态`, `实际交房时间`, `考核条件`, `车位区域`, `车位路址`, `车位编号`, `车位面积`, `车位类别`, `车位类型`, `车位产权性质`, `车位使用状态`, `车位实际交付时间`, `车位考核条件`, `费用名称`, `计费方式`, `计算方式`, `收费标准`, `计算周期`, `数量1`, `数量2`, `起度`, `止度`, `使用量`, `费用日期`, `应收日期`, `开始日期`, `结束日期`, `收款日期`, `税率`, `含税金额`, `税费`, `不含税金额`, `收入佣金科目`, `收入佣金含税金额`, `收入佣金税率`, `佣金科目`, `佣金含税金额`, `佣金税费`, `佣金不含税费`, `实收含税金额`, `实收税费`, `实收不含税金额`, `预存含税金额`, `预存税费`, `预存不含税金额`, `减免含税金额`, `减免税费`, `减免不含税金额`, `欠费含税金额`, `欠费税费`, `欠费不含税金额`, `退款金额`, `备注`)" +
//                " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        String insertSql = "INSERT INTO `tf_arrear`(`费用ID`, `客户名称`, `房屋编号`, `房屋名称`, `客户类别`, `移动电话`, `管家`, `组团区域`, `楼宇名称`, `建筑面积`, `套内面积`, `公摊面积`, `花园面积`, `地下室面积`, `计算面积`, `产权性质`, `楼宇类型`, `使用性质`, `使用状态`, `交房状态`, `实际交房时间`, `考核条件`, `车位区域`, `车位路址`, `车位编号`, `车位面积`, `车位类别`, `车位类型`, `车位产权性质`, `车位使用状态`, `车位实际交付时间`, `车位考核条件`, `费用名称`, `计费方式`, `计算方式`, `收费标准`, `计算周期`, `数量1`, `数量2`, `起度`, `止度`, `使用量`, `费用日期`, `应收日期`, `开始日期`, `结束日期`, `收款日期`, `税率`, `含税金额`, `税费`, `不含税金额`, `收入佣金科目`, `收入佣金含税金额`, `收入佣金税率`, `佣金科目`, `佣金含税金额`, `佣金税费`, `佣金不含税费`, `实收含税金额`, `实收税费`, `实收不含税金额`, `预存含税金额`, `预存税费`, `预存不含税金额`, `减免含税金额`, `减免税费`, `减免不含税金额`, `欠费含税金额`, `欠费税费`, `欠费不含税金额`, `退款金额`, `备注`)";
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

        threadBatchSizeUpdate(sql, datas);
        log.info("导入应收款数据成功,耗时{}s", (System.currentTimeMillis() - start) / 1000L);
        return true;
    }

    @Override
    @Transactional
    public void doClean() {
        start = System.currentTimeMillis();
        long begin = start;
        String tableName = "tf_arrear";
        log.info("开始清理应收款数据，表名为 {}", tableName);
        String updateSql1 = "UPDATE tf_arrear a left join tf_base b on a.`房屋编号`=b.`房产备注`" +
                "SET a.`新视窗项目名称` = b.`项目名称`," +
                " a.`新视窗应收日期` = CONCAT(a.`费用日期`,'01日')," +
                " a.`新视窗应收账期` =a.`费用日期` ," +
                "a.`房屋编号`=b.`房产简称`";
        int update = update(updateSql1);
        long executeSeconds = (System.currentTimeMillis() - start) / 1000L;
        log.info("更新 tf_arrear 表的字段[新视窗项目名称，新视窗应收日期，新视窗应收账期，房屋编号]成功，执行时间{}，受影响的条数{}", executeSeconds, update);
        String insertSql = "INSERT INTO `tf_data_log` (`table_name`,`start_time`,`update_columns`, `execute_time`, `update_num`) VALUES (?,?,?,?,?)";
        if (update > 0) {
            update(insertSql, "tf_arrear", new Date(), "新视窗项目名称，新视窗应收日期，新视窗应收账期，房屋编号", executeSeconds, update);
        }
        start = System.currentTimeMillis();
        String updateSql2 = "UPDATE tf_arrear a set a.`备注`=CONCAT(a.`备注`,'起度:',a.`起度`,',止度:',a.`止度`),a.`状态`=1  " +
                "where a.`起度` >=0 and a.`止度`>0 and a.`状态` is null";
        int update1 = update(updateSql2);
        executeSeconds = (System.currentTimeMillis() - start) / 1000L;
        log.info("更新 tf_arrear 表的字段[备注]成功，执行时间{}，受影响的条数{}", (System.currentTimeMillis() - start) / 1000L, update1);
        String insertSql1 = "INSERT INTO `tf_data_log` (`table_name`,`start_time`,`update_columns`, `execute_time`, `update_num`) VALUES (?,?,?,?,?)";
        if (update1 > 0) {
            update(insertSql1, "tf_arrear", new Date(), "备注", executeSeconds, update1);
        }

        List<String> updateSqlList = Lists.newArrayList();
        updateSqlList.add("update  tf_arrear a set a.`科目状态`='暂收' where a.`费用名称` in ( select b.accountName from tf_account b )");
        updateSqlList.add("update  tf_arrear a set a.`科目状态`='非暂收' where a.`费用名称` not in ( select b.accountName from tf_account b )");

        int[] updates = batchUpdate(updateSqlList);
        int updateCount = 0;
        for (int i : updates) {
            updateCount = updateCount + i;
        }
        if (updateCount > 0) {
            log.info("更新 tf_arrear 表的字段[科目状态]成功，执行时间{}，受影响的条数{}", (System.currentTimeMillis() - start) / 1000L, update1);
            String insertSql2 = "INSERT INTO `tf_data_log` (`table_name`,`start_time`,`update_columns`, `execute_time`, `update_num`) VALUES (?,?,?,?,?)";
            if (update1 > 0) {
                update(insertSql2, "tf_arrear", new Date(), "科目状态", executeSeconds, update1);
            }
        }

        log.info("开始转换应收款数据格式");
        start = System.currentTimeMillis();
        List<String> sqls = new ArrayList<>();
        sqls.add("update  tf_arrear a   set a.`客户名称`=trim(a.`客户名称`)");
        sqls.add("update  tf_arrear a   set a.`客户名称`=replace(a.`客户名称`,'，','_')");
        sqls.add("update  tf_arrear a   set a.`客户名称`=replace(a.`客户名称`,',','_')");
        sqls.add("update  tf_arrear a   set a.`客户名称`=replace(a.`客户名称`,',','_')");
        sqls.add("update  tf_arrear a   set a.`客户名称`=replace(a.`客户名称`,'、','_')");
        sqls.add("update  tf_arrear a   set a.`客户名称`=replace(a.`客户名称`,'/','_')");
        sqls.add("update  tf_arrear a   set a.`客户名称`=replace(a.`客户名称`,' ','_');");
        int[] ints = batchUpdate(sqls.toArray(new String[0]));
        executeSeconds = (System.currentTimeMillis() - start) / 1000L;
        int count = 0;
        for (int i : ints) {
            count = count + i;
        }
        log.info("更新 tf_arrear 表的客户名称字段格式成功，执行时间{}，受影响的条数{}", executeSeconds, count);
        String insertSql2 = "INSERT INTO `tf_data_log` (`table_name`,`start_time`,`update_columns`, `execute_time`, `update_num`) VALUES (?,?,?,?,?)";
        if (count > 0) {
            update(insertSql2, "tf_arrear", new Date(), "转换客户名称数据格式", executeSeconds, count);
        }
        String updateSql = "UPDATE tf_arrear a  set a.`开始日期`=a.`应收日期`,  a.`结束日期`=a.`应收日期`  where a.`开始日期` is null or  a.`结束日期` is null";
        start = System.currentTimeMillis();
        int update2 = update(updateSql);
        if (update2 > 0) {
            insertSql2 = "INSERT INTO `tf_data_log` (`table_name`,`start_time`,`update_columns`, `execute_time`, `update_num`) VALUES (?,?,?,?,?)";
            update(insertSql2, "tf_paymentdetail", new Date(), "开始日期,结束日期", executeSeconds, update2);
        }
        log.info("清理应收款数据成功,总耗时{}s", (System.currentTimeMillis() - begin) / 1000L);
    }
}
