package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.ExpressInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Eric Xie on 2017/9/21 0021.
 */
public interface IExpressInfoMapper {



    Integer addExpressInfo(ExpressInfo info);


    Integer updateExpressInfo(ExpressInfo info);


    List<ExpressInfo> queryExpressInfoByContent(@Param("orderNumber") String orderNumber,
                                                @Param("expressPersonId") Integer expressPersonId,
                                                @Param("expressCompanyId") Integer expressCompanyId);

    List<ExpressInfo> queryExpressInfo(Map<String,Object> params);


    Integer countExpressInfo(Map<String,Object> params);


    ExpressInfo queryExpressInfoById(@Param("id") Integer id);


}
