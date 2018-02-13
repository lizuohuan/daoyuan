package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.BusinessInsurance;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by Eric Xie on 2017/10/24 0024.
 */
public interface IBusinessInsuranceMapper {


    double countBusinessInsurance(@Param("companySonTotalBillId") Integer companySonTotalBillId);

    double countBusinessInsuranceByCompanyId(@Param("companyId") Integer companyId,
                                             @Param("billMonth") Date billMonth);

    Integer batchAddBusinessInsurance(List<BusinessInsurance> businessInsurances);


    Integer delBusinessInsurance(@Param("companySonTotalBillIds") Integer[] companySonTotalBillIds);

    Integer del(List<Integer> ids);


    Set<BusinessInsurance> queryBusinessInsurance(@Param("companySonTotalBillId") Integer companySonTotalBillId);

    Set<BusinessInsurance> queryBusinessInsuranceByCompanyIdAndBillMonth(@Param("companyId") Integer companyId, @Param("billMonth") Date billMonth);


    List<BusinessInsurance> queryBusinessInsuranceIds(@Param("companySonTotalBillIds") Integer[] companySonTotalBillIds);

}
