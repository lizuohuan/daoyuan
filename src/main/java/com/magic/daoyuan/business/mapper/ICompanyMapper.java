package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.BillAmountOfCompany;
import com.magic.daoyuan.business.entity.Company;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Eric Xie on 2017/9/13 0013.
 */
public interface ICompanyMapper {

    /**
     * 统计该时间段 公司的新增数 和 终止合作公司数
     * @param date
     * @return
     */
    Map<String,Object> countCompany(@Param("date") Date date);


    int countCompanyByMonth(@Param("date") Date date);

    /**
     * 获取所有公司的合作状态
     * @return
     */
    List<Company> queryCompanyCooperation();


    Company queryCompanyByName(@Param("companyName") String companyName);


    /**
     * 查询公司部分字段
     * @param limit
     * @param limitSize
     * @return
     */
    List<Company> queryOtherCompany(@Param("companyName") String companyName,@Param("limit") Integer limit,@Param("limitSize") Integer limitSize);

    int countOtherCompany(@Param("companyName") String companyName);

    /**
     * 获取所有公司的合作方式
     * @return
     */
    List<Company> queryAllCompanyCooperationMethod();

    /**
     * 批量统计 公司下的账单
     * @param companyIds
     * @return
     */
    List<BillAmountOfCompany> countBillAmountByCompany(@Param("companyIds") List<Integer> companyIds);

    /**
     * 通过 总账单ID 查询公司部分字段
     * @param companySonTotalBillId 总账单id
     * @return
     */
    Company queryCompanyByCompanySonTotalBillId(@Param("companySonTotalBillId") Integer companySonTotalBillId);

    /**
     * 查询所有公司部分字段
     * @return
     */
    List<Company> queryAllCompany();

    /**
     * 通过ID 获取公司详情
     * @param companyId
     * @return
     */
    Company queryCompanyById(@Param("companyId") Integer companyId);
    /**
     * 通过ID 获取公司详情
     * @param companyId
     * @return
     */
    Company queryCompanyById2(@Param("companyId") Integer companyId);


    /**
     * 通过IDs 获取公司列表详情
     * @param companyIds
     * @return
     */
    List<Company> queryCompanyByIds(@Param("companyIds") Integer[] companyIds);

    /**
     * 获取公司列表详情
     * @return
     */
    List<Company> queryCompanyAll();
    /**
     * 获取公司列表详情
     * @return
     */
    List<Company> queryCompanyAll2();

    Company info(@Param("id") Integer id);

    /**
     * 动态条件获取公司
     * @param params
     * @return
     */
    List<Company> queryCompanyByItems(Map<String,Object> params);

    /**
     * 动态条件统计公司
     * @param params
     * @return
     */
    Integer countCompanyByItems(Map<String,Object> params);

    /**
     * 新增公司
     * @param company
     * @return
     */
    Integer addCompany(Company company);

    /**
     * 修改公司信息
     * @param company
     * @return
     */
    Integer updateCompany(Company company);

    /**
     * 获取公司详情 和公司联系人
     * @param id
     * @return
     */
    Company infoAndContactsList(@Param("id") Integer id);

    /**
     * 通过帐号查询用户
     * @param companyName
     * @return
     */
    Company hrLogin(@Param("companyName") String companyName);


    /**
     * 动态条件获取公司
     * @param params
     * @return
     */
    List<Company> queryCompanyByItems2(Map<String,Object> params);

    /**
     * 动态条件统计公司
     * @param params
     * @return
     */
    Integer countCompanyByItems2(Map<String,Object> params);

    Company queryCompanyName(@Param("companyId") Integer companyId);
}
