package com.magic.daoyuan.business.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 联系人
 * Created by Eric Xie on 2017/9/12 0012.
 */
public class Contacts extends BaseEntity implements Serializable {

    /** 联系人 */
    private String contactsName;

    /** 固定电话 - 座机 */
    private String landLine;

    /** 电话号码 */
    private String phone;

    /** QQ */
    private String qq;

    /** Email */
    private String email;

    /** 微信号 */
    private String weChat;

    /** 是否为账单接受人 */
    private Integer isReceiver;

    /** 公司ID */
    private Integer companyId;

    /** 所属公司名称 */
    private String companyName;

    /** 类型 0:联系人 1:邮寄信息 */
    private Integer type;

    /** 部门 */
    private String departmentName;

    /** 职位 */
    private String jobTitle;


    private Date billMonth;

    public String getCompanyName() {
        return companyName;
    }

    public Contacts setCompanyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public Contacts setCompanyId(Integer companyId) {
        this.companyId = companyId;
        return this;
    }

    public String getContactsName() {
        return contactsName;
    }

    public Contacts setContactsName(String contactsName) {
        this.contactsName = contactsName;
        return this;
    }

    public String getLandLine() {
        return landLine;
    }

    public Contacts setLandLine(String landLine) {
        this.landLine = landLine;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public Contacts setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getQq() {
        return qq;
    }

    public Contacts setQq(String qq) {
        this.qq = qq;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Contacts setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getWeChat() {
        return weChat;
    }

    public Contacts setWeChat(String weChat) {
        this.weChat = weChat;
        return this;
    }

    public Integer getIsReceiver() {
        return isReceiver;
    }

    public Contacts setIsReceiver(Integer isReceiver) {
        this.isReceiver = isReceiver;
        return this;
    }

    /** 获取 类型 0:联系人 1:邮寄信息 */
    public Integer getType() {
        return this.type;
    }

    /** 设置 类型 0:联系人 1:邮寄信息 */
    public void setType(Integer type) {
        this.type = type;
    }

    /** 获取 部门 */
    public String getDepartmentName() {
        return this.departmentName;
    }

    /** 设置 部门 */
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    /** 获取 职位 */
    public String getJobTitle() {
        return this.jobTitle;
    }

    /** 设置 职位 */
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Date getBillMonth() {
        return this.billMonth;
    }

    public void setBillMonth(Date billMonth) {
        this.billMonth = billMonth;
    }
}
