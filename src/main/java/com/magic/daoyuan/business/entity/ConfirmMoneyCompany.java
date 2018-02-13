package com.magic.daoyuan.business.entity;

import java.io.Serializable;

/**
 * 到款信息 对应 匹配出来的公司信息
 * Created by Eric Xie on 2017/10/27 0027.
 */
public class ConfirmMoneyCompany extends BaseEntity implements Serializable {



    private Integer confirmMoneyRecordId;

    private Integer companyId;

    private Integer serviceId;

    private String companyName;

    private String serviceUserName;

    /** 公司银行信息 */
    private Integer bankInfoId;

    // 业务字段
    private Double confirmAmount;

    public Integer getConfirmMoneyRecordId() {
        return this.confirmMoneyRecordId;
    }

    public void setConfirmMoneyRecordId(Integer confirmMoneyRecordId) {
        this.confirmMoneyRecordId = confirmMoneyRecordId;
    }

    public Integer getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getServiceId() {
        return this.serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getServiceUserName() {
        return this.serviceUserName;
    }

    public void setServiceUserName(String serviceUserName) {
        this.serviceUserName = serviceUserName;
    }

    /** 获取 公司银行信息 */
    public Integer getBankInfoId() {
        return this.bankInfoId;
    }

    /** 设置 公司银行信息 */
    public void setBankInfoId(Integer bankInfoId) {
        this.bankInfoId = bankInfoId;
    }

    // 获取 业务字段
    public Double getConfirmAmount() {
        return this.confirmAmount;
    }

    // 设置 业务字段
    public void setConfirmAmount(Double confirmAmount) {
        this.confirmAmount = confirmAmount;
    }
}
