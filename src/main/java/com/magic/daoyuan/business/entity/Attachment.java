package com.magic.daoyuan.business.entity;

import java.io.Serializable;

/**
 * 合同附件
 * Created by Eric Xie on 2017/9/14 0014.
 */
public class Attachment extends BaseEntity implements Serializable {

    /** 合同ID */
    private Integer contractId;

    private String attachmentName;

    /** 合同附件 URL */
    private String url;

    public Integer getContractId() {
        return contractId;
    }

    public Attachment setContractId(Integer contractId) {
        this.contractId = contractId;
        return this;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public Attachment setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Attachment setUrl(String url) {
        this.url = url;
        return this;
    }
}
