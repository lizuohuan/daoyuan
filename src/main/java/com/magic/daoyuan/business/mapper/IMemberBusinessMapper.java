package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.MemberBusiness;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 员工 与 业务
 * Created by Eric Xie on 2017/10/12 0012.
 */
public interface IMemberBusinessMapper {

    /**
     * 查公司下所有员工的 一次性险业务数据，删除
     * @param companyIds
     * @return
     */
    List<MemberBusiness> queryMemberBusinessForDel(List<Integer> companyIds);

    int statisticsMemberBusiness(@Param("companyId") Integer companyId,@Param("businessIds") List<Integer> businessIds);

    List<MemberBusiness> queryMemberBusinessByMember(List<Integer> memberIds);

    Integer batchDel(List<MemberBusiness> memberBusinessList);

    Integer addMemberBusiness(MemberBusiness memberBusiness);


    Integer batchAddMemberBusiness(List<MemberBusiness> memberBusinesses);


    Integer delMemberBusiness(@Param("memberId") Integer memberId);

    /**
     * 批量删除员工的社保、公积金业务
     * @param memberIds
     * @return
     */
    Integer batDelMemberBusiness(List<Integer> memberIds);

    Integer del(List<Integer> ids);

    /**
     * 获取员工信息及业务信息
     * @return
     */
    List<MemberBusiness> getByCompanySonBillId(@Param("companySonBillId") Integer companySonBillId);
}
