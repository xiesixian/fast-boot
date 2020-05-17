package com.xiesx.springboot.core.context;

import java.net.URL;
import java.util.Map;

import com.google.common.collect.Maps;
import com.xiesx.springboot.support.event.EventBusHelper;
import com.xiesx.springboot.support.event.base.AbstractEventBus;
import com.xiesx.springboot.support.schedule.ScheduleHelper;
import com.xiesx.springboot.support.schedule.decorator.DefaultDecorator;
import com.xiesx.springboot.support.schedule.decorator.DefaultSchedule;
import com.xiesx.springboot.support.schedule.impl.ISchedule;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SpringStartup {

    @SuppressWarnings("rawtypes")
    public static Map<String, AbstractEventBus> beans = Maps.newConcurrentMap();

    public static String classUrl;

    /** 由于是分布式环境，该名字为每个tomcat的目录名（在部署时必须唯一） */
    public static String servername;

    public static String serverpath;

    public static void init() {
        try {
            URL url = SpringStartup.class.getResource("/");
            classUrl = url.getPath();
            log.info("Startup classpath url " + classUrl);
            // /home/gtgj/tomcat-loco/webapps/trainnet/WEB-INF/classes/
            int index = classUrl.indexOf("/webapps/");
            if (index > 0) {
                // /home/gtgj/tomcat-loco
                String path = classUrl.substring(0, index);
                index = path.lastIndexOf("/");
                // tomcat-loco1
                servername = path.substring(index + 1);
                serverpath = classUrl.split(servername)[0] + servername;
            } else {
                servername = classUrl.substring(classUrl.length() - 20);
                serverpath = classUrl;
            }
            log.info("Startup tomcat-name " + servername + ", path " + serverpath);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    public static void event() {
        beans = SpringApplicationContextAware.getApplicationContext().getBeansOfType(AbstractEventBus.class);
        if (beans != null) {
            for (AbstractEventBus<?> eventAbstract : beans.values()) {
                EventBusHelper.register(eventAbstract);
            }
        }
        log.info("Startup EventBus {} register completed.", beans.size());
    }

    public static void schedule() {
        log.info("Startup Schedule Schedule init Starting...");
        try {
            // 默认实现
            ISchedule job = new DefaultSchedule();
            // 默认装饰追加
            ISchedule job2 = new DefaultDecorator(job);
            // 开始初始化....
            job2.init();
            log.info("Startup Schedule {} init completed.", ScheduleHelper.getJobsName().size());
        } catch (Exception e) {
            log.error("Startup Schedule {}", e);
        }
    }
}
