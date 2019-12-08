package com.xiesx.gotv.utils;

import java.io.StringReader;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;

/**
 * @title IKanalyzerUtils.java
 * @description 分词器
 * @author Sixian.xie
 * @date 2019年1月17日 下午6:53:59
 */
@Slf4j
public class IKanalyzerUtils {

	public static final String DATA_JSON_SERVICES = "[{\"name\":\"网络\",\"y\":\"https://cdn.133.cn/gtgj/icon/server/2019/01/wifi_01.png\",\"n\":\"https://cdn.133.cn/gtgj/icon/server/2019/01/wifi_no_01.png\"},{\"name\":\"餐食小吃\",\"y\":\"https://cdn.133.cn/gtgj/icon/server/2019/01/meal_01.png\",\"n\":\"https://cdn.133.cn/gtgj/icon/server/2019/01/meal_no_01.png\"},{\"name\":\"快速安检\",\"y\":\"https://cdn.133.cn/gtgj/icon/server/2019/01/check_01.png\",\"n\":\"https://cdn.133.cn/gtgj/icon/server/2019/01/check_no_01.png\"},{\"name\":\"饮料茶水\",\"y\":\"https://cdn.133.cn/gtgj/icon/server/2019/01/drink_01.png\",\"n\":\"https://cdn.133.cn/gtgj/icon/server/2019/01/drink_no_01.png\"},{\"name\":\"高清电视\",\"y\":\"https://cdn.133.cn/gtgj/icon/server/2019/01/tv_01.png\",\"n\":\"https://cdn.133.cn/gtgj/icon/server/2019/01/tv_no_01.png\"},{\"name\":\"报刊杂志\",\"y\":\"https://cdn.133.cn/gtgj/icon/server/2019/01/book_01.png\",\"n\":\"https://cdn.133.cn/gtgj/icon/server/2019/01/book_no_01.png\"},{\"name\":\"航班动态\",\"y\":\"https://cdn.133.cn/gtgj/icon/server/2019/01/dynamic_01.png\",\"n\":\"https://cdn.133.cn/gtgj/icon/server/2019/01/dynamic_no_01.png\"},{\"name\":\"母婴\",\"y\":\"https://cdn.133.cn/gtgj/icon/server/2019/01/baby_01.png\",\"n\":\"https://cdn.133.cn/gtgj/icon/server/2019/01/baby_no_01.png\"},{\"name\":\"吸烟\",\"y\":\"https://cdn.133.cn/gtgj/icon/server/2019/01/smoke_01.png\",\"n\":\"https://cdn.133.cn/gtgj/icon/server/2019/01/smoke_no_01.png\"},{\"name\":\"登机提醒\",\"y\":\"https://cdn.133.cn/gtgj/icon/server/2019/01/remind_01.png\",\"n\":\"https://cdn.133.cn/gtgj/icon/server/2019/01/remind_no_01.png\"},{\"name\":\"沐浴设施\",\"y\":\"https://cdn.133.cn/gtgj/icon/server/2019/01/bath_01.png\",\"n\":\"https://cdn.133.cn/gtgj/icon/server/2019/01/bath_no_01.png\"},{\"name\":\"空调\",\"y\":\"https://cdn.133.cn/gtgj/icon/server/2019/01/airconditioner_01.png\",\"n\":\"https://cdn.133.cn/gtgj/icon/server/2019/01/airconditioner_no_01.png\"}]";

	/**
	 * 分词匹配([A,B,C]与ABC)
	 * 
	 * @param d
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public static List<String> ikanalyzer(String d, List<String> list) throws Exception {
		// 原始文本
		String ser = d;
		// 匹配服务
		List<String> data = Lists.newArrayList();
		// 所有服务 
		List<String> services = list;
		// 分词 与 原始文本匹配
		for (String str : services) {
			//记录分词结果
			List<String> temp = Lists.newArrayList();
			IKSegmenter ik = new IKSegmenter(new StringReader(str), false);
			Lexeme word = null;
			while ((word = ik.next()) != null) {
				temp.add(word.getLexemeText());
			}
			log.debug("结果：" + JSON.toJSONString(temp));
			int i = temp.size();
			for (String t : temp) {
				if (ser.contains(t)) {
					i--;//命中则减1
				}
			}
			if (i == 0) {//0则完全命中
				data.add(str);
				log.debug("-匹配:" + str);
			}
		}
		return data;
	}

	public static void main(String[] args) throws Exception {
//		// 所有服务
//		List<CipService> services = Lists.newArrayList(JSON.parseArray(DATA_JSON_SERVICES, CipService.class));
//		// 所有服务（名称）
//		List<String> service_names = Lists.transform(services, new Function<CipService, String>() {
//
//			@Override
//			public String apply(CipService input) {
//				return input.getName();
//			}
//		});
//		System.out.println(ikanalyzer("登机提醒饮料茶水高清电视报刊杂志航班动态 快速安检空调", service_names));
	}
}
