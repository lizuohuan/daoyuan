package com.magic.daoyuan.business.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 公司 合作方式 entity
 * Created by Eric Xie on 2017/11/2 0002.
 */
public class CompanyCooperationMethod extends BaseEntity implements Serializable {

    /** 公司ID */
    private Integer companyId;

    /** 合作方式 0：普通 1：派遣  2：外包 */
    private Integer cooperationMethodId;

    /** 服务配置ID */
    private Integer serviceFeeConfigId;

    /** 服务配置的名称 */
    private String serviceFeeConfigName;

    /** 服务月序开始时间 */
    private Date serviceFeeStartTime;

    /** 服务月序周期 单位：月 */
    private Integer serviceFeeCycle;

    /** 服务费区间最小 */
    private Double serviceFeeMin;

    /** 服务费区间最大 */
    private Double serviceFeeMax;

    /** 服务费是否纳入百分比计算 0 否  1 是 */
    private Integer isPercent;

    /** 如果纳入百分比计算 则百分比数值 */
    private Double percent;
    /** 异动量的基础服务费 */
    private Double baseFee;

    /** 服务类别集合 */
    private List<CompanyCooperationBusinessServiceFee> businessServiceFeeList;

    /** 合作方式的阶梯收费集合 */
    private List<CompanyCooperationServiceFee> serviceFeeList;

    /** 服务区域 缴金地配置集合 */
    private List<CompanyCooperationServicePayPlace> payPlaceList;


    public Integer getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getCooperationMethodId() {
        return this.cooperationMethodId;
    }

    public void setCooperationMethodId(Integer cooperationMethodId) {
        this.cooperationMethodId = cooperationMethodId;
    }

    public Integer getServiceFeeConfigId() {
        return this.serviceFeeConfigId;
    }

    public void setServiceFeeConfigId(Integer serviceFeeConfigId) {
        this.serviceFeeConfigId = serviceFeeConfigId;
    }

    public Date getServiceFeeStartTime() {
        return this.serviceFeeStartTime;
    }

    public void setServiceFeeStartTime(Date serviceFeeStartTime) {
        this.serviceFeeStartTime = serviceFeeStartTime;
    }

    public Integer getServiceFeeCycle() {
        return this.serviceFeeCycle;
    }

    public void setServiceFeeCycle(Integer serviceFeeCycle) {
        this.serviceFeeCycle = serviceFeeCycle;
    }

    public Double getServiceFeeMin() {
        return this.serviceFeeMin;
    }

    public void setServiceFeeMin(Double serviceFeeMin) {
        this.serviceFeeMin = serviceFeeMin;
    }

    public Double getServiceFeeMax() {
        return this.serviceFeeMax;
    }

    public void setServiceFeeMax(Double serviceFeeMax) {
        this.serviceFeeMax = serviceFeeMax;
    }

    /** 获取 服务费是否纳入百分比计算 0 否  1 是 */
    public Integer getIsPercent() {
        return this.isPercent;
    }

    /** 设置 服务费是否纳入百分比计算 0 否  1 是 */
    public void setIsPercent(Integer isPercent) {
        this.isPercent = isPercent;
    }

    /** 获取 如果纳入百分比计算 则百分比数值 */
    public Double getPercent() {
        return this.percent;
    }

    /** 设置 如果纳入百分比计算 则百分比数值 */
    public void setPercent(Double percent) {
        this.percent = percent;
    }

    /** 获取 服务类别集合 */
    public List<CompanyCooperationBusinessServiceFee> getBusinessServiceFeeList() {
        return this.businessServiceFeeList;
    }

    /** 设置 服务类别集合 */
    public void setBusinessServiceFeeList(List<CompanyCooperationBusinessServiceFee> businessServiceFeeList) {
        this.businessServiceFeeList = businessServiceFeeList;
    }

    /** 获取 合作方式的阶梯收费集合 */
    public List<CompanyCooperationServiceFee> getServiceFeeList() {
        return this.serviceFeeList;
    }

    /** 设置 合作方式的阶梯收费集合 */
    public void setServiceFeeList(List<CompanyCooperationServiceFee> serviceFeeList) {
        this.serviceFeeList = serviceFeeList;
    }

    /** 获取 服务区域 缴金地配置集合 */
    public List<CompanyCooperationServicePayPlace> getPayPlaceList() {
        return this.payPlaceList;
    }

    /** 设置 服务区域 缴金地配置集合 */
    public void setPayPlaceList(List<CompanyCooperationServicePayPlace> payPlaceList) {
        this.payPlaceList = payPlaceList;
    }

    /** 获取 异动量的基础服务费 */
    public Double getBaseFee() {
        return this.baseFee;
    }

    /** 设置 异动量的基础服务费 */
    public void setBaseFee(Double baseFee) {
        this.baseFee = baseFee;
    }

    /** 获取 服务配置的名称 */
    public String getServiceFeeConfigName() {
        return this.serviceFeeConfigName;
    }

    /** 设置 服务配置的名称 */
    public void setServiceFeeConfigName(String serviceFeeConfigName) {
        this.serviceFeeConfigName = serviceFeeConfigName;
    }
}
