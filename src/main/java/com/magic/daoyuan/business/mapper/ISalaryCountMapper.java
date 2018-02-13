package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.SalaryCount;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Eric Xie on 2017/10/19 0019.
 */
public interface ISalaryCountMapper {


    Integer addSalaryCount(SalaryCount salaryCount);


    List<SalaryCount> queryAllSalaryCount();


    Integer delSalaryCount(@Param("id") Integer id);

}
