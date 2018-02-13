package com.magic.daoyuan.business.service;

import com.magic.daoyuan.business.entity.ConfirmMoneyRecord;
import com.magic.daoyuan.business.entity.PageArgs;
import com.magic.daoyuan.business.entity.PageList;
import com.magic.daoyuan.business.mapper.IConfirmMoneyRecordMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Eric Xie on 2017/10/30 0030.
 */
@Service
public class ConfirmMoneyRecordService {


    @Resource
    private IConfirmMoneyRecordMapper confirmMoneyRecordMapper;


    public PageList<ConfirmMoneyRecord> getConfirmMoneyRecord(Map<String,Object> map, PageArgs pageArgs){

        List<ConfirmMoneyRecord> dataList = new ArrayList<ConfirmMoneyRecord>();
        int count = confirmMoneyRecordMapper.countConfirmMoneyRecordByItems(map);
        if(count > 0){
            map.put("limit",pageArgs.getPageStart());
            map.put("limitSize",pageArgs.getPageSize());
            dataList = confirmMoneyRecordMapper.queryConfirmMoneyRecordByItems(map);
        }
        return new PageList<ConfirmMoneyRecord>(dataList,count);
    }

    /**
     * 获取超时未处理的认款单数量
     * @param date
     * @return
     */
    public int getOverTime(Date date) {
        return confirmMoneyRecordMapper.getOverTime(date);
    }

}
