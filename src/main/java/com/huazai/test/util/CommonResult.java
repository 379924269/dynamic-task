package com.huazai.test.util;

import lombok.Data;

/**
 * description: 返回通用类
 *
 * @author: 华仔
 * @date: 2022/12/8
 */
@Data
public class CommonResult<T> {
    private boolean result;
    private long code;
    private String msg;
    private T data;

    public CommonResult(boolean result, long code, String message, T data) {
        this.result = result;
        this.code = code;
        this.msg = message;
        this.data = data;
    }

    public static <T> CommonResult<T> success() {
        return success(null);
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<T>(true, 200, "OK", data);
    }

    /**
     * 成功返回结果
     *
     * @param data    获取的数据
     * @param message 提示信息
     */
    public static <T> CommonResult<T> success(T data, String message) {
        return new CommonResult<T>(true, 200, message, data);
    }


    /**
     * 失败返回结果
     *
     * @param message 提示信息
     */
    public static <T> CommonResult<T> failed(String message) {
        return new CommonResult<T>(false, 200, message, null);
    }

    /**
     * 失败返回结果
     */
    public static <T> CommonResult<T> failed() {
        return failed("操作失败");
    }
}
