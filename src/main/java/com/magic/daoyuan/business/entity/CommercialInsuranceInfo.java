package com.magic.daoyuan.business.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 账单-专项服务明细
 * @author lzh
 * @create 2017/10/19 18:50
 */
public class CommercialInsuranceInfo implements Serializable {


    private Integer id;

    /** 姓名 */
    private String userName;

    /** 证件类型 */
    private Integer certificateType;

    /** 证件编号 */
    private String idCard;

    /** 开始缴纳年月 */
    private Date beginPayYM;

    /** 服务时长 1个月 / 多个月 */
    private Integer serviceTimeLong;

    /** 员工id */
    private Integer memberId;

    /********  业务字段 不进行数据库持久化    *******/

    /** 账单-商业险明细子类-险种 */
    private List<CommercialInsuranceInfoItem> commercialInsuranceInfoItems;
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /** 获取 姓名 */
    public String getUserName() {
        return this.userName;
    }

    /** 设置 姓名 */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /** 获取 证件类型 */
    public Integer getCertificateType() {
        return this.certificateType;
    }

    /** 设置 证件类型 */
    public void setCertificateType(Integer certificateType) {
        this.certificateType = certificateType;
    }

    /** 获取 证件编号 */
    public String getIdCard() {
        return this.idCard;
    }

    /** 设置 证件编号 */
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    /** 获取 开始缴纳年月 */
    public Date getBeginPayYM() {
        return this.beginPayYM;
    }

    /** 设置 开始缴纳年月 */
    public void setBeginPayYM(Date beginPayYM) {
        this.beginPayYM = beginPayYM;
    }

    /** 获取 服务时长 1个月 / 多个月 */
    public Integer getServiceTimeLong() {
        return this.serviceTimeLong;
    }

    /** 设置 服务时长 1个月 / 多个月 */
    public void setServiceTimeLong(Integer serviceTimeLong) {
        this.serviceTimeLong = serviceTimeLong;
    }

    /** 获取 员工id */
    public Integer getMemberId() {
        return this.memberId;
    }

    /** 设置 员工id */
    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    /** 获取 账单-商业险明细子类-险种 */
    public List<CommercialInsuranceInfoItem> getCommercialInsuranceInfoItems() {
        return this.commercialInsuranceInfoItems;
    }

    /** 设置 账单-商业险明细子类-险种 */
    public void setCommercialInsuranceInfoItems(List<CommercialInsuranceInfoItem> commercialInsuranceInfoItems) {
        this.commercialInsuranceInfoItems = commercialInsuranceInfoItems;
    }
}
