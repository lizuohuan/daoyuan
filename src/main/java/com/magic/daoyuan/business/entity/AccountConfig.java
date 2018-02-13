package com.magic.daoyuan.business.entity;

import java.io.Serializable;

/**
 * Created by Eric Xie on 2017/10/20 0020.
 */
public class AccountConfig extends BaseEntity implements Serializable {

    /** 账户名 */
    private String accountName;

    /** 银行帐号 */
    private String bankAccount;

    /** 开户行 */
    private String bankName;

    /** 支付宝帐号 */
    private String aliPayAccount;

    /** 支付宝名字 */
    private String aliPayName;


    public String getAccountName() {
        return this.accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getBankAccount() {
        return this.bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getBankName() {
        return this.bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAliPayAccount() {
        return this.aliPayAccount;
    }

    public void setAliPayAccount(String aliPayAccount) {
        this.aliPayAccount = aliPayAccount;
    }

    public String getAliPayName() {
        return this.aliPayName;
    }

    public void setAliPayName(String aliPayName) {
        this.aliPayName = aliPayName;
    }
}
