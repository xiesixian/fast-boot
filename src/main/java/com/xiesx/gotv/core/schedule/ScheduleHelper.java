package com.xiesx.gotv.core.schedule;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.xiesx.gotv.core.schedule.job.SimpleJobSchedule;

/**
 * @title SimpleSchedule.java
 * @description 定时任务工具类
 * @author Sixian.xie
 * @date 2019年3月4日 下午2:12:39
 */
@Slf4j
public class ScheduleHelper {

	private static SchedulerFactory gSchedulerFactory = new StdSchedulerFactory();

	private static String JOB_GROUP_NAME = "JOB_GROUP_NAME";

	private static String TRIGGER_GROUP_NAME = "TRIGGER_GROUP_NAME";

	/**
	 * 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名
	 * 
	 * @param jobName
	 *        任务名
	 * @param cls
	 *        任务
	 * @param cron
	 *        时间设置，参考quartz说明文档
	 * @throws SchedulerException
	 */
	public static void addJob(String jobName, Class<? extends Job> cls, String cron, JobDataMap map) throws SchedulerException {
		Scheduler sched = gSchedulerFactory.getScheduler();
		// 用于描叙Job实现类及其他的一些静态信息，构建一个作业实例
		JobDetail jobDetail = JobBuilder.newJob(cls).setJobData(map).withIdentity(jobName, JOB_GROUP_NAME).build();

		// 构建一个触发器，规定触发的规则
		Trigger trigger = TriggerBuilder.newTrigger()// 创建一个新的TriggerBuilder来规范一个触发器
				.withIdentity(jobName, TRIGGER_GROUP_NAME)
				// 给触发器起一个名字和组名
				.startNow()
				// 立即执行
				.withSchedule(CronScheduleBuilder.cronSchedule(cron))
				// 触发器的执行时间
				.build();// 产生触发器

		sched.scheduleJob(jobDetail, trigger);
		log.debug("添加任务:{},{},{}", jobName, cls, cron);
		// 启动
		if (!sched.isShutdown()) {
			sched.start();
		}
	}

