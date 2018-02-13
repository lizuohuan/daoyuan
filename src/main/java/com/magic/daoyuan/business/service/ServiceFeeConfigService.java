package com.magic.daoyuan.business.service;

import com.magic.daoyuan.business.entity.ServiceFeeConfig;
import com.magic.daoyuan.business.mapper.IServiceFeeConfigMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Eric Xie on 2017/9/13 0013.
 */
@Service
public class ServiceFeeConfigService {

    @Resource
    private IServiceFeeConfigMapper serviceFeeConfigMapper;

    public List<ServiceFeeConfig> queryAllFeeConfig(){
        return serviceFeeConfigMapper.queryAllFeeConfig();
    }

}
