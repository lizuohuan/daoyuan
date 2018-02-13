package com.magic.daoyuan.business.entity;

import com.magic.daoyuan.business.dto.MemberBusinessCityDto;
import com.magic.daoyuan.business.dto.MemberBusinessUpdateRecordItemDto;
import com.magic.daoyuan.business.dto.MemberMonthPayBusinessDto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 员工信息
 * @author lzh
 * @create 2017/9/26 20:45
 */
public class Member implements Serializable {

    /** 员工编号 */
    private Integer id;

    /** 员工名 */
    private String userName;

    /** 证件类型 */
    private Integer certificateType;

    /** 证件编号 */
    private String certificateNum;

    /** 所属公司id */
    private Integer companyId;

    /** 所属公司名 */
    private String companyName;

    /** 所属部门 */
    private String department;

    /** 学历 */
    private Integer education;

    /** 合作状态 0：离职  1：在职 */
    private Integer stateCooperation;


    private Integer memberBusinessUpdateRecordId;

    private Integer recordId;

    /** 创建时间 */
    private Date createTime;

    /** 离职时间 */
    private Date leaveOfficeTime;

    /** 合作方式 0：普通 1：派遣  2：外包 */
    private Integer waysOfCooperation;

    /** 地区id */
    private Integer cityId;

    /** 城市对象 */
    private City city;

    /** 地区名 */
    private String cityName;

    /** 合同执行时间 */
    private Date contractStartTime;

    /** 合同结束时间 */
    private Date contractEndTime;

    /** 员工业务 集合 */
    private List<Business> businessList;

    /** 员工工资信息 */
    private MemberSalary memberSalary;

    /** 电话号码 */
    private String phone;

    /********  业务字段 不进行数据库持久化    *******/

    /** 服务费 */
    private Double serviceFee;

    /** 公司险种 员工所属 */
    private List<CompanyInsurance> companyInsurances;

    /** 上传工资明细时 使用工资明细对象 */
    private SalaryInfo salaryInfo;

    /** 员工绑定的业务id 以逗号隔开 */
    private String businessIds;

    /** 员工业务公积金和社保 */
    private List<MemberBusinessItem> memberBusinessItems;

    /** 实做-员工业务增减变记录表 - 子类  记录具体的增减变操作记录 */
    private List<MemberBusinessUpdateRecordItem> recordItems;

    /** 实做-员工业务增减变记录表 - 子类  记录具体的增减变操作记录 dto */
    private List<MemberBusinessUpdateRecordItemDto> recordItemDtos;

    /** 实做-员工业务增减变记录表-父类 */
    private List<MemberBusinessUpdateRecord> updateRecordList;

    /** 员工每月缴纳了服务费的业务集合 */
    private List<MemberMonthPayBusinessDto> monthPayBusinessDtoList;

    /** 员工每月缴纳了服务费的业务明细集合 */
    private Set<MemberMonthPayBusiness> memberMonthPayBusinessList;

    /** 员工业务绑定的地区id集合 */
    private Set<MemberBusinessCityDto> memberBusinessCityDtoSet;

    /** 员工业务集合 */
    private Set<MemberBusiness> memberBusinessSet;

    /** 公积金 */
    private MemberBusinessItem reservedFundsMemberBusinessItem;

    /** 社保 */
    private MemberBusinessItem socialSecurityMemberBusinessItem;

    /** 获取 员工编号 */
    public Integer getId() {
        return this.id;
    }

    /** 设置 员工编号 */
    public void setId(Integer id) {
        this.id = id;
    }

    /** 获取 员工名 */
    public String getUserName() {
        return this.userName;
    }

    /** 设置 员工名 */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /** 获取 证件类型 */
    public Integer getCertificateType() {
        return this.certificateType;
    }

    /** 设置 证件类型 */
    public void setCertificateType(Integer certificateType) {
        this.certificateType = certificateType;
    }

    /** 获取 证件编号 */
    public String getCertificateNum() {
        return this.certificateNum;
    }

    /** 设置 证件编号 */
    public void setCertificateNum(String certificateNum) {
        this.certificateNum = certificateNum;
    }

    /** 获取 所属公司id */
    public Integer getCompanyId() {
        return this.companyId;
    }

    /** 设置 所属公司id */
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    /** 获取 所属公司名 */
    public String getCompanyName() {
        return this.companyName;
    }

