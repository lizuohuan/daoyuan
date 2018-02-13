package com.magic.daoyuan.business.entity;

import java.util.Date;

/**
 * 反馈entity 不进行持久化
 * Created by Eric Xie on 2017/10/26 0026.
 */
public class Feedback {

    /** 证件号 */
    private String certificateNumber;

    /** 姓名 */
    private String userName;

    /** 服务时间 */
    private Date serviceDate;

    /** 失败原因 */
    private String reason;

    /** 处理是否成功  0:没有  1:是  2  已申报待上传、3 已上传待审核*/
    private Integer isSuccess;

    /** 备注 */
    private String remark;

    /** 0 参保  1 停保 */
    private Integer flag;


    public String getCertificateNumber() {
        return this.certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getServiceDate() {
        return this.serviceDate;
    }

    public void setServiceDate(Date serviceDate) {
        this.serviceDate = serviceDate;
    }

    public String getReason() {
        return this.reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getIsSuccess() {
        return this.isSuccess;
    }

    public void setIsSuccess(Integer isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    /** 获取 0 参保  1 停保 */
    public Integer getFlag() {
        return this.flag;
    }

    /** 设置 0 参保  1 停保 */
    public void setFlag(Integer flag) {
        this.flag = flag;
    }
}
