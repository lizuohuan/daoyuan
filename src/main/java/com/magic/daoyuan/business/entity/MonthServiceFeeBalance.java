package com.magic.daoyuan.business.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 本月应该补差的服务费，纳入下月计算
 * @author lzh
 * @create 2018/1/2 10:51
 */
public class MonthServiceFeeBalance implements Serializable {


    private Integer id;

    /** 公司id */
    private Integer companyId;

    /** 账单月 */
    private Date billMonth;

    /** 账单月剩余服务费 */
    private Double serviceFeeBalance;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /** 获取 公司id */
    public Integer getCompanyId() {
        return this.companyId;
    }

    /** 设置 公司id */
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    /** 获取 账单月 */
    public Date getBillMonth() {
        return this.billMonth;
    }

    /** 设置 账单月 */
    public void setBillMonth(Date billMonth) {
        this.billMonth = billMonth;
    }

    /** 获取 账单月剩余服务费 */
    public Double getServiceFeeBalance() {
        return this.serviceFeeBalance;
    }

    /** 设置 账单月剩余服务费 */
    public void setServiceFeeBalance(Double serviceFeeBalance) {
        this.serviceFeeBalance = serviceFeeBalance;
    }
}
