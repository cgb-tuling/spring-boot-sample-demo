package com.example.data.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@Repository
public class TfRepository {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private ObjectMapper mapper = new ObjectMapper();


    @Autowired
    private JdbcTemplate jdbcTemplate;

//    @Resource(name = "transactionManager")
//    private DataSourceTransactionManager transactionManager;

    public String query() {
//        String querySql = "SELECT * FROM `tf_base` LIMIT 0,1000";
//        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(querySql);
        String insertSql = "INSERT INTO `tf_data_log` VALUES (?,?,?,?,?)";
        jdbcTemplate.update(insertSql, "table1", new Date(), "新视窗项目名称，新视窗应收日期，新视窗应收账期，房屋编号", 100, 100);
        return "success";
    }


    /**
     * 应收款数据
     */
    @Transactional
    public boolean updateTfArrear() {
        long start = System.currentTimeMillis();
        String updateSql1 = "UPDATE tf_arrear a left join tf_base b on a.`房屋名称`=b.`房产备注`" +
                "SET a.`新视窗项目名称` = b.`项目名称`," +
                " a.`新视窗应收日期` = CONCAT(a.`费用日期`,'01日')," +
                " a.`新视窗应收账期` =a.`费用日期` ," +
                "a.`房屋编号`=b.`房产简称`";
        int update = jdbcTemplate.update(updateSql1);
        long executeSeconds = (System.currentTimeMillis() - start) / 1000L;
        log.info("更新 TF_ARREAR 表的字段[新视窗项目名称，新视窗应收日期，新视窗应收账期，房屋编号]成功，执行时间{}，受影响的条数{}", executeSeconds, update);

        String insertSql = "INSERT INTO `tf_data_log` (`table_name`,`start_time`,`update_columns`, `execute_time`, `update_num`) VALUES (?,?,?,?,?)";
        jdbcTemplate.update(insertSql, "tf_arrear", new Date(), "新视窗项目名称，新视窗应收日期，新视窗应收账期，房屋编号", executeSeconds, update);


        start = System.currentTimeMillis();
        String updateSql2 = "UPDATE tf_arrear a set a.`备注`=CONCAT(a.`备注`,'起度:',a.`起度`,',止度:',a.`止度`),a.`状态`=1  " +
                "where a.`起度` >=0 and a.`止度`>0 and a.`状态` is null";
        int update1 = jdbcTemplate.update(updateSql2);
        executeSeconds = (System.currentTimeMillis() - start) / 1000L;
        log.info("更新 TF_ARREAR 表的字段[备注]成功，执行时间{}，受影响的条数{}", (System.currentTimeMillis() - start) / 1000L, update1);
        String insertSql1 = "INSERT INTO `tf_data_log` (`table_name`,`start_time`,`update_columns`, `execute_time`, `update_num`) VALUES (?,?,?,?,?)";
        jdbcTemplate.update(insertSql1, "tf_arrear", new Date(), "备注", executeSeconds, update1);
        //数据格式转换

        List<String> sqls = new ArrayList<>();
        sqls.add("update  tf_arrear a   set a.`客户名称`=trim(a.`客户名称`)");
        sqls.add("update  tf_arrear a   set a.`客户名称`=replace(a.`客户名称`,'，','_')");
        sqls.add("update  tf_arrear a   set a.`客户名称`=replace(a.`客户名称`,',','_')");
        sqls.add("update  tf_arrear a   set a.`客户名称`=replace(a.`客户名称`,',','_')");
        sqls.add("update  tf_arrear a   set a.`客户名称`=replace(a.`客户名称`,'、','_')");
        sqls.add("update  tf_arrear a   set a.`客户名称`=replace(a.`客户名称`,'/','_')");
        sqls.add("update  tf_arrear a   set a.`客户名称`=replace(a.`客户名称`,' ','_');");
        jdbcTemplate.batchUpdate(sqls.toArray(new String[0]));
        return true;
    }


    /**
     * 已收款数据
     */
    @Transactional
    public boolean updateTfPaymentDetail() {
        long start = System.currentTimeMillis();
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
        int update = jdbcTemplate.update(updateSql1);
        long executeSeconds = (System.currentTimeMillis() - start) / 1000L;
        log.info("更新 tf_paymentdetail 表的字段[房屋编号，组图区域，应收日期]成功，执行时间{}，受影响的条数{}", executeSeconds, update);
        String insertSql = "INSERT INTO `tf_data_log` ('table_name',`start_time`,`update_columns`, `execute_time`, `update_num`) VALUES (?,?,?,?,?)";
        jdbcTemplate.update(insertSql, "tf_paymentdetail", new Date(), "房屋编号，组图区域，应收日期", executeSeconds, update);

        List<String> sqls = new ArrayList<>();
        sqls.add("update  tf_paymentdetail a   set a.`客户名称`=trim(a.`客户名称`)");
        sqls.add("update  tf_paymentdetail a   set a.`客户名称`=replace(a.`客户名称`,'，','_')");
        sqls.add("update  tf_paymentdetail a   set a.`客户名称`=replace(a.`客户名称`,',','_')");
        sqls.add("update  tf_paymentdetail a   set a.`客户名称`=replace(a.`客户名称`,',','_')");
        sqls.add("update  tf_paymentdetail a   set a.`客户名称`=replace(a.`客户名称`,'、','_')");
        sqls.add("update  tf_paymentdetail a   set a.`客户名称`=replace(a.`客户名称`,'/','_')");
        sqls.add("update  tf_paymentdetail a   set a.`客户名称`=replace(a.`客户名称`,' ','_')");
        jdbcTemplate.batchUpdate(sqls.toArray(new String[0]));

        return true;
    }


