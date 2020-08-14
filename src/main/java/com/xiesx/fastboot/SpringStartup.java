package com.xiesx.fastboot;

import com.xiesx.fastboot.support.license.LicenseVerify;
import com.xiesx.fastboot.support.license.cfg.LicenseProperties;
import com.xiesx.fastboot.support.schedule.ScheduleHelper;
import com.xiesx.fastboot.support.schedule.decorator.DefaultDecorator;
import com.xiesx.fastboot.support.schedule.impl.ISchedule;
import com.xiesx.fastboot.utils.RuntimeUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SpringStartup {

    public static String classUrl;

    /** 由于是分布式环境，该名字为每个tomcat的目录名（在部署时必须唯一） */
    public static String servername;

    public static String serverpath;

    // www/wwwroot/go168.xyz/WEB-INF
    // www/wwwroot/gotv-api/webapps

    public static void init() {
        try {
            classUrl = RuntimeUtils.getRootPath();
            log.info("Startup classpath url " + classUrl);
            if (classUrl.contains("web-inf")) {
                int index = classUrl.indexOf("/web-inf");
                if (index > 0) {
                    String path = classUrl.substring(0, index);
                    index = path.lastIndexOf("/");
                    servername = path.substring(index + 1);
                    serverpath = classUrl.split(servername)[0] + servername;
                }
            } else if (classUrl.contains("webapps")) {
                int index = classUrl.indexOf("/webapps");
                if (index > 0) {
                    String path = classUrl.substring(0, index);
                    index = path.lastIndexOf("/");
                    servername = path.substring(index + 1);
                    serverpath = classUrl.split(servername)[0] + servername;
                }
            } else {
                servername = "unknown";
                serverpath = "unknown";
            }
            log.info("Startup tomcat-name " + servername + ", path " + serverpath);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    public static void license() {
        try {
            // 获取参数
            LicenseProperties properties = SpringHelper.getBean(LicenseProperties.class);
            //
            if (properties != null) {
                // 构造
                LicenseVerify param = new LicenseVerify();
                // 证书主题
                param.setSubject(properties.getSubject());
                // 公钥库存储路径
                param.setPublicKeysStorePath(properties.getPublicKeysStorePath());
                // 访问公钥库的密码
                param.setStorePass(properties.getStorePass());
                // 公钥别称
                param.setPublicAlias(properties.getPublicAlias());
                // 证书生成路径
                param.setLicensePath(properties.getLicensePath());
                // 安装证书
                param.install();
                // 卸载证书
                // param.unInstall();
                // 校验证书
                if (!param.verify()) {
                    // System.exit(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void schedule() {
        log.info("Startup Schedule Schedule init Starting...");
        try {
            // 默认实现
            // 默认装饰追加
            ISchedule job2 = new DefaultDecorator();
            // 开始初始化....
            job2.init();
            log.info("Startup Schedule {} init completed.", ScheduleHelper.queryAllJob().size());
        } catch (Exception e) {
            log.error("Startup Schedule {}", e);
        }
    }
}
