package com.xiesx.gotv.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.Collator;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("all")
public class ObjectUtil
{

	/**
	 * 截取两段字符串中间的内容（对字符串唯一性不做校验），不区分大小写，推荐用getStringFromToEx
	 * @param src 源字符串
	 * @param from 起始字符串
	 * @param to 结束字符串
	 */
	public static String getStringFromTo(String src, String from, String to)
	{
		from = from.toLowerCase(Locale.US);
		to = to.toLowerCase(Locale.US);
		StringBuilder sb = new StringBuilder();
		// 起始字符是否已经匹配到，空字符当作匹配到
		boolean startMatched = from.equals("") ? true : false;
		// 结束字符是否已经匹配到
		boolean endmatched = to.equals("") ? true : false;
		// 避免在循环的时候不停的判断有没有越界
		int limit = src.length() - to.length();
		for (int i = 0; i < limit; i++)
		{
			if (!startMatched)
			{
				for (int k = i, j = 0; j < from.length(); k++, j++)
				{
					if (src.charAt(k) != from.charAt(j) && src.charAt(k) != from.charAt(j) - 32)
					{
						break;
					}
					// 如果from循环完还有没跳出去，则说明匹配成功
					if (k == i + from.length() - 1)
					{
						startMatched = true;
						// 改变限制
						limit = src.length() - to.length();
						i = k;
					}
				}
			}
			else
			{
				// 起始匹配到就要开始匹配结束字符串
				// 不管结束字符串能不能匹配到都开始记录
				sb.append(src.charAt(i));
				for (int k = i, j = 0; j < to.length(); k++, j++)
				{
					if (src.charAt(k) != to.charAt(j) && src.charAt(k) != to.charAt(j) - 32)
					{
						break;
					}
					// 如果from循环完还有没跳出去，则说明匹配成功
					if (k == i + to.length() - 1)
					{
						endmatched = true;
						// 匹配到结束字符串则退出 
						break;
					}
				}
			}
			if (startMatched && endmatched)
			{
				String str = sb.toString();
				// 去掉进else时最开始加的一个字符
				str = str.substring(0, str.length() - 1);
				return str;
			}
		}
		return null;
	}

	/**
	 * 截取两段字符串中间的内容（对字符串唯一性不做校验），不区分大小写
	 * @param src 源字符串
	 * @param from 起始字符串
	 * @param to 结束字符串
	 */
	public static String getStringFromToEx(String src, String from, String to)
	{
		if (ObjectUtil.isNull(from) || ObjectUtil.isNull(to))
		{
			return null;
		}
		from = from.toLowerCase(Locale.US);
		to = to.toLowerCase(Locale.US);
		StringBuilder sb = new StringBuilder();
		// 起始字符是否已经匹配到
		boolean startMatched = false;
		// 结束字符是否已经匹配到
		boolean endmatched = false;
		// 读出出可比较长度
		int limit = src.length() - to.length() + 1;
		for (int i = 0; i < limit; i++)
		{
			if (!startMatched)
			{
				for (int k = i, j = 0; j < from.length(); k++, j++)
				{
					if (src.charAt(k) != from.charAt(j) && src.charAt(k) != from.charAt(j) - 32)
					{
						break;
					}
					// 如果from循环完还有没跳出去，则说明匹配成功
					if (k == i + from.length() - 1)
					{
						startMatched = true;
						i = k;
					}
				}
			}
			else
			{
				// 起始匹配到就要开始匹配结束字符串
				// 不管结束字符串能不能匹配到都开始记录
				sb.append(src.charAt(i));
				for (int k = i, j = 0; j < to.length(); k++, j++)
				{
					if (src.charAt(k) != to.charAt(j) && src.charAt(k) != to.charAt(j) - 32)
					{
						break;
					}
					// 如果from循环完还有没跳出去，则说明匹配成功
					if (k == i + to.length() - 1)
					{
						endmatched = true;
						// 匹配到结束字符串则退出 
						break;
					}
				}
			}
			if (startMatched && endmatched)
			{
				String str = sb.toString();
				// 去掉进else时最开始加的一个字符
				str = str.substring(0, str.length() - 1);
				return str;
			}
		}
		return null;
	}

	public static String toBrowsercodeByUTF8(String word) throws Exception
	{
		return toBrowserCode(word, "UTF-8");
	}

	public static String toBrowserCode(String word, String encoding) throws Exception
	{
		byte[] textByte = null;
		try
		{
			textByte = word.getBytes(encoding);
		}
		catch (UnsupportedEncodingException e)
		{
			throw new Exception(e.getMessage());
		}
		StringBuilder strBuilder = new StringBuilder();

		for (int j = 0; j < textByte.length; j++)
		{
			// 转换为16进制字符
			String hexStr = Integer.toHexString(textByte[j] & 0xff);
			strBuilder.append("%" + hexStr.toUpperCase());
		}
		return strBuilder.toString();
	}

