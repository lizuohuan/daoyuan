package com.magic.daoyuan.business.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 员工每月缴纳了服务费的业务
 * @author lzh
 * @create 2017/11/24 10:55
 */
public class MemberMonthPayBusiness implements Serializable {

    private Integer id;

    /** 员工id */
    private Integer memberId;

    /** 业务id */
    private Integer businessId;

    /** 缴纳月份 */
    private Date serviceMonth;

    /** 账单月 */
    private Date billMonth;

    /** 服务区域id */
    private Integer cityId;

    /** 所属子账单 */
    private Integer companySonBillId;

    /** 账单类型 0：预收 1：实作 */
    private Integer billType;

    /** 公司id */
    private Integer companyId;

    /** 是否有效 0 无效 1 有效 */
    private Integer isValid;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /** 获取 员工id */
    public Integer getMemberId() {
        return this.memberId;
    }

    /** 设置 员工id */
    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    /** 获取 业务id */
    public Integer getBusinessId() {
        return this.businessId;
    }

    /** 设置 业务id */
    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    /** 获取 缴纳月份 */
    public Date getServiceMonth() {
        return this.serviceMonth;
    }

    /** 设置 缴纳月份 */
    public void setServiceMonth(Date serviceMonth) {
        this.serviceMonth = serviceMonth;
    }

    /** 获取 账单月 */
    public Date getBillMonth() {
        return this.billMonth;
    }

    /** 设置 账单月 */
    public void setBillMonth(Date billMonth) {
        this.billMonth = billMonth;
    }

    /** 获取 服务区域id */
    public Integer getCityId() {
        return this.cityId;
    }

    /** 设置 服务区域id */
    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    /** 获取 所属子账单 */
    public Integer getCompanySonBillId() {
        return this.companySonBillId;
    }

    /** 设置 所属子账单 */
    public void setCompanySonBillId(Integer companySonBillId) {
        this.companySonBillId = companySonBillId;
    }

    /** 获取 账单类型 0：预收 1：实作 */
    public Integer getBillType() {
        return this.billType;
    }

    /** 设置 账单类型 0：预收 1：实作 */
    public void setBillType(Integer billType) {
        this.billType = billType;
    }

    /** 获取 公司id */
    public Integer getCompanyId() {
        return this.companyId;
    }

    /** 设置 公司id */
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    /** 获取 是否有效 0 无效 1 有效 */
    public Integer getIsValid() {
        return this.isValid;
    }

    /** 设置 是否有效 0 无效 1 有效 */
    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }
}
