package com.magic.daoyuan.business.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Eric Xie on 2017/9/13 0013.
 */
public class Company  implements Serializable {

    private Integer id;

    private Integer isValid;

    /** 公司名称 */
    private String companyName;

    /** 公司编号 */
    private String serialNumber;

    /** 到款日 */
    private Date payTime;

    /** 行业ID */
    private Integer tradeId;

    /** 行业名字 */
    private String tradeName;

    /** 财务编号 */
    private String financialNum;

    /** 是否先票 后款 0 ：否  1：是 */
    private Integer isFirstBill;

    /** 票据类型  0：专票 1：普票  2：收据 */
    private Integer billType;

    /** 业务月序开始时间 */
    private Date businessStartTime;

    /** 业务月序周期  月 */
    private Integer businessCycle;

    /** 服务费月序开始时间 */
    private Date serviceFeeStartTime;

    /** 服务费月序 周期 */
    private Integer serviceFeeCycle;

    /** 服务费最低收费 */
    private Double serviceFeeMin;

    /** 服务费最高收费 */
    private Double serviceFeeMax;

    /** 服务费配置ID */
    private Integer serviceFeeConfigId;

    /** 服务费是否纳入百分比计算 0 否  1 是 */
    private Integer isPercent;

    /** 如果纳入百分比计算 则百分比数值 */
    private Double percent;

    /** 业务集合 */
    private List<Business> businesses;

    /** 如果票据类型为 专票 则选择上传 纳税人证明的文档 */
    private String billTypeImg;

    /** 营业执照正本 */
    private String license;

    /** 营业执照 副本 */
    private String licenseTranscript;

    /** 一般纳税人 证明 */
    private String taxpayerProve;

    /** 稳岗补贴情况 */
    private String subsidyProve;

    /** 其他情况 说明 */
    private String otherProve;

    /** 创建时间 */
    private Date createTime;

    /** 票据信息集合 */
    private List<CompanyBillInfo> billInfos;

    /** 服务费配置集合 */
    private List<CompanyServiceFee> feeList;

    /** 合同集合 */
    private List<Contract> contractList;

    /** 最新进度 */
    private String schedule;

    /** 列表展示服务费 */
    private String serviceFee;

    /** 合同开始日期 */
    private Date contractStartTime;

    /** 客服ID */
    private Integer beforeService;

    /** 客服名字 */
    private String beforeServiceName;

    /** 销售ID */
    private Integer sales;

    /** 销售名字 */
    private String salesName;


    /** 工资发放日期 */
    private List<SalaryDate> salaryDateList;

    /** 合作状态 0 空户  1 合作 */
    private Integer cooperationStatus;

    /** 关联客户公司 */
    private Integer relevanceCompanyId;

    /** 推荐客户公司 */
    private Integer recommendCompanyId;

    /** 当服务类别为  按服务类别 收费时，收费集合 */
    private List<BusinessServiceFee> serviceFeeList;

    /** 是否生成 账单数据 0 否  1 是 */
    private Integer isShowBuildBtn;

    /** 是否同行客户  0：否  1：是 */
    private Integer isPeer;

    /** hr端登录密码 */
    private String pwd;


    /*************  业务字段 不进行数据库持久化  ***************/

    /** 当服务配置是按服务区域收费时，此字段为 服务区域配置集合 */
    private List<CompanyServicePayPlace> servicePayPlaceList;

    /** 公司下缴金地集合 */
    private List<PayPlace> payPlaces;

    private List<Date> dateList;

    /** 是否显示社保配置按钮  0：否 1：是*/
    private Integer isShowSocialSecurity = 0;

    /** 是否显示 公积金配置按钮 0：否 1：是 */
    private Integer isShowAccumulationFund = 0;

    /** 合作方式 */
    private List<CompanyCooperationMethod> cooperationMethodList;

    /** 公司业务集合 */
    private List<CompanyBusiness> companyBusinessList;

    /** 是否服务费 账单数据 0 否  1 是 */
    private Integer isCanServceFree;

