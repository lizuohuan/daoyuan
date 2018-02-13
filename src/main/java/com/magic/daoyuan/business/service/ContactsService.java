package com.magic.daoyuan.business.service;

import com.magic.daoyuan.business.entity.*;
import com.magic.daoyuan.business.exception.InterfaceCommonException;
import com.magic.daoyuan.business.mapper.IContactsMapper;
import com.magic.daoyuan.business.mapper.ILogMapper;
import com.magic.daoyuan.business.util.CommonUtil;
import com.magic.daoyuan.business.util.StatusConstant;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 联系人
 * Created by Eric Xie on 2017/9/12 0012.
 */
@Service
public class ContactsService {

    @Resource
    private IContactsMapper contactsMapper;
    @Resource
    private ILogMapper logMapper;


    /**
     * 通过汇总账单id查询 账单接受人
     * @param companySonTotalBillId
     * @return
     */
    public Contacts getContactsByCompanySonBillItemId(Integer companySonTotalBillId){
        return contactsMapper.queryContactsByCompanySonBillItemId(companySonTotalBillId);
    }


    /**
     * 获取联系人
     * @param companyId
     * @param isReceiver
     * @return
     */
    public List<Contacts> queryContactsByIsReceiver(Integer companyId,Integer isReceiver){
        return contactsMapper.queryContactsByIsReceiver(companyId,isReceiver);
    }

    public List<Contacts> queryContactsByCompany(Integer companyId){
        return contactsMapper.queryContactsByCompany(companyId);
    }

    public Contacts queryContactsById(Integer id){
        return contactsMapper.queryContactsById(id);
    }

    public PageList<Contacts> queryContactsByItems(Map<String,Object> params, PageArgs pageArgs){
        List<Contacts> dataList = new ArrayList<Contacts>();
        int count = contactsMapper.countContactsByItems(params);
        if(count > 0){
            params.put("limit",pageArgs.getPageStart());
            params.put("limitSize",pageArgs.getPageSize());
            dataList = contactsMapper.queryContactsByItems(params);
        }
        return new PageList<Contacts>(dataList,count);
    }

    @Transactional
    public void update(Contacts contacts,User user) throws Exception{
        Contacts info = contactsMapper.queryContactsById(contacts.getId());
        if(null == info){
            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST,"联系人不存在");
        }
        contactsMapper.updateContacts(contacts);
        if(!CommonUtil.isEmpty(contacts.getContactsName()) && !contacts.getContactsName().equals(info.getContactsName())){
            Log log = new Log(user.getId(), StatusConstant.LOG_MODEL_COMPANY,"修改 "+contacts.getContactsName()+"联系人",
                    StatusConstant.LOG_FLAG_UPDATE);
            logMapper.add(log);
        }
    }


    @Transactional
    public void add(Contacts contacts, User user){
        contactsMapper.addContacts(contacts);
        // 日志
        Log log = new Log(user.getId(), StatusConstant.LOG_MODEL_COMPANY,"新增 "+contacts.getContactsName()+"联系人",
                StatusConstant.LOG_FLAG_ADD);
        logMapper.add(log);
    }

}
