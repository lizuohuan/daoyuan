package com.magic.daoyuan.business.entity;

import java.io.Serializable;

/**
 * 账单-专项服务明细
 * @author lzh
 * @create 2017/10/19 19:22
 */
public class SpecialServiceInfo implements Serializable {


    private Integer id;

    /** 姓名 */
    private String userName;

    /** 证件类型 */
    private Integer certificateType;

    /** 证件编号 */
    private String idCard;

    /** 员工id */
    private Integer memberId;

    /** 缴纳金额 */
    private Double payPrice;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /** 获取 姓名 */
    public String getUserName() {
        return this.userName;
    }

    /** 设置 姓名 */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /** 获取 证件类型 */
    public Integer getCertificateType() {
        return this.certificateType;
    }

    /** 设置 证件类型 */
    public void setCertificateType(Integer certificateType) {
        this.certificateType = certificateType;
    }

    /** 获取 证件编号 */
    public String getIdCard() {
        return this.idCard;
    }

    /** 设置 证件编号 */
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    /** 获取 员工id */
    public Integer getMemberId() {
        return this.memberId;
    }

    /** 设置 员工id */
    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    /** 获取 缴纳金额 */
    public Double getPayPrice() {
        return this.payPrice;
    }

    /** 设置 缴纳金额 */
    public void setPayPrice(Double payPrice) {
        this.payPrice = payPrice;
    }
}
