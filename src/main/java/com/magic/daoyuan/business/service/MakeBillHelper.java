package com.magic.daoyuan.business.service;

import com.magic.daoyuan.business.entity.*;
import com.magic.daoyuan.business.enums.BillType;
import com.magic.daoyuan.business.mapper.*;
import com.magic.daoyuan.business.util.StatusConstant;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Eric Xie on 2017/12/28 0028.
 */

@Component
public class MakeBillHelper {


    @Resource
    private ISocialSecurityInfoMapper socialSecurityInfoMapper;
    @Resource
    private IReservedFundsInfoMapper reservedFundsInfoMapper;
    @Resource
    private IBusinessInsuranceItemMapper businessInsuranceItemMapper;
    @Resource
    private IBusinessYcItemMapper businessYcItemMapper;
    @Resource
    private ISalaryInfoMapper salaryInfoMapper;
    @Resource
    private IMonthServiceFeeMapper monthServiceFeeMapper;
    @Resource
    private ICompanySonBillMapper companySonBillMapper;
    @Resource
    private ICompanyBillInfoMapper companyBillInfoMapper;
    @Resource
    private ICompanySonTotalBillMapper companySonTotalBillMapper;



    public List<MakeBill> getMakeBill(List<Integer> companySonTotalBillIds,Integer confirmFundId,Date makeBillDate) throws Exception{

        List<MakeBill> makeBills = new ArrayList<MakeBill>();
        // 查询账单年月
        Date billMonth = companySonTotalBillMapper.info(companySonTotalBillIds.get(0)).getBillMonth();

        // 查询此总账单下所有的账单明细
        // 社保账单明细
        List<SocialSecurityInfo> securityInfoList = socialSecurityInfoMapper.querySocialSecurityInfoByTotalBillId(companySonTotalBillIds);
        // 公积金账单明细
        List<ReservedFundsInfo> fundsInfoList = reservedFundsInfoMapper.queryReservedFundsInfoByTotalBillIds(companySonTotalBillIds);
        // 商业险
        List<BusinessInsuranceItem> insuranceItemList = businessInsuranceItemMapper.queryBusinessInsuranceItemByTotalBillId(companySonTotalBillIds);
        // 一次性业务
        List<BusinessYcItem> ycItemList = businessYcItemMapper.queryBusinessYcItemByTotalBillId(companySonTotalBillIds);
        // 工资
        List<SalaryInfo> salaryInfoList = salaryInfoMapper.querySalaryInfoByTotalBillId(companySonTotalBillIds);
        // 服务费详细
        List<MonthServiceFeeDetail> serviceFeeList = monthServiceFeeMapper.queryMonthServiceFeeByTotalBillId(companySonTotalBillIds);

        // 获取所有的子账单ID集合
        Set<Integer> sonBillList = new HashSet<Integer>();
        if(null != securityInfoList && securityInfoList.size() > 0){
            for (SocialSecurityInfo socialSecurityInfo : securityInfoList) {
                if (null != socialSecurityInfo.getCompanySonBillId()) {
                    sonBillList.add(socialSecurityInfo.getCompanySonBillId());
                }
            }
        }
        if(null != fundsInfoList && fundsInfoList.size() > 0){
            for (ReservedFundsInfo info : fundsInfoList) {
                if (null != info.getCompanySonBillId()) {
                    sonBillList.add(info.getCompanySonBillId());
                }
            }
        }
        if(null != insuranceItemList && insuranceItemList.size() > 0){
            for (BusinessInsuranceItem item : insuranceItemList) {
                if (null != item.getCompanySonBillId()) {
                    sonBillList.add(item.getCompanySonBillId());
                }
            }
        }
        if(null != ycItemList && ycItemList.size() > 0){
            for (BusinessYcItem item : ycItemList) {
                if (null != item.getCompanySonBillId()) {
                    sonBillList.add(item.getCompanySonBillId());
                }
            }
        }
        if(null != salaryInfoList && salaryInfoList.size() > 0){
            for (SalaryInfo item : salaryInfoList) {
                if (null != item.getCompanySonBillId()) {
                    sonBillList.add(item.getCompanySonBillId());
                }
            }
        }
        // 获取所有的子账单下绑定的票据信息
        if(sonBillList.size() > 0){
            List<CompanySonBill> billList = companySonBillMapper.queryCompanySonBillByIds(sonBillList);
            // 生产票据
            if(null != billList && billList.size() > 0){
                for (CompanySonBill companySonBill : billList) {
                    Map<String, Double> billData = getBillData(securityInfoList, fundsInfoList, insuranceItemList, ycItemList, salaryInfoList, serviceFeeList, companySonBill);
                    double socialSecurityAmount = billData.get("socialSecurityAmount"); // 社保 业务费
                    double reservedFundsAmount = billData.get("reservedFundsAmount"); // 公积金 业务费
                    double salaryAmount = billData.get("salaryAmount"); // 工资

                    double socialSecurityAmount_wb = billData.get("socialSecurityAmount_wb"); // 社保 业务费 外包
                    double reservedFundsAmount_wb = billData.get("reservedFundsAmount_wb");// 公积金 业务费 外包
                    double salaryAmount_wb = billData.get("salaryAmount_wb"); // 工资 外包

                    double totalServiceAmount = billData.get("totalServiceAmount"); // 此子账单下员工的服务费
                    double totalServiceAmount_wb = billData.get("totalServiceAmount_wb"); // 此子账单下员工的服务费
                    double disabilityAmount = billData.get("disabilityAmount"); // 残保金费用
                    double otherAmount = billData.get("otherAmount"); // 其他周期性业务（除残保金外）　＋　一次性业务
                    double disabilityAmount_wb = billData.get("disabilityAmount_wb"); // 残保金费用 外包
                    double otherAmount_wb = billData.get("otherAmount_wb"); // 其他周期性业务（除残保金外）　＋　一次性业务 外包
                    // 开票
                    if(companySonBill.getBillInfo().getBillType().equals(BillType.SpecialInvoice.ordinal())){
                        // 如果当前票据类型为 专票
                        if(StatusConstant.NO.equals(companySonBill.getBillInfo().getIsPartBill())){
                            // 不分开 开票
                            double wb = socialSecurityAmount_wb + reservedFundsAmount_wb + salaryAmount_wb + disabilityAmount_wb + totalServiceAmount_wb;
                            if(wb != 0 || otherAmount_wb != 0){
                                //外包不分开开票  开始
                                MakeBill makeBill = getMakeBill(confirmFundId,makeBillDate,companySonBill.getCompanyBillInfoId(),wb + otherAmount_wb,0.0,
                                        getRemark(socialSecurityAmount_wb,reservedFundsAmount_wb,salaryAmount_wb,totalServiceAmount_wb),StatusConstant.SHOPNAMEID_LABOR,billMonth);
                                makeBills.add(makeBill);
//                                if (wb != 0) {
//                                    MakeBill makeBill = getMakeBill(confirmFundId,makeBillDate,companySonBill.getCompanyBillInfoId(),wb + otherAmount_wb,0.0,
//                                            getRemark(socialSecurityAmount_wb,reservedFundsAmount_wb,salaryAmount_wb,totalServiceAmount_wb),StatusConstant.SHOPNAMEID_LABOR,billMonth);
//                                    makeBills.add(makeBill);
//                                }
//                                if(otherAmount_wb != 0){
//                                    MakeBill makeBill = getOtherMakeBill(confirmFundId, makeBillDate, companySonBill, otherAmount_wb,billMonth);
//                                    makeBills.add(makeBill);
//                                }
                                //外包不分开开票  结束
                            }
                            // 不是外包
                            double un_wb = socialSecurityAmount + reservedFundsAmount + salaryAmount + disabilityAmount + totalServiceAmount;
                            // 不是外包不分开开票  开始
                            if (un_wb != 0) {
                                MakeBill makeBill = getMakeBill(confirmFundId,makeBillDate,companySonBill.getCompanyBillInfoId(),un_wb,un_wb -totalServiceAmount,
                                        getRemark(socialSecurityAmount,reservedFundsAmount,salaryAmount,totalServiceAmount),StatusConstant.SHOPNAMEID_STAFFING,billMonth);
                                makeBills.add(makeBill);
                            }
                            if(otherAmount != 0){
                                MakeBill makeBill = getOtherMakeBill(confirmFundId, makeBillDate, companySonBill, otherAmount,billMonth);
                                makeBills.add(makeBill);
                            }
                            //不是外包不分开开票  结束
                        }
                        else{
                            // 分开开票
                            // 外包开始
                            double wb = socialSecurityAmount_wb + reservedFundsAmount_wb + salaryAmount_wb + disabilityAmount_wb;
                            if(wb != 0){
                                MakeBill makeBill = getMakeBill(confirmFundId,makeBillDate,companySonBill.getCompanyBillInfoId(),wb,0.0,
                                        getRemark(socialSecurityAmount_wb,reservedFundsAmount_wb,salaryAmount_wb,0),StatusConstant.SHOPNAMEID_LABOR,billMonth);
                                makeBills.add(makeBill);
                            }
//                            if( totalServiceAmount_wb!= 0){
//                                MakeBill makeBill = getMakeBill(confirmFundId,makeBillDate,companySonBill.getCompanyBillInfoId(),totalServiceAmount_wb,0.0,
//                                        getRemark(0,0,0,totalServiceAmount_wb),StatusConstant.SHOPNAMEID_LABOR,billMonth);
//                                makeBills.add(makeBill);
//                            }
                            if(otherAmount_wb != 0 || totalServiceAmount_wb != 0){
                                MakeBill makeBill = getOtherMakeBill(confirmFundId, makeBillDate, companySonBill, otherAmount_wb + totalServiceAmount_wb,billMonth);
                                makeBills.add(makeBill);
                            }
                            // 外包 结束

                            // 普通、派遣 开始  // 分开开票
                            double un_wb = socialSecurityAmount + reservedFundsAmount + salaryAmount + disabilityAmount;
                            if(un_wb > 0){
                                CompanyBillInfo billInfo = (CompanyBillInfo)companySonBill.getBillInfo().clone();
                                billInfo.setId(null);
                                billInfo.setIsCopy(StatusConstant.YES);
                                billInfo.setBillType(BillType.TaxInvoice.ordinal());
                                companyBillInfoMapper.addBillInfo(billInfo);
                                // 票据生成
                                MakeBill makeBill = getMakeBill(confirmFundId,makeBillDate,billInfo.getId(),un_wb,un_wb,
                                        getRemark(socialSecurityAmount,reservedFundsAmount,salaryAmount,0),StatusConstant.SHOPNAMEID_STAFFING,billMonth);
                                makeBills.add(makeBill);
                            }
                            // 服务费专票
                            if (totalServiceAmount > 0) {
                                MakeBill makeBill_service = getMakeBill(confirmFundId,makeBillDate,companySonBill.getCompanyBillInfoId(),totalServiceAmount,0.0,
                                        getRemark(0,0,0,totalServiceAmount),StatusConstant.SHOPNAMEID_STAFFING,billMonth);
                                makeBills.add(makeBill_service);
                            }
                            // 其他
                            if (otherAmount > 0) {
                                makeBills.add(getOtherMakeBill(confirmFundId, makeBillDate, companySonBill, otherAmount,billMonth));
                            }
                            // 普通、派遣 结束 // 分开开票
                    }
                    }
                    else{

                        // 普票
                        // 普票外包开始
                        double wb = socialSecurityAmount_wb + reservedFundsAmount_wb + salaryAmount_wb + disabilityAmount_wb + totalServiceAmount_wb;
                        if(wb != 0 || otherAmount_wb != 0){
                            MakeBill makeBill = getMakeBill(confirmFundId,makeBillDate,companySonBill.getCompanyBillInfoId(),wb + otherAmount_wb,0.0,
                                    getRemark(socialSecurityAmount_wb,reservedFundsAmount_wb,salaryAmount_wb,totalServiceAmount_wb),StatusConstant.SHOPNAMEID_LABOR,billMonth);
                            makeBills.add(makeBill);
                        }
//                        if(otherAmount_wb != 0){
//                            MakeBill makeBill = getOtherMakeBill(confirmFundId, makeBillDate, companySonBill, otherAmount_wb,billMonth);
//                            makeBills.add(makeBill);
//                        }
                        // 普票外包结束

                        // 普票 派遣普通 开始
                        double un_wb = socialSecurityAmount + reservedFundsAmount + salaryAmount + disabilityAmount + totalServiceAmount;
                        if (un_wb > 0) {
                            MakeBill makeBill = getMakeBill(confirmFundId,makeBillDate,companySonBill.getCompanyBillInfoId(),un_wb,un_wb - totalServiceAmount,
                                    getRemark(socialSecurityAmount,reservedFundsAmount,salaryAmount,totalServiceAmount), StatusConstant.SHOPNAMEID_STAFFING,billMonth);
                            makeBills.add(makeBill);
                        }
                        // 其他
                        if (otherAmount > 0) {
                            makeBills.add(getOtherMakeBill(confirmFundId, makeBillDate, companySonBill, otherAmount,billMonth));
                        }
                        // 普票 派遣普通 结束
                    }
                }

            }
        }
        return makeBills;
    }


