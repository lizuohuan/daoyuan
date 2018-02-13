package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.MemberBaseChange;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Eric Xie on 2017/11/30 0030.
 */
public interface IMemberBaseChangeMapper {

    Integer batchAdd(List<MemberBaseChange> changes);


    List<MemberBaseChange> queryMemberBaseChange(@Param("recordItem") Integer recordItem);

}
