package com.magic.daoyuan.business.service;

import com.magic.daoyuan.business.entity.*;
import com.magic.daoyuan.business.enums.Common;
import com.magic.daoyuan.business.exception.InterfaceCommonException;
import com.magic.daoyuan.business.mapper.IInsuranceMapper;
import com.magic.daoyuan.business.mapper.ILogMapper;
import com.magic.daoyuan.business.mapper.IPayTheWayMapper;
import com.magic.daoyuan.business.util.StatusConstant;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 保险
 * Created by Eric Xie on 2017/9/27 0027.
 */
@Service
public class InsuranceService {

    @Resource
    private IInsuranceMapper insuranceMapper;
    @Resource
    private IPayTheWayMapper payTheWayMapper;
    @Resource
    private ILogMapper logMapper;


    public PageList<Insurance> getInsuranceByItems(Map<String,Object> params, PageArgs pageArgs){
        int count = insuranceMapper.countInsuranceByItems(params);
        List<Insurance> dataList = new ArrayList<Insurance>();
        if(count > 0){
            params.put("limit",pageArgs.getPageStart());
            params.put("limitSize",pageArgs.getPageSize());
            dataList = insuranceMapper.queryInsuranceByItems(params);
        }
        return new PageList<Insurance>(dataList,count);
    }

    public Insurance getInsuranceById(Integer id){
        return insuranceMapper.queryInsuranceById(id);
    }


    @Transactional
    public void add(Insurance insurance, User user){
        insuranceMapper.addInsurance(insurance);
        logMapper.add(new Log(user.getId(),StatusConstant.LOG_MODEL_CONFIG,"新增险种:"+insurance.getInsuranceName(),StatusConstant.LOG_FLAG_ADD));
    }


    public void update(Insurance insurance,User user){
        Insurance info = insuranceMapper.queryInsuranceById(insurance.getId());
        if (null == info) {
            throw new InterfaceCommonException(StatusConstant.NO_DATA,"未知的险种");
        }
        if(null == insurance.getRemark()){
            insurance.setRemark("");
        }
        insuranceMapper.updateInsurance(insurance);
        if(null != insurance.getInsuranceName() && !info.getInsuranceName().equals(insurance.getInsuranceName())){
            logMapper.add(new Log(user.getId(),StatusConstant.LOG_MODEL_CONFIG,"修改险种:"+insurance.getInsuranceName(),StatusConstant.LOG_FLAG_UPDATE));
        }
        if(null != insurance.getIsValid() && Common.NO.ordinal() == insurance.getIsValid()){
            logMapper.add(new Log(user.getId(),StatusConstant.LOG_MODEL_CONFIG,"删除险种:"+info.getInsuranceName(),StatusConstant.LOG_FLAG_DEL));
        }
    }

    /**
     * 获取只是办理方下的险种及档次
     * @param payPlaceId 缴金地id
     * @return
     */
    public List<Insurance> getOnlyTransactor(Integer payPlaceId,Integer insuranceLevelId,Integer transactorId) {
        return insuranceMapper.getOnlyTransactor(payPlaceId,insuranceLevelId,transactorId);
    }

    /**
     * 获取缴金地下跟随办理方的险种及档次
     * @param payPlaceId
     * @return
     */
    public List<Insurance> getByPayPlaceId(Integer payPlaceId) {
        return insuranceMapper.getByPayPlaceId(payPlaceId);
    }
}
