package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.CompanySonBill;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 公司子账单
 * @author lzh
 * @create 2017/10/13 9:39
 */
public interface ICompanySonBillMapper {


    /**
     *  通过子账单ID 查询子账单关联的票据信息 (包括 票据信息)
     * @param ids
     * @return
     */
    List<CompanySonBill> queryCompanySonBillByIds(@Param("ids") Set<Integer> ids);


    /**
     *  获取公司下的子账单集合
     * @param companyId
     * @return
     */
    List<CompanySonBill> queryCompanySonBillByCompany(@Param("companyId") Integer companyId);

    /**
     * 删除
     * @param id
     * @return
     */
    int delete(Integer id);

    /**
     * 添加
     * @param record
     * @return
     */
    int save(CompanySonBill record);

    /**
     * 详情
     * @param id
     * @return
     */
    CompanySonBill info(@Param("id") Integer id);

    /**
     * 更新不为空的字段
     * @param record
     * @return
     */
    int update(CompanySonBill record);


    /**
     * 后台页面 分页 通过各种条件 查询公司子账单
     * @param map map
     * @return
     */
    List<CompanySonBill> list(Map<String , Object> map);

    /**
     * 后台页面 统计 查询公司子账单数量
     * @param map map
     * @return
     */
    int listCount(Map<String , Object> map);



    /**
     * 通过公司ids 查询当前时间公司子账单
     * @param companyIds 公司ids
     * @param billMadeMethod 账单制作方式 0：预收  1：实做
     * @return
     */
    List<CompanySonBill> listForCompanyIds(@Param("companyIds") Integer[] companyIds,
                                           @Param("billMadeMethod") Integer billMadeMethod);

  /**
     * 通过公司ids 查询子账单下未绑定子账单子类id的
     * @param companyIds 公司ids
     * @return
     */
    List<CompanySonBill> listForCompanyIds2(@Param("companyIds") Set<Integer> companyIds);


}
