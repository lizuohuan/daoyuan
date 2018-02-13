package com.magic.daoyuan.business.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 账单确认率、核销率
 * Created by Eric Xie on 2017/12/26 0026.
 */
public class CompanyBillCount implements Serializable {

    private Integer id;

    private Integer companyId;

    /** 账单年月 */
    private Date billMonth;

    /** 0: 账单确认  1：账单核销 */
    private Integer type;

    /** 核销/账单确认时间 */
    private Date createTime;

    /** 公司是否同行 */
    private Integer isPeer;


    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Date getBillMonth() {
        return this.billMonth;
    }

    public void setBillMonth(Date billMonth) {
        this.billMonth = billMonth;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /** 获取 公司是否同行 */
    public Integer getIsPeer() {
        return this.isPeer;
    }

    /** 设置 公司是否同行 */
    public void setIsPeer(Integer isPeer) {
        this.isPeer = isPeer;
    }
}
