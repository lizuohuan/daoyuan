package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.Business;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Eric Xie on 2017/9/12 0012.
 */
public interface IBusinessMapper {


    /**
     * 获取 其他周期性服务 和 一次性服务 业务 极其子类
     * @return
     */
    List<Business> queryOtherBusiness();

    Integer addBusiness(Business business);

    Integer updateBusiness(Business business);

    List<Business> queryBusinessByItems(@Param("limit") Integer limit,@Param("limitSize") Integer limitSize);


    int countBusinessByItems();


    List<Business> queryBusiness(@Param("companyId") Integer companyId);


    /**
     * 查询 员工的 业务集合
     * @param memberId
     * @return
     */
    List<Business> queryBusinessByMember(@Param("memberId") Integer memberId);
    /**
     * 查询 员工的 业务集合
     * @param memberId
     * @return
     */
    List<Business> queryOnlyBusinessByMember(@Param("memberId") Integer memberId);



}
