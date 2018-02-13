package com.magic.daoyuan.business.entity;

import java.io.Serializable;

/**
 * Created by Eric Xie on 2017/10/18 0018.
 */
public class SalaryType extends BaseEntity implements Serializable {


    /** 工资类型名称 */
    private String typeName;


    /** 获取 工资类型名称 */
    public String getTypeName() {
        return this.typeName;
    }

    /** 设置 工资类型名称 */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
