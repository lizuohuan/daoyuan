package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.SalaryDate;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 工资发放Mapper
 * Created by Eric Xie on 2017/9/22 0022.
 */
public interface ISalaryDateMapper {



    Integer batchAddSalaryDate(@Param("salaryDateList") List<SalaryDate> salaryDateList);


    Integer addSalaryDate(SalaryDate salaryDate);


    Integer delSalaryDate(@Param("companyId") Integer companyId);


    List<SalaryDate> querySalaryDateByCompany(@Param("companyId") Integer companyId);

}
