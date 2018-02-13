package com.magic.daoyuan.business.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.Map;

/**
 * 控制台 部分数据
 * @author lzh
 * @create 2017/12/25 15:47
 */
public interface IConsoleMapper {

    /**
     * 控制台右下角统计数据
     * @param date
     * @return
     */
    Map<String ,Object> rightConsole(@Param("date") Date date);


}
