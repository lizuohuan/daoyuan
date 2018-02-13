package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.MemberCount;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Eric Xie on 2017/12/22 0022.
 */
public interface IMemberCountMapper {


    /**
     * 统计本月员工的数量
     * @param date
     * @return
     */
    Map<String,Object> countMemberByDate(@Param("date") Date date);

    /**
     * 统计这个时间段哪些公司的员工数量达到要求
     * @param startTime
     * @param endTime
     * @return
     */
    List<MemberCount> countMemberCounts(@Param("startTime") Date startTime,@Param("endTime") Date endTime);

    /**
     * 统计合作中的人数
     * @param map
     * @return
     */
    List<MemberCount> statisticsMember(Map<String,Object> map);


    int add(MemberCount memberCount);

    int batchAdd(List<MemberCount> list);

    MemberCount info(@Param("memberId") Integer memberId, @Param("date") Date date);

    int update(MemberCount memberCount);


    int batchUpdate(List<MemberCount> memberCounts);


    int del(@Param("date") Date date);


}
