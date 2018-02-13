package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.Organization;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Eric Xie on 2017/9/27 0027.
 */
public interface IOrganizationMapper {


    Map<String,Double> countByOrganization(@Param("organization") Integer organization);

    List<Organization> queryAllOrganization();

    Integer addOrganization(Organization organization);


    Integer updateOrganization(Organization organization);


    List<Organization> queryOrganizationByItems(Map<String,Object> params);


    int countOrganizationByItems(Map<String,Object> params);


    Organization queryOrganizationById(@Param("id") Integer id);

    /**
     * 通过缴金地 查询经办机构的ID 和 名称
     * @param payPlaceId
     * @return
     */
    List<Organization> queryOrganizationByPayPlace(@Param("payPlaceId") Integer payPlaceId);

}
