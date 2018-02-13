package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.dto.CompanySonTotalBillDto;
import com.magic.daoyuan.business.dto.HrBillListDto;
import com.magic.daoyuan.business.dto.QTemp;
import com.magic.daoyuan.business.entity.CompanySonTotalBill;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 汇总账单
 * @author lzh
 * @create 2017/10/23 19:34
 */
public interface ICompanySonTotalBillMapper {



    List<CompanySonTotalBill> queryLastMonthNoConfirmBill(@Param("lastMonth") Date lastMonth);


    /**
     * 查询未核销的总账单集合
     * @return
     */
    List<CompanySonTotalBill> queryCompanySonTotalBillByNo(List<Integer> ids);

    /**
     * 通过ID集合查询所有的账单明细记录
     * @param ids
     * @return
     */
    List<CompanySonTotalBill> queryBillDetailByIds(@Param("ids") List<Integer> ids);

    /**
     * 查询所有已经提交的账单
     * @param mapList 公司id和账单月集合 如果为null 查询所有
     * @return
     */
    List<CompanySonTotalBill> queryCompanySonTotalBill(@Param("mapList") List<Map<String,Object>> mapList);

    /**
     * 查询所有已经提交的账单 发送账单接口
     * @param mapList 公司id和账单月集合 如果为null 查询所有
     * @return
     */
    List<CompanySonTotalBill> queryCompanySonTotalBill_(@Param("mapList") List<Map<String,Object>> mapList);

    /**
     * 添加批量
     * @param records
     * @return
     */
    int save(List<CompanySonTotalBill> records);

    /**
     * 详情
     * @param id
     * @return
     */
    CompanySonTotalBill info(@Param("id") Integer id);

    /**
     * 根据账单月和公司id获取
     * @return
     */
    List<CompanySonTotalBill> listByCompanyIdAndBillMonth(@Param("companyId") Integer companyId,
                                                          @Param("billMonth") Date billMonth);

    /**
     * 根据子账单获取最后一条汇总账单
     * @param companySonBillId
     * @return
     */
    CompanySonTotalBill getFinallyByCompanySonBillId(@Param("companySonBillId") Integer companySonBillId);

    /**
     * 根据子账单获取最后一条汇总账单
     * @param companySonBillIdSet
     * @return
     */
    List<CompanySonTotalBill> getFinallyByCompanySonBillId2(@Param("companySonBillIdSet") Set<Integer> companySonBillIdSet);


    /**
     * 后台页面 分页 通过各种条件 查询公司子账单汇总账单
     * @param map map
     * @return
     */
    List<CompanySonTotalBill> list(Map<String, Object> map);

    /**
     * 后台页面 统计 查询公司子账单汇总账单数量
     * @param map map
     * @return
     */
    int listCount(Map<String, Object> map);

    /**
     * 更新
     * @param companySonTotalBill
     */
    void update(CompanySonTotalBill companySonTotalBill);

    void batchUpdate(List<CompanySonTotalBill> companySonTotalBills);

    /**
     * 更新
     * @param companySonTotalBill
     */
    void update2(CompanySonTotalBill companySonTotalBill);


    void delete(@Param("ids") Integer[] ids);

    /**
     * 根据汇总id 获取所有子账单下平级汇总集合
     * @param companySonTotalBillId
     * @return
     */
    List<CompanySonTotalBill> getByCompanySonTotalBillId(
            @Param("companySonTotalBillId") Integer companySonTotalBillId,
            @Param("billMonth") Date billMonth);

    /**
     * 根据创建时间和公司id 获取所有汇总集合
     * @param companyId
     * @return
     */
    List<CompanySonTotalBill> getByCompanyId(
            @Param("companyId") Integer companyId,
            @Param("billMonth") Date billMonth);

    /**
     * 根据创建时间和公司id map集合 获取所有汇总集合
     * @param companyDateMapList
     * @return
     */
    List<CompanySonTotalBill> getByCompanyDateMapList(@Param("companyDateMapList") List<Map<String ,Object>> companyDateMapList);

    /**
     * 根据创建账单月 获取所有汇总集合
     * @param billMonth
     * @return
     */
    List<CompanySonTotalBill> getByBillMonth(@Param("billMonth") Date billMonth,@Param("companyId") Integer companyId);

    /**
     * 获取本账单月的业务月集合
     * @param billMonth
     * @param companyId
     * @return
     */
    List<Date> getDateByBillMonthAndCompanyId(@Param("billMonth") Date billMonth,@Param("companyId") Integer companyId);

    /**
     * 后台页面 分页 通过各种条件 查询公账单汇总列表
     * @param map map
     * @return
     */
    List<CompanySonTotalBillDto> listDto3(Map<String, Object> map);

    /**
     * 后台页面 分页 通过各种条件 查询公账单汇总列表
     * @param map map
     * @return
     */
    List<CompanySonTotalBillDto> listDto(Map<String, Object> map);

    /**
     * 统计 跟随 listDto
     * @param map map
     * @return
     */
    List<CompanySonTotalBillDto> countDto(Map<String, Object> map);

    /**
     * 后台页面 统计 查询公司子账单子类数量
     * @param map map
     * @return
     */
    int listDtoCount(Map<String, Object> map);

    /**
     * 后台页面 分页 通过各种条件 查询公账单汇总列表
     * @param map map
     * @return
     */
    List<HrBillListDto> listConsoleDto(Map<String, Object> map);

    /**
     * 后台页面 统计 查询公司子账单子类数量
     * @param map map
     * @return
     */
    int listConsoleDtoCount(Map<String, Object> map);


    /**
     * 详情 根据子账单子类获取
     * @param companySonBillItemId
     * @return
     */
    CompanySonTotalBill infoByCompanySonBillItemId(@Param("companySonBillItemId") Integer companySonBillItemId);


    /**
     * hr端页面 分页 通过各种条件 查询公账单汇总列表
     * @param map map
     * @return
     */
    List<HrBillListDto> listHrBillListDto(Map<String, Object> map);

    /**
     * hr端页面 统计 查询公司子账单子类数量
     * @param map map
     * @return
     */
    int listHrBillListDtoCount(Map<String, Object> map);


    /**
     * 获取超时未确认的账单
     * @param dateDay 时间 天 1-31
     * @return
     */
    List<CompanySonTotalBill> getUncertainBill(@Param("dateDay") Integer dateDay);

    /**
     * 批量更新发送总结
     * @param qTemps
     */
    void updateConsigneeList( List<QTemp> qTemps);

    /**
     * 获取该公司下 未核销的总账单
     * @param companyId
     * @return
     */
    List<CompanySonTotalBill> queryTotalBillByCompany(@Param("companyId") Integer companyId);
}
