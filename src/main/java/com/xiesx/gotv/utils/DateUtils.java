package com.xiesx.gotv.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * 日期处理
 * 
 * @author theodo
 * @email 36780272@qq.com
 * @date 2017年10月21日 下午12:53:33
 */
public class DateUtils {

	/** 时间格式(yyyy-MM-dd) */
	public final static String DATE_PATTERN = "yyyy-MM-dd";

	/** 时间格式(yyyy-MM-dd HH:mm:ss) */
	public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

	public final static String USER_DATA_PATTERN = "yyyyMMddHHmmss";

	public static String format(Date date) {
		return format(date, DATE_PATTERN);
	}

	public static String formatLong(Date date) {
		return format(date, DATE_TIME_PATTERN);
	}

	public static Date format(String dataStr) {
		if (!StringUtils.isEmpty(dataStr)) {
			SimpleDateFormat df = new SimpleDateFormat(DATE_PATTERN);
			try {
				return df.parse(dataStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	public static Date formatLong(String dataStr) {
		if (!StringUtils.isEmpty(dataStr)) {
			SimpleDateFormat df = new SimpleDateFormat(DATE_TIME_PATTERN);
			try {
				return df.parse(dataStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	public static String getUserDate() {
		return getUserDate(USER_DATA_PATTERN);
	}

	public static String getUserDate(String sformat) {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(sformat);
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	public static String format(Date date, String pattern) {
		if (date != null) {
			SimpleDateFormat df = new SimpleDateFormat(pattern);
			return df.format(date);
		}
		return null;
	}

	public static Date getRudeMonth(int mcount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - mcount);// 让日期加1
		return calendar.getTime();
	}
}
