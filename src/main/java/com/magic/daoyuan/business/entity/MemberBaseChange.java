package com.magic.daoyuan.business.entity;

import java.io.Serializable;

/**
 *  增减变子类的 基础字段变更记录
 * Created by Eric Xie on 2017/11/30 0030.
 */
public class MemberBaseChange implements Serializable {


    private Integer id;

    /** 子类ID */
    private Integer recordItemId;

    /** 0:姓名 1:证件类型 2:证件号 3:手机号 4:学历 5:档次 6:基数 */
    private Integer type;

    /** 变更后的记录 */
    private String content;

    /** 变更ID */
    private Integer targetId;


    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRecordItemId() {
        return this.recordItemId;
    }

    public void setRecordItemId(Integer recordItemId) {
        this.recordItemId = recordItemId;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    /** 获取 变更ID */
    public Integer getTargetId() {
        return this.targetId;
    }

    /** 设置 变更ID */
    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }
}
