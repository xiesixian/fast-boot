package com.xiesx.fastboot;

import com.xiesx.fastboot.support.license.LicenseVerify;
import com.xiesx.fastboot.support.license.cfg.LicenseProperties;
import com.xiesx.fastboot.support.scheduler.ScheduleHelper;
import com.xiesx.fastboot.support.scheduler.decorator.ISchedule;
import com.xiesx.fastboot.support.scheduler.decorator.SimpleDecorator;
import com.xiesx.fastboot.utils.RuntimeUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SpringStartup {

    public static String classUrl;

    /** 分布式环境，该名字为每个tomcat的目录名（在部署时必须唯一） */
    public static String servername;

    public static String serverpath;

    public static void init() {
        classUrl = RuntimeUtils.getRootPath().toLowerCase();
        if (classUrl.contains("target")) {// 本地
            int index = classUrl.indexOf("/target");
            if (index > 0) {
                String path = classUrl.substring(0, index);
                index = path.lastIndexOf("/");
                servername = path.substring(index + 1);
                serverpath = classUrl.split(servername)[0] + servername;
            }
        } else if (classUrl.contains("web-inf")) {// tomcat启动
            int index = classUrl.indexOf("/web-inf");
            if (index > 0) {
                String path = classUrl.substring(0, index);
                index = path.lastIndexOf("/");
                servername = path.substring(index + 1);
                serverpath = classUrl.split(servername)[0] + servername;
            }
        } else if (classUrl.contains("webapps")) {// jar启动
            int index = classUrl.indexOf("/webapps");
            if (index > 0) {
                String path = classUrl.substring(0, index);
                index = path.lastIndexOf("/");
                servername = path.substring(index + 1);
                serverpath = classUrl.split(servername)[0] + servername;
            }
        } else {
            servername = "unknown";
            serverpath = classUrl;
        }
        log.info("Startup name: " + servername + ", path: " + serverpath);
    }

    public static void license() {
        // 获取参数
        LicenseProperties properties = SpringHelper.getBean(LicenseProperties.class);
        if (properties != null) {
            // 构造
            LicenseVerify param = new LicenseVerify();
            // 证书主题
            param.setSubject(properties.getSubject());
            // 公钥库存储路径
            param.setPublicKeysStorePath(properties.getPublicStorePath());
            // 访问公钥库的密码
            param.setStorePass(properties.getStorePass());
            // 公钥别称
            param.setPublicAlias(properties.getPublicAlias());
            // 证书生成路径
            param.setLicensePath(properties.getLicensePath());
            // 安装证书
            try {
                param.install();
            } catch (Exception e) {
                log.error("Startup License Install Error {}", e.getMessage());
            }
            try {
                if (!param.verify()) {
                    // System.exit(1);
                }
            } catch (Exception e) {
                log.error("Startup License Verify Error {}", e.getMessage());
            }
        }
    }

    public static void scheduler() {
        try {
            // 默认实现
            // 默认装饰追加
            ISchedule job2 = new SimpleDecorator();
            // 开始初始化....
            job2.init();
            log.info("Startup Scheduler {} job completed.", ScheduleHelper.queryAllJob().size());
        } catch (Exception e) {
            log.error("Startup Scheduler ", e.getMessage());
        }
    }
}
