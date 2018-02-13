package com.magic.daoyuan.business.service;

import com.magic.daoyuan.business.entity.*;
import com.magic.daoyuan.business.enums.BillType;
import com.magic.daoyuan.business.enums.Common;
import com.magic.daoyuan.business.enums.TimeField;
import com.magic.daoyuan.business.exception.InterfaceCommonException;
import com.magic.daoyuan.business.mapper.*;
import com.magic.daoyuan.business.util.CommonUtil;
import com.magic.daoyuan.business.util.DateUtil;
import com.magic.daoyuan.business.util.StatusConstant;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Eric Xie on 2017/9/13 0013.
 */
@Service
public class CompanyService {

    @Resource
    private ICompanyMapper companyMapper;
    @Resource
    private ICompanyBusinessMapper companyBusinessMapper;
    @Resource
    private ICompanyBillInfoMapper companyBillInfoMapper;
    @Resource
    private ICompanyServiceFeeMapper companyServiceFeeMapper;
    @Resource
    private ISalaryDateMapper salaryDateMapper;
    @Resource
    private IBusinessMethodMapper businessMethodMapper;
    @Resource
    private ICompanyBusinessItemMapper companyBusinessItemMapper;
    @Resource
    private IBusinessServiceFeeMapper businessServiceFeeMapper;
    @Resource
    private ICompanyServicePayPlaceMapper companyServicePayPlaceMapper;
    @Resource
    private ICompanyCooperationMethodMapper companyCooperationMethodMapper;
    @Resource
    private ICompanyCooperationBusinessServiceFeeMapper companyCooperationBusinessServiceFeeMapper;
    @Resource
    private ICompanyCooperationServiceFeeMapper companyCooperationServiceFeeMapper;
    @Resource
    private ICompanyCooperationServicePayPlaceMapper companyCooperationServicePayPlaceMapper;
    @Resource
    private IBusinessMapper businessMapper;
    @Resource
    private IMemberBusinessMapper memberBusinessMapper;
    @Resource
    private IContactsMapper contactsMapper;
    @Resource
    private ICompanyCooperationMapper companyCooperationMapper;
    @Resource
    private IBacklogMapper backlogMapper;
    @Resource
    private ILogMapper logMapper;
    @Resource
    private ICityMapper cityMapper;

    public PageList<Company> queryOtherCompany(String companyName,PageArgs pageArgs,Date currentDate){
        List<Company> dataList = new ArrayList<Company>();
        int count = companyMapper.countOtherCompany(companyName);
        currentDate = DateUtil.getDateByConfig(currentDate);
        if(count > 0){
            dataList = companyMapper.queryOtherCompany(companyName,pageArgs.getPageStart(),pageArgs.getPageSize());
            if(null != dataList && dataList.size() > 0){
                for (Company company : dataList) {
                    Date target = DateUtil.getDateByConfig(company.getBusinessStartTime());
                    List<Date> dateList = new ArrayList<Date>();
                    // 循环设置时间
                    Date start = DateUtil.getDateByConfig(company.getBusinessStartTime());
                    for (int i = 0; i < 6; i++) {
                        if(currentDate.getTime() > start.getTime() ){
                            Date tempStart = start;
                            while (true){
                                Date temp = DateUtil.addDate(tempStart, TimeField.Month.ordinal(),company.getBusinessCycle());
                                temp = DateUtil.getDateByConfig(temp);
                                if(currentDate.getTime() <= temp.getTime()){
                                    start = temp;
                                    break;
                                }
                                tempStart = temp;
                            }
                        }
                        if(i == 0){
                            dateList.add(start);
                        }
                        if(i > 0){
                            dateList.add(start);
                        }
                        start = DateUtil.addDate(start, TimeField.Month.ordinal(),company.getBusinessCycle());
                    }
                    company.setDateList(dateList);
                }
            }
        }
        return new PageList<Company>(dataList,count);
    }


    public List<CompanyCooperationMethod> queryBaseCooperationMethod(Integer companyId){
        return companyCooperationMethodMapper.queryBaseCooperationMethod(companyId);
    }


    /**
     * 查询所有公司部分字段
     * @return
     */
    public List<Company> queryAllCompany(){
        return companyMapper.queryAllCompany();
    }

    /**
     * 获取公司的票据信息
     * @param companyId 公司
     * @return
     */
    public List<CompanyBillInfo> queryBillInfo(Integer companyId){
        return companyBillInfoMapper.queryBillInfo(companyId);
    }

    /**
     * 动态分页获取
     * @param params
     * @param pageArgs
     * @return
     */
    public PageList<Company> queryCompanyByItems(Map<String,Object> params, PageArgs pageArgs){
        List<Company> dataList = new ArrayList<Company>();
        int count = companyMapper.countCompanyByItems(params);
        if(count > 0){
            params.put("limit",pageArgs.getPageStart());
            params.put("limitSize",pageArgs.getPageSize());
            dataList = companyMapper.queryCompanyByItems(params);
            for (Company company : dataList) {
                if(null != company.getContractList() && company.getContractList().size() > 0) {
                    company.setContractStartTime(company.getContractList().get(0).getStartTime());
                }
                isBillServiceMonth(company);
                // 设置是否显示公积金 和 社保 配置
                if(null != company.getBusinesses() && company.getBusinesses().size() > 0){
                    for (Business business : company.getBusinesses()) {
                        if(business.getId() == 3 && null != business.getBusinessMethod()){
                            // 社保
                            if(null != business.getBusinessMethod().getTuoGuan()){
                                company.setIsShowSocialSecurity(1);
                            }
                        }
                        else if(business.getId() == 4 && null != business.getBusinessMethod()){
                            // 公积金
                            if(null != business.getBusinessMethod().getTuoGuan()){
                                company.setIsShowAccumulationFund(1);
                            }
                        }
                    }
                }
            }
        }
        return new PageList<Company>(dataList,count);
    }

