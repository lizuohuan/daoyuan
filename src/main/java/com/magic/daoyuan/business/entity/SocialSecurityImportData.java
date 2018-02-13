package com.magic.daoyuan.business.entity;

import java.util.Date;
import java.util.List;

/**
 * 实做 导入数据封装 entity
 * Created by Eric Xie on 2017/10/26 0026.
 */
public class SocialSecurityImportData {

    /** 社保编码 */
    private String socialSecurityNumber;

    /** 身份证号 */
    private String idCard;

    /** 姓名 */
    private String userName;

    private String payPlaceName;
    private String organizationName;
    private String transactorName;
    private String levelName;
    private Double baseNumber;


    private Integer payPlaceId;


    /** 款项所属月份 */
    private Date month;

    /** 账单起始月 */
    private Date billStartMonth;

    /** 服务结束月 */
    private Date serviceEndMonth;

    /** 公司总计缴纳 */
    private double companyCountPrice;

    /** 个人总计缴纳 */
    private double personCountPrice;

    /** 养老缴费基数 */
    private double provisionCardinal;

    /** 养老单位缴纳比例 */
    private double provisionCompanyRatio;

    /** 养老单位缴纳金额 */
    private double provisionCompanyPrice;

    /** 养老个人缴纳比例 */
    private double provisionSelfRatio;

    /** 养老个人缴纳金额 */
    private double provisionSelfPrice;

    /** 医疗缴纳基数 */
    private double medicalCardinal;

    /** 医疗公司缴纳比例 */
    private double medicalCompanyRatio;

    /** 医疗单位缴费金额 */
    private double medicalCompanyPrice;

    /** 医疗个人缴纳比例 */
    private double medicalSelfRatio;

    /** 医疗个人缴纳金额 */
    private double medicalSelfPrice;

    /** 生育缴费基数 */
    private double bearCardinal;

    /** 生育单位缴纳比例 */
    private double bearCompanyRatio;

    /** 生育单位缴纳金额 */
    private double bearCompanyPrice;

    /** 生育个人缴纳比例 */
    private double bearSelfRatio;

    /** 失业缴纳基数 */
    private double workCardinal;

    /** 失业单位缴纳比例 */
    private double workCompanyRatio;

    /** 失业单位缴费金额 */
    private double workCompanyPrice;

    /** 失业个人缴费比例 */
    private double workPersonRatio;

    /** 失业个人缴费金额 */
    private double workPersonPrice;

    /** 工伤缴费基数 */
    private double injuryCardinal;

    /** 工伤单位缴费比例 */
    private double injuryCompanyRatio;

    /** 工伤单位缴费金额 */
    private double injuryCompanyPrice;

    /** 工伤 个人缴纳金额 */
    private double injurySelfPrice;

    /** 机关养老缴费基数 */
    private double organCardinal;

    /** 机关养老单位缴费比例 */
    private double organCompanyRatio;

    /** 机关养老单位缴费金额 */
    private double organCompanyPrice;

    /** 机关养老个人缴费比例 */
    private double organSelfRatio;

    /** 机关养老个人缴费金额 */
    private double organSelfPrice;

    /** 大病缴费基数 */
    private double illnessCardinal;

    /** 大病单位缴费比例 */
    private double illnessCompanyRatio;

    /** 大病单位缴费金额 */
    private double illnessCompanyPrice;

    /** 大病 个人缴费金额 */
    private double illnessSelfPrice;

    /** 公司残保金缴纳金额 */
    private double residualCompanyPrice;

    /** 个人残保金缴纳金额 */
    private double residualSelfPrice;

    /** 通用动态险种 */
    private List<SocialSecurityCommon> socialSecurityCommonList;

    public List<SocialSecurityCommon> getSocialSecurityCommonList() {
        return socialSecurityCommonList;
    }

    public SocialSecurityImportData setSocialSecurityCommonList(List<SocialSecurityCommon> socialSecurityCommonList) {
        this.socialSecurityCommonList = socialSecurityCommonList;
        return this;
    }



    /** 获取 社保编码 */
    public String getSocialSecurityNumber() {
        return this.socialSecurityNumber;
    }

