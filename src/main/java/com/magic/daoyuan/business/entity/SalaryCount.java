package com.magic.daoyuan.business.entity;

import java.io.Serializable;

/**
 * Created by Eric Xie on 2017/10/19 0019.
 */
public class SalaryCount implements Serializable {

    private Integer id;

    /** 税前所得  */
    private Double amount;

    /** 征收税率 */
    private Double ratio;

    /** 速算扣除数 */
    private Double deduct;


    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /** 获取 税前所得  */
    public Double getAmount() {
        return this.amount;
    }

    /** 设置 税前所得  */
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    /** 获取 征收税率 */
    public Double getRatio() {
        return this.ratio;
    }

    /** 设置 征收税率 */
    public void setRatio(Double ratio) {
        this.ratio = ratio;
    }

    /** 获取 速算扣除数 */
    public Double getDeduct() {
        return this.deduct;
    }

    /** 设置 速算扣除数 */
    public void setDeduct(Double deduct) {
        this.deduct = deduct;
    }
}
