package com.magic.daoyuan.business.entity;

import java.io.Serializable;

/**
 *  合作方式的阶梯收费
 * Created by Eric Xie on 2017/11/2 0002.
 */
public class CompanyCooperationServiceFee extends BaseEntity implements Serializable {

    /** 价格 */
    private Double price;

    /** 阶梯 低于多少 */
    private Integer extent;

    /** 公司的合作方式ID */
    private Integer companyCooperationMethodId;




    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getExtent() {
        return this.extent;
    }

    public void setExtent(Integer extent) {
        this.extent = extent;
    }

    public Integer getCompanyCooperationMethodId() {
        return this.companyCooperationMethodId;
    }

    public void setCompanyCooperationMethodId(Integer companyCooperationMethodId) {
        this.companyCooperationMethodId = companyCooperationMethodId;
    }

}
