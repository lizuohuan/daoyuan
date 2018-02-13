package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.SocialSecurityInfoItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * 账单-社保明细 缴纳险种
 * @author lzh
 * @create 2017/10/17 18:32
 */
public interface ISocialSecurityInfoItemMapper {

    /**
     * 添加批量
     * @param records
     * @return
     */
    void save(List<SocialSecurityInfoItem> records);

    /**
     * 批量更新
     * @param records
     */
    void updateList(List<SocialSecurityInfoItem> records);

    /**
     * 删除
     * @param companySonTotalBillIds
     */
    void delList(@Param("companySonTotalBillIds") Integer[] companySonTotalBillIds);

    /**
     * 根据社保id集合获取社保险种明细集合
     * @param ssiIds
     * @return
     */
    List<SocialSecurityInfoItem> listBySSIId(@Param("ssiIds") Set<Integer> ssiIds);
}
