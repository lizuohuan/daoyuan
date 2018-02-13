package com.magic.daoyuan.business.service;

import com.magic.daoyuan.business.entity.AccountConfig;
import com.magic.daoyuan.business.mapper.IAccountConfigMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Eric Xie on 2017/10/20 0020.
 */
@Service
public class AccountConfigService {


    @Resource
    private IAccountConfigMapper accountConfigMapper;


    public void update(AccountConfig accountConfig){
        accountConfigMapper.update(accountConfig);
    }

}