    /** 服务费集合 */
    private List<MonthServiceFee> monthServiceFeeList;

    /** 汇总账单集合 */
    private List<CompanySonTotalBill> companySonTotalBillList;

    /** 公司子账单集合 */
    private List<CompanySonBill> companySonBillList;

    /** 员工集合 */
    private List<Member> memberList;

    /** 公司联系人集合 */
    private List<Contacts> contactsList;

    /** 服务费配置集合 */
    List<CompanyCooperationMethod> methods;

    /** 获取 公司名称 */
    public String getCompanyName() {
        return this.companyName;
    }

    /** 设置 公司名称 */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /** 获取 公司编号 */
    public String getSerialNumber() {
        return this.serialNumber;
    }

    /** 设置 公司编号 */
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    /** 获取 到款日 */
    public Date getPayTime() {
        return this.payTime;
    }

    /** 设置 到款日 */
    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    /** 获取 行业ID */
    public Integer getTradeId() {
        return this.tradeId;
    }

    /** 设置 行业ID */
    public void setTradeId(Integer tradeId) {
        this.tradeId = tradeId;
    }

    /** 获取 行业名字 */
    public String getTradeName() {
        return this.tradeName;
    }

    /** 设置 行业名字 */
    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    /** 获取 财务编号 */
    public String getFinancialNum() {
        return this.financialNum;
    }

    /** 设置 财务编号 */
    public void setFinancialNum(String financialNum) {
        this.financialNum = financialNum;
    }

    /** 获取 是否先票 后款 0 ：否  1：是 */
    public Integer getIsFirstBill() {
        return this.isFirstBill;
    }

    /** 设置 是否先票 后款 0 ：否  1：是 */
    public void setIsFirstBill(Integer isFirstBill) {
        this.isFirstBill = isFirstBill;
    }

    /** 获取 票据类型  0：专票 1：普票  2：收据 */
    public Integer getBillType() {
        return this.billType;
    }

    /** 设置 票据类型  0：专票 1：普票  2：收据 */
    public void setBillType(Integer billType) {
        this.billType = billType;
    }

    /** 获取 业务月序开始时间 */
    public Date getBusinessStartTime() {
        return this.businessStartTime;
    }

    /** 设置 业务月序开始时间 */
    public void setBusinessStartTime(Date businessStartTime) {
        this.businessStartTime = businessStartTime;
    }

    /** 获取 业务月序周期  月 */
    public Integer getBusinessCycle() {
        return this.businessCycle;
    }

    /** 设置 业务月序周期  月 */
    public void setBusinessCycle(Integer businessCycle) {
        this.businessCycle = businessCycle;
    }

    /** 获取 服务费月序开始时间 */
    public Date getServiceFeeStartTime() {
        return this.serviceFeeStartTime;
    }

    /** 设置 服务费月序开始时间 */
    public void setServiceFeeStartTime(Date serviceFeeStartTime) {
        this.serviceFeeStartTime = serviceFeeStartTime;
    }

    /** 获取 服务费月序 周期 */
    public Integer getServiceFeeCycle() {
        return this.serviceFeeCycle;
    }

    /** 设置 服务费月序 周期 */
    public void setServiceFeeCycle(Integer serviceFeeCycle) {
        this.serviceFeeCycle = serviceFeeCycle;
    }

    /** 获取 服务费最低收费 */
    public Double getServiceFeeMin() {
        return this.serviceFeeMin;
    }

    /** 设置 服务费最低收费 */
    public void setServiceFeeMin(Double serviceFeeMin) {
        this.serviceFeeMin = serviceFeeMin;
    }

    /** 获取 服务费最高收费 */
    public Double getServiceFeeMax() {
        return this.serviceFeeMax;
    }

    /** 设置 服务费最高收费 */
    public void setServiceFeeMax(Double serviceFeeMax) {
        this.serviceFeeMax = serviceFeeMax;
    }

    /** 获取 服务费配置ID */
    public Integer getServiceFeeConfigId() {
        return this.serviceFeeConfigId;
    }

