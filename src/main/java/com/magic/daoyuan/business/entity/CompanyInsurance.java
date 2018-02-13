package com.magic.daoyuan.business.entity;

import java.io.Serializable;

/**
 * 公司险种
 * @author lzh
 * @create 2017/10/10 15:14
 */
public class CompanyInsurance  implements Serializable {

    private Integer id;

    private Integer isValid;

    /** 公司id */
    private Integer companyId;

    /** 险种id */
    private Integer insuranceId;

    /** 公司缴金地ID */
    private Integer companyPayPlaceId;

    /** 公司缴纳类型 0：金额  1：比例 */
    private Integer coPayType;

    /** 公司缴纳 根据公司缴纳类型填写  金额/比例 */
    private Double coPayPrice;

    /** 个人缴纳类型 0：金额  1：比例 */
    private Integer mePayType;

    /** 个人缴纳金额/比例 */
    private Double mePayPrice;

    /** 档次id */
    private Integer insuranceLevelId;


    /*************  业务字段 不进行数据库持久化  ***************/

    /** 险种名 */
    private String insuranceName;

    /** 获取 公司id */
    public Integer getCompanyId() {
        return this.companyId;
    }

    /** 设置 公司id */
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    /** 获取 险种id */
    public Integer getInsuranceId() {
        return this.insuranceId;
    }

    /** 设置 险种id */
    public void setInsuranceId(Integer insuranceId) {
        this.insuranceId = insuranceId;
    }

    /** 获取 公司缴纳类型 0：金额  1：比例 */
    public Integer getCoPayType() {
        return this.coPayType;
    }

    /** 设置 公司缴纳类型 0：金额  1：比例 */
    public void setCoPayType(Integer coPayType) {
        this.coPayType = coPayType;
    }

    /** 获取 公司缴纳 根据公司缴纳类型填写  金额/比例 */
    public Double getCoPayPrice() {
        return this.coPayPrice;
    }

    /** 设置 公司缴纳 根据公司缴纳类型填写  金额/比例 */
    public void setCoPayPrice(Double coPayPrice) {
        this.coPayPrice = coPayPrice;
    }

    /** 获取 个人缴纳类型 0：金额  1：比例 */
    public Integer getMePayType() {
        return this.mePayType;
    }

    /** 设置 个人缴纳类型 0：金额  1：比例 */
    public void setMePayType(Integer mePayType) {
        this.mePayType = mePayType;
    }

    /** 获取 个人缴纳金额/比例 */
    public Double getMePayPrice() {
        return this.mePayPrice;
    }

    /** 设置 个人缴纳金额/比例 */
    public void setMePayPrice(Double mePayPrice) {
        this.mePayPrice = mePayPrice;
    }

    /** 获取 险种名 */
    public String getInsuranceName() {
        return this.insuranceName;
    }

    /** 设置 险种名 */
    public void setInsuranceName(String insuranceName) {
        this.insuranceName = insuranceName;
    }

    /** 获取 档次id */
    public Integer getInsuranceLevelId() {
        return this.insuranceLevelId;
    }

    /** 设置 档次id */
    public void setInsuranceLevelId(Integer insuranceLevelId) {
        this.insuranceLevelId = insuranceLevelId;
    }

    /** 获取 公司缴金地ID */
    public Integer getCompanyPayPlaceId() {
        return this.companyPayPlaceId;
    }

    /** 设置 公司缴金地ID */
    public void setCompanyPayPlaceId(Integer companyPayPlaceId) {
        this.companyPayPlaceId = companyPayPlaceId;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIsValid() {
        return this.isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }
}
