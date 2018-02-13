package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.ConfirmFund;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Eric Xie on 2017/10/31 0031.
 */
public interface IConfirmFundMapper {


    /**
     * 获取公司最新一条记录并且是追回尾款的记录
     * @param companyIds
     * @return
     */
    List<ConfirmFund> queryNewConfirmFundByCompany(List<Integer> companyIds);

    /**
     * 批量查询公司 有认款记录 且没有处理方式的记录 用于合并本次账单金额使用
     * @param companyIds
     * @return
     */
    List<ConfirmFund> queryConfirmFundByCompany(List<Integer> companyIds);

    /**
     * 查询没有处理方式的公司
     * @param companyIds
     * @return
     */
    List<ConfirmFund> queryConfirmFundByHandleMethod(List<Integer> companyIds);

    ConfirmFund queryById(@Param("id") Integer id);

    Integer batchAddConfirmFund(List<ConfirmFund> list);


    Integer addConfirmFund(ConfirmFund confirmFund);


    List<ConfirmFund> queryConfirmFundByRecord(@Param("confirmMoneyRecordId") Integer confirmMoneyRecordId);


    List<ConfirmFund> queryConfirmFundByItems(Map<String,Object> map);


    int countConfirmFundByItems(Map<String,Object> map);


    Integer updateConfirmFund(@Param("id") Integer id,@Param("handleMethod") Integer handleMethod);


    Integer update(ConfirmFund confirmFund);


    Integer updateConfirmFundBillAmount(@Param("id") Integer id,@Param("billAmount") Double billAmount);


    Integer batchUpdateHandleMethod(List<ConfirmFund> confirmFunds);

    /**
     * 获取超时 的 没有选择核销处理方式
     * @param date
     * @return
     */
    List<ConfirmFund> getUnDispose(@Param("date") Date date);

}