	/**
	 * 判断一个字条串数组中是否存在某字符串（忽略大小写）
	 * @param strs 数据
	 * @param dest 目标字符串
	 * @return true，存在；false，不存在。
	 */
	public static boolean exsit(String[] strs, String dest)
	{
		for (String str : strs)
		{
			if (str.equalsIgnoreCase(dest))
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * 比较两个时间的大小（只比较到天）
	 * @param date1 目前支持YYYY-MM-DD
	 * @param date2 日期对象
	 * @return 1，大于；0，等于；-1，小于
	 */
	public static int compareDate(String date1, Date date2)
	{
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try
		{
			Date d1 = df.parse(date1);
			// 去掉时间
			String s2 = df.format(date2);
			Date d2 = df.parse(s2);
			return d1.compareTo(d2);
		}
		catch (ParseException e)
		{
			return -1;
		}
	}

	/**
	 * 比较两个时间的大小（只比较到天）
	 * @param date1 目前支持YYYY-MM-DD
	 * @param date2 目前支持YYYY-MM-DD
	 * @return 1，大于；0，等于；-1，小于
	 */
	public static int compareDate(String date1, String date2)
	{
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try
		{
			Date d1 = df.parse(date1);
			Date d2 = df.parse(date2);
			return d1.compareTo(d2);
		}
		catch (ParseException e)
		{
			return -1;
		}
	}

	/**
	 * 比较两个版本大小
	 * @param v1  版本1
	 * @param v2 版本2
	 * @return 1，大于；0，等于；-1，小于
	 */
	public static int compareVersion(String v1, String v2)
	{
		// 多增加几个0，以解决长度不一致问题
		v1 = v1 + ".0.0.0.0";
		v2 = v2 + ".0.0.0.0";
		String[] v1s = v1.split("\\.");
		String[] v2s = v2.split("\\.");
		if (Integer.valueOf(v1s[0]).compareTo(Integer.valueOf(v2s[0])) != 0)
		{
			return Integer.valueOf(v1s[0]).compareTo(Integer.valueOf(v2s[0]));
		}
		else if (v1s.length > 1 && v2s.length > 1 && Integer.valueOf(v1s[1]).compareTo(Integer.valueOf(v2s[1])) != 0)
		{
			return Integer.valueOf(v1s[1]).compareTo(Integer.valueOf(v2s[1]));
		}
		else if (v1s.length > 2 && v2s.length > 2 && Integer.valueOf(v1s[2]).compareTo(Integer.valueOf(v2s[2])) != 0)
		{
			return Integer.valueOf(v1s[2]).compareTo(Integer.valueOf(v2s[2]));
		}
		else if (v1s.length > 3 && v2s.length > 3 && Integer.valueOf(v1s[3]).compareTo(Integer.valueOf(v2s[3])) != 0)
		{
			return Integer.valueOf(v1s[3]).compareTo(Integer.valueOf(v2s[3]));
		}
		else
		{
			return 0;
		}
	}

	/**
	 * 转换版本的格式，如2.1.1转成002001001，便于在数据库中比较
	 * @param v 原始版本
	 * @return 转换后的版本格式
	 */
	public static String versionStr(String v)
	{
		String[] vs = v.split("\\.");
		StringBuilder sb = new StringBuilder();
		for (String s : vs)
		{
			sb.append("000".substring(0, 3 - s.length()));
			sb.append(s);
		}
		return sb.toString();
	}

	/**
	 * 计算出时间差
	 * @param startDay 开始天数
	 * @param startTime 开始时间
	 * @param endDay 结束天数
	 * @param endTime 结束时间
	 * @return 时间（分）
	 */
	public static int getTimeDifference(int startDay, String startTime, int endDay, String endTime)
	{
		String[] starts = startTime.split(":");
		int s = Integer.parseInt(starts[0]) * 60 + Integer.parseInt(starts[1]);
		String[] ends = endTime.split(":");
		int e = Integer.parseInt(ends[0]) * 60 + Integer.parseInt(ends[1]);
		return e + (endDay - startDay) * 24 * 60 - s;
	}

	/**
	 * 提前天数后的日期
	 * @param separator 年月日分隔符
	 *  @param earlyDate 从当天（第1天）往后数，抓第几天的票
	 * @return 订票日期参数
	 */
	public static String getTrainDay(String separator, int earlyDate)
	{
		Date date = new Date(System.currentTimeMillis() + (earlyDate - 1) * 24 * 60 * 60 * 1000l);
		DateFormat df = new SimpleDateFormat("yyyy" + separator + "MM" + separator + "dd");
		return df.format(date);
	}

	/**
	 * 把格式如20131122格式的字串转化为时间
	 * @param date 字符串日期
	 * @return 时间
	 */
	public static Timestamp parseTimestamp(String date)
	{
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		try
		{
			return new Timestamp(df.parse(date).getTime());
		}
		catch (ParseException e)
		{
			return null;
		}
	}

	/**
	 * 转换日期时间戳
	 * @param date
	 * @param format
	 * @return
	 */
	public static Timestamp parseTimestamp(String date, String format)
	{
		DateFormat df = new SimpleDateFormat(format);
		try
		{
			return new Timestamp(df.parse(date).getTime());
		}
		catch (ParseException e)
		{
			return null;
		}
	}

	/**
	 * 获得上个月月份 XXXX-XX
	 * @return 上个月月份 XXXX-XX
	 */
	public static String getLastMonth()
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
		Timestamp tmp = new Timestamp(calendar.getTimeInMillis());
		return getTimeStampToString(tmp).substring(0, 7);
	}

	/**
	 * 获得当天日期如 2013-05-23
	 * @return 当天日期
	 */
	public static String getDay()
	{
		Date date = new Date(System.currentTimeMillis());
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(date);
	}

	/**
	 * 获得早于当天天数差异的日期 2013-05-23
	 * @param ealry 相对于当天的天数差，正数向后，负数向前
	 * @return 日期
	 */
	public static String getDay(int early)
	{
		Date date = new Date(System.currentTimeMillis() + early * 24 * 60 * 60 * 1000l);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(date);
	}

	/**
	 * 获得早于给定时间分钟数差异的日期 2015-10-30
	 * @param timestamp 给定的时间
	 * @param early 相对于给定时间的分钟数差，正数向后，负数向前
	 * @return
	 */
	public static String getEarlyMinute(Timestamp timestamp, int early)
	{
		Date date = new Date(timestamp.getTime() + early * 60 * 1000l);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(date);
	}

	/**
	 * 根据时间获取通俗说法
	 * 如 2016-06-03 13:02 => 后天下午1点02分
	 */
	public static String getTimeCommonSaying(Timestamp time)
	{
		if (time == null)
		{
			return null;
		}
		StringBuilder result = new StringBuilder();
		String timestr = getTimeStampToString(time);
		String date = timestr.split(" ")[0];
		if (date.equals(getDay(0)))
		{
			result.append("今天");
		}
		else if (date.equals(getDay(1)))
		{
			result.append("明天");
		}
		else if (date.equals(getDay(2)))
		{
			result.append("后天");
		}
		else
		{
			// 2016-06-15 => 6月15日
			result.append(Integer.parseInt(date.substring(5, 7))).append("月");
			result.append(Integer.parseInt(date.substring(8, 10))).append("日");
		}
		// 2016-06-15 09:20 ==> 上午9点20分   12:20==>中午12点20分  15:00==>下午3点
		int hour = Integer.parseInt(timestr.substring(11, 13));
		if (hour < 12)
		{
			result.append("上午");
		}
		else if (hour == 12)
		{
			result.append("中午");
		}
		else
		{
			result.append("下午");
		}
		result.append(hour).append("点");
		int minute = Integer.parseInt(timestr.substring(14, 16));
		if (minute != 0)
		{
			// 分钟前面的0不能省
			result.append(timestr.substring(14, 16)).append("分");
		}
		return result.toString();
	}

	/**
	 * 获得早于当前时间分钟数差异的日期 2016-03-03
	 * @param timestamp 给定的时间
	 * @param early 相对于给定时间的分钟数差，正数向后，负数向前
	 * @return
	 */
	public static String getEarlyMinute(int early)
	{
		Date date = new Date(System.currentTimeMillis() + early * 60 * 1000l);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(date);
	}

	/**
	 * 获取与给定日期天数差的日期  2015-11-10
	 * @param date yyyy-MM-dd
	 * @param early 天数  正数向后，负数向前
	 * @return yyyy-MM-dd
	 */
	public static String getEarlyDay(String date, int early)
	{
		Timestamp timestamp = getTimestamp(date + " 00:00:00");
		Date d = new Date(timestamp.getTime() + 24 * 60 * early * 60 * 1000l);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(d);
	}

	/**
	 * 获得早于当月月数差异的日期 2013-05
	 * @param ealry 相对于当天的天数差，正数向后，负数向前
	 * @return 日期
	 */
	public static String getMonth(int early)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + early);
		Timestamp time = new Timestamp(calendar.getTimeInMillis());
		return time.toString().substring(0, 7);
	}

	/**
	 * 获得当月日期如  2013-05
	 * @return 当天日期
	 */
	public static String getMonth()
	{
		Date date = new Date(System.currentTimeMillis());
		DateFormat df = new SimpleDateFormat("yyyy-MM");
		return df.format(date);
	}

	/**
	 * 获取月份起始日期 2015-10-1
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String getMinMonthDate(long currenttime) throws ParseException
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(currenttime);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		Timestamp time = new Timestamp(calendar.getTimeInMillis());
		return time.toString().substring(0, 10);
	}

	/**
	 * 获取月份最后日期 2015-10-31
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String getMaxMonthDate(long currenttime) throws ParseException
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(currenttime);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		Timestamp time = new Timestamp(calendar.getTimeInMillis());
		return time.toString().substring(0, 10);
	}

	/**
	 * 获得早于源日期月数差异的日期 2013-05
	 * @param daytime 源日期
	 * @param ealry 相对于当天的天数差，正数向后，负数向前
	 * @return 日期
	 */
	public static String getMonthByDaytime(int early, long daytime)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(daytime);
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + early);
		Timestamp time = new Timestamp(calendar.getTimeInMillis());
		return time.toString().substring(0, 7);
	}

	/**
	 * 比较时间的大小
	 * @param time1 时间一
	 * @param time2 时间二
	 * @return 1，大于；0，等于；-1，小于
	 */
	public static int compareTime(String time1, String time2)
	{
		String[] t1s = time1.split(":");
		int s = Integer.parseInt(t1s[0]) * 60 + Integer.parseInt(t1s[1]);
		String[] t2s = time2.split(":");
		int e = Integer.parseInt(t2s[0]) * 60 + Integer.parseInt(t2s[1]);
		if (s == e)
		{
			return 0;
		}
		else if (s > e)
		{
			return 1;
		}
		else
		{
			return -1;
		}
	}

	/**
	 * 比较价格的大小
	 * @param p1
	 * @param p2
	 * @return
	 */
	public static int comparePrice(Object p1, Object p2)
	{
		BigDecimal price1 = getBigDecimal(p1);
		BigDecimal price2 = getBigDecimal(p2);
		return price1.compareTo(price2);
	}

	/**
	 * 保留pre位小数
	 * @param value 原值
	 * @param pre 小数位数
	 * @return 字符串型对象
	 */
	public static String round(float value, int pre)
	{
		return String.format("%." + pre + "f", value);
	}

	public static String round(double value, int pre)
	{
		return String.format("%." + pre + "f", value);
	}

	/**
	 * 四舍五入取整
	 * @param value 浮点数
	 * @return 整数
	 */
	public static float roundHalfUp(float value)
	{
		return new BigDecimal(value).setScale(0, BigDecimal.ROUND_HALF_UP).floatValue();
	}

	/**
	 * 四舍五入取整
	 * @param value 浮点数
	 * @return 整数
	 */
	public static int roundHalfUpToInt(float value)
	{
		return new BigDecimal(value).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
	}

	public static boolean isNull(Object param)
	{
		return param == null || "".equals(param);
	}

	public static boolean hasNullObj(Object... param)
	{
		for (Object obj : param)
		{
			if (isNull(obj))
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * 参数集是否有参数为null
	 * @param param 参数集 
	 * @return true，有；false，没有
	 */
	public static boolean hasNull(String... param)
	{
		for (String str : param)
		{
			if (isNull(str))
			{
				return true;
			}
		}
		return false;
	}

	public static boolean isNotNull(Object param)
	{
		return !isNull(param);
	}

	public static <E> boolean isNotEmpty(Collection<E> coll)
	{
		return coll != null && !coll.isEmpty();
	}

	public static <E> boolean isEmpty(Collection<E> coll)
	{
		return !isNotEmpty(coll);
	}

	/**
	 * 以16进制获取当时的时间
	 * @param 日期格式
	 * @return 当时的时间
	 */
	public static String currentHexDate(String form)
	{
		return Long.toHexString(formatLongDate(form));
	}

	public static long formatLongDate(String form)
	{
		String date = new SimpleDateFormat(form).format(new Date());
		return Long.parseLong(date);
	}

	public static int formatIntegerDate(String form)
	{
		String date = new SimpleDateFormat(form).format(new Date());
		return Integer.parseInt(date);
	}

	public static String getTimeStamp()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date());
	}

	public static String formatDate(Timestamp timestamp)
	{
		String str = getTimeStampString(timestamp.getTime());
		str = str.split(" ")[0];
		str = str.replaceFirst("-", "年");
		str = str.replaceFirst("-", "月");
		return str + "日";
	}

	public static String formatDate(String date)
	{
		date = date.replaceFirst("-", "年");
		date = date.replaceFirst("-", "月");
		return date + "日";
	}

	/**
	 * 判断是不是日期格式
	 * @param day 字条串
	 */
	public static boolean isDate(String day)
	{
		try
		{
			Timestamp.valueOf(day + " 00:00:00");
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}

	public static String getString(Object obj)
	{
		return obj == null ? null : obj.toString();
	}

	public static String getString2(Object obj)
	{
		return obj == null ? "" : obj.toString();
	}

	public static String geTrimString(String str)
	{
		return str == null ? null : str.trim();
	}

	public static String getStringNotBlank(Object obj)
	{
		String s = obj == null ? null : obj.toString();
		if (s == null || s.equals(""))
		{
			return null;
		}
		return s;
	}
 
	public static String getStringByFastJson(com.alibaba.fastjson.JSONObject json, String key)
	{
		if (json == null || ObjectUtil.isNull(key))
		{
			return null;
		}
		if (json.containsKey(key))
		{
			return json.getString(key);
		}
		return null;
	}

	public static double getDouble(Object obj)
	{
		return obj == null ? 0.0 : Double.parseDouble(obj.toString());
	}

	public static float getFloat(Object obj)
	{
		return obj == null ? 0.0f : Float.parseFloat(obj.toString());
	}

	public static int getInt(Object obj)
	{
		if (obj == null || "".equals(obj))
		{
			return 0;
		}
		if (obj instanceof Integer)
		{
			return ((Integer) obj).intValue();
		}
		return obj == null ? 0 : Integer.parseInt(obj.toString());
	}

	public static Integer getInteger(Object obj)
	{
		if (obj == null)
			return null;

		if (obj instanceof Integer)
		{
			return ((Integer) obj).intValue();
		}
		return Integer.parseInt(obj.toString());
	}

	public static Integer getIntV3(Object obj)
	{
		if (obj instanceof Integer)
		{
			return ((Integer) obj).intValue();
		}
		return obj == null ? null : Integer.parseInt(obj.toString());
	}

	public static int getIntV2(Object obj)
	{
		if (obj instanceof Integer)
		{
			return ((Integer) obj).intValue();
		}
		return isNull(obj) ? 0 : Integer.parseInt(obj.toString());
	}

	public static long getLong(Object obj)
	{
		return isNull(obj) ? 0l : Long.parseLong(obj.toString());
	}

	public static boolean getBoolean(Object obj)
	{
		return isNull(obj) ? false : Boolean.parseBoolean(obj.toString());
	}

	public static Timestamp getTimestamp(Object obj)
	{
		return isNull(obj) ? null : Timestamp.valueOf(obj.toString());
	}

	public static Date getDate(Object obj)
	{
		return isNull(obj) ? null : new Date(Timestamp.valueOf(obj.toString()).getTime());
	}

	public static BigDecimal getBigDecimal(Object obj)
	{
		return isNull(obj) ? new BigDecimal(0) : new BigDecimal(obj.toString());
	}

	/**
	 * 通过字符串类型和长整型时间格式转化
	 */
	public static Timestamp getTimestampByString(String str)
	{
		if (ObjectUtil.isNull(str))
		{
			return null;
		}
		Timestamp time = null;
		if (str.indexOf(':') != -1)
		{
			time = Timestamp.valueOf(str);
		}
		else
		{
			time = new Timestamp(ObjectUtil.getLong(str));
		}
		return time;
	}

	public static Timestamp checkAndGetTimestamp(String str)
	{
		if (isNull(str))
		{
			return null;
		}
		try
		{
			// 数字格式
			Timestamp time = new Timestamp(Long.parseLong(str));
			return time;
		}
		catch (Exception e)
		{
		}
		try
		{
			// 日期格式
			Timestamp time = Timestamp.valueOf(str);
			return time;
		}
		catch (Exception e)
		{
		}
		return null;
	}

	/**
	 * 比较两天的差，格式都必须为yyyy-MM-dd
	 * @param day1 第一个日期
	 * @param day2 第二个日期
	 * @return 差的天数
	 */
	public static long sub2Day(String day1, String day2)
	{
		long t1 = Timestamp.valueOf(day1 + " 00:00:00").getTime();
		long t2 = Timestamp.valueOf(day2 + " 00:00:00").getTime();
		return (t1 - t2) / (24 * 60 * 60 * 1000l);
	}

	public static long sub2Day(Timestamp day1, Timestamp day2)
	{
		return (day1.getTime() - day2.getTime()) / (24 * 60 * 60 * 1000l);
	}

	/**
	 * 获得日期是星期几（1，星期一……）
	 * @param time 日期
	 * @return 星期
	 */
	public static int getWeekDay(Timestamp time)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		// 星期日当作7处理
		if (w == 0)
		{
			w = 7;
		}
		return w;
	}

	/**
	 * 获得日期是星期几（1，星期一……）
	 * @param time 日期
	 * @return 星期
	 */
	public static String getWeekDayStr(Timestamp time)
	{
		Map<Integer, String> weekMap = new HashMap<Integer, String>();
		weekMap.put(1, "mon");
		weekMap.put(2, "tue");
		weekMap.put(3, "wed");
		weekMap.put(4, "thu");
		weekMap.put(5, "fri");
		weekMap.put(6, "sat");
		weekMap.put(7, "sun");
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		// 星期日当作7处理
		if (w == 0)
		{
			w = 7;
		}
		return weekMap.get(w);
	}

	/**
	 * 获得日期是星期几（1，星期一……）
	 * @param time 日期
	 * @return 星期
	 */
	public static String getWeekDayStr2(Timestamp time)
	{
		Map<Integer, String> weekMap = new HashMap<Integer, String>();
		weekMap.put(1, "周一");
		weekMap.put(2, "周二");
		weekMap.put(3, "周三");
		weekMap.put(4, "周四");
		weekMap.put(5, "周五");
		weekMap.put(6, "周六");
		weekMap.put(7, "周日");
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		// 星期日当作7处理
		if (w == 0)
		{
			w = 7;
		}
		return weekMap.get(w);
	}

	/**
	 * 获得整点
	 * @param time 日期
	 * @return 星期
	 */
	public static int getHour(Timestamp time)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		return cal.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 判断字符串是否为手机号
	 * @param phone 手机号
	 * @return true，是；false，不是
	 */
	public static boolean isPhone(String phone)
	{
		String regex = "^\\d{11}$";
		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(phone);
		if (matcher.find())
		{
			return true;
		}
		return false;
	}

	/**
	 * 计算一个子字符串在父字符串中出现的次数
	 * @param src 父字符
	 * @param child 子字符串
	 * @return 次数
	 */
	public static int count(String src, String child)
	{
		if (src.equals(child))
		{
			return 1;
		}
		if ("".equals(child))
		{
			return 0;
		}
		int count = 0;
		int fromIndex = 0;
		int endIndex = -1;
		while ((endIndex = src.indexOf(child, fromIndex)) != -1)
		{
			++count;
			fromIndex = endIndex + child.length();
		}
		return count;
	}

	/**
	 * 比较两个字符串
	 * @param o1 字符串1
	 * @param o2 字符串2
	 * @return -1，小于；0，等于；1，大于
	 */
	public static int compareString(String o1, String o2)
	{
		if (o1 == null && o2 == null)
		{
			return 0;
		}
		else if (o1 == null)
		{
			return -1;
		}
		else if (o2 == null)
		{
			return 1;
		}
		else
		{
			return o1.compareTo(o2);
		}
	}

	/**
	 * 替换字符串的原字符串，有别于String的replace方法，这里是原文替换（String.replace是按正则替换）
	 * @param src 原字符串
	 * @param olds 被替换的字符串
	 * @param news 新替换的字符串
	 * @return 结果
	 */
	public static String replace(String src, String olds, String news)
	{
		int start = src.indexOf(olds);
		return src.substring(0, start) + news.toString() + src.substring(start + olds.length(), src.length());
	}

	/**
	 * 格式化日间  （英文环境）
	 * @param time 日间 
	 * @param format 格式化样式
	 * @return 字符串时间格式
	 */
	public static String dateFormatEn(Date time, String format)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.ENGLISH);
		return sdf.format(time);
	}

	/**
	 * 将时间转为格式 2014-01-02 14:11:49
	 */
	public static String getTimeStampString(Timestamp timestamp)
	{
		if (timestamp == null)
		{
			return null;
		}
		//2014-01-02 14:11:49.128
		String str = timestamp.toString();
		return str.substring(0, 19);
	}

	/**
	 * 将时间转为格式13464949898498L
	 */
	public static long getTimeLong(Timestamp timestamp)
	{
		if (timestamp == null)
		{
			return 0L;
		}
		return timestamp.getTime();
	}

	/**
	 * 将时间转为格式 2014-01-02
	 */
	public static String getTimeStampDay(Timestamp timestamp)
	{
		if (timestamp == null)
		{
			return null;
		}
		//2014-01-02 14:11:49.128
		String str = timestamp.toString();
		return str.substring(0, 10);
	}

	/**
	 * 将时间转为格式 14:11:49
	 */
	public static String getTimeStampTime(Timestamp timestamp)
	{
		if (timestamp == null)
		{
			return null;
		}
		//2014-01-02 14:11:49.128
		String str = timestamp.toString();
		return str.substring(11, 16);
	}

	/**
	 * 将时间转为格式 2014-01-02 14:11:49
	 */
	public static String getTimeStampString(long time)
	{
		//2014-01-02 14:11:49.128
		String str = new Timestamp(time).toString();
		return str.substring(0, 19);
	}

	/**
	 * 包装一层,以免tostring方法改变
	 */
	public static String getTimeStampToString(Timestamp timestamp)
	{
		return timestamp.toString();
	}

	/**
	 * 返回格式 XXXX年XX月XX日 XX:XX
	 */
	public static String getTimeCh(Timestamp time)
	{
		if (time == null)
		{
			return null;
		}
		return getTimeStampToString(time).substring(0, 16).replaceFirst("-", "年").replace("-", "月").replace(" ", "日 ");
	}

	public static String getTimeString()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(new Date());
	}

	/**
	 * 字符是否为数字
	 * @param ch 字符
	 * @return true，是；false，不是
	 */
	public static boolean isNumber(char ch)
	{
		return ch >= 48 && ch <= 57;
	}

	public static boolean isNumber(String str)
	{
		if (isNull(str))
		{
			return false;
		}
		for (int i = 0; i < str.length(); i++)
		{
			if (!ObjectUtil.isNumber(str.charAt(i)))
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * 验证是否为数字或浮点数
	 * @param value 待验证字符串
	 * @return true，是；false，不是
	 */
	public static boolean isDouble(String value)
	{
		if (value == null)
			return false;
		return value.matches("^([+-])?[0-9]+(.[0-9]+)?$");
	}

	/**
	 * <div style='font-size:1.1em'>
	 *     <b>说明</b>
	 * <p>
	 *   数据类型转换,主要用于String<=>Number<br/>
	 *   如果是自定义类型，必须有String类型的构造参数
	 * </p>
	 * </div>
	 * @param 
	 * value 要转换的对象
	 * @param 
	 * clazz 转换的目标类型
	 * @return 指定类型的对象
	 * @author Bob
	 * @since 2008-02-18 v1.0加入
	 */
	public static <T> T convert(Object value, Class<T> clazz)
	{
		if (isNotNull(value) && isDouble(String.valueOf(value)))
		{
			try
			{
				String val = String.valueOf(value);
				Constructor<T> constructor = clazz.getConstructor(new Class[] { String.class });
				return constructor.newInstance(val);
			}
			catch (SecurityException e)
			{
			}
			catch (NoSuchMethodException e)
			{
			}
			catch (IllegalArgumentException e)
			{
			}
			catch (InstantiationException e)
			{
			}
			catch (IllegalAccessException e)
			{
			}
			catch (InvocationTargetException e)
			{
			}
		}
		return null;
	}

	/**
	 * 是不是英文字母
	 * @param ch 字符
	 * @return true，是；false，不是
	 */
	public static boolean isEngLetter(char ch)
	{
		return (ch >= 65 && ch <= 90) || (ch >= 97 && ch <= 122);
	}

	/**
	 * 是否纯英文字符
	 * @param str 字符串
	 * @return true，是；false，不是
	 */
	public static boolean isEnglish(String str)
	{
		if (ObjectUtil.isNotNull(str))
		{
			for (int i = 0; i < str.length(); i++)
			{
				char ch = str.charAt(i);
				if (!ObjectUtil.isEngLetter(ch))
				{
					return false;
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * 判断字符是不是中文
	 * @param oneChar 字符
	 * @return true，中文；false，不是中文
	 */
	public static boolean isChinese(char oneChar)
	{
		if ((oneChar >= '\u4e00' && oneChar <= '\u9fa5') || (oneChar >= '\uf900' && oneChar <= '\ufa2d'))
		{
			return true;
		}
		return false;
	}

	/**
	 * 字符串是否仅包含中文或英文
	 * @param str 字符串
	 * @return true，是；false，不是
	 */
	public static boolean isChineseOrEnglish(String str)
	{
		if (ObjectUtil.isNotNull(str))
		{
			for (int i = 0; i < str.length(); i++)
			{
				char ch = str.charAt(i);
				if (!ObjectUtil.isChinese(ch) && !ObjectUtil.isEngLetter(ch))
				{
					return false;
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * 将数组转换成set
	 * @param <T> 数据类型
	 * @param ay 数组
	 * @return set
	 */
	public static <T> Set<T> arrayToSet(T[] ay)
	{
		if (ay == null)
		{
			return null;
		}
		Set<T> set = new HashSet<T>(ay.length);
		for (T t : ay)
		{
			if (ObjectUtil.isNotNull(t))
			{
				set.add(t);
			}
		}
		return set;
	}

	/**
	 * 将set转换成list
	 * @param <T> 数据类型
	 * @param ay set
	 * @return set
	 */
	public static <T> List<T> setToList(Set<T> ay)
	{
		if (ay == null)
		{
			return null;
		}
		List<T> list = new ArrayList<T>(ay.size());
		for (T t : ay)
		{
			list.add(t);
		}
		return list;
	}

	/**
	 * 将list转换成set
	 * @param <T> 数据类型
	 * @param ay set
	 * @return set
	 */
	public static <T> Set<T> listToSet(List<T> ay)
	{
		if (ay == null)
		{
			return null;
		}
		Set<T> set = new HashSet<T>(ay.size());
		for (T t : ay)
		{
			set.add(t);
		}
		return set;
	}

	/**
	 * 通过分隔符分隔得到列表
	 * @param str 字符串
	 * @param spt 分隔符
	 * @return 列表
	 */
	public static List<String> listBySplit(String str, String spt)
	{
		if (ObjectUtil.isNull(str))
		{
			return null;
		}
		String[] strs = str.split(spt);
		List<String> list = new ArrayList<String>(strs.length);
		for (String s : strs)
		{
			if (ObjectUtil.isNotNull(s))
			{
				list.add(s);
			}
		}
		return list;
	}

	/**
	 * 将集合通过分隔符拼接获得字符串
	 * @param list 集合
	 * @param spt 分隔符
	 * @return 字符串
	 */
	public static <T> String strBySplit(Collection<T> coll, String spt)
	{
		if (coll == null || coll.isEmpty())
		{
			return null;
		}
		StringBuilder sb = new StringBuilder();
		Iterator<T> iter = coll.iterator();
		int i = 0;
		while (iter.hasNext())
		{
			T t = iter.next();
			if (i != coll.size() - 1)
			{
				sb.append(t).append(spt);
			}
			else
			{
				sb.append(t);
			}
			i++;
		}
		return sb.toString();
	}

	public static String join(CharSequence delimiter, Object[] tokens)
	{
		StringBuilder sb = new StringBuilder();
		boolean firstTime = true;
		for (Object token : tokens)
		{
			if (firstTime)
			{
				firstTime = false;
			}
			else
			{
				sb.append(delimiter);
			}
			sb.append(token);
		}
		return sb.toString();
	}

	/**
	 * 去掉价格后面的0；100.20->100.2  100.00->100
	 */
	public static String getPrice(String str)
	{
		if (isNull(str))
		{
			return "0";
		}
		if (str.indexOf('.') == -1)
		{
			return str;
		}
		String price = null;
		int end = str.length() - 1;
		for (int i = end; i >= 0; i--)
		{
			if (str.charAt(i) == '.')
			{
				price = str.substring(0, i);
				break;
			}
			else if (str.charAt(i) != '.' && str.charAt(i) != '0')
			{
				price = str.substring(0, i + 1);
				break;
			}
		}
		return price;
	}

	/**
	 * 去掉价格后面的0；100.20->100.2  100.00->100
	 */
	public static String getPrice(BigDecimal price)
	{
		return getPrice(getString(price));
	}

	/**
	 * 是否是钱的格式
	 */
	public static boolean isMoney(String money)
	{
		int pcount = 0;
		int index = 0;
		for (int i = 0; i < money.length(); i++)
		{
			char c = money.charAt(i);
			if (c != '.' && !isNumber(c))
			{
				return false;
			}
			if (c == '.')
			{
				index = i;
				pcount++;
			}
			if (pcount > 1)
			{
				return false;
			}
		}
		if (pcount == 1)
		{
			String str = money.substring(index + 1);
			if (str.length() > 2)
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * 通过IP计算获取其整形
	 */
	public static long getLongByIp(String ip)
	{
		String[] strs = ip.split("\\.");
		long i1 = Long.parseLong(strs[0]) << 24;
		long i2 = Long.parseLong(strs[1]) << 16;
		long i3 = Long.parseLong(strs[2]) << 8;
		long i4 = Long.parseLong(strs[3]);
		return i1 | i2 | i3 | i4;
	}

	public static String getSecondBetweenDate(Date begin, Date end)
	{
		String str = new String();
		if (ObjectUtil.isNotNull(begin) && ObjectUtil.isNotNull(end))
		{
			str = ObjectUtil.getString((end.getTime() - begin.getTime()) / 1000);
		}
		else
		{
			str = "";
		}
		return str;
	}

	/**
	 * 获取当前精确时间(yyyy-MM-dd HH:mm:ss.SSS)
	 */
	public static String getDetailTime()
	{
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
	}

	/**
	 * 过滤掉特殊字符
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static String StringFilter(String str) throws Exception
	{
		// 只允许字母和数字       
		// String   regEx  =  "[^a-zA-Z0-9]";                     
		// 清除掉所有特殊字符  
		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

	/**
	 * 严正名字中是否有特殊字符
	 * @param str
	 * @return
	 */
	public static boolean isConSpeCharacters(String str)
	{
		if (str.replaceAll("[\u4e00-\u9fa5]*[a-z]*[A-Z]*\\s*", "").length() == 0)
		{
			//不包含特殊字符 
			return false;
		}
		return true;
	}

	public static boolean isNumeric(String str)
	{
		for (int i = 0; i < str.length(); i++)
		{
			System.out.println(str.charAt(i));
			if (!Character.isDigit(str.charAt(i)))
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断字符串中是否包含阿拉伯数字
	 * @param content
	 * @return
	 */
	public static boolean hasDigit(String content)
	{

		boolean flag = false;

		Pattern p = Pattern.compile(".*\\d+.*");

		Matcher m = p.matcher(content);

		if (m.matches())

			flag = true;

		return flag;

	}

	/**
	 * 判断姓名是否正确
	 * @param str
	 * @return
	 */
	public static boolean checkName(String str)
	{
		if (isNotNull(str))
		{
			if (isNull(str.replace(" ", "")))
			{
				return false;
			}
			if (str.replace(" ", "").length() <= 1)
			{
				return false;
			}
			if (hasDigit(str))
			{
				return false;
			}
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * Map 按照key的降序排序
	 * @param oriMap
	 * @return oriMap
	 * @author Administrator
	 */
	public static Map<Integer, Float> sortMapByKey(Map<Integer, Float> oriMap)
	{
		if (oriMap == null || oriMap.isEmpty())
		{
			return null;
		}
		Map<Integer, Float> sortedMap = new java.util.TreeMap<Integer, Float>(new Comparator<Integer>()
		{
			@Override
			public int compare(Integer key1, Integer key2)
			{
				int intKey1 = 0, intKey2 = 0;
				try
				{
					intKey1 = key1;
					intKey2 = key2;
				}
				catch (Exception e)
				{
					intKey1 = 0;
					intKey2 = 0;
				}
				return intKey2 - intKey1;
			}

		});
		sortedMap.putAll(oriMap);
		return sortedMap;
	}

	/**
	 * 两个整数相除求百分数
	 * @param n1
	 * @param n2
	 * @return
	 */
	public static String getPercent(int n1, int n2)
	{
		DecimalFormat df = new DecimalFormat("0.00");//格式化小数
		float num = (float) n1 / n2;
		return df.format(num * 100) + "%";
	}

	/**
	 * map 拼接字符串 
	 */
	public static String createLinkString(Map<String, String> params)
	{
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < keys.size(); i++)
		{
			String key = keys.get(i);
			String value = params.get(key);
			// 签名字段不参加签名，其余均参加签名
			if ("sid".equals(key))
			{
				continue;
			}
			if (i == keys.size() - 1)
			{// 拼接时，不包括最后一个&字符
				builder.append(key).append("=").append(value);
			}
			else
			{
				builder.append(key).append("=").append(value).append("&");
			}
		}
		return builder.toString();
	}

	/**
	 * 验证乘客名字是否正确
	 */
	public static boolean checkName2(String str)
	{
		return str.matches("^[a-zA-Z．·\\.\\s\u3400-\u9FFF]+$");
	}
	
	public static boolean checkNameV3(String str)
	{
		return str.matches("^[a-zA-Z．·\\.\\s\u3400-\u9FFF]+$") && str.length()>1;
	}

	/**
	 * CHECK港澳通行证NO是否正确
	 * @param args
	 */
	public static boolean checkGatxz(String str)
	{
		return str.matches("[HMhmCc]{1}([0-9]{10}|[0-9]{8})$");
	}

	/**
	 * CHECK护照NO是否正确
	 * @param args
	 */
	public static boolean checkHz(String str)
	{
		return str.matches("^[a-zA-Z0-9]{5,17}$");
	}

	/**
	 * CHECK台湾通行证NO是否正确
	 * @param args
	 */
	public static boolean checkTwtxz(String str)
	{
		return str.matches("^[0-9]{8}$") || str.matches("^[0-9]{10}$");
	}

	/**
	 * 金额保留小数位
	 * @param decl
	 * @return
	 */
	public static String decimalFormat(BigDecimal decl)
	{
		DecimalFormat df = new DecimalFormat("0.00"); // 保留几位小数
		return df.format(decl);
	}

	/**
	 * 从map中获取String
	 * @param map
	 * @param title
	 * @return
	 */
	public static String getMapString(Map<String, Object> map, String title)
	{
		return getString(map.get(title));
	}

	public static Timestamp getMapTimestamp(Map<String, Object> map, String title)
	{
		return getTimestamp(map.get(title));
	}

	public static BigDecimal getMapBigDecimal(Map<String, Object> map, String title)
	{
		return getBigDecimal(map.get(title));
	}

	public static long getMapLong(Map<String, Object> map, String title)
	{
		return getLong(map.get(title));
	}

	public static int getMapInt(Map<String, Object> map, String title)
	{
		return getInt(map.get(title));
	}

	/**
	 * map 按key 首字母排序（首字母相同，比较第二个字母...）
	 * @param map
	 * @return sortMap
	 */
	public static Map<String, String> sortMapString(Map<String, String> map)
	{
		Map<String, String> sortedMap = new java.util.TreeMap<String, String>(new Comparator<String>()
		{
			Collator collator = Collator.getInstance(java.util.Locale.CHINA);

			@Override
			public int compare(String key1, String key2)
			{
				if (this.collator.compare(key1, key2) > 0)
				{
					return 1;
				}
				else
				{
					return -1;
				}
			}

		});
		sortedMap.putAll(map);
		return sortedMap;
	}

	/**
	 * map 按key 首字母排序（首字母相同，比较第二个字母...）
	 * @param map
	 * @return sortMap
	 */
	public static Map<String, Object> sortMapObject(Map<String, Object> map)
	{
		Map<String, Object> sortedMap = new java.util.TreeMap<String, Object>(new Comparator<String>()
		{
			Collator collator = Collator.getInstance(java.util.Locale.CHINA);

			@Override
			public int compare(String key1, String key2)
			{
				if (this.collator.compare(key1, key2) > 0)
				{
					return 1;
				}
				else
				{
					return -1;
				}
			}

		});
		sortedMap.putAll(map);
		return sortedMap;
	}

	public static String insertSymbol(String s, String symbol)
	{
		if (s == null)
		{
			return s;
		}
		char[] cs = s.toCharArray();
		StringBuilder sb = new StringBuilder();
		for (char c : cs)
		{
			sb.append(c);
			sb.append(" ");
		}
		return sb.toString();
	}

	/**
	 * 整数相除，求百分比（保留两位小数）
	 * @param a
	 * @param b
	 * @return
	 */
	public static String intDivision(int a, int b)
	{
		DecimalFormat df = new DecimalFormat("0.00");//格式化小数
		float buySuccPro = (float) a / (b);
		return df.format(buySuccPro * 100) + "%";//买票成功率
	}

	/**
	 * 去除首尾空白字符
	 * @param str
	 * @return
	 */
	public static String trim(String str)
	{
		if (isNull(str))
		{
			return str;
		}
		return str.trim();
	}

	/**
	 * 比较时间大小
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static int compareTime2(String time1, String time2, String format)
	{
		DateFormat df = new SimpleDateFormat(format);
		try
		{
			Date dt1 = df.parse(time1);
			Date dt2 = df.parse(time2);
			if (dt1.getTime() > dt2.getTime())
			{
				return 1;
			}
			else if (dt1.getTime() < dt2.getTime())
			{
				return -1;
			}
			else
			{
				return 0;
			}
		}
		catch (Exception exception)
		{
			exception.printStackTrace();
		}
		return 0;
	}

	/**
	 * 替换字符串中的中文
	 * @param source  字符串
	 * @param replacement 替换成的字符
	 * @return
	 */
	public static String replaceChinese(String source, String replacement)
	{
		String reg = "[\u4e00-\u9fa5]";
		Pattern pat = Pattern.compile(reg);
		Matcher mat = pat.matcher(source);
		String repickStr = mat.replaceAll(replacement);
		return repickStr;
	}

	/**
	 * 替换敏感信息方法，如手机号中间4位用*号隐去，若传入字符串为空，或长度小于begin+len，返回原字符串
	 * @param src 原字符串
	 * @param ch 替换敏感内容的字符
	 * @param begin 开始替换的位置，从0开始算
	 * @param len 替换长度
	 * @return 处理后的字符串
	 */
	public static String getSensitiveStr(String src, String ch, int begin, int len)
	{
		if (StringUtils.isEmpty(src) || src.length() < begin + len)
			return src;
		StringBuilder sb = new StringBuilder(src.substring(0, begin));
		for (int i = 0; i < len; i++)
			sb.append(ch);
		if (src.length() > begin + len)
			sb.append(src.substring(begin + len));
		return sb.toString();
	}

	/**
	 * 根据正则返回匹配内容
	 * @param regex
	 * @param content
	 * @return
	 */
	public static String regString(String regex, String content)
	{
		Pattern pa = Pattern.compile(regex);
		Matcher ma = pa.matcher(content);
		if (ma.find())
		{
			return ma.group();
		}
		return "";
	}
	
	private static final String CHN_NUMBERS = "零一二三四五六七八九十";
	/**
	 * 以"零一二三四五六七八九十"显示数字。
	 * n<0 或者 n>10 :返回阿拉伯数字。
	 * @param n 
	 * @return
	 */
	public static String getChnNumber(int n)
	{
		if(n<0 || n>10)
		{
			return ""+n;
		}
		return CHN_NUMBERS.substring(n, n+1);
	}
	private static  ObjectMapper objectMapper;
	/**
	 * json字符串转对象
	 * @param jsonStr
	 * @param valueType
	 * @return
	 * @throws IOException
	 */
	public static <T> T deserialize(String jsonStr,Class<T> valueType) throws IOException
	{
		if (isNotNull(jsonStr))
		{
			if(objectMapper==null)
			{
				objectMapper=new ObjectMapper();
			}
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			return objectMapper.readValue(jsonStr, valueType);
		}
		return null;
	}
	/**
	 * 对象转json字符串
	 * @param obj
	 * @return
	 * @throws JsonProcessingException
	 */
	public static String serialize(Object obj) throws JsonProcessingException
	{
		if(obj!=null)
		{
			if(objectMapper==null)
			{
				objectMapper=new ObjectMapper();
			}
			objectMapper.setSerializationInclusion(Include.NON_NULL);
			return objectMapper.writeValueAsString(obj);
		}
		return null;
	}
	public static <T> void addIfAbsent(List<T> list,T t)
	{
		if(!list.contains(t))
		{
			list.add(t);
		}
	}
}
