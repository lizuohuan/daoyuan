package com.magic.daoyuan.business.service;

import com.magic.daoyuan.business.entity.*;
import com.magic.daoyuan.business.exception.InterfaceCommonException;
import com.magic.daoyuan.business.mapper.ICompanyPayPlaceMapper;
import com.magic.daoyuan.business.mapper.ILogMapper;
import com.magic.daoyuan.business.mapper.IOrganizationMapper;
import com.magic.daoyuan.business.util.CommonUtil;
import com.magic.daoyuan.business.util.StatusConstant;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * 经办机构 业务
 * Created by Eric Xie on 2017/9/27 0027.
 */
@Service
public class OrganizationService {


    @Resource
    private IOrganizationMapper organizationMapper;
    @Resource
    private ILogMapper logMapper;
    @Resource
    private ICompanyPayPlaceMapper companyPayPlaceMapper;


    /**
     * 获取上下限
     * @param organization
     * @return
     */
    public Map<String,Double> countByOrganization(Integer organization){
        return organizationMapper.countByOrganization(organization);
    }

    /**
     * 计算 缴金地下的最大基数 和 最小基数
     * @param payPlaceId
     * @return
     */
    public Map<String,Double> countBaseNumber(Integer payPlaceId){
        Map<String,Double> result = new HashMap<String, Double>();
        Double min = null;
        Double max = null;
        List<Organization> organizations = organizationMapper.queryOrganizationByPayPlace(payPlaceId);
        if(null != organizations && organizations.size() > 0){
            List<Double> minList = new ArrayList<Double>();
            List<Double> maxList = new ArrayList<Double>();
            for (Organization organization : organizations) {
                if(null != organization.getMinCardinalNumber()){
                    minList.add(organization.getMinCardinalNumber());
                }
                if(null != organization.getMaxCardinalNumber()){
                    maxList.add(organization.getMaxCardinalNumber());
                }
            }
            if(minList.size() > 0){
                Collections.sort(minList);
                min = minList.get(0);
            }
            if(maxList.size() > 0){
                Collections.reverse(maxList);
                max = maxList.get(0);
            }
        }
        result.put("min",min);
        result.put("max",max);
        return result;
    }

    /**
     * 通过缴金地 查询经办机构的ID 和 名称
     * @param payPlaceId
     * @return
     */
    public List<Organization> queryOrganizationByPayPlace(Integer payPlaceId){
        return organizationMapper.queryOrganizationByPayPlace(payPlaceId);
    }

    /**
     * 通过缴金地 查询经办机构的ID 和 名称
     * @param payPlaceId
     * @param type 0 社保  1 公积金
     * @return
     */
    public List<Organization> queryOrganizationByPayPlace(Integer payPlaceId,Integer type){
        // 获取托管下的经办机构
        List<Organization> organizations = companyPayPlaceMapper.queryOrganizationByPayPlace(payPlaceId);
        if(null == organizations){
            organizations = new ArrayList<Organization>();
        }
        List<Organization> organizations1 = organizationMapper.queryOrganizationByPayPlace(payPlaceId);
        organizations.addAll(organizations1);
        return organizations;
    }

    public PageList<Organization> getOrganization(Map<String,Object> params, PageArgs pageArgs){
        List<Organization> dataList = new ArrayList<Organization>();
        int count = organizationMapper.countOrganizationByItems(params);
        if(count > 0){
            params.put("limit",pageArgs.getPageStart());
            params.put("limitSize",pageArgs.getPageSize());
            dataList = organizationMapper.queryOrganizationByItems(params);
        }
        return new PageList<Organization>(dataList,count);
    }

    public Organization getOrganizationById(Integer id){
        return organizationMapper.queryOrganizationById(id);
    }


    @Transactional
    public void updateOrganization(Organization organization,User user){
        Organization o = organizationMapper.queryOrganizationById(organization.getId());
        if(null == o){
            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST,"经办机构不存在");
        }
        StringBuffer sb = new StringBuffer("经办机构修改为：");

        if(!CommonUtil.isEmpty(organization.getOrganizationName())){
            sb.append("名称："+organization.getOrganizationName());
        }
        if(!CommonUtil.isEmpty(organization.getOperationType())){
            if(organization.getOperationType() == 0){
                sb.append("  操作方式: 本月");
            }
            else if(organization.getOperationType() == 1){
                sb.append("  操作方式: 次月");
            }
            else if(organization.getOperationType() == 2){
                sb.append("  操作方式: 上月");
            }
        }
        if(!CommonUtil.isEmpty(organization.getInTheEndTime())){
            sb.append("  最晚实做日期: "+organization.getInTheEndTime());
        }
        if(!CommonUtil.isEmpty(organization.getComputationalAccuracy())){
            sb.append("  计算精度: "+organization.getComputationalAccuracy());
        }
        if(!CommonUtil.isEmpty(organization.getPrecision())){
            sb.append("  填写精度: "+organization.getComputationalAccuracy());
        }
        if(!CommonUtil.isEmpty(organization.getComputationRule())){
            // 0：四舍五入 1：升角省分（精度为0） 2：去尾  3：进一
            if(organization.getComputationRule() == 0){
                sb.append("  计算规则: 四舍五入");
            }
            else if(organization.getComputationRule() == 1){
                sb.append("  计算规则: 升角省分");
            }
            else if(organization.getComputationRule() == 2){
                sb.append("  计算规则: 去尾");
            }
            else if(organization.getComputationRule() == 3){
                sb.append("  计算规则: 进一");
            }
        }
        if(!CommonUtil.isEmpty(organization.getMinCardinalNumber(),organization.getMaxCardinalNumber())){
            sb.append(" 基数范围："+organization.getMinCardinalNumber() +" - "+ organization.getMaxCardinalNumber());
        }
        logMapper.add(new Log(user.getId(),StatusConstant.LOG_MODEL_CONFIG,sb.toString(),StatusConstant.LOG_FLAG_UPDATE));

        organizationMapper.updateOrganization(organization);
    }

    @Transactional
    public void addOrganization(Organization organization, User user){
        organizationMapper.addOrganization(organization);
        logMapper.add(new Log(user.getId(), StatusConstant.LOG_MODEL_CONFIG,"新增缴金地："+organization.getOrganizationName(),StatusConstant.LOG_FLAG_ADD));
    }


}
