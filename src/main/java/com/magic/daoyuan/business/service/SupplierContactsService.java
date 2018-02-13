package com.magic.daoyuan.business.service;

import com.magic.daoyuan.business.entity.PageArgs;
import com.magic.daoyuan.business.entity.PageList;
import com.magic.daoyuan.business.entity.Supplier;
import com.magic.daoyuan.business.entity.SupplierContacts;
import com.magic.daoyuan.business.mapper.ISupplierContactsMapper;
import com.magic.daoyuan.business.mapper.ISupplierMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 供应商联系人实现
 * @author lzh
 * @create 2017/9/26 16:15
 */
@Service
public class SupplierContactsService {

    @Resource
    private ISupplierContactsMapper supplierContactsMapper;

    /**
     * 新增
     * @param supplier
     */
    public void save(SupplierContacts supplier) {
        supplierContactsMapper.save(supplier);
    }

    /**
     * 更新不为空字段
     * @param supplier
     */
    public void update(SupplierContacts supplier) {
        supplierContactsMapper.update(supplier);
    }

    /**
     * 更新所有字段
     * @param supplier
     */
    public void updateAll(SupplierContacts supplier) {
        supplierContactsMapper.updateAll(supplier);
    }

    /**
     * 详情
     * @param id
     * @return
     */
    public SupplierContacts info(Integer id) {
        return supplierContactsMapper.info(id);
    }


    /**
     * 后台页面 分页获取供应商联系人
     *
     * @param pageArgs    分页属性
     * @param jobTitle 职位
     * @param departmentName 部门
     * @param weChat 微信号
     * @param email Email
     * @param qq QQ
     * @param phone 电话号码
     * @param landLine 固定电话 - 座机
     * @param contactsName 联系人
     * @param supplierId 供应商ID
     * @return
     */
    public PageList<SupplierContacts> list(PageArgs pageArgs , String jobTitle ,  String departmentName ,
                                           String weChat ,  String email ,  String qq , String phone ,
                                           String landLine , String contactsName , Integer supplierId) {
        PageList<SupplierContacts> pageList = new PageList<SupplierContacts>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("jobTitle", jobTitle);
        map.put("departmentName", departmentName);
        map.put("weChat", weChat);
        map.put("email", email);
        map.put("qq", qq);
        map.put("phone", phone);
        map.put("landLine", landLine);
        map.put("contactsName", contactsName);
        map.put("supplierId", supplierId);
        int count = supplierContactsMapper.listCount(map);
        if (count > 0) {
            map.put("pageArgs", pageArgs);
            pageList.setList(supplierContactsMapper.list(map));
        }
        pageList.setTotalSize(count);
        return pageList;
    }

}
