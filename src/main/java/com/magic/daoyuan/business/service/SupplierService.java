package com.magic.daoyuan.business.service;

import com.magic.daoyuan.business.entity.*;
import com.magic.daoyuan.business.exception.InterfaceCommonException;
import com.magic.daoyuan.business.mapper.*;
import com.magic.daoyuan.business.util.CommonUtil;
import com.magic.daoyuan.business.util.StatusConstant;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 供应商实现
 * @author lzh
 * @create 2017/9/26 16:15
 */
@Service
public class SupplierService {

    @Resource
    private ISupplierMapper supplierMapper;
    @Resource
    private ICompanyServiceFeeMapper companyServiceFeeMapper;
    @Resource
    private IBusinessServiceFeeMapper businessServiceFeeMapper;
    @Resource
    private ICompanyServicePayPlaceMapper companyServicePayPlaceMapper;
    @Resource
    private ILogMapper logMapper;
    @Resource
    private ICityMapper cityMapper;

    /**
     * 新增
     * @param supplier
     */
    @Transactional
    public void save(Supplier supplier,String extentArr,String serviceFeeArr,User user) throws Exception {

        Supplier sql = supplierMapper.querySupplierByName(supplier.getSupplierName());
        if(null != sql){
            throw new InterfaceCommonException(StatusConstant.OBJECT_EXIST,"供应商名字已经存在");
        }

        supplierMapper.save(supplier);
        // 服务费配置业务处理 Start
        JSONArray businessFeeArr = JSONArray.fromObject(extentArr);
        List<CompanyServiceFee> feeList = new ArrayList<CompanyServiceFee>();
        List<BusinessServiceFee> serviceFeeList = new ArrayList<BusinessServiceFee>();
        for (Object o : businessFeeArr) {
            JSONObject jsonObject = JSONObject.fromObject(o);
            if(null != supplier.getServiceFeeConfigId() &&
                    3 != supplier.getServiceFeeConfigId()){
                CompanyServiceFee fee = new CompanyServiceFee();
                fee.setCompanyId(supplier.getId());
                fee.setType(1);
                fee.setExtent(jsonObject.getInt("extent"));
                fee.setPrice(jsonObject.getDouble("price"));
                if(null != jsonObject.get("businessId")){
                    fee.setBusinessId(jsonObject.getInt("businessId"));
                }
                feeList.add(fee);
            }else{
                BusinessServiceFee serviceFee = new BusinessServiceFee();
                serviceFee.setPrice(jsonObject.getDouble("price"));
                serviceFee.setBusinessIds(jsonObject.getString("businessIds"));
                serviceFee.setCompanyId(supplier.getId());
                serviceFee.setType(1);
                serviceFeeList.add(serviceFee);
            }
        }
        List<CompanyServicePayPlace> companyServicePayPlaceList = new ArrayList<CompanyServicePayPlace>();
        if(null != serviceFeeArr){
            JSONArray serviceFeeArrJSON = JSONArray.fromObject(serviceFeeArr);
            for (Object o : serviceFeeArrJSON) {
                JSONObject jsonObject = JSONObject.fromObject(o);
                CompanyServicePayPlace payPlace = new CompanyServicePayPlace();
                payPlace.setCityId(jsonObject.getInt("cityId"));
                payPlace.setCompanyId(supplier.getId());
                payPlace.setType(1);
                payPlace.setPrice(jsonObject.getDouble("price"));
                companyServicePayPlaceList.add(payPlace);
            }
        }
        if(companyServicePayPlaceList.size() > 0){
            companyServicePayPlaceMapper.batchAddCompanyServicePayPlace(companyServicePayPlaceList);
        }

        if(feeList.size() > 0){
            companyServiceFeeMapper.batchAddCompanyServiceFee(feeList);
        }
        if(serviceFeeList.size() > 0){
            // 批量新增
            businessServiceFeeMapper.batchAddBusinessServiceFee(serviceFeeList);
        }
        // 服务费配置业务处理 End

        // 日志
        logMapper.add(new Log(user.getId(),StatusConstant.LOG_MODEL_SUPPLIER,"新增供应商: "+ supplier.getSupplierName(),StatusConstant.LOG_FLAG_ADD));
    }

