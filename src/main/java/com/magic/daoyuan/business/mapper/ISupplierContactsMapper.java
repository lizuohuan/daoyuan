package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.SupplierContacts;

import java.util.List;
import java.util.Map;

/**
 * 供应商联系人
 */
public interface ISupplierContactsMapper {

    int delete(Integer id);

    int save(SupplierContacts record);

    SupplierContacts info(Integer id);

    int update(SupplierContacts record);

    int updateAll(SupplierContacts record);

    /**
     * 后台页面 分页 通过各种条件 查询供应商联系人集合
     * @param map map
     * @return
     */
    List<SupplierContacts> list(Map<String, Object> map);

    /**
     * 后台页面 统计 供应商联系人数量
     * @param map map
     * @return
     */
    int listCount(Map<String, Object> map);

}