package org.example.result;

import lombok.Data;

/**
 * 分页返回值结构体
 * @author bee
 */
@Data
public class ScPageResult extends ScResult {

    private long total;

    private long totalPage;

    public static ScResult set(Object value, int totalPages, long totalElements) {
        ScPageResult rs = new ScPageResult();
        if(value == null) {
            rs.setCode(ScResult.SUCCESS);
            rs.setMessage("返回值为null.");
        } else {
            rs.setCode(ScResult.SUCCESS);
            rs.setData(value);
            rs.setTotalPage(totalPages);
            rs.setTotal(totalElements);
        }
        return rs;
    }
}
