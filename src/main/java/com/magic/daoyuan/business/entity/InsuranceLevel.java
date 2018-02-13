package com.magic.daoyuan.business.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 险种档次
 * Created by Eric Xie on 2017/9/27 0027.
 */
public class InsuranceLevel extends BaseEntity implements Serializable {


    /** 档次名称 */
    private String levelName;

    /** 缴金地ID */
    private Integer payPlaceId;

    /********  业务字段 不进行数据库持久化    *******/

    /** 险种集合 */
    private List<Insurance> insurances;

    /** 险种档次配置 */
    private List<PayTheWay> payTheWays;

    /** 获取 档次名称 */
    public String getLevelName() {
        return this.levelName;
    }

    /** 设置 档次名称 */
    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    /** 获取 缴金地ID */
    public Integer getPayPlaceId() {
        return this.payPlaceId;
    }

    /** 设置 缴金地ID */
    public void setPayPlaceId(Integer payPlaceId) {
        this.payPlaceId = payPlaceId;
    }

    /** 获取 险种集合 */
    public List<Insurance> getInsurances() {
        return this.insurances;
    }

    /** 设置 险种集合 */
    public void setInsurances(List<Insurance> insurances) {
        this.insurances = insurances;
    }

    /** 获取 险种档次 */
    public List<PayTheWay> getPayTheWays() {
        return this.payTheWays;
    }

    /** 设置 险种档次 */
    public void setPayTheWays(List<PayTheWay> payTheWays) {
        this.payTheWays = payTheWays;
    }
}
