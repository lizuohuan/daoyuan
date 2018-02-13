package com.magic.daoyuan.business.util;

import com.magic.daoyuan.business.enums.TimeField;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *  日期格式化工具
 * @author QimouXie
 *
 */
public class DateUtil {
	
	
	 /**  
     * 计算两个日期之间相差的天数  
     * @param smdate 较小的时间 
     * @param bdate  较大的时间 
     * @return 相差天数 
     * @throws ParseException  
     */    
    public static int daysBetween(Date smdate,Date bdate) throws ParseException   {    
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        smdate=sdf.parse(sdf.format(smdate));  
        bdate=sdf.parse(sdf.format(bdate));  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(smdate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(bdate);    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
       return Integer.parseInt(String.valueOf(between_days));           
    }    

	
	/**
	 *  转换 日期为 yyyyMMdd
	 * @param date
	 * @return
	 */
	public static String DateToyyyyMMdd(Date date){
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		return format.format(date);
	}
	/**
	 *  转换 日期为 yyyyMM
	 * @param date
	 * @return
	 */
	public static String DateToyyyyMM(Date date){
		SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
		return format.format(date);
	}
	
	/**
	 *  将 时间戳转换成指定格式的日期类型
	 * @param millis
	 * @param formats
	 * @return
	 */
	public static Date longToDate(Long millis,String formats){
		Date result = null;
		if(null == millis || 0 == millis){
			return result;
		}
		try {
			Date date = new Date(millis);
			SimpleDateFormat format = new SimpleDateFormat(formats);
			result = format.parse(date.toString());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return result;
	}
	

	/**
	 *  将 时间戳转换成指定格式的日期类型
	 * @param millis
	 * @param formats
	 * @return
	 */
	public static Date longToDate(Date millis,String formats){
		Date result = null;
		if(null == millis ){
			return result;
		}
		try {
			SimpleDateFormat format = new SimpleDateFormat(formats);
			result = format.parse(millis.toString());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return result;
	}


	/**
	 *  转换 日期为 yyyyMM
	 * @param date
	 * @return
	 */
	public static String DateTime(Date date,String formats){
		SimpleDateFormat format = new SimpleDateFormat(formats);
		if(null == date){
			return null;
		}
		return format.format(date);
	}

	/**
	 *  转换 日期为 yyyyMM
	 * @param date
	 * @return
	 */
	public static String dateFormat(Date date,String formats){
		SimpleDateFormat format = new SimpleDateFormat(formats);
		if(null == date){
			date = new Date();
		}
		return format.format(date);
	}

	public static Date stringToDate(String yyyyMMddhhmmss){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = format.parse(yyyyMMddhhmmss);
		} catch (Exception e) {
			if(yyyyMMddhhmmss !=null && yyyyMMddhhmmss.matches("\\d{4}-\\d{2}-\\d{2}")){
				SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
				try {
					date = simple.parse(yyyyMMddhhmmss);
				} catch (ParseException ee) {
					return null;
				}
			}
		}
		return date;
	}
	
	public static Date getDate(String dateStr){
		Date date = null;
		if(dateStr !=null && dateStr.matches("\\d{4}-\\d{2}-\\d{2}")){
			SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
			try {
				date = simple.parse(dateStr);
			} catch (ParseException e) {
				return null;
			}
		}
		return date;
	}
	
	/**
	 *  获取 指定日期的 之后 之前 的几秒时间
	 * @param date
	 * @return
	 */
	public static Date getPreSecondDate(Date date,Integer second){
		if(null == date){
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.SECOND, second);
		return c.getTime();
	}
	
	public static Date getNextDate(Date currentDate,Integer days,Integer hours,Integer mins){
		if(null == currentDate ){
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(currentDate);
		c.add(Calendar.DAY_OF_MONTH, days);
		c.add(Calendar.HOUR_OF_DAY, hours);
		c.add(Calendar.MINUTE, mins);
		return c.getTime();
	}
	
	public static Date getNextDay(Date date,Integer days){
		if(null == date){
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH,days);
		return c.getTime();
	}
	public static Date stringToDate_(String yyyyMMddhhmmss){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date = null;
		try {
			date = format.parse(yyyyMMddhhmmss);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
	public static Date stringToDate_1(String dateStr) throws Exception{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return format.parse(dateStr);
	}



	public static Date setDate(Date date){
		Calendar calendar = Calendar.getInstance();

		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH,1);
		calendar.set(Calendar.HOUR_OF_DAY,0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND,0);
		calendar.set(Calendar.MILLISECOND,0);
		return calendar.getTime();
	}

	public static Integer getMill(Date date){

		if(null == date){
			return (int)(System.currentTimeMillis() / 1000);
		}
		Calendar calendar = Calendar.getInstance();

		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY,23);
		calendar.set(Calendar.MINUTE,59);
		calendar.set(Calendar.SECOND,59);
		return (int)(calendar.getTime().getTime() / 1000);
	}

//	public static Date simpleDate(Date date,String format){
//		if(null == date || CommonUtil.isEmpty(format)){
//			return new Date();
//		}
//
//	}

//	public static Date dateToDate(Date date,String format){
//
//		SimpleDateFormat format_ = new SimpleDateFormat(format);
//		Date date_ = null;
//		try {
//			date_ = format_.
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return date_;
//
//	}

	public static Date getDateByConfig(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date == null ? new Date() : date);
		calendar.set(Calendar.DAY_OF_MONTH,1);
		calendar.set(Calendar.HOUR_OF_DAY,0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND,0);
		calendar.set(Calendar.MILLISECOND,0);
		return calendar.getTime();
	}


	public static Date updateDate(Date currentDate,String timeStr){
		if(null == currentDate ){
			return null;
		}
		if(CommonUtil.isEmpty(timeStr)){
			return null;
		}
		String[] split = timeStr.split(":");

		Calendar c = Calendar.getInstance();
		c.setTime(currentDate);
		c.set(Calendar.HOUR_OF_DAY, Integer.valueOf(split[0]));
		c.set(Calendar.MINUTE,Integer.valueOf(split[1]));
		c.set(Calendar.SECOND,0);
		return c.getTime();
	}


	public static int getMonth(Date date){
		Calendar instance = Calendar.getInstance();
		instance.setTime(null == date ? new Date() : date);
		return instance.get(Calendar.MONTH) + 1;
	}





	public static Date addDate(Date date,Integer field,Integer value){
		Calendar instance = Calendar.getInstance();
		instance.setTime(null == date ? new Date() : date);
		switch (field){
			case 0 :
				instance.add(Calendar.YEAR,value);
				break;
			case 1 :
				instance.add(Calendar.MONTH,value);
				break;
			case 2 :
				instance.add(Calendar.DAY_OF_MONTH,value);
				break;
			case 3 :
				instance.add(Calendar.HOUR_OF_DAY,value);
				break;
			case 4 :
				instance.add(Calendar.MINUTE,value);
				break;
			case 5 :
				instance.add(Calendar.SECOND,value);
				break;
			default:
				break;
		}
		return instance.getTime();
	}

	public static Date setDate(Date date,Integer field,Integer value){
		Calendar instance = Calendar.getInstance();
		instance.setTime(null == date ? new Date() : date);
		switch (field){
			case 0 :
				instance.set(Calendar.YEAR,value);
				break;
			case 1 :
				instance.set(Calendar.MONTH,value - 1);
				break;
			case 2 :
				instance.set(Calendar.DAY_OF_MONTH,value);
				break;
			case 3 :
				instance.set(Calendar.HOUR_OF_DAY,value);
				break;
			case 4 :
				instance.set(Calendar.MINUTE,value);
				break;
			case 5 :
				instance.set(Calendar.SECOND,value);
				break;
			default:
				break;
		}
		return instance.getTime();
	}

	/**
	 * 计算两个月相差月份
	 * @return
     */
	public static int countMonth(Date date1,Date date2){
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(date1);
		c2.setTime(date2);
		int result = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
		int month = (c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR)) * 12;
		return Math.abs(result + month);
	}

	public static Date strToDate(String data,String resourceFormat){
		SimpleDateFormat format = new SimpleDateFormat(resourceFormat);
		try {
			return format.parse(data);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 *
	 * @param date
	 * @param flag 1 : 凌晨  0：24点
	 * @return
	 */
	public static int getDateStamp(Date date,Integer flag){
		Calendar instance = Calendar.getInstance();
		instance.setTime(null == date ? new Date() :  date);
		if(flag == 1){
			instance.set(Calendar.HOUR_OF_DAY,0);
			instance.set(Calendar.MINUTE,0);
			instance.set(Calendar.SECOND,0);
		}
		else{
			instance.set(Calendar.HOUR_OF_DAY,23);
			instance.set(Calendar.MINUTE,59);
			instance.set(Calendar.SECOND,59);
		}
		return (int)(instance.getTime().getTime() / 1000);
	}




	public static void main(String[] args)throws Exception  {

		System.out.println(getDateStamp(new Date(),1));
	}
	
	
}
