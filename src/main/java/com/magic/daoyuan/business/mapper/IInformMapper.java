package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.Inform;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 通知
 * @author lzh
 * @create 2017/12/21 17:16
 */
public interface IInformMapper {


    /**
     * 后台页面 分页 通过公司id查询通知集合
     * @param map 公司id
     * @return
     */
    List<Inform> list(Map<String , Object> map);


    /**
     * 后台页面 统计 通过公司id查询通知数量
     * @param map 公司id
     * @return
     */
    int listCount(Map<String , Object> map);

    /**
     * 添加通知
     * @param inform
     */
    void save(Inform inform);
    void saveList(List<Inform> inform);

    /**
     * 删除
     * @param id
     */
    void delete(@Param("id") Integer id);

}
