package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.ReservedFundsInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 账单--公积金明细
 * @author lzh
 * @create 2017/10/19 10:53
 */
public interface IReservedFundsInfoMapper {


    /**
     * 通过总账单ID 查询所有总账单ID 下的公积金缴纳明细集合 (不是子账单)
     * @param totalBillIds
     * @return
     */
    List<ReservedFundsInfo> queryReservedFundsInfoByTotalBillIds(List<Integer> totalBillIds);


    Integer del(@Param("companySonTotalBillIds") Integer[] companySonTotalBillIds);

    /**
     * 添加批量
     * @param records
     * @return
     */
    void save(List<ReservedFundsInfo> records);

    /**
     * 详情
     * @param id
     * @return
     */
    ReservedFundsInfo info(@Param("id") Integer id);

    /**
     * 后台页面 查询账单-社保缴纳明细
     * @param map map
     * @return
     */
    List<ReservedFundsInfo> list(Map<String ,Object> map);

    /**
     * 后台页面 统计 查询账单-社保缴纳明细数量
     * @param map map
     * @return
     */
    int listCount(Map<String , Object> map);

    /**
     * 后台页面 统计 稽核未处理的条数
     * @param companySonBillItemId
     * @return
     */
    int processingSchemeCount(@Param("companySonBillItemId") Integer companySonBillItemId);

    /**
     * 根据总账单ids和订单创建时间获取缴纳总费用
     * @param companySonBillId
     * @param createTime
     * @return
     */
    Double getPayPriceByCompanySonBillItemIdAndCreateTime(@Param("companySonBillId") Integer companySonBillId ,
                                                          @Param("createTime") Date createTime);
    /**
     * 根据公司id和账单月获取缴纳总费用
     * @param companyId
     * @param billMonth
     * @return
     */
    Double getPayPriceByCompanyIdAndBillMonth(@Param("companyId") Integer companyId ,
                                              @Param("billMonth") Date billMonth);

    /**
     * 根据公司id和账单月获取纳入次月的实际缴纳金额
     * @param companyId
     * @param billMonth
     * @return
     */
    Double getPracticalByCompanyIdAndBillMonth(@Param("companyId") Integer companyId ,
                                               @Param("companySonBillId") Integer companySonBillId ,
                                               @Param("billMonth") Date billMonth,
                                               @Param("createTime") Date createTime);

    /**
     * 更新拷盘数据
     * @param companySonTotalBillIds
     */
    void updateList2(@Param("companySonTotalBillIds") Integer[] companySonTotalBillIds);

    /**
     * 批量更新
     * @param list
     */
    void updateList(List<ReservedFundsInfo> list);

    /**
     * 稽核有差异的稽核列表
     * @param map
     * @return
     */
    List<ReservedFundsInfo> auditList(Map<String ,Object> map);

    /**
     * 稽核有差异的稽核总条数
     * @param map
     * @return
     */
    Integer auditListCount(Map<String ,Object> map);

    /**
     * 更新
     * @param fundsInfo
     */
    void update(ReservedFundsInfo fundsInfo);

    /**
     * 根据子账单子类id集合获取公积金集合
     * @param sonBillItemIds
     * @return
     */
    List<ReservedFundsInfo> listBySonBillItemId(@Param("sonBillItemIds") Set<Integer> sonBillItemIds);

    /**
     * 确认账单时 更新纳入次月的公积金 并插入确认的账单年月
     * @param affirmBillMonth
     */
    void updateAffirm(@Param("affirmBillMonth") Date affirmBillMonth,
                      @Param("companyId") Integer companyId,
                      @Param("companySonBillId") Integer companySonBillId);

    /**
     * 根据员工id和服务年月获取公积金
     * @param serviceNowYM
     * @param memberId
     * @return
     */
    ReservedFundsInfo getByMemberIdAndServiceNowYM(@Param("serviceNowYM") Date serviceNowYM,
                                                   @Param("memberId") Integer memberId);
    /**
     * 根据公司id和服务月获取公积金集合
     * @param companyId
     * @param serviceNowYM
     * @return
     */
    List<ReservedFundsInfo> getCompanyIdAndServiceNowYMAndServiceFeeConfigId(@Param("companyId") Integer companyId ,
                                                                                @Param("serviceNowYM") Date serviceNowYM,
                                                                             @Param("serviceFeeConfigId") Integer serviceFeeConfigId);
    /**
     * 根据公司id和账单月获取社保集合
     * @param companyId
     * @param billMonth
     * @return
     */
    List<ReservedFundsInfo> getByCompanyAndBillMonth(@Param("companyId") Integer companyId ,
                                                      @Param("billMonth") Date billMonth);

    /**
     * 获取这个员工上最后一条数据
     * @param memberId
     * @return
     */
    ReservedFundsInfo getLastByMemberId(@Param("memberId") Integer memberId);
}
