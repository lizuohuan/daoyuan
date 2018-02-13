package com.magic.daoyuan.business.entity;

import java.io.Serializable;

/**
 * 险种缴纳规则
 * @author lzh
 * @create 2017/9/27 11:53
 */
public class PayTheWay implements Serializable {

    private Integer id;

    /** 公司缴纳类型 0：金额  1：比例   2：跟随办理方*/
    private Integer coPayType;

    /** 公司缴纳 根据公司缴纳类型填写（ps:办理方操作此类时 不操作此字段）  金额/比例 */
    private Double coPayPrice;

    /** 公司计算精度 */
    private Integer coComputationalAccuracy;

    /** 公司计算规则  0：四舍五入 1：升角省分（精度为0） 2：去尾  3：进一 */
    private Integer coComputationRule;

    /** 公司是否补缴  0：否  1：是 */
    private Integer isCMakeASupplementaryPayment;

    /** 公司是否离职补差  0：否  1：是 */
    private Integer isCDimissionSupplementaryPay;

    /** 公司填写精度 3、2、1、0、-1、-2，对应保留小数位数 */
    private Double coPrecision;

    /** 个人缴纳类型 0：金额  1：比例   2：跟随办理方 */
    private Integer mePayType;

    /** 个人缴纳金额/比例 （ps:办理方操作此类时 不操作此字段）*/
    private Double mePayPrice;

    /** 个人计算精度 */
    private Integer meComputationalAccuracy;

    /** 个人计算规则  0：四舍五入 1：升角省分（精度为0） 2：去尾  3：进一 */
    private Integer meComputationRule;

    /** 个人是否补缴  0：否  1：是 */
    private Integer isMMakeASupplementaryPayment;

    /** 个人是否离职补差  0：否  1：是 */
    private Integer isMDimissionSupplementaryPay;

    /** 个人填写精度 3、2、1、0、-1、-2，对应保留小数位数 */
    private Double mePrecision;

    /** 档次id */
    private Integer insuranceLevelId;

    /** 公司基数最小范围 */
    private Double coMinScope;

    /** 公司基数最大范围 */
    private Double coMaxScope;

    /** 个人基数最小范围 */
    private Double meMinScope;

    /** 个人基数最大范围 */
    private Double meMaxScope;

    /** 是否有效 0 无效 1有效 */
    private Integer isValid;

    /** 险种id */
    private Integer insuranceId;

    /*********  业务字段 不进行数据库持久化  **********/

    /** 险种档次名 */
    private String insuranceLevelName;

    /** 险种名 */
    private String insuranceName;

    /** 办理方下的档次配置 */
    private TransactorInsuranceLevel transactorInsuranceLevel;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /** 获取 公司缴纳金额/比例 */
    public Double getCoPayPrice() {
        return this.coPayPrice;
    }

    /** 设置 公司缴纳金额/比例 */
    public void setCoPayPrice(Double coPayPrice) {
        this.coPayPrice = coPayPrice;
    }

    /** 获取 公司计算精度 */
    public Integer getCoComputationalAccuracy() {
        return this.coComputationalAccuracy;
    }

    /** 设置 公司计算精度 */
    public void setCoComputationalAccuracy(Integer coComputationalAccuracy) {
        this.coComputationalAccuracy = coComputationalAccuracy;
    }

    /** 获取 公司计算规则  0：四舍五入 1：升角省分（精度为0） 2：去尾  3：进一 */
    public Integer getCoComputationRule() {
        return this.coComputationRule;
    }

    /** 设置 公司计算规则  0：四舍五入 1：升角省分（精度为0） 2：去尾  3：进一 */
    public void setCoComputationRule(Integer coComputationRule) {
        this.coComputationRule = coComputationRule;
    }

    /** 获取 公司是否补缴  0：否  1：是 */
    public Integer getIsCMakeASupplementaryPayment() {
        return this.isCMakeASupplementaryPayment;
    }

    /** 设置 公司是否补缴  0：否  1：是 */
    public void setIsCMakeASupplementaryPayment(Integer isCMakeASupplementaryPayment) {
        this.isCMakeASupplementaryPayment = isCMakeASupplementaryPayment;
    }

    /** 获取 个人缴纳金额/比例 */
    public Double getMePayPrice() {
        return this.mePayPrice;
    }

    /** 设置 个人缴纳金额/比例 */
    public void setMePayPrice(Double mePayPrice) {
        this.mePayPrice = mePayPrice;
    }

    /** 获取 个人计算精度 */
    public Integer getMeComputationalAccuracy() {
        return this.meComputationalAccuracy;
    }

    /** 设置 个人计算精度 */
    public void setMeComputationalAccuracy(Integer meComputationalAccuracy) {
        this.meComputationalAccuracy = meComputationalAccuracy;
    }

    /** 获取 个人计算规则  0：四舍五入 1：升角省分（精度为0） 2：去尾  3：进一 */
    public Integer getMeComputationRule() {
        return this.meComputationRule;
    }

    /** 设置 个人计算规则  0：四舍五入 1：升角省分（精度为0） 2：去尾  3：进一 */
    public void setMeComputationRule(Integer meComputationRule) {
        this.meComputationRule = meComputationRule;
    }

