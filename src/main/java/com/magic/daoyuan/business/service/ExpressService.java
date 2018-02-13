package com.magic.daoyuan.business.service;

import com.magic.daoyuan.business.entity.ExpressCompany;
import com.magic.daoyuan.business.entity.ExpressInfo;
import com.magic.daoyuan.business.entity.PageArgs;
import com.magic.daoyuan.business.entity.PageList;
import com.magic.daoyuan.business.mapper.IExpressCompanyMapper;
import com.magic.daoyuan.business.mapper.IExpressInfoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 快递类 业务层
 * Created by Eric Xie on 2017/9/21 0021.
 */
@Service
public class ExpressService {

    @Resource
    private IExpressInfoMapper expressInfoMapper;
    @Resource
    private IExpressCompanyMapper expressCompanyMapper;


    /**
     * 通过ID 获取 快递信息d
     * @param id
     * @return
     */
    public ExpressInfo queryExpressInfoById(Integer id){
        return expressInfoMapper.queryExpressInfoById(id);
    }

    /**
     * 通过ID 获取 快递公司
     * @param id
     * @return
     */
    public ExpressCompany queryExpressCompanyById(Integer id){
        return expressCompanyMapper.queryExpressCompanyById(id);
    }

    /**
     * 查询快递信息
     * @param params
     * @param pageArgs
     * @return
     */
    public PageList<ExpressInfo> queryExpressInfo(Map<String,Object> params,PageArgs pageArgs){
        int count  = expressInfoMapper.countExpressInfo(params);
        List<ExpressInfo> dataList = new ArrayList<ExpressInfo>();
        if(count > 0){
            params.put("limit",pageArgs.getPageStart());
            params.put("limitSize",pageArgs.getPageSize());
            dataList = expressInfoMapper.queryExpressInfo(params);
        }
        return new PageList<ExpressInfo>(dataList,count);
    }

    /**
     * 获取所有的快递公司
     * @return
     */
    public List<ExpressCompany> queryAllExpressCompany(){
        return expressCompanyMapper.queryExpressCompany(null,null);
    }

    /**
     * 查询 快递公司列表
     * @return
     */
    public PageList<ExpressCompany> queryExpressCompany(PageArgs pageArgs){
        int count = expressCompanyMapper.countExpressCompany();
        List<ExpressCompany> dataList = new ArrayList<ExpressCompany>();
        if(count > 0){
            dataList = expressCompanyMapper.queryExpressCompany(pageArgs.getPageStart(),pageArgs.getPageSize());
        }
        return new PageList<ExpressCompany>(dataList,count);
    }

    /**
     * 更新快递信息
     * @param expressInfo
     */
    public void updateExpressInfo(ExpressInfo expressInfo){
        ExpressInfo info = expressInfoMapper.queryExpressInfoById(expressInfo.getId());
        if(null != info){
            if(info.getIsReceive() == 0 && expressInfo.getIsReceive() == 1){
                expressInfo.setReceiveDate(new Date());
            }
        }
        expressInfoMapper.updateExpressInfo(expressInfo);
    }

    /**
     * 新增快递信息
     * @param expressInfo
     */
    public void addExpressInfo(ExpressInfo expressInfo){
        expressInfoMapper.addExpressInfo(expressInfo);
    }

    /**
     * 更新快递公司信息
     * @param expressCompany
     */
    public void updateExpressCompany(ExpressCompany expressCompany){
        expressCompanyMapper.updateExpressCompany(expressCompany);
    }

    /**
     * 新增快递公司
     * @param expressCompany
     */
    public void addExpressCompany(ExpressCompany expressCompany){
        expressCompanyMapper.addExpressCompany(expressCompany);
    }


}
