package com.magic.daoyuan.business.entity;

/**
 * Created by Eric Xie on 2017/12/21 0021.
 */
public class SocialSecurityCommon {

    /** 险种名称 */
    private String insuranceName;

    /** 公司缴纳 */
    private Double companyPrice;

    /** 个人缴纳 */
    private Double personPrice;

    public String getInsuranceName() {
        return insuranceName;
    }

    public SocialSecurityCommon setInsuranceName(String insuranceName) {
        this.insuranceName = insuranceName;
        return this;
    }

    public Double getCompanyPrice() {
        return companyPrice;
    }

    public SocialSecurityCommon setCompanyPrice(Double companyPrice) {
        this.companyPrice = companyPrice;
        return this;
    }

    public Double getPersonPrice() {
        return personPrice;
    }

    public SocialSecurityCommon setPersonPrice(Double personPrice) {
        this.personPrice = personPrice;
        return this;
    }
}
