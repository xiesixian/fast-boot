package com.xiesx.springboot.utils;

import java.util.List;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

/**
 * @title ArrayUtils
 * @description
 * @author XIE
 * @date 2020年4月24日下午10:11:16
 */
public class ArrayUtils {

    /**
     * list转str[]
     *
     * @param str
     * @return
     */
    public static String[] listToArray(List<String> list) {
        return list.toArray(new String[list.size()]);
    }

    /**
     * str[]转list
     *
     * @param str
     * @return
     */
    public static List<String> arrayToList(String[] str) {
        return Lists.newArrayList(str);
    }

    /**
     * str转list
     *
     * @param str
     * @return
     */
    public static List<String> splitToList(String str) {
        return splitToList(str, ",");
    }

    /**
     * str转list
     *
     * @param str
     * @param separator
     * @return
     */
    public static List<String> splitToList(String str, String separator) {
        return Splitter.on(separator).omitEmptyStrings().trimResults().splitToList(str);
    }

    /**
     * list转str
     *
     * @param str
     * @return
     */
    public static String joinTolist(List<String> list) {
        return joinTolist(list, ",");
    }

    /**
     * list转str
     *
     * @param str
     * @param separator
     * @return
     */
    public static String joinTolist(List<String> list, String separator) {
        if (list.isEmpty()) {
            return null;
        }
        return Joiner.on(separator).join(list);
    }

    public static void main(String[] args) {
        //
        List<String> list = Lists.newArrayList("1", "2", "3", "4");
        //
        String[] arry = listToArray(list);
        System.out.println(JSON.toJSONString(arry));
        list = arrayToList(arry);
        System.out.println(JSON.toJSONString(list));

        //
        String str1 = "1,2,3,4";
        //
        list = splitToList(str1);
        System.out.println(JSON.toJSONString(list));
        str1 = joinTolist(list);
        System.out.println(str1);

        //
        String str2 = "1-2-3-4";
        //
        list = splitToList(str2, "-");
        System.out.println(JSON.toJSONString(list));
        str2 = joinTolist(list, "-");
        System.out.println(str2);
    }
}
