package com.magic.daoyuan.business.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 账单-一次性业务
 * Created by Eric Xie on 2017/10/25 0025.
 */
public class BusinessYc implements Serializable {


    private Integer id;

    private Integer businessItemId;

    private String businessItemName;

    private Integer companySonTotalBillId;

    private Date createTime;

    private List<BusinessYcItem> businessYcItemList;


    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBusinessItemId() {
        return this.businessItemId;
    }

    public void setBusinessItemId(Integer businessItemId) {
        this.businessItemId = businessItemId;
    }

    public Integer getCompanySonTotalBillId() {
        return this.companySonTotalBillId;
    }

    public void setCompanySonTotalBillId(Integer companySonTotalBillId) {
        this.companySonTotalBillId = companySonTotalBillId;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<BusinessYcItem> getBusinessYcItemList() {
        return this.businessYcItemList;
    }

    public void setBusinessYcItemList(List<BusinessYcItem> businessYcItemList) {
        this.businessYcItemList = businessYcItemList;
    }

    public String getBusinessItemName() {
        return this.businessItemName;
    }

    public void setBusinessItemName(String businessItemName) {
        this.businessItemName = businessItemName;
    }
}
