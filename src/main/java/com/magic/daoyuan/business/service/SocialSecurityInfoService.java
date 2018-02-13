package com.magic.daoyuan.business.service;

import com.fasterxml.jackson.databind.ObjectMapper;
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
 * 账单-社保缴纳明细
 * @author lzh
 * @create 2017/10/19 9:38
 */
@Service
public class SocialSecurityInfoService {

    @Resource
    private ISocialSecurityInfoMapper socialSecurityInfoMapper;
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
    private IReservedFundsInfoMapper reservedFundsInfoMapper;
    @Resource
    private ISalaryInfoMapper salaryInfoMapper;
    @Resource
    private IMonthServiceFeeDetailMapper monthServiceFeeDetailMapper;


    /**
     * 后台页面 分页获取社保缴纳明细
     *
     * @param pageArgs    分页属性
     * @param userName     姓名
     * @param certificateType     证件类型
     * @param idCard     证件编号
     * @param socialSecurityNum   社保编码
     * @param payPlaceOrganizationName     缴金地-经办机构 名
     * @param insuranceLevelName     险种档次名
     * @param companySonBillItemId     子账单子类id
     * @param companySonTotalBillId     账单总汇id
     * @return
     */
    public PageList<SocialSecurityInfo> list(PageArgs pageArgs , String userName, Integer certificateType, String idCard,
                                             String socialSecurityNum, String payPlaceOrganizationName,
                                             String insuranceLevelName, Integer companySonBillItemId,
                                             Integer companySonTotalBillId, Date billMonth , Integer companyId) {
        PageList<SocialSecurityInfo> pageList = new PageList<SocialSecurityInfo>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userName", userName);
        map.put("payPlaceOrganizationName", payPlaceOrganizationName);
        map.put("socialSecurityNum", socialSecurityNum);
        map.put("insuranceLevelName", insuranceLevelName);
        map.put("certificateType", certificateType);
        map.put("idCard", idCard);
        map.put("companySonBillItemId", companySonBillItemId);
        map.put("companySonTotalBillId", companySonTotalBillId);
        map.put("billMonth", billMonth);
        map.put("companyId", companyId);
        int count = socialSecurityInfoMapper.listCount(map);
        if (count > 0) {
            map.put("pageArgs", pageArgs);
            List<SocialSecurityInfo> socialSecurityInfos = socialSecurityInfoMapper.list(map);
            for (SocialSecurityInfo info : socialSecurityInfos) {
                Iterator<SocialSecurityInfoItem> infoItemIterator = info.getSocialSecurityInfoItems().iterator();
                while (infoItemIterator.hasNext()) {
                    SocialSecurityInfoItem socialSecurityInfoItem = infoItemIterator.next();
                    if (null == socialSecurityInfoItem.getPayPrice() ||socialSecurityInfoItem.getPayPrice() == 0) {
                        infoItemIterator.remove();
                    }
                }
            }
            pageList.setList(socialSecurityInfos);
        }
        pageList.setTotalSize(count);
        return pageList;
    }




