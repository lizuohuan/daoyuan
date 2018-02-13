package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.MonthServiceFee;
import com.magic.daoyuan.business.entity.MonthServiceFeeDetail;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 每月服务费
 * @author lzh
 * @create 2017/10/23 19:38
 */
public interface IMonthServiceFeeMapper {


    /**
     * 通过总账单ID
     * @param totalBillIds
     * @return
     */
    List<MonthServiceFeeDetail> queryMonthServiceFeeByTotalBillId(List<Integer> totalBillIds);

    /**
     * 添加批量
     * @param records
     * @return
     */
    int save(List<MonthServiceFee> records);

    /**
     * 批量更新
     * @param records
     */
    void updateList(List<MonthServiceFee> records);

    /**
     * 详情
     * @param id
     * @return
     */
    MonthServiceFee info(@Param("id") Integer id);

    /**
     * 根据汇总id获取服务费
     * @param companySonTotalBillId
     * @return
     */
    Double getByCompanySonTotalBillId(@Param("companySonTotalBillId") Integer companySonTotalBillId);


    void delete(@Param("companySonTotalBillIds") Integer[] companySonTotalBillIds);


    /**
     * 根据公司id和服务月查询所有服务费账单的数量
     * @param companyId
     * @return
     */
    int getByCompanyIdAndServiceMonthCount(@Param("companyId") Integer companyId ,
                                           @Param("serviceMonth") Date serviceMonth);

    /** 查询已缴纳服务费 */
    List<MonthServiceFee> getByDateAndCompanyCompanyId(@Param("dateSet") Set<Date> dateSet,
                                                       @Param("companyIds") Integer[] companyIds);

    /** 查询单个共欧式已缴纳服务费及明细 */
    List<MonthServiceFee> getByDateAndCompanyId(@Param("date") Date date,
                                                @Param("companyId") Integer companyId);


    MonthServiceFee getByDateAndCompanyIdOnlyOne(@Param("date") Date date,
                                                 @Param("companyId") Integer companyId);

    /**
     * 根据服务月 公司id 服务费配置id获取服务费总和
     * @param date
     * @param companyId
     * @param serviceFeeConfigId
     * @return
     */
    Double getByDateAndCompanyIdOnlyOneAndServiceFeeConfigId(@Param("date") Date date,
                                                 @Param("companyId") Integer companyId,
                                                 @Param("serviceFeeConfigId") Integer serviceFeeConfigId);

}
