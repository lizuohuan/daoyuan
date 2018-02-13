package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.CompanyBusiness;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Eric Xie on 2017/9/13 0013.
 */
public interface ICompanyBusinessMapper {


    List<CompanyBusiness> queryCompanyBusiness();

    Integer addCompanyBusiness(CompanyBusiness companyBusiness);

    Integer batchAddCompanyBusiness(@Param("companyBusinessList") List<CompanyBusiness> companyBusinessList);

    Integer delCompanyBusiness(@Param("companyId") Integer companyId);

    int countCompanyBusiness(@Param("companyId") Integer companyId,@Param("businessId") Integer businessId);

    /**
     * 获取公司的一次性险
     * @param list
     * @return
     */
    List<CompanyBusiness> queryCompanyBusinessOfYC(List<Integer> list);


    Integer del(List<Integer> ids);

}
