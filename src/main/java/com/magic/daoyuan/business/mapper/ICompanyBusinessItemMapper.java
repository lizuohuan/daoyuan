package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.BusinessItem;
import com.magic.daoyuan.business.entity.CompanyBusinessItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Eric Xie on 2017/9/22 0022.
 */
public interface ICompanyBusinessItemMapper {

    /**
     * 查询一次性业务的子账单ID
     * @param businessId
     * @param companyId
     * @return
     */
    List<BusinessItem> queryItemByCompany(@Param("businessId") Integer businessId,@Param("companyId") Integer companyId);

    Integer batchAddCompanyBusinessItem(@Param("items") List<CompanyBusinessItem> companyBusinessItems);


    Integer delCompanyBusinessItem(@Param("companyBusinessItemIds") List<Integer> companyBusinessItemIds);

    /**
     * 通过公司 业务 ID 查询 子业务集合
     * @param companyBusinessId
     * @return
     */
    List<BusinessItem> queryBusinessItems(@Param("companyBusinessId") Integer companyBusinessId);

    List<BusinessItem> queryBusinessItemByBusiness(@Param("businessId") Integer businessId);

    Integer del(List<Integer> list);

}
