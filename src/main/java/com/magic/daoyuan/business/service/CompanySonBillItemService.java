package com.magic.daoyuan.business.service;

import com.magic.daoyuan.business.dto.CompanySonTotalBillDto;
import com.magic.daoyuan.business.dto.MemberAuditDto;
import com.magic.daoyuan.business.dto.MemberBusinessCityDto;
import com.magic.daoyuan.business.dto.MemberMonthPayBusinessDto;
import com.magic.daoyuan.business.entity.*;
import com.magic.daoyuan.business.enums.BusinessEnum;
import com.magic.daoyuan.business.enums.InsuranceType;
import com.magic.daoyuan.business.enums.TimeField;
import com.magic.daoyuan.business.exception.InterfaceCommonException;
import com.magic.daoyuan.business.mapper.*;
import com.magic.daoyuan.business.util.*;
import com.sun.org.glassfish.external.statistics.Statistic;
import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 公司子账单子类
 * @author lzh
 * @create 2017/10/13 10:08
 */
@Service
public class CompanySonBillItemService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private ICompanySonBillItemMapper companySonBillItemMapper;
    @Resource
    private ICompanySonBillMapper companySonBillMapper;
    @Resource
    private ICompanyMapper companyMapper;
    @Resource
    private ICompanyServiceFeeMapper companyServiceFeeMapper;
    @Resource
    private IMemberMapper memberMapper;
    @Resource
    private IMemberBusinessMapper memberBusinessMapper;
    @Resource
    private ISocialSecurityInfoMapper socialSecurityInfoMapper;
    @Resource
    private ISocialSecurityInfoItemMapper socialSecurityInfoItemMapper;
    @Resource
    private IReservedFundsInfoMapper reservedFundsInfoMapper;
    @Resource
    private ISalaryInfoMapper salaryInfoMapper;
    @Resource
    private IUserMapper userMapper;
    @Resource
    private IAccountConfigMapper accountConfigMapper;
    @Resource
    private IMonthServiceFeeMapper monthServiceFeeMapper;
    @Resource
    private ICompanySonTotalBillMapper companySonTotalBillMapper;
    @Resource
    private IBusinessItemMapper businessItemMapper;
    @Resource
    private IBusinessInsuranceMapper businessInsuranceMapper;
    @Resource
    private IBusinessInsuranceItemMapper businessInsuranceItemMapper;
    @Resource
    private IBusinessYcMapper businessYcMapper;
    @Resource
    private IBusinessYcItemMapper businessYcItemMapper;
    @Resource
    private IMemberBusinessItemMapper memberBusinessItemMapper;
    @Resource
    private CompanyService companyService;
    @Resource
    private ICompanyCooperationMethodMapper companyCooperationMethodMapper;
    @Resource
    private IMemberBusinessUpdateRecordItemMapper memberBusinessUpdateRecordItemMapper;
    @Resource
    private IMemberMonthPayBusinessMapper memberMonthPayBusinessMapper;
    @Resource
    private IMonthServiceFeeDetailMapper monthServiceFeeDetailMapper;
    @Resource
    private MemberService memberService;
    @Resource
    private IMemberNumberMapper memberNumberMapper;
    @Resource
    private IPayPlaceMapper payPlaceMapper;
    @Resource
    private ICompanyBillCountMapper companyBillCountMapper;
    @Resource
    private ILogMapper logMapper;
    @Resource
    private MakeBillHelper makeBillHelper;
    @Resource
    private IMakeBillMapper makeBillMapper;
    @Resource
    private IMonthServiceFeeBalanceMapper monthServiceFeeBalanceMapper;
    @Resource
    private IInformMapper informMapper;


    /**
     * 新增
     * @param companyList
     * @param dateMap
     */
    @Transactional
    public Map<String ,Object> save(
            List<Company> companyList,Map<Integer , List<Date>> dateMap,
            Set<Integer> companyIdSet
            /*String companyIds,String dates,Integer isAll*/) throws ParseException, IOException {

        Map<String ,Object> resultMap = new HashMap<String, Object>();
//
        //子账单子类
        List<CompanySonBillItem> companySonBillItems = new ArrayList<CompanySonBillItem>();
//        //公司集合
//        List<Company> companyList;
//        Integer[] companyIdInts;
//        //记录公司对应的账单月
//        Map<Integer , List<Date>> dateMap = new HashMap<Integer, List<Date>>();
//
//        //封装集合 公司和时间集合
//        List<Map<Integer ,Date>> mapList = new ArrayList<Map<Integer, Date>>();
//
//
//
//
//        if (isAll == 1) {
//            companyIdInts = ClassConvert.strToIntegerGather(companyIds.replaceAll("，",",").split(","));
//            companyList = companyMapper.queryCompanyByIds(companyIdInts);
//            List<Date> dateList = ClassConvert.strListToDateListGather(JSONArray.toList(JSONArray.fromObject(dates),Long.class));
//
//            for (int i = 0; i < companyIdInts.length; i++) {
//                List<Date> dateList2 = new ArrayList<Date>();
//                dateList2.add(dateList.get(i));
//                dateList2.add(Timestamp.parseDate3(CommonUtil.getMonth(companyList.get(i).getBusinessStartTime(),companyList.get(i).getBusinessCycle(),1,dateList.get(i)).get(0),"yyyy-MM"));
//                dateMap.put(companyIdInts[i],dateList2);
//                Map<Integer ,Date> integerDateMap = new HashMap<Integer, Date>();
//                integerDateMap.put(companyIdInts[i],dateList2.get(1));
//                mapList.add(integerDateMap);
//            }
//        } else {
//            companyList = companyMapper.queryCompanyAll();
//            companyIdInts = new Integer[companyList.size()];
//            for (int i = 0 ; i < companyList.size() ; i++) {
//                List<Date> dateList2 = new ArrayList<Date>();
//                dateList2.add(new Date());
//                dateList2.add(Timestamp.parseDate3(CommonUtil.getMonth(companyList.get(i).getBusinessStartTime(),companyList.get(i).getBusinessCycle(),1,new Date()).get(0),"yyyy-MM"));
//                dateMap.put(companyList.get(i).getId(),dateList2);
//                companyIdInts[i] = companyList.get(i).getId();
//                Map<Integer ,Date> integerDateMap = new HashMap<Integer, Date>();
//                integerDateMap.put(companyIdInts[i],dateList2.get(1));
//                mapList.add(integerDateMap);
//            }
//        }

//        List<CompanySonTotalBill>

        //公司子账单
        List<CompanySonBill> companySonBills = new ArrayList<CompanySonBill>();
        int j = 0;
        for (int i = 0; i < companyList.size(); i++) {
            if (null == companyList.get(i).getCompanySonBillList() ||
                    companyList.get(i).getCompanySonBillList().size() < 1) {
                if (companyList.size() == 1 && j == 0) {
                    throw new InterfaceCommonException(StatusConstant.NO_DATA,"该公司没有子账单,无法生成账单");
                }
                dateMap.remove(companyList.get(i).getId());
                companyList.remove(i);
                i --;
                j ++;
            } else {
                if (null == companyList.get(i).getMemberList() ||
                        companyList.get(i).getMemberList().size() < 1) {
                    resultMap.put("msg","有公司的员工数量为0");
                }
                // TODO: 2018/1/29  装配公司服务费配置集合
                companyList.get(i).setMethods(companyCooperationMethodMapper.queryCompanyCooperationMethod(companyList.get(i).getId()));
                companySonBills.addAll(companyList.get(i).getCompanySonBillList());
            }


//            if (null == companyList.get(i).getCompanySonBillList() ||
//                    companyList.get(i).getCompanySonBillList().size() < 1 ||
//                    null == companyList.get(i).getMemberList() ||
//                    companyList.get(i).getMemberList().size() < 1) {
//                if (companyList.size() == 1) {
//                    if (null == companyList.get(i).getCompanySonBillList() ||
//                            companyList.get(i).getCompanySonBillList().size() < 1) {
//                        throw new InterfaceCommonException(StatusConstant.NO_DATA,"请先为该公司设置子账单");
//                    }
//                    if (null == companyList.get(i).getMemberList() ||
//                            companyList.get(i).getMemberList().size() < 1) {
//                        resultMap.put("msg","有公司的员工数量为0");
//                        companySonBills.addAll(companyList.get(i).getCompanySonBillList());
////                        throw new InterfaceCommonException(StatusConstant.NO_DATA,"请先为该公司添加员工");
//                    }
//                }
//                if (null == companyList.get(i).getMemberList() ||
//                        companyList.get(i).getMemberList().size() < 1) {
//                    resultMap.put("msg","有公司的员工数量为0");
//                    companySonBills.addAll(companyList.get(i).getCompanySonBillList());
//                }
//                /*dateMap.remove(companyList.get(i).getId());
//                companyList.remove(i);
//                i --;*/
//            } else {
//                companySonBills.addAll(companyList.get(i).getCompanySonBillList());
//            }
        }

        if (companyList.size() < 1) {
            resultMap.put("msg","没有公司可以生成账单");
            return resultMap;
        }
        //存放 公司和账单月集合mapList
        List<Map<String ,Object>> companyDateMapList = new ArrayList<Map<String, Object>>();

        for (int i = 0; i < companyList.size(); i++) {
            Map<String ,Object> companyDateMap = new HashMap<String, Object>();
            companyDateMap.put("companyId",companyList.get(i).getId());
            companyDateMap.put("billMonth",dateMap.get(companyList.get(i).getId()).get(0));
            companyDateMapList.add(companyDateMap);
        }

        Set<Integer> companySonTotalBillIdSetRem = new HashSet<Integer>();
        if (companyDateMapList.size() > 0) {
            //需要被移除的公司id 及移除
            Set<Integer> companyIdSet2 = new HashSet<Integer>();
            //删除后重新生成
            List<CompanySonTotalBill> companySonTotalBills2 =
                    companySonTotalBillMapper.getByCompanyDateMapList(companyDateMapList);
            for (CompanySonTotalBill bill : companySonTotalBills2) {
                if (bill.getStatus() == 2) {
                    companyIdSet2.add(bill.getCompanyId());
                    for (int i = 0; i < companyList.size(); i++) {
                        if (bill.getCompanyId().equals(companyList.get(i).getId())) {
                            companyList.remove(i);
                            dateMap.remove(i);
                            break;
                        }
                    }
                    for (int i = 0; i < companyDateMapList.size(); i++) {
                        if (null != companyDateMapList.get(i).get(bill.getCompanyId().toString())) {
                            companyDateMapList.remove(i);
                            break;
                        }
                    }
                }
            }
            for (int i = 0; i < companySonTotalBills2.size(); i++) {
                if (companyIdSet2.contains(companySonTotalBills2.get(i).getCompanyId())) {
                    companySonTotalBills2.remove(i);
                    i --;
                    continue;
                }
                companySonTotalBillIdSetRem.add(companySonTotalBills2.get(i).getId());
            }
        }
        Integer[] companySonTotalBillIds = companySonTotalBillIdSetRem.toArray(new Integer[companySonTotalBillIdSetRem.size()]);
        if (companySonTotalBillIds.length > 0) {
            //还原第一次缴纳标识
            memberBusinessItemMapper.updateIsFirstPay2(companyDateMapList);
            //异动量回滚
            memberBusinessUpdateRecordItemMapper.update2ListMap(companyDateMapList);
            // 重新生成账单时， 删除其他数据
            //删除缴纳记录
            memberMonthPayBusinessMapper.delList2(companyDateMapList);
            // 更新工资信息
            salaryInfoMapper.updateListByCompanySonTotalBillIds(companySonTotalBillIds);
            //更新拷盘数据制空子账单子类id
            socialSecurityInfoMapper.updateList2(companySonTotalBillIds);
            // 删除社保险种明细
            socialSecurityInfoItemMapper.delList(companySonTotalBillIds);
            // 删除社保
            socialSecurityInfoMapper.del(companySonTotalBillIds);
            //更新拷盘数据制空子账单子类id
            reservedFundsInfoMapper.updateList2(companySonTotalBillIds);
            // 删除公积金
            reservedFundsInfoMapper.del(companySonTotalBillIds);
            //更新稽核后的数据
//                    companySonBillItemMapper.updateList2(companySonTotalBillIds);
            // 删除月总账单 子账单子类
            companySonBillItemMapper.deleteByCompanySonTotalBillId(companySonTotalBillIds);
            //删除服务费明细
            monthServiceFeeDetailMapper.delete(companySonTotalBillIds);
            // 删除服务费
            monthServiceFeeMapper.delete(companySonTotalBillIds);
            // 删除汇总账单
            companySonTotalBillMapper.delete(companySonTotalBillIds);
            // 删除专项服务
            List<BusinessInsurance> list = businessInsuranceMapper.queryBusinessInsuranceIds(companySonTotalBillIds);
            if(null != list && list.size() > 0){
                List<Integer> ids = new ArrayList<Integer>();
                for (BusinessInsurance insurance : list) {
                    ids.add(insurance.getId());
                }
                if(ids.size() > 0){
                    businessInsuranceMapper.del(ids);
                    businessInsuranceItemMapper.delBusinessInsuranceItemByIds(ids);
                }
            }
            // 删除一次性险
            List<BusinessYc> businessYcList = businessYcMapper.queryBusinessYc3(companySonTotalBillIds);
            if(businessYcList.size() > 0){
                List<Integer> ycIds = new ArrayList<Integer>();
                List<Integer> ycItemsIds = new ArrayList<Integer>();
                for (BusinessYc businessYc : businessYcList) {
                    ycIds.add(businessYc.getId());
                    if(null != businessYc.getBusinessYcItemList() && businessYc.getBusinessYcItemList().size() > 0){
                        for (BusinessYcItem ycItem : businessYc.getBusinessYcItemList()) {
                            ycItemsIds.add(ycItem.getId());
                        }
                    }

                }
                businessYcMapper.del(ycIds);
                if (ycItemsIds.size() > 0) {
                    businessYcItemMapper.del(ycItemsIds);
                }
            }
        }

//        out:for (int k = 0 ; k < companyList.size() ; k ++) {
//            {
//                //删除后重新生成
//                List<CompanySonTotalBill> companySonTotalBills2 =
//                        companySonTotalBillMapper.getByCompanyId(companyList.get(k).getId(),dateMap.get(companyList.get(k).getId()).get(0));
//                for (CompanySonTotalBill bill : companySonTotalBills2) {
//                    if (bill.getStatus() == 2) {
//                        companyList.remove(k);
//                        k--;
//                        continue out;
//                    }
//                }
//                Integer[] companySonTotalBillIds = new Integer[companySonTotalBills2.size()];
//
//
//                memberBusinessItemMapper.updateIsFirstPay(companyList.get(k).getId(),dateMap.get(companyList.get(k).getId()).get(0));
//
//                for (int i = 0 ; i < companySonTotalBills2.size() ; i ++) {
//                    companySonTotalBillIds[i] = companySonTotalBills2.get(i).getId();
//                }
//                if (companySonTotalBillIds.length > 0) {
//
//                    //异动量回滚
//                    memberBusinessUpdateRecordItemMapper.update2(companyList.get(k).getId(),dateMap.get(companyList.get(k).getId()).get(0));
//                    // 重新生成账单时， 删除其他数据
//                    //删除缴纳记录
//                    memberMonthPayBusinessMapper.delList(dateMap.get(companyList.get(k).getId()).get(0),companyList.get(k).getId());
//                    // 更新工资信息
//                    salaryInfoMapper.updateListByCompanySonTotalBillIds(companySonTotalBillIds);
//                    //更新拷盘数据制空子账单子类id
//                    socialSecurityInfoMapper.updateList2(companySonTotalBillIds);
//                    // 删除社保险种明细
//                    socialSecurityInfoItemMapper.delList(companySonTotalBillIds);
//                    // 删除社保
//                    socialSecurityInfoMapper.del(companySonTotalBillIds);
//                    //更新拷盘数据制空子账单子类id
//                    reservedFundsInfoMapper.updateList2(companySonTotalBillIds);
//                    // 删除公积金
//                    reservedFundsInfoMapper.del(companySonTotalBillIds);
//                    //更新稽核后的数据
////                    companySonBillItemMapper.updateList2(companySonTotalBillIds);
//                    // 删除月总账单 子账单子类
//                    companySonBillItemMapper.deleteByCompanySonTotalBillId(companySonTotalBillIds);
//                    //删除服务费明细
//                    monthServiceFeeDetailMapper.delete(companySonTotalBillIds);
//                    // 删除服务费
//                    monthServiceFeeMapper.delete(companySonTotalBillIds);
//                    // 删除汇总账单
//                    companySonTotalBillMapper.delete(companySonTotalBillIds);
//                    // 删除专项服务
//                    List<BusinessInsurance> list = businessInsuranceMapper.queryBusinessInsuranceIds(companySonTotalBillIds);
//                    if(null != list && list.size() > 0){
//                        List<Integer> ids = new ArrayList<Integer>();
//                        for (BusinessInsurance insurance : list) {
//                            ids.add(insurance.getId());
//                        }
//                        if(ids.size() > 0){
//                            businessInsuranceMapper.del(ids);
//                            businessInsuranceItemMapper.delBusinessInsuranceItemByIds(ids);
//                        }
//                    }
//                    // 删除一次性险
//                    List<BusinessYc> businessYcList = businessYcMapper.queryBusinessYc3(companySonTotalBillIds);
//                    if(businessYcList.size() > 0){
//                        List<Integer> ycIds = new ArrayList<Integer>();
//                        List<Integer> ycItemsIds = new ArrayList<Integer>();
//                        for (BusinessYc businessYc : businessYcList) {
//                            ycIds.add(businessYc.getId());
//                            if(null != businessYc.getBusinessYcItemList() && businessYc.getBusinessYcItemList().size() > 0){
//                                for (BusinessYcItem ycItem : businessYc.getBusinessYcItemList()) {
//                                    ycItemsIds.add(ycItem.getId());
//                                }
//                            }
//
//                        }
//                        businessYcMapper.del(ycIds);
//                        if (ycItemsIds.size() > 0) {
//                            businessYcItemMapper.del(ycItemsIds);
//                        }
//                    }
//                }
//            }
//        }
//        if (true) {
//            return null;
//        }
        if (companyList.size() < 1) {
            resultMap.put("msg","没有公司可以生成账单");
            return resultMap;
        }
        resultMap.put("msg2","生成成功");
        List<CompanySonTotalBill> companySonTotalBills =
                getCompanySonTotalBills(companySonBills,companyList,dateMap);

        //添加汇总账单
        companySonTotalBillMapper.save(companySonTotalBills);

        Map<Integer,Set<Date>> setMap = new HashMap<Integer, Set<Date>>();


        //员工增加变记录集合 计算服务费异动量
        List<MemberBusinessUpdateRecordItem> recordItems = new ArrayList<MemberBusinessUpdateRecordItem>();
        //获取商业险
        List<BusinessInsurance> insuranceList = getBusinessInsurance(companySonTotalBills, companyList, dateMap);
        //获取一次性险
        List<BusinessYc> businessYcList = getBusinessYc(companySonTotalBills, companyList);
        for (int k = 0 ; k < companyList.size() ; k ++) {
            Set<Date> dateSet = new HashSet<Date>();
            //第一个月的时间
            Date date = Timestamp.parseDate3(dateMap.get(companyList.get(k).getId()).get(0),"yyyy-MM");
//
//            //获取商业险
//            insuranceList = getBusinessInsurance(companySonTotalBills, companyList, dateMap);
//            //获取一次性险
//            businessYcList = getBusinessYc(companySonTotalBills, companyList);


            for (int i = 0 ; i < companyList.get(k).getBusinessCycle() ; i ++) {
                dateSet.add(date);
                for (CompanySonTotalBill bill : companySonTotalBills) {
                    CompanySonBillItem companySonBillItem = new CompanySonBillItem();
                    companySonBillItem.setCompanySonTotalBillId(bill.getId());
                    companySonBillItem.setServiceMonth(date);
                    companySonBillItem.setIsTotalBill(0);
                    companySonBillItem.setIsAudit(0);
                    companySonBillItem.setBalanceOfCancelAfterVerification(0.0);
                    companySonBillItem.setTotalPrice(0.0);
                    companySonBillItem.setReceivablePrice(0.0);
                    companySonBillItem.setAuditTheDifference(0.0);
                    companySonBillItem.setCompanySonBillId(bill.getCompanySonBillId());
                    companySonBillItem.setCompanySonTotalBill(bill);
                    companySonBillItem.setCompanyId(companyList.get(k).getId());
                    companySonBillItem.setBillMonth(dateMap.get(companyList.get(k).getId()).get(1));
                    companySonBillItems.add(companySonBillItem);
                }
                date = DateUtil.addDate(date, TimeField.Month.ordinal(),1);
            }
            setMap.put(companyList.get(k).getId(),dateSet);
        }

        // 新增商业险数据
        if (insuranceList.size() > 0) {
            businessInsuranceMapper.batchAddBusinessInsurance(insuranceList);
            List<BusinessInsuranceItem> insuranceItemList = new ArrayList<BusinessInsuranceItem>();
            for (BusinessInsurance businessInsurance : insuranceList) {
                if(null != businessInsurance.getBusinessInsuranceItemList()){
                    for (BusinessInsuranceItem item : businessInsurance.getBusinessInsuranceItemList()) {
                        item.setBusinessInsuranceId(businessInsurance.getId());
                        insuranceItemList.add(item);
                    }
                }
            }
            // 新增险种子类
            if (insuranceItemList.size() > 0) {
                businessInsuranceItemMapper.batchAddBusinessInsuranceItem(insuranceItemList);
            }
        }


        if(businessYcList.size() > 0){
            businessYcMapper.batchAddBusinessYc(businessYcList);
            List<BusinessYcItem> businessYcItemList = new ArrayList<BusinessYcItem>();
            for (BusinessYc businessYc : businessYcList) {
                if(null != businessYc.getBusinessYcItemList()){
                    for (BusinessYcItem businessYcItem : businessYc.getBusinessYcItemList()) {
                        businessYcItem.setBusinessYcId(businessYc.getId());
                        businessYcItemList.add(businessYcItem);
                    }
                }
            }
            if(businessYcItemList.size() > 0){
                businessYcItemMapper.batchAddBusinessYcItem(businessYcItemList);
            }
        }

        //社保明细集合
        List<SocialSecurityInfo> list1 = new ArrayList<SocialSecurityInfo>();
        //公积金明细集合
        List<ReservedFundsInfo> list2 = new ArrayList<ReservedFundsInfo>();

        //社保明细更新集合
        List<SocialSecurityInfo> updateListSSI = new ArrayList<SocialSecurityInfo>();
        //公积金明细更新集合
        List<ReservedFundsInfo> updateListRFI = new ArrayList<ReservedFundsInfo>();
        //工资明细更新集合
        List<SalaryInfo> updateListSI = new ArrayList<SalaryInfo>();

        companySonBillItemMapper.save(companySonBillItems);

        //公司子账单 0：预收的子账单
        List<CompanySonBill> companySonBills2 = companySonBillMapper.listForCompanyIds2(companyIdSet);

        for (CompanySonBillItem item : companySonBillItems) {
            //社保明细临时集合
            List<SocialSecurityInfo> listSSI = new ArrayList<SocialSecurityInfo>();
            //公积金明细临时集合
            List<ReservedFundsInfo> listRFI = new ArrayList<ReservedFundsInfo>();
            //工资明细临时集合
            List<SalaryInfo> listSI = new ArrayList<SalaryInfo>();
            Set<Date> dateSet = new HashSet<Date>();

            for (CompanySonBill bill : companySonBills2) {
                Set<Date> dateSet1 = setMap.get(bill.getCompanyId());
                if (item.getCompanySonBillId().equals(bill.getId())){
                    //businessId ：3 社保
                    //businessId ：4 公积金
                    Double totalPrice = 0.0;
                    Double receivablePrice = 0.0;
                    for (MemberBusiness business : bill.getMemberBusinessList()) {
                        if (null != bill.getSocialSecurityInfoList()
                                && bill.getSocialSecurityInfoList().size() > 0
                                && business.getBusinessId() == 3) {
                            Double ssiCPrice = 0.0;
                            Double ssiMPrice = 0.0;
                            Double rssiCPrice = 0.0;
                            Double rssiMPrice = 0.0;
                            Double ssiCPrice2 = 0.0;
                            Double ssiMPrice2 = 0.0;
                            Double rssiCPrice2 = 0.0;
                            Double rssiMPrice2 = 0.0;
                            List<SocialSecurityInfo> ssiList = bill.getSocialSecurityInfoList();
                            for (int i = 0; i < ssiList.size(); i++) {
                                if (ssiList.get(i).getBillMonth().compareTo(dateMap.get(bill.getCompanyId()).get(1)) == 0 &&
                                        item.getServiceMonth().compareTo(ssiList.get(i).getServiceNowYM()) >= 0) {
                                    dateSet1.add(ssiList.get(i).getServiceNowYM());
                                      ssiList.get(i).setCompanySonBillItemId(item.getId());
                                    ssiList.get(i).setBillMonth(item.getBillMonth());
                                    listSSI.add(ssiList.get(i));
                                    if (ssiList.get(i).getBillMadeMethod() == 1) {
                                        rssiCPrice += ssiList.get(i).getCompanyTotalPay();
                                        rssiMPrice += ssiList.get(i).getMemberTotalPay();
                                        ssiCPrice += ssiList.get(i).getPracticalCompanyTotalPay();
                                        ssiCPrice += ssiList.get(i).getPracticalMemberTotalPay();
                                    } else {
                                        rssiCPrice2 += ssiList.get(i).getCompanyTotalPay();
                                        rssiMPrice2 += ssiList.get(i).getMemberTotalPay();
                                        ssiCPrice2 += ssiList.get(i).getPracticalCompanyTotalPay();
                                        ssiCPrice2 += ssiList.get(i).getPracticalMemberTotalPay();
                                    }

                                    ssiList.remove(i);
                                    i--;
                                }
                            }
                            totalPrice += ssiCPrice + ssiMPrice;
                            receivablePrice += rssiCPrice + rssiMPrice;
                            /*if (rssiCPrice2 + rssiMPrice2 - (ssiCPrice2 + ssiMPrice2) == 0) {
                                item.setIsSocialSecurityAudit(1);
                            }*/
                            item.setSocialSecurityPracticalPrice(0.0);
                            item.setSsPaidInPrice(ssiCPrice + ssiMPrice);

                        }
                        if (null != bill.getReservedFundsInfoList() && bill.getReservedFundsInfoList().size() > 0 && business.getBusinessId() == 4) {
                            Double rfiCPrice = 0.0;
                            Double rfiMPrice = 0.0;
                            Double rrfiCPrice = 0.0;
                            Double rrfiMPrice = 0.0;
                            Double rfiCPrice2 = 0.0;
                            Double rfiMPrice2 = 0.0;
                            Double rrfiCPrice2 = 0.0;
                            Double rrfiMPrice2 = 0.0;
                            List<ReservedFundsInfo> rfiList = bill.getReservedFundsInfoList();
                            for (int i = 0; i < rfiList.size(); i++) {
                                if (rfiList.get(i).getBillMonth().compareTo(dateMap.get(bill.getCompanyId()).get(1)) == 0 &&
                                        item.getServiceMonth().compareTo(rfiList.get(i).getServiceNowYM()) >= 0) {
                                    dateSet1.add(rfiList.get(i).getServiceNowYM());
                                    rfiList.get(i).setBillMonth(item.getBillMonth());
                                    rfiList.get(i).setCompanySonBillItemId(item.getId());
                                    listRFI.add(rfiList.get(i));
                                    if (rfiList.get(i).getBillMadeMethod() == 1) {
                                        rrfiMPrice += rfiList.get(i).getMemberTotalPay();
                                        rrfiCPrice += rfiList.get(i).getCompanyTotalPay();
                                        rfiCPrice += rfiList.get(i).getPracticalCompanyTotalPay();
                                        rfiMPrice += rfiList.get(i).getPracticalMemberTotalPay();
                                    } else {
                                        rrfiMPrice2 += rfiList.get(i).getMemberTotalPay();
                                        rrfiCPrice2 += rfiList.get(i).getCompanyTotalPay();
                                        rfiCPrice2 += rfiList.get(i).getPracticalCompanyTotalPay();
                                        rfiMPrice2 += rfiList.get(i).getPracticalMemberTotalPay();
                                    }
                                    rfiList.remove(i);
                                    i--;
                                }
                            }
                            totalPrice += rfiCPrice + rfiMPrice;
                            receivablePrice += rrfiCPrice + rrfiMPrice;
                            /*if (rrfiCPrice2 + rrfiMPrice2 - (rfiCPrice2 + rfiMPrice2) == 0) {
                                item.setIsReservedFundAudit(1);
                            }*/
                            item.setReservedFundPracticalPrice(0.0);
                            item.setRfPaidInPrice(rfiCPrice + rfiMPrice);
                        }
                        item.setTotalPrice(totalPrice);
                        item.setReceivablePrice(receivablePrice);

                        if (business.getBusinessId() == 5) {
                            for (int i = 0; i < companyList.size(); i++) {
                                if (bill.getCompanyId().equals(companyList.get(i).getId())) {
                                    if (Timestamp.parseDate3(item.getServiceMonth(),"yyyy-MM").
                                            compareTo(Timestamp.parseDate3(dateMap.get(companyList.get(i).getId()).get(0),"yyyy-MM")) == 0) {
                                        List<SalaryInfo> siList = bill.getSalaryInfoList();
                                        if (siList.size() > 0) {
                                            for (int i1 = 0; i1 < siList.size(); i1++) {
                                                dateSet1.add(siList.get(i).getMonth());
                                                siList.get(i1).setCompanySonTotalBillId(item.getCompanySonTotalBillId());
                                                for (CompanyCooperationMethod method : companyList.get(i).getMethods()) {
                                                    if (siList.get(i1).getWaysOfCooperation().equals(method.getCooperationMethodId())) {
                                                        siList.get(i1).setTaxPrice(siList.get(i1).getSalaryBeforeTax() * method.getPercent() / 100);
                                                        break;
                                                    }
                                                }
                                                listSI.add(siList.get(i1));
                                                siList.remove(i1);
                                                i1--;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                setMap.put(bill.getCompanyId(),dateSet1);
            }

            item.setSocialSecurityInfos(listSSI);
            item.setReservedFundsInfoList(listRFI);
            updateListSSI.addAll(listSSI);
            updateListRFI.addAll(listRFI);
            updateListSI.addAll(listSI);
        }


        //变更员工公积金或者社保缴纳业务
        List<MemberBusinessItem> memberBusinessItems = new ArrayList<MemberBusinessItem>();
        Map<String ,Boolean> isFirstForeach = new HashMap<String, Boolean>();
        //需要更新交纳的社保险种明细
        List<SocialSecurityInfoItem> updateListSSII = new ArrayList<SocialSecurityInfoItem>();
        //需要新增交纳的社保险种明细
        List<SocialSecurityInfoItem> addListSSII = new ArrayList<SocialSecurityInfoItem>();
        //员工缴纳业务集合
        List<MemberMonthPayBusiness> memberMonthPayBusinessList = new ArrayList<MemberMonthPayBusiness>();
        //服务费配置集合
        List<CompanyCooperationMethod> methodList = new ArrayList<CompanyCooperationMethod>();
        for (CompanySonBillItem item : companySonBillItems) {
            Date billMonth = Timestamp.parseDate3(new Date(),"yyyy-MM");
            Company company = null;
            out:for (int i = 0; i < companyList.size(); i++) {
                if (item.getCompanyId().equals(companyList.get(i).getId())) {
                    billMonth = Timestamp.parseDate3(dateMap.get(companyList.get(i).getId()).get(1),"yyyy-MM");
                    company = companyList.get(i);
                    methodList = company.getMethods()/*companyCooperationMethodMapper.queryCompanyCooperationMethod(companyList.get(i).getId())*/;
                    continue out;
                }
            }

            build(item,item.getCompanySonTotalBill().getCompanySonBillId(),
                    item.getServiceMonth(),list1,list2,updateListSSI,updateListRFI,
                    memberBusinessItems,isFirstForeach,
                    memberMonthPayBusinessList, setMap.get(company.getId()),
                    billMonth,company,methodList,updateListSSII,addListSSII);
            /*if (item.getSocialSecurityInfos().size() < 1 && item.getReservedFundsInfoList().size() < 1){
                build(item,item.getCompanySonTotalBill().getCompanySonBillId(),
                        item.getServiceMonth(),list1,list2,updateListSSI,updateListRFI,
                        memberBusinessItems,isFirstForeach,
                        memberMonthPayBusinessList, setMap.get(company.getId()),
                        billMonth,company,methodList,updateListSSII,addListSSII);
            }*/

        }
        if (list1.size() > 0) {
            //批量添加社保信息
            socialSecurityInfoMapper.save(list1);
            //社保明细子类险种集合
            List<SocialSecurityInfoItem> list10 = new ArrayList<SocialSecurityInfoItem>();
            for (SocialSecurityInfo info : list1) {
                for (SocialSecurityInfoItem item : info.getSocialSecurityInfoItems()) {
                    item.setSocialSecurityInfoId(info.getId());
                    list10.add(item);
                }
            }
            if (list10.size() > 0) {
                socialSecurityInfoItemMapper.save(list10);
            }
            if (list10.size() > 0) {

            }

        }
        if (list2.size() > 0) {
            //批量添加公积金信息
            reservedFundsInfoMapper.save(list2);
        }
        if (memberBusinessItems.size() > 0) {
            memberBusinessItemMapper.updateList(memberBusinessItems);
        }
        if (recordItems.size() > 0) {
            memberBusinessUpdateRecordItemMapper.updateList(recordItems);
        }
        //批量更新子账单子类
        companySonBillItemMapper.updateList(companySonBillItems);

        if (updateListSSI.size() > 0) {
            socialSecurityInfoMapper.updateList(updateListSSI);
        }

        if (addListSSII.size() > 0) {
            socialSecurityInfoItemMapper.save(addListSSII);
        }
        if (updateListSSII.size() > 0) {
            socialSecurityInfoItemMapper.updateList(updateListSSII);
        }

        if (updateListRFI.size() > 0) {
            reservedFundsInfoMapper.updateList(updateListRFI);
        }
        if (updateListSI.size() > 0) {
            salaryInfoMapper.updateList(updateListSI);
        }

        if (memberMonthPayBusinessList.size() > 0) {
            memberMonthPayBusinessMapper.saveList(memberMonthPayBusinessList);
        }

        //服务费集合新增
        List<MonthServiceFee> monthServiceFeesAdd = new ArrayList<MonthServiceFee>();
        //服务费明细集合
        List<MonthServiceFeeDetail> monthServiceFeeDetails = new ArrayList<MonthServiceFeeDetail>();
        //需要更新的异动量
        List<MemberBusinessUpdateRecordItem> updateRecordItemList = new ArrayList<MemberBusinessUpdateRecordItem>();

        for (int i = 0; i < companyList.size(); i++) {
            List<CompanySonTotalBill> companySonTotalBillList = companySonTotalBillMapper.getByBillMonth(Timestamp.parseDate3(dateMap.get(companyList.get(i).getId()).get(0),"yyyy-MM"),companyList.get(i).getId());
            //汇总账单的id集合
            Set<Integer> companySonTotalBillIdSet = new HashSet<Integer>();
            //服务费配置集合
            List<CompanyCooperationMethod> methods = companyList.get(i).getMethods();
                    /*companyCooperationMethodMapper.queryCompanyCooperationMethod(companyList.get(i).getId());*/
            if (null != companySonTotalBillList && companySonTotalBillList.size() > 0) {
                for (CompanySonTotalBill bill : companySonTotalBillList) {
                    Date billMonth = Timestamp.parseDate3(dateMap.get(companyList.get(i).getId()).get(0),"yyyy-MM");
                    if (Timestamp.parseDate3(bill.getBillMonth(),"yyyy-MM").compareTo(billMonth) == 0 ){
                        methods = memberService.getCompanyCooperationMethod(companyList.get(i),methods,companySonTotalBillIdSet,bill,billMonth);

                    }
                }
                for (Date date : setMap.get(companyList.get(i).getId())) {
                    memberService.buildServiceFee(companySonTotalBillList, companyList.get(i),
                            methods,Timestamp.parseDate3(dateMap.get(companyList.get(i).getId()).get(0),"yyyy-MM"),
                            monthServiceFeesAdd,date,updateRecordItemList);
                }

            }

        }
        if (updateRecordItemList.size() > 0) {
            memberBusinessUpdateRecordItemMapper.updateList(updateRecordItemList);
        }

        if (monthServiceFeesAdd.size() > 0) {
            monthServiceFeeMapper.save(monthServiceFeesAdd);
            for (MonthServiceFee fee : monthServiceFeesAdd) {
                if (null != fee.getMonthServiceFeeDetailList()) {
                    for (MonthServiceFeeDetail detail : fee.getMonthServiceFeeDetailList()) {
                        detail.setMonthServiceFeeId(fee.getId());
                        monthServiceFeeDetails.add(detail);
                    }
                }
            }
            if (monthServiceFeeDetails.size() > 0) {
                monthServiceFeeDetailMapper.batchAdd(monthServiceFeeDetails);
            }
        }

        return resultMap;

//        List<Member> memberList = memberMapper.getMemberByCompanyIdsAndDateSet(companyIdSet,dateSet);
//        // TODO: 2017/11/28 计算服务费
//        Map<Integer,List<Map<Integer,List<Member>>>> dateMembersMap = new HashMap<Integer, List<Map<Integer, List<Member>>>>();
//
//        for (int i = 0; i < companyList.size(); i++) {
//            List<Map<Integer,List<Member>>> listMap = new ArrayList<Map<Integer, List<Member>>>();
//            Map<Integer,List<Member>> map1 = new HashMap<Integer, List<Member>>();
//            for (Date date : dateSet) {
//                Integer timesTamp = Timestamp.timesTamp2(date,"yyyy-MM");
//                List<Member> m2 = new ArrayList<Member>();
//                for (Member member : memberList) {
//                    for (MemberMonthPayBusinessDto dto : member.getMonthPayBusinessDtoList()) {
//                        Integer timesTamp2 = Timestamp.timesTamp2(dto.getServiceMonth(),"yyyy-MM");
//                        if (timesTamp.equals(timesTamp2)) {
//                            m2.add(member);
//                        }
//                    }
//                }
//                if (m2.size() > 0) {
//                    map1.put(timesTamp,m2);
//                }
//            }
//            listMap.add(map1);
//            dateMembersMap.put(companyList.get(i).getId(),listMap);
//        }
//        for (int i = 0; i < companyList.size(); i++) {
//            Date billMonth = Timestamp.parseDate3(dateList.get(i),"yyyy-MM");
//            Date billMonth2 = Timestamp.parseDate3(billMonthList.get(i),"yyyy-MM");
//            for (CompanySonBill bill : companySonBills) {
//                if (companyList.get(i).getId().equals(bill.getCompanyId())) {
//                    //服务费配置集合
//                    List<CompanyCooperationMethod> methods =
//                            companyCooperationMethodMapper.queryCompanyCooperationMethod(companyList.get(i).getId());
//                    if (methods.size() > 0) {
//                        if (methods.get(0).getServiceFeeConfigId().equals(3) ||
//                                methods.get(0).getServiceFeeConfigId().equals(6) ||
//                                methods.get(0).getServiceFeeConfigId().equals(7)) {
//                            getServiceFree2(monthServiceFeesAdd,companySonTotalBills,companyList.get(i),dateMembersMap,
//                                    bill,0,methods,billMonth,billMonth2);
//                        } else {
//                            getServiceFree2(monthServiceFeesAdd,companySonTotalBills,companyList.get(i),dateMembersMap,
//                                    bill,1,methods,billMonth,billMonth2);
//                        }
//                    }
//
//                }
//            }
//        }




    }

    private List<BusinessYc> getBusinessYc(List<CompanySonTotalBill> sonTotalBills, Company company, Date now) {
        List<BusinessYc> businessYcList = new ArrayList<BusinessYc>();
        // 获取拥有 一次性业务的员工
        List<Member> members = memberMapper.queryMemberBusinessYc(company.getId());
        // 获取公司下 一次性业务的子类险种集合
        List<BusinessItem> businessItems = businessItemMapper.queryBusinessItemByCompany(company.getId(), BusinessEnum.yiXiXingYeWu.ordinal());
        // 公司的一次性险
        if(null != businessItems){
            for (BusinessItem businessItem : businessItems) {
                if(null != businessItem.getIsCompany() &&businessItem.getIsCompany() == 0){
                    // 公司业务
                    BusinessYc businessYc = new BusinessYc();
                    businessYc.setBusinessItemId(businessItem.getId());
                    List<BusinessYcItem> businessYcItemList = new ArrayList<BusinessYcItem>();
                    BusinessYcItem businessYcItem = new BusinessYcItem();
                    businessYcItem.setMemberId(company.getId());
                    businessYcItem.setFlag(1);
                    businessYcItem.setCompanySonBillId(businessItem.getCompanySonBillId());
                    // 设置总账单ID
                    for (CompanySonTotalBill sonTotalBill : sonTotalBills) {
                        if(null != businessItem.getCompanySonBillId() && sonTotalBill.getCompanySonBillId().equals(businessItem.getCompanySonBillId())){
                            businessYcItem.setCompanySonTotalBillId(sonTotalBill.getId());
                        }
                    }
                    businessYcItem.setPrice(businessItem.getPrice());
                    businessYcItemList.add(businessYcItem);
                    businessYc.setBusinessYcItemList(businessYcItemList);
                    businessYcList.add(businessYc);
                }
            }
        }


        if(null != businessItems){
            for (BusinessItem businessItem : businessItems) {
                BusinessYc businessYc = new BusinessYc();
                businessYc.setBusinessItemId(businessItem.getId());
                if(null != members){
                    List<BusinessYcItem> businessYcItemList = new ArrayList<BusinessYcItem>();
                    out : for (Member member : members) {
                        BusinessYcItem businessYcItem = new BusinessYcItem();
                        businessYcItem.setMemberId(member.getId());
                        businessYcItem.setFlag(0);
                        if(null != member.getBusinessList()){
                            for (Business business : member.getBusinessList()) {
                                double price = 0.0;
                                if(BusinessEnum.yiXiXingYeWu.ordinal() == business.getId() && null != business.getBusinessItems()){
                                    for (BusinessItem item : business.getBusinessItems()) {
                                        if(businessItem.getId().equals(item.getId())){
                                            price = item.getPrice();
                                            businessYcItem.setCompanySonBillId(item.getCompanySonBillId());
                                            // 设置总账单ID
                                            for (CompanySonTotalBill sonTotalBill : sonTotalBills) {
                                                if(null != item.getCompanySonBillId() && sonTotalBill.getCompanySonBillId().equals(item.getCompanySonBillId())){
                                                    businessYcItem.setCompanySonTotalBillId(sonTotalBill.getId());
                                                }
                                            }
                                            break ;
                                        }
                                    }
                                    businessYcItem.setPrice(price);
                                    businessYcItemList.add(businessYcItem);
                                    continue out;
                                }
                            }
                        }
                    }
                    businessYc.setBusinessYcItemList(businessYcItemList);
                }
                businessYcList.add(businessYc);
            }
        }
        return businessYcList;
    }
    private List<BusinessYc> getBusinessYc(List<CompanySonTotalBill> sonTotalBills, List<Company> companyList) {

        Set<Integer> companyIdSet = new HashSet<Integer>();
        for (Company company : companyList) {
            companyIdSet.add(company.getId());
        }

        List<BusinessYc> businessYcList = new ArrayList<BusinessYc>();
        // 获取拥有 一次性业务的员工
        List<Member> members = memberMapper.queryMemberBusinessYc2(companyIdSet);
        // 获取公司下 一次性业务的子类险种集合
        List<BusinessItem> businessItems = businessItemMapper.queryBusinessItemByCompany2(companyIdSet, BusinessEnum.yiXiXingYeWu.ordinal());

        // 公司的一次性险
        if(null != businessItems){
            for (Company company : companyList) {
                for (BusinessItem businessItem : businessItems) {
                    if(company.getId().equals(businessItem.getCompanyId()) &&
                            null != businessItem.getIsCompany() &&businessItem.getIsCompany() == 0){
                        // 公司业务
                        BusinessYc businessYc = new BusinessYc();
                        businessYc.setBusinessItemId(businessItem.getId());
                        List<BusinessYcItem> businessYcItemList = new ArrayList<BusinessYcItem>();
                        BusinessYcItem businessYcItem = new BusinessYcItem();
                        businessYcItem.setMemberId(company.getId());
                        businessYcItem.setFlag(1);
                        businessYcItem.setCompanySonBillId(businessItem.getCompanySonBillId());
                        // 设置总账单ID
                        for (CompanySonTotalBill sonTotalBill : sonTotalBills) {
                            if(null != businessItem.getCompanySonBillId() && sonTotalBill.getCompanySonBillId().equals(businessItem.getCompanySonBillId())){
                                businessYcItem.setCompanySonTotalBillId(sonTotalBill.getId());
                            }
                        }
                        businessYcItem.setPrice(businessItem.getPrice());
                        businessYcItemList.add(businessYcItem);
                        businessYc.setBusinessYcItemList(businessYcItemList);
                        businessYcList.add(businessYc);
                    }
                }
                for (BusinessItem businessItem : businessItems) {
                    if (company.getId().equals(businessItem.getCompanyId())) {
                        BusinessYc businessYc = new BusinessYc();
                        businessYc.setBusinessItemId(businessItem.getId());
                        if(null != members){
                            List<BusinessYcItem> businessYcItemList = new ArrayList<BusinessYcItem>();
                            out : for (Member member : members) {
                                if (member.getCompanyId().equals(company.getId())) {
                                    BusinessYcItem businessYcItem = new BusinessYcItem();
                                    businessYcItem.setMemberId(member.getId());
                                    businessYcItem.setFlag(0);
                                    if(null != member.getBusinessList()){
                                        for (Business business : member.getBusinessList()) {
                                            double price = 0.0;
                                            if(BusinessEnum.yiXiXingYeWu.ordinal() == business.getId() && null != business.getBusinessItems()){
                                                for (BusinessItem item : business.getBusinessItems()) {
                                                    if(businessItem.getId().equals(item.getId())){
                                                        price = item.getPrice();
                                                        businessYcItem.setCompanySonBillId(item.getCompanySonBillId());
                                                        // 设置总账单ID
                                                        for (CompanySonTotalBill sonTotalBill : sonTotalBills) {
                                                            if(null != item.getCompanySonBillId() && sonTotalBill.getCompanySonBillId().equals(item.getCompanySonBillId())){
                                                                businessYcItem.setCompanySonTotalBillId(sonTotalBill.getId());
                                                            }
                                                        }
                                                        break ;
                                                    }
                                                }
                                                businessYcItem.setPrice(price);
                                                businessYcItemList.add(businessYcItem);
                                                continue out;
                                            }
                                        }
                                    }
                                }
                            }
                            businessYc.setBusinessYcItemList(businessYcItemList);
                        }
                        businessYcList.add(businessYc);
                    }
                }
                    }


        }
        return businessYcList;
    }

    private List<BusinessInsurance> getBusinessInsurance(List<CompanySonTotalBill> sonTotalBills, Company company,
                                                         Date now) {

        List<BusinessInsurance> businessInsuranceList = new ArrayList<BusinessInsurance>();
        // 获取当前月 应该生成账单的员工
        List<Member> members = memberMapper.queryMemberBusinessInsurance(company.getId(), now);
        // 计算当前月 员工的服务时长 根据公司的业务月序和业务月序开始时间来计算
        int serviceTime = 0; // 员工服务时长
        Date serviceStartTime = null; // 服务开始时间
        Date serviceEndTime = null; // 服务结束时间
        Date serviceDate = company.getBusinessStartTime();
        // 格式化时间为月时间
        now = DateUtil.setDate(now);
        while (true){
            serviceDate = DateUtil.setDate(serviceDate);
            if(now.getTime() == serviceDate.getTime()){
                serviceTime = company.getBusinessCycle();
                serviceStartTime = now;
                serviceEndTime = serviceDate;
                break;
            }
            Date temp = DateUtil.addDate(serviceDate, TimeField.Month.ordinal(), company.getBusinessCycle());
            if(now.getTime() > serviceDate.getTime() && now.getTime() <= temp.getTime()){
                serviceTime = DateUtil.countMonth(serviceDate,now);
                serviceStartTime = now;
                serviceEndTime =DateUtil.addDate(temp, TimeField.Month.ordinal(), -1);
                break;
            }
            serviceDate = DateUtil.addDate(serviceDate, TimeField.Month.ordinal(), company.getBusinessCycle());
        }
        // 获取公司下 商业险的子类险种集合
        List<BusinessItem> businessItems = businessItemMapper.queryBusinessItemByCompany(company.getId(), BusinessEnum.shangYeXian.ordinal());
        // 计算子险种价格
        for (Member member : members) {
            if(null != member.getBusinessList()){
                for (Business business : member.getBusinessList()) {
                    if(BusinessEnum.shangYeXian.ordinal() == business.getId() && null != business.getBusinessItems()){
                        BusinessInsurance businessInsurance = new BusinessInsurance();
                        businessInsurance.setMemberId(member.getId());
                        businessInsurance.setServiceEndTime(serviceEndTime);
                        businessInsurance.setServiceStartTime(serviceStartTime);
                        businessInsurance.setServiceTime(serviceTime);

                        List<BusinessInsuranceItem> itemList = new ArrayList<BusinessInsuranceItem>();

                        for (BusinessItem businessItem : businessItems) {
                            if(!business.getBusinessItems().contains(businessItem)){
                                // 如果员工的商业险子类 在 公司商业险子类中不存在， 则新增实体
                                BusinessInsuranceItem insuranceItem = new BusinessInsuranceItem();
                                // 判断按年收费 还是 按月收费
                                insuranceItem.setBusinessItemId(businessItem.getId());
                                insuranceItem.setPrice(0.0);

                                itemList.add(insuranceItem);
                            }
                        }

                        for (BusinessItem item : business.getBusinessItems()) {
                            BusinessInsuranceItem insuranceItem = new BusinessInsuranceItem();
                            // 设置当时的子账单ID
                            insuranceItem.setCompanySonBillId(item.getCompanySonBillId());
                            // 设置总账单ID
                            for (CompanySonTotalBill sonTotalBill : sonTotalBills) {
                                if(null != item.getCompanySonBillId() && sonTotalBill.getCompanySonBillId().equals(item.getCompanySonBillId())){
                                    insuranceItem.setCompanySonTotalBillId(sonTotalBill.getId());
                                }
                            }
                            //税费
                            Double taxPrice = 0.0;

                            // 判断按年收费 还是 按月收费
                            if(item.getChargeMethod() == 0){
                                // 如果是按年收费
                                item.setPrice(Double.valueOf(serviceTime) / 12 * item.getPrice());
                            }else if(item.getChargeMethod() == 1){
                                // 如果是按月收费
                                item.setPrice(serviceTime * item.getPrice());
                            }else{
                                item.setPrice(0.0);
                            }
                            if (item.getId() == 10) {
                                //服务费配置集合
                                List<CompanyCooperationMethod> methods = company.getMethods();
//                                        companyCooperationMethodMapper.queryCompanyCooperationMethod(company.getId());
                                // 计算税费开始 start
                                out:for (CompanyCooperationMethod method : methods) {
                                    if (member.getCompanyId().equals(company.getId()) &&
                                            member.getWaysOfCooperation().equals(method.getCooperationMethodId())) {
                                        taxPrice = item.getPrice() * method.getPercent() / 100;
//                                        item.setPrice(item.getPrice() * (1 + method.getPercent() / 100));
                                        break out;
                                    }
                                }
                                // 计算税费结束 end
                            }

                            insuranceItem.setTaxPrice(taxPrice);
                            insuranceItem.setBusinessItemId(item.getId());
                            insuranceItem.setPrice(item.getPrice());
                            itemList.add(insuranceItem);
                        }
                        businessInsurance.setBusinessInsuranceItemList(itemList);
                        businessInsuranceList.add(businessInsurance);
                    }
                }
            }
        }
        return businessInsuranceList;
    }
    private List<BusinessInsurance> getBusinessInsurance(List<CompanySonTotalBill> sonTotalBills, List<Company> companyList,
                                                         Map<Integer , List<Date>> dateMap) {


        List<Map<String ,Object>> companyDateMapList = new ArrayList<Map<String, Object>>();
        Iterator<Map.Entry<Integer , List<Date>>> iterator = dateMap.entrySet().iterator();
        Set<Integer> companyIdSet = new HashSet<Integer>();
        while (iterator.hasNext()) {
            Map.Entry<Integer , List<Date>> listEntry = iterator.next();
            Map<String ,Object> map = new HashMap<String, Object>();
            map.put("companyId",listEntry.getKey());
            map.put("serviceDate",listEntry.getValue().get(0));
            companyIdSet.add(listEntry.getKey());
            companyDateMapList.add(map);
        }
        List<BusinessInsurance> businessInsuranceList = new ArrayList<BusinessInsurance>();
        // 获取当前月 应该生成账单的员工
        List<Member> members = memberMapper.queryMemberBusinessInsurance2(companyDateMapList);

        // 获取公司下 商业险的子类险种集合
        List<BusinessItem> businessItems = businessItemMapper.queryBusinessItemByCompany2(companyIdSet, BusinessEnum.shangYeXian.ordinal());
        for (Company company : companyList) {
            // 计算当前月 员工的服务时长 根据公司的业务月序和业务月序开始时间来计算
            int serviceTime = 0; // 员工服务时长
            Date serviceStartTime = null; // 服务开始时间
            Date serviceEndTime = null; // 服务结束时间
            Date serviceDate = company.getBusinessStartTime();
            // 格式化时间为月时间
            Date now = DateUtil.setDate(dateMap.get(company.getId()).get(0));
            while (true){
                serviceDate = DateUtil.setDate(serviceDate);
                if(now.getTime() == serviceDate.getTime()){
                    serviceTime = company.getBusinessCycle();
                    serviceStartTime = now;
                    serviceEndTime = serviceDate;
                    break;
                }
                Date temp = DateUtil.addDate(serviceDate, TimeField.Month.ordinal(), company.getBusinessCycle());
                if(now.getTime() > serviceDate.getTime() && now.getTime() <= temp.getTime()){
                    serviceTime = DateUtil.countMonth(serviceDate,now);
                    serviceStartTime = now;
                    serviceEndTime =DateUtil.addDate(temp, TimeField.Month.ordinal(), -1);
                    break;
                }
                serviceDate = DateUtil.addDate(serviceDate, TimeField.Month.ordinal(), company.getBusinessCycle());
            }
            // 计算子险种价格
            for (Member member : members) {
                if(member.getCompanyId().equals(company.getId()) && null != member.getBusinessList()){
                    for (Business business : member.getBusinessList()) {
                        if(BusinessEnum.shangYeXian.ordinal() == business.getId() && null != business.getBusinessItems()){
                            BusinessInsurance businessInsurance = new BusinessInsurance();
                            businessInsurance.setMemberId(member.getId());
                            businessInsurance.setServiceEndTime(serviceEndTime);
                            businessInsurance.setServiceStartTime(serviceStartTime);
                            businessInsurance.setServiceTime(serviceTime);

                            List<BusinessInsuranceItem> itemList = new ArrayList<BusinessInsuranceItem>();

                            for (BusinessItem businessItem : businessItems) {
                                if(businessItem.getCompanyId().equals(company.getId()) && !business.getBusinessItems().contains(businessItem)){
                                    // 如果员工的商业险子类 在 公司商业险子类中不存在， 则新增实体
                                    BusinessInsuranceItem insuranceItem = new BusinessInsuranceItem();
                                    // 判断按年收费 还是 按月收费
                                    insuranceItem.setBusinessItemId(businessItem.getId());
                                    insuranceItem.setPrice(0.0);

                                    itemList.add(insuranceItem);
                                }
                            }

                            for (BusinessItem item : business.getBusinessItems()) {
                                BusinessInsuranceItem insuranceItem = new BusinessInsuranceItem();
                                // 设置当时的子账单ID
                                insuranceItem.setCompanySonBillId(item.getCompanySonBillId());
                                // 设置总账单ID
                                for (CompanySonTotalBill sonTotalBill : sonTotalBills) {
                                    if(null != item.getCompanySonBillId() && sonTotalBill.getCompanySonBillId().equals(item.getCompanySonBillId())){
                                        insuranceItem.setCompanySonTotalBillId(sonTotalBill.getId());
                                    }
                                }
                                //税费
                                Double taxPrice = 0.0;

                                // 判断按年收费 还是 按月收费
                                if(item.getChargeMethod() == 0){
                                    // 如果是按年收费
                                    item.setPrice(Double.valueOf(serviceTime) / 12 * item.getPrice());
                                }else if(item.getChargeMethod() == 1){
                                    // 如果是按月收费
                                    item.setPrice(serviceTime * item.getPrice());
                                }else{
                                    item.setPrice(0.0);
                                }
                                if (item.getId() == 10) {
                                    //服务费配置集合
                                    List<CompanyCooperationMethod> methods = company.getMethods();
//                                        companyCooperationMethodMapper.queryCompanyCooperationMethod(company.getId());
                                    // 计算税费开始 start
                                    out:for (CompanyCooperationMethod method : methods) {
                                        if (member.getCompanyId().equals(company.getId()) &&
                                                member.getWaysOfCooperation().equals(method.getCooperationMethodId())) {
                                            taxPrice = item.getPrice() * method.getPercent() / 100;
//                                        item.setPrice(item.getPrice() * (1 + method.getPercent() / 100));
                                            break out;
                                        }
                                    }
                                    // 计算税费结束 end
                                }

                                insuranceItem.setTaxPrice(taxPrice);
                                insuranceItem.setBusinessItemId(item.getId());
                                insuranceItem.setPrice(item.getPrice());
                                itemList.add(insuranceItem);
                            }
                            businessInsurance.setBusinessInsuranceItemList(itemList);
                            businessInsuranceList.add(businessInsurance);
                        }
                    }
                }
            }
        }
        return businessInsuranceList;
    }

    /**
     * 汇总总账单
     * @param companyList
     * @param companySonBills
     * @return
     */
    private List<CompanySonTotalBill> getCompanySonTotalBills(List<CompanySonBill> companySonBills,
                                                              List<Company> companyList,
                                                              Map<Integer , List<Date>> dateMap) throws ParseException {

        Set<Integer> companySonBillIdSet = new HashSet<Integer>();
        for (CompanySonBill bill : companySonBills) {
            companySonBillIdSet.add(bill.getId());
        }
        //根据子账单获取最后一条汇总账单
        List<CompanySonTotalBill> finallyCompanySonTotalBillList = companySonTotalBillMapper.getFinallyByCompanySonBillId2(companySonBillIdSet);
        //汇总账单
        List<CompanySonTotalBill> companySonTotalBills = new ArrayList<CompanySonTotalBill>();
        List<CompanySonBill> companySonBillListCopy = new ArrayList<CompanySonBill>(companySonBills);
        for (int i = 0; i < companyList.size(); i++) {
            String billNumber = Timestamp.timesTamp2(dateMap.get(companyList.get(i).getId()).get(0),"yyyy-MM").toString();
            int j = 0;
            while (companySonBillListCopy.size() > 0 && j < companySonBillListCopy.size()) {
                CompanySonBill bill = companySonBillListCopy.get(j);
                Integer num = 1;
                if (bill.getCompanyId().equals(companyList.get(i).getId())){
                    CompanySonTotalBill companySonTotalBill = new CompanySonTotalBill();
                    companySonTotalBill.setBillMonth(Timestamp.parseDate3(dateMap.get(companyList.get(i).getId()).get(0),"yyyy-MM"));
                    if (Timestamp.parseDate3(dateMap.get(companyList.get(i).getId()).get(0),"yyyy-MM").
                            compareTo(Timestamp.parseDate3(dateMap.get(companyList.get(i).getId()).get(1),"yyyy-MM")) == 0) {
                        companySonTotalBill.setIsCreateBillMonth(1);
                    } else {
                        companySonTotalBill.setIsCreateBillMonth(0);
                    }
                    companySonTotalBill.setBillNumber(billNumber);
                    companySonTotalBill.setItemBillNumber(billNumber + String.format("%02d",num));
                    companySonTotalBill.setUserId(companyList.get(i).getBeforeService());
                    companySonTotalBill.setCompanySonBillId(bill.getId());
                    companySonTotalBill.setCompanyId(bill.getCompanyId());
                    Boolean flag = true;
                    for (int i1 = 0; i1 < finallyCompanySonTotalBillList.size(); i1++) {
                        if (bill.getId().equals(finallyCompanySonTotalBillList.get(i1).getId())) {
                            companySonTotalBill.setLastMonthBalance(null == finallyCompanySonTotalBillList.get(i1).getMonthBalance() ? 0.0 : finallyCompanySonTotalBillList.get(i1).getMonthBalance());
                            companySonTotalBill.setTotalPrice(null == finallyCompanySonTotalBillList.get(i1).getLastMonthBalance() ? 0.0 :-finallyCompanySonTotalBillList.get(i1).getLastMonthBalance());
                            flag = false;
                            continue;
                        }
                    }
                    if (flag) {
                        companySonTotalBill.setLastMonthBalance(0.0);
                        companySonTotalBill.setTotalPrice(0.0);
                    }
//                    //根据子账单获取最后一条汇总账单
//                    CompanySonTotalBill finallyCompanySonTotalBill = companySonTotalBillMapper.getFinallyByCompanySonBillId(bill.getId());
//                    //获取上月稽核余额
//                    if (null != finallyCompanySonTotalBill) {
//                        companySonTotalBill.setLastMonthBalance(null == finallyCompanySonTotalBill.getMonthBalance() ? 0.0 : finallyCompanySonTotalBill.getMonthBalance());
//                        companySonTotalBill.setTotalPrice(null == finallyCompanySonTotalBill.getLastMonthBalance() ? 0.0 :-finallyCompanySonTotalBill.getLastMonthBalance());
//                    } else {
//                        companySonTotalBill.setLastMonthBalance(0.0);
//                        companySonTotalBill.setTotalPrice(0.0);
//                    }
                    companySonTotalBills.add(companySonTotalBill);
                    companySonBillListCopy.remove(j);
                    j --;
                }
                j ++;
            }
        }
        return companySonTotalBills;
    }


    /**
     * 封装获取服务费
     * @param monthServiceFees
     * @param companySonTotalBills
     * @param company
     * @param source 来源 0：生成账单  1：合并账单
     */
    public void getServiceFree(List<MonthServiceFee> monthServiceFees,
                               List<CompanySonTotalBill> companySonTotalBills,
                                Company company ,Integer source,
                               List<MemberBusinessUpdateRecordItem> recordItems) throws ParseException {
        //服务月
        Date serviceMonth = new Date();
        //服务费配置集合
        List<CompanyCooperationMethod> methods =
                companyCooperationMethodMapper.queryCompanyCooperationMethod(company.getId());
        if (null == methods || methods.size() < 1) {
            return;
        }

        for (CompanyCooperationMethod method : methods) {
            Boolean isCanServiceFree = true;
            // 计算是否到 生成服务费月份
            if(null != method.getServiceFeeStartTime() && null != method.getServiceFeeCycle()){
                final int cycle = method.getServiceFeeCycle(); // 服务月序周期
                Date now = DateUtil.setDate(new Date());
                Date serviceDate = method.getServiceFeeStartTime();
                int i = 0;
                while (true){
                    serviceDate = DateUtil.addDate(serviceDate, TimeField.Month.ordinal(), i);
                    serviceDate = DateUtil.setDate(serviceDate);
                    if(now.getTime() == serviceDate.getTime()){
                        isCanServiceFree = true;
                        break;
                    }
                    if(serviceDate.getTime() > now.getTime()){
                        isCanServiceFree = false;
                        break;
                    }
                    i += cycle;
                }
            }
            if (isCanServiceFree) {
                //服务月续
                Integer serviceFeeCycle = method.getServiceFeeCycle();
                if (source == 1) {
                    serviceFeeCycle = 1;
                }
                for (int i = 0 ; i < serviceFeeCycle ; i ++) {
                    switch (method.getServiceFeeConfigId()) {
                        case 1 :
                            //按固定额
                            for (CompanySonTotalBill bill : companySonTotalBills) {
                                MonthServiceFee monthServiceFee = new MonthServiceFee();
                                monthServiceFee.setCompanySonTotalBillId(bill.getId());
                                //服务费
                                Double memberFee = 0.0;
                                //派遣方式 0：普通 1：派遣  2：外包
                                switch (method.getCooperationMethodId()) {
                                    case 0 :
                                        List<Member> memberCommons = memberMapper.getMemberBusinessItem(bill.getCompanySonBillId(),0);
                                        memberFee = getMemberFeeMemberNum(method, memberCommons, memberFee);
                                        break;
                                    case 1 :
                                        List<Member> memberDispatchs = memberMapper.getMemberBusinessItem(bill.getCompanySonBillId(),1);
                                        memberFee = getMemberFeeMemberNum(method, memberDispatchs, memberFee);
                                        break;
                                    case 2 :
                                        List<Member> memberEpibolys = memberMapper.getMemberBusinessItem(bill.getCompanySonBillId(),2);
                                        memberFee = getMemberFeeMemberNum(method, memberEpibolys, memberFee);
                                        break;
                                }
                                if (method.getIsPercent() == 1) {
                                    memberFee = (1 + method.getPercent() / 100 ) * memberFee;
                                }
                                //服务费
                                monthServiceFee.setServiceFee(null == company.getServiceFeeMax() && null == company.getServiceFeeMin() ? memberFee :
                                        company.getServiceFeeMax() < memberFee ? company.getServiceFeeMax() :
                                                company.getServiceFeeMin() > memberFee ? company.getServiceFeeMin() : memberFee);
                                //服务月
                                monthServiceFee.setMonth(serviceMonth);
                                monthServiceFees.add(monthServiceFee);
                            }
                            break;
                        case 2 :
                            for (CompanySonTotalBill bill : companySonTotalBills) {
                                //按人数阶梯
                                MonthServiceFee monthServiceFee = new MonthServiceFee();
                                monthServiceFee.setCompanySonTotalBillId(bill.getId());
                                //临时记录人数
                                Integer temporaryCount = 0;
                                //临时服务费
                                Double memberFee = 0.0;
                                //类型人数
                                Integer memberCount = 0;
                                //派遣方式 0：普通 1：派遣  2：外包
                                switch (method.getCooperationMethodId()) {
                                    case 0 :
                                        List<Member> memberCommons = memberMapper.getMemberBusinessItem(bill.getCompanySonBillId(),0);
                                        memberCount = getMemberFeeMemberPayNum(method, memberCommons, memberCount);
                                        break;
                                    case 1 :
                                        List<Member> memberDispatchs = memberMapper.getMemberBusinessItem(bill.getCompanySonBillId(),1);
                                        memberCount = getMemberFeeMemberPayNum(method, memberDispatchs, memberCount);
                                        break;
                                    case 2 :
                                        List<Member> memberEpibolys = memberMapper.getMemberBusinessItem(bill.getCompanySonBillId(),2);
                                        memberCount = getMemberFeeMemberPayNum(method, memberEpibolys, memberCount);
                                        break;
                                }

                                if (memberCount > 0 ){
                                    if (method.getServiceFeeList().size() > 0) {
                                        //升序排序
                                        Collections.sort(method.getServiceFeeList(), new Comparator<CompanyCooperationServiceFee>() {
                                            public int compare(CompanyCooperationServiceFee o1, CompanyCooperationServiceFee o2) {
                                                return o1.getExtent() - o2.getExtent();
                                            }
                                        });
                                    }

                                    int j = 0;
                                    for (CompanyCooperationServiceFee fee : method.getServiceFeeList()) {
                                        if (j == 0 && fee.getExtent() > memberCount) {
                                            //小于最低人数
                                            memberFee = memberCount * fee.getPrice();
                                            break;
                                        } else if(method.getServiceFeeList().get(method.getServiceFeeList().size() - 1).getExtent() < memberCount) {
                                            //大于最高人数
                                            memberFee = method.getServiceFeeList().get(method.getServiceFeeList().size() - 1).getPrice() * memberCount;
                                            break;
                                        } else  {
                                            if (fee.getExtent() > memberCount && memberCount > temporaryCount) {
                                                //两者之间
                                                memberFee = method.getServiceFeeList().get(j-1).getPrice() * memberCount;
                                                break;
                                            }
                                        }
                                        temporaryCount = fee.getExtent();
                                        j ++;
                                    }
                                    if (method.getIsPercent() == 1) {
                                        memberFee = (1 + method.getPercent() / 100 ) * memberFee;
                                    }
                                    if (memberFee != 0) {
                                        //服务费 应收金额大于公司最高金额 直接用最高 同理 小于最低 直接用最低
                                        monthServiceFee.setServiceFee(null == company.getServiceFeeMax() && null == company.getServiceFeeMin() ? memberFee :
                                                company.getServiceFeeMax() < memberFee ? company.getServiceFeeMax() :
                                                        company.getServiceFeeMin() > memberFee ? company.getServiceFeeMin() : memberFee);
                                        //服务月
                                        monthServiceFee.setMonth(serviceMonth);
                                        monthServiceFees.add(monthServiceFee);
                                    }

                                }

                            }
                            break;
                        case 3 :
                            //服务类别
                            for (CompanySonTotalBill bill : companySonTotalBills) {
                                MonthServiceFee monthServiceFee = new MonthServiceFee();
                                monthServiceFee.setCompanySonTotalBillId(bill.getId());
                                //员工及业务
                                List<Member> members = memberMapper.getBusinessIds(company.getId());
                                //临时服务费
                                Double memberFee = 0.0;
                                for (Member member : members) {
                                    if (method.getCooperationMethodId() == 0 && member.getWaysOfCooperation() == 0) {
                                        for (CompanyCooperationBusinessServiceFee fee : method.getBusinessServiceFeeList()) {
                                            if (fee.getBusinessIds().equals(member.getBusinessIds())) {
                                                memberFee += fee.getPrice();
                                            }
                                        }
                                    }
                                    if (method.getCooperationMethodId() == 1 && member.getWaysOfCooperation() == 1) {
                                        for (CompanyCooperationBusinessServiceFee fee : method.getBusinessServiceFeeList()) {
                                            if (fee.getBusinessIds().equals(member.getBusinessIds())) {
                                                memberFee += fee.getPrice();
                                            }
                                        }
                                    }
                                    if (method.getCooperationMethodId() == 2 && member.getWaysOfCooperation() == 2) {
                                        for (CompanyCooperationBusinessServiceFee fee : method.getBusinessServiceFeeList()) {
                                            if (fee.getBusinessIds().equals(member.getBusinessIds())) {
                                                memberFee += fee.getPrice();
                                            }
                                        }
                                    }
                                }
                                if (method.getIsPercent() == 1) {
                                    memberFee = (1 + method.getPercent() / 100 ) * memberFee;
                                }
                                if (memberFee != 0) {
                                    //服务费
                                    monthServiceFee.setServiceFee(null == company.getServiceFeeMax() && null == company.getServiceFeeMin() ? memberFee :
                                            company.getServiceFeeMax() < memberFee ? company.getServiceFeeMax() :
                                                    company.getServiceFeeMin() > memberFee ? company.getServiceFeeMin() : memberFee);
                                    //服务月
                                    monthServiceFee.setMonth(serviceMonth);
                                    monthServiceFees.add(monthServiceFee);
                                }

                            }
                            break;
                        case 4 :
                            //服务区
                            for (CompanySonTotalBill bill : companySonTotalBills) {
                                MonthServiceFee monthServiceFee = new MonthServiceFee();
                                monthServiceFee.setCompanySonTotalBillId(bill.getId());
                                //员工及业务
                                List<Member> members = memberMapper.getByCompanyId(company.getId());
                                //临时服务费
                                Double memberFee = 0.0;
                                for (Member member : members) {
                                    if (method.getCooperationMethodId() == 0 && member.getWaysOfCooperation() == 0) {
                                        for (CompanyCooperationServicePayPlace place : method.getPayPlaceList()) {
                                            if (place.getCityId().equals(member.getCityId())) {
                                                memberFee += getMemberFeeMemberPayNum(place.getPrice(),member,memberFee);
                                            }
                                        }
                                    }
                                    if (method.getCooperationMethodId() == 1 && member.getWaysOfCooperation() == 1) {
                                        for (CompanyCooperationServicePayPlace place : method.getPayPlaceList()) {
                                            if (place.getCityId().equals(member.getCityId())) {
                                                memberFee += getMemberFeeMemberPayNum(place.getPrice(),member,memberFee);
                                            }
                                        }
                                    }
                                    if (method.getCooperationMethodId() == 2 && member.getWaysOfCooperation() == 2) {
                                        for (CompanyCooperationServicePayPlace place : method.getPayPlaceList()) {
                                            if (place.getCityId().equals(member.getCityId())) {
                                                memberFee += getMemberFeeMemberPayNum(place.getPrice(),member,memberFee);
                                            }
                                        }
                                    }
                                }
                                if (method.getIsPercent() == 1) {
                                    memberFee = (1 + method.getPercent() / 100 ) * memberFee;
                                }

                                if (memberFee != 0) {
                                    //服务费
                                    monthServiceFee.setServiceFee(null == company.getServiceFeeMax() && null == company.getServiceFeeMin() ? memberFee :
                                            company.getServiceFeeMax() < memberFee ? company.getServiceFeeMax() :
                                                    company.getServiceFeeMin() > memberFee ? company.getServiceFeeMin() : memberFee);
                                    //服务月
                                    monthServiceFee.setMonth(serviceMonth);
                                    monthServiceFees.add(monthServiceFee);
                                }

                            }
                            break;
                        case 5 :
                            //异动量
                            for (CompanySonTotalBill bill : companySonTotalBills) {
                                MonthServiceFee monthServiceFee = new MonthServiceFee();
                                monthServiceFee.setCompanySonTotalBillId(bill.getId());
                                List<Member> members = memberMapper.getMemberBusinessUpdateRecordItem(company.getId());
                                //临时服务费
                                Double memberFee = 0.0;
                                for (Member member : members) {
                                    if (method.getCooperationMethodId().equals(member.getWaysOfCooperation())) {
                                        memberFee += member.getRecordItems().size() *
                                                method.getServiceFeeList().get(0).getPrice();
                                    }
                                }
                                if (method.getIsPercent() == 1) {
                                    memberFee = (1 + method.getPercent() / 100 ) * memberFee;
                                }
                                if (memberFee != 0) {
                                    //服务费
                                    monthServiceFee.setServiceFee(null == company.getServiceFeeMax() && null == company.getServiceFeeMin() ? memberFee :
                                            company.getServiceFeeMax() < memberFee ? company.getServiceFeeMax() :
                                                    company.getServiceFeeMin() > memberFee ? company.getServiceFeeMin() : memberFee);
                                    //服务月
                                    monthServiceFee.setMonth(serviceMonth);
                                    monthServiceFees.add(monthServiceFee);
                                }

                            }

                            break;
                        case 6 :
                            //整体打包
                            for (CompanySonTotalBill bill : companySonTotalBills) {
                                MonthServiceFee monthServiceFee = new MonthServiceFee();
                                monthServiceFee.setCompanySonTotalBillId(bill.getId());
                                Double memberFee = 0.0;
                                if (null != method.getServiceFeeList() && method.getServiceFeeList().size() > 0) {
                                    memberFee = method.getServiceFeeList().get(0).getPrice();
                                    if (method.getIsPercent() == 1) {
                                        memberFee = (1 + method.getPercent() / 100 ) * memberFee;
                                    }
                                }
                                if (memberFee != 0) {
                                    //服务费
                                    monthServiceFee.setServiceFee(null == company.getServiceFeeMax() && null == company.getServiceFeeMin() ? memberFee :
                                            company.getServiceFeeMax() < memberFee ? company.getServiceFeeMax() :
                                                    company.getServiceFeeMin() > memberFee ? company.getServiceFeeMin() : memberFee);
                                    //服务月
                                    monthServiceFee.setMonth(serviceMonth);
                                    monthServiceFees.add(monthServiceFee);
                                }

                            }
                            break;
                        case 7 :
                            //按人数阶梯式整体
                            for (CompanySonTotalBill bill : companySonTotalBills) {
                                //按人数阶梯
                                MonthServiceFee monthServiceFee = new MonthServiceFee();
                                monthServiceFee.setCompanySonTotalBillId(bill.getId());
                                //临时记录人数
                                Integer temporaryCount = 0;
                                //临时服务费
                                Double memberFee = 0.0;
                                //类型人数
                                Integer memberCount = 0;

                                //派遣方式 0：普通 1：派遣  2：外包
                                switch (method.getCooperationMethodId()) {
                                    case 0 :
                                        List<Member> memberCommons = memberMapper.getMemberBusinessItem(bill.getCompanySonBillId(),0);
                                        memberCount = getMemberFeeMemberPayNum(method, memberCommons, memberCount);
                                        break;
                                    case 1 :
                                        List<Member> memberDispatchs = memberMapper.getMemberBusinessItem(bill.getCompanySonBillId(),1);
                                        memberCount = getMemberFeeMemberPayNum(method, memberDispatchs, memberCount);
                                        break;
                                    case 2 :
                                        List<Member> memberEpibolys = memberMapper.getMemberBusinessItem(bill.getCompanySonBillId(),2);
                                        memberCount = getMemberFeeMemberPayNum(method, memberEpibolys, memberCount);
                                        break;
                                }

                                if (memberCount > 0) {
                                    int j = 0;
                                    if (method.getServiceFeeList().size() > 0) {
                                        //升序排序
                                        Collections.sort(method.getServiceFeeList(), new Comparator<CompanyCooperationServiceFee>() {
                                            public int compare(CompanyCooperationServiceFee o1, CompanyCooperationServiceFee o2) {
                                                return o1.getExtent() - o2.getExtent();
                                            }
                                        });
                                    }
                                    for (CompanyCooperationServiceFee fee : method.getServiceFeeList()) {
                                        if (j == 0 && fee.getExtent() > memberCount) {
                                            //小于最低人数
                                            memberFee = fee.getPrice();
                                            break;
                                        } else if(method.getServiceFeeList().get(method.getServiceFeeList().size() - 1).getExtent() < memberCount) {
                                            //大于最高人数
                                            memberFee = method.getServiceFeeList().get(method.getServiceFeeList().size() - 1).getPrice();
                                            break;
                                        } else  {
                                            if (fee.getExtent() > memberCount && memberCount > temporaryCount) {
                                                //两者之间
                                                memberFee = method.getServiceFeeList().get(j-1).getPrice();
                                                break;
                                            }
                                        }
                                        temporaryCount = fee.getExtent();
                                        j ++;
                                    }
                                    if (method.getIsPercent() == 1) {
                                        memberFee = (1 + method.getPercent() / 100 ) * memberFee;
                                    }
                                    //服务费 应收金额大于公司最高金额 直接用最高 同理 小于最低 直接用最低
                                    monthServiceFee.setServiceFee(null == company.getServiceFeeMax() && null == company.getServiceFeeMin() ? memberFee :
                                            company.getServiceFeeMax() < memberFee ? company.getServiceFeeMax() :
                                                    company.getServiceFeeMin() > memberFee ? company.getServiceFeeMin() : memberFee);
                                    //服务月
                                    monthServiceFee.setMonth(serviceMonth);
                                    monthServiceFees.add(monthServiceFee);
                                }
                            }
                            break;
                    }
                    serviceMonth = DateUtil.addDate(serviceMonth, TimeField.Month.ordinal(),i+1);
                }
            }


        }




    }




    /**
     * 服务费 按人头收费 获取
     * @param method
     * @param members
     * @param memberFee
     * @return
     * @throws ParseException
     */
    public Double getMemberFeeMemberNum(CompanyCooperationMethod method, List<Member> members, Double memberFee) throws ParseException {
        for (Member member : members) {
            //此员工为第一次缴纳 需要补交
            int payNum1 = 1;
            int payNum2 = 1;
            Date newDate = new Date();
            for (MemberBusinessItem item : member.getMemberBusinessItems()) {
                if (item.getIsFirstPay() == 1) {
                    Date serviceNowYM1 = item.getServiceStartTime();
                    if (item.getType() == 0) {
                        payNum1 = 0;
                    } else {
                        payNum2 = 0;
                    }
                    while (true) {
                        if (DateUtil.daysBetween(serviceNowYM1,newDate) > 0) {
                            if (item.getType() == 0) {
                                payNum1 ++;
                            } else {
                                payNum2 ++;
                            }
                            serviceNowYM1 = DateUtil.addDate(serviceNowYM1, TimeField.Month.ordinal(),1);
                        } else {
                            break;
                        }
                    }
                }
            }
            if (payNum1 >= payNum2) {
                memberFee += payNum1 * method.getServiceFeeList().get(0).getPrice();
            } else {
                memberFee += payNum2 * method.getServiceFeeList().get(0).getPrice();
            }

        }
        return memberFee;
    }
    /**
     * 服务费 阶梯式收费 获取缴纳次数
     * @param method
     * @param members
     * @param payNum
     * @return
     * @throws ParseException
     */
    public Integer getMemberFeeMemberPayNum(CompanyCooperationMethod method, List<Member> members, Integer payNum) throws ParseException {
        for (Member member : members) {
            //此员工为第一次缴纳 需要补交
            int payNum1 = 1;
            int payNum2 = 1;
            Date newDate = new Date();
            for (MemberBusinessItem item : member.getMemberBusinessItems()) {
                if (item.getIsFirstPay() == 1) {
                    Date serviceNowYM1 = item.getServiceStartTime();
                    if (item.getType() == 0) {
                        payNum1 = 0;
                    } else {
                        payNum2 = 0;
                    }
                    while (true) {
                        if (DateUtil.daysBetween(serviceNowYM1,newDate) > 0) {
                            if (item.getType() == 0) {
                                payNum1 ++;
                            } else {
                                payNum2 ++;
                            }
                            serviceNowYM1 = DateUtil.addDate(serviceNowYM1, TimeField.Month.ordinal(),1);
                        } else {
                            break;
                        }
                    }
                }
            }
            if (payNum1 >= payNum2) {
                payNum += payNum1;
            } else {
                payNum += payNum2;
            }

        }
        return payNum;
    }
    /**
     * 服务费 服务区收费 获取个人缴纳费用
     * @param price
     * @param member
     * @param memberFee
     * @return
     * @throws ParseException
     */
    public Double getMemberFeeMemberPayNum(Double price, Member member, Double memberFee) throws ParseException {
        //此员工为第一次缴纳 需要补交
        int payNum = 1;
        int payNum1 = 1;
        int payNum2 = 1;
        Date newDate = new Date();
        for (MemberBusinessItem item : member.getMemberBusinessItems()) {
            if (item.getIsFirstPay() == 1) {
                Date serviceNowYM1 = item.getServiceStartTime();
                if (item.getType() == 0) {
                    payNum1 = 0;
                } else {
                    payNum2 = 0;
                }
                while (true) {
                    if (DateUtil.daysBetween(serviceNowYM1,newDate) > 0) {
                        if (item.getType() == 0) {
                            payNum1 ++;
                        } else {
                            payNum2 ++;
                        }
                        serviceNowYM1 = DateUtil.addDate(serviceNowYM1, TimeField.Month.ordinal(),1);
                    } else {
                        break;
                    }
                }
            }
        }
        if (payNum1 >= payNum2) {
            payNum += payNum1;
        } else {
            payNum += payNum2;
        }

        return payNum * price;
    }

    /**
     * 更新不为空字段
     * @param companySonBillItem
     */
    public void update(CompanySonBillItem companySonBillItem) {
        companySonBillItemMapper.update(companySonBillItem);
    }


    /**
     * 更新状态
     * @param companySonTotalBillId
     * @param status 状态 0：已提交  1：仍需调整  2：确认账单
     * @throws Exception
     */
    @Transactional
    public void updateStatus(Integer companySonTotalBillId ,Integer status) throws Exception{
        CompanySonTotalBill companySonTotalBill = companySonTotalBillMapper.info(companySonTotalBillId);
        companySonTotalBill.setStatus(status);
        companySonTotalBill.setId(companySonTotalBillId);
        if (status == 2) {
            companySonTotalBill.setOkTime(new Date());
            // 获取查看是否所有的账单都已经确认完，如果确认完新增确认数据，否则不新增统计数据
            List<CompanySonTotalBill> companySonTotalBills = companySonTotalBillMapper.listByCompanyIdAndBillMonth(companySonTotalBill.getCompanyId(), companySonTotalBill.getBillMonth());
            boolean isFinish = false;
            for (CompanySonTotalBill sonTotalBill : companySonTotalBills) {
                if(null == sonTotalBill.getStatus() || sonTotalBill.getStatus() == 0){
                    isFinish = true;
                    break;
                }
            }
            if(!isFinish){
                // 如果全部完成
                CompanyBillCount companyBillCount = new CompanyBillCount();
                companyBillCount.setBillMonth(companySonTotalBill.getBillMonth());
                companyBillCount.setCompanyId(companySonTotalBill.getCompanyId());
                companyBillCount.setType(0); //  账单确认
                companyBillCountMapper.add(companyBillCount);
            }

            // TODO: 2018/1/15 实做型账单，业务月序不为1，如何判断是否稽核是否完成   待讨论后继续完成  （经讨论：实做大月1 不做处理）
            List<SocialSecurityInfo> socialSecurityInfoList = socialSecurityInfoMapper.getByCompanyAndBillMonth(companySonTotalBill.getCompanyId(),companySonTotalBill.getBillMonth());
            List<ReservedFundsInfo> reservedFundsInfoList = reservedFundsInfoMapper.getByCompanyAndBillMonth(companySonTotalBill.getCompanyId(),companySonTotalBill.getBillMonth());
            if (socialSecurityInfoList.size() == 0 && reservedFundsInfoList.size() == 0) {
                companySonBillItemMapper.updateList3(null,null,companySonTotalBillId);
            } else {
                if (socialSecurityInfoList.size() > 0) {
                    socialSecurityInfoMapper.updateAffirm(companySonTotalBill.getBillMonth(),null,companySonTotalBill.getCompanySonBillId());
                }
                if (reservedFundsInfoList.size() > 0) {
                    reservedFundsInfoMapper.updateAffirm(companySonTotalBill.getBillMonth(),null,companySonTotalBill.getCompanySonBillId());
                }
            }
/*            socialSecurityInfoMapper.updateAffirm(companySonTotalBill.getBillMonth(),null,companySonTotalBill.getCompanySonBillId());
            reservedFundsInfoMapper.updateAffirm(companySonTotalBill.getBillMonth(),null,companySonTotalBill.getCompanySonBillId());*/


        }
        if(status == 0){
            // 取消确认，删除统计数据中的确认数据
            companyBillCountMapper.del(companySonTotalBill.getCompanyId(),companySonTotalBill.getBillMonth(),0);
        }
        companySonTotalBillMapper.update(companySonTotalBill);
    }


    public void delBill(Integer companyId,Date billMonth){

        // 通过 公司和账单月  查询总帐单ID集合
        List<CompanySonTotalBill> bills = companySonTotalBillMapper.listByCompanyIdAndBillMonth(companyId, billMonth);

        if(null != bills && bills.size() > 0){
            Integer[] companySonTotalBillIds = new Integer[bills.size()];
            for (int i = 0; i < bills.size(); i++) {
                companySonTotalBillIds[i] = bills.get(i).getId();
            }
            //异动量回滚
            memberBusinessUpdateRecordItemMapper.update2(companyId,billMonth);
            // 重新生成账单时， 删除其他数据
            //删除缴纳记录
            memberMonthPayBusinessMapper.delList(billMonth,companyId);
//            // 更新工资信息
//            salaryInfoMapper.updateListByCompanySonTotalBillIds(companySonTotalBillIds);
            // 删除工资信息
            salaryInfoMapper.delSalaryInfoByCompanySonTotalBillId(companySonTotalBillIds);
            //更新拷盘数据制空子账单子类id
            socialSecurityInfoMapper.updateList2(companySonTotalBillIds);
            // 删除社保险种明细
            socialSecurityInfoItemMapper.delList(companySonTotalBillIds);
            // 删除社保
            socialSecurityInfoMapper.del(companySonTotalBillIds);
            //更新拷盘数据制空子账单子类id
            reservedFundsInfoMapper.updateList2(companySonTotalBillIds);
            // 删除公积金
            reservedFundsInfoMapper.del(companySonTotalBillIds);
            //更新稽核后的数据
//                    companySonBillItemMapper.updateList2(companySonTotalBillIds);
            // 删除月总账单 子账单子类
            companySonBillItemMapper.deleteByCompanySonTotalBillId(companySonTotalBillIds);
            //删除服务费明细
            monthServiceFeeDetailMapper.delete(companySonTotalBillIds);
            // 删除服务费
            monthServiceFeeMapper.delete(companySonTotalBillIds);
            // 删除汇总账单
            companySonTotalBillMapper.delete(companySonTotalBillIds);
            // 删除专项服务
            List<BusinessInsurance> list = businessInsuranceMapper.queryBusinessInsuranceIds(companySonTotalBillIds);
            if(null != list && list.size() > 0){
                List<Integer> ids = new ArrayList<Integer>();
                for (BusinessInsurance insurance : list) {
                    ids.add(insurance.getId());
                }
                if(ids.size() > 0){
                    businessInsuranceMapper.del(ids);
                    businessInsuranceItemMapper.delBusinessInsuranceItemByIds(ids);
                }
            }
            // 删除一次性险
            List<BusinessYc> businessYcList = businessYcMapper.queryBusinessYc3(companySonTotalBillIds);
            if(businessYcList.size() > 0){
                List<Integer> ycIds = new ArrayList<Integer>();
                List<Integer> ycItemsIds = new ArrayList<Integer>();
                for (BusinessYc businessYc : businessYcList) {
                    ycIds.add(businessYc.getId());
                    if(null != businessYc.getBusinessYcItemList() && businessYc.getBusinessYcItemList().size() > 0){
                        for (BusinessYcItem ycItem : businessYc.getBusinessYcItemList()) {
                            ycItemsIds.add(ycItem.getId());
                        }
                    }

                }
                businessYcMapper.del(ycIds);
                if (ycItemsIds.size() > 0) {
                    businessYcItemMapper.del(ycItemsIds);
                }
            }
        }


    }

    /**
     * 更新状态
     * @param companyId 公司id
     * @param billMonth 账单月
     * @param status 状态 0：已提交  1：仍需调整  2：确认账单
     * @throws Exception
     */
    @Transactional
    public void updateStatus2(Integer companyId,Date billMonth ,Integer status,User user,Double amount) throws Exception{
        CompanySonTotalBill companySonTotalBill = new CompanySonTotalBill();
        companySonTotalBill.setStatus(status);
        companySonTotalBill.setCompanyId(companyId);
        companySonTotalBill.setBillMonth(billMonth);
        Company company = companyMapper.queryCompanyName(companyId);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        if (status == 2) {
            companySonTotalBill.setOkTime(new Date());
            CompanyBillCount companyBillCount = new CompanyBillCount();
            companyBillCount.setBillMonth(billMonth);
            companyBillCount.setCompanyId(companyId);
            companyBillCount.setType(0); //  账单确认
            companyBillCountMapper.add(companyBillCount);
           /* socialSecurityInfoMapper.updateAffirm(companySonTotalBill.getBillMonth(),companyId,null);
            reservedFundsInfoMapper.updateAffirm(companySonTotalBill.getBillMonth(),companyId,null);*/
            // TODO: 2018/1/15 实做型账单，业务月序不为1，如何判断是否稽核是否完成   待讨论后继续完成
            List<SocialSecurityInfo> socialSecurityInfoList = socialSecurityInfoMapper.getByCompanyAndBillMonth(companySonTotalBill.getCompanyId(),companySonTotalBill.getBillMonth());
            List<ReservedFundsInfo> reservedFundsInfoList = reservedFundsInfoMapper.getByCompanyAndBillMonth(companySonTotalBill.getCompanyId(),companySonTotalBill.getBillMonth());
            if (socialSecurityInfoList.size() == 0 && reservedFundsInfoList.size() == 0) {
                companySonBillItemMapper.updateList3(billMonth,companyId,null);
            } else {
                if (socialSecurityInfoList.size() > 0) {
                    socialSecurityInfoMapper.updateAffirm(companySonTotalBill.getBillMonth(),companyId,null);
                }
                if (reservedFundsInfoList.size() > 0) {
                    reservedFundsInfoMapper.updateAffirm(companySonTotalBill.getBillMonth(),companyId,null);
                }
            }
            // 如果应收金额为0，确认账单自动完成核销 开始
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("billMonth", billMonth);
            map.put("companyId", companyId);
            map.put("flag", 0);
            List<CompanySonTotalBillDto> list = companySonTotalBillMapper.listDto(map);
            List<Integer> companyIds = new ArrayList<Integer>();
            double receivableAmount = 0.0;
            for (CompanySonTotalBillDto companySonTotalBillDto : list) {

                Double sPractical = 0.0;
                Double rPractical = 0.0;
                if (!companyIds.contains(companySonTotalBillDto.getCompanyId())) {
                    sPractical = socialSecurityInfoMapper.getPracticalByCompanyIdAndBillMonth(
                            companySonTotalBillDto.getCompanyId(),null,companySonTotalBillDto.getBillMonth(),null);
                    rPractical = reservedFundsInfoMapper.getPracticalByCompanyIdAndBillMonth(
                            companySonTotalBillDto.getCompanyId(),null,companySonTotalBillDto.getBillMonth(),null);
                }
                //获取上月稽核服务费
                MonthServiceFeeBalance monthServiceFeeBalance = monthServiceFeeBalanceMapper.
                        getByCompanyIdAndBillMonth2(companySonTotalBillDto.getCompanyId(), companySonTotalBillDto.getBillMonth());
                Double lastMonthServiceFee = 0.0;
                if (null != monthServiceFeeBalance) {
                    lastMonthServiceFee = monthServiceFeeBalance.getServiceFeeBalance();
                }
                BigDecimal price = new BigDecimal(rPractical.toString()).
                        subtract(new BigDecimal(companySonTotalBillDto.getLastMonthBalance().toString())).
                        subtract(new BigDecimal(sPractical.toString())).
                        subtract(new BigDecimal(rPractical.toString())).
                        subtract(new BigDecimal(lastMonthServiceFee)).
                        add(new BigDecimal(companySonTotalBillDto.getServiceFee().toString())).
                        add(new BigDecimal(getDouble(companySonTotalBillDto.getInsurancePrice()).toString())).
                        add(new BigDecimal(getDouble(companySonTotalBillDto.getYcPrice()).toString())).
                        add(new BigDecimal(companySonTotalBillDto.getSalaryInfoPrice().toString()));
                receivableAmount += new BigDecimal(companySonTotalBillDto.getTotalPrice().toString()).add(price).setScale(2,BigDecimal.ROUND_DOWN).doubleValue();
                companyIds.add(companySonTotalBillDto.getCompanyId());
            }

            if(receivableAmount == 0){
                companySonTotalBill.setBalanceOfCancelAfterVerification(0.0);
                companySonTotalBill.setIsBalanceOfCancelAfterVerification(1);
            }
            // 如果应收金额为0，确认账单自动完成核销 结束

            logMapper.add(new Log(user.getId(),StatusConstant.LOG_MODEL_BILL,"确认"+company.getCompanyName()+"(" + sdf.format(billMonth) +")的账单",
                    StatusConstant.LOG_FLAG_UPDATE));


            // 开票
            if(StatusConstant.FIRST_BILL_YES.equals(company.getIsFirstBill()) && null != amount && amount > 0){
                // 如果公司先票，则开票
                // 获取该公司下该账单月所有的总账单ID集合
                List<CompanySonTotalBill> byBillMonth = companySonTotalBillMapper.getByBillMonth(billMonth, companyId);
                List<Integer> companySonTotalBillIds = new ArrayList<Integer>(); // 当前认款后 管理的总账单ID
                if(null != byBillMonth && byBillMonth.size() > 0){
                    for (CompanySonTotalBill totalBill : byBillMonth) {
                        companySonTotalBillIds.add(totalBill.getId());
                    }
                }
                if(companySonTotalBillIds.size() > 0){
                    List<MakeBill> makeBills = makeBillHelper.getMakeBill(companySonTotalBillIds, null, new Date());
                    if(makeBills.size() > 0){
                        List<MakeBill> others = new ArrayList<MakeBill>();
                        // 判断开票金额的数量是否大于配置的10W，如果大于等于10W则拆分成两张
                        Iterator<MakeBill> iterator = makeBills.iterator();
                        while (iterator.hasNext()){
                            MakeBill next = iterator.next();
                            if(next.getAmountOfBill() > StatusConstant.BILL_AMOUNT){
                                List<MakeBill> bills = makeBillHelper.splitMakeBill(next);
                                if(bills.size() > 0){
                                    others.addAll(bills);
                                }
                                iterator.remove();
                            }
                        }
                        if(others.size() > 0){
                            makeBills.addAll(others);
                        }
                        makeBillMapper.batchAddMakeBill(makeBills);
                    }
                }

            }
        }
        if(status == 0){
            // 取消确认，删除统计数据中的确认数据
            companyBillCountMapper.del(companyId,billMonth,0);
            logMapper.add(new Log(user.getId(),StatusConstant.LOG_MODEL_BILL,"取消确认"+company.getCompanyName()+"(" + sdf.format(billMonth) +")的账单",
                    StatusConstant.LOG_FLAG_UPDATE));
            // 取消账单 删除票据
            List<MakeBill> makeBills = makeBillMapper.queryMakeBill(companyId, billMonth);
            if(makeBills.size() > 0){
                List<Integer> ids = new ArrayList<Integer>();
                for (MakeBill makeBill : makeBills) {
                    ids.add(makeBill.getId());
                }
                makeBillMapper.delMakeBill(ids);
            }
        }
        companySonTotalBillMapper.update2(companySonTotalBill);
    }

    private Double getDouble(Double insurancePrice) {

        if (null == insurancePrice){
            return 0.0;
        }else{
            return insurancePrice;
        }
    }

    /**
     * 驳回数据
     * @param companySonTotalBillId 汇总id
     * @param createTime 创建时间
     * @throws Exception
     */
    @Transactional
    public void delete(Integer companySonTotalBillId,Date createTime) throws Exception{
        List<CompanySonTotalBill> companySonTotalBills =
                companySonTotalBillMapper.getByCompanySonTotalBillId(companySonTotalBillId,createTime);
        Integer[] companySonTotalBillIds = new Integer[companySonTotalBills.size()];
        for (int i = 0 ; i < companySonTotalBills.size() ; i ++) {
            companySonTotalBillIds[i] = companySonTotalBills.get(i).getId();
        }
        if (companySonTotalBillIds.length > 0) {
            // 驳回数据时， 删除其他数据
            //删除缴纳记录
            memberMonthPayBusinessMapper.delList(companySonTotalBills.get(0).getBillMonth(),companySonTotalBills.get(0).getCompanyId());
           /* // 更新工资信息
            salaryInfoMapper.updateListByCompanySonTotalBillIds(companySonTotalBillIds);*/
            // 删除工资信息
            salaryInfoMapper.delSalaryInfoByCompanySonTotalBillId(companySonTotalBillIds);
            //更新拷盘数据制空子账单子类id
            socialSecurityInfoMapper.updateList2(companySonTotalBillIds);
            // 删除社保险种明细
            socialSecurityInfoItemMapper.delList(companySonTotalBillIds);
            // 删除社保
            socialSecurityInfoMapper.del(companySonTotalBillIds);
            //更新拷盘数据制空子账单子类id
            reservedFundsInfoMapper.updateList2(companySonTotalBillIds);
            // 删除公积金
            reservedFundsInfoMapper.del(companySonTotalBillIds);
            // 删除月总账单 子账单子类
            companySonBillItemMapper.deleteByCompanySonTotalBillId(companySonTotalBillIds);
            // 删除服务费
            monthServiceFeeMapper.delete(companySonTotalBillIds);
            // 删除汇总账单
            companySonTotalBillMapper.delete(companySonTotalBillIds);
            // 删除专项服务
            List<BusinessInsurance> list = businessInsuranceMapper.queryBusinessInsuranceIds(companySonTotalBillIds);
            if(null != list && list.size() > 0){
                List<Integer> ids = new ArrayList<Integer>();
                for (BusinessInsurance insurance : list) {
                    ids.add(insurance.getId());
                }
                if(ids.size() > 0){
                    businessInsuranceItemMapper.delBusinessInsuranceItemByIds(ids);
                }
            }
            businessInsuranceMapper.delBusinessInsurance(companySonTotalBillIds);
            Company company = companyMapper.queryCompanyById(companySonTotalBills.get(0).getCompanyId());
            //通知 供应商管理专员
            Inform inform = new Inform();
            inform.setRoleId(9);
            inform.setContent(company.getCompanyName()+"客户驳回了账单");
            inform.setUserId(company.getBeforeService());
            informMapper.save(inform);
        }


    }



    /**
     * 详情
     * @param id
     * @return
     */
    public CompanySonBillItem info(Integer id) {
        return companySonBillItemMapper.info(id);
    }


    /**
     * 后台页面 分页获取公司子账单
     *
     * @param pageArgs    分页属性
     * @param companyId     公司id
     * @param companySonTotalBillId     汇总账单id
     * @param isAudit     是否稽核 0：否 1：是
     * @param startTime   账单创建的开始时间
     * @param endTime     账单创建的结束时间
     * @return
     */
    public PageList<CompanySonBillItem> list(PageArgs pageArgs , Integer companyId , Integer companySonTotalBillId ,
                                             Integer isAudit, Date startTime , Date endTime) {
        PageList<CompanySonBillItem> pageList = new PageList<CompanySonBillItem>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("companyId", companyId);
        map.put("companySonTotalBillId", companySonTotalBillId);
        map.put("isAudit", isAudit);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        int count = companySonBillItemMapper.listCount(map);
        if (count > 0) {
            map.put("pageArgs", pageArgs);
            pageList.setList(companySonBillItemMapper.list(map));
        }
        pageList.setTotalSize(count);
        return pageList;
    }


    /**
     * 生成账单
     * @param companySonBillItem
     * @return
     */
    public void build(CompanySonBillItem companySonBillItem , Integer companySonBillId ,Date date,
                      List<SocialSecurityInfo> list1,List<ReservedFundsInfo> list2,
                      List<SocialSecurityInfo> updateListSSI,List<ReservedFundsInfo> updateListRFI,
                      List<MemberBusinessItem> memberBusinessItems,
                      Map<String ,Boolean> isFirstForeach,
                      List<MemberMonthPayBusiness> memberMonthPayBusinessList,
                      Set<Date> dateSet,Date billMonth,Company company,
                      List<CompanyCooperationMethod> methodList,
                      List<SocialSecurityInfoItem> updateListSSII,
                      List<SocialSecurityInfoItem> addListSSII) throws ParseException {
        List<MemberBusiness> memberBusinesses =
                memberBusinessMapper.getByCompanySonBillId(companySonBillId);
        if (null == company) {
            return;
        }
        Double price = 0.0;
        //纳入应收
        Double priceYS = 0.0;
        out:for (MemberBusiness business : memberBusinesses) {
            if (null != business.getMemberBusinessItem() &&
                    null != business.getMemberBusinessItem().getType() &&
                    (business.getBusinessId() == 3 || business.getBusinessId() == 4) &&
                    (business.getMemberBusinessItem().getType() == 0 ||
                            business.getMemberBusinessItem().getType() == 1)) {
                List<CompanyBusiness> companyBusinesses = company.getCompanyBusinessList();
                if (business.getMemberBusinessItem().getServeMethod() == 0) {
                    for (CompanyBusiness companyBusiness : companyBusinesses) {
                        if (companyBusiness.getBusinessId().equals(business.getBusinessId())) {
                            if (null != companyBusiness.getBusinessMethodList().get(0).getDaiLi() &&
                                    companyBusiness.getBusinessMethodList().get(0).getDaiLi() == 1) {
                                continue out;
                            }

                        }
                    }
                } else {
                    for (CompanyBusiness companyBusiness : companyBusinesses) {
                        if (companyBusiness.getBusinessId().equals(business.getBusinessId())) {
                            if (null != companyBusiness.getBusinessMethodList().get(0).getTuoGuan() &&
                                    companyBusiness.getBusinessMethodList().get(0).getTuoGuan() == 1) {
                                continue out;
                            }
                        }
                    }
                }


                if (business.getMemberBusinessItem().getIsFirstPay() == 1) {
                    MemberBusinessItem memberBusinessItem = new MemberBusinessItem();
                    memberBusinessItem.setIsFirstPay(0);
                    memberBusinessItem.setId(business.getMemberBusinessItem().getId());
                    memberBusinessItem.setFirstPayBillMonth(Timestamp.parseDate3(date,"yyyy-MM"));
                    memberBusinessItems.add(memberBusinessItem);
                }
                //社保明细集合
                if (business.getMemberBusinessItem().getType() == 0) {
//                    if (companySonBillItem.getSocialSecurityInfos().size() > 0) {
//                        companySonBillItem.setIsSocialSecurityAudit(1);
//                        continue;
//                    }
                    MemberBusinessItem memberBusinessItem = business.getMemberBusinessItem();

                    if (Timestamp.parseDate3(memberBusinessItem.getBillStartTime(),"yyyy-MM").compareTo(billMonth) > 0) {
                        continue;
                    }
                    Double lsPrice = buildSocialSecurityInfo(business,list1,updateListSSI, date,companySonBillItem.getId(),
                            companySonBillItem.getCompanySonTotalBill().getCompanySonBillId(),
                            isFirstForeach,memberMonthPayBusinessList,dateSet,billMonth,
                            methodList,updateListSSII,addListSSII);
                    companySonBillItem.setIsSocialSecurityAudit(0);
                    if (memberBusinessItem.getIsReceivable() == 1) {
                        priceYS += lsPrice;
                    }
                    price += lsPrice;
                }

                //公积金明细
                if (business.getMemberBusinessItem().getType() == 1) {
//                    if (companySonBillItem.getReservedFundsInfoList().size() > 0) {
//                        companySonBillItem.setIsReservedFundAudit(1);
//                        continue;
//                    }
                    MemberBusinessItem memberBusinessItem = business.getMemberBusinessItem();
                    if (Timestamp.parseDate3(memberBusinessItem.getBillStartTime(),"yyyy-MM").compareTo(billMonth) > 0) {
                        continue;
                    }
                    Double lsPrice = buildReservedFundsInfo(business,list2,updateListRFI,date,companySonBillItem.getId(),
                            companySonBillItem.getCompanySonTotalBill().getCompanySonBillId(),
                            isFirstForeach,memberMonthPayBusinessList,dateSet,billMonth,methodList);
                    companySonBillItem.setIsReservedFundAudit(0);
                    if (memberBusinessItem.getIsReceivable() == 1) {
                        priceYS += lsPrice;
                    }
                    price += lsPrice;
                }

            }
        }
        companySonBillItem.setSocialSecurityInfos(list1);
        companySonBillItem.setReservedFundsInfoList(list2);
        //预收金额
        companySonBillItem.setReceivablePrice(companySonBillItem.getReceivablePrice() + price);
        //每个员工各项业务缴纳应收总金额
        companySonBillItem.setTotalPrice(companySonBillItem.getTotalPrice() + priceYS);
    }

    /**
     * 生成账单--社保明细
     * @param business
     * @param list
     * @return
     */
    public Double buildSocialSecurityInfo(MemberBusiness business,List<SocialSecurityInfo> list,
                                          List<SocialSecurityInfo> updateListSSI,Date date ,
                                          Integer companySonBillItemId,Integer companySonBillId,
                                          Map<String ,Boolean> isFirstForeach,
                                          List<MemberMonthPayBusiness> memberMonthPayBusinessList,
                                          Set<Date> dateSet,Date billMonth,
                                          List<CompanyCooperationMethod> methodList,
                                          List<SocialSecurityInfoItem> updateListSSII,
                                          List<SocialSecurityInfoItem> addListSSII) throws ParseException {


        Double price = 0.0;
        //员工信息
        Member member = business.getMember();
        //获取最新一条社保信息
        SocialSecurityInfo ssi = socialSecurityInfoMapper.getLastByMemberId(member.getId());
        //员工社保信息
        MemberBusinessItem item = business.getMemberBusinessItem();
        Date serviceNowYM = date;

        //循环次数
        int forNum = 1;
        //判断是否是第一次循环社保数据
        if (null == isFirstForeach.get(member.getId()+"sIsFirstForeach") ? true : isFirstForeach.get(member.getId()+"sIsFirstForeach")) {
            //此员工为第一次缴纳 需要补交
            if (Timestamp.parseDate3(item.getBillStartTime(),"yyyy-MM").
                    compareTo(Timestamp.parseDate3(item.getServiceStartTime(),"yyyy-MM")) != 0 &&
                    item.getIsFirstPay() == 1 ) {
                Date serviceNowYM1 = Timestamp.parseDate3(item.getServiceStartTime(),"yyyy-MM");
                serviceNowYM = Timestamp.parseDate3(item.getServiceStartTime(),"yyyy-MM");
                forNum = 0;
                while (true) {
                    if (DateUtil.daysBetween(serviceNowYM1,date) >= 0 &&
                            (null != item.getServiceEndTime() ?
                                    Timestamp.parseDate3(item.getServiceEndTime(),"yyyy-MM").
                                            compareTo(Timestamp.parseDate3(serviceNowYM1,"yyyy-MM")) >= 0 : true)) {
                        forNum ++;
                        serviceNowYM1 = DateUtil.addDate(serviceNowYM1, TimeField.Month.ordinal(),1);
                    } else {
                        break;
                    }
                }
            } else {
                if (null != item.getServiceEndTime() &&
                        Timestamp.parseDate3(item.getServiceEndTime(),"yyyy-MM").
                                compareTo(Timestamp.parseDate3(date,"yyyy-MM")) < 0) {
                    forNum --;
                }
            }
            isFirstForeach.put(member.getId()+"sIsFirstForeach",false);
        } else {
            if (null != item.getServiceEndTime() &&
                    Timestamp.parseDate3(item.getServiceEndTime(),"yyyy-MM").
                            compareTo(Timestamp.parseDate3(date,"yyyy-MM")) < 0) {
                forNum --;
            }
        }

        String payPlaceOrganizationName = (null != item.getPayPlaceName() ? item.getPayPlaceName().replace("中国,","").replace("省,","") : "") + "-" +  item.getOrganizationName();
        String payPlaceName = null != item.getPayPlaceName() ? item.getPayPlaceName().replace("中国,","").replace("省,","") : "";

        for (int i = 0 ; i < forNum ; i ++) {
            dateSet.add(Timestamp.parseDate3(serviceNowYM,"yyyy-MM"));
            isFirstForeach.put(member.getId()+"sIsFirstForeach",false);
            if (null != ssi) {
                //员工每月缴纳
                MemberMonthPayBusiness memberMonthPayBusiness = new MemberMonthPayBusiness();
                memberMonthPayBusiness.setServiceMonth(Timestamp.parseDate3(serviceNowYM,"yyyy-MM"));
                memberMonthPayBusiness.setBusinessId(3);
                memberMonthPayBusiness.setMemberId(member.getId());
                memberMonthPayBusiness.setBillMonth(billMonth);
                memberMonthPayBusiness.setCityId(item.getCityId());
                memberMonthPayBusiness.setCompanySonBillId(business.getCompanySonBillId());
                memberMonthPayBusiness.setCompanyId(member.getCompanyId());
                memberMonthPayBusiness.setBillType(0);
                memberMonthPayBusinessList.add(memberMonthPayBusiness);
                //账单-社保明细
                SocialSecurityInfo ss = new SocialSecurityInfo();

                ss.setUserName(member.getUserName());
                ss.setCertificateType(member.getCertificateType());
                ss.setIdCard(member.getCertificateNum());
                ss.setSocialSecurityNum(item.getCoding());
                if (null == item.getPayPlaceName() || "null".equals(item.getPayPlaceName())) {
                    item.setPayPlaceName("");
                }
                if (null == item.getOrganizationName() || "null".equals(item.getOrganizationName())) {
                    item.setOrganizationName("");
                }
                ss.setPayPlaceOrganizationName(payPlaceOrganizationName);
                ss.setPayPlaceName(payPlaceName);
                ss.setTransactorName(item.getTransactorName());
                ss.setOrganizationName(item.getOrganizationName());
                ss.setBillMonth(billMonth);
                ss.setInsuranceLevelName(item.getInsuranceLevelName());
                ss.setBeginPayYM(item.getServiceStartTime());
                ss.setServiceNowYM(Timestamp.parseDate3(serviceNowYM,"yyyy-MM"));
                ss.setPayCardinalNumber(item.getBaseNumber());
                ss.setMemberId(member.getId());
                ss.setBillMadeMethod(0);
                //公司缴纳总金额
                Double companyTotalPay = ssi.getPracticalCompanyTotalPay();
                //个人缴纳总金额
                Double memberTotalPay = ssi.getPracticalMemberTotalPay();
                //税费
                Double taxPrice = 0.0;

                //社保险种档次集合
                List<SocialSecurityInfoItem> socialSecurityInfoItems = new ArrayList<SocialSecurityInfoItem>();
                for (SocialSecurityInfoItem infoItem : ssi.getSocialSecurityInfoItems()) {
                    SocialSecurityInfoItem securityInfoItem = new SocialSecurityInfoItem();
                    securityInfoItem.setPayPrice(infoItem.getPracticalPayPrice());
                    securityInfoItem.setType(infoItem.getType());
                    securityInfoItem.setInsuranceName(infoItem.getInsuranceName());
//                    if (infoItem.getType().equals(0)) {
//                        companyTotalPay += infoItem.getPracticalPayPrice();
//                    } else {
//                        memberTotalPay += infoItem.getPracticalPayPrice();
//                    }
                    socialSecurityInfoItems.add(securityInfoItem);
                }

                for (CompanyCooperationMethod method : methodList) {
                    if (member.getWaysOfCooperation().equals(method.getCooperationMethodId())) {
                        taxPrice = (companyTotalPay + memberTotalPay) * method.getPercent() / 100;
                       /* companyTotalPay = companyTotalPay * (1 + method.getPercent() / 100);
                        memberTotalPay = memberTotalPay * (1 + method.getPercent() / 100);*/
                        break;
                    }

                }
                ss.setTaxPrice(taxPrice);
                ss.setCompanyTotalPay(companyTotalPay);
                ss.setMemberTotalPay(memberTotalPay);
                ss.setCompanySonBillItemId(companySonBillItemId);
                ss.setSocialSecurityInfoItems(socialSecurityInfoItems);
                ss.setCompanySonBillId(companySonBillId);
                list.add(ss);
                price += companyTotalPay + memberTotalPay;
                serviceNowYM = DateUtil.addDate(serviceNowYM, TimeField.Month.ordinal(),1);
            } else {
                //是否需要新增 true：是  false：否
                Boolean flag = true;
                out:for (SocialSecurityInfo ss : updateListSSI) {
                    if (ss.getBillMadeMethod() == 0 && ss.getMemberId().equals(member.getId()) &&
                            Timestamp.parseDate3(serviceNowYM,"yyyy-MM").
                                    compareTo(Timestamp.parseDate3(ss.getServiceNowYM(),"yyyy-MM")) == 0) {

                        flag = false;
                        if (null == item.getPayPlaceName() || "null".equals(item.getPayPlaceName())) {
                            item.setPayPlaceName("");
                        }
                        if (null == item.getOrganizationName() || "null".equals(item.getOrganizationName())) {
                            item.setOrganizationName("");
                        }
                        if (null != item.getCoding()) {
                            ss.setSocialSecurityNum(item.getCoding());
                        }
                        ss.setPayPlaceOrganizationName((null != item.getPayPlaceName() ? item.getPayPlaceName().replace("中国,","").replace("省,","") : "") + "-" + item.getOrganizationName());
                        ss.setPayPlaceName(null != item.getPayPlaceName() ? item.getPayPlaceName().replace("中国,","").replace("省,","") : "");
                        ss.setTransactorName(item.getTransactorName());
                        ss.setOrganizationName(item.getOrganizationName());

                        //公司缴纳总金额
                        Double companyTotalPay = 0.0;
                        //个人缴纳总金额
                        Double memberTotalPay = 0.0;
                        //税费
                        Double taxPrice = 0.0;

                        if (null != ss.getSocialSecurityInfoItems() && ss.getSocialSecurityInfoItems().size() > 0) {
                            for (PayTheWay way : item.getPayTheWays()) {
                                if (null == way.getInsuranceName() || "".equals(way.getInsuranceName())) {
                                    continue;
                                }
                                //公司本次缴纳金额
                                Double companyPay = 0.0;
                                //员工本次缴纳金额
                                Double memberPay = 0.0;

                                //公司缴纳类型 0：金额  1：比例   2：跟随办理方
                                if (way.getCoPayType() == 0) {
                                    companyPay = null == way.getCoPayPrice() ? 0.0 : way.getCoPayPrice();
                                } else if (way.getCoPayType() == 1) {
                                    if (item.getBaseType() == 0) {
                                        companyPay = way.getCoPayPrice() / 100 * way.getCoMinScope();
                                    } else if(item.getBaseType() == 1) {
                                        companyPay = way.getCoPayPrice() / 100 * way.getCoMaxScope();
                                    } else {
                                        if (way.getCoMinScope() > item.getBaseNumber()) {
                                            companyPay = way.getCoPayPrice()  / 100 * way.getCoMinScope();
                                        } else if (way.getCoMaxScope() < item.getBaseNumber()) {
                                            companyPay = way.getCoPayPrice()  / 100 * way.getCoMaxScope();
                                        } else {
                                            companyPay = way.getCoPayPrice()  / 100 * item.getBaseNumber();
                                        }
                                    }
                                    //公司计算规则  0：四舍五入 1：升角省分（精度为0） 2：去尾  3：进一
                                    //公司填写精度 3、2、1、0、-1、-2，对应保留小数位数
                                    if (way.getCoComputationRule() == 0) {
                                        BigDecimal b = new BigDecimal(companyPay.toString());
                                        companyPay = b.setScale(Integer.parseInt(way.getCoComputationalAccuracy().toString().replace(".0","")),
                                                BigDecimal.ROUND_HALF_UP).doubleValue();
                                    } else if (way.getCoComputationRule() == 1){
                                        companyPay = Math.ceil(new BigDecimal(companyPay.toString()).setScale(1, RoundingMode.DOWN).
                                                doubleValue());
                                    } else if (way.getCoComputationRule() == 2) {
                                        companyPay = new BigDecimal(companyPay.toString()).setScale(Integer.parseInt(way.getCoComputationalAccuracy().toString()),
                                                RoundingMode.DOWN).doubleValue();
                                    } else {
                                        companyPay = new BigDecimal(companyPay.toString()).setScale(Integer.parseInt(way.getCoComputationalAccuracy().toString()),
                                                RoundingMode.UP).doubleValue();
                                    }
                                } else if (way.getCoPayType() == 2) {
                                    //办理方
                                    TransactorInsuranceLevel til = way.getTransactorInsuranceLevel();
                                    if (null == til) {
                                        throw new InterfaceCommonException(StatusConstant.Fail_CODE,"请检查险种档次配置或者跟随办理方配置");
                                    }
                                    //公司缴纳类型 0：金额  1：比例
                                    if (til.getCoPayType() == 0) {
                                        companyPay = til.getCoPayPrice();
                                    } else {
                                        if (item.getBaseType() == 0) {
                                            companyPay = til.getCoPayPrice() / 100 * way.getCoMinScope();
                                        } else if(item.getBaseType() == 1) {
                                            companyPay = til.getCoPayPrice() / 100 * way.getCoMaxScope();
                                        } else {
                                            if (way.getCoMinScope() > item.getBaseNumber()) {
                                                companyPay = til.getCoPayPrice()  / 100 * way.getCoMinScope();
                                            } else if (way.getCoMaxScope() < item.getBaseNumber()) {
                                                companyPay = til.getCoPayPrice()  / 100 * way.getCoMaxScope();
                                            } else {
                                                companyPay = til.getCoPayPrice()  / 100 * item.getBaseNumber();
                                            }
                                        }
                                        //公司计算规则  0：四舍五入 1：升角省分（精度为0） 2：去尾  3：进一
                                        //公司填写精度 3、2、1、0、-1、-2，对应保留小数位数
                                        if (way.getCoComputationRule() == 0) {
                                            BigDecimal b = new BigDecimal(companyPay.toString());
                                            companyPay = b.setScale(Integer.parseInt(way.getCoComputationalAccuracy().toString().replace(".0","")),
                                                    BigDecimal.ROUND_HALF_UP).doubleValue();
                                        } else if (way.getCoComputationRule() == 1){
                                            companyPay = Math.ceil(new BigDecimal(companyPay.toString()).setScale(1, RoundingMode.DOWN).
                                                    doubleValue());
                                        } else if (way.getCoComputationRule() == 2) {
                                            companyPay = new BigDecimal(companyPay.toString()).setScale(Integer.parseInt(way.getCoComputationalAccuracy().toString()),
                                                    RoundingMode.DOWN).doubleValue();
                                        } else {
                                            companyPay = new BigDecimal(companyPay.toString()).setScale(Integer.parseInt(way.getCoComputationalAccuracy().toString()),
                                                    RoundingMode.UP).doubleValue();
                                        }
                                    }
                                }
                                //个人缴纳类型 0：金额  1：比例   2：跟随办理方
                                if (way.getMePayType() == 0) {
                                    memberPay = null == way.getMePayPrice() ? 0.0 : way.getMePayPrice();
                                } else if (way.getMePayType() == 1) {
                                    if (item.getBaseType() == 0) {
                                        memberPay = way.getMePayPrice() / 100  * way.getMeMinScope();
                                    } else if(item.getBaseType() == 1) {
                                        memberPay = way.getMePayPrice() / 100  * way.getMeMaxScope();
                                    } else {
                                        if (way.getMeMinScope() > item.getBaseNumber()) {
                                            memberPay = way.getMePayPrice() / 100 * way.getMeMinScope();
                                        } else if (way.getMeMaxScope() < item.getBaseNumber()) {
                                            memberPay = way.getMePayPrice() / 100 * way.getMeMaxScope();
                                        } else {
                                            memberPay = way.getMePayPrice() / 100 * item.getBaseNumber();
                                        }
                                    }
                                    //个人计算规则  0：四舍五入 1：升角省分（精度为0） 2：去尾  3：进一
                                    //个人公司填写精度 3、2、1、0、-1、-2，对应保留小数位数
                                    if (way.getMeComputationRule() == 0) {
                                        BigDecimal b = new BigDecimal(memberPay.toString());
                                        memberPay = b.setScale(Integer.parseInt(way.getMeComputationalAccuracy().toString().replace(".0","")),
                                                BigDecimal.ROUND_HALF_UP).doubleValue();
                                    } else if (way.getMeComputationRule() == 1){
                                        memberPay = Math.ceil(new BigDecimal(memberPay.toString()).setScale(1, RoundingMode.DOWN).
                                                doubleValue());
                                    } else if (way.getMeComputationRule() == 2) {
                                        memberPay = new BigDecimal(memberPay.toString()).setScale(Integer.parseInt(way.getMeComputationalAccuracy().toString().replace(".0","")),
                                                RoundingMode.DOWN).doubleValue();
                                    } else {
                                        memberPay = new BigDecimal(memberPay.toString()).setScale(Integer.parseInt(way.getMeComputationalAccuracy().toString().replace(".0","")),
                                                RoundingMode.UP).doubleValue();
                                    }
                                } else if (way.getMePayType() == 2) {
                                    //办理方
                                    TransactorInsuranceLevel til = way.getTransactorInsuranceLevel();
                                    //公司缴纳类型 0：金额  1：比例
                                    if (til.getMePayType() == 0) {
                                        memberPay = til.getMePayPrice();
                                    } else {
                                        if (item.getBaseType() == 0) {
                                            memberPay = til.getMePayPrice() / 100  * way.getMeMinScope();
                                        } else if(item.getBaseType() == 1) {
                                            memberPay = til.getMePayPrice() / 100  * way.getMeMinScope();
                                        } else {
                                            if (way.getMeMinScope() > item.getBaseNumber()) {
                                                memberPay = til.getMePayPrice() / 100 * way.getMeMinScope();
                                            } else if (way.getMeMaxScope() < item.getBaseNumber()) {
                                                memberPay = til.getMePayPrice() / 100 * way.getMeMinScope();
                                            } else {
                                                memberPay = til.getMePayPrice() / 100 * item.getBaseNumber();
                                            }
                                        }
                                    }
                                    //个人计算规则  0：四舍五入 1：升角省分（精度为0） 2：去尾  3：进一
                                    //个人公司填写精度 3、2、1、0、-1、-2，对应保留小数位数
                                    if (way.getMeComputationRule() == 0) {
                                        BigDecimal b = new BigDecimal(memberPay.toString());
                                        memberPay = b.setScale(Integer.parseInt(way.getMeComputationalAccuracy().toString().replace(".0","")),
                                                BigDecimal.ROUND_HALF_UP).doubleValue();
                                    } else if (way.getMeComputationRule() == 1){
                                        memberPay = Math.ceil(new BigDecimal(memberPay.toString()).setScale(1, RoundingMode.DOWN).
                                                doubleValue());
                                    } else if (way.getMeComputationRule() == 2) {
                                        memberPay = new BigDecimal(memberPay.toString()).setScale(Integer.parseInt(way.getMeComputationalAccuracy().toString().replace(".0","")),
                                                RoundingMode.DOWN).doubleValue();
                                    } else {
                                        memberPay = new BigDecimal(memberPay.toString()).setScale(Integer.parseInt(way.getMeComputationalAccuracy().toString().replace(".0","")),
                                                RoundingMode.UP).doubleValue();
                                    }
                                }
                                companyTotalPay += companyPay;
                                memberTotalPay += memberPay;
                                //个人是否存在
                                Boolean mFlag = true;
                                //公司是否存在
                                Boolean cFlag = true;
                                out2: for (SocialSecurityInfoItem infoItem : ss.getSocialSecurityInfoItems()) {
                                    // TODO: 2017/12/28 是否存在 不存在  进行新增
                                    if (infoItem.getInsuranceName().equals(way.getInsuranceName())) {
                                        if (infoItem.getType() == 0) {
                                            cFlag = false;
                                            infoItem.setPayPrice(companyPay);
                                            updateListSSII.add(infoItem);
                                            continue out2;
                                        }
                                        if (infoItem.getType() == 1) {
                                            mFlag = false;
                                            infoItem.setPayPrice(memberPay);
                                            updateListSSII.add(infoItem);
                                            continue out2;
                                        }
                                    }
                                }
                                if (cFlag) {
                                    //type 缴纳方 0：公司 1：个人
                                    SocialSecurityInfoItem s = new SocialSecurityInfoItem();
                                    s.setInsuranceName(way.getInsuranceName());
                                    s.setPayPrice(companyPay);
                                    s.setSocialSecurityInfoId(ss.getId());
                                    s.setType(0);
                                    addListSSII.add(s);
                                }
                                if (mFlag) {
                                    SocialSecurityInfoItem s = new SocialSecurityInfoItem();
                                    s.setInsuranceName(way.getInsuranceName());
                                    s.setSocialSecurityInfoId(ss.getId());
                                    s.setPayPrice(memberPay);
                                    s.setType(1);
                                    addListSSII.add(s);
                                }
                            }
                        } else {
                            for (PayTheWay way : item.getPayTheWays()) {
                                if (null == way.getInsuranceName() || "".equals(way.getInsuranceName())) {
                                    continue;
                                }
                                //公司本次缴纳金额
                                Double companyPay = 0.0;
                                //员工本次缴纳金额
                                Double memberPay = 0.0;

                                //公司缴纳类型 0：金额  1：比例   2：跟随办理方
                                if (way.getCoPayType() == 0) {
                                    companyPay = null == way.getCoPayPrice() ? 0.0 : way.getCoPayPrice();
                                } else if (way.getCoPayType() == 1) {
                                    if (item.getBaseType() == 0) {
                                        companyPay = way.getCoPayPrice() / 100 * way.getCoMinScope();
                                    } else if(item.getBaseType() == 1) {
                                        companyPay = way.getCoPayPrice() / 100 * way.getCoMaxScope();
                                    } else {
                                        if (way.getCoMinScope() > item.getBaseNumber()) {
                                            companyPay = way.getCoPayPrice()  / 100 * way.getCoMinScope();
                                        } else if (way.getCoMaxScope() < item.getBaseNumber()) {
                                            companyPay = way.getCoPayPrice()  / 100 * way.getCoMaxScope();
                                        } else {
                                            companyPay = way.getCoPayPrice()  / 100 * item.getBaseNumber();
                                        }
                                    }
                                    //公司计算规则  0：四舍五入 1：升角省分（精度为0） 2：去尾  3：进一
                                    //公司填写精度 3、2、1、0、-1、-2，对应保留小数位数
                                    if (way.getCoComputationRule() == 0) {
                                        BigDecimal b = new BigDecimal(companyPay.toString());
                                        companyPay = b.setScale(Integer.parseInt(way.getCoComputationalAccuracy().toString().replace(".0","")),
                                                BigDecimal.ROUND_HALF_UP).doubleValue();
                                    } else if (way.getCoComputationRule() == 1){
                                        companyPay = Math.ceil(new BigDecimal(companyPay.toString()).setScale(1, RoundingMode.DOWN).
                                                doubleValue());
                                    } else if (way.getCoComputationRule() == 2) {
                                        companyPay = new BigDecimal(companyPay.toString()).setScale(Integer.parseInt(way.getCoComputationalAccuracy().toString()),
                                                RoundingMode.DOWN).doubleValue();
                                    } else {
                                        companyPay = new BigDecimal(companyPay.toString()).setScale(Integer.parseInt(way.getCoComputationalAccuracy().toString()),
                                                RoundingMode.UP).doubleValue();
                                    }
                                } else if (way.getCoPayType() == 2) {
                                    //办理方
                                    TransactorInsuranceLevel til = way.getTransactorInsuranceLevel();
                                    if (null == til) {
                                        throw new InterfaceCommonException(StatusConstant.Fail_CODE,"请检查险种档次配置或者跟随办理方配置");
                                    }
                                    //公司缴纳类型 0：金额  1：比例
                                    if (til.getCoPayType() == 0) {
                                        companyPay = til.getCoPayPrice();
                                    } else {
                                        if (item.getBaseType() == 0) {
                                            companyPay = til.getCoPayPrice() / 100 * way.getCoMinScope();
                                        } else if(item.getBaseType() == 1) {
                                            companyPay = til.getCoPayPrice() / 100 * way.getCoMaxScope();
                                        } else {
                                            if (way.getCoMinScope() > item.getBaseNumber()) {
                                                companyPay = til.getCoPayPrice()  / 100 * way.getCoMinScope();
                                            } else if (way.getCoMaxScope() < item.getBaseNumber()) {
                                                companyPay = til.getCoPayPrice()  / 100 * way.getCoMaxScope();
                                            } else {
                                                companyPay = til.getCoPayPrice()  / 100 * item.getBaseNumber();
                                            }
                                        }
                                        //公司计算规则  0：四舍五入 1：升角省分（精度为0） 2：去尾  3：进一
                                        //公司填写精度 3、2、1、0、-1、-2，对应保留小数位数
                                        if (way.getCoComputationRule() == 0) {
                                            BigDecimal b = new BigDecimal(companyPay.toString());
                                            companyPay = b.setScale(Integer.parseInt(way.getCoComputationalAccuracy().toString().replace(".0","")),
                                                    BigDecimal.ROUND_HALF_UP).doubleValue();
                                        } else if (way.getCoComputationRule() == 1){
                                            companyPay = Math.ceil(new BigDecimal(companyPay.toString()).setScale(1, RoundingMode.DOWN).
                                                    doubleValue());
                                        } else if (way.getCoComputationRule() == 2) {
                                            companyPay = new BigDecimal(companyPay.toString()).setScale(Integer.parseInt(way.getCoComputationalAccuracy().toString()),
                                                    RoundingMode.DOWN).doubleValue();
                                        } else {
                                            companyPay = new BigDecimal(companyPay.toString()).setScale(Integer.parseInt(way.getCoComputationalAccuracy().toString()),
                                                    RoundingMode.UP).doubleValue();
                                        }
                                    }
                                }
                                //个人缴纳类型 0：金额  1：比例   2：跟随办理方
                                if (way.getMePayType() == 0) {
                                    memberPay = null == way.getMePayPrice() ? 0.0 : way.getMePayPrice();
                                } else if (way.getMePayType() == 1) {
                                    if (item.getBaseType() == 0) {
                                        memberPay = way.getMePayPrice() / 100  * way.getMeMinScope();
                                    } else if(item.getBaseType() == 1) {
                                        memberPay = way.getMePayPrice() / 100  * way.getMeMaxScope();
                                    } else {
                                        if (way.getMeMinScope() > item.getBaseNumber()) {
                                            memberPay = way.getMePayPrice() / 100 * way.getMeMinScope();
                                        } else if (way.getMeMaxScope() < item.getBaseNumber()) {
                                            memberPay = way.getMePayPrice() / 100 * way.getMeMaxScope();
                                        } else {
                                            memberPay = way.getMePayPrice() / 100 * item.getBaseNumber();
                                        }
                                    }
                                    //个人计算规则  0：四舍五入 1：升角省分（精度为0） 2：去尾  3：进一
                                    //个人公司填写精度 3、2、1、0、-1、-2，对应保留小数位数
                                    if (way.getMeComputationRule() == 0) {
                                        BigDecimal b = new BigDecimal(memberPay.toString());
                                        memberPay = b.setScale(Integer.parseInt(way.getMeComputationalAccuracy().toString().replace(".0","")),
                                                BigDecimal.ROUND_HALF_UP).doubleValue();
                                    } else if (way.getMeComputationRule() == 1){
                                        memberPay = Math.ceil(new BigDecimal(memberPay.toString()).setScale(1, RoundingMode.DOWN).
                                                doubleValue());
                                    } else if (way.getMeComputationRule() == 2) {
                                        memberPay = new BigDecimal(memberPay.toString()).setScale(Integer.parseInt(way.getMeComputationalAccuracy().toString().replace(".0","")),
                                                RoundingMode.DOWN).doubleValue();
                                    } else {
                                        memberPay = new BigDecimal(memberPay.toString()).setScale(Integer.parseInt(way.getMeComputationalAccuracy().toString().replace(".0","")),
                                                RoundingMode.UP).doubleValue();
                                    }
                                } else if (way.getMePayType() == 2) {
                                    //办理方
                                    TransactorInsuranceLevel til = way.getTransactorInsuranceLevel();
                                    //公司缴纳类型 0：金额  1：比例
                                    if (til.getMePayType() == 0) {
                                        memberPay = til.getMePayPrice();
                                    } else {
                                        if (item.getBaseType() == 0) {
                                            memberPay = til.getMePayPrice() / 100  * way.getMeMinScope();
                                        } else if(item.getBaseType() == 1) {
                                            memberPay = til.getMePayPrice() / 100  * way.getMeMinScope();
                                        } else {
                                            if (way.getMeMinScope() > item.getBaseNumber()) {
                                                memberPay = til.getMePayPrice() / 100 * way.getMeMinScope();
                                            } else if (way.getMeMaxScope() < item.getBaseNumber()) {
                                                memberPay = til.getMePayPrice() / 100 * way.getMeMinScope();
                                            } else {
                                                memberPay = til.getMePayPrice() / 100 * item.getBaseNumber();
                                            }
                                        }
                                    }
                                    //个人计算规则  0：四舍五入 1：升角省分（精度为0） 2：去尾  3：进一
                                    //个人公司填写精度 3、2、1、0、-1、-2，对应保留小数位数
                                    if (way.getMeComputationRule() == 0) {
                                        BigDecimal b = new BigDecimal(memberPay.toString());
                                        memberPay = b.setScale(Integer.parseInt(way.getMeComputationalAccuracy().toString().replace(".0","")),
                                                BigDecimal.ROUND_HALF_UP).doubleValue();
                                    } else if (way.getMeComputationRule() == 1){
                                        memberPay = Math.ceil(new BigDecimal(memberPay.toString()).setScale(1, RoundingMode.DOWN).
                                                doubleValue());
                                    } else if (way.getMeComputationRule() == 2) {
                                        memberPay = new BigDecimal(memberPay.toString()).setScale(Integer.parseInt(way.getMeComputationalAccuracy().toString().replace(".0","")),
                                                RoundingMode.DOWN).doubleValue();
                                    } else {
                                        memberPay = new BigDecimal(memberPay.toString()).setScale(Integer.parseInt(way.getMeComputationalAccuracy().toString().replace(".0","")),
                                                RoundingMode.UP).doubleValue();
                                    }
                                }
                                companyTotalPay += companyPay;
                                memberTotalPay += memberPay;
                                {
                                    //type 缴纳方 0：公司 1：个人
                                    SocialSecurityInfoItem s1 = new SocialSecurityInfoItem();
                                    s1.setInsuranceName(way.getInsuranceName());
                                    s1.setSocialSecurityInfoId(ss.getId());
                                    s1.setPayPrice(companyPay);
                                    s1.setType(0);
                                    addListSSII.add(s1);
                                }


                                {
                                    SocialSecurityInfoItem s2 = new SocialSecurityInfoItem();
                                    s2.setInsuranceName(way.getInsuranceName());
                                    s2.setPayPrice(memberPay);
                                    s2.setSocialSecurityInfoId(ss.getId());
                                    s2.setType(1);
                                    addListSSII.add(s2);
                                }
                            }

                        }

                        for (CompanyCooperationMethod method : methodList) {
                            if (member.getWaysOfCooperation().equals(method.getCooperationMethodId())) {
                                taxPrice = (companyTotalPay + memberTotalPay) * method.getPercent() / 100;
                            /*companyTotalPay = companyTotalPay * (1 + method.getPercent() / 100);
                            memberTotalPay = memberTotalPay * (1 + method.getPercent() / 100);*/
                                break;
                            }

                        }
                        ss.setTaxPrice(taxPrice);
                        ss.setCompanyTotalPay(companyTotalPay);
                        ss.setMemberTotalPay(memberTotalPay);
                        price += companyTotalPay + memberTotalPay;
                        continue out;
                    }
                }
                if (flag) {
                    //员工每月缴纳
                    MemberMonthPayBusiness memberMonthPayBusiness = new MemberMonthPayBusiness();
                    memberMonthPayBusiness.setServiceMonth(Timestamp.parseDate3(serviceNowYM,"yyyy-MM"));
                    memberMonthPayBusiness.setBusinessId(3);
                    memberMonthPayBusiness.setMemberId(member.getId());
                    memberMonthPayBusiness.setBillMonth(billMonth);
                    memberMonthPayBusiness.setCityId(item.getCityId());
                    memberMonthPayBusiness.setCompanySonBillId(business.getCompanySonBillId());
                    memberMonthPayBusiness.setCompanyId(member.getCompanyId());
                    memberMonthPayBusiness.setBillType(0);
                    memberMonthPayBusinessList.add(memberMonthPayBusiness);
                    //账单-社保明细
                    SocialSecurityInfo ss = new SocialSecurityInfo();
                    ss.setUserName(member.getUserName());
                    ss.setCertificateType(member.getCertificateType());
                    ss.setIdCard(member.getCertificateNum());
                    ss.setSocialSecurityNum(item.getCoding());
                    if (null == item.getPayPlaceName() || "null".equals(item.getPayPlaceName())) {
                        item.setPayPlaceName("");
                    }
                    if (null == item.getOrganizationName() || "null".equals(item.getOrganizationName())) {
                        item.setOrganizationName("");
                    }
                    ss.setPayPlaceOrganizationName((null != item.getPayPlaceName() ? item.getPayPlaceName().replace("中国,","").replace("省,","") : "") + "-" +  item.getOrganizationName());
                    ss.setPayPlaceName(null != item.getPayPlaceName() ? item.getPayPlaceName().replace("中国,","").replace("省,","") : "");
                    ss.setTransactorName(item.getTransactorName());
                    ss.setOrganizationName(item.getOrganizationName());
                    ss.setBillMonth(billMonth);
                    ss.setInsuranceLevelName(item.getInsuranceLevelName());
                    ss.setBeginPayYM(item.getServiceStartTime());
                    ss.setServiceNowYM(Timestamp.parseDate3(serviceNowYM,"yyyy-MM"));
                    ss.setPayCardinalNumber(item.getBaseNumber());
                    ss.setMemberId(member.getId());
                    ss.setBillMadeMethod(0);
                    //公司缴纳总金额
                    Double companyTotalPay = 0.0;
                    //个人缴纳总金额
                    Double memberTotalPay = 0.0;
                    //税费
                    Double taxPrice = 0.0;

                    //社保险种档次集合
                    List<SocialSecurityInfoItem> socialSecurityInfoItems = new ArrayList<SocialSecurityInfoItem>();

                    for (PayTheWay way : item.getPayTheWays()) {
                        if (null == way.getInsuranceName() || "".equals(way.getInsuranceName())) {
                            continue;
                        }
                        //公司本次缴纳金额
                        Double companyPay = 0.0;
                        //员工本次缴纳金额
                        Double memberPay = 0.0;

                        //公司缴纳类型 0：金额  1：比例   2：跟随办理方
                        if (way.getCoPayType() == 0) {
                            companyPay = null == way.getCoPayPrice() ? 0.0 : way.getCoPayPrice();
                        } else if (way.getCoPayType() == 1) {
                            if (item.getBaseType() == 0) {
                                companyPay = way.getCoPayPrice() / 100 * way.getCoMinScope();
                            } else if(item.getBaseType() == 1) {
                                companyPay = way.getCoPayPrice() / 100 * way.getCoMaxScope();
                            } else {
                                if (way.getCoMinScope() > item.getBaseNumber()) {
                                    companyPay = way.getCoPayPrice()  / 100 * way.getCoMinScope();
                                } else if (way.getCoMaxScope() < item.getBaseNumber()) {
                                    companyPay = way.getCoPayPrice()  / 100 * way.getCoMaxScope();
                                } else {
                                    companyPay = way.getCoPayPrice()  / 100 * item.getBaseNumber();
                                }
                            }
                            //公司计算规则  0：四舍五入 1：升角省分（精度为0） 2：去尾  3：进一
                            //公司填写精度 3、2、1、0、-1、-2，对应保留小数位数
                            if (way.getCoComputationRule() == 0) {
                                BigDecimal b = new BigDecimal(companyPay.toString());
                                companyPay = b.setScale(Integer.parseInt(way.getCoComputationalAccuracy().toString().replace(".0","")),
                                        BigDecimal.ROUND_HALF_UP).doubleValue();
                            } else if (way.getCoComputationRule() == 1){
                                companyPay = Math.ceil(new BigDecimal(companyPay.toString()).setScale(1, RoundingMode.DOWN).
                                        doubleValue());
                            } else if (way.getCoComputationRule() == 2) {
                                companyPay = new BigDecimal(companyPay.toString()).setScale(Integer.parseInt(way.getCoComputationalAccuracy().toString()),
                                        RoundingMode.DOWN).doubleValue();
                            } else {
                                companyPay = new BigDecimal(companyPay.toString()).setScale(Integer.parseInt(way.getCoComputationalAccuracy().toString()),
                                        RoundingMode.UP).doubleValue();
                            }
                        } else if (way.getCoPayType() == 2) {
                            //办理方
                            TransactorInsuranceLevel til = way.getTransactorInsuranceLevel();
                            if (null == til) {
                                throw new InterfaceCommonException(StatusConstant.Fail_CODE,"请检查险种档次配置或者跟随办理方配置");
                            }
                            //公司缴纳类型 0：金额  1：比例
                            if (til.getCoPayType() == 0) {
                                companyPay = til.getCoPayPrice();
                            } else {
                                if (item.getBaseType() == 0) {
                                    companyPay = til.getCoPayPrice() / 100 * way.getCoMinScope();
                                } else if(item.getBaseType() == 1) {
                                    companyPay = til.getCoPayPrice() / 100 * way.getCoMaxScope();
                                } else {
                                    if (way.getCoMinScope() > item.getBaseNumber()) {
                                        companyPay = til.getCoPayPrice()  / 100 * way.getCoMinScope();
                                    } else if (way.getCoMaxScope() < item.getBaseNumber()) {
                                        companyPay = til.getCoPayPrice()  / 100 * way.getCoMaxScope();
                                    } else {
                                        companyPay = til.getCoPayPrice()  / 100 * item.getBaseNumber();
                                    }
                                }
                                //公司计算规则  0：四舍五入 1：升角省分（精度为0） 2：去尾  3：进一
                                //公司填写精度 3、2、1、0、-1、-2，对应保留小数位数
                                if (way.getCoComputationRule() == 0) {
                                    BigDecimal b = new BigDecimal(companyPay.toString());
                                    companyPay = b.setScale(Integer.parseInt(way.getCoComputationalAccuracy().toString().replace(".0","")),
                                            BigDecimal.ROUND_HALF_UP).doubleValue();
                                } else if (way.getCoComputationRule() == 1){
                                    companyPay = Math.ceil(new BigDecimal(companyPay.toString()).setScale(1, RoundingMode.DOWN).
                                            doubleValue());
                                } else if (way.getCoComputationRule() == 2) {
                                    companyPay = new BigDecimal(companyPay.toString()).setScale(Integer.parseInt(way.getCoComputationalAccuracy().toString()),
                                            RoundingMode.DOWN).doubleValue();
                                } else {
                                    companyPay = new BigDecimal(companyPay.toString()).setScale(Integer.parseInt(way.getCoComputationalAccuracy().toString()),
                                            RoundingMode.UP).doubleValue();
                                }
                            }
                        }
                        //个人缴纳类型 0：金额  1：比例   2：跟随办理方
                        if (way.getMePayType() == 0) {
                            memberPay = null == way.getMePayPrice() ? 0.0 : way.getMePayPrice();
                        } else if (way.getMePayType() == 1) {
                            if (item.getBaseType() == 0) {
                                memberPay = way.getMePayPrice() / 100  * way.getMeMinScope();
                            } else if(item.getBaseType() == 1) {
                                memberPay = way.getMePayPrice() / 100  * way.getMeMaxScope();
                            } else {
                                if (way.getMeMinScope() > item.getBaseNumber()) {
                                    memberPay = way.getMePayPrice() / 100 * way.getMeMinScope();
                                } else if (way.getMeMaxScope() < item.getBaseNumber()) {
                                    memberPay = way.getMePayPrice() / 100 * way.getMeMaxScope();
                                } else {
                                    memberPay = way.getMePayPrice() / 100 * item.getBaseNumber();
                                }
                            }
                            //个人计算规则  0：四舍五入 1：升角省分（精度为0） 2：去尾  3：进一
                            //个人公司填写精度 3、2、1、0、-1、-2，对应保留小数位数
                            if (way.getMeComputationRule() == 0) {
                                BigDecimal b = new BigDecimal(memberPay.toString());
                                memberPay = b.setScale(Integer.parseInt(way.getMeComputationalAccuracy().toString().replace(".0","")),
                                        BigDecimal.ROUND_HALF_UP).doubleValue();
                            } else if (way.getMeComputationRule() == 1){
                                memberPay = Math.ceil(new BigDecimal(memberPay.toString()).setScale(1, RoundingMode.DOWN).
                                        doubleValue());
                            } else if (way.getMeComputationRule() == 2) {
                                memberPay = new BigDecimal(memberPay.toString()).setScale(Integer.parseInt(way.getMeComputationalAccuracy().toString().replace(".0","")),
                                        RoundingMode.DOWN).doubleValue();
                            } else {
                                memberPay = new BigDecimal(memberPay.toString()).setScale(Integer.parseInt(way.getMeComputationalAccuracy().toString().replace(".0","")),
                                        RoundingMode.UP).doubleValue();
                            }
                        } else if (way.getMePayType() == 2) {
                            //办理方
                            TransactorInsuranceLevel til = way.getTransactorInsuranceLevel();
                            //公司缴纳类型 0：金额  1：比例
                            if (til.getMePayType() == 0) {
                                memberPay = til.getMePayPrice();
                            } else {
                                if (item.getBaseType() == 0) {
                                    memberPay = til.getMePayPrice() / 100  * way.getMeMinScope();
                                } else if(item.getBaseType() == 1) {
                                    memberPay = til.getMePayPrice() / 100  * way.getMeMinScope();
                                } else {
                                    if (way.getMeMinScope() > item.getBaseNumber()) {
                                        memberPay = til.getMePayPrice() / 100 * way.getMeMinScope();
                                    } else if (way.getMeMaxScope() < item.getBaseNumber()) {
                                        memberPay = til.getMePayPrice() / 100 * way.getMeMinScope();
                                    } else {
                                        memberPay = til.getMePayPrice() / 100 * item.getBaseNumber();
                                    }
                                }
                            }
                            //个人计算规则  0：四舍五入 1：升角省分（精度为0） 2：去尾  3：进一
                            //个人公司填写精度 3、2、1、0、-1、-2，对应保留小数位数
                            if (way.getMeComputationRule() == 0) {
                                BigDecimal b = new BigDecimal(memberPay.toString());
                                memberPay = b.setScale(Integer.parseInt(way.getMeComputationalAccuracy().toString().replace(".0","")),
                                        BigDecimal.ROUND_HALF_UP).doubleValue();
                            } else if (way.getMeComputationRule() == 1){
                                memberPay = Math.ceil(new BigDecimal(memberPay.toString()).setScale(1, RoundingMode.DOWN).
                                        doubleValue());
                            } else if (way.getMeComputationRule() == 2) {
                                memberPay = new BigDecimal(memberPay.toString()).setScale(Integer.parseInt(way.getMeComputationalAccuracy().toString().replace(".0","")),
                                        RoundingMode.DOWN).doubleValue();
                            } else {
                                memberPay = new BigDecimal(memberPay.toString()).setScale(Integer.parseInt(way.getMeComputationalAccuracy().toString().replace(".0","")),
                                        RoundingMode.UP).doubleValue();
                            }
                        }
                        companyTotalPay += companyPay;
                        memberTotalPay += memberPay;

                        //type 缴纳方 0：公司 1：个人
                        SocialSecurityInfoItem s1 = new SocialSecurityInfoItem();
                        s1.setInsuranceName(way.getInsuranceName());
                        s1.setPayPrice(companyPay);
                        s1.setType(0);

                        SocialSecurityInfoItem s2 = new SocialSecurityInfoItem();
                        s2.setInsuranceName(way.getInsuranceName());
                        s2.setPayPrice(memberPay);
                        s2.setType(1);
                        socialSecurityInfoItems.add(s1);
                        socialSecurityInfoItems.add(s2);
                    }

                    for (CompanyCooperationMethod method : methodList) {
                        if (member.getWaysOfCooperation().equals(method.getCooperationMethodId())) {
                            taxPrice = (companyTotalPay + memberTotalPay) * method.getPercent() / 100;
                       /* companyTotalPay = companyTotalPay * (1 + method.getPercent() / 100);
                        memberTotalPay = memberTotalPay * (1 + method.getPercent() / 100);*/
                            break;
                        }

                    }
                    ss.setTaxPrice(taxPrice);
                    ss.setCompanyTotalPay(companyTotalPay);
                    ss.setMemberTotalPay(memberTotalPay);
                    ss.setCompanySonBillItemId(companySonBillItemId);
                    ss.setSocialSecurityInfoItems(socialSecurityInfoItems);
                    ss.setCompanySonBillId(companySonBillId);
                    list.add(ss);
                    price += companyTotalPay + memberTotalPay;
                    serviceNowYM = DateUtil.addDate(serviceNowYM, TimeField.Month.ordinal(),1);
                }
            }


        }
        return price;

    }
    /**
     * 生成账单--公积金明细
     * @param business
     * @param list
     * @return
     */
    public Double buildReservedFundsInfo(MemberBusiness business,List<ReservedFundsInfo> list,
                                         List<ReservedFundsInfo> updateListRFI,Date date ,
                                         Integer companySonBillItemId,Integer companySonBillId ,
                                         Map<String ,Boolean> isFirstForeach,
                                         List<MemberMonthPayBusiness> memberMonthPayBusinessList,
                                         Set<Date> dateSet,Date billMonth,
                                         List<CompanyCooperationMethod> methodList) throws ParseException {

        Double price = 0.0;
        //员工信息
        Member member = business.getMember();
        //获取这个员工上最后一条数据
        ReservedFundsInfo rfi = reservedFundsInfoMapper.getLastByMemberId(member.getId());

        //员工公积金信息
        MemberBusinessItem item = business.getMemberBusinessItem();
        Date serviceNowYM = date;
        //循环次数
        int forNum = 1;
        //判断是否是第一次循环社保数据
        if (null == isFirstForeach.get(member.getId()+"rIsFirstForeach") ? true : isFirstForeach.get(member.getId()+"rIsFirstForeach")) {
            //此员工为第一次缴纳 需要补交
            if (Timestamp.parseDate3(item.getBillStartTime(),"yyyy-MM").
                    compareTo(Timestamp.parseDate3(item.getServiceStartTime(),"yyyy-MM")) != 0 &&
                    item.getIsFirstPay() == 1) {
                serviceNowYM = Timestamp.parseDate3(item.getServiceStartTime(),"yyyy-MM");
                Date serviceNowYM1 = Timestamp.parseDate3(item.getServiceStartTime(),"yyyy-MM");
                forNum = 0;
                while (true) {
                    if (DateUtil.daysBetween(serviceNowYM1,date) >= 0 &&
                            (null != item.getServiceEndTime() ?
                                    Timestamp.parseDate3(item.getServiceEndTime(),"yyyy-MM").
                                    compareTo(Timestamp.parseDate3(new Date(),"yyyy-MM")) >= 0 : true)) {
                        forNum ++;
                        serviceNowYM1 = DateUtil.addDate(serviceNowYM1, TimeField.Month.ordinal(),1);
                    } else {
                        break;
                    }
                }
            } else {
                if (null != item.getServiceEndTime() &&
                        Timestamp.parseDate3(item.getServiceEndTime(),"yyyy-MM").
                                compareTo(Timestamp.parseDate3(date,"yyyy-MM")) < 0) {
                    forNum --;
                }
            }
            isFirstForeach.put(member.getId()+"rIsFirstForeach",false);
        } else {
            if (null != item.getServiceEndTime() &&
                    Timestamp.parseDate3(item.getServiceEndTime(),"yyyy-MM").
                            compareTo(Timestamp.parseDate3(date,"yyyy-MM")) < 0) {
                forNum --;
            }
        }

        String payPlaceOrganizationName = (null != item.getPayPlaceName() ? item.getPayPlaceName().replace("中国,","").replace("省,","") : "") + "-" +  item.getOrganizationName();
        String payPlaceName = null != item.getPayPlaceName() ? item.getPayPlaceName().replace("中国,","").replace("省,","") : "";


        out:for (int i = 0 ; i < forNum ; i ++) {
            dateSet.add(Timestamp.parseDate3(serviceNowYM,"yyyy-MM"));
            isFirstForeach.put(member.getId()+"rIsFirstForeach",false);

            if (null != rfi) {
                //员工每月缴纳
                MemberMonthPayBusiness memberMonthPayBusiness = new MemberMonthPayBusiness();
                memberMonthPayBusiness.setServiceMonth(Timestamp.parseDate3(serviceNowYM,"yyyy-MM"));
                memberMonthPayBusiness.setBusinessId(4);
                memberMonthPayBusiness.setMemberId(member.getId());
                memberMonthPayBusiness.setBillMonth(billMonth);
                memberMonthPayBusiness.setCompanySonBillId(business.getCompanySonBillId());
                memberMonthPayBusiness.setCityId(item.getCityId());
                memberMonthPayBusiness.setCompanyId(member.getCompanyId());
                memberMonthPayBusiness.setBillType(0);
                memberMonthPayBusinessList.add(memberMonthPayBusiness);
                //账单-公积金明细
                ReservedFundsInfo rf = new ReservedFundsInfo();
                rf.setUserName(member.getUserName());
                rf.setCertificateType(member.getCertificateType());
                rf.setIdCard(member.getCertificateNum());
                if (null == item.getPayPlaceName() || "null".equals(item.getPayPlaceName())) {
                    item.setPayPlaceName("");
                }
                if (null == item.getOrganizationName() || "null".equals(item.getOrganizationName())) {
                    item.setOrganizationName("");
                }
                rf.setPayPlaceOrganizationName(payPlaceOrganizationName);
                rf.setPayPlaceName(payPlaceName);
                rf.setTransactorName(item.getTransactorName());
                rf.setOrganizationName(item.getOrganizationName());
                rf.setBeginPayYM(item.getServiceStartTime());
                rf.setServiceNowYM(Timestamp.parseDate3(serviceNowYM,"yyyy-MM"));
                rf.setPayCardinalNumber(item.getBaseNumber());
                rf.setMemberNum(item.getCoding());
                rf.setBillMonth(billMonth);
                rf.setPayRatio(item.getRatio());
                Double coTotalPay = rfi.getPracticalCompanyTotalPay();
                Double meTotalPay = rfi.getPracticalMemberTotalPay();
                Double totalPay = coTotalPay + meTotalPay;
                //税费
                Double taxPrice = 0.0;

                for (CompanyCooperationMethod method : methodList) {
                    if (member.getWaysOfCooperation().equals(method.getCooperationMethodId())) {
                        taxPrice = totalPay * 2 * method.getPercent() / 100;
//                        totalPay = totalPay * (1 + method.getPercent() / 100);
                        break;
                    }
                }

                rf.setTaxPrice(taxPrice);
                rf.setCompanyTotalPay(coTotalPay);
                rf.setMemberTotalPay(meTotalPay);
                rf.setCompanySonBillItemId(companySonBillItemId);
                rf.setMemberId(member.getId());
                rf.setCompanySonBillId(companySonBillId);
                rf.setBillMadeMethod(0);
                list.add(rf);
                price += rf.getCompanyTotalPay() + rf.getMemberTotalPay();
                serviceNowYM = DateUtil.addDate(serviceNowYM, TimeField.Month.ordinal(),1);

            } else {
                //是否需要新增 true：是  false：否
                Boolean flag = true;
                for (ReservedFundsInfo rf : updateListRFI) {
                    if (rf.getBillMadeMethod() == 0 && rf.getMemberId().equals(member.getId()) &&
                            Timestamp.parseDate3(serviceNowYM,"yyyy-MM").
                                    compareTo(Timestamp.parseDate3(rf.getServiceNowYM(),"yyyy-MM")) == 0) {

                        flag = true;

                        if (null == item.getPayPlaceName() || "null".equals(item.getPayPlaceName())) {

                            item.setPayPlaceName("");
                        }
                        if (null == item.getOrganizationName() || "null".equals(item.getOrganizationName())) {
                            item.setOrganizationName("");
                        }
                        if (null != item.getCoding()) {
                            rf.setMemberNum(item.getCoding());
                        }
                        rf.setPayPlaceOrganizationName((null != item.getPayPlaceName() ? item.getPayPlaceName().replace("中国,","").replace("省,","") : "") + "-" +  item.getOrganizationName());
                        rf.setPayPlaceName(null != item.getPayPlaceName() ? item.getPayPlaceName().replace("中国,","").replace("省,","") : "");
                        rf.setTransactorName(item.getTransactorName());
                        rf.setOrganizationName(item.getOrganizationName());
                        Double totalPay = item.getBaseNumber() * item.getRatio() / 100;
                        //计算规则  0：四舍五入 1：升角省分（精度为0） 2：去尾  3：进一
                        //填写精度 3、2、1、0、-1、-2，对应保留小数位数
                        if (item.getComputationRule() == 0) {
                            BigDecimal b = new BigDecimal(totalPay.toString());
                            totalPay = b.setScale(item.getComputationalAccuracy(),
                                    BigDecimal.ROUND_HALF_UP).doubleValue();

                        } else if (item.getComputationRule() == 1){
                            totalPay = Math.ceil(new BigDecimal(totalPay.toString()).setScale(1, RoundingMode.DOWN).
                                    doubleValue());
                        } else if (item.getComputationRule() == 2) {
                            totalPay = new BigDecimal(totalPay.toString()).setScale(item.getComputationalAccuracy(),
                                    RoundingMode.DOWN).doubleValue();
                        } else {
                            totalPay = new BigDecimal(totalPay.toString()).setScale(item.getComputationalAccuracy(),
                                    RoundingMode.UP).doubleValue();
                        }
                    /*for (CompanyCooperationMethod method : methodList) {
                        if (member.getWaysOfCooperation().equals(method.getCooperationMethodId())) {
                            totalPay = totalPay * (1 + method.getPercent() / 100);
                            break;
                        }

                    }*/
                        rf.setCompanyTotalPay(totalPay);
                        rf.setMemberTotalPay(totalPay);
                        price += rf.getCompanyTotalPay() + rf.getMemberTotalPay();
                        continue out;
                    }
                }

                if (flag) {
                    //员工每月缴纳
                    MemberMonthPayBusiness memberMonthPayBusiness = new MemberMonthPayBusiness();
                    memberMonthPayBusiness.setServiceMonth(Timestamp.parseDate3(serviceNowYM,"yyyy-MM"));
                    memberMonthPayBusiness.setBusinessId(4);
                    memberMonthPayBusiness.setMemberId(member.getId());
                    memberMonthPayBusiness.setBillMonth(billMonth);
                    memberMonthPayBusiness.setCompanySonBillId(business.getCompanySonBillId());
                    memberMonthPayBusiness.setCityId(item.getCityId());
                    memberMonthPayBusiness.setCompanyId(member.getCompanyId());
                    memberMonthPayBusiness.setBillType(0);
                    memberMonthPayBusinessList.add(memberMonthPayBusiness);
                    //账单-公积金明细
                    ReservedFundsInfo rf = new ReservedFundsInfo();
                    rf.setUserName(member.getUserName());
                    rf.setCertificateType(member.getCertificateType());
                    rf.setIdCard(member.getCertificateNum());
                    if (null == item.getPayPlaceName() || "null".equals(item.getPayPlaceName())) {
                        item.setPayPlaceName("");
                    }
                    if (null == item.getOrganizationName() || "null".equals(item.getOrganizationName())) {
                        item.setOrganizationName("");
                    }
                    rf.setPayPlaceOrganizationName((null != item.getPayPlaceName() ? item.getPayPlaceName().replace("中国,","").replace("省,","") : "") + "-" +  item.getOrganizationName());
                    rf.setPayPlaceName(null != item.getPayPlaceName() ? item.getPayPlaceName().replace("中国,","").replace("省,","") : "");
                    rf.setTransactorName(item.getTransactorName());
                    rf.setOrganizationName(item.getOrganizationName());
                    rf.setBeginPayYM(item.getServiceStartTime());
                    rf.setServiceNowYM(Timestamp.parseDate3(serviceNowYM,"yyyy-MM"));
                    rf.setPayCardinalNumber(item.getBaseNumber());
                    rf.setMemberNum(item.getCoding());
                    rf.setBillMonth(billMonth);
                    rf.setPayRatio(item.getRatio());
                    Double totalPay = item.getBaseNumber() * item.getRatio() / 100;
                    //税费
                    Double taxPrice = 0.0;
                    //计算规则  0：四舍五入 1：升角省分（精度为0） 2：去尾  3：进一
                    //填写精度 3、2、1、0、-1、-2，对应保留小数位数
                    if (item.getComputationRule() == 0) {
                        BigDecimal b = new BigDecimal(totalPay.toString());
                        totalPay = b.setScale(item.getComputationalAccuracy(),
                                BigDecimal.ROUND_HALF_UP).doubleValue();

                    } else if (item.getComputationRule() == 1){
                        totalPay = Math.ceil(new BigDecimal(totalPay.toString()).setScale(1, RoundingMode.DOWN).
                                doubleValue());
                    } else if (item.getComputationRule() == 2) {
                        totalPay = new BigDecimal(totalPay.toString()).setScale(item.getComputationalAccuracy(),
                                RoundingMode.DOWN).doubleValue();
                    } else {
                        totalPay = new BigDecimal(totalPay.toString()).setScale(item.getComputationalAccuracy(),
                                RoundingMode.UP).doubleValue();
                    }
                    for (CompanyCooperationMethod method : methodList) {
                        if (member.getWaysOfCooperation().equals(method.getCooperationMethodId())) {
                            taxPrice = totalPay * 2 * method.getPercent() / 100;
//                        totalPay = totalPay * (1 + method.getPercent() / 100);
                            break;
                        }
                    }

                    rf.setTaxPrice(taxPrice);
                    rf.setCompanyTotalPay(totalPay);
                    rf.setMemberTotalPay(totalPay);
                    rf.setCompanySonBillItemId(companySonBillItemId);
                    rf.setMemberId(member.getId());
                    rf.setCompanySonBillId(companySonBillId);
                    rf.setBillMadeMethod(0);
                    list.add(rf);
                    price += rf.getCompanyTotalPay() + rf.getMemberTotalPay();
                    serviceNowYM = DateUtil.addDate(serviceNowYM, TimeField.Month.ordinal(),1);
                }
            }


        }
        return price;
    }


    /**
     * 合并总账单
     * @param companyId
     */
//    @Transactional
//    public void mergeBill(Integer companyId,Integer userId) throws ParseException {
//
//        Company company = companyMapper.queryCompanyById(companyId);
//
//        if (null == company) {
//            throw new InterfaceCommonException(StatusConstant.NO_DATA,"未知公司");
//        }
//        if (null == company.getBusinesses() || company.getBusinesses().size() < 1) {
//            throw new InterfaceCommonException(StatusConstant.NO_DATA,"检测到该公司下未绑定业务，请先绑定业务");
//        }
//        //公司子账单
//        Map<String , Object> map0 = new HashMap<String, Object>();
//        map0.put("companyId",companyId);
//        List<CompanySonBill> companySonBills = companySonBillMapper.list(map0);
//        if (null == companySonBills || companySonBills.size() < 1 ) {
//            throw new InterfaceCommonException(StatusConstant.NO_DATA,"请先设置为公司子账单");
//        }
//
//        // 计算是否到 生成账单月份
//        //true : 可以生成  false : 不能生成
//        Boolean flag = true;
//        if(null != company.getServiceFeeStartTime() && null != company.getServiceFeeCycle()){
//            final int cycle = company.getServiceFeeCycle(); // 月序周期
//            Date now = DateUtil.setDate(new Date());
//            Date serviceDate = company.getServiceFeeStartTime();
//            while (true){
//                serviceDate = DateUtil.addDate(serviceDate, TimeField.Month.ordinal(), cycle);
//                serviceDate = DateUtil.setDate(serviceDate);
//                if(now.getTime() == serviceDate.getTime()){
//                    flag = true;
//                    break;
//                }
//                if(serviceDate.getTime() > now.getTime()){
//                    flag = false;
//                    break;
//                }
//            }
//        }
//
//
//        //生成的账单
//        List<CompanySonBillItem> companySonBillItems = null;
//        //导入的工资表
//        List<SalaryInfo> salaryInfos = null;
//        if (flag &&
//                (company.getBusinesses().size() > 1 ||
//                    (company.getBusinesses().size() == 1 &&
//                        company.getBusinesses().get(0).getId() != 5))) {
//
//            companySonBillItems =
//                    companySonBillItemMapper.listByCompanyIdAndServiceMonth(companyId,new Date());
//
//            if (companySonBillItems.size() < 1) {
//                throw new InterfaceCommonException(StatusConstant.NO_DATA,"请先生成账单");
//            }
//        }
//
//        //判断是否有工资业务
//        Boolean isHaveSalary = false;
//        for (Business business : company.getBusinesses()) {
//            if (business.getId() == 5) {
//                isHaveSalary = true;
//                break;
//            }
//        }
//        //有工资业务
//        if (isHaveSalary) {
//            //工资
//            salaryInfos = salaryInfoMapper.querySalaryInfoByCompany(companyId, new Date());
//            if (salaryInfos.size() < 1) {
//                throw new InterfaceCommonException(StatusConstant.NO_DATA,"请先导入工资表");
//            }
//        }
//        //生成的账单和导入的工资都不为空
//        if (null != companySonBillItems && null != salaryInfos) {
//            for (CompanySonBillItem item : companySonBillItems) {
//                for (SalaryInfo info : salaryInfos) {
//                    //绑定总账单id
//                    info.setCompanySonBillItemId(item.getId());
//                    //叠加实发工资
//                    item.setTotalPrice(item.getTotalPrice() + info.getTakeHomePay());
//                    //叠加应发工资
//                    item.setReceivablePrice(item.getReceivablePrice() + info.getShouldSendSalary());
//                }
//                //合成总账单
//                item.setIsTotalBill(1);
//            }
//            salaryInfoMapper.updateList(salaryInfos);
//            companySonBillItemMapper.updateList(companySonBillItems);
//        } else if (null != companySonBillItems) {
//            for (CompanySonBillItem item : companySonBillItems) {
//                //合成总账单
//                item.setIsTotalBill(1);
//            }
//            companySonBillItemMapper.updateList(companySonBillItems);
//        } else if (null != salaryInfos) {
//            companySonBillItems = new ArrayList<CompanySonBillItem>();
//            //汇总账单
//            List<CompanySonTotalBill> companySonTotalBills = getCompanySonTotalBills(userId, companySonBills);
//            companySonTotalBillMapper.save(companySonTotalBills);
//
//            //获取是否可以生成服务费单
//            companyService.isServiceFeeMonth(company);
//            //员工增加变记录集合 计算服务费异动量
//            List<MemberBusinessUpdateRecordItem> recordItems = new ArrayList<MemberBusinessUpdateRecordItem>();
//            if (company.getIsCanServceFree() == 1) {
//                //判断本月是否已经生成服务费账单 已生成 就不进行生成
//                if (monthServiceFeeMapper.getByCompanyIdAndServiceMonthCount(companyId,new Date()) < 1) {
//                    //服务费集合
//                    List<MonthServiceFee> monthServiceFees = new ArrayList<MonthServiceFee>();
//                    //获取服务费
//                    getServiceFree(monthServiceFees, companySonTotalBills, company,0,recordItems);
//                }
//            }
//
//            for (CompanySonBillItem item : companySonBillItems) {
//                for (SalaryInfo info : salaryInfos) {
//                    //叠加实发工资
//                    item.setTotalPrice(item.getTotalPrice() + info.getTakeHomePay());
//                    //叠加应发工资
//                    item.setReceivablePrice(item.getReceivablePrice() + info.getShouldSendSalary());
//                }
//                //合成总账单
//                item.setIsTotalBill(1);
//            }
//            //生成并合并成总账单
//            companySonBillItemMapper.save(companySonBillItems);
//            for (CompanySonBillItem item : companySonBillItems) {
//                for (SalaryInfo info : salaryInfos) {
//                    //绑定总账单id
//                    info.setCompanySonBillItemId(item.getId());
//                }
//            }
//            //更新工资
//            salaryInfoMapper.updateList(salaryInfos);
//        }
//
//    }


    /**
     * 获取总账单数据
     * @param companySonBillItems 总账单集合
     * @return
     */
//    public Map<String ,Object> getTheTotalBill(List<CompanySonBillItem> companySonBillItems , Integer userId) {
//
//        Integer[] companySonBillItemIds = new Integer[companySonBillItems.size()];
//
//        Map<String ,Object> map = new HashMap<String, Object>();
//        //账单制作人
//        User user = new User();
//        //服务费
//        Double serviceFee = 0.0;
//        int i = 0;
//        for (CompanySonBillItem item : companySonBillItems) {
//            user = userMapper.queryUserById(userId);
//            if (null == user) {
//                throw new InterfaceCommonException(StatusConstant.NO_DATA,"未知的账单制作人");
//            }
//            companySonBillItemIds[i] = item.getId();
//            i ++;
//        }
//        Date createTime = new Date();
//        //社保缴纳总金额
//        Double socialSecurityPay = socialSecurityInfoMapper.getPayPriceByCompanySonBillItemIdAndCreateTime(companySonBillItemIds,createTime);
//        //公积金
//        Double reservedFundPay = reservedFundsInfoMapper.getPayPriceByCompanySonBillItemIdAndCreateTime(companySonBillItemIds,createTime);
//        //工资
//        Double salaryPay = salaryInfoMapper.getPayPriceByCompanySonBillItemIdAndCreateTime(companySonBillItemIds,createTime);
//
//
//        map.put("socialSecurityPay",socialSecurityPay);
//        map.put("reservedFundPay",reservedFundPay);
//        map.put("salaryPay",salaryPay);
//        map.put("phone",user.getWorkPhone());
//        map.put("createTime",companySonBillItems.get(0).getCreateTime());
//        map.put("producer",user.getUserName());
//        map.put("totalPay",socialSecurityPay + reservedFundPay + salaryPay);
//        map.put("accountConfig",accountConfigMapper.queryAccountConfig());
//        map.put("serviceFee",serviceFee);
//        return map;
//    }

    /**
     * 获取此子账单下在此创建时间中的总账单列表
     * @param companySonBillId
     * @return
     */
    public List<CompanySonBillItem> getByCompanySonBillIdAndCreateTime(Integer companySonBillId,
                                                                Date createTime) {
        return companySonBillItemMapper.getByCompanySonBillIdAndCreateTime(companySonBillId, createTime);
    }




    /**
     * 获取总账单数据
     * @param companySonBillItems 总账单集合
     * @return
     */
    public Map<String ,Object> getTheTotalBill2(List<CompanySonBillItem> companySonBillItems ,Integer userId,
                                                Integer companySonTotalBillId,Integer companySonBillId,
                                                Date createTime) {

        Integer[] companySonBillItemIds = new Integer[companySonBillItems.size()];

        Map<String ,Object> map = new HashMap<String, Object>();
        Map<String ,Object> map1 = new HashMap<String, Object>();
        //账单制作人
        User user = new User();
        int i = 0;
        for (CompanySonBillItem item : companySonBillItems) {
            user = userMapper.queryUserById(userId);
            if (null == user) {
                throw new InterfaceCommonException(StatusConstant.NO_DATA,"未知的账单制作人 ");
            }
            companySonBillItemIds[i] = item.getId();
            i ++;
        }
        map1.put("companySonTotalBillId",companySonTotalBillId);


        List<CompanySonTotalBillDto> companySonTotalBillDtoList = companySonTotalBillMapper.listDto3(map1);
        CompanySonTotalBillDto companySonTotalBillDto;
        if (companySonTotalBillDtoList.size() > 0) {
            companySonTotalBillDto = companySonTotalBillDtoList.get(0);
        } else {
            throw new InterfaceCommonException(StatusConstant.NO_DATA,"暂无账单");
        }


        Company company = companyMapper.queryCompanyByCompanySonTotalBillId(companySonTotalBillId);
        //获取汇总账单
        CompanySonTotalBill companySonTotalBill = companySonTotalBillMapper.info(companySonTotalBillId);
        //服务费

        Double serviceFee = companySonTotalBillDto.getServiceFee();
        // 商业险
        Double businessInsurancePrice = businessInsuranceMapper.countBusinessInsurance(companySonTotalBillId);
        // 一次性险
        Double countBusinessYc = businessYcMapper.countBusinessYc(companySonTotalBillId);
        //社保缴纳总金额
        Double socialSecurityPay = socialSecurityInfoMapper.getPayPriceByCompanySonBillItemIdAndCreateTime(companySonBillId,createTime);
        //纳入次月账单社保缴纳的实际总金额
        Double ssiPracticalPay = socialSecurityInfoMapper.getPracticalByCompanyIdAndBillMonth(null,companySonBillId,null,createTime);
        //公积金
        Double reservedFundPay = reservedFundsInfoMapper.getPayPriceByCompanySonBillItemIdAndCreateTime(companySonBillId,createTime);
        //纳入次月账单公积金缴纳的实际总金额
        Double rfiPracticalPay = reservedFundsInfoMapper.getPracticalByCompanyIdAndBillMonth(null,companySonBillId,null,createTime);
        //工资
        Double salaryPay = salaryInfoMapper.getPayPriceByCompanySonBillItemIdAndCreateTime(companySonTotalBillId,createTime);

        map.put("totalPay",new BigDecimal(socialSecurityPay.toString()).
                add(new BigDecimal(reservedFundPay.toString())).
                add(new BigDecimal(salaryPay.toString())).
                add(new BigDecimal(serviceFee.toString())).
                /* add(new BigDecimal(businessInsurancePrice.toString())).
                 add(new BigDecimal(countBusinessYc.toString())).*/
                        add(new BigDecimal(businessInsurancePrice.toString())).
                        add(new BigDecimal(countBusinessYc.toString())).
                        add(new BigDecimal(companySonTotalBillDto.getTaxPrice().toString())).
//                add(new BigDecimal(taxPrice.toString())).
        subtract(new BigDecimal(companySonTotalBillDto.getLastMonthBalance().toString())).
                        subtract(new BigDecimal("0")).
                        subtract(new BigDecimal(ssiPracticalPay.toString())).
                        subtract(new BigDecimal(rfiPracticalPay.toString())).setScale(2,RoundingMode.DOWN).doubleValue());



        map.put("socialSecurityPay",socialSecurityPay+ssiPracticalPay);
        map.put("reservedFundPay",reservedFundPay + rfiPracticalPay);
        map.put("salaryPay",salaryPay);

        map.put("phone",user.getWorkPhone());
        map.put("createTime",createTime);
        map.put("producer",user.getUserName());
//        map.put("totalPay",new DecimalFormat("#0.00").format(socialSecurityPay + reservedFundPay + salaryPay + serviceFee + businessInsurancePrice + countBusinessYc + ssiPracticalPay + rfiPracticalPay));
        map.put("accountConfig",accountConfigMapper.queryAccountConfig());
        map.put("serviceFee",serviceFee);
        map.put("businessInsurancePrice",businessInsurancePrice);
        map.put("countBusinessYc",countBusinessYc);
        map.put("companyName",null == company.getCompanyName() ? "" : company.getCompanyName());
        map.put("status",companySonTotalBill.getStatus());
        map.put("payTime",company.getPayTime());
        map.put("monthBalance",companySonTotalBillDto.getLastMonthBalance());
        return map;
    }


    /**
     * 获取总账单数据 后台查看
     * @param companyId 公司id
     * @return
     */
    public Map<String ,Object> getTheTotalBill3(Integer companyId,Date billMonth ) {


        Integer userId;
        Map<String ,Object> map = new HashMap<String, Object>();

        Company company = companyMapper.queryCompanyById(companyId);
        List<CompanySonTotalBill> companySonTotalBillList = companySonTotalBillMapper.listByCompanyIdAndBillMonth(companyId,billMonth);
        if (companySonTotalBillList.size() < 1) {
            throw new InterfaceCommonException(StatusConstant.NO_DATA,"暂无账单");
        } else {
            userId = companySonTotalBillList.get(0).getUserId();
        }
        //账单制作人
        User user = userMapper.queryUserById(userId);
        if (null == user) {
            throw new InterfaceCommonException(StatusConstant.NO_DATA,"未知的账单制作人 ");
        }
        Map<String ,Object> map1 = new HashMap<String, Object>();
        map1.put("companyId",companyId);
        map1.put("billMonth",billMonth);
        map.put("flag", 0);
        List<CompanySonTotalBillDto> companySonTotalBillDtoList = companySonTotalBillMapper.listDto(map1);
        CompanySonTotalBillDto companySonTotalBillDto;
        if (companySonTotalBillDtoList.size() > 0) {
            companySonTotalBillDto = companySonTotalBillDtoList.get(0);
        } else {
            throw new InterfaceCommonException(StatusConstant.NO_DATA,"暂无账单");
        }

        //服务费
        Double serviceFee = companySonTotalBillDto.getServiceFee();
      /*  // 商业险
        Double businessInsurancePrice = businessInsuranceMapper.countBusinessInsuranceByCompanyId(companyId,billMonth);
        // 一次性险
        Double countBusinessYc = businessYcMapper.countBusinessYcByCompanyId(companyId,billMonth);*/
        //本次账单月社保缴纳总金额
        Double socialSecurityPay = socialSecurityInfoMapper.getPayPriceByCompanyIdAndBillMonth(companyId,billMonth);
        //纳入次月账单社保缴纳的实际总金额
        Double ssiPracticalPay = socialSecurityInfoMapper.getPracticalByCompanyIdAndBillMonth(companyId,null,billMonth,null);

        //本次账单月公积金预收
        Double reservedFundPay = reservedFundsInfoMapper.getPayPriceByCompanyIdAndBillMonth(companyId,billMonth);
        //纳入次月账单公积金缴纳的实际总金额
        Double rfiPracticalPay = reservedFundsInfoMapper.getPracticalByCompanyIdAndBillMonth(companyId,null,billMonth,null);
        //工资
        Double salaryPay = salaryInfoMapper.getPayPriceByCompanyIdAndBillMonth(companyId,billMonth);
        //获取上月稽核服务费
        MonthServiceFeeBalance monthServiceFeeBalance = monthServiceFeeBalanceMapper.
                getByCompanyIdAndBillMonth2(companySonTotalBillDto.getCompanyId(), companySonTotalBillDto.getBillMonth());
        Double lastMonthServiceFee = 0.0;
        //税费
//        Double taxPrice = 0.0;
        if (null != monthServiceFeeBalance) {
            lastMonthServiceFee = monthServiceFeeBalance.getServiceFeeBalance();
           /* if (null != companySonTotalBillDto.getCompanyCooperationMethodJson2()) {
                //服务费配置集合 上月
                List<CompanyCooperationMethod> methodList2 = com.alibaba.fastjson.JSONArray.parseArray(companySonTotalBillDto.getCompanyCooperationMethodJson2(),CompanyCooperationMethod.class);
                if (methodList2.get(0).getIsPercent() == 1) {
                    taxPrice += new BigDecimal(lastMonthServiceFee.toString()).multiply(new BigDecimal(methodList2.get(0).getPercent().toString())).doubleValue();
                }
            }*/
        }


        /*//服务费配置集合
        List<CompanyCooperationMethod> methodList;
        if (null == companySonTotalBillDto.getCompanyCooperationMethodJson()) {
            methodList = companyCooperationMethodMapper.queryCompanyCooperationMethod(companySonTotalBillDto.getCompanyId());
        } else {
            methodList = com.alibaba.fastjson.JSONArray.parseArray(companySonTotalBillDto.getCompanyCooperationMethodJson(),CompanyCooperationMethod.class);
        }

        if (null != companySonTotalBillDto.getCompanyCooperationMethodJson2()) {
            //服务费配置集合 上月
            List<CompanyCooperationMethod> methodList2 = com.alibaba.fastjson.JSONArray.parseArray(companySonTotalBillDto.getCompanyCooperationMethodJson2(),CompanyCooperationMethod.class);
            taxPrice += new BigDecimal(ssiPracticalPay.toString()).add(new BigDecimal(rfiPracticalPay.toString())).multiply(new BigDecimal(methodList2.get(0).getPercent())).doubleValue();
        }
        // TODO: 2018/1/8
        BigDecimal price = new BigDecimal(0.0).
                subtract(new BigDecimal(companySonTotalBillDto.getLastMonthBalance().toString())).
                subtract(new BigDecimal(ssiPracticalPay.toString())).
                subtract(new BigDecimal(rfiPracticalPay.toString())).
                subtract(new BigDecimal(lastMonthServiceFee.toString())).
                add(new BigDecimal(companySonTotalBillDto.getServiceFee().toString())).
                add(new BigDecimal(getDouble(companySonTotalBillDto.getInsurancePrice()).toString())).
                add(new BigDecimal(getDouble(companySonTotalBillDto.getYcPrice()).toString())).
                add(new BigDecimal(companySonTotalBillDto.getSalaryInfoPrice().toString()));
        //当月税费 - 上月税费 = 当前税费
        taxPrice = new BigDecimal(new BigDecimal(companySonTotalBillDto.getTotalPrice().toString()).
                add(new BigDecimal(companySonTotalBillDto.getSalaryInfoPrice().toString())).toString()).
                multiply(new BigDecimal(methodList.get(0).getPercent().toString())).subtract(new BigDecimal(taxPrice.toString())).doubleValue();
        if (methodList.get(0).getIsPercent() == 1) {
            companySonTotalBillDto.setServiceFee(new BigDecimal(companySonTotalBillDto.getServiceFee().toString()).
                    multiply(new BigDecimal(methodList.get(0).getPercent().toString())).
                    add(new BigDecimal(companySonTotalBillDto.getServiceFee().toString())).doubleValue());
            taxPrice = new BigDecimal(new BigDecimal(companySonTotalBillDto.getTotalPrice().toString()).
                    add(new BigDecimal(companySonTotalBillDto.getSalaryInfoPrice().toString()).
                            add(new BigDecimal(companySonTotalBillDto.getServiceFee().toString()))).toString()).
                    multiply(new BigDecimal(methodList.get(0).getPercent().toString())).subtract(new BigDecimal(taxPrice.toString())).doubleValue();
        }*/

       /* companySonTotalBillDto.setTotalPrice(new BigDecimal(companySonTotalBillDto.getTotalPrice().toString()).add(price).add(new BigDecimal(taxPrice.toString())).setScale(2,BigDecimal.ROUND_DOWN).doubleValue());
        companySonTotalBillDto.setReceivablePrice(new BigDecimal(companySonTotalBillDto.getReceivablePrice().toString()).add(price).add(new BigDecimal(taxPrice.toString())).setScale(2,BigDecimal.ROUND_DOWN).doubleValue());
        companySonTotalBillDto.setTaxPrice(taxPrice);*/
       // TODO: 2018/1/8
        BigDecimal price = new BigDecimal(0.0).
                subtract(new BigDecimal(companySonTotalBillDto.getLastMonthBalance().toString())).
                subtract(new BigDecimal(ssiPracticalPay.toString())).
                subtract(new BigDecimal(rfiPracticalPay.toString())).
                subtract(new BigDecimal(lastMonthServiceFee.toString())).
                add(new BigDecimal(companySonTotalBillDto.getServiceFee().toString())).
                add(new BigDecimal(companySonTotalBillDto.getTaxPrice().toString())).
                add(new BigDecimal(getDouble(companySonTotalBillDto.getInsurancePrice()).toString())).
                add(new BigDecimal(getDouble(companySonTotalBillDto.getYcPrice()).toString())).
                add(new BigDecimal(companySonTotalBillDto.getSalaryInfoPrice().toString()));

        map.put("socialSecurityPay",new BigDecimal(socialSecurityPay.toString()).subtract(new BigDecimal(ssiPracticalPay.toString())).doubleValue());
        map.put("reservedFundPay",new BigDecimal(reservedFundPay.toString()).subtract(new BigDecimal(rfiPracticalPay.toString())).doubleValue());
        map.put("salaryPay",salaryPay);
        map.put("phone",user.getWorkPhone());
        map.put("createTime",companySonTotalBillDto.getCreateTime());
        map.put("producer",user.getUserName());
        map.put("taxPrice",companySonTotalBillDto.getTaxPrice());
        map.put("receivablePrice",new BigDecimal(companySonTotalBillDto.getTotalPrice().toString()).add(price).setScale(2,BigDecimal.ROUND_DOWN).doubleValue());
        map.put("totalPay",new BigDecimal(socialSecurityPay.toString()).
                add(new BigDecimal(reservedFundPay.toString())).
                add(new BigDecimal(salaryPay.toString())).
                add(new BigDecimal(serviceFee.toString())).
               /* add(new BigDecimal(businessInsurancePrice.toString())).
                add(new BigDecimal(countBusinessYc.toString())).*/
                add(new BigDecimal(getDouble(companySonTotalBillDto.getInsurancePrice()).toString())).
                add(new BigDecimal(getDouble(companySonTotalBillDto.getYcPrice()).toString())).
                        add(new BigDecimal(companySonTotalBillDto.getTaxPrice().toString())).
//                add(new BigDecimal(taxPrice.toString())).
//                subtract(new BigDecimal(companySonTotalBillDto.getLastMonthBalance().toString())).
                subtract(new BigDecimal(lastMonthServiceFee.toString())).
                subtract(new BigDecimal(ssiPracticalPay.toString())).
                subtract(new BigDecimal(rfiPracticalPay.toString())).setScale(2,RoundingMode.DOWN).doubleValue());
        map.put("accountConfig",accountConfigMapper.queryAccountConfig());
        map.put("serviceFee",new BigDecimal(serviceFee.toString()).subtract(new BigDecimal(lastMonthServiceFee.toString())).doubleValue());
        map.put("businessInsurancePrice",companySonTotalBillDto.getInsurancePrice());
        /*map.put("businessInsurancePrice",businessInsurancePrice);
        map.put("countBusinessYc",countBusinessYc);*/
        map.put("countBusinessYc",companySonTotalBillDto.getYcPrice());
        map.put("companyName",null == company.getCompanyName() ? "" : company.getCompanyName());
        map.put("status",companySonTotalBillDto.getStatus());
        map.put("payTime",company.getPayTime());
        map.put("billMonth",billMonth);
        map.put("monthBalance",companySonTotalBillDto.getLastMonthBalance());
        return map;
    }


    /**
     * 社保账单稽核
     * @param serviceMonth 服务月
     * @param cityType 城市类型 0：成都 2：通用
     * @param map
     * @param operateType 操作类型  0：覆盖新增  1：追加
     * @param list 用于处理 员工的社保编号
     */
    @Transactional
    public void auditSocialSecurityBill(Set<Date> serviceMonth ,Map<String ,SocialSecurityImportData> map,
                                        List<SocialSecurityImportData> list,Set<String> idCardSet,
                                        Integer cityType,Integer operateType) throws ParseException, IOException {

        // 员工社保编码处理 Start
        if(null != list && list.size() > 0){
            List<String> idCards = new ArrayList<String>();
            for (SocialSecurityImportData data : list) {
                if(!CommonUtil.isEmpty(data.getIdCard(),data.getSocialSecurityNumber())){
                    idCards.add(data.getIdCard());
                    idCardSet.add(data.getIdCard());
                }
            }
            if (idCards.size() > 0) {
                List<Member> members = memberMapper.queryMemberByIdCardList(idCards);
                // 匹配员工ID 创建社保号员工对象
                if(null != members && members.size() > 0){
                    // 通过 社保号继续查询员工的社保号
                    Set<MemberNumber> numbers = memberNumberMapper.queryMemberNumberByIdCards(idCards);

                    for (Member member : members) {
                        for (SocialSecurityImportData data : list) {
                            if(!CommonUtil.isEmpty(data.getSocialSecurityNumber()) && data.getIdCard().equals(member.getCertificateNum())){
                                MemberNumber memberNumber = new MemberNumber();
                                memberNumber.setMemberId(member.getId());
                                memberNumber.setSerialNumber(data.getSocialSecurityNumber());
                                memberNumber.setPayPlaceId(data.getPayPlaceId());
                                if(null == numbers){
                                    numbers = new HashSet<MemberNumber>();
                                }
                                numbers.add(memberNumber);
                                break;
                            }
                        }
                    }
                    List<MemberNumber> memberNumbers = new ArrayList<MemberNumber>();
                    Iterator<MemberNumber> iterator = numbers.iterator();
                    while (iterator.hasNext()){
                        MemberNumber next = iterator.next();
                        if(null == next.getId()){
                            memberNumbers.add(next);
                        }
                    }
                    if(memberNumbers.size() > 0){
                        memberNumberMapper.batchAdd(memberNumbers);
                    }
                }
            }
        }
        // 员工社保编码处理 End

        if (idCardSet.size() == 0) {
            throw new InterfaceCommonException(StatusConstant.NO_DATA,"没有员工证件编号");
        }
        List<Member> memberBusinessList = memberMapper.getBusinessItem(idCardSet,3);

        Set<Integer> companyIdSet = new HashSet<Integer>();
        if (memberBusinessList.size() < 1) {
            return;
        }
        for (Member member : memberBusinessList) {
            companyIdSet.add(member.getCompanyId());
        }

        {
            List<CompanySonBillItem> companySonBillItemsUpd = new ArrayList<CompanySonBillItem>();
            //根据服务月查询所有未稽核账单 预收型
            List<CompanySonBillItem> companySonBillItems = companySonBillItemMapper.getByServiceMonth(new Date(),null,companyIdSet,new Date());

            if (companySonBillItems.size() > 0) {
                Set<Integer> sonBillItemId = new HashSet<Integer>();
                for (CompanySonBillItem item : companySonBillItems) {
                    sonBillItemId.add(item.getId());
                }
                List<SocialSecurityInfo> ssiList = socialSecurityInfoMapper.listBySonBillItemId(sonBillItemId);
                if (ssiList.size() > 0) {

                    Set<Integer> ssiIds = new HashSet<Integer>();
                    for (SocialSecurityInfo info : ssiList) {
                        ssiIds.add(info.getId());
                    }
                    List<SocialSecurityInfoItem> ssiiList =
                            socialSecurityInfoItemMapper.listBySSIId(ssiIds);
                    //需要更新的社保集合
                    List<SocialSecurityInfo> socialSecurityInfos = new ArrayList<SocialSecurityInfo>();
                    //需要更新的社保险种集合
                    List<SocialSecurityInfoItem> socialSecurityInfoItems = new ArrayList<SocialSecurityInfoItem>();
                    //需要更新的员工业务集合
                    List<MemberBusinessItem> mbiUpdList = new ArrayList<MemberBusinessItem>();

                    //预收型
                    for (CompanySonBillItem item : companySonBillItems) {
                        //实际缴纳总金额
                        Double totalPrice = 0.0;
                        //预收金额
                        Double receivablePrice = 0.0;
                        //上一次上传的实作金额
                        Double oldImplementationPrice = 0.0;
                        //重新上传的实作金额
                        Double implementationPrice = 0.0;

                        //已收取纳入应收的金额
                        Double receivablePrice2 = 0.0;
                        //新上传
                        Double newReceivablePrice2 = 0.0;

                        out:for (int k = 0; k < ssiList.size(); k++) {
                            if (ssiList.get(k).getCompanySonBillItemId().equals(item.getId())) {
                                SocialSecurityImportData data = map.get(ssiList.get(k).getIdCard() +
                                        Timestamp.timesTamp2(ssiList.get(k).getServiceNowYM(),"yyyy-MM").toString());

                                //是否纳入应收 false 否  true 是
                                Boolean isReceivable = false;
                                //是否有此项业务 false 否  true 是
                                Boolean isHaveBusiness = false;
                                //需要更新的员工业务
                                MemberBusinessItem mbiUpd = new MemberBusinessItem();
                                ss:for (Member member : memberBusinessList) {
                                    if (member.getCertificateNum().equals(ssiList.get(k).getIdCard())) {
                                        isHaveBusiness = true;
                                        if (null != member.getMemberBusinessItems()) {
                                            for (MemberBusinessItem businessItem : member.getMemberBusinessItems()) {
                                                mbiUpd = businessItem;
                                                if (businessItem.getIsReceivable() == 1) {
                                                    isReceivable = true;
                                                    break ss;
                                                }
                                            }
                                        }
                                    }
                                }

                                if (isHaveBusiness) {
                                    if (ssiList.get(k).getBillMadeMethod() == 0) {
                                        receivablePrice += ssiList.get(k).getCompanyTotalPay() + ssiList.get(k).getMemberTotalPay();
                                    } else {
                                        oldImplementationPrice += ssiList.get(k).getCompanyTotalPay() + ssiList.get(k).getMemberTotalPay();
                                        if (isReceivable) {
                                            receivablePrice2 += ssiList.get(k).getCompanyTotalPay() + ssiList.get(k).getMemberTotalPay();
                                        }
                                    }
                                }

                                if (null != data) {
                                    ssiList.get(k).setBillMonth(item.getBillMonth());
                                    //设置社保编码 次月使用
                                    mbiUpd.setCoding(data.getSocialSecurityNumber());
                                    mbiUpdList.add(mbiUpd);
//                                    ssiList.get(k).setSocialSecurityNum(data.getSocialSecurityNumber());
                                    ssiList.get(k).setRealDoTime(new Date());
                                    ssiList.get(k).setPracticalCompanyTotalPay(data.getCompanyCountPrice(cityType));
                                    ssiList.get(k).setPracticalMemberTotalPay(data.getPersonCountPrice(cityType));
                                    if (ssiList.get(k).getBillMadeMethod() == 1) {
                                        Double percent = ssiList.get(k).getTaxPrice() / (ssiList.get(k).getCompanyTotalPay() +  ssiList.get(k).getMemberTotalPay());
                                        ssiList.get(k).setCompanyTotalPay(data.getCompanyCountPrice(cityType));
                                        ssiList.get(k).setMemberTotalPay(data.getPersonCountPrice(cityType));
                                        ssiList.get(k).setTaxPrice((ssiList.get(k).getCompanyTotalPay() +  ssiList.get(k).getMemberTotalPay()) * percent);
                                    }
                                    ssiList.get(k).setIsUploadKaoPan(1);
                                    if (isHaveBusiness) {
                                        if (ssiList.get(k).getBillMadeMethod() == 0) {
                                            totalPrice += data.getCompanyCountPrice(cityType) + data.getPersonCountPrice(cityType);
                                        } else {
                                            implementationPrice += data.getCompanyCountPrice(cityType) + data.getPersonCountPrice(cityType);
                                            if (isReceivable) {
                                                newReceivablePrice2 += ssiList.get(k).getCompanyTotalPay() + ssiList.get(k).getMemberTotalPay();
                                            }
                                        }
                                    }


                                    if (cityType == 0 ) {
                                        for (int i = 0; i < ssiiList.size(); i++) {
                                            if (ssiiList.get(i).getSocialSecurityInfoId().equals(ssiList.get(k).getId())) {
                                                if (ssiiList.get(i).getInsuranceName().contains("工伤")) {
                                                    if (ssiiList.get(i).getType() == 0 ) {
                                                        ssiiList.get(i).setPracticalPayPrice(data.getInjuryCompanyPrice());
                                                        if (ssiList.get(k).getBillMadeMethod() == 1) {
                                                            ssiiList.get(i).setPayPrice(data.getInjuryCompanyPrice());
                                                        }
                                                    } else {
                                                        ssiiList.get(i).setPracticalPayPrice(data.getInjurySelfPrice());
                                                        if (ssiList.get(k).getBillMadeMethod() == 1) {
                                                            ssiiList.get(i).setPayPrice(data.getInjurySelfPrice());
                                                        }
                                                    }
                                                } else if (ssiiList.get(i).getInsuranceName().contains("生育")){
                                                    if (ssiiList.get(i).getType() == 0 ) {
                                                        ssiiList.get(i).setPracticalPayPrice(data.getBearCompanyPrice());
                                                        if (ssiList.get(k).getBillMadeMethod() == 1) {
                                                            ssiiList.get(i).setPayPrice(data.getBearCompanyPrice());
                                                        }
                                                    } else {
                                                        ssiiList.get(i).setPracticalPayPrice(0.0);
                                                    }
                                                }  else if (ssiiList.get(i).getInsuranceName().contains("大病")){
                                                    if (ssiiList.get(i).getType() == 0 ) {
                                                        ssiiList.get(i).setPracticalPayPrice(data.getIllnessCompanyPrice());
                                                        if (ssiList.get(k).getBillMadeMethod() == 1) {
                                                            ssiiList.get(i).setPayPrice(data.getIllnessCompanyPrice());
                                                        }
                                                    } else {
                                                        ssiiList.get(i).setPracticalPayPrice(data.getIllnessSelfPrice());
                                                        if (ssiList.get(k).getBillMadeMethod() == 1) {
                                                            ssiiList.get(i).setPayPrice(data.getIllnessSelfPrice());
                                                        }
                                                    }
                                                }  else if (ssiiList.get(i).getInsuranceName().contains("失业")){
                                                    if (ssiiList.get(i).getType() == 0 ) {
                                                        ssiiList.get(i).setPracticalPayPrice(data.getWorkCompanyPrice());
                                                        if (ssiList.get(k).getBillMadeMethod() == 1) {
                                                            ssiiList.get(i).setPayPrice(data.getWorkCompanyPrice());
                                                        }
                                                    } else {
                                                        ssiiList.get(i).setPracticalPayPrice(data.getWorkPersonPrice());
                                                        if (ssiList.get(k).getBillMadeMethod() == 1) {
                                                            ssiiList.get(i).setPayPrice(data.getWorkPersonPrice());
                                                        }
                                                    }
                                                }  else if (ssiiList.get(i).getInsuranceName().contains("医疗")){
                                                    if (ssiiList.get(i).getType() == 0 ) {
                                                        ssiiList.get(i).setPracticalPayPrice(data.getMedicalCompanyPrice());
                                                        if (ssiList.get(k).getBillMadeMethod() == 1) {
                                                            ssiiList.get(i).setPayPrice(data.getMedicalCompanyPrice());
                                                        }
                                                    } else {
                                                        ssiiList.get(i).setPracticalPayPrice(data.getMedicalSelfPrice());
                                                        if (ssiList.get(k).getBillMadeMethod() == 1) {
                                                            ssiiList.get(i).setPayPrice(data.getMedicalSelfPrice());
                                                        }
                                                    }
                                                }  else if (ssiiList.get(i).getInsuranceName().contains("养老")){
                                                    if (ssiiList.get(i).getType() == 0 ) {
                                                        ssiiList.get(i).setPracticalPayPrice(data.getProvisionCompanyPrice());
                                                        if (ssiList.get(k).getBillMadeMethod() == 1) {
                                                            ssiiList.get(i).setPayPrice(data.getProvisionCompanyPrice());
                                                        }
                                                    } else {
                                                        ssiiList.get(i).setPracticalPayPrice(data.getProvisionSelfPrice());
                                                        if (ssiList.get(k).getBillMadeMethod() == 1) {
                                                            ssiiList.get(i).setPayPrice(data.getProvisionSelfPrice());
                                                        }
                                                    }
                                                }  else if (ssiiList.get(i).getInsuranceName().contains("残保金")){
                                                    if (ssiiList.get(i).getType() == 0 ) {
                                                        ssiiList.get(i).setPracticalPayPrice(data.getResidualCompanyPrice());
                                                        if (ssiList.get(k).getBillMadeMethod() == 1) {
                                                            ssiiList.get(i).setPayPrice(data.getResidualCompanyPrice());
                                                        }
                                                    } else {
                                                        ssiiList.get(i).setPracticalPayPrice(data.getResidualSelfPrice());
                                                        if (ssiList.get(k).getBillMadeMethod() == 1) {
                                                            ssiiList.get(i).setPayPrice(data.getResidualSelfPrice());
                                                        }
                                                    }
                                                } else {
                                                    ssiiList.get(i).setPracticalPayPrice(0.0);
                                                }
                                                socialSecurityInfoItems.add(ssiiList.get(i));
                                                ssiiList.remove(i);
                                                i --;
                                            }
                                        }
                                    } else {
                                        if (null != data.getSocialSecurityCommonList()) {
                                            for (SocialSecurityCommon common : data.getSocialSecurityCommonList()) {
                                                for (int i = 0; i < ssiiList.size(); i++) {
                                                    if (ssiiList.get(i).getSocialSecurityInfoId().equals(ssiList.get(k).getId())) {
                                                        if (common.getInsuranceName().equals(ssiiList.get(i).getInsuranceName())) {
                                                            if (ssiiList.get(i).getType() == 0 ) {
                                                                ssiiList.get(i).setPracticalPayPrice(common.getCompanyPrice());
                                                                if (ssiList.get(k).getBillMadeMethod() == 1) {
                                                                    ssiiList.get(i).setPayPrice(common.getCompanyPrice());
                                                                }
                                                            } else {
                                                                ssiiList.get(i).setPracticalPayPrice(common.getPersonPrice());
                                                                if (ssiList.get(k).getBillMadeMethod() == 1) {
                                                                    ssiiList.get(i).setPayPrice(common.getPersonPrice());
                                                                }
                                                            }
                                                            socialSecurityInfoItems.add(ssiiList.get(i));
                                                            ssiiList.remove(i);
                                                            i --;
                                                        }
                                                    }
                                                }

                                            }
                                        }
                                    }
                                    map.remove(ssiList.get(k).getIdCard() +
                                            Timestamp.timesTamp2(ssiList.get(k).getServiceNowYM(),"yyyy-MM").toString());
                                } else {

                                    if (operateType == 0) {
                                        ssiList.get(k).setPracticalCompanyTotalPay(0.0);
                                        ssiList.get(k).setPracticalMemberTotalPay(0.0);
                                        ssiList.get(k).setIsUploadKaoPan(0);
                                        for (int i = 0; i < ssiiList.size(); i++) {
                                            if (ssiiList.get(i).getSocialSecurityInfoId().equals(ssiList.get(k).getId())) {
                                                ssiiList.get(i).setPracticalPayPrice(0.0);
                                                socialSecurityInfoItems.add(ssiiList.get(i));
                                                ssiiList.remove(i);
                                                i --;
                                            }
                                        }
                                    } else {
                                        ssiList.remove(k);
                                        k--;
                                        continue out;
                                    }


                                }
                                ssiList.get(k).setIsAudit(1);
                                socialSecurityInfos.add(ssiList.get(k));
                                ssiList.remove(k);
                                k--;
                            }
                        }

                        if (operateType == 0) {
                            //社保是否稽核 社保
                            item.setSocialSecurityPracticalPrice(receivablePrice - totalPrice);
//                            item.setIsSocialSecurityAudit(receivablePrice - totalPrice == 0 ? 1 : 0);
                            item.setSsPaidInPrice(totalPrice + implementationPrice);
                            item.setIsUploadKaoPanS(1);
                            item.setReceivablePrice(item.getReceivablePrice() - oldImplementationPrice + implementationPrice);
                            item.setTotalPrice(item.getTotalPrice() - receivablePrice2 + newReceivablePrice2);
                            if (null != item.getIsUploadKaoPanR() && item.getIsUploadKaoPanR() == 1) {
//                            item.setTotalPrice(item.getRfPaidInPrice() + item.getSsPaidInPrice());
                                item.setAuditTheDifference(item.getSocialSecurityPracticalPrice() + item.getReservedFundPracticalPrice());
//                        item.setIsAudit(1);
                            } else {
//                            item.setTotalPrice(item.getSsPaidInPrice());
                                item.setAuditTheDifference(item.getSocialSecurityPracticalPrice());
                            }
                        } else {
                            //社保是否稽核 社保
                            item.setSocialSecurityPracticalPrice(item.getSocialSecurityPracticalPrice() + receivablePrice - totalPrice);
//                            item.setIsSocialSecurityAudit(receivablePrice - totalPrice == 0 ? 1 : 0);
                            item.setSsPaidInPrice(item.getSsPaidInPrice() + totalPrice + implementationPrice);
                            item.setIsUploadKaoPanS(1);
                            item.setReceivablePrice(item.getReceivablePrice() - oldImplementationPrice + implementationPrice);
                            item.setTotalPrice(item.getTotalPrice() - receivablePrice2 + newReceivablePrice2);
                            if (null != item.getIsUploadKaoPanR() && item.getIsUploadKaoPanR() == 1) {
//                            item.setTotalPrice(item.getRfPaidInPrice() + item.getSsPaidInPrice());
                                item.setAuditTheDifference(item.getSocialSecurityPracticalPrice() + item.getReservedFundPracticalPrice());
//                        item.setIsAudit(1);
                            } else {
//                            item.setTotalPrice(item.getSsPaidInPrice());
                                item.setAuditTheDifference(item.getSocialSecurityPracticalPrice());
                            }
                        }


                    }

                    //预收型
                    if (socialSecurityInfoItems.size() > 0) {
                        socialSecurityInfoItemMapper.updateList(socialSecurityInfoItems);
                    }
                    if (socialSecurityInfos.size() > 0) {
                        socialSecurityInfoMapper.updateList(socialSecurityInfos);
                    }
                    if (companySonBillItems.size() > 0) {
                        companySonBillItemsUpd.addAll(companySonBillItems);
                    }
                    if (mbiUpdList.size() > 0) {
                        memberBusinessItemMapper.updateList(mbiUpdList);
                    }
                }
            }
            if (companySonBillItemsUpd.size() > 0) {
                companySonBillItemMapper.updateList(companySonBillItemsUpd);
            }
        }

        if (map.size() > 0){
            List<CompanySonBillItem> companySonBillItemsUpd = new ArrayList<CompanySonBillItem>();
            //未产生账单的实作型或已产生账单的实作型
            //已产生账单的实作型
            List<CompanySonBillItem> companySonBillItems2 = companySonBillItemMapper.getByServiceMonth(new Date(),1,companyIdSet,new Date());
            if (companySonBillItems2.size() > 0) {
                //实作型员工
                List<Member> members = memberMapper.getMemberByBillMadeMethod(1);
                /*Set<Integer> companyIds = new HashSet<Integer>();
                for (Member member : members) {
                    if (null != map.get(member.getCertificateNum())) {
                        companyIds.add(member.getCompanyId());
                    }
                }*/
                //后面计算税费使用
                List<Member> taxMemberList = new ArrayList<Member>();

                //公司集合
                List<Company> companyList = companyMapper.queryCompanyByIds(companyIdSet.toArray(new Integer[companyIdSet.size()])/*companyIds.toArray(new Integer[companyIds.size()])*/);
                //进行添加的社保信息
                List<SocialSecurityInfo> socialSecurityInfosAdd = new ArrayList<SocialSecurityInfo>();
                //社保险种信息
                List<SocialSecurityInfoItem> socialSecurityInfoItemAddAll = new ArrayList<SocialSecurityInfoItem>();
                //社保缴纳明细
                List<MemberMonthPayBusiness> monthPayBusinessList = new ArrayList<MemberMonthPayBusiness>();
                for (CompanySonBillItem item : companySonBillItems2) {

                    List<Member> memberList = new ArrayList<Member>();
                    for (int i = 0; i < members.size(); i++) {
                        if (members.get(i).getCompanyId().equals(item.getCompanyId())) {
                            memberList.add(members.get(i));
                            taxMemberList.add(members.get(i));
                            members.remove(i);
                            i--;
                        }
                    }

                    Double[] price = buildSocialSecurityInfo(map, memberList, socialSecurityInfosAdd,
                            monthPayBusinessList,item.getServiceMonth(),
                            item.getBillMonth(),item.getCompanySonBillId(),
                            item.getCompanyId(),item.getId(),cityType);
                    //社保是否稽核 社保
                    item.setSocialSecurityPracticalPrice(0.0);
//                    item.setIsSocialSecurityAudit(1);
                    item.setIsUploadKaoPanS(1);
                    item.setSsPaidInPrice(item.getSsPaidInPrice()+price[0]);
                    item.setAuditTheDifference(null);
                    item.setIsUploadKaoPanS(1);
                    item.setReceivablePrice(item.getReceivablePrice() + price[0]);
                    item.setTotalPrice(item.getTotalPrice() + price[1]);
//                    if (null != item.getIsUploadKaoPanR() && item.getIsUploadKaoPanR() == 1) {
//                        item.setTotalPrice(item.getRfPaidInPrice() + item.getSsPaidInPrice());
//                        item.setIsAudit(1);
//                    }  else {
//                        item.setTotalPrice(item.getSsPaidInPrice());
//                    }
                }
                //实作型
                if (companySonBillItems2.size() > 0) {
                    companySonBillItemsUpd.addAll(companySonBillItems2);
                }
                if (socialSecurityInfosAdd.size() > 0) {
                    socialSecurityInfoMapper.save(socialSecurityInfosAdd);
                }
                if (monthPayBusinessList.size() > 0) {
                    memberMonthPayBusinessMapper.saveList(monthPayBusinessList);
                }

                for (SocialSecurityInfo info : socialSecurityInfosAdd) {
                    for (SocialSecurityInfoItem item : info.getSocialSecurityInfoItems()) {
                        item.setSocialSecurityInfoId(info.getId());
                        socialSecurityInfoItemAddAll.add(item);
                    }
                }
                if (socialSecurityInfoItemAddAll.size() > 0) {
                    socialSecurityInfoItemMapper.save(socialSecurityInfoItemAddAll);
                }
                List<MonthServiceFee> addListMSF = new ArrayList<MonthServiceFee>();
                //需要添加的服务费明细
                List<MonthServiceFeeDetail> addListMSFD = new ArrayList<MonthServiceFeeDetail>();


                //需要更新的异动量
                List<MemberBusinessUpdateRecordItem> updateRecordItemList = new ArrayList<MemberBusinessUpdateRecordItem>();
                for (int i = 0; i < companyList.size(); i++) {
                    for (CompanySonBillItem item : companySonBillItems2) {
                        if (item.getCompanyId().equals(companyList.get(i).getId())) {
                            List<CompanySonTotalBill> companySonTotalBillList =
                                    companySonTotalBillMapper.getByBillMonth(Timestamp.parseDate3(item.getBillMonth(),"yyyy-MM"),companyList.get(i).getId());
                            List<Date> dateList = companySonTotalBillMapper.getDateByBillMonthAndCompanyId(Timestamp.parseDate3(item.getBillMonth(),"yyyy-MM"),companyList.get(i).getId());
                            //汇总账单的id集合
                            Set<Integer> companySonTotalBillIdSet = new HashSet<Integer>();
                            //服务费配置集合
                            List<CompanyCooperationMethod> methods =
                                    companyCooperationMethodMapper.queryCompanyCooperationMethod(companyList.get(i).getId());
                            if (null != companySonTotalBillList && companySonTotalBillList.size() > 0) {
                                for (CompanySonTotalBill bill : companySonTotalBillList) {
                                    if (bill.getCompanyId().equals(companyList.get(i).getId())) {
                                        Date billMonth = Timestamp.parseDate3(bill.getBillMonth(),"yyyy-MM");
                                        if (Timestamp.parseDate3(bill.getBillMonth(),"yyyy-MM").compareTo(billMonth) == 0 ){
                                            methods = memberService.getCompanyCooperationMethod(companyList.get(i),methods,companySonTotalBillIdSet,bill,billMonth);

                                        }
                                    }
                                }
                                // 计算税费开始 start
                                out:for (SocialSecurityInfo info : socialSecurityInfosAdd) {
                                    for (Member member : taxMemberList) {
                                        for (CompanyCooperationMethod method : methods) {
                                            if (member.getCertificateNum().equals(info.getIdCard()) &&
                                                    member.getWaysOfCooperation().equals(method.getCooperationMethodId())) {
                                                info.setTaxPrice((info.getCompanyTotalPay() + info.getMemberTotalPay()) * method.getPercent() / 100);
//                                                info.setCompanyTotalPay(info.getCompanyTotalPay() * (1 + method.getPercent() / 100));
//                                                info.setMemberTotalPay(info.getMemberTotalPay() * (1 + method.getPercent() / 100));
                                                continue out;
                                            }
                                        }

                                    }
                                }
                                if (socialSecurityInfosAdd.size() > 0) {
                                    socialSecurityInfoMapper.updateList(socialSecurityInfosAdd);
                                }
                                // 计算税费结束 end


                                //删除服务费明细
                                monthServiceFeeDetailMapper.delete(companySonTotalBillIdSet.toArray(new Integer[companySonTotalBillIdSet.size()]));
                                // 删除服务费
                                monthServiceFeeMapper.delete(companySonTotalBillIdSet.toArray(new Integer[companySonTotalBillIdSet.size()]));
                                for (Date date : dateList) {
                                    memberService.buildServiceFee(companySonTotalBillList, companyList.get(i),
                                            methods,Timestamp.parseDate3(item.getBillMonth(),"yyyy-MM"),
                                            addListMSF,date,updateRecordItemList);
                                }

                            }

                        }
                    }


                }

                if (updateRecordItemList.size() > 0) {
                    memberBusinessUpdateRecordItemMapper.updateList(updateRecordItemList);
                }

                if (addListMSF.size() > 0) {
                    monthServiceFeeMapper.save(addListMSF);
                    for (MonthServiceFee fee : addListMSF) {
                        if (null != fee.getMonthServiceFeeDetailList()) {
                            for (MonthServiceFeeDetail detail : fee.getMonthServiceFeeDetailList()) {
                                detail.setMonthServiceFeeId(fee.getId());
                                addListMSFD.add(detail);
                            }
                        }
                    }
                    if (addListMSFD.size() > 0) {
                        monthServiceFeeDetailMapper.batchAdd(addListMSFD);
                    }
                }
            }
            if (companySonBillItemsUpd.size() > 0) {
                companySonBillItemMapper.updateList(companySonBillItemsUpd);
            }
        }

        if (map.size() > 0){
            //未产生账单的实作型
            //员工的身份证号集合
            Set<String> idCards = new HashSet<String>();
            Set<Date> dateSet = new HashSet<Date>();
            for (Map.Entry<String, SocialSecurityImportData> entry : map.entrySet()) {
                idCards.add(entry.getValue().getIdCard());
                dateSet.add(entry.getValue().getMonth());
            }
            if (idCards.size() > 0 &&  dateSet.size() > 0) {
                Set<Integer> companyIds = new HashSet<Integer>();
                List<Member> memberList = memberMapper.getMemberByIdCards(idCards,dateSet);
                for (Member member : memberList) {
                    companyIds.add(member.getCompanyId());

                }
                //公司集合
                List<Company> companyList = companyMapper.queryCompanyByIds(companyIds.toArray(new Integer[companyIds.size()]));

                //记录员工缴纳的业务  后期计算服务费使用
                List<MemberMonthPayBusiness> monthPayBusinessList = new ArrayList<MemberMonthPayBusiness>();
                //进行添加的社保信息
                List<SocialSecurityInfo> socialSecurityInfosAdd = new ArrayList<SocialSecurityInfo>();
                //社保险种信息
                List<SocialSecurityInfoItem> socialSecurityInfoItemAddAll = new ArrayList<SocialSecurityInfoItem>();

                for (Member member : memberList) {

                    if (null != member.getMemberBusinessSet()) {
                        for (MemberBusiness business : member.getMemberBusinessSet()) {
                            if (business.getBusinessId() == 3) {
                                Date billMonth = Timestamp.parseDate3(new Date(),"yyyy-MM");
                                for (Date date : serviceMonth) {
                                    out:for (Company company : companyList) {
                                        if (member.getCompanyId().equals(company.getId())) {
                                            billMonth = CommonUtil.getMonth(company.getBusinessStartTime(),company.getBusinessCycle(),1,date).get(0);
                                            break out;
                                        }
                                    }
                                    buildSocialSecurityInfo(map, memberList, socialSecurityInfosAdd,
                                            monthPayBusinessList,Timestamp.parseDate3(date,"yyyy-MM"),
                                            billMonth,business.getCompanySonBillId(),member.getCompanyId(),
                                            null,cityType);
                                }

                            }
                        }
                    }

                }


                if (socialSecurityInfosAdd.size() > 0) {

                    for (Company company : companyList) {
                        //服务费配置集合
                        List<CompanyCooperationMethod> methods =
                                companyCooperationMethodMapper.queryCompanyCooperationMethod(company.getId());
                        // 计算税费开始 start
                        out:for (SocialSecurityInfo info : socialSecurityInfosAdd) {
                            for (Member member : memberList) {
                                for (CompanyCooperationMethod method : methods) {
                                    if (member.getCertificateNum().equals(info.getIdCard()) &&
                                            member.getWaysOfCooperation().equals(method.getCooperationMethodId())) {
                                        info.setTaxPrice((info.getCompanyTotalPay() + info.getMemberTotalPay()) * method.getPercent() / 100);
//                                        info.setCompanyTotalPay(info.getCompanyTotalPay() * (1 + method.getPercent() / 100));
//                                        info.setMemberTotalPay(info.getMemberTotalPay() * (1 + method.getPercent() / 100));
                                        continue out;
                                    }
                                }

                            }
                        }
                        // 计算税费结束 end
                    }


                    socialSecurityInfoMapper.save(socialSecurityInfosAdd);
                    for (SocialSecurityInfo info : socialSecurityInfosAdd) {
                        for (SocialSecurityInfoItem item : info.getSocialSecurityInfoItems()) {
                            item.setSocialSecurityInfoId(info.getId());
                            socialSecurityInfoItemAddAll.add(item);
                        }
                    }
                    if (socialSecurityInfoItemAddAll.size() > 0) {
                        socialSecurityInfoItemMapper.save(socialSecurityInfoItemAddAll);
                    }
                    if (monthPayBusinessList.size() > 0) {
                        memberMonthPayBusinessMapper.saveList(monthPayBusinessList);
                    }
                }
            }
        }
    }

    /**
     * 公积金账单稽核
     * @param serviceMonth 服务月
     * @param map
     * @param operateType 操作类型  0：覆盖新增  1：追加
     */
    @Transactional
    public void auditReservedFundsBill(Set<Date> serviceMonth ,Map<String ,ReservedFundImportData> map,
                                       Set<String> idCardSet,Integer operateType) throws ParseException, IOException {

        if (idCardSet.size() == 0) {
            throw new InterfaceCommonException(StatusConstant.NO_DATA,"没有员工证件编号");
        }
        List<Member> memberBusinessList = memberMapper.getBusinessItem(idCardSet,4);

        Set<Integer> companyIdSet = new HashSet<Integer>();
        if (memberBusinessList.size() < 1) {
            return;
        }
        for (Member member : memberBusinessList) {
            companyIdSet.add(member.getCompanyId());
        }
        {
            List<CompanySonBillItem> companySonBillItemsUpd = new ArrayList<CompanySonBillItem>();
            //根据服务月查询所有未稽核账单 预收型
            List<CompanySonBillItem> companySonBillItems = companySonBillItemMapper.getByServiceMonth(new Date(),null,companyIdSet,new Date());
            //需要更新的员工业务集合
            List<MemberBusinessItem> mbiUpdList = new ArrayList<MemberBusinessItem>();
            if (companySonBillItems.size() > 0) {

                Set<Integer> sonBillItemId = new HashSet<Integer>();
                for (CompanySonBillItem item : companySonBillItems) {
                    sonBillItemId.add(item.getId());
                }
                List<ReservedFundsInfo> rfiList = reservedFundsInfoMapper.listBySonBillItemId(sonBillItemId);

                //需要更新的公积金集合
                List<ReservedFundsInfo> reservedFundsInfoList = new ArrayList<ReservedFundsInfo>();
                //预收型
                for (CompanySonBillItem item : companySonBillItems) {
                    //实际缴纳总金额
                    Double totalPrice = 0.0;
                    //预收金额
                    Double receivablePrice = 0.0;
                    //上一次上传的实作金额
                    Double oldImplementationPrice = 0.0;
                    //重新上传的实作金额
                    Double implementationPrice = 0.0;

                    //已收取纳入应收的金额
                    Double receivablePrice2 = 0.0;
                    //新上传
                    Double newReceivablePrice2 = 0.0;
                    out:for (int i = 0 ; i < rfiList.size() ; i ++) {
                        if (rfiList.get(i).getCompanySonBillItemId().equals(item.getId())) {
                            ReservedFundImportData data = map.get(rfiList.get(i).getIdCard() +
                                    Timestamp.timesTamp2(rfiList.get(i).getServiceNowYM(),"yyyy-MM").toString());

                            //是否纳入应收 false 否  true 是
                            Boolean isReceivable = false;
                            //是否有此项业务 false 否  true 是
                            Boolean isHaveBusiness = false;
                            //需要更新的员工业务
                            MemberBusinessItem mbiUpd = new MemberBusinessItem();
                            ss:for (Member member : memberBusinessList) {
                                if (member.getCertificateNum().equals(rfiList.get(i).getIdCard())) {
                                    isHaveBusiness = true;
                                    if (null != member.getMemberBusinessItems()) {
                                        for (MemberBusinessItem businessItem : member.getMemberBusinessItems()) {
                                            mbiUpd = businessItem;
                                            if (businessItem.getIsReceivable() == 1) {
                                                isReceivable = true;
                                                break ss;
                                            }
                                        }
                                    }
                                }
                            }

                            if (isHaveBusiness) {
                                if (rfiList.get(i).getBillMadeMethod() == 0) {
                                    receivablePrice += rfiList.get(i).getCompanyTotalPay() + rfiList.get(i).getMemberTotalPay();
//                                    if (isReceivable) {
//                                        receivablePrice2 += rfiList.get(i).getCompanyTotalPay() + rfiList.get(i).getMemberTotalPay();
//                                    }
                                } else {
                                    oldImplementationPrice += rfiList.get(i).getCompanyTotalPay() + rfiList.get(i).getMemberTotalPay();
                                    if (isReceivable) {
                                        receivablePrice2 += rfiList.get(i).getCompanyTotalPay() + rfiList.get(i).getMemberTotalPay();
                                    }
                                }
                            }
                            if (null != data) {
                                //公积金编号
                                //设置公积金编号 次月使用
                                mbiUpd.setCoding(data.getCustomerNumber());
                                mbiUpdList.add(mbiUpd);
//                                rfiList.get(i).setMemberNum(data.getCustomerNumber());
                                rfiList.get(i).setBillMonth(item.getBillMonth());
                                rfiList.get(i).setPracticalCompanyTotalPay(data.getCompanyTotalPay());
                                rfiList.get(i).setPracticalMemberTotalPay(data.getMemberTotalPay());
                                if (rfiList.get(i).getBillMadeMethod() == 1) {
                                    Double percent = rfiList.get(i).getTaxPrice() / (rfiList.get(i).getCompanyTotalPay() +  rfiList.get(i).getMemberTotalPay());
                                    rfiList.get(i).setCompanyTotalPay(data.getCompanyTotalPay());
                                    rfiList.get(i).setMemberTotalPay(data.getMemberTotalPay());
                                    rfiList.get(i).setTaxPrice((rfiList.get(i).getCompanyTotalPay() +  rfiList.get(i).getMemberTotalPay()) * percent);
                                }
                                rfiList.get(i).setTransactorName(data.getTransactorName());
                                rfiList.get(i).setPayPlaceOrganizationName(data.getOrganizationName());
                                rfiList.get(i).setRealDoTime(new Date());
                                rfiList.get(i).setIsUploadKaoPan(1);

                                 if (isHaveBusiness) {
                                    if (rfiList.get(i).getBillMadeMethod() == 0) {
                                        totalPrice += data.getCompanyTotalPay() + data.getMemberTotalPay();
//                                        if (isReceivable) {
//                                            receivablePrice2 += rfiList.get(i).getCompanyTotalPay() + rfiList.get(i).getMemberTotalPay();
//                                        }
                                    } else {
                                        implementationPrice += data.getCompanyTotalPay() + data.getMemberTotalPay();
                                        if (isReceivable) {
                                            newReceivablePrice2 += rfiList.get(i).getCompanyTotalPay() + rfiList.get(i).getMemberTotalPay();
                                        }
                                    }
                                }

                                map.remove(rfiList.get(i).getIdCard() +
                                        Timestamp.timesTamp2(rfiList.get(i).getServiceNowYM(),"yyyy-MM").toString());
                            } else {
                                if (operateType == 0) {
                                    rfiList.get(i).setPracticalCompanyTotalPay(0.0);
                                    rfiList.get(i).setPracticalMemberTotalPay(0.0);
                                    rfiList.get(i).setIsUploadKaoPan(0);
                                } else {
                                    rfiList.remove(i);
                                    i --;
                                    continue out;
                                }

                            }
                            rfiList.get(i).setIsAudit(1);
                            reservedFundsInfoList.add(rfiList.get(i));
                            rfiList.remove(i);
                            i --;
                        }
                    }
                    if (operateType == 0) {
                        //公积金是否稽核 公积金
                        item.setReservedFundPracticalPrice(receivablePrice - totalPrice);
//                        item.setIsSocialSecurityAudit(receivablePrice - totalPrice == 0 ? 1 : 0);
                        item.setRfPaidInPrice(totalPrice + implementationPrice);
                        item.setIsUploadKaoPanR(1);
                        item.setReceivablePrice(item.getReceivablePrice() - oldImplementationPrice + implementationPrice);
                        item.setTotalPrice(item.getTotalPrice() - receivablePrice2 + newReceivablePrice2);
                        if (null != item.getIsUploadKaoPanS() && item.getIsUploadKaoPanS() == 1) {
//                        item.setTotalPrice(item.getRfPaidInPrice() + item.getSsPaidInPrice());
                            item.setAuditTheDifference(item.getReservedFundPracticalPrice() + item.getSocialSecurityPracticalPrice());
//                        item.setIsAudit(1);
                        } else {
//                        item.setTotalPrice(item.getRfPaidInPrice());
                            item.setAuditTheDifference(item.getReservedFundPracticalPrice());
                        }
                    } else {
                        //公积金是否稽核 公积金
                        item.setReservedFundPracticalPrice(item.getReservedFundPracticalPrice() + receivablePrice - totalPrice);
//                        item.setIsSocialSecurityAudit(receivablePrice - totalPrice == 0 ? 1 : 0);
                        item.setRfPaidInPrice(item.getRfPaidInPrice() + totalPrice + implementationPrice);
                        item.setIsUploadKaoPanR(1);
                        item.setReceivablePrice(item.getReceivablePrice() - oldImplementationPrice + implementationPrice);
                        item.setTotalPrice(item.getTotalPrice() - receivablePrice2 + newReceivablePrice2);
                        if (null != item.getIsUploadKaoPanS() && item.getIsUploadKaoPanS() == 1) {
//                        item.setTotalPrice(item.getRfPaidInPrice() + item.getSsPaidInPrice());
                            item.setAuditTheDifference(item.getReservedFundPracticalPrice() + item.getSocialSecurityPracticalPrice());
//                        item.setIsAudit(1);
                        } else {
//                        item.setTotalPrice(item.getRfPaidInPrice());
                            item.setAuditTheDifference(item.getReservedFundPracticalPrice());
                        }
                    }

                }
                if (reservedFundsInfoList.size() > 0) {
                    reservedFundsInfoMapper.updateList(reservedFundsInfoList);
                }
                if (companySonBillItems.size() > 0) {
                    companySonBillItemsUpd.addAll(companySonBillItems);
                }
            }
            if (companySonBillItemsUpd.size() > 0) {
                companySonBillItemMapper.updateList(companySonBillItemsUpd);
            }
            if (mbiUpdList.size() > 0) {
                memberBusinessItemMapper.updateList(mbiUpdList);
            }
        }

        if (map.size() > 0) {
            List<CompanySonBillItem> companySonBillItemsUpd = new ArrayList<CompanySonBillItem>();
            //未产生账单的实作型或已产生账单的实作型
            //已产生账单的实作型
            List<CompanySonBillItem> companySonBillItems2 = companySonBillItemMapper.getByServiceMonth(new Date(),1,companyIdSet,new Date());
            if (companySonBillItems2.size() > 0) {
                //实作型员工
                List<Member> members = memberMapper.getMemberByBillMadeMethod(1);
//                Set<Integer> companyIds = new HashSet<Integer>();
//                for (Member member : members) {
//                    if (null != map.get(member.getCertificateNum())) {
//                        companyIds.add(member.getCompanyId());
//                    }
//                }
                //后面计算税费使用
                List<Member> taxMemberList = new ArrayList<Member>();
                //公司集合
                List<Company> companyList = companyMapper.queryCompanyByIds(companyIdSet.toArray(new Integer[companyIdSet.size()])/*companyIds.toArray(new Integer[companyIds.size()])*/);
                //进行添加的公积金信息
                List<ReservedFundsInfo> reservedFundsInfoList = new ArrayList<ReservedFundsInfo>();
                //社保缴纳明细
                List<MemberMonthPayBusiness> monthPayBusinessList = new ArrayList<MemberMonthPayBusiness>();
                for (CompanySonBillItem item : companySonBillItems2) {
                    List<Member> memberList = new ArrayList<Member>();
                    for (int i = 0; i < members.size(); i++) {
                        if (members.get(i).getCompanyId().equals(item.getCompanyId())) {
                            memberList.add(members.get(i));
                            taxMemberList.add(members.get(i));
                            members.remove(i);
                            i--;
                        }
                    }
                    Double[] price = buildReservedFundInfo(map, memberList, reservedFundsInfoList,
                            monthPayBusinessList,item.getServiceMonth(),
                            item.getBillMonth(),item.getCompanySonBillId(),item.getCompanyId(),item.getId());
                    //公积金是否稽核 公积金
                    item.setReservedFundPracticalPrice(0.0);
                    item.setAuditTheDifference(null);
//                    item.setIsReservedFundAudit(1);
                    item.setRfPaidInPrice(item.getRfPaidInPrice() + price[0]);
                    item.setReceivablePrice(item.getReceivablePrice() + price[0]);
                    item.setIsUploadKaoPanR(1);
                    item.setTotalPrice(item.getTotalPrice() + price[1]);
//                    item.setAuditTheDifference(item.getReservedFundPracticalPrice() + item.getAuditTheDifference());
//                    if (null != item.getIsUploadKaoPanS() && item.getIsUploadKaoPanS() == 1) {
//                        item.setIsAudit(1);
//                        item.setTotalPrice(item.getRfPaidInPrice() + item.getSsPaidInPrice());
//                    } else {
//                        item.setTotalPrice(item.getRfPaidInPrice());
//                    }
                }
                //实作型
                if (companySonBillItems2.size() > 0) {
                    companySonBillItemsUpd.addAll(companySonBillItems2);
                }
                if (reservedFundsInfoList.size() > 0) {
                    reservedFundsInfoMapper.save(reservedFundsInfoList);
                }
                if (monthPayBusinessList.size() > 0) {
                    memberMonthPayBusinessMapper.saveList(monthPayBusinessList);
                }

                List<MonthServiceFee> addListMSF = new ArrayList<MonthServiceFee>();
                //需要添加的服务费明细
                List<MonthServiceFeeDetail> addListMSFD = new ArrayList<MonthServiceFeeDetail>();


                //需要更新的异动量
                List<MemberBusinessUpdateRecordItem> updateRecordItemList = new ArrayList<MemberBusinessUpdateRecordItem>();

                for (int i = 0; i < companyList.size(); i++) {
                    for (CompanySonBillItem item : companySonBillItems2) {
                        if (item.getCompanyId().equals(companyList.get(i).getId())) {
                            List<CompanySonTotalBill> companySonTotalBillList =
                                    companySonTotalBillMapper.getByBillMonth(Timestamp.parseDate3(item.getBillMonth(),"yyyy-MM"),companyList.get(i).getId());
                            List<Date> dateList = companySonTotalBillMapper.getDateByBillMonthAndCompanyId(Timestamp.parseDate3(item.getBillMonth(),"yyyy-MM"),companyList.get(i).getId());
                            //汇总账单的id集合
                            Set<Integer> companySonTotalBillIdSet = new HashSet<Integer>();
                            //服务费配置集合
                            List<CompanyCooperationMethod> methods =
                                    companyCooperationMethodMapper.queryCompanyCooperationMethod(companyList.get(i).getId());
                            if (null != companySonTotalBillList && companySonTotalBillList.size() > 0) {
                                for (CompanySonTotalBill bill : companySonTotalBillList) {
                                    if (bill.getCompanyId().equals(companyList.get(i).getId())) {
                                        Date billMonth = Timestamp.parseDate3(bill.getBillMonth(),"yyyy-MM");
                                        if (Timestamp.parseDate3(bill.getBillMonth(),"yyyy-MM").compareTo(billMonth) == 0 ){
                                            methods = memberService.getCompanyCooperationMethod(companyList.get(i),methods,companySonTotalBillIdSet,bill,billMonth);
                                        }
                                    }
                                }
                                // 计算税费开始 start
                                out:for (ReservedFundsInfo info : reservedFundsInfoList) {
                                    for (Member member : taxMemberList) {
                                        for (CompanyCooperationMethod method : methods) {
                                            if (member.getCertificateNum().equals(info.getIdCard()) &&
                                                    member.getWaysOfCooperation().equals(method.getCooperationMethodId())) {
                                                info.setTaxPrice((info.getCompanyTotalPay() + info.getMemberTotalPay()) * method.getPercent() / 100);
//                                                info.setCompanyTotalPay(info.getCompanyTotalPay() * (1 + method.getPercent() / 100));
//                                                info.setMemberTotalPay(info.getMemberTotalPay() * (1 + method.getPercent() / 100));
                                                continue out;
                                            }
                                        }

                                    }
                                }
                                if (reservedFundsInfoList.size() > 0) {
                                    reservedFundsInfoMapper.updateList(reservedFundsInfoList);
                                }
                                // 计算税费结束 end
                                //删除服务费明细
                                monthServiceFeeDetailMapper.delete(companySonTotalBillIdSet.toArray(new Integer[companySonTotalBillIdSet.size()]));
                                // 删除服务费
                                monthServiceFeeMapper.delete(companySonTotalBillIdSet.toArray(new Integer[companySonTotalBillIdSet.size()]));
                                for (Date date : dateList) {
                                    memberService.buildServiceFee(companySonTotalBillList, companyList.get(i),
                                            methods,Timestamp.parseDate3(item.getBillMonth(),"yyyy-MM"),addListMSF,date,updateRecordItemList);
                                }
                            }
                        }
                    }
                }
                if (updateRecordItemList.size() > 0) {
                    memberBusinessUpdateRecordItemMapper.updateList(updateRecordItemList);
                }
                if (addListMSF.size() > 0) {
                    monthServiceFeeMapper.save(addListMSF);
                    for (MonthServiceFee fee : addListMSF) {
                        if (null != fee.getMonthServiceFeeDetailList()) {
                            for (MonthServiceFeeDetail detail : fee.getMonthServiceFeeDetailList()) {
                                detail.setMonthServiceFeeId(fee.getId());
                                addListMSFD.add(detail);
                            }
                        }
                    }
                    if (addListMSFD.size() > 0) {
                        monthServiceFeeDetailMapper.batchAdd(addListMSFD);
                    }
                }
            }
            if (companySonBillItemsUpd.size() > 0) {
                companySonBillItemMapper.updateList(companySonBillItemsUpd);
            }
        }


        if (map.size() > 0) {
            //未产生账单的实作型
            //员工的身份证号集合
            Set<String> idCards = new HashSet<String>();
            Set<Date> dateSet = new HashSet<Date>();
            for (Map.Entry<String, ReservedFundImportData> entry : map.entrySet()) {
                idCards.add(entry.getValue().getIdCard());
                dateSet.add(entry.getValue().getServiceMonth());
            }
            if (idCards.size() > 0 && dateSet.size() > 0) {
                Set<Integer> companyIds = new HashSet<Integer>();
                List<Member> memberList = memberMapper.getMemberByIdCards(idCards,dateSet);
                for (Member member : memberList) {
                    companyIds.add(member.getCompanyId());
                }
                //公司集合
                List<Company> companyList = companyMapper.queryCompanyByIds(companyIds.toArray(new Integer[companyIds.size()]));

                //记录员工缴纳的业务  后期计算服务费使用
                List<MemberMonthPayBusiness> monthPayBusinessList = new ArrayList<MemberMonthPayBusiness>();
                //进行添加的社保信息
                List<ReservedFundsInfo> reservedFundsInfoList = new ArrayList<ReservedFundsInfo>();

                for (Member member : memberList) {
                    //实际缴纳总金额
                    Double totalPrice = 0.0;
                    if (null != member.getMemberBusinessSet()) {
                        for (MemberBusiness business : member.getMemberBusinessSet()) {
                            if (business.getBusinessId() == 3) {
                                Date billMonth = Timestamp.parseDate3(new Date(),"yyyy-MM");
                                for (Date date : serviceMonth) {
                                    out:for (Company company : companyList) {
                                        if (member.getCompanyId().equals(company.getId())) {
                                            billMonth = CommonUtil.getMonth(company.getBusinessStartTime(),company.getBusinessCycle(),1,date).get(0);
                                            break out;
                                        }
                                    }
                                    buildReservedFundInfo(map, memberList, reservedFundsInfoList,
                                            monthPayBusinessList,Timestamp.parseDate3(date,"yyyy-MM"),
                                            billMonth,business.getCompanySonBillId(),member.getCompanyId(),null);
                                }
                            }
                        }
                    }

                }
                if (reservedFundsInfoList.size() > 0) {
                    for (Company company : companyList) {
                        //服务费配置集合
                        List<CompanyCooperationMethod> methods =
                                companyCooperationMethodMapper.queryCompanyCooperationMethod(company.getId());
                        // 计算税费开始 start
                        out:for (ReservedFundsInfo info : reservedFundsInfoList) {
                            for (Member member : memberList) {
                                for (CompanyCooperationMethod method : methods) {
                                    if (member.getCertificateNum().equals(info.getIdCard()) &&
                                            member.getWaysOfCooperation().equals(method.getCooperationMethodId())) {
                                        info.setTaxPrice((info.getCompanyTotalPay() + info.getMemberTotalPay()) * method.getPercent() / 100);
//                                        info.setCompanyTotalPay(info.getCompanyTotalPay() * (1 + method.getPercent() / 100));
//                                        info.setMemberTotalPay(info.getMemberTotalPay() * (1 + method.getPercent() / 100));
                                        continue out;
                                    }
                                }

                            }
                        }
                        // 计算税费结束 end
                    }
                    reservedFundsInfoMapper.save(reservedFundsInfoList);
                    if (monthPayBusinessList.size() > 0) {
                        memberMonthPayBusinessMapper.saveList(monthPayBusinessList);
                    }
                }
            }
        }
    }

    /**
     * 封装稽核的社保险种信息
     * @param map
     * @param memberList
     * @param socialSecurityInfosAdd
     * @return
     * @throws ParseException
     */
    public Double[] buildSocialSecurityInfo(Map<String, SocialSecurityImportData> map, List<Member> memberList,
                                          List<SocialSecurityInfo> socialSecurityInfosAdd,
                                           List<MemberMonthPayBusiness> monthPayBusinessList,
                                          Date serviceMonth ,Date billMonth,
                                          Integer companySonBillId ,Integer companyId,Integer companySonBillItemId,
                                          Integer cityType) throws ParseException {
        //纳入应收金额
        Double totalPrice = 0.0;
        //预收金额
        Double receivablePrice = 0.0;
        for (int i = 0 ; i < memberList.size() ; i ++) {
            if (companyId.equals(memberList.get(i).getCompanyId())) {
                Iterator<Map.Entry<String, SocialSecurityImportData>> it = map.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, SocialSecurityImportData> entry = it.next();
                    String key = memberList.get(i).getCertificateNum() +
                            Timestamp.timesTamp2(serviceMonth,"yyyy-MM").toString();
                    if (entry.getKey().contains(memberList.get(i).getCertificateNum()) &&
                            (entry.getKey().compareTo(key) < 0 ||
                                    entry.getKey().equals(key))) {
                        SocialSecurityImportData data = entry.getValue();
                        // TODO: 2017/12/26 还未完成 是否纳入应收
                        if (null != data) {
                            SocialSecurityInfo ssi = new SocialSecurityInfo();
                            ssi.setCompanySonBillId(companySonBillId);
                            ssi.setCompanySonBillItemId(companySonBillItemId);
                            ssi.setPayCardinalNumber(data.getBaseNumber());
                            //社保缴纳明细
                            MemberMonthPayBusiness  monthPayBusiness = new MemberMonthPayBusiness();
                            monthPayBusiness.setBusinessId(3);
                            monthPayBusiness.setServiceMonth(Timestamp.parseDate(entry.getKey().substring(memberList.get(i).getCertificateNum().length()),"yyyy-MM"));
                            monthPayBusiness.setMemberId(memberList.get(i).getId());
                            monthPayBusiness.setBillMonth(Timestamp.parseDate3(billMonth,"yyyy-MM"));
                            monthPayBusiness.setCompanyId(companyId);
                            monthPayBusiness.setCompanySonBillId(companySonBillId);
                            monthPayBusiness.setBillType(1);
                            //是否纳入应收 false 否  true 是
                            Boolean isReceivable = false;
                            //是否有此项业务 false 否  true 是
                            Boolean isHaveBusiness = false;
                            if (null != memberList.get(i).getMemberBusinessItems()) {
                                out:for (MemberBusinessItem item : memberList.get(i).getMemberBusinessItems()) {
                                    if (item.getBusinessId() == 3) {
                                        monthPayBusiness.setCityId(item.getCityId());
                                        ssi.setBillMadeMethod(item.getBillMadeMethod());
                                        isHaveBusiness = true;
                                        if (null == ssi.getPayPlaceOrganizationName()) {
                                            ssi.setPayPlaceOrganizationName((null != item.getPayPlaceName() ? item.getPayPlaceName().replace("中国,","").replace("省,","") : "") + "-" + item.getOrganizationName());
                                        }
                                        if (null == ssi.getPayPlaceName()) {
                                            ssi.setPayPlaceName(null != item.getPayPlaceName() ? item.getPayPlaceName().replace("中国,","").replace("省,","") : "");
                                        }
                                        if (null == ssi.getTransactorName()) {
                                            ssi.setTransactorName(item.getTransactorName());
                                        }
                                        if (null == ssi.getOrganizationName()) {
                                            ssi.setOrganizationName(item.getOrganizationName());
                                        }




                                       /* ssi.setTransactorName(data.getTransactorName());
                                        ssi.setOrganizationName(data.getOrganizationName());
                                        ssi.setPayPlaceName(data.getPayPlaceName());*/
                                        if (null == ssi.getPayCardinalNumber()) {
                                            ssi.setPayCardinalNumber(item.getBaseNumber());
                                        }
                                        ssi.setBeginPayYM(item.getServiceStartTime());
                                        if (item.getIsReceivable() == 1) {
                                            isReceivable = true;
                                        }
                                        break out;
                                    }
                                }
                            }
                            monthPayBusinessList.add(monthPayBusiness);
                            //社保编号
                            ssi.setSocialSecurityNum(data.getSocialSecurityNumber());
                            ssi.setMemberTotalPay(data.getPersonCountPrice(cityType));
                            ssi.setCompanyTotalPay(data.getCompanyCountPrice(cityType));
                            ssi.setPracticalCompanyTotalPay(data.getCompanyCountPrice(cityType));
                            ssi.setPracticalMemberTotalPay(data.getPersonCountPrice(cityType));
                            ssi.setBillMonth(billMonth);
                            ssi.setIsAudit(1);
                            ssi.setRealDoTime(new Date());
                            ssi.setServiceNowYM(Timestamp.parseDate(entry.getKey().substring(memberList.get(i).getCertificateNum().length()),"yyyy-MM"));
                            ssi.setUserName(data.getUserName());
                            ssi.setIdCard(data.getIdCard());
                            ssi.setIsUploadKaoPan(1);
                            ssi.setMemberId(memberList.get(i).getId());
                            ssi.setInsuranceLevelName(data.getLevelName());
                            if (isHaveBusiness) {
                                receivablePrice += data.getCompanyCountPrice(cityType) + data.getPersonCountPrice(cityType);
                                if (isReceivable) {
                                    totalPrice += data.getCompanyCountPrice(cityType) + data.getPersonCountPrice(cityType);
                                }
                            }


                            //社保险种信息
                            List<SocialSecurityInfoItem> socialSecurityInfoItemAdd = new ArrayList<SocialSecurityInfoItem>();


                            if (cityType == 0) {
                                {
                                    //公司缴纳工伤
                                    SocialSecurityInfoItem ssii = new SocialSecurityInfoItem();
                                    ssii.setPracticalPayPrice(data.getInjuryCompanyPrice());
                                    ssii.setType(0);
                                    ssii.setInsuranceName("工伤");
                                    ssii.setPayPrice(data.getInjuryCompanyPrice());
                                    socialSecurityInfoItemAdd.add(ssii);
                                }
                                {
                                    //个人缴纳工伤
                                    SocialSecurityInfoItem ssii = new SocialSecurityInfoItem();
                                    ssii.setPracticalPayPrice(data.getInjurySelfPrice());
                                    ssii.setType(1);
                                    ssii.setInsuranceName("工伤");
                                    ssii.setPayPrice(data.getInjurySelfPrice());
                                    socialSecurityInfoItemAdd.add(ssii);
                                }
                                {
                                    //公司缴纳生育
                                    SocialSecurityInfoItem ssii = new SocialSecurityInfoItem();
                                    ssii.setPracticalPayPrice(data.getBearCompanyPrice());
                                    ssii.setType(0);
                                    ssii.setInsuranceName("生育");
                                    ssii.setPayPrice(data.getBearCompanyPrice());
                                    socialSecurityInfoItemAdd.add(ssii);
                                }
                                {
                                    //公司缴纳大病
                                    SocialSecurityInfoItem ssii = new SocialSecurityInfoItem();
                                    ssii.setPracticalPayPrice(data.getIllnessCompanyPrice());
                                    ssii.setType(0);
                                    ssii.setInsuranceName("大病");
                                    ssii.setPayPrice(data.getIllnessCompanyPrice());
                                    socialSecurityInfoItemAdd.add(ssii);
                                }
                                {
                                    //个人缴纳大病
                                    SocialSecurityInfoItem ssii = new SocialSecurityInfoItem();
                                    ssii.setPracticalPayPrice(data.getIllnessSelfPrice());
                                    ssii.setType(1);
                                    ssii.setInsuranceName("大病");
                                    ssii.setPayPrice(data.getIllnessSelfPrice());
                                    socialSecurityInfoItemAdd.add(ssii);
                                }
                                {
                                    //公司缴纳失业
                                    SocialSecurityInfoItem ssii = new SocialSecurityInfoItem();
                                    ssii.setPracticalPayPrice(data.getWorkCompanyPrice());
                                    ssii.setType(0);
                                    ssii.setInsuranceName("失业");
                                    ssii.setPayPrice(data.getWorkCompanyPrice());
                                    socialSecurityInfoItemAdd.add(ssii);
                                }
                                {
                                    //个人缴纳失业
                                    SocialSecurityInfoItem ssii = new SocialSecurityInfoItem();
                                    ssii.setPracticalPayPrice(data.getWorkPersonPrice());
                                    ssii.setType(1);
                                    ssii.setInsuranceName("失业");
                                    ssii.setPayPrice(data.getWorkPersonPrice());
                                    socialSecurityInfoItemAdd.add(ssii);
                                }
                                {
                                    //公司缴纳医疗
                                    SocialSecurityInfoItem ssii = new SocialSecurityInfoItem();
                                    ssii.setPracticalPayPrice(data.getMedicalCompanyPrice());
                                    ssii.setType(0);
                                    ssii.setInsuranceName("医疗");
                                    ssii.setPayPrice(data.getMedicalCompanyPrice());
                                    socialSecurityInfoItemAdd.add(ssii);
                                }
                                {
                                    //个人缴纳医疗
                                    SocialSecurityInfoItem ssii = new SocialSecurityInfoItem();
                                    ssii.setPracticalPayPrice(data.getMedicalSelfPrice());
                                    ssii.setType(1);
                                    ssii.setInsuranceName("医疗");
                                    ssii.setPayPrice(data.getMedicalSelfPrice());
                                    socialSecurityInfoItemAdd.add(ssii);
                                }
                                {
                                    //公司缴纳养老
                                    SocialSecurityInfoItem ssii = new SocialSecurityInfoItem();
                                    ssii.setPracticalPayPrice(data.getProvisionCompanyPrice());
                                    ssii.setType(0);
                                    ssii.setInsuranceName("养老");
                                    ssii.setPayPrice(data.getProvisionCompanyPrice());
                                    socialSecurityInfoItemAdd.add(ssii);
                                }
                                {
                                    //个人缴纳养老
                                    SocialSecurityInfoItem ssii = new SocialSecurityInfoItem();
                                    ssii.setPracticalPayPrice(data.getProvisionSelfPrice());
                                    ssii.setType(1);
                                    ssii.setInsuranceName("养老");
                                    ssii.setPayPrice(data.getProvisionSelfPrice());
                                    socialSecurityInfoItemAdd.add(ssii);
                                }
                                {
                                    //公司缴纳残保金
                                    SocialSecurityInfoItem ssii = new SocialSecurityInfoItem();
                                    ssii.setPracticalPayPrice(data.getResidualCompanyPrice());
                                    ssii.setType(0);
                                    ssii.setInsuranceName("残保金");
                                    ssii.setPayPrice(data.getResidualCompanyPrice());
                                    socialSecurityInfoItemAdd.add(ssii);
                                }
                                {
                                    //个人缴纳残保金
                                    SocialSecurityInfoItem ssii = new SocialSecurityInfoItem();
                                    ssii.setPracticalPayPrice(data.getResidualSelfPrice());
                                    ssii.setType(1);
                                    ssii.setInsuranceName("残保金");
                                    ssii.setPayPrice(data.getResidualSelfPrice());
                                    socialSecurityInfoItemAdd.add(ssii);
                                }
                            } else {
                                if (null != data.getSocialSecurityCommonList()) {
                                    for (SocialSecurityCommon common : data.getSocialSecurityCommonList()) {

                                        SocialSecurityInfoItem ssii = new SocialSecurityInfoItem();
                                        ssii.setPracticalPayPrice(common.getCompanyPrice());
                                        ssii.setType(1);
                                        ssii.setInsuranceName(common.getInsuranceName());
                                        ssii.setPayPrice(common.getCompanyPrice());
                                        socialSecurityInfoItemAdd.add(ssii);

                                        SocialSecurityInfoItem ssii2 = new SocialSecurityInfoItem();
                                        ssii.setPracticalPayPrice(common.getPersonPrice());
                                        ssii.setType(0);
                                        ssii.setInsuranceName(common.getInsuranceName());
                                        ssii.setPayPrice(common.getPersonPrice());
                                        socialSecurityInfoItemAdd.add(ssii2);
                                    }
                                }
                            }

                            ssi.setSocialSecurityInfoItems(socialSecurityInfoItemAdd);
                            it.remove();
//                            map.remove(ssi.getIdCard() +
//                                    Timestamp.timesTamp2(serviceMonth,"yyyy-MM").toString());
                            socialSecurityInfosAdd.add(ssi);
                        }
                    }
                }
            }
        }


        return new Double[]{receivablePrice,totalPrice};
    }

    /**
     * 封装公积金稽核信息
     * @param map
     * @param memberList
     * @param reservedFundsInfoList
     * @return
     * @throws ParseException
     */
    public Double[] buildReservedFundInfo(Map<String, ReservedFundImportData> map, List<Member> memberList,
                                          List<ReservedFundsInfo> reservedFundsInfoList,
                                          List<MemberMonthPayBusiness> monthPayBusinessList,
                                          Date serviceMonth ,Date billMonth,
                                          Integer companySonBillId ,Integer companyId,Integer companySonBillItemId) throws ParseException {
        //纳入应收金额
        Double totalPrice = 0.0;
        //预收金额
        Double receivablePrice = 0.0;
        for (int i = 0 ; i < memberList.size() ; i ++) {
            if (companyId.equals(memberList.get(i).getCompanyId())) {
                Iterator<Map.Entry<String, ReservedFundImportData>> it = map.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, ReservedFundImportData> entry = it.next();
                    String key = memberList.get(i).getCertificateNum() +
                            Timestamp.timesTamp2(serviceMonth,"yyyy-MM").toString();
                    if (entry.getKey().contains(memberList.get(i).getCertificateNum()) &&
                            (entry.getKey().compareTo(key) < 0 ||
                                    entry.getKey().equals(key))) {
                        ReservedFundImportData data = entry.getValue();
                        if (null != data) {
                            ReservedFundsInfo rfi = new ReservedFundsInfo();
                            rfi.setCompanySonBillId(companySonBillId);
                            rfi.setCompanySonBillItemId(companySonBillItemId);
                            rfi.setPayCardinalNumber(data.getPayCardinalNumber());
                            //公积金缴纳明细
                            MemberMonthPayBusiness  monthPayBusiness = new MemberMonthPayBusiness();
                            monthPayBusiness.setBusinessId(3);
                            monthPayBusiness.setServiceMonth(Timestamp.parseDate(entry.getKey().substring(memberList.get(i).getCertificateNum().length()),"yyyy-MM"));
                            monthPayBusiness.setMemberId(memberList.get(i).getId());
                            monthPayBusiness.setBillMonth(Timestamp.parseDate3(billMonth,"yyyy-MM"));
                            monthPayBusiness.setCompanyId(companyId);
                            monthPayBusiness.setCompanySonBillId(companySonBillId);
                            monthPayBusiness.setBillType(1);
                            //是否纳入应收 false 否  true 是
                            Boolean isReceivable = false;
                            //是否有此项业务 false 否  true 是
                            Boolean isHaveBusiness = false;
                            if (null != memberList.get(i).getMemberBusinessItems()) {
                                out:for (MemberBusinessItem item : memberList.get(i).getMemberBusinessItems()) {
                                    if (item.getBusinessId() == 4) {
                                        isHaveBusiness = true;
                                        if (item.getIsReceivable() == 1) {
                                            isReceivable = true;
                                        }
                                        monthPayBusiness.setCityId(item.getCityId());
                                        if (null == rfi.getPayCardinalNumber()) {
                                            rfi.setPayCardinalNumber(item.getBaseNumber());
                                        }
                                        rfi.setPayPlaceOrganizationName((null != item.getPayPlaceName() ? item.getPayPlaceName().replace("中国,","").replace("省,","") : "") + "-" +  item.getOrganizationName());
                                        rfi.setPayPlaceName(null != item.getPayPlaceName() ? item.getPayPlaceName().replace("中国,","").replace("省,","") : "");
                                        rfi.setTransactorName(item.getTransactorName());
                                        rfi.setOrganizationName(item.getOrganizationName());

                                        rfi.setBeginPayYM(item.getServiceStartTime());
                                        rfi.setBillMadeMethod(item.getBillMadeMethod());
                                        break out;
                                    }
                                }
                            }
                            monthPayBusinessList.add(monthPayBusiness);
                            //公积金编号
                            rfi.setMemberNum(data.getCustomerNumber());
                            rfi.setPracticalCompanyTotalPay(data.getCompanyTotalPay());
                            rfi.setPracticalMemberTotalPay(data.getMemberTotalPay());
                            rfi.setMemberNum(data.getCustomerNumber());
                            rfi.setUserName(data.getUserName());
                            rfi.setRealDoTime(new Date());
                            rfi.setIdCard(data.getIdCard());
                            rfi.setIsUploadKaoPan(1);
                            rfi.setMemberTotalPay(data.getMemberTotalPay());
                            rfi.setCompanyTotalPay(data.getCompanyTotalPay());
                           /* rfi.setTransactorName(data.getTransactorName());
                            rfi.setPayPlaceName(data.getPayPlaceName());
                            rfi.setOrganizationName(data.getOrganizationName());
                            rfi.setPayPlaceOrganizationName(data.getOrganizationName());*/
                            rfi.setPayRatio(data.getPayRatio());
                            rfi.setBillMonth(billMonth);
                            rfi.setServiceNowYM(Timestamp.parseDate(entry.getKey().substring(memberList.get(i).getCertificateNum().length()),"yyyy-MM"));

                            rfi.setMemberId(memberList.get(i).getId());
                            rfi.setIsAudit(1);
                            rfi.setTransactorName(data.getTransactorName());
                            if (isHaveBusiness) {
                                receivablePrice += data.getCompanyTotalPay() + data.getMemberTotalPay();
                                if (isReceivable) {
                                    totalPrice += data.getCompanyTotalPay() + data.getMemberTotalPay();
                                }
                            }
                            it.remove();
//                            map.remove(rfi.getIdCard() +
//                                    Timestamp.timesTamp2(serviceMonth,"yyyy-MM").toString());
                            reservedFundsInfoList.add(rfi);
                        }
                    }
                }
            }
        }
        return new Double[]{receivablePrice,totalPrice};
    }

    public void IsHaveSonBill(List<Company> companyList, List<CompanySonBill> companySonBills,List<Date> dateList) throws ParseException {
        if (null == companySonBills || companySonBills.size() < 1 ) {
            throw new InterfaceCommonException(StatusConstant.NO_DATA,"请先设置为公司子账单");
        } else {
            for (int k = 0 ; k < companyList.size() ; k ++) {
                out:for (int i = 0; i < companySonBills.size(); i++) {
                    if (companyList.get(k).getId().equals(companySonBills.get(i).getCompanyId())){
                        for (CompanySonTotalBill totalBill : companySonBills.get(i).getCompanySonTotalBillList()) {
                            if (Timestamp.parseDate3(totalBill.getBillMonth(),"yyyy-MM").
                                    compareTo(Timestamp.parseDate3(dateList.get(k),"yyyy-MM")) == 0 && totalBill.getStatus() == 2) {
                                companySonBills.remove(i);
                                i--;
                                continue out;
                            }
                        }
                    }
                }
            }
            for (int k = 0 ; k < companyList.size() ; k ++) {
                int num = 0;
                for (int i = 0; i < companySonBills.size(); i++) {
                    if (companyList.get(k).getId().equals(companySonBills.get(i).getCompanyId())){
                        num++;
                    }
                }
                if (num < 1) {
                    companyList.remove(k);
                    dateList.remove(k);
                    k--;
                }
            }
            if (companySonBills.size() < 1 ) {
                throw new InterfaceCommonException(StatusConstant.NO_DATA,"请先设置为公司子账单");
            }
        }
    }

    /**
     * 稽核列表
     * @param serviceMonth 服务月份
     * @param companyId 公司id
     * @return
     */
    public PageList<MemberAuditDto> memberAuditDtoList(PageArgs pageArgs ,Date serviceMonth, Integer companyId) {
        PageList<MemberAuditDto> pageList = new PageList<MemberAuditDto>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("serviceMonth", serviceMonth);
        map.put("companyId", companyId);
        int count = companySonBillItemMapper.memberAuditDtoCount(map);
        if (count > 0) {
            map.put("pageArgs", pageArgs);
            pageList.setList(companySonBillItemMapper.memberAuditDtoList(map));
        }
        pageList.setTotalSize(count);
        return pageList;
    }

    /**
     * 封装获取服务费2 稽核实作未产生账单时
     * @param monthServiceFeesAdd
     * @param companySonTotalBills
     * @param company
     */
    public void getServiceFree2(List<MonthServiceFee> monthServiceFeesAdd,
                                List<CompanySonTotalBill> companySonTotalBills,
                                Company company ,
                                Map<Integer,List<Map<Integer,List<Member>>>> dateMembersMap,
                                CompanySonBill companySonBill,
                                Integer type,List<CompanyCooperationMethod> methods,
                                Date billMonth,Date billMonth2) throws ParseException {
        //以前已缴纳过的服务费集合
        List<CompanySonTotalBill> cstbList = company.getCompanySonTotalBillList();
        //降序
        Collections.sort(cstbList, new Comparator<CompanySonTotalBill>() {
            public int compare(CompanySonTotalBill o1, CompanySonTotalBill o2) {
                return o2.getId() - o1.getId();
            }
        });
        for (CompanySonTotalBill bill : companySonTotalBills) {
            for (CompanyCooperationMethod method : methods) {
                Integer serviceFeeConfigId = method.getServiceFeeConfigId();
                for (Map<Integer, List<Member>> listMap : dateMembersMap.get(company.getId())) {
                    for (Map.Entry<Integer, List<Member>> entry : listMap.entrySet()) {
                        //判断上一次账单生成月的服务类别
                        out:for (CompanySonTotalBill totalBill : cstbList) {
                            if (totalBill.getIsCreateBillMonth() == 1 &&
                                    Timestamp.parseDate3(totalBill.getBillMonth(),"yyyy").
                                            compareTo(Timestamp.parseDate(entry.getKey().toString(),"yyyy-MM")) < 0 &&
                                    Timestamp.parseDate(entry.getKey().toString(),"yyyy-MM").
                                            compareTo(Timestamp.parseDate3(billMonth2,"yyyy")) < 0) {
                                for (MonthServiceFee fee : totalBill.getMonthServiceFeeList()) {
                                    serviceFeeConfigId = fee.getServiceFeeConfigId();
                                    break out;
                                }

                            }
                        }

                        switch (serviceFeeConfigId) {
                            case 1 :
                                //按固定额
                                {
                                    //记录已缴纳员工的id 防止重复缴纳
                                    Set<Integer> memberIdSet = new HashSet<Integer>();
                                    for (CompanySonTotalBill totalBill : cstbList) {
                                        for (MonthServiceFee fee : totalBill.getMonthServiceFeeList()) {
                                            if (Timestamp.parseDate3(fee.getMonth(),"yyyy").
                                                    compareTo(Timestamp.parseDate(entry.getKey().toString(),"yyyy-MM")) == 0) {
                                                for (MonthServiceFeeDetail detail : fee.getMonthServiceFeeDetailList()) {
                                                    memberIdSet.add(detail.getMemberId());
                                                }
                                            }
                                        }
                                    }

                                    if (companySonBill.getId().equals(bill.getCompanySonBillId())) {
                                        List<MonthServiceFeeDetail> msfdList = new ArrayList<MonthServiceFeeDetail>();
                                        MonthServiceFee monthServiceFee = new MonthServiceFee();
                                        monthServiceFee.setCompanySonTotalBillId(bill.getId());
                                        monthServiceFee.setServiceFeeConfigId(method.getServiceFeeConfigId());
                                        monthServiceFee.setCompanySonBillId(bill.getCompanySonBillId());
                                        monthServiceFee.setCompanyId(company.getId());
                                        //服务费
                                        Double memberFee = 0.0;
                                        //派遣方式 0：普通 1：派遣  2：外包
                                        switch (method.getCooperationMethodId()) {
                                            case 0 :
                                                List<Member> memberCommons = new ArrayList<Member>();
                                                for (Member member : entry.getValue()) {
                                                    if (member.getWaysOfCooperation() == 0 &&
                                                            !memberIdSet.contains(member.getId())) {

                                                        for (MemberBusiness business : member.getMemberBusinessSet()) {
                                                            if (business.getBusinessId() == 3 &&
                                                                    business.getCompanySonBillId().equals(companySonBill.getId())) {

                                                            }
                                                        }

                                                        memberCommons.add(member);
                                                        MonthServiceFeeDetail msfd = new MonthServiceFeeDetail();
                                                        msfd.setMemberId(member.getId());
                                                        msfd.setAmount(method.getServiceFeeList().get(0).getPrice());
                                                        msfd.setCompanySonBillId(bill.getCompanySonBillId());
                                                        msfdList.add(msfd);
                                                    }
                                                }
                                                memberFee = method.getServiceFeeList().get(0).getPrice() * memberCommons.size();
                                                break;
                                            case 1 :
                                                List<Member> memberDispatchs = new ArrayList<Member>();
                                                for (Member member : entry.getValue()) {
                                                    if (member.getWaysOfCooperation() == 1 &&
                                                            !memberIdSet.contains(member.getId())) {
                                                        memberDispatchs.add(member);
                                                        MonthServiceFeeDetail msfd = new MonthServiceFeeDetail();
                                                        msfd.setMemberId(member.getId());
                                                        msfd.setAmount(method.getServiceFeeList().get(0).getPrice());
                                                        msfd.setCompanySonBillId(bill.getCompanySonBillId());
                                                        msfdList.add(msfd);
                                                    }
                                                }
                                                memberFee = method.getServiceFeeList().get(0).getPrice() * memberDispatchs.size();
                                                break;
                                            case 2 :
                                                List<Member> memberEpibolys = new ArrayList<Member>();
                                                for (Member member : entry.getValue()) {
                                                    if (member.getWaysOfCooperation() == 2 &&
                                                            !memberIdSet.contains(member.getId())) {
                                                        memberEpibolys.add(member);
                                                        MonthServiceFeeDetail msfd = new MonthServiceFeeDetail();
                                                        msfd.setCompanySonBillId(bill.getCompanySonBillId());
                                                        msfd.setMemberId(member.getId());
                                                        msfd.setAmount(method.getServiceFeeList().get(0).getPrice());
                                                        msfdList.add(msfd);
                                                    }
                                                }
                                                memberFee = method.getServiceFeeList().get(0).getPrice() * memberEpibolys.size();
                                                break;
                                        }
                                        if (method.getIsPercent() == 1) {
                                            memberFee = (1 + method.getPercent() / 100 ) * memberFee;
                                        }
                                        //服务费
                                        monthServiceFee.setServiceFee(null == company.getServiceFeeMax() && null == company.getServiceFeeMin() ? memberFee :
                                                company.getServiceFeeMax() < memberFee ? company.getServiceFeeMax() :
                                                        company.getServiceFeeMin() > memberFee ? company.getServiceFeeMin() : memberFee);
                                        //服务月
                                        monthServiceFee.setMonth(Timestamp.parseDate(entry.getKey().toString(),"yyyy-MM"));
                                        monthServiceFee.setMonthServiceFeeDetailList(msfdList);
                                        monthServiceFee.setCompanyCooperationMethodJson(JSONArray.fromObject(methods).toString());
                                        monthServiceFeesAdd.add(monthServiceFee);
                                    }
                                }
                                break;
                            case 2 :
                                {
                                    if (companySonBill.getId().equals(bill.getCompanySonBillId())) {
                                        List<MonthServiceFeeDetail> msfdList = new ArrayList<MonthServiceFeeDetail>();
                                        //按人数阶梯
                                        MonthServiceFee monthServiceFee = new MonthServiceFee();
                                        monthServiceFee.setCompanySonTotalBillId(bill.getId());
                                        monthServiceFee.setServiceFeeConfigId(method.getServiceFeeConfigId());
                                        monthServiceFee.setCompanySonBillId(bill.getCompanySonBillId());
                                        monthServiceFee.setCompanyId(company.getId());
                                        //临时记录人数
                                        Integer temporaryCount = 0;
                                        //临时服务费
                                        Double memberFee = 0.0;
                                        //类型人数
                                        Integer memberCount = 0;
                                        //派遣方式 0：普通 1：派遣  2：外包
                                        switch (method.getCooperationMethodId()) {
                                            case 0 :
                                                List<Member> memberCommons = new ArrayList<Member>();
                                                for (Member member : entry.getValue()) {
                                                    if (member.getWaysOfCooperation() == 0) {
                                                        MonthServiceFeeDetail msfd = new MonthServiceFeeDetail();
                                                        msfd.setMemberId(member.getId());
                                                        msfd.setCompanySonBillId(bill.getCompanySonBillId());
                                                        msfdList.add(msfd);
                                                        memberCommons.add(member);
                                                    }
                                                }
                                                memberCount = memberCommons.size();
                                                logger.info("按人数阶梯式派遣方式:普通...");
                                                break;
                                            case 1 :
                                                List<Member> memberDispatchs = new ArrayList<Member>();
                                                for (Member member : entry.getValue()) {
                                                    if (member.getWaysOfCooperation() == 1) {
                                                        memberDispatchs.add(member);
                                                        MonthServiceFeeDetail msfd = new MonthServiceFeeDetail();
                                                        msfd.setMemberId(member.getId());
                                                        msfd.setCompanySonBillId(bill.getCompanySonBillId());
                                                        msfdList.add(msfd);
                                                    }
                                                }
                                                memberCount = memberDispatchs.size();
                                                logger.info("按人数阶梯式派遣方式:派遣...");
                                                break;
                                            case 2 :
                                                List<Member> memberEpibolys = new ArrayList<Member>();
                                                for (Member member : entry.getValue()) {
                                                    if (member.getWaysOfCooperation() == 2) {
                                                        memberEpibolys.add(member);
                                                        MonthServiceFeeDetail msfd = new MonthServiceFeeDetail();
                                                        msfd.setMemberId(member.getId());
                                                        msfd.setCompanySonBillId(bill.getCompanySonBillId());
                                                        msfdList.add(msfd);
                                                    }
                                                }
                                                memberCount = memberEpibolys.size();
                                                logger.info("按人数阶梯式派遣方式:外包...");
                                                break;
                                        }

                                        if (memberCount > 0 ){
                                            if (method.getServiceFeeList().size() > 0) {
                                                //升序排序
                                                Collections.sort(method.getServiceFeeList(), new Comparator<CompanyCooperationServiceFee>() {
                                                    public int compare(CompanyCooperationServiceFee o1, CompanyCooperationServiceFee o2) {
                                                        return o1.getExtent() - o2.getExtent();
                                                    }
                                                });
                                            }

                                            int j = 0;
                                            for (CompanyCooperationServiceFee fee : method.getServiceFeeList()) {
                                                if (j == 0 && fee.getExtent() > memberCount) {
                                                    //小于最低人数
                                                    memberFee = memberCount * fee.getPrice();
                                                    for (MonthServiceFeeDetail detail : msfdList) {
                                                        detail.setAmount(fee.getPrice());
                                                    }
                                                    break;
                                                } else if(method.getServiceFeeList().get(method.getServiceFeeList().size() - 1).getExtent() < memberCount) {
                                                    //大于最高人数
                                                    memberFee = method.getServiceFeeList().get(method.getServiceFeeList().size() - 1).getPrice() * memberCount;
                                                    for (MonthServiceFeeDetail detail : msfdList) {
                                                        detail.setAmount(method.getServiceFeeList().get(method.getServiceFeeList().size() - 1).getPrice());
                                                    }
                                                    break;
                                                } else  {
                                                    if (fee.getExtent() > memberCount && memberCount >= temporaryCount) {
                                                        //两者之间
                                                        memberFee = method.getServiceFeeList().get(j).getPrice() * memberCount;
                                                        for (MonthServiceFeeDetail detail : msfdList) {
                                                            detail.setAmount(method.getServiceFeeList().get(j).getPrice());
                                                        }
                                                        break;
                                                    }
                                                }
                                                temporaryCount = fee.getExtent();
                                                j ++;
                                            }
                                            if (method.getIsPercent() == 1) {
                                                memberFee = (1 + method.getPercent() / 100 ) * memberFee;
                                            }
                                            if (memberFee != 0) {
                                                //服务费 应收金额大于公司最高金额 直接用最高 同理 小于最低 直接用最低
                                                monthServiceFee.setServiceFee(null == company.getServiceFeeMax() && null == company.getServiceFeeMin() ? memberFee :
                                                        company.getServiceFeeMax() < memberFee ? company.getServiceFeeMax() :
                                                                company.getServiceFeeMin() > memberFee ? company.getServiceFeeMin() : memberFee);
                                                //服务月
                                                monthServiceFee.setMonth(Timestamp.parseDate(entry.getKey().toString(),"yyyy-MM"));
                                                monthServiceFee.setMonthServiceFeeDetailList(msfdList);
                                                monthServiceFee.setCompanyCooperationMethodJson(JSONArray.fromObject(methods).toString());
                                                monthServiceFeesAdd.add(monthServiceFee);
                                            }

                                        }
                                    }
                                }
                                break;
                            case 3 :
                                //服务类别
                                // TODO: 2017/11/24 此服务费还未计算完成
                                {
                                    MonthServiceFee monthServiceFee = new MonthServiceFee();
                                    monthServiceFee.setCompanySonTotalBillId(bill.getId());
                                    monthServiceFee.setServiceFeeConfigId(method.getServiceFeeConfigId());
                                    monthServiceFee.setCompanySonBillId(bill.getCompanySonBillId());
                                    monthServiceFee.setCompanyId(company.getId());
                                    //员工及业务
                                    List<Member> members = entry.getValue();
                                    //临时服务费
                                    Double memberFee = 0.0;
                                    for (Member member : members) {
                                        if (method.getCooperationMethodId().equals(member.getWaysOfCooperation())) {
                                            for (CompanyCooperationBusinessServiceFee fee : method.getBusinessServiceFeeList()) {
                                                for (MemberMonthPayBusinessDto dto : member.getMonthPayBusinessDtoList()) {
                                                    if (dto.getServiceMonth().
                                                            compareTo(Timestamp.parseDate(entry.getKey().toString(),"yyyy-MM")) == 0) {
                                                        if (fee.getBusinessIds().equals(dto.getMemberMonthPayBusinessStr())) {
                                                            memberFee += fee.getPrice();
                                                        }
                                                    }
                                                }

                                            }
                                        }
                                    }
                                    if (method.getIsPercent() == 1) {
                                        memberFee = (1 + method.getPercent() / 100 ) * memberFee;
                                    }
                                    if (memberFee != 0) {
                                        //服务费
                                        monthServiceFee.setServiceFee(null == company.getServiceFeeMax() && null == company.getServiceFeeMin() ? memberFee :
                                                company.getServiceFeeMax() < memberFee ? company.getServiceFeeMax() :
                                                        company.getServiceFeeMin() > memberFee ? company.getServiceFeeMin() : memberFee);
                                        //服务月
                                        monthServiceFee.setMonth(Timestamp.parseDate(entry.getKey().toString(),"yyyy-MM"));
                                        monthServiceFee.setCompanyCooperationMethodJson(JSONArray.fromObject(methods).toString());
                                        monthServiceFeesAdd.add(monthServiceFee);
                                    }

                                }
                                break;
                            case 4 :
                                //服务区
                                {
                                    if (companySonBill.getId().equals(bill.getCompanySonBillId())) {
                                        List<MonthServiceFeeDetail> msfdList = new ArrayList<MonthServiceFeeDetail>();
                                        MonthServiceFee monthServiceFee = new MonthServiceFee();
                                        monthServiceFee.setCompanySonTotalBillId(bill.getId());
                                        monthServiceFee.setServiceFeeConfigId(method.getServiceFeeConfigId());
                                        monthServiceFee.setCompanySonBillId(bill.getCompanySonBillId());
                                        monthServiceFee.setCompanyId(company.getId());
                                        //员工及业务
                                        List<Member> members = entry.getValue();
                                        //临时服务费
                                        Double memberFee = 0.0;
                                        //公司交金地集合
                                        List<CompanyCooperationServicePayPlace> payPlaceList = new ArrayList<CompanyCooperationServicePayPlace>(method.getPayPlaceList());

                                        for (Member member : members) {
                                            if (method.getCooperationMethodId().equals(member.getWaysOfCooperation())) {
                                                for (int i = 0; i < payPlaceList.size(); i++) {
                                                    if (null != member.getMemberBusinessCityDtoSet()){
                                                        for (MemberBusinessCityDto dto : member.getMemberBusinessCityDtoSet()) {
                                                            if (payPlaceList.get(i).getCityId().equals(dto.getCityId())){
                                                                memberFee += payPlaceList.get(i).getPrice();
                                                                MonthServiceFeeDetail msfd = new MonthServiceFeeDetail();
                                                                msfd.setMemberId(member.getId());
                                                                msfd.setAmount(payPlaceList.get(i).getPrice());
                                                                msfd.setWaysOfCooperation(member.getWaysOfCooperation());
                                                                msfd.setCompanySonBillId(bill.getCompanySonBillId());
                                                                msfdList.add(msfd);
                                                            }
                                                        }
                                                    }

                                                }
                                            }
                                        }
                                        if (method.getIsPercent() == 1) {
                                            memberFee = (1 + method.getPercent() / 100 ) * memberFee;
                                        }

                                        if (memberFee != 0) {
                                            //服务费
                                            monthServiceFee.setServiceFee(null == company.getServiceFeeMax() && null == company.getServiceFeeMin() ? memberFee :
                                                    company.getServiceFeeMax() < memberFee ? company.getServiceFeeMax() :
                                                            company.getServiceFeeMin() > memberFee ? company.getServiceFeeMin() : memberFee);
                                            //服务月
                                            monthServiceFee.setMonth(Timestamp.parseDate(entry.getKey().toString(),"yyyy-MM"));
                                            monthServiceFee.setMonthServiceFeeDetailList(msfdList);
                                            monthServiceFee.setCompanyCooperationMethodJson(JSONArray.fromObject(methods).toString());
                                            monthServiceFeesAdd.add(monthServiceFee);
                                        }

                                    }
                                }
                                break;
                            case 5 :
                                //异动量
                                {
                                    if (companySonBill.getId().equals(bill.getCompanySonBillId())) {
                                        List<MonthServiceFeeDetail> msfdList = new ArrayList<MonthServiceFeeDetail>();
                                        MonthServiceFee monthServiceFee = new MonthServiceFee();
                                        monthServiceFee.setCompanySonTotalBillId(bill.getId());
                                        monthServiceFee.setServiceFeeConfigId(method.getServiceFeeConfigId());
                                        monthServiceFee.setCompanySonBillId(bill.getCompanySonBillId());
                                        monthServiceFee.setCompanyId(company.getId());
                                        List<Member> members = entry.getValue();
                                        //临时服务费
                                        Double memberFee = 0.0;
                                        for (Member member : members) {
                                            if (method.getCooperationMethodId().equals(member.getWaysOfCooperation())) {
                                                memberFee += member.getRecordItems().size() *
                                                        method.getServiceFeeList().get(0).getPrice();
                                                MonthServiceFeeDetail msfd = new MonthServiceFeeDetail();
                                                msfd.setMemberId(member.getId());
                                                msfd.setAmount(method.getServiceFeeList().get(0).getPrice());
                                                msfd.setCompanySonBillId(bill.getCompanySonBillId());
                                                msfdList.add(msfd);
                                            }
                                        }
                                        if (method.getIsPercent() == 1) {
                                            memberFee = (1 + method.getPercent() / 100 ) * memberFee;
                                        }
                                        if (memberFee != 0) {
                                            //服务费
                                            monthServiceFee.setServiceFee(null == company.getServiceFeeMax() && null == company.getServiceFeeMin() ? memberFee :
                                                    company.getServiceFeeMax() < memberFee ? company.getServiceFeeMax() :
                                                            company.getServiceFeeMin() > memberFee ? company.getServiceFeeMin() : memberFee);
                                            //服务月
                                            monthServiceFee.setMonth(Timestamp.parseDate(entry.getKey().toString(),"yyyy-MM"));
                                            monthServiceFee.setMonthServiceFeeDetailList(msfdList);
                                            monthServiceFee.setCompanyCooperationMethodJson(JSONArray.fromObject(methods).toString());
                                            monthServiceFeesAdd.add(monthServiceFee);
                                        }
                                    }
                                }

                                break;
                            case 6 :
                                //整体打包
                                if (billMonth.compareTo(Timestamp.parseDate(entry.getKey().toString(),"yyyy-MM")) == 0) {
                                    MonthServiceFee monthServiceFee = new MonthServiceFee();
                                    monthServiceFee.setCompanySonTotalBillId(bill.getId());
                                    monthServiceFee.setServiceFeeConfigId(method.getServiceFeeConfigId());
                                    monthServiceFee.setCompanySonBillId(bill.getCompanySonBillId());
                                    monthServiceFee.setCompanyId(company.getId());
                                    Double memberFee = 0.0;
                                    if (null != method.getServiceFeeList() && method.getServiceFeeList().size() > 0) {
                                        memberFee = method.getServiceFeeList().get(0).getPrice();
                                        if (method.getIsPercent() == 1) {
                                            memberFee = (1 + method.getPercent() / 100 ) * memberFee;
                                        }
                                    }
                                    if (memberFee != 0) {
                                        //服务费
                                        monthServiceFee.setServiceFee(null == company.getServiceFeeMax() && null == company.getServiceFeeMin() ? memberFee :
                                                company.getServiceFeeMax() < memberFee ? company.getServiceFeeMax() :
                                                        company.getServiceFeeMin() > memberFee ? company.getServiceFeeMin() : memberFee);
                                        //服务月
                                        monthServiceFee.setMonth(Timestamp.parseDate(entry.getKey().toString(),"yyyy-MM"));
                                        monthServiceFee.setCompanyCooperationMethodJson(JSONArray.fromObject(methods).toString());
                                        monthServiceFeesAdd.add(monthServiceFee);
                                    }
                                }
                                break;
                            case 7 :
                                //按人数阶梯式整体
                                if (billMonth.compareTo(Timestamp.parseDate(entry.getKey().toString(),"yyyy-MM")) == 0) {
                                    //按人数阶梯
                                    MonthServiceFee monthServiceFee = new MonthServiceFee();
                                    monthServiceFee.setCompanySonTotalBillId(bill.getId());
                                    monthServiceFee.setServiceFeeConfigId(method.getServiceFeeConfigId());
                                    monthServiceFee.setCompanySonBillId(bill.getCompanySonBillId());
                                    monthServiceFee.setCompanyId(company.getId());
                                    //临时记录人数
                                    Integer temporaryCount = 0;
                                    //临时服务费
                                    Double memberFee = 0.0;
                                    //类型人数
                                    Integer memberCount = 0;

                                    //派遣方式 0：普通 1：派遣  2：外包
                                    switch (method.getCooperationMethodId()) {
                                        case 0 :
                                            List<Member> memberCommons = new ArrayList<Member>();
                                            for (Member member : entry.getValue()) {
                                                if (member.getWaysOfCooperation() == 0) {
                                                    memberCommons.add(member);
                                                }
                                            }
                                            memberCount = memberCommons.size();
                                            logger.info("按人数阶梯式整体派遣方式:普通...");
                                            break;
                                        case 1 :
                                            List<Member> memberDispatchs = new ArrayList<Member>();
                                            for (Member member : entry.getValue()) {
                                                if (member.getWaysOfCooperation() == 1) {
                                                    memberDispatchs.add(member);
                                                }
                                            }
                                            memberCount = memberDispatchs.size();
                                            logger.info("按人数阶梯式整体派遣方式:派遣...");
                                            break;
                                        case 2 :
                                            List<Member> memberEpibolys = new ArrayList<Member>();
                                            for (Member member : entry.getValue()) {
                                                if (member.getWaysOfCooperation() == 2) {
                                                    memberEpibolys.add(member);
                                                }
                                            }
                                            memberCount = memberEpibolys.size();
                                            logger.info("按人数阶梯式整体派遣方式:外包...");
                                            break;
                                    }

                                    if (memberCount > 0) {
                                        int j = 0;
                                        if (method.getServiceFeeList().size() > 0) {
                                            //升序排序
                                            Collections.sort(method.getServiceFeeList(), new Comparator<CompanyCooperationServiceFee>() {
                                                public int compare(CompanyCooperationServiceFee o1, CompanyCooperationServiceFee o2) {
                                                    return o1.getExtent() - o2.getExtent();
                                                }
                                            });
                                        }
                                        for (CompanyCooperationServiceFee fee : method.getServiceFeeList()) {
                                            if (j == 0 && fee.getExtent() > memberCount) {
                                                //小于最低人数
                                                memberFee = fee.getPrice();
                                                break;
                                            } else if(method.getServiceFeeList().get(method.getServiceFeeList().size() - 1).getExtent() < memberCount) {
                                                //大于最高人数
                                                memberFee = method.getServiceFeeList().get(method.getServiceFeeList().size() - 1).getPrice();
                                                break;
                                            } else  {
                                                if (fee.getExtent() > memberCount && memberCount >= temporaryCount) {
                                                    //两者之间
                                                    memberFee = method.getServiceFeeList().get(j).getPrice();
                                                    break;
                                                }
                                            }
                                            temporaryCount = fee.getExtent();
                                            j ++;
                                        }
                                        if (method.getIsPercent() == 1) {
                                            memberFee = (1 + method.getPercent() / 100 ) * memberFee;
                                        }
                                        //服务费 应收金额大于公司最高金额 直接用最高 同理 小于最低 直接用最低
                                        monthServiceFee.setServiceFee(null == company.getServiceFeeMax() && null == company.getServiceFeeMin() ? memberFee :
                                                company.getServiceFeeMax() < memberFee ? company.getServiceFeeMax() :
                                                        company.getServiceFeeMin() > memberFee ? company.getServiceFeeMin() : memberFee);
                                        //服务月
                                        monthServiceFee.setMonth(Timestamp.parseDate(entry.getKey().toString(),"yyyy-MM"));
                                        monthServiceFee.setCompanyCooperationMethodJson(JSONArray.fromObject(methods).toString());
                                        monthServiceFeesAdd.add(monthServiceFee);
                                    }
                                }
                                break;
                        }
                    }
                }
                if (type == 0) {
                    break;
                }
            }
        }
    }

    /**
     * 批量生成账单
     * @param companyIds 公司id
     * @param isAll 0:生成全部  1：批量生成
     */
    /*@Transactional
    public void save2(String companyIds,Integer userId,String dates,Integer isAll) throws ParseException {
        //子账单子类
        List<CompanySonBillItem> companySonBillItems = new ArrayList<CompanySonBillItem>();
        //公司集合
        List<Company> companyList;
        //时间集合
        List<Date> dateList = new ArrayList<Date>();
        Integer[] companyIdInts;
        if (isAll == 1) {
            companyIdInts = ClassConvert.strToIntegerGather(companyIds.replaceAll("，",",").split(","));
            companyList = companyMapper.queryCompanyByIds(companyIdInts);
            dateList = ClassConvert.strListToDateListGather(JSONArray.toList(JSONArray.fromObject(dates),Long.class));
        } else {
            companyList = companyMapper.queryCompanyAll();
            companyIdInts = new Integer[companyList.size()];
            for (int i = 0 ; i < companyList.size() ; i++) {
                dateList.add(new Date());
                companyIdInts[i] = companyList.get(i).getId();
            }
        }

        //封装公司账单月
        Map<Integer ,Date> billCompanyMonthMap = new HashMap<Integer, Date>();
        for (int i = 0; i < companyList.size(); i++) {

//            List<Date> dateList2 = CommonUtil.getMonth(companyList.get(i).getBusinessCycle())

            billCompanyMonthMap.put(companyList.get(i).getId(),dateList.get(i));

        }




        //公司子账单
        List<CompanySonBill> companySonBills = companySonBillMapper.listForCompanyIds(companyIdInts,0);
        IsHaveSonBill(companyList, companySonBills);

        List<CompanySonTotalBill> companySonTotalBills = getCompanySonTotalBills(userId, companySonBills);

        //封装每个公司的服务月集合及员工集合 Map<公司Id,List<Map<时间戳,员工集合>>>
        Map<Integer,List<Map<Integer,List<Member>>>> dateMembersMap =
                new HashMap<Integer, List<Map<Integer, List<Member>>>>();

        //添加汇总账单
        companySonTotalBillMapper.save(companySonTotalBills);
    }*/


}
