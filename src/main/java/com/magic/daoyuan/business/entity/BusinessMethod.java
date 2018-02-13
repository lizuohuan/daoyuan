package com.magic.daoyuan.business.entity;

import java.io.Serializable;

/**
 * 公司业务关联 的 合作方式
 * 针对 社保、公积金
 * Created by Eric Xie on 2017/9/22 0022.
 */
public class BusinessMethod extends BaseEntity implements Serializable {

    private Integer companyBusinessId;

    /** 0 : 代理  */
    private Integer daiLi;

    /** 1 : 托管 */
    private Integer tuoGuan;


    public Integer getCompanyBusinessId() {
        return this.companyBusinessId;
    }

    public void setCompanyBusinessId(Integer companyBusinessId) {
        this.companyBusinessId = companyBusinessId;
    }


    /** 获取 0 : 代理  */
    public Integer getDaiLi() {
        return this.daiLi;
    }

    /** 设置 0 : 代理  */
    public void setDaiLi(Integer daiLi) {
        this.daiLi = daiLi;
    }

    /** 获取 1 : 托管 */
    public Integer getTuoGuan() {
        return this.tuoGuan;
    }

    /** 设置 1 : 托管 */
    public void setTuoGuan(Integer tuoGuan) {
        this.tuoGuan = tuoGuan;
    }
}
