package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.Supplier;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 供应商
 */
public interface ISupplierMapper {

    Supplier querySupplierByName(@Param("supplierName") String supplierName);

    int delete(Integer id);

    int save(Supplier record);

    Supplier info(Integer id);

    int update(Supplier record);

    int updateAll(Supplier record);

    /**
     * 后台页面 分页 通过各种条件 查询供应商集合
     * @param map map
     * @return
     */
    List<Supplier> list(Map<String , Object> map);

    /**
     * 下拉列表--不分页供应商列表
     * @param map map
     * @return
     */
    List<Supplier> getAllList();

    /**
     * 后台页面 统计 供应商数量
     * @param map map
     * @return
     */
    int listCount(Map<String , Object> map);

    /**
     * 获取未被此经办机构绑定的供应商
     * @param organizationId 经办机构Id
     * @return
     */
    List<Supplier> getNOBindToSelect(@Param("organizationId") Integer organizationId,@Param("supplierId") Integer supplierId);

}