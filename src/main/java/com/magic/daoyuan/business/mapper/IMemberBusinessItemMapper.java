package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.MemberBusinessItem;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 员工业务 社保公积金表
 * @author lzh
 * @create 2017/10/11 11:01
 */
public interface IMemberBusinessItemMapper {


    List<MemberBusinessItem> queryMemberBusinessItem(List<Integer> memberIds);

    /**
     * 通过员工身份证 查询其他业务信息
     * @param memberIds
     * @return
     */
    List<MemberBusinessItem> queryMemberBusinessItemByMembers(List<String> memberIds);


    int save(@Param("records") List<MemberBusinessItem> records);

    /**
     * 通过 员工业务 查询业务下的 相关配置
     * @param businessMemberId
     * @return
     */
    MemberBusinessItem queryMemberBusinessItemByBusinessMember(@Param("businessMemberId") Integer businessMemberId,@Param("type") Integer type);

    MemberBusinessItem info(Integer id);

    int update(MemberBusinessItem record);

    /**
     * 批量更新
     * @param list
     */
    void updateList(List<MemberBusinessItem> list);

    Integer delMemberBusinessItemByMember(@Param("memberId") Integer memberId);

    Integer batchDelMemberBusinessItemByMember(List<Integer> memberIds);

    /**
     * 根据员工id获取员工的公积金或者社保业务
     * @param memberId
     * @return
     */
    List<MemberBusinessItem> listByMemberId(@Param("memberId") Integer memberId);

    /**
     * 还原第一次缴纳标识
     * @param companyId
     * @param month
     */
    void updateIsFirstPay(@Param("companyId") Integer companyId,@Param("month") Date month);
    /**
     * 还原第一次缴纳标识
     * @param companyId
     * @param month
     */
    void updateIsFirstPay2(@Param("companyDateMapList") List<Map<String ,Object>> companyDateMapList);
}
