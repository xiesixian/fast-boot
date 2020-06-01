package com.xiesx.fastboot.support.schedule.decorator;

import com.xiesx.fastboot.support.schedule.impl.ISchedule;

import lombok.extern.slf4j.Slf4j;

/**
 * @title JobDefault.java
 * @description 默认接口实现
 * @author Sixian.xie
 * @date 2019年3月4日 下午1:55:52
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
