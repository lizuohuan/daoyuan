package com.magic.daoyuan.business.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 办理方
 * @author lzh
 * @create 2017/9/27 14:53
 */
public class Transactor implements Serializable {


    private Integer id;

    /** 操作方式  0：本月  1：次月  2：上月 */
    private Integer operationType;

    /** 最晚实做日期 XX日XX点（24小时制） */
    private Integer inTheEndTime;

    /** XX日XX点（24小时制），提醒规则：提前2个工作日提醒一次（站内信-通知），当天早上提醒一次（待办事项） */
    private Integer remindTime;

    /** 提醒内容 */
    private String remindContent;

    /** 是否有效 0 无效 1有效 */
    private Integer isValid;

    /** 最小范围 公积金使用 */
    private Double minScope;

    /** 最大范围 公积金使用 */
    private Double maxScope;

    /** 经办机构id */
    private Integer organizationId;

    /** type=0绑定供应商id /  type=1公司（客户）id */
    private Integer supplierId;

    /** 办理方类型 0：供应商  1：公司（客户） */
    private Integer type;

    /** type=0供应商名称 当type = 1 时 公司名字 */
    private String transactorName;

    /** 是否预收 0 否 1 是 */
    private Integer isReceive;

    /** 是否实做 0 否 1 是 */
    private Integer isImplements;

    /** 0:办理方  1:托管的办理方 */
    private Integer flag;

    /*************  业务字段 不进行数据库持久化  ***************/

    /** 办理方下所有办理方 绑定 险种档次 */
    private List<PayTheWay> payTheWays;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    /** 获取 是否有效 0 无效 1有效 */
    public Integer getIsValid() {
        return this.isValid;
    }

    /** 设置 是否有效 0 无效 1有效 */
    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    /** 获取 供应商名称 */
    public String getTransactorName() {
        return this.transactorName;
    }

    /** 设置 供应商名称 */
    public void setTransactorName(String transactorName) {
        this.transactorName = transactorName;
    }

    /** 获取 最小范围 */
    public Double getMinScope() {
        return this.minScope;
    }

    /** 设置 最小范围 */
    public void setMinScope(Double minScope) {
        this.minScope = minScope;
    }

    /** 获取 最大范围 */
    public Double getMaxScope() {
        return this.maxScope;
    }

    /** 设置 最大范围 */
    public void setMaxScope(Double maxScope) {
        this.maxScope = maxScope;
    }

    /** 获取 经办机构id */
    public Integer getOrganizationId() {
        return this.organizationId;
    }

    /** 设置 经办机构id */
    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }


    /** 获取 绑定供应商id */
    public Integer getSupplierId() {
        return this.supplierId;
    }

    /** 设置 绑定供应商id */
    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    /** 获取 办理方类型 0：供应商  1：公司（客户） */
    public Integer getType() {
        return this.type;
    }

    /** 设置 办理方类型 0：供应商  1：公司（客户） */
    public void setType(Integer type) {
        this.type = type;
    }

    /** 获取 办理方下所有办理方 绑定 险种档次 */
    public List<PayTheWay> getPayTheWays() {
        return this.payTheWays;
    }

    /** 设置 办理方下所有办理方 绑定 险种档次 */
    public void setPayTheWays(List<PayTheWay> payTheWays) {
        this.payTheWays = payTheWays;
    }

    /** 获取 是否预收 0 否 1 是 */
    public Integer getIsReceive() {
        return this.isReceive;
    }

    /** 设置 是否预收 0 否 1 是 */
    public void setIsReceive(Integer isReceive) {
        this.isReceive = isReceive;
    }

    /** 获取 是否实做 0 否 1 是 */
    public Integer getIsImplements() {
        return this.isImplements;
    }

    /** 设置 是否实做 0 否 1 是 */
    public void setIsImplements(Integer isImplements) {
        this.isImplements = isImplements;
    }

    /** 获取 0:办理方  1:托管的办理方 */
    public Integer getFlag() {
        return this.flag;
    }

    /** 设置 0:办理方  1:托管的办理方 */
    public void setFlag(Integer flag) {
        this.flag = flag;
    }
}
