package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.BusinessInsuranceItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Eric Xie on 2017/10/25 0025.
 */
public interface IBusinessInsuranceItemMapper {


    /**
     * 通过 总账单ID 查询 其他业务子类 明细
     * @param totalBillIds
     * @return
     */
    List<BusinessInsuranceItem> queryBusinessInsuranceItemByTotalBillId(List<Integer> totalBillIds);


    Integer batchAddBusinessInsuranceItem(List<BusinessInsuranceItem> insuranceItemList);


    Integer delBusinessInsuranceItem(@Param("businessInsuranceId") Integer businessInsuranceId);



    List<BusinessInsuranceItem> queryBusinessInsuranceItem(@Param("businessInsuranceId") Integer businessInsuranceId);

    void delBusinessInsuranceItemByIds(@Param("ids") List<Integer> ids);
}
