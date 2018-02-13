package com.magic.daoyuan.business.util;

import com.magic.daoyuan.business.entity.*;
import com.magic.daoyuan.business.enums.BusinessEnum;
import com.magic.daoyuan.business.enums.InsuranceType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Eric Xie on 2017/11/29 0029.
 */
public class ServiceUtil {


    // 增减变 记录 0增员、1减员、2变更
    public static final int ADD = 0;
    public static final int SUBTRACT = 1;
    public static final int CHANGE = 2;


    /**
     * 从历史数据 - 构建出老员工的业务数据
     * @param old
     * @return
     */
    public static Member buildMemberForRecord(Member old) {
        Member o = new Member();
        o.setId(old.getId());
        if(null != old.getUpdateRecordList() && old.getUpdateRecordList().size() > 0){
            List<Business> businessList = new ArrayList<Business>();
            for (MemberBusinessUpdateRecord record : old.getUpdateRecordList()) {
                Business business = new Business();
                List<MemberBusinessUpdateRecordItem> success = new ArrayList<MemberBusinessUpdateRecordItem>();
                MemberBusinessUpdateRecordItem original = null; // 最原始的数据
                if(InsuranceType.SheBao.ordinal() == record.getServiceType()){
                    // 构建社保业务
                    original = record.getMemberBusinessUpdateRecordItems().get(record.getMemberBusinessUpdateRecordItems().size() - 1);
                    for (MemberBusinessUpdateRecordItem item : record.getMemberBusinessUpdateRecordItems()) {
                        if(StatusConstant.ITEM_SUCCESS.equals(item.getStatus())){
                            success.add(item);
                        }
                    }
                    MemberBusinessItem memberBusinessItem = null;
                    if(success.size() > 0){
                        // 如果有成功反馈的数据，则用反馈的数据构建原始员工对象,第一条为最新一条，查询时已经排序过。
                        MemberBusinessUpdateRecordItem item = success.get(0);
                        memberBusinessItem = getMemberBusinessItem(item);
                        if(ServiceUtil.SUBTRACT != item.getServiceStatus()){
                            business.setId(BusinessEnum.sheBao.ordinal());
                        }
                        business.setMemberBusinessItem(memberBusinessItem);
                        // 设置基础字段 0:姓名 1:证件类型 2:证件号 3:手机号 4:学历 5:档次 6:基数
                        setBaseData(o, item);
                    }
                    else {
                        // 如果没有成功数据，则获取第一条初始数据
                        memberBusinessItem = getMemberBusinessItem(original);
                        if(ServiceUtil.SUBTRACT != record.getMemberBusinessUpdateRecordItems().get(0).getServiceStatus()){
                            business.setId(BusinessEnum.sheBao.ordinal());
                        }
                        business.setMemberBusinessItem(memberBusinessItem);
                        // 设置基础字段 0:姓名 1:证件类型 2:证件号 3:手机号 4:学历 5:档次 6:基数
                        setBaseData(o, original);
                    }

                }
                else{
                    // 构建公积金业务
                    original = record.getMemberBusinessUpdateRecordItems().get(record.getMemberBusinessUpdateRecordItems().size() - 1);
                    for (MemberBusinessUpdateRecordItem item : record.getMemberBusinessUpdateRecordItems()) {
                        if(StatusConstant.ITEM_SUCCESS.equals(item.getStatus())){
                            success.add(item);
                        }
                    }
                    MemberBusinessItem memberBusinessItem = null;
                    if(success.size() > 0){
                        // 如果有成功反馈的数据，则用反馈的数据构建原始员工对象,第一条为最新一条，查询时已经排序过。
                        MemberBusinessUpdateRecordItem item = success.get(0);
                        memberBusinessItem = getMemberBusinessItem(item);
                        if(ServiceUtil.SUBTRACT != item.getServiceStatus()){
                            business.setId(BusinessEnum.gongJiJin.ordinal());
                        }
                        business.setMemberBusinessItem(memberBusinessItem);
                        // 设置基础字段 0:姓名 1:证件类型 2:证件号 3:手机号 4:学历 5:档次 6:基数
                        setBaseData(o, item);
                    }
                    else {
                        // 如果没有成功数据，则获取第一条初始数据
                        memberBusinessItem = getMemberBusinessItem(original);
                        if(ServiceUtil.SUBTRACT != record.getMemberBusinessUpdateRecordItems().get(0).getServiceStatus()){
                            business.setId(BusinessEnum.gongJiJin.ordinal());
                        }
                        business.setMemberBusinessItem(memberBusinessItem);
                        // 设置基础字段 0:姓名 1:证件类型 2:证件号 3:手机号 4:学历 5:档次 6:基数
                        setBaseData(o, original);
                    }
                }
                if (null != business.getId()) {
                    businessList.add(business);
                }
            }
            o.setBusinessList(businessList);
        }
        o.setUpdateRecordList(old.getUpdateRecordList());
        if(null != old.getCompanyId()){
            o.setCompanyId(old.getCompanyId());
        }
        return o;
    }

    private static void setBaseData(Member o, MemberBusinessUpdateRecordItem item) {
        for (MemberBaseChange change : item.getBaseChangeList()) {
            switch (change.getType()){
                case 0 :
                    o.setUserName(change.getContent());
                    break;
                case 1 :
                    o.setCertificateType(change.getTargetId());
                    break;
                case 2 :
                    o.setCertificateNum(change.getContent());
                    break;
                case 3 :
                    o.setPhone(change.getContent());
                    break;
                case 4 :
                    o.setEducation(change.getTargetId());
                    break;
            }
        }
    }

    private static MemberBusinessItem getMemberBusinessItem(MemberBusinessUpdateRecordItem item) {
        MemberBusinessItem memberBusinessItem = new MemberBusinessItem();
        memberBusinessItem.setType(InsuranceType.SheBao.ordinal());
        memberBusinessItem.setServeMethod(item.getServeMethod());
        memberBusinessItem.setPayPlaceId(item.getPayPlaceId());
        memberBusinessItem.setOrganizationId(item.getOrganizationId());
        memberBusinessItem.setTransactorId(item.getTransactorId());
        memberBusinessItem.setInsuranceLevelId(item.getInsuranceLevelId());
        memberBusinessItem.setRatio(item.getRatio());
        memberBusinessItem.setBaseType(item.getBaseType());
        memberBusinessItem.setBaseNumber(item.getBaseNumber());
        return memberBusinessItem;
    }


