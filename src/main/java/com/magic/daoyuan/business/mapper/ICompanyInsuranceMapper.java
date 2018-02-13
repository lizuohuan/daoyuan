package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.CompanyInsurance;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 公司险种
 * @author lzh
 * @create 2017/10/10 15:17
 */
public interface ICompanyInsuranceMapper {

    /**
     * 批量添加
     * @param records
     * @return
     */
    int save(@Param("records") List<CompanyInsurance> records);

    /**
     * 详情
     * @param id
     * @return
     */
    CompanyInsurance info(Integer id);

    /**
     * 更新不为空的字段
     * @param record
     * @return
     */
    int update(CompanyInsurance record);

    /**
     * 通过各种条件 查询公司险种
     * @param map map
     * @return
     */
    List<CompanyInsurance> list(Map<String , Object> map);

    /**
     * 删除公司下所有险种
     * @param companyId
     */
    void delete(@Param("companyId") Integer companyId,@Param("companyPayPlaceId") Integer companyPayPlaceId);
}
