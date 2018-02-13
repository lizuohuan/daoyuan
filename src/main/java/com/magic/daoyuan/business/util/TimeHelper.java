package com.magic.daoyuan.business.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期类
 * Created by Eric Xie on 2017/12/21 0021.
 */
public class TimeHelper {


    /**
     * 获取两个日期之前的周 每周的开始日期和结束日期
     * @param start
     * @param end
     */
    public static List<Map<String,Date>> getWeekDate(String start,String end) throws Exception{

        List<String> betweenDates = getBetweenDates(start, end);// 两个日期之前的所有天数
        List<Map<String,Date>> result = new ArrayList<Map<String, Date>>();
        for (String betweenDate : betweenDates) {
            // 求每一天所在的周
            Map<String, Date> firstAndLastOfWeek = getFirstAndLastOfWeek(betweenDate);
            boolean isExist = false;
            for (Map<String, Date> stringMap : result) {
                if(firstAndLastOfWeek.get("startTime").equals(stringMap.get("startTime"))
                        && firstAndLastOfWeek.get("endTime").equals(stringMap.get("endTime"))){
                    isExist = true;
                    break;
                }
            }
            if(!isExist){
                result.add(firstAndLastOfWeek);
            }
        }
        return result;
    }

    /**
     * 获取指定日期所在周的第一天和最后一天
     * @param dataStr
     * @return
     * @throws ParseException
     */
    public static Map<String,Date> getFirstAndLastOfWeek(String dataStr) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(dataStr));
        int d = 0;
        if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
            d = -6;
        } else {
            d = 2 - cal.get(Calendar.DAY_OF_WEEK);
        }
        cal.add(Calendar.DAY_OF_WEEK, d);
        // 所在周开始日期
        Date data1 = cal.getTime();
        cal.add(Calendar.DAY_OF_WEEK, 6);
        // 所在周结束日期
        Date data2 = cal.getTime();
        Map<String,Date> result = new HashMap<String, Date>();
        result.put("startTime",data1);
        result.put("endTime",data2);
        return result;
    }


    /**
     * 获取两个日期之间的月份
     *
     * @param minDate
     * @param maxDate
     * @return
     * @throws ParseException
     */
    public static List<String> getMonthBetween(String minDate, String maxDate) throws ParseException {

        if (CommonUtil.isEmpty(minDate, maxDate)) {
            // 如果其中一个为null 则默认当前时间年的所有月份
            Calendar instance = Calendar.getInstance();
            instance.setTime(new Date());
            int i = instance.get(Calendar.YEAR);
            minDate = i + "-01";
            maxDate = i + "-12";
        }

        ArrayList<String> result = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");//格式化为年月

        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();

        min.setTime(sdf.parse(minDate));
        min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

        max.setTime(sdf.parse(maxDate));
        max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

        Calendar curr = min;
        while (curr.before(max)) {
            result.add(sdf.format(curr.getTime()));
            curr.add(Calendar.MONTH, 1);
        }

        return result;
    }

    /**
     * 获取两个日期之间的日期
     *
     * @param start 开始日期
     * @param end   结束日期
     * @return 日期集合
     */
    public static List<String> getBetweenDates(String start, String end) throws Exception {

        if (CommonUtil.isEmpty(start, end)) {
            // 如果其中一个为null 则默认当前时间月的所有天数
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cale = Calendar.getInstance();
            cale.setTime(new Date());
            cale.add(Calendar.MONTH, 0);
            cale.set(Calendar.DAY_OF_MONTH, 1);
            start = format.format(cale.getTime());
            // 获取前月的最后一天
            cale = Calendar.getInstance();
            cale.add(Calendar.MONTH, 1);
            cale.set(Calendar.DAY_OF_MONTH, 0);
            end = format.format(cale.getTime());
        }

        ArrayList<String> result = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//格式化为年月
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(sdf.parse(start));

        Calendar tempEnd = Calendar.getInstance();
        tempEnd.setTime(sdf.parse(end));
        tempEnd.add(Calendar.DAY_OF_MONTH, 1);

        Calendar curr = tempStart;
        while (curr.before(tempEnd)) {
            result.add(sdf.format(curr.getTime()));
            curr.add(Calendar.DAY_OF_YEAR, 1);
        }
        return result;
    }


    /**
     *  获取 指定日期的第一天
     * @param date
     * @return
     */
    public static Date getFirstDayOfMonth(Date date) throws Exception {
        Calendar cale = Calendar.getInstance();
        if (null == date) {
            date = new Date();
        }
        cale.setTime(date);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        return format.parse(format.format(cale.getTime()));
    }


    /**
     * 获取指定日期的最后一天日期
     * @param date
     * @return
     */
    public static Date getLastDayOfMonth(Date date) throws Exception {
        Calendar cale = Calendar.getInstance();
        if (null == date) {
            date = new Date();
        }
        cale.setTime(date);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 1);
        cale.set(Calendar.DAY_OF_MONTH, 0);
        cale.set(Calendar.HOUR_OF_DAY,23);
        cale.set(Calendar.MINUTE,59);
        cale.set(Calendar.SECOND,59);
        return format.parse(format.format(cale.getTime()));
    }



    /**
     * 获取某年第一天日期
     *
     * @param year 年份
     * @return Date
     */
    public static Date getYearFirst(Integer year) {
        if (null == year) {
            year = new Date().getYear();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();
        return currYearFirst;
    }

    /**
     * 获取某年第一天日期
     *
     * @return Date
     */
    public static Date getYearFirst() {
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, now.get(Calendar.YEAR));
        Date currYearFirst = calendar.getTime();
        return currYearFirst;
    }

    /**
     * 获取某年最后一天日期
     *
     * @param year 年份
     * @return Date
     */
    public static Date getYearLast(Integer year) {
        if (null == year) {
            year = new Date().getYear();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearLast = calendar.getTime();
        return currYearLast;
    }

    /**
     * 获取某年最后一天日期
     *
     * @return Date
     */
    public static Date getYearLast() {
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, now.get(Calendar.YEAR));
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearLast = calendar.getTime();
        return currYearLast;
    }


    public static Date getDateByDay(Date date,Integer day){
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.set(Calendar.DAY_OF_MONTH,day);
        return instance.getTime();
    }

    /**
     * 获取当前时间的近 month 个月的日期
     * @param date
     * @param day
     */
    public static List<Date> getNearMonth(Date date,Integer day,int month) throws Exception{
        List<Date> dates = new ArrayList<Date>();
        date = null == date ? new Date() : date;
        Date firstDayOfMonth = getFirstDayOfMonth(date); //
        Calendar instance = Calendar.getInstance();
        instance.setTime(firstDayOfMonth);
        int i = 0;
        while (i < month){
            Date lastDayOfMonth = getLastDayOfMonth(instance.getTime()); // 本日期的最后一天
            Calendar last = Calendar.getInstance();
            last.setTime(lastDayOfMonth);
            int lastDay = last.get(Calendar.DAY_OF_MONTH);
            if(day <= lastDay){
                instance.set(Calendar.DAY_OF_MONTH,day);
            }
            else {
                instance.set(Calendar.DAY_OF_MONTH,lastDay);
            }
            dates.add(instance.getTime());
            // 重置日期
            instance.set(Calendar.DAY_OF_MONTH,1);
            instance.add(Calendar.MONTH,-1);
            i++;
        }
        return dates;
    }


    public static void main(String[] args) throws Exception {
        List<Date> nearMonth = getNearMonth(null, 31, 6);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (Date date : nearMonth) {
            System.out.println(sdf.format(date));
        }
//        System.out.println(getLastDayOfMonth(new Date()));
    }


}
