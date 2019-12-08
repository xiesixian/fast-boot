package com.xiesx.gotv.support.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @title ActException.java
 * @description
 * @author Sixian.xie
 * @date 2018年12月21日 下午6:15:34
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ActException extends Throwable {

	private static final long serialVersionUID = 1L;

	private Integer errorCode;

	public ActException(){
		super();
	}

	/**
	 * throw new ActException("出错啦！");
	 * 
	 * @param message
	 */
	public ActException(String message){
		super(message);
		this.errorCode = ActExc.ACT.getErrorCode();
	}

	/**
	 * throw new ActException(e);
	 * 
	 * @param message
	 */
	public ActException(Throwable e){
		super(e);
		this.errorCode = ActExc.ACT.getErrorCode();
	}

	/**
	 * throw new ActException(ActExc.BUSINESS);
	 * 
	 * @param message
	 */
	public ActException(ActExc act){
		super(act.getMassage());
		this.errorCode = act.getErrorCode();
	}

	/**
	 * throw new ActException(ActExc.BUSINESS,"活动业务处理失败----");
	 * 
	 * @param message
	 * @param message
	 */
	public ActException(ActExc act, String message){
		super(act.getMassage() + " : " + message);
		this.errorCode = act.getErrorCode();
	}

	/**
	 * throw new ActException(ActExc.BUSINESS,"{}活动业务处理失败----","暑期砍价");
	 * 
	 * @param message
	 * @param format
	 * @param message
	 */
	public ActException(ActExc act, String format, Object... message){
		super(act.getMassage() + " : " + String.format(format, message));
		this.errorCode = act.getErrorCode();
	}
}
