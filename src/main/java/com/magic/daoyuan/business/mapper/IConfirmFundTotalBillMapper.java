package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.ConfirmFundTotalBill;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Eric Xie on 2017/10/31 0031.
 */
public interface IConfirmFundTotalBillMapper {


    Integer batchAddConfirmFundTotalBill(List<ConfirmFundTotalBill> confirmFundTotalBills);


    Integer addConfirmFundTotalBill(ConfirmFundTotalBill confirmFundTotalBill);


    List<ConfirmFundTotalBill> queryConfirmFundTotalBillByCompanyId(List<Integer> companyIds);


    Integer update(List<ConfirmFundTotalBill> list);


    Integer del(List<ConfirmFundTotalBill> list);


}
