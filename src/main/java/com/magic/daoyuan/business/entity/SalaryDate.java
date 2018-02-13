package com.magic.daoyuan.business.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 工资发放时间
 * Created by Eric Xie on 2017/9/22 0022.
 */
public class SalaryDate extends BaseEntity implements Serializable {

    /** 公司ID */
    private Integer companyId;

    /** 发放日期 */
    private Date grantDate;

    /** 获取 公司ID */
    public Integer getCompanyId() {
        return this.companyId;
    }

    /** 设置 公司ID */
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    /** 获取 发放日期 */
    public Date getGrantDate() {
        return this.grantDate;
    }

    /** 设置 发放日期 */
    public void setGrantDate(Date grantDate) {
        this.grantDate = grantDate;
    }
}
