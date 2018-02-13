package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.MemberBusinessUpdateRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 实做-员工业务增减变表
 * @author lzh
 * @create 2017/10/25 15:14
 */
public interface IMemberBusinessUpdateRecordMapper {

    /**
     * 获取有效记录列表，用作数据对比用
     * @param ids
     * @return
     */
    List<MemberBusinessUpdateRecord> queryValidRecord(Set<Integer> ids);

    MemberBusinessUpdateRecord queryBaseInfo(@Param("id") Integer id);

    /**
     * 批量新增
     * @param record
     * @return
     */
    int save(List<MemberBusinessUpdateRecord> record);

    int add(MemberBusinessUpdateRecord record);


    /**
     * 详情
     * @param id
     * @return
     */
    MemberBusinessUpdateRecord info(@Param("id") Integer id);

    /**
     * 更新
     * @param record
     * @return
     */
    int update(MemberBusinessUpdateRecord record);

    /**
     * 批量更新
     * @param records
     */
    void updateList(List<MemberBusinessUpdateRecord> records);

    /**
     * 后台页面 分页 通过各种条件 查询员工业务增减变集合
     * @param map map
     * @return
     */
    List<MemberBusinessUpdateRecord> list(Map<String , Object> map);

    /**
     * 后台页面 统计 员工业务增减变数量
     * @param map map
     * @return
     */
    int listCount(Map<String , Object> map);

    /**
     * 获取员工增减变
     * @param memberId
     * @return
     */
    List<MemberBusinessUpdateRecord> getMemberUpdateList(@Param("memberId") Integer memberId);
    /**
     * 获取员工增减变
     * @param memberIds
     * @return
     */
    List<MemberBusinessUpdateRecord> getMemberUpdateListByMembers(List<Integer> memberIds);

    /**
     * 根据ids 获取增减变及记录
     * @param ids
     * @return
     */
    List<MemberBusinessUpdateRecord> getRecordAndItemByIds(@Param("ids") Integer[] ids);

    /**
     * 全部申请
     */
    void allApplyFor();
}
