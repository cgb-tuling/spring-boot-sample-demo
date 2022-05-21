package org.example.result;

import lombok.Data;
import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * 返回值结构体
 * @author bee
 */
@Data
public class ScResult  implements Serializable {

    private String code;
    private String message;
    private Object data;

    /**
     * 正常返回
     */
    public final static String SUCCESS = "0";
    /**
     * SCMSA异常、业务异常
     */
    public final static String BIZ_ERR = "-1";
    /**
     * 系统异常
     */
    public final static String SYS_ERR = "-2";


    /**
     * 直接设置返回值
     * @param value
     * @return
     */
    public static ScResult set(Object value) {
        ScResult tmResult = new ScResult();
        if (value == null) {
            tmResult.setCode(ScResult.SUCCESS);
            tmResult.setMessage("返回值为null.");
        } else {
            tmResult.setCode(ScResult.SUCCESS);
            tmResult.setData(value);
            tmResult.setMessage("");
        }
        return tmResult;
    }

    public static ScResult set(Object value, String message) {
        ScResult tmResult = new ScResult();
        tmResult.setCode(ScResult.SUCCESS);


        if (value == null) {
            if (StringUtils.isEmpty(message)) {
                message = "返回信息为null";
            }
            tmResult.setMessage(message);
        } else {
            tmResult.setData(value);

            if (StringUtils.isEmpty(message)) {
                message = "";
            }
            tmResult.setMessage(message);
        }

        return tmResult;
    }

    public static ScResult err(String message) {
        ScResult tmResult = new ScResult();
        tmResult.setCode(ScResult.BIZ_ERR);
        tmResult.setMessage(message);
        return tmResult;
    }

    public static ScResult sysErr(String message) {
        ScResult tmResult = new ScResult();
        tmResult.setCode(ScResult.SYS_ERR);
        tmResult.setMessage(message);
        return tmResult;
    }

    /**
     * 方法未实现
     * @return
     */
    public static ScResult methodNotImplement() {
        ScResult tmResult = new ScResult();
        tmResult.setCode(ScResult.SYS_ERR);
        tmResult.setMessage("方法未实现.");
        return tmResult;
    }

    /**
     * 用户未登录或不具备权限.
     * @return
     */
    public static ScResult unauthorized() {
        ScResult tmResult = new ScResult();
        tmResult.setCode("403");
        tmResult.setMessage("用户未登录或不具备权限.");
        return tmResult;
    }

    /**
     * 直接设置返回值
     * @param value
     * @return
     */
    public static ScResult setValueAndMsg(Object value,String msg) {
        ScResult tmResult = new ScResult();
        if (value == null) {
            tmResult.setCode(ScResult.SUCCESS);
            tmResult.setMessage("返回值为null.");
        } else {
            tmResult.setCode(ScResult.SUCCESS);
            tmResult.setData(value);
            tmResult.setMessage(msg);
        }
        return tmResult;
    }
}