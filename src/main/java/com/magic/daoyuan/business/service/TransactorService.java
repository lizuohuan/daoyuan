package com.magic.daoyuan.business.service;

import com.magic.daoyuan.business.entity.*;
import com.magic.daoyuan.business.mapper.ICompanyPayPlaceMapper;
import com.magic.daoyuan.business.mapper.ILogMapper;
import com.magic.daoyuan.business.mapper.ISupplierMapper;
import com.magic.daoyuan.business.mapper.ITransactorMapper;
import com.magic.daoyuan.business.util.StatusConstant;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 办理方
 * @author lzh
 * @create 2017/9/27 15:16
 */
@Service
public class TransactorService {

    @Resource
    private ITransactorMapper transactorMapper;
    @Resource
    private ILogMapper logMapper;
    @Resource
    private ISupplierMapper supplierMapper;
    @Resource
    private ICompanyPayPlaceMapper companyPayPlaceMapper;

    /**
     * 通过经办机构ID 查询办理方 部分字段
     * @param organizationId
     * @return
     */
    public List<Transactor> queryTransactorByOrganization(Integer organizationId){
        return transactorMapper.queryTransactorByOrganization(organizationId);
    }

    /**
     * 通过经办机构ID 查询办理方 部分字段
     * @param organizationId
     * @return
     */
    public List<Transactor> queryTransactorByOrganization2(Integer organizationId,String organizationName,Integer type){
        if(organizationName.indexOf("-托管配置") > 0){
            return companyPayPlaceMapper.queryTransactorByOrganization(organizationId, organizationName.split("-")[0], type);
        }
        return transactorMapper.queryTransactorByOrganization(organizationId);
    }

    /**
     * 新增
     * @param transactor
     */
    @Transactional
    public void save(Transactor transactor, User user) {
        transactorMapper.save(transactor);
        Supplier info = supplierMapper.info(transactor.getSupplierId());
        logMapper.add(new Log(user.getId(), StatusConstant.LOG_MODEL_CONFIG,"新增办理方:"+info.getSupplierName(),StatusConstant.LOG_FLAG_ADD));
    }

    /**
     * 更新不为空字段
     * @param transactor
     */
    @Transactional
    public void update(Transactor transactor,User user) {
        transactorMapper.update(transactor);
        Supplier info = supplierMapper.info(transactor.getSupplierId());
        StringBuffer sb = new StringBuffer("更新办理方："+info.getSupplierName());
        if(null != transactor.getOperationType()){
            // 0：本月  1：次月  2：上月
            if(transactor.getOperationType() == 0){
                sb.append(" 本月实做影响: 本月");
            }
            else if(transactor.getOperationType() == 1){
                sb.append(" 本月实做影响: 次月");
            }
            else if(transactor.getOperationType() == 2){
                sb.append(" 本月实做影响: 上月");
            }
        }
        if(null != transactor.getInTheEndTime()){
            sb.append(" 最晚实做日期: "+transactor.getInTheEndTime());
        }
        if(null != transactor.getRemindTime()){
            sb.append(" 提醒日期: "+transactor.getRemindTime());
        }
        if(null != transactor.getIsReceive()){
            if(0 == transactor.getIsReceive()){
                sb.append(" 账单类型: 预收");
            }
            if(1 == transactor.getIsReceive()){
                sb.append(" 账单类型: 实做");
            }
        }
        logMapper.add(new Log(user.getId(),StatusConstant.LOG_MODEL_CONFIG,sb.toString(),StatusConstant.LOG_FLAG_UPDATE));
    }

    /**
     * 更新所有字段
     * @param transactor
     */
    public void updateAll(Transactor transactor) {
        transactorMapper.updateAll(transactor);
    }

    /**
     * 详情
     * @param id
     * @return
     */
    public Transactor info(Integer id) {
        return transactorMapper.info(id);
    }


    /**
     * 后台页面 分页获取办理方
     *
     * @param pageArgs    分页属性
     * @param transactorName     办理方名称
     * @param operationType   操作方式  0：本月  1：次月  2：上月
     * @param isValid   是否有效 0 无效 1有效
     * @param organizationId   经办机构id
     * @return
     */
    public PageList<Transactor> list(PageArgs pageArgs , String transactorName ,
                                     Integer operationType , Integer isValid, Integer organizationId) {
        PageList<Transactor> pageList = new PageList<Transactor>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("transactorName", transactorName);
        map.put("operationType", operationType);
        map.put("isValid", isValid);
        map.put("organizationId", organizationId);
        int count = transactorMapper.listCount(map);
        if (count > 0) {
            map.put("pageArgs", pageArgs);
            pageList.setList(transactorMapper.list(map));
        }
        pageList.setTotalSize(count);
        return pageList;
    }
}
