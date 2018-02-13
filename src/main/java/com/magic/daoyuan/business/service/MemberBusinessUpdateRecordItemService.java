package com.magic.daoyuan.business.service;

import com.magic.daoyuan.business.entity.MemberBusinessUpdateRecord;
import com.magic.daoyuan.business.entity.MemberBusinessUpdateRecordItem;
import com.magic.daoyuan.business.entity.PageArgs;
import com.magic.daoyuan.business.entity.PageList;
import com.magic.daoyuan.business.mapper.IMemberBusinessUpdateRecordItemMapper;
import com.magic.daoyuan.business.mapper.IMemberBusinessUpdateRecordMapper;
import com.magic.daoyuan.business.util.CommonUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 实做-员工业务增减变记录表 - 子类  记录具体的增减变操作记录
 * @author lzh
 * @create 2017/10/26 17:26
 */
@Service
public class MemberBusinessUpdateRecordItemService {

    @Resource
    private IMemberBusinessUpdateRecordItemMapper memberBusinessUpdateRecordItemMapper;
    @Resource
    private IMemberBusinessUpdateRecordMapper memberBusinessUpdateRecordMapper;


    public List<MemberBusinessUpdateRecordItem> queryRecordItemByRecords(Integer[] ids){
        return memberBusinessUpdateRecordItemMapper.queryRecordItemByRecords(ids);
    }

    /**
     * 分页 通过各种条件 实做-员工业务增减变记录表
     *
     * @param pageArgs    分页属性
     * @param serviceStatus 0增员、1减员、2变更
     * @param organizationId 经办机构id
     * @param payPlaceId 缴金地id
     * @param transactorId 办理方id
     * @param status 0：申请 1：待反馈、2：成功、3：失败 4：部分成功
     * @return
     */
    public PageList<MemberBusinessUpdateRecordItem> list(PageArgs pageArgs , Integer serviceStatus,
                                                         Integer organizationId , Integer payPlaceId,
                                                         Integer status, Integer transactorId,
                                                         Integer memberBusinessUpdateRecordId) {
        PageList<MemberBusinessUpdateRecordItem> pageList = new PageList<MemberBusinessUpdateRecordItem>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("serviceStatus", serviceStatus);
        map.put("organizationId", organizationId);
        map.put("payPlaceId", payPlaceId);
        map.put("memberBusinessUpdateRecordId", memberBusinessUpdateRecordId);
        map.put("status", status);
        map.put("transactorId", transactorId);
        int count = memberBusinessUpdateRecordItemMapper.listCount(map);
        if (count > 0) {
            map.put("pageArgs", pageArgs);
            List<MemberBusinessUpdateRecordItem> list = memberBusinessUpdateRecordItemMapper.list(map);
            for (MemberBusinessUpdateRecordItem item : list) {
                item.setPayPlaceName(CommonUtil.formatPayPlace(item.getPayPlaceName()));
            }
            pageList.setList(list);
        }
        pageList.setTotalSize(count);
        return pageList;
    }

    /**
     * 更新
     * @param recordItem
     */
    public void update(MemberBusinessUpdateRecordItem recordItem) {
        memberBusinessUpdateRecordItemMapper.update(recordItem);
    }
/**
     * 更新
     * @param recordItem
     */
    public void updateStatus(List<MemberBusinessUpdateRecordItem> recordItem) {
        memberBusinessUpdateRecordItemMapper.updateList(recordItem);
    }

    /**
     * 详情
     * @param id
     * @return
     */
    public MemberBusinessUpdateRecordItem info(Integer id) {
        return memberBusinessUpdateRecordItemMapper.info(id);
    }


    /**
     * 批量更新
     * @param recordItems
     */
    public void update(List<MemberBusinessUpdateRecordItem> recordItems) {

        Integer[] recordIds = new Integer[recordItems.size()];
        for (int i = 0; i < recordItems.size(); i++) {
            recordIds[i] = recordItems.get(i).getId();
        }
        //需要更新的
        List<MemberBusinessUpdateRecordItem> updateRecordItems = new ArrayList<MemberBusinessUpdateRecordItem>();
        updateRecordItems.addAll(recordItems);


        //根据ID或者增减变 及记录
        List<MemberBusinessUpdateRecord> records =
                memberBusinessUpdateRecordMapper.getRecordAndItemByIds(recordIds);

        for (MemberBusinessUpdateRecord record : records) {
            for (int i = 0; i < recordItems.size(); i++) {
                if (record.getId().equals(recordItems.get(i).getMemberBusinessUpdateRecordId())) {
                    //当次增加变次数>1时
                    if (record.getMemberBusinessUpdateRecordItems().size() > 1) {
                        //记录处理次数
                        Integer disposeNum = 0;
                        //处理成功次数
                        Integer successNum = 0;
                        for (MemberBusinessUpdateRecordItem item : record.getMemberBusinessUpdateRecordItems()) {
                            if (!item.getId().equals(recordItems.get(i).getId())) {
                                if (item.getStatus() == 1 || item.getStatus() == 2) {
                                    item.setIsNowCreate(1);
                                    disposeNum ++;
                                    if (item.getStatus() == 1) {
                                        successNum ++;
                                    }
                                }
                                updateRecordItems.add(item);
                                break;
                            }
                        }
                        if (recordItems.get(i).getStatus() == 1) {
                            successNum ++;
                        }
                        if (disposeNum == 0) {
                            recordItems.get(i).setIsNowCreate(0);
                        }
                        //record.status 2：成功、3：失败 4:部分成功
                        if (successNum == 2) {
                            record.setStatus(2);
                        } else if (successNum == 1){
                            record.setStatus(4);
                        } else {
                            record.setStatus(3);
                        }
                    } else {
                        //当次增加变次数=1时
                        if (recordItems.get(i).getStatus() == 1) {
                            record.setStatus(2);
                        } else {
                            record.setStatus(3);
                        }
                    }
                    //移除
                    recordItems.remove(i);
                    i--;
                    break;
                }
            }

        }
        if (updateRecordItems.size() > 0) {
            memberBusinessUpdateRecordItemMapper.updateList(updateRecordItems);
        }
        if ( records.size() > 0) {
            memberBusinessUpdateRecordMapper.updateList(records);
        }
    }

    /**
     * 根据状态获取实作员工
     * @param status  0：待申请 1：待反馈  2：成功  3: 失败 4: 失效
     * @param date  提醒日期和实作日期 1-31天
     * @return
     */
    public List<MemberBusinessUpdateRecordItem> getUpdateRecordStatus( Integer status, Integer date) {
        return memberBusinessUpdateRecordItemMapper.getUpdateRecordStatus(status,date);
    }

}
