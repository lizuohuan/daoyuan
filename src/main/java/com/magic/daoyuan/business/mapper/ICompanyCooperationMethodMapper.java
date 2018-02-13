package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.CompanyCooperationMethod;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * Created by Eric Xie on 2017/11/2 0002.
 */
public interface ICompanyCooperationMethodMapper {


    Integer batchAdd(List<CompanyCooperationMethod> list);


    Integer add(CompanyCooperationMethod method);


    Integer del(@Param("companyId") Integer companyId);


    List<CompanyCooperationMethod> queryCompanyCooperationMethod(@Param("companyId") Integer companyId);

    List<CompanyCooperationMethod> queryCompanyCooperationMethod2(@Param("companyIdSet") Set<Integer> companyIdSet);

    List<CompanyCooperationMethod> queryBaseCooperationMethod(@Param("companyId") Integer companyId);

}
