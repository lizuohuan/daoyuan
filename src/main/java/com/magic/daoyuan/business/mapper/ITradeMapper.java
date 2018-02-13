package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.Trade;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Eric Xie on 2017/9/12 0012.
 */
public interface ITradeMapper {

    /**
     * add
     * @param trade
     * @return
     */
    Integer addTrade(Trade trade);

    /**
     * update
     * @param trade
     * @return
     */
    Integer update(Trade trade);


    List<Trade> queryTradeByItems(@Param("tradeName") String tradeName,@Param("limit") Integer limit,
                                  @Param("limitSize") Integer limitSize);

    Integer countTradeByItems(@Param("tradeName") String tradeName);

}
