package com.xiesx.fastboot.core.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @title RunException.java
 * @description 自定义异常
 * @author Sixian.xie
 * @date 2020-7-21 22:31:17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RunException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private Integer errorCode;

    public RunException() {
        super();
    }

    /**
     * throw new RunException("出错啦！");
     *
     * @param message
     */
    public RunException(String message) {
        super(message);
        this.errorCode = RunExc.RUNTIME.getErrorCode();
    }

    /**
     * throw new RunException(e);
     *
     * @param message
     */
    public RunException(Throwable e) {
        super(e);
        this.errorCode = RunExc.RUNTIME.getErrorCode();
    }

    /**
     * throw new RunException(RunExc.RUN);
     *
     * @param message
     */
    public RunException(RunExc act) {
        super(act.getMassage());
        this.errorCode = act.getErrorCode();
    }

    /**
     * throw new RunException(RunExc.RUN,"活动业务处理失败----");
     *
     * @param message
     * @param message
     */
    public RunException(RunExc act, String message) {
        super(act.getMassage() + ":" + message);
        this.errorCode = act.getErrorCode();
    }

    /**
     * throw new RunException(RunExc.RUN,"{}活动业务处理失败----","暑期砍价");
     *
     * @param message
     * @param format
     * @param message
     */
    public RunException(RunExc act, String format, Object... message) {
        super(act.getMassage() + ":" + String.format(format, message));
        this.errorCode = act.getErrorCode();
    }
}
