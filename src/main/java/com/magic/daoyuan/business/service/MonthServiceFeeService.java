package com.magic.daoyuan.business.service;

import com.magic.daoyuan.business.mapper.IMonthServiceFeeMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 月服务费
 * @author lzh
 * @create 2017/10/24 11:35
 */
@Service
public class MonthServiceFeeService {

    @Resource
    private IMonthServiceFeeMapper monthServiceFeeMapper;

}