    private  MakeBill getMakeBill(Integer confirmFundId,Date makeBillDate,Integer billInfoId,Double amountOfBill,Double amount
            ,String remark,Integer shopNameId,Date billMonth){
        MakeBill makeBill = new MakeBill();
        makeBill.setConfirmFundId(confirmFundId);
        makeBill.setMakeBillDate(null == makeBillDate ? new Date() : makeBillDate);
        makeBill.setStatus(StatusConstant.BILL_STATUS_UN);
        makeBill.setCompanyBillInfoId(billInfoId);
        makeBill.setAmountOfBill(amountOfBill);
        makeBill.setAmount(amount);
        makeBill.setRemark(remark);
        makeBill.setShopNameId(shopNameId);
        makeBill.setBillMonth(billMonth);
        return makeBill;
    }

    private  MakeBill getOtherMakeBill(Integer confirmFundId, Date makeBillDate, CompanySonBill companySonBill, double otherAmount
            ,Date billMonth) {
        MakeBill makeBill = new MakeBill();
        makeBill.setConfirmFundId(confirmFundId);
        makeBill.setMakeBillDate(makeBillDate);
        makeBill.setStatus(StatusConstant.BILL_STATUS_UN);
        makeBill.setCompanyBillInfoId(companySonBill.getBillInfo().getId());
        makeBill.setAmountOfBill(otherAmount);
        makeBill.setAmount(otherAmount - otherAmount);
        makeBill.setRemark(getRemark(0,0,0,otherAmount));
        makeBill.setShopNameId(StatusConstant.SHOPNAMEID_SERVICE);
        makeBill.setBillMonth(billMonth);
        return makeBill;
    }