    /**
     * 动态分页获取
     * @param params
     * @param pageArgs
     * @return
     */
    public PageList<Company> queryCompanyByItems2(Map<String,Object> params, PageArgs pageArgs){
        List<Company> dataList = new ArrayList<Company>();
        int count = companyMapper.countCompanyByItems(params);
        if(count > 0){
            params.put("limit",pageArgs.getPageStart());
            params.put("limitSize",pageArgs.getPageSize());
            dataList = companyMapper.queryCompanyByItems(params);
        }
        return new PageList<Company>(dataList,count);
    }

    /**
     * 是否可以到了生成账单月
     * @param company
     */
    public void isBillServiceMonth(Company company) {
        // 计算是否到 生成账单月份
        if(null != company.getBusinessStartTime() && null != company.getBusinessCycle()){
            final int cycle = company.getBusinessCycle(); // 月序周期
            Date now = DateUtil.setDate(new Date());
            Date serviceDate = company.getBusinessStartTime();
            int i = 0;
            while (true){
                serviceDate = DateUtil.addDate(serviceDate, TimeField.Month.ordinal(), i);
                serviceDate = DateUtil.setDate(serviceDate);
                if(now.getTime() == serviceDate.getTime()){
                    company.setIsShowBuildBtn(1);
                    break;
                }
                if(serviceDate.getTime() > now.getTime()){
                    company.setIsShowBuildBtn(0);
                    break;
                }
                i += cycle;
            }
        }
    }


    /**
     * 是否可以到了生成服务费月份
     * @param company
     */
    public void isServiceFeeMonth(Company company) {
        // 计算是否到 生成服务费月份
        if(null != company.getServiceFeeStartTime() && null != company.getServiceFeeCycle()){
            final int cycle = company.getServiceFeeCycle(); // 服务月序周期
            Date now = DateUtil.setDate(new Date());
            Date serviceDate = company.getServiceFeeStartTime();
            int i = 0;
            while (true){
                serviceDate = DateUtil.addDate(serviceDate, TimeField.Month.ordinal(), i);
                serviceDate = DateUtil.setDate(serviceDate);
                if(now.getTime() == serviceDate.getTime()){
                    company.setIsCanServceFree(1);
                    break;
                }
                if(serviceDate.getTime() > now.getTime()){
                    company.setIsCanServceFree(0);
                    break;
                }
                i += cycle;
            }
        }
    }

    public Company queryCompanyById(Integer id){
        return companyMapper.queryCompanyById(id);
    }


    public Company info(Integer id){
        return companyMapper.info(id);
    }

