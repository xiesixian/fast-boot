package com.xiesx.fastboot.support.scheduler;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import cn.hutool.core.date.DateUtil;
import lombok.extern.log4j.Log4j2;

/**
 * @title SimpleJobSchedule.java
 * @description 默认定时任务
 * @author Sixian.xie
 * @date 2020-7-21 22:43:29
 */
@Log4j2
public class SimpleJob implements Job {

    public static final String simple_job_name = "SimpleJob";

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap map = context.getMergedJobDataMap();
        log.info("simple job {} {}，当前任务{}个，正在运行 {}", map.getString("key"), DateUtil.now(), ScheduleHelper.queryAllJob().size(),
                ScheduleHelper.queryRunJob().size());
    }
}
