package com.magic.daoyuan.business.vo;

import java.util.Date;

/**
 * 个人封存
 * Created by Eric Xie on 2017/11/28 0028.
 */
public class PersonArchive {


    /** 个人客户号 */
    private String customerID;

    /** 员工名称 */
    private String memberName;

    /** 生效时间 */
    private Date effectiveTime;

    /** 启封原因 */
    private Integer unsealReason;


    /** 获取 个人客户号 */
    public String getCustomerID() {
        return this.customerID;
    }

    /** 设置 个人客户号 */
    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    /** 获取 员工名称 */
    public String getMemberName() {
        return this.memberName;
    }

    /** 设置 员工名称 */
    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    /** 获取 生效时间 */
    public Date getEffectiveTime() {
        return this.effectiveTime;
    }

    /** 设置 生效时间 */
    public void setEffectiveTime(Date effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    /** 获取 启封原因 */
    public Integer getUnsealReason() {
        return this.unsealReason;
    }

    /** 设置 启封原因 */
    public void setUnsealReason(Integer unsealReason) {
        this.unsealReason = unsealReason;
    }
}
