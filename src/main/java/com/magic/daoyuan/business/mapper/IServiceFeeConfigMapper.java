package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.ServiceFeeConfig;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Eric Xie on 2017/9/13 0013.
 */
public interface IServiceFeeConfigMapper {

    @Select("select * from service_fee_config")
    @ResultType(com.magic.daoyuan.business.entity.ServiceFeeConfig.class)
    List<ServiceFeeConfig> queryAllFeeConfig();

}
