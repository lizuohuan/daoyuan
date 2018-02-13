package com.magic.daoyuan.business.service;

import com.magic.daoyuan.business.entity.BankInfo;
import com.magic.daoyuan.business.entity.PageArgs;
import com.magic.daoyuan.business.entity.PageList;
import com.magic.daoyuan.business.exception.InterfaceCommonException;
import com.magic.daoyuan.business.mapper.IBankInfoMapper;
import com.magic.daoyuan.business.util.StatusConstant;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Eric Xie on 2017/10/27 0027.
 */
@Service
public class BankInfoService {

    @Resource
    private IBankInfoMapper bankInfoMapper;


    public List<BankInfo> getAllBankInfo(){
        return bankInfoMapper.queryAllBankInfo();
    }


    public void addBankInfo(BankInfo bankInfo) throws InterfaceCommonException {
        // 排除同一家公司下不能有相同的银行帐号 和 账户名
        BankInfo t = bankInfoMapper.queryBankInfoByAccount(bankInfo.getBankAccount(), bankInfo.getAccountName(), bankInfo.getCompanyId());
        if(null != t){
            throw new InterfaceCommonException(StatusConstant.OBJECT_EXIST,"已经存在相同的收款信息");
        }
        bankInfoMapper.addBankInfo(bankInfo);
    }


    public void updateBankInfo(BankInfo bankInfo) throws InterfaceCommonException{

        BankInfo i = bankInfoMapper.info(bankInfo.getId());
        if(null == i){
            throw new InterfaceCommonException(StatusConstant.Fail_CODE,"银行信息不存在");
        }

        if(!bankInfo.getBankAccount().equals(i.getBankAccount()) || !bankInfo.getAccountName().equals(i.getAccountName())){
            BankInfo t = bankInfoMapper.queryBankInfoByAccount(bankInfo.getBankAccount(), bankInfo.getAccountName(), bankInfo.getCompanyId());
            if(null != t){
                throw new InterfaceCommonException(StatusConstant.OBJECT_EXIST,"已经存在相同的收款信息");
            }
        }

        bankInfoMapper.updateBankInfo(bankInfo);
    }



    public PageList<BankInfo> getBankInfo(Integer companyId, PageArgs pageArgs){
        List<BankInfo> dataList = new ArrayList<BankInfo>();
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("companyId",companyId);
        int count = bankInfoMapper.countBankInfoByItems(map);
        if(count > 0){
            map.put("limit",pageArgs.getPageStart());
            map.put("limitSize",pageArgs.getPageSize());
            dataList = bankInfoMapper.queryBankInfoByItems(map);
        }
        return new PageList<BankInfo>(dataList,count);
    }
}
