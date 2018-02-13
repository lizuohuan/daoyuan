package com.magic.daoyuan.business.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 员工-商业险 生成后数据
 * Created by Eric Xie on 2017/10/24 0024.
 */
public class BusinessInsurance  implements Serializable {

    private Integer id;

    /** 员工ID */
    private Integer memberId;

    /** 服务起始年月 */
    private Date serviceStartTime;

    /** 服务结束年月 */
    private Date serviceEndTime;

    /** 本次服务时长 */
    private Integer serviceTime;

    /** 创建时间 */
    private Date createTime;

    /** 总账单ID */
    private Integer companySonTotalBillId;


    /** 业务子类应用 */
    private List<BusinessInsuranceItem> businessInsuranceItemList;


    // 业务字段

    /** 用户名 */
    private String userName;

    /** 证件编号 */
    private String certificateNum;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BusinessInsurance that = (BusinessInsurance) o;

        return id != null ? id.equals(that.id) : that.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public Integer getMemberId() {
        return this.memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Date getServiceStartTime() {
        return this.serviceStartTime;
    }

    public void setServiceStartTime(Date serviceStartTime) {
        this.serviceStartTime = serviceStartTime;
    }

    public Integer getServiceTime() {
        return this.serviceTime;
    }

    public void setServiceTime(Integer serviceTime) {
        this.serviceTime = serviceTime;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /** 获取 总账单ID */
    public Integer getCompanySonTotalBillId() {
        return this.companySonTotalBillId;
    }

    /** 设置 总账单ID */
    public void setCompanySonTotalBillId(Integer companySonTotalBillId) {
        this.companySonTotalBillId = companySonTotalBillId;
    }

    /** 获取 业务子类应用 */
    public List<BusinessInsuranceItem> getBusinessInsuranceItemList() {
        return this.businessInsuranceItemList;
    }

    /** 设置 业务子类应用 */
    public void setBusinessInsuranceItemList(List<BusinessInsuranceItem> businessInsuranceItemList) {
        this.businessInsuranceItemList = businessInsuranceItemList;
    }

    /** 获取 服务结束年月 */
    public Date getServiceEndTime() {
        return this.serviceEndTime;
    }

    /** 设置 服务结束年月 */
    public void setServiceEndTime(Date serviceEndTime) {
        this.serviceEndTime = serviceEndTime;
    }

    /** 获取 用户名 */
    public String getUserName() {
        return this.userName;
    }

    /** 设置 用户名 */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /** 获取 证件编号 */
    public String getCertificateNum() {
        return this.certificateNum;
    }

    /** 设置 证件编号 */
    public void setCertificateNum(String certificateNum) {
        this.certificateNum = certificateNum;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
