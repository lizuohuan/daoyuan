package com.magic.daoyuan.business.service;

import com.magic.daoyuan.business.entity.Backlog;
import com.magic.daoyuan.business.entity.OutlayAmountRecord;
import com.magic.daoyuan.business.entity.PageArgs;
import com.magic.daoyuan.business.entity.PageList;
import com.magic.daoyuan.business.mapper.IBacklogMapper;
import com.magic.daoyuan.business.mapper.IOutlayAmountRecordMapper;
import com.magic.daoyuan.business.util.CommonUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Eric Xie on 2017/11/17 0017.
 */
@Service
public class OutlayAmountRecordService {

    @Resource
    private IOutlayAmountRecordMapper outlayAmountRecordMapper;

    @Resource
    private IBacklogMapper backlogMapper;

    public OutlayAmountRecord queryById(Integer id){
        return outlayAmountRecordMapper.queryById(id);
    }


    public PageList<OutlayAmountRecord> queryOutlayAmountRecordByItems(Map<String,Object> map, PageArgs pageArgs){
        List<OutlayAmountRecord> dataList = new ArrayList<OutlayAmountRecord>();
        int count = outlayAmountRecordMapper.countOutlayAmountRecordByItems(map);
        if(count > 0){
            map.put("limit",pageArgs.getPageStart());
            map.put("limitSize",pageArgs.getPageSize());
            dataList = outlayAmountRecordMapper.queryOutlayAmountRecordByItems(map);
        }
        return new PageList<OutlayAmountRecord>(dataList,count);
    }

    @Transactional
    public void addOutlayAmountRecord(OutlayAmountRecord outlayAmountRecord){
        outlayAmountRecordMapper.addOutlayAmountRecord(outlayAmountRecord);
        Backlog backlog = new Backlog();
        backlog.setRoleId(12);
        backlog.setContent("有新的待审核的出款申请");
        backlog.setUrl("/page/outlayAmountRecord/list?roleId=12&status=2001");
        backlogMapper.save(backlog);
    }

    @Transactional
    public void updateOutlayAmountRecord(OutlayAmountRecord outlayAmountRecord){

        outlayAmountRecordMapper.updateOutlayAmountRecord(outlayAmountRecord);
        OutlayAmountRecord record = outlayAmountRecordMapper.queryById(outlayAmountRecord.getId());
        Backlog backlog = new Backlog();
        if (!CommonUtil.isEmpty(record.getStatus())) {
            if (record.getStatus().equals(2002)) {
                backlog.setRoleId(11);
                backlog.setContent("有新的待出款的出款申请 ");
                backlog.setUrl("/page/outlayAmountRecord/list?roleId=11&status=2002");
            } else if (record.getStatus().equals(2003)) {
                backlog.setRoleId(11);
                backlog.setContent("有新的出款申请已出款  ");
                backlog.setUrl("/page/outlayAmountRecord/list?userId="+record.getUserId()+"&status=2003");
            }
        }
        backlogMapper.save(backlog);

    }




}
