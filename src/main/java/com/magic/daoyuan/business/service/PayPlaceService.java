package com.magic.daoyuan.business.service;

import com.magic.daoyuan.business.entity.Insurance;
import com.magic.daoyuan.business.entity.PageArgs;
import com.magic.daoyuan.business.entity.PageList;
import com.magic.daoyuan.business.entity.PayPlace;
import com.magic.daoyuan.business.exception.InterfaceCommonException;
import com.magic.daoyuan.business.mapper.IInsuranceMapper;
import com.magic.daoyuan.business.mapper.IPayPlaceMapper;
import com.magic.daoyuan.business.util.CommonUtil;
import com.magic.daoyuan.business.util.StatusConstant;
import org.apache.bcel.generic.IF_ACMPEQ;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Eric Xie on 2017/9/27 0027.
 */
@Service
public class PayPlaceService {

    @Resource
    private IPayPlaceMapper payPlaceMapper;
    @Resource
    private IInsuranceMapper insuranceMapper;

    public List<PayPlace> queryAllPayPlace(){
        List<PayPlace> list = payPlaceMapper.queryAllPayPlace();
        for (PayPlace place : list) {
            place.setPayPlaceName(CommonUtil.formatPayPlace(place.getPayPlaceName()));
        }
        return list;
    }

    /**
     *  通过类型 获取 缴金地列表
     * 缴金地类型 0：社保  1：公积金
     * @param type
     * @return
     */
    public List<PayPlace> getPayPlaceByType(Integer type){
        List<PayPlace> list = payPlaceMapper.queryPayPlaceByType2(type);
        for (PayPlace place : list) {
            place.setPayPlaceName(CommonUtil.formatPayPlace2(place.getPayPlaceName()));
        }
        return list;
    }

    public PayPlace queryPayPlaceById(Integer id){

        return payPlaceMapper.queryPayPlaceById(id);
    }

    public PayPlace queryPayPlaceByCompanyPayPlace(Integer companyPayPlaceId){
        return payPlaceMapper.queryPayPlaceByCompanyPayPlace(companyPayPlaceId);
    }

    public PageList<PayPlace> queryPayPlaceByItems(Map<String,Object> params, PageArgs pageArgs){
        int count = payPlaceMapper.countPayPlaceByItems(params);
        List<PayPlace> dataList = new ArrayList<PayPlace>();
        if(count > 0){
            params.put("limit",pageArgs.getPageStart());
            params.put("limitSize",pageArgs.getPageSize());
            dataList = payPlaceMapper.queryPayPlaceByItems2(params);
        }
        return new PageList<PayPlace>(dataList,count);
    }

    public void addPayPlace(PayPlace payPlace){
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("cityId",payPlace.getCityId());
        params.put("type",payPlace.getType());
        if (payPlaceMapper.queryPayPlaceByItems(params).size() > 0) {
            throw new InterfaceCommonException(StatusConstant.OBJECT_EXIST,"此缴金地已存在");
        }

        payPlaceMapper.addPayPlace(payPlace);
        List<Insurance> insurances = new ArrayList<Insurance>();
        for (int i = 0 ; i < 6 ; i ++) {
            Insurance insurance = new Insurance();
            insurance.setPayPlaceId(payPlace.getId());
            switch (i) {
                case 0 : insurance.setInsuranceName("养老"); break;
                case 1 : insurance.setInsuranceName("工伤"); break;
                case 2 : insurance.setInsuranceName("生育"); break;
                case 3 : insurance.setInsuranceName("医疗"); break;
                case 4 : insurance.setInsuranceName("失业"); break;
                case 5 : insurance.setInsuranceName("大病"); break;
            }
            insurances.add(insurance);
        }
        insuranceMapper.saveList(insurances);

    }


    public void update(PayPlace payPlace){
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("cityId",payPlace.getCityId());
        params.put("type",payPlace.getType());
        List<PayPlace> dataList = payPlaceMapper.queryPayPlaceByItems(params);
        if (dataList.size() > 0) {
            for (PayPlace place : dataList) {
                if (!place.getId().equals(payPlace.getId())) {
                    throw new InterfaceCommonException(StatusConstant.OBJECT_EXIST,"此缴金地已存在");
                }
            }
        }
        payPlaceMapper.updatePayPlace(payPlace);
    }



    /**
     * 根据地区id获取此缴金地下所有险种及档次 缴金地下所有机构、办理方、险种档次
     * @param cityId
     * @return
     */
    public List<PayPlace> getOTILInsuranceByCityId (Integer cityId) {
        List<PayPlace> list = new ArrayList<PayPlace>();
        list.addAll(payPlaceMapper.getOTILByCityId(cityId));
        //获取险种信息
        // FIXME: 2018/1/26 bug:514
//        PayPlace payPlace = payPlaceMapper.getInsuranceByCityId(cityId);
        PayPlace payPlace = payPlaceMapper.getInsuranceByCityId2(cityId);
        //设置type 为2 表示险种
        payPlace.setType(2);
        list.add(payPlace);
        return list;
    }

}
