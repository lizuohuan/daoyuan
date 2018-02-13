package com.magic.daoyuan.business.dto;

import com.magic.daoyuan.business.entity.ConfirmFund;
import com.magic.daoyuan.business.util.ClassConvert;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 账单汇总
 * @author lzh
 * @create 2017/12/5 9:25
 */
public class CompanySonTotalBillDto implements Serializable {

    /** 公司id */
    private Integer companyId;

    /** 公司名 */
    private String companyName;

    /** 账单年月 */
    private Date billMonth;

    /** 创建时间  */
    private Date createTime;

    /** 状态 0：已提交  1：部分确认  2：全部确认 */
    private Integer status;
    private String statusStr;

    /** 总金额(实收金额) */
    private Double totalPrice;

    /** 应收金额 */
    private Double receivablePrice;

    /** 核销差额 */
    private Double balanceOfCancelAfterVerification;

    /** 核销状态 0：未完成  1：部分完成  2：全部完成 */
    private Integer cancelAfterVerificationStatus;
    private String cancelAfterVerificationStatusStr;

    /** 稽核差额 */
    private Double auditTheDifference;

    /** 稽核状态 0：未完成  1：部分完成  2：全部完成 */
    private Integer auditStatus;
    private String auditStatusStr;

    /** 服务费 */
    private Double serviceFee;

    /** 商业险金额 */
    private Double insurancePrice;

    /** 一次性业务金额 */
    private Double ycPrice;

    /** 工资 */
    private Double salaryInfoPrice;
    /** 认款记录，一个公司最多只有一条 */
    private ConfirmFund confirmFund;

    /** 纳入次月社保预收的金额 */
    private Double ssiReceivablePrice;

    /** 纳入次月社保实缴的金额 */
    private Double ssiTotalPrice;

    /** 纳入次月公积金预收的金额 */
    private Double rfiReceivablePrice;

    /** 纳入次月公积金实缴的金额 */
    private Double rfiTotalPrice;

    /** 账单确认的最新时间 */
    private Date okTime;

    /** 账单核销的最新时间 */
    private Date afterVerificationTime;

    /** 上月余额 */
    private Double lastMonthBalance;

    /** 客服 */
    private String userName;

    /** 服务费类型及配置json */
    private String companyCooperationMethodJson;

    /** 服务费类型及配置json 上月 */
    private String companyCooperationMethodJson2;

    /** 税费 */
    private Double taxPrice;

    /** 汇总账单id */
    private Integer companySonTotalBillId;

    /** 公司包含的业务 以逗号隔开 */
    private String companyBusinessStr;

    /** 获取 公司id */
    public Integer getCompanyId() {
        return this.companyId;
    }

    /** 设置 公司id */
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    /** 获取 公司名 */
    public String getCompanyName() {
        return this.companyName;
    }

    /** 设置 公司名 */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /** 获取 账单年月 */
    public Date getBillMonth() {
        return this.billMonth;
    }

    /** 设置 账单年月 */
    public void setBillMonth(Date billMonth) {
        this.billMonth = billMonth;
    }

    /** 获取 创建时间  */
    public Date getCreateTime() {
        return this.createTime;
    }

    /** 设置 创建时间  */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /** 获取 状态 0：已提交  1：部分确认  2：全部确认 */
    public Integer getStatus() {
        //已提交
        Integer TNum = 0;
        //已确认
        Integer QNum = 0;
        if (null != statusStr) {
            Set<Integer> statusList = ClassConvert.strToIntegerSetGather(statusStr.split(","));
            for (Integer integer : statusList) {
                if (integer == 0) {
                    TNum ++;
                } else {
                    QNum ++;
                }
            }
            if (TNum > 0 && QNum == 0) {
                return 0;
            } else if (TNum > 0 && QNum > 0) {
                return 1;
            } else if (TNum == 0 && QNum > 0) {
                return 2;
            }
        }
        return this.status;
    }

    /** 设置 状态 0：已提交  1：仍需调整  2：确认账单 */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /** 获取 总金额(实收金额) */
    public Double getTotalPrice() {
        return this.totalPrice;
    }

    /** 设置 总金额(实收金额) */
    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    /** 获取 应收金额 */
    public Double getReceivablePrice() {
        return this.receivablePrice;
    }

    /** 设置 应收金额 */
    public void setReceivablePrice(Double receivablePrice) {
        this.receivablePrice = receivablePrice;
    }

    /** 获取 核销差额 */
    public Double getBalanceOfCancelAfterVerification() {
        return this.balanceOfCancelAfterVerification;
    }

    /** 设置 核销差额 */
    public void setBalanceOfCancelAfterVerification(Double balanceOfCancelAfterVerification) {
        this.balanceOfCancelAfterVerification = balanceOfCancelAfterVerification;
    }

