package com.magic.daoyuan.business.entity;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 工单实体
 */
public class WorkOrder implements Serializable {

    /**工单ID**/
    private Integer id;

    /**服务类型 0: 社保特殊补缴、1: 社保特殊减少、2: 社保紧急制卡、3: 当月参保类型修改、4: 公积金退费、5: 公积金特殊修改基数、6: 公积金个人收入证明、7:自由流程**/
    private Integer serviceType;

    /**服务名称**/
    private String serviceName;

    /**公司ID **/
    private Integer companyId;

    /**员工ID **/
    private Integer memberId;

    /**申请人 **/
    private Integer proposerId;

    /**客户编号 **/
    private String number;

    /**账单月 **/
    private Date theBillingMonth;

    /**审批人ID **/
    private Integer userId;

    /**角色ID **/
    private Integer roleId;

    /**附件Url **/
    private String accessory;

    /**备注 **/
    private String remark;

    /**创建时间 **/
    private Date createTime;

    /**状态 0 = 待审批, 1 = 同意， 2 不同意 ,3 完成, 4 作废, 5 返回申请人确认**/
    private Integer status;

    /**工单进度集合**/
    private List<WorkOrderSchedule> workOrderSchedules;

    /**最晚处理时间**/
    private Date latestTime;


    /***************************************************不持久的字段**/

    /**公司名字 **/
    private String companyName;

    /**员工名字 **/
    private String memberName;

    /**审批人名字 **/
    private String userName;

    /**角色名字 **/
    private String roleName;

    /**申请人 **/
    private String proposerName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getTheBillingMonth() {
        return theBillingMonth;
    }

    public void setTheBillingMonth(Date theBillingMonth) {
        this.theBillingMonth = theBillingMonth;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getAccessory() {
        return accessory;
    }

    public void setAccessory(String accessory) {
        this.accessory = accessory;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<WorkOrderSchedule> getWorkOrderSchedules() {
        return workOrderSchedules;
    }

    public void setWorkOrderSchedules(List<WorkOrderSchedule> workOrderSchedules) {
        this.workOrderSchedules = workOrderSchedules;
    }

    public Integer getProposerId() {
        return proposerId;
    }

    public void setProposerId(Integer proposerId) {
        this.proposerId = proposerId;
    }

    public String getProposerName() {
        return proposerName;
    }

    public void setProposerName(String proposerName) {
        this.proposerName = proposerName;
    }

    public Integer getServiceType() {
        return serviceType;
    }

    public void setServiceType(Integer serviceType) {
        this.serviceType = serviceType;
    }

    public Date getLatestTime() {
        return latestTime;
    }

    public void setLatestTime(Date latestTime) {
        this.latestTime = latestTime;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
