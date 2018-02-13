package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.Bank;
import com.magic.daoyuan.business.entity.BankInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Eric Xie on 2017/10/27 0027.
 */
public interface IBankInfoMapper {

    BankInfo info(@Param("id") Integer id);

    BankInfo queryBankInfoByAccount(@Param("bankAccount") String bankAccount, @Param("accountName") String accountName,
                                    @Param("companyId") Integer companyId);

    BankInfo queryBankInfoByConfirmRecord(@Param("companyId") Integer companyId,@Param("recordId") Integer recordId);


    List<BankInfo> queryBankInfo(@Param("bankAccount") String bankAccount, @Param("accountName") String accountName,
                                 @Param("companyIds") List<Integer> companyIds);

    List<BankInfo> queryBankInfoByCompany(List<Integer> companyIds);

    /**
     * 查询所有的银行信息 部分字段
     * @return
     */
    List<BankInfo> queryAllBankInfo();

    Integer addBankInfo(BankInfo bankInfo);

    /**
     * 批量新增
     * @param bankInfos
     * @return
     */
    Integer batchAddBankInfo(List<BankInfo> bankInfos);


    Integer updateBankInfo(BankInfo bankInfo);


    List<BankInfo> queryBankInfoByItems(Map<String,Object> map);


    int countBankInfoByItems(Map<String,Object> map);

    BankInfo getByCompanyId(@Param("companyId") Integer companyId);

}
