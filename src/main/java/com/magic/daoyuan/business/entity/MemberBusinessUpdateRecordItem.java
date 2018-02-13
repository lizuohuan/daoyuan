package com.magic.daoyuan.business.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 实做-员工业务增减变记录表 - 子类  记录具体的增减变操作记录
 * @author lzh
 * @create 2017/10/25 19:51
 */
public class MemberBusinessUpdateRecordItem implements Serializable {


    private Integer id;

    /** 实做-员工业务增减变记录表Id */
    private Integer memberBusinessUpdateRecordId;

    /** 原因 */
    private String  reason;

    /** 变更内容 */
    private String updateContent;

    /** 服务时间 服务结束月、服务生效月 */
    private Date serviceMonth;

    /** 是否是最新添加 0：否  1： 是   当上级处理成功或者失败  更新此字段 为1 */
    private Integer isNowCreate;

    /** 0：待申请 1：待反馈  2：成功  3: 失败 4: 失效
     */
    private Integer status;

    /** 创建时间 */
    private Date createTime;

    /** 根据员工管理的增减变由系统自行生成，0增员、1减员、2变更 */
    private Integer serviceStatus;

    /** 缴金地id */
    private Integer payPlaceId;

    /** 经办机构id */
    private Integer organizationId;

    /** 办理方id */
    private Integer transactorId;

    /** 档次id */
    private Integer insuranceLevelId;

    /** 备注 */
    private String remark;

    /** 是否计算异动量 0：否  1：是 */
    private Integer isCalculate;

    /** 服务方式 0：代理  1：托管 */
    private Integer serveMethod;

    private Double ratio;

    private Integer baseType;

    private Double baseNumber;

    private Integer updateContentFlag;

    /** 创建人 */
    private Integer createUserId;

    /** 计算异动量的月份 */
    private Date billMonth;

    /** 公司id */
    private Integer companyId;

    /********  业务字段 不进行数据库持久化    *******/

    /** 缴金地名 */
    private String payPlaceName;

    private String createUserName;

    /** 经办机构名 */
    private String organizationName;

    /** 办理方名 */
    private String transactorName;

    /** 档次 */
    private String insuranceLevelName;

    /** 变更记录下的变更列表 */
    private List<MemberBaseChange> baseChangeList;

    /** 前道客服id */
    private Integer userId;

    /** 员工id */
    private Integer memberId;

    /** 地区id */
    private Integer cityId;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /** 获取 实做-员工业务增减变记录表Id */
    public Integer getMemberBusinessUpdateRecordId() {
        return this.memberBusinessUpdateRecordId;
    }

    /** 设置 实做-员工业务增减变记录表Id */
    public void setMemberBusinessUpdateRecordId(Integer memberBusinessUpdateRecordId) {
        this.memberBusinessUpdateRecordId = memberBusinessUpdateRecordId;
    }

    /** 获取 原因 */
    public String getReason() {
        return this.reason;
    }

    /** 设置 原因 */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /** 获取 变更内容 */
    public String getUpdateContent() {
        return this.updateContent;
    }

    /** 设置 变更内容 */
    public void setUpdateContent(String updateContent) {
        this.updateContent = updateContent;
    }

    /** 获取 服务时间 服务结束月、服务生效月 */
    public Date getServiceMonth() {
        return this.serviceMonth;
    }

    /** 设置 服务时间 服务结束月、服务生效月 */
    public void setServiceMonth(Date serviceMonth) {
        this.serviceMonth = serviceMonth;
    }

    /** 获取 是否是最新添加 0：否  1： 是   当上级处理成功或者失败  更新此字段 为1 */
    public Integer getIsNowCreate() {
        return this.isNowCreate;
    }

    /** 设置 是否是最新添加 0：否  1： 是   当上级处理成功或者失败  更新此字段 为1 */
    public void setIsNowCreate(Integer isNowCreate) {
        this.isNowCreate = isNowCreate;
    }

    /** 获取 0：待反馈  1：成功  2：失败 */
    public Integer getStatus() {
        return this.status;
    }

