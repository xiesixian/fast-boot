package com.xiesx.gotv.support.schedule.job;

import java.util.Date;

import lombok.extern.slf4j.Slf4j;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.xiesx.gotv.utils.DateUtils;

/**
 * @title SimpleJob.java
 * @description 默认定时任务
 * @author Sixian.xie
 * @date 2019年1月2日 下午3:05:47
 */
@Slf4j
public class SimpleJobSchedule implements Job {

	public static final String simple_job_key = "SimpleJob";

	public static final String simple_job_name = "SimpleJob";

	public static final String simple_job_cron = "0/10 * * * * ?";

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap map = context.getJobDetail().getJobDataMap();
		log.info("simple job execute: " + map.getString(simple_job_key) + DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));
	}
}
