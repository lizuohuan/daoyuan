package com.magic.daoyuan.business.entity;

import java.io.Serializable;

/**
 * 业务和公司选择的业务子类
 * Created by Eric Xie on 2017/9/22 0022.
 */
public class CompanyBusinessItem extends BaseEntity implements Serializable {


    private Integer businessCompanyId;

    private Integer businessItemId;

    private Double price;

    /** 公司的一次性险业务的子账单ID */
    private Integer sonBillId;


    public Integer getBusinessCompanyId() {
        return this.businessCompanyId;
    }

    public void setBusinessCompanyId(Integer businessCompanyId) {
        this.businessCompanyId = businessCompanyId;
    }

    public Integer getBusinessItemId() {
        return this.businessItemId;
    }

    public void setBusinessItemId(Integer businessItemId) {
        this.businessItemId = businessItemId;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    /** 获取 公司的一次性险业务的子账单ID */
    public Integer getSonBillId() {
        return this.sonBillId;
    }

    /** 设置 公司的一次性险业务的子账单ID */
    public void setSonBillId(Integer sonBillId) {
        this.sonBillId = sonBillId;
    }
}