    @Transactional
    public void baseBatchInsert(List<Object[]> values) {
        log.info("开始删除数据......");
        long start = System.currentTimeMillis();
        String deleteSql = "TRUNCATE TABLE `tf_base`";
        jdbcTemplate.execute(deleteSql);
        log.info("开始插入数据......");
        String insertSql = "INSERT INTO `finance`.`tf_base`(`企业名称`, `所属区域`, `项目名称`, `项目类型`, `项目编号`, `管理区域名称`, `管理区域编号`, `业态组团名称`, `业态组团编号`, `楼栋名称`, `楼栋编号`, `单元名称`, `单元编号`, `楼层`, `房号`, `房产编号`, `房产简称`, `计费面积`, `房产类型`, `房屋类型`, `装修类型`, `入伙日期`, `辅助计费面积`, `建筑面积`, `预测建筑面积`, `套内面积`, `预测套内面积`, `公摊面积`, `花园面积`, `地下室面积`, `赠送面积`, `关联房产简称`, `是否虚拟`, `第三方房产ID`, `房产备注`, `售楼日期`, `收房日期`, `入住日期`, `业主客户类型`, `业主客户名称`, `业主类型`, `业主联系电话`, `业主联系人`, `业主证件类型`, `业主证件号码`, `业主客户性质`, `业主籍贯`, `业主婚姻状况`, `业主文化程度`, `业主国籍/地区`, `业主民族`, `业主第三方ID`) VALUES" +
                "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
                "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        batchSizeUpdate(values, insertSql);
        log.info("插入数据成功,耗时{}s", (System.currentTimeMillis() - start) / 1000L);
    }

    @Transactional
    public void paymentdetailBatchInsert(List<Object[]> values) {
        log.info("开始插入数据......");
        long start = System.currentTimeMillis();
        String deleteSql = "TRUNCATE TABLE `tf_paymentdetail`";
        jdbcTemplate.execute(deleteSql);
        log.info("开始插入数据......");
        String insertSql = "INSERT INTO `tf_paymentdetail`(`费用ID`, `客户名称`, `房屋编号`, `房屋名称`, `客户类别`, `管家`, `组团区域`, `楼宇名称`, `建筑面积`, `套内面积`, `公摊面积`, `花园面积`, `地下室面积`, `计算面积`, `产权性质`, `楼宇类型`, `使用性质`, `使用状态`, `交房状态`, `实际交房时间`, `考核条件`, `车位区域`, `车位路址`, `车位编号`, `车位面积`, `计费面积`, `车位类别`, `车位类型`, `产权性质 (1)`, `使用状态 (1)`, `实际交付时间`, `考核条件 (1)`, `费用名称`, `计费方式`, `计算方式`, `收费标准`, `计算周期`, `数量1`, `数量2`, `起度`, `止度`, `使用量`, `税率`, `费用日期`, `应收日期`, `开始日期`, `结束日期`, `收款日期`, `票据类别`, `票据号码`, `天问单号`, `含税金额`, `税费`, `不含税金额`, `佣金项目`, `收入含税金额`, `收入税费`, `收入不含税金额`, `合同含税金额`, `合同税费`, `合同不含税金额`, `收款人`, `收款方式`, `备注`) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
                "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        batchSizeUpdate(values, insertSql);
        log.info("插入数据成功,耗时{}s", (System.currentTimeMillis() - start) / 1000L);
    }

    @Transactional
    public void arrearBatchInsert(List<Object[]> values) {
        log.info("开始删除数据......");
        long start = System.currentTimeMillis();
        String deleteSql = "TRUNCATE TABLE `tf_arrear`";
        jdbcTemplate.execute(deleteSql);
        log.info("开始插入数据......");
        String insertSql = "INSERT INTO `tf_arrear`(`费用ID`, `客户名称`, `房屋编号`, `房屋名称`, `客户类别`, `移动电话`, `管家`, `组团区域`, `楼宇名称`, `建筑面积`, `套内面积`, `公摊面积`, `花园面积`, `地下室面积`, `计算面积`, `产权性质`, `楼宇类型`, `使用性质`, `使用状态`, `交房状态`, `实际交房时间`, `考核条件`, `车位区域`, `车位路址`, `车位编号`, `车位面积`, `车位类别`, `车位类型`, `车位产权性质`, `车位使用状态`, `车位实际交付时间`, `车位考核条件`, `费用名称`, `计费方式`, `计算方式`, `收费标准`, `计算周期`, `数量1`, `数量2`, `起度`, `止度`, `使用量`, `费用日期`, `应收日期`, `开始日期`, `结束日期`, `收款日期`, `税率`, `含税金额`, `税费`, `不含税金额`, `收入佣金科目`, `收入佣金含税金额`, `收入佣金税率`, `佣金科目`, `佣金含税金额`, `佣金税费`, `佣金不含税费`, `实收含税金额`, `实收税费`, `实收不含税金额`, `预存含税金额`, `预存税费`, `预存不含税金额`, `减免含税金额`, `减免税费`, `减免不含税金额`, `欠费含税金额`, `欠费税费`, `欠费不含税金额`, `退款金额`, `备注`)" +
                " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
//        jdbcTemplate.batchUpdate(insertSql, values);
        batchSizeUpdate(values, insertSql);
        //singleUpdate(values, insertSql);
        log.info("插入数据成功,耗时{}s", (System.currentTimeMillis() - start) / 1000L);
    }

