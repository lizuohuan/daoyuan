package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.WorkOrderSchedule;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 工单进度接口
 */
public interface IWorkOrderScheduleMapper {

    /**添加**/
    void insert(WorkOrderSchedule workOrder);

    /**后台分页查询**/
    List<WorkOrderSchedule> queryWorkOrderScheduleByItems(@Param("workOrderId") Integer workOrderId);

    /**根据工单ID获取上一级审批进度**/
    WorkOrderSchedule getNextWorkOrderSchedule(@Param("workOrderId") Integer workOrderId);

}
