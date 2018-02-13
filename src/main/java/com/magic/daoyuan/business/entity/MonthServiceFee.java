package com.magic.daoyuan.business.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 每月服务费
 * @author lzh
 * @create 2017/10/23 19:36
 */
public class MonthServiceFee implements Serializable {


    private Integer id;

    /** 缴纳月份 */
    private Date month;

    /** 服务费 */
    private Double serviceFee;

    /** 汇总账单id */
    private Integer companySonTotalBillId;

    /** 创建时间 */
    private Date createTime;

    /** 服务费配置id */
    private Integer serviceFeeConfigId;

    /** 公司id */
    private Integer companyId;

    /** 服务费类型及配置json */
    private String companyCooperationMethodJson;

    /** 子账单id */
    private Integer companySonBillId;

    /** 合作方式 0：普通 1：派遣  2：外包 */
    private Integer waysOfCooperation;


    /********  业务字段 不进行数据库持久化    *******/

    /** 服务费明细集合 */
    private List<MonthServiceFeeDetail> monthServiceFeeDetailList;


    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /** 获取 缴纳月份 */
    public Date getMonth() {
        return this.month;
    }

    /** 设置 缴纳月份 */
    public void setMonth(Date month) {
        this.month = month;
    }

    /** 获取 服务费 */
    public Double getServiceFee() {
        return this.serviceFee;
    }

    /** 设置 服务费 */
    public void setServiceFee(Double serviceFee) {
        this.serviceFee = serviceFee;
    }

    /** 获取 汇总账单id */
    public Integer getCompanySonTotalBillId() {
        return this.companySonTotalBillId;
    }

    /** 设置 汇总账单id */
    public void setCompanySonTotalBillId(Integer companySonTotalBillId) {
        this.companySonTotalBillId = companySonTotalBillId;
    }

    /** 获取 创建时间 */
    public Date getCreateTime() {
        return this.createTime;
    }

    /** 设置 创建时间 */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /** 获取 服务费配置id */
    public Integer getServiceFeeConfigId() {
        return this.serviceFeeConfigId;
    }

    /** 设置 服务费配置id */
    public void setServiceFeeConfigId(Integer serviceFeeConfigId) {
        this.serviceFeeConfigId = serviceFeeConfigId;
    }

    /** 获取 公司id */
    public Integer getCompanyId() {
        return this.companyId;
    }

    /** 设置 公司id */
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    /** 获取 服务费明细集合 */
    public List<MonthServiceFeeDetail> getMonthServiceFeeDetailList() {
        return this.monthServiceFeeDetailList;
    }

    /** 设置 服务费明细集合 */
    public void setMonthServiceFeeDetailList(List<MonthServiceFeeDetail> monthServiceFeeDetailList) {
        this.monthServiceFeeDetailList = monthServiceFeeDetailList;
    }

    /** 获取 服务费类型及配置json */
    public String getCompanyCooperationMethodJson() {
        return this.companyCooperationMethodJson;
    }

    /** 设置 服务费类型及配置json */
    public void setCompanyCooperationMethodJson(String companyCooperationMethodJson) {
        this.companyCooperationMethodJson = companyCooperationMethodJson;
    }

    /** 获取 子账单id */
    public Integer getCompanySonBillId() {
        return this.companySonBillId;
    }

    /** 设置 子账单id */
    public void setCompanySonBillId(Integer companySonBillId) {
        this.companySonBillId = companySonBillId;
    }

    /** 获取 合作方式 0：普通 1：派遣  2：外包 */
    public Integer getWaysOfCooperation() {
        return this.waysOfCooperation;
    }

    /** 设置 合作方式 0：普通 1：派遣  2：外包 */
    public void setWaysOfCooperation(Integer waysOfCooperation) {
        this.waysOfCooperation = waysOfCooperation;
    }
}
