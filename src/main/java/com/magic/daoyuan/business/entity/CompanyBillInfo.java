package com.magic.daoyuan.business.entity;

import java.io.Serializable;

/**
 * 客户/公司 票据信息
 * Created by Eric Xie on 2017/9/13 0013.
 */
public class CompanyBillInfo implements Serializable,Cloneable {

    /** 主键ID */
    private Integer id;


    /** 票据名称 */
    private String name;

    /** 发票抬头 */
    private String title;

    /** 税号 */
    private String taxNumber;

    /** 地址 */
    private String address;

    /** 电话 */
    private String phone;

    /** 开户行 */
    private String accountName;

    /** 银行帐号 */
    private String bankAccount;

    /** 所属公司 */
    private Integer companyId;

    /** 所属公司名称 */
    private String companyName;

    /** 是否启用 默认为是，关联账单管理的子账单设置 */
    private Integer isEnabled;

    /** 票据类型 0：专票 1：普票  2：收据 */
    private Integer billType;

    /** 是否先票 后款  0 否  1 是 */
    private Integer isFirstBill;

    /** 服务费与业务费分别开票 0 : 否  1 ：是 */
    private Integer isPartBill;

    /** 是否有效 0 无效 1 有效 */
    private Integer isValid;

    /** 是否是复制的数据(在开票的时候) 0 不是  1 是 */
    private Integer isCopy;

    /** 复制的时候，使用的ID */
    private Integer relevanceId;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompanyBillInfo billInfo = (CompanyBillInfo) o;

        return id.equals(billInfo.id);

    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return (CompanyBillInfo)super.clone();
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIsFirstBill() {
        return isFirstBill;
    }

    public CompanyBillInfo setIsFirstBill(Integer isFirstBill) {
        this.isFirstBill = isFirstBill;
        return this;
    }

    public Integer getBillType() {
        return billType;
    }

    public CompanyBillInfo setBillType(Integer billType) {
        this.billType = billType;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public CompanyBillInfo setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public CompanyBillInfo setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public CompanyBillInfo setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public CompanyBillInfo setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getAccountName() {
        return accountName;
    }

    public CompanyBillInfo setAccountName(String accountName) {
        this.accountName = accountName;
        return this;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public CompanyBillInfo setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
        return this;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public CompanyBillInfo setCompanyId(Integer companyId) {
        this.companyId = companyId;
        return this;
    }

    public String getCompanyName() {
        return companyName;
    }

    public CompanyBillInfo setCompanyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public Integer getIsEnabled() {
        return isEnabled;
    }

    public CompanyBillInfo setIsEnabled(Integer isEnabled) {
        this.isEnabled = isEnabled;
        return this;
    }

    /** 获取 服务费与业务费分别开票 0 : 否  1 ：是 */
    public Integer getIsPartBill() {
        return this.isPartBill;
    }

    /** 设置 服务费与业务费分别开票 0 : 否  1 ：是 */
    public void setIsPartBill(Integer isPartBill) {
        this.isPartBill = isPartBill;
    }

    /** 获取 主键ID */
    public Integer getId() {
        return this.id;
    }

    /** 设置 主键ID */
    public void setId(Integer id) {
        this.id = id;
    }

    /** 获取 是否有效 0 无效 1 有效 */
    public Integer getIsValid() {
        return this.isValid;
    }

    /** 设置 是否有效 0 无效 1 有效 */
    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    /** 获取 是否是复制的数据(在开票的时候) 0 不是  1 是 */
    public Integer getIsCopy() {
        return this.isCopy;
    }

    /** 设置 是否是复制的数据(在开票的时候) 0 不是  1 是 */
    public void setIsCopy(Integer isCopy) {
        this.isCopy = isCopy;
    }

    /** 获取 复制的时候，使用的ID */
    public Integer getRelevanceId() {
        return this.relevanceId;
    }

    /** 设置 复制的时候，使用的ID */
    public void setRelevanceId(Integer relevanceId) {
        this.relevanceId = relevanceId;
    }
}
