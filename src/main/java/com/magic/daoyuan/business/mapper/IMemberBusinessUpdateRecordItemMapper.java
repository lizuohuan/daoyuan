package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.MemberBusinessUpdateRecordItem;
import com.magic.daoyuan.business.vo.CommonTransact;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * 实做-员工业务增减变记录表 - 子类  记录具体的增减变操作记录
 * @author lzh
 * @create 2017/10/25 19:56
 */
public interface IMemberBusinessUpdateRecordItemMapper {


    /**
     * 通过记录ID 查询 记录下所有待申请的子类记录
     * @param ids
     * @return
     */
    List<MemberBusinessUpdateRecordItem> queryRecordItemByRecords(@Param("ids") Integer[] ids);


    /**
     * 获取有效的待申请前的数据
     * @param recordId
     * @return
     */
    List<MemberBusinessUpdateRecordItem> queryValidRecordItem(@Param("recordId") Integer recordId);


    List<MemberBusinessUpdateRecordItem> queryMemberBusinessUpdateRecordItem(@Param("recordId") Integer recordId);

    List<CommonTransact> queryCommonTransactByItem(Map<String,Object> map);

    /**
     * 批量新增
     * @param record
     * @return
     */
    int save(List<MemberBusinessUpdateRecordItem> record);

    /**
     * 详情
     * @param id
     * @return
     */
    MemberBusinessUpdateRecordItem info(@Param("id") Integer id);

    /**
     * 更新
     * @param record
     * @return
     */
    int update(MemberBusinessUpdateRecordItem record);

    /**
     * 批量更新
     * @param records
     * @return
     */
    int updateList(List<MemberBusinessUpdateRecordItem> records);

    /**
     * 根据公司id和账单月回滚异动量
     * @param companyId
     * @param billMonth
     * @return
     */
    int update2(@Param("companyId") Integer companyId,@Param("billMonth") Date billMonth);

    /**
     * 根据公司id和账单月回滚异动量
     * @param companyId
     * @param billMonth
     * @return
     */
    int update2ListMap(@Param("companyDateMapList") List<Map<String ,Object>> companyDateMapList);


    int batchUpdateStatus(List<MemberBusinessUpdateRecordItem> records);

    /**
     * 后台页面 分页 通过各种条件 查询员工业务增减变记录集合
     * @param map map
     * @return
     */
    List<MemberBusinessUpdateRecordItem> list(Map<String , Object> map);

    /**
     * 后台页面 统计 员工业务增减变记录数量
     * @param map map
     * @return
     */
    int listCount(Map<String , Object> map);

    /**
     * 全部申请
     */
    void allApplyFor();

    /**
     * 根据状态获取实作员工
     * @param status  0：待申请 1：待反馈  2：成功  3: 失败 4: 失效
     * @param date  提醒日期和实作日期 1-31天
     * @return
     */
    List<MemberBusinessUpdateRecordItem> getUpdateRecordStatus(@Param("status") Integer status,
                                                               @Param("date") Integer date);
}
