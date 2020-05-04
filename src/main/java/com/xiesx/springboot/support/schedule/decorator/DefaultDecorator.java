package com.xiesx.springboot.support.schedule.decorator;

import lombok.extern.slf4j.Slf4j;

import org.quartz.JobDataMap;
import org.quartz.SchedulerException;

import com.xiesx.springboot.support.schedule.ScheduleHelper;
import com.xiesx.springboot.support.schedule.base.BaseDecorator;
import com.xiesx.springboot.support.schedule.impl.ISchedule;
import com.xiesx.springboot.support.schedule.job.SimpleJobSchedule;

/**
 * @title DefaultDecorator.java
 * @description 默认装饰器实现
 * @author Sixian.xie
 * @date 2019年3月4日 下午2:08:10
 */
@Slf4j
public class DefaultDecorator extends BaseDecorator implements ISchedule {

	public DefaultDecorator(ISchedule decoratedJob){
		super(decoratedJob);
	}

	@Override
	public void init() {
		decoratedJob.init();
		if (isStart()) {
			JobDataMap map = new JobDataMap();
			map.put(SimpleJobSchedule.simple_job_key, "time is ");
			try {
				ScheduleHelper.removeJob(SimpleJobSchedule.simple_job_name);
				ScheduleHelper.addJob(SimpleJobSchedule.simple_job_name, SimpleJobSchedule.class, SimpleJobSchedule.simple_job_cron, map);
			} catch (SchedulerException e) {
				log.error("DefaultDecorator error", e);
			}
		}
	}

	@Override
	public boolean isStart() {
		return true;
	}
}
