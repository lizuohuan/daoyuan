package com.magic.daoyuan.business.entity;

import java.io.Serializable;

/**
 * 供应商联系人
 */
public class SupplierContacts implements Serializable {

    private Integer id;

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

    /** 供应商ID */
    private Integer supplierId;

    /** 供应商名称 */
    private String supplierName;

    /** 部门 */
    private String departmentName;

    /** 职位 */
    private String jobTitle;

    private Integer isValid;

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    /** 获取 联系人 */
    public String getContactsName() {
        return this.contactsName;
    }

    /** 设置 联系人 */
    public void setContactsName(String contactsName) {
        this.contactsName = contactsName;
    }

    /** 获取 固定电话 - 座机 */
    public String getLandLine() {
        return this.landLine;
    }

    /** 设置 固定电话 - 座机 */
    public void setLandLine(String landLine) {
        this.landLine = landLine;
    }

    /** 获取 电话号码 */
    public String getPhone() {
        return this.phone;
    }

    /** 设置 电话号码 */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /** 获取 QQ */
    public String getQq() {
        return this.qq;
    }

    /** 设置 QQ */
    public void setQq(String qq) {
        this.qq = qq;
    }

    /** 获取 Email */
    public String getEmail() {
        return this.email;
    }

    /** 设置 Email */
    public void setEmail(String email) {
        this.email = email;
    }

    /** 获取 微信号 */
    public String getWeChat() {
        return this.weChat;
    }

    /** 设置 微信号 */
    public void setWeChat(String weChat) {
        this.weChat = weChat;
    }

    /** 获取 供应商ID */
    public Integer getSupplierId() {
        return this.supplierId;
    }

    /** 设置 供应商ID */
    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    /** 获取 供应商名称 */
    public String getSupplierName() {
        return this.supplierName;
    }

    /** 设置 供应商名称 */
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
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

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
