package com.xiesx.fastboot.support.scheduler.decorator;

/**
 * @title BaseDecorator.java
 * @description 装饰器父类
 * @author Sixian.xie
 * @date 2020-7-21 22:42:52
 */
public class BaseDecorator implements ISchedule {

    /**
     * 被装饰对象
     */
    protected ISchedule decoratedJob;

    /**
     * 构造
     */
    public BaseDecorator() {}

    /**
     * 构造
     *
     * @param decoratedJob
     */
    public BaseDecorator(ISchedule decoratedJob) {
        this.decoratedJob = decoratedJob;
    }

    /**
     * 初始化
     */
    @Override
    public void init() {
        decoratedJob.init();
    }

    /**
     * 是否启动
     */
    @Override
    public boolean isStart() {
        return false;
    }
}
