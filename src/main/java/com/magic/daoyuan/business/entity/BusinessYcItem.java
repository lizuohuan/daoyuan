package com.magic.daoyuan.business.entity;

import java.io.Serializable;

/**
 * Created by Eric Xie on 2017/10/25 0025.
 */
public class BusinessYcItem implements Serializable {


    private Integer id;

    private Integer memberId;

    private Double price;

    private Integer businessYcId;

    /** 0:员工  1:公司 */
    private Integer flag;

    /** 证件编号 */
    private String certificateNum;

    /** 用户名 */
    private String userName;

    /** 员工 当前子业务 业务当前所得的子账单ID */
    private Integer companySonBillId;

    /** 员工合作方式  开票据的时候应用 */
    private Integer waysOfCooperation;

    /** 汇总账单 */
    private Integer companySonTotalBillId;


    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMemberId() {
        return this.memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getBusinessYcId() {
        return this.businessYcId;
    }

    public void setBusinessYcId(Integer businessYcId) {
        this.businessYcId = businessYcId;
    }

    /** 获取 证件编号 */
    public String getCertificateNum() {
        return this.certificateNum;
    }

    /** 设置 证件编号 */
    public void setCertificateNum(String certificateNum) {
        this.certificateNum = certificateNum;
    }

    /** 获取 用户名 */
    public String getUserName() {
        return this.userName;
    }

    /** 设置 用户名 */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /** 获取 员工 当前子业务 业务当前所得的子账单ID */
    public Integer getCompanySonBillId() {
        return this.companySonBillId;
    }

    /** 设置 员工 当前子业务 业务当前所得的子账单ID */
    public void setCompanySonBillId(Integer companySonBillId) {
        this.companySonBillId = companySonBillId;
    }

    /** 获取 员工合作方式  开票据的时候应用 */
    public Integer getWaysOfCooperation() {
        return this.waysOfCooperation;
    }

    /** 设置 员工合作方式  开票据的时候应用 */
    public void setWaysOfCooperation(Integer waysOfCooperation) {
        this.waysOfCooperation = waysOfCooperation;
    }

    /** 获取 汇总账单 */
    public Integer getCompanySonTotalBillId() {
        return this.companySonTotalBillId;
    }

    /** 设置 汇总账单 */
    public void setCompanySonTotalBillId(Integer companySonTotalBillId) {
        this.companySonTotalBillId = companySonTotalBillId;
    }

    /** 获取 0:员工  1:公司 */
    public Integer getFlag() {
        return this.flag;
    }

    /** 设置 0:员工  1:公司 */
    public void setFlag(Integer flag) {
        this.flag = flag;
    }
}
