package com.magic.daoyuan.business.service;

import com.alibaba.fastjson.JSONArray;
import com.magic.daoyuan.business.entity.*;
import com.magic.daoyuan.business.enums.InsuranceType;
import com.magic.daoyuan.business.exception.InterfaceCommonException;
import com.magic.daoyuan.business.mapper.ICompanyInsuranceMapper;
import com.magic.daoyuan.business.mapper.ICompanyPayPlaceMapper;
import com.magic.daoyuan.business.mapper.IInsuranceMapper;
import com.magic.daoyuan.business.util.StatusConstant;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 公司绑定缴金地
 * @author lzh
 * @create 2017/10/10 15:52
 */
@Service
public class CompanyPayPlaceService {

    @Resource
    private ICompanyPayPlaceMapper companyPayPlaceMapper;

    @Resource
    private ICompanyInsuranceMapper companyInsuranceMapper;
    @Resource
    private IInsuranceMapper insuranceMapper;


    public List<CompanyPayPlace> queryCompanyPayPlaceByItems(Integer companyId,
                                                             Integer type){
        List<CompanyPayPlace> list = companyPayPlaceMapper.queryCompanyPayPlaceByItems(companyId,type);
        for (CompanyPayPlace place : list) {
            place.setPayPlaceName(null != place.getPayPlaceName() ? place.getPayPlaceName().replace("中国,","").replace("省","") : "");
        }
        return list;
    }

    /**
     * 添加缴金地 和险种
     * @param companyPayPlace 缴金地
     * @param companyInsuranceJsonAry 险种的json数组 字符串
     */
    @Transactional
    public void save (CompanyPayPlace companyPayPlace,String companyInsuranceJsonAry) {

        if (null != companyPayPlaceMapper.isHave(companyPayPlace.getCompanyId(),
                companyPayPlace.getTransactorName(),
                companyPayPlace.getOrganizationName(),companyPayPlace.getPayPlaceId(),
                companyPayPlace.getOrganizationId(),null,companyPayPlace.getType())) {
            throw new InterfaceCommonException(StatusConstant.OBJECT_EXIST,"已存在");
        }

        companyPayPlaceMapper.save(companyPayPlace);
        if (null != companyInsuranceJsonAry && !"".equals(companyInsuranceJsonAry)) {
            List<CompanyInsurance> companyInsurances = JSONArray.parseArray(companyInsuranceJsonAry,CompanyInsurance.class);
            for (CompanyInsurance insurance : companyInsurances) {
                insurance.setCompanyId(companyPayPlace.getCompanyId());
                insurance.setCompanyPayPlaceId(companyPayPlace.getId());
            }
            if(companyInsurances.size() > 0){
                companyInsuranceMapper.save(companyInsurances);
            }
        }
    }

    /**
     * 更新缴金地 和险种
     * @param companyPayPlace 缴金地
     * @param companyInsuranceJsonAry 险种的json数组 字符串
     */
    @Transactional
    public void update (CompanyPayPlace companyPayPlace,String companyInsuranceJsonAry) {

        CompanyPayPlace info = companyPayPlaceMapper.info(companyPayPlace.getId());
        if(null == info){
            throw new InterfaceCommonException(StatusConstant.OBJECT_EXIST,"数据不存在");
        }

        if (null != companyPayPlaceMapper.isHave(companyPayPlace.getCompanyId(),
                companyPayPlace.getTransactorName(),
                companyPayPlace.getOrganizationName(),companyPayPlace.getPayPlaceId(),
                companyPayPlace.getOrganizationId(),info.getId(),companyPayPlace.getType())) {
            throw new InterfaceCommonException(StatusConstant.OBJECT_EXIST,"已存在");
        }

        companyPayPlaceMapper.update(companyPayPlace);
        if (null != companyInsuranceJsonAry && !"".equals(companyInsuranceJsonAry)) {
            List<CompanyInsurance> companyInsurances = JSONArray.parseArray(companyInsuranceJsonAry,CompanyInsurance.class);
            for (CompanyInsurance insurance : companyInsurances) {
                insurance.setCompanyId(companyPayPlace.getCompanyId());
                insurance.setCompanyPayPlaceId(companyPayPlace.getId());
            }
            companyInsuranceMapper.delete(companyPayPlace.getCompanyId(),info.getId());
            if (null != companyInsurances && companyInsurances.size() > 0) {
                companyInsuranceMapper.save(companyInsurances);
            }
        }
    }


    /**
     * 后台页面 分页获取缴金地
     *
     * @param pageArgs    分页属性
     * @param transactorName     办理方名称
     * @param payPlaceName   缴金地名
     * @param isValid   是否有效 0 无效 1有效
     * @param organizationName   经办机构名
     * @param companyId   公司名
     * @return
     */
    public PageList<CompanyPayPlace> list(PageArgs pageArgs , String transactorName ,String payPlaceName ,
                                          String organizationName , Integer isValid, Integer companyId, Integer type) {
        PageList<CompanyPayPlace> pageList = new PageList<CompanyPayPlace>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("transactorName", transactorName);
        map.put("organizationName", organizationName);
        map.put("payPlaceName", payPlaceName);
        map.put("isValid", isValid);
        map.put("companyId", companyId);
        map.put("type", type);
        int count = companyPayPlaceMapper.listCount(map);
        if (count > 0) {
            map.put("pageArgs", pageArgs);
            List<CompanyPayPlace> list = companyPayPlaceMapper.list(map);
            if(null != list && list.size() > 0){
                for (CompanyPayPlace place : list) {
                    if(null != place.getPayPlaceName()){
                        String s = place.getPayPlaceName().substring(3);
                        s = s.replaceAll("省", "");
                        place.setPayPlaceName(s.replaceAll("市",""));
                    }
                }
            }
            pageList.setList(list);
        }
        pageList.setTotalSize(count);
        return pageList;
    }

    /**
     * 缴金地 和险种详情
     * @param id
     * @return
     */
    public CompanyPayPlace info (Integer id) {
        CompanyPayPlace companyPayPlace = companyPayPlaceMapper.info(id);
        if (null != companyPayPlace && null != companyPayPlace.getCompanyId()) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("companyId",companyPayPlace.getCompanyId());
            map.put("companyPayPlaceId",id);
            List<CompanyInsurance> list = companyInsuranceMapper.list(map);

            companyPayPlace.setCompanyInsurances(list);
        }
        return companyPayPlace;
    }

}
