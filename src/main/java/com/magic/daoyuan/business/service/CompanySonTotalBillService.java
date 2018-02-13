package com.magic.daoyuan.business.service;

import com.alibaba.fastjson.JSONArray;
import com.magic.daoyuan.business.dto.CompanySonTotalBillDto;
import com.magic.daoyuan.business.dto.QTemp;
import com.magic.daoyuan.business.entity.*;
import com.magic.daoyuan.business.mapper.*;
import com.magic.daoyuan.business.util.CommonUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * 汇总月账单
 * @author lzh
 * @create 2017/10/24 11:14
 */
@Service
public class CompanySonTotalBillService {

    @Resource
    private ICompanySonTotalBillMapper companySonTotalBillMapper;
    @Resource
    private IConfirmFundMapper confirmFundMapper;
    @Resource
    private ISocialSecurityInfoMapper socialSecurityInfoMapper;
    @Resource
    private IReservedFundsInfoMapper reservedFundsInfoMapper;
    @Resource
    private ICompanyBusinessMapper companyBusinessMapper;
    @Resource
    private ICompanyBusinessItemMapper companyBusinessItemMapper;
    @Resource
    private IMemberBusinessMapper memberBusinessMapper;
    @Resource
    private IMemberBusinessOtherItemMapper memberBusinessOtherItemMapper;
    @Resource
    private IMonthServiceFeeBalanceMapper monthServiceFeeBalanceMapper;
    @Resource
    private ICompanyCooperationMethodMapper companyCooperationMethodMapper;


    public List<CompanySonTotalBill> queryCompanySonTotalBill(List<Map<String ,Object>> mapList){
        return companySonTotalBillMapper.queryCompanySonTotalBill(mapList);
    }


    public List<CompanySonTotalBill> queryCompanySonTotalBill_(List<Map<String ,Object>> mapList){
        return companySonTotalBillMapper.queryCompanySonTotalBill_(mapList);
    }

    /**
     * 详情
     * @param id
     * @return
     */
    public CompanySonTotalBill info(Integer id) {
        return companySonTotalBillMapper.info(id);
    }


    /**
     * 后台页面 分页获取公司汇总
     *
     * @param pageArgs    分页属性
     * @param companyId     公司id
     * @param companySonBillId     子账单id
     * @param startTime   账单创建的开始时间
     * @param endTime     账单创建的结束时间
     * @return
     */
    public PageList<CompanySonTotalBill> list(PageArgs pageArgs , Integer companyId , String companyName , Integer companySonBillId ,
                                             Date startTime , Date endTime) {
        PageList<CompanySonTotalBill> pageList = new PageList<CompanySonTotalBill>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("companyId", companyId);
        map.put("companyName", companyName);
        map.put("companySonBillId", companySonBillId);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        int count = companySonTotalBillMapper.listCount(map);
        if (count > 0) {
            map.put("pageArgs", pageArgs);
            pageList.setList(companySonTotalBillMapper.list(map));
        }
        pageList.setTotalSize(count);
        return pageList;
    }


