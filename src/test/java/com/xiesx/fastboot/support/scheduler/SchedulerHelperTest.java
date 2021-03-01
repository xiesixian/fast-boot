package com.xiesx.fastboot.support.scheduler;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.Maps;
import com.xiesx.fastboot.FastBootApplication;

import lombok.extern.log4j.Log4j2;

/**
 * @title SimpleTest.java
 * @description
 * @author Sixian.xie
 * @date 2018年12月21日 下午6:16:35
 */
@Log4j2
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = FastBootApplication.class)
public class SchedulerHelperTest {

    String job = "job_test";

    @Test
    public void scheduler_simple() throws InterruptedException {

        Map<String, String> map = Maps.newHashMap();
        map.put("key", "time");

        log.info("【添加A】每1秒输出一次 ");
        ScheduleHelper.addJob(job, SimpleJob.class, 1, 0, map);
        Thread.sleep(6000);

        log.info("【修改A】每2秒输出一次");
        ScheduleHelper.updateJob(job, 2, 0);
        Thread.sleep(6000);

        log.info("【移除A】");
        ScheduleHelper.deleteJob(job);
        Thread.sleep(6000);

        log.info("【添加B】每3秒输出一次");
        ScheduleHelper.addJob(job, SimpleJob.class, 3, 0, map);
        Thread.sleep(6000);

        log.info("【暂停B】");
        ScheduleHelper.pauseJob(job);
        Thread.sleep(6000);

        log.info("【恢复B】");
        ScheduleHelper.resumeJob(job);
        Thread.sleep(6000);

        log.info("【移除B】.");
        ScheduleHelper.deleteJob(job);
        Thread.sleep(6000);
    }

    @Test
    public void scheduler_cron() throws InterruptedException {

        Map<String, String> map = Maps.newHashMap();
        map.put("key", "time");

        log.info("【添加A】每1秒输出一次 ");
        ScheduleHelper.addJob(job, SimpleJob.class, "0/1 * * * * ?", map);
        Thread.sleep(6000);

        log.info("【修改A】每2秒输出一次");
        ScheduleHelper.updateJob(job, "0/2 * * * * ?");
        Thread.sleep(6000);

        log.info("【移除A】");
        ScheduleHelper.deleteJob(job);
        Thread.sleep(6000);

        log.info("【添加B】每3秒输出一次");
        ScheduleHelper.addJob(job, SimpleJob.class, "*/3 * * * * ?", map);
        Thread.sleep(6000);

        log.info("【暂停B】");
        ScheduleHelper.pauseJob(job);
        Thread.sleep(6000);

        log.info("【恢复B】");
        ScheduleHelper.resumeJob(job);
        Thread.sleep(6000);

        log.info("【移除B】");
        ScheduleHelper.deleteJob(job);
        Thread.sleep(6000);
    }

    @Test
    public void scheduler() throws InterruptedException {
        ScheduleHelper.startJobs();
        Thread.sleep(60 * 1000);
        ScheduleHelper.shutdownJobs();
    }
}
