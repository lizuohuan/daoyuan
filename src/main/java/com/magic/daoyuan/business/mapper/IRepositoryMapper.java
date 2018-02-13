package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.Repository;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 知识库接口
 */
public interface IRepositoryMapper {

    /**添加知识库**/
    void insert(Repository repository);

    /**修改知识库**/
    void update(Repository repository);

    /**后台分页查询**/
    List<Repository> queryRepositoryByItems(Map<String,Object> params);

    /**通过ID查询**/
    Repository findById(@Param("id") Integer id);

    /**条件动态统计知识库**/
    Integer countRepositoryByItems(Map<String,Object> params);

    /**统计当月添加了多少条**/
    Integer countNumber(@Param("dateTime") Date dateTime);
}
