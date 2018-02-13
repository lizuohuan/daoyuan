package com.magic.daoyuan.business.entity;

import java.io.Serializable;

/**
 *  快递公司
 * Created by Eric Xie on 2017/9/21 0021.
 */

public class ExpressCompany extends BaseEntity implements Serializable {

    private String expressCompanyName;

    private String url;


    public String getExpressCompanyName() {
        return this.expressCompanyName;
    }

    public void setExpressCompanyName(String expressCompanyName) {
        this.expressCompanyName = expressCompanyName;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
