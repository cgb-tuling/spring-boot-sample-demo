package com.example.data.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.metadata.CellExtra;
import com.alibaba.fastjson.JSON;
import com.example.data.config.SpringUtils;
import com.example.data.service.DataTransfer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * 直接用list接收数据*
 *
 * @author
 */
@Slf4j
@Getter
public class DataImportListener extends AnalysisEventListener<LinkedHashMap<Integer, String>> {
    private ObjectMapper mapper = new ObjectMapper();
    private final LinkedList<LinkedHashMap<Integer, String>> headColumns = new LinkedList<>();
    private final String beanName;
    //从哪一行开始读
    private final Integer headRowNumber;

    private long start;

    /**
     * 合并单元格
     */
    private List<CellExtra> extraMergeInfoList = new ArrayList<>();

    public DataImportListener(String beanName, Integer headRowNumber) {
        this.beanName = beanName;
        this.headRowNumber = headRowNumber;
        start = System.currentTimeMillis();
    }

    /**
     * 结果集 保证顺序
     */
    LinkedList<Object[]> list = new LinkedList<>();

    /**
     * 一行一行的读
     *
     * @param data
     * @param context
     */
    @Override
    public void invoke(LinkedHashMap<Integer, String> data, AnalysisContext context) {
        LinkedList<String> listData = new LinkedList<>();
        for (int i = 0; i < data.size(); i++) {
            listData.add(data.get(i));
        }
//        LinkedHashMap<Integer, String> headMap = headColumns.get(headRowNumber - 1);
//        LinkedHashMap<Integer, String> headMap = resetHeadMap();
/*        LinkedHashMap<Integer, String> headMap = resetHeadMap();
        LinkedHashMap<String, String> dataMap = new LinkedHashMap<>();
        for (Map.Entry<Integer, String> next : headMap.entrySet()) {
            String headName = next.getValue().trim();
            dataMap.put(next.getValue(), data.get(next.getKey()));
        }
        list.add(dataMap);*/
//        log.info("解析到一条数据:{}", writeToJson(data));
        Object[] objects = listData.toArray(new Object[0]);
        list.add(objects);
    }

    private LinkedHashMap<Integer, String> resetHeadMap() {
        LinkedHashMap<Integer, String> map = headColumns.get(headRowNumber - 1);
        LinkedHashMap<Integer, String> newMap = Maps.newLinkedHashMap(map);
        for (Map.Entry<Integer, String> entry : newMap.entrySet()) {
            //String value = entry.getValue();
            Integer key = entry.getKey();
            int index = headRowNumber - 1;
            String value = getNonValue(key, index);
            if (!StringUtils.hasText(value)) {
                log.warn("第{}列表头数据为空", key);
            }
            newMap.put(key, value);
        }
        if (headColumns.size() > 2) {
            LinkedHashMap<Integer, String> linkedHashMap = headColumns.get(headRowNumber - 2);
            if (linkedHashMap.size() > map.size()) {
                int index = linkedHashMap.size() - map.size();
                for (int i = 0; i < index; i++) {
                    String value = linkedHashMap.get(map.size() + i);
                    newMap.put(map.size() + i, value);
                }
            }
        }
        return newMap;
    }

    private String getNonValue(Integer key, int index) {
        if (index < 0) {
            return "";
        }
        String value = headColumns.get(index).get(key);
        if (!StringUtils.hasText(value)) {
            return getNonValue(key, --index);
        }
        return value;
    }


    /**
     * 读完之后的操作
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("解析数据共耗时{}s,共{}条数据", (System.currentTimeMillis() - start) / 1000L, list.size());
        DataTransfer dataTransfer = SpringUtils.getBean(beanName, DataTransfer.class);
        dataTransfer.doImport(list);
        if (headColumns.size() > 0) {
            headColumns.clear();
        }
    }

    /**
     * 这里会一行行的返回头
     * 监听器只需要重写这个方法就可以读取到头信息
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        log.info("解析到一条头数据:{}", writeToJson(headMap));
        LinkedHashMap<Integer, String> map = Maps.newLinkedHashMap(headMap);
        headColumns.add(map);
    }

    /**
     * 监听器实现这个方法就可以在读取数据的时候获取到异常信息
     */
    @Override
    public void onException(Exception exception, AnalysisContext context) {
        log.error("解析失败，但是继续解析下一行:{}", exception.getMessage());
        // 如果是某一个单元格的转换异常 能获取到具体行号
        // 如果要获取头的信息 配合invokeHeadMap使用
        if (exception instanceof ExcelDataConvertException) {
            ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException) exception;
            log.error("第{}行，第{}列解析异常", excelDataConvertException.getRowIndex(),
                    excelDataConvertException.getColumnIndex());
        }
        throw new RuntimeException("解析失败");
    }

    @Override
    public void extra(CellExtra extra, AnalysisContext context) {
        log.info("读取到了一条额外信息:{}", JSON.toJSONString(extra));
        switch (extra.getType()) {
            case COMMENT: {
                log.info("额外信息是批注,在rowIndex:{},columnIndex;{},内容是:{}", extra.getRowIndex(), extra.getColumnIndex(),
                        extra.getText());
                break;
            }
            case HYPERLINK: {
                if ("Sheet1!A1".equals(extra.getText())) {
                    log.info("额外信息是超链接,在rowIndex:{},columnIndex;{},内容是:{}", extra.getRowIndex(),
                            extra.getColumnIndex(), extra.getText());
                } else if ("Sheet2!A1".equals(extra.getText())) {
                    log.info(
                            "额外信息是超链接,而且覆盖了一个区间,在firstRowIndex:{},firstColumnIndex;{},lastRowIndex:{},lastColumnIndex:{},"
                                    + "内容是:{}",
                            extra.getFirstRowIndex(), extra.getFirstColumnIndex(), extra.getLastRowIndex(),
                            extra.getLastColumnIndex(), extra.getText());
                } else {
                    log.error("Unknown hyperlink!");
                }
                break;
            }
            case MERGE: {
                log.info(
                        "额外信息是合并单元格,而且覆盖了一个区间,在firstRowIndex:{},firstColumnIndex;{},lastRowIndex:{},lastColumnIndex:{}",
                        extra.getFirstRowIndex(), extra.getFirstColumnIndex(), extra.getLastRowIndex(),
                        extra.getLastColumnIndex());
                if (extra.getRowIndex() >= headRowNumber) {
                    extraMergeInfoList.add(extra);
                }
                break;
            }
            default: {
            }
        }
    }

    public List<CellExtra> getExtraMergeInfoList() {
        return extraMergeInfoList;
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
