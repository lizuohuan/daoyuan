package com.magic.daoyuan.business.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Eric Xie on 2017/9/12 0012.
 */
public class Business implements Serializable {


    /** 主键ID */
    private Integer id;

    /** 是否有效 0 无效 1 有效 */
    private Integer isValid;

    /** 业务名称 */
    private String businessName;

    /** 业务描述 */
    private String describe;

    /** 子账单ID */
    private Integer companySonBillId;


    // 业务对象

    /** 如果业务是 公积金 或者 社保 则对象不为空 */
    private BusinessMethod businessMethod;

    /** 业务子类集合 */
    private List<BusinessItem> businessItems;


    /** 员工管理时 业务属性对象 */
    private MemberBusinessItem memberBusinessItem;


    /** 获取 业务名称 */
    public String getBusinessName() {
        return this.businessName;
    }

    /** 设置 业务名称 */
    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    /** 获取 业务描述 */
    public String getDescribe() {
        return this.describe;
    }

    /** 设置 业务描述 */
    public void setDescribe(String describe) {
        this.describe = describe;
    }

    /** 获取 子账单ID */
    public Integer getCompanySonBillId() {
        return this.companySonBillId;
    }

    /** 设置 子账单ID */
    public void setCompanySonBillId(Integer companySonBillId) {
        this.companySonBillId = companySonBillId;
    }

    /** 获取 如果业务是 公积金 或者 社保 则对象不为空 */
    public BusinessMethod getBusinessMethod() {
        return this.businessMethod;
    }

    /** 设置 如果业务是 公积金 或者 社保 则对象不为空 */
    public void setBusinessMethod(BusinessMethod businessMethod) {
        this.businessMethod = businessMethod;
    }

    /** 获取 业务子类集合 */
    public List<BusinessItem> getBusinessItems() {
        return this.businessItems;
    }

    /** 设置 业务子类集合 */
    public void setBusinessItems(List<BusinessItem> businessItems) {
        this.businessItems = businessItems;
    }

    /** 获取 员工管理时 业务属性对象 */
    public MemberBusinessItem getMemberBusinessItem() {
        return this.memberBusinessItem;
    }

    /** 设置 员工管理时 业务属性对象 */
    public void setMemberBusinessItem(MemberBusinessItem memberBusinessItem) {
        this.memberBusinessItem = memberBusinessItem;
    }

    /** 获取 主键ID */
    public Integer getId() {
        return this.id;
    }

    /** 设置 主键ID */
    public void setId(Integer id) {
        this.id = id;
    }

    /** 获取 是否有效 0 无效 1 有效 */
    public Integer getIsValid() {
        return this.isValid;
    }

    /** 设置 是否有效 0 无效 1 有效 */
    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }
}
