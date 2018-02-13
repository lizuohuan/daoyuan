package com.magic.daoyuan.business.service;

import com.magic.daoyuan.business.entity.*;
import com.magic.daoyuan.business.mapper.IInformMapper;
import com.magic.daoyuan.business.mapper.IUserMapper;
import com.magic.daoyuan.business.mapper.IWorkOrderMapper;
import com.magic.daoyuan.business.mapper.IWorkOrderScheduleMapper;
import com.magic.daoyuan.business.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 工单业务
 */
@Service
public class WorkOrderService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private IWorkOrderMapper workOrderMapper;
    @Resource
    private IUserMapper userMapper;
    @Resource
    private IInformMapper informMapper;
    /**
     * 添加
     */
    @Transactional
    public int insert(WorkOrder workOrder){
        int workOrderId = 0;
        try {
            workOrderMapper.insert(workOrder);
            workOrderId = workOrder.getId();

            User user = userMapper.queryUserById(workOrder.getUserId());
            Inform inform = new Inform();
            inform.setRoleId(user.getRoleId());
            inform.setUserId(user.getId());
            inform.setContent("有新的工单需要处理");
            informMapper.save(inform);

        } catch (Exception e) {
            logger.error("服务器超时,添加失败",e);
        }
        return workOrderId;
    }

    /**
     * 修改
     */
    @Transactional
    public void update(WorkOrder workOrder){
        try {
            workOrderMapper.update(workOrder);
            if (!CommonUtil.isEmpty(workOrder.getStatus()) && workOrder.getStatus() == 3) {
                User user = userMapper.queryUserById(workOrder.getProposerId());
                Inform inform = new Inform();
                inform.setRoleId(user.getRoleId());
                inform.setUserId(user.getId());
                inform.setContent("工单已处理完成");
                informMapper.save(inform);
            }
        } catch (Exception e) {
            logger.error("服务器超时,更新失败",e);
        }
    }

    /**分页查询工单**/
    public PageList<WorkOrder> queryWorkOrderByItems (Map<String,Object> params, PageArgs pageArgs) {
        List<WorkOrder> list = new ArrayList<WorkOrder>();
        int count = workOrderMapper.countWorkOrderByItems(params);
        if (count > 0) {
            params.put("limit",pageArgs.getPageStart());
            params.put("limitSize",pageArgs.getPageSize());
            list = workOrderMapper.queryWorkOrderByItems(params);
        }
        return new PageList<WorkOrder>(list, count);
    }

    /**通过ID查询**/
    public WorkOrder findById (Integer id) {
        WorkOrder workOrder = new WorkOrder();
        try {
            workOrder = workOrderMapper.findById(id);
        } catch (Exception e) {
            logger.error("服务器超时,更新失败",e);
        }
        return workOrder;
    }

    /**
     * 获取超时工单
     * @param date
     * @return
     */
    public List<WorkOrder> getOverTime(Date date) {
        return workOrderMapper.getOverTime(date);
    }
}
