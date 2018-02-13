package com.magic.daoyuan.business.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 导入的到款信息 entity
 * Created by Eric Xie on 2017/10/27 0027.
 */
public class ConfirmMoneyRecord extends BaseEntity implements Serializable {


    /** 对方银行帐号 */
    private String bankAccount;

    /** 交易时间 */
    private Date transactionTime;

    /** 到款金额 */
    private Double amount;

    /** 对方单位名称 */
    private String companyName;

    /** 摘要 */
    private String digest;

    /** 认款方式 0:自动认款  1:手动认款 */
    private Integer confirmMethod;

    private Date createTime;

    /** 备注 */
    private String remark;

    private Integer createUserId;

    /** 对应的公司列表 */
    private List<ConfirmMoneyCompany> confirmMoneyCompanyList;

    /** 认款记录集合 */
    private List<ConfirmFund> confirmFundList;

    /** 认款状态 0:未认款 1:已认款 */
    private Integer status;

    /** 数据来源 0:工行、1:成都银行、2:华夏银行、3:支付宝 */
    private Integer resource;

    /** 获取 对方银行帐号 */
    public String getBankAccount() {
        return this.bankAccount;
    }

    /** 设置 对方银行帐号 */
    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    /** 获取 交易时间 */
    public Date getTransactionTime() {
        return this.transactionTime;
    }

    /** 设置 交易时间 */
    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
    }

    /** 获取 到款金额 */
    public Double getAmount() {
        return this.amount;
    }

    /** 设置 到款金额 */
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    /** 获取 对方单位名称 */
    public String getCompanyName() {
        return this.companyName;
    }

    /** 设置 对方单位名称 */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /** 获取 摘要 */
    public String getDigest() {
        return this.digest;
    }

    /** 设置 摘要 */
    public void setDigest(String digest) {
        this.digest = digest;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /** 获取 备注 */
    public String getRemark() {
        return this.remark;
    }

    /** 设置 备注 */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getCreateUserId() {
        return this.createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    /** 获取 对应的公司列表 */
    public List<ConfirmMoneyCompany> getConfirmMoneyCompanyList() {
        return this.confirmMoneyCompanyList;
    }

    /** 设置 对应的公司列表 */
    public void setConfirmMoneyCompanyList(List<ConfirmMoneyCompany> confirmMoneyCompanyList) {
        this.confirmMoneyCompanyList = confirmMoneyCompanyList;
    }

    /** 获取 认款状态 0:未认款 1:已认款 */
    public Integer getStatus() {
        return this.status;
    }

    /** 设置 认款状态 0:未认款 1:已认款 */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /** 获取 认款记录集合 */
    public List<ConfirmFund> getConfirmFundList() {
        return this.confirmFundList;
    }

    /** 设置 认款记录集合 */
    public void setConfirmFundList(List<ConfirmFund> confirmFundList) {
        this.confirmFundList = confirmFundList;
    }

    /** 获取 数据来源 0:工行、1:成都银行、2:华夏银行、3:支付宝 */
    public Integer getResource() {
        return this.resource;
    }

    /** 设置 数据来源 0:工行、1:成都银行、2:华夏银行、3:支付宝 */
    public void setResource(Integer resource) {
        this.resource = resource;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConfirmMoneyRecord record = (ConfirmMoneyRecord) o;

        if (!bankAccount.equals(record.bankAccount)) return false;
        if (!transactionTime.equals(record.transactionTime)) return false;
        if (!amount.equals(record.amount)) return false;
        if (!companyName.equals(record.companyName)) return false;
        return digest.equals(record.digest);

    }

    @Override
    public int hashCode() {
        int result = bankAccount.hashCode();
        result = 31 * result + transactionTime.hashCode();
        result = 31 * result + amount.hashCode();
        result = 31 * result + companyName.hashCode();
        result = 31 * result + digest.hashCode();
        return result;
    }

    /** 获取 认款方式 0:自动认款  1:手动认款 */
    public Integer getConfirmMethod() {
        return this.confirmMethod;
    }

    /** 设置 认款方式 0:自动认款  1:手动认款 */
    public void setConfirmMethod(Integer confirmMethod) {
        this.confirmMethod = confirmMethod;
    }
}