    /**
     * 处理增减变数据
     *
     * @param oldMember 之前员工对象，包括其他业务
     * @param newMember 现有员工对象
     */
    public static Map<String,Object> handleUpdateItem(Member oldMember, Member newMember) {
        List<MemberBusinessUpdateRecordItem> itemList = new ArrayList<MemberBusinessUpdateRecordItem>();
        List<MemberBusinessUpdateRecordItem> itemListUpdate = new ArrayList<MemberBusinessUpdateRecordItem>();
        List<MemberBusinessUpdateRecord> recordList = new ArrayList<MemberBusinessUpdateRecord>();
        if (null != oldMember) {
            // 判断基础字段的变更
            Map<String, Object> objectMap = getBaseChange(oldMember, newMember);
            String baseChange = (String) objectMap.get("msg");
            List<MemberBaseChange> baseChangeList = (List<MemberBaseChange>) objectMap.get("changeList");
            if(null != oldMember.getBusinessList() && oldMember.getBusinessList().size() > 0){
                // 之前有业务
                // 判断现在的员工的业务
                if(null == newMember.getBusinessList() || newMember.getBusinessList().size() == 0){
                    // 减员业务
                    for (Business business : oldMember.getBusinessList()) {
                        if(business.getId().equals(BusinessEnum.sheBao.ordinal())){
                            for (MemberBusinessUpdateRecord record : oldMember.getUpdateRecordList()) {
                                if(0 == record.getServiceType()){
                                    // 社保
                                    // 如果是社保减员，判断在未申请的记录里是否有 其他减员记录或者增员社保的记录，如果有则将其他记录更新为 无效状态
                                    // 遍历老数据的记录
                                    // 成功记录，当前成功反馈的最新一条数据
                                    Integer status = StatusConstant.ITEM_APPLICATION; // 新数据的状态
                                    MemberBusinessUpdateRecordItem success = null;
                                    int index = 0;
                                    if(null != record.getMemberBusinessUpdateRecordItems() && record.getMemberBusinessUpdateRecordItems().size() > 0){
                                        for (MemberBusinessUpdateRecordItem item : record.getMemberBusinessUpdateRecordItems()) {
                                            if(StatusConstant.ITEM_APPLICATION.equals(item.getStatus())){
                                                // 如果老数据中有待申请的资料，则设置为 无效
                                                item.setStatus(StatusConstant.ITEM_CANCEL);
                                                itemListUpdate.add(item);
                                            }
                                            if(index == 0 && StatusConstant.ITEM_SUCCESS.equals(item.getStatus())){
                                                if( StatusConstant.UPDATE_SOCIAL_SECURITY.equals(item.getUpdateContentFlag())
                                                        && SUBTRACT == item.getServiceStatus()){
                                                    // 如果最新一条的成功反馈数据为  社保减员，当前操作为减员数据 则当条记录也为 无效记录
                                                    success = item;
                                                    status = StatusConstant.ITEM_CANCEL;
                                                }
                                                index++;
                                            }

                                        }
                                    }
                                    if(!isFinishData(oldMember,InsuranceType.SheBao.ordinal())){
                                        status = StatusConstant.ITEM_CANCEL;
                                    }
                                    MemberBusinessUpdateRecordItem item = getRecordItem(record.getId(), StatusConstant.UPDATE_SOCIAL_SECURITY, StatusConstant.MSG_SHEBAO,SUBTRACT, business.getMemberBusinessItem(),status,newMember);
                                    itemList.add(item);
                                    break;
                                }
                            }

                        }
                        else if(business.getId().equals(BusinessEnum.gongJiJin.ordinal())){
                            for (MemberBusinessUpdateRecord record : oldMember.getUpdateRecordList()) {
                                if(1 == record.getServiceType()){

                                    // 如果是公积金减员，判断在未申请的记录里是否有 其他记录，如果有则将其他记录更新为 无效状态
                                    // 遍历老数据的记录
                                    Integer status = StatusConstant.ITEM_APPLICATION; // 新数据的状态
                                    MemberBusinessUpdateRecordItem success = null; // 成功记录，当前成功反馈的最新一条数据
                                    if(null != record.getMemberBusinessUpdateRecordItems() && record.getMemberBusinessUpdateRecordItems().size() > 0){
                                        for (MemberBusinessUpdateRecordItem item : record.getMemberBusinessUpdateRecordItems()) {
                                            if(StatusConstant.ITEM_APPLICATION.equals(item.getStatus())){
                                                // 如果老数据中有待申请的资料，则设置为 无效
                                                item.setStatus(StatusConstant.ITEM_CANCEL);
                                                itemListUpdate.add(item);
                                            }
                                            if(StatusConstant.ITEM_SUCCESS.equals(item.getStatus()) && null == success
                                                    && StatusConstant.UPDATE_RESERVED_FUNDS.equals(item.getUpdateContentFlag())
                                                    && SUBTRACT == item.getServiceStatus()){
                                                // 如果最新一条的成功反馈数据为  社保减员，当前操作为减员数据 则当条记录也为 无效记录
                                                success = item;
                                                status = StatusConstant.ITEM_CANCEL;
                                            }
                                        }
                                    }
                                    if(!isFinishData(oldMember,InsuranceType.GongJiJin.ordinal())){
                                        status = StatusConstant.ITEM_CANCEL;
                                    }
                                    // 公积金
                                    MemberBusinessUpdateRecordItem item = getRecordItem(record.getId(), StatusConstant.UPDATE_RESERVED_FUNDS, StatusConstant.MSG_GONGJIJIN,SUBTRACT, business.getMemberBusinessItem(),status,newMember);
                                    itemList.add(item);
                                    break;
                                }
                            }
                        }
                    }
                }
                else{
                    // 减员、增员、变更业务处理
                    for (Business old : oldMember.getBusinessList()) {
                        boolean isExist = false;
                        boolean isExistOfReservedFunds = false;
                        for (Business nB : newMember.getBusinessList()) {
                            if(old.getId().equals(nB.getId()) && BusinessEnum.sheBao.ordinal() == nB.getId()){
                                // 如果存在社保，则判断其他数据变更情况
                                Integer recordId =  null;
                                for (MemberBusinessUpdateRecord record : oldMember.getUpdateRecordList()) {
                                    if(0 == record.getServiceType()){
                                        // 社保
                                        recordId = record.getId();
                                        break;
                                    }
                                }
                                if(null == recordId){
                                    continue;
                                }
                                if(old.getMemberBusinessItem().getServeMethod().equals(nB.getMemberBusinessItem().getServeMethod())
                                        && old.getMemberBusinessItem().getServeMethod() == 0){
                                    // 如果都是代理
                                    if(old.getMemberBusinessItem().getPayPlaceId().equals(nB.getMemberBusinessItem().getPayPlaceId())){
                                        // 如果缴金地没有变更,判断经办机构
                                        if(old.getMemberBusinessItem().getOrganizationId().equals(nB.getMemberBusinessItem().getOrganizationId())){
                                            // 如果经办机构没有发生变更，判断办理方
                                            if(old.getMemberBusinessItem().getTransactorId().equals(nB.getMemberBusinessItem().getTransactorId())){
                                                // 办理方没有发生变更，判断档次
                                                String content = "";
                                                List<MemberBaseChange> changeList = new ArrayList<MemberBaseChange>();

                                                if(!old.getMemberBusinessItem().getInsuranceLevelId().equals(nB.getMemberBusinessItem().getInsuranceLevelId())){
                                                    // 档次发生变更
                                                    content += "档次、";
                                                    MemberBaseChange base = new MemberBaseChange();
                                                    base.setTargetId(nB.getMemberBusinessItem().getInsuranceLevelId());
                                                    base.setType(StatusConstant.BASE_LEVEL);
                                                    changeList.add(base);
                                                }
                                                if(old.getMemberBusinessItem().getBaseType() == 2 && old.getMemberBusinessItem().getBaseType().equals(nB.getMemberBusinessItem().getBaseType())
                                                        && !old.getMemberBusinessItem().getBaseNumber().equals(nB.getMemberBusinessItem().getBaseNumber())){
                                                    // 基数发生变更
                                                    content += "基数、";
                                                    MemberBaseChange base = new MemberBaseChange();
                                                    base.setContent(nB.getMemberBusinessItem().getBaseNumber().toString());
                                                    base.setTargetId(nB.getMemberBusinessItem().getBaseType());
                                                    base.setType(StatusConstant.BASE_BASE_BUMBER);
                                                    changeList.add(base);
                                                }
                                                if(!old.getMemberBusinessItem().getBaseType().equals(nB.getMemberBusinessItem().getBaseType())){
                                                    // 基数反生变更
                                                    content += "基数、";
                                                    MemberBaseChange base = new MemberBaseChange();
                                                    base.setTargetId(nB.getMemberBusinessItem().getBaseType());
                                                    base.setType(StatusConstant.BASE_BASE_BUMBER);
                                                    changeList.add(base);
                                                }
                                                if(!CommonUtil.isEmpty(baseChange)){
                                                    // 基础字段有发生变更
                                                    content += baseChange + "、";
                                                }
                                                boolean finishData = isFinishData(oldMember, InsuranceType.SheBao.ordinal());
                                                if(!CommonUtil.isEmpty(content)){
                                                    int serviceStatus = CHANGE;
                                                    if(!finishData){
                                                        // 如果不存在 成功的数据
                                                        serviceStatus = ADD;
                                                    }
                                                    MemberBusinessUpdateRecordItem temp = getRecordItem(recordId, StatusConstant.UPDATE_RATIO_BASENUMBER, content.substring(0,content.length() - 1),serviceStatus, nB.getMemberBusinessItem(),StatusConstant.ITEM_APPLICATION,null);
                                                    changeList.addAll(baseChangeList);
                                                    temp.setBaseChangeList(changeList);
                                                    itemList.add(temp);
                                                    if(null != oldMember.getUpdateRecordList() && oldMember.getUpdateRecordList().size() > 0){
                                                        for (MemberBusinessUpdateRecord record : oldMember.getUpdateRecordList()) {
                                                            if(InsuranceType.SheBao.ordinal() == record.getServiceType() && null != record.getMemberBusinessUpdateRecordItems() && record.getMemberBusinessUpdateRecordItems().size() > 0){
                                                                // 获取社保的变更数据
                                                                for (MemberBusinessUpdateRecordItem item : record.getMemberBusinessUpdateRecordItems()) {
//                                                                    if((CHANGE == item.getServiceStatus() && StatusConstant.ITEM_APPLICATION.equals(item.getStatus()))
                                                                    if((StatusConstant.ITEM_APPLICATION.equals(item.getStatus()))
                                                                            || !finishData){
                                                                        // 如果是变更数据，并且是待申请状态下的，设置为 无效状态
                                                                        item.setStatus(StatusConstant.ITEM_CANCEL);
                                                                        itemListUpdate.add(item);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                else{
                                                    // 如果什么都没有变更，并且有成功数据，则清除所有的待申请状态的记录
                                                    if(finishData){
                                                        if(null != oldMember.getUpdateRecordList() && oldMember.getUpdateRecordList().size() > 0){
                                                            for (MemberBusinessUpdateRecord record : oldMember.getUpdateRecordList()) {
                                                                if(InsuranceType.SheBao.ordinal() == record.getServiceType() && null != record.getMemberBusinessUpdateRecordItems() && record.getMemberBusinessUpdateRecordItems().size() > 0){
                                                                    // 获取社保的变更数据
                                                                    for (MemberBusinessUpdateRecordItem item : record.getMemberBusinessUpdateRecordItems()) {
                                                                        if((StatusConstant.ITEM_APPLICATION.equals(item.getStatus()))){
                                                                            // 如果什么都没有变更，并且有成功数据，则清除所有的待申请状态的记录
                                                                            item.setStatus(StatusConstant.ITEM_CANCEL);
                                                                            itemListUpdate.add(item);
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            else{
                                                String msg = StatusConstant.MSG_TRANSACTOR;
                                                if(!CommonUtil.isEmpty(baseChange)){
                                                    msg += "、" + baseChange;
                                                }
                                                if(isFinishData(oldMember,InsuranceType.SheBao.ordinal())){
                                                    // 办理方放生了变更，减员办理方、新增办理方
                                                    MemberBusinessUpdateRecordItem item = getRecordItem(recordId, StatusConstant.UPDATE_TRANSACTOR, StatusConstant.MSG_TRANSACTOR,SUBTRACT, old.getMemberBusinessItem(),StatusConstant.ITEM_APPLICATION,newMember);
                                                    itemList.add(item);
                                                }

                                                // 判断其他基础字段修改项
                                                MemberBusinessUpdateRecordItem addItem = getRecordItem(recordId, StatusConstant.UPDATE_TRANSACTOR, msg,ADD, nB.getMemberBusinessItem(),StatusConstant.ITEM_APPLICATION,newMember);
                                                itemList.add(addItem);


                                                // 把之前的社保减员 和 增员记录 设置为 无效
                                                getValidData(oldMember, itemListUpdate, 0);
                                            }
                                        }
                                        else{
                                            if(isFinishData(oldMember,InsuranceType.SheBao.ordinal())){
                                                // 如果经办机构发生了变更，则减员经办机构，然后新增经办机构
                                                MemberBusinessUpdateRecordItem item = getRecordItem(recordId, StatusConstant.UPDATE_ORGNAIZATION, StatusConstant.MSG_ORGNAIZATION,SUBTRACT, old.getMemberBusinessItem(),StatusConstant.ITEM_APPLICATION,newMember);
                                                itemList.add(item);
                                            }
                                            // 判断其他基础字段修改项
                                            String msg = StatusConstant.MSG_ORGNAIZATION;
                                            if(!CommonUtil.isEmpty(baseChange)){
                                                msg += "、" + baseChange;
                                            }
                                            MemberBusinessUpdateRecordItem addItem = getRecordItem(recordId, StatusConstant.UPDATE_ORGNAIZATION, msg,ADD, nB.getMemberBusinessItem(),StatusConstant.ITEM_APPLICATION,newMember);
                                            itemList.add(addItem);
                                            // 把之前的社保减员 和 增员记录 设置为 无效
                                            getValidData(oldMember, itemListUpdate, InsuranceType.SheBao.ordinal());
                                        }
                                    }
                                    else{

                                        if(isFinishData(oldMember,InsuranceType.SheBao.ordinal())){
                                            // 如果缴金地变更了，则减员缴金地，然后增员缴金地
                                            MemberBusinessUpdateRecordItem item = getRecordItem(recordId, StatusConstant.UPDATE_PAY_PLACE, StatusConstant.MSG_PAY_PLACE,SUBTRACT, old.getMemberBusinessItem(),StatusConstant.ITEM_APPLICATION,newMember);
                                            itemList.add(item);
                                        }
                                        // 判断其他基础字段修改项
                                        String msg = StatusConstant.MSG_PAY_PLACE;
                                        if(!CommonUtil.isEmpty(baseChange)){
                                            msg += "、" + baseChange;
                                        }
                                        MemberBusinessUpdateRecordItem addItem = getRecordItem(recordId, StatusConstant.UPDATE_PAY_PLACE, msg,ADD, nB.getMemberBusinessItem(),StatusConstant.ITEM_APPLICATION,newMember);
                                        itemList.add(addItem);
                                        // 把之前的社保减员 和 增员记录 设置为 无效
                                        getValidData(oldMember, itemListUpdate, InsuranceType.SheBao.ordinal());
                                    }
                                }
                                else if(old.getMemberBusinessItem().getServeMethod().equals(nB.getMemberBusinessItem().getServeMethod())
                                        && old.getMemberBusinessItem().getServeMethod() == 1){
                                    // 如果都是托管
                                    if(!old.getMemberBusinessItem().getPayPlaceId().equals(nB.getMemberBusinessItem().getPayPlaceId())){
                                        // 判断ID
                                        if(isFinishData(oldMember,InsuranceType.SheBao.ordinal())){
                                            // 如果缴金地变更了，则减员缴金地，然后增员缴金地
                                            MemberBusinessUpdateRecordItem item = getRecordItem(recordId, StatusConstant.UPDATE_PAY_PLACE, StatusConstant.MSG_PAY_PLACE,SUBTRACT, old.getMemberBusinessItem(),StatusConstant.ITEM_APPLICATION,newMember);
                                            itemList.add(item);
                                        }
                                        // 判断其他基础字段修改项
                                        String msg = StatusConstant.MSG_PAY_PLACE;
                                        if(!CommonUtil.isEmpty(baseChange)){
                                            msg += "、" + baseChange;
                                        }
                                        MemberBusinessUpdateRecordItem addItem = getRecordItem(recordId, StatusConstant.UPDATE_PAY_PLACE, msg,ADD, nB.getMemberBusinessItem(),StatusConstant.ITEM_APPLICATION,newMember);
                                        itemList.add(addItem);
                                        // 把之前的社保减员 和 增员记录 设置为 无效
                                        getValidData(oldMember, itemListUpdate, InsuranceType.SheBao.ordinal());
                                    }
                                    else{
                                        // 如果都没有变，则应判断基础字段
                                        boolean finishData = isFinishData(oldMember, InsuranceType.SheBao.ordinal());
                                        if(!CommonUtil.isEmpty(baseChange)){
                                            // 基础字段有发生变更
                                            List<MemberBaseChange> changeList = new ArrayList<MemberBaseChange>();
                                            int serviceStatus = CHANGE;
                                            if(!finishData){
                                                // 如果不存在 成功的数据
                                                serviceStatus = ADD;
                                            }
                                            MemberBusinessUpdateRecordItem temp = getRecordItem(recordId, StatusConstant.UPDATE_RATIO_BASENUMBER, baseChange,serviceStatus, nB.getMemberBusinessItem(),StatusConstant.ITEM_APPLICATION,null);
                                            changeList.addAll(baseChangeList);
                                            temp.setBaseChangeList(changeList);
                                            itemList.add(temp);
                                            if(null != oldMember.getUpdateRecordList() && oldMember.getUpdateRecordList().size() > 0){
                                                for (MemberBusinessUpdateRecord record : oldMember.getUpdateRecordList()) {
                                                    if(InsuranceType.SheBao.ordinal() == record.getServiceType() && null != record.getMemberBusinessUpdateRecordItems() && record.getMemberBusinessUpdateRecordItems().size() > 0){
                                                        // 获取社保的变更数据
                                                        for (MemberBusinessUpdateRecordItem item : record.getMemberBusinessUpdateRecordItems()) {
                                                            if((StatusConstant.ITEM_APPLICATION.equals(item.getStatus()))
                                                                    || !finishData){
                                                                // 如果是变更数据，并且是待申请状态下的，设置为 无效状态
                                                                item.setStatus(StatusConstant.ITEM_CANCEL);
                                                                itemListUpdate.add(item);
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        else{
                                            // 如果什么都没有变更
                                            // 如果什么都没有变更，并且有成功数据，则清除所有的待申请状态的记录
                                            if(finishData){
                                                if(null != oldMember.getUpdateRecordList() && oldMember.getUpdateRecordList().size() > 0){
                                                    for (MemberBusinessUpdateRecord record : oldMember.getUpdateRecordList()) {
                                                        if(InsuranceType.SheBao.ordinal() == record.getServiceType() && null != record.getMemberBusinessUpdateRecordItems() && record.getMemberBusinessUpdateRecordItems().size() > 0){
                                                            // 获取社保的变更数据
                                                            for (MemberBusinessUpdateRecordItem item : record.getMemberBusinessUpdateRecordItems()) {
                                                                if((StatusConstant.ITEM_APPLICATION.equals(item.getStatus()))){
                                                                    // 如果什么都没有变更，并且有成功数据，则清除所有的待申请状态的记录
                                                                    item.setStatus(StatusConstant.ITEM_CANCEL);
                                                                    itemListUpdate.add(item);
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            else{
                                                //如果什么都没有变更，并且没有有成功数据
                                                // 创建一条新增记录，并且设置其他所有除了失败记录外的状态为失效
                                                if(null != oldMember.getUpdateRecordList() && oldMember.getUpdateRecordList().size() > 0){
                                                    for (MemberBusinessUpdateRecord record : oldMember.getUpdateRecordList()) {
                                                        if(InsuranceType.SheBao.ordinal() == record.getServiceType() && null != record.getMemberBusinessUpdateRecordItems() && record.getMemberBusinessUpdateRecordItems().size() > 0){
                                                            // 获取社保的变更数据
                                                            for (MemberBusinessUpdateRecordItem item : record.getMemberBusinessUpdateRecordItems()) {
                                                                if((StatusConstant.ITEM_APPLICATION.equals(item.getStatus()))){
                                                                    // 如果什么都没有变更，并且有成功数据，则清除所有的待申请状态的记录
                                                                    item.setStatus(StatusConstant.ITEM_CANCEL);
                                                                    itemListUpdate.add(item);
                                                                }
                                                            }
                                                        }
                                                    }
                                                    MemberBusinessUpdateRecordItem item = getRecordItem(recordId, StatusConstant.UPDATE_PAY_PLACE, StatusConstant.MSG_PAY_PLACE,ADD, old.getMemberBusinessItem(),StatusConstant.ITEM_APPLICATION,newMember);
                                                    itemList.add(item);
                                                }
                                            }
                                        }
                                    }
                                }
                                else if(!old.getMemberBusinessItem().getServeMethod().equals(nB.getMemberBusinessItem().getServeMethod())){
                                    // 如果缴金地变更了，则减员缴金地，然后增员缴金地
                                    if(isFinishData(oldMember,InsuranceType.SheBao.ordinal())){
                                        MemberBusinessUpdateRecordItem item = getRecordItem(recordId, StatusConstant.UPDATE_PAY_PLACE, StatusConstant.MSG_PAY_PLACE,SUBTRACT, old.getMemberBusinessItem(),StatusConstant.ITEM_APPLICATION,newMember);
                                        itemList.add(item);
                                    }
                                    // 判断其他基础字段修改项
                                    String msg = StatusConstant.MSG_PAY_PLACE;
                                    if(!CommonUtil.isEmpty(baseChange)){
                                        msg += "、" + baseChange;
                                    }
                                    MemberBusinessUpdateRecordItem addItem = getRecordItem(recordId, StatusConstant.UPDATE_PAY_PLACE, msg,ADD, nB.getMemberBusinessItem(),StatusConstant.ITEM_APPLICATION,newMember);
                                    itemList.add(addItem);
                                    // 把之前的社保减员 和 增员记录 设置为 无效
                                    getValidData(oldMember, itemListUpdate, InsuranceType.SheBao.ordinal());
                                }
                                isExist = true;
                            }
                            // 存在社保业务处理-结束

                            if(old.getId().equals(nB.getId()) && BusinessEnum.gongJiJin.ordinal() == nB.getId()){
                                // 公积金存在
                                Integer recordId =  null;
                                for (MemberBusinessUpdateRecord record : oldMember.getUpdateRecordList()) {
                                    if(InsuranceType.GongJiJin.ordinal() == record.getServiceType()){
                                        // 公积金
                                        recordId = record.getId();
                                        break;
                                    }
                                }
                                if(null == recordId){
                                    continue;
                                }
                                if(old.getMemberBusinessItem().getServeMethod().equals(nB.getMemberBusinessItem().getServeMethod())
                                        && old.getMemberBusinessItem().getServeMethod() == 0){
                                    // 如果都是代理
                                    if(old.getMemberBusinessItem().getPayPlaceId().equals(nB.getMemberBusinessItem().getPayPlaceId())){
                                        // 如果缴金地没有变更,判断经办机构
                                        if(old.getMemberBusinessItem().getOrganizationId().equals(nB.getMemberBusinessItem().getOrganizationId())){
                                            // 如果经办机构没有发生变更，判断办理方
                                            if(old.getMemberBusinessItem().getTransactorId().equals(nB.getMemberBusinessItem().getTransactorId())){
                                                // 办理方没有发生变更，判断比例
                                                if(old.getMemberBusinessItem().getRatio().equals(nB.getMemberBusinessItem().getRatio())){
                                                    // 比例没有发生变更 判断基数
                                                    String content = "";
                                                    List<MemberBaseChange> changeList = new ArrayList<MemberBaseChange>();
                                                    if(old.getMemberBusinessItem().getBaseType() == 2 && old.getMemberBusinessItem().getBaseType().equals(nB.getMemberBusinessItem().getBaseType())
                                                            && !old.getMemberBusinessItem().getBaseNumber().equals(nB.getMemberBusinessItem().getBaseNumber())){
                                                        // 基数发生变更
                                                        content += "基数、";
                                                        MemberBaseChange base = new MemberBaseChange();
                                                        base.setContent(nB.getMemberBusinessItem().getBaseNumber().toString());
                                                        base.setTargetId(nB.getMemberBusinessItem().getBaseType());
                                                        base.setType(StatusConstant.BASE_BASE_BUMBER);
                                                        changeList.add(base);
                                                    }
                                                    if(!old.getMemberBusinessItem().getBaseType().equals(nB.getMemberBusinessItem().getBaseType())){
                                                        // 基数反生变更
                                                        content += "基数、";
                                                        MemberBaseChange base = new MemberBaseChange();
                                                        base.setTargetId(nB.getMemberBusinessItem().getBaseType());
                                                        base.setType(StatusConstant.BASE_BASE_BUMBER);
                                                        changeList.add(base);
                                                    }
                                                    if(!CommonUtil.isEmpty(baseChange)){
                                                        // 基础字段有发生变更
                                                        content += baseChange + "、";
                                                    }
                                                    boolean finishData = isFinishData(oldMember, InsuranceType.GongJiJin.ordinal());
                                                    if(!CommonUtil.isEmpty(content)){
                                                        int serviceStatus = CHANGE;
                                                        if(!finishData){
                                                            serviceStatus = ADD;
                                                        }
                                                        if(null != oldMember.getUpdateRecordList() && oldMember.getUpdateRecordList().size() > 0){
                                                            for (MemberBusinessUpdateRecord record : oldMember.getUpdateRecordList()) {
                                                                if(InsuranceType.GongJiJin.ordinal() == record.getServiceType() && null != record.getMemberBusinessUpdateRecordItems() && record.getMemberBusinessUpdateRecordItems().size() > 0){
                                                                    // 获取公积金的变更数据
                                                                    for (MemberBusinessUpdateRecordItem item : record.getMemberBusinessUpdateRecordItems()) {
//                                                                        if((CHANGE == item.getServiceStatus() && StatusConstant.ITEM_APPLICATION.equals(item.getStatus()))
                                                                        if((StatusConstant.ITEM_APPLICATION.equals(item.getStatus()))
                                                                                || !finishData){
                                                                            // 如果是变更数据，并且是待申请状态下的，设置为 无效状态
                                                                            item.setStatus(StatusConstant.ITEM_CANCEL);
                                                                            itemListUpdate.add(item);
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        MemberBusinessUpdateRecordItem temp = getRecordItem(recordId, StatusConstant.UPDATE_RATIO_BASENUMBER, content.substring(0,content.length() - 1),serviceStatus, nB.getMemberBusinessItem(),StatusConstant.ITEM_APPLICATION,null);
                                                        changeList.addAll(baseChangeList);
                                                        temp.setBaseChangeList(changeList);
                                                        itemList.add(temp);
                                                    }
                                                    else{
                                                        if(finishData){
                                                            if(null != oldMember.getUpdateRecordList() && oldMember.getUpdateRecordList().size() > 0){
                                                                for (MemberBusinessUpdateRecord record : oldMember.getUpdateRecordList()) {
                                                                    if(InsuranceType.GongJiJin.ordinal() == record.getServiceType() && null != record.getMemberBusinessUpdateRecordItems() && record.getMemberBusinessUpdateRecordItems().size() > 0){
                                                                        // 获取公积金的变更数据
                                                                        for (MemberBusinessUpdateRecordItem item : record.getMemberBusinessUpdateRecordItems()) {
//                                                                        if((CHANGE == item.getServiceStatus() && StatusConstant.ITEM_APPLICATION.equals(item.getStatus()))
                                                                            if((StatusConstant.ITEM_APPLICATION.equals(item.getStatus()))){
                                                                                // 如果是变更数据，并且是待申请状态下的，设置为 无效状态
                                                                                item.setStatus(StatusConstant.ITEM_CANCEL);
                                                                                itemListUpdate.add(item);
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        else{
                                                            //如果什么都没有变更，并且没有有成功数据
                                                            // 创建一条新增记录，并且设置其他所有除了失败记录外的状态为失效
                                                            if(null != oldMember.getUpdateRecordList() && oldMember.getUpdateRecordList().size() > 0){
                                                                for (MemberBusinessUpdateRecord record : oldMember.getUpdateRecordList()) {
                                                                    if(InsuranceType.GongJiJin.ordinal() == record.getServiceType() && null != record.getMemberBusinessUpdateRecordItems() && record.getMemberBusinessUpdateRecordItems().size() > 0){
                                                                        // 获取社保的变更数据
                                                                        for (MemberBusinessUpdateRecordItem item : record.getMemberBusinessUpdateRecordItems()) {
                                                                            if((StatusConstant.ITEM_APPLICATION.equals(item.getStatus()))){
                                                                                // 如果什么都没有变更，并且有成功数据，则清除所有的待申请状态的记录
                                                                                item.setStatus(StatusConstant.ITEM_CANCEL);
                                                                                itemListUpdate.add(item);
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                                MemberBusinessUpdateRecordItem item = getRecordItem(recordId, StatusConstant.UPDATE_PAY_PLACE, StatusConstant.MSG_PAY_PLACE,ADD, old.getMemberBusinessItem(),StatusConstant.ITEM_APPLICATION,newMember);
                                                                itemList.add(item);
                                                            }
                                                        }
                                                    }
                                                }else{
                                                    // 比例发生变更,减员比例、增员比例
                                                    if (isFinishData(oldMember,InsuranceType.GongJiJin.ordinal())) {
                                                        MemberBusinessUpdateRecordItem item = getRecordItem(recordId, StatusConstant.UPDATE_RATIO, StatusConstant.MSG_RATIO,SUBTRACT, old.getMemberBusinessItem(),StatusConstant.ITEM_APPLICATION,newMember);
                                                        itemList.add(item);
                                                    }
                                                    // 判断其他基础字段修改项
                                                    String msg = StatusConstant.MSG_RATIO;
                                                    if(!CommonUtil.isEmpty(baseChange)){
                                                        msg += "、" + baseChange;
                                                    }
                                                    // 把之前的社保减员 和 增员记录 设置为 无效
                                                    getValidData(oldMember, itemListUpdate, InsuranceType.GongJiJin.ordinal());
                                                    MemberBusinessUpdateRecordItem addItem = getRecordItem(recordId, StatusConstant.UPDATE_RATIO, msg,ADD, nB.getMemberBusinessItem(),StatusConstant.ITEM_APPLICATION,newMember);
                                                    itemList.add(addItem);
                                                }
                                            }
                                            else{
                                                // 办理方发生了变更，减员办理方、新增办理方
                                                if (isFinishData(oldMember,InsuranceType.GongJiJin.ordinal())) {
                                                    MemberBusinessUpdateRecordItem item = getRecordItem(recordId, StatusConstant.UPDATE_TRANSACTOR, StatusConstant.MSG_TRANSACTOR,SUBTRACT, old.getMemberBusinessItem(),StatusConstant.ITEM_APPLICATION,newMember);
                                                    itemList.add(item);
                                                }
                                                // 判断其他基础字段修改项
                                                String msg = StatusConstant.MSG_TRANSACTOR;
                                                if(!CommonUtil.isEmpty(baseChange)){
                                                    msg += "、" + baseChange;
                                                }
                                                // 把之前的社保减员 和 增员记录 设置为 无效
                                                getValidData(oldMember, itemListUpdate, InsuranceType.GongJiJin.ordinal());
                                                MemberBusinessUpdateRecordItem addItem = getRecordItem(recordId, StatusConstant.UPDATE_TRANSACTOR, msg,ADD, nB.getMemberBusinessItem(),StatusConstant.ITEM_APPLICATION,newMember);
                                                itemList.add(addItem);
                                            }
                                        }
                                        else{
                                            // 如果经办机构发生了变更，则减员经办机构，然后新增经办机构
                                            if (isFinishData(oldMember,InsuranceType.GongJiJin.ordinal())) {
                                                MemberBusinessUpdateRecordItem item = getRecordItem(recordId, StatusConstant.UPDATE_ORGNAIZATION, StatusConstant.MSG_ORGNAIZATION,SUBTRACT, old.getMemberBusinessItem(),StatusConstant.ITEM_APPLICATION,newMember);
                                                itemList.add(item);
                                            }
                                            // 判断其他基础字段修改项
                                            String msg = StatusConstant.MSG_ORGNAIZATION;
                                            if(!CommonUtil.isEmpty(baseChange)){
                                                msg += "、" + baseChange;
                                            }
                                            // 把之前的社保减员 和 增员记录 设置为 无效
                                            getValidData(oldMember, itemListUpdate, InsuranceType.GongJiJin.ordinal());
                                            MemberBusinessUpdateRecordItem addItem = getRecordItem(recordId, StatusConstant.UPDATE_ORGNAIZATION, msg,ADD, nB.getMemberBusinessItem(),StatusConstant.ITEM_APPLICATION,newMember);
                                            itemList.add(addItem);
                                        }
                                    }
                                    else{
                                        // 如果缴金地变更了，则减员缴金地，然后增员缴金地
                                        if (isFinishData(oldMember,InsuranceType.GongJiJin.ordinal())) {
                                            MemberBusinessUpdateRecordItem item = getRecordItem(recordId, StatusConstant.UPDATE_PAY_PLACE, StatusConstant.MSG_PAY_PLACE,SUBTRACT, old.getMemberBusinessItem(),StatusConstant.ITEM_APPLICATION,newMember);
                                            itemList.add(item);
                                        }
                                        // 判断其他基础字段修改项
                                        String msg = StatusConstant.MSG_PAY_PLACE;
                                        if(!CommonUtil.isEmpty(baseChange)){
                                            msg += "、" + baseChange;
                                        }
                                        // 把之前的社保减员 和 增员记录 设置为 无效
                                        getValidData(oldMember, itemListUpdate, InsuranceType.GongJiJin.ordinal());
                                        MemberBusinessUpdateRecordItem addItem = getRecordItem(recordId, StatusConstant.UPDATE_PAY_PLACE, msg,ADD, nB.getMemberBusinessItem(),StatusConstant.ITEM_APPLICATION,newMember);
                                        itemList.add(addItem);
                                    }
                                }
                                else if(old.getMemberBusinessItem().getServeMethod().equals(nB.getMemberBusinessItem().getServeMethod())
                                        && old.getMemberBusinessItem().getServeMethod() == 1){
                                    // 如果都是托管
                                    if(!old.getMemberBusinessItem().getPayPlaceId().equals(nB.getMemberBusinessItem().getPayPlaceId())){
                                        // 判断ID
                                        // 如果缴金地变更了，则减员缴金地，然后增员缴金地
                                        if (isFinishData(oldMember,InsuranceType.GongJiJin.ordinal())) {
                                            MemberBusinessUpdateRecordItem item = getRecordItem(recordId, StatusConstant.UPDATE_PAY_PLACE, StatusConstant.MSG_PAY_PLACE,SUBTRACT, old.getMemberBusinessItem(),StatusConstant.ITEM_APPLICATION,newMember);
                                            itemList.add(item);
                                        }
                                        // 判断其他基础字段修改项
                                        String msg = StatusConstant.MSG_PAY_PLACE;
                                        if(!CommonUtil.isEmpty(baseChange)){
                                            msg += "、" + baseChange;
                                        }
                                        // 把之前的社保减员 和 增员记录 设置为 无效
                                        getValidData(oldMember, itemListUpdate, InsuranceType.GongJiJin.ordinal());
                                        MemberBusinessUpdateRecordItem addItem = getRecordItem(recordId, StatusConstant.UPDATE_PAY_PLACE, msg,ADD, nB.getMemberBusinessItem(),StatusConstant.ITEM_APPLICATION,newMember);
                                        itemList.add(addItem);
                                    }
                                    else{
                                        // 如果缴金地没有变
                                        List<MemberBaseChange> changeList = new ArrayList<MemberBaseChange>();
                                        boolean finishData = isFinishData(oldMember, InsuranceType.GongJiJin.ordinal());
                                        if(!CommonUtil.isEmpty(baseChange)){
                                            // 基础字段有发生变更
                                            int serviceStatus = CHANGE;
                                            if(!finishData){
                                                serviceStatus = ADD;
                                            }
                                            if(null != oldMember.getUpdateRecordList() && oldMember.getUpdateRecordList().size() > 0){
                                                for (MemberBusinessUpdateRecord record : oldMember.getUpdateRecordList()) {
                                                    if(InsuranceType.GongJiJin.ordinal() == record.getServiceType() && null != record.getMemberBusinessUpdateRecordItems() && record.getMemberBusinessUpdateRecordItems().size() > 0){
                                                        // 获取公积金的变更数据
                                                        for (MemberBusinessUpdateRecordItem item : record.getMemberBusinessUpdateRecordItems()) {
                                                            if((StatusConstant.ITEM_APPLICATION.equals(item.getStatus()))
                                                                    || !finishData){
                                                                // 如果是变更数据，并且是待申请状态下的，设置为 无效状态
                                                                item.setStatus(StatusConstant.ITEM_CANCEL);
                                                                itemListUpdate.add(item);
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            MemberBusinessUpdateRecordItem temp = getRecordItem(recordId, StatusConstant.UPDATE_RATIO_BASENUMBER, baseChange,serviceStatus, nB.getMemberBusinessItem(),StatusConstant.ITEM_APPLICATION,null);
                                            changeList.addAll(baseChangeList);
                                            temp.setBaseChangeList(changeList);
                                            itemList.add(temp);
                                        }
                                        else{
                                            if(finishData){
                                                if(null != oldMember.getUpdateRecordList() && oldMember.getUpdateRecordList().size() > 0){
                                                    for (MemberBusinessUpdateRecord record : oldMember.getUpdateRecordList()) {
                                                        if(InsuranceType.GongJiJin.ordinal() == record.getServiceType() && null != record.getMemberBusinessUpdateRecordItems() && record.getMemberBusinessUpdateRecordItems().size() > 0){
                                                            // 获取公积金的变更数据
                                                            for (MemberBusinessUpdateRecordItem item : record.getMemberBusinessUpdateRecordItems()) {
                                                                if((StatusConstant.ITEM_APPLICATION.equals(item.getStatus()))){
                                                                    // 如果是变更数据，并且是待申请状态下的，设置为 无效状态
                                                                    item.setStatus(StatusConstant.ITEM_CANCEL);
                                                                    itemListUpdate.add(item);
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }


                                    }
                                }
                                else if(!old.getMemberBusinessItem().getServeMethod().equals(nB.getMemberBusinessItem().getServeMethod())){
                                    // 如果缴金地变更了，则减员缴金地，然后增员缴金地
                                    if (isFinishData(oldMember,InsuranceType.GongJiJin.ordinal())) {
                                        MemberBusinessUpdateRecordItem item = getRecordItem(recordId, StatusConstant.UPDATE_PAY_PLACE, StatusConstant.MSG_PAY_PLACE,SUBTRACT, old.getMemberBusinessItem(),StatusConstant.ITEM_APPLICATION,newMember);
                                        itemList.add(item);
                                    }
                                    // 判断其他基础字段修改项
                                    String msg = StatusConstant.MSG_PAY_PLACE;
                                    if(!CommonUtil.isEmpty(baseChange)){
                                        msg += "、" + baseChange;
                                    }
                                    MemberBusinessUpdateRecordItem addItem = getRecordItem(recordId, StatusConstant.UPDATE_PAY_PLACE, StatusConstant.MSG_PAY_PLACE,ADD, nB.getMemberBusinessItem(),StatusConstant.ITEM_APPLICATION,newMember);
                                    itemList.add(addItem);
                                    // 把之前的社保减员 和 增员记录 设置为 无效
                                    getValidData(oldMember, itemListUpdate, InsuranceType.GongJiJin.ordinal());
                                }
                                isExistOfReservedFunds = true;
                                // 存在公积金 业务处理-结束
                            }
                        }
                        if(!isExist){
                            // 如果社保被减员 // fixme
                            for (MemberBusinessUpdateRecord record : oldMember.getUpdateRecordList()) {
                                if(InsuranceType.SheBao.ordinal() == record.getServiceType() && BusinessEnum.sheBao.ordinal() == old.getId()){
                                    // 社保
                                    Integer status = StatusConstant.ITEM_APPLICATION; // 新数据的状态
                                    MemberBusinessUpdateRecordItem success = null;
                                    if(null != record.getMemberBusinessUpdateRecordItems() && record.getMemberBusinessUpdateRecordItems().size() > 0){
                                        for (MemberBusinessUpdateRecordItem item : record.getMemberBusinessUpdateRecordItems()) {
                                            if(StatusConstant.ITEM_APPLICATION.equals(item.getStatus())){
                                                // 如果老数据中有待申请的资料，则设置为 无效
                                                item.setStatus(StatusConstant.ITEM_CANCEL);
                                                itemListUpdate.add(item);
                                            }
                                            if(StatusConstant.ITEM_SUCCESS.equals(item.getStatus()) && null == success
                                                    && StatusConstant.UPDATE_SOCIAL_SECURITY.equals(item.getUpdateContentFlag())
                                                    && SUBTRACT == item.getServiceStatus()){
                                                // 如果最新一条的成功反馈数据为  社保减员，当前操作为减员数据 则当条记录也为 无效记录
                                                success = item;
                                                status = StatusConstant.ITEM_CANCEL;
                                            }
                                        }
                                    }
                                    if(!isFinishData(oldMember,InsuranceType.SheBao.ordinal())){
                                        status = StatusConstant.ITEM_CANCEL;
                                    }
                                    MemberBusinessUpdateRecordItem item = getRecordItem(record.getId(), StatusConstant.UPDATE_SOCIAL_SECURITY, StatusConstant.MSG_SHEBAO,SUBTRACT, old.getMemberBusinessItem(),status,newMember);
                                    itemList.add(item);
                                    break;
                                }
                            }

                        }
                        if(!isExistOfReservedFunds){
                            // 如果公积金被减员
                            for (MemberBusinessUpdateRecord record : oldMember.getUpdateRecordList()) {
                                if(InsuranceType.GongJiJin.ordinal() == record.getServiceType() && BusinessEnum.gongJiJin.ordinal() == old.getId()){
                                    // 公积金
                                    Integer status = StatusConstant.ITEM_APPLICATION; // 新数据的状态
                                    MemberBusinessUpdateRecordItem success = null; // 成功记录，当前成功反馈的最新一条数据
                                    if(null != record.getMemberBusinessUpdateRecordItems() && record.getMemberBusinessUpdateRecordItems().size() > 0){
                                        for (MemberBusinessUpdateRecordItem item : record.getMemberBusinessUpdateRecordItems()) {
                                            if(StatusConstant.ITEM_APPLICATION.equals(item.getStatus())){
                                                // 如果老数据中有待申请的资料，则设置为 无效
                                                item.setStatus(StatusConstant.ITEM_CANCEL);
                                                itemListUpdate.add(item);
                                            }
                                            if(StatusConstant.ITEM_SUCCESS.equals(item.getStatus()) && null == success
                                                    && StatusConstant.UPDATE_RESERVED_FUNDS.equals(item.getUpdateContentFlag())
                                                    && SUBTRACT == item.getServiceStatus()){
                                                // 如果最新一条的成功反馈数据为  社保减员，当前操作为减员数据 则当条记录也为 无效记录
                                                success = item;
                                                status = StatusConstant.ITEM_CANCEL;
                                            }
                                        }
                                    }
                                    if(!isFinishData(oldMember,InsuranceType.GongJiJin.ordinal())){
                                        status = StatusConstant.ITEM_CANCEL;
                                    }
                                    MemberBusinessUpdateRecordItem item = getRecordItem(record.getId(), StatusConstant.UPDATE_RESERVED_FUNDS, StatusConstant.MSG_GONGJIJIN,SUBTRACT, old.getMemberBusinessItem(),status,newMember);
                                    itemList.add(item);
                                    break;
                                }
                            }
                        }

                    }


                    for (Business nb : newMember.getBusinessList()) {
                        if(BusinessEnum.sheBao.ordinal() == nb.getId()){
                            boolean isExist = false;
                            for (Business ob : oldMember.getBusinessList()) {
                                if(nb.getId().equals(ob.getId())){
                                    isExist = true;
                                }
                            }
                            if(!isExist){
                                // 如果老数据不存在社保，新的存在社保，则新增
                                boolean isExist2 = false;
                                for (MemberBusinessUpdateRecord record : newMember.getUpdateRecordList()) {
                                    if(0 == record.getServiceType()){
                                        // 社保
                                        // 把之前的
                                        MemberBusinessUpdateRecordItem item = getRecordItem(record.getId(), StatusConstant.UPDATE_SOCIAL_SECURITY, StatusConstant.MSG_SHEBAO,ADD, nb.getMemberBusinessItem(),StatusConstant.ITEM_APPLICATION,newMember);
                                        itemList.add(item);
                                        isExist2 = true;
                                        break;
                                    }
                                }
                                if(!isExist2){
                                    recordList.add(getRecord(nb,newMember));
                                }
                            }
                        }
                        if(BusinessEnum.gongJiJin.ordinal() == nb.getId()){
                            boolean isExistReservedFunds = false;
                            for (Business ob : oldMember.getBusinessList()) {
                                if(nb.getId().equals(ob.getId())){
                                    isExistReservedFunds = true;
                                }
                            }
                            if(!isExistReservedFunds){
                                // 如果老数据不存在公积金，新的存在公积金，则新增公积金
                                boolean isExist2 = false;
                                for (MemberBusinessUpdateRecord record : newMember.getUpdateRecordList()) {
                                    if(1 == record.getServiceType()){
                                        // 公积金
                                        // 把之前的
                                        MemberBusinessUpdateRecordItem item = getRecordItem(record.getId(), StatusConstant.UPDATE_RESERVED_FUNDS, StatusConstant.MSG_GONGJIJIN,ADD, nb.getMemberBusinessItem(),StatusConstant.ITEM_APPLICATION,newMember);
                                        itemList.add(item);
                                        isExist2 = true;
                                        break;
                                    }
                                }
                                if(!isExist2){
                                    //不存在大类公积金
                                    recordList.add(getRecord(nb,newMember));
                                }
                            }
                        }
                    }
                }
            }
            else{
                // 如果老业务不存在，判断新业务，新增数据
                if(null != newMember.getBusinessList() && newMember.getBusinessList().size() > 0){
                    for (Business nB : newMember.getBusinessList()) {
                        if(BusinessEnum.sheBao.ordinal() == nB.getId()){
                            // 如果有社保
                            if(null != oldMember.getUpdateRecordList() && oldMember.getUpdateRecordList().size() > 0){
                                for (MemberBusinessUpdateRecord record : oldMember.getUpdateRecordList()) {
                                    if(0 == record.getServiceType()){
                                        // 社保
                                        // 把之前的
                                        MemberBusinessUpdateRecordItem item = getRecordItem(record.getId(), StatusConstant.UPDATE_SOCIAL_SECURITY, StatusConstant.MSG_SHEBAO,ADD, nB.getMemberBusinessItem(),StatusConstant.ITEM_APPLICATION,newMember);
                                        itemList.add(item);
                                        break;
                                    }
                                }
                            }
                            else{
                                recordList.add(getRecord(nB,newMember));
                            }
                        }
                        if(BusinessEnum.gongJiJin.ordinal() == nB.getId()){
                            // 如果有社保
                            if (null != oldMember.getUpdateRecordList() && oldMember.getUpdateRecordList().size() > 0) {
                                for (MemberBusinessUpdateRecord record : oldMember.getUpdateRecordList()) {
                                    if(1 == record.getServiceType()){
                                        // 公积金
                                        MemberBusinessUpdateRecordItem item = getRecordItem(record.getId(), StatusConstant.UPDATE_RESERVED_FUNDS, StatusConstant.MSG_GONGJIJIN,ADD, nB.getMemberBusinessItem(),StatusConstant.ITEM_APPLICATION,newMember);
                                        itemList.add(item);
                                        break;
                                    }
                                }
                            }
                            else{
                                recordList.add(getRecord(nB,newMember));
                            }
                        }
                    }

                }

            }
        }
        else {
            // 如果是新增的新员工
            if(null != newMember.getBusinessList() && newMember.getBusinessList().size() > 0){
                for (Business nB : newMember.getBusinessList()) {
                    if(BusinessEnum.sheBao.ordinal() == nB.getId()){
                        // 如果有社保
                        recordList.add(getRecord(nB,newMember));
                    }
                    if(BusinessEnum.gongJiJin.ordinal() == nB.getId()){
                        // 如果有社保
                        recordList.add(getRecord(nB,newMember));
                    }
                }
            }
        }
        Map<String,Object> data = new HashMap<String, Object>();
        data.put("recordList",recordList);
        data.put("recordItemList",itemList);
        data.put("itemListUpdate",itemListUpdate);
        return data;
    }

    /**
     *  获取老数据之前待申请的记录 等待更新为 无效状态
     * @param oldMember
     * @param itemListUpdate
     */
    private static void getValidData(Member oldMember, List<MemberBusinessUpdateRecordItem> itemListUpdate,int serviceType) {
        for (MemberBusinessUpdateRecord record : oldMember.getUpdateRecordList()) {
            if(serviceType == record.getServiceType() && null != record.getMemberBusinessUpdateRecordItems() && record.getMemberBusinessUpdateRecordItems().size() > 0){
                // 获取社保的新增、减员数据记录
                for (MemberBusinessUpdateRecordItem temp : record.getMemberBusinessUpdateRecordItems()) {
                    if((ADD == temp.getServiceStatus() || SUBTRACT == temp.getServiceStatus())
                            && StatusConstant.ITEM_APPLICATION.equals(temp.getStatus())){
                        // 如果是变更数据，并且是待申请状态下的，设置为 无效状态
                        temp.setStatus(StatusConstant.ITEM_CANCEL);
                        itemListUpdate.add(temp);
                    }
                }
            }
        }
    }

    /**
     * 判断之前是否有成功数据，如果没有成功数据，则当前新增的记录应为 增员操作，而非其他 且只有一条
     * @param oldMember
     * @return
     */
    private static boolean isFinishData(Member oldMember,int serviceType){

        if(null != oldMember.getUpdateRecordList() && oldMember.getUpdateRecordList().size() > 0){
            for (MemberBusinessUpdateRecord record : oldMember.getUpdateRecordList()) {
                if(serviceType == record.getServiceType() && null != record.getMemberBusinessUpdateRecordItems()
                        && record.getMemberBusinessUpdateRecordItems().size() > 0){
                    for (MemberBusinessUpdateRecordItem item : record.getMemberBusinessUpdateRecordItems()) {
                        if(StatusConstant.ITEM_SUCCESS.equals(item.getStatus())){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }



    /**
     * 获取基础字段的变更内容
     * @param oldMember
     * @param newMember
     * @return
     */
    private static Map<String,Object> getBaseChange(Member oldMember, Member newMember) {
        String msg = "";
        List<MemberBaseChange> changeList = new ArrayList<MemberBaseChange>();
        Map<String,Object> data = new HashMap<String, Object>();
        if (!CommonUtil.isEmpty(oldMember.getUserName(),newMember.getUserName())) {
            if(!oldMember.getUserName().equals(newMember.getUserName())){
                msg += StatusConstant.MSG_NAME+"、";
                MemberBaseChange base = new MemberBaseChange();
                base.setContent(newMember.getUserName());
                base.setType(StatusConstant.BASE_NAME);
                changeList.add(base);
            }
        }
        if (!CommonUtil.isEmpty(oldMember.getCertificateType(),newMember.getCertificateType())) {
            if(!oldMember.getCertificateType().equals(newMember.getCertificateType())){
                msg += StatusConstant.MSG_CARD_TYPE+"、";
                MemberBaseChange base = new MemberBaseChange();
                base.setTargetId(newMember.getCertificateType());
                base.setType(StatusConstant.BASE_CARD_TYPE);
                changeList.add(base);
            }
        }
        if (!CommonUtil.isEmpty(oldMember.getCertificateNum(),newMember.getCertificateNum())) {
            if(!oldMember.getCertificateNum().equals(newMember.getCertificateNum())){
                msg += StatusConstant.MSG_CARD+"、";
                MemberBaseChange base = new MemberBaseChange();
                base.setContent(newMember.getCertificateNum());
                base.setType(StatusConstant.BASE_CARD);
                changeList.add(base);
            }
        }
        if (!CommonUtil.isEmpty(oldMember.getEducation(),newMember.getEducation())) {
            if(!oldMember.getEducation().equals(newMember.getEducation())){
                msg += StatusConstant.MSG_EDUCATION+"、";
                MemberBaseChange base = new MemberBaseChange();
                base.setTargetId(newMember.getEducation());
                base.setType(StatusConstant.BASE_EDUCATION);
                changeList.add(base);
            }
        }
        if (!CommonUtil.isEmpty(oldMember.getPhone(),newMember.getPhone())) {
            if(!oldMember.getPhone().equals(newMember.getPhone())){
                msg += StatusConstant.MSG_PHONE;
                MemberBaseChange base = new MemberBaseChange();
                base.setContent(newMember.getPhone());
                base.setType(StatusConstant.BASE_PHONE);
                changeList.add(base);
            }
        }
        if(!CommonUtil.isEmpty(msg)){
            msg = msg.substring(0,msg.length() - 1);
        }
        data.put("msg",msg);
        data.put("changeList",changeList);
        return data;
    }


    private static MemberBusinessUpdateRecord getRecord(Business nb,Member member){
        MemberBusinessUpdateRecord r = new MemberBusinessUpdateRecord();
        r.setMemberId(member.getId());

        r.setServiceType(BusinessEnum.sheBao.ordinal() == nb.getId() ? 0 : 1);
        r.setPayPlaceId(nb.getMemberBusinessItem().getPayPlaceId());
        r.setOrganizationId(nb.getMemberBusinessItem().getOrganizationId());
        r.setTransactorId(nb.getMemberBusinessItem().getTransactorId());
        r.setStatus(0);
        r.setInsuranceLevelId(nb.getMemberBusinessItem().getInsuranceLevelId());

        List<MemberBusinessUpdateRecordItem> iList = new ArrayList<MemberBusinessUpdateRecordItem>();
        if(nb.getMemberBusinessItem().getServeMethod() == 0){
            MemberBusinessUpdateRecordItem item = getRecordItem(null,
                    BusinessEnum.sheBao.ordinal() == nb.getId() ? StatusConstant.UPDATE_SOCIAL_SECURITY : StatusConstant.UPDATE_RESERVED_FUNDS,
                    BusinessEnum.sheBao.ordinal() == nb.getId() ? StatusConstant.MSG_SHEBAO : StatusConstant.MSG_GONGJIJIN, ADD, nb.getMemberBusinessItem(),StatusConstant.ITEM_APPLICATION,member);
            iList.add(item);
        }
        else{
            MemberBusinessUpdateRecordItem item = getRecordItem(null,
                    BusinessEnum.sheBao.ordinal() == nb.getId() ? StatusConstant.UPDATE_SOCIAL_SECURITY : StatusConstant.UPDATE_RESERVED_FUNDS,
                    BusinessEnum.sheBao.ordinal() == nb.getId() ? StatusConstant.MSG_SHEBAO : StatusConstant.MSG_GONGJIJIN, ADD, nb.getMemberBusinessItem(),StatusConstant.ITEM_APPLICATION,member);
            iList.add(item);
        }
        r.setMemberBusinessUpdateRecordItems(iList);
        return r;
    }



    private static MemberBusinessUpdateRecordItem getRecordItem(Integer recordId,Integer contentFlag,String content,Integer serviceStatus,
                                                                MemberBusinessItem businessItem,Integer status,Member member){
        MemberBusinessUpdateRecordItem item = new MemberBusinessUpdateRecordItem();
        item.setStatus(status);
        item.setInsuranceLevelId(businessItem.getInsuranceLevelId());
        item.setTransactorId(businessItem.getTransactorId());
        item.setUpdateContent(content);
        item.setIsNowCreate(0);
        item.setServiceStatus(serviceStatus);
        item.setPayPlaceId(businessItem.getPayPlaceId());
        item.setOrganizationId(businessItem.getOrganizationId());
        item.setIsCalculate(0);
//        item.setCreateUserId(LoginHelper.getCurrentUser().getId());
        item.setServeMethod(businessItem.getServeMethod());
        item.setRatio(businessItem.getRatio());
        item.setBaseType(businessItem.getBaseType());
        item.setBaseNumber(businessItem.getBaseNumber());
        item.setUpdateContentFlag(contentFlag);
        item.setMemberBusinessUpdateRecordId(recordId);

        if(null != member){
            if(null != member.getCompanyId()){
                item.setCompanyId(member.getCompanyId());
            }
            // 设置基础数据
            List<MemberBaseChange> baseChangeList = new ArrayList<MemberBaseChange>();
            MemberBaseChange baseName = new MemberBaseChange();
            baseName.setType(StatusConstant.BASE_NAME);
            baseName.setContent(member.getUserName());
            baseChangeList.add(baseName);

            MemberBaseChange baseType = new MemberBaseChange();
            baseType.setType(StatusConstant.BASE_CARD_TYPE);
            baseType.setTargetId(member.getCertificateType());
            baseChangeList.add(baseType);

            MemberBaseChange baseCard = new MemberBaseChange();
            baseCard.setType(StatusConstant.BASE_CARD);
            baseCard.setContent(member.getCertificateNum());
            baseChangeList.add(baseCard);

            MemberBaseChange basePhone = new MemberBaseChange();
            basePhone.setType(StatusConstant.BASE_PHONE);
            basePhone.setContent(member.getPhone());
            baseChangeList.add(basePhone);

            MemberBaseChange baseEducation = new MemberBaseChange();
            baseEducation.setType(StatusConstant.BASE_EDUCATION);
            baseEducation.setTargetId(member.getEducation());
            baseChangeList.add(baseEducation);

            item.setBaseChangeList(baseChangeList);
        }
        return item;
    }


}
