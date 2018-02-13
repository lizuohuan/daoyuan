package com.magic.daoyuan.business.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Eric Xie on 2017/9/13 0013.
 */
public class CompanyBusiness implements Serializable {

    private Integer id;

    private Integer businessId;

    private Integer companyId;

    /** 账单制作方式 0：预收型  1：实做型 */
    private Integer billMadeMethod;

    /** 合作方式 如果是 社保、公积金则必选 0 : 代理 1 : 托管 2 : 托管及代理 */
    private Integer businessMethod;

    private BusinessMethod businessMethods;

    private List<CompanyBusinessItem> companyBusinessItems;

    /** 公司业务子类 */
    private List<BusinessMethod> businessMethodList;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBusinessId() {
        return this.businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public Integer getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    /** 获取 账单制作方式 0：预收型  1：实做型 */
    public Integer getBillMadeMethod() {
        return this.billMadeMethod;
    }

    /** 设置 账单制作方式 0：预收型  1：实做型 */
    public void setBillMadeMethod(Integer billMadeMethod) {
        this.billMadeMethod = billMadeMethod;
    }

    /** 获取 合作方式 如果是 社保、公积金则必选 0 : 代理 1 : 托管 2 : 托管及代理 */
    public Integer getBusinessMethod() {
        return this.businessMethod;
    }

    /** 设置 合作方式 如果是 社保、公积金则必选 0 : 代理 1 : 托管 2 : 托管及代理 */
    public void setBusinessMethod(Integer businessMethod) {
        this.businessMethod = businessMethod;
    }

    public BusinessMethod getBusinessMethods() {
        return this.businessMethods;
    }

    public void setBusinessMethods(BusinessMethod businessMethods) {
        this.businessMethods = businessMethods;
    }

    public List<CompanyBusinessItem> getCompanyBusinessItems() {
        return this.companyBusinessItems;
    }

    public void setCompanyBusinessItems(List<CompanyBusinessItem> companyBusinessItems) {
        this.companyBusinessItems = companyBusinessItems;
    }

    /** 获取 公司业务子类 */
    public List<BusinessMethod> getBusinessMethodList() {
        return this.businessMethodList;
    }

    /** 设置 公司业务子类 */
    public void setBusinessMethodList(List<BusinessMethod> businessMethodList) {
        this.businessMethodList = businessMethodList;
    }
}
