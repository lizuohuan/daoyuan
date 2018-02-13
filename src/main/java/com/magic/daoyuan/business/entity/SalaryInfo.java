package com.magic.daoyuan.business.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 工资信息
 * Created by Eric Xie on 2017/10/19 0019.
 */
public class SalaryInfo extends BaseEntity implements Serializable {

    /** 员工ID */
    private Integer memberId;

    /** 银行帐号 */
    private String bankCard;


    /** 所属月份 */
    private Date month;

    /** 应发工资 */
    private Double shouldSendSalary;

    /** 应扣工资 */
    private Double shouldBeDeductPay;

    /** 税前工资 */
    private Double salaryBeforeTax;

    /** 个税 */
    private Double individualIncomeTax;

    /** 实发工资 */
    private Double takeHomePay;

    /** 所属 汇总账单id */
    private Integer companySonTotalBillId;

    /** 工资类型IDs */
    private Integer salaryTypeId;

    /** 创建年月 */
    private Date createTime;

    /** 养老 */
    private Double pension;

    /** 医疗 */
    private Double medical;

    /** 失业 */
    private Double unemployment;

    /** 公积金 */
    private Double reserved;

    /** 员工合作方式  开票据的时候应用 */
    private Integer waysOfCooperation;

    /** 税费 */
    private Double taxPrice;

    /** 工资发放日期 */
    private Date grantDate;


    // 业务字段
    private String userName;

    /** 员工名字 */
    private String memberName;

    private String bankName;

    private String nationality;

    private String cityName;

    private String salaryTypeName;

    private String certificateNumber;

    /** 子账单ID */
    private Integer companySonBillId;

    /** 员工工资资料 */
    private MemberSalary memberSalary;


    /** 获取 员工ID */
    public Integer getMemberId() {
        return this.memberId;
    }

    /** 设置 员工ID */
    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    /** 获取 银行帐号 */
    public String getBankCard() {
        return this.bankCard;
    }

    /** 设置 银行帐号 */
    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }


    /** 获取 所属月份 */
    public Date getMonth() {
        return this.month;
    }

    /** 设置 所属月份 */
    public void setMonth(Date month) {
        this.month = month;
    }

    /** 获取 应发工资 */
    public Double getShouldSendSalary() {
        return this.shouldSendSalary;
    }

    /** 设置 应发工资 */
    public void setShouldSendSalary(Double shouldSendSalary) {
        this.shouldSendSalary = shouldSendSalary;
    }

    /** 获取 应扣工资 */
    public Double getShouldBeDeductPay() {
        return this.shouldBeDeductPay;
    }

    /** 设置 应扣工资 */
    public void setShouldBeDeductPay(Double shouldBeDeductPay) {
        this.shouldBeDeductPay = shouldBeDeductPay;
    }

    /** 获取 税前工资 */
    public Double getSalaryBeforeTax() {
        return this.salaryBeforeTax;
    }

    /** 设置 税前工资 */
    public void setSalaryBeforeTax(Double salaryBeforeTax) {
        this.salaryBeforeTax = salaryBeforeTax;
    }

    /** 获取 个税 */
    public Double getIndividualIncomeTax() {
        return this.individualIncomeTax;
    }

    /** 设置 个税 */
    public void setIndividualIncomeTax(Double individualIncomeTax) {
        this.individualIncomeTax = individualIncomeTax;
    }

    /** 获取 实发工资 */
    public Double getTakeHomePay() {
        return this.takeHomePay;
    }

    /** 设置 实发工资 */
    public void setTakeHomePay(Double takeHomePay) {
        this.takeHomePay = takeHomePay;
    }

    // 获取 业务字段
    public String getUserName() {
        return this.userName;
    }

    // 设置 业务字段
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBankName() {
        return this.bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    /** 获取 工资类型IDs */
    public Integer getSalaryTypeId() {
        return this.salaryTypeId;
    }

    /** 设置 工资类型IDs */
    public void setSalaryTypeId(Integer salaryTypeId) {
        this.salaryTypeId = salaryTypeId;
    }

    /** 获取 创建年月 */
    public Date getCreateTime() {
        return this.createTime;
    }

    /** 设置 创建年月 */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /** 获取 子账单ID */
    public Integer getCompanySonBillId() {
        return this.companySonBillId;
    }

    /** 设置 子账单ID */
    public void setCompanySonBillId(Integer companySonBillId) {
        this.companySonBillId = companySonBillId;
    }

    public String getNationality() {
        return this.nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getCityName() {
        return this.cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getSalaryTypeName() {
        return this.salaryTypeName;
    }

    public void setSalaryTypeName(String salaryTypeName) {
        this.salaryTypeName = salaryTypeName;
    }

    public String getCertificateNumber() {
        return this.certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    public Double getPension() {
        return this.pension;
    }

    public void setPension(Double pension) {
        this.pension = pension;
    }

    public Double getMedical() {
        return this.medical;
    }

    public void setMedical(Double medical) {
        this.medical = medical;
    }

    public Double getUnemployment() {
        return this.unemployment;
    }

    public void setUnemployment(Double unemployment) {
        this.unemployment = unemployment;
    }

    public Double getReserved() {
        return this.reserved;
    }

    public void setReserved(Double reserved) {
        this.reserved = reserved;
    }

    /** 获取 员工合作方式  开票据的时候应用 */
    public Integer getWaysOfCooperation() {
        return this.waysOfCooperation;
    }

    /** 设置 员工合作方式  开票据的时候应用 */
    public void setWaysOfCooperation(Integer waysOfCooperation) {
        this.waysOfCooperation = waysOfCooperation;
    }

    /** 获取 员工名字 */
    public String getMemberName() {
        return this.memberName;
    }

    /** 设置 员工名字 */
    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    /** 获取 所属 汇总账单id */
    public Integer getCompanySonTotalBillId() {
        return this.companySonTotalBillId;
    }

    /** 设置 所属 汇总账单id */
    public void setCompanySonTotalBillId(Integer companySonTotalBillId) {
        this.companySonTotalBillId = companySonTotalBillId;
    }

    /** 获取 员工工资资料 */
    public MemberSalary getMemberSalary() {
        return this.memberSalary;
    }

    /** 设置 员工工资资料 */
    public void setMemberSalary(MemberSalary memberSalary) {
        this.memberSalary = memberSalary;
    }

    /** 获取 税费 */
    public Double getTaxPrice() {
        return this.taxPrice;
    }

    /** 设置 税费 */
    public void setTaxPrice(Double taxPrice) {
        this.taxPrice = taxPrice;
    }

    /** 获取 工资发放日期 */
    public Date getGrantDate() {
        return this.grantDate;
    }

    /** 设置 工资发放日期 */
    public void setGrantDate(Date grantDate) {
        this.grantDate = grantDate;
    }
}
