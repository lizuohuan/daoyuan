package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.ConfirmMoneyCompany;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Eric Xie on 2017/10/30 0030.
 */
public interface IConfirmMoneyCompanyMapper {


    List<ConfirmMoneyCompany> queryConfirmMoneyCompanyByRecordId(@Param("recordId") Integer recordId);

    Integer batchAddConfirmMoneyCompany(List<ConfirmMoneyCompany> list);


    List<ConfirmMoneyCompany> queryConfirmMoneyCompany(Map<String,Object> map);


    int countConfirmMoneyCompany(Map<String,Object> map);

}
