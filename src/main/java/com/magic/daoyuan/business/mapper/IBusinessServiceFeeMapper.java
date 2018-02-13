package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.BusinessServiceFee;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Eric Xie on 2017/10/9 0009.
 */
public interface IBusinessServiceFeeMapper {


    Integer batchAddBusinessServiceFee(@Param("serviceFeeList") List<BusinessServiceFee> serviceFeeList);



    Integer delBusinessServiceFee(@Param("companyId") Integer companyId,@Param("type") Integer type);


    List<BusinessServiceFee> queryBusinessServiceFee(@Param("companyId") Integer companyId,@Param("type") Integer type);

}
