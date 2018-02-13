package com.magic.daoyuan.business.dto;

import com.magic.daoyuan.business.util.ClassConvert;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * hr（web）端账单列表
 * @author lzh
 * @create 2017/12/19 10:30
 */
public class HrBillListDto implements Serializable {


    /** 账单号 */
    private String billNumber;

    /** 账户名 */
    private String accountName;

    /** 开户行 */
    private String bankName;

    /** 银行帐号 */
    private String bankAccount;

    /** 金额 */
    private Double amount;

    /** 备注 */
    private String remark;

    /** 账单月 */
    private Date billMonth;

    /** 业务类型 */
    private String businessStr;

    /** 快递单号 */
    private String expressNumber;

    /** 是否先票后款 0：先款  1：先票 */
    private Integer isFirstBill;

    /** 汇总账单状态 0：未确认  1：确认 */
    private Integer cstbStatus;
    private String cstbStatusStr;

    /** 是否到款认款 0：否 1：是 */
    private Integer isConfirmFund;

    /** 是否开票  0：否 1：是 */
    private Integer isMakeBill;

    /** 是否总结发送  0：否 1：是 */
    private Integer isSend;

    /** 查询快递的url地址 */
    private String expressUrl;

    /** 服务费 */
    private Double serviceFee;

    /** 客服 */
    private String userName;

    /** 公司名 */
    private String companyName;

    /** 公司id */
    private Integer companyId;

    /** 实做状态  0：待申请 1：待反馈 2：已完成 */
    private Integer realDoStatus;
    private String realDoStatusStr;

    /** 稽核状态 0 缺少拷盘 1 待稽核 2 已完成*/
    private Integer auditStatus;
    private String auditStatusStr;
    /** 拷盘状态 */
    private String isUploadKaoPanStr;

    /** 核销状态 0：未核销  1：已核销 */
    private Integer cancelAfterVerificationStatus;
    private String cancelAfterVerificationStatusStr;

    /** 票据是否到件 0：未到件 1：已收件*/
    private Integer isConsignee;
    private String isConsigneeStr;

    /** 上月余额 */
    private Double lastMonthBalance;

    /** 服务费类型及配置json */
    private String companyCooperationMethodJson;

    /** 服务费类型及配置json 上月 */
    private String companyCooperationMethodJson2;

    /** 商业险金额 */
    private Double insurancePrice;

    /** 一次性业务金额 */
    private Double ycPrice;

    /** 税费 */
    private Double taxPrice;

    /** 获取 账户名 */
    public String getAccountName() {
        return this.accountName;
    }

    /** 设置 账户名 */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    /** 获取 开户行 */
    public String getBankName() {
        return this.bankName;
    }

    /** 设置 开户行 */
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    /** 获取 银行帐号 */
    public String getBankAccount() {
        return this.bankAccount;
    }

    /** 设置 银行帐号 */
    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    /** 获取 金额 */
    public Double getAmount() {
        return this.amount;
    }

    /** 设置 金额 */
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    /** 获取 备注 */
    public String getRemark() {
        return this.remark;
    }

    /** 设置 备注 */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /** 获取 账单月 */
    public Date getBillMonth() {
        return this.billMonth;
    }

    public Integer getIsSend() {
        return isSend;
    }

    public void setIsSend(Integer isSend) {
        this.isSend = isSend;
    }

    /** 设置 账单月 */
    public void setBillMonth(Date billMonth) {
        this.billMonth = billMonth;
    }

    /** 获取 业务类型 */
    public String getBusinessStr() {
        return this.businessStr;
    }

    /** 设置 业务类型 */
    public void setBusinessStr(String businessStr) {
        this.businessStr = businessStr;
    }

    /** 获取 快递单号 */
    public String getExpressNumber() {
        return this.expressNumber;
    }

    /** 设置 快递单号 */
    public void setExpressNumber(String expressNumber) {
        this.expressNumber = expressNumber;
    }

    /** 获取 是否先票后款 0：先款  1：先票 */
    public Integer getIsFirstBill() {
        return this.isFirstBill;
    }

    /** 设置 是否先票后款 0：先款  1：先票 */
    public void setIsFirstBill(Integer isFirstBill) {
        this.isFirstBill = isFirstBill;
    }

