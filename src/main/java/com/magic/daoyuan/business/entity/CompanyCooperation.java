package com.magic.daoyuan.business.entity;

import java.io.Serializable;
import java.util.Date;

/**
 *  公司合作状态记录 entity
 * Created by Eric Xie on 2017/12/21 0021.
 */
public class CompanyCooperation implements Serializable {


    /** ID */
    private Integer id;

    /** 合作状态 0 无合作  1 合作中 */
    private Integer cooperationStatus;

    /** 公司ID */
    private Integer companyId;

    /** 创建时间 */
    private Date createTime;


    /** 是否主动生成，非定时任务生成数据 1是 0 否 */
    private Integer isInitiative;

    /** 是否同行 */
    private Integer isPeer;


    // 业务
    /** 统计用 数量字段 */
    private int count;

    public Integer getIsInitiative() {
        return isInitiative;
    }

    public CompanyCooperation setIsInitiative(Integer isInitiative) {
        this.isInitiative = isInitiative;
        return this;
    }
    public int getCount() {
        return count;
    }

    public CompanyCooperation setCount(int count) {
        this.count = count;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public CompanyCooperation setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getCooperationStatus() {
        return cooperationStatus;
    }

    public CompanyCooperation setCooperationStatus(Integer cooperationStatus) {
        this.cooperationStatus = cooperationStatus;
        return this;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public CompanyCooperation setCompanyId(Integer companyId) {
        this.companyId = companyId;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public CompanyCooperation setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    /** 获取 是否同行 */
    public Integer getIsPeer() {
        return this.isPeer;
    }

    /** 设置 是否同行 */
    public void setIsPeer(Integer isPeer) {
        this.isPeer = isPeer;
    }
}
