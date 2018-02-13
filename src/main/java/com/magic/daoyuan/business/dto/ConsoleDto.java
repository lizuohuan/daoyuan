package com.magic.daoyuan.business.dto;

/**
 * 控制台右下角
 * @author lzh
 * @create 2017/12/25 15:30
 */
public class ConsoleDto {


    /** 服务人数 */
    private Integer memberNum;
    /** 服务公司数 */
    private Integer companyNum;
    /** 本月新增人数 */
    private Integer addMemberNum;
    /** 本月减少人数 */
    private Integer delMemberNum;
    /** 新增公司数 */
    private Integer addCompanyNum;
    /** 终止公司数 */
    private Integer delCompanyNum;
    /** 未确认账单公司 */
    private Integer unconfirmedBillCompanyNum;
    /** 未完成核销公司 */
    private Integer unCancelAfterVerificationCompanyNum;
    /** 当月核销率 */
    private Double cancelAfterVerificationForNewMonthRate;


    /** 获取 服务人数 */
    public Integer getMemberNum() {
        return this.memberNum;
    }

    /** 设置 服务人数 */
    public void setMemberNum(Integer memberNum) {
        this.memberNum = memberNum;
    }

    /** 获取 服务公司数 */
    public Integer getCompanyNum() {
        return this.companyNum;
    }

    /** 设置 服务公司数 */
    public void setCompanyNum(Integer companyNum) {
        this.companyNum = companyNum;
    }

    /** 获取 本月新增人数 */
    public Integer getAddMemberNum() {
        return this.addMemberNum;
    }

    /** 设置 本月新增人数 */
    public void setAddMemberNum(Integer addMemberNum) {
        this.addMemberNum = addMemberNum;
    }

    /** 获取 本月减少人数 */
    public Integer getDelMemberNum() {
        return this.delMemberNum;
    }

    /** 设置 本月减少人数 */
    public void setDelMemberNum(Integer delMemberNum) {
        this.delMemberNum = delMemberNum;
    }

    /** 获取 新增公司数 */
    public Integer getAddCompanyNum() {
        return this.addCompanyNum;
    }

    /** 设置 新增公司数 */
    public void setAddCompanyNum(Integer addCompanyNum) {
        this.addCompanyNum = addCompanyNum;
    }

    /** 获取 终止公司数 */
    public Integer getDelCompanyNum() {
        return this.delCompanyNum;
    }

    /** 设置 终止公司数 */
    public void setDelCompanyNum(Integer delCompanyNum) {
        this.delCompanyNum = delCompanyNum;
    }

    /** 获取 未确认账单公司 */
    public Integer getUnconfirmedBillCompanyNum() {
        return this.unconfirmedBillCompanyNum;
    }

    /** 设置 未确认账单公司 */
    public void setUnconfirmedBillCompanyNum(Integer unconfirmedBillCompanyNum) {
        this.unconfirmedBillCompanyNum = unconfirmedBillCompanyNum;
    }

    /** 获取 未完成核销公司 */
    public Integer getUnCancelAfterVerificationCompanyNum() {
        return this.unCancelAfterVerificationCompanyNum;
    }

    /** 设置 未完成核销公司 */
    public void setUnCancelAfterVerificationCompanyNum(Integer unCancelAfterVerificationCompanyNum) {
        this.unCancelAfterVerificationCompanyNum = unCancelAfterVerificationCompanyNum;
    }

    /** 获取 当月核销率 */
    public Double getCancelAfterVerificationForNewMonthRate() {
        return this.cancelAfterVerificationForNewMonthRate;
    }

    /** 设置 当月核销率 */
    public void setCancelAfterVerificationForNewMonthRate(Double cancelAfterVerificationForNewMonthRate) {
        this.cancelAfterVerificationForNewMonthRate = cancelAfterVerificationForNewMonthRate;
    }
}
