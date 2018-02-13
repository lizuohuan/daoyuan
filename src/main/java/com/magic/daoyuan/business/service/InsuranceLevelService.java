package com.magic.daoyuan.business.service;

import com.magic.daoyuan.business.entity.InsuranceLevel;
import com.magic.daoyuan.business.entity.PageArgs;
import com.magic.daoyuan.business.entity.PageList;
import com.magic.daoyuan.business.entity.PayTheWay;
import com.magic.daoyuan.business.mapper.IInsuranceLevelMapper;
import com.magic.daoyuan.business.mapper.IPayTheWayMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 险种档次
 * Created by Eric Xie on 2017/9/27 0027.
 */
@Service
public class InsuranceLevelService {


    @Resource
    private IInsuranceLevelMapper iInsuranceLevelMapper;


    /**
     * 通过缴金地 查询缴金地下所有的险种档次集合
     * @param payPlaceId 缴金地ID
     * @return
     */
    public List<InsuranceLevel> queryInsuranceLevelByPayPlace(Integer payPlaceId,Integer isTuoGuan){
        return iInsuranceLevelMapper.queryInsuranceLevelByPayPlace(payPlaceId,isTuoGuan);
    }

    /**
     * 获取列表
     * @param params
     * @param pageArgs
     * @return
     */
    public PageList<InsuranceLevel> getInsuranceLevel(Map<String,Object> params, PageArgs pageArgs){
        int count = iInsuranceLevelMapper.countInsuranceLevelByItems(params);
        List<InsuranceLevel> dataList = new ArrayList<InsuranceLevel>();
        if(count > 0){
            params.put("limit",pageArgs.getPageStart());
            params.put("limitSize",pageArgs.getPageSize());
            dataList = iInsuranceLevelMapper.queryInsuranceLevelByItems(params);
        }
        return new PageList<InsuranceLevel>(dataList,count);
    }

    public InsuranceLevel getInsuranceLevelById(Integer id){
        return iInsuranceLevelMapper.queryInsuranceLevelById(id);
    }

    /**
     * 新增
     * @param insuranceLevel
     * @throws Exception
     */
    @Transactional
    public void addInsuranceLevel(InsuranceLevel insuranceLevel) throws Exception{
        iInsuranceLevelMapper.addInsuranceLevel(insuranceLevel);
    }


    @Transactional
    public void updateInsuranceLevel(InsuranceLevel insuranceLevel) throws Exception{
        iInsuranceLevelMapper.updateInsuranceLevel(insuranceLevel);
    }



}
