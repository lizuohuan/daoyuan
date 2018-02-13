package com.magic.daoyuan.business.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 财务出款记录信息
 * Created by Eric Xie on 2017/11/17 0017.
 */
public class OutlayAmountRecord extends BaseEntity implements Serializable {

    /** 申请人 */
    private Integer userId;

    /** 收款信息ID 备用字段 */
    private Integer bankInfoId;

    /** 账户名 */
    private String accountName;

    /** 开户行 */
    private String bankName;

    /** 银行帐号 */
    private String bankAccount;

    /** 金额 */
    private Double amount;

    /** 是否加急 */
    private Integer isUrgent;

    /** 备注 */
    private String remark;

    /** 状态  2001：待审核、2002：待出款、2003：已退款、2004：已完成 2005:拒绝*/
    private Integer status;

    /** 自动认款 和 手动认款记录Id 如果为空则不是通过列表处理 */
    private Integer confirmFundId;

    private Date createTime;

    /**类型：0 = 供应商，1 = 非供应商**/
    private Integer type;

    /**供应商ID**/
    private Integer supplierId;

    /**收款信息ID**/
    private Integer supplierAccountToBeCreditedId;

    /** type2 = 1时使用 公司id */
    private Integer companyId;

    /** type2 = 1时使用 账单月 */
    private Date billMonth;

    /** 0：认款  1：稽核退回客户 */
    private Integer type2;

    /** 拒绝时填写的理由 */
    private String reason;


    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBankInfoId() {
        return this.bankInfoId;
    }

    public void setBankInfoId(Integer bankInfoId) {
        this.bankInfoId = bankInfoId;
    }

    public String getAccountName() {
        return this.accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getBankName() {
        return this.bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAccount() {
        return this.bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public Double getAmount() {
        return this.amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getIsUrgent() {
        return this.isUrgent;
    }

    public void setIsUrgent(Integer isUrgent) {
        this.isUrgent = isUrgent;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getConfirmFundId() {
        return this.confirmFundId;
    }

    public void setConfirmFundId(Integer confirmFundId) {
        this.confirmFundId = confirmFundId;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public Integer getSupplierAccountToBeCreditedId() {
        return supplierAccountToBeCreditedId;
    }

    public void setSupplierAccountToBeCreditedId(Integer supplierAccountToBeCreditedId) {
        this.supplierAccountToBeCreditedId = supplierAccountToBeCreditedId;
    }

    /** 获取 type2 = 1时使用 公司id */
    public Integer getCompanyId() {
        return this.companyId;
    }

    /** 设置 type2 = 1时使用 公司id */
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    /** 获取 type2 = 1时使用 账单月 */
    public Date getBillMonth() {
        return this.billMonth;
    }

    /** 设置 type2 = 1时使用 账单月 */
    public void setBillMonth(Date billMonth) {
        this.billMonth = billMonth;
    }

    /** 获取 0：认款  1：稽核退回客户 */
    public Integer getType2() {
        return this.type2;
    }

    /** 设置 0：认款  1：稽核退回客户 */
    public void setType2(Integer type2) {
        this.type2 = type2;
    }

    /** 获取 拒绝时填写的理由 */
    public String getReason() {
        return this.reason;
    }

    /** 设置 拒绝时填写的理由 */
    public void setReason(String reason) {
        this.reason = reason;
    }
}
