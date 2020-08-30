package com.xiesx.fastboot.base.result;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Builder;
import lombok.Data;

/**
 * @title BaseResult.java
 * @description 结果基类
 * @author Sixian.xie
 * @date 2020-7-21 22:29:55
 */
@Data
@Builder
public class BaseResult {

    public static String OP_MSG_SUCC = "操作成功";

    public static String OP_MSG_FAIL = "操作失败";

    public static String OP_MSG_FAI_TETRY = "重试失败";

    // 返回值
    public static Integer SUCCESS = 0; // 成功

    public static Integer FAIL = -1;// 失败

    public static Integer ERROR = -2;// 异常

    public static Integer RETRY = -3;// 重试

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
}
