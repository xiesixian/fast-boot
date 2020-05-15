package com.xiesx.springboot.base.result;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Builder;
import lombok.Data;

/**
 * @title BaseResult.java
 * @description 响应基类
 * @author Sixian.xie
 * @date 2018年12月26日 上午10:51:20
 */
@Data
@Builder
public class BaseResult {

    public static String OP_MSG_SUCC = "操作成功";

    public static String OP_MSG_FAIL = "操作失败";

    // 返回值
    public static Integer SUCCESS = 0; // 成功

    public static Integer FAIL = -1;// 失败

    public static Integer ERROR = -2;// 异常

    public static Integer ERROR_RETRY = -3;// 重试

    // 状态
    @JSONField(ordinal = 1)
    private Integer code;

    // 提示
    @JSONField(ordinal = 2)
    private String msg;

    // 数据
    @JSONField(ordinal = 3)
    private Object data;

    /**
     * 判断是否成功并返回
     */
    @JSONField(ordinal = 4)
    public Boolean getSuccess() {
        return code == SUCCESS;
    }

    // ====================================================================================================================

    /**
     * 判断是否成功
     */
    @JSONField(serialize = false)
    public Boolean isOk() {
        return code == SUCCESS ? true : false;
    }

    /**
     * 判断是否失败
     *
     * @return
     */
    @JSONField(serialize = false)
    public Boolean isFail() {
        return code == FAIL ? true : false;
    }

    /**
     * 判断是否异常
     *
     * @return
     */
    @JSONField(serialize = false)
    public Boolean isError() {
        return code == ERROR ? true : false;
    }

    // ====================================================================================================================

    /**
     * 成功
     */
    /*
     * public static BaseResult success() { return
     * BaseResult.builder().code(SUCCESS).msg(OP_MSG_SUCC).build(); } public static BaseResult
     * success(@NonNull String msg) { return BaseResult.builder().code(SUCCESS).msg(msg).build(); }
     * public static BaseResult success(@NonNull Object data) { return
     * BaseResult.builder().code(SUCCESS).msg(OP_MSG_SUCC).data(data).build(); } public static
     * BaseResult success(@NonNull String msg, @NonNull Object data) { return
     * BaseResult.builder().code(SUCCESS).msg(msg).data(data).build(); } public static BaseResult
     * success(@NonNull Integer code, @NonNull String msg) { return
     * BaseResult.builder().code(code).msg(msg).build(); } public static BaseResult success(@NonNull
     * Integer code, @NonNull String msg, @NonNull Object data) { return
     * BaseResult.builder().code(code).msg(msg).data(data).build(); }
     *//**
        * 失败
        */
    /*
     * public static BaseResult fail() { return
     * BaseResult.builder().code(FAIL).msg(OP_MSG_FAIL).build(); } public static BaseResult
     * fail(@NonNull String msg) { return BaseResult.builder().code(FAIL).msg(msg).build(); } public
     * static BaseResult fail(@NonNull Object data) { return
     * BaseResult.builder().code(FAIL).msg(OP_MSG_FAIL).data(data).build(); } public static BaseResult
     * fail(@NonNull String msg, Object data) { return
     * BaseResult.builder().code(FAIL).msg(msg).data(data).build(); } public static BaseResult
     * fail(@NonNull Integer code, @NonNull String msg) { return
     * BaseResult.builder().code(code).msg(msg).build(); } public static BaseResult fail(@NonNull
     * Integer code, @NonNull String msg, Object data) { return
     * BaseResult.builder().code(code).msg(msg).data(data).build(); }
     *//**
        * 异常
        */
    /*
     * public static BaseResult error() { return
     * BaseResult.builder().code(ERROR).msg(OP_MSG_FAIL).build(); } public static BaseResult
     * error(@NonNull String msg) { return BaseResult.builder().code(ERROR).msg(msg).build(); } public
     * static BaseResult error(@NonNull Object data) { return
     * BaseResult.builder().code(ERROR).msg(OP_MSG_FAIL).data(data).build(); } public static BaseResult
     * error(@NonNull String msg, Object data) { return
     * BaseResult.builder().code(ERROR).msg(msg).data(data).build(); } public static BaseResult
     * error(@NonNull Integer code, @NonNull String msg) { return
     * BaseResult.builder().code(code).msg(msg).build(); } public static BaseResult error(@NonNull
     * Integer code, @NonNull String msg, Object data) { return
     * BaseResult.builder().code(code).msg(msg).data(data).build(); }
     */
}
