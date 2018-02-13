package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.CompanyCooperationBusinessServiceFee;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Eric Xie on 2017/11/2 0002.
 */
public interface ICompanyCooperationBusinessServiceFeeMapper {


    Integer add(CompanyCooperationBusinessServiceFee serviceFee);


    Integer batchAdd(List<CompanyCooperationBusinessServiceFee> list);


    Integer del(@Param("companyCooperationMethodId") Integer companyCooperationMethodId);


    List<CompanyCooperationBusinessServiceFee> queryByCompanyCooperationMethodId(@Param("companyCooperationMethodId") Integer companyCooperationMethodId);

}
