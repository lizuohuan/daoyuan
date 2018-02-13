package com.magic.daoyuan.business.entity;

import java.io.Serializable;

/**
 * 供应商收款账户
 * @author lzh
 * @create 2017/9/26 17:17
 */
public class SupplierAccountToBeCredited implements Serializable {


    private Integer id;

    /** 账户名 */
    private String accountName;

    /** 账号 */
    private String account;

    private Integer bankId;

    /** 开户行 type=0时不能为空 */
    private String bankName;

    /** 账号类型 0：银行卡  1：支付宝  */
    private Integer type;

    /** 供应商ID  */
    private Integer supplierId;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /** 获取 账户名 */
    public String getAccountName() {
        return this.accountName;
    }

    /** 设置 账户名 */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    /** 获取 账号 */
    public String getAccount() {
        return this.account;
    }

    /** 设置 账号 */
    public void setAccount(String account) {
        this.account = account;
    }

    /** 获取 开户行 */
    public String getBankName() {
        return this.bankName;
    }

    /** 设置 开户行 */
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    /** 获取 账号类型 0：银行卡  1：支付宝  */
    public Integer getType() {
        return this.type;
    }

    /** 设置 账号类型 0：银行卡  1：支付宝  */
    public void setType(Integer type) {
        this.type = type;
    }

    /** 获取 供应商ID  */
    public Integer getSupplierId() {
        return this.supplierId;
    }

    /** 设置 供应商ID  */
    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public Integer getBankId() {
        return this.bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }
}
