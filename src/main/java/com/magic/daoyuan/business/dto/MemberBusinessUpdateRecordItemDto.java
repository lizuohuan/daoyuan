package com.magic.daoyuan.business.dto;

import java.io.Serializable;

/**
 * 异动量dto
 * @author lzh
 * @create 2018/1/17 11:13
 */
public class MemberBusinessUpdateRecordItemDto implements Serializable {

    private Integer id;

    /** 实做-员工业务增减变记录表Id */
    private Integer memberBusinessUpdateRecordId;

    /** 员工id */
    private Integer memberId;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /** 获取 实做-员工业务增减变记录表Id */
    public Integer getMemberBusinessUpdateRecordId() {
        return this.memberBusinessUpdateRecordId;
    }

    /** 设置 实做-员工业务增减变记录表Id */
    public void setMemberBusinessUpdateRecordId(Integer memberBusinessUpdateRecordId) {
        this.memberBusinessUpdateRecordId = memberBusinessUpdateRecordId;
    }

    /** 获取 员工id */
    public Integer getMemberId() {
        return this.memberId;
    }

    /** 设置 员工id */
    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }
}
