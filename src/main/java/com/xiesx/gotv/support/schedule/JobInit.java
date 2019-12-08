package com.xiesx.gotv.support.schedule;

import lombok.extern.slf4j.Slf4j;

import com.xiesx.gotv.GotvStartup;
import com.xiesx.gotv.support.schedule.decorator.DefaultDecorator;
import com.xiesx.gotv.support.schedule.decorator.DefaultJob;
import com.xiesx.gotv.support.schedule.decorator.JobInterface;

/**
 * @title JobInit.java
 * @description 全局定时任务启动项
 * @author Sixian.xie
 * @date 2019年3月4日 下午1:42:27
 */
@Slf4j
public class JobInit {

	/**
	 * 初始
	 */
	public static void initialization() {
		try {
			log.info("服务器: 【{}】", GotvStartup.gtgjxname.trim());
			log.info("定时任务启动项-start");
			// 默认实现
			JobInterface job = new DefaultJob();
			// 默认装饰追加
			JobInterface job2 = new DefaultDecorator(job);
			// 开始初始化....
			job2.init();
			log.info("定时任务启动项:-end-:{}", ScheduleHelper.getJobsName().size());
		} catch (Exception e) {
			log.info("定时任务启动项-error", e);
		}
	}

	public static void main(String[] args) {
		initialization();
	}
}
