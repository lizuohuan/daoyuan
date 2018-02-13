package com.magic.daoyuan.business.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 员工业务 社保公积金表
 * @author lzh
 * @create 2017/10/11 10:54
 */
public class MemberBusinessItem implements Serializable {


    private Integer id;

    /** 业务类型 0：社保  1：公积金 */
    private Integer type;

    /** 服务方式 0：代理  1：托管 */
    private Integer serveMethod;

    /** 是否纳入应收 0：否  1：是 */
    private Integer isReceivable;

    /** 绑定公司下的缴金地id */
    private Integer payPlaceId;

    /** 经办机构id */
    private Integer organizationId;

    /** 办理方id */
    private Integer transactorId;

    /** 险种档次id type=0 时使用 */
    private Integer insuranceLevelId;

    /** 绑定员工业务id */
    private Integer memberBusinessId;

    /** 比例  type=1时使用 */
    private Double ratio;

    /** 缴纳基数类型 0：最低  1：最高  2：填写 */
    private Integer baseType;

    /** 缴纳基数类型 = 2 时 此字段不能为空 缴纳基数基数 */
    private Double baseNumber;

    private Date serviceStartTime;

    private Date billStartTime;

    private Date serviceEndTime;

    /** 社保或者公积金编码  根据type */
    private String coding;

    /** 是否是第一次缴纳 0：否  1：是 */
    private Integer isFirstPay;

    /** 第一次缴纳的账单月 */
    private Date firstPayBillMonth;

    /********  业务字段 不进行数据库持久化    *******/

    /** 经办机构名 */
    private String organizationName;

    /** 缴金地名 */
    private String payPlaceName;

    /** 办理方名 */
    private String transactorName;

    /** 档次名 */
    private String insuranceLevelName;

    private String certificateNum;

    /** serveMethod = 0 时 用此集合 缴金地下险种档次下险种集合 */
    private List<PayTheWay> payTheWays;

    /** 计算精度 3、2、1、0、-1、-2，对应保留小数位数 */
    private Integer computationalAccuracy;

    /** 计算规则  0：四舍五入 1：升角省分（精度为0） 2：去尾  3：进一 */
    private Integer computationRule;

    /** 最小基数 */
    private Double minCardinalNumber;

    /** 最大基数 */
    private Double maxCardinalNumber;

    /** 填写精度 3、2、1、0、-1、-2，对应保留小数位数 */
    private Integer precision;

    /** 服务区域id */
    private Integer cityId;

    /** 业务id */
    private Integer businessId;

    /** 子账单名称 */
    private String sonBillName;

    /** 账单制作方式 0：预收型  1：实做型 */
    private Integer billMadeMethod;


    private Integer memberId;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /** 获取 业务类型 0：社保  1：公积金 */
    public Integer getType() {
        return this.type;
    }

    /** 设置 业务类型 0：社保  1：公积金 */
    public void setType(Integer type) {
        this.type = type;
    }

    /** 获取 服务方式 0：代理  1：托管 */
    public Integer getServeMethod() {
        return this.serveMethod;
    }

    /** 设置 服务方式 0：代理  1：托管 */
    public void setServeMethod(Integer serveMethod) {
        this.serveMethod = serveMethod;
    }

    /** 获取 是否纳入应收 0：否  1：是 */
    public Integer getIsReceivable() {
        return this.isReceivable;
    }

    /** 设置 是否纳入应收 0：否  1：是 */
    public void setIsReceivable(Integer isReceivable) {
        this.isReceivable = isReceivable;
    }

    /** 获取 绑定公司下的缴金地id */
    public Integer getPayPlaceId() {
        return this.payPlaceId;
    }

    /** 设置 绑定公司下的缴金地id */
    public void setPayPlaceId(Integer payPlaceId) {
        this.payPlaceId = payPlaceId;
    }

    /** 获取 经办机构id */
    public Integer getOrganizationId() {
        return this.organizationId;
    }

    /** 设置 经办机构id */
    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

    /** 获取 办理方id */
    public Integer getTransactorId() {
        return this.transactorId;
    }

    /** 设置 办理方id */
    public void setTransactorId(Integer transactorId) {
        this.transactorId = transactorId;
    }

    /** 获取 险种档次id type=0 时使用 */
    public Integer getInsuranceLevelId() {
        return this.insuranceLevelId;
    }

    /** 设置 险种档次id type=0 时使用 */
    public void setInsuranceLevelId(Integer insuranceLevelId) {
        this.insuranceLevelId = insuranceLevelId;
    }

    /** 获取 绑定员工业务id */
    public Integer getMemberBusinessId() {
        return this.memberBusinessId;
    }

    /** 设置 绑定员工业务id */
    public void setMemberBusinessId(Integer memberBusinessId) {
        this.memberBusinessId = memberBusinessId;
    }

    /** 获取 比例  type=1时使用 */
    public Double getRatio() {
        return this.ratio;
    }

    /** 设置 比例  type=1时使用 */
    public void setRatio(Double ratio) {
        this.ratio = ratio;
    }


    /** 获取 基数 */
    public Double getBaseNumber() {
        return this.baseNumber;
    }

    /** 设置 基数 */
    public void setBaseNumber(Double baseNumber) {
        this.baseNumber = baseNumber;
    }

    public Date getServiceStartTime() {
        return this.serviceStartTime;
    }

    public void setServiceStartTime(Date serviceStartTime) {
        this.serviceStartTime = serviceStartTime;
    }

    public Date getBillStartTime() {
        return this.billStartTime;
    }

