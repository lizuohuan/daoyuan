package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.dto.CompanySonTotalBillDto;
import com.magic.daoyuan.business.dto.MemberAuditDto;
import com.magic.daoyuan.business.entity.CompanySonBillItem;
import com.magic.daoyuan.business.entity.Contacts;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 公司子账单子类
 * @author lzh
 * @create 2017/10/13 9:39
 */
public interface ICompanySonBillItemMapper {




    /**
     * 删除
     * @param id
     * @return
     */
    int delete(Integer id);

    /**
     * 添加批量
     * @param records
     * @return
     */
    int save(List<CompanySonBillItem> records);

    /**
     * 详情
     * @param id
     * @return
     */
    CompanySonBillItem info(@Param("id") Integer id);

    /**
     * 获取此子账单下在此创建时间中的总账单列表
     * @param companySonTotalBillId
     * @return
     */
    List<CompanySonBillItem> getByCompanySonBillIdAndCreateTime(@Param("companySonTotalBillId") Integer companySonTotalBillId,
                                      @Param("createTime") Date createTime);

    /**
     * 更新不为空的字段
     * @param record
     * @return
     */
    int update(CompanySonBillItem record);

    /**
     * 批量更新不为空的字段
     * @param records
     * @return
     */
    int updateList(@Param("records") List<CompanySonBillItem> records);


    /**
     * 后台页面 分页 通过各种条件 查询公司子账单子类
     * @param map map
     * @return
     */
    List<CompanySonBillItem> list(Map<String, Object> map);

    /**
     * 后台页面 统计 查询公司子账单子类数量
     * @param map map
     * @return
     */
    int listCount(Map<String, Object> map);

    /**
     * 根据公司id和服务月查询所有未合并的总账单
     * @param companyId
     * @return
     */
    List<CompanySonBillItem> listByCompanyIdAndServiceMonth(@Param("companyId") Integer companyId ,
                                                            @Param("serviceMonth") Date serviceMonth);
    /**
     * 根据公司id和服务月查询所有总账单的数量
     * @param companyId
     * @return
     */
    int getByCompanyIdAndServiceMonthCount(@Param("companyId") Integer companyId ,
                                           @Param("serviceMonth") Date serviceMonth);

    /**
     * 根据汇总账单删除月总账单
     * @param companySonTotalBillIds
     */
    void deleteByCompanySonTotalBillId(@Param("companySonTotalBillIds") Integer[] companySonTotalBillIds);

    /**
     * 更新稽核后的数据
     * @param companySonTotalBillIds
     */
    void updateList2(@Param("companySonTotalBillIds") Integer[] companySonTotalBillIds);

    /**
     * 更新为已稽核状态
     * @param billMont
     */
    void updateList3(@Param("billMonth") Date billMont,
                     @Param("companyId") Integer companyId,
                     @Param("companySonTotalBillId") Integer companySonTotalBillId);

    /**
     * 根据服务月查询所有未稽核账单 社保
     * @param serviceMonth
     * @param billMadeMethod  0：预收  1：实做
     * @return
     */
    List<CompanySonBillItem> getByServiceMonthSSI(@Param("serviceMonth") Set<Date> serviceMonth,
                                               @Param("billMadeMethod") Integer billMadeMethod,
                                               @Param("billMonth") Date billMonth);

    /**
     * 根据服务月查询所有未稽核账单 公积金
     * @param serviceMonth
     * @param billMadeMethod  0：预收  1：实做
     * @return
     */
    List<CompanySonBillItem> getByServiceMonthRFI(@Param("serviceMonth") Set<Date> serviceMonth,
                                               @Param("billMadeMethod") Integer billMadeMethod,
                                               @Param("billMonth") Date billMonth);

    /**
     * 根据服务月查询所有未稽核账单
     * @param serviceMonth
     * @param billMadeMethod  0：预收  1：实做
     * @return
     */
    List<CompanySonBillItem> getByServiceMonth(@Param("serviceMonth") Date serviceMonth,
                                               @Param("billMadeMethod") Integer billMadeMethod,
                                               @Param("companyIds") Set<Integer> companyIds,
                                               @Param("billMonth") Date billMonth);

    /**
     * 稽核列表
     * @return
     */
    List<MemberAuditDto> memberAuditDtoList(Map<String ,Object> map);

    /**
     * 统计 查稽核列表数量
     * @param map map
     * @return
     */
    int memberAuditDtoCount(Map<String , Object> map);

    /**
     * 获取多个公司下多个子账单下最后一条数据
     * @param companyIds 公司id数组
     * @param isAll 是否是查看全部公司  0：否  1：是
     * @return
     */
    List<CompanySonBillItem> listCompanyIdLast(@Param("companyIds") Integer[] companyIds,
                                               @Param("isAll") Integer isAll);
}
