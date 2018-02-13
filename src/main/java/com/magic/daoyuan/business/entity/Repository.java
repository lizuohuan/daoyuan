package com.magic.daoyuan.business.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 知识库实体
 */
public class Repository implements Serializable{

    /**知识库ID**/
    private Integer id;

    /**类型+数字生成，数字规则：YYMM00（当月第多少个）**/
    private String KBID;

    /**标题**/
    private String title;

    /**类型：0: HT、1：TS、2：SOP**/
    private Integer type;

    /**关键词**/
    private String antistop;

    /**有效期限**/
    private Date startValidity;

    /**有效期限**/
    private Date endValidity;

    /**外部信息**/
    private String externalInfo;

    /**内部信息**/
    private String interiorInfo;

    /**可查看角色ID，多个**/
    private String roleIds;

    /**创建人Id**/
    private Integer createUserId;

    /**创建人名字**/
    private String createUserName;

    /**修改人ID**/
    private Integer updateUserId;

    /**修改人名字**/
    private String updateUserName;

    /**修改时间**/
    private Date updateTime;

    /**创建时间**/
    private Date createTime;

    /**是否有效 0：有效、 1：过期**/
    private Integer isValid;

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKBID() {
        return KBID;
    }

    public void setKBID(String KBID) {
        this.KBID = KBID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getAntistop() {
        return antistop;
    }

    public void setAntistop(String antistop) {
        this.antistop = antistop;
    }

    public Date getStartValidity() {
        return startValidity;
    }

    public void setStartValidity(Date startValidity) {
        this.startValidity = startValidity;
    }

    public Date getEndValidity() {
        return endValidity;
    }

    public void setEndValidity(Date endValidity) {
        this.endValidity = endValidity;
    }

    public String getExternalInfo() {
        return externalInfo;
    }

    public void setExternalInfo(String externalInfo) {
        this.externalInfo = externalInfo;
    }

    public String getInteriorInfo() {
        return interiorInfo;
    }

    public void setInteriorInfo(String interiorInfo) {
        this.interiorInfo = interiorInfo;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
