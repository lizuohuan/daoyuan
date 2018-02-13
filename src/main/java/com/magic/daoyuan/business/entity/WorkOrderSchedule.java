package com.magic.daoyuan.business.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 工单进度实体
 */
public class WorkOrderSchedule implements Serializable {

    /**工单进度ID**/
    private Integer id;

    /**工单ID **/
    private Integer workOrderId;

    /**申请人 **/
    private Integer proposerId;

    /**当前审批人ID **/
    private Integer userId;

    /**当前审批角色ID **/
    private Integer roleId;

    /**下级审批人ID **/
    private Integer nextUserId;

    /**下级审批角色ID **/
    private Integer nextRoleId;

    /**附件Url **/
    private String accessory;

    /**提醒方式 **/
    private Integer remindWay;

    /**备注 **/
    private String remark;

    /**创建时间 **/
    private Date createTime;

    /**状态 0 = 待审批, 1 = 同意， 2 不同意 ,3 完成 ，4 作废, 5 返回申请人确认**/
    private Integer status;

    /**服务类型 0: 社保特殊补缴、1: 社保特殊减少、2: 社保紧急制卡、3: 当月参保类型修改、4: 公积金退费、5: 公积金特殊修改基数、6: 公积金个人收入证明、7:自由流程**/
    private Integer serviceType;

    /***************************************************不持久的字段**/

    /**当前审批人名字 **/
    private String userName;

    /**当前审批角色名字 **/
    private String roleName;

    /**下级审批人名字 **/
    private String nextUserName;

    /**下级审批角色名字 **/
    private String nextRoleName;

    /**申请人 **/
    private String proposerName;

    public Integer getServiceType() {
        return serviceType;
    }

    public void setServiceType(Integer serviceType) {
        this.serviceType = serviceType;
    }

    public String getProposerName() {
        return proposerName;
    }

    public void setProposerName(String proposerName) {
        this.proposerName = proposerName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(Integer workOrderId) {
        this.workOrderId = workOrderId;
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

    public Integer getRemindWay() {
        return remindWay;
    }

    public void setRemindWay(Integer remindWay) {
        this.remindWay = remindWay;
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

    public Integer getProposerId() {
        return proposerId;
    }

    public void setProposerId(Integer proposerId) {
        this.proposerId = proposerId;
    }

    public Integer getNextUserId() {
        return nextUserId;
    }

    public void setNextUserId(Integer nextUserId) {
        this.nextUserId = nextUserId;
    }

    public Integer getNextRoleId() {
        return nextRoleId;
    }

    public void setNextRoleId(Integer nextRoleId) {
        this.nextRoleId = nextRoleId;
    }

    public String getNextUserName() {
        return nextUserName;
    }

    public void setNextUserName(String nextUserName) {
        this.nextUserName = nextUserName;
    }

    public String getNextRoleName() {
        return nextRoleName;
    }

    public void setNextRoleName(String nextRoleName) {
        this.nextRoleName = nextRoleName;
    }
}
