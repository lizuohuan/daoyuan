package com.magic.daoyuan.business.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 公司绑定缴金地
 * @author lzh
 * @create 2017/10/10 10:51
 */
public class CompanyPayPlace implements Serializable {

    /** 主键ID */
    private Integer id;

    /** 是否有效 0 无效 1 有效 */
    private Integer isValid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    /** 客户（公司）id */
    private Integer companyId;

    /** 缴金地id */
    private Integer payPlaceId;

    /** 经办机构名 */
    private String organizationName;

    /** 办理方名 */
    private String transactorName;

    /** 类型 0：社保 1：公积金 */
    private Integer type;

    /** 当是公积金的时候，缴金地ID */
    private Integer organizationId;


    /** type = 0 时使用 公司缴纳 根据公司缴纳类型填写  金额/比例 */
    private Double coPayPrice;

    /** type = 0 时使用 个人缴纳金额/比例 */
    private Double mePayPrice;

    /*************  业务字段 不进行数据库持久化  ***************/

    /** 缴金地名 */
    private String payPlaceName;

    /** 单位编码 */
    private String coding;

    /** 公司下险种 */
    private List<CompanyInsurance> companyInsurances;

    /** 获取 客户（公司）id */
    public Integer getCompanyId() {
        return this.companyId;
    }

    /** 设置 客户（公司）id */
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    /** 获取 缴金地id */
    public Integer getPayPlaceId() {
        return this.payPlaceId;
    }

    /** 设置 缴金地id */
    public void setPayPlaceId(Integer payPlaceId) {
        this.payPlaceId = payPlaceId;
    }

    /** 获取 经办机构名 */
    public String getOrganizationName() {
        return this.organizationName;
    }

    /** 设置 经办机构名 */
    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    /** 获取 办理方名 */
    public String getTransactorName() {
        return this.transactorName;
    }

    /** 设置 办理方名 */
    public void setTransactorName(String transactorName) {
        this.transactorName = transactorName;
    }

    /** 获取 缴金地名 */
    public String getPayPlaceName() {
        return this.payPlaceName;
    }

    /** 设置 缴金地名 */
    public void setPayPlaceName(String payPlaceName) {
        this.payPlaceName = payPlaceName;
    }

    /** 获取 公司下险种 */
    public List<CompanyInsurance> getCompanyInsurances() {
        return this.companyInsurances;
    }

    /** 设置 公司下险种 */
    public void setCompanyInsurances(List<CompanyInsurance> companyInsurances) {
        this.companyInsurances = companyInsurances;
    }

    /** 获取 类型 0：社保 1：公积金 */
    public Integer getType() {
        return this.type;
    }

    /** 设置 类型 0：社保 1：公积金 */
    public void setType(Integer type) {
        this.type = type;
    }


    /** 获取 type = 0 时使用 公司缴纳 根据公司缴纳类型填写  金额/比例 */
    public Double getCoPayPrice() {
        return this.coPayPrice;
    }

    /** 设置 type = 0 时使用 公司缴纳 根据公司缴纳类型填写  金额/比例 */
    public void setCoPayPrice(Double coPayPrice) {
        this.coPayPrice = coPayPrice;
    }

    /** 获取 type = 0 时使用 个人缴纳金额/比例 */
    public Double getMePayPrice() {
        return this.mePayPrice;
    }

    /** 设置 type = 0 时使用 个人缴纳金额/比例 */
    public void setMePayPrice(Double mePayPrice) {
        this.mePayPrice = mePayPrice;
    }

    /** 获取 当是公积金的时候，缴金地ID */
    public Integer getOrganizationId() {
        return this.organizationId;
    }

    /** 设置 当是公积金的时候，缴金地ID */
    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

    /** 获取 单位编码 */
    public String getCoding() {
        return this.coding;
    }

    /** 设置 单位编码 */
    public void setCoding(String coding) {
        this.coding = coding;
    }
}
