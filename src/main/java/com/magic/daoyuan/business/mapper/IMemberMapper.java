package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.ExportUser;
import com.magic.daoyuan.business.entity.Member;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 员工信息 接口
 * @author lzh
 * @create 2017/9/26 20:54
 */
public interface IMemberMapper {



    /**
     * 通过 证件号集合 批量查询员工部分字段
     * @param idCards
     * @return
     */
    List<Member> queryMemberByIdCardList(List<String> idCards);

    /**
     * 批量查询员工所有字段
     * @param ids
     * @return
     */
    List<Member> batchQueryMemberAllField(List<Integer> ids);

    /**
     * 查询 实做记录待反馈的员工 只有员工ID
     * @param  memberId 如果为null，查询所有
     * @return
     */
    List<Member> queryFeeBack(@Param("memberId") Integer memberId);


    Member queryMemberByIdCards(@Param("idCard") String idCard);

    List<Member> batchQueryMember(List<Integer> ids);

    /**
     * 获取所有员工的身份证
     * @return
     */
    List<Member> queryAllMemberIdCard();

    List<ExportUser> queryExportUser(Map<String,Object> map);
    List<ExportUser> queryExportUser_(Map<String,Object> map);

    /**
     * 通过员工身份证 查询员工是否被注册
     * @param idCard
     * @return
     */
    List<Member> queryMemberByIdCard(@Param("idCard") String idCard);

    /**
     *  查询 增减变 待反馈的员工 核对结果
     * @param companyId 公司ID
     * @param serviceType 服务类型 0：社保  1：公积金
     * @param status 0：待反馈  1：成功  2：失败
     * @return
     */
    List<Member> queryVerifyMember(@Param("companyId") Integer companyId,@Param("serviceType") Integer serviceType,@Param("status") Integer status);

    /**
     * 获取 该公司下 拥有 一次性业务的员工， 包含员工选择的子险种集合
     * @param companyId 公司ID
     * @return
     */
    List<Member> queryMemberBusinessYc(@Param("companyId") Integer companyId);
    /**
     * 获取 该公司下 拥有 一次性业务的员工， 包含员工选择的子险种集合
     * @param companyIdSet 公司ID
     * @return
     */
    List<Member> queryMemberBusinessYc2(@Param("companyIdSet") Set<Integer> companyIdSet);


    /**
     * 获取 该公司下 拥有 商业险业务的员工，并且在当前时间内 没有生成过账单(没有缴过费)的员工 包含员工选择的子险种集合
     * @param companyId 公司ID
     * @param serviceDate 服务时间
     * @return
     */
    List<Member> queryMemberBusinessInsurance(@Param("companyId") Integer companyId, @Param("serviceDate") Date serviceDate);

    /**
     * 获取 该公司下 拥有 商业险业务的员工，并且在当前时间内 没有生成过账单(没有缴过费)的员工 包含员工选择的子险种集合
     * @param companyId 公司ID
     * @param serviceDate 服务时间
     * @return
     */
    List<Member> queryMemberBusinessInsurance2(@Param("companyDateMapList") List<Map<String ,Object>> companyDateMapList);

    /**
     * 批量修改员工基础信息
     * @param list
     * @return
     */
    Integer batchUpdateMember(@Param("list") List<Member> list);

    /**
     * 批量新增员工 并返回其主键
     * @param list
     * @return
     */
    Integer batchAddMember(List<Member> list);

    /**
     * 通过公司ID 查询员工的基础信息
     * @param companyId
     * @return
     */
    List<Member> queryMemberByCompany(@Param("companyId") Integer companyId);

    int delete(Integer id);

    int save(Member record);

    Member info(Integer id);

    int update(Member record);

    int updateAll(Member record);

    /**
     * 后台页面 分页 通过各种条件 查询供应商集合
     * @param map map
     * @return
     */
    List<Member> list(Map<String , Object> map);

    /**
     * 后台页面 统计 供应商数量
     * @param map map
     * @return
     */
    int listCount(Map<String , Object> map);

    /**
     * 根据子账单id获取人数
     * @param companySonBillId 子账单id
     * @param companyCooperationMethod 派遣方式
     * @return
     */
    int getCountByCompanySonBillId(@Param("companySonBillId")Integer companySonBillId ,
                                   @Param("companyCooperationMethod")Integer companyCooperationMethod);

    /**
     * 根据子账单id获取员工基础信息及公积金和社保业务
     * @param companySonBillId 子账单id
     * @param companyCooperationMethod 派遣方式
     * @return
     */
    List<Member> getMemberBusinessItem(@Param("companySonBillId")Integer companySonBillId ,
                                       @Param("companyCooperationMethod")Integer companyCooperationMethod);

    /**
     * 获取员工信息及员工绑定业务
     * @param companyId
     * @return
     */
    List<Member> getBusinessIds(@Param("companyId") Integer companyId);

    /**
     * 获取此公司下员工的基础信息
     * @param companyId
     * @return
     */
    List<Member> getBaseByCompanyId(@Param("companyId") Integer companyId);

    /***
     * 获取员工基础信息及增减变记录
     * @param companyId
     * @return
     */
    List<Member> getMemberBusinessUpdateRecordItem(@Param("companyId") Integer companyId);

    /**
     * 获取此公司下员工的基础信息及公积金社保信息
     * @param companyId
     * @return
     */
    List<Member> getByCompanyId(@Param("companyId") Integer companyId);

    /**
     * 获取此公司下的员工
     * @param companyId
     * @return
     */
    List<Member> getCompanyMember(@Param("companyId") Integer companyId);

    /**
     * 根据账单制作方式 获取员工
     * @param billMadeMethod 账单制作方式 0：预收  1：实做
     * @return
     */
    List<Member> getMemberByBillMadeMethod(@Param("billMadeMethod") Integer billMadeMethod);


    /**
     * 根据证件号 获取员工
     * @param idCards 证件号
     * @return
     */
    List<Member> getMemberByIdCards(@Param("idCards") Set<String> idCards ,@Param("dateSet") Set<Date> dateSet);

    /***
     * 根据公司id和时间集合 获取员工
     * @param companyIds
     * @param dateSet
     * @return
     */
    List<Member> getMemberByCompanyIdsAndDateSet(@Param("companyIds") Set<Integer> companyIds ,@Param("dateSet") Set<Date> dateSet);

    /**
     * 根据公司id和时间 获取员工
     * @param companyIds
     * @param date
     * @return
     */
    List<Member> getMemberByCompanyIdAndDate(@Param("companyId") Integer companyIds ,@Param("date") Date date);

    /**
     * 根据员工证件号集合获取公司id集合
     * @param idCards
     * @return
     */
    Set<Integer> getCompanyIds(@Param("idCards") Set<String> idCards);


    /**
     * 上传拷盘数据时使用 获取员工业务
     * @param idCards
     * @param businessId
     * @return
     */
    List<Member> getBusinessItem(@Param("idCards") Set<String> idCards,@Param("businessId") Integer businessId);
}