    /** 获取 个人是否补缴  0：否  1：是 */
    public Integer getIsMMakeASupplementaryPayment() {
        return this.isMMakeASupplementaryPayment;
    }

    /** 设置 个人是否补缴  0：否  1：是 */
    public void setIsMMakeASupplementaryPayment(Integer isMMakeASupplementaryPayment) {
        this.isMMakeASupplementaryPayment = isMMakeASupplementaryPayment;
    }



    /** 获取 公司基数最小范围 */
    public Double getCoMinScope() {
        return this.coMinScope;
    }

    /** 设置 公司基数最小范围 */
    public void setCoMinScope(Double coMinScope) {
        this.coMinScope = coMinScope;
    }

    /** 获取 公司基数最大范围 */
    public Double getCoMaxScope() {
        return this.coMaxScope;
    }

    /** 设置 公司基数最大范围 */
    public void setCoMaxScope(Double coMaxScope) {
        this.coMaxScope = coMaxScope;
    }

    /** 获取 个人基数最小范围 */
    public Double getMeMinScope() {
        return this.meMinScope;
    }

    /** 设置 个人基数最小范围 */
    public void setMeMinScope(Double meMinScope) {
        this.meMinScope = meMinScope;
    }

    /** 获取 个人基数最大范围 */
    public Double getMeMaxScope() {
        return this.meMaxScope;
    }

    /** 设置 个人基数最大范围 */
    public void setMeMaxScope(Double meMaxScope) {
        this.meMaxScope = meMaxScope;
    }

    /** 获取 是否有效 0 无效 1有效 */
    public Integer getIsValid() {
        return this.isValid;
    }

    /** 设置 是否有效 0 无效 1有效 */
    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    /** 获取 公司填写精度 */
    public Double getCoPrecision() {
        return this.coPrecision;
    }

    /** 设置 公司填写精度 */
    public void setCoPrecision(Double coPrecision) {
        this.coPrecision = coPrecision;
    }

    /** 获取 公司填写精度 */
    public Double getMePrecision() {
        return this.mePrecision;
    }

    /** 设置 公司填写精度 */
    public void setMePrecision(Double mePrecision) {
        this.mePrecision = mePrecision;
    }

    /** 获取 公司缴纳类型 0：金额  1：比例  2：按照办理方  ps:办理方操作此类时 此字段不做操作 */
    public Integer getCoPayType() {
        return this.coPayType;
    }

    /** 设置 公司缴纳类型 0：金额  1：比例  2：按照办理方  ps:办理方操作此类时 此字段不做操作 */
    public void setCoPayType(Integer coPayType) {
        this.coPayType = coPayType;
    }

    /** 获取 个人缴纳类型 0：金额  1：比例 */
    public Integer getMePayType() {
        return this.mePayType;
    }

    /** 设置 个人缴纳类型 0：金额  1：比例 */
    public void setMePayType(Integer mePayType) {
        this.mePayType = mePayType;
    }

    /** 获取 档次id */
    public Integer getInsuranceLevelId() {
        return this.insuranceLevelId;
    }

    /** 设置 档次id */
    public void setInsuranceLevelId(Integer insuranceLevelId) {
        this.insuranceLevelId = insuranceLevelId;
    }

    /** 获取 险种id */
    public Integer getInsuranceId() {
        return this.insuranceId;
    }

    /** 设置 险种id */
    public void setInsuranceId(Integer insuranceId) {
        this.insuranceId = insuranceId;
    }

    /** 获取 险种档次名 */
    public String getInsuranceLevelName() {
        return this.insuranceLevelName;
    }

    /** 设置 险种档次名 */
    public void setInsuranceLevelName(String insuranceLevelName) {
        this.insuranceLevelName = insuranceLevelName;
    }

    /** 获取 险种名 */
    public String getInsuranceName() {
        return this.insuranceName;
    }

    /** 设置 险种名 */
    public void setInsuranceName(String insuranceName) {
        this.insuranceName = insuranceName;
    }

    /** 获取 办理方下的档次配置 */
    public TransactorInsuranceLevel getTransactorInsuranceLevel() {
        return this.transactorInsuranceLevel;
    }

    /** 设置 办理方下的档次配置 */
    public void setTransactorInsuranceLevel(TransactorInsuranceLevel transactorInsuranceLevel) {
        this.transactorInsuranceLevel = transactorInsuranceLevel;
    }

    /** 获取 公司是否离职补差  0：否  1：是 */
    public Integer getIsCDimissionSupplementaryPay() {
        return this.isCDimissionSupplementaryPay;
    }

    /** 设置 公司是否离职补差  0：否  1：是 */
    public void setIsCDimissionSupplementaryPay(Integer isCDimissionSupplementaryPay) {
        this.isCDimissionSupplementaryPay = isCDimissionSupplementaryPay;
    }

    /** 获取 个人是否离职补差  0：否  1：是 */
    public Integer getIsMDimissionSupplementaryPay() {
        return this.isMDimissionSupplementaryPay;
    }

    /** 设置 个人是否离职补差  0：否  1：是 */
    public void setIsMDimissionSupplementaryPay(Integer isMDimissionSupplementaryPay) {
        this.isMDimissionSupplementaryPay = isMDimissionSupplementaryPay;
    }
}
