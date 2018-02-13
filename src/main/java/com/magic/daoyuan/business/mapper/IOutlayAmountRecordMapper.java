package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.OutlayAmountRecord;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Eric Xie on 2017/11/17 0017.
 */
public interface IOutlayAmountRecordMapper {


    Integer addOutlayAmountRecord(OutlayAmountRecord record);


    Integer updateOutlayAmountRecord(OutlayAmountRecord record);


    List<OutlayAmountRecord> queryOutlayAmountRecordByItems(Map<String,Object> map);


    int countOutlayAmountRecordByItems(Map<String,Object> map);


    OutlayAmountRecord queryById(@Param("id") Integer id);


    /**
     * 根据账单月 和公司id获取出款单
     * @param companyId
     * @param billMonth
     * @return
     */
    OutlayAmountRecord getByBillMonthAndCompanyId(@Param("companyId") Integer companyId,
                                                  @Param("billMonth") Date billMonth);

}
