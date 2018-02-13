package com.magic.daoyuan.business.service;

import com.magic.daoyuan.business.entity.PageArgs;
import com.magic.daoyuan.business.entity.PageList;
import com.magic.daoyuan.business.entity.Trade;
import com.magic.daoyuan.business.mapper.ITradeMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric Xie on 2017/9/12 0012.
 */
@Service
public class TradeService {

    @Resource
    private ITradeMapper tradeMapper;


    public PageList<Trade> queryTradeByItems(String tradeName, PageArgs pageArgs){
        List<Trade> dataList = new ArrayList<Trade>();
        int count = tradeMapper.countTradeByItems(tradeName);
        if(count > 0){
            dataList = tradeMapper.queryTradeByItems(tradeName,pageArgs.getPageStart() , pageArgs.getPageSize());
        }
        return new PageList<Trade>(dataList,count);
    }

    public void updateTrade(Trade trade){
        tradeMapper.update(trade);
    }


    public void addTrade(Trade trade){
        tradeMapper.addTrade(trade);
    }

    public List<Trade> queryAllTrade(String tradeName){
        return tradeMapper.queryTradeByItems(tradeName,null,null);
    }

}
