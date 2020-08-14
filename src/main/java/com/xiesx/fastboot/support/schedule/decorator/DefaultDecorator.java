package com.xiesx.fastboot.support.schedule.decorator;

import java.util.Map;

import com.google.common.collect.Maps;
import com.xiesx.fastboot.support.schedule.ScheduleHelper;
import com.xiesx.fastboot.support.schedule.SimpleJob;
import com.xiesx.fastboot.support.schedule.base.BaseDecorator;
import com.xiesx.fastboot.support.schedule.impl.ISchedule;

/**
 * @title DefaultDecorator.java
 * @description DefaultDecorator.java
 * @author Sixian.xie
 * @date 2020-7-21 22:42:58
 */
public class DefaultDecorator extends BaseDecorator implements ISchedule {

    public DefaultDecorator() {
        super();
    }

    public DefaultDecorator(ISchedule decoratedJob) {
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
        return true;
    }
}