    /** 获取 核销状态 0：未完成  1：部分完成  2：全部完成 */
    public Integer getCancelAfterVerificationStatus() {
        //已核銷
        Integer YHXNum = 0;
        //未核銷
        Integer WHXNum = 0;
        if (null != cancelAfterVerificationStatusStr) {
            Set<Integer> statusList = ClassConvert.strToIntegerSetGather(cancelAfterVerificationStatusStr.split(","));
            for (Integer integer : statusList) {
                if (integer == 0) {
                    WHXNum ++;
                } else {
                    YHXNum ++;
                }
            }
            if (YHXNum > 0 && WHXNum > 0) {
                return 1;
            } else if (YHXNum == 0 && WHXNum > 0) {
                return 0;
            } else if (YHXNum > 0 && WHXNum == 0) {
                return 2;
            }
        }

        return this.cancelAfterVerificationStatus;
    }

    /** 设置 核销状态 0：未完成  1：部分完成  2：全部完成 */
    public void setCancelAfterVerificationStatus(Integer cancelAfterVerificationStatus) {
        this.cancelAfterVerificationStatus = cancelAfterVerificationStatus;
    }

    /** 获取 稽核差额 */
    public Double getAuditTheDifference() {
        return this.auditTheDifference;
    }

    /** 设置 稽核差额 */
    public void setAuditTheDifference(Double auditTheDifference) {
        this.auditTheDifference = auditTheDifference;
    }

    /** 获取 稽核状态 0：未完成  1：部分完成  2：全部完成 */
    public Integer getAuditStatus() {
        //已稽核
        Integer YJHNum = 0;
        //未稽核
        Integer WJHNum = 0;
        if (null != auditStatusStr) {
            Set<Integer> statusList = ClassConvert.strToIntegerSetGather(auditStatusStr.split(","));
            for (Integer integer : statusList) {
                if (integer == 0) {
                    WJHNum ++;
                } else {
                    YJHNum ++;
                }
            }
            if (WJHNum > 0 && YJHNum > 0) {
                return 1;
            } else if (YJHNum == 0 && WJHNum > 0) {
                return 0;
            } else if (YJHNum > 0 && WJHNum == 0) {
                return 2;
            }
        }

        return this.auditStatus;
    }

    /** 设置 稽核状态 0：未完成  1：部分完成  2：全部完成 */
    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getCancelAfterVerificationStatusStr() {
        return this.cancelAfterVerificationStatusStr;
    }

    public void setCancelAfterVerificationStatusStr(String cancelAfterVerificationStatusStr) {
        this.cancelAfterVerificationStatusStr = cancelAfterVerificationStatusStr;
    }

    public String getAuditStatusStr() {
        return this.auditStatusStr;
    }

    public void setAuditStatusStr(String auditStatusStr) {
        this.auditStatusStr = auditStatusStr;
    }

    /** 获取 服务费 */
    public Double getServiceFee() {
        if (null == serviceFee) {
            return 0.0;
        }
        return this.serviceFee;
    }

    /** 设置 服务费 */
    public void setServiceFee(Double serviceFee) {
        this.serviceFee = serviceFee;
    }

