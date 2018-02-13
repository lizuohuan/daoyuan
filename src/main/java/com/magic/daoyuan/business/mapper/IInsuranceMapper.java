package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.Insurance;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Eric Xie on 2017/9/27 0027.
 */
public interface IInsuranceMapper {

    /**
     *  获取所有的险种
     * @return
     */
    List<Insurance> queryAllInsurance();

    /**
     * 批量添加险种
     * @param list
     */
    void saveList(List<Insurance> list);

    Integer addInsurance(Insurance insurance);

    Integer updateInsurance(Insurance insurance);


    List<Insurance> queryInsuranceByItems(Map<String,Object> params);

    /**
     * 获取缴金地下跟随办理方的险种及档次
     * @param payPlaceId
     * @return
     */
    List<Insurance> getByPayPlaceId(@Param("payPlaceId") Integer payPlaceId);


    int countInsuranceByItems(Map<String,Object> params);


    Insurance queryInsuranceById(@Param("id") Integer id);

    /**
     * 获取只是办理方下的险种及档次
     * @param payPlaceId 缴金地id
     * @param transactorId 办理方id
     * @return
     */
    List<Insurance> getOnlyTransactor(@Param("payPlaceId") Integer payPlaceId,
                                      @Param("insuranceLevelId") Integer insuranceLevelId,
                                      @Param("transactorId") Integer transactorId);


}