    /** 获取 汇总账单状态 0：未确认  1：确认 */
    public Integer getCstbStatus() {
        //已提交
        Integer TNum = 0;
        //已确认
        Integer QNum = 0;
        if (null != cstbStatusStr) {
            Set<Integer> statusList = ClassConvert.strToIntegerSetGather(cstbStatusStr.split(","));
            for (Integer integer : statusList) {
                if (integer == 0) {
                    TNum ++;
                } else {
                    QNum ++;
                }
            }
            if (QNum > 0 && TNum == 0) {
                return 1;
            } else {
                return 0;
            }
        }
        return this.cstbStatus;
    }

    /** 设置 汇总账单状态 0：未确认  1：确认 */
    public void setCstbStatus(Integer cstbStatus) {
        this.cstbStatus = cstbStatus;
    }

    public String getCstbStatusStr() {
        return this.cstbStatusStr;
    }

    public void setCstbStatusStr(String cstbStatusStr) {
        this.cstbStatusStr = cstbStatusStr;
    }

    /** 获取 是否到款认款 0：否 1：是 */
    public Integer getIsConfirmFund() {
        return this.isConfirmFund;
    }

    /** 设置 是否到款认款 0：否 1：是 */
    public void setIsConfirmFund(Integer isConfirmFund) {
        this.isConfirmFund = isConfirmFund;
    }

    /** 获取 是否开票  0：否 1：是 */
    public Integer getIsMakeBill() {
        return this.isMakeBill;
    }

    /** 设置 是否开票  0：否 1：是 */
    public void setIsMakeBill(Integer isMakeBill) {
        this.isMakeBill = isMakeBill;
    }

    /** 获取 查询快递的url地址 */
    public String getExpressUrl() {
        return this.expressUrl;
    }

    /** 设置 查询快递的url地址 */
    public void setExpressUrl(String expressUrl) {
        this.expressUrl = expressUrl;
    }

    /** 获取 账单号 */
    public String getBillNumber() {
        return this.billNumber;
    }

    /** 设置 账单号 */
    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    /** 获取 服务费 */
    public Double getServiceFee() {
        return this.serviceFee;
    }

    /** 设置 服务费 */
    public void setServiceFee(Double serviceFee) {
        this.serviceFee = serviceFee;
    }

    /** 获取 客服 */
    public String getUserName() {
        return this.userName;
    }

    /** 设置 客服 */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /** 获取 公司名 */
    public String getCompanyName() {
        return this.companyName;
    }

    /** 设置 公司名 */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /** 获取 公司id */
    public Integer getCompanyId() {
        return this.companyId;
    }

    /** 设置 公司id */
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    /** 获取 实做状态  0：待申请 1：待反馈 2：已完成 */
    public Integer getRealDoStatus() {
        if (null != realDoStatusStr) {
            Set<Integer> statusList = ClassConvert.strToIntegerSetGather(realDoStatusStr.split(","));
            for (Integer integer : statusList) {
                if (integer == 0) {
                    return 0;
                } else if(integer == 1) {
                    return 1;
                }  else {
                    return 2;
                }
            }
        }
        return this.realDoStatus;
    }

    /** 设置 实做状态  0：待申请 1：待反馈 2：已完成 */
    public void setRealDoStatus(Integer realDoStatus) {
        this.realDoStatus = realDoStatus;
    }

    public String getRealDoStatusStr() {
        return this.realDoStatusStr;
    }

    public void setRealDoStatusStr(String realDoStatusStr) {
        this.realDoStatusStr = realDoStatusStr;
    }

    /** 获取 稽核状态 */
    public Integer getAuditStatus() {
        if (null != isUploadKaoPanStr) {
            /*Set<Integer> statusList = ClassConvert.strToIntegerSetGather(isUploadKaoPanStr.split(","));
            for (Integer integer : statusList) {
                if (integer == 0) {
                    return 0;
                }
            }*/
            if (null != auditStatusStr) {
                Set<Integer> statusList2 = ClassConvert.strToIntegerSetGather(auditStatusStr.split(","));
                for (Integer integer : statusList2) {
                    if (integer == 0) {
                        return 1;
                    }
                }
                return 2;
            }

        }
        return this.auditStatus;
    }

    /** 设置 稽核状态 */
    public void setAuditStatus(Integer auditStatus) {

        this.auditStatus = auditStatus;
    }

