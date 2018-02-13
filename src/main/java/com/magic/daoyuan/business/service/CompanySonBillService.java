package com.magic.daoyuan.business.service;

import com.magic.daoyuan.business.entity.CompanySonBill;
import com.magic.daoyuan.business.entity.PageArgs;
import com.magic.daoyuan.business.entity.PageList;
import com.magic.daoyuan.business.exception.InterfaceCommonException;
import com.magic.daoyuan.business.mapper.ICompanySonBillMapper;
import com.magic.daoyuan.business.mapper.IMemberBusinessMapper;
import com.magic.daoyuan.business.util.CommonUtil;
import com.magic.daoyuan.business.util.StatusConstant;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 公司子账单
 * @author lzh
 * @create 2017/10/13 10:08
 */
@Service
public class CompanySonBillService {

    @Resource
    private ICompanySonBillMapper companySonBillMapper;
    @Resource
    private IMemberBusinessMapper memberBusinessMapper;

    /**
     * 通过公司 获取 子账单列表
     * @param companyId
     * @return
     */
    public List<CompanySonBill> queryCompanySonBillByCompany(Integer companyId){
        return companySonBillMapper.queryCompanySonBillByCompany(companyId);
    }

    /**
     * 新增
     * @param companySonBill
     */
    public void save(CompanySonBill companySonBill) {
        companySonBillMapper.save(companySonBill);
    }

    /**
     * 更新不为空字段
     * @param companySonBill
     */
    public void update(CompanySonBill companySonBill) {
        companySonBillMapper.update(companySonBill);
    }


    /**
     * 逻辑删除子账单
     * @param id
     */
    public void updateIsValid(Integer id) {
        CompanySonBill companySonBill = new CompanySonBill();
        if (memberBusinessMapper.getByCompanySonBillId(id).size() > 0) {
            throw new InterfaceCommonException(StatusConstant.OBJECT_EXIST,"子账单已绑定员工业务，不能删除");
        }
        companySonBill.setId(id);
        companySonBill.setIsValid(0);
        companySonBillMapper.update(companySonBill);
    }


    /**
     * 详情
     * @param id
     * @return
     */
    public CompanySonBill info(Integer id) {
        return companySonBillMapper.info(id);
    }


    /**
     * 后台页面 分页获取公司子账单
     *
     * @param pageArgs    分页属性
     * @param companyId     公司id
     * @param companyName     公司名
     * @return
     */
    public PageList<CompanySonBill> list(PageArgs pageArgs , Integer companyId,String companyName,String sonBillName,
                                         Integer contactsId,Integer billInfoId ) {
        PageList<CompanySonBill> pageList = new PageList<CompanySonBill>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("companyId", companyId);
        map.put("companyName", companyName);
        map.put("sonBillName", CommonUtil.isEmpty(sonBillName) ? null : sonBillName);
        map.put("contactsId", contactsId);
        map.put("billInfoId", billInfoId);
        int count = companySonBillMapper.listCount(map);
        if (count > 0) {
            map.put("pageArgs", pageArgs);
            pageList.setList(companySonBillMapper.list(map));
        }
        pageList.setTotalSize(count);
        return pageList;
    }

}
