package com.magic.daoyuan.business.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 汇总账单
 * @author lzh
 * @create 2017/10/23 19:18
 */
public class CompanySonTotalBill implements Serializable {


    private Integer id;

    /** 账单制作人id */
    private Integer userId;

    /** 稽核余额汇总账单下 每月稽核叠加 */
    private Double monthBalance;

    /** 子账单id */
    private Integer companySonBillId;

    /** 创建时间  */
    private Date createTime;

    /** 状态 0：已提交  1：仍需调整  2：确认账单 */
    private Integer status;

    /** 账单月 */
    private Date billMonth;

    /** 是否是账单生成月的 汇总账单 0：否 1：是 */
    private Integer isCreateBillMonth;

    /** 公司id */
    private Integer companyId;

    /** 当前总账单的核销差额 */
    private Double balanceOfCancelAfterVerification;

    /** 当前总账单 是否核销  0:未核销  1：已经核销 */
    private Integer isBalanceOfCancelAfterVerification;

    /** 总账单号 */
    private String billNumber;

    /** 总账单号 */
    private String itemBillNumber;

    /** 账单确认时间 */
    private Date okTime;

    /** 核销时间 */
    private Date afterVerificationTime;

    /** 票据是否到件 0：未到件 1：已收件 */
    private Integer isConsignee;

    /** 上月余额 */
    private Double lastMonthBalance;

    /********  业务字段 不进行数据库持久化    *******/

    /** 公司名 */
    private String companyName;

    /** 子账单名 */
    private String sonBillName;

    /** 总金额(实收金额) */
    private Double totalPrice;

    /** 应收金额 */
    private Double receivablePrice;

    /** 稽核差额 */
    private Double auditTheDifference;

    /** 联系人 */
    private Contacts contacts;

    /** 客服名称 */
    private String serviceName;

    private String servicePhone;

    /** 到款日 */
    private Date payTime;

    /** 公司子账单子类集合 */
    private List<CompanySonBillItem> companySonBillItemList;

    /** 服务费集合 */
    private List<MonthServiceFee> monthServiceFeeList;

    /** 此张子账单包含了几个月业务 */
    private Set<Date> dateSet;

    /** 公司客服 */
    private Integer userId2;


    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /** 获取 占点制作人id */
    public Integer getUserId() {
        return this.userId;
    }

    /** 设置 占点制作人id */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /** 获取 稽核余额汇总账单下 每月稽核叠加 */
    public Double getMonthBalance() {
        return this.monthBalance;
    }

    /** 设置 稽核余额汇总账单下 每月稽核叠加 */
    public void setMonthBalance(Double monthBalance) {
        this.monthBalance = monthBalance;
    }

    /** 获取 子账单id */
    public Integer getCompanySonBillId() {
        return this.companySonBillId;
    }

    /** 设置 子账单id */
    public void setCompanySonBillId(Integer companySonBillId) {
        this.companySonBillId = companySonBillId;
    }

    /** 获取 公司名 */
    public String getCompanyName() {
        return this.companyName;
    }

    /** 设置 公司名 */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /** 获取 子账单名 */
    public String getSonBillName() {
        return this.sonBillName;
    }

