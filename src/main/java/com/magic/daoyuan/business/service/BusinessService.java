package com.magic.daoyuan.business.service;

import com.magic.daoyuan.business.entity.*;
import com.magic.daoyuan.business.enums.BusinessEnum;
import com.magic.daoyuan.business.exception.InterfaceCommonException;
import com.magic.daoyuan.business.mapper.IBusinessItemMapper;
import com.magic.daoyuan.business.mapper.IBusinessMapper;
import com.magic.daoyuan.business.mapper.ICompanyBusinessItemMapper;
import com.magic.daoyuan.business.mapper.ILogMapper;
import com.magic.daoyuan.business.util.CommonUtil;
import com.magic.daoyuan.business.util.StatusConstant;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Eric Xie on 2017/9/12 0012.
 */
@Service
public class BusinessService {

    @Resource
    private IBusinessMapper businessMapper;
    @Resource
    private IBusinessItemMapper businessItemMapper;
    @Resource
    private ILogMapper logMapper;
    @Resource
    private ICompanyBusinessItemMapper companyBusinessItemMapper;


    public List<Business> queryOtherBusiness(){
        return businessMapper.queryOtherBusiness();
    }

    /**
     * 通过公司 查询公司下的业务集合
     * @param companyId
     * @return
     */
    public List<Business> queryBusinessByCompany(Integer companyId){
        return businessMapper.queryBusiness(companyId);
    }

    /**
     * 获取所有的业务子类 通过业务ID
     * @param businessId
     * @return
     */
    public List<BusinessItem> queryBusiness(Integer businessId,Integer companyId){

        List<BusinessItem> businessItemList = businessItemMapper.queryBusinessByCompany(businessId, companyId);

        if(null != businessItemList && businessItemList.size() > 0 && BusinessEnum.yiXiXingYeWu.ordinal() == businessId && null != companyId){
            // 通过公司ID 查询公司业务下一次性业务子类的子账单ID
            List<BusinessItem> items = companyBusinessItemMapper.queryItemByCompany(businessId, companyId);
            if(null != items && items.size() > 0){
                for (BusinessItem item : businessItemList) {
                    for (BusinessItem businessItem : items) {
                        if(item.getId().equals(businessItem.getId()) && null != businessItem.getCompanySonBillId()){
                            item.setCompanySonBillId(businessItem.getCompanySonBillId());
                            break;
                        }
                    }
                }
            }
        }
        return businessItemList;
    }

    /**
     * 获取所有 业务
     * @return
     */
    public List<Business> queryBusiness(){
        return businessMapper.queryBusinessByItems(null,null);
    }

    /**
     * 分页获取 业务子类列表 通过业务ID
     * @param pageArgs
     * @return
     */
    public PageList<BusinessItem> queryBusinessByItems(Map<String,Object> params, PageArgs pageArgs){
        List<BusinessItem> dataList = new ArrayList<BusinessItem>();
        int count = businessItemMapper.countBusinessItem(params);
        if(count > 0){
            params.put("limit",pageArgs.getPageStart());
            params.put("limitSize",pageArgs.getPageSize());
            dataList = businessItemMapper.queryBusinessItem(params);
        }
        return new PageList<BusinessItem>(dataList,count);
    }



    /**
     * 分页获取 业务
     * @param pageArgs
     * @return
     */
    public PageList<Business> queryBusinessByItems(PageArgs pageArgs){
        List<Business> dataList = new ArrayList<Business>();
        int count = businessMapper.countBusinessByItems();
        if(count > 0){
            dataList = businessMapper.queryBusinessByItems(pageArgs.getPageStart(),pageArgs.getPageSize());
            Iterator<Business> iterator = dataList.iterator();
            while (iterator.hasNext()){
                Business next = iterator.next();
                if(next.getId() == 1 || next.getId() == 2){
                    iterator.remove();
                }
            }
        }
        return new PageList<Business>(dataList,count);
    }


    @Transactional
    public void updateBusiness(BusinessItem item,User user){

        BusinessItem info = businessItemMapper.info(item.getId());
        if(null == info){
            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST,"业务不存在");
        }
        businessItemMapper.updateBusinessItem(item);
        String msg = "";

        if(null != item.getItemName() && !info.getItemName().equals(item.getItemName())){
            msg += "修改了业务配置名称为：" +item.getItemName() +"  ";
        }
        if(info.getType() == 0){
            // 其他周期性
            if(null != item.getChargeMethod() && info.getChargeMethod().equals(item.getChargeMethod())){
                msg += "修改了业务配置收费方式为：" +( 0 == item.getChargeMethod() ? "按年" : "按月")+"  ";
            }
            if(null != item.getServiceFee() && !item.getServiceFee().equals(info.getServiceFee())){
                msg += "修改了业务配置服务费为：" +item.getServiceFee()+"  ";
            }
        }
        else{
            // 一次性业务
            if(null != item.getIsCompany() && !item.getIsCompany().equals(info.getIsCompany())){
                msg += "修改了业务配置业务分类为："+(0 == item.getIsCompany() ? "公司" : "员工");
            }
        }
        if(!CommonUtil.isEmpty(msg)){
            if(BusinessEnum.shangYeXian.ordinal() == info.getBusinessId()){
                msg = "其他周期性服务 " + msg;
            }
            if(BusinessEnum.yiXiXingYeWu.ordinal() == info.getBusinessId()){
                msg = "一次性服务 " + msg;
            }
            Log log = new Log(user.getId(),StatusConstant.LOG_MODEL_BUSINESS,msg,StatusConstant.LOG_FLAG_UPDATE);
            logMapper.add(log);
        }
        if(null != item.getIsValid() && !item.getIsValid().equals(info.getIsValid())){
            // 删除
            Log log = new Log(user.getId(),StatusConstant.LOG_MODEL_BUSINESS,"删除业务:"+info.getItemName(),StatusConstant.LOG_FLAG_DEL);
            logMapper.add(log);
        }
    }


    @Transactional
    public void addBusiness(BusinessItem item,User user){
        businessItemMapper.addBusinessItem(item);
        String msg = "";
        if(BusinessEnum.shangYeXian.ordinal() == item.getBusinessId()){
            msg += "新增其他周期性服务下子业务 ";
        }
        if(BusinessEnum.yiXiXingYeWu.ordinal() == item.getBusinessId()){
            msg += "新增一次性服务下子业务 ";
        }
        Log log = new Log(user.getId(), StatusConstant.LOG_MODEL_BUSINESS,msg+item.getItemName(),StatusConstant.LOG_FLAG_ADD);
        logMapper.add(log);
    }


    public void updateBusiness(Business business){
        businessMapper.updateBusiness(business);
    }


    public void addBusiness(Business business){
        businessMapper.addBusiness(business);
    }

}
