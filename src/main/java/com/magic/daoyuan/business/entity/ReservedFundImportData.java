package com.magic.daoyuan.business.entity;

import java.util.Date;

/**
 * 拷盘 公积金通用数据模型
 * Created by Eric Xie on 2017/12/8 0008.
 */
public class ReservedFundImportData {

    /** 个人客户号 */
    private String customerNumber;

    /** 证件号 */
    private String idCard;

    /** 姓名 */
    private String userName;

    /** 服务月 */
    private Date serviceMonth;


    /** 账单起始月 */
    private Date billStartMonth;

    /** 服务结束月 */
    private Date serviceEndMonth;

    /** 缴金地 */
    private String payPlaceName;

    /** 经办机构 名 */
    private String organizationName;

    /** 办理方 名 */
    private String transactorName;

    /** 缴纳比例 */
    private Double payRatio;

    /** 缴纳基数 */
    private Double payCardinalNumber;

    /** 公司缴纳部分 */
    private Double companyTotalPay;

    /** 个人缴纳部分 */
    private Double memberTotalPay;


    /** 获取 个人客户号 */
    public String getCustomerNumber() {
        return this.customerNumber;
    }

    /** 设置 个人客户号 */
    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    /** 获取 证件号 */
    public String getIdCard() {
        return this.idCard;
    }

    /** 设置 证件号 */
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    /** 获取 姓名 */
    public String getUserName() {
        return this.userName;
    }

    /** 设置 姓名 */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /** 获取 服务月 */
    public Date getServiceMonth() {
        return this.serviceMonth;
    }

    /** 设置 服务月 */
    public void setServiceMonth(Date serviceMonth) {
        this.serviceMonth = serviceMonth;
    }

    /** 获取 缴金地 */
    public String getPayPlaceName() {
        return this.payPlaceName;
    }

    /** 设置 缴金地 */
    public void setPayPlaceName(String payPlaceName) {
        this.payPlaceName = payPlaceName;
    }

    /** 获取 经办机构 名 */
    public String getOrganizationName() {
        return this.organizationName;
    }

    /** 设置 经办机构 名 */
    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    /** 获取 办理方 名 */
    public String getTransactorName() {
        return this.transactorName;
    }

    /** 设置 办理方 名 */
    public void setTransactorName(String transactorName) {
        this.transactorName = transactorName;
    }

    /** 获取 缴纳比例 */
    public Double getPayRatio() {
        return this.payRatio;
    }

    /** 设置 缴纳比例 */
    public void setPayRatio(Double payRatio) {
        this.payRatio = payRatio;
    }

    /** 获取 缴纳基数 */
    public Double getPayCardinalNumber() {
        return this.payCardinalNumber;
    }

    /** 设置 缴纳基数 */
    public void setPayCardinalNumber(Double payCardinalNumber) {
        this.payCardinalNumber = payCardinalNumber;
    }

    /** 获取 公司缴纳部分 */
    public Double getCompanyTotalPay() {
        return this.companyTotalPay;
    }

    /** 设置 公司缴纳部分 */
    public void setCompanyTotalPay(Double companyTotalPay) {
        this.companyTotalPay = companyTotalPay;
    }

    /** 获取 个人缴纳部分 */
    public Double getMemberTotalPay() {
        return this.memberTotalPay;
    }

    /** 设置 个人缴纳部分 */
    public void setMemberTotalPay(Double memberTotalPay) {
        this.memberTotalPay = memberTotalPay;
    }

    /** 获取 账单起始月 */
    public Date getBillStartMonth() {
        return this.billStartMonth;
    }

    /** 设置 账单起始月 */
    public void setBillStartMonth(Date billStartMonth) {
        this.billStartMonth = billStartMonth;
    }

    /** 获取 服务结束月 */
    public Date getServiceEndMonth() {
        return this.serviceEndMonth;
    }

    /** 设置 服务结束月 */
    public void setServiceEndMonth(Date serviceEndMonth) {
        this.serviceEndMonth = serviceEndMonth;
    }
}
