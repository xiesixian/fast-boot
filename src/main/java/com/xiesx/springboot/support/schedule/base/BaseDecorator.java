package com.xiesx.springboot.support.schedule.base;

import java.util.concurrent.Executors;

import com.google.common.util.concurrent.ListeningScheduledExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.xiesx.springboot.support.schedule.impl.ISchedule;

/**
 * @title BaseDecorator.java
 * @description 装饰器父类
 * @author Sixian.xie
 * @date 2019年3月4日 下午2:12:04
 */
public class BaseDecorator implements ISchedule {

	/**
	 * 调度型线程池 scheduleWithFixedDelay new timeTaskForException() 要执行的任务线程
	 * initialDelay：延迟多长时间执行 delay: 每隔多少长时间执行一次 TimeUnit.MILLISECONDS：时间单位
	 */
	protected static ListeningScheduledExecutorService scheduled = MoreExecutors
			.listeningDecorator(Executors.newScheduledThreadPool(10));

	/**
	 * 被装饰对象
	 */
	protected ISchedule decoratedJob;

	/**
	 * 构造
	 *
	 * @param decoratedJob
	 */
	public BaseDecorator(ISchedule decoratedJob) {
		this.decoratedJob = decoratedJob;
	}

	/**
	 * 初始化
	 */
	@Override
	public void init() {
		decoratedJob.init();
	}

	/**
	 * 是否启动
	 */
	@Override
	public boolean isStart() {
		return false;
	}
}
