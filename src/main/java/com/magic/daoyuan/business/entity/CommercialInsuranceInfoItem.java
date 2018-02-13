package com.magic.daoyuan.business.entity;

import java.io.Serializable;

/**
 * 账单-商业险明细子类-险种
 * @author lzh
 * @create 2017/10/19 19:19
 */
public class CommercialInsuranceInfoItem implements Serializable {

    private Integer id;

    /** 险种名 */
    private String insuranceName;

    /** 缴纳金额 */
    private Double payPrice;

    /** 商业险明细id */
    private Integer commercialInsuranceId;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /** 获取 险种名 */
    public String getInsuranceName() {
        return this.insuranceName;
    }

    /** 设置 险种名 */
    public void setInsuranceName(String insuranceName) {
        this.insuranceName = insuranceName;
    }

    /** 获取 缴纳金额 */
    public Double getPayPrice() {
        return this.payPrice;
    }

    /** 设置 缴纳金额 */
    public void setPayPrice(Double payPrice) {
        this.payPrice = payPrice;
    }

    /** 获取 商业险明细id */
    public Integer getCommercialInsuranceId() {
        return this.commercialInsuranceId;
    }

    /** 设置 商业险明细id */
    public void setCommercialInsuranceId(Integer commercialInsuranceId) {
        this.commercialInsuranceId = commercialInsuranceId;
    }
}
