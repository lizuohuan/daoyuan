package com.magic.daoyuan.business.entity;

import java.io.Serializable;

/**
 * Created by Eric Xie on 2017/10/11 0011.
 */
public class MemberSalary extends BaseEntity implements Serializable {


    /** 是否中国大陆 0：不是  1：是 */
    private Integer nationality;

    /** 银行帐号 */
    private String bankAccount;

    private String bankName;

    /** 电话号码 */
    private String phone;

    /** 员工ID */
    private Integer memberId;

    /** 报税地 ID 市级ID */
    private Integer cityId;

    private City city;

    /** 子账单ID */
    private Integer companySonBillId;

    public Integer getNationality() {
        return this.nationality;
    }

    public void setNationality(Integer nationality) {
        this.nationality = nationality;
    }

    public String getBankAccount() {
        return this.bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }



    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    /** 获取 员工ID */
    public Integer getMemberId() {
        return this.memberId;
    }

    /** 设置 员工ID */
    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    /** 获取 报税地 ID 市级ID */
    public Integer getCityId() {
        return this.cityId;
    }

    /** 设置 报税地 ID 市级ID */
    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public City getCity() {
        return this.city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    /** 获取 子账单ID */
    public Integer getCompanySonBillId() {
        return this.companySonBillId;
    }

    /** 设置 子账单ID */
    public void setCompanySonBillId(Integer companySonBillId) {
        this.companySonBillId = companySonBillId;
    }

    public String getBankName() {
        return this.bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MemberSalary that = (MemberSalary) o;

        return memberId.equals(that.memberId);

    }

    @Override
    public int hashCode() {
        return memberId.hashCode();
    }
}
