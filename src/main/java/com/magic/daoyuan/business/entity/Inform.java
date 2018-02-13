package com.magic.daoyuan.business.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 通知
 * @author lzh
 * @create 2017/12/21 17:11
 */
public class Inform implements Serializable {


    private Integer id;

    /** 用户id */
    private Integer userId;

    /** 角色id */
    private Integer roleId;

    /** 内容 */
    private String content;

    /** 创建时间 */
    private Date createTime;


    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /** 获取 内容 */
    public String getContent() {
        return this.content;
    }

    /** 设置 内容 */
    public void setContent(String content) {
        this.content = content;
    }

    /** 获取 创建时间 */
    public Date getCreateTime() {
        return this.createTime;
    }

    /** 设置 创建时间 */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /** 获取 用户id */
    public Integer getUserId() {
        return this.userId;
    }

    /** 设置 用户id */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /** 获取 角色id */
    public Integer getRoleId() {
        return this.roleId;
    }

    /** 设置 角色id */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
