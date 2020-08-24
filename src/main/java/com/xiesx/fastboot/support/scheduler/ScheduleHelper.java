package com.xiesx.fastboot.support.scheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.ObjectUtils;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiesx.fastboot.SpringHelper;
import com.xiesx.fastboot.core.exception.RunExc;
import com.xiesx.fastboot.core.exception.RunException;

public class ScheduleHelper {

    private static Scheduler scheduler = SpringHelper.getBean(Scheduler.class);

    private static String JOB_GROUP_NAME = "FAST_JOB_GROUP_NAME";

    // ============================

    /**
     * 增加SimpleJob
     *
     * @param job 任务名称
     * @param cls 任务实现类
     * @param interval 时间表达式 (这是每隔多少秒为一次任务)
     * @param repeat 运行的次数 （<0:表示不限次数）
     */
    public static void addJob(String job, Class<? extends Job> cls, int interval, int repeat) {
        // 创建
        addJob(job, cls, interval, repeat, null);
    }

    /**
     * 增加SimpleJob
     *
     * @param job 任务名称
     * @param cls 任务实现类
     * @param interval 时间表达式 (这是每隔多少秒为一次任务)
     * @param repeat 运行的次数 （<0:表示不限次数）
     * @param data 参数
     */
    public static void addJob(String job, Class<? extends Job> cls, int interval, int repeat,
            Map<? extends String, ? extends Object> data) {
        // 构建SimpleScheduleBuilder规则
        SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule() // 几秒钟重复执行
                .withIntervalInSeconds(interval);
        if (repeat > 0) {
            // 重复次数
            simpleScheduleBuilder.withRepeatCount(repeat);
        }
        // 一直执行
        simpleScheduleBuilder.repeatForever();
        // 创建
        createJob(job, cls, simpleScheduleBuilder, data);
    }

    /**
     * 增加SimpleJob
     *
     * @param job 任务名称
     * @param group 任务组名
     * @param cls 任务实现类
     * @param interval 时间表达式 (这是每隔多少秒为一次任务)
     * @param repeat 运行的次数 （<0:表示不限次数）
     * @param data 参数
     */
    public static void addJob(String job, String group, Class<? extends Job> cls, int interval, int repeat,
            Map<? extends String, ? extends Object> data) {
        // 构建SimpleScheduleBuilder规则
        SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                // 几秒钟重复执行
                .withIntervalInSeconds(interval);
        if (repeat > 0) {
            // 重复次数
            simpleScheduleBuilder.withRepeatCount(repeat);
        }
        // 一直执行
        simpleScheduleBuilder.repeatForever();
        // 创建
        createJob(job, group, cls, simpleScheduleBuilder, data);
    }


    /**
     * 增加CronJob
     *
     * @param job 任务名称
     * @param cls 任务实现类
     * @param cron 时间表达式 （如：0/5 * * * * ? ）
     */
    public static void addJob(String job, Class<? extends Job> cls, String cron) {
        addJob(job, cls, cron, null);
    }

    /**
     * 增加CronJob
     *
     * @param job 任务名称
     * @param cls 任务实现类
     * @param cron 时间表达式 （如：0/5 * * * * ? ）
     * @param data 参数
     */
    public static void addJob(String job, Class<? extends Job> cls, String cron, Map<? extends String, ? extends Object> data) {
        // 构建CronScheduleBuilder规则
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
        // 创建
        createJob(job, cls, cronScheduleBuilder, data);
    }

    /**
     * 增加CronJob
     *
     * @param job 任务名称
     * @param group 任务组名
     * @param cls 任务实现类
     * @param cron 时间表达式 （如：0/5 * * * * ? ）
     * @param data 参数
     */
    public static void addJob(String job, String group, Class<? extends Job> cls, String cron,
            Map<? extends String, ? extends Object> data) {
        // 构建CronScheduleBuilder规则
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
        // 创建
        createJob(job, group, cls, cronScheduleBuilder, data);
    }


    // ============================

