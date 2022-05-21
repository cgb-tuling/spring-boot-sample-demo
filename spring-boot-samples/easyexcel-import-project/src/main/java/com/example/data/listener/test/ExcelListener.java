package com.example.data.listener.test;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.example.data.domain.ExcelTemplate;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author lbb
 * 方法执行顺序，从上到下
 */
@Getter
public class ExcelListener extends AnalysisEventListener<ExcelTemplate> {

    /**
     * 读到到数据
     */
    List<ExcelTemplate> excelTemplateList = new LinkedList<>();
    /**
     * 读取表头,如果设置了 headRowNumber(0) ，则改方法不会被调用
     * 空实现即可，或者根据headMap.size()验证列数，context.readSheetHolder().getApproximateTotalRowNumber()验证行数
     * 
     * @param headMap
     * @param context
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头信息："+headMap+"共多少列"+headMap.size()+"共多少行"+context.readSheetHolder().getApproximateTotalRowNumber());
    }
    /**
     * 一行一行的去读
     * 这里可以根据 list 的size去分批入库，防止OOM
     * list 的size每够3000条，存一次数据库，之后清空list，
     * 但：1.如果导入失败需要事务回滚就不能分批入库
     *    2.本来解析excel是在controller，存库在service，为防止OOM，这里分层混了
     * 所以推荐一次入库，控制文件流的大小即可
     * @param excelTemplate
     * @param analysisContext
     */
    @Override
    public void invoke(ExcelTemplate excelTemplate, AnalysisContext analysisContext) {
        excelTemplateList.add(excelTemplate);
        //读取第一行时，获取总行数
        if (excelTemplateList.size() == 1) {
            System.out.println("共多少行" + analysisContext.readSheetHolder().getApproximateTotalRowNumber());
        }
    }


    /**
     * 读完之后执行，空实现即可，或者日志记录size
     * @param analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println("ok了"+excelTemplateList);
    }
}

