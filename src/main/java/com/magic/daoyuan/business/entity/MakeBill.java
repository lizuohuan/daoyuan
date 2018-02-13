package com.magic.daoyuan.business.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 开票据
 * Created by Eric Xie on 2017/11/18 0018.
 */
public class MakeBill implements Serializable,Cloneable {

    private Integer id;

    /** 认款记录ID */
    private Integer confirmFundId;

    /** 开票时间 */
    private Date makeBillDate;

    /** 快递单号 */
    private String expressNumber;

    /** 发票号 */
    private String invoiceNumber;

    /** 票据状态 3001:未开票、3002:已开票、3003:已邮寄 */
    private Integer status;

    /** 公司的票据信息ID */
    private Integer companyBillInfoId;

    private Date createTime;

    /** 票据所开金额 */
    private Double amountOfBill;

    /** 差额 */
    private Double amount;

    /** 开票的商品名称
     * 1:劳务外包费 税收：6% 税收编码：304080399
     * 2:人事代理费 税收：5% 税收编码：304080299
     * 3:服务费     税收：6% 税收编码：无 */
    private Integer shopNameId;

    /** 备注 */
    private String remark;

    /** 账单年月 */
    private Date billMonth;

    /** 快递信息ID */
    private Integer expressInfoId;

    // 业务字段

    /** 公司名称 */
    private String companyName;

    /** 公司ID */
    private Integer companyId;

    /** 客服名称 */
    private String serviceName;

    /** 账单年月 */
    private Date billDate;

    /** 票据信息 */
    private CompanyBillInfo billInfo;


    /** 发票抬头 */
    private String title;

    /** 税号 */
    private String taxNumber;

    /** 票据类型 0：专票 1：普票  2：收据 */
    private Integer billType;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return (MakeBill)super.clone();
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /** 获取 认款记录ID */
    public Integer getConfirmFundId() {
        return this.confirmFundId;
    }

    /** 设置 认款记录ID */
    public void setConfirmFundId(Integer confirmFundId) {
        this.confirmFundId = confirmFundId;
    }

    /** 获取 开票时间 */
    public Date getMakeBillDate() {
        return this.makeBillDate;
    }

    /** 设置 开票时间 */
    public void setMakeBillDate(Date makeBillDate) {
        this.makeBillDate = makeBillDate;
    }

    /** 获取 快递单号 */
    public String getExpressNumber() {
        return this.expressNumber;
    }

    /** 设置 快递单号 */
    public void setExpressNumber(String expressNumber) {
        this.expressNumber = expressNumber;
    }

    /** 获取 发票号 */
    public String getInvoiceNumber() {
        return this.invoiceNumber;
    }

    /** 设置 发票号 */
    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    /** 获取 票据状态 3001:未开票、3002:已开票、3003:已邮寄 */
    public Integer getStatus() {
        return this.status;
    }

    /** 设置 票据状态 3001:未开票、3002:已开票、3003:已邮寄 */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /** 获取 公司的票据信息ID */
    public Integer getCompanyBillInfoId() {
        return this.companyBillInfoId;
    }

    /** 设置 公司的票据信息ID */
    public void setCompanyBillInfoId(Integer companyBillInfoId) {
        this.companyBillInfoId = companyBillInfoId;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /** 获取 公司名称 */
    public String getCompanyName() {
        return this.companyName;
    }

    /** 设置 公司名称 */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /** 获取 客服名称 */
    public String getServiceName() {
        return this.serviceName;
    }

    /** 设置 客服名称 */
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    /** 获取 账单年月 */
    public Date getBillDate() {
        return this.billDate;
    }

    /** 设置 账单年月 */
    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    /** 获取 金额 */
    public Double getAmount() {
        return this.amount;
    }

    /** 设置 金额 */
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    /** 获取 票据所开金额 */
    public Double getAmountOfBill() {
        return this.amountOfBill;
    }

    /** 设置 票据所开金额 */
    public void setAmountOfBill(Double amountOfBill) {
        this.amountOfBill = amountOfBill;
    }

    /** 开票的商品名称
     * 1:劳务外包费 税收：6% 税收编码：304080399
     * 2:人事代理费 税收：5% 税收编码：304080299
     * 3:服务费     税收：6% 税收编码：无 */
    public Integer getShopNameId() {
        return this.shopNameId;
    }

    /** 开票的商品名称
     * 1:劳务外包费 税收：6% 税收编码：304080399
     * 2:人事代理费 税收：5% 税收编码：304080299
     * 3:服务费     税收：6% 税收编码：无 */
    public void setShopNameId(Integer shopNameId) {
        this.shopNameId = shopNameId;
    }

    /** 获取 备注 */
    public String getRemark() {
        return this.remark;
    }

    /** 设置 备注 */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /** 获取 票据信息 */
    public CompanyBillInfo getBillInfo() {
        return this.billInfo;
    }

    /** 设置 票据信息 */
    public void setBillInfo(CompanyBillInfo billInfo) {
        this.billInfo = billInfo;
    }

    /** 获取 账单年月 */
    public Date getBillMonth() {
        return this.billMonth;
    }

    /** 设置 账单年月 */
    public void setBillMonth(Date billMonth) {
        this.billMonth = billMonth;
    }

    public Integer getExpressInfoId() {
        return this.expressInfoId;
    }

    public void setExpressInfoId(Integer expressInfoId) {
        this.expressInfoId = expressInfoId;
    }

    /** 获取 发票抬头 */
    public String getTitle() {
        return this.title;
    }

    /** 设置 发票抬头 */
    public void setTitle(String title) {
        this.title = title;
    }

    /** 获取 税号 */
    public String getTaxNumber() {
        return this.taxNumber;
    }

    /** 设置 税号 */
    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    /** 获取 票据类型 0：专票 1：普票  2：收据 */
    public Integer getBillType() {
        return this.billType;
    }

    /** 设置 票据类型 0：专票 1：普票  2：收据 */
    public void setBillType(Integer billType) {
        this.billType = billType;
    }

    /** 获取 公司ID */
    public Integer getCompanyId() {
        return this.companyId;
    }

    /** 设置 公司ID */
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
}
