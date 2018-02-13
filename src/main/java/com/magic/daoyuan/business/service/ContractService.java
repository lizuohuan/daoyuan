package com.magic.daoyuan.business.service;

import com.magic.daoyuan.business.entity.Attachment;
import com.magic.daoyuan.business.entity.Contract;
import com.magic.daoyuan.business.entity.PageArgs;
import com.magic.daoyuan.business.entity.PageList;
import com.magic.daoyuan.business.mapper.IAttachmentMapper;
import com.magic.daoyuan.business.mapper.IContractMapper;
import com.magic.daoyuan.business.util.CommonUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 合同管理
 * Created by Eric Xie on 2017/9/14 0014.
 */
@Service
public class ContractService {

    @Resource
    private IContractMapper contractMapper;
    @Resource
    private IAttachmentMapper attachmentMapper;


    /**
     * 获取合同详情、包括附件集合
     * @param contractId
     * @return
     */
    public Contract queryContract(Integer contractId){
        return contractMapper.queryContractById(contractId);
    }

    /**
     * 动态获取合同列表
     * @param params
     * @param pageArgs
     * @return
     */
    public PageList<Contract> queryContractByItems(Map<String,Object> params, PageArgs pageArgs){
        int count = contractMapper.countContractByItems(params);
        List<Contract> dataList = new ArrayList<Contract>();
        if(count > 0){
            params.put("limit",pageArgs.getPageStart());
            params.put("limitSize",pageArgs.getPageSize());
            dataList = contractMapper.queryContractByItems(params);
        }
        return new PageList<Contract>(dataList,count);
    }

    /**
     * 新增合同
     * @param contract 合同
     * @param attachmentArr 附件集合
     */
    @Transactional
    public void addContract(Contract contract,String attachmentArr) throws Exception{
        contractMapper.addContract(contract);
        // 生成合同编号
        contract.setSerialNumber(CommonUtil.buildContractSerialNumber(contract.getId()));
        contractMapper.updateContract(contract);

        JSONArray attachmentJsonArr = JSONArray.fromObject(attachmentArr);
        List<Attachment> attachments = new ArrayList<Attachment>();
        for (Object o : attachmentJsonArr) {
            JSONObject json = JSONObject.fromObject(o);
            Attachment attachment = new Attachment();
            attachment.setAttachmentName(json.getString("attachmentName"));
            attachment.setUrl(json.getString("url"));
            attachment.setContractId(contract.getId());
            attachments.add(attachment);
        }
        if(attachments.size() > 0){
            attachmentMapper.batchAddAttachment(attachments);
        }
    }


    @Transactional
    public void updateContract(Contract contract,String attachmentArr) throws Exception{
        contractMapper.updateContract(contract);
        JSONArray attachmentJsonArr = JSONArray.fromObject(attachmentArr);
        List<Attachment> attachments = new ArrayList<Attachment>();
        for (Object o : attachmentJsonArr) {
            JSONObject json = JSONObject.fromObject(o);
            Attachment attachment = new Attachment();
            attachment.setAttachmentName(json.getString("attachmentName"));
            attachment.setUrl(json.getString("url"));
            attachment.setContractId(contract.getId());
            attachments.add(attachment);
        }
        attachmentMapper.delAttachment(contract.getId());
        if(attachments.size() > 0){
            attachmentMapper.batchAddAttachment(attachments);
        }

    }

    public void updateContract(Contract contract){
        contractMapper.updateContract(contract);
    }

}
