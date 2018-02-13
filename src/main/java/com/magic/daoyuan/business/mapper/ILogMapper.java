package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.Log;

import java.util.List;
import java.util.Map;

/**
 * Created by Eric Xie on 2017/12/27 0027.
 */
public interface ILogMapper {

    int add(Log log);

    int batchAdd(List<Log> logs);

    List<Log> queryLogByItems(Map<String,Object> map);

    int countLog(Map<String,Object> map);

}
