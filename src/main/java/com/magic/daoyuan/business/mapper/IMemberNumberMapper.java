package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.MemberNumber;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * Created by Eric Xie on 2017/12/14 0014.
 */
public interface IMemberNumberMapper {


    int batchAdd(List<MemberNumber> memberNumbers);


    List<MemberNumber> queryMemberNumberByItems(List<Integer> memberIds);


    List<MemberNumber> queryMemberNumberByMember(@Param("memberId") Integer memberId);


    Set<MemberNumber> queryMemberNumberByIdCards(List<String> idCards);

}
