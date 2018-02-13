package com.magic.daoyuan.business.entity;

import java.io.Serializable;

/**
 * Created by Eric Xie on 2017/9/13 0013.
 */
public class ServiceFeeConfig extends BaseEntity implements Serializable {

    private String describe;

    public String getDescribe() {
        return describe;
    }

    public ServiceFeeConfig setDescribe(String describe) {
        this.describe = describe;
        return this;
    }
}
