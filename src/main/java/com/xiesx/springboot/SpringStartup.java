package com.xiesx.springboot;

import com.xiesx.springboot.support.license.LicenseVerify;
import com.xiesx.springboot.support.schedule.ScheduleHelper;
import com.xiesx.springboot.support.schedule.decorator.DefaultDecorator;
import com.xiesx.springboot.support.schedule.decorator.DefaultSchedule;
import com.xiesx.springboot.support.schedule.impl.ISchedule;
import com.xiesx.springboot.utils.RuntimeUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SpringStartup {

    public static String classUrl;

    /** 由于是分布式环境，该名字为每个tomcat的目录名（在部署时必须唯一） */
    public static String servername;

    public static String serverpath;

    public static void init() {
        try {
            classUrl = RuntimeUtils.getRootPath();
            log.info("Startup classpath url " + classUrl);
            int index = classUrl.indexOf("/webapps/");
            if (index > 0) {
                String path = classUrl.substring(0, index);
                index = path.lastIndexOf("/");
                servername = path.substring(index + 1);
                serverpath = classUrl.split(servername)[0] + servername;
            } else {
                servername = classUrl.substring(classUrl.length() - 34);
                serverpath = classUrl;
            }
            System.out.println();
            log.info("Startup tomcat-name " + servername + ", path " + serverpath);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    public static void license() {
        try {
            LicenseVerify param = new LicenseVerify();
            // 证书主题
            param.setSubject("FAST");
            // 公钥别称
            param.setPublicAlias("publicCert");
            // 访问公钥库的密码
            param.setStorePass("136305973@qq.com");
            // 公钥库存储路径
            param.setPublicKeysStorePath(SpringStartup.class.getResource("/license/publicCerts.store").getPath());
            // 证书生成路径
            param.setLicensePath(SpringStartup.class.getResource("/license/license").getPath());
            // 安装证书
            param.install();
            // 卸载证书
            // param.unInstall();
            // 校验证书
            if (!param.verify()) {
                // System.exit(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
