package com.magic.daoyuan.business.service;

import com.magic.daoyuan.business.entity.Log;
import com.magic.daoyuan.business.entity.PageArgs;
import com.magic.daoyuan.business.entity.PageList;
import com.magic.daoyuan.business.mapper.ILogMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Eric Xie on 2017/12/27 0027.
 */
@Service
public class LogService {

    @Resource
    private ILogMapper logMapper;

    public void add(Log log){
        logMapper.add(log);
    }

    public PageList<Log> getLog(Map<String,Object> params, PageArgs pageArgs){
        int count = logMapper.countLog(params);
        List<Log> logs = new ArrayList<Log>();
        if(count > 0){
            params.put("pageArgs",pageArgs);
            logs = logMapper.queryLogByItems(params);
        }
        return new PageList<Log>(logs,count);
    }

}
