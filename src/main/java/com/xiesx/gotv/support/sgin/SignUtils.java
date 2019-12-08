package com.xiesx.gotv.support.sgin;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.codec.digest.DigestUtils;

public class SignUtils {

	/**
	 * 获取签名
	 * 
	 * @param params
	 * @param key
	 * @return
	 */
	public static String getSignature(Map<String, String> params, String key) {
		return DigestUtils.md5Hex(getSortParams(params) + "&key=" + key);
	}

	/**
	 * 按key进行正序排列，之间以&相连 <功能描述>
	 * 
	 * @param params
	 * @return
	 */
	public static String getSortParams(Map<String, String> params) {
		SortedMap<String, String> map = new TreeMap<String, String>();
		for (String key : params.keySet()) {
			map.put(key, params.get(key));
		}
		Set<String> keySet = map.keySet();
		Iterator<String> iter = keySet.iterator();
		String str = "";
		while (iter.hasNext()) {
			String key = iter.next();
			String value = map.get(key);
			str += key + "=" + value + "&";
		}
		if (str.length() > 0) {
			str = str.substring(0, str.length() - 1);
		}
		return str;
	}

	public static void main(String[] args) {
		Map<String, String> params = new HashMap<>();
		params.put("qq", "136305973");
		System.out.println(getSignature(params, "136305973"));

		params = new HashMap<>();
		params.put("url", "https://www.iqiyi.com/v_19rsmej7tw.html?vfm=2008_aldbd");
		System.out.println(getSignature(params, "136305973"));
		
		params = new HashMap<>();
		params.put("url", "https://v.youku.com/v_show/id_XNDE0ODQ5NzczNg==.html");
		System.out.println(getSignature(params, "136305973"));
	}
}
