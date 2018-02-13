package com.magic.daoyuan.business.entity;

import java.io.Serializable;

/**
 * 服务费收费
 * Created by Eric Xie on 2017/9/19 0019.
 */
public class CompanyServiceFee extends BaseEntity implements Serializable {

    private Integer companyId;

    private Double price;

    private Integer extent;

    /** 业务Id 当选择按服务类别收费的时候则存在 */
    private Integer businessId;

    /** 业务名称 */
    private String businessName;

    /** 0:客户/公司  1:供应商配置 */
    private Integer type;

    public Integer getCompanyId() {
        return companyId;
    }

    public CompanyServiceFee setCompanyId(Integer companyId) {
        this.companyId = companyId;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public CompanyServiceFee setPrice(Double price) {
        this.price = price;
        return this;
    }

    public Integer getExtent() {
        return extent;
    }

    public CompanyServiceFee setExtent(Integer extent) {
        this.extent = extent;
        return this;
    }

    /** 获取 业务Id 当选择按服务类别收费的时候则存在 */
    public Integer getBusinessId() {
        return this.businessId;
    }

    /** 设置 业务Id 当选择按服务类别收费的时候则存在 */
    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    /** 获取 业务名称 */
    public String getBusinessName() {
        return this.businessName;
    }

    /** 设置 业务名称 */
    public void setBusinessName(String businessName) {
        this.businessName = businessName;
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
