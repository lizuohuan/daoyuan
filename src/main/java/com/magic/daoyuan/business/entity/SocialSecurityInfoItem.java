package com.magic.daoyuan.business.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 账单-社保明细 缴纳险种
 * @author lzh
 * @create 2017/10/17 17:56
 */
public class SocialSecurityInfoItem implements Serializable {

    private Integer id;

    /** 险种名 */
    private String insuranceName;

    /** 缴纳金额 */
    private Double payPrice;

    /** 缴纳方 0：公司 1：个人 */
    private Integer type;

    /** 账单-社保缴纳明细id */
    private Integer socialSecurityInfoId;

    /** 稽核后实际缴纳金额 */
    private Double practicalPayPrice;


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

    /** 获取 缴纳方 0：公司 1：个人 */
    public Integer getType() {
        return this.type;
    }

    /** 设置 缴纳方 0：公司 1：个人 */
    public void setType(Integer type) {
        this.type = type;
    }

    /** 获取 账单-社保缴纳明细id */
    public Integer getSocialSecurityInfoId() {
        return this.socialSecurityInfoId;
    }

    /** 设置 账单-社保缴纳明细id */
    public void setSocialSecurityInfoId(Integer socialSecurityInfoId) {
        this.socialSecurityInfoId = socialSecurityInfoId;
    }

    /** 获取 稽核后实际缴纳金额 */
    public Double getPracticalPayPrice() {
        return this.practicalPayPrice;
    }

    /** 设置 稽核后实际缴纳金额 */
    public void setPracticalPayPrice(Double practicalPayPrice) {
        this.practicalPayPrice = practicalPayPrice;
    }

    /**
     * 稽核差
     * @return
     */
    public Double getAuditDifference() {
        if (null != practicalPayPrice && null != payPrice) {
            return new BigDecimal(payPrice.toString()).subtract(new BigDecimal(practicalPayPrice.toString())).doubleValue();
        }
        return 0.0;
    }
}
