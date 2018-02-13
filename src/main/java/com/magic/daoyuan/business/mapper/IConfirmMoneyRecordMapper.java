package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.ConfirmMoneyRecord;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Eric Xie on 2017/10/27 0027.
 */
public interface IConfirmMoneyRecordMapper {



    Integer batchAddConfirmMoneyRecord(List<ConfirmMoneyRecord> list);


    Integer updateConfirmMoneyRecord(ConfirmMoneyRecord confirmMoneyRecord);


    Integer update(List<ConfirmMoneyRecord> list);

    List<ConfirmMoneyRecord> queryConfirmMoneyRecordByItems(Map<String,Object> map);


    int countConfirmMoneyRecordByItems(Map<String,Object> map);

    ConfirmMoneyRecord queryById(@Param("id") Integer id);

    /**
     * 获取超时未处理的认款单数量
     * @param date
     * @return
     */
    int getOverTime(@Param("date") Date date);

    List<ConfirmMoneyRecord> queryAll();

}
