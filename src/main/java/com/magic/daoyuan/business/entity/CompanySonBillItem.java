package com.magic.daoyuan.business.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 公司子账单子类
 *
 * @author lzh
 * @create 2017/10/13 9:35
 */
public class CompanySonBillItem implements Serializable {


    private Integer id;


    /** 汇总账单的id */
    private Integer companySonTotalBillId;

    /** 总金额(实收金额) */
    private Double totalPrice;

    /** 应收金额 */
    private Double receivablePrice;

    /** 核销差额 */
    private Double balanceOfCancelAfterVerification;

    /** 是否稽核 0：否 1：是 */
    private Integer isAudit;

    /** 稽核差额 */
    private Double auditTheDifference;

    /** 创建时间 */
    private Date createTime;

    /** 状态 0：已提交  1：仍需调整  2：确认账单 */
    private Integer status;

    /** 是否有效 0：否  1：是 */
    private Integer isValid;

    /** 服务月 */
    private Date serviceMonth;

    /** 是否是合并后的账单 0：否 1：是 */
    private Integer isTotalBill;

    /** 社保是否稽核 0:否 1:是 */
    private Integer isSocialSecurityAudit;

    /** 公积金是否稽核 0:否 1:是 */
    private Integer isReservedFundAudit;

    /** 社保稽核差额 社保 */
    private Double socialSecurityPracticalPrice;

    /** 公积金稽核差额 公积金 */
    private Double reservedFundPracticalPrice;

    /** 子账单id */
    private Integer companySonBillId;


    /** 公积金实收金额 */
    private Double rfPaidInPrice;

    /** 社保实收金额 */
    private Double ssPaidInPrice;

    /** 社保是否上传拷盘 0：否  1：是 */
    private Integer isUploadKaoPanS;

    /** 公积金是否上传拷盘 0：否  1：是 */
    private Integer isUploadKaoPanR;

    /*************  业务字段 不进行数据库持久化  ***************/

    /** 汇总账单 */
    private CompanySonTotalBill companySonTotalBill;

    /** 此月账单下缴纳的社保明细集合 */
    private List<SocialSecurityInfo> socialSecurityInfos;

    /** 此月账单下缴纳的公积金明细集合 */
    private List<ReservedFundsInfo> reservedFundsInfoList;

    /** 公司id */
    private Integer companyId;

    /** 账单月 */
    private Date billMonth;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /** 获取 汇总账单的id */
    public Integer getCompanySonTotalBillId() {
        return this.companySonTotalBillId;
    }

