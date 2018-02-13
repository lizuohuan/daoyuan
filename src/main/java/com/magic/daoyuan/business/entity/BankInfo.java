package com.magic.daoyuan.business.entity;

import java.io.Serializable;

/**
 * 公司银行信息 账户管理
 * Created by Eric Xie on 2017/10/27 0027.
 */
public class BankInfo extends BaseEntity implements Serializable {

    /** 开户名 */
    private String accountName;


    /** 银行名字 */
    private String bankName;

    /** 银行帐号 */
    private String bankAccount;

    /** 所属公司ID */
    private Integer companyId;

    /** 公司名称 */
    private String companyName;

    /** 业务员ID 导入时候 使用 */
    private Integer serviceId;

    /** 类型 0 ： 银行  1：支付宝 */
    private Integer type;

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

    public Integer getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    /** 获取 公司名称 */
    public String getCompanyName() {
        return this.companyName;
    }

    /** 设置 公司名称 */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /** 获取 业务员ID 导入时候 使用 */
    public Integer getServiceId() {
        return this.serviceId;
    }

    /** 设置 业务员ID 导入时候 使用 */
    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    /** 获取 类型 0 ： 银行  1：支付宝 */
    public Integer getType() {
        return this.type;
    }

    /** 设置 类型 0 ： 银行  1：支付宝 */
    public void setType(Integer type) {
        this.type = type;
    }
}