    private String getRemark(double socialSecurityAmount, double reservedFundsAmount, double salaryAmount, double totalServiceAmount) {

        String msg = "";
        if(socialSecurityAmount > 0){
            msg += "社保费："+socialSecurityAmount+"元，";
        }
        if(reservedFundsAmount > 0){
            msg += "公积金："+reservedFundsAmount+"元，";
        }
        if(salaryAmount > 0){
            msg += "工资："+salaryAmount+"元，";
        }
        if(totalServiceAmount > 0){
            msg += "服务费："+totalServiceAmount+"元，";
        }
        return msg.length() > 1 ? (msg.substring(0,msg.length() - 1) + "。") : msg + "。";
    }


    private Map<String,Double> getBillData(List<SocialSecurityInfo> securityInfoList, List<ReservedFundsInfo> fundsInfoList, List<BusinessInsuranceItem> insuranceItemList, List<BusinessYcItem> ycItemList, List<SalaryInfo> salaryInfoList, List<MonthServiceFeeDetail> serviceFeeList, CompanySonBill companySonBill) {

        double socialSecurityAmount = 0.0; // 社保 业务费
        double reservedFundsAmount = 0.0; // 公积金 业务费
        double salaryAmount = 0.0; // 工资

        double socialSecurityAmount_wb = 0.0; // 社保 业务费 外包
        double reservedFundsAmount_wb = 0.0; // 公积金 业务费 外包
        double salaryAmount_wb = 0.0; // 工资 外包

        double totalServiceAmount = 0.0; // 此子账单下员工的服务费
        double totalServiceAmount_wb = 0.0; // 外包 此子账单下员工的服务费
        double disabilityAmount = 0.0; // 残保金费用
        double otherAmount = 0.0; // 其他周期性业务（除残保金外）　＋　一次性业务
        double disabilityAmount_wb = 0.0; // 残保金费用 外包
        double otherAmount_wb = 0.0; // 其他周期性业务（除残保金外）　＋　一次性业务 外包



        Set<Integer> memberIds = new HashSet<Integer>();
        if(null != securityInfoList && securityInfoList.size() > 0){
            for (SocialSecurityInfo socialSecurityInfo : securityInfoList) {
                if(companySonBill.getId().equals(socialSecurityInfo.getCompanySonBillId())){
                    double v = socialSecurityInfo.getCompanyTotalPay() + socialSecurityInfo.getMemberTotalPay();
                    if (2 == socialSecurityInfo.getWaysOfCooperation()) {
                        socialSecurityAmount_wb += v;
                    }else{
                        socialSecurityAmount += v;
                    }
                    memberIds.add(socialSecurityInfo.getMemberId());
                }
            }
        }
        if(null != fundsInfoList && fundsInfoList.size() > 0){
            for (ReservedFundsInfo info : fundsInfoList) {
                if(companySonBill.getId().equals(info.getCompanySonBillId())){
                    double v = info.getCompanyTotalPay() + info.getMemberTotalPay();
                    if (2 == info.getWaysOfCooperation()) {
                        reservedFundsAmount_wb += v;
                    }
                    else{
                        reservedFundsAmount += v;
                    }
                    memberIds.add(info.getMemberId());
                }
            }
        }
        if(null != insuranceItemList && insuranceItemList.size() > 0){
            for (BusinessInsuranceItem item : insuranceItemList) {
                if(companySonBill.getId().equals(item.getCompanySonBillId())){
                    if (2 == item.getWaysOfCooperation()) {
                        if(StatusConstant.DISABILITY_ID.equals(item.getBusinessItemId())){
                            disabilityAmount_wb += item.getPrice();
                        }else{
                            otherAmount_wb += item.getPrice();
                        }
                    }
                    else{
                        if(StatusConstant.DISABILITY_ID.equals(item.getBusinessItemId())){
                            disabilityAmount += item.getPrice();
                        }else{
                            otherAmount += item.getPrice();
                        }
                    }
                    memberIds.add(item.getMemberId());
                }
            }
        }
        if(null != ycItemList && ycItemList.size() > 0){
            for (BusinessYcItem item : ycItemList) {
                if(companySonBill.getId().equals(item.getCompanySonBillId())){
                    if (2 == item.getWaysOfCooperation()) {
                        otherAmount_wb += item.getPrice();
                    }
                    else{
                        otherAmount += item.getPrice();
                    }
                    memberIds.add(item.getMemberId());
                }
            }
        }
        if(null != salaryInfoList && salaryInfoList.size() > 0){
            for (SalaryInfo item : salaryInfoList) {
                if(companySonBill.getId().equals(item.getCompanySonBillId())){
                    if (2 == item.getWaysOfCooperation()) {
                        salaryAmount_wb += item.getTakeHomePay();
                    }
                    else{
                        salaryAmount += item.getTakeHomePay();
                    }
                    memberIds.add(item.getMemberId());
                }
            }
        }
        if(null != serviceFeeList && serviceFeeList.size() > 0){
            Iterator<Integer> memberIdIterator = memberIds.iterator();
            while (memberIdIterator.hasNext()){
                Integer next = memberIdIterator.next();
                Iterator<MonthServiceFeeDetail> iterator = serviceFeeList.iterator();
                while (iterator.hasNext()){
                    MonthServiceFeeDetail detail = iterator.next();
                    if(next.equals(detail.getMemberId())){
                        if (2 != detail.getWaysOfCooperation()) {
                            totalServiceAmount += detail.getAmount();
                        }else{
                            totalServiceAmount_wb += detail.getAmount();
                        }
                        iterator.remove();
                    }
                }
            }
        }
        Map<String,Double> data = new HashMap<String, Double>();
        data.put("socialSecurityAmount",socialSecurityAmount);
        data.put("reservedFundsAmount",reservedFundsAmount);
        data.put("salaryAmount",salaryAmount);
        data.put("socialSecurityAmount_wb",socialSecurityAmount_wb);
        data.put("reservedFundsAmount_wb",reservedFundsAmount_wb);
        data.put("salaryAmount_wb",salaryAmount_wb);
        data.put("totalServiceAmount",totalServiceAmount);
        data.put("totalServiceAmount_wb",totalServiceAmount_wb);
        data.put("disabilityAmount",disabilityAmount);
        data.put("otherAmount",otherAmount);
        data.put("disabilityAmount_wb",disabilityAmount_wb);
        data.put("otherAmount_wb",otherAmount_wb);
        return data;
    }

    public List<MakeBill> splitMakeBill(MakeBill next) throws Exception{
        List<MakeBill> others = new ArrayList<MakeBill>();
        int v = (int)Math.floor(next.getAmountOfBill() / StatusConstant.BILL_AMOUNT);
        double v1 = next.getAmountOfBill() % StatusConstant.BILL_AMOUNT;

        for (int i = 0; i < v; i++){
            MakeBill m = (MakeBill) next.clone();
            m.setAmountOfBill(StatusConstant.BILL_AMOUNT);
            if(i > 0){
                m.setAmount(0.0);
            }
            others.add(m);
        }
        if(v1 > 0){
            MakeBill m = (MakeBill) next.clone();
            m.setAmountOfBill(v1);
            m.setAmount(0.0);
            others.add(m);
        }
        return others;
    }


}
