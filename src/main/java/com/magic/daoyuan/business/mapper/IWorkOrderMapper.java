package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.WorkOrder;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 工单接口
 */
public interface IWorkOrderMapper {

    /**添加**/
    int insert(WorkOrder workOrder);

    /**修改**/
    void update(WorkOrder workOrder);

    /**后台分页查询**/
    List<WorkOrder> queryWorkOrderByItems(Map<String, Object> params);

    /**条件动态统计知识库**/
    Integer countWorkOrderByItems(Map<String, Object> params);

    /**通过ID查询**/
    WorkOrder findById(@Param("id") Integer id);

    /**
     * 获取超时工单
     * @param date
     * @return
     */
    List<WorkOrder> getOverTime(@Param("date") Date date);

}
