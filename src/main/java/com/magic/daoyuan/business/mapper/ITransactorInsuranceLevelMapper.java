package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.TransactorInsuranceLevel;

import java.util.List;
import java.util.Map;

/**
 * 办理方绑定的险种及档次
 * @author lzh
 * @create 2017/9/28 14:57
 */
public interface ITransactorInsuranceLevelMapper {


    /**
     * 添加
     * @param record
     * @return
     */
    int save(TransactorInsuranceLevel record);

    /**
     * 详情
     * @param id
     * @return
     */
    TransactorInsuranceLevel info(Integer id);


    /**
     * 更新不为空的字段
     * @param record
     * @return
     */
    int update(TransactorInsuranceLevel record);

    /**
     * 更新全部
     * @param record
     * @return
     */
    int updateAll(TransactorInsuranceLevel record);


    /**
     * 后台页面 分页 通过各种条件 查询办理方绑定的险种及档次
     * @param map map
     * @return
     */
    List<TransactorInsuranceLevel> list(Map<String , Object> map);

    /**
     * 后台页面 统计 查询办理方绑定的险种及档次数量
     * @param map map
     * @return
     */
    int listCount(Map<String , Object> map);

}
