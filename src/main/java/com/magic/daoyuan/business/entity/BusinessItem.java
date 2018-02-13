package com.magic.daoyuan.business.entity;

import java.io.Serializable;

/**
 * 业务子类
 * Created by Eric Xie on 2017/9/22 0022.
 */
public class BusinessItem  implements Serializable {

    private Integer id;

    private Integer businessId;

    private String itemName;

    private String businessName;

    /** 险种类型 0 商业险  1 一次性险 */
    private Integer type;

    /** 当type为 0 时  服务费*/
    private Double serviceFee;

    /** 当type为 0 时 收费方式  0 按年 1 按月 */
    private Integer chargeMethod;

    /** 当type为 1 时 属于公司/员工 0 ：属于公司  1：属于员工 */
    private Integer isCompany;

    private Double price;

    private Integer isValid;

    private Integer companySonBillId;

    /** 公司id */
    private Integer companyId;

    public Integer getBusinessId() {
        return this.businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public String getItemName() {
        return this.itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getBusinessName() {
        return this.businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    /** 获取 险种类型 0 商业险  1 一次性险 */
    public Integer getType() {
        return this.type;
    }

    /** 设置 险种类型 0 商业险  1 一次性险 */
    public void setType(Integer type) {
        this.type = type;
    }

    /** 获取 当type为 0 时  服务费*/
    public Double getServiceFee() {
        return this.serviceFee;
    }

    /** 设置 当type为 0 时  服务费*/
    public void setServiceFee(Double serviceFee) {
        this.serviceFee = serviceFee;
    }

    /** 获取 当type为 0 时 收费方式  0 按年 1 按月 */
    public Integer getChargeMethod() {
        return this.chargeMethod;
    }

    /** 设置 当type为 0 时 收费方式  0 按年 1 按月 */
    public void setChargeMethod(Integer chargeMethod) {
        this.chargeMethod = chargeMethod;
    }

    /** 获取 当type为 1 时 属于公司/员工 0 ：属于公司  1：属于员工 */
    public Integer getIsCompany() {
        return this.isCompany;
    }

    /** 设置 当type为 1 时 属于公司/员工 0 ：属于公司  1：属于员工 */
    public void setIsCompany(Integer isCompany) {
        this.isCompany = isCompany;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BusinessItem that = (BusinessItem) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public Integer getIsValid() {
        return this.isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public Integer getCompanySonBillId() {
        return this.companySonBillId;
    }

    public void setCompanySonBillId(Integer companySonBillId) {
        this.companySonBillId = companySonBillId;
    }

    /** 获取 公司id */
    public Integer getCompanyId() {
        return this.companyId;
    }

    /** 设置 公司id */
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
}
