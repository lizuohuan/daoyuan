package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.PayTheWay;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 缴纳规则
 * @author lzh
 * @create 2017/9/27 11:59
 */
public interface IPayTheWayMapper {

    /**
     * 通过档次查询所有的险种配置
     * @param levelId
     * @return
     */
    List<PayTheWay> queryPayTheWayByLevel(@Param("levelId") Integer levelId);

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
    int save(PayTheWay record);

    /**
     * 详情
     * @param id
     * @return
     */
    PayTheWay info(Integer id);

    /**
     * 更新不为空的字段
     * @param record
     * @return
     */
    int update(PayTheWay record);

    /**
     * 更新全部
     * @param record
     * @return
     */
    int updateAll(PayTheWay record);

    /**
     * 后台页面 分页 通过各种条件 查询供应商集合
     * @param map map
     * @return
     */
    List<PayTheWay> list(Map<String , Object> map);

    /**
     * 后台页面 统计 供应商数量
     * @param map map
     * @return
     */
    int listCount(Map<String , Object> map);

    /**
     * 查询 档次 下的配置对象
     * @param insuranceLevelId
     * @return
     */
    PayTheWay queryInsuranceById(@Param("insuranceLevelId") Integer insuranceLevelId);

    /**
     * 通过险种ID 查询配置
     * @param insuranceId
     * @return
     */
    List<PayTheWay> queryPayTheWayByInsurance(@Param("insuranceId") Integer insuranceId);

    /**
     * 根据险种id 获取上一次最新添加的数据
     * @param insuranceId
     * @return
     */
    PayTheWay getNewByInsuranceId(@Param("insuranceId") Integer insuranceId);

}
