package com.magic.daoyuan.business.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 快递信息
 * Created by Eric Xie on 2017/9/21 0021.
 */
public class ExpressInfo extends BaseEntity implements Serializable {

    /** 订单号 */
    private String orderNumber;

    /** 快递公司ID */
    private Integer expressCompanyId;

    /** 快递公司名称 */
    private String expressCompanyName;

    /** 快递包含内容 */
    private String content;

    /** 客户是否收到  0 ：否  1：是*/
    private Integer isReceive;

    /** 公司ID */
    private Integer companyId;

    /** 公司名称 */
    private String companyName;

    private Integer expressPersonId;

    private String expressPersonName;

    /** 收货时间 */
    private Date receiveDate;

    /** 快递公司的连接 */
    private String expressUrl;


    /** 获取 订单号 */
    public String getOrderNumber() {
        return this.orderNumber;
    }

    /** 设置 订单号 */
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    /** 获取 快递公司ID */
    public Integer getExpressCompanyId() {
        return this.expressCompanyId;
    }

    /** 设置 快递公司ID */
    public void setExpressCompanyId(Integer expressCompanyId) {
        this.expressCompanyId = expressCompanyId;
    }



    /** 获取 快递包含内容 */
    public String getContent() {
        return this.content;
    }

    /** 设置 快递包含内容 */
    public void setContent(String content) {
        this.content = content;
    }

    /** 获取 客户是否收到  0 ：否  1：是*/
    public Integer getIsReceive() {
        return this.isReceive;
    }

    /** 设置 客户是否收到  0 ：否  1：是*/
    public void setIsReceive(Integer isReceive) {
        this.isReceive = isReceive;
    }

    /** 获取 公司ID */
    public Integer getCompanyId() {
        return this.companyId;
    }

    /** 设置 公司ID */
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    /** 获取 公司名称 */
    public String getCompanyName() {
        return this.companyName;
    }

    /** 设置 公司名称 */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /** 获取 快递公司名称 */
    public String getExpressCompanyName() {
        return this.expressCompanyName;
    }

    /** 设置 快递公司名称 */
    public void setExpressCompanyName(String expressCompanyName) {
        this.expressCompanyName = expressCompanyName;
    }

    public Integer getExpressPersonId() {
        return this.expressPersonId;
    }

    public void setExpressPersonId(Integer expressPersonId) {
        this.expressPersonId = expressPersonId;
    }

    public String getExpressPersonName() {
        return this.expressPersonName;
    }

    public void setExpressPersonName(String expressPersonName) {
        this.expressPersonName = expressPersonName;
    }

    /** 获取 收货时间 */
    public Date getReceiveDate() {
        return this.receiveDate;
    }

    /** 设置 收货时间 */
    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    /** 获取 快递公司的连接 */
    public String getExpressUrl() {
        return this.expressUrl;
    }

    /** 设置 快递公司的连接 */
    public void setExpressUrl(String expressUrl) {
        this.expressUrl = expressUrl;
    }
}
