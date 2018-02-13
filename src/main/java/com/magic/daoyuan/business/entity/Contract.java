package com.magic.daoyuan.business.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 合同
 * Created by Eric Xie on 2017/9/14 0014.
 */
public class Contract extends BaseEntity implements Serializable {

    /** 合同编号 */
    private String serialNumber;

    /** 合同起始日期 */
    private Date startTime;

    /** 合同截至日期 */
    private Date endTime;

    /** 备注 */
    private String remarks;

    /** 公司ID */
    private Integer companyId;

    /** 所属公司名称 */
    private String companyName;

    /** 合同附件集合 */
    private List<Attachment> attachments;

    /** 创建时间 */
    private Date createTime;

    /** 合同是否终止  单方面终止 */
    private Integer isTermination;

    public Integer getIsTermination() {
        return isTermination;
    }

    public Contract setIsTermination(Integer isTermination) {
        this.isTermination = isTermination;
        return this;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public Contract setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Contract setStartTime(Date startTime) {
        this.startTime = startTime;
        return this;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public Contract setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
        return this;
    }

    public Date getEndTime() {
        return endTime;
    }

    public Contract setEndTime(Date endTime) {
        this.endTime = endTime;
        return this;
    }

    public String getRemarks() {
        return remarks;
    }

    public Contract setRemarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public Contract setCompanyId(Integer companyId) {
        this.companyId = companyId;
        return this;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Contract setCompanyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Contract setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
}