    /**
     * 创建
     *
     * @param cls 任务实现类
     * @param ScheduleBuilder 时间表达式 (这是每隔多少秒为一次任务)
     * @param data 参数
     */
    public static void createJob(String job, Class<? extends Job> cls, ScheduleBuilder<? extends Trigger> ScheduleBuilder,
            Map<? extends String, ? extends Object> data) {
        createJob(job, JOB_GROUP_NAME, cls, ScheduleBuilder, data);
    }

    /**
     * 创建
     *
     * @param job 任务名称
     * @param group 任务组名
     * @param cls 任务实现类
     * @param interval 时间表达式 (这是每隔多少秒为一次任务)
     * @param repeat 运行的次数 （<0:表示不限次数）
     * @param data 参数
     */
    public static void createJob(String job, String group, Class<? extends Job> cls, ScheduleBuilder<? extends Trigger> ScheduleBuilder,
            Map<? extends String, ? extends Object> data) {
        try {
            // 构建实例
            JobDetail jobDetail = JobBuilder.newJob(cls).withIdentity(job, group).build();
            // 设置参数
            if (data != null && data.size() > 0) {
                jobDetail.getJobDataMap().putAll(data);
            }
            // 构建触发器
            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger().withIdentity(job, group);
            triggerBuilder.withSchedule(ScheduleBuilder);
            triggerBuilder.startNow();
            scheduler.scheduleJob(jobDetail, triggerBuilder.build());
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    // ============================

    /**
     * 修改
     *
     * @param cron
     */
    public static void updateJob(String job, int interval, int repeat) {
        updateJob(job, JOB_GROUP_NAME, interval, repeat);
    }

    /**
     * 修改
     *
     * @param cron
     */
    public static void updateJob(String job, String cron) {
        updateJob(job, JOB_GROUP_NAME, cron);
    }

    /**
     * 修改
     *
     * @param job
     * @param group
     * @param cron
     */
    public static void updateJob(String job, String group, int interval, int repeat) {
        if (ObjectUtils.isEmpty(scheduler)) {
            throw new RunException(RunExc.RUNTIME, "please check pom.xml -> spring-boot-starter-quartz");
        }
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(job, group);
            SimpleTrigger trigger = (SimpleTrigger) scheduler.getTrigger(triggerKey);
            //
            SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule() // 几秒钟重复执行
                    .withIntervalInSeconds(interval);
            if (repeat > 0) {
                // 重复次数
                simpleScheduleBuilder.withRepeatCount(repeat);
            }
            // 一直执行
            simpleScheduleBuilder.repeatForever();
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(simpleScheduleBuilder).build();
            // 重启触发器
            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改
     *
     * @param job
     * @param group
     * @param cron
     */
    public static void updateJob(String job, String group, String cron) {
        if (ObjectUtils.isEmpty(scheduler)) {
            throw new RunException(RunExc.RUNTIME, "please check pom.xml -> spring-boot-starter-quartz");
        }
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(job, group);
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            //
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(CronScheduleBuilder.cronSchedule(cron)).build();
            // 重启触发器
            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    // ============================

    /**
     * 删除
     */
    public static void deleteJob(String job) {
        deleteJob(job, JOB_GROUP_NAME);
    }

    /**
     * 删除
     *
     * @param job 任务名称
     * @param group 任务组名
     */
    public static void deleteJob(String job, String group) {
        if (ObjectUtils.isEmpty(scheduler)) {
            throw new RunException(RunExc.RUNTIME, "please check pom.xml -> spring-boot-starter-quartz");
        }
        try {
            scheduler.deleteJob(new JobKey(job, group));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ============================

    /**
     * 暂停
     */
    public static void pauseJob(String job) {
        pauseJob(job, JOB_GROUP_NAME);
    }

    /**
     * 暂停
     *
     * @param job
     * @param group
     */
    public static void pauseJob(String job, String group) {
        if (ObjectUtils.isEmpty(scheduler)) {
            throw new RunException(RunExc.RUNTIME, "please check pom.xml -> spring-boot-starter-quartz");
        }
        try {
            JobKey jobKey = JobKey.jobKey(job, group);
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    // ============================

    /**
     * 恢复
     */
    public static void resumeJob(String job) {
        resumeJob(job, JOB_GROUP_NAME);
    }

    /**
     * 恢复
     *
     * @param job
     * @param group
     */
    public static void resumeJob(String job, String group) {
        if (ObjectUtils.isEmpty(scheduler)) {
            throw new RunException(RunExc.RUNTIME, "please check pom.xml -> spring-boot-starter-quartz");
        }
        try {
            JobKey jobKey = JobKey.jobKey(job, group);
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    // ============================

    /**
     * 立即执行
     *
     */
    public static void runAJobNow(String job) {
        resumeJob(job, JOB_GROUP_NAME);
    }

    /**
     * 立即执行
     *
     * @param job
     * @param group
     */
    public static void runAJobNow(String job, String group) {
        if (ObjectUtils.isEmpty(scheduler)) {
            throw new RunException(RunExc.RUNTIME, "please check pom.xml -> spring-boot-starter-quartz");
        }
        try {
            JobKey jobKey = JobKey.jobKey(job, group);
            scheduler.triggerJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    // ============================

    /**
     * 启动任务
     *
     * @throws SchedulerException
     */
    public static void startJobs() {
        if (ObjectUtils.isEmpty(scheduler)) {
            throw new RunException(RunExc.RUNTIME, "please check pom.xml -> spring-boot-starter-quartz");
        }
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭任务
     *
     * @throws SchedulerException
     */
    public static void shutdownJobs() {
        if (ObjectUtils.isEmpty(scheduler)) {
            throw new RunException(RunExc.RUNTIME, "please check pom.xml -> spring-boot-starter-quartz");
        }
        try {
            if (!scheduler.isShutdown()) {
                scheduler.shutdown();
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取所有计划中的任务列表
     *
     * @return
     */
    public static List<Map<String, Object>> queryAllJob() {
        if (ObjectUtils.isEmpty(scheduler)) {
            throw new RunException(RunExc.RUNTIME, "please check pom.xml -> spring-boot-starter-quartz");
        }
        List<Map<String, Object>> jobList = null;
        try {
            GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
            Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
            jobList = Lists.newArrayList();
            for (JobKey jobKey : jobKeys) {
                List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                for (Trigger trigger : triggers) {
                    Map<String, Object> map = Maps.newHashMap();
                    map.put("key", trigger.getKey());
                    map.put("job", jobKey.getName());
                    map.put("group", jobKey.getGroup());
                    Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                    map.put("status", triggerState.name());
                    if (trigger instanceof CronTrigger) {
                        CronTrigger cronTrigger = (CronTrigger) trigger;
                        String cronExpression = cronTrigger.getCronExpression();
                        map.put("cron", cronExpression);
                    }
                    jobList.add(map);
                }
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return jobList;
    }

    /**
     * 获取所有正在运行的job
     *
     * @return
     */
    public static List<Map<String, Object>> queryRunJob() {
        if (ObjectUtils.isEmpty(scheduler)) {
            throw new RunException(RunExc.RUNTIME, "please check pom.xml -> spring-boot-starter-quartz");
        }
        List<Map<String, Object>> jobList = null;
        try {
            List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
            jobList = new ArrayList<>(executingJobs.size());
            for (JobExecutionContext executingJob : executingJobs) {
                Map<String, Object> map = Maps.newHashMap();
                JobDetail jobDetail = executingJob.getJobDetail();
                JobKey jobKey = jobDetail.getKey();
                Trigger trigger = executingJob.getTrigger();
                map.put("key", trigger.getKey());
                map.put("job", jobKey.getName());
                map.put("group", jobKey.getGroup());
                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                map.put("status", triggerState.name());
                if (trigger instanceof CronTrigger) {
                    CronTrigger cronTrigger = (CronTrigger) trigger;
                    String cronExpression = cronTrigger.getCronExpression();
                    map.put("cron", cronExpression);
                }
                jobList.add(map);
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return jobList;
    }
}
