package com.example.data.service;

import com.google.common.collect.Lists;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 冲抵明细
 */
@Service("offset")
public class OffsetDataTransfer extends AbstractDataTransfer {

    public OffsetDataTransfer(JdbcTemplate jdbcTemplate, DataSourceTransactionManager transactionManager) {
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
        String executeSql = "TRUNCATE TABLE `tf_yucun_cd`";
        execute(executeSql);

        String tableName = "tf_yucun_cd";
        log.info("开始导入冲抵明细数据，表名为 {}", tableName);
//        String insertSql = "INSERT INTO `finance`.`tf_yucun_cd`(`费用ID`, `客户名称`, `房屋编号`, `房屋名称`, `客户类别`, `联系电话`, `管家`, `组团区域`, `楼宇名称`, `建筑面积`, `套内面积`, `公摊面积`, `花园面积`, `地下室面积`, `计算面积`, `产权性质`, `楼宇类型`, `使用性质`, `使用状态`, `交房状态`, `实际交房时间`, `考核条件`, `车位区域`, `车位路址`, `车位编号`, `车位面积`, `计算面积 (1)`, `车位类别`, `车位类型`, `产权性质 (1)`, `使用状态 (1)`, `实际交付时间`, `考核条件 (1)`, `费用名称`, `计费方式`, `计算方式`, `收费标准`, `计算周期`, `数量1`, `数量2`, `起度`, `止度`, `使用量`, `税率`, `费用日期`, `应收日期`, `费用开始时间`, `费用结束时间`, `预交冲抵日期`, `冲抵人`, `标准名称`, `应冲含税金额`, `应冲税费`, `应冲不含税金额`, `原预交余额`, `冲抵总金额`, `预交余额`, `冲抵项目`, `冲抵含税金额`, `冲抵税费`, `冲抵不含税金额`, `冲抵佣金项目`, `冲抵佣金金额`, `处理方式`)" +
//                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
//                "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        String insertSql = "INSERT INTO `tf_yucun_cd`(`费用ID`, `客户名称`, `房屋编号`, `房屋名称`, `客户类别`, `联系电话`, `管家`, `组团区域`, `楼宇名称`, `建筑面积`, `套内面积`, `公摊面积`, `花园面积`, `地下室面积`, `计算面积`, `产权性质`, `楼宇类型`, `使用性质`, `使用状态`, `交房状态`, `实际交房时间`, `考核条件`, `车位区域`, `车位路址`, `车位编号`, `车位面积`, `计算面积 (1)`, `车位类别`, `车位类型`, `产权性质 (1)`, `使用状态 (1)`, `实际交付时间`, `考核条件 (1)`, `费用名称`, `计费方式`, `计算方式`, `收费标准`, `计算周期`, `数量1`, `数量2`, `起度`, `止度`, `使用量`, `税率`, `费用日期`, `应收日期`, `费用开始时间`, `费用结束时间`, `预交冲抵日期`, `冲抵人`, `标准名称`, `应冲含税金额`, `应冲税费`, `应冲不含税金额`, `原预交余额`, `冲抵总金额`, `预交余额`, `冲抵项目`, `冲抵含税金额`, `冲抵税费`, `冲抵不含税金额`, `冲抵佣金项目`, `冲抵佣金金额`, `处理方式`)";
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
        batchSizeUpdate(sql, values);
        log.info("导入冲抵明细数据成功,耗时{}s", (System.currentTimeMillis() - start) / 1000L);
        return true;
    }

    @Override
    @Transactional
    public void doClean() {
        start = System.currentTimeMillis();
        long begin = start;
        String updateSql = "UPDATE `tf_yucun_cd` a\n" +
                "LEFT JOIN tf_base b ON a.`房屋编号` = b.`房产备注`  \n" +
                "\tSET a.`房屋编号` =\n" +
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
                "\ta.`组团区域` = b.`项目名称`";
        int update = update(updateSql);
        long executeSeconds = (System.currentTimeMillis() - start) / 1000L;
        log.info("更新 tf_yucun_cd 表的字段[房屋编号,组团区域]成功，执行时间{}，受影响的条数{}", executeSeconds, update);
        String insertSql = "INSERT INTO `tf_data_log` (`table_name`,`start_time`,`update_columns`, `execute_time`, `update_num`) VALUES (?,?,?,?,?)";
        if (update > 0) {
            update(insertSql, "tf_yucun_cd", new Date(), "房屋编号,组团区域", executeSeconds, update);
        }

        log.info("开始转换应收款数据格式");
        start = System.currentTimeMillis();
        List<String> sqls = new ArrayList<>();
        sqls.add("update  tf_yucun_cd a   set a.`客户名称`=trim(a.`客户名称`)");
        sqls.add("update  tf_yucun_cd a   set a.`客户名称`=replace(a.`客户名称`,'，','_')");
        sqls.add("update  tf_yucun_cd a   set a.`客户名称`=replace(a.`客户名称`,',','_')");
        sqls.add("update  tf_yucun_cd a   set a.`客户名称`=replace(a.`客户名称`,'、','_')");
        sqls.add("update  tf_yucun_cd a   set a.`客户名称`=replace(a.`客户名称`,'/','_')");
        sqls.add("update  tf_yucun_cd a   set a.`客户名称`=replace(a.`客户名称`,' ','_');");
        int[] ints = batchUpdate(sqls.toArray(new String[0]));
        executeSeconds = (System.currentTimeMillis() - start) / 1000L;
        int count = 0;
        for (int i : ints) {
            count = count + i;
        }
        log.info("更新 tf_arrear 表的客户名称字段格式成功，执行时间{}，受影响的条数{}", executeSeconds, count);
        String insertSql2 = "INSERT INTO `tf_data_log` (`table_name`,`start_time`,`update_columns`, `execute_time`, `update_num`) VALUES (?,?,?,?,?)";
        if (count > 0) {
            update(insertSql2, "tf_yucun_cd", new Date(), "转换客户名称数据格式", executeSeconds, count);
        }
        log.info("清理应收款数据成功,总耗时{}s", (System.currentTimeMillis() - begin) / 1000L);

    }
}