    /**
     * 更新不为空字段
     * @param supplier
     */
    @Transactional
    public void update(Supplier supplier,String extentArr,String serviceFeeArr,User user) throws Exception {

        Supplier info = supplierMapper.info(supplier.getId());
        if(null == info){
            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST,"供应商不存在");
        }
        Supplier sql = supplierMapper.querySupplierByName(supplier.getSupplierName());
        if(null != sql && !info.getSupplierName().equals(supplier.getSupplierName())){
            throw new InterfaceCommonException(StatusConstant.OBJECT_EXIST,"供应商名字已经存在");
        }
        supplierMapper.update(supplier);
        // 服务费配置业务处理 Start
        JSONArray businessFeeArr = JSONArray.fromObject(extentArr);
        List<CompanyServiceFee> feeList = new ArrayList<CompanyServiceFee>();
        List<BusinessServiceFee> serviceFeeList = new ArrayList<BusinessServiceFee>();
        for (Object o : businessFeeArr) {
            JSONObject jsonObject = JSONObject.fromObject(o);
            if(null != supplier.getServiceFeeConfigId() &&
                    3 != supplier.getServiceFeeConfigId()){
                CompanyServiceFee fee = new CompanyServiceFee();
                fee.setCompanyId(supplier.getId());
                fee.setType(1);
                fee.setExtent(jsonObject.getInt("extent"));
                fee.setPrice(jsonObject.getDouble("price"));
                if(null != jsonObject.get("businessId")){
                    fee.setBusinessId(jsonObject.getInt("businessId"));
                }
                feeList.add(fee);
            }else{
                BusinessServiceFee serviceFee = new BusinessServiceFee();
                serviceFee.setPrice(jsonObject.getDouble("price"));
                serviceFee.setBusinessIds(jsonObject.getString("businessIds"));
                serviceFee.setCompanyId(supplier.getId());
                serviceFee.setType(1);
                serviceFeeList.add(serviceFee);
            }
        }
        List<CompanyServicePayPlace> companyServicePayPlaceList = new ArrayList<CompanyServicePayPlace>();
        if(null != serviceFeeArr){
            JSONArray serviceFeeArrJSON = JSONArray.fromObject(serviceFeeArr);
            for (Object o : serviceFeeArrJSON) {
                JSONObject jsonObject = JSONObject.fromObject(o);
                CompanyServicePayPlace payPlace = new CompanyServicePayPlace();
                payPlace.setCityId(jsonObject.getInt("cityId"));
                payPlace.setCompanyId(supplier.getId());
                payPlace.setType(1);
                payPlace.setPrice(jsonObject.getDouble("price"));
                companyServicePayPlaceList.add(payPlace);
            }
        }

        // 日志 开始
        String msg = "";
        if(info.getServiceFeeStartTime().getTime() != supplier.getServiceFeeStartTime().getTime()){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            msg += "修改了成本价月序开始时间为："+sdf.format(supplier.getServiceFeeStartTime());
        }
        if(!info.getServiceFeeCycle().equals(supplier.getServiceFeeCycle())){
            msg += "修改了成本价月序周期为："+supplier.getServiceFeeCycle();
        }

        if(!info.getPercent().equals(supplier.getPercent()) ||
                !info.getIsPercent().equals(supplier.getIsPercent())){
            msg += "修改了纳入百分比 ： "+supplier.getPercent();
        }
        // 组装成本价配置

