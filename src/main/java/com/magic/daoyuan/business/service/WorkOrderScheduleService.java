package com.magic.daoyuan.business.service;

import com.magic.daoyuan.business.entity.PageArgs;
import com.magic.daoyuan.business.entity.PageList;
import com.magic.daoyuan.business.entity.Repository;
import com.magic.daoyuan.business.entity.WorkOrderSchedule;
import com.magic.daoyuan.business.mapper.IRepositoryMapper;
import com.magic.daoyuan.business.mapper.IWorkOrderScheduleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 工单进度业务
 */
@Service
public class WorkOrderScheduleService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private IWorkOrderScheduleMapper workOrderScheduleMapper;

    /**
     * 添加
     */
    public void insert(WorkOrderSchedule workOrderSchedule){
        try {
            workOrderScheduleMapper.insert(workOrderSchedule);
        } catch (Exception e) {
            logger.error("服务器超时,添加失败",e);
        }
    }

    /**
     * 通过工单ID查询进度
     */
    public List<WorkOrderSchedule> queryRepositoryByItems (Integer workOrderId) {
        List<WorkOrderSchedule> list = new ArrayList<WorkOrderSchedule>();
        list = workOrderScheduleMapper.queryWorkOrderScheduleByItems(workOrderId);
        return list;
    }

    /**
     * 根据工单ID获取上一级审批进度
     */
    public WorkOrderSchedule getNextWorkOrderSchedule (Integer workOrderId) {
        return workOrderScheduleMapper.getNextWorkOrderSchedule(workOrderId);
    }

}
