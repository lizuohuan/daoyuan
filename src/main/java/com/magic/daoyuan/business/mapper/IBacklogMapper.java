package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.Backlog;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 待办
 * @author lzh
 * @create 2017/12/21 17:16
 */
public interface IBacklogMapper {


    /**
     * 后台页面 分页 通过公司id查询待办集合
     * @param map 公司id
     * @return
     */
    List<Backlog> list(Map<String, Object> map);


    /**
     * 后台页面 统计 通过公司id查询待办数量
     * @param map 公司id
     * @return
     */
    int listCount(Map<String, Object> map);

    /**
     * 添加待办
     * @param backlog
     */
    void save(Backlog backlog);

    /**
     * 删除
     * @param id
     */
    void delete(@Param("id") Integer id);

}