    /** 设置 服务费配置ID */
    public void setServiceFeeConfigId(Integer serviceFeeConfigId) {
        this.serviceFeeConfigId = serviceFeeConfigId;
    }

    /** 获取 业务集合 */
    public List<Business> getBusinesses() {
        return this.businesses;
    }

    /** 设置 业务集合 */
    public void setBusinesses(List<Business> businesses) {
        this.businesses = businesses;
    }

    /** 获取 如果票据类型为 专票 则选择上传 纳税人证明的文档 */
    public String getBillTypeImg() {
        return this.billTypeImg;
    }

    /** 设置 如果票据类型为 专票 则选择上传 纳税人证明的文档 */
    public void setBillTypeImg(String billTypeImg) {
        this.billTypeImg = billTypeImg;
    }

    /** 获取 营业执照正本 */
    public String getLicense() {
        return this.license;
    }

    /** 设置 营业执照正本 */
    public void setLicense(String license) {
        this.license = license;
    }

    /** 获取 营业执照 副本 */
    public String getLicenseTranscript() {
        return this.licenseTranscript;
    }

    /** 设置 营业执照 副本 */
    public void setLicenseTranscript(String licenseTranscript) {
        this.licenseTranscript = licenseTranscript;
    }

    /** 获取 一般纳税人 证明 */
    public String getTaxpayerProve() {
        return this.taxpayerProve;
    }

    /** 设置 一般纳税人 证明 */
    public void setTaxpayerProve(String taxpayerProve) {
        this.taxpayerProve = taxpayerProve;
    }

    /** 获取 稳岗补贴情况 */
    public String getSubsidyProve() {
        return this.subsidyProve;
    }

    /** 设置 稳岗补贴情况 */
    public void setSubsidyProve(String subsidyProve) {
        this.subsidyProve = subsidyProve;
    }

    /** 获取 其他情况 说明 */
    public String getOtherProve() {
        return this.otherProve;
    }

    /** 设置 其他情况 说明 */
    public void setOtherProve(String otherProve) {
        this.otherProve = otherProve;
    }

    /** 获取 创建时间 */
    public Date getCreateTime() {
        return this.createTime;
    }

    /** 设置 创建时间 */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /** 获取 票据信息集合 */
    public List<CompanyBillInfo> getBillInfos() {
        return this.billInfos;
    }

    /** 设置 票据信息集合 */
    public void setBillInfos(List<CompanyBillInfo> billInfos) {
        this.billInfos = billInfos;
    }

    /** 获取 服务费配置集合 */
    public List<CompanyServiceFee> getFeeList() {
        return this.feeList;
    }

    /** 设置 服务费配置集合 */
    public void setFeeList(List<CompanyServiceFee> feeList) {
        this.feeList = feeList;
    }

    /** 获取 合同集合 */
    public List<Contract> getContractList() {
        return this.contractList;
    }

    /** 设置 合同集合 */
    public void setContractList(List<Contract> contractList) {
        this.contractList = contractList;
    }

    /** 获取 最新进度 */
    public String getSchedule() {
        return this.schedule;
    }

    /** 设置 最新进度 */
    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    /** 获取 列表展示服务费 */
    public String getServiceFee() {
        return this.serviceFee;
    }

    /** 设置 列表展示服务费 */
    public void setServiceFee(String serviceFee) {
        this.serviceFee = serviceFee;
    }

    /** 获取 合同开始日期 */
    public Date getContractStartTime() {
        return this.contractStartTime;
    }

    /** 设置 合同开始日期 */
    public void setContractStartTime(Date contractStartTime) {
        this.contractStartTime = contractStartTime;
    }

    /** 获取 客服ID */
    public Integer getBeforeService() {
        return this.beforeService;
    }

    /** 设置 客服ID */
    public void setBeforeService(Integer beforeService) {
        this.beforeService = beforeService;
    }

    /** 获取 客服名字 */
    public String getBeforeServiceName() {
        return this.beforeServiceName;
    }

    /** 设置 客服名字 */
    public void setBeforeServiceName(String beforeServiceName) {
        this.beforeServiceName = beforeServiceName;
    }

