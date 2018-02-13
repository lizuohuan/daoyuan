package com.magic.daoyuan.business.service;

import com.magic.daoyuan.business.entity.*;
import com.magic.daoyuan.business.exception.InterfaceCommonException;
import com.magic.daoyuan.business.mapper.*;
import com.magic.daoyuan.business.util.CommonUtil;
import com.magic.daoyuan.business.util.StatusConstant;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * 账单--公积金明细
 * @author lzh
 * @create 2017/10/19 16:40
 */
@Service
public class ReservedFundsInfoService {

    @Resource
    private IReservedFundsInfoMapper reservedFundsInfoMapper;
    @Resource
    private ICompanySonTotalBillMapper companySonTotalBillMapper;
    @Resource
    private ICompanySonBillItemMapper companySonBillItemMapper;
    @Resource
    private IOutlayAmountRecordMapper outlayAmountRecordMapper;
    @Resource
    private IBankInfoMapper bankInfoMapper;
    @Resource
    private IBacklogMapper backlogMapper;
    @Resource
    private IMonthServiceFeeBalanceMapper monthServiceFeeBalanceMapper;
    @Resource
    private IMonthServiceFeeMapper monthServiceFeeMapper;
    @Resource
    private ISocialSecurityInfoMapper socialSecurityInfoMapper;
    @Resource
    private ISalaryInfoMapper salaryInfoMapper;
    @Resource
    private IMonthServiceFeeDetailMapper monthServiceFeeDetailMapper;
    /**
     * 后台页面 分页获取公积金明细
     *
     * @param pageArgs    分页属性
     * @param userName     姓名
     * @param certificateType     证件类型
     * @param idCard     证件编号
     * @param memberNum   公积金编码
     * @param payPlaceOrganizationName     缴金地-经办机构 名
     * @param companySonBillItemId     子账单子类id
     * @return
     */
    public PageList<ReservedFundsInfo> list(PageArgs pageArgs , String userName, Integer certificateType, String idCard,
                                            String memberNum, String payPlaceOrganizationName,
                                            Integer companySonBillItemId,
                                            Integer companySonTotalBillId,
                                            Date billMonth, Integer companyId) {
        PageList<ReservedFundsInfo> pageList = new PageList<ReservedFundsInfo>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userName", userName);
        map.put("payPlaceOrganizationName", payPlaceOrganizationName);
        map.put("memberNum", memberNum);
        map.put("certificateType", certificateType);
        map.put("idCard", idCard);
        map.put("companySonBillItemId", companySonBillItemId);
        map.put("companySonTotalBillId", companySonTotalBillId);
        map.put("billMonth", billMonth);
        map.put("companyId", companyId);
        int count = reservedFundsInfoMapper.listCount(map);
        if (count > 0) {
            map.put("pageArgs", pageArgs);
            pageList.setList(reservedFundsInfoMapper.list(map));
        }
        pageList.setTotalSize(count);
        return pageList;
    }

