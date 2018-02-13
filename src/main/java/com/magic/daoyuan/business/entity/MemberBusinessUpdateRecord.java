package com.magic.daoyuan.business.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 实做-员工业务增减变记录表
 * @author lzh
 * @create 2017/10/25 15:06
 */
public class MemberBusinessUpdateRecord implements Serializable {


    private Integer id;

    /** 员工id */
    private Integer memberId;

    /** 服务类型 0：社保  1：公积金 */
    private Integer serviceType;

    /** 社保或者公积金编号 */
    private String serviceNumber;

    /** 缴金地id */
    private Integer payPlaceId;

    /** 经办机构id */
    private Integer organizationId;

    /** 办理方id */
    private Integer transactorId;

    /** 0：申请 1：待反馈、2：成功、3：失败 4：部分成功 */
    private Integer status;

    /** 创建时间 */
    private Date createTime;

    /** 档次id */
    private Integer insuranceLevelId;

    /** JSONArray 备注信息 */
    private String remark;

    /********  业务字段 不进行数据库持久化    *******/

    /** 绑定员工业务 社保公积金 */
    private MemberBusinessItem memberBusinessItem;

    /** 公司名 */
    private String companyName;

    private String createUserName;

    /** 员工名 */
    private String userName;

    /** 证件类型 */
    private Integer certificateType;

    /** 证件编号 */
    private String certificateNum;

    /** 客服 */
    private String beforeService;

    /** 缴金地名 */
    private String payPlaceName;

    /** 经办机构名 */
    private String organizationName;

    /** 办理方名 */
    private String transactorName;

    /** 档次 */
    private String insuranceLevelName;

    /** 增减变记录 */
    private List<MemberBusinessUpdateRecordItem> memberBusinessUpdateRecordItems;

    /** 最新 根据员工管理的增减变由系统自行生成，0增员、1减员、2变更  */
    private Integer serviceStatus;


    private Integer mburiId;

    /** 失败原因 */
    private String failReason;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /** 获取 员工id */
    public Integer getMemberId() {
        return this.memberId;
    }

    /** 设置 员工id */
    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    /** 获取 服务类型 0：社保  1：公积金 */
    public Integer getServiceType() {
        return this.serviceType;
    }

    /** 设置 服务类型 0：社保  1：公积金 */
    public void setServiceType(Integer serviceType) {
        this.serviceType = serviceType;
    }

    /** 获取 社保或者公积金编号 */
    public String getServiceNumber() {
        return this.serviceNumber;
    }

    /** 设置 社保或者公积金编号 */
    public void setServiceNumber(String serviceNumber) {
        this.serviceNumber = serviceNumber;
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

    /** 获取 0：申请 1：待反馈、2：成功、3：失败 */
    public Integer getStatus() {
        return this.status;
    }

    /** 设置 0：申请 1：待反馈、2：成功、3：失败 */
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

    /** 获取 绑定员工业务 社保公积金 */
    public MemberBusinessItem getMemberBusinessItem() {
        return this.memberBusinessItem;
    }

    /** 设置 绑定员工业务 社保公积金 */
    public void setMemberBusinessItem(MemberBusinessItem memberBusinessItem) {
        this.memberBusinessItem = memberBusinessItem;
    }

    /** 获取 公司名 */
    public String getCompanyName() {
        return this.companyName;
    }

    /** 设置 公司名 */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /** 获取 员工名 */
    public String getUserName() {
        return this.userName;
    }

    /** 设置 员工名 */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /** 获取 证件类型 */
    public Integer getCertificateType() {
        return this.certificateType;
    }

    /** 设置 证件类型 */
    public void setCertificateType(Integer certificateType) {
        this.certificateType = certificateType;
    }

    /** 获取 证件编号 */
    public String getCertificateNum() {
        return this.certificateNum;
    }

    /** 设置 证件编号 */
    public void setCertificateNum(String certificateNum) {
        this.certificateNum = certificateNum;
    }

    /** 获取 客服 */
    public String getBeforeService() {
        return this.beforeService;
    }

    /** 设置 客服 */
    public void setBeforeService(String beforeService) {
        this.beforeService = beforeService;
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

    /** 获取 档次id */
    public Integer getInsuranceLevelId() {
        return this.insuranceLevelId;
    }

    /** 设置 档次id */
    public void setInsuranceLevelId(Integer insuranceLevelId) {
        this.insuranceLevelId = insuranceLevelId;
    }

    /** 获取 档次 */
    public String getInsuranceLevelName() {
        return this.insuranceLevelName;
    }

    /** 设置 档次 */
    public void setInsuranceLevelName(String insuranceLevelName) {
        this.insuranceLevelName = insuranceLevelName;
    }

    /** 获取 增减变记录 */
    public List<MemberBusinessUpdateRecordItem> getMemberBusinessUpdateRecordItems() {
        return this.memberBusinessUpdateRecordItems;
    }

    /** 设置 增减变记录 */
    public void setMemberBusinessUpdateRecordItems(List<MemberBusinessUpdateRecordItem> memberBusinessUpdateRecordItems) {
        this.memberBusinessUpdateRecordItems = memberBusinessUpdateRecordItems;
    }

    /** 获取 最新 根据员工管理的增减变由系统自行生成，0增员、1减员、2变更  */
    public Integer getServiceStatus() {
        return this.serviceStatus;
    }

    /** 设置 最新 根据员工管理的增减变由系统自行生成，0增员、1减员、2变更  */
    public void setServiceStatus(Integer serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    /** 获取 失败原因 */
    public String getFailReason() {
        return this.failReason;
    }

    /** 设置 失败原因 */
    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }

    /** 获取 JSONArray 备注信息 */
    public String getRemark() {
        return this.remark;
    }

    /** 设置 JSONArray 备注信息 */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateUserName() {
        return this.createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public Integer getMburiId() {
        return this.mburiId;
    }

    public void setMburiId(Integer mburiId) {
        this.mburiId = mburiId;
    }
}
