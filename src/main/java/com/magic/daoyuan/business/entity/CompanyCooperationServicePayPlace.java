package com.magic.daoyuan.business.entity;

import java.io.Serializable;

/**
 * Created by Eric Xie on 2017/11/2 0002.
 */
public class CompanyCooperationServicePayPlace extends BaseEntity implements Serializable {


    /** 城市ID */
    private Integer cityId;

    /** 公司的合作方式ID */
    private Integer companyCooperationMethodId;

    /** 价格 */
    private Double price;


    public Integer getCityId() {
        return this.cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getCompanyCooperationMethodId() {
        return this.companyCooperationMethodId;
    }

    public void setCompanyCooperationMethodId(Integer companyCooperationMethodId) {
        this.companyCooperationMethodId = companyCooperationMethodId;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