    /** 设置 0：待反馈  1：成功  2：失败 */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /** 获取 创建时间 */
    public Date getCreateTime() {
        return this.createTime;
    }

    /** 设置 创建时间 */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /** 获取 根据员工管理的增减变由系统自行生成，0增员、1减员、2变更 */
    public Integer getServiceStatus() {
        return this.serviceStatus;
    }

    /** 设置 根据员工管理的增减变由系统自行生成，0增员、1减员、2变更 */
    public void setServiceStatus(Integer serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    /** 获取 缴金地id */
    public Integer getPayPlaceId() {
        return this.payPlaceId;
    }

    /** 设置 缴金地id */
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

    /** 获取 档次id */
    public Integer getInsuranceLevelId() {
        return this.insuranceLevelId;
    }

    /** 设置 档次id */
    public void setInsuranceLevelId(Integer insuranceLevelId) {
        this.insuranceLevelId = insuranceLevelId;
    }

    /** 获取 缴金地名 */
    public String getPayPlaceName() {
        return this.payPlaceName;
    }

    /** 设置 缴金地名 */
    public void setPayPlaceName(String payPlaceName) {
        this.payPlaceName = payPlaceName;
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

    /** 获取 档次 */
    public String getInsuranceLevelName() {
        return this.insuranceLevelName;
    }

    /** 设置 档次 */
    public void setInsuranceLevelName(String insuranceLevelName) {
        this.insuranceLevelName = insuranceLevelName;
    }

    /** 获取 备注 */
    public String getRemark() {
        return this.remark;
    }

    /** 设置 备注 */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /** 获取 是否计算异动量 0：否  1：是 */
    public Integer getIsCalculate() {
        return this.isCalculate;
    }

    /** 设置 是否计算异动量 0：否  1：是 */
    public void setIsCalculate(Integer isCalculate) {
        this.isCalculate = isCalculate;
    }

    /** 获取 服务方式 0：代理  1：托管 */
    public Integer getServeMethod() {
        return this.serveMethod;
    }

    /** 设置 服务方式 0：代理  1：托管 */
    public void setServeMethod(Integer serveMethod) {
        this.serveMethod = serveMethod;
    }

    public Double getRatio() {
        return this.ratio;
    }

    public void setRatio(Double ratio) {
        this.ratio = ratio;
    }

    public Integer getBaseType() {
        return this.baseType;
    }

    public void setBaseType(Integer baseType) {
        this.baseType = baseType;
    }

    public Double getBaseNumber() {
        return this.baseNumber;
    }

    public void setBaseNumber(Double baseNumber) {
        this.baseNumber = baseNumber;
    }

    public Integer getUpdateContentFlag() {
        return this.updateContentFlag;
    }

    public void setUpdateContentFlag(Integer updateContentFlag) {
        this.updateContentFlag = updateContentFlag;
    }

    /** 获取 变更记录下的变更列表 */
    public List<MemberBaseChange> getBaseChangeList() {
        return this.baseChangeList;
    }

    /** 设置 变更记录下的变更列表 */
    public void setBaseChangeList(List<MemberBaseChange> baseChangeList) {
        this.baseChangeList = baseChangeList;
    }

    /** 获取 前道客服id */
    public Integer getUserId() {
        return this.userId;
    }

    /** 设置 前道客服id */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /** 获取 地区id */
    public Integer getCityId() {
        return this.cityId;
    }

    /** 设置 地区id */
    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    /** 获取 创建人 */
    public Integer getCreateUserId() {
        return this.createUserId;
    }

    /** 设置 创建人 */
    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return this.createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    /** 获取 员工id */
    public Integer getMemberId() {
        return this.memberId;
    }

    /** 设置 员工id */
    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    /** 获取 计算异动量的月份 */
    public Date getBillMonth() {
        return this.billMonth;
    }

    /** 设置 计算异动量的月份 */
    public void setBillMonth(Date billMonth) {
        this.billMonth = billMonth;
    }

    /** 获取 公司id */
    public Integer getCompanyId() {
        return this.companyId;
    }

    /** 设置 公司id */
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
}
