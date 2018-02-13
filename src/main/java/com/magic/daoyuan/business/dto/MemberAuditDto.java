package com.magic.daoyuan.business.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 员工稽核
 * @author lzh
 * @create 2017/10/31 19:06
 */
public class MemberAuditDto implements Serializable {

    /** 员工名 */
    private String userName;

    /** 证件编号 */
    private String certificateNum;

    /** 所属公司名 */
    private String companyName;

    /** 服务月 */
    private Date serviceMonth;

    /** 社保缴金地名 */
    private String socialSecurityPayPlaceName;

    /** 社保办理方名 */
    private String socialSecurityTransactorName;

    /** 社保档次名 */
    private String socialSecurityInsuranceLevelName;

    /** 社保填写基数 */
    private Double socialSecurityBaseNumber;

    /** 社保缴纳明细 以逗号隔开 */
    private String socialSecurityInfo;

    /** 公积金缴金地名 */
    private String reservedFundsPayPlaceName;

    /** 公积金办理方名 */
    private String reservedFundsTransactorName;

    /** 公积金填写基数 */
    private Double reservedFundsBaseNumber;

    /** 公积金缴纳明细 以逗号隔开 */
    private String reservedFundsInfo;

    /** 获取 员工名 */
    public String getUserName() {
        return this.userName;
    }

    /** 设置 员工名 */
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

    /** 获取 所属公司名 */
    public String getCompanyName() {
        return this.companyName;
    }

    /** 设置 所属公司名 */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /** 获取 服务月 */
    public Date getServiceMonth() {
        return this.serviceMonth;
    }

    /** 设置 服务月 */
    public void setServiceMonth(Date serviceMonth) {
        this.serviceMonth = serviceMonth;
    }

    /** 获取 社保缴金地名 */
    public String getSocialSecurityPayPlaceName() {
        return this.socialSecurityPayPlaceName;
    }

    /** 设置 社保缴金地名 */
    public void setSocialSecurityPayPlaceName(String socialSecurityPayPlaceName) {
        this.socialSecurityPayPlaceName = socialSecurityPayPlaceName;
    }

    /** 获取 社保办理方名 */
    public String getSocialSecurityTransactorName() {
        return this.socialSecurityTransactorName;
    }

    /** 设置 社保办理方名 */
    public void setSocialSecurityTransactorName(String socialSecurityTransactorName) {
        this.socialSecurityTransactorName = socialSecurityTransactorName;
    }

    /** 获取 社保档次名 */
    public String getSocialSecurityInsuranceLevelName() {
        return this.socialSecurityInsuranceLevelName;
    }

    /** 设置 社保档次名 */
    public void setSocialSecurityInsuranceLevelName(String socialSecurityInsuranceLevelName) {
        this.socialSecurityInsuranceLevelName = socialSecurityInsuranceLevelName;
    }

    /** 获取 社保填写基数 */
    public Double getSocialSecurityBaseNumber() {
        return this.socialSecurityBaseNumber;
    }

    /** 设置 社保填写基数 */
    public void setSocialSecurityBaseNumber(Double socialSecurityBaseNumber) {
        this.socialSecurityBaseNumber = socialSecurityBaseNumber;
    }

    /** 获取 社保缴纳明细 以逗号隔开 */
    public String getSocialSecurityInfo() {
        return this.socialSecurityInfo;
    }

    /** 设置 社保缴纳明细 以逗号隔开 */
    public void setSocialSecurityInfo(String socialSecurityInfo) {
        this.socialSecurityInfo = socialSecurityInfo;
    }

    /** 获取 公积金缴金地名 */
    public String getReservedFundsPayPlaceName() {
        return this.reservedFundsPayPlaceName;
    }

    /** 设置 公积金缴金地名 */
    public void setReservedFundsPayPlaceName(String reservedFundsPayPlaceName) {
        this.reservedFundsPayPlaceName = reservedFundsPayPlaceName;
    }

    /** 获取 公积金办理方名 */
    public String getReservedFundsTransactorName() {
        return this.reservedFundsTransactorName;
    }

    /** 设置 公积金办理方名 */
    public void setReservedFundsTransactorName(String reservedFundsTransactorName) {
        this.reservedFundsTransactorName = reservedFundsTransactorName;
    }

    /** 获取 公积金填写基数 */
    public Double getReservedFundsBaseNumber() {
        return this.reservedFundsBaseNumber;
    }

    /** 设置 公积金填写基数 */
    public void setReservedFundsBaseNumber(Double reservedFundsBaseNumber) {
        this.reservedFundsBaseNumber = reservedFundsBaseNumber;
    }

    /** 获取 公积金缴纳明细 以逗号隔开 */
    public String getReservedFundsInfo() {
        return this.reservedFundsInfo;
    }

    /** 设置 公积金缴纳明细 以逗号隔开 */
    public void setReservedFundsInfo(String reservedFundsInfo) {
        this.reservedFundsInfo = reservedFundsInfo;
    }
}
