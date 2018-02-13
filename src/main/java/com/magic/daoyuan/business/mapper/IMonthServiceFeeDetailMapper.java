package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.MonthServiceFeeDetail;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by Eric Xie on 2017/11/24 0024.
 */
public interface IMonthServiceFeeDetailMapper {


    Integer batchAdd(List<MonthServiceFeeDetail> detailList);


    Integer del(@Param("monthServiceFeeId") Integer monthServiceFeeId);


    void delete(@Param("companySonTotalBillIds") Integer[] companySonTotalBillIds);

    /**
     * 获取根据员工id和服务月
     * @param memberId
     * @param month
     * @return
     */
    List<MonthServiceFeeDetail> getByMemberIdAndMonth(@Param("memberId") Integer memberId,
                                                @Param("month") Date month);

}
