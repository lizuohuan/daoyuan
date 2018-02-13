package com.magic.daoyuan.business.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 经办机构 entity
 * Created by Eric Xie on 2017/9/27 0027.
 */
public class Organization extends BaseEntity implements Serializable {

    /** 机构名称 */
    private String organizationName;

    /** 操作方式  0：本月  1：次月  2：上月 */
    private Integer operationType;

    /** 最晚实做日期 XX日XX点（24小时制） */
    private Integer inTheEndTime;

    /** XX日XX点（24小时制），提醒规则：提前2个工作日提醒一次（站内信-通知），当天早上提醒一次（待办事项） */
    private Integer remindTime;

    /** 提醒内容 */
    private String remindContent;

    /** 缴金地id */
    private Integer payPlaceId;

    /** 计算精度 3、2、1、0、-1、-2，对应保留小数位数 */
    private Integer computationalAccuracy;

    /** 填写精度 3、2、1、0、-1、-2，对应保留小数位数 */
    private Integer precision;

    /** 计算规则  0：四舍五入 1：升角省分（精度为0） 2：去尾  3：进一 */
    private Integer computationRule;

    /** 最小基数 */
    private Double minCardinalNumber;

    /** 最大基数 */
    private Double maxCardinalNumber;

    /** 经办机构类型 0：配置缴金地 1：公司*/
    private Integer type;

    /** 最大比例 */
    private Double max;

    /** 最小比例 */
    private Double min;

    /** 0: 经办机构  1：托管的经办机构 */
    private Integer flag;

    private String companyName;

    /*************  业务字段 不进行数据库持久化  ***************/
    /** 经办机构下所有办理方 */
    private List<Transactor> transactors;


    /** 获取 机构名称 */
    public String getOrganizationName() {
        return this.organizationName;
    }

    /** 设置 机构名称 */
    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    /** 获取 操作方式  0：本月  1：次月  2：上月 */
    public Integer getOperationType() {
        return this.operationType;
    }

    /** 设置 操作方式  0：本月  1：次月  2：上月 */
    public void setOperationType(Integer operationType) {
        this.operationType = operationType;
    }

    /** 获取 最晚实做日期 XX日XX点（24小时制） */
    public Integer getInTheEndTime() {
        return this.inTheEndTime;
    }

    /** 设置 最晚实做日期 XX日XX点（24小时制） */
    public void setInTheEndTime(Integer inTheEndTime) {
        this.inTheEndTime = inTheEndTime;
    }

    /** 获取 XX日XX点（24小时制），提醒规则：提前2个工作日提醒一次（站内信-通知），当天早上提醒一次（待办事项） */
    public Integer getRemindTime() {
        return this.remindTime;
    }

    /** 设置 XX日XX点（24小时制），提醒规则：提前2个工作日提醒一次（站内信-通知），当天早上提醒一次（待办事项） */
    public void setRemindTime(Integer remindTime) {
        this.remindTime = remindTime;
    }

    /** 获取 提醒内容 */
    public String getRemindContent() {
        return this.remindContent;
    }

    /** 设置 提醒内容 */
    public void setRemindContent(String remindContent) {
        this.remindContent = remindContent;
    }

    /** 获取 缴金地id */
    public Integer getPayPlaceId() {
        return this.payPlaceId;
    }

    /** 设置 缴金地id */
    public void setPayPlaceId(Integer payPlaceId) {
        this.payPlaceId = payPlaceId;
    }

    /** 获取 计算精度 */
    public Integer getComputationalAccuracy() {
        return this.computationalAccuracy;
    }

    /** 设置 计算精度 */
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

    /** 获取 经办机构下所有办理方 */
    public List<Transactor> getTransactors() {
        return this.transactors;
    }

    /** 设置 经办机构下所有办理方 */
    public void setTransactors(List<Transactor> transactors) {
        this.transactors = transactors;
    }

    /** 获取 填写精度 3、2、1、0、-1、-2，对应保留小数位数 */
    public Integer getPrecision() {
        return this.precision;
    }

    /** 设置 填写精度 3、2、1、0、-1、-2，对应保留小数位数 */
    public void setPrecision(Integer precision) {
        this.precision = precision;
    }

    /** 获取 经办机构类型 0：配置缴金地 1：公司*/
    public Integer getType() {
        return this.type;
    }

    /** 设置 经办机构类型 0：配置缴金地 1：公司*/
    public void setType(Integer type) {
        this.type = type;
    }

    /** 获取 最大比例 */
    public Double getMax() {
        return this.max;
    }

    /** 设置 最大比例 */
    public void setMax(Double max) {
        this.max = max;
    }

    /** 获取 最小比例 */
    public Double getMin() {
        return this.min;
    }

    /** 设置 最小比例 */
    public void setMin(Double min) {
        this.min = min;
    }

    /** 获取 0: 经办机构  1：托管的经办机构 */
    public Integer getFlag() {
        return this.flag;
    }

    /** 设置 0: 经办机构  1：托管的经办机构 */
    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
