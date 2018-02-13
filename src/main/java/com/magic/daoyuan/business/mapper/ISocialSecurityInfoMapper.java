package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.SocialSecurityInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 账单-社保缴纳明细
 * @author lzh
 * @create 2017/10/17 18:06
 */
public interface ISocialSecurityInfoMapper {


    /**
     * 通过 总账单ID 查询总账单下所有的社保明细记录 (不是子账单ID)
     * @param totalBillIds
     * @return
     */
    List<SocialSecurityInfo> querySocialSecurityInfoByTotalBillId(List<Integer> totalBillIds);


    Integer del(@Param("companySonTotalBillIds") Integer[] companySonTotalBillIds);

    /**
     * 添加批量
     * @param records
     * @return
     */
    void save(List<SocialSecurityInfo> records);

    /**
     * 详情
     * @param id
     * @return
     */
    SocialSecurityInfo info(@Param("id") Integer id);

    /**
     * 后台页面 查询账单-社保缴纳明细
     * @param map map
     * @return
     */
    List<SocialSecurityInfo> list(Map<String ,Object> map);

    /**
     * 后台页面 统计 查询账单-社保缴纳明细数量
     * @param map map
     * @return
     */
    int listCount(Map<String , Object> map);

    /**
     * 后台页面 统计 稽核未处理的条数
     * @param companySonBillItemId map
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
     * 批量更新
     * @param list
     */
    void updateList(List<SocialSecurityInfo> list);

    /**
     * 更新拷盘数据
     * @param companySonTotalBillIds
     */
    void updateList2(@Param("companySonTotalBillIds") Integer[] companySonTotalBillIds);

    /**
     * 稽核有差异的稽核列表
     * @param map
     * @return
     */
    List<SocialSecurityInfo> auditList(Map<String ,Object> map);

    /**
     * 稽核有差异的稽核总条数
     * @param map
     * @return
     */
    Integer auditListCount(Map<String ,Object> map);

    /**
     * 更新
     * @param securityInfo
     */
    void update(SocialSecurityInfo securityInfo);

    /**
     * 根据子账单子类id集合获取社保集合
     * @param sonBillItemIds
     * @return
     */
    List<SocialSecurityInfo> listBySonBillItemId(@Param("sonBillItemIds") Set<Integer> sonBillItemIds);

    /**
     * 确认账单时 更新纳入次月的社保 并插入确认的账单年月
     * @param affirmBillMonth
     */
    void updateAffirm(@Param("affirmBillMonth") Date affirmBillMonth,
                      @Param("companyId") Integer companyId,
                      @Param("companySonBillId") Integer companySonBillId);

    /**
     * 根据员工id和服务年月获取社保
     * @param serviceNowYM
     * @param memberId
     * @return
     */
    SocialSecurityInfo getByMemberIdAndServiceNowYM(@Param("serviceNowYM") Date serviceNowYM,
                                                   @Param("memberId") Integer memberId);

    /**
     * 根据公司id和服务月和服务配置id获取社保集合
     * @param companyId
     * @param serviceNowYM
     * @return
     */
    List<SocialSecurityInfo> getCompanyIdAndServiceNowYMAndServiceFeeConfigId(@Param("companyId") Integer companyId ,
                                                        @Param("serviceNowYM") Date serviceNowYM,
                                                        @Param("serviceFeeConfigId") Integer serviceFeeConfigId);


    /**
     * 根据公司id和账单月获取社保集合
     * @param companyId
     * @param billMonth
     * @return
     */
    List<SocialSecurityInfo> getByCompanyAndBillMonth(@Param("companyId") Integer companyId ,
                                                      @Param("billMonth") Date billMonth);

    /**
     * 获取这个员工上月账单的最后一条数据
     * @param lastBillMonth
     * @param memberId
     * @return
     */
    SocialSecurityInfo getLastByMemberIdAndLastBillMonth(@Param("billMonth") Date lastBillMonth,
                                                         @Param("memberId") Integer memberId);
    /**
     * 获取这个员工上最后一条数据
     * @param memberId
     * @return
     */
    SocialSecurityInfo getLastByMemberId(@Param("memberId") Integer memberId);

}
