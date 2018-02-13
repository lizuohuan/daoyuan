package com.magic.daoyuan.business.service;

import com.magic.daoyuan.business.dto.CompanySonTotalBillDto;
import com.magic.daoyuan.business.entity.CompanyBillCount;
import com.magic.daoyuan.business.entity.CompanyCooperation;
import com.magic.daoyuan.business.entity.MemberCount;
import com.magic.daoyuan.business.enums.Common;
import com.magic.daoyuan.business.mapper.*;
import com.magic.daoyuan.business.util.TimeHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 统计业务处理
 * Created by Eric Xie on 2017/12/21 0021.
 */
@Service
public class StatisticsService {

    @Resource
    private ICompanyMapper companyMapper;
    @Resource
    private ICompanyCooperationMapper companyCooperationMapper;
    @Resource
    private IMemberCountMapper memberCountMapper;
    @Resource
    private ICompanySonTotalBillMapper companySonTotalBillMapper;
    @Resource
    private ICompanyBillCountMapper companyBillCountMapper;


    /**
     * 账单确认率
     * @param more 客户人数大于
     * @param less 客户人数小于
     * @param serviceAmount 上月服务费
     * @param day 日期
     * @param type 0 : 账单确认率  1：账单核销率
     */
    public Map<String,Object> billIdentificationRate(Integer more,Integer less,Double serviceAmount,Integer day,Integer type) throws Exception{

        Map<String,Object> map = new HashMap<String, Object>();
        map.put("more",null == more ? null : more);
        map.put("less",null == less ? null : less);
        map.put("type",type);
        // 近六个月的日期
        List<Date> nearMonth = TimeHelper.getNearMonth(new Date(), day, 6);
        map.put("endTime",nearMonth.get(0));
        map.put("startTime",nearMonth.get(nearMonth.size() - 1));
        map.put("serviceAmount",serviceAmount);
        List<CompanyBillCount> companyBillCounts = companyBillCountMapper.countCompanyBillCount(map);
        List<String> abscissa = new ArrayList<String>();
        List<Object> data = new ArrayList<Object>();
        List<Object> dataIsPeer = new ArrayList<Object>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (Date date : nearMonth) {
            abscissa.add(sdf.format(date));
            int count = 0;
            int countPeer = 0;
            for (CompanyBillCount companyBillCount : companyBillCounts) {
                if(sdf.format(date).equals(sdf.format(companyBillCount.getCreateTime()))){
                    count++;
                    if(null != companyBillCount.getIsPeer() && 1 == companyBillCount.getIsPeer()){
                        countPeer++;
                    }
                }
            }
            data.add(count);
            dataIsPeer.add(countPeer);
        }
        Map<String,Object> result = new HashMap<String, Object>();
        result.put("abscissa",abscissa);
        result.put("data",data);
        result.put("dataIsPeer",dataIsPeer);
        return result;
    }


    /**
     * 当月每天的账单确认率
     * @param type 0 : 账单确认率  1：账单核销率
     */
    public Map<String,Object> billIdentificationRateOfMonth(Integer type) throws Exception{
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("type",type);
        map.put("month",new Date());
        // 获取本月的所有天数
        List<String> nearMonth = TimeHelper.getBetweenDates(null,null);
        List<CompanyBillCount> companyBillCounts = companyBillCountMapper.countCompanyBillCount(map);
        List<String> abscissa = new ArrayList<String>();
        List<Object> data = new ArrayList<Object>();
        List<Object> dataIsPeer = new ArrayList<Object>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (String date : nearMonth) {
            abscissa.add(date);
            int count = 0;
            int countPeer = 0;
            for (CompanyBillCount companyBillCount : companyBillCounts) {
                if(date.equals(sdf.format(companyBillCount.getCreateTime()))){
                    count++;
                    if(null != companyBillCount.getIsPeer() && 1 == companyBillCount.getIsPeer()){
                        countPeer++;
                    }
                }
            }
            data.add(count);
            dataIsPeer.add(countPeer);
        }
        Map<String,Object> result = new HashMap<String, Object>();
        result.put("abscissa",abscissa);
        result.put("data",data);
        result.put("dataIsPeer",dataIsPeer);
        return result;
    }