    /**
     * H5页面 分页获取社保缴纳明细
     *
     * @param companyId
     * @return
     */
    public List<SocialSecurityInfo> list3(Integer companyId,Date billMonth) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userName", null);
        map.put("payPlaceOrganizationName", null);
        map.put("socialSecurityNum", null);
        map.put("insuranceLevelName", null);
        map.put("certificateType", null);
        map.put("idCard", null);
        map.put("companySonBillItemId", null);
        map.put("companySonTotalBillId", null);
        map.put("companyId", companyId);
        map.put("billMonth", billMonth);
        return socialSecurityInfoMapper.list(map);
    }



    /**
     * H5页面 分页获取社保缴纳明细
     *
     * @param companySonTotalBillId     账单总汇id
     * @return
     */
    public List<SocialSecurityInfo> list(Integer companySonTotalBillId,Date billMonth) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userName", null);
        map.put("payPlaceOrganizationName", null);
        map.put("socialSecurityNum", null);
        map.put("insuranceLevelName", null);
        map.put("certificateType", null);
        map.put("idCard", null);
        map.put("companySonBillItemId", null);
        map.put("companySonTotalBillId", companySonTotalBillId);
        map.put("billMonth", billMonth);
        return socialSecurityInfoMapper.list(map);
    }

    /**
     * 后台页面 分页获取社保稽核有差异的稽核列表
     *
     * @param pageArgs    分页属性
     * @param serviceNowYM     服务年月
     * @param companyId     公司id
     * @return
     */
    public PageList<SocialSecurityInfo> auditList(PageArgs pageArgs , Date serviceNowYM, Integer companyId) {
        PageList<SocialSecurityInfo> pageList = new PageList<SocialSecurityInfo>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("serviceNowYM", serviceNowYM);
        map.put("companyId", companyId);
        int count = socialSecurityInfoMapper.auditListCount(map);
        if (count > 0) {
            map.put("pageArgs", pageArgs);
            pageList.setList(socialSecurityInfoMapper.auditList(map));
        }
        pageList.setTotalSize(count);
        return pageList;
    }

    /**
     * 更新
     * @param securityInfo
     */
    public void update(SocialSecurityInfo securityInfo) {
        socialSecurityInfoMapper.update(securityInfo);
    }

    /**
     * 更新处理方案状态
     * @param securityInfo
     */
    @Transactional
    public void updateProcessingScheme(SocialSecurityInfo securityInfo) throws IOException {
        SocialSecurityInfo ssi = socialSecurityInfoMapper.info(securityInfo.getId());
        if (null == ssi) {
            throw new InterfaceCommonException(StatusConstant.NO_DATA,"未知的社保信息");
        }
        socialSecurityInfoMapper.update(securityInfo);
        int count = socialSecurityInfoMapper.processingSchemeCount(ssi.getCompanySonBillItemId());
        CompanySonBillItem companySonBillItem = companySonBillItemMapper.info(ssi.getCompanySonBillItemId());
        if (count == 0) {
            companySonBillItem.setIsSocialSecurityAudit(1);
            List<ReservedFundsInfo> reservedFundsInfoList = reservedFundsInfoMapper.getByCompanyAndBillMonth(companySonBillItem.getCompanyId(),ssi.getBillMonth());
            if (reservedFundsInfoList.size() == 0) {
                companySonBillItem.setIsReservedFundAudit(1);
            }
        }
        companySonBillItem.setAuditTheDifference(companySonBillItem.getAuditTheDifference() -
                (ssi.getCompanyTotalPay() - ssi.getPracticalCompanyTotalPay()) -
                (ssi.getMemberTotalPay() - ssi.getPracticalMemberTotalPay()));

        companySonBillItemMapper.update(companySonBillItem);

        // TODO: 2017/12/8 退回客户
        if (securityInfo.getProcessingScheme() == 2) {
            OutlayAmountRecord record = outlayAmountRecordMapper.getByBillMonthAndCompanyId(companySonBillItem.getCompanyId(),ssi.getBillMonth());
            if (null == record) {
                record = new OutlayAmountRecord();
                BankInfo bankInfo = bankInfoMapper.getByCompanyId(companySonBillItem.getCompanyId());
                if (null == bankInfo) {
                    throw new InterfaceCommonException(StatusConstant.NO_DATA,"请先设置公司收款账号信息");
                }
                record.setBillMonth(ssi.getBillMonth());
                record.setType2(1);
                record.setIsValid(1);
                record.setAccountName(bankInfo.getAccountName());
                record.setBankAccount(bankInfo.getBankAccount());
                record.setBankInfoId(bankInfo.getId());
                record.setBankName(bankInfo.getBankName());
                record.setCompanyId(companySonBillItem.getCompanyId());
                record.setIsUrgent(0);
                record.setStatus(2001);
                record.setAmount((ssi.getCompanyTotalPay() - ssi.getPracticalCompanyTotalPay()) +
                        (ssi.getMemberTotalPay() - ssi.getPracticalMemberTotalPay()));
                outlayAmountRecordMapper.addOutlayAmountRecord(record);
                Backlog backlog = new Backlog();
                backlog.setRoleId(12);
                backlog.setContent("有新的待审核的出款申请");
                backlog.setUrl("/page/outlayAmountRecord/list?roleId=12&status=2001");
                backlogMapper.save(backlog);
            } else {
                record.setAmount(record.getAmount() + (ssi.getCompanyTotalPay() - ssi.getPracticalCompanyTotalPay()) -
                        (ssi.getMemberTotalPay() - ssi.getPracticalMemberTotalPay()));
                outlayAmountRecordMapper.updateOutlayAmountRecord(record);
                Backlog backlog = new Backlog();
                if (!CommonUtil.isEmpty(record.getStatus())) {
                    if (record.getStatus().equals(2002)) {
                        backlog.setRoleId(11);
                        backlog.setContent("有新的待出款的出款申请  ");
                        backlog.setUrl("/page/outlayAmountRecord/list?roleId=11&status=2002");
                    } else if (record.getStatus().equals(2003)) {
                        backlog.setRoleId(11);
                        backlog.setContent("有新的出款申请已出款  ");
                        backlog.setUrl("/page/outlayAmountRecord/list?userId="+record.getUserId()+"&status=2003");
                    }
                }
                backlogMapper.save(backlog);
            }
        }


        //处理服务费 记录补差
        //没有上传拷盘
        if (ssi.getIsUploadKaoPan() == 0 ||
                (ssi.getPracticalCompanyTotalPay() == 0 &&
                        ssi.getPracticalMemberTotalPay() == 0)) {
            //获取服务费
            MonthServiceFee monthServiceFee = monthServiceFeeMapper.
                    getByDateAndCompanyIdOnlyOne(ssi.getBillMonth(),companySonBillItem.getCompanyId());
            //公司 合作方式
            List<CompanyCooperationMethod> methods = com.alibaba.fastjson.JSONArray.parseArray(
                    monthServiceFee.getCompanyCooperationMethodJson(),
                    CompanyCooperationMethod.class);

            //服务费补差
            MonthServiceFeeBalance monthServiceFeeBalance = monthServiceFeeBalanceMapper.getByCompanyIdAndBillMonth(companySonBillItem.getCompanyId(),ssi.getBillMonth());
            //合作方式 0：普通 1：派遣  2：外包
            out:for (CompanyCooperationMethod method : methods) {
                if (method.getCooperationMethodId().equals(monthServiceFee.getWaysOfCooperation())) {
                    switch (method.getServiceFeeConfigId()) {
                        case 1 :
                            //按固定额
                        {
                            //获取工资
                            SalaryInfo salaryInfo = salaryInfoMapper.getByMemberIdAndMonth(ssi.getServiceNowYM(),ssi.getMemberId());
                            if (null == salaryInfo) {
                                //获取公积金
                                ReservedFundsInfo reservedFundsInfo = reservedFundsInfoMapper.getByMemberIdAndServiceNowYM(ssi.getServiceNowYM(),ssi.getMemberId());
                                if (null == reservedFundsInfo ||
                                        (null != reservedFundsInfo.getProcessingScheme()
                                                && reservedFundsInfo.getPracticalMemberTotalPay() == 0
                                                && reservedFundsInfo.getPracticalCompanyTotalPay() == 0) ||
                                        (null == reservedFundsInfo.getPracticalMemberTotalPay()
                                                || null != reservedFundsInfo.getPracticalCompanyTotalPay()
//                                                || reservedFundsInfo.getPracticalMemberTotalPay() != 0
//                                                || reservedFundsInfo.getPracticalCompanyTotalPay() != 0
                                        ) ) {
                                    if (null == monthServiceFeeBalance) {
                                        monthServiceFeeBalance = new MonthServiceFeeBalance();
                                        monthServiceFeeBalance.setBillMonth(ssi.getBillMonth());
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
                            SalaryInfo salaryInfo = salaryInfoMapper.getByMemberIdAndMonth(ssi.getServiceNowYM(),ssi.getMemberId());
                            if (null == salaryInfo) {
                                //获取公积金
                                ReservedFundsInfo reservedFundsInfo = reservedFundsInfoMapper.getByMemberIdAndServiceNowYM(ssi.getServiceNowYM(),ssi.getMemberId());
                                if (null == reservedFundsInfo ||
                                        (null != reservedFundsInfo.getProcessingScheme()
                                                && reservedFundsInfo.getPracticalMemberTotalPay() == 0
                                                && reservedFundsInfo.getPracticalCompanyTotalPay() == 0) ||
                                        (null == reservedFundsInfo.getPracticalMemberTotalPay()
                                                || null != reservedFundsInfo.getPracticalCompanyTotalPay()
//                                                || reservedFundsInfo.getPracticalMemberTotalPay() != 0
//                                                || reservedFundsInfo.getPracticalCompanyTotalPay() != 0
                                        ) ) {
                                    //0：普通 1：派遣  2：外包
                                    Set<Integer> memberSet0 = new HashSet<Integer>();
                                    Set<Integer> memberSet1 = new HashSet<Integer>();
                                    Set<Integer> memberSet2 = new HashSet<Integer>();
                                    //根据公司id和服务月获取社保集合
                                    List<SocialSecurityInfo> socialSecurityInfoList =
                                            socialSecurityInfoMapper.getCompanyIdAndServiceNowYMAndServiceFeeConfigId(companySonBillItem.getCompanyId(),
                                                    ssi.getServiceNowYM(),method.getServiceFeeConfigId());
                                    //根据公司id和服务月获取公积金集合
                                    List<ReservedFundsInfo> reservedFundsInfoList =
                                            reservedFundsInfoMapper.getCompanyIdAndServiceNowYMAndServiceFeeConfigId(companySonBillItem.getCompanyId(),ssi.getServiceNowYM(),method.getServiceFeeConfigId());
                                     //根据公司id和服务月获取公积金集合
                                    List<SalaryInfo> salaryInfoList =
                                            salaryInfoMapper.getCompanyIdAndServiceNowYMAndServiceFeeConfigId(companySonBillItem.getCompanyId(),ssi.getServiceNowYM(),method.getServiceFeeConfigId());
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
                                            ssi.getBillMonth(),companySonBillItem.getCompanyId(),method.getServiceFeeConfigId());
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
                                        monthServiceFeeBalance.setBillMonth(ssi.getBillMonth());
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
                            //获取公积金
                            ReservedFundsInfo reservedFundsInfo = reservedFundsInfoMapper.getByMemberIdAndServiceNowYM(ssi.getServiceNowYM(),ssi.getMemberId());
                            if (null != reservedFundsInfo && (reservedFundsInfo.getPracticalMemberTotalPay() != 0
                                    || reservedFundsInfo.getPracticalCompanyTotalPay() != 0)) {
                                businessStr = "4";
                            }
                            //获取工资
                            SalaryInfo salaryInfo = salaryInfoMapper.getByMemberIdAndMonth(ssi.getServiceNowYM(),ssi.getMemberId());
                            if (null != salaryInfo) {
                                if (null == businessStr) {
                                    businessStr = "5";
                                } else {
                                    businessStr = businessStr + ",5";
                                }
                            }

                            //本月员工缴纳明细
                            List<MonthServiceFeeDetail> monthServiceFeeDetail = monthServiceFeeDetailMapper.getByMemberIdAndMonth(ssi.getMemberId(),ssi.getServiceNowYM());
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
                            if (null == reservedFundsInfo ||
                                    (null != reservedFundsInfo.getProcessingScheme()
                                    && reservedFundsInfo.getPracticalMemberTotalPay() == 0
                                    && reservedFundsInfo.getPracticalCompanyTotalPay() == 0) ||
                                    (null == reservedFundsInfo.getPracticalMemberTotalPay()
                                            || null != reservedFundsInfo.getPracticalCompanyTotalPay()
//                                                || reservedFundsInfo.getPracticalMemberTotalPay() != 0
//                                                || reservedFundsInfo.getPracticalCompanyTotalPay() != 0
                                    ) ) {
                                if (null == monthServiceFeeBalance) {
                                    monthServiceFeeBalance = new MonthServiceFeeBalance();
                                    monthServiceFeeBalance.setBillMonth(ssi.getBillMonth());
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
                            List<MonthServiceFeeDetail> monthServiceFeeDetail = monthServiceFeeDetailMapper.getByMemberIdAndMonth(ssi.getMemberId(),ssi.getServiceNowYM());
                            BigDecimal serviceFeeTotal = new BigDecimal("0.0");
                            for (MonthServiceFeeDetail detail : monthServiceFeeDetail) {
                                mapList.putAll(detail.getBusinessCityJsonList());
                                serviceFeeTotal = serviceFeeTotal.add(new BigDecimal(detail.getAmount().toString()));
                            }
                            List<Object> list1 = mapList.get(3);
                            if (mapList.size() > 1) {
                                //获取公积金
                                ReservedFundsInfo reservedFundsInfo = reservedFundsInfoMapper.getByMemberIdAndServiceNowYM(ssi.getServiceNowYM(),ssi.getMemberId());
                                if (null == reservedFundsInfo) {
                                    if (null == monthServiceFeeBalance) {
                                        monthServiceFeeBalance = new MonthServiceFeeBalance();
                                        monthServiceFeeBalance.setBillMonth(ssi.getBillMonth());
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
                                    if (null != reservedFundsInfo.getProcessingScheme()
                                            && reservedFundsInfo.getPracticalMemberTotalPay() == 0
                                            && reservedFundsInfo.getPracticalCompanyTotalPay() == 0) {
                                        if (null == monthServiceFeeBalance) {
                                            monthServiceFeeBalance = new MonthServiceFeeBalance();
                                            monthServiceFeeBalance.setBillMonth(ssi.getBillMonth());
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
                                    if (null != reservedFundsInfo.getProcessingScheme()
                                            && reservedFundsInfo.getPracticalMemberTotalPay() == 0
                                            && reservedFundsInfo.getPracticalCompanyTotalPay() == 0) {
                                        if (null == monthServiceFeeBalance) {
                                            monthServiceFeeBalance = new MonthServiceFeeBalance();
                                            monthServiceFeeBalance.setBillMonth(ssi.getBillMonth());
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
                                    if (null == reservedFundsInfo.getPracticalMemberTotalPay()
                                            || null != reservedFundsInfo.getPracticalCompanyTotalPay()
//                                                || reservedFundsInfo.getPracticalMemberTotalPay() != 0
//                                                || reservedFundsInfo.getPracticalCompanyTotalPay() != 0
                                            )  {
                                        if (null == monthServiceFeeBalance) {
                                            monthServiceFeeBalance = new MonthServiceFeeBalance();
                                            monthServiceFeeBalance.setBillMonth(ssi.getBillMonth());
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
                                    monthServiceFeeBalance.setBillMonth(ssi.getBillMonth());
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
                            SalaryInfo salaryInfo = salaryInfoMapper.getByMemberIdAndMonth(ssi.getServiceNowYM(),ssi.getMemberId());
                            if (null == salaryInfo) {
                                //获取公积金
                                ReservedFundsInfo reservedFundsInfo = reservedFundsInfoMapper.getByMemberIdAndServiceNowYM(ssi.getServiceNowYM(),ssi.getMemberId());
                                if (null == reservedFundsInfo ||
                                        (null != reservedFundsInfo.getProcessingScheme()
                                                && reservedFundsInfo.getPracticalMemberTotalPay() == 0
                                                && reservedFundsInfo.getPracticalCompanyTotalPay() == 0) ||
                                        (null == reservedFundsInfo.getPracticalMemberTotalPay()
                                                || null != reservedFundsInfo.getPracticalCompanyTotalPay()
//                                                || reservedFundsInfo.getPracticalMemberTotalPay() != 0
//                                                || reservedFundsInfo.getPracticalCompanyTotalPay() != 0
                                        ) ) {
                                    //0：普通 1：派遣  2：外包
                                    Set<Integer> memberSet0 = new HashSet<Integer>();
                                    Set<Integer> memberSet1 = new HashSet<Integer>();
                                    Set<Integer> memberSet2 = new HashSet<Integer>();
                                    //根据公司id和服务月获取社保集合
                                    List<SocialSecurityInfo> socialSecurityInfoList =
                                            socialSecurityInfoMapper.getCompanyIdAndServiceNowYMAndServiceFeeConfigId(companySonBillItem.getCompanyId(),
                                                    ssi.getServiceNowYM(),method.getServiceFeeConfigId());
                                    //根据公司id和服务月获取公积金集合
                                    List<ReservedFundsInfo> reservedFundsInfoList =
                                            reservedFundsInfoMapper.getCompanyIdAndServiceNowYMAndServiceFeeConfigId(companySonBillItem.getCompanyId(),ssi.getServiceNowYM(),method.getServiceFeeConfigId());
                                    //根据公司id和服务月获取公积金集合
                                    List<SalaryInfo> salaryInfoList =
                                            salaryInfoMapper.getCompanyIdAndServiceNowYMAndServiceFeeConfigId(companySonBillItem.getCompanyId(),ssi.getServiceNowYM(),method.getServiceFeeConfigId());
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
                                            ssi.getBillMonth(),companySonBillItem.getCompanyId(),method.getServiceFeeConfigId());
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
                                        monthServiceFeeBalance.setBillMonth(ssi.getBillMonth());
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