    // TODO: 2018/1/26 0026  使用于 账单核销，查询公司的账单
    public List<BillAmountOfCompany> buildBillAmountOfCompany(List<Integer> companyIdList){

        List<BillAmountOfCompany> companyList = new ArrayList<BillAmountOfCompany>();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("companyIds", companyIdList);
        map.put("flag", 1);
        List<CompanySonTotalBillDto> list = companySonTotalBillMapper.listDto(map);
        if(null != list && list.size() > 0){
            Collections.sort(list, new Comparator<CompanySonTotalBillDto>() {
                public int compare(CompanySonTotalBillDto o1, CompanySonTotalBillDto o2) {
                    return o2.getBillMonth().compareTo(o1.getBillMonth());
                }
            });
            List<Integer> companyIds = new ArrayList<Integer>();
            for (CompanySonTotalBillDto companySonTotalBillDto : list) {
                //税费
                //纳入次月账单社保缴纳的实际总差额
                Double sPractical = 0.0;
                //纳入次月账单公积金缴纳的实际总差额
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
                // TODO: 2018/1/8
                BigDecimal price = new BigDecimal(0.0).
                        subtract(new BigDecimal(companySonTotalBillDto.getLastMonthBalance().toString())).
                        subtract(new BigDecimal(sPractical.toString())).
                        subtract(new BigDecimal(rPractical.toString())).
                        subtract(new BigDecimal(lastMonthServiceFee.toString())).
                        add(new BigDecimal(companySonTotalBillDto.getServiceFee().toString())).
                        add(new BigDecimal(companySonTotalBillDto.getTaxPrice().toString())).
                        add(new BigDecimal(getDouble(companySonTotalBillDto.getInsurancePrice()).toString())).
                        add(new BigDecimal(getDouble(companySonTotalBillDto.getYcPrice()).toString())).
                        add(new BigDecimal(companySonTotalBillDto.getSalaryInfoPrice().toString()));
                companySonTotalBillDto.setTotalPrice(new BigDecimal(companySonTotalBillDto.getTotalPrice().toString()).add(price)/*.add(new BigDecimal(taxPrice.toString()))*/.setScale(2,BigDecimal.ROUND_DOWN).doubleValue());
                companySonTotalBillDto.setReceivablePrice(new BigDecimal(companySonTotalBillDto.getReceivablePrice().toString()).add(price)/*.add(new BigDecimal(taxPrice.toString()))*/.setScale(2,BigDecimal.ROUND_DOWN).doubleValue());
                companyIds.add(companySonTotalBillDto.getCompanyId());
            }

            // 封装计算后的数据
            for (CompanySonTotalBillDto dto : list) {
                BillAmountOfCompany c = new BillAmountOfCompany();
                c.setBillAmount(dto.getTotalPrice());
                c.setCompanyId(dto.getCompanyId());
                c.setCompanySonTotalBillId(dto.getCompanySonTotalBillId());
                companyList.add(c);
            }
        }
        return companyList;
    }

