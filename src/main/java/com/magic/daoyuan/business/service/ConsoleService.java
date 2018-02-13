package com.magic.daoyuan.business.service;

import com.alibaba.fastjson.JSONArray;
import com.magic.daoyuan.business.dto.CompanySonTotalBillDto;
import com.magic.daoyuan.business.dto.HrBillListDto;
import com.magic.daoyuan.business.entity.*;
import com.magic.daoyuan.business.mapper.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * 控制台 部分数据
 * @author lzh
 * @create 2017/12/25 18:06
 */
@Service
public class ConsoleService {

    @Resource
    private IConsoleMapper consoleMapper;
    @Resource
    private ICompanySonTotalBillMapper companySonTotalBillMapper;
    @Resource
    private IConfirmFundMapper confirmFundMapper;
    @Resource
    private ISocialSecurityInfoMapper socialSecurityInfoMapper;
    @Resource
    private IReservedFundsInfoMapper reservedFundsInfoMapper;
    @Resource
    private IMonthServiceFeeBalanceMapper monthServiceFeeBalanceMapper;
    @Resource
    private ICompanyCooperationMethodMapper companyCooperationMethodMapper;
    /**
     * 控制台右下角统计数据
     * @param date
     * @return
     */
    public Map<String ,Object> rightConsole(Date date) {
        return consoleMapper.rightConsole(date);
    }


    /**
     * 后台页面 分页获取工作台列表
     *
     * @param pageArgs    分页属性
     * @param companyId     公司id
     * @param companySonBillId     子账单id
     * @param startTime   账单创建的开始时间
     * @param endTime     账单创建的结束时间
     * @return
     */
    public PageList<HrBillListDto> listConsoleDto(PageArgs pageArgs , Integer companyId , String companyName , Integer companySonBillId ,
                                                    Date startTime , Date endTime) {
        PageList<HrBillListDto> pageList = new PageList<HrBillListDto>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("companyId", companyId);
        map.put("companyName", companyName);
        map.put("companySonBillId", companySonBillId);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        int count = companySonTotalBillMapper.listConsoleDtoCount(map);
        if (count > 0) {
            map.put("pageArgs", pageArgs);
            List<HrBillListDto> list = companySonTotalBillMapper.listConsoleDto(map);
            if(null != list && list.size() > 0){
                Collections.sort(list, new Comparator<HrBillListDto>() {
                    public int compare(HrBillListDto o1, HrBillListDto o2) {
                        return o2.getBillMonth().compareTo(o1.getBillMonth());
                    }
                });
                List<Integer> companyIds = new ArrayList<Integer>();
                for (HrBillListDto companySonTotalBillDto : list) {
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
                       /* if (null != companySonTotalBillDto.getCompanyCooperationMethodJson2()) {
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
                            add(new BigDecimal(companySonTotalBillDto.getTaxPrice().toString())).
                            add(new BigDecimal(getDouble(companySonTotalBillDto.getInsurancePrice()).toString())).
                            add(new BigDecimal(getDouble(companySonTotalBillDto.getYcPrice()).toString()));
                   /* //当月税费 - 上月税费 = 当前税费
                    taxPrice = new BigDecimal(new BigDecimal(companySonTotalBillDto.getAmount().toString()).
                            multiply(new BigDecimal(methodList.get(0).getPercent().toString())).toString()).subtract(new BigDecimal(taxPrice.toString())).doubleValue();
                    if (methodList.get(0).getIsPercent() == 1) {
                        companySonTotalBillDto.setServiceFee(new BigDecimal(companySonTotalBillDto.getServiceFee().toString()).
                                multiply(new BigDecimal(methodList.get(0).getPercent().toString())).
                                add(new BigDecimal(companySonTotalBillDto.getServiceFee().toString())).doubleValue());

                        taxPrice = new BigDecimal(new BigDecimal(companySonTotalBillDto.getAmount().toString()).
                                add(new BigDecimal(companySonTotalBillDto.getServiceFee().toString())).toString()).
                                multiply(new BigDecimal(methodList.get(0).getPercent().toString())).subtract(new BigDecimal(taxPrice.toString())).doubleValue();
                    }*/

                    companySonTotalBillDto.setAmount(new BigDecimal(companySonTotalBillDto.getAmount().toString()).add(price)./*add(new BigDecimal(taxPrice.toString())).*/setScale(2,BigDecimal.ROUND_DOWN).doubleValue());

                    companyIds.add(companySonTotalBillDto.getCompanyId());
                }

            }

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


}
