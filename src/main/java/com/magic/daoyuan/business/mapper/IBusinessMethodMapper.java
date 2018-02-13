package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.BusinessMethod;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Eric Xie on 2017/9/22 0022.
 */
public interface IBusinessMethodMapper {


    Integer batchAddBusinessMethod(@Param("businessMethods") List<BusinessMethod> businessMethods);


    Integer delBusinessMethod(@Param("ids") List<Integer> ids);

    /**
     * 通过公司关联的业务 查询 合作方式  仅限 公积金 社保使用
     * @param companyBusinessId
     * @return
     */
    BusinessMethod queryBusinessMethodByCompanyBusinessId(@Param("companyBusinessId") Integer companyBusinessId);

}
