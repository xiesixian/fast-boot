package com.xiesx.gotv;

import java.net.URL;

import lombok.extern.slf4j.Slf4j;

import com.xiesx.gotv.utils.ObjectUtil;

@Slf4j
public class GotvStartup  {

	public static String classUrl;

	/** 由于是分布式环境，该名字为每个tomcat的目录名（在部署时必须唯一） */
	public static String gtgjxname;

	public static int gtgjxIndex = -1;

	public static String gtgjxpath;

	/** 程序名 */
	public static String projectname;

	public static String projectPath;

	public static void startup() {
		try {
			URL url = GotvStartup.class.getResource("/");
			classUrl = url.getPath();
			log.info("Startup classpath url " + classUrl);

			// /home/gtgj/tomcat-loco/webapps/trainnet/WEB-INF/classes/
			int index = classUrl.indexOf("/webapps/");
			if (index > 0) {
				// /home/gtgj/tomcat-loco
				String path = classUrl.substring(0, index);
				index = path.lastIndexOf("/");

				// tomcat-loco1
				gtgjxname = path.substring(index + 1);
				gtgjxpath = classUrl.split(gtgjxname)[0] + gtgjxname;
				projectname = ObjectUtil.getStringFromToEx(classUrl, "/webapps/", "/");
				projectPath = gtgjxpath + "/webapps/" + projectname;

				String tindex = gtgjxname.replace("tomcat-loco", "");
				if (ObjectUtil.isNotNull(tindex) && ObjectUtil.isNumber(tindex)) {
					gtgjxIndex = Integer.parseInt(tindex);
				}
			} else {
				gtgjxname = classUrl.substring(classUrl.length() - 20);
				gtgjxpath = classUrl;
				projectname = classUrl;
				projectPath = classUrl;
			}
			log.info("Startup tomcat-name " + gtgjxname + ", index " + gtgjxIndex + ", path " + gtgjxpath + ", projectname " + projectname + ", projectPath " + projectPath);
		} catch (Exception e) {
			log.error("", e);
			System.exit(1);
		}
	}
}
