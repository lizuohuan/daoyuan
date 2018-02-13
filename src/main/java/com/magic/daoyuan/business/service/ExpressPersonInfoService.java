package com.magic.daoyuan.business.service;

import com.magic.daoyuan.business.entity.ExpressPersonInfo;
import com.magic.daoyuan.business.entity.PageArgs;
import com.magic.daoyuan.business.entity.PageList;
import com.magic.daoyuan.business.mapper.IExpressPersonInfoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Eric Xie on 2017/9/26 0026.
 */

@Service
public class ExpressPersonInfoService {


    @Resource
    private IExpressPersonInfoMapper expressPersonInfoMapper;


    public List<ExpressPersonInfo> queryAllPersonInfoByCompany(Integer companyId){
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("companyId",companyId);
        params.put("limit",null);
        params.put("limitSize",null);
        return expressPersonInfoMapper.queryExpressPersonInfoByItems(params);
    }

    public ExpressPersonInfo queryPersonInfoById(Integer id){
        return expressPersonInfoMapper.queryInfoById(id);
    }


    public PageList<ExpressPersonInfo> queryInfoByItems(Integer companyId, PageArgs pageArgs){
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("companyId",companyId);
        List<ExpressPersonInfo> dataList = new ArrayList<ExpressPersonInfo>();
        int count = expressPersonInfoMapper.countExpressPersonInfoByItems(params);
        if(count > 0){
            params.put("limit",pageArgs.getPageStart());
            params.put("limitSize",pageArgs.getPageSize());
            dataList = expressPersonInfoMapper.queryExpressPersonInfoByItems(params);
        }
        return new PageList<ExpressPersonInfo>(dataList,count);
    }


    @Transactional
    public void addExpressPersonInfo(ExpressPersonInfo info){
        if(info.getIsDefault() == 1){
            expressPersonInfoMapper.updateAllIsDefault(info.getCompanyId());
        }
        expressPersonInfoMapper.addExpressPersonInfo(info);
    }

    @Transactional
    public void updateExpressPersonInfo(ExpressPersonInfo info){
        if(null != info.getIsDefault() && null != info.getCompanyId() && 1 == info.getIsDefault()){
            expressPersonInfoMapper.updateAllIsDefault(info.getCompanyId());
        }
        expressPersonInfoMapper.updateExpressPersonInfo(info);

    }








}
