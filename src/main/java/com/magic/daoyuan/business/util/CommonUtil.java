package com.magic.daoyuan.business.util;



import com.alibaba.fastjson.JSONArray;
import com.magic.daoyuan.business.enums.TimeField;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * 通用工具
 * Created by Eric Xie on 2017/2/13 0013.
 */
public class CommonUtil {


    /**
     * 获取业务月集合
     * @param startDate 业务月开始时间
     * @param cycle 周期
     * @param size 需要周期数量
     * @return
     */
    public static List<Date> getMonth(Date startDate,Integer cycle,Integer size,Date currentDate){
        List<Date> dateList = new ArrayList<Date>();
        // 循环设置时间
        Date start = DateUtil.getDateByConfig(startDate);
        currentDate = DateUtil.getDateByConfig(currentDate);
        for (int i = 0; i < size; i++) {
            if(currentDate.getTime() == start.getTime()){
                dateList.add(start);
                start = DateUtil.addDate(start, TimeField.Month.ordinal(),cycle);
                continue;
            }
            if(currentDate.getTime() > start.getTime() ){
                Date tempStart = start;
                while (true){
                    Date temp = DateUtil.addDate(tempStart, TimeField.Month.ordinal(),cycle);
                    temp = DateUtil.getDateByConfig(temp);
                    if(currentDate.getTime() <= temp.getTime()){
                        start = temp;
                        break;
                    }
                    tempStart = temp;
                }
            }
            if(i >= 0){
                dateList.add(start);
            }
            start = DateUtil.addDate(start, TimeField.Month.ordinal(),cycle);
        }
       return dateList;
    }

    public static void main(String[] args) throws ParseException {
        List<Date> dateList = getMonth(Timestamp.parseDate2("2017-12","yyyy-MM"),12,1,Timestamp.parseDate2("2018-12","yyyy-MM"));
        for (Date date : dateList) {
            System.out.println(Timestamp.DateTimeStamp(date,"yyyy-MM-dd"));
        }

    }


    public static String formatPayPlace(String payPlaceName){
        return null != payPlaceName ? payPlaceName.replace("中国,","").replace("省","") : "";
    }


    public static String formatPayPlace2(String cityName){
        if(isEmpty(cityName)){
            return "";
        }
        String s = cityName.substring(2, cityName.length()).replaceAll(",", "");
        if(s.indexOf("省") < 0 ){
            s = s.substring(2,s.length());
        }
        return s;
    }


    public static boolean isEmpty(Object... args){
        for (Object arg : args) {
            if(null == arg){
                return true;
            }
            if(arg instanceof String ){
                if( ((String) arg).trim().length() == 0 || "null".equals(arg)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 不为空 为true
     * @param args
     * @return
     */
    public static boolean isEmpty2(Object... args){
        for (Object arg : args) {
            if(null != arg){
                return true;
            }
            if(arg instanceof String ){
                if( ((String) arg).trim().length() > 0 || !"null".equals(arg)){
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isIdCard(String idCard){

        String reg = "^\\d{15}$|^\\d{17}[0-9Xx]$";
        if (!idCard.matches(reg)) {
            System.out.println("Format Error!");
            return false;
        }
        return true;
    }




    /**
     *  获取手机号的后四位
     * @return
     */
    public static String subMobile(String mobile){
        if(null == mobile || mobile.length() <= 5){
            return "0000";
        }
        return mobile.substring(mobile.length() - 4);
    }

    /**
     *  订单号生成规则
     * @return
     */
    public static String buildOrderNumber(){
        SimpleDateFormat format = new SimpleDateFormat("yyMMddhhmmssSSSS");
        return format.format(new Date());
    }

    /**
     *  获取客户端的IP地址
//     * @param request
     * @return
     */
//    public static String getIpAddr(HttpServletRequest request) {
//        String ip = request.getHeader("x-forwarded-for");
//        if(ip == null || ip.length() == 0 ||"unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("Proxy-Client-IP");
//        }
//        if(ip == null || ip.length() == 0 ||"unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("WL-Proxy-Client-IP");
//        }
//        if(ip == null || ip.length() == 0 ||"unknown".equalsIgnoreCase(ip)) {
//            ip = request.getRemoteAddr();
//        }
//        return ip;
//    }
    public static String get32UUID(){
        return UUID.randomUUID().toString().trim().replaceAll("-", "");
    }


    public static String buildSerialNumber(Integer id){
        String num = "";
        if(id <= 9){
            num += "00"+id;
        }
        if(id >= 10 && id <= 99 ){
            num += "0"+id;
        }
        if(id > 99){
            num = id.toString();
        }
        return "D"+ DateUtil.DateTime(new Date(),"yyMM")+num;
    }


    public static String buildContractSerialNumber(Integer id){
        String num = "";
        if(id <= 9){
            num += "00"+id;
        }
        if(id >= 10 && id <= 99 ){
            num += "0"+id;
        }
        if(id > 99){
            num = id.toString();
        }
        return "F"+ DateUtil.DateTime(new Date(),"yyMM")+num;
    }


    /**
     * 生成票据 单据号
     * 规则：F00000001 / F00000099
     * @param billId
     * @return
     */
    public static String buildBillNumber(Integer billId){
        StringBuffer sb = new StringBuffer();
        sb.append("F");
        int zero = 8 - billId.toString().length() - 1;
        for (int i = 0; i < zero; i++){
            sb.append("0");
        }
        sb.append(billId);
        return sb.toString();
    }


    public static String getStringSuffix(String url){
        int i = url.lastIndexOf(".");
        if(i == -1){
            return "";
        }
        return url.substring(i,url.length()).split("\\.")[1];
    }


    public static String getServiceConfigName(Integer configId){
        String msg = "";
        switch (configId){
            case 1 : msg = "按人*服务月收费";break;
            case 2 : msg = "按人*服务月阶梯式收费";break;
            case 3 : msg = "服务类别";break;
            case 4 : msg = "服务区域";break;
            case 5 : msg = "按异动量收费";break;
            case 6 : msg = "整体打包";break;
            case 7 : msg = "按人数阶梯式整体打包";break;
        }
        return msg;
    }

    public static String getBusinessName(Integer businessId){
        String businessName = "";
        switch (businessId){
            case 1:
                businessName = "派遣";
                break;
            case 2:
                businessName = "外包";
                break;
            case 3:
                businessName = "社保";
                break;
            case 4:
                businessName = "公积金";
                break;
            case 5:
                businessName = "工资";
                break;
            case 6:
                businessName = "商业险";
                break;
            case 7:
                businessName = "一次性业务";
                break;
            default:
                businessName = "未知";
                break;
        }
        return businessName;
    }



}
