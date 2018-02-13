package com.magic.daoyuan.business.vo;

import java.util.Date;

/**
 * 个人启封
 * Created by Eric Xie on 2017/11/28 0028.
 */
public class PersonUnseal {

    /** 个人客户号 */
    private String customerID;

    /** 员工名称 */
    private String memberName;

    /** 执行时间 */
    private Date executionTime;

    /** 启封后缴存基数 */
    private String unsealBaseNumber;

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

    public String getMemberName() {
        return this.memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public Date getExecutionTime() {
        return this.executionTime;
    }

    public void setExecutionTime(Date executionTime) {
        this.executionTime = executionTime;
    }

    public String getUnsealBaseNumber() {
        return this.unsealBaseNumber;
    }

    public void setUnsealBaseNumber(String unsealBaseNumber) {
        this.unsealBaseNumber = unsealBaseNumber;
    }

    public Integer getUnsealReason() {
        return this.unsealReason;
    }

    public void setUnsealReason(Integer unsealReason) {
        this.unsealReason = unsealReason;
    }
}
