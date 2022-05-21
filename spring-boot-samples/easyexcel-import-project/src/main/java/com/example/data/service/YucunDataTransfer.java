package com.example.data.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 预存款数据
 */
@Service("yucun")
public class YucunDataTransfer extends AbstractDataTransfer {

    public YucunDataTransfer(JdbcTemplate jdbcTemplate, DataSourceTransactionManager transactionManager) {
        super(jdbcTemplate, transactionManager);
    }

    @Override
    @Transactional
    public boolean doImport(List<Object[]> datas) {
        if (CollectionUtils.isEmpty(datas)){
            log.warn("导入的数据为空，不作处理。");
            return false;
        }
        //删除最后一个元素  因为最后一行为 合计
        datas.remove(datas.size() - 1);
        start = System.currentTimeMillis();
        log.info("导入数据前清除数据开始");
        String executeSql = "TRUNCATE TABLE `tf_yucun`";
        execute(executeSql);

        String tableName = "tf_yucun";
        log.info("开始导入已收款数据，表名为 {}", tableName);
//        String insertSql = "INSERT INTO `tf_yucun`(`客户名称`, `房屋编号`, `房屋名称`, `车位`, `车位编号`, `联系电话`, `移动电话`, `预交余额`, `是否参与冲抵`, `冲抵费用项目`, `备注`, `余额提示`, `项目名称`)" +
//                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        String insertSql = "INSERT INTO `tf_yucun`(`客户名称`, `房屋编号`, `房屋名称`, `车位`, `车位编号`, `联系电话`, `移动电话`, `预交余额`, `是否参与冲抵`, `冲抵费用项目`, `备注`, `余额提示`, `项目名称`)";
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
        log.info("导入已收款数据成功,耗时{}s", (System.currentTimeMillis() - start) / 1000L);
        return true;
    }

    @Override
    @Transactional
    public void doClean() {
        start = System.currentTimeMillis();
        long begin = start;
        String tableName = "tf_yucun";
        log.info("开始清理预存款数据，表名为 {}", tableName);
        String updateSql1 = "UPDATE tf_yucun a\n" +
                "        LEFT JOIN tf_base b ON a.`房屋编号` = b.`房产备注`\n" +
                "        SET a.`房屋编号` =\n" +
                "        CASE\n" +
                "        a.`冲抵费用项目`\n" +
                "        WHEN '车位物管费' THEN\n" +
                "        a.`车位`\n" +
                "        WHEN '车位物管费-昆明耍街项目' THEN\n" +
                "        a.`车位`\n" +
                "        WHEN '地下车位租金' THEN\n" +
                "        a.`车位`\n" +
                "        WHEN '车位使用费' THEN\n" +
                "        a.`车位`\n" +
                "        WHEN '【停用】车位费' THEN\n" +
                "        a.`车位`\n" +
                "        WHEN '车位使用费（停用）' THEN\n" +
                "        a.`车位`\n" +
                "        WHEN '【停用】车位使用费' THEN\n" +
                "        a.`车位`\n" +
                "        WHEN '车位租金（代收）' THEN\n" +
                "        a.`车位` ELSE b.`房产简称`\n" +
                "        END,\n" +
                "                a.`项目名称` = b.`项目名称`";
        int update = update(updateSql1);
        long executeSeconds = (System.currentTimeMillis() - start) / 1000L;
        log.info("更新 tf_yucun 表的字段[房屋编号，项目名称]成功，执行时间{}，受影响的条数{}", executeSeconds, update);
        String insertSql = "INSERT INTO `tf_data_log` (`table_name`,`start_time`,`update_columns`, `execute_time`, `update_num`) VALUES (?,?,?,?,?)";
        if (update>0)
        update(insertSql, "tf_yucun", new Date(), "房屋编号，项目名称", executeSeconds, update);

        log.info("开始转换已存款数据格式");
        start = System.currentTimeMillis();
        List<String> sqls = new ArrayList<>();
        sqls.add("update  tf_yucun a   set a.`客户名称`=trim(a.`客户名称`)");
        sqls.add("update  tf_yucun a   set a.`客户名称`=replace(a.`客户名称`,'，','_')");
        sqls.add("update  tf_yucun a   set a.`客户名称`=replace(a.`客户名称`,',','_')");
        sqls.add("update  tf_yucun a   set a.`客户名称`=replace(a.`客户名称`,',','_')");
        sqls.add("update  tf_yucun a   set a.`客户名称`=replace(a.`客户名称`,'、','_')");
        sqls.add("update  tf_yucun a   set a.`客户名称`=replace(a.`客户名称`,'/','_')");
        sqls.add("update  tf_yucun a   set a.`客户名称`=replace(a.`客户名称`,' ','_')");
        int[] ints = batchUpdate(sqls.toArray(new String[0]));
        executeSeconds = (System.currentTimeMillis() - start) / 1000L;
        int count = 0;
        for (int i : ints) {
            count = count + i;
        }
        log.info("更新 tf_yucun 表的客户名称字段格式成功，执行时间{}，受影响的条数{}", executeSeconds, count);
        String insertSql2 = "INSERT INTO `tf_data_log` (`table_name`,`start_time`,`update_columns`, `execute_time`, `update_num`) VALUES (?,?,?,?,?)";
        if (count>0)
        update(insertSql2, "tf_yucun", new Date(), "转换客户名称数据格式", executeSeconds, count);
        log.info("清理应已存款数据成功,总耗时{}s", (System.currentTimeMillis() - begin) / 1000L);
    }
}
