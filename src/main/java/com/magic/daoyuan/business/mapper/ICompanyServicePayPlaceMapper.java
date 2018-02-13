package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.CompanyServicePayPlace;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Eric Xie on 2017/10/24 0024.
 */
public interface ICompanyServicePayPlaceMapper {


    Integer batchAddCompanyServicePayPlace(List<CompanyServicePayPlace> servicePayPlaceList);



    Integer delCompanyServicePayPlace(@Param("companyId") Integer companyId,@Param("type") Integer type);


    List<CompanyServicePayPlace> queryCompanyServicePayPlace(@Param("companyId") Integer companyId,@Param("type") Integer type);


}