    /** 设置 所属公司名 */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /** 获取 合作状态 0：离职  1：在职 */
    public Integer getStateCooperation() {
        return this.stateCooperation;
    }

    /** 设置 合作状态 0：离职  1：在职 */
    public void setStateCooperation(Integer stateCooperation) {
        this.stateCooperation = stateCooperation;
    }

    /** 获取 创建时间 */
    public Date getCreateTime() {
        return this.createTime;
    }

    /** 设置 创建时间 */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /** 获取 离职时间 */
    public Date getLeaveOfficeTime() {
        return this.leaveOfficeTime;
    }

    /** 设置 离职时间 */
    public void setLeaveOfficeTime(Date leaveOfficeTime) {
        this.leaveOfficeTime = leaveOfficeTime;
    }

    /** 获取 合作方式 0：普通 1：派遣  2：外包 */
    public Integer getWaysOfCooperation() {
        return this.waysOfCooperation;
    }

    /** 设置 合作方式 0：普通 1：派遣  2：外包 */
    public void setWaysOfCooperation(Integer waysOfCooperation) {
        this.waysOfCooperation = waysOfCooperation;
    }

    /** 获取 地区id */
    public Integer getCityId() {
        return this.cityId;
    }

    /** 设置 地区id */
    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    /** 获取 地区名 */
    public String getCityName() {
        return this.cityName;
    }

    /** 设置 地区名 */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    /** 获取 合同执行时间 */
    public Date getContractStartTime() {
        return this.contractStartTime;
    }

    /** 设置 合同执行时间 */
    public void setContractStartTime(Date contractStartTime) {
        this.contractStartTime = contractStartTime;
    }

    /** 获取 合同结束时间 */
    public Date getContractEndTime() {
        return this.contractEndTime;
    }

    /** 设置 合同结束时间 */
    public void setContractEndTime(Date contractEndTime) {
        this.contractEndTime = contractEndTime;
    }

    /** 获取 员工业务 集合 */
    public List<Business> getBusinessList() {
        return this.businessList;
    }

    /** 设置 员工业务 集合 */
    public void setBusinessList(List<Business> businessList) {
        this.businessList = businessList;
    }

    /** 获取 员工工资信息 */
    public MemberSalary getMemberSalary() {
        return this.memberSalary;
    }

    /** 设置 员工工资信息 */
    public void setMemberSalary(MemberSalary memberSalary) {
        this.memberSalary = memberSalary;
    }



    /** 获取 服务费 */
    public Double getServiceFee() {
        return this.serviceFee;
    }

    /** 设置 服务费 */
    public void setServiceFee(Double serviceFee) {
        this.serviceFee = serviceFee;
    }
    /** 获取 城市对象 */
    public City getCity() {
        return this.city;
    }

    /** 设置 城市对象 */
    public void setCity(City city) {
        this.city = city;
    }

    /** 获取 公司险种 员工所属 */
    public List<CompanyInsurance> getCompanyInsurances() {
        return this.companyInsurances;
    }

    /** 设置 公司险种 员工所属 */
    public void setCompanyInsurances(List<CompanyInsurance> companyInsurances) {
        this.companyInsurances = companyInsurances;
    }

    /** 获取 上传工资明细时 使用工资明细对象 */
    public SalaryInfo getSalaryInfo() {
        return this.salaryInfo;
    }

    /** 设置 上传工资明细时 使用工资明细对象 */
    public void setSalaryInfo(SalaryInfo salaryInfo) {
        this.salaryInfo = salaryInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Member member = (Member) o;

        return certificateNum.equals(member.certificateNum);

    }

    @Override
    public int hashCode() {
        return certificateNum.hashCode();
    }

    /** 获取 员工绑定的业务id 以逗号隔开 */
    public String getBusinessIds() {
        return this.businessIds;
    }

    /** 设置 员工绑定的业务id 以逗号隔开 */
    public void setBusinessIds(String businessIds) {
        this.businessIds = businessIds;
    }

    /** 获取 学历 */
    public Integer getEducation() {
        return this.education;
    }

    /** 设置 学历 */
    public void setEducation(Integer education) {
        this.education = education;
    }

    public Integer getMemberBusinessUpdateRecordId() {
        return this.memberBusinessUpdateRecordId;
    }

    public void setMemberBusinessUpdateRecordId(Integer memberBusinessUpdateRecordId) {
        this.memberBusinessUpdateRecordId = memberBusinessUpdateRecordId;
    }