    /** 设置 汇总账单的id */
    public void setCompanySonTotalBillId(Integer companySonTotalBillId) {
        this.companySonTotalBillId = companySonTotalBillId;
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

    /** 获取 是否稽核 0：否 1：是 */
    public Integer getIsAudit() {
        return this.isAudit;
    }

    /** 设置 是否稽核 0：否 1：是 */
    public void setIsAudit(Integer isAudit) {
        this.isAudit = isAudit;
    }

    /** 获取 稽核差额 */
    public Double getAuditTheDifference() {
        return this.auditTheDifference;
    }

    /** 设置 稽核差额 */
    public void setAuditTheDifference(Double auditTheDifference) {
        this.auditTheDifference = auditTheDifference;
    }

    /** 获取 创建时间 */
    public Date getCreateTime() {
        return this.createTime;
    }

    /** 设置 创建时间 */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /** 获取 状态 0：已提交  1：仍需调整  2：确认账单 */
    public Integer getStatus() {
        return this.status;
    }

    /** 设置 状态 0：已提交  1：仍需调整  2：确认账单 */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /** 获取 是否有效 0：否  1：是 */
    public Integer getIsValid() {
        return this.isValid;
    }

    /** 设置 是否有效 0：否  1：是 */
    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    /** 获取 服务月 */
    public Date getServiceMonth() {
        return this.serviceMonth;
    }

    /** 设置 服务月 */
    public void setServiceMonth(Date serviceMonth) {
        this.serviceMonth = serviceMonth;
    }

    /** 获取 是否是合并后的账单 0：否 1：是 */
    public Integer getIsTotalBill() {
        return this.isTotalBill;
    }

    /** 设置 是否是合并后的账单 0：否 1：是 */
    public void setIsTotalBill(Integer isTotalBill) {
        this.isTotalBill = isTotalBill;
    }

    /** 获取 汇总账单 */
    public CompanySonTotalBill getCompanySonTotalBill() {
        return this.companySonTotalBill;
    }

    /** 设置 汇总账单 */
    public void setCompanySonTotalBill(CompanySonTotalBill companySonTotalBill) {
        this.companySonTotalBill = companySonTotalBill;
    }

    /** 获取 此月账单下缴纳的社保明细集合 */
    public List<SocialSecurityInfo> getSocialSecurityInfos() {
        return this.socialSecurityInfos;
    }

    /** 设置 此月账单下缴纳的社保明细集合 */
    public void setSocialSecurityInfos(List<SocialSecurityInfo> socialSecurityInfos) {
        this.socialSecurityInfos = socialSecurityInfos;
    }

    /** 获取 社保是否稽核 0:否 1:是 */
    public Integer getIsSocialSecurityAudit() {
        return this.isSocialSecurityAudit;
    }

    /** 设置 社保是否稽核 0:否 1:是 */
    public void setIsSocialSecurityAudit(Integer isSocialSecurityAudit) {
        this.isSocialSecurityAudit = isSocialSecurityAudit;
    }

    /** 获取 公积金是否稽核 0:否 1:是 */
    public Integer getIsReservedFundAudit() {
        return this.isReservedFundAudit;
    }

    /** 设置 公积金是否稽核 0:否 1:是 */
    public void setIsReservedFundAudit(Integer isReservedFundAudit) {
        this.isReservedFundAudit = isReservedFundAudit;
    }

    /** 获取 社保是否稽核 社保 */
    public Double getSocialSecurityPracticalPrice() {
        if (null == socialSecurityPracticalPrice) {
            return 0.0;
        }
        return this.socialSecurityPracticalPrice;
    }

    /** 设置 社保是否稽核 社保 */
    public void setSocialSecurityPracticalPrice(Double socialSecurityPracticalPrice) {
        this.socialSecurityPracticalPrice = socialSecurityPracticalPrice;
    }

    /** 获取 公积金是否稽核 公积金 */
    public Double getReservedFundPracticalPrice() {
        if (null == reservedFundPracticalPrice) {
            return 0.0;
        }
        return this.reservedFundPracticalPrice;
    }

    /** 设置 公积金是否稽核 公积金 */
    public void setReservedFundPracticalPrice(Double reservedFundPracticalPrice) {
        this.reservedFundPracticalPrice = reservedFundPracticalPrice;
    }

    /** 获取 公司id */
    public Integer getCompanyId() {
        return this.companyId;
    }

    /** 设置 公司id */
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    /** 获取 子账单id */
    public Integer getCompanySonBillId() {
        return this.companySonBillId;
    }

    /** 设置 子账单id */
    public void setCompanySonBillId(Integer companySonBillId) {
        this.companySonBillId = companySonBillId;
    }

    /** 获取 此月账单下缴纳的公积金明细集合 */
    public List<ReservedFundsInfo> getReservedFundsInfoList() {
        return this.reservedFundsInfoList;
    }

    /** 设置 此月账单下缴纳的公积金明细集合 */
    public void setReservedFundsInfoList(List<ReservedFundsInfo> reservedFundsInfoList) {
        this.reservedFundsInfoList = reservedFundsInfoList;
    }

    /** 获取 账单月 */
    public Date getBillMonth() {
        return this.billMonth;
    }

    /** 设置 账单月 */
    public void setBillMonth(Date billMonth) {
        this.billMonth = billMonth;
    }


    /** 获取 公积金实收金额 */
    public Double getRfPaidInPrice() {
        if (null == rfPaidInPrice) {
            return 0.0;
        }
        return this.rfPaidInPrice;
    }

    /** 设置 公积金实收金额 */
    public void setRfPaidInPrice(Double rfPaidInPrice) {
        this.rfPaidInPrice = rfPaidInPrice;
    }

    /** 获取 社保实收金额 */
    public Double getSsPaidInPrice() {
        if (null == ssPaidInPrice) {
            return 0.00;
        }
        return this.ssPaidInPrice;
    }

    /** 设置 社保实收金额 */
    public void setSsPaidInPrice(Double ssPaidInPrice) {
        this.ssPaidInPrice = ssPaidInPrice;
    }

    /** 获取 社保是否上传拷盘 0：否  1：是 */
    public Integer getIsUploadKaoPanS() {
        return this.isUploadKaoPanS;
    }

    /** 设置 社保是否上传拷盘 0：否  1：是 */
    public void setIsUploadKaoPanS(Integer isUploadKaoPanS) {
        this.isUploadKaoPanS = isUploadKaoPanS;
    }

    /** 获取 公积金是否上传拷盘 0：否  1：是 */
    public Integer getIsUploadKaoPanR() {
        return this.isUploadKaoPanR;
    }

    /** 设置 公积金是否上传拷盘 0：否  1：是 */
    public void setIsUploadKaoPanR(Integer isUploadKaoPanR) {
        this.isUploadKaoPanR = isUploadKaoPanR;
    }
}
