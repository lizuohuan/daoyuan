package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.CompanyCooperation;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Eric Xie on 2017/12/21 0021.
 */
public interface ICompanyCooperationMapper {


    CompanyCooperation info(@Param("companyId") Integer companyId,@Param("date") Date date);

    Integer update(CompanyCooperation companyCooperation);

    Integer add(CompanyCooperation companyCooperation);


    Integer batchAdd(List<CompanyCooperation> list);


    Integer del(@Param("companyId") Integer companyId,@Param("date") Date date);

    /**
     * 删除当天所有的记录数据
     * @param date
     * @return
     */
    Integer delAllByDate(@Param("date") Date date);

    /**
     * 统计公司 正在合作的数据
     * @param map
     * @return
     */
    List<CompanyCooperation> statistics(Map<String,Object> map);


}
