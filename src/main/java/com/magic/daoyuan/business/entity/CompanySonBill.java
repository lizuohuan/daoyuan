package com.magic.daoyuan.business.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 公司子账单
 *
 * @author lzh
 * @create 2017/10/13 9:35
 */
public class CompanySonBill implements Serializable {


    private Integer id;

    /** 公司id */
    private Integer companyId;

    /** 联系人id */
    private Integer contactsId;

    /** 票据id */
    private Integer companyBillInfoId;

    /** 子账单名 */
    private String sonBillName;

    /** 是否有效 */
    private Integer isValid;

    /********  业务字段 不进行数据库持久化    *******/
    /** 公司名 */
    private String companyName;

    /** 联系人名 */
    private String contactsName;

    /** 票据名 */
    private String companyBillInfoName;

    /** 票据 */
    private CompanyBillInfo billInfo;

    /** 汇总账单集合 */
    private List<CompanySonTotalBill> companySonTotalBillList;

    /** 社保集合 未绑定子账单子类的 */
    private List<SocialSecurityInfo> socialSecurityInfoList;

    /** 公积金集合 未绑定子账单子类的 */
    private List<ReservedFundsInfo> reservedFundsInfoList;

    /** 此账单下绑定的员工业务 */
    private List<MemberBusiness> memberBusinessList;

    /** 工资集合 未绑定子账单子类的 */
    private List<SalaryInfo> salaryInfoList;

    /** 合作方式 0：普通 1：派遣  2：外包 */
    private Integer waysOfCooperation;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /** 获取 公司id */
    public Integer getCompanyId() {
        return this.companyId;
    }

    /** 设置 公司id */
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    /** 获取 联系人id */
    public Integer getContactsId() {
        return this.contactsId;
    }

    /** 设置 联系人id */
    public void setContactsId(Integer contactsId) {
        this.contactsId = contactsId;
    }

    /** 获取 票据id */
    public Integer getCompanyBillInfoId() {
        return this.companyBillInfoId;
    }

    /** 设置 票据id */
    public void setCompanyBillInfoId(Integer companyBillInfoId) {
        this.companyBillInfoId = companyBillInfoId;
    }

    /** 获取 公司名 */
    public String getCompanyName() {
        return this.companyName;
    }

    /** 设置 公司名 */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }


    /** 获取 子账单名 */
    public String getSonBillName() {
        return this.sonBillName;
    }

    /** 设置 子账单名 */
    public void setSonBillName(String sonBillName) {
        this.sonBillName = sonBillName;
    }

    /** 获取 联系人名 */
    public String getContactsName() {
        return this.contactsName;
    }

    /** 设置 联系人名 */
    public void setContactsName(String contactsName) {
        this.contactsName = contactsName;
    }

    /** 获取 票据名 */
    public String getCompanyBillInfoName() {
        return this.companyBillInfoName;
    }

    /** 设置 票据名 */
    public void setCompanyBillInfoName(String companyBillInfoName) {
        this.companyBillInfoName = companyBillInfoName;
    }

    /** 获取 是否有效 */
    public Integer getIsValid() {
        return this.isValid;
    }

    /** 设置 是否有效 */
    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompanySonBill that = (CompanySonBill) o;

        return companyId.equals(that.companyId);

    }

    @Override
    public int hashCode() {
        return companyId.hashCode();
    }

    /** 获取 票据 */
    public CompanyBillInfo getBillInfo() {
        return this.billInfo;
    }

    /** 设置 票据 */
    public void setBillInfo(CompanyBillInfo billInfo) {
        this.billInfo = billInfo;
    }

    /** 获取 汇总账单集合 */
    public List<CompanySonTotalBill> getCompanySonTotalBillList() {
        return this.companySonTotalBillList;
    }

    /** 设置 汇总账单集合 */
    public void setCompanySonTotalBillList(List<CompanySonTotalBill> companySonTotalBillList) {
        this.companySonTotalBillList = companySonTotalBillList;
    }

    /** 获取 社保集合 未绑定子账单子类的 */
    public List<SocialSecurityInfo> getSocialSecurityInfoList() {
        return this.socialSecurityInfoList;
    }

    /** 设置 社保集合 未绑定子账单子类的 */
    public void setSocialSecurityInfoList(List<SocialSecurityInfo> socialSecurityInfoList) {
        this.socialSecurityInfoList = socialSecurityInfoList;
    }

    /** 获取 公积金集合 未绑定子账单子类的 */
    public List<ReservedFundsInfo> getReservedFundsInfoList() {
        return this.reservedFundsInfoList;
    }

    /** 设置 公积金集合 未绑定子账单子类的 */
    public void setReservedFundsInfoList(List<ReservedFundsInfo> reservedFundsInfoList) {
        this.reservedFundsInfoList = reservedFundsInfoList;
    }

    /** 获取 此账单下绑定的员工业务 */
    public List<MemberBusiness> getMemberBusinessList() {
        return this.memberBusinessList;
    }

    /** 设置 此账单下绑定的员工业务 */
    public void setMemberBusinessList(List<MemberBusiness> memberBusinessList) {
        this.memberBusinessList = memberBusinessList;
    }

    /** 获取 工资集合 未绑定子账单子类的 */
    public List<SalaryInfo> getSalaryInfoList() {
        return this.salaryInfoList;
    }

    /** 设置 工资集合 未绑定子账单子类的 */
    public void setSalaryInfoList(List<SalaryInfo> salaryInfoList) {
        this.salaryInfoList = salaryInfoList;
    }

    /** 获取 合作方式 0：普通 1：派遣  2：外包 */
    public Integer getWaysOfCooperation() {
        return this.waysOfCooperation;
    }

    /** 设置 合作方式 0：普通 1：派遣  2：外包 */
    public void setWaysOfCooperation(Integer waysOfCooperation) {
        this.waysOfCooperation = waysOfCooperation;
    }
}
