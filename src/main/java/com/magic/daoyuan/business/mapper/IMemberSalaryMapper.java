package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.Member;
import com.magic.daoyuan.business.entity.MemberSalary;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Eric Xie on 2017/10/11 0011.
 */
public interface IMemberSalaryMapper {


    Integer batchAddMemberSalary(List<MemberSalary> memberSalary);


    Integer addMemberSalary(MemberSalary memberSalary);


    Integer updateMemberSalary(MemberSalary memberSalary);


    Integer batchUpdateMemberSalaryByMember(@Param("memberSalaryList") List<MemberSalary> memberSalaryList);


    MemberSalary queryMemberSalary(@Param("memberId") Integer memberId);

    Integer delMemberSalary(@Param("memberId") Integer memberId);
    Integer delMemberSalarys(List<Integer> memberIds);



}
