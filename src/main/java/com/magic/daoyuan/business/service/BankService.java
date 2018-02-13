package com.magic.daoyuan.business.service;

import com.magic.daoyuan.business.entity.Bank;
import com.magic.daoyuan.business.entity.PageArgs;
import com.magic.daoyuan.business.entity.PageList;
import com.magic.daoyuan.business.mapper.IBankMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Eric Xie on 2017/10/10 0010.
 */
@Service
public class BankService {

    @Resource
    private IBankMapper bankMapper;


    public Bank queryBankById(Integer id){
        return bankMapper.queryBankById(id);
    }

    public PageList<Bank> queryBank(PageArgs pageArgs){
        List<Bank> dataList = new ArrayList<Bank>();
        Map<String,Object> params = new HashMap<String, Object>();
        int count = bankMapper.countCount(params);
        if(count > 0){
            params.put("limit",pageArgs.getPageStart());
            params.put("limitSize",pageArgs.getPageSize());
            dataList = bankMapper.queryBank(params);
        }
        return new PageList<Bank>(dataList,count);
    }

    public List<Bank> queryAllBank(){
        return bankMapper.queryAllBank();
    }

    public void updateBank(Bank bank){
        bankMapper.updateBank(bank);
    }

    public void addBank(Bank bank){
        bankMapper.addBank(bank);
    }

}
