package com.xiesx.fastboot.support.schedule;

import java.util.*;

import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;

import com.google.common.collect.Maps;
import com.xiesx.fastboot.SpringHelper;

public class ScheduleHelper {

    private static Scheduler scheduler = SpringHelper.getBean(Scheduler.class);

    private static String JOB_GROUP_NAME = "FAST_JOB_GROUP_NAME";

    // ============================

    /**
     * 增加
     *
     * @param jobClass 任务实现类
     * @param interval 时间表达式 (这是每隔多少秒为一次任务)
     * @param repeat 运行的次数 （<0:表示不限次数）
     * @param data 参数
     */
    public static void addJob(String jobName, Class<? extends Job> jobClass, int interval, int repeat,
            Map<? extends String, ? extends Object> data) {
        // 构建SimpleScheduleBuilder规则
        SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.repeatSecondlyForever(1);
        if (repeat > 0) {
            // 几秒钟重复执行
            simpleScheduleBuilder.withIntervalInSeconds(interval);
            // 重复次数
            simpleScheduleBuilder.withRepeatCount(repeat);
        }
        // 创建
        createJob(jobName, jobClass, simpleScheduleBuilder, data);
    }

    /**
     * 增加
     *
     * @param jobClass 任务实现类
     * @param cron 时间表达式 （如：0/5 * * * * ? ）
     * @param data 参数
     */
    public static void addJob(String jobName, Class<? extends Job> jobClass, String cron, Map<? extends String, ? extends Object> data) {
        // 构建CronScheduleBuilder规则
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
        // 创建
        createJob(jobName, jobClass, cronScheduleBuilder, data);
    }

    /**
     * 增加
     *
     * @param jobName 任务名称
     * @param jobGroupName 任务组名
     * @param jobClass 任务实现类
     * @param interval 时间表达式 (这是每隔多少秒为一次任务)
     * @param repeat 运行的次数 （<0:表示不限次数）
     * @param data 参数
     */
    public static void addJob(String jobName, String jobGroupName, Class<? extends Job> jobClass, int interval, int repeat,
            Map<? extends String, ? extends Object> data) {
        // 构建SimpleScheduleBuilder规则
        SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.repeatSecondlyForever(1);
        if (repeat > 0) {
            // 几秒钟重复执行
            simpleScheduleBuilder.withIntervalInSeconds(interval);
            // 重复次数
            simpleScheduleBuilder.withRepeatCount(repeat);
        }
        // 创建
        createJob(jobName, jobGroupName, jobClass, simpleScheduleBuilder, data);
    }

    /**
     * 增加
     *
     * @param jobName 任务名称
     * @param jobGroupName 任务组名
     * @param jobClass 任务实现类
     * @param cron 时间表达式 （如：0/5 * * * * ? ）
     * @param data 参数
     */
    public static void addJob(String jobName, String jobGroupName, Class<? extends Job> jobClass, String cron,
            Map<? extends String, ? extends Object> data) {
        // 构建CronScheduleBuilder规则
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
        // 创建
        createJob(jobName, jobGroupName, jobClass, cronScheduleBuilder, data);
    }


    // ============================

    /**
     * 创建
     *
     * @param jobClass 任务实现类
     * @param ScheduleBuilder 时间表达式 (这是每隔多少秒为一次任务)
     * @param data 参数
     */
    public static void createJob(String jobName, Class<? extends Job> jobClass, ScheduleBuilder<? extends Trigger> ScheduleBuilder,
            Map<? extends String, ? extends Object> data) {
        createJob(jobName, JOB_GROUP_NAME, jobClass, ScheduleBuilder, data);
    }