    @Transactional
    public void tuikuanBatchInsert(List<Object[]> values) {
        log.info("开始删除数据......");
        long start = System.currentTimeMillis();
        String deleteSql = "TRUNCATE TABLE `tf_tuikuan`";
        jdbcTemplate.execute(deleteSql);
        log.info("开始插入数据......");
        String insertSql = "INSERT INTO `tf_tuikuan`(`客户名称`, `房屋编号`, `费用名称`, `收款时间`, `收据号码`, `收款人`, `收款金额`, `冲抵金额`, `合同违约金`, `付款金额`, `已退金额`, `未退金额`, `费用性质`, `费用时间`, `备注`) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        batchSizeUpdate(values, insertSql);
        log.info("插入数据成功,耗时{}s", (System.currentTimeMillis() - start) / 1000L);

        //更新数据
        List<String> sqls = new ArrayList<>();
        sqls.add("UPDATE tf_tuikuan a SET a.`房屋编号` = REPLACE ( a.`房屋编号`, '\\'', '' )");
        sqls.add("UPDATE tf_tuikuan a\n" +
                "LEFT JOIN tf_base b ON a.`房屋编号` = b.`房产备注` \n" +
                "SET a.`项目名称` = b.`项目名称`,\n" +
                "a.`房屋编号` = b.`房产简称`");
        sqls.add("UPDATE tf_tuikuan a SET a.`客户名称` = trim( a.`客户名称` )");
        sqls.add("UPDATE tf_tuikuan a SET a.`客户名称` = REPLACE ( a.`客户名称`, '，', '_' )");
        sqls.add("UPDATE tf_tuikuan a SET a.`客户名称` = REPLACE ( a.`客户名称`, ',', '_' )");
        sqls.add("UPDATE tf_tuikuan a SET a.`客户名称` = REPLACE ( a.`客户名称`, ',', '_' )");
        sqls.add("UPDATE tf_tuikuan a SET a.`客户名称` = REPLACE ( a.`客户名称`, '、', '_' )");
        sqls.add("UPDATE tf_tuikuan a SET a.`客户名称` = REPLACE ( a.`客户名称`, '/', '_' )");
        sqls.add("UPDATE tf_tuikuan a SET a.`客户名称` = REPLACE ( a.`客户名称`, ' ', '_' )");
        jdbcTemplate.batchUpdate(sqls.toArray(new String[0]));

    }


    /**
     * 单条插入
     */
    public void singleUpdate(List<Object[]> datas, String sqlTemplate) {
        for (Object[] data : datas) {
            jdbcTemplate.update(sqlTemplate, data);
        }
    }


    public void batchSizeUpdate(List<Object[]> datas, String sqlTemplate) {
        if (datas.size() <= 0) {
            return;
        }
        int batchSize = 1000;
        int start = 0;
        int size = batchSize;

        do {
            if (datas.size() - start < size) {
                //不足1000数量，重新计算size
                size = datas.size() - start;
            }
            batchUpdate(datas, sqlTemplate, start, size);
            start += size;
        } while (start < datas.size());
    }

    public void batchUpdate(final List<Object[]> datas, String sql, final int start, final int size) {
//        DefaultTransactionDefinition transDefinition = new DefaultTransactionDefinition();
//        //开启新事物
//        transDefinition.setPropagationBehavior(DefaultTransactionDefinition.PROPAGATION_REQUIRES_NEW);
//        TransactionStatus transStatus = transactionManager.getTransaction(transDefinition);
//        try {
        int[] value = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {

                Object[] objects = datas.get(start + i);
                if (Objects.isNull(objects[0])) {
                    log.info("异常数据:{}", writeToJson(objects));
                }
                //设置具体的参数
                for (int j = 1; j <= objects.length; j++) {
                    ps.setObject(j, objects[j - 1]);
                }
            }

            @Override
            public int getBatchSize() {
                return size;
            }
        });
//            transactionManager.commit(transStatus);
//        } catch (Exception e) {
//            log.error("batchUpdate() error:", e);
//            transactionManager.rollback(transStatus);
//        }
    }

    private String writeToJson(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
