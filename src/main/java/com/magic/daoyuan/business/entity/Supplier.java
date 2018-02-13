package com.magic.daoyuan.business.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 供应商
 */
public class Supplier implements Serializable {

    private Integer id;

    /** 供应商名字 */
    private String supplierName;

    /** 出票顺序 0：先票  1：先款 */
    private Integer drawABillOrder;

    /** 服务费月序开始时间 */
    private Date serviceFeeStartTime;

    /** 服务费月序 周期 */
    private Integer serviceFeeCycle;

    /** 服务费最低收费 */
    private Double serviceFeeMin;

    /** 服务费最高收费 */
    private Double serviceFeeMax;

    /** 服务费配置ID */
    private Integer serviceFeeConfigId;

    /** 配置名称 */
    private String serviceFeeConfigName;

    /** 服务费配置集合 */
    private List<CompanyServiceFee> feeList;

    /** 当服务类别为  按服务类别 收费时，收费集合 */
    private List<BusinessServiceFee> serviceFeeList;

    /** 当服务类别为  按服务区域 收费时，收费集合 */
    private List<CompanyServicePayPlace> servicePayPlaceList;

    /** 百分比 */
    private Double percent;

    /** 是否纳入百分比 */
    private Integer isPercent;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /** 获取 供应商名字 */
    public String getSupplierName() {
        return this.supplierName;
    }

    /** 设置 供应商名字 */
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    /** 获取 出票顺序 0：先票  1：先款 */
    public Integer getDrawABillOrder() {
        return this.drawABillOrder;
    }

    /** 设置 出票顺序 0：先票  1：先款 */
    public void setDrawABillOrder(Integer drawABillOrder) {
        this.drawABillOrder = drawABillOrder;
    }

    /** 获取 服务费月序开始时间 */
    public Date getServiceFeeStartTime() {
        return this.serviceFeeStartTime;
    }

    /** 设置 服务费月序开始时间 */
    public void setServiceFeeStartTime(Date serviceFeeStartTime) {
        this.serviceFeeStartTime = serviceFeeStartTime;
    }

    /** 获取 服务费月序 周期 */
    public Integer getServiceFeeCycle() {
        return this.serviceFeeCycle;
    }

    /** 设置 服务费月序 周期 */
    public void setServiceFeeCycle(Integer serviceFeeCycle) {
        this.serviceFeeCycle = serviceFeeCycle;
    }

    /** 获取 服务费最低收费 */
    public Double getServiceFeeMin() {
        return this.serviceFeeMin;
    }

    /** 设置 服务费最低收费 */
    public void setServiceFeeMin(Double serviceFeeMin) {
        this.serviceFeeMin = serviceFeeMin;
    }

    /** 获取 服务费最高收费 */
    public Double getServiceFeeMax() {
        return this.serviceFeeMax;
    }

    /** 设置 服务费最高收费 */
    public void setServiceFeeMax(Double serviceFeeMax) {
        this.serviceFeeMax = serviceFeeMax;
    }

    /** 获取 服务费配置ID */
    public Integer getServiceFeeConfigId() {
        return this.serviceFeeConfigId;
    }

    /** 设置 服务费配置ID */
    public void setServiceFeeConfigId(Integer serviceFeeConfigId) {
        this.serviceFeeConfigId = serviceFeeConfigId;
    }

    /** 获取 服务费配置集合 */
    public List<CompanyServiceFee> getFeeList() {
        return this.feeList;
    }

    /** 设置 服务费配置集合 */
    public void setFeeList(List<CompanyServiceFee> feeList) {
        this.feeList = feeList;
    }

    /** 获取 当服务类别为  按服务类别 收费时，收费集合 */
    public List<BusinessServiceFee> getServiceFeeList() {
        return this.serviceFeeList;
    }

    /** 设置 当服务类别为  按服务类别 收费时，收费集合 */
    public void setServiceFeeList(List<BusinessServiceFee> serviceFeeList) {
        this.serviceFeeList = serviceFeeList;
    }

    /** 获取 百分比 */
    public Double getPercent() {
        return this.percent;
    }

    /** 设置 百分比 */
    public void setPercent(Double percent) {
        this.percent = percent;
    }

    /** 获取 是否纳入百分比 */
    public Integer getIsPercent() {
        return this.isPercent;
    }

    /** 设置 是否纳入百分比 */
    public void setIsPercent(Integer isPercent) {
        this.isPercent = isPercent;
    }

    /** 获取 当服务类别为  按服务区域 收费时，收费集合 */
    public List<CompanyServicePayPlace> getServicePayPlaceList() {
        return this.servicePayPlaceList;
    }

    /** 设置 当服务类别为  按服务区域 收费时，收费集合 */
    public void setServicePayPlaceList(List<CompanyServicePayPlace> servicePayPlaceList) {
        this.servicePayPlaceList = servicePayPlaceList;
    }

    /** 获取 配置名称 */
    public String getServiceFeeConfigName() {
        return this.serviceFeeConfigName;
    }

    /** 设置 配置名称 */
    public void setServiceFeeConfigName(String serviceFeeConfigName) {
        this.serviceFeeConfigName = serviceFeeConfigName;
    }
}