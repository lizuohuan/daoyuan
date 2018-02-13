package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.Transactor;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 办理方
 * @author lzh
 * @create 2017/9/27 15:01
 */
public interface ITransactorMapper {

    List<Transactor> queryAllTransactor();

    /**
     * 通过经办机构ID 查询办理方 部分字段
     * @param organizationId
     * @return
     */
    List<Transactor> queryTransactorByOrganization(@Param("organizationId") Integer organizationId);


    /**
     * 删除
     * @param id
     * @return
     */
    int delete(Integer id);

    /**
     * 添加
     * @param record
     * @return
     */
    int save(Transactor record);

    /**
     * 详情
     * @param id
     * @return
     */
    Transactor info(Integer id);

    /**
     * 更新不为空的字段
     * @param record
     * @return
     */
    int update(Transactor record);

    /**
     * 更新全部
     * @param record
     * @return
     */
    int updateAll(Transactor record);

    /**
     * 后台页面 分页 通过各种条件 查询办理方
     * @param map map
     * @return
     */
    List<Transactor> list(Map<String , Object> map);

    /**
     * 后台页面 统计 查询办理方数量
     * @param map map
     * @return
     */
    int listCount(Map<String , Object> map);

}
