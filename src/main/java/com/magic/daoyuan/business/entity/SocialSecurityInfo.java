package com.magic.daoyuan.business.entity;

import com.magic.daoyuan.business.util.Timestamp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * 账单-社保缴纳明细
 * @author lzh
 * @create 2017/10/17 17:31
 */
public class SocialSecurityInfo implements Serializable {

    private Integer id;

    /** 姓名 */
    private String userName;

    /** 证件类型 */
    private Integer certificateType;

    /** 证件编号 */
    private String idCard;

    /** 社保编码 */
    private String socialSecurityNum;

    /** 缴金地-经办机构 名 */
    private String payPlaceOrganizationName;

    /** 缴金地 名 */
    private String payPlaceName;

    /** 经办机构 名 */
    private String organizationName;

    /** 办理方 名 */
    private String transactorName;


    /** 险种档次名 */
    private String insuranceLevelName;

    /** 开始缴纳年月 */
    private Date beginPayYM;

    /** 本次服务年月 */
    private Date serviceNowYM;

    /** 缴纳基数 */
    private Double payCardinalNumber;

    /** 公司缴纳总金额 */
    private Double companyTotalPay;

    /** 公司实际缴纳金额 */
    private Double practicalCompanyTotalPay;

    /** 个人缴纳总金额 */
    private Double memberTotalPay;

    /** 员工实际缴纳金额 */
    private Double practicalMemberTotalPay;

    /** 子账单子类id */
    private Integer companySonBillItemId;

    /** 员工id */
    private Integer memberId;

    /** 子账单id */
    private Integer companySonBillId;

    /** 账单制作方式 0：预收型  1：实做型 */
    private Integer billMadeMethod;

    /** 是否稽核 0:否 1:是 */
    private Integer isAudit;

    /** 处理方案 1：纳入次月账单 2：退回客户  10：纳入次月并已在次月确认账单 */
    private Integer processingScheme;

    /** 纳入次月处理的账单月 */
    private Date affirmBillMonth;

    /** 账单月 */
    private Date billMonth;

    /** 实做时间 */
    private Date realDoTime;

    /** 是否上传拷盘 */
    private Integer isUploadKaoPan;

    /** 税费 */
    private Double taxPrice;

    /********  业务字段 不进行数据库持久化    *******/

    /** 员工合作方式  开票据的时候应用 */
    private Integer waysOfCooperation;

    /** 公司名 */
    private String companyName;

    /** 汇总账单的账单生成时间 */
    private Date billMonthCstb;

    /** 服务配置id */
    private Integer serviceFeeConfigId;

    /** 账单-社保明细 缴纳险种 */
    private List<SocialSecurityInfoItem> socialSecurityInfoItems;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /** 获取 姓名 */
    public String getUserName() {
        return this.userName;
    }

    /** 设置 姓名 */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /** 获取 身份证 */
    public String getIdCard() {
        return this.idCard;
    }

    /** 设置 身份证 */
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    /** 获取 社保编码 */
    public String getSocialSecurityNum() {
        return this.socialSecurityNum;
    }

    /** 设置 社保编码 */
    public void setSocialSecurityNum(String socialSecurityNum) {
        this.socialSecurityNum = socialSecurityNum;
    }

    /** 获取 缴金地-经办机构 名 */
    public String getPayPlaceOrganizationName() {
        return this.payPlaceOrganizationName;
    }

    /** 设置 缴金地-经办机构 名 */
    public void setPayPlaceOrganizationName(String payPlaceOrganizationName) {
        this.payPlaceOrganizationName = payPlaceOrganizationName;
    }

    /** 获取 险种档次名 */
    public String getInsuranceLevelName() {
        return this.insuranceLevelName;
    }

    /** 设置 险种档次名 */
    public void setInsuranceLevelName(String insuranceLevelName) {
        this.insuranceLevelName = insuranceLevelName;
    }

    /** 获取 开始缴纳年月 */
    public Date getBeginPayYM() {
        return this.beginPayYM;
    }