    /**
     * 统计合作中的员工
     * @param flag 0 日  1 周  2 月
     * @param payPlaceId 缴金地ID
     * @param startTime 开始日期
     * @param endTime 截至日期
     * @param more 大于员工的数量
     * @param less 小于员工的数量
     */
    public Map<String,Object> statisticsMember(Integer flag,Integer payPlaceId,Date startTime,Date endTime,Integer more,Integer less) throws Exception{


        Map<String,Object> params = new HashMap<String, Object>();
        params.put("flag",flag);
        params.put("payPlaceId",payPlaceId);
        List<String> abscissa = new ArrayList<String>();
        List<Object> data = new ArrayList<Object>();
        List<Object> dataIsPeer = new ArrayList<Object>();

        if(flag == 0 || flag == 1){
            // 按日统计
            Date firstDayOfMonth = TimeHelper.getFirstDayOfMonth(new Date());
            Date lastDayOfMonth = TimeHelper.getLastDayOfMonth(new Date());
            params.put("startTime",null == startTime ? firstDayOfMonth : startTime);
            params.put("endTime",null == endTime ? lastDayOfMonth : endTime);
            List<Integer> companyIds = new ArrayList<Integer>();
            if(null != more || null != less){
                List<MemberCount> memberCounts = memberCountMapper.countMemberCounts(null == startTime ? firstDayOfMonth : startTime, null == endTime ? lastDayOfMonth : endTime);
                for (MemberCount memberCount : memberCounts) {
                    if(null != more && memberCount.getCountNumber() >= more){
                        companyIds.add(memberCount.getCompanyId());
                    }
                    if(null != less && memberCount.getCountNumber() <= less){
                        companyIds.add(memberCount.getCompanyId());
                    }
                }
            }
            params.put("companyIds",companyIds.size() > 0 ? companyIds : null);

            List<MemberCount> memberCounts = memberCountMapper.statisticsMember(params);

            params.put("isPeer",1);
            List<MemberCount> memberCountsIsPeer = memberCountMapper.statisticsMember(params);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//格式化为年月
            if (flag == 0 ) {
                List<String> betweenDates = TimeHelper.getBetweenDates(null == startTime ? null : sdf.format(startTime), null == endTime ? null : sdf.format(endTime));
                for (String betweenDate : betweenDates) {
                    abscissa.add(betweenDate);
                    int count = 0;
                    int countIsPeer = 0;
                    for (MemberCount memberCount : memberCounts) {
                        if(betweenDate.equals(sdf.format(memberCount.getCreateTime()))){
                            count = memberCount.getCountNumber();
                            break;
                        }
                    }
                    for (MemberCount memberCount : memberCountsIsPeer) {
                        if(betweenDate.equals(sdf.format(memberCount.getCreateTime()))){
                            countIsPeer = memberCount.getCountNumber();
                            break;
                        }
                    }
                    data.add(count);
                    dataIsPeer.add(countIsPeer);
                }
            }
            else{
                // 按周统计
                List<Map<String, Date>> weekDate = TimeHelper.getWeekDate(null == startTime ? null : sdf.format(startTime), null == endTime ? null : sdf.format(endTime));
                for (Map<String, Date> dateMap : weekDate) {
                    abscissa.add(sdf.format(dateMap.get("endTime")));
                    int count = 0;
                    int countPeer = 0;
                    for (MemberCount memberCount : memberCounts) {
                        Date parse = sdf.parse(sdf.format(memberCount.getCreateTime()));
                        if(parse.getTime() >= dateMap.get("startTime").getTime() && parse.getTime() <= dateMap.get("endTime").getTime()){
                            count += memberCount.getCountNumber();
                        }
                    }
                    data.add(count);

                    for (MemberCount memberCount : memberCountsIsPeer) {
                        Date parse = sdf.parse(sdf.format(memberCount.getCreateTime()));
                        if(parse.getTime() >= dateMap.get("startTime").getTime() && parse.getTime() <= dateMap.get("endTime").getTime()){
                            countPeer += memberCount.getCountNumber();
                        }
                    }
                    dataIsPeer.add(countPeer);
                }
            }
        }

        else {
            // 按月统计
            Date yearFirst = TimeHelper.getYearFirst();
            Date yearLast = TimeHelper.getYearLast();
            params.put("startTime",null == startTime ? yearFirst : startTime);
            params.put("endTime",null == endTime ? yearLast : endTime);

            List<Integer> companyIds = new ArrayList<Integer>();
            if(null != more || null != less){
                List<MemberCount> memberCounts = memberCountMapper.countMemberCounts(null == startTime ? yearFirst : startTime, null == endTime ? yearLast : endTime);
                for (MemberCount memberCount : memberCounts) {
                    if(null != more && memberCount.getCountNumber() >= more){
                        companyIds.add(memberCount.getCompanyId());
                    }
                    if(null != less && memberCount.getCountNumber() <= less){
                        companyIds.add(memberCount.getCompanyId());
                    }
                }
            }
            params.put("companyIds",companyIds.size() > 0 ? companyIds : null);

            List<MemberCount> memberCounts = memberCountMapper.statisticsMember(params);

            params.put("isPeer",1);
            List<MemberCount> memberCountsIsPeer = memberCountMapper.statisticsMember(params);

            // 获取两个时间段之间所有的月份
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");//格式化为年月
            List<String> monthBetween = TimeHelper.getMonthBetween(null == startTime ? null : sdf.format(startTime), null == endTime ? null : sdf.format(endTime));

            for (String month : monthBetween) {
                abscissa.add(month);
                int count = 0;
                int countIsPeer = 0;
                for (MemberCount memberCount : memberCounts) {
                    if(month.equals(sdf.format(memberCount.getCreateTime()))){
                        count = memberCount.getCountNumber();
                        break;
                    }
                }
                for (MemberCount memberCount : memberCountsIsPeer) {
                    if(month.equals(sdf.format(memberCount.getCreateTime()))){
                        countIsPeer = memberCount.getCountNumber();
                        break;
                    }
                }
                data.add(count);
                dataIsPeer.add(countIsPeer);
            }
        }

        Map<String,Object> result = new HashMap<String, Object>();
        result.put("abscissa",abscissa);
        result.put("data",data);
        result.put("dataIsPeer",dataIsPeer);
        return result;
    }



