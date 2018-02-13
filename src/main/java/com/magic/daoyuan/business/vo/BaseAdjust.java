package com.magic.daoyuan.business.vo;

import java.util.Date;

/**
 * 基数调整
 * Created by Eric Xie on 2017/11/28 0028.
 */
public class BaseAdjust {

    /** 个人客户号 */
    private String customerID;

    /** 员工名称 */
    private String memberName;

    /** 调整前 */
    private Integer beforeAdjust;

    /** 调整后 */
    private Integer afterAdjust;

    /** 执行时间 */
    private Date executionTime;

    /** 备注 */
    private String remark;


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

    /** 获取 调整前 */
    public Integer getBeforeAdjust() {
        return this.beforeAdjust;
    }

    /** 设置 调整前 */
    public void setBeforeAdjust(Integer beforeAdjust) {
        this.beforeAdjust = beforeAdjust;
    }

    /** 获取 调整后 */
    public Integer getAfterAdjust() {
        return this.afterAdjust;
    }

    /** 设置 调整后 */
    public void setAfterAdjust(Integer afterAdjust) {
        this.afterAdjust = afterAdjust;
    }

    /** 获取 执行时间 */
    public Date getExecutionTime() {
        return this.executionTime;
    }

    /** 设置 执行时间 */
    public void setExecutionTime(Date executionTime) {
        this.executionTime = executionTime;
    }

    /** 获取 备注 */
    public String getRemark() {
        return this.remark;
    }

    /** 设置 备注 */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}
