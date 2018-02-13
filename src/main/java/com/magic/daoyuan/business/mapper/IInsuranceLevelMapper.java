package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.Insurance;
import com.magic.daoyuan.business.entity.InsuranceLevel;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Eric Xie on 2017/9/27 0027.
 */
public interface IInsuranceLevelMapper {


    List<InsuranceLevel> queryAllInsuranceLevel();



    /**
     * 通过缴金地 查询缴金地下所有的险种档次集合
     * @param payPlaceId 缴金地ID
     * @return
     */
    List<InsuranceLevel> queryInsuranceLevelByPayPlace(@Param("payPlaceId") Integer payPlaceId,
                                                       @Param("isTuoGuan") Integer isTuoGuan);

    Integer addInsuranceLevel(InsuranceLevel insuranceLevel);


    Integer updateInsuranceLevel(InsuranceLevel insuranceLevel);


    List<InsuranceLevel> queryInsuranceLevelByItems(Map<String,Object> params);


    int countInsuranceLevelByItems(Map<String,Object> params);



    InsuranceLevel queryInsuranceLevelById(@Param("id") Integer id);

}
