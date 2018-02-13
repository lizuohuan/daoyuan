package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.ExpressCompany;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 快递公司
 * Created by Eric Xie on 2017/9/21 0021.
 */
public interface IExpressCompanyMapper {


    ExpressCompany queryExpressCompanyById(@Param("id") Integer id);

    Integer addExpressCompany(ExpressCompany expressCompany);


    Integer updateExpressCompany(ExpressCompany expressCompany);


    List<ExpressCompany> queryExpressCompany(@Param("limit") Integer limit,@Param("limitSize") Integer limitSize);


    Integer countExpressCompany();


}
