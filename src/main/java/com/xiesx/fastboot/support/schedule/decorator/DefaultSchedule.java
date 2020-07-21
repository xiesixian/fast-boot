package com.xiesx.fastboot.support.schedule.decorator;

import com.xiesx.fastboot.support.schedule.impl.ISchedule;

import lombok.extern.slf4j.Slf4j;

/**
 * @title DefaultSchedule.java
 * @description 默认接口实现
 * @author Sixian.xie
 * @date 2020-7-21 22:43:06
 */
@Slf4j
public class DefaultSchedule implements ISchedule {

    @Override
    public void init() {
        log.debug("initialization." + isStart());
    }

    @Override
    public boolean isStart() {
        return false;
    }
}
