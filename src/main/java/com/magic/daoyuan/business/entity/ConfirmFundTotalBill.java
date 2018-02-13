package com.magic.daoyuan.business.entity;

import java.io.Serializable;

/**
 * Created by Eric Xie on 2017/10/31 0031.
 */
public class ConfirmFundTotalBill implements Serializable {

    private Integer id;

    /** 认款记录ID */
    private Integer confirmFundId;

    /** 认款的账单ID */
    private Integer companySonTotalBillId;

    /** 当前账单金额 */
    private Double amount;

    /** 是否被核销过 */
    private Integer status;

    /** 公司ID */
    private Integer companyId;


    public Integer getConfirmFundId() {
        return this.confirmFundId;
    }

    public void setConfirmFundId(Integer confirmFundId) {
        this.confirmFundId = confirmFundId;
    }

    public Integer getCompanySonTotalBillId() {
        return this.companySonTotalBillId;
    }

    public void setCompanySonTotalBillId(Integer companySonTotalBillId) {
        this.companySonTotalBillId = companySonTotalBillId;
    }

    /** 获取 当前账单金额 */
    public Double getAmount() {
        return this.amount;
    }

    /** 设置 当前账单金额 */
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /** 获取 是否被核销过 */
    public Integer getStatus() {
        return this.status;
    }

    /** 设置 是否被核销过 */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /** 获取 公司ID */
    public Integer getCompanyId() {
        return this.companyId;
    }

    /** 设置 公司ID */
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
}
