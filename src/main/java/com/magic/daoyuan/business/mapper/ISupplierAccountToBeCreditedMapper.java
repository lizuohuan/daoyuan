package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.SupplierAccountToBeCredited;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 供应商收款账户 接口
 * @author lzh
 * @create 2017/9/26 17:44
 */
public interface ISupplierAccountToBeCreditedMapper {


    int querySupplierBankInfo(@Param("accountName") String accountName,@Param("account") String account,@Param("supplerId") Integer supplerId);


    int delete(Integer id);

    int save(SupplierAccountToBeCredited record);

    SupplierAccountToBeCredited info(@Param("id") Integer id);

    int update(SupplierAccountToBeCredited record);

    int updateAll(SupplierAccountToBeCredited record);

    /**
     * 后台页面 分页 通过各种条件 查询供应商联系人集合
     * @param map map
     * @return
     */
    List<SupplierAccountToBeCredited> list(Map<String, Object> map);

    /**
     * 通过供应商ID获取--下拉使用
     * @param map map
     * @return
     */
    List<SupplierAccountToBeCredited> getBySupplierId(@Param("supplierId") Integer supplierId);

    /**
     * 后台页面 统计 供应商联系人数量
     * @param map map
     * @return
     */
    int listCount(Map<String, Object> map);

}