    /** 设置 子账单名 */
    public void setSonBillName(String sonBillName) {
        this.sonBillName = sonBillName;
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

    /** 获取 稽核差额 */
    public Double getAuditTheDifference() {
        return this.auditTheDifference;
    }

    /** 设置 稽核差额 */
    public void setAuditTheDifference(Double auditTheDifference) {
        this.auditTheDifference = auditTheDifference;
    }

    /** 获取 联系人 */
    public Contacts getContacts() {
        return this.contacts;
    }

    /** 设置 联系人 */
    public void setContacts(Contacts contacts) {
        this.contacts = contacts;
    }

    /** 获取 账单月 */
    public Date getBillMonth() {
        return this.billMonth;
    }

    /** 设置 账单月 */
    public void setBillMonth(Date billMonth) {
        this.billMonth = billMonth;
    }

    /** 获取 公司子账单子类集合 */
    public List<CompanySonBillItem> getCompanySonBillItemList() {
        return this.companySonBillItemList;
    }

    /** 设置 公司子账单子类集合 */
    public void setCompanySonBillItemList(List<CompanySonBillItem> companySonBillItemList) {
        this.companySonBillItemList = companySonBillItemList;
    }

    /** 获取 是否是账单生成月的 汇总账单 0：否 1：是 */
    public Integer getIsCreateBillMonth() {
        return this.isCreateBillMonth;
    }

    /** 设置 是否是账单生成月的 汇总账单 0：否 1：是 */
    public void setIsCreateBillMonth(Integer isCreateBillMonth) {
        this.isCreateBillMonth = isCreateBillMonth;
    }

    /** 获取 服务费集合 */
    public List<MonthServiceFee> getMonthServiceFeeList() {
        return this.monthServiceFeeList;
    }

    /** 设置 服务费集合 */
    public void setMonthServiceFeeList(List<MonthServiceFee> monthServiceFeeList) {
        this.monthServiceFeeList = monthServiceFeeList;
    }

    /** 获取 公司id */
    public Integer getCompanyId() {
        return this.companyId;
    }

    /** 设置 公司id */
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    /** 获取 此张子账单包含了几个月业务 */
    public Set<Date> getDateSet() {
        return this.dateSet;
    }

    /** 设置 此张子账单包含了几个月业务 */
    public void setDateSet(Set<Date> dateSet) {
        this.dateSet = dateSet;
    }

    /** 获取 当前总账单 是否核销  0:未核销  1：已经核销 */
    public Integer getIsBalanceOfCancelAfterVerification() {
        return this.isBalanceOfCancelAfterVerification;
    }

    /** 设置 当前总账单 是否核销  0:未核销  1：已经核销 */
    public void setIsBalanceOfCancelAfterVerification(Integer isBalanceOfCancelAfterVerification) {
        this.isBalanceOfCancelAfterVerification = isBalanceOfCancelAfterVerification;
    }

    /** 获取 总账单号 */
    public String getBillNumber() {
        return this.billNumber;
    }

    /** 设置 总账单号 */
    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    /** 获取 总账单号 */
    public String getItemBillNumber() {
        return this.itemBillNumber;
    }

    /** 设置 总账单号 */
    public void setItemBillNumber(String itemBillNumber) {
        this.itemBillNumber = itemBillNumber;
    }

    /** 获取 公司客服 */
    public Integer getUserId2() {
        return this.userId2;
    }

    /** 设置 公司客服 */
    public void setUserId2(Integer userId2) {
        this.userId2 = userId2;
    }

    /** 获取 账单确认时间 */
    public Date getOkTime() {
        return this.okTime;
    }

    /** 设置 账单确认时间 */
    public void setOkTime(Date okTime) {
        this.okTime = okTime;
    }

    /** 获取 核销时间 */
    public Date getAfterVerificationTime() {
        return this.afterVerificationTime;
    }

    /** 设置 核销时间 */
    public void setAfterVerificationTime(Date afterVerificationTime) {
        this.afterVerificationTime = afterVerificationTime;
    }

    /** 获取 票据是否到件 0：未到件 1：已收件 */
    public Integer getIsConsignee() {
        return this.isConsignee;
    }

    /** 设置 票据是否到件 0：未到件 1：已收件 */
    public void setIsConsignee(Integer isConsignee) {
        this.isConsignee = isConsignee;
    }

    /** 获取 上月余额 */
    public Double getLastMonthBalance() {
        return this.lastMonthBalance;
    }

    /** 设置 上月余额 */
    public void setLastMonthBalance(Double lastMonthBalance) {
        this.lastMonthBalance = lastMonthBalance;
    }

    /** 获取 客户名称 */
    public String getServiceName() {
        return this.serviceName;
    }

    /** 设置 客户名称 */
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServicePhone() {
        return this.servicePhone;
    }

    public void setServicePhone(String servicePhone) {
        this.servicePhone = servicePhone;
    }

    /** 获取 到款日 */
    public Date getPayTime() {
        return this.payTime;
    }

    /** 设置 到款日 */
    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }
}
