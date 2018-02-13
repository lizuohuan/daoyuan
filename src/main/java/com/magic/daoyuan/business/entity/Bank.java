package com.magic.daoyuan.business.entity;

import java.io.Serializable;

/**
 * Created by Eric Xie on 2017/10/10 0010.
 */
public class Bank extends BaseEntity implements Serializable {

    /** 银行名字 */
    private String bankName;


    public String getBankName() {
        return this.bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}
