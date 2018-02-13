package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.CompanyBillInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Eric Xie on 2017/9/13 0013.
 */
public interface ICompanyBillInfoMapper {

    /**
     * 通过票据ID 查询票据详情
     * @param id
     * @return
     */
    CompanyBillInfo queryBillInfoById(@Param("id") Integer id);

    List<CompanyBillInfo> queryBillInfoByItems(Map<String,Object> params);

    int countBillInfoByItems(Map<String,Object> params);

    /**
     *  ADD BillInfo
     * @param billInfo Object
     * @return
     */
    Integer addBillInfo(CompanyBillInfo billInfo);

    /**
     * Batch Add BillInfo
     * @param billInfoList Object
     * @return
     */
    Integer batchAddBillInfo(@Param("billInfoList") List<CompanyBillInfo> billInfoList);

    /**
     * Update
     * @param billInfo billInfo
     * @return
     */
    Integer updateBillInfo(CompanyBillInfo billInfo);

    /**
     * Query BillInfo
     * @param companyId companyId
     * @return
     */
    List<CompanyBillInfo> queryBillInfo(@Param("companyId") Integer companyId);



}
