package com.magic.daoyuan.business.service;

import com.magic.daoyuan.business.entity.*;
import com.magic.daoyuan.business.enums.BillType;
import com.magic.daoyuan.business.exception.InterfaceCommonException;
import com.magic.daoyuan.business.mapper.ICompanyBillInfoMapper;
import com.magic.daoyuan.business.mapper.ILogMapper;
import com.magic.daoyuan.business.util.StatusConstant;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Eric Xie on 2017/9/20 0020.
 */
@Service
public class CompanyBillInfoService {


    @Resource
    private ICompanyBillInfoMapper companyBillInfoMapper;
    @Resource
    private ILogMapper logMapper;


    public List<CompanyBillInfo> queryBillInfo(Integer companyId){
        return companyBillInfoMapper.queryBillInfo(companyId);
    }


    public CompanyBillInfo queryBillInfoById(Integer id){
        return companyBillInfoMapper.queryBillInfoById(id);
    }

    public PageList<CompanyBillInfo> queryBillInfoByItems(Map<String,Object> params, PageArgs pageArgs){
        List<CompanyBillInfo> billInfoList = new ArrayList<CompanyBillInfo>();
        int count = companyBillInfoMapper.countBillInfoByItems(params);
        if(count > 0){
            params.put("limit",pageArgs.getPageStart());
            params.put("limitSize",pageArgs.getPageSize());
            billInfoList = companyBillInfoMapper.queryBillInfoByItems(params);
        }
        return new PageList<CompanyBillInfo>(billInfoList,count);
    }

    @Transactional
    public void updateBillInfo(CompanyBillInfo billInfo,User user){
        CompanyBillInfo info = companyBillInfoMapper.queryBillInfoById(billInfo.getId());
        if(null == info){
            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST,"票据不存在");
        }
        companyBillInfoMapper.updateBillInfo(billInfo);
        if(!info.getBillType().equals(billInfo.getBillType())){
            String type = "";
            if(billInfo.getBillType() == BillType.SpecialInvoice.ordinal()){
                type = "专票";
            }
            else if(billInfo.getBillType() == BillType.TaxInvoice.ordinal()){
                type = "普票";
            }
            else{
                type = "收据";
            }
            Log log = new Log(user.getId(),StatusConstant.LOG_MODEL_COMPANY,"修改了票据为："+type,StatusConstant.LOG_FLAG_UPDATE);
            logMapper.add(log);
        }
    }

    @Transactional
    public void addBillInfo(CompanyBillInfo billInfo, User user){
        companyBillInfoMapper.addBillInfo(billInfo);
        Log log = new Log(user.getId(), StatusConstant.LOG_MODEL_COMPANY,"新增 "+billInfo.getTitle() + "票据",StatusConstant.LOG_FLAG_ADD);
        logMapper.add(log);
    }



}
