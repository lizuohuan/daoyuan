package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.CompanyBillCount;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Eric Xie on 2017/12/26 0026.
 */
public interface ICompanyBillCountMapper {


    Integer add(CompanyBillCount companyBillCount);



    CompanyBillCount info(@Param("companyId") Integer companyId,@Param("billMonth") Date billMonth,@Param("type") Integer type);

    List<CompanyBillCount> countCompanyBillCount(Map<String,Object> map);

    /**
     * 删除
     * @param companyId
     * @param billMonth
     * @return
     */
    Integer del(@Param("companyId") Integer companyId, @Param("billMonth") Date billMonth ,@Param("type") Integer type);

}
