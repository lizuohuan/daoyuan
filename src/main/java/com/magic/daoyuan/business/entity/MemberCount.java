package com.magic.daoyuan.business.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 员工统计记录表
 * Created by Eric Xie on 2017/12/22 0022.
 */
public class MemberCount implements Serializable {

    private Integer id;

    /** 员工ID */
    private Integer memberId;

    /** 公司ID */
    private Integer companyId;

    /** 社保缴金地 ID */
    private Integer payPlaceIdOfSocialSecurity;

    /** 公积金缴金地 ID */
    private Integer payPlaceIdOfReservedFunds;

    /** 合作状态 0：离职  1：在职 */
    private Integer stateCooperation;

    /** 创建时间 */
    private Date createTime;

    // 业务字段

    private Integer countNumber;

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

    public Integer getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getPayPlaceIdOfSocialSecurity() {
        return payPlaceIdOfSocialSecurity;
    }

    public MemberCount setPayPlaceIdOfSocialSecurity(Integer payPlaceIdOfSocialSecurity) {
        this.payPlaceIdOfSocialSecurity = payPlaceIdOfSocialSecurity;
        return this;
    }

    public Integer getPayPlaceIdOfReservedFunds() {
        return payPlaceIdOfReservedFunds;
    }

    public MemberCount setPayPlaceIdOfReservedFunds(Integer payPlaceIdOfReservedFunds) {
        this.payPlaceIdOfReservedFunds = payPlaceIdOfReservedFunds;
        return this;
    }

    public Integer getStateCooperation() {
        return this.stateCooperation;
    }

    public void setStateCooperation(Integer stateCooperation) {
        this.stateCooperation = stateCooperation;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCountNumber() {
        return this.countNumber;
    }

    public void setCountNumber(Integer countNumber) {
        this.countNumber = countNumber;
    }
}
