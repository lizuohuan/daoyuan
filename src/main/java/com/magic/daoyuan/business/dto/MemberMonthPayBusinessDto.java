package com.magic.daoyuan.business.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 员工每月缴纳了服务费的业务 dto
 * @author lzh
 * @create 2017/11/24 11:01
 */
public class MemberMonthPayBusinessDto implements Serializable {


    /** 缴纳月份 */
    private Date serviceMonth;

    /** 当月缴纳的业务 */
    private String memberMonthPayBusinessStr;

    /** 员工id */
    private Integer memberId;

    /** 获取 缴纳月份 */
    public Date getServiceMonth() {
        return this.serviceMonth;
    }

    /** 设置 缴纳月份 */
    public void setServiceMonth(Date serviceMonth) {
        this.serviceMonth = serviceMonth;
    }

    /** 获取 当月缴纳的业务 */
    public String getMemberMonthPayBusinessStr() {
        return this.memberMonthPayBusinessStr;
    }

    /** 设置 当月缴纳的业务 */
    public void setMemberMonthPayBusinessStr(String memberMonthPayBusinessStr) {
        this.memberMonthPayBusinessStr = memberMonthPayBusinessStr;
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