    public void setBillStartTime(Date billStartTime) {
        this.billStartTime = billStartTime;
    }

    public Date getServiceEndTime() {
        return this.serviceEndTime;
    }

    public void setServiceEndTime(Date serviceEndTime) {
        this.serviceEndTime = serviceEndTime;
    }

    /** 获取 缴纳基数类型 0：最低  1：最高  2：填写 */
    public Integer getBaseType() {
        return this.baseType;
    }

    /** 设置 缴纳基数类型 0：最低  1：最高  2：填写 */
    public void setBaseType(Integer baseType) {
        this.baseType = baseType;
    }

    /** 获取 经办机构名 */
    public String getOrganizationName() {
        return this.organizationName;
    }

    /** 设置 经办机构名 */
    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    /** 获取 缴金地名 */
    public String getPayPlaceName() {
        return this.payPlaceName;
    }

    /** 设置 缴金地名 */
    public void setPayPlaceName(String payPlaceName) {
        this.payPlaceName = payPlaceName;
    }

    /** 获取 社保或者公积金编码  根据type */
    public String getCoding() {
        return this.coding;
    }

    /** 设置 社保或者公积金编码  根据type */
    public void setCoding(String coding) {
        this.coding = coding;
    }

    /** 获取 档次名 */
    public String getInsuranceLevelName() {
        return this.insuranceLevelName;
    }

    /** 设置 档次名 */
    public void setInsuranceLevelName(String insuranceLevelName) {
        this.insuranceLevelName = insuranceLevelName;
    }

    /** 获取 serveMethod = 0 时 用此集合 缴金地下险种档次下险种集合 */
    public List<PayTheWay> getPayTheWays() {
        return this.payTheWays;
    }

    /** 设置 serveMethod = 0 时 用此集合 缴金地下险种档次下险种集合 */
    public void setPayTheWays(List<PayTheWay> payTheWays) {
        this.payTheWays = payTheWays;
    }

    /** 获取 办理方名 */
    public String getTransactorName() {
        return this.transactorName;
    }

    /** 设置 办理方名 */
    public void setTransactorName(String transactorName) {
        this.transactorName = transactorName;
    }

    /** 获取 是否是第一次缴纳 0：否  1：是 */
    public Integer getIsFirstPay() {
        return this.isFirstPay;
    }

    /** 设置 是否是第一次缴纳 0：否  1：是 */
    public void setIsFirstPay(Integer isFirstPay) {
        this.isFirstPay = isFirstPay;
    }

    /** 获取 第一次缴纳的账单月 */
    public Date getFirstPayBillMonth() {
        return this.firstPayBillMonth;
    }

    /** 设置 第一次缴纳的账单月 */
    public void setFirstPayBillMonth(Date firstPayBillMonth) {
        this.firstPayBillMonth = firstPayBillMonth;
    }

    /** 获取 计算精度 3、2、1、0、-1、-2，对应保留小数位数 */
    public Integer getComputationalAccuracy() {
        return this.computationalAccuracy;
    }

    /** 设置 计算精度 3、2、1、0、-1、-2，对应保留小数位数 */
    public void setComputationalAccuracy(Integer computationalAccuracy) {
        this.computationalAccuracy = computationalAccuracy;
    }

    /** 获取 计算规则  0：四舍五入 1：升角省分（精度为0） 2：去尾  3：进一 */
    public Integer getComputationRule() {
        return this.computationRule;
    }

    /** 设置 计算规则  0：四舍五入 1：升角省分（精度为0） 2：去尾  3：进一 */
    public void setComputationRule(Integer computationRule) {
        this.computationRule = computationRule;
    }

    /** 获取 最小基数 */
    public Double getMinCardinalNumber() {
        return this.minCardinalNumber;
    }

    /** 设置 最小基数 */
    public void setMinCardinalNumber(Double minCardinalNumber) {
        this.minCardinalNumber = minCardinalNumber;
    }

    /** 获取 最大基数 */
    public Double getMaxCardinalNumber() {
        return this.maxCardinalNumber;
    }

    /** 设置 最大基数 */
    public void setMaxCardinalNumber(Double maxCardinalNumber) {
        this.maxCardinalNumber = maxCardinalNumber;
    }

    /** 获取 填写精度 3、2、1、0、-1、-2，对应保留小数位数 */
    public Integer getPrecision() {
        return this.precision;
    }

    /** 设置 填写精度 3、2、1、0、-1、-2，对应保留小数位数 */
    public void setPrecision(Integer precision) {
        this.precision = precision;
    }

    /** 获取 服务区域id */
    public Integer getCityId() {
        return this.cityId;
    }

    /** 设置 服务区域id */
    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    /** 获取 业务id */
    public Integer getBusinessId() {
        return this.businessId;
    }

    /** 设置 业务id */
    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    /** 获取 子账单名称 */
    public String getSonBillName() {
        return this.sonBillName;
    }

    /** 设置 子账单名称 */
    public void setSonBillName(String sonBillName) {
        this.sonBillName = sonBillName;
    }

    public String getCertificateNum() {
        return this.certificateNum;
    }

    public void setCertificateNum(String certificateNum) {
        this.certificateNum = certificateNum;
    }

    /** 获取 账单制作方式 0：预收型  1：实做型 */
    public Integer getBillMadeMethod() {
        return this.billMadeMethod;
    }

    /** 设置 账单制作方式 0：预收型  1：实做型 */
    public void setBillMadeMethod(Integer billMadeMethod) {
        this.billMadeMethod = billMadeMethod;
    }

    public Integer getMemberId() {
        return this.memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }
}
