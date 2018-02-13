package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.SalaryType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Eric Xie on 2017/10/18 0018.
 */
public interface ISalaryTypeMapper {


    @Insert("insert into salary_type (typeName) values (#{salaryType.typeName})")
    Integer addSalaryType(@Param("salaryType") SalaryType salaryType);


    @Select("select * from salary_type")
    List<SalaryType> queryAllSalaryType();

}
