package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.Contacts;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Eric Xie on 2017/9/12 0012.
 */
public interface IContactsMapper {

    /**
     * 通过汇总账单 查询 账单接受人
     * @param companySonTotalBillId
     * @return
     */
    Contacts queryContactsByCompanySonBillItemId(@Param("companySonTotalBillId") Integer companySonTotalBillId);

    Contacts queryContactsById(@Param("id") Integer id);

    Integer addContacts(Contacts contacts);


    Integer updateContacts(Contacts contacts);


    List<Contacts> queryContactsByItems(Map<String,Object> params);


    Integer countContactsByItems(Map<String,Object> params);


    List<Contacts> queryContactsByCompany(@Param("companyId") Integer companyId);


    List<Contacts> queryContactsByIsReceiver(@Param("companyId") Integer companyId,
                                             @Param("isReceiver") Integer isReceiver);

    void addList(List<Contacts> contactsList);

    void updateList(List<Contacts> contactsList);
}
