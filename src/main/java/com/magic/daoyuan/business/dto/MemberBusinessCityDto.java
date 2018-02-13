package com.magic.daoyuan.business.dto;

import java.io.Serializable;

/**
 * 员工业务绑定的缴金地地区id
 * @author lzh
 * @create 2017/11/29 18:43
 */
public class MemberBusinessCityDto implements Serializable {

    /** 员工id */
    private Integer memberId;

    /** 地区id */
    private Integer cityId;


    /** 获取 员工id */
    public Integer getMemberId() {
        return this.memberId;
    }

    /** 设置 员工id */
    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    /** 获取 地区id */
    public Integer getCityId() {
        return this.cityId;
    }

    /** 设置 地区id */
    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }
}