    /**
     * H5页面 分页获取公积金明细
     *
     * @param companyId     汇总id
     * @return
     */
    public List<ReservedFundsInfo> list3( Integer companyId,Date billMonth) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userName", null);
        map.put("payPlaceOrganizationName", null);
        map.put("memberNum", null);
        map.put("certificateType", null);
        map.put("idCard", null);
        map.put("companySonBillItemId", null);
        map.put("companySonTotalBillId", null);
        map.put("companyId", companyId);
        map.put("billMonth", null);
        map.put("billMonth", billMonth);
        return reservedFundsInfoMapper.list(map);
    }



    /**
     * H5页面 分页获取公积金明细
     *
     * @param companySonTotalBillId     汇总id
     * @return
     */
    public List<ReservedFundsInfo> list( Integer companySonTotalBillId,Date billMonth) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userName", null);
        map.put("payPlaceOrganizationName", null);
        map.put("memberNum", null);
        map.put("certificateType", null);
        map.put("idCard", null);
        map.put("companySonBillItemId", null);
        map.put("companySonTotalBillId", companySonTotalBillId);
        map.put("billMonth", null);
        map.put("companyId", null);
        map.put("billMonth", billMonth);
        return reservedFundsInfoMapper.list(map);
    }



    /**
     * 后台页面 分页获取公积金稽核有差异的稽核列表
     *
     * @param pageArgs    分页属性
     * @param serviceNowYM     服务年月
     * @param companyId     公司id
     * @return
     */
    public PageList<ReservedFundsInfo> auditList(PageArgs pageArgs , Date serviceNowYM, Integer companyId) {
        PageList<ReservedFundsInfo> pageList = new PageList<ReservedFundsInfo>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("serviceNowYM", serviceNowYM);
        map.put("companyId", companyId);
        int count = reservedFundsInfoMapper.auditListCount(map);
        if (count > 0) {
            map.put("pageArgs", pageArgs);
            pageList.setList(reservedFundsInfoMapper.auditList(map));
        }
        pageList.setTotalSize(count);
        return pageList;
    }

    /**
     * 更新
     * @param fundsInfo
     */
    public void update(ReservedFundsInfo fundsInfo) {
        reservedFundsInfoMapper.update(fundsInfo);
    }

    /**
     * 更新处理方案状态
     * @param fundsInfo
     */
    @Transactional
    public void updateProcessingScheme(ReservedFundsInfo fundsInfo) throws IOException {
        ReservedFundsInfo rfi = reservedFundsInfoMapper.info(fundsInfo.getId());
        if (null == rfi) {
            throw new InterfaceCommonException(StatusConstant.NO_DATA,"未知的公积金信息");
        }
        reservedFundsInfoMapper.update(fundsInfo);
        int count = reservedFundsInfoMapper.processingSchemeCount(rfi.getCompanySonBillItemId());
        CompanySonBillItem companySonBillItem = companySonBillItemMapper.info(rfi.getCompanySonBillItemId());
        if (count == 0) {
            List<SocialSecurityInfo> socialSecurityInfoList = socialSecurityInfoMapper.getByCompanyAndBillMonth(companySonBillItem.getCompanyId(),rfi.getBillMonth());
            companySonBillItem.setIsReservedFundAudit(1);
            if (socialSecurityInfoList.size() == 0) {
                companySonBillItem.setIsSocialSecurityAudit(1);
            }
        }
        companySonBillItem.setAuditTheDifference(companySonBillItem.getAuditTheDifference() -
                (rfi.getCompanyTotalPay() - rfi.getPracticalCompanyTotalPay()) -
                (rfi.getMemberTotalPay() - rfi.getPracticalMemberTotalPay()));
        companySonBillItemMapper.update(companySonBillItem);

        // TODO: 2017/12/8 退回客户
        if (fundsInfo.getProcessingScheme() == 2) {
            OutlayAmountRecord record = outlayAmountRecordMapper.getByBillMonthAndCompanyId(companySonBillItem.getCompanyId(),rfi.getBillMonth());
            if (null == record) {
                record = new OutlayAmountRecord();
                BankInfo bankInfo = bankInfoMapper.getByCompanyId(companySonBillItem.getCompanyId());
                if (null == bankInfo) {
                    throw new InterfaceCommonException(StatusConstant.NO_DATA,"请先设置公司收款账号信息");
                }
                record.setBillMonth(rfi.getBillMonth());
                record.setType2(1);
                record.setIsValid(1);
                record.setAccountName(bankInfo.getAccountName());
                record.setBankAccount(bankInfo.getBankAccount());
                record.setBankInfoId(bankInfo.getId());
                record.setCompanyId(companySonBillItem.getCompanyId());
                record.setBankName(bankInfo.getBankName());
                record.setIsUrgent(0);
                record.setStatus(2001);
                record.setAmount((rfi.getCompanyTotalPay() - rfi.getPracticalCompanyTotalPay()) +
                        (rfi.getMemberTotalPay() - rfi.getPracticalMemberTotalPay()));
                outlayAmountRecordMapper.addOutlayAmountRecord(record);
                Backlog backlog = new Backlog();
                backlog.setRoleId(12);
                backlog.setContent("有新的待审核的出款申请");
                backlog.setUrl("/page/outlayAmountRecord/list?roleId=12&status=2001");
                backlogMapper.save(backlog);
            } else {
                record.setAmount(record.getAmount() + (rfi.getCompanyTotalPay() - rfi.getPracticalCompanyTotalPay()) -
                        (rfi.getMemberTotalPay() - rfi.getPracticalMemberTotalPay()));
                outlayAmountRecordMapper.updateOutlayAmountRecord(record);
                Backlog backlog = new Backlog();
                if (!CommonUtil.isEmpty(record.getStatus())) {
                    if (record.getStatus().equals(2002)) {
                        backlog.setRoleId(11);
                        backlog.setContent("有新的待出款的出款申请 ");
                        backlog.setUrl("/page/outlayAmountRecord/list?roleId=11&status=2002");
                    } else if (record.getStatus().equals(2003)) {
                        backlog.setRoleId(11);
                        backlog.setContent("有新的出款申请已出款 ");
                        backlog.setUrl("/page/outlayAmountRecord/list?userId="+record.getUserId()+"&status=2003");
                    }
                }
                backlogMapper.save(backlog);
            }
        }


        //处理服务费 记录补差
        //没有上传拷盘
        if (rfi.getIsUploadKaoPan() == 0 ||
                (rfi.getPracticalCompanyTotalPay() == 0 &&
                        rfi.getPracticalMemberTotalPay() == 0)) {
            //获取服务费
            MonthServiceFee monthServiceFee = monthServiceFeeMapper.
                    getByDateAndCompanyIdOnlyOne(rfi.getBillMonth(),companySonBillItem.getCompanyId());
            //公司 合作方式
            List<CompanyCooperationMethod> methods = com.alibaba.fastjson.JSONArray.parseArray(
                    monthServiceFee.getCompanyCooperationMethodJson(),
                    CompanyCooperationMethod.class);

            //服务费补差
            MonthServiceFeeBalance monthServiceFeeBalance = monthServiceFeeBalanceMapper.getByCompanyIdAndBillMonth(companySonBillItem.getCompanyId(),rfi.getBillMonth());
            //合作方式 0：普通 1：派遣  2：外包
            out:for (CompanyCooperationMethod method : methods) {
                if (method.getCooperationMethodId().equals(monthServiceFee.getWaysOfCooperation())) {
                    switch (method.getServiceFeeConfigId()) {
                        case 1 :
                            //按固定额
                        {
                            //获取工资
                            SalaryInfo salaryInfo = salaryInfoMapper.getByMemberIdAndMonth(rfi.getServiceNowYM(),rfi.getMemberId());
                            if (null == salaryInfo) {
                                //获取社保
                                SocialSecurityInfo socialSecurityInfo = socialSecurityInfoMapper.getByMemberIdAndServiceNowYM(rfi.getServiceNowYM(),rfi.getMemberId());
                                if (null == socialSecurityInfo ||
                                        (null != socialSecurityInfo.getProcessingScheme()
                                                && socialSecurityInfo.getPracticalMemberTotalPay() == 0
                                                && socialSecurityInfo.getPracticalCompanyTotalPay() == 0) ||
                                        (null == socialSecurityInfo.getPracticalMemberTotalPay()
                                                || null != socialSecurityInfo.getPracticalCompanyTotalPay()
//                                                || socialSecurityInfo.getPracticalMemberTotalPay() != 0
//                                                || socialSecurityInfo.getPracticalCompanyTotalPay() != 0
                                        ) ) {
                                    if (null == monthServiceFeeBalance) {
                                        monthServiceFeeBalance = new MonthServiceFeeBalance();
                                        monthServiceFeeBalance.setBillMonth(rfi.getBillMonth());
                                        monthServiceFeeBalance.setCompanyId(companySonBillItem.getCompanyId());
                                        monthServiceFeeBalance.setServiceFeeBalance(method.getServiceFeeList().get(0).getPrice());
                                        monthServiceFeeBalanceMapper.save(monthServiceFeeBalance);
                                        break out;
                                    } else {
                                        monthServiceFeeBalance.setServiceFeeBalance(new BigDecimal(monthServiceFeeBalance.getServiceFeeBalance().toString()).
                                                add(new BigDecimal(method.getServiceFeeList().get(0).getPrice().toString())).doubleValue());
                                        monthServiceFeeBalanceMapper.update(monthServiceFeeBalance);
                                        break out;
                                    }
                                }
                            }
                        }

                        break;
                        case 2 :
                            //按人数阶梯
                        {
                            //获取工资
                            SalaryInfo salaryInfo = salaryInfoMapper.getByMemberIdAndMonth(rfi.getServiceNowYM(),rfi.getMemberId());
                            if (null == salaryInfo) {
                                //获取社保
                                SocialSecurityInfo socialSecurityInfo = socialSecurityInfoMapper.getByMemberIdAndServiceNowYM(rfi.getServiceNowYM(),rfi.getMemberId());
                                if (null == socialSecurityInfo ||
                                        (null != socialSecurityInfo.getProcessingScheme()
                                                && socialSecurityInfo.getPracticalMemberTotalPay() == 0
                                                && socialSecurityInfo.getPracticalCompanyTotalPay() == 0) ||
                                        (null == socialSecurityInfo.getPracticalMemberTotalPay()
                                                || null != socialSecurityInfo.getPracticalCompanyTotalPay()
//                                                || socialSecurityInfo.getPracticalMemberTotalPay() != 0
//                                                || socialSecurityInfo.getPracticalCompanyTotalPay() != 0
                                        ) ) {
                                    //0：普通 1：派遣  2：外包
                                    Set<Integer> memberSet0 = new HashSet<Integer>();
                                    Set<Integer> memberSet1 = new HashSet<Integer>();
                                    Set<Integer> memberSet2 = new HashSet<Integer>();
                                    //根据公司id和服务月获取社保集合
                                    List<SocialSecurityInfo> socialSecurityInfoList =
                                            socialSecurityInfoMapper.getCompanyIdAndServiceNowYMAndServiceFeeConfigId(companySonBillItem.getCompanyId(),
                                                    rfi.getServiceNowYM(),method.getServiceFeeConfigId());
                                    //根据公司id和服务月获取公积金集合
                                    List<ReservedFundsInfo> reservedFundsInfoList =
                                            reservedFundsInfoMapper.getCompanyIdAndServiceNowYMAndServiceFeeConfigId(companySonBillItem.getCompanyId(),rfi.getServiceNowYM(),method.getServiceFeeConfigId());
                                    //根据公司id和服务月获取公积金集合
                                    List<SalaryInfo> salaryInfoList =
                                            salaryInfoMapper.getCompanyIdAndServiceNowYMAndServiceFeeConfigId(companySonBillItem.getCompanyId(),rfi.getServiceNowYM(),method.getServiceFeeConfigId());
                                    for (SocialSecurityInfo info : socialSecurityInfoList) {
                                        if (info.getWaysOfCooperation() == 0 && null == info.getProcessingScheme()) {
                                            memberSet0.add(info.getMemberId());
                                        }
                                        if (info.getWaysOfCooperation() == 1) {
                                            memberSet1.add(info.getMemberId());
                                        }
                                        if (info.getWaysOfCooperation() == 2) {
                                            memberSet2.add(info.getMemberId());
                                        }
                                    }
                                    for (ReservedFundsInfo info : reservedFundsInfoList) {
                                        if (info.getWaysOfCooperation() == 0 && null == info.getProcessingScheme()) {
                                            memberSet0.add(info.getMemberId());
                                        }
                                        if (info.getWaysOfCooperation() == 1) {
                                            memberSet1.add(info.getMemberId());
                                        }
                                        if (info.getWaysOfCooperation() == 2) {
                                            memberSet2.add(info.getMemberId());
                                        }
                                    }
                                    for (SalaryInfo info : salaryInfoList) {
                                        if (info.getWaysOfCooperation() == 0) {
                                            memberSet0.add(info.getMemberId());
                                        }
                                        if (info.getWaysOfCooperation() == 1) {
                                            memberSet1.add(info.getMemberId());
                                        }
                                        if (info.getWaysOfCooperation() == 2) {
                                            memberSet2.add(info.getMemberId());
                                        }
                                    }

                                    //获取此服务配置总服务费
                                    Double serviceFeeTotal = monthServiceFeeMapper.getByDateAndCompanyIdOnlyOneAndServiceFeeConfigId(
                                            rfi.getBillMonth(),companySonBillItem.getCompanyId(),method.getServiceFeeConfigId());
                                    //0：普通 1：派遣  2：外包
                                    Double serviceFee0 = 0.0;
                                    Double serviceFee1 = 0.0;
                                    Double serviceFee2 = 0.0;
                                    for (CompanyCooperationMethod cooperationMethod : methods) {
                                        Collections.sort(cooperationMethod.getServiceFeeList(), new Comparator<CompanyCooperationServiceFee>() {
                                            public int compare(CompanyCooperationServiceFee o1, CompanyCooperationServiceFee o2) {
                                                return o1.getExtent() - o2.getExtent();
                                            }
                                        });
                                        if (cooperationMethod.getCooperationMethodId() == 0) {
                                            serviceFee0 = MemberService.getServiceFeePersonNumJT2(cooperationMethod,memberSet0.size());
                                            continue ;
                                        }
                                        if (cooperationMethod.getCooperationMethodId() == 1) {
                                            serviceFee1 = MemberService.getServiceFeePersonNumJT2(cooperationMethod,memberSet1.size());
                                            continue ;
                                        }
                                        if (cooperationMethod.getCooperationMethodId() == 2) {
                                            serviceFee2 = MemberService.getServiceFeePersonNumJT2(cooperationMethod,memberSet2.size());
                                            continue ;
                                        }
                                    }
                                    BigDecimal serviceFeeTotal2 = new BigDecimal(serviceFee0.toString()).
                                            add(new BigDecimal(serviceFee1.toString())).
                                            add(new BigDecimal(serviceFee2.toString()));
                                    if (null == monthServiceFeeBalance) {
                                        monthServiceFeeBalance = new MonthServiceFeeBalance();
                                        monthServiceFeeBalance.setBillMonth(rfi.getBillMonth());
                                        monthServiceFeeBalance.setCompanyId(companySonBillItem.getCompanyId());
                                        monthServiceFeeBalance.setServiceFeeBalance(new BigDecimal(serviceFeeTotal.toString()).subtract(serviceFeeTotal2).doubleValue());
                                        monthServiceFeeBalanceMapper.save(monthServiceFeeBalance);
                                        break out;
                                    } else {
                                        monthServiceFeeBalance.setServiceFeeBalance(new BigDecimal(serviceFeeTotal.toString()).subtract(serviceFeeTotal2).doubleValue());
                                        monthServiceFeeBalanceMapper.update(monthServiceFeeBalance);
                                        break out;
                                    }

                                }
                            }
                        }

                        break;
                        case 3 :
                            //服务类别
                        {
                            String businessStr = null;
                            //获取社保
                            SocialSecurityInfo socialSecurityInfo = socialSecurityInfoMapper.getByMemberIdAndServiceNowYM(rfi.getServiceNowYM(),rfi.getMemberId());
                            if (null != socialSecurityInfo && (socialSecurityInfo.getPracticalMemberTotalPay() != 0
                                    || socialSecurityInfo.getPracticalCompanyTotalPay() != 0)) {
                                businessStr = "4";
                            }
                            //获取工资
                            SalaryInfo salaryInfo = salaryInfoMapper.getByMemberIdAndMonth(rfi.getServiceNowYM(),rfi.getMemberId());
                            if (null != salaryInfo) {
                                if (null == businessStr) {
                                    businessStr = "5";
                                } else {
                                    businessStr = businessStr + ",5";
                                }
                            }

                            //本月员工缴纳明细
                            List<MonthServiceFeeDetail> monthServiceFeeDetail = monthServiceFeeDetailMapper.getByMemberIdAndMonth(rfi.getMemberId(),rfi.getServiceNowYM());
                            BigDecimal serviceFeeTotal = new BigDecimal("0.0");
                            for (MonthServiceFeeDetail detail : monthServiceFeeDetail) {
                                serviceFeeTotal = serviceFeeTotal.add(new BigDecimal(detail.getAmount().toString()));
                            }
                            Double serviceFee = 0.0;
                            for (CompanyCooperationBusinessServiceFee fee : method.getBusinessServiceFeeList()) {
                                if (fee.getBusinessIds().equals(businessStr)) {
                                    serviceFee = fee.getPrice();
                                }
                            }
                            if (null == socialSecurityInfo ||
                                    (null != socialSecurityInfo.getProcessingScheme()
                                            && socialSecurityInfo.getPracticalMemberTotalPay() == 0
                                            && socialSecurityInfo.getPracticalCompanyTotalPay() == 0) ||
                                    (null == socialSecurityInfo.getPracticalMemberTotalPay()
                                            || null != socialSecurityInfo.getPracticalCompanyTotalPay()
//                                                || socialSecurityInfo.getPracticalMemberTotalPay() != 0
//                                                || socialSecurityInfo.getPracticalCompanyTotalPay() != 0
                                    ) ) {
                                if (null == monthServiceFeeBalance) {
                                    monthServiceFeeBalance = new MonthServiceFeeBalance();
                                    monthServiceFeeBalance.setBillMonth(rfi.getBillMonth());
                                    monthServiceFeeBalance.setCompanyId(companySonBillItem.getCompanyId());
                                    monthServiceFeeBalance.setServiceFeeBalance(serviceFeeTotal.subtract(new BigDecimal(serviceFee.toString())).doubleValue());
                                    monthServiceFeeBalanceMapper.save(monthServiceFeeBalance);
                                    break out;
                                } else {
                                    monthServiceFeeBalance.setServiceFeeBalance(new BigDecimal(monthServiceFeeBalance.getServiceFeeBalance().toString()).
                                            add(serviceFeeTotal.subtract(new BigDecimal(serviceFee.toString()))).doubleValue());
                                    monthServiceFeeBalanceMapper.update(monthServiceFeeBalance);
                                    break out;
                                }
                            }
                        }
                        break;
                        case 4 :
                            //服务区
                        {
                            Map<Object,List<Object>> mapList = new HashMap<Object, List<Object>>();
                            //本月员工缴纳明细
                            List<MonthServiceFeeDetail> monthServiceFeeDetail = monthServiceFeeDetailMapper.getByMemberIdAndMonth(rfi.getMemberId(),rfi.getServiceNowYM());
                            BigDecimal serviceFeeTotal = new BigDecimal("0.0");
                            for (MonthServiceFeeDetail detail : monthServiceFeeDetail) {
                                mapList.putAll(detail.getBusinessCityJsonList());
                                serviceFeeTotal = serviceFeeTotal.add(new BigDecimal(detail.getAmount().toString()));
                            }
                            List<Object> list1 = mapList.get(3);
                            if (mapList.size() > 1) {
                                //获取社保
                                SocialSecurityInfo socialSecurityInfo = socialSecurityInfoMapper.getByMemberIdAndServiceNowYM(rfi.getServiceNowYM(),rfi.getMemberId());
                                if (null == socialSecurityInfo) {
                                    if (null == monthServiceFeeBalance) {
                                        monthServiceFeeBalance = new MonthServiceFeeBalance();
                                        monthServiceFeeBalance.setBillMonth(rfi.getBillMonth());
                                        monthServiceFeeBalance.setCompanyId(companySonBillItem.getCompanyId());
                                        monthServiceFeeBalance.setServiceFeeBalance(serviceFeeTotal.subtract(new BigDecimal(list1.get(1).toString())).doubleValue());
                                        monthServiceFeeBalanceMapper.save(monthServiceFeeBalance);
                                        break out;
                                    } else {
                                        monthServiceFeeBalance.setServiceFeeBalance(new BigDecimal(monthServiceFeeBalance.getServiceFeeBalance().toString()).
                                                add(serviceFeeTotal.subtract(new BigDecimal(list1.get(1).toString()))).doubleValue());
                                        monthServiceFeeBalanceMapper.update(monthServiceFeeBalance);
                                        break out;
                                    }
                                }
                                List<Object> list2 = mapList.get(4);
                                if (list1.get(0).equals(list2.get(0))) {
                                    if (null != socialSecurityInfo.getProcessingScheme()
                                            && socialSecurityInfo.getPracticalMemberTotalPay() == 0
                                            && socialSecurityInfo.getPracticalCompanyTotalPay() == 0) {
                                        if (null == monthServiceFeeBalance) {
                                            monthServiceFeeBalance = new MonthServiceFeeBalance();
                                            monthServiceFeeBalance.setBillMonth(rfi.getBillMonth());
                                            monthServiceFeeBalance.setCompanyId(companySonBillItem.getCompanyId());
                                            monthServiceFeeBalance.setServiceFeeBalance(serviceFeeTotal.subtract(new BigDecimal(list1.get(1).toString())).doubleValue());
                                            monthServiceFeeBalanceMapper.save(monthServiceFeeBalance);
                                            break out;
                                        } else {
                                            monthServiceFeeBalance.setServiceFeeBalance(new BigDecimal(monthServiceFeeBalance.getServiceFeeBalance().toString()).
                                                    add(serviceFeeTotal.subtract(new BigDecimal(list1.get(1).toString()))).doubleValue());
                                            monthServiceFeeBalanceMapper.update(monthServiceFeeBalance);
                                            break out;
                                        }
                                    }
                                } else {
                                    if (null != socialSecurityInfo.getProcessingScheme()
                                            && socialSecurityInfo.getPracticalMemberTotalPay() == 0
                                            && socialSecurityInfo.getPracticalCompanyTotalPay() == 0) {
                                        if (null == monthServiceFeeBalance) {
                                            monthServiceFeeBalance = new MonthServiceFeeBalance();
                                            monthServiceFeeBalance.setBillMonth(rfi.getBillMonth());
                                            monthServiceFeeBalance.setCompanyId(companySonBillItem.getCompanyId());
                                            monthServiceFeeBalance.setServiceFeeBalance(serviceFeeTotal.subtract(new BigDecimal(list1.get(1).toString()).
                                                    add(new BigDecimal(list2.get(1).toString()))).doubleValue());
                                            monthServiceFeeBalanceMapper.save(monthServiceFeeBalance);
                                            break out;
                                        } else {
                                            monthServiceFeeBalance.setServiceFeeBalance(new BigDecimal(monthServiceFeeBalance.getServiceFeeBalance().toString()).
                                                    add(serviceFeeTotal.subtract(new BigDecimal(list1.get(1).toString()).
                                                            add(new BigDecimal(list2.get(1).toString())))).doubleValue());
                                            monthServiceFeeBalanceMapper.update(monthServiceFeeBalance);
                                            break out;
                                        }
                                    }
                                    if ((null == socialSecurityInfo.getPracticalMemberTotalPay()
                                            || null != socialSecurityInfo.getPracticalCompanyTotalPay()
//                                                || socialSecurityInfo.getPracticalMemberTotalPay() != 0
//                                                || socialSecurityInfo.getPracticalCompanyTotalPay() != 0
                                    ) ) {
                                        if (null == monthServiceFeeBalance) {
                                            monthServiceFeeBalance = new MonthServiceFeeBalance();
                                            monthServiceFeeBalance.setBillMonth(rfi.getBillMonth());
                                            monthServiceFeeBalance.setCompanyId(companySonBillItem.getCompanyId());
                                            monthServiceFeeBalance.setServiceFeeBalance(serviceFeeTotal.subtract(new BigDecimal(list1.get(1).toString())).doubleValue());
                                            monthServiceFeeBalanceMapper.save(monthServiceFeeBalance);
                                            break out;
                                        } else {
                                            monthServiceFeeBalance.setServiceFeeBalance(new BigDecimal(monthServiceFeeBalance.getServiceFeeBalance().toString()).
                                                    add(serviceFeeTotal.subtract(new BigDecimal(list1.get(1).toString()))).doubleValue());
                                            monthServiceFeeBalanceMapper.update(monthServiceFeeBalance);
                                            break out;
                                        }
                                    }
                                }

                            } else {
                                if (null == monthServiceFeeBalance) {
                                    monthServiceFeeBalance = new MonthServiceFeeBalance();
                                    monthServiceFeeBalance.setBillMonth(rfi.getBillMonth());
                                    monthServiceFeeBalance.setCompanyId(companySonBillItem.getCompanyId());
                                    monthServiceFeeBalance.setServiceFeeBalance(serviceFeeTotal.subtract(new BigDecimal(list1.get(1).toString())).doubleValue());
                                    monthServiceFeeBalanceMapper.save(monthServiceFeeBalance);
                                    break out;
                                } else {
                                    monthServiceFeeBalance.setServiceFeeBalance(new BigDecimal(monthServiceFeeBalance.getServiceFeeBalance().toString()).
                                            add(serviceFeeTotal.subtract(new BigDecimal(list1.get(1).toString()))).doubleValue());
                                    monthServiceFeeBalanceMapper.update(monthServiceFeeBalance);
                                    break out;
                                }
                            }
                        }
                        break;
                        case 5 :
                            //异动量
                            break;
                        case 6 :
                            //整体打包
                            break;
                        case 7 :
                            //按人数阶梯式整体
                        {
                            //获取工资
                            SalaryInfo salaryInfo = salaryInfoMapper.getByMemberIdAndMonth(rfi.getServiceNowYM(),rfi.getMemberId());
                            if (null == salaryInfo) {
                                //获取社保
                                SocialSecurityInfo socialSecurityInfo = socialSecurityInfoMapper.getByMemberIdAndServiceNowYM(rfi.getServiceNowYM(),rfi.getMemberId());
                                if (null == socialSecurityInfo ||
                                        (null != socialSecurityInfo.getProcessingScheme()
                                                && socialSecurityInfo.getPracticalMemberTotalPay() == 0
                                                && socialSecurityInfo.getPracticalCompanyTotalPay() == 0) ||
                                        (null == socialSecurityInfo.getPracticalMemberTotalPay()
                                                || null != socialSecurityInfo.getPracticalCompanyTotalPay()
//                                                || socialSecurityInfo.getPracticalMemberTotalPay() != 0
//                                                || socialSecurityInfo.getPracticalCompanyTotalPay() != 0
                                        ) ) {
                                    //0：普通 1：派遣  2：外包
                                    Set<Integer> memberSet0 = new HashSet<Integer>();
                                    Set<Integer> memberSet1 = new HashSet<Integer>();
                                    Set<Integer> memberSet2 = new HashSet<Integer>();
                                    //根据公司id和服务月获取社保集合
                                    List<SocialSecurityInfo> socialSecurityInfoList =
                                            socialSecurityInfoMapper.getCompanyIdAndServiceNowYMAndServiceFeeConfigId(companySonBillItem.getCompanyId(),
                                                    rfi.getServiceNowYM(),method.getServiceFeeConfigId());
                                    //根据公司id和服务月获取公积金集合
                                    List<ReservedFundsInfo> reservedFundsInfoList =
                                            reservedFundsInfoMapper.getCompanyIdAndServiceNowYMAndServiceFeeConfigId(companySonBillItem.getCompanyId(),rfi.getServiceNowYM(),method.getServiceFeeConfigId());
                                    //根据公司id和服务月获取公积金集合
                                    List<SalaryInfo> salaryInfoList =
                                            salaryInfoMapper.getCompanyIdAndServiceNowYMAndServiceFeeConfigId(companySonBillItem.getCompanyId(),rfi.getServiceNowYM(),method.getServiceFeeConfigId());
                                    for (SocialSecurityInfo info : socialSecurityInfoList) {
                                        if (info.getWaysOfCooperation() == 0 && null == info.getProcessingScheme()) {
                                            memberSet0.add(info.getMemberId());
                                        }
                                        if (info.getWaysOfCooperation() == 1) {
                                            memberSet1.add(info.getMemberId());
                                        }
                                        if (info.getWaysOfCooperation() == 2) {
                                            memberSet2.add(info.getMemberId());
                                        }
                                    }
                                    for (ReservedFundsInfo info : reservedFundsInfoList) {
                                        if (info.getWaysOfCooperation() == 0 && null == info.getProcessingScheme()) {
                                            memberSet0.add(info.getMemberId());
                                        }
                                        if (info.getWaysOfCooperation() == 1) {
                                            memberSet1.add(info.getMemberId());
                                        }
                                        if (info.getWaysOfCooperation() == 2) {
                                            memberSet2.add(info.getMemberId());
                                        }
                                    }
                                    for (SalaryInfo info : salaryInfoList) {
                                        if (info.getWaysOfCooperation() == 0) {
                                            memberSet0.add(info.getMemberId());
                                        }
                                        if (info.getWaysOfCooperation() == 1) {
                                            memberSet1.add(info.getMemberId());
                                        }
                                        if (info.getWaysOfCooperation() == 2) {
                                            memberSet2.add(info.getMemberId());
                                        }
                                    }

                                    //获取此服务配置总服务费
                                    Double serviceFeeTotal = monthServiceFeeMapper.getByDateAndCompanyIdOnlyOneAndServiceFeeConfigId(
                                            rfi.getBillMonth(),companySonBillItem.getCompanyId(),method.getServiceFeeConfigId());
                                    //0：普通 1：派遣  2：外包
                                    Double serviceFee0 = 0.0;
                                    Double serviceFee1 = 0.0;
                                    Double serviceFee2 = 0.0;
                                    for (CompanyCooperationMethod cooperationMethod : methods) {
                                        Collections.sort(cooperationMethod.getServiceFeeList(), new Comparator<CompanyCooperationServiceFee>() {
                                            public int compare(CompanyCooperationServiceFee o1, CompanyCooperationServiceFee o2) {
                                                return o1.getExtent() - o2.getExtent();
                                            }
                                        });
                                        if (cooperationMethod.getCooperationMethodId() == 0) {
                                            serviceFee0 = MemberService.getServiceFeePersonNumJTZT2(cooperationMethod,memberSet0.size());
                                            continue ;
                                        }
                                        if (cooperationMethod.getCooperationMethodId() == 1) {
                                            serviceFee1 = MemberService.getServiceFeePersonNumJTZT2(cooperationMethod,memberSet1.size());
                                            continue ;
                                        }
                                        if (cooperationMethod.getCooperationMethodId() == 2) {
                                            serviceFee2 = MemberService.getServiceFeePersonNumJTZT2(cooperationMethod,memberSet2.size());
                                            continue ;
                                        }
                                    }
                                    BigDecimal serviceFeeTotal2 = new BigDecimal(serviceFee0.toString()).
                                            add(new BigDecimal(serviceFee1.toString())).
                                            add(new BigDecimal(serviceFee2.toString()));
                                    if (null == monthServiceFeeBalance) {
                                        monthServiceFeeBalance = new MonthServiceFeeBalance();
                                        monthServiceFeeBalance.setBillMonth(rfi.getBillMonth());
                                        monthServiceFeeBalance.setCompanyId(companySonBillItem.getCompanyId());
                                        monthServiceFeeBalance.setServiceFeeBalance(new BigDecimal(serviceFeeTotal.toString()).subtract(serviceFeeTotal2).doubleValue());
                                        monthServiceFeeBalanceMapper.save(monthServiceFeeBalance);
                                        break out;
                                    } else {
                                        monthServiceFeeBalance.setServiceFeeBalance(new BigDecimal(serviceFeeTotal.toString()).subtract(serviceFeeTotal2).doubleValue());
                                        monthServiceFeeBalanceMapper.update(monthServiceFeeBalance);
                                        break out;
                                    }

                                }
                            }
                        }
                        break;
                    }
                }
            }

        }

    }
}
