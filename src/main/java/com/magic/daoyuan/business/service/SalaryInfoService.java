package com.magic.daoyuan.business.service;

import com.magic.daoyuan.business.entity.PageArgs;
import com.magic.daoyuan.business.entity.PageList;
import com.magic.daoyuan.business.entity.SalaryInfo;
import com.magic.daoyuan.business.mapper.ISalaryInfoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Eric Xie on 2017/10/20 0020.
 */
@Service
public class SalaryInfoService {

    @Resource
    private ISalaryInfoMapper salaryInfoMapper;



    public PageList<SalaryInfo> querySalaryInfo(Integer companySonTotalBillId,
                                                PageArgs pageArgs, Date billMonth, Integer companyId){
        List<SalaryInfo> dataList = new ArrayList<SalaryInfo>();
        Map<String,Object> data = new HashMap<String, Object>();
        data.put("companySonTotalBillId",companySonTotalBillId);
        data.put("billMonth", billMonth);
        data.put("companyId", companyId);
        int count = salaryInfoMapper.countSalaryInfo(data);
        if(count > 0){
            data.put("limit",pageArgs.getPageStart());
            data.put("limitSize",pageArgs.getPageSize());
            dataList = salaryInfoMapper.querySalaryInfo(data);
        }
        return new PageList<SalaryInfo>(dataList,count);

    }



    public List<SalaryInfo> querySalaryInfo2(Integer companyId,Date billMonth){
        Map<String,Object> data = new HashMap<String, Object>();
        data.put("companySonTotalBillId",null);
        data.put("billMonth", billMonth);
        data.put("companyId", companyId);
        return salaryInfoMapper.querySalaryInfo(data);
    }



    public List<SalaryInfo> querySalaryInfo(Integer companySonTotalBillId){
        return salaryInfoMapper.querySalaryInfoByCompanySonTotalBillId(companySonTotalBillId);
    }


}
