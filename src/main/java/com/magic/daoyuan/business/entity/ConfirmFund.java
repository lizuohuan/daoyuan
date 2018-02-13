package com.magic.daoyuan.business.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Eric Xie on 2017/10/31 0031.
 */
public class ConfirmFund extends BaseEntity implements Serializable {


    /** 公司ID */
    private Integer companyId;

    /** 账单金额 */
    private Double billAmount;

    /** 认款金额 */
    private Double confirmAmount;

    /** 本次 账单金额 - 认款金额 的余额 */
    private Double balance;

    /** 处理方式 ：
     *  0、大于0；纳入次月账单、退回客户（自动生成出款单）
     *  1、小于0；纳入次月账单、追回尾款（线下）
     *  2、等于0；足额到款，不做处理。
     *
     *  值： 0 纳入次月账单  1 退回客户（自动生成出款单）
     *      2 追回尾款（线下） 3 不做处理
     */
    private Integer handleMethod;

    /** 认款方式：0：自动认款  1：手动拆分认款 */
    private Integer confirmMethod;

    /** 银行账单导入 到款记录ID */
    private Integer confirmMoneyRecordId;

    /** 用户ID 如果自动认款 则没有记录 */
    private Integer createUserId;

    /** 创建时间 */
    private Date createTime;

    /** 预计回款时间 */
    private Date returnTime;

    private String createUserName;

    private String companyName;

    /** 业务字段，0 先款后票  1：先票后款*/
    private Integer isFirstBill;

    private List<ConfirmFundTotalBill> confirmFundTotalBillList;

    /** 客服id */
    private Integer userId;


    public Integer getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Double getBillAmount() {
        return this.billAmount;
    }

    public void setBillAmount(Double billAmount) {
        this.billAmount = billAmount;
    }

    public Double getConfirmAmount() {
        return this.confirmAmount;
    }

    public void setConfirmAmount(Double confirmAmount) {
        this.confirmAmount = confirmAmount;
    }

    public Integer getHandleMethod() {
        return this.handleMethod;
    }

    public void setHandleMethod(Integer handleMethod) {
        this.handleMethod = handleMethod;
    }

    public Integer getConfirmMethod() {
        return this.confirmMethod;
    }

    public void setConfirmMethod(Integer confirmMethod) {
        this.confirmMethod = confirmMethod;
    }



    public Integer getCreateUserId() {
        return this.createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<ConfirmFundTotalBill> getConfirmFundTotalBillList() {
        return this.confirmFundTotalBillList;
    }

    public void setConfirmFundTotalBillList(List<ConfirmFundTotalBill> confirmFundTotalBillList) {
        this.confirmFundTotalBillList = confirmFundTotalBillList;
    }

    /** 获取 银行账单导入 到款记录ID */
    public Integer getConfirmMoneyRecordId() {
        return this.confirmMoneyRecordId;
    }

    /** 设置 银行账单导入 到款记录ID */
    public void setConfirmMoneyRecordId(Integer confirmMoneyRecordId) {
        this.confirmMoneyRecordId = confirmMoneyRecordId;
    }

    public String getCreateUserName() {
        return this.createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /** 获取 本次 账单金额 - 认款金额 的余额 */
    public Double getBalance() {
        return this.balance;
    }

    /** 设置 本次 账单金额 - 认款金额 的余额 */
    public void setBalance(Double balance) {
        this.balance = balance;
    }

    /** 获取 业务字段，0 先款后票  1：先票后款*/
    public Integer getIsFirstBill() {
        return this.isFirstBill;
    }

    /** 设置 业务字段，0 先款后票  1：先票后款*/
    public void setIsFirstBill(Integer isFirstBill) {
        this.isFirstBill = isFirstBill;
    }

    /** 获取 客服id */
    public Integer getUserId() {
        return this.userId;
    }

    /** 设置 客服id */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /** 获取 预计回款时间 */
    public Date getReturnTime() {
        return this.returnTime;
    }

    /** 设置 预计回款时间 */
    public void setReturnTime(Date returnTime) {
        this.returnTime = returnTime;
    }
}
