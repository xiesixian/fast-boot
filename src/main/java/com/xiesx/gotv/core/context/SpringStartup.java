package com.xiesx.gotv.core.context;

import java.net.URL;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.xiesx.gotv.core.logger.LogStorage;
import com.xiesx.gotv.support.schedule.ScheduleHelper;
import com.xiesx.gotv.support.schedule.decorator.DefaultDecorator;
import com.xiesx.gotv.support.schedule.decorator.DefaultSchedule;
import com.xiesx.gotv.support.schedule.impl.ISchedule;

@Slf4j
public class SpringStartup {

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

	public static void logger() {
		//
		log.info("Startup logger Storage init Starting...");
		//
		JdbcTemplate jdbcTemplate = SpringHelper.getBean(JdbcTemplate.class);
		//
		String sql = "SELECT * FROM information_schema.TABLES WHERE TABLE_SCHEMA=(SELECT DATABASE()) AND `table_name` =? ";
		try {
			Map<String, Object> map = SpringHelper.getBean(JdbcTemplate.class).queryForMap(sql, new Object[] { LogStorage.TABLE.API_LOG });
			log.info("Startup Logger Storage {}", map.isEmpty() == false);
		} catch (Exception e) {
			if (e instanceof EmptyResultDataAccessException) {
				StringBuilder sb = new StringBuilder();
				sb.append("CREATE TABLE " + LogStorage.TABLE.API_LOG + " ( ");
				sb.append("id VARCHAR(255) NOT NULL COMMENT '主键',");
				sb.append("create_date DATETIME NOT NULL COMMENT '创建时间',");
				sb.append("ip VARCHAR(255) COMMENT '请求IP',");
				sb.append("method VARCHAR(255) NOT NULL COMMENT '方法',");
				sb.append("TYPE VARCHAR(255) NOT NULL COMMENT '方式',");
				sb.append("url VARCHAR(1000) NOT NULL COMMENT '地址',");
				sb.append("req LONGTEXT NOT NULL COMMENT '请求',");
				sb.append("res LONGTEXT NOT NULL COMMENT '响应',");
				sb.append("t INT(11) DEFAULT 0 COMMENT '执行时间（毫秒）'");
				sb.append(")");
				sb.append("COMMENT='日志存储表';");
				//
				jdbcTemplate.execute(sb.toString());
				log.info("Startup Logger Storage init completed.");
			} else {
				log.error("Startup Logger {}", e);
			}
		}
	}

	public static void schedule() {
		//
		log.info("Startup Schedule Schedule init Starting...");
		//
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
