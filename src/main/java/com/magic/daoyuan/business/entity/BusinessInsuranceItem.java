package com.magic.daoyuan.business.entity;

import java.io.Serializable;

/**
 * Created by Eric Xie on 2017/10/25 0025.
 */
public class BusinessInsuranceItem extends BaseEntity implements Serializable {


    private Integer businessInsuranceId;

    private Integer businessItemId;

    private Double price;

    private BusinessItem businessItem;

    /** 员工 当前子业务 业务当前所得的子账单ID */
    private Integer companySonBillId;

    /** 员工合作方式  开票据的时候应用 */
    private Integer waysOfCooperation;
    private Integer memberId;

    /** 汇总账单ID */
    private Integer companySonTotalBillId;

    /** 税费 */
    private Double taxPrice;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BusinessInsuranceItem that = (BusinessInsuranceItem) o;

        return businessItemId.equals(that.businessItemId);

    }

    @Override
    public int hashCode() {
        return businessItemId.hashCode();
    }

    public Integer getBusinessInsuranceId() {
        return this.businessInsuranceId;
    }

    public void setBusinessInsuranceId(Integer businessInsuranceId) {
        this.businessInsuranceId = businessInsuranceId;
    }

    public Integer getBusinessItemId() {
        return this.businessItemId;
    }

    public void setBusinessItemId(Integer businessItemId) {
        this.businessItemId = businessItemId;
    }

    public BusinessItem getBusinessItem() {
        return this.businessItem;
    }

    public void setBusinessItem(BusinessItem businessItem) {
        this.businessItem = businessItem;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    /** 获取 员工 当前子业务 业务当前所得的子账单ID */
    public Integer getCompanySonBillId() {
        return this.companySonBillId;
    }

    /** 设置 员工 当前子业务 业务当前所得的子账单ID */
    public void setCompanySonBillId(Integer companySonBillId) {
        this.companySonBillId = companySonBillId;
    }

    /** 获取 员工合作方式  开票据的时候应用 */
    public Integer getWaysOfCooperation() {
        return this.waysOfCooperation;
    }

    /** 设置 员工合作方式  开票据的时候应用 */
    public void setWaysOfCooperation(Integer waysOfCooperation) {
        this.waysOfCooperation = waysOfCooperation;
    }

    public Integer getMemberId() {
        return this.memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    /** 获取 汇总账单ID */
    public Integer getCompanySonTotalBillId() {
        return this.companySonTotalBillId;
    }

    /** 设置 汇总账单ID */
    public void setCompanySonTotalBillId(Integer companySonTotalBillId) {
        this.companySonTotalBillId = companySonTotalBillId;
    }

    /** 获取 税费 */
    public Double getTaxPrice() {
        return this.taxPrice;
    }

    /** 设置 税费 */
    public void setTaxPrice(Double taxPrice) {
        this.taxPrice = taxPrice;
    }
}
