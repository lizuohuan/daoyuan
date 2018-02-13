package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.BusinessYcItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Eric Xie on 2017/10/25 0025.
 */
public interface IBusinessYcItemMapper {


    List<BusinessYcItem> queryBusinessYcItemByTotalBillId(List<Integer> totalBillIds);

    Integer batchAddBusinessYcItem(List<BusinessYcItem> businessYcItemList);


    Integer delBusinessYcItem(@Param("businessYcId") Integer businessYcId);


    Integer del(List<Integer>  list);


    List<BusinessYcItem> queryBusinessYcItem(@Param("businessYcId") Integer businessYcId);

}
