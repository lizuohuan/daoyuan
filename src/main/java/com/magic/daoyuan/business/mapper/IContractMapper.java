package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.Contract;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Eric Xie on 2017/9/14 0014.
 */
public interface IContractMapper {

    /**
     * 获取合同详情 包含附件集合
     * @param id
     * @return
     */
    Contract queryContractById(@Param("id") Integer id);

    /**
     * 增加合同
     * @param contract
     * @return
     */
    Integer addContract(Contract contract);

    /**
     * 更新合同
     * @param contract
     * @return
     */
    Integer updateContract(Contract contract);

    /**
     * 动态获取 合同
     * @param params
     * @return
     */
    List<Contract> queryContractByItems(Map<String,Object> params);

    /**
     * 动态查询合同
     * @param params
     * @return
     */
    Integer countContractByItems(Map<String,Object> params);

    /**
     * 查询公司下的合同  包含附件集合
     * @param companyId
     * @return
     */
    List<Contract> queryContractByCompany(@Param("companyId") Integer companyId);


    /**
     * 查询公司下的合同  不包含附件集合  升序排列
     * @param companyId
     * @return
     */
    List<Contract> queryContractByCompanyASC(@Param("companyId") Integer companyId);


}
