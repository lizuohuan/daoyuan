package com.magic.daoyuan.business.entity;

import java.io.Serializable;

/**
 * 员工社保编码
 * Created by Eric Xie on 2017/12/14 0014.
 */
public class MemberNumber implements Serializable {


    private Integer id;

    /** 员工ID */
    private Integer memberId;

    /** 社保编码 */
    private String serialNumber;

    /** 缴金地ID  一个缴金地下只有一个社保编码 */
    private Integer payPlaceId;


    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /** 获取 员工ID */
    public Integer getMemberId() {
        return this.memberId;
    }

    /** 设置 员工ID */
    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    /** 获取 社保编码 */
    public String getSerialNumber() {
        return this.serialNumber;
    }

    /** 设置 社保编码 */
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    /** 获取 缴金地ID  一个缴金地下只有一个社保编码 */
    public Integer getPayPlaceId() {
        return this.payPlaceId;
    }

    /** 设置 缴金地ID  一个缴金地下只有一个社保编码 */
    public void setPayPlaceId(Integer payPlaceId) {
        this.payPlaceId = payPlaceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MemberNumber that = (MemberNumber) o;

        if (!memberId.equals(that.memberId)) return false;
        if (!serialNumber.equals(that.serialNumber)) return false;
        return payPlaceId.equals(that.payPlaceId);

    }

    @Override
    public int hashCode() {
        int result = memberId.hashCode();
        result = 31 * result + serialNumber.hashCode();
        try {
            result = 31 * result + payPlaceId.hashCode();
        } catch (Exception e) {
            System.out.println("memberId 没有叫金地————————————   " + memberId);
            e.printStackTrace();
        }
        return result;
    }
}
