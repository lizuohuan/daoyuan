package com.magic.daoyuan.business.service;

import com.magic.daoyuan.business.entity.BusinessInsurance;
import com.magic.daoyuan.business.entity.BusinessInsuranceItem;
import com.magic.daoyuan.business.entity.BusinessYc;
import com.magic.daoyuan.business.mapper.IBusinessInsuranceMapper;
import com.magic.daoyuan.business.mapper.IBusinessYcMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 专项服务 Service
 * Created by Eric Xie on 2017/10/25 0025.
 */
@Service
public class BusinessInsuranceService {

    @Resource
    private IBusinessInsuranceMapper businessInsuranceMapper;
    @Resource
    private IBusinessYcMapper businessYcMapper;

    /**
     * 获取专项服务 List
     * @param companySonTotalBillId 总账单ID
     * @return
     */
    public Map<String,Object> getBusinessInsurance(Integer companySonTotalBillId){
        Map<String,Object> result = new HashMap<String, Object>();
        Set<BusinessInsurance> businessInsurances = businessInsuranceMapper.queryBusinessInsurance(companySonTotalBillId);
        result.put("businessInsurance", businessInsurances);
        Set<BusinessInsuranceItem> data = new HashSet<BusinessInsuranceItem>();
        for (BusinessInsurance businessInsurance : businessInsurances) {
            if (null != businessInsurance.getBusinessInsuranceItemList()) {
                data.addAll(businessInsurance.getBusinessInsuranceItemList());
            }
        }
        result.put("businessItems",data);
        result.put("businessYc",businessYcMapper.queryBusinessYc(companySonTotalBillId));
        return result;
    }


    /**
     * 获取专项服务 List
     * @param companyId 公司id
     * @param billMonth 账单月
     * @return
     */
    public Map<String,Object> getBusinessInsurance2(Integer companyId,Date billMonth){
        Map<String,Object> result = new HashMap<String, Object>();
        Set<BusinessInsurance> businessInsurances = businessInsuranceMapper.queryBusinessInsuranceByCompanyIdAndBillMonth(companyId,billMonth);

        Iterator<BusinessInsurance> iterator = businessInsurances.iterator();
        List<BusinessInsurance> dd = new ArrayList<BusinessInsurance>();
        while (iterator.hasNext()){
            BusinessInsurance next = iterator.next();
            dd.add(next);
        }

        result.put("businessInsurance", dd);
        Set<BusinessInsuranceItem> data = new HashSet<BusinessInsuranceItem>();
        for (BusinessInsurance businessInsurance : businessInsurances) {
            if (null != businessInsurance.getBusinessInsuranceItemList()) {
                data.addAll(businessInsurance.getBusinessInsuranceItemList());
            }
        }
        result.put("businessItems",data);
        result.put("businessYc",businessYcMapper.queryBusinessYc2(companyId,billMonth));
        return result;
    }



}
