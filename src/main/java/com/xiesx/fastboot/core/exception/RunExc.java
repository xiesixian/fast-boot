package com.xiesx.fastboot.core.exception;

import lombok.AllArgsConstructor;

/**
 * @title RunExc.java
 * @description 异常枚举处理类
 * @author Sixian.xie
 * @date 2020-7-21 22:30:51
 */
@AllArgsConstructor
public enum RunExc {

    RUNTIME(1000, "运行错误"), // --> BaseRestExceptionAdvice --> runtimeException

    REQUEST(2000, "请求失败"), // --> BaseRestExceptionAdvice --> requestException

    VALIDATOR(3000, "校验错误"), // --> BaseRestExceptionAdvice --> validatorException

    DBASE(4000, "数据错误"), // --> BaseRestExceptionAdvice --> jdbcException

    TOKEN(5000, "令牌错误"), // --> TokenInterceptorHandler

    SIGN(6000, "签名错误"), // --> SignAspect

    RETRY(7000, "重试失败"), // --> HttpRetryer

    LIMITER(8000, "请求限流"), // --> LimiterAspect

    UNKNOWN(9999, "未知");

    private Integer errorCode;

    private String errorMsg;

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
