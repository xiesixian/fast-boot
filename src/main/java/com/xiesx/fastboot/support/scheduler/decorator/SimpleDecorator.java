package com.xiesx.fastboot.support.scheduler.decorator;

import java.util.Map;

import com.google.common.collect.Maps;
import com.xiesx.fastboot.support.scheduler.ScheduleHelper;
import com.xiesx.fastboot.support.scheduler.SimpleJob;

/**
 * @title DefaultDecorator.java
 * @description DefaultDecorator.java
 * @author Sixian.xie
 * @date 2020-7-21 22:42:58
 */
public class SimpleDecorator extends BaseDecorator implements ISchedule {

    public SimpleDecorator() {
        super();
    }

    public SimpleDecorator(ISchedule decoratedJob) {
        super(decoratedJob);
    }

    @Override
    public void init() {
        if (isStart()) {
            Map<String, String> map = Maps.newHashMap();
            map.put("key", "time ");

            ScheduleHelper.addJob(SimpleJob.simple_job_name, SimpleJob.class, "0/10 * * * * ?", map);
        }
    }

    @Override
    public boolean isStart() {
        return false;
    }
}