        String configMsg = "";
        if(supplier.getServiceFeeConfigId() == 1 || supplier.getServiceFeeConfigId() == 5
                || supplier.getServiceFeeConfigId() == 6){
            if(feeList.size() > 0){
                CompanyServiceFee companyServiceFee = feeList.get(0);
                configMsg += CommonUtil.getServiceConfigName(supplier.getServiceFeeConfigId()) + ": " + companyServiceFee.getPrice();
            }
        }
        if(supplier.getServiceFeeConfigId() == 2 || supplier.getServiceFeeConfigId() == 7){
            // 阶梯式
            String configName = CommonUtil.getServiceConfigName(supplier.getServiceFeeConfigId());
            StringBuffer sb = new StringBuffer(configName+" :");
            if(feeList.size() > 0){
                for (int i = 0; i < feeList.size(); i++) {
                    if(i == 0){
                        sb.append("0-"+feeList.get(i).getExtent()+" :"+feeList.get(i).getPrice());
                    }
                    else{
                        sb.append(feeList.get(i - 1).getExtent() +"-"+feeList.get(i).getExtent()+" :"+feeList.get(i).getPrice());
                    }
                }
            }
            configMsg += sb.toString();
        }
        if(supplier.getServiceFeeConfigId() == 3){
            // 服务类别
            if(serviceFeeList.size() > 0){
                String configName = CommonUtil.getServiceConfigName(supplier.getServiceFeeConfigId());
                StringBuffer sb = new StringBuffer(configName+" :");
                for (BusinessServiceFee serviceFee : serviceFeeList) {
                    String[] split = serviceFee.getBusinessIds().split(",");
                    String business = "";
                    for (int i = 0; i < split.length; i++) {
                        business += CommonUtil.getBusinessName(Integer.valueOf(split[i]))+"+";
                    }
                    sb.append(business.substring(0,business.length() - 1) +" :"+serviceFee.getPrice());
                }
                configMsg += sb.toString();
            }
        }
        if(supplier.getServiceFeeConfigId() == 4){
            // 服务区域
            if(companyServicePayPlaceList.size() > 0){
                String configName = CommonUtil.getServiceConfigName(supplier.getServiceFeeConfigId());
                StringBuffer sb = new StringBuffer(configName+" :");
                List<Integer> cityIds = new ArrayList<Integer>();
                for (CompanyServicePayPlace payPlace : companyServicePayPlaceList) {
                    cityIds.add(payPlace.getCityId());
                }
                // 获取城市名称
                List<City> cities = cityMapper.queryCityByIds(cityIds);
                for (CompanyServicePayPlace payPlace : companyServicePayPlaceList) {
                    String cityName = "";
                    for (City city : cities) {
                        if(city.getId().equals(payPlace.getCityId())){
                            cityName = city.getName();
                            break;
                        }
                    }
                    sb.append(cityName + " :" + payPlace.getPrice());
                }
                configMsg += sb.toString();
            }
        }
        msg += "成本价配置为："+ configMsg;

        logMapper.add(new Log(user.getId(),StatusConstant.LOG_MODEL_SUPPLIER,msg,StatusConstant.LOG_FLAG_UPDATE));
        // 日志 结束
        companyServicePayPlaceMapper.delCompanyServicePayPlace(supplier.getId(),1);
        if(companyServicePayPlaceList.size() > 0){
            companyServicePayPlaceMapper.batchAddCompanyServicePayPlace(companyServicePayPlaceList);
        }
        companyServiceFeeMapper.delServiceFee(supplier.getId(),1);
        businessServiceFeeMapper.delBusinessServiceFee(supplier.getId(),1);
        if(feeList.size() > 0){
            companyServiceFeeMapper.batchAddCompanyServiceFee(feeList);
        }
        if(serviceFeeList.size() > 0){
            // 批量新增
            businessServiceFeeMapper.batchAddBusinessServiceFee(serviceFeeList);
        }
        // 服务费配置业务处理 End
    }

    /**
     * 更新所有字段
     * @param supplier
     */
    public void updateAll(Supplier supplier) {
        supplierMapper.updateAll(supplier);
    }

    /**
     * 详情
     * @param id
     * @return
     */
    public Supplier info(Integer id) {
        return supplierMapper.info(id);
    }


    /**
     * 后台页面 分页获取供应商
     *
     * @param pageArgs    分页属性
     * @param supplierName     供应商名
     * @param drawABillOrder   出票顺序 0：先票  1：先款
     * @return
     */
    public PageList<Supplier> list(PageArgs pageArgs , String supplierName , Integer drawABillOrder) {
        PageList<Supplier> pageList = new PageList<Supplier>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("supplierName", supplierName);
        map.put("drawABillOrder", drawABillOrder);
        int count = supplierMapper.listCount(map);
        if (count > 0) {
            map.put("pageArgs", pageArgs);
            pageList.setList(supplierMapper.list(map));
        }
        pageList.setTotalSize(count);
        return pageList;
    }


    /**
     * 获取未被此经办机构绑定的供应商
     * @param organizationId 经办机构Id
     * @return
     */
    public List<Supplier> getNOBindToSelect(Integer organizationId,Integer supplierId){
        return supplierMapper.getNOBindToSelect(organizationId,supplierId);
    }

    /**
     * 下拉使用--全部供应商
     * @return
     */
    public List<Supplier> getAllList(){
        return supplierMapper.getAllList();
    }

}
