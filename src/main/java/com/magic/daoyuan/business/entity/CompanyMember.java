package com.magic.daoyuan.business.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Eric Xie on 2017/10/24 0024.
 */
public class CompanyMember extends BaseEntity implements Serializable {


    private Integer memberId;

    private Integer companyId;

    private Date createTime;


    public Integer getMemberId() {
        return this.memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
