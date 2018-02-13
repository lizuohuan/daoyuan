package com.magic.daoyuan.business.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 日志 entity
 * Created by Eric Xie on 2017/12/27 0027.
 */
public class Log implements Serializable {


    private Integer id;

    /** 创建者ID */
    private Integer userId;

    /** 操作者 */
    private String userName;

    /** 模块ID */
    private Integer model;

    /** 操作内容 */
    private String context;

    /**标记 0：增、1：删、2：改 */
    private Integer flag;

    /** 创建时间 */
    private Date createTime;

    public Log() {}

    public Log(Integer userId, Integer model, String context, Integer flag) {
        this.userId = userId;
        this.model = model;
        this.context = context;
        this.flag = flag;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getModel() {
        return this.model;
    }

    public void setModel(Integer model) {
        this.model = model;
    }

    public String getContext() {
        return this.context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Integer getFlag() {
        return this.flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /** 获取 操作者 */
    public String getUserName() {
        return this.userName;
    }

    /** 设置 操作者 */
    public void setUserName(String userName) {
        this.userName = userName;
    }
}