    public String getAuditStatusStr() {
        return this.auditStatusStr;
    }

    public void setAuditStatusStr(String auditStatusStr) {
        this.auditStatusStr = auditStatusStr;
    }

    /** 获取 拷盘状态 */
    public String getIsUploadKaoPanStr() {
        return this.isUploadKaoPanStr;
    }

    /** 设置 拷盘状态 */
    public void setIsUploadKaoPanStr(String isUploadKaoPanStr) {
        this.isUploadKaoPanStr = isUploadKaoPanStr;
    }

    /** 获取 核销状态 0：未核销  1：已核销 */
    public Integer getCancelAfterVerificationStatus() {
        if (null != cancelAfterVerificationStatusStr) {
            Set<Integer> statusList = ClassConvert.strToIntegerSetGather(cancelAfterVerificationStatusStr.split(","));
            for (Integer integer : statusList) {
                if (integer == 0) {
                   return 0;
                }
            }
            return 1;

        }
        return this.cancelAfterVerificationStatus;
    }

    /** 设置 核销状态 0：未核销  1：已核销 */
    public void setCancelAfterVerificationStatus(Integer cancelAfterVerificationStatus) {
        this.cancelAfterVerificationStatus = cancelAfterVerificationStatus;
    }

    public String getCancelAfterVerificationStatusStr() {
        return this.cancelAfterVerificationStatusStr;
    }

    public void setCancelAfterVerificationStatusStr(String cancelAfterVerificationStatusStr) {
        this.cancelAfterVerificationStatusStr = cancelAfterVerificationStatusStr;
    }

    /** 获取 票据是否到件 0：未到件 1：已收件*/
    public Integer getIsConsignee() {
        if (null != isConsigneeStr) {
            Set<Integer> statusList = ClassConvert.strToIntegerSetGather(isConsigneeStr.split(","));
            for (Integer integer : statusList) {
                if (integer == 0) {
                    return 0;
                }
            }
            return 1;
        }
        return this.isConsignee;
    }

    /** 设置 票据是否到件 0：未到件 1：已收件*/
    public void setIsConsignee(Integer isConsignee) {
        this.isConsignee = isConsignee;
    }

    public String getIsConsigneeStr() {
        return this.isConsigneeStr;
    }

    public void setIsConsigneeStr(String isConsigneeStr) {
        this.isConsigneeStr = isConsigneeStr;
    }

    /** 获取 服务费类型及配置json */
    public String getCompanyCooperationMethodJson() {
        return this.companyCooperationMethodJson;
    }

    /** 设置 服务费类型及配置json */
    public void setCompanyCooperationMethodJson(String companyCooperationMethodJson) {
        this.companyCooperationMethodJson = companyCooperationMethodJson;
    }

    /** 获取 服务费类型及配置json 上月 */
    public String getCompanyCooperationMethodJson2() {
        return this.companyCooperationMethodJson2;
    }

    /** 设置 服务费类型及配置json 上月 */
    public void setCompanyCooperationMethodJson2(String companyCooperationMethodJson2) {
        this.companyCooperationMethodJson2 = companyCooperationMethodJson2;
    }

    /** 获取 上月余额 */
    public Double getLastMonthBalance() {
        return this.lastMonthBalance;
    }

    /** 设置 上月余额 */
    public void setLastMonthBalance(Double lastMonthBalance) {
        this.lastMonthBalance = lastMonthBalance;
    }

    /** 获取 商业险金额 */
    public Double getInsurancePrice() {
        return this.insurancePrice;
    }

    /** 设置 商业险金额 */
    public void setInsurancePrice(Double insurancePrice) {
        this.insurancePrice = insurancePrice;
    }

    /** 获取 一次性业务金额 */
    public Double getYcPrice() {
        return this.ycPrice;
    }

    /** 设置 一次性业务金额 */
    public void setYcPrice(Double ycPrice) {
        this.ycPrice = ycPrice;
    }

    /** 获取 税费 */
    public Double getTaxPrice() {
        if (null == taxPrice) {
            return 0.0;
        }
        return this.taxPrice;
    }

    /** 设置 税费 */
    public void setTaxPrice(Double taxPrice) {
        this.taxPrice = taxPrice;
    }
}
