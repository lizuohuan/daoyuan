package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.Bank;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Eric Xie on 2017/10/10 0010.
 */
public interface IBankMapper {


    Bank queryBankById(@Param("id") Integer id);

    Integer addBank(Bank bank);


    Integer updateBank(Bank bank);


    List<Bank> queryBank(Map<String,Object> params);


    int countCount(Map<String,Object> params);


    List<Bank> queryAllBank();

}
