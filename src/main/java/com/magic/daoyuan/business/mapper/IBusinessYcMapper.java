package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.BusinessYc;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by Eric Xie on 2017/10/25 0025.
 */
public interface IBusinessYcMapper {




    double countBusinessYc(@Param("companySonTotalBillId") Integer companySonTotalBillId);

    double countBusinessYcByCompanyId(@Param("companyId") Integer companyId,
                                      @Param("billMonth") Date billMonth);

    Integer batchAddBusinessYc(List<BusinessYc> businessYcList);


    Integer delBusinessYc(@Param("companySonTotalBillId") Integer companySonTotalBillId);


    List<BusinessYc> queryBusinessYc(@Param("companySonTotalBillId") Integer companySonTotalBillId);


    List<BusinessYc> queryBusinessYc3( @Param("list") Integer[] list);


    List<BusinessYc> queryBusinessYc2(@Param("companyId") Integer companyId,@Param("billMonth") Date billMonth);


    Integer del(List<Integer> ids);

}