    /** 设置 开始缴纳年月 */
    public void setBeginPayYM(Date beginPayYM) {
        this.beginPayYM = beginPayYM;
    }

    /** 获取 本次服务年月 */
    public Date getServiceNowYM() {
        return this.serviceNowYM;
    }

    /** 设置 本次服务年月 */
    public void setServiceNowYM(Date serviceNowYM) {
        this.serviceNowYM = serviceNowYM;
    }

    /** 获取 缴纳基数 */
    public Double getPayCardinalNumber() {
        return this.payCardinalNumber;
    }

    /** 设置 缴纳基数 */
    public void setPayCardinalNumber(Double payCardinalNumber) {
        this.payCardinalNumber = payCardinalNumber;
    }

    /** 获取 公司缴纳总金额 */
    public Double getCompanyTotalPay() {
        if (null == companyTotalPay) {
            return 0.0;
        }
        return new BigDecimal(this.companyTotalPay).setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /** 设置 公司缴纳总金额 */
    public void setCompanyTotalPay(Double companyTotalPay) {
        this.companyTotalPay = companyTotalPay;
    }

    /** 获取 个人缴纳总金额 */
    public Double getMemberTotalPay() {
        if (null == memberTotalPay) {
            return 0.0;
        }
        return new BigDecimal(this.memberTotalPay).setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /** 设置 个人缴纳总金额 */
    public void setMemberTotalPay(Double memberTotalPay) {
        this.memberTotalPay = memberTotalPay;
    }

    /** 获取 子账单子类id */
    public Integer getCompanySonBillItemId() {
        return this.companySonBillItemId;
    }

    /** 设置 子账单子类id */
    public void setCompanySonBillItemId(Integer companySonBillItemId) {
        this.companySonBillItemId = companySonBillItemId;
    }

    /** 获取 证件类型 */
    public Integer getCertificateType() {
        return this.certificateType;
    }

    /** 设置 证件类型 */
    public void setCertificateType(Integer certificateType) {
        this.certificateType = certificateType;
    }

    /** 获取 账单-社保明细 缴纳险种 */
    public List<SocialSecurityInfoItem> getSocialSecurityInfoItems() {
        return this.socialSecurityInfoItems;
    }

    /** 设置 账单-社保明细 缴纳险种 */
    public void setSocialSecurityInfoItems(List<SocialSecurityInfoItem> socialSecurityInfoItems) {
        this.socialSecurityInfoItems = socialSecurityInfoItems;
    }

    /** 获取 员工id */
    public Integer getMemberId() {
        return this.memberId;
    }

    /** 设置 员工id */
    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    /** 获取 公司实际缴纳金额 */
    public Double getPracticalCompanyTotalPay() {
        return this.practicalCompanyTotalPay;
    }

    /** 设置 公司实际缴纳金额 */
    public void setPracticalCompanyTotalPay(Double practicalCompanyTotalPay) {
        this.practicalCompanyTotalPay = practicalCompanyTotalPay;
    }

    /** 获取 员工实际缴纳金额 */
    public Double getPracticalMemberTotalPay() {
        return this.practicalMemberTotalPay;
    }

    /** 设置 员工实际缴纳金额 */
    public void setPracticalMemberTotalPay(Double practicalMemberTotalPay) {
        this.practicalMemberTotalPay = practicalMemberTotalPay;
    }

    /** 获取 缴金地 名 */
    public String getPayPlaceName() {
        return this.payPlaceName;
    }

    /** 设置 缴金地 名 */
    public void setPayPlaceName(String payPlaceName) {
        this.payPlaceName = payPlaceName;
    }

    /** 获取 经办机构 名 */
    public String getOrganizationName() {
        return this.organizationName;
    }

    /** 设置 经办机构 名 */
    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    /** 获取 办理方 名 */
    public String getTransactorName() {
        return this.transactorName;
    }

    /** 设置 办理方 名 */
    public void setTransactorName(String transactorName) {
        this.transactorName = transactorName;
    }

    /** 获取 子账单id */
    public Integer getCompanySonBillId() {
        return this.companySonBillId;
    }

    /** 设置 子账单id */
    public void setCompanySonBillId(Integer companySonBillId) {
        this.companySonBillId = companySonBillId;
    }

    /** 获取 员工合作方式  开票据的时候应用 */
    public Integer getWaysOfCooperation() {
        return this.waysOfCooperation;
    }

    /** 设置 员工合作方式  开票据的时候应用 */
    public void setWaysOfCooperation(Integer waysOfCooperation) {
        this.waysOfCooperation = waysOfCooperation;
    }

    /** 获取 账单制作方式 0：预收型  1：实做型 */
    public Integer getBillMadeMethod() {
        return this.billMadeMethod;
    }

    /** 设置 账单制作方式 0：预收型  1：实做型 */
    public void setBillMadeMethod(Integer billMadeMethod) {
        this.billMadeMethod = billMadeMethod;
    }

    /** 获取 是否稽核 0:否 1:是 */
    public Integer getIsAudit() {
        return this.isAudit;
    }

    /** 设置 是否稽核 0:否 1:是 */
    public void setIsAudit(Integer isAudit) {
        this.isAudit = isAudit;
    }

    /** 获取 处理方案 1：纳入次月账单 2：退回客户 */
    public Integer getProcessingScheme() {
        return this.processingScheme;
    }

    /** 设置 处理方案 1：纳入次月账单 2：退回客户 */
    public void setProcessingScheme(Integer processingScheme) {
        this.processingScheme = processingScheme;
    }

    /** 获取 公司名 */
    public String getCompanyName() {
        return this.companyName;
    }

    /** 设置 公司名 */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /** 获取 纳入次月处理的账单月 */
    public Date getAffirmBillMonth() {
        return this.affirmBillMonth;
    }

    /** 设置 纳入次月处理的账单月 */
    public void setAffirmBillMonth(Date affirmBillMonth) {
        this.affirmBillMonth = affirmBillMonth;
    }

    /** 获取 账单月 */
    public Date getBillMonth() {
        return this.billMonth;
    }

    /** 设置 账单月 */
    public void setBillMonth(Date billMonth) {
        this.billMonth = billMonth;
    }

    /** 获取 汇总账单的账单生成时间 */
    public Date getBillMonthCstb() {
        return this.billMonthCstb;
    }

    /** 设置 汇总账单的账单生成时间 */
    public void setBillMonthCstb(Date billMonthCstb) {
        this.billMonthCstb = billMonthCstb;
    }

    /** 获取本月是否是次月 */
    public Integer getIsProcessingScheme() throws ParseException {
        if (null != billMonth && null != billMonthCstb && null != processingScheme && processingScheme == 1 && Timestamp.parseDate3(billMonth,"yyyy-MM").
                compareTo(Timestamp.parseDate3(billMonthCstb,"yyyy-MM")) < 0 ) {
            return 1;
        }
        return 0;
    }

    /** 获取 实做时间 */
    public Date getRealDoTime() {
        return this.realDoTime;
    }

    /** 设置 实做时间 */
    public void setRealDoTime(Date realDoTime) {
        this.realDoTime = realDoTime;
    }

    /** 获取 是否上传拷盘 */
    public Integer getIsUploadKaoPan() {
        if (null == isUploadKaoPan) {
            return 0;
        }
        return this.isUploadKaoPan;
    }

    /** 设置 是否上传拷盘 */
    public void setIsUploadKaoPan(Integer isUploadKaoPan) {
        this.isUploadKaoPan = isUploadKaoPan;
    }

    /** 获取 服务配置id */
    public Integer getServiceFeeConfigId() {
        return this.serviceFeeConfigId;
    }

    /** 设置 服务配置id */
    public void setServiceFeeConfigId(Integer serviceFeeConfigId) {
        this.serviceFeeConfigId = serviceFeeConfigId;
    }

    /** 获取 税费 */
    public Double getTaxPrice() {
        if (null == taxPrice) {
            return 0.0;
        }
        return this.taxPrice;
    }

    /** 设置 税费 */
    public void setTaxPrice(Double taxPrice) {
        this.taxPrice = taxPrice;
    }
}
