package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.CompanyServiceFee;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Eric Xie on 2017/9/19 0019.
 */
public interface ICompanyServiceFeeMapper {


    /**
     * 新增公司服务费配置
     * @param serviceFee
     * @return
     */
    Integer addCompanyServiceFee(CompanyServiceFee serviceFee);

    /**
     * 批量新增 公司服务费配置
     * @param serviceFees
     * @return
     */
    Integer batchAddCompanyServiceFee(@Param("serviceFees") List<CompanyServiceFee> serviceFees);


    /**
     * 通过公司ID 查询公司的收费集合
     * @param companyId
     * @return
     */
    List<CompanyServiceFee> queryServiceFeeByCompany(@Param("companyId") Integer companyId,@Param("type") Integer type);


    /**
     * 删除公司收费集合
     * @param companyId
     * @return
     */
    Integer delServiceFee(@Param("companyId") Integer companyId,@Param("type") Integer type);

}
