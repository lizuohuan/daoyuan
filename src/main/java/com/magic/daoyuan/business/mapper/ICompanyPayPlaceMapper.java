package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.CompanyPayPlace;
import com.magic.daoyuan.business.entity.Organization;
import com.magic.daoyuan.business.entity.Transactor;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 公司绑定缴金地
 * @author lzh
 * @create 2017/10/10 14:46
 */
public interface ICompanyPayPlaceMapper {

    List<Transactor> queryTransactorByOrganization(@Param("organizationId") Integer organizationId,
                                                   @Param("organizationName") String organizationName,@Param("type") Integer type);

    List<Organization> queryOrganizationByPayPlace(@Param("payPlaceId") Integer payPlaceId);

    List<CompanyPayPlace> queryAlCompanyPayPlace();

    List<CompanyPayPlace> queryCompanyPayPlaceByItems(@Param("companyId") Integer companyId,
                                                      @Param("type") Integer type);

    /**
     * 添加
     * @param record
     * @return
     */
    int save(CompanyPayPlace record);

    /**
     * 详情
     * @param id
     * @return
     */
    CompanyPayPlace info(@Param("id") Integer id);


    /**
     * 更新不为空的字段
     * @param record
     * @return
     */
    int update(CompanyPayPlace record);

    /**
     * 后台页面 分页 通过各种条件 查询公司缴金地
     * @param map map
     * @return
     */
    List<CompanyPayPlace> list(Map<String , Object> map);

    /**
     * 后台页面 统计 查询办理方数量
     * @param map map
     * @return
     */
    int listCount(Map<String , Object> map);


    /**
     * 是否已经存在
     * @param companyId
     * @return
     */
    CompanyPayPlace isHave(@Param("companyId") Integer companyId,
                           @Param("transactorName") String transactorName,
                           @Param("organizationName") String organizationName,
                           @Param("payPlaceId") Integer payPlaceId,
                           @Param("organizationId") Integer organizationId,
                           @Param("id") Integer id,
                           @Param("type") Integer type);


    int judgeIsExistOrganization(@Param("companyId") Integer companyId,@Param("type") Integer type,
                                 @Param("organizationName") String organizationName,@Param("organizationId") Integer organizationId);
}
