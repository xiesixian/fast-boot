package com.xiesx.fastboot.base.result;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;

import lombok.NonNull;

/**
 * @title R.java
 * @description 返回值快速构造类
 * @author Sixian.xie
 * @date 2020-7-21 22:30:11
 */
public class R {

    /**
     * 成功
     */
    public static BaseResult succ() {
        return BaseResult.builder().code(BaseResult.SUCCESS).msg(BaseResult.OP_MSG_SUCC).build();
    }

    public static BaseResult succ(@NonNull String msg) {
        return BaseResult.builder().code(BaseResult.SUCCESS).msg(msg).build();
    }

    public static BaseResult succ(@NonNull Object data) {
        return BaseResult.builder().code(BaseResult.SUCCESS).msg(BaseResult.OP_MSG_SUCC).data(data).build();
    }

    public static BaseResult succ(@NonNull String msg, @NonNull Object data) {
        return BaseResult.builder().code(BaseResult.SUCCESS).msg(msg).data(data).build();
    }

    public static BaseResult succ(@NonNull Integer code, @NonNull String msg) {
        return BaseResult.builder().code(code).msg(msg).build();
    }

    public static BaseResult succ(@NonNull Integer code, @NonNull String msg, @NonNull Object data) {
        return BaseResult.builder().code(code).msg(msg).data(data).build();
    }

    /**
     * 失败
     */
    public static BaseResult fail() {
        return BaseResult.builder().code(BaseResult.FAIL).msg(BaseResult.OP_MSG_FAIL).build();
    }

    public static BaseResult fail(@NonNull String msg) {
        return BaseResult.builder().code(BaseResult.FAIL).msg(msg).build();
    }

    public static BaseResult fail(@NonNull Object data) {
        return BaseResult.builder().code(BaseResult.FAIL).msg(BaseResult.OP_MSG_FAIL).data(data).build();
    }

    public static BaseResult fail(@NonNull String msg, Object data) {
        return BaseResult.builder().code(BaseResult.FAIL).msg(msg).data(data).build();
    }

    public static BaseResult fail(@NonNull Integer code, @NonNull String msg) {
        return BaseResult.builder().code(code).msg(msg).build();
    }

    public static BaseResult fail(@NonNull Integer code, @NonNull String msg, Object data) {
        return BaseResult.builder().code(code).msg(msg).data(data).build();
    }

    /**
     * 异常
     */
    public static BaseResult error() {
        return BaseResult.builder().code(BaseResult.ERROR).msg(BaseResult.OP_MSG_FAIL).build();
    }

    public static BaseResult error(@NonNull String msg) {
        return BaseResult.builder().code(BaseResult.ERROR).msg(msg).build();
    }

    public static BaseResult error(@NonNull Object data) {
        return BaseResult.builder().code(BaseResult.ERROR).msg(BaseResult.OP_MSG_FAIL).data(data).build();
    }

    public static BaseResult error(@NonNull String msg, Object data) {
        return BaseResult.builder().code(BaseResult.ERROR).msg(msg).data(data).build();
    }

    public static BaseResult error(@NonNull Integer code, @NonNull String msg) {
        return BaseResult.builder().code(code).msg(msg).build();
    }

    public static BaseResult error(@NonNull Integer code, @NonNull String msg, Object data) {
        return BaseResult.builder().code(code).msg(msg).data(data).build();
    }

    /**
     * 重试
     */
    public static BaseResult retry() {
        return BaseResult.builder().code(BaseResult.RETRY).msg(BaseResult.OP_MSG_FAI_TETRY).build();
    }

    public static BaseResult retry(@NonNull String msg) {
        return BaseResult.builder().code(BaseResult.RETRY).msg(msg).build();
    }

    public static BaseResult retry(@NonNull Object data) {
        return BaseResult.builder().code(BaseResult.RETRY).msg(BaseResult.OP_MSG_FAI_TETRY).data(data).build();
    }

    public static BaseResult retry(@NonNull String msg, Object data) {
        return BaseResult.builder().code(BaseResult.RETRY).msg(msg).data(data).build();
    }

    public static BaseResult retry(@NonNull Integer code, @NonNull String msg) {
        return BaseResult.builder().code(code).msg(msg).build();
    }

    public static BaseResult retry(@NonNull Integer code, @NonNull String msg, Object data) {
        return BaseResult.builder().code(code).msg(msg).data(data).build();
    }


    /**
     * str 转 jsonObject
     */
    public static <T> T parseObject(String text, Class<T> clazz) {
        return JSON.parseObject(text, clazz, new Feature[0]);
    }

    /**
     * str 转 jsonArray
     *
     * @param text
     * @param clazz
     * @return
     */
    public static <T> List<T> parseArray(String text, Class<T> clazz) {
        return JSON.parseArray(text, clazz);
    }
}
