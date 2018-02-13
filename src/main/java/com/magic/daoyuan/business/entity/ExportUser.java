package com.magic.daoyuan.business.entity;

import java.util.Date;

/**
 * Created by Eric Xie on 2017/11/5 0005.
 */
public class ExportUser {

    private Integer memberId;

    private String userName;

    private String certificateNum;

    private String levelName;

    private Integer education;

    private String educationName;

    private Date createTime;

    private String phone;

    private Integer stateCooperation;

    private Integer salary;

    private String coding;

    private String categoryName;


    public Integer getMemberId() {
        return this.memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCertificateNum() {
        return this.certificateNum;
    }

    public void setCertificateNum(String certificateNum) {
        this.certificateNum = certificateNum;
    }

    public String getLevelName() {
        return this.levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public Integer getEducation() {
        return this.education;
    }

    public void setEducation(Integer education) {
        this.education = education;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public Integer getStateCooperation() {
        return this.stateCooperation;
    }

    public void setStateCooperation(Integer stateCooperation) {
        this.stateCooperation = stateCooperation;
    }

    public String getCoding() {
        return this.coding;
    }

    public void setCoding(String coding) {
        this.coding = coding;
    }

    public String getEducationName() {
        return this.educationName;
    }

    public void setEducationName(String educationName) {
        this.educationName = educationName;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
