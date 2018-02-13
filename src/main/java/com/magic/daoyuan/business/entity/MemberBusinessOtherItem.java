package com.magic.daoyuan.business.entity;

import java.io.Serializable;

/**
 * Created by Eric Xie on 2017/10/12 0012.
 */
public class MemberBusinessOtherItem extends BaseEntity implements Serializable {


    private Integer memberBusinessId;

    private Integer businessItemId;

    /** 员工所属的子账单ID */
    private Integer companySonBillId;

    public Integer getMemberBusinessId() {
        return this.memberBusinessId;
    }

    public void setMemberBusinessId(Integer memberBusinessId) {
        this.memberBusinessId = memberBusinessId;
    }

    public Integer getBusinessItemId() {
        return this.businessItemId;
    }

    public void setBusinessItemId(Integer businessItemId) {
        this.businessItemId = businessItemId;
    }

    /** 获取 员工所属的子账单ID */
    public Integer getCompanySonBillId() {
        return this.companySonBillId;
    }

    /** 设置 员工所属的子账单ID */
    public void setCompanySonBillId(Integer companySonBillId) {
        this.companySonBillId = companySonBillId;
    }
}
