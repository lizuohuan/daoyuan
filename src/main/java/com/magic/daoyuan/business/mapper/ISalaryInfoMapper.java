package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.SalaryInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 工资信息 Mapper
 * Created by Eric Xie on 2017/10/19 0019.
 */
public interface ISalaryInfoMapper {

    /**
     * 统计 账单确认后新上传工资可以提示账单已确认，不能继续上传该公司的工资账单
     * @param companyId
     * @param targetMonth
     * @return
     */
    int countSalaryInfoByTotalBill(@Param("companyId") Integer companyId,@Param("targetMonth") Date targetMonth);

    /**
     *  查询当前公司下，当前月份 已经导入过的工资信息
     * @param companyId
     * @param salaryMonth
     * @return
     */
    List<SalaryInfo> querySalaryInfoOfTotalBillByCompany(@Param("companyId") Integer companyId,@Param("salaryMonth") Date salaryMonth);

    /**
     * 获取员工的年终奖数据
     * @param members
     * @return
     */
    List<SalaryInfo> querySalaryInfoByMembers(List<Integer> members);


    /**
     * 获取员工当月的工资信息
     * @param members
     * @return
     */
    List<SalaryInfo> querySalaryInfoByMembersAndMonth(@Param("members") List<Integer> members,@Param("salaryMonth") Date salaryMonth);

    /**
     *  通过 总账单ID 查询总账单下所有的工资发放明细 (不是子账单)
     * @param totalBillIds
     * @return
     */
    List<SalaryInfo> querySalaryInfoByTotalBillId(List<Integer> totalBillIds);

    /**
     * 批量新增
     * @param salaryInfoList
     * @return
     */
    Integer batchAddSalaryInfo(List<SalaryInfo> salaryInfoList);


    List<SalaryInfo> querySalaryInfoByCompanySonTotalBillId(@Param("companySonTotalBillId") Integer companySonTotalBillId);

    /**
     * 通过公司ID 查询工资信息，包含子账单ID
     * @param companyId
     * @param month
     * @return
     */
    List<SalaryInfo> querySalaryInfoByCompany(@Param("companyId") Integer companyId,@Param("month") Date month);

    /**
     * 统计查询 该公司下 该月份是否导入过 工资表
     *  如果等于 0  则没有
     * @param companyId
     * @param month
     * @return
     */
    int countSalaryInfoByCompany(@Param("companyId") Integer companyId, @Param("month") Date month);

    /**
     * 批量更新
     * @param list
     */
    void updateList(List<SalaryInfo> list);
    /**
     * 批量更新 根据子账单子类id
     * @param companySonTotalBillIds
     */
    void updateListByCompanySonTotalBillIds(@Param("companySonTotalBillIds") Integer[] companySonTotalBillIds);

//    Integer delSalaryInfo

    Integer delSalaryInfoByCompanySonTotalBillId(@Param("companySonTotalBillIds") Integer[] companySonTotalBillIds);



    List<SalaryInfo> querySalaryInfo(Map<String,Object> map);


    Integer countSalaryInfo(Map<String,Object> map);

    /**
     * 根据总账单ids和订单创建时间获取缴纳总费用
     * @param companySonTotalBillId
     * @param createTime
     * @return
     */
    Double getPayPriceByCompanySonBillItemIdAndCreateTime(@Param("companySonTotalBillId") Integer companySonTotalBillId ,
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
     * 根据员工id和服务年月获取工资
     * @param month
     * @param memberId
     * @return
     */
    SalaryInfo getByMemberIdAndMonth(@Param("month") Date month,
                                                    @Param("memberId") Integer memberId);
    /**
     * 根据公司id和服务月获取工资集合
     * @param companyId
     * @param serviceNowYM
     * @return
     */
    List<SalaryInfo> getCompanyIdAndServiceNowYMAndServiceFeeConfigId(@Param("companyId") Integer companyId ,
                                                                             @Param("serviceNowYM") Date serviceNowYM,
                                                                             @Param("serviceFeeConfigId") Integer serviceFeeConfigId);
}
