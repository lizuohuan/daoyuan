package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.PayPlace;
import com.magic.daoyuan.business.entity.Transactor;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 缴金地
 * Created by Eric Xie on 2017/9/27 0027.
 */
public interface IPayPlaceMapper {


    List<PayPlace> queryAllPayPlaceForImport();

    /**
     * 通过类型 获取 缴金地列表
     * 缴金地类型 0：社保  1：公积金
     * @param type 可选
     * @return
     */
    List<PayPlace> queryPayPlaceByType(@Param("type") Integer type);

    /**
     * 通过类型 获取 缴金地列表
     * 缴金地类型 0：社保  1：公积金
     * @param type 可选
     * @return
     */
    List<PayPlace> queryPayPlaceByType2(@Param("type") Integer type);

    /**
     * 获取 缴金地列表 添加客户使用
     * @return
     */
    List<PayPlace> queryAllPayPlace();

    PayPlace queryPayPlaceById(@Param("id") Integer id);

    PayPlace queryPayPlaceByCompanyPayPlace(@Param("companyPayPlace") Integer companyPayPlace);

    Integer addPayPlace(PayPlace payPlace);


    Integer updatePayPlace(PayPlace payPlace);


    List<PayPlace> queryPayPlaceByItems(Map<String,Object> params);



    List<PayPlace> queryPayPlaceByItems2(Map<String,Object> params);


    int countPayPlaceByItems(Map<String,Object> params);


    /**
     * 根据地区id获取此缴金地下所有险种及档次 (险种-->档次)
     * @param cityId
     * @return
     */
    PayPlace getInsuranceByCityId(@Param("cityId") Integer cityId);

    /**
     * 根据地区id获取此缴金地下所有险种及档次 (档次-->险种)
     * @param cityId
     * @return
     */
    PayPlace getInsuranceByCityId2(@Param("cityId") Integer cityId);

    /**
     * 根据地区id获取此缴金地下所有机构、办理方、险种档次
     * @param cityId
     * @return
     */
    List<PayPlace> getOTILByCityId(@Param("cityId") Integer cityId);

}