    public String getStatusStr() {
        return this.statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    /** 获取 认款记录，一个公司最多只有一条 */
    public ConfirmFund getConfirmFund() {
        return this.confirmFund;
    }

    /** 设置 认款记录，一个公司最多只有一条 */
    public void setConfirmFund(ConfirmFund confirmFund) {
        this.confirmFund = confirmFund;
    }

    /** 获取 工资 */
    public Double getSalaryInfoPrice() {
        return this.salaryInfoPrice;
    }

    /** 设置 工资 */
    public void setSalaryInfoPrice(Double salaryInfoPrice) {
        this.salaryInfoPrice = salaryInfoPrice;
    }

    /** 获取 纳入次月社保预收的金额 */
    public Double getSsiReceivablePrice() {
        if (null == ssiReceivablePrice) {
            return 0.0;
        }
        return this.ssiReceivablePrice;
    }

    /** 设置 纳入次月社保预收的金额 */
    public void setSsiReceivablePrice(Double ssiReceivablePrice) {
        this.ssiReceivablePrice = ssiReceivablePrice;
    }

    /** 获取 纳入次月社保实缴的金额 */
    public Double getSsiTotalPrice() {
        if (null == ssiTotalPrice) {
            return 0.0;
        }
        return this.ssiTotalPrice;
    }

    /** 设置 纳入次月社保实缴的金额 */
    public void setSsiTotalPrice(Double ssiTotalPrice) {
        this.ssiTotalPrice = ssiTotalPrice;
    }

    /** 获取 纳入次月公积金预收的金额 */
    public Double getRfiReceivablePrice() {
        if (null == rfiReceivablePrice) {
            return 0.0;
        }
        return this.rfiReceivablePrice;
    }

    /** 设置 纳入次月公积金预收的金额 */
    public void setRfiReceivablePrice(Double rfiReceivablePrice) {
        this.rfiReceivablePrice = rfiReceivablePrice;
    }

    /** 获取 纳入次月公积金实缴的金额 */
    public Double getRfiTotalPrice() {
        if (null == rfiTotalPrice) {
            return 0.0;
        }
        return this.rfiTotalPrice;
    }

    /** 设置 纳入次月公积金实缴的金额 */
    public void setRfiTotalPrice(Double rfiTotalPrice) {
        this.rfiTotalPrice = rfiTotalPrice;
    }

    /** 获取 是否可以取消账单 0：否 1：是 */
    public Integer getIsCanCancelBill() {
        Integer auditStatus2 = getAuditStatus();
        if (null == balanceOfCancelAfterVerification && auditStatus2 == 0) {
            return 1;
        } else {
            if (auditStatus2 != 0 && getIsHaveSSOrRef() == 0) {
                return 1;
            }
            return 0;
        }
    }

    /** 获取 账单确认的最新时间 */
    public Date getOkTime() {
        return this.okTime;
    }

    /** 设置 账单确认的最新时间 */
    public void setOkTime(Date okTime) {
        this.okTime = okTime;
    }

    /** 获取 账单核销的最新时间 */
    public Date getAfterVerificationTime() {
        return this.afterVerificationTime;
    }

    /** 设置 账单核销的最新时间 */
    public void setAfterVerificationTime(Date afterVerificationTime) {
        this.afterVerificationTime = afterVerificationTime;
    }


    /** 获取 客服 */
    public String getUserName() {
        return this.userName;
    }

    /** 设置 客服 */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /** 获取 上月余额 */
    public Double getLastMonthBalance() {
        if (null == lastMonthBalance) {
            return 0.0;
        }
        return this.lastMonthBalance;
    }

    /** 设置 上月余额 */
    public void setLastMonthBalance(Double lastMonthBalance) {
        this.lastMonthBalance = lastMonthBalance;
    }

    /** 获取 商业险金额 */
    public Double getInsurancePrice() {
        if (null == insurancePrice) {
            return 0.0;
        }
        return this.insurancePrice;
    }

    /** 设置 商业险金额 */
    public void setInsurancePrice(Double insurancePrice) {
        this.insurancePrice = insurancePrice;
    }

    /** 获取 一次性业务金额 */
    public Double getYcPrice() {
        if (null == ycPrice) {
            return 0.0;
        }
        return this.ycPrice;
    }

    /** 设置 一次性业务金额 */
    public void setYcPrice(Double ycPrice) {
        this.ycPrice = ycPrice;
    }

    /** 获取 服务费类型及配置json */
    public String getCompanyCooperationMethodJson() {
        return this.companyCooperationMethodJson;
    }

    /** 设置 服务费类型及配置json */
    public void setCompanyCooperationMethodJson(String companyCooperationMethodJson) {
        this.companyCooperationMethodJson = companyCooperationMethodJson;
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

    /** 获取 服务费类型及配置json 上月 */
    public String getCompanyCooperationMethodJson2() {
        return this.companyCooperationMethodJson2;
    }

    /** 设置 服务费类型及配置json 上月 */
    public void setCompanyCooperationMethodJson2(String companyCooperationMethodJson2) {
        this.companyCooperationMethodJson2 = companyCooperationMethodJson2;
    }

    /** 获取 汇总账单id */
    public Integer getCompanySonTotalBillId() {
        return this.companySonTotalBillId;
    }

    /** 设置 汇总账单id */
    public void setCompanySonTotalBillId(Integer companySonTotalBillId) {
        this.companySonTotalBillId = companySonTotalBillId;
    }

    /** 获取 公司包含的业务 以逗号隔开 */
    public String getCompanyBusinessStr() {
        return this.companyBusinessStr;
    }

    /** 设置 公司包含的业务 以逗号隔开 */
    public void setCompanyBusinessStr(String companyBusinessStr) {
        this.companyBusinessStr = companyBusinessStr;
    }

    /** 是否存在社保或者公积金  0：否  1：是 */
    public Integer getIsHaveSSOrRef() {
        if (null != companyBusinessStr) {
            if (companyBusinessStr.contains("4") || companyBusinessStr.contains("3")) {
                return 1;
            }
        }
        return 0;
    }
}
