package com.magic.daoyuan.business.service;

import com.alibaba.fastjson.JSONArray;
import com.magic.daoyuan.business.dto.HrBillListDto;
import com.magic.daoyuan.business.entity.CompanyCooperationMethod;
import com.magic.daoyuan.business.entity.MonthServiceFeeBalance;
import com.magic.daoyuan.business.entity.PageArgs;
import com.magic.daoyuan.business.entity.PageList;
import com.magic.daoyuan.business.mapper.*;
import com.magic.daoyuan.business.util.CommonUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * hr（web）端账单列表
 * @author lzh
 * @create 2017/12/19 14:21
 */
@Service
public class HrBillListDtoService {

    @Resource
    private ICompanySonTotalBillMapper hrBillListDtoMapper;
    @Resource
    private IMonthServiceFeeBalanceMapper monthServiceFeeBalanceMapper;
    @Resource
    private ICompanyCooperationMethodMapper companyCooperationMethodMapper;
    @Resource
    private ISocialSecurityInfoMapper socialSecurityInfoMapper;
    @Resource
    private IReservedFundsInfoMapper reservedFundsInfoMapper;


    /**
     * hr（web）端页面 分页获取账单列表
     * @param pageArgs 分页属性
     * @param billMonth 账单月
     * @param affirmTime 账单确认时间
     * @param amount 账单金额
     * @param businessIds 业务类型id集合
     * @param businessName 业务名
     * @return
     */
    public PageList<HrBillListDto> list(PageArgs pageArgs , Date billMonth , Date affirmTime,
                                        Double amount , List<Integer> businessIds, String businessName ,Integer companyId) {
        PageList<HrBillListDto> pageList = new PageList<HrBillListDto>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("billMonth", billMonth);
        map.put("affirmTime", affirmTime);
        map.put("amount", amount);
        map.put("businessIds", businessIds);
        map.put("businessName", businessName);
        map.put("companyId", companyId);
        int count = hrBillListDtoMapper.listHrBillListDtoCount(map);
        //如果总金额不为空使用
        List<HrBillListDto> rsultList = new ArrayList<HrBillListDto>();
        if (count > 0) {
            if (CommonUtil.isEmpty(amount)) {
                map.put("pageArgs", pageArgs);
            }
            List<HrBillListDto> list = hrBillListDtoMapper.listHrBillListDto(map);
            if(null != list && list.size() > 0){
                Collections.sort(list, new Comparator<HrBillListDto>() {
                    public int compare(HrBillListDto o1, HrBillListDto o2) {
                        return o2.getBillMonth().compareTo(o1.getBillMonth());
                    }
                });
                List<Integer> companyIds = new ArrayList<Integer>();
                for (HrBillListDto companySonTotalBillDto : list) {
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
                            add(new BigDecimal(companySonTotalBillDto.getTaxPrice().toString())).
                            add(new BigDecimal(getDouble(companySonTotalBillDto.getYcPrice()).toString())).
                            add(new BigDecimal(getDouble(companySonTotalBillDto.getInsurancePrice()).toString()));
                    companySonTotalBillDto.setAmount(new BigDecimal(companySonTotalBillDto.getAmount().toString()).add(price).setScale(2,BigDecimal.ROUND_DOWN).doubleValue());
                    companyIds.add(companySonTotalBillDto.getCompanyId());
                    //如果总金额与计算出来的金额相同 则进行装配
                    if (!CommonUtil.isEmpty(amount) && amount.toString().equals(companySonTotalBillDto.getAmount().toString())) {
                        rsultList.add(companySonTotalBillDto);
                    }
                }
                //手动分页
                if (!CommonUtil.isEmpty(amount)) {
                    pageList.setTotalSize(rsultList.size());
                    list = rsultList;
                    if (rsultList.size() > 0) {
                        Collections.sort(rsultList, new Comparator<HrBillListDto>() {
                            public int compare(HrBillListDto o1, HrBillListDto o2) {
                                return o2.getBillMonth().compareTo(o1.getBillMonth());
                            }
                        });
                        //最大页数
                        int totalPage = (int)Math.ceil(rsultList.size() / pageArgs.getPageSize());
                        //当前页
                        int page = totalPage < pageArgs.getPageStart() ? totalPage : pageArgs.getPageStart();
                        int fromIndex = page * pageArgs.getPageSize();
                        int toIndex = (page + pageArgs.getPageSize() - 1) > rsultList.size() ? rsultList.size() : (page + pageArgs.getPageSize() - 1);
                        list = rsultList.subList(fromIndex,toIndex);
                    }

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
