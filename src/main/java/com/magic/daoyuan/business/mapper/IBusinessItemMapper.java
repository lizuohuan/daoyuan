package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.BusinessItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Eric Xie on 2017/9/22 0022.
 */
public interface IBusinessItemMapper {


    /**
     *  通过公司 查询公司下所有的商业险 或者 一次性险 下的 所有子类险种
     * @param companyId 公司ID
     * @param businessId  业务ID  商业险ID  或者 一次性险 ID
     * @return
     */
    List<BusinessItem> queryBusinessItemByCompany(@Param("companyId") Integer companyId,@Param("businessId") Integer businessId);

    /**
     *  通过公司 查询公司下所有的商业险 或者 一次性险 下的 所有子类险种
     * @param companyIds 公司ID
     * @param businessId  业务ID  商业险ID  或者 一次性险 ID
     * @return
     */
    List<BusinessItem> queryBusinessItemByCompany2(@Param("companyIds") Set<Integer> companyIds, @Param("businessId") Integer businessId);


    Integer addBusinessItem(BusinessItem item);

    BusinessItem info(@Param("id") Integer id);


    Integer updateBusinessItem(BusinessItem item);


    List<BusinessItem> queryBusinessItem(Map<String,Object> params);


    int countBusinessItem(Map<String,Object> params);


    List<BusinessItem> queryBusinessByBusiness(@Param("businessId") Integer businessId);


    List<BusinessItem> queryBusinessByCompany(@Param("businessId") Integer businessId,@Param("companyId") Integer companyId);


    /**
     * 通过员工 业务 ID 查询 子业务集合
     * @param memberBusinessId
     * @return
     */
    List<BusinessItem> queryBusinessItemsByMember(@Param("memberBusinessId") Integer memberBusinessId);


}
