package com.magic.daoyuan.business.vo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.magic.daoyuan.business.util.CommonUtil;

import java.util.Date;

/**
 *  通用模版实做办理及反馈
 * Created by Eric Xie on 2017/11/28 0028.
 */
public class CommonTransact {

    /** 子类ID */
    private Integer mburiId;

    /** 员工姓名 */
    private String memberName;

    /** 员工ID */
    private Integer memberId;

    /** 证件类型 */
    private String idCardType;

    /** 证件号 */
    private String idCard;

    /** 手机号 */
    private String phone;

    /** 学历 */
    private String educationName;

    /** 服务类型 公积金/社保 */
    private String serviceType;

    /** 服务名称 增员/减员/变更 */
    private String serviceName;

    /** 变更内容 姓名/证件类型/证件编号/手机号/学历/ 档次| /基数  多个用逗号隔开 */
    private String contentOfChange;

    /** 社保/公积金 编号 */
    private String serialNumber;

    /** 缴金地名字 */
    private String payPlaceName;

    /** 经办机构名字 */
    private String orgnaizationName;

    /** 办理方名字 */
    private String transactName;

    /** 档次/比例 */
    private String levelName;

    /** 基数 */
    private String baseNumber;

    /** 服务起始年月 */
    private Date serviceStartMonth;

    /** 备注 */
    private String remark;


    public String getMemberName() {
        return this.memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getIdCardType() {
        return this.idCardType;
    }

    public void setIdCardType(String idCardType) {
        this.idCardType = idCardType;
    }

    public String getIdCard() {
        return this.idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEducationName() {
        return this.educationName;
    }

    public void setEducationName(String educationName) {
        this.educationName = educationName;
    }

    public String getServiceType() {
        return this.serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceName() {
        return this.serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getContentOfChange() {
        return this.contentOfChange;
    }

    public void setContentOfChange(String contentOfChange) {
        this.contentOfChange = contentOfChange;
    }

    public String getSerialNumber() {
        return this.serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getPayPlaceName() {
        return this.payPlaceName;
    }

    public void setPayPlaceName(String payPlaceName) {
        this.payPlaceName = payPlaceName;
    }

    public String getOrgnaizationName() {
        return this.orgnaizationName;
    }

    public void setOrgnaizationName(String orgnaizationName) {
        this.orgnaizationName = orgnaizationName;
    }

    public String getTransactName() {
        return this.transactName;
    }

    public void setTransactName(String transactName) {
        this.transactName = transactName;
    }

    public String getLevelName() {
        return this.levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getBaseNumber() {
        if(!CommonUtil.isEmpty(this.baseNumber)){
            String[] split = this.baseNumber.split("\\.");
            if(split.length > 1 && Integer.valueOf(split[1]) == 0){
                this.baseNumber = split[0];
            }
        }
        return this.baseNumber;
    }

    public void setBaseNumber(String baseNumber) {
        this.baseNumber = baseNumber;
    }

    public Date getServiceStartMonth() {
        return this.serviceStartMonth;
    }

    public void setServiceStartMonth(Date serviceStartMonth) {
        this.serviceStartMonth = serviceStartMonth;
    }

    public String getRemark() {
        if (!CommonUtil.isEmpty(remark)) {
            JSONArray jsonArray = JSONArray.parseArray(remark);
            String r = "";
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonobject = jsonArray.getJSONObject(i);
                if (i == 0) {
                    r += jsonobject.getString("userName") + ":" + jsonobject.getString("remark");
                } else {
                    r += "|" + jsonobject.getString("userName") + ":" + jsonobject.getString("remark");
                }

            }
            return r;
        }
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    /** 获取 子类ID */
    public Integer getMburiId() {
        return this.mburiId;
    }

    /** 设置 子类ID */
    public void setMburiId(Integer mburiId) {
        this.mburiId = mburiId;
    }

    /** 获取 员工ID */
    public Integer getMemberId() {
        return this.memberId;
    }

    /** 设置 员工ID */
    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }
}
