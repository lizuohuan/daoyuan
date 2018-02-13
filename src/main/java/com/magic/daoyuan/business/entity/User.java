package com.magic.daoyuan.business.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Eric Xie on 2017/9/12 0012.
 */
public class User extends BaseEntity implements Serializable {

    /** 角色ID */
    private Integer roleId;

    /** 角色名称 */
    private String roleName;

    /** 帐号 */
    private String account;

    /** 密码 */
    private String pwd;

    /** 姓名 */
    private String userName;

    /** email */
    private String email;

    /** 工作电话 */
    private String workPhone;

    /** 私人电话 */
    private String homePhone;

    /** 创建时间 */
    private Date createTime;

    public Integer getRoleId() {
        return roleId;
    }

    public User setRoleId(Integer roleId) {
        this.roleId = roleId;
        return this;
    }

    public String getRoleName() {
        return roleName;
    }

    public User setRoleName(String roleName) {
        this.roleName = roleName;
        return this;
    }

    public String getAccount() {
        return account;
    }

    public User setAccount(String account) {
        this.account = account;
        return this;
    }

    public String getPwd() {
        return pwd;
    }

    public User setPwd(String pwd) {
        this.pwd = pwd;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public User setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public User setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
        return this;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public User setHomePhone(String homePhone) {
        this.homePhone = homePhone;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public User setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
}