    /**
     * 创建
     *
     * @param jobName 任务名称
     * @param jobGroupName 任务组名
     * @param jobClass 任务实现类
     * @param interval 时间表达式 (这是每隔多少秒为一次任务)
     * @param repeat 运行的次数 （<0:表示不限次数）
     * @param data 参数
     */
    public static void createJob(String jobName, String jobGroupName, Class<? extends Job> jobClass,
            ScheduleBuilder<? extends Trigger> ScheduleBuilder, Map<? extends String, ? extends Object> data) {
        try {
            // 构建实例
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).build();
            // 设置参数
            if (data != null && data.size() > 0) {
                jobDetail.getJobDataMap().putAll(data);
            }
            // 构建触发器
            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger().withIdentity(jobName, jobGroupName);
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
    public static void updateJob(String jobName, String cron) {
        updateJob(jobName, JOB_GROUP_NAME, cron);
    }

    /**
     * 修改
     *
     * @param jobName
     * @param jobGroupName
     * @param cron
     */
    public static void updateJob(String jobName, String jobGroupName, String cron) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroupName);
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
    public static void deleteJob(String jobName) {
        deleteJob(jobName, JOB_GROUP_NAME);
    }

    /**
     * 删除
     *
     * @param jobName 任务名称
     * @param jobGroupName 任务组名
     */
    public static void deleteJob(String jobName, String jobGroupName) {
        try {
            scheduler.deleteJob(new JobKey(jobName, jobGroupName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ============================

    /**
     * 暂停
     */
    public static void pauseJob(String jobName) {
        pauseJob(jobName, JOB_GROUP_NAME);
    }

    /**
     * 暂停
     *
     * @param jobName
     * @param jobGroupName
     */
    public static void pauseJob(String jobName, String jobGroupName) {
        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    // ============================

    /**
     * 恢复
     */
    public static void resumeJob(String jobName) {
        resumeJob(jobName, JOB_GROUP_NAME);
    }

    /**
     * 恢复
     * 
     * @param jobName
     * @param jobGroupName
     */
    public static void resumeJob(String jobName, String jobGroupName) {
        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
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
    public static void runAJobNow(String jobName) {
        resumeJob(jobName, JOB_GROUP_NAME);
    }

    /**
     * 立即执行
     *
     * @param jobName
     * @param jobGroupName
     */
    public static void runAJobNow(String jobName, String jobGroupName) {
        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
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
        List<Map<String, Object>> jobList = null;
        try {
            GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
            Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
            jobList = new ArrayList<Map<String, Object>>();
            for (JobKey jobKey : jobKeys) {
                List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                for (Trigger trigger : triggers) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("jobName", jobKey.getName());
                    map.put("jobGroupName", jobKey.getGroup());
                    map.put("description", "触发器:" + trigger.getKey());
                    Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                    map.put("jobStatus", triggerState.name());
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
        List<Map<String, Object>> jobList = null;
        try {
            List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
            jobList = new ArrayList<Map<String, Object>>(executingJobs.size());
            for (JobExecutionContext executingJob : executingJobs) {
                Map<String, Object> map = new HashMap<String, Object>();
                JobDetail jobDetail = executingJob.getJobDetail();
                JobKey jobKey = jobDetail.getKey();
                Trigger trigger = executingJob.getTrigger();
                map.put("jobName", jobKey.getName());
                map.put("jobGroupName", jobKey.getGroup());
                map.put("description", "触发器:" + trigger.getKey());
                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                map.put("jobStatus", triggerState.name());
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

    // ============================

    public static void main(String[] args) {
        try {
            Map<String, String> map = Maps.newHashMap();
            map.put("type", "1");

            String job_name = "动态任务调度";
            System.out.println("【系统启动】开始(每1秒输出一次)...");
            ScheduleHelper.addJob(SimpleJob.simple_job_name, SimpleJob.class, "0/1 * * * * ?", map);
            System.out.println("【修改时间】开始(每2秒输出一次)...");
            ScheduleHelper.updateJob(SimpleJob.simple_job_name, "0/2 * * * * ?");
            Thread.sleep(5000);
            System.out.println("【移除定时】开始...");
            ScheduleHelper.deleteJob(job_name);
            System.out.println("【移除定时】成功");

            System.out.println("【再次添加定时任务】开始(每5秒输出一次)...");
            ScheduleHelper.addJob(SimpleJob.simple_job_name, SimpleJob.class, "*/5 * * * * ?", map);
            Thread.sleep(11000);
            System.out.println("【移除定时】开始...");
            ScheduleHelper.deleteJob(job_name);
            System.out.println("【移除定时】成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
