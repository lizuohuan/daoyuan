package com.magic.daoyuan.business.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 员工与业务
 * Created by Eric Xie on 2017/10/12 0012.
 */
public class MemberBusiness extends BaseEntity implements Serializable {

    private Integer memberId;

    private Integer businessId;

    /** 员工所属的子账单ID */
    private Integer companySonBillId;

    /********  业务字段 不进行数据库持久化    *******/
    /** 员工 */
    private Member member;
    /** 员工业务 社保公积金表 */
    private MemberBusinessItem memberBusinessItem;

    /** 员工与业务子类 */
    private List<MemberBusinessOtherItem> otherItemList;

    public Integer getMemberId() {
        return this.memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getBusinessId() {
        return this.businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    /** 获取 员工所属的子账单ID */
    public Integer getCompanySonBillId() {
        return this.companySonBillId;
    }

    /** 设置 员工所属的子账单ID */
    public void setCompanySonBillId(Integer companySonBillId) {
        this.companySonBillId = companySonBillId;
    }

    /** 获取 员工 */
    public Member getMember() {
        return this.member;
    }

    /** 设置 员工 */
    public void setMember(Member member) {
        this.member = member;
    }

    /** 获取 员工业务 社保公积金表 */
    public MemberBusinessItem getMemberBusinessItem() {
        return this.memberBusinessItem;
    }

    /** 设置 员工业务 社保公积金表 */
    public void setMemberBusinessItem(MemberBusinessItem memberBusinessItem) {
        this.memberBusinessItem = memberBusinessItem;
    }

    /** 获取 员工与业务子类 */
    public List<MemberBusinessOtherItem> getOtherItemList() {
        return this.otherItemList;
    }

    /** 设置 员工与业务子类 */
    public void setOtherItemList(List<MemberBusinessOtherItem> otherItemList) {
        this.otherItemList = otherItemList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MemberBusiness business = (MemberBusiness) o;

        if (!memberId.equals(business.memberId)) return false;
        return businessId.equals(business.businessId);

    }

    @Override
    public int hashCode() {
        int result = memberId.hashCode();
        result = 31 * result + businessId.hashCode();
        return result;
    }
}