	/**
	 * 添加一个定时任务
	 * 
	 * @param jobName
	 *        任务名
	 * @param jobGroupName
	 *        任务组名
	 * @param triggerName
	 *        触发器名
	 * @param triggerGroupName
	 *        触发器组名
	 * @param jobClass
	 *        任务
	 * @param cron
	 *        时间设置，参考quartz说明文档
	 */
	public static void addJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName, Class<? extends Job> cls, String cron, JobDataMap map) throws SchedulerException {

		Scheduler sched = gSchedulerFactory.getScheduler();
		// 用于描叙Job实现类及其他的一些静态信息，构建一个作业实例
		JobDetail jobDetail = JobBuilder.newJob(cls).setJobData(map).withIdentity(jobName, jobGroupName).build();

		// 构建一个触发器，规定触发的规则
		Trigger trigger = TriggerBuilder.newTrigger()// 创建一个新的TriggerBuilder来规范一个触发器
				.withIdentity(jobName, triggerGroupName)
				// 给触发器起一个名字和组名
				.startNow()
				// 立即执行
				.withSchedule(CronScheduleBuilder.cronSchedule(cron))
				// 触发器的执行时间
				.build();// 产生触发器

		sched.scheduleJob(jobDetail, trigger);
		log.debug("添加任务:{},{},{},{},{},{}", jobName, jobGroupName, triggerName, triggerGroupName, cls, cron);
		// 启动
		if (!sched.isShutdown()) {
			sched.start();
		}

	}

	/**
	 * 修改一个任务的触发时间(使用默认的任务组名，触发器名，触发器组名)
	 * 
	 * @param jobName
	 * @param cron
	 * @throws SchedulerException
	 */
	public static void modifyJobTime(String jobName, String cron) throws SchedulerException {
		Scheduler sched = gSchedulerFactory.getScheduler();
		TriggerKey triggerKey = new TriggerKey(jobName, TRIGGER_GROUP_NAME);
		CronTrigger trigger = (CronTrigger) sched.getTrigger(triggerKey);
		if (trigger == null) {
			return;
		}
		String oldTime = trigger.getCronExpression();
		if (!oldTime.equalsIgnoreCase(cron)) {
			JobDetail jobDetail = sched.getJobDetail(new JobKey(jobName, JOB_GROUP_NAME));
			Class<? extends Job> objJobClass = jobDetail.getJobClass();
			removeJob(jobName);
			addJob(jobName, objJobClass, cron, jobDetail.getJobDataMap());
			log.debug("修改任务:{},{}", jobName, cron);
		}
	}

	/**
	 * 移除一个任务(使用默认的任务组名，触发器名，触发器组名)
	 * 
	 * @param jobName
	 * @throws SchedulerException
	 */
	public static void removeJob(String jobName) throws SchedulerException {
		Scheduler sched = gSchedulerFactory.getScheduler();

		JobKey jobKey = new JobKey(jobName, TRIGGER_GROUP_NAME);
		// 停止触发器
		sched.pauseJob(jobKey);
		sched.unscheduleJob(new TriggerKey(jobName, TRIGGER_GROUP_NAME));// 移除触发器
		sched.deleteJob(jobKey);// 删除任务
		log.debug("移除任务:{}", jobName);
	}

	/**
	 * 移除任务
	 * 
	 * @param jobName
	 * @param jobGroupName
	 * @param triggerName
	 * @param triggerGroupName
	 * @throws SchedulerException
	 */
	public static void removeJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName) throws SchedulerException {
		Scheduler sched = gSchedulerFactory.getScheduler();
		JobKey jobKey = new JobKey(jobName, jobGroupName);
		// 停止触发器
		sched.pauseJob(jobKey);
		sched.unscheduleJob(new TriggerKey(jobName, triggerGroupName));// 移除触发器
		sched.deleteJob(jobKey);// 删除任务
		log.debug("移除任务:{},{},{},{},{},{}", jobName, jobGroupName, triggerName, triggerGroupName);
	}

	/**
	 * 获取所有任务名称
	 * 
	 * @return
	 * @throws SchedulerException
	 */
	public static List<Trigger> getJobsName() throws SchedulerException {
		List<Trigger> jobs = Lists.newArrayList();
		Scheduler scheduler = gSchedulerFactory.getScheduler();
		for (String groupName : scheduler.getJobGroupNames()) {
			for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
				jobs.addAll(scheduler.getTriggersOfJob(jobKey));
			}
		}
		log.debug("获取所有任务" + JSON.toJSONString(jobs, true));
		return jobs;
	}

	/**
	 * 启动所有任务
	 * 
	 * @throws SchedulerException
	 */
	public static void startJobs() throws SchedulerException {
		Scheduler sched = gSchedulerFactory.getScheduler();
		sched.start();
		log.debug("启动所有任务");
	}

	/**
	 * 关闭所有定时任务
	 * 
	 * @throws SchedulerException
	 */
	public static void shutdownJobs() throws SchedulerException {
		Scheduler sched = gSchedulerFactory.getScheduler();
		if (!sched.isShutdown()) {
			sched.shutdown();
			log.debug("关闭所有任务");
		}
	}

	public static void main(String[] args) {
		try {
			//
			JobDataMap map = new JobDataMap();
			map.put("type", "1");

			String job_name = "动态任务调度";
			System.out.println("【系统启动】开始(每1秒输出一次)...");
			ScheduleHelper.addJob(job_name, SimpleJobSchedule.class, "0/1 * * * * ?", map);
			Thread.sleep(5000);
			System.out.println("【修改时间】开始(每2秒输出一次)...");
			ScheduleHelper.modifyJobTime(job_name, "10/2 * * * * ?");

			Thread.sleep(6000);
			System.out.println("【移除定时】开始...");
			ScheduleHelper.removeJob(job_name);
			System.out.println("【移除定时】成功");

			System.out.println("【再次添加定时任务】开始(每10秒输出一次)...");
			ScheduleHelper.addJob(job_name, SimpleJobSchedule.class, "*/10 * * * * ?", map);
			Thread.sleep(6000);
			System.out.println("【移除定时】开始...");
			ScheduleHelper.removeJob(job_name);
			System.out.println("【移除定时】成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
