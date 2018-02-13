package com.magic.daoyuan.business.vo;

import java.util.Date;

/**
 * 个人开户
 * Created by Eric Xie on 2017/11/28 0028.
 */
public class PersonOpenAccount {

    /** 姓名 */
    private String memberName;

    /** 证件类型 */
    private Integer idCardType;

    /** 证件号 */
    private String idCard;

    /** 缴存基数 */
    private Integer baseNumber;

    /** 初次缴存时间 */
    private Date startTime;

    /** 人员身份 */
    private Integer identity;

    /** 个人联系电话 */
    private String phone;

    /** 固定电话 */
    private String linePhone;

    /** 邮件 */
    private String email;


    public String getMemberName() {
        return this.memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public Integer getIdCardType() {
        return this.idCardType;
    }

    public void setIdCardType(Integer idCardType) {
        this.idCardType = idCardType;
    }

    public String getIdCard() {
        return this.idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Integer getBaseNumber() {
        return this.baseNumber;
    }

    public void setBaseNumber(Integer baseNumber) {
        this.baseNumber = baseNumber;
    }

    public Date getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Integer getIdentity() {
        return this.identity;
    }

    public void setIdentity(Integer identity) {
        this.identity = identity;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLinePhone() {
        return this.linePhone;
    }

    public void setLinePhone(String linePhone) {
        this.linePhone = linePhone;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