    /**
     * 后台页面 分页获取公司汇总
     *
     * @param pageArgs    分页属性
     * @param companyId     公司id
     * @param companySonBillId     子账单id
     * @param startTime   账单创建的开始时间
     * @param endTime     账单创建的结束时间
     * @return
     */
    // TODO: 2018/1/26 0026  如果修改下面的计算方式，记得修改  方法：buildBillAmountOfCompany() 的计算方式，保持一致性
    public PageList<CompanySonTotalBillDto> listDto(PageArgs pageArgs , Integer companyId , String companyName , Integer companySonBillId ,
                                                    Date startTime , Date endTime,Integer beforeService,Integer hexiao,
                                                    Integer jihe,Integer status,Date billMonth) {
        PageList<CompanySonTotalBillDto> pageList = new PageList<CompanySonTotalBillDto>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("companyId", companyId);
        map.put("companyName", companyName);
        map.put("companySonBillId", companySonBillId);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("beforeService", beforeService);
        map.put("hexiao", hexiao);
        map.put("jihe", jihe);
        map.put("status", status);
        map.put("billMonth", billMonth);
        map.put("flag", 0);
        int count = companySonTotalBillMapper.listDtoCount(map);
        if (count > 0) {
            map.put("pageArgs", pageArgs);
            List<CompanySonTotalBillDto> list = companySonTotalBillMapper.listDto(map);
            // 认款记录查询，获取处理方式 开始
            if(null != list && list.size() > 0){
                Collections.sort(list, new Comparator<CompanySonTotalBillDto>() {
                    public int compare(CompanySonTotalBillDto o1, CompanySonTotalBillDto o2) {
                        return o2.getBillMonth().compareTo(o1.getBillMonth());
                    }
                });
                List<Integer> companyIds = new ArrayList<Integer>();
                for (CompanySonTotalBillDto companySonTotalBillDto : list) {
                    //税费
//                    Double taxPrice = 0.0;

                    //纳入次月账单社保缴纳的实际总差额
                    Double sPractical = 0.0;
//                    Double ssiPracticalPay = 0.0;
                    //纳入次月账单公积金缴纳的实际总差额
                    Double rPractical = 0.0;
//                    Double rfiPracticalPay = 0.0;
                    if (!companyIds.contains(companySonTotalBillDto.getCompanyId())) {
                        sPractical = socialSecurityInfoMapper.getPracticalByCompanyIdAndBillMonth(
                                        companySonTotalBillDto.getCompanyId(),null,companySonTotalBillDto.getBillMonth(),null);
                        rPractical = reservedFundsInfoMapper.getPracticalByCompanyIdAndBillMonth(
                                        companySonTotalBillDto.getCompanyId(),null,companySonTotalBillDto.getBillMonth(),null);
                        //本次账单月社保缴纳总金额
//                        socialSecurityPay = socialSecurityInfoMapper.getPayPriceByCompanyIdAndBillMonth(companySonTotalBillDto.getCompanyId(),companySonTotalBillDto.getBillMonth());
                        //纳入次月账单社保缴纳的实际总金额
//                        ssiPracticalPay = socialSecurityInfoMapper.getPracticalByCompanyIdAndBillMonth(companySonTotalBillDto.getCompanyId(),null,companySonTotalBillDto.getBillMonth(),null);

                        //本次账单月公积金预收
//                        reservedFundPay = reservedFundsInfoMapper.getPayPriceByCompanyIdAndBillMonth(companySonTotalBillDto.getCompanyId(),companySonTotalBillDto.getBillMonth());
                        //纳入次月账单公积金缴纳的实际总金额
//                        rfiPracticalPay = reservedFundsInfoMapper.getPracticalByCompanyIdAndBillMonth(companySonTotalBillDto.getCompanyId(),null,companySonTotalBillDto.getBillMonth(),null);
                        /*if (null != companySonTotalBillDto.getCompanyCooperationMethodJson2()) {
                            //服务费配置集合 上月
                            List<CompanyCooperationMethod> methodList = JSONArray.parseArray(companySonTotalBillDto.getCompanyCooperationMethodJson2(),CompanyCooperationMethod.class);
                            taxPrice += new BigDecimal(sPractical.toString()).add(new BigDecimal(rPractical.toString())).multiply(new BigDecimal(methodList.get(0).getPercent())).doubleValue();
                        }*/
                    }


                   /* //服务费配置集合
                    List<CompanyCooperationMethod> methodList = new ArrayList<CompanyCooperationMethod>();
                    if (null == companySonTotalBillDto.getCompanyCooperationMethodJson()) {
                        methodList = companyCooperationMethodMapper.queryCompanyCooperationMethod(companySonTotalBillDto.getCompanyId());
                    } else {
                        methodList = JSONArray.parseArray(companySonTotalBillDto.getCompanyCooperationMethodJson(),CompanyCooperationMethod.class);
                    }*/
                    //获取上月稽核服务费
                    MonthServiceFeeBalance monthServiceFeeBalance = monthServiceFeeBalanceMapper.
                            getByCompanyIdAndBillMonth2(companySonTotalBillDto.getCompanyId(), companySonTotalBillDto.getBillMonth());



                    Double lastMonthServiceFee = 0.0;
                    if (null != monthServiceFeeBalance) {
                        lastMonthServiceFee = monthServiceFeeBalance.getServiceFeeBalance();
                        /*if (null != companySonTotalBillDto.getCompanyCooperationMethodJson2()) {
                            //服务费配置集合 上月
                            List<CompanyCooperationMethod> methodList2 = JSONArray.parseArray(companySonTotalBillDto.getCompanyCooperationMethodJson2(),CompanyCooperationMethod.class);
                            if (methodList.get(0).getIsPercent() == 1) {
                                taxPrice += new BigDecimal(lastMonthServiceFee.toString()).multiply(new BigDecimal(methodList2.get(0).getPercent().toString())).doubleValue();
                            }
                        }*/
                    }
                    // TODO: 2018/1/8  
                    BigDecimal price = new BigDecimal(0.0).
                            subtract(new BigDecimal(companySonTotalBillDto.getLastMonthBalance().toString())).
                            subtract(new BigDecimal(sPractical.toString())).
                            subtract(new BigDecimal(rPractical.toString())).
                            subtract(new BigDecimal(lastMonthServiceFee.toString())).
                            add(new BigDecimal(companySonTotalBillDto.getServiceFee().toString())).
                            add(new BigDecimal(companySonTotalBillDto.getTaxPrice().toString())).
                            add(new BigDecimal(getDouble(companySonTotalBillDto.getInsurancePrice()).toString())).
                            add(new BigDecimal(getDouble(companySonTotalBillDto.getYcPrice()).toString())).
                            add(new BigDecimal(companySonTotalBillDto.getSalaryInfoPrice().toString()));
                    /*//当月税费 - 上月税费 = 当前税费
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

                    companySonTotalBillDto.setTotalPrice(new BigDecimal(companySonTotalBillDto.getTotalPrice().toString()).add(price)/*.add(new BigDecimal(taxPrice.toString()))*/.setScale(2,BigDecimal.ROUND_DOWN).doubleValue());
                    companySonTotalBillDto.setReceivablePrice(new BigDecimal(companySonTotalBillDto.getReceivablePrice().toString()).add(price)/*.add(new BigDecimal(taxPrice.toString()))*/.setScale(2,BigDecimal.ROUND_DOWN).doubleValue());
//                    companySonTotalBillDto.setTaxPrice(taxPrice);
                    companyIds.add(companySonTotalBillDto.getCompanyId());
                }
                List<ConfirmFund> confirmFunds = confirmFundMapper.queryConfirmFundByCompany(companyIds);
                if(null != confirmFunds && confirmFunds.size() > 0){
                    for (CompanySonTotalBillDto companySonTotalBillDto : list) {
                        for (ConfirmFund confirmFund : confirmFunds) {
                            if(companySonTotalBillDto.getCompanyId().equals(confirmFund.getCompanyId())){
                                companySonTotalBillDto.setConfirmFund(confirmFund);
                                break;
                            }
                        }
                    }
                }
            }
            // 认款记录查询，获取处理方式 结束
            pageList.setList(list);
        }
        pageList.setTotalSize(count);
        return pageList;
    }

    private Double getDouble(Double insurancePrice) {

        if (null == insurancePrice){
            return 0.0;
        }else{
            return insurancePrice;
        }
    }

    /**
     * 获取超时未确认的账单
     * @param dateDay 时间 天 1-31
     * @return
     */
    public List<CompanySonTotalBill> getUncertainBill(Integer dateDay) {
       return companySonTotalBillMapper.getUncertainBill(dateDay);
    }


    /**
     * 批量更新发送总结
     * @param
     */
    @Transactional
    public void updateConsigneeList(List<QTemp> qTemps) {

        companySonTotalBillMapper.updateConsigneeList(qTemps);
        // 清除一次性险
        List<Integer> companyIds = new ArrayList<Integer>();
        for (QTemp qTemp : qTemps) {
            companyIds.add(qTemp.getCompanyId());
        }
        if(companyIds.size() > 0){
            List<CompanyBusiness> companyBusinesses = companyBusinessMapper.queryCompanyBusinessOfYC(companyIds);
            List<Integer> cbs = new ArrayList<Integer>();
            List<Integer> cbis = new ArrayList<Integer>();
            for (CompanyBusiness companyBusiness : companyBusinesses) {
                cbs.add(companyBusiness.getId());
                if(null != companyBusiness.getCompanyBusinessItems() && companyBusiness.getCompanyBusinessItems().size() > 0){
                    for (CompanyBusinessItem item : companyBusiness.getCompanyBusinessItems()) {
                        cbis.add(item.getId());
                    }
                }
            }
            // 删除数据
            if(cbs.size() > 0){
                companyBusinessMapper.del(cbs);
            }
            if(cbis.size() > 0){
                companyBusinessItemMapper.del(cbis);
            }
            // 员工一次性业务数据
            List<MemberBusiness> businessList = memberBusinessMapper.queryMemberBusinessForDel(companyIds);
            List<Integer> cbs_ = new ArrayList<Integer>();
            List<Integer> cbis_ = new ArrayList<Integer>();
            for (MemberBusiness business : businessList) {
                cbs_.add(business.getId());
                if(null != business.getOtherItemList() && business.getOtherItemList().size() > 0){
                    for (MemberBusinessOtherItem otherItem : business.getOtherItemList()) {
                        cbis_.add(otherItem.getId());
                    }
                }
            }
            if(cbs_.size() > 0){
                memberBusinessMapper.del(cbs_);
            }
            if(cbis_.size() > 0){
                memberBusinessOtherItemMapper.del(cbis_);
            }
        }
    }

}
