package com.magic.daoyuan.business.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 险种
 * Created by Eric Xie on 2017/9/27 0027.
 */
public class Insurance extends BaseEntity implements Serializable {


    /** 保险名称 */
    private String insuranceName;

    /** 备注 */
    private String remark;

    /** 缴金地ID */
    private Integer payPlaceId;

    /***********  业务字段 不进行数据库持久化  ************/

    /** 险种档次缴纳规则 */
    private List<PayTheWay> payTheWays;


    /** 获取 保险名称 */
    public String getInsuranceName() {
        return this.insuranceName;
    }

    /** 设置 保险名称 */
    public void setInsuranceName(String insuranceName) {
        this.insuranceName = insuranceName;
    }

    /** 获取 备注 */
    public String getRemark() {
        return this.remark;
    }

    /** 设置 备注 */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /** 获取 缴金地ID */
    public Integer getPayPlaceId() {
        return this.payPlaceId;
    }

    /** 设置 缴金地ID */
    public void setPayPlaceId(Integer payPlaceId) {
        this.payPlaceId = payPlaceId;
    }

    /** 获取 险种档次缴纳规则 */
    public List<PayTheWay> getPayTheWays() {
        return this.payTheWays;
    }

    /** 设置 险种档次缴纳规则 */
    public void setPayTheWays(List<PayTheWay> payTheWays) {
        this.payTheWays = payTheWays;
    }
}