    public Integer getRecordId() {
        return this.recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    /** 获取 电话号码 */
    public String getPhone() {
        return this.phone;
    }

    /** 设置 电话号码 */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /** 获取 实做-员工业务增减变记录表 - 子类  记录具体的增减变操作记录 */
    public List<MemberBusinessUpdateRecordItem> getRecordItems() {
        if (null == recordItems) {
            return new ArrayList<MemberBusinessUpdateRecordItem>();
        }
        return this.recordItems;
    }

    /** 设置 实做-员工业务增减变记录表 - 子类  记录具体的增减变操作记录 */
    public void setRecordItems(List<MemberBusinessUpdateRecordItem> recordItems) {
        this.recordItems = recordItems;
    }

    /** 获取 员工业务公积金和社保 */
    public List<MemberBusinessItem> getMemberBusinessItems() {
        return this.memberBusinessItems;
    }

    /** 设置 员工业务公积金和社保 */
    public void setMemberBusinessItems(List<MemberBusinessItem> memberBusinessItems) {
        this.memberBusinessItems = memberBusinessItems;
    }

    /** 获取 员工每月缴纳了服务费的业务集合 */
    public List<MemberMonthPayBusinessDto> getMonthPayBusinessDtoList() {
        return this.monthPayBusinessDtoList;
    }

    /** 设置 员工每月缴纳了服务费的业务集合 */
    public void setMonthPayBusinessDtoList(List<MemberMonthPayBusinessDto> monthPayBusinessDtoList) {
        this.monthPayBusinessDtoList = monthPayBusinessDtoList;
    }

    /** 获取 员工每月缴纳了服务费的业务明细集合 */
    public Set<MemberMonthPayBusiness> getMemberMonthPayBusinessList() {
        return this.memberMonthPayBusinessList;
    }

    /** 设置 员工每月缴纳了服务费的业务明细集合 */
    public void setMemberMonthPayBusinessList(Set<MemberMonthPayBusiness> memberMonthPayBusinessList) {
        this.memberMonthPayBusinessList = memberMonthPayBusinessList;
    }

    /** 获取 员工业务绑定的地区id集合 */
    public Set<MemberBusinessCityDto> getMemberBusinessCityDtoSet() {
        return this.memberBusinessCityDtoSet;
    }

    /** 设置 员工业务绑定的地区id集合 */
    public void setMemberBusinessCityDtoSet(Set<MemberBusinessCityDto> memberBusinessCityDtoSet) {
        this.memberBusinessCityDtoSet = memberBusinessCityDtoSet;
    }

    public List<MemberBusinessUpdateRecord> getUpdateRecordList() {
        return this.updateRecordList;
    }

    public void setUpdateRecordList(List<MemberBusinessUpdateRecord> updateRecordList) {
        this.updateRecordList = updateRecordList;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    /** 获取 员工业务集合 */
    public Set<MemberBusiness> getMemberBusinessSet() {
        return this.memberBusinessSet;
    }

    /** 设置 员工业务集合 */
    public void setMemberBusinessSet(Set<MemberBusiness> memberBusinessSet) {
        this.memberBusinessSet = memberBusinessSet;
    }

    /** 获取 公积金 */
    public MemberBusinessItem getReservedFundsMemberBusinessItem() {
        return this.reservedFundsMemberBusinessItem;
    }

    /** 设置 公积金 */
    public void setReservedFundsMemberBusinessItem(MemberBusinessItem reservedFundsMemberBusinessItem) {
        this.reservedFundsMemberBusinessItem = reservedFundsMemberBusinessItem;
    }

    /** 获取 社保 */
    public MemberBusinessItem getSocialSecurityMemberBusinessItem() {
        return this.socialSecurityMemberBusinessItem;
    }

    /** 设置 社保 */
    public void setSocialSecurityMemberBusinessItem(MemberBusinessItem socialSecurityMemberBusinessItem) {
        this.socialSecurityMemberBusinessItem = socialSecurityMemberBusinessItem;
    }

    /** 获取 实做-员工业务增减变记录表 - 子类  记录具体的增减变操作记录 */
    public List<MemberBusinessUpdateRecordItemDto> getRecordItemDtos() {
        return this.recordItemDtos;
    }

    /** 设置 实做-员工业务增减变记录表 - 子类  记录具体的增减变操作记录 */
    public void setRecordItemDtos(List<MemberBusinessUpdateRecordItemDto> recordItemDtos) {
        this.recordItemDtos = recordItemDtos;
    }
}
