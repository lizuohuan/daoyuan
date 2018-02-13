package com.magic.daoyuan.business.entity;

import java.io.Serializable;

/**
 * Created by Eric Xie on 2017/10/9 0009.
 */
public class BusinessServiceFee extends BaseEntity implements Serializable {


    private Integer companyId;

    private String businessIds;

    private Double price;

    /** 0:客户/公司  1:供应商配置 */
    private Integer type;


    public Integer getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getBusinessIds() {
        return this.businessIds;
    }

    public void setBusinessIds(String businessIds) {
        this.businessIds = businessIds;
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
}
