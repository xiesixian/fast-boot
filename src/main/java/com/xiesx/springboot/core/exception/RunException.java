package com.xiesx.springboot.core.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @title RunException.java
 * @description
 * @author Sixian.xie
 * @date 2018年12月21日 下午6:15:34
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RunException extends Throwable {

	private static final long serialVersionUID = 1L;

	private Integer errorCode;

	public RunException(){
		super();
	}

	/**
	 * throw new RunException("出错啦！");
	 * 
	 * @param message
	 */
	public RunException(String message){
		super(message);
		this.errorCode = RunExc.RUN.getErrorCode();
	}

	/**
	 * throw new RunException(e);
	 * 
	 * @param message
	 */
	public RunException(Throwable e){
		super(e);
		this.errorCode = RunExc.RUN.getErrorCode();
	}

	/**
	 * throw new RunException(ActExc.BUSINESS);
	 * 
	 * @param message
	 */
	public RunException(RunExc act){
		super(act.getMassage());
		this.errorCode = act.getErrorCode();
	}

	/**
	 * throw new RunException(ActExc.BUSINESS,"活动业务处理失败----");
	 * 
	 * @param message
	 * @param message
	 */
	public RunException(RunExc act, String message){
		super(act.getMassage() + " : " + message);
		this.errorCode = act.getErrorCode();
	}

	/**
	 * throw new RunException(ActExc.BUSINESS,"{}活动业务处理失败----","暑期砍价");
	 * 
	 * @param message
	 * @param format
	 * @param message
	 */
	public RunException(RunExc act, String format, Object... message){
		super(act.getMassage() + " : " + String.format(format, message));
		this.errorCode = act.getErrorCode();
	}
}
