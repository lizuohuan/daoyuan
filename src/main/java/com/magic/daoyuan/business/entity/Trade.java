package com.magic.daoyuan.business.entity;

import java.io.Serializable;

/**
 * Trade Entity
 * Created by Eric Xie on 2017/9/12 0012.
 */
public class Trade extends BaseEntity implements Serializable {

    /** 行业名称 */
    private String tradeName;

    /** 描述 */
    private String describe;

    public String getTradeName() {
        return tradeName;
    }

    public Trade setTradeName(String tradeName) {
        this.tradeName = tradeName;
        return this;
    }

    public String getDescribe() {
        return describe;
    }

    public Trade setDescribe(String describe) {
        this.describe = describe;
        return this;
    }
}
