package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.MemberBusinessOtherItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Eric Xie on 2017/10/12 0012.
 */
public interface IMemberBusinessOtherItemMapper {


    /**
     * 批量新增
     * @param itemList
     * @return
     */
    Integer batchAddMemberBusinessOtherItem(@Param("itemList") List<MemberBusinessOtherItem> itemList);


    Integer delMemberBusinessOtherItem(@Param("memberId") Integer memberId);


    Integer batDel(List<MemberBusinessOtherItem> itemList);


    Integer del(List<Integer> ids);



}