    @Transactional
    public void updateCompany(Company company,String businessArr,String extentArr,String salaryDateArr
            ,String serviceFeeArr,String cooperationMethodArr,User user)throws Exception{

        StringBuffer sb = new StringBuffer(); // 修改记录

        Company info = companyMapper.queryCompanyById(company.getId());
        Company sqlCompany = companyMapper.queryCompanyByName(company.getCompanyName());
        if(null != sqlCompany && !info.getCompanyName().equals(company.getCompanyName())){
            throw new InterfaceCommonException(StatusConstant.OBJECT_EXIST,"公司名已经存在");
        }


        companyMapper.updateCompany(company);

        // 公司合作状态记录 开始

        if(!info.getCooperationStatus().equals(company.getCooperationStatus())){
            // 如果状态发生变更
            // 获取当天发生状态发生状态发生变更的记录，删除，新增最新的数据
            companyCooperationMapper.del(company.getId(),new Date());
            CompanyCooperation companyCooperation = new CompanyCooperation();
            companyCooperation.setCooperationStatus(company.getCooperationStatus());
            companyCooperation.setCompanyId(company.getId());
            companyCooperation.setIsPeer(company.getIsPeer());
            companyCooperation.setIsInitiative(0);
            companyCooperationMapper.add(companyCooperation);
        }

        if(!info.getIsPeer().equals(company.getIsPeer())){
            CompanyCooperation info1 = companyCooperationMapper.info(company.getId(), new Date());
            if(null != info1){
                info1.setIsPeer(company.getIsPeer());
                companyCooperationMapper.update(info1);
            }else{
                CompanyCooperation companyCooperation = new CompanyCooperation();
                companyCooperation.setCooperationStatus(company.getCooperationStatus());
                companyCooperation.setCompanyId(company.getId());
                companyCooperation.setIsPeer(company.getIsPeer());
                companyCooperation.setIsInitiative(Common.NO.ordinal());
                companyCooperationMapper.add(companyCooperation);
            }
        }

        // 公司合作状态记录 结束

        // 合作方式业务封装  开始
        List<CompanyCooperationMethod> methodList = new ArrayList<CompanyCooperationMethod>();
        if(!CommonUtil.isEmpty(cooperationMethodArr)){
            JSONArray cooperationJsonArray = JSONArray.fromObject(cooperationMethodArr);
            for (Object o : cooperationJsonArray) {
                JSONObject jsonObject = JSONObject.fromObject(o);
                CompanyCooperationMethod method = new CompanyCooperationMethod();
                method.setCompanyId(company.getId());
                method.setCooperationMethodId(jsonObject.getInt("cooperationMethodId"));
                method.setServiceFeeConfigId(jsonObject.getInt("serviceFeeConfigId"));
                method.setServiceFeeStartTime(new Date(jsonObject.getLong("serviceFeeStartTime")));
                method.setServiceFeeCycle(jsonObject.getInt("serviceFeeCycle"));
                if(method.getServiceFeeConfigId() == 5){
                    method.setBaseFee(jsonObject.getDouble("baseFee"));
                }
                if (null != jsonObject.get("serviceFeeMin") && !"".equals(jsonObject.getString("serviceFeeMin")) ) {
                    method.setServiceFeeMin(jsonObject.getDouble("serviceFeeMin"));
                }
                if (null != jsonObject.get("serviceFeeMax") && !"".equals(jsonObject.getString("serviceFeeMax")) ) {
                    method.setServiceFeeMax(jsonObject.getDouble("serviceFeeMax"));
                }
                if (null != jsonObject.get("isPercent") && !"".equals(jsonObject.getString("isPercent"))) {
                    method.setIsPercent(jsonObject.getInt("isPercent"));
                }
                if (null != jsonObject.get("percent") && !"".equals(jsonObject.getString("percent"))) {
                    method.setPercent(jsonObject.getDouble("percent"));
                }
                if(method.getServiceFeeConfigId() == 1 || method.getServiceFeeConfigId() == 2
                        || method.getServiceFeeConfigId() == 5 || method.getServiceFeeConfigId() == 6
                        || method.getServiceFeeConfigId() == 7){

                    List<CompanyCooperationServiceFee> feeList = new ArrayList<CompanyCooperationServiceFee>();
                    String serviceFeeList = jsonObject.getString("serviceFeeList");
                    if(!CommonUtil.isEmpty(serviceFeeList)){
                        JSONArray jsonArray = JSONArray.fromObject(serviceFeeList);
                        for (Object o1 : jsonArray) {
                            JSONObject temp = JSONObject.fromObject(o1);
                            CompanyCooperationServiceFee fee = new CompanyCooperationServiceFee();
                            fee.setExtent(temp.getInt("extent"));
                            fee.setPrice(temp.getDouble("price"));
                            feeList.add(fee);
                        }
                    }
                    method.setServiceFeeList(feeList);
                }else if(method.getServiceFeeConfigId() == 3){
                    List<CompanyCooperationBusinessServiceFee> serviceFeeList = new ArrayList<CompanyCooperationBusinessServiceFee>();
                    String tempServiceFeeArr = jsonObject.getString("businessServiceFeeList");
                    if(!CommonUtil.isEmpty(tempServiceFeeArr)){
                        JSONArray jsonArray = JSONArray.fromObject(tempServiceFeeArr);
                        for (Object o1 : jsonArray) {
                            JSONObject temp = JSONObject.fromObject(o1);
                            CompanyCooperationBusinessServiceFee fee = new CompanyCooperationBusinessServiceFee();
                            fee.setPrice(temp.getDouble("price"));
                            fee.setBusinessIds(temp.getString("businessIds"));
                            serviceFeeList.add(fee);
                        }
                    }
                    method.setBusinessServiceFeeList(serviceFeeList);
                }else if(method.getServiceFeeConfigId() == 4){
                    List<CompanyCooperationServicePayPlace> serviceFeeList = new ArrayList<CompanyCooperationServicePayPlace>();
                    String tempServiceFeeArr = jsonObject.getString("payPlaceList");
                    if(!CommonUtil.isEmpty(tempServiceFeeArr)){
                        JSONArray jsonArray = JSONArray.fromObject(tempServiceFeeArr);
                        for (Object o1 : jsonArray) {
                            JSONObject temp = JSONObject.fromObject(o1);
                            CompanyCooperationServicePayPlace fee = new CompanyCooperationServicePayPlace();
                            fee.setPrice(temp.getDouble("price"));
                            fee.setCityId(temp.getInt("cityId"));
                            serviceFeeList.add(fee);
                        }
                    }
                    method.setPayPlaceList(serviceFeeList);
                }
                methodList.add(method);
            }
        }

        // 对比合作方式，产生日志

        // 对比客户名称、合作状态、到款日 是否修改
        sb.append("公司名："+company.getCompanyName());
        sb.append("、合作状态："+ (0 == company.getCooperationStatus() ? "空户/终止" : "合作"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sb.append("、到款日："+sdf.format(company.getPayTime()));

        for (CompanyCooperationMethod method : methodList) {
            String methodName = "";
            switch (method.getCooperationMethodId()){
                case 0 : methodName = "普通";break;
                case 1 : methodName = "派遣";break;
                case 2 : methodName = "外包";break;
            }
            // 合作方式相同,判断其他
            sb.append("、 合作方式"+methodName+"的服务月序开始时间为："+ sdf.format(method.getServiceFeeStartTime()));
            sb.append("、 合作方式"+methodName+"的服务月序周期为："+ method.getServiceFeeCycle());
            String configMsg = "";
            if(method.getServiceFeeConfigId() == 1 || method.getServiceFeeConfigId() == 5
                    || method.getServiceFeeConfigId() == 6){
                if(method.getServiceFeeList().size() > 0){
                    CompanyCooperationServiceFee companyServiceFee = method.getServiceFeeList().get(0);
                    configMsg += CommonUtil.getServiceConfigName(method.getServiceFeeConfigId()) + ": " + companyServiceFee.getPrice();
                }
            }
            if(method.getServiceFeeConfigId() == 2 || method.getServiceFeeConfigId() == 7){
                // 阶梯式
                String configName = CommonUtil.getServiceConfigName(method.getServiceFeeConfigId());
                StringBuffer sb_ = new StringBuffer(configName+" :");
                if(method.getServiceFeeList().size() > 0){
                    for (int i = 0; i < method.getServiceFeeList().size(); i++) {
                        if(i == 0){
                            sb_.append("0-"+method.getServiceFeeList().get(i).getExtent()+" :"+method.getServiceFeeList().get(i).getPrice()+"、");
                        }
                        else{
                            sb_.append(method.getServiceFeeList().get(i - 1).getExtent() +"-"+method.getServiceFeeList().get(i).getExtent()+" :"+method.getServiceFeeList().get(i).getPrice()+"、");
                        }
                    }
                }
                configMsg += sb_.toString();
            }
            if(method.getServiceFeeConfigId() == 3){
                // 服务类别
                if(method.getBusinessServiceFeeList().size() > 0){
                    String configName = CommonUtil.getServiceConfigName(method.getServiceFeeConfigId());
                    StringBuffer sb_ = new StringBuffer(configName+" :");
                    for (CompanyCooperationBusinessServiceFee serviceFee : method.getBusinessServiceFeeList()) {
                        String[] split = serviceFee.getBusinessIds().split(",");
                        String business = "";
                        for (int i = 0; i < split.length; i++) {
                            business += CommonUtil.getBusinessName(Integer.valueOf(split[i]))+"+";
                        }
                        sb_.append(business.substring(0,business.length() - 1) +" :"+serviceFee.getPrice());
                    }
                    configMsg += sb_.toString();
                }
            }
            if(method.getServiceFeeConfigId() == 4){
                // 服务区域
                if(method.getPayPlaceList().size() > 0){
                    String configName = CommonUtil.getServiceConfigName(method.getServiceFeeConfigId());
                    StringBuffer sb_ = new StringBuffer(configName+" :");
                    List<Integer> cityIds = new ArrayList<Integer>();
                    for (CompanyCooperationServicePayPlace payPlace : method.getPayPlaceList()) {
                        cityIds.add(payPlace.getCityId());
                    }
                    // 获取城市名称
                    List<City> cities = cityMapper.queryCityByIds(cityIds);
                    for (CompanyCooperationServicePayPlace payPlace : method.getPayPlaceList()) {
                        String cityName = "";
                        for (City city : cities) {
                            if(city.getId().equals(payPlace.getCityId())){
                                cityName = city.getName();
                                break;
                            }
                        }
                        sb_.append(cityName + " :" + payPlace.getPrice());
                    }
                    configMsg += sb_.toString();
                }
            }

            sb.append("、 合作方式"+methodName+"的服务费配置为："+configMsg);

            // 服务费区间
            sb.append("、 合作方式"+methodName+"的服务费区间为："+ (null == method.getServiceFeeMin() ? 0 : method.getServiceFeeMin()) +"-"
                    + (null == method.getServiceFeeMax() ? "" : method.getServiceFeeMax()));

            // 缴纳百分比
            sb.append("、 合作方式"+methodName+"的纳入百分比为："+ method.getPercent());
        }


        // 记录日志
        Log log = new Log(user.getId(),StatusConstant.LOG_MODEL_COMPANY,sb.toString(),StatusConstant.LOG_FLAG_UPDATE);
        logMapper.add(log);
        // 删除之前数据
        List<CompanyCooperationMethod> methods = companyCooperationMethodMapper.queryCompanyCooperationMethod(company.getId());
        if(null != methods && methods.size() > 0){
            for (CompanyCooperationMethod method : methods) {
                companyCooperationBusinessServiceFeeMapper.del(method.getId());
                companyCooperationServicePayPlaceMapper.del(method.getId());
                companyCooperationServiceFeeMapper.del(method.getId());
            }
            companyCooperationMethodMapper.del(company.getId());
        }
        if(methodList.size() > 0){
            companyCooperationMethodMapper.batchAdd(methodList);
            for (CompanyCooperationMethod method : methodList) {
                if(null != method.getBusinessServiceFeeList() && method.getBusinessServiceFeeList().size() > 0){
                    for (CompanyCooperationBusinessServiceFee fee : method.getBusinessServiceFeeList()) {
                        fee.setCompanyCooperationMethodId(method.getId());
                    }
                    companyCooperationBusinessServiceFeeMapper.batchAdd(method.getBusinessServiceFeeList());
                }
                if(null != method.getPayPlaceList() && method.getPayPlaceList().size() > 0){
                    for (CompanyCooperationServicePayPlace place : method.getPayPlaceList()) {
                        place.setCompanyCooperationMethodId(method.getId());
                    }
                    companyCooperationServicePayPlaceMapper.batchAdd(method.getPayPlaceList());
                }
                if(null != method.getServiceFeeList() && method.getServiceFeeList().size() > 0){
                    for (CompanyCooperationServiceFee fee : method.getServiceFeeList()) {
                        fee.setCompanyCooperationMethodId(method.getId());
                    }
                    companyCooperationServiceFeeMapper.batchAdd(method.getServiceFeeList());
                }
            }
        }

        // 合作方式业务封装  结束

        // 封装业务模块 Start
        JSONArray businessArray = JSONArray.fromObject(businessArr);
        List<CompanyBusiness> businessList = new ArrayList<CompanyBusiness>();
        for (Object obj : businessArray) {
            // 中间表对象
            JSONObject buss = JSONObject.fromObject(obj);
            int businessId = buss.getInt("businessId");
            CompanyBusiness c = new CompanyBusiness();
            c.setBusinessId(buss.getInt("businessId"));
            c.setCompanyId(company.getId());
            if(businessId == 3 || businessId == 4){
                // 如果是公积金、社保
                BusinessMethod businessMethod = new BusinessMethod();
                if(null != buss.get("daili")){
                    Integer daili = buss.getInt("daili");// 代理选择的账单制作方式
                    businessMethod.setDaiLi(daili);
                }
                if(null != buss.get("tuoguan")){
                    Integer tuoguan = buss.getInt("tuoguan");// 托管选择的账单制作方式
                    businessMethod.setTuoGuan(tuoguan);
                }
                c.setBusinessMethods(businessMethod);
            }
            else if(businessId == 6 || businessId == 7){
                // 如果是商业保险、一次性业务
                String arr = buss.getString("arr");
                if(arr.indexOf("[") < 0){
                    arr = "["+arr+"]";
                }
                JSONArray busItemArr = JSONArray.fromObject(arr); // 商业保险、一次性业务 下选择的子类业务集合
                List<CompanyBusinessItem> companyBusinessItems = new ArrayList<CompanyBusinessItem>();
                for (Object o : busItemArr) {
                    JSONArray tempArr = JSONArray.fromObject(o);
                    for (Object o1 : tempArr){
                        JSONObject temp = JSONObject.fromObject(o1);
                        CompanyBusinessItem companyBusinessItem = new CompanyBusinessItem();
                        if(businessId == 7 && temp.getInt("isCompany") == 0){
                            // 如果是公司的一次性险业务
                            companyBusinessItem.setSonBillId(temp.getInt("sonBillId"));
                        }
                        companyBusinessItem.setBusinessItemId(temp.getInt("businessItemId"));
                        companyBusinessItem.setPrice(temp.getDouble("price"));
                        companyBusinessItems.add(companyBusinessItem);
                    }
                }
                c.setCompanyBusinessItems(companyBusinessItems);
            }
            businessList.add(c);
        }


        // 验证公司准备删除的业务，在公司名下的员工是否还存在业务，如果员工存在了公司正在删除的业务，则不允许删除该业务
        List<Business> preBusinessList = businessMapper.queryBusiness(company.getId()); // 更新之前的业务
        // 对比删除的业务有哪些
        if(null != preBusinessList && preBusinessList.size() > 0){
            List<Integer> delBusinessIds = new ArrayList<Integer>();
            for (Business pre : preBusinessList) {
                boolean isExist = false;
                for (CompanyBusiness business : businessList) {
                    if(business.getBusinessId().equals(pre.getId())){
                        isExist = true;
                    }
                }
                if (!isExist){
                    // 如果当前业务被删除
                    delBusinessIds.add(pre.getId());
                }
            }
            if(delBusinessIds.size() > 0){
                // 查询该公司下是否有员工存在这些业务，如果存在，则不能删除这些业务
                int i = memberBusinessMapper.statisticsMemberBusiness(company.getId(), delBusinessIds);
                if(i > 0){
                    throw new InterfaceCommonException(StatusConstant.Fail_CODE,"该公司下有员工拥有此业务，不能删除业务");
                }
            }

        }

        companyBusinessMapper.delCompanyBusiness(company.getId());
        if(businessList.size() > 0){
            for (CompanyBusiness companyBusiness : businessList) {
                companyBusinessMapper.addCompanyBusiness(companyBusiness);
            }
            // 处理子集业务
            List<BusinessMethod> businessMethods = new ArrayList<BusinessMethod>();
            List<CompanyBusinessItem> companyBusinessItems = new ArrayList<CompanyBusinessItem>();
            List<Integer> companyBusinessIds  = new ArrayList<Integer>();
            for (CompanyBusiness companyBusiness : businessList) {
                companyBusinessIds.add(companyBusiness.getId());
                if(null != companyBusiness.getBusinessMethods()){
                    companyBusiness.getBusinessMethods().setCompanyBusinessId(companyBusiness.getId());
                    businessMethods.add(companyBusiness.getBusinessMethods());
                }
                if(null != companyBusiness.getCompanyBusinessItems() && companyBusiness.getCompanyBusinessItems().size() > 0){
                    for (CompanyBusinessItem companyBusinessItem : companyBusiness.getCompanyBusinessItems()) {
                        companyBusinessItem.setBusinessCompanyId(companyBusiness.getId());
                    }
                    companyBusinessItems.addAll(companyBusiness.getCompanyBusinessItems());
                }
            }
            if(companyBusinessIds.size() > 0){
                businessMethodMapper.delBusinessMethod(companyBusinessIds);
                companyBusinessItemMapper.delCompanyBusinessItem(companyBusinessIds);
            }

            if(businessMethods.size() > 0){
                businessMethodMapper.batchAddBusinessMethod(businessMethods);
            }
            if(companyBusinessItems.size() > 0){
                companyBusinessItemMapper.batchAddCompanyBusinessItem(companyBusinessItems);
            }

        }
        // 封装业务模块 End
        // 工资发放日期 业务处理 Start
        JSONArray jsonArray = JSONArray.fromObject(salaryDateArr);
        List<SalaryDate> salaryDateList = new ArrayList<SalaryDate>();
        for (Object o : jsonArray) {
            SalaryDate salaryDate = new SalaryDate();
            salaryDate.setCompanyId(company.getId());
            salaryDate.setGrantDate(new Date(Long.valueOf(o.toString())));
            salaryDateList.add(salaryDate);
        }
        salaryDateMapper.delSalaryDate(company.getId());
        if(salaryDateList.size() > 0){
            salaryDateMapper.batchAddSalaryDate(salaryDateList);
        }
        // 工资发放日期 业务处理 End

    }

    /**
     *
     * @param company
     * @param businessArr
     * @param extentArr
     * @param salaryDateArr
     * @param serviceFeeArr
     * @param cooperationMethodArr
     * @throws Exception
     */
    @Transactional
    public void addCompany(Company company,String businessArr,String extentArr,String salaryDateArr,
                           String serviceFeeArr,String cooperationMethodArr,User currentUser) throws Exception{
        Company sqlCompany = companyMapper.queryCompanyByName(company.getCompanyName());
        if(null != sqlCompany){
            throw new InterfaceCommonException(StatusConstant.OBJECT_EXIST,"公司名已经存在");
        }
        // 获取当月新增的公司数
        int count = companyMapper.countCompanyByMonth(new Date());
        company.setSerialNumber(CommonUtil.buildSerialNumber(count));
        companyMapper.addCompany(company);
        // 更新编号
//        Company company1 = new Company();
//        company1.setId(company.getId());
//        companyMapper.updateCompany(company1);

        // 新增公司合作状态记录 开始
        CompanyCooperation companyCooperation = new CompanyCooperation();
        companyCooperation.setCompanyId(company.getId());
        companyCooperation.setCooperationStatus(company.getCooperationStatus());
        companyCooperation.setIsPeer(company.getIsPeer());
        companyCooperation.setIsInitiative(Common.NO.ordinal());
        companyCooperationMapper.add(companyCooperation);
        // 新增公司合作状态记录 结束



        // 合作方式业务封装  开始
        List<CompanyCooperationMethod> methodList = new ArrayList<CompanyCooperationMethod>();
        if(!CommonUtil.isEmpty(cooperationMethodArr)){
            JSONArray cooperationJsonArray = JSONArray.fromObject(cooperationMethodArr);
            for (Object o : cooperationJsonArray) {
                JSONObject jsonObject = JSONObject.fromObject(o);
                CompanyCooperationMethod method = new CompanyCooperationMethod();
                method.setCompanyId(company.getId());
                method.setCooperationMethodId(jsonObject.getInt("cooperationMethodId"));
                method.setServiceFeeConfigId(jsonObject.getInt("serviceFeeConfigId"));
                method.setServiceFeeStartTime(new Date(jsonObject.getLong("serviceFeeStartTime")));
                method.setServiceFeeCycle(jsonObject.getInt("serviceFeeCycle"));
                if(method.getServiceFeeConfigId() == 5){
                    method.setBaseFee(jsonObject.getDouble("baseFee"));
                }
                if (null != jsonObject.get("serviceFeeMin") && !"".equals(jsonObject.getString("serviceFeeMin")) ) {
                    method.setServiceFeeMin(jsonObject.getDouble("serviceFeeMin"));
                }
                if (null != jsonObject.get("serviceFeeMax") && !"".equals(jsonObject.getString("serviceFeeMax")) ) {
                    method.setServiceFeeMax(jsonObject.getDouble("serviceFeeMax"));
                }
                if (null != jsonObject.get("isPercent") && !"".equals(jsonObject.getString("isPercent"))) {
                    method.setIsPercent(jsonObject.getInt("isPercent"));
                }
                if (null != jsonObject.get("percent") && !"".equals(jsonObject.getString("percent"))) {
                    method.setPercent(jsonObject.getDouble("percent"));
                }
                if(method.getServiceFeeConfigId() == 1 || method.getServiceFeeConfigId() == 2
                        || method.getServiceFeeConfigId() == 5 || method.getServiceFeeConfigId() == 6
                        || method.getServiceFeeConfigId() == 7){

                    List<CompanyCooperationServiceFee> feeList = new ArrayList<CompanyCooperationServiceFee>();
                    String serviceFeeList = jsonObject.getString("serviceFeeList");
                    if(!CommonUtil.isEmpty(serviceFeeList)){
                        JSONArray jsonArray = JSONArray.fromObject(serviceFeeList);
                        for (Object o1 : jsonArray) {
                            JSONObject temp = JSONObject.fromObject(o1);
                            CompanyCooperationServiceFee fee = new CompanyCooperationServiceFee();
                            fee.setExtent(temp.getInt("extent"));
                            fee.setPrice(temp.getDouble("price"));

                            feeList.add(fee);
                        }
                    }
                    method.setServiceFeeList(feeList);
                }else if(method.getServiceFeeConfigId() == 3){
                    List<CompanyCooperationBusinessServiceFee> serviceFeeList = new ArrayList<CompanyCooperationBusinessServiceFee>();
                    String tempServiceFeeArr = jsonObject.getString("businessServiceFeeList");
                    if(!CommonUtil.isEmpty(tempServiceFeeArr)){
                        JSONArray jsonArray = JSONArray.fromObject(tempServiceFeeArr);
                        for (Object o1 : jsonArray) {
                            JSONObject temp = JSONObject.fromObject(o1);
                            CompanyCooperationBusinessServiceFee fee = new CompanyCooperationBusinessServiceFee();
                            fee.setPrice(temp.getDouble("price"));
                            fee.setBusinessIds(temp.getString("businessIds"));
                            serviceFeeList.add(fee);
                        }
                    }
                    method.setBusinessServiceFeeList(serviceFeeList);
                }else if(method.getServiceFeeConfigId() == 4){
                    List<CompanyCooperationServicePayPlace> serviceFeeList = new ArrayList<CompanyCooperationServicePayPlace>();
                    String tempServiceFeeArr = jsonObject.getString("payPlaceList");
                    if(!CommonUtil.isEmpty(tempServiceFeeArr)){
                        JSONArray jsonArray = JSONArray.fromObject(tempServiceFeeArr);
                        for (Object o1 : jsonArray) {
                            JSONObject temp = JSONObject.fromObject(o1);
                            CompanyCooperationServicePayPlace fee = new CompanyCooperationServicePayPlace();
                            fee.setPrice(temp.getDouble("price"));
                            fee.setCityId(temp.getInt("cityId"));
                            serviceFeeList.add(fee);
                        }
                    }
                    method.setPayPlaceList(serviceFeeList);
                }
                methodList.add(method);
            }
        }

        if(methodList.size() > 0){
            companyCooperationMethodMapper.batchAdd(methodList);
            for (CompanyCooperationMethod method : methodList) {
                if(null != method.getBusinessServiceFeeList() && method.getBusinessServiceFeeList().size() > 0){
                    for (CompanyCooperationBusinessServiceFee fee : method.getBusinessServiceFeeList()) {
                        fee.setCompanyCooperationMethodId(method.getId());
                    }
                    companyCooperationBusinessServiceFeeMapper.batchAdd(method.getBusinessServiceFeeList());
                }
                if(null != method.getPayPlaceList() && method.getPayPlaceList().size() > 0){
                    for (CompanyCooperationServicePayPlace place : method.getPayPlaceList()) {
                        place.setCompanyCooperationMethodId(method.getId());
                    }
                    companyCooperationServicePayPlaceMapper.batchAdd(method.getPayPlaceList());
                }
                if(null != method.getServiceFeeList() && method.getServiceFeeList().size() > 0){
                    for (CompanyCooperationServiceFee fee : method.getServiceFeeList()) {
                        fee.setCompanyCooperationMethodId(method.getId());
                    }
                    companyCooperationServiceFeeMapper.batchAdd(method.getServiceFeeList());
                }
            }
        }

        // 合作方式业务封装  结束

        // 封装业务模块 Start
        JSONArray businessArray = JSONArray.fromObject(businessArr);
        List<CompanyBusiness> businessList = new ArrayList<CompanyBusiness>();
        for (Object obj : businessArray) {
            // 中间表对象
            JSONObject buss = JSONObject.fromObject(obj);
            int businessId = buss.getInt("businessId");
            CompanyBusiness c = new CompanyBusiness(); // 公司与业务多对多
            c.setBusinessId(businessId);
            c.setCompanyId(company.getId());
            if(businessId == 3 || businessId == 4){
                // 如果是公积金、社保
                BusinessMethod businessMethod = new BusinessMethod();
                if(null != buss.get("daili")){
                    Integer daili = buss.getInt("daili");// 代理选择的账单制作方式
                    businessMethod.setDaiLi(daili);
                }
                if(null != buss.get("tuoguan")){
                    Integer tuoguan = buss.getInt("tuoguan");// 托管选择的账单制作方式
                    businessMethod.setTuoGuan(tuoguan);
                }
                c.setBusinessMethods(businessMethod);
            }
            else if(businessId == 6 || businessId == 7){
                // 如果是商业保险、一次性业务
                String arr = buss.getString("arr");
                if(arr.indexOf("[") < 0){
                    arr = "["+arr+"]";
                }
                JSONArray busItemArr = JSONArray.fromObject(arr); // 商业保险、一次性业务 下选择的子类业务集合
                List<CompanyBusinessItem> companyBusinessItems = new ArrayList<CompanyBusinessItem>();
                for (Object o : busItemArr) {
                    JSONArray tempArr = JSONArray.fromObject(o);
                    for (Object o1 : tempArr){
                        JSONObject temp = JSONObject.fromObject(o1);
                        CompanyBusinessItem companyBusinessItem = new CompanyBusinessItem();
                        companyBusinessItem.setBusinessItemId(temp.getInt("businessItemId"));
//                        companyBusinessItem.setPrice(temp.getDouble("price"));
                        companyBusinessItems.add(companyBusinessItem);
                    }
                }
                c.setCompanyBusinessItems(companyBusinessItems);
            }
            businessList.add(c);
        }
        companyBusinessMapper.delCompanyBusiness(company.getId());
        if(businessList.size() > 0){
            for (CompanyBusiness companyBusiness : businessList) {
                companyBusinessMapper.addCompanyBusiness(companyBusiness);
            }
//            companyBusinessMapper.batchAddCompanyBusiness(businessList);
            // 处理子集业务
            List<BusinessMethod> businessMethods = new ArrayList<BusinessMethod>();
            List<CompanyBusinessItem> companyBusinessItems = new ArrayList<CompanyBusinessItem>();
            List<Integer> companyBusinessIds  = new ArrayList<Integer>();
            for (CompanyBusiness companyBusiness : businessList) {
                companyBusinessIds.add(companyBusiness.getId());
                if(null != companyBusiness.getBusinessMethods()){
                    companyBusiness.getBusinessMethods().setCompanyBusinessId(companyBusiness.getId());
                    businessMethods.add(companyBusiness.getBusinessMethods());
                }
                if(null != companyBusiness.getCompanyBusinessItems() && companyBusiness.getCompanyBusinessItems().size() > 0){
                    for (CompanyBusinessItem companyBusinessItem : companyBusiness.getCompanyBusinessItems()) {
                        companyBusinessItem.setBusinessCompanyId(companyBusiness.getId());
                    }
                    companyBusinessItems.addAll(companyBusiness.getCompanyBusinessItems());
                }
            }
            if(companyBusinessIds.size() > 0){
                businessMethodMapper.delBusinessMethod(companyBusinessIds);
                companyBusinessItemMapper.delCompanyBusinessItem(companyBusinessIds);
            }

            if(businessMethods.size() > 0){
                businessMethodMapper.batchAddBusinessMethod(businessMethods);
            }
            if(companyBusinessItems.size() > 0){
                companyBusinessItemMapper.batchAddCompanyBusinessItem(companyBusinessItems);
            }
        }
        // 封装业务模块 End

        // 工资发放日期 业务处理 Start
        JSONArray jsonArray = JSONArray.fromObject(salaryDateArr);
        List<SalaryDate> salaryDateList = new ArrayList<SalaryDate>();
        for (Object o : jsonArray) {
            SalaryDate salaryDate = new SalaryDate();
            salaryDate.setCompanyId(company.getId());
            salaryDate.setGrantDate(new Date(Long.valueOf(o.toString())));
            salaryDateList.add(salaryDate);
        }
        salaryDateMapper.delSalaryDate(company.getId());
        if(salaryDateList.size() > 0){
            salaryDateMapper.batchAddSalaryDate(salaryDateList);
        }
        // 工资发放日期 业务处理 End
        //待办
        //待办 前道客服
        Backlog backlog = new Backlog();
        backlog.setRoleId(9);
        backlog.setUserId(company.getBeforeService());
        backlog.setContent("还存在实做待申请的员工，请及时处理");
        backlog.setUrl("/page/company/edit?id=" + company.getId());
        backlogMapper.save(backlog);
        // 日志记录
        Log log = new Log(currentUser.getId(),StatusConstant.LOG_MODEL_COMPANY,"新增 "+company.getCompanyName(),StatusConstant.LOG_FLAG_ADD);
        logMapper.add(log);
    }

    /**
     * 更新公司和公司联系人
     * @param company
     * @param contactsList
     */
    @Transactional
    public void updateAndContacts(Company company,List<Contacts> contactsList) {
        Company sqlCompany = companyMapper.queryCompanyByName(company.getCompanyName());
        if(null != sqlCompany && !sqlCompany.getCompanyName().equals(company.getCompanyName())){
            throw new InterfaceCommonException(StatusConstant.OBJECT_EXIST,"公司名已经存在");
        }
        companyMapper.updateCompany(company);
        List<Contacts> addList = new ArrayList<Contacts>();
        List<Contacts> updateList = new ArrayList<Contacts>();
        for (Contacts contacts : contactsList) {
            if (null == contacts.getId()) {
                addList.add(contacts);
            } else {
                updateList.add(contacts);
            }
        }
        if (addList.size() > 0) {
            contactsMapper.addList(addList);
        }
        if (updateList.size() > 0) {
            contactsMapper.updateList(updateList);
        }
    }
    /**
     * 更新公司
     * @param company
     */
    @Transactional
    public void update(Company company) {
        companyMapper.updateCompany(company);
    }

    /**
     * 获取公司详情 和公司联系人
     * @param id
     * @return
     */
    public Company infoAndContactsList(Integer id){
        return companyMapper.infoAndContactsList(id);
    }

    public Company hrLogin(String account){
        return companyMapper.hrLogin(account);
    }


    public List<Company> queryCompanyByIds(Integer[] companyIdInts) {
        return companyMapper.queryCompanyByIds(companyIdInts);
    }

    public List<Company> queryCompanyAll() {
        return companyMapper.queryCompanyAll();
    }

    public List<Company> queryCompanyAll2() {
        return companyMapper.queryCompanyAll2();
    }
}
