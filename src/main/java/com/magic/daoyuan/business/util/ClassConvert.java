package com.magic.daoyuan.business.util;

import java.util.*;

/**
 * 转换
 * @author lzh
 * @create 2017/6/27 15:08
 */
public class ClassConvert {

    /**
     * String数组转Long数组
     * @param strs
     * @return
     */
    public static Long[] strToLongGather(String[] strs) {
        Long[] ls = new Long[strs.length];
        for (int i = 0 ; i < strs.length ; i ++) {
            ls[i] = Long.valueOf(strs[i]);
        }
        return ls;
    }

    /**
     * String数组转Integer数组
     * @param strs
     * @return
     */
    public static Integer[] strToIntegerGather(String[] strs) {
        Integer[] ls = new Integer[strs.length];
        for (int i = 0 ; i < strs.length ; i ++) {
            ls[i] = Integer.parseInt(strs[i]);
        }
        return ls;
    }
    /**
     * String数组转Integer集合
     * @param strs
     * @return
     */
    public static List<Integer> strToIntegerListGather(String[] strs) {
        List<Integer> ls = new ArrayList<Integer>();
        for (int i = 0 ; i < strs.length ; i ++) {
            ls.add(Integer.parseInt(strs[i]));
        }
        Collections.sort(ls, new Comparator<Integer>() {
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });
        return ls;
    }
    /**
     * String数组转Set集合
     * @param strs
     * @return
     */
    public static Set<Integer> strToIntegerSetGather(String[] strs) {
        List<Integer> ls = new ArrayList<Integer>();
        for (int i = 0 ; i < strs.length ; i ++) {
            ls.add(Integer.parseInt(strs[i]));
        }
        Collections.sort(ls, new Comparator<Integer>() {
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });
        return new HashSet<Integer>(ls);
    }

    /**
     * 时间戳集合 转date集合
     * @param str
     * @return
     */
    public static List<Date> strListToDateListGather(List<Long> str) {
        List<Date> list = new ArrayList<Date>();
        for (int i = 0 ; i < str.size() ; i ++) {
            list.add(new Date(Long.parseLong(str.get(i).toString())));
        }

        return list;
    }
}
