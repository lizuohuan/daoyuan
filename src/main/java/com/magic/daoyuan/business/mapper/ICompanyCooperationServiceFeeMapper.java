package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.CompanyCooperationServiceFee;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Eric Xie on 2017/11/2 0002.
 */
public interface ICompanyCooperationServiceFeeMapper {


    Integer batchAdd(List<CompanyCooperationServiceFee> list);


    Integer add(CompanyCooperationServiceFee serviceFee);


    Integer del(@Param("companyCooperationMethodId") Integer companyCooperationMethodId);


    List<CompanyCooperationServiceFee> queryByCompanyCooperationMethodId(@Param("companyCooperationMethodId") Integer companyCooperationMethodId);

}
