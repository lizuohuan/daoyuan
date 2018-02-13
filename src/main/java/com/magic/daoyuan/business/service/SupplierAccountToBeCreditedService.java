package com.magic.daoyuan.business.service;

import com.magic.daoyuan.business.entity.PageArgs;
import com.magic.daoyuan.business.entity.PageList;
import com.magic.daoyuan.business.entity.SupplierAccountToBeCredited;
import com.magic.daoyuan.business.exception.InterfaceCommonException;
import com.magic.daoyuan.business.mapper.ISupplierAccountToBeCreditedMapper;
import com.magic.daoyuan.business.util.StatusConstant;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 供应商收款账户实现
 * @author lzh
 * @create 2017/9/26 16:15
 */
@Service
public class SupplierAccountToBeCreditedService {

    @Resource
    private ISupplierAccountToBeCreditedMapper supplierAccountToBeCreditedMapper;

    /**
     * 新增
     * @param supplier
     */
    public void save(SupplierAccountToBeCredited supplier) throws InterfaceCommonException {

        int i = supplierAccountToBeCreditedMapper.querySupplierBankInfo(supplier.getAccountName(), supplier.getAccount(),supplier.getSupplierId());
        if(i > 0){
            throw new InterfaceCommonException(StatusConstant.OBJECT_EXIST,"银行信息已经存在");
        }
        supplierAccountToBeCreditedMapper.save(supplier);
    }

    /**
     * 更新不为空字段
     * @param supplier
     */
    public void update(SupplierAccountToBeCredited supplier) {

        SupplierAccountToBeCredited i = supplierAccountToBeCreditedMapper.info(supplier.getId());
        if(null == i){
            throw new InterfaceCommonException(StatusConstant.Fail_CODE,"银行信息不存在");
        }
        if(!i.getAccountName().equals(supplier.getAccountName()) || !i.getAccount().equals(supplier.getAccount())){
            int i1 = supplierAccountToBeCreditedMapper.querySupplierBankInfo(supplier.getAccountName(), supplier.getAccount(),supplier.getSupplierId());
            if(i1 > 0){
                throw new InterfaceCommonException(StatusConstant.OBJECT_EXIST,"银行信息已经存在");
            }
        }
        supplierAccountToBeCreditedMapper.update(supplier);
    }

    /**
     * 更新所有字段
     * @param supplier
     */
    public void updateAll(SupplierAccountToBeCredited supplier) {
        supplierAccountToBeCreditedMapper.updateAll(supplier);
    }

    /**
     * 详情
     * @param id
     * @return
     */
    public SupplierAccountToBeCredited info(Integer id) {
        return supplierAccountToBeCreditedMapper.info(id);
    }


    /**
     * 后台页面 分页获取供应商联系人
     *
     * @param pageArgs    分页属性
     * @param accountName 账户名
     * @param account 账号
     * @param bankName 开户行 type=0时不能为空
     * @param type 账号类型 0：银行卡  1：支付宝
     * @param supplierId 供应商ID
     * @return
     */
    public PageList<SupplierAccountToBeCredited> list(PageArgs pageArgs , String accountName ,  String account ,
                                           String bankName , Integer type , Integer supplierId)  {
        PageList<SupplierAccountToBeCredited> pageList = new PageList<SupplierAccountToBeCredited>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("accountName", accountName);
        map.put("account", account);
        map.put("bankName", bankName);
        map.put("type", type);
        map.put("supplierId", supplierId);
        int count = supplierAccountToBeCreditedMapper.listCount(map);
        if (count > 0) {
            map.put("pageArgs", pageArgs);
            pageList.setList(supplierAccountToBeCreditedMapper.list(map));
        }
        pageList.setTotalSize(count);
        return pageList;
    }

    /****
     * 根据供应商ID查询 -- 下拉使用
     * @param supplierId
     * @return
     */
    public List<SupplierAccountToBeCredited> getBySupplierId (Integer supplierId) {
        List<SupplierAccountToBeCredited> list = new ArrayList<SupplierAccountToBeCredited>();
        list = supplierAccountToBeCreditedMapper.getBySupplierId(supplierId);
        return list;
    }

}
