package com.magic.daoyuan.business.entity;

import java.io.Serializable;

/**
 * Created by Eric Xie on 2017/11/2 0002.
 */
public class CompanyCooperationBusinessServiceFee extends BaseEntity implements Serializable {

    /** 逗号隔开的业务组合 */
    private String businessIds;

    /** 费用 */
    private Double price;

    /** 公司的合作方式ID */
    private Integer companyCooperationMethodId;


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

    public Integer getCompanyCooperationMethodId() {
        return this.companyCooperationMethodId;
    }

    public void setCompanyCooperationMethodId(Integer companyCooperationMethodId) {
        this.companyCooperationMethodId = companyCooperationMethodId;
    }
}
