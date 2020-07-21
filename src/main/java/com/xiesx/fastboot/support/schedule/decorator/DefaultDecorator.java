package com.xiesx.fastboot.support.schedule.decorator;

import org.quartz.JobDataMap;
import org.quartz.SchedulerException;

import com.xiesx.fastboot.support.schedule.ScheduleHelper;
import com.xiesx.fastboot.support.schedule.base.BaseDecorator;
import com.xiesx.fastboot.support.schedule.impl.ISchedule;
import com.xiesx.fastboot.support.schedule.job.SimpleJobSchedule;

import lombok.extern.slf4j.Slf4j;

/**
 * @title DefaultDecorator.java
 * @description DefaultDecorator.java
 * @author Sixian.xie
 * @date 2020-7-21 22:42:58
 */
@Slf4j
public class DefaultDecorator extends BaseDecorator implements ISchedule {

    public DefaultDecorator(ISchedule decoratedJob) {
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
