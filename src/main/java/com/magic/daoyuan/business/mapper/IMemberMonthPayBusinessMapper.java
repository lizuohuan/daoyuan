package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.MemberMonthPayBusiness;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 员工每月缴纳了服务费的业务
 * @author lzh
 * @create 2017/11/24 11:42
 */
public interface IMemberMonthPayBusinessMapper {

    /**
     * 批量员工每月缴纳了服务费的业务
     * @param list
     */
    void saveList(List<MemberMonthPayBusiness> list);

    /**
     * 删除此公司此账单生成月不为拷盘的数据
     * @param billMonth
     * @param companyId
     */
    void delList(@Param("billMonth") Date billMonth,@Param("companyId") Integer companyId);

    void delList2(@Param("companyDateMapList") List<Map<String ,Object>> companyDateMapList);

}
