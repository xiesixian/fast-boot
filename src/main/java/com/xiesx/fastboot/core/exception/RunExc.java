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

    REQUEST(2000, "请求错误"), // --> BaseRestExceptionAdvice --> requestException

    VALI(3000, "效验错误"), // --> BaseRestExceptionAdvice --> validatorException

    JDBC(4000, "数据错误"), // --> BaseRestExceptionAdvice --> jdbcException

    TOKEN(5000, "令牌错误"), // --> TokenInterceptorHandler

    SIGNA(6000, "签名错误"), // --> SignAspect

    RETRY(7000, "重试错误"), // --> BaseResultRetryListener

    LIMITER(8000, "请求限流"); // --> BaseResultRetryListener

    private Integer errorCode;

    private String massage;

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }
}
