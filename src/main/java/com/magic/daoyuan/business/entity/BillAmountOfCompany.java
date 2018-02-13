package com.magic.daoyuan.business.entity;

import java.io.Serializable;

/**
 * 公司的账单金额  业务
 * Created by Eric Xie on 2017/10/31 0031.
 */
public class BillAmountOfCompany implements Serializable {

    /** 公司ID */
    private Integer companyId;

    /** 账单总金额 */
    private double billAmount;

    /** 总账单ID */
    private Integer companySonTotalBillId;


    public Integer getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public double getBillAmount() {
        return this.billAmount;
    }

    public void setBillAmount(double billAmount) {
        this.billAmount = billAmount;
    }

    /** 获取 总账单ID */
    public Integer getCompanySonTotalBillId() {
        return this.companySonTotalBillId;
    }

    /** 设置 总账单ID */
    public void setCompanySonTotalBillId(Integer companySonTotalBillId) {
        this.companySonTotalBillId = companySonTotalBillId;
    }
}
