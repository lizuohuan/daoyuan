package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.Attachment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Eric Xie on 2017/9/14 0014.
 */
public interface IAttachmentMapper {

    /**
     * 增加合同附件
     * @param attachment
     * @return
     */
    Integer addAttachment(Attachment attachment);

    /**
     * 批量新增合同附件
     * @param attachments
     * @return
     */
    Integer batchAddAttachment(@Param("attachments") List<Attachment> attachments);

    /**
     * 更新合同附件
     * @param attachment
     * @return
     */
    Integer updateAttachment(Attachment attachment);

    /**
     * 批量更新合同附件
     * @param attachments
     * @return
     */
    Integer batchUpdateAttachment(@Param("attachments") List<Attachment> attachments);

    /**
     * 查询 合同的附件
     * @param contractId 合同
     * @return 附件集合
     */
    List<Attachment> queryAttachmentByContract(@Param("contractId") Integer contractId);

    /**
     * 删除合同附件
     * @param contractId
     * @return
     */
    Integer delAttachment(@Param("contractId") Integer contractId);

}
