package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.CompanyCooperationServicePayPlace;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Eric Xie on 2017/11/2 0002.
 */
public interface ICompanyCooperationServicePayPlaceMapper {


    Integer add(CompanyCooperationServicePayPlace payPlace);


    Integer batchAdd(List<CompanyCooperationServicePayPlace> list);


    Integer del(@Param("companyCooperationMethodId") Integer companyCooperationMethodId);


    List<CompanyCooperationServicePayPlace> queryByCompanyCooperationMethodId(@Param("companyCooperationMethodId") Integer companyCooperationMethodId);


}
