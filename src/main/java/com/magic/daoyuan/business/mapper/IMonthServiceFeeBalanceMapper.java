package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.MonthServiceFeeBalance;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * 本月应该补差的服务费，纳入下月计算
 * @author lzh
 * @create 2018/1/2 11:15
 */
public interface IMonthServiceFeeBalanceMapper {


    void save(MonthServiceFeeBalance balance);


    void update(MonthServiceFeeBalance balance);


    MonthServiceFeeBalance getByCompanyIdAndBillMonth(@Param("companyId") Integer companyId ,
                                                      @Param("billMonth") Date billMonth);

    MonthServiceFeeBalance getByCompanyIdAndBillMonth2(@Param("companyId") Integer companyId ,
                                                      @Param("billMonth") Date billMonth);


}