    /**
     * 统计合作中的公司
     * @param flag
     * @param startTime
     * @param endTime
     * @return
     * @throws Exception
     */
    public Map<String,Object> statisticsEffectiveCompany(Integer flag, Date startTime,Date endTime) throws Exception{

        Map<String,Object> params = new HashMap<String, Object>();
        params.put("flag",flag);
        List<String> abscissa = new ArrayList<String>();
        List<Object> data = new ArrayList<Object>();
        List<Object> dataIsPeer = new ArrayList<Object>();
        if(flag == 2){
            params.put("startTime",null == startTime ? TimeHelper.getYearFirst() : startTime);
            params.put("endTime",null == endTime ? TimeHelper.getYearLast() : endTime);
            // 按月统计 所有
            List<CompanyCooperation> statistics = companyCooperationMapper.statistics(params);
            // 按月统计 同行
            params.put("isPeer", Common.YES.ordinal());
            List<CompanyCooperation> statisticsIsPeer = companyCooperationMapper.statistics(params);

            // 获取两个时间段之间所有的月份
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");//格式化为年月
            List<String> monthBetween = TimeHelper.getMonthBetween(null == startTime ? null : sdf.format(startTime), null == endTime ? null : sdf.format(endTime));
            for (String month : monthBetween) {
                boolean isExist = false;
                boolean isExistPeer = false;
                abscissa.add(month);
                for (CompanyCooperation statistic : statistics) {
                    if(month.equals(sdf.format(statistic.getCreateTime()))){
                        data.add(statistic.getCount());
                        isExist = true;
                        break;
                    }
                }

                if(!isExist){
                    data.add(0);
                }
                for (CompanyCooperation companyCooperation : statisticsIsPeer) {
                    if(month.equals(sdf.format(companyCooperation.getCreateTime()))){
                        dataIsPeer.add(companyCooperation.getCount());
                        isExistPeer = true;
                        break;
                    }
                }
                if(!isExistPeer){
                    dataIsPeer.add(0);
                }
            }
        }
        else if(flag == 1){
            // 按周统计
            params.put("startTime",null == startTime ? TimeHelper.getFirstDayOfMonth(new Date()) : startTime);
            params.put("endTime",null == endTime ? TimeHelper.getLastDayOfMonth(new Date()) : endTime);
            // 按周统计
            List<CompanyCooperation> statistics = companyCooperationMapper.statistics(params);
            // 按周统计 同行
            params.put("isPeer", Common.YES.ordinal());
            List<CompanyCooperation> statisticsIsPeer = companyCooperationMapper.statistics(params);


            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//格式化为年月
            // 获取两个日期之间周的 开始日期和结束日期
            List<Map<String, Date>> weekDate = TimeHelper.getWeekDate(null == startTime ? null : sdf.format(startTime), null == endTime ? null : sdf.format(endTime));

            for (Map<String, Date> dateMap : weekDate) {
                abscissa.add(sdf.format(dateMap.get("endTime")));
                int count = 0;
                int countPeer = 0;
                for (CompanyCooperation statistic : statistics) {
                    Date parse = sdf.parse(sdf.format(statistic.getCreateTime()));
                    if(parse.getTime() >= dateMap.get("startTime").getTime() && parse.getTime() <= dateMap.get("endTime").getTime()){
                        count += statistic.getCount();
                    }
                }
                data.add(count);

                for (CompanyCooperation companyCooperation : statisticsIsPeer) {
                    Date parse = sdf.parse(sdf.format(companyCooperation.getCreateTime()));
                    if(parse.getTime() >= dateMap.get("startTime").getTime() && parse.getTime() <= dateMap.get("endTime").getTime()){
                        countPeer += companyCooperation.getCount();
                    }
                }
                dataIsPeer.add(countPeer);
            }

        }
        else{
            // 按天统计
            params.put("startTime",null == startTime ? TimeHelper.getFirstDayOfMonth(new Date()) : startTime);
            params.put("endTime",null == endTime ? TimeHelper.getLastDayOfMonth(new Date()) : endTime);
            // 按天统计
            List<CompanyCooperation> statistics = companyCooperationMapper.statistics(params);
            // 按周统计 同行
            params.put("isPeer", Common.YES.ordinal());
            List<CompanyCooperation> statisticsIsPeer = companyCooperationMapper.statistics(params);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//格式化为年月
            List<String> betweenDates = TimeHelper.getBetweenDates(null == startTime ? null : sdf.format(startTime), null == endTime ? null : sdf.format(endTime));
            for (String betweenDate : betweenDates) {
                boolean isExistPeer = false;
                boolean isExist = false;
                abscissa.add(betweenDate);
                for (CompanyCooperation statistic : statistics) {
                    if(betweenDate.equals(sdf.format(statistic.getCreateTime()))){
                        data.add(statistic.getCount());
                        isExist = true;
                        break;
                    }
                }
                if(!isExist){
                    data.add(0);
                }
                for (CompanyCooperation companyCooperation : statisticsIsPeer) {
                    if(betweenDate.equals(sdf.format(companyCooperation.getCreateTime()))){
                        dataIsPeer.add(companyCooperation.getCount());
                        isExistPeer = true;
                        break;
                    }
                }
                if(!isExistPeer){
                    dataIsPeer.add(0);
                }
            }
        }
        Map<String,Object> result = new HashMap<String, Object>();
        result.put("abscissa",abscissa);
        result.put("data",data);
        result.put("dataIsPeer",dataIsPeer);
        return result;
    }


    /**
     * 统计 新增、终止公司数量
     * @param date
     * @return
     */
    public Map<String,Object> statisticsCompanyCount(Date date){
        return companyMapper.countCompany(date);
    }




    /**
     * 统计 新增、终止员工数量
     * @param date
     * @return
     */
    public Map<String,Object> countMemberByDate(Date date){
        return memberCountMapper.countMemberByDate(date);
    }





}
