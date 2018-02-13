package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.ExpressPersonInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Eric Xie on 2017/9/26 0026.
 */
public interface IExpressPersonInfoMapper {


    ExpressPersonInfo queryInfoById(@Param("id") Integer id);

    Integer addExpressPersonInfo(ExpressPersonInfo expressPersonInfo);

    ExpressPersonInfo queryByAllField(ExpressPersonInfo expressPersonInfo);

    Integer updateExpressPersonInfo(ExpressPersonInfo expressPersonInfo);


    List<ExpressPersonInfo> queryExpressPersonInfoByItems(Map<String,Object> params);


    Integer countExpressPersonInfoByItems(Map<String,Object> params);


    /**
     * 查询公司下 默认的 邮寄信息
     * @param companyId
     * @return
     */
    ExpressPersonInfo queryDefaultInfo(@Param("companyId") Integer companyId);


    /**
     * 更新公司下所有的邮寄信息的默认收货地址为 否
     * @param companyId
     * @return
     */
    Integer updateAllIsDefault(@Param("companyId") Integer companyId);


}
