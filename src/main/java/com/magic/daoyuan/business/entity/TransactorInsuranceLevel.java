package com.magic.daoyuan.business.entity;

import java.io.Serializable;

/**
 * 办理方 绑定 险种档次缴纳规则id
 * @author lzh
 * @create 2017/9/28 11:43
 */
public class TransactorInsuranceLevel extends BaseEntity implements Serializable {

    /** 险种档次缴纳规则id */
    private Integer payTheWayId;

    /** 办理方id */
    private Integer transactorId;

     /** 公司缴纳类型 0：金额  1：比例 */
    private Integer coPayType;

    /** 公司缴纳 根据公司缴纳类型填写 金额/比例 */
    private Double coPayPrice;

    /** 个人缴纳类型 0：金额  1：比例 */
    private Integer mePayType;

    /** 个人缴纳金额/比例 */
    private Double mePayPrice;


    /********  业务字段 不进行数据库持久化    *******/

    /** 险种档次缴纳规则 */
    private PayTheWay payTheWay;


    /** 获取 险种档次缴纳规则id */
    public Integer getPayTheWayId() {
        return this.payTheWayId;
    }

    /** 设置 险种档次缴纳规则id */
    public void setPayTheWayId(Integer payTheWayId) {
        this.payTheWayId = payTheWayId;
    }

    /** 获取 办理方id */
    public Integer getTransactorId() {
        return this.transactorId;
    }

    /** 设置 办理方id */
    public void setTransactorId(Integer transactorId) {
        this.transactorId = transactorId;
    }

    /** 获取 公司缴纳类型 0：金额  1：比例 */
    public Integer getCoPayType() {
        return this.coPayType;
    }

    /** 设置 公司缴纳类型 0：金额  1：比例 */
    public void setCoPayType(Integer coPayType) {
        this.coPayType = coPayType;
    }

    /** 获取 公司缴纳 根据公司缴纳类型填写 金额/比例 */
    public Double getCoPayPrice() {
        return this.coPayPrice;
    }

    /** 设置 公司缴纳 根据公司缴纳类型填写 金额/比例 */
    public void setCoPayPrice(Double coPayPrice) {
        this.coPayPrice = coPayPrice;
    }

    /** 获取 个人缴纳类型 0：金额  1：比例 */
    public Integer getMePayType() {
        return this.mePayType;
    }

    /** 设置 个人缴纳类型 0：金额  1：比例 */
    public void setMePayType(Integer mePayType) {
        this.mePayType = mePayType;
    }

    /** 获取 个人缴纳金额/比例 */
    public Double getMePayPrice() {
        return this.mePayPrice;
    }

    /** 设置 个人缴纳金额/比例 */
    public void setMePayPrice(Double mePayPrice) {
        this.mePayPrice = mePayPrice;
    }

    /** 获取 险种档次缴纳规则 */
    public PayTheWay getPayTheWay() {
        return this.payTheWay;
    }

    /** 设置 险种档次缴纳规则 */
    public void setPayTheWay(PayTheWay payTheWay) {
        this.payTheWay = payTheWay;
    }
}
