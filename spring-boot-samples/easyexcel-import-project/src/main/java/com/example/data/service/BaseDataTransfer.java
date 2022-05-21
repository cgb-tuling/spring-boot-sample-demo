package com.example.data.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 基础数据
 */
@Service("base")
public class BaseDataTransfer extends AbstractDataTransfer {

    public BaseDataTransfer(JdbcTemplate jdbcTemplate, DataSourceTransactionManager transactionManager) {
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
        String executeSql = "TRUNCATE TABLE `tf_base`";
        execute(executeSql);
        String tableName = "tf_base";
        log.info("开始导入基础数据，表名为 {}", tableName);
//        String insertSql = "INSERT INTO `tf_base`(`企业名称`, `所属区域`, `项目名称`, `项目类型`, `项目编号`, `管理区域名称`, `管理区域编号`, `业态组团名称`, `业态组团编号`, `楼栋名称`, `楼栋编号`, `单元名称`, `单元编号`, `楼层`, `房号`, `房产编号`, `房产简称`, `计费面积`, `房产类型`, `房屋类型`, `装修类型`, `入伙日期`, `辅助计费面积`, `建筑面积`, `预测建筑面积`, `套内面积`, `预测套内面积`, `公摊面积`, `花园面积`, `地下室面积`, `赠送面积`, `关联房产简称`, `是否虚拟`, `第三方房产ID`, `房产备注`, `售楼日期`, `收房日期`, `入住日期`, `业主客户类型`, `业主客户名称`, `业主类型`, `业主联系电话`, `业主联系人`, `业主证件类型`, `业主证件号码`, `业主客户性质`, `业主籍贯`, `业主婚姻状况`, `业主文化程度`, `业主国籍/地区`, `业主民族`, `业主第三方ID`) VALUES" +
//                "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
//                "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        String insertSql = "INSERT INTO `tf_base`(`企业名称`, `所属区域`, `项目名称`, `项目类型`, `项目编号`, `管理区域名称`, `管理区域编号`, `业态组团名称`, `业态组团编号`, `楼栋名称`, `楼栋编号`, `单元名称`, `单元编号`, `楼层`, `房号`, `房产编号`, `房产简称`, `计费面积`, `房产类型`, `房屋类型`, `装修类型`, `入伙日期`, `辅助计费面积`, `建筑面积`, `预测建筑面积`, `套内面积`, `预测套内面积`, `公摊面积`, `花园面积`, `地下室面积`, `赠送面积`, `关联房产简称`, `是否虚拟`, `第三方房产ID`, `房产备注`, `售楼日期`, `收房日期`, `入住日期`, `业主客户类型`, `业主客户名称`, `业主类型`, `业主联系电话`, `业主联系人`, `业主证件类型`, `业主证件号码`, `业主客户性质`, `业主籍贯`, `业主婚姻状况`, `业主文化程度`, `业主国籍/地区`, `业主民族`, `业主第三方ID`)";
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
        log.info("导入基础数据成功,耗时{}s", (System.currentTimeMillis() - start) / 1000L);
        return true;
    }
}
