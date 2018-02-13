package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.CompanyMember;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

/**
 * Created by Eric Xie on 2017/10/24 0024.
 */
public interface ICompanyMemberMapper {


    @Insert("INSERT INTO company_member (memberId,companyId,createTime) VALUES (#{companyMember.memberId},#{companyMember.companyId},now())")
    Integer addCompanyMember(@Param("companyMember") CompanyMember companyMember);


}
