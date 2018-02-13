package com.magic.daoyuan.business.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 缴金地 entity
 * Created by Eric Xie on 2017/9/27 0027.
 */
public class PayPlace extends BaseEntity implements Serializable {


    /** 缴金地的名字 */
    private String payPlaceName;

    /** 城市ID */
    private Integer cityId;

    /** 城市对象 */
    private City city;

    /** 缴金地类型 0：社保  1：公积金  InsuranceType.class */
    private Integer type;

    /** 年度调基月份 每年X月（此处仅文本，提醒用） */
    private Integer yearAlterMonth;

    /** 操作方式  0：本月  1：次月  2：上月 */
    private Integer operationType;

    /** 最晚实做日期 XX日XX点（24小时制） */
    private Integer inTheEndTime;

    /** XX日XX点（24小时制），提醒规则：提前2个工作日提醒一次（站内信-通知），当天早上提醒一次（待办事项） */
    private Integer remindTime;

    /** 提醒内容 */
    private String remindContent;

    /*************  业务字段 不进行数据库持久化  ***************/

    /** 缴金地下所有险种集合 */
    private List<Insurance> insurances;

    /** 缴金地下所有经办机构 */
    private List<Organization> organizations;

    /** 缴金地下所有档次集合 */
    private List<InsuranceLevel> insurancesLevelList;

    /** 获取 缴金地的名字 */
    public String getPayPlaceName() {
        return this.payPlaceName;
    }

    /** 设置 缴金地的名字 */
    public void setPayPlaceName(String payPlaceName) {
        this.payPlaceName = payPlaceName;
    }

    /** 获取 城市ID */
    public Integer getCityId() {
        return this.cityId;
    }

    /** 设置 城市ID */
    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    /** 获取 缴金地类型 0：社保  1：公积金  InsuranceType.class */
    public Integer getType() {
        return this.type;
    }

    /** 设置 缴金地类型 0：社保  1：公积金  InsuranceType.class */
    public void setType(Integer type) {
        this.type = type;
    }

    /** 获取 年度调基月份 每年X月（此处仅文本，提醒用） */
    public Integer getYearAlterMonth() {
        return this.yearAlterMonth;
    }

    /** 设置 年度调基月份 每年X月（此处仅文本，提醒用） */
    public void setYearAlterMonth(Integer yearAlterMonth) {
        this.yearAlterMonth = yearAlterMonth;
    }

    /** 获取 城市对象 */
    public City getCity() {
        return this.city;
    }

    /** 设置 城市对象 */
    public void setCity(City city) {
        this.city = city;
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

    /** 获取 缴金地下所有险种集合 */
    public List<Insurance> getInsurances() {
        return this.insurances;
    }

    /** 设置 缴金地下所有险种集合 */
    public void setInsurances(List<Insurance> insurances) {
        this.insurances = insurances;
    }

    /** 获取 缴金地下所有经办机构 */
    public List<Organization> getOrganizations() {
        return this.organizations;
    }

    /** 设置 缴金地下所有经办机构 */
    public void setOrganizations(List<Organization> organizations) {
        this.organizations = organizations;
    }

    /** 获取 缴金地下所有档次集合 */
    public List<InsuranceLevel> getInsurancesLevelList() {
        return this.insurancesLevelList;
    }

    /** 设置 缴金地下所有档次集合 */
    public void setInsurancesLevelList(List<InsuranceLevel> insurancesLevelList) {
        this.insurancesLevelList = insurancesLevelList;
    }
}
