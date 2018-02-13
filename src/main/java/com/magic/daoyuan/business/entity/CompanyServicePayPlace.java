package com.magic.daoyuan.business.entity;

import java.io.Serializable;

/**
 * Created by Eric Xie on 2017/10/24 0024.
 */
public class CompanyServicePayPlace extends BaseEntity implements Serializable {


    /** 城市ID */
    private Integer cityId;

    /** 所属公司ID */
    private Integer companyId;

    /** 单价 */
    private Double price;

    /** 0:客户/公司  1:供应商配置 */
    private Integer type;


    private String cityName;

    public Integer getCityId() {
        return this.cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    /** 获取 0:客户/公司  1:供应商配置 */
    public Integer getType() {
        return this.type;
    }

    /** 设置 0:客户/公司  1:供应商配置 */
    public void setType(Integer type) {
        this.type = type;
    }

    public String getCityName() {
        return this.cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
