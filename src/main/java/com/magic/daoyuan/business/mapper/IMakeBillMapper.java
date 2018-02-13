package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.MakeBill;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 开票Mapper
 * Created by Eric Xie on 2017/11/18 0018.
 */
public interface IMakeBillMapper {


    Integer delMakeBill(List<Integer> ids);


    List<MakeBill> queryMakeBill(@Param("companyId") Integer companyId, @Param("billMonth") Date billMonth);

    Integer addMakeBill(MakeBill makeBill);


    Integer batchAddMakeBill(List<MakeBill> makeBills);


    Integer updateMakeBill(MakeBill makeBill);

    Integer batchUpdateMakeBill(List<MakeBill> makeBills);


    List<MakeBill> queryMakeBillByItems(Map<String,Object> map);


    Integer countMakeBillByItems(Map<String,Object> map);


    MakeBill queryMakeBillById(@Param("id") Integer id);


    List<MakeBill> queryMakeBillForExport(Map<String,Object> map);

    /**
     * 获取未开票票据数量
     * @return
     */
    int getMakeBillUnDispose();
}