    /** 获取 销售ID */
    public Integer getSales() {
        return this.sales;
    }

    /** 设置 销售ID */
    public void setSales(Integer sales) {
        this.sales = sales;
    }

    /** 获取 销售名字 */
    public String getSalesName() {
        return this.salesName;
    }

    /** 设置 销售名字 */
    public void setSalesName(String salesName) {
        this.salesName = salesName;
    }

    /** 获取 工资发放日期 */
    public List<SalaryDate> getSalaryDateList() {
        return this.salaryDateList;
    }

    /** 设置 工资发放日期 */
    public void setSalaryDateList(List<SalaryDate> salaryDateList) {
        this.salaryDateList = salaryDateList;
    }

    /** 获取 合作状态 0 空户  1 合作，2 终止 */
    public Integer getCooperationStatus() {
        return this.cooperationStatus;
    }

    /** 设置 合作状态 0 空户  1 合作，2 终止 */
    public void setCooperationStatus(Integer cooperationStatus) {
        this.cooperationStatus = cooperationStatus;
    }

    /** 获取 关联客户公司 */
    public Integer getRelevanceCompanyId() {
        return this.relevanceCompanyId;
    }

    /** 设置 关联客户公司 */
    public void setRelevanceCompanyId(Integer relevanceCompanyId) {
        this.relevanceCompanyId = relevanceCompanyId;
    }

    /** 获取 推荐客户公司 */
    public Integer getRecommendCompanyId() {
        return this.recommendCompanyId;
    }

    /** 设置 推荐客户公司 */
    public void setRecommendCompanyId(Integer recommendCompanyId) {
        this.recommendCompanyId = recommendCompanyId;
    }

    /** 获取 当服务类别为  按服务类别 收费时，收费集合 */
    public List<BusinessServiceFee> getServiceFeeList() {
        return this.serviceFeeList;
    }

    /** 设置 当服务类别为  按服务类别 收费时，收费集合 */
    public void setServiceFeeList(List<BusinessServiceFee> serviceFeeList) {
        this.serviceFeeList = serviceFeeList;
    }

    /** 获取 是否生成 账单数据 0 否  1 是 */
    public Integer getIsShowBuildBtn() {
        return this.isShowBuildBtn;
    }

    /** 设置 是否生成 账单数据 0 否  1 是 */
    public void setIsShowBuildBtn(Integer isShowBuildBtn) {
        this.isShowBuildBtn = isShowBuildBtn;
    }

    /** 获取 是否同行客户  0：否  1：是 */
    public Integer getIsPeer() {
        return this.isPeer;
    }

    /** 设置 是否同行客户  0：否  1：是 */
    public void setIsPeer(Integer isPeer) {
        this.isPeer = isPeer;
    }

    /** 获取 公司下缴金地集合 */
    public List<PayPlace> getPayPlaces() {
        return this.payPlaces;
    }

    /** 设置 公司下缴金地集合 */
    public void setPayPlaces(List<PayPlace> payPlaces) {
        this.payPlaces = payPlaces;
    }

    /** 获取 是否显示社保配置按钮  0：否 1：是*/
    public Integer getIsShowSocialSecurity() {
        return this.isShowSocialSecurity;
    }

    /** 设置 是否显示社保配置按钮  0：否 1：是*/
    public void setIsShowSocialSecurity(Integer isShowSocialSecurity) {
        this.isShowSocialSecurity = isShowSocialSecurity;
    }

    /** 获取 是否显示 公积金配置按钮 0：否 1：是 */
    public Integer getIsShowAccumulationFund() {
        return this.isShowAccumulationFund;
    }

    /** 设置 是否显示 公积金配置按钮 0：否 1：是 */
    public void setIsShowAccumulationFund(Integer isShowAccumulationFund) {
        this.isShowAccumulationFund = isShowAccumulationFund;
    }

    /** 获取 当服务配置是按服务区域收费时，此字段为 服务区域配置集合 */
    public List<CompanyServicePayPlace> getServicePayPlaceList() {
        return this.servicePayPlaceList;
    }

