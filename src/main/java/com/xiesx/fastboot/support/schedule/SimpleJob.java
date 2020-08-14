package com.xiesx.fastboot.support.schedule;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.xiesx.fastboot.utils.DateUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @title SimpleJobSchedule.java
 * @description 默认定时任务
 * @author Sixian.xie
 * @date 2020-7-21 22:43:29
 */
@Slf4j
public class SimpleJob implements Job {

    public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static final String simple_job_name = "SimpleJob";

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap map = context.getMergedJobDataMap();
        log.info("simple job execute: " + map.getString("key") + DateUtils.format(new Date(), DATE_TIME_PATTERN));
    }
}