    /** 设置 社保编码 */
    public void setSocialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }

    /** 获取 身份证号 */
    public String getIdCard() {
        return this.idCard;
    }

    /** 设置 身份证号 */
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    /** 获取 姓名 */
    public String getUserName() {
        return this.userName;
    }

    /** 设置 姓名 */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /** 获取 款项所属月份 */
    public Date getMonth() {
        return this.month;
    }

    /** 设置 款项所属月份 */
    public void setMonth(Date month) {
        this.month = month;
    }

    /** 获取 公司总计缴纳 */
    public double getCompanyCountPrice(Integer cityType) {
        if (0.0 == companyCountPrice && cityType == 2 && null != socialSecurityCommonList && socialSecurityCommonList.size() > 0) {
            for (SocialSecurityCommon common : socialSecurityCommonList) {
                companyCountPrice+=common.getCompanyPrice();
            }
        }
        return this.companyCountPrice;
    }

    /** 设置 公司总计缴纳 */
    public void setCompanyCountPrice(double companyCountPrice) {
        this.companyCountPrice = companyCountPrice;
    }

    /** 获取 个人总计缴纳 */
    public double getPersonCountPrice(Integer cityType) {
        if (0.0 == personCountPrice && cityType == 2 && null != socialSecurityCommonList && socialSecurityCommonList.size() > 0) {
            for (SocialSecurityCommon common : socialSecurityCommonList) {
                personCountPrice+=common.getPersonPrice();
            }
        }
        return this.personCountPrice;
    }

    /** 设置 个人总计缴纳 */
    public void setPersonCountPrice(double personCountPrice) {
        this.personCountPrice = personCountPrice;
    }

    /** 获取 养老缴费基数 */
    public double getProvisionCardinal() {
        return this.provisionCardinal;
    }

    /** 设置 养老缴费基数 */
    public void setProvisionCardinal(double provisionCardinal) {
        this.provisionCardinal = provisionCardinal;
    }

    /** 获取 养老单位缴纳比例 */
    public double getProvisionCompanyRatio() {
        return this.provisionCompanyRatio;
    }

    /** 设置 养老单位缴纳比例 */
    public void setProvisionCompanyRatio(double provisionCompanyRatio) {
        this.provisionCompanyRatio = provisionCompanyRatio;
    }

    /** 获取 养老单位缴纳金额 */
    public double getProvisionCompanyPrice() {
        return this.provisionCompanyPrice;
    }

    /** 设置 养老单位缴纳金额 */
    public void setProvisionCompanyPrice(double provisionCompanyPrice) {
        this.provisionCompanyPrice = provisionCompanyPrice;
    }

    /** 获取 养老个人缴纳比例 */
    public double getProvisionSelfRatio() {
        return this.provisionSelfRatio;
    }

    /** 设置 养老个人缴纳比例 */
    public void setProvisionSelfRatio(double provisionSelfRatio) {
        this.provisionSelfRatio = provisionSelfRatio;
    }

    /** 获取 养老个人缴纳金额 */
    public double getProvisionSelfPrice() {
        return this.provisionSelfPrice;
    }

    /** 设置 养老个人缴纳金额 */
    public void setProvisionSelfPrice(double provisionSelfPrice) {
        this.provisionSelfPrice = provisionSelfPrice;
    }

    /** 获取 医疗缴纳基数 */
    public double getMedicalCardinal() {
        return this.medicalCardinal;
    }

    /** 设置 医疗缴纳基数 */
    public void setMedicalCardinal(double medicalCardinal) {
        this.medicalCardinal = medicalCardinal;
    }

    /** 获取 医疗公司缴纳比例 */
    public double getMedicalCompanyRatio() {
        return this.medicalCompanyRatio;
    }

    /** 设置 医疗公司缴纳比例 */
    public void setMedicalCompanyRatio(double medicalCompanyRatio) {
        this.medicalCompanyRatio = medicalCompanyRatio;
    }

    /** 获取 医疗单位缴费金额 */
    public double getMedicalCompanyPrice() {
        return this.medicalCompanyPrice;
    }

    /** 设置 医疗单位缴费金额 */
    public void setMedicalCompanyPrice(double medicalCompanyPrice) {
        this.medicalCompanyPrice = medicalCompanyPrice;
    }

    /** 获取 医疗个人缴纳比例 */
    public double getMedicalSelfRatio() {
        return this.medicalSelfRatio;
    }

    /** 设置 医疗个人缴纳比例 */
    public void setMedicalSelfRatio(double medicalSelfRatio) {
        this.medicalSelfRatio = medicalSelfRatio;
    }

    /** 获取 医疗个人缴纳金额 */
    public double getMedicalSelfPrice() {
        return this.medicalSelfPrice;
    }

    /** 设置 医疗个人缴纳金额 */
    public void setMedicalSelfPrice(double medicalSelfPrice) {
        this.medicalSelfPrice = medicalSelfPrice;
    }

    /** 获取 生育缴费基数 */
    public double getBearCardinal() {
        return this.bearCardinal;
    }

    /** 设置 生育缴费基数 */
    public void setBearCardinal(double bearCardinal) {
        this.bearCardinal = bearCardinal;
    }

    /** 获取 生育单位缴纳比例 */
    public double getBearCompanyRatio() {
        return this.bearCompanyRatio;
    }

    /** 设置 生育单位缴纳比例 */
    public void setBearCompanyRatio(double bearCompanyRatio) {
        this.bearCompanyRatio = bearCompanyRatio;
    }

    /** 获取 生育单位缴纳金额 */
    public double getBearCompanyPrice() {
        return this.bearCompanyPrice;
    }

    /** 设置 生育单位缴纳金额 */
    public void setBearCompanyPrice(double bearCompanyPrice) {
        this.bearCompanyPrice = bearCompanyPrice;
    }

    /** 获取 生育个人缴纳比例 */
    public double getBearSelfRatio() {
        return this.bearSelfRatio;
    }

    /** 设置 生育个人缴纳比例 */
    public void setBearSelfRatio(double bearSelfRatio) {
        this.bearSelfRatio = bearSelfRatio;
    }

    /** 获取 失业缴纳基数 */
    public double getWorkCardinal() {
        return this.workCardinal;
    }

    /** 设置 失业缴纳基数 */
    public void setWorkCardinal(double workCardinal) {
        this.workCardinal = workCardinal;
    }

    /** 获取 失业单位缴纳比例 */
    public double getWorkCompanyRatio() {
        return this.workCompanyRatio;
    }

    /** 设置 失业单位缴纳比例 */
    public void setWorkCompanyRatio(double workCompanyRatio) {
        this.workCompanyRatio = workCompanyRatio;
    }

    /** 获取 失业单位缴费金额 */
    public double getWorkCompanyPrice() {
        return this.workCompanyPrice;
    }

    /** 设置 失业单位缴费金额 */
    public void setWorkCompanyPrice(double workCompanyPrice) {
        this.workCompanyPrice = workCompanyPrice;
    }

    /** 获取 失业个人缴费比例 */
    public double getWorkPersonRatio() {
        return this.workPersonRatio;
    }

    /** 设置 失业个人缴费比例 */
    public void setWorkPersonRatio(double workPersonRatio) {
        this.workPersonRatio = workPersonRatio;
    }

    /** 获取 失业个人缴费金额 */
    public double getWorkPersonPrice() {
        return this.workPersonPrice;
    }

    /** 设置 失业个人缴费金额 */
    public void setWorkPersonPrice(double workPersonPrice) {
        this.workPersonPrice = workPersonPrice;
    }

    /** 获取 工伤缴费基数 */
    public double getInjuryCardinal() {
        return this.injuryCardinal;
    }

    /** 设置 工伤缴费基数 */
    public void setInjuryCardinal(double injuryCardinal) {
        this.injuryCardinal = injuryCardinal;
    }

    /** 获取 工伤单位缴费比例 */
    public double getInjuryCompanyRatio() {
        return this.injuryCompanyRatio;
    }

    /** 设置 工伤单位缴费比例 */
    public void setInjuryCompanyRatio(double injuryCompanyRatio) {
        this.injuryCompanyRatio = injuryCompanyRatio;
    }

    /** 获取 工伤单位缴费金额 */
    public double getInjuryCompanyPrice() {
        return this.injuryCompanyPrice;
    }

    /** 设置 工伤单位缴费金额 */
    public void setInjuryCompanyPrice(double injuryCompanyPrice) {
        this.injuryCompanyPrice = injuryCompanyPrice;
    }

    /** 获取 机关养老缴费基数 */
    public double getOrganCardinal() {
        return this.organCardinal;
    }

    /** 设置 机关养老缴费基数 */
    public void setOrganCardinal(double organCardinal) {
        this.organCardinal = organCardinal;
    }

    /** 获取 机关养老单位缴费比例 */
    public double getOrganCompanyRatio() {
        return this.organCompanyRatio;
    }

    /** 设置 机关养老单位缴费比例 */
    public void setOrganCompanyRatio(double organCompanyRatio) {
        this.organCompanyRatio = organCompanyRatio;
    }

    /** 获取 机关养老单位缴费金额 */
    public double getOrganCompanyPrice() {
        return this.organCompanyPrice;
    }

    /** 设置 机关养老单位缴费金额 */
    public void setOrganCompanyPrice(double organCompanyPrice) {
        this.organCompanyPrice = organCompanyPrice;
    }

    /** 获取 机关养老个人缴费比例 */
    public double getOrganSelfRatio() {
        return this.organSelfRatio;
    }

    /** 设置 机关养老个人缴费比例 */
    public void setOrganSelfRatio(double organSelfRatio) {
        this.organSelfRatio = organSelfRatio;
    }


    /** 获取 大病缴费基数 */
    public double getIllnessCardinal() {
        return this.illnessCardinal;
    }

    /** 设置 大病缴费基数 */
    public void setIllnessCardinal(double illnessCardinal) {
        this.illnessCardinal = illnessCardinal;
    }

    /** 获取 大病单位缴费比例 */
    public double getIllnessCompanyRatio() {
        return this.illnessCompanyRatio;
    }

    /** 设置 大病单位缴费比例 */
    public void setIllnessCompanyRatio(double illnessCompanyRatio) {
        this.illnessCompanyRatio = illnessCompanyRatio;
    }

    /** 获取 大病单位缴费金额 */
    public double getIllnessCompanyPrice() {
        return this.illnessCompanyPrice;
    }

    /** 设置 大病单位缴费金额 */
    public void setIllnessCompanyPrice(double illnessCompanyPrice) {
        this.illnessCompanyPrice = illnessCompanyPrice;
    }

    /** 获取 机关养老个人缴费金额 */
    public double getOrganSelfPrice() {
        return this.organSelfPrice;
    }

    /** 设置 机关养老个人缴费金额 */
    public void setOrganSelfPrice(double organSelfPrice) {
        this.organSelfPrice = organSelfPrice;
    }

    /** 获取 公司残保金缴纳金额 */
    public double getResidualCompanyPrice() {
        return this.residualCompanyPrice;
    }

    /** 设置 公司残保金缴纳金额 */
    public void setResidualCompanyPrice(double residualCompanyPrice) {
        this.residualCompanyPrice = residualCompanyPrice;
    }

    /** 获取 个人残保金缴纳金额 */
    public double getResidualSelfPrice() {
        return this.residualSelfPrice;
    }

    /** 设置 个人残保金缴纳金额 */
    public void setResidualSelfPrice(double residualSelfPrice) {
        this.residualSelfPrice = residualSelfPrice;
    }

    /** 获取 工伤 个人缴纳金额 */
    public double getInjurySelfPrice() {
        return this.injurySelfPrice;
    }

    /** 设置 工伤 个人缴纳金额 */
    public void setInjurySelfPrice(double injurySelfPrice) {
        this.injurySelfPrice = injurySelfPrice;
    }

    /** 获取 大病 个人缴费金额 */
    public double getIllnessSelfPrice() {
        return this.illnessSelfPrice;
    }

    /** 设置 大病 个人缴费金额 */
    public void setIllnessSelfPrice(double illnessSelfPrice) {
        this.illnessSelfPrice = illnessSelfPrice;
    }

    public String getPayPlaceName() {
        return this.payPlaceName;
    }

    public void setPayPlaceName(String payPlaceName) {
        this.payPlaceName = payPlaceName;
    }

    public String getOrganizationName() {
        return this.organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getTransactorName() {
        return this.transactorName;
    }

    public void setTransactorName(String transactorName) {
        this.transactorName = transactorName;
    }

    public String getLevelName() {
        return this.levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public Double getBaseNumber() {
        return this.baseNumber;
    }

    public void setBaseNumber(Double baseNumber) {
        this.baseNumber = baseNumber;
    }

    public Integer getPayPlaceId() {
        return this.payPlaceId;
    }

    public void setPayPlaceId(Integer payPlaceId) {
        this.payPlaceId = payPlaceId;
    }

    /** 获取 账单起始月 */
    public Date getBillStartMonth() {
        return this.billStartMonth;
    }

    /** 设置 账单起始月 */
    public void setBillStartMonth(Date billStartMonth) {
        this.billStartMonth = billStartMonth;
    }

    /** 获取 服务结束月 */
    public Date getServiceEndMonth() {
        return this.serviceEndMonth;
    }

    /** 设置 服务结束月 */
    public void setServiceEndMonth(Date serviceEndMonth) {
        this.serviceEndMonth = serviceEndMonth;
    }
}
