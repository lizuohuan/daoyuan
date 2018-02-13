package com.magic.daoyuan.business.service;

import com.magic.daoyuan.business.entity.*;
import com.magic.daoyuan.business.exception.InterfaceCommonException;
import com.magic.daoyuan.business.mapper.IMemberBusinessUpdateRecordItemMapper;
import com.magic.daoyuan.business.mapper.IMemberBusinessUpdateRecordMapper;
import com.magic.daoyuan.business.util.CommonUtil;
import com.magic.daoyuan.business.util.StatusConstant;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * 实做-员工业务增减变表
 * @author lzh
 * @create 2017/10/26 16:54
 */
@Service
public class MemberBusinessUpdateRecordService {

    @Resource
    private IMemberBusinessUpdateRecordMapper memberBusinessUpdateRecordMapper;
    @Resource
    private IMemberBusinessUpdateRecordItemMapper memberBusinessUpdateRecordItemMapper;


    public MemberBusinessUpdateRecord queryBaseInfo (Integer id){
        return memberBusinessUpdateRecordMapper.queryBaseInfo(id);
    }

    /**
     * 分页 通过各种条件 实做-员工业务增减变表
     *
     * @param pageArgs    分页属性
     * @param uUserName  客服名
     * @param mUserName 员工名
     * @param certificateNum 员工证件编号
     * @param companyId 公司id
     * @param payPlaceId 缴金地id
     * @param transactorId 办理方id
     * @param serviceType 服务类型 0：社保  1：公积金
     * @param status 0：申请 1：待反馈、2：成功、3：失败 4：部分成功
     * @return
     */
    public PageList<MemberBusinessUpdateRecord> list(PageArgs pageArgs , String uUserName,Integer userId  , String mUserName ,String certificateNum ,
                                                     Integer companyId , Integer payPlaceId, Integer status, Integer transactorId,Integer serviceType,
                                                     Integer organizationId) {
        PageList<MemberBusinessUpdateRecord> pageList = new PageList<MemberBusinessUpdateRecord>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("uUserName", uUserName);
        map.put("userId", userId);
        map.put("mUserName", mUserName);
        map.put("certificateNum", certificateNum);
        map.put("companyId", companyId);
        map.put("payPlaceId", payPlaceId);
        map.put("organizationId", organizationId);
        map.put("status", status);
        map.put("serviceType", serviceType);
        map.put("transactorId", transactorId);
        int count = memberBusinessUpdateRecordMapper.listCount(map);
        if (count > 0) {
            map.put("pageArgs", pageArgs);
            List<MemberBusinessUpdateRecord> list = memberBusinessUpdateRecordMapper.list(map);
            if(null != list && list.size() > 0){
                for (MemberBusinessUpdateRecord record : list) {
                    record.setPayPlaceName(CommonUtil.formatPayPlace(record.getPayPlaceName()));
                }
            }
            pageList.setList(list);
        }
        pageList.setTotalSize(count);
        return pageList;
    }

    /**
     * 更新
     * @param memberBusinessUpdateRecord
     */
    public void update(MemberBusinessUpdateRecord memberBusinessUpdateRecord) {
        memberBusinessUpdateRecordMapper.update(memberBusinessUpdateRecord);
    }

    /**
     * 批量更新
     * @param list
     */
    public void updateList(List<MemberBusinessUpdateRecord> list){
        memberBusinessUpdateRecordMapper.updateList(list);
    }


    @Transactional
    public void allApplyFor(String uUserName,Integer userId , String mUserName  ,String certificateNum ,
                            Integer companyId , Integer payPlaceId, Integer status, Integer transactorId,
                            Integer serviceType){
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("uUserName", uUserName);
        map.put("userId", userId);
        map.put("mUserName", mUserName);
        map.put("certificateNum", certificateNum);
        map.put("companyId", companyId);
        map.put("payPlaceId", payPlaceId);
        map.put("serviceType", serviceType);
        map.put("transactorId", transactorId);
        map.put("status", status);
        List<MemberBusinessUpdateRecord> list = memberBusinessUpdateRecordMapper.list(map);
        if (list.size() > 0){
//            Set<Integer> recordIdSet = new HashSet<Integer>();
            List<MemberBusinessUpdateRecordItem> itemList = new ArrayList<>();

            list.forEach((MemberBusinessUpdateRecord record) ->{
                record.setStatus(1);
                MemberBusinessUpdateRecordItem item = new MemberBusinessUpdateRecordItem();
                item.setId(record.getMburiId());
                item.setStatus(StatusConstant.ITEM_FEEDBACKING);
                itemList.add(item);
            });

            if (itemList.size() > 0) {
                memberBusinessUpdateRecordItemMapper.updateList(itemList);
            }
            if (list.size() > 0) {
                memberBusinessUpdateRecordMapper.updateList(list);
            }


//            if (CommonUtil.isEmpty2(uUserName,userId,mUserName,certificateNum,companyId,payPlaceId,serviceType,transactorId)) {
//                // 通过记录ID集合 查询待申请的子类集合
//                List<MemberBusinessUpdateRecordItem> itemList =
//                        memberBusinessUpdateRecordItemMapper.queryRecordItemByRecords(recordIdSet.toArray(new Integer[recordIdSet.size()]));
//                if(null == itemList || itemList.size() == 0){
//                    throw new InterfaceCommonException(StatusConstant.Fail_CODE,"没有记录可以申请");
//                }
//                for (MemberBusinessUpdateRecordItem item : itemList) {
//                    item.setStatus(StatusConstant.ITEM_FEEDBACKING);
//                }
//                memberBusinessUpdateRecordItemMapper.updateList(itemList);
//                memberBusinessUpdateRecordMapper.updateList(list);
//            } else {
//                memberBusinessUpdateRecordMapper.allApplyFor();
//                memberBusinessUpdateRecordItemMapper.allApplyFor();
//            }

        } else {
            throw new InterfaceCommonException(StatusConstant.NO_DATA,"暂无可申请");
        }

    }
}
