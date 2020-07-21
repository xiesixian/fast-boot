package com.xiesx.fastboot.support.schedule.job;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import lombok.extern.slf4j.Slf4j;

/**
 * @title SimpleJobSchedule.java
 * @description 默认定时任务
 * @author Sixian.xie
 * @date 2020-7-21 22:43:29
 */
@Slf4j
public class SimpleJobSchedule implements Job {

    public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static final String simple_job_key = "SimpleJob";

    public static final String simple_job_name = "SimpleJob";

    public static final String simple_job_cron = "0/30 * * * * ?";

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap map = context.getJobDetail().getJobDataMap();
        log.info("simple job execute: " + map.getString(simple_job_key) + format(new Date(), DATE_TIME_PATTERN));
    }

    public static String format(Date date, String pattern) {
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }
}
