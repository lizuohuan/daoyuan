package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.AccountConfig;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * Created by Eric Xie on 2017/10/20 0020.
 */
public interface IAccountConfigMapper {



    @Select("select * from account_config")
    AccountConfig queryAccountConfig();


    @Update("update account_config set accountName = #{config.accountName}," +
            "bankAccount = #{config.bankAccount},bankName=#{config.bankName},aliPayAccount=#{config.aliPayAccount},aliPayName=#{config.aliPayName}" +
            "where id = 1")
    void update(@Param("config") AccountConfig accountConfig);



}
