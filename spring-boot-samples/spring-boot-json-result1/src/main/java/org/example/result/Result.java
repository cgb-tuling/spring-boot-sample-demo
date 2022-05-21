package org.example.result;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result<T> implements Serializable {
    private Integer code;
    private String message;
    private T data;

    public Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(ResultCode resultCode, T data) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.data = data;
    }

    public Result() {

    }

    //返回成功
    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.setCode(ResultCode.SUCCESS.getCode());
        return result;
    }

    //返回成功
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setData(data);
        return result;
    }

    //返回失败
    public static <T> Result<T> failure(ResultCode resultcode) {
        Result<T> result = new Result<>();
        result.setCode(resultcode.getCode());
        return result;
    }

    //返回失败
    public static <T> Result<T> failure(ResultCode resultCode, T data) {
        Result<T> result = new Result<>();
        result.setCode(resultCode.getCode());
        result.setData(data);
        return result;

    }

    public static <T> Result<T> failure(int resultCode, T data) {
        Result<T> result = new Result<>();
        result.setCode(resultCode);
        result.setData(data);
        return result;

    }
}