    /** 设置 当服务配置是按服务区域收费时，此字段为 服务区域配置集合 */
    public void setServicePayPlaceList(List<CompanyServicePayPlace> servicePayPlaceList) {
        this.servicePayPlaceList = servicePayPlaceList;
    }

    /** 获取 是否服务费 账单数据 0 否  1 是 */
    public Integer getIsCanServceFree() {
        return this.isCanServceFree;
    }

    /** 设置 是否服务费 账单数据 0 否  1 是 */
    public void setIsCanServceFree(Integer isCanServceFree) {
        this.isCanServceFree = isCanServceFree;
    }

    /** 获取 合作方式 */
    public List<CompanyCooperationMethod> getCooperationMethodList() {
        return this.cooperationMethodList;
    }

    /** 设置 合作方式 */
    public void setCooperationMethodList(List<CompanyCooperationMethod> cooperationMethodList) {
        this.cooperationMethodList = cooperationMethodList;
    }

    /** 获取 服务费是否纳入百分比计算 0 否  1 是 */
    public Integer getIsPercent() {
        return this.isPercent;
    }

    /** 设置 服务费是否纳入百分比计算 0 否  1 是 */
    public void setIsPercent(Integer isPercent) {
        this.isPercent = isPercent;
    }

    /** 获取 如果纳入百分比计算 则百分比数值 */
    public Double getPercent() {
        return this.percent;
    }

    /** 设置 如果纳入百分比计算 则百分比数值 */
    public void setPercent(Double percent) {
        this.percent = percent;
    }

    public List<Date> getDateList() {
        return this.dateList;
    }

    public void setDateList(List<Date> dateList) {
        this.dateList = dateList;
    }

    /** 获取 公司业务集合 */
    public List<CompanyBusiness> getCompanyBusinessList() {
        return this.companyBusinessList;
    }

    /** 设置 公司业务集合 */
    public void setCompanyBusinessList(List<CompanyBusiness> companyBusinessList) {
        this.companyBusinessList = companyBusinessList;
    }

    /** 获取 服务费集合 */
    public List<MonthServiceFee> getMonthServiceFeeList() {
        return this.monthServiceFeeList;
    }

    /** 设置 服务费集合 */
    public void setMonthServiceFeeList(List<MonthServiceFee> monthServiceFeeList) {
        this.monthServiceFeeList = monthServiceFeeList;
    }

    /** 获取 汇总账单集合 */
    public List<CompanySonTotalBill> getCompanySonTotalBillList() {
        return this.companySonTotalBillList;
    }

    /** 设置 汇总账单集合 */
    public void setCompanySonTotalBillList(List<CompanySonTotalBill> companySonTotalBillList) {
        this.companySonTotalBillList = companySonTotalBillList;
    }

    /** 获取 公司子账单集合 */
    public List<CompanySonBill> getCompanySonBillList() {
        return this.companySonBillList;
    }

    /** 设置 公司子账单集合 */
    public void setCompanySonBillList(List<CompanySonBill> companySonBillList) {
        this.companySonBillList = companySonBillList;
    }

    /** 获取 员工集合 */
    public List<Member> getMemberList() {
        return this.memberList;
    }

    /** 设置 员工集合 */
    public void setMemberList(List<Member> memberList) {
        this.memberList = memberList;
    }

    /** 获取 公司联系人集合 */
    public List<Contacts> getContactsList() {
        return this.contactsList;
    }

    /** 设置 公司联系人集合 */
    public void setContactsList(List<Contacts> contactsList) {
        this.contactsList = contactsList;
    }

    /** 获取 hr端登录密码 */
    public String getPwd() {
        return this.pwd;
    }

    /** 设置 hr端登录密码 */
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIsValid() {
        return this.isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    /** 获取 服务费配置集合 */
    public List<CompanyCooperationMethod> getMethods() {
        return this.methods;
    }

    /** 设置 服务费配置集合 */
    public void setMethods(List<CompanyCooperationMethod> methods) {
        this.methods = methods;
    }
}
