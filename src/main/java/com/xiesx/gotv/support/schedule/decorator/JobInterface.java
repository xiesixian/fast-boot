package com.xiesx.gotv.support.schedule.decorator;

/**
 * @title SimpleInterface.java
 * @description 装饰起接口
 * @author Sixian.xie
 * @date 2019年3月4日 下午1:07:35
 */
public interface JobInterface {

	/**
	 * 初始化
	 */
	public void init();

	/**
	 * 是否启动
	 */
	public boolean isStart();
}
