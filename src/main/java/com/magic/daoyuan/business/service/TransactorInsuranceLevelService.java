package com.magic.daoyuan.business.service;

import com.magic.daoyuan.business.entity.PageArgs;
import com.magic.daoyuan.business.entity.PageList;
import com.magic.daoyuan.business.entity.TransactorInsuranceLevel;
import com.magic.daoyuan.business.mapper.ITransactorInsuranceLevelMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 办理方绑定的险种及档次
 * @author lzh
 * @create 2017/9/28 15:40
 */
@Service
public class TransactorInsuranceLevelService {

    @Resource
    private ITransactorInsuranceLevelMapper transactorInsuranceLevelMapper;

    /**
     * 新增
     * @param transactor
     */
    public void save(TransactorInsuranceLevel transactor) {

        transactorInsuranceLevelMapper.save(transactor);
    }

    /**
     * 更新不为空字段
     * @param transactor
     */
    public void update(TransactorInsuranceLevel transactor) {
        transactorInsuranceLevelMapper.update(transactor);
    }

    /**
     * 更新所有字段
     * @param transactor
     */
    public void updateAll(TransactorInsuranceLevel transactor) {
        transactorInsuranceLevelMapper.updateAll(transactor);
    }

    /**
     * 详情
     * @param id
     * @return
     */
    public TransactorInsuranceLevel info(Integer id) {
        return transactorInsuranceLevelMapper.info(id);
    }


    /**
     * 分页 通过各种条件 查询办理方绑定的险种及档次
     *
     * @param pageArgs    分页属性
     * @param levelName  险种档次名
     * @param insuranceName 险种名
     * @param coPayType 公司缴纳类型 0：金额  1：比例
     * @param mePayType 个人缴纳类型 0：金额  1：比例
     * @param isValid 是否有效 0 无效 1 有效
     * @param transactorId 办理方id
     * @return
     */
    public PageList<TransactorInsuranceLevel> list(PageArgs pageArgs , String levelName ,String insuranceName ,
                                     Integer coPayType ,Integer mePayType, Integer isValid,  Integer transactorId) {
        PageList<TransactorInsuranceLevel> pageList = new PageList<TransactorInsuranceLevel>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("levelName", levelName);
        map.put("insuranceName", insuranceName);
        map.put("coPayType", coPayType);
        map.put("mePayType", mePayType);
        map.put("isValid", isValid);
        map.put("transactorId", transactorId);
        int count = transactorInsuranceLevelMapper.listCount(map);
        if (count > 0) {
            map.put("pageArgs", pageArgs);
            pageList.setList(transactorInsuranceLevelMapper.list(map));
        }
        pageList.setTotalSize(count);
        return pageList;
    }
}
