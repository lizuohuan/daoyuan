package com.magic.daoyuan.business.service;

import com.magic.daoyuan.business.entity.*;
import com.magic.daoyuan.business.enums.*;
import com.magic.daoyuan.business.exception.InterfaceCommonException;
import com.magic.daoyuan.business.mapper.*;
import com.magic.daoyuan.business.util.*;
import com.magic.daoyuan.business.vo.CommonTransact;
import org.apache.xmlbeans.impl.jam.mutable.MElement;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 导入表格业务处理
 * Created by Eric Xie on 2017/10/26 0026.
 */
@Service
public class ImportExcelService {

    @Resource
    private IConfirmMoneyRecordMapper confirmMoneyRecordMapper;
    @Resource
    private IConfirmMoneyCompanyMapper confirmMoneyCompanyMapper;
    @Resource
    private ICompanyMapper companyMapper;
    @Resource
    private IConfirmFundMapper confirmFundMapper;
    @Resource
    private IConfirmFundTotalBillMapper confirmFundTotalBillMapper;
    @Resource
    private IMemberMapper memberMapper;
    @Resource
    private ICityMapper cityMapper;
    @Resource
    private ICompanyBusinessMapper companyBusinessMapper;
    @Resource
    private IPayPlaceMapper payPlaceMapper;
    @Resource
    private IOrganizationMapper organizationMapper;
    @Resource
    private ITransactorMapper transactorMapper;
    @Resource
    private IInsuranceLevelMapper iInsuranceLevelMapper;
    @Resource
    private IPayTheWayMapper payTheWayMapper;
    @Resource
    private ICompanyPayPlaceMapper companyPayPlaceMapper;
    @Resource
    private ICompanySonBillMapper companySonBillMapper;
    @Resource
    private IBusinessItemMapper businessItemMapper;
    @Resource
    private IMemberBusinessMapper memberBusinessMapper;
    @Resource
    private IMemberBusinessItemMapper memberBusinessItemMapper;
    @Resource
    private IMemberBusinessOtherItemMapper memberBusinessOtherItemMapper;
    @Resource
    private IMemberBusinessUpdateRecordMapper memberBusinessUpdateRecordMapper;
    @Resource
    private IMemberBusinessUpdateRecordItemMapper memberBusinessUpdateRecordItemMapper;
    @Resource
    private IMemberBaseChangeMapper memberBaseChangeMapper;
    @Resource
    private ICompanySonTotalBillMapper companySonTotalBillMapper;
    @Resource
    private IMemberCountMapper memberCountMapper;
    @Resource
    private MakeBillHelper makeBillHelper;
    @Resource
    private IMakeBillMapper makeBillMapper;
    @Resource
    private CompanySonTotalBillService companySonTotalBillService;

    /**
     * 导入 变更员工信息
     * @param targetUrl
     */
    public void importChangeMember(String targetUrl) throws Exception{

        if (CommonUtil.isEmpty(targetUrl)) {
            return;
        }
        int HttpResult; // 服务器返回的状态
        URL url = new URL(targetUrl); // 创建URL
        URLConnection urlConn = url.openConnection();
        urlConn.connect();
        HttpURLConnection httpConn = (HttpURLConnection) urlConn;
        HttpResult = httpConn.getResponseCode();
        if (HttpResult != HttpURLConnection.HTTP_OK)
            throw new InterfaceCommonException(StatusConstant.Fail_CODE, "数据读取失败");
        else {
            List<Member> importMemberList = new ArrayList<Member>();
            List<Integer> memberIds = new ArrayList<Integer>();

            Map<Integer, List<String>> map = ExcelReader.readExcelContent(urlConn.getInputStream(), 2,"xls",null);
            // 获取所有员工的身份证号
            List<Member> memberList = memberMapper.queryAllMemberIdCard();
            List<City> cities = cityMapper.queryCityByLevelImport(2); // 所有市级城市
            // 所有的缴金地
            List<PayPlace> payPlaces = payPlaceMapper.queryAllPayPlaceForImport();
            // 所有的经办机构
            List<Organization> organizations = organizationMapper.queryAllOrganization();
            //所有的办理方
            List<Transactor> transactors = transactorMapper.queryAllTransactor();
            // 所有的档次集合
            List<InsuranceLevel> insuranceLevels = iInsuranceLevelMapper.queryAllInsuranceLevel();
            // 所有档次配置
            List<PayTheWay> payTheWays = payTheWayMapper.queryPayTheWayByLevel(null);
            // 所有公司的缴金地配置
            List<CompanyPayPlace> companyPayPlaces = companyPayPlaceMapper.queryAlCompanyPayPlace();
            // 所有的子账单
            List<CompanySonBill> companySonBills = companySonBillMapper.queryCompanySonBillByCompany(null);
            // 所有公司的业务
            List<CompanyBusiness> companyBusinesses = companyBusinessMapper.queryCompanyBusiness();
            List<Company> allCompany = companyMapper.queryAllCompanyCooperationMethod();
            int index = 3;
            for (Integer cellNum : map.keySet()) {

                List<String> values = map.get(cellNum);
                Member member = new Member();

                List<Business> businessList = new ArrayList<Business>(); // 员工业务集合

                if(CommonUtil.isEmpty(values.get(0))){
                    throw new InterfaceCommonException(StatusConstant.FIELD_NOT_NULL, "第"+index+"行公司名不能为空");
                }
                Integer company = getCompany(allCompany, values.get(0));
                if(null == company){
                    throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第"+index+"行公司不存在");
                }
                member.setCompanyId(company);
                member.setDepartment(values.get(1));
                if(CommonUtil.isEmpty(values.get(2))){
                    throw new InterfaceCommonException(StatusConstant.FIELD_NOT_NULL, "第"+index+"行姓名不能为空");
                }
                member.setUserName(values.get(2));
                if(CommonUtil.isEmpty(values.get(3))){
                    throw new InterfaceCommonException(StatusConstant.FIELD_NOT_NULL, "第"+index+"行证件类型不能为空");
                }
                if("身份证".equals(values.get(3))){
                    member.setCertificateType(IdType.identityCard.ordinal());
                }
                else if("护照".equals(values.get(3))){
                    member.setCertificateType(IdType.passport.ordinal());
                }
                else if("通行证".equals(values.get(3))){
                    member.setCertificateType(IdType.passCard.ordinal());
                }
                else{
                    throw new InterfaceCommonException(StatusConstant.FIELD_NOT_NULL, "第"+index+"行证件类型填写错误");
                }
                if(CommonUtil.isEmpty(values.get(4))){
                    throw new InterfaceCommonException(StatusConstant.FIELD_NOT_NULL, "第"+index+"行证件号不能为空");
                }
                String s = getStringNumber(values.get(4));
                if(IdType.identityCard.ordinal() == member.getCertificateType() && !CommonUtil.isIdCard(s)){
                    throw new InterfaceCommonException(StatusConstant.FIELD_NOT_NULL, "第"+index+"行证件号格式错误");
                }

                Integer memberId = getMemberId(memberList, s);
                if(null == memberId){
                    throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第"+index+"行证件号不存在");
                }
                member.setCertificateNum(s);
                member.setId(memberId);
                member.setPhone(getStringNumber(values.get(5)));
                memberIds.add(memberId);
                if(CommonUtil.isEmpty(values.get(6))){
                    throw new InterfaceCommonException(StatusConstant.FIELD_NOT_NULL, "第"+index+"行城市不能为空");
                }
                Integer city = getCity(cities, values.get(6));
                if(null == city){
                    throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第"+index+"行城市不存在");
                }
                member.setCityId(city);
                if(CommonUtil.isEmpty(values.get(7))){
                    throw new InterfaceCommonException(StatusConstant.FIELD_NOT_NULL, "第"+index+"行学历不能为空");
                }
                Integer education = getEducation(values.get(7));
                if(null == education){
                    throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第"+index+"行学历填写错误");
                }
                member.setEducation(education);
                if(CommonUtil.isEmpty(values.get(8))){
                    throw new InterfaceCommonException(StatusConstant.FIELD_NOT_NULL, "第"+index+"行合作方式不能为空");
                }
                Integer method = getCompanyCooperationMethod(allCompany, company, values.get(8));
                if(null == method){
                    throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第"+index+"行合作方式填写有误");
                }
                member.setWaysOfCooperation(method);
                if(1 == method || 2 == method){
                    if(CommonUtil.isEmpty(values.get(9),values.get(10))){
                        throw new InterfaceCommonException(StatusConstant.FIELD_NOT_NULL, "第"+index+"行合同时间不能为空");
                    }
                    member.setContractStartTime(Timestamp.parseDate2(values.get(9),"yyyy-MM-dd"));
                    member.setContractEndTime(Timestamp.parseDate2(values.get(10),"yyyy-MM-dd"));
                }
                // 封装社保业务-开始


                if(!CommonUtil.isEmpty(values.get(11))){
                    if (!checkCompanyBusiness(companyBusinesses, member.getCompanyId(), BusinessEnum.sheBao.ordinal())) {
                        throw new InterfaceCommonException(StatusConstant.Fail_CODE, "第" + index + "行数据该公司没有社保业务");
                    }
                    Business ssBusiness = new Business();
                    ssBusiness.setId(BusinessEnum.sheBao.ordinal());


                    MemberBusinessItem item = new MemberBusinessItem();
                    item.setType(InsuranceType.SheBao.ordinal());
                    if("代理".equals(values.get(11))){
                        item.setServeMethod(0);
                        // 是否纳入应收
                        if ("是".equals(values.get(12))) {
                            item.setIsReceivable(1);
                        } else {
                            throw new InterfaceCommonException(StatusConstant.Fail_CODE, "第" + index + "行数据的是否纳入应收填写错误");
                        }
                        if (CommonUtil.isEmpty(values.get(13))) {
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + index + "行数据的缴金地不能为空");
                        }
                        Integer payPlaceId = getPayPlace(payPlaces, values.get(13), InsuranceType.SheBao.ordinal());
                        if (null == payPlaceId) {
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + index + "行数据的缴金地不存在");
                        }
                        item.setPayPlaceId(payPlaceId);
                        if (CommonUtil.isEmpty(values.get(14))) {
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + index + "行数据的经办机构不能为空");
                        }
                        Integer organization = getOrganization(organizations, values.get(14), item.getPayPlaceId());
                        if (null == organization) {
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + index + "行数据的经办机构不存在");
                        }
                        item.setOrganizationId(organization);
                        if (CommonUtil.isEmpty(values.get(15))) {
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + index + "行数据的办理方不能为空");
                        }
                        Integer transactor = getTransactor(transactors, values.get(15), item.getOrganizationId());
                        if (null == transactor) {
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + index + "行数据的办理方不存在");
                        }
                        item.setTransactorId(transactor);
                    }
                    else if("托管".equals(values.get(11))){

                        item.setServeMethod(1);
                        // 是否纳入应收
                        if ("是".equals(values.get(12))) {
                            item.setIsReceivable(1);
                        }else if("否".equals(values.get(12))){
                            item.setIsReceivable(0);
                        } else {
                            throw new InterfaceCommonException(StatusConstant.Fail_CODE, "第" + index + "行数据的是否纳入应收填写错误");
                        }
                        if (CommonUtil.isEmpty(values.get(13))) {
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + index + "行数据的缴金地不能为空");
                        }
                        Integer payPlaceId = getPayPlace(payPlaces, values.get(13), InsuranceType.SheBao.ordinal());
                        if (null == payPlaceId) {
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + index + "行数据的缴金地不存在");
                        }

                        if (CommonUtil.isEmpty(values.get(14))) {
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + index + "行数据的经办机构不能为空");
                        }
                        if (CommonUtil.isEmpty(values.get(15))) {
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + index + "行数据的办理方不能为空");
                        }
                        Integer companyPayPlaceId = getCompanyPayPlace(companyPayPlaces, payPlaceId, null, values.get(15),
                                member.getCompanyId(),InsuranceType.SheBao.ordinal(),values.get(14));
                        if(null == companyPayPlaceId){
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + index + "行数据的办理方不存在");
                        }
                        item.setPayPlaceId(companyPayPlaceId);
                    }
                    else {
                        throw new InterfaceCommonException(StatusConstant.Fail_CODE, "第" + index + "行数据的社保服务方式填写错误");
                    }
                    // 封装公共部分
                    if (CommonUtil.isEmpty(values.get(16))) {
                        throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + index + "行数据的档次不能为空");
                    }
                    Integer insuranceLevel = getInsuranceLevel(insuranceLevels, values.get(16), getPayPlace(payPlaces, values.get(13), InsuranceType.SheBao.ordinal()));
                    if (null == insuranceLevel) {
                        throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + index + "行数据的档次不存在");
                    }
                    item.setInsuranceLevelId(insuranceLevel);
                    Map<String, Object> ways = getPayTheWays(payTheWays, item.getInsuranceLevelId());
                    if(null == ways){
                        throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + index + "行数据的档次下不存在基数");
                    }
                    double min = (Double) ways.get("min");
                    double max = (Double) ways.get("max");



                    if(CommonUtil.isEmpty(values.get(17))){
                        throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + index + "行数据的基数类型不能为空");
                    }




                    String baseType = values.get(17);
                    if("最低基数".equals(baseType)){
                        item.setBaseType(0);
                    }
                    else if("最高基数".equals(baseType)){
                        item.setBaseType(1);
                    }
                    else if("基数填写".equals(baseType)){
                        if(CommonUtil.isEmpty(values.get(18))){
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + index + "行数据的基数类型不能为空");
                        }
                        Double baseNumber = Double.valueOf(values.get(18));
                        if(baseNumber < min || baseNumber > max){
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + index + "行数据的社保基数填写错误");
                        }
                        item.setBaseType(2);
                        item.setBaseNumber(baseNumber);
                    }
                    else{
                        throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + index + "行数据的社保基数类型填写错误");
                    }


                    if(CommonUtil.isEmpty(values.get(19))){
                        throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + index + "行数据的服务起始月不能为空");
                    }
                    if(values.get(19).indexOf("-") < 0){
                        throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + index + "行数据的服务起始月格式错误");
                    }
                    String[] split = values.get(19).split("-");
                    if(split.length < 1){
                        throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + index + "行数据的服务起始月格式错误");
                    }
                    Date serviceStartTime = DateUtil.setDate(DateUtil.setDate(new Date(), TimeField.Year.ordinal(), Integer.valueOf(split[0])), TimeField.Month.ordinal(), Integer.valueOf(split[1]));
                    item.setServiceStartTime(serviceStartTime);
                    if(!CommonUtil.isEmpty(values.get(20))){
                        if(CommonUtil.isEmpty(values.get(20))){
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + index + "行数据的服务结束月不能为空");
                        }
                        if(values.get(20).indexOf("-") < 0){
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + index + "行数据的服务结束月格式错误");
                        }
                        String[] split_ = values.get(20).split("-");
                        if(split_.length < 1){
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + index + "行数据的服务结束月格式错误");
                        }
                        Date serviceEndTime = DateUtil.setDate(DateUtil.setDate(new Date(), TimeField.Year.ordinal(), Integer.valueOf(split_[0])), TimeField.Month.ordinal(), Integer.valueOf(split_[1]));
                        item.setServiceEndTime(serviceEndTime);
                    }
                    if(CommonUtil.isEmpty(values.get(21))){
                        throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + index + "行数据的账单起始月不能为空");
                    }
                    if(values.get(21).indexOf("-") < 0){
                        throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + index + "行数据的账单起始月格式错误");
                    }
                    String[] split__ = values.get(21).split("-");
                    if(split__.length < 1){
                        throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + index + "行数据的账单起始月格式错误");
                    }
                    Date billStartTime = DateUtil.setDate(DateUtil.setDate(new Date(), TimeField.Year.ordinal(), Integer.valueOf(split__[0])), TimeField.Month.ordinal(), Integer.valueOf(split__[1]));
                    item.setBillStartTime(billStartTime);
                    // 设置子账单
                    if(CommonUtil.isEmpty(values.get(22))){
                        throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + index + "行数据的子账单不能为空");
                    }
                    Integer sonBill = getSonBill(companySonBills, values.get(22), member.getCompanyId());
                    if(null == sonBill){
                        throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + index + "行数据的子账单不存在");
                    }
                    ssBusiness.setCompanySonBillId(sonBill);
                    item.setIsFirstPay(1);
                    ssBusiness.setMemberBusinessItem(item);
                    businessList.add(ssBusiness);
                }
                // 封装社保业务-结束


                // 封装公积金业务 - 开始
                if(!CommonUtil.isEmpty(values.get(23))){
                    if (!checkCompanyBusiness(companyBusinesses, member.getCompanyId(), BusinessEnum.gongJiJin.ordinal())) {
                        throw new InterfaceCommonException(StatusConstant.Fail_CODE, "第" + index + "行数据该公司没有公积金业务");
                    }
                    Business rfBusiness = new Business();
                    rfBusiness.setId(BusinessEnum.gongJiJin.ordinal());


                    MemberBusinessItem item = new MemberBusinessItem();
                    item.setType(InsuranceType.GongJiJin.ordinal());
                    if("代理".equals(values.get(23))){
                        item.setServeMethod(0);
                        // 是否纳入应收
                        if ("是".equals(values.get(24))) {
                            item.setIsReceivable(1);
                        } else {
                            throw new InterfaceCommonException(StatusConstant.Fail_CODE, "第" + index + "行数据的是否纳入应收填写错误");
                        }
                        if (CommonUtil.isEmpty(values.get(25))) {
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + index + "行数据的缴金地不能为空");
                        }
                        Integer payPlaceId = getPayPlace(payPlaces, values.get(25), InsuranceType.GongJiJin.ordinal());
                        if (null == payPlaceId) {
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + index + "行数据的缴金地不存在");
                        }
                        item.setPayPlaceId(payPlaceId);
                        if (CommonUtil.isEmpty(values.get(26))) {
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + index + "行数据的经办机构不能为空");
                        }
                        Integer organization = getOrganization(organizations, values.get(26), item.getPayPlaceId());
                        if (null == organization) {
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + index + "行数据的经办机构不存在");
                        }
                        item.setOrganizationId(organization);
                        if (CommonUtil.isEmpty(values.get(27))) {
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + index + "行数据的办理方不能为空");
                        }
                        Integer transactor = getTransactor(transactors, values.get(27), item.getOrganizationId());
                        if (null == transactor) {
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + index + "行数据的办理方不存在");
                        }
                        item.setTransactorId(transactor);
                    }
                    else if("托管".equals(values.get(23))){

                        item.setServeMethod(1);
                        // 是否纳入应收
                        if ("是".equals(values.get(24))) {
                            item.setIsReceivable(1);
                        }else if("否".equals(values.get(24))){
                            item.setIsReceivable(0);
                        } else {
                            throw new InterfaceCommonException(StatusConstant.Fail_CODE, "第" + index + "行数据的是否纳入应收填写错误");
                        }
                        if (CommonUtil.isEmpty(values.get(25))) {
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + index + "行数据的缴金地不能为空");
                        }
                        Integer payPlaceId = getPayPlace(payPlaces, values.get(25), InsuranceType.GongJiJin.ordinal());
                        if (null == payPlaceId) {
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + index + "行数据的缴金地不存在");
                        }

                        if (CommonUtil.isEmpty(values.get(26))) {
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + index + "行数据的经办机构不能为空");
                        }
                        Integer organization = getOrganization(organizations, values.get(26), payPlaceId);
                        if (null == organization) {
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + index + "行数据的公积金经办机构不存在");
                        }
                        item.setOrganizationId(organization);

                        if (CommonUtil.isEmpty(values.get(27))) {
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + index + "行数据的办理方不能为空");
                        }
                        Integer companyPayPlace = getCompanyPayPlace(companyPayPlaces, payPlaceId, organization, values.get(27), member.getCompanyId(),
                                InsuranceType.GongJiJin.ordinal(),null);
                        item.setPayPlaceId(companyPayPlace);
                    }
                    else {
                        throw new InterfaceCommonException(StatusConstant.Fail_CODE, "第" + index + "行数据的公积金服务方式填写错误");
                    }
                    // 封装公共部分
                    if (CommonUtil.isEmpty(values.get(28))) {
                        throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + index + "行数据的公积金比例不能为空");
                    }
                    // 判断比例是否在之间
//                    if(!judgeRation(organizations, item.getOrganizationId(), Double.valueOf(values.get(28)))){
////                        throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + index + "行数据的公积金比例填写有误");
////                    }

                    // 判断比例是否在之间
                    if(item.getServeMethod() == 0 && !judgeRation(organizations, item.getOrganizationId(), Double.valueOf(values.get(28)))){
                        throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + index + "行数据的公积金比例填写有误");
                    }
                    if(item.getServeMethod() == 1){
                        // 验证比例
                        CompanyPayPlace place = getCompanyPayPlace(companyPayPlaces, item.getPayPlaceId());
                        if(null == place){
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + index + "行数据的公积金缴金地办理方数据有误");
                        }
                        else{
                            if(!Double.valueOf(values.get(28)).equals(place.getMePayPrice())){
                                throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + index + "行数据的公积金比例填写有误");
                            }
                        }
                    }

                    item.setRatio(Double.valueOf(values.get(28)));
                    if(CommonUtil.isEmpty(values.get(29))){
                        throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + index + "行数据的公积金基数不能为空");
                    }
                    Map<String, Object> result = getBaseNumber(organizations, item.getOrganizationId());
                    if(null == result){
                        throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + index + "行数据的公积金基数填写错误");
                    }
                    double baseNumber = Double.valueOf(values.get(29));
                    Double min = (Double) result.get("min");
                    Double max = (Double) result.get("max");
//                    if(baseNumber == min){
//                        item.setBaseType(0);
//                    }
//                    else if(baseNumber == max){
//                        item.setBaseType(1);
//                    }
//                    else{
//                        if(baseNumber < min || baseNumber > max){
//                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + index + "行数据的公积金基数填写错误");
//                        }
//                        item.setBaseType(2);
//                        item.setBaseNumber(baseNumber);
//                    }
                    if(baseNumber < min || baseNumber > max){
                        throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + index + "行数据的公积金基数填写错误");
                    }
                    item.setBaseType(2);
                    item.setBaseNumber(baseNumber);
                    if(CommonUtil.isEmpty(values.get(30))){
                        throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + index + "行数据的公积金服务起始月不能为空");
                    }
                    if(values.get(30).indexOf("-") < 0){
                        throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + index + "行数据的公积金服务起始月格式错误");
                    }
                    String[] split = values.get(30).split("-");
                    if(split.length < 1){
                        throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + index + "行数据的公积金服务起始月格式错误");
                    }
                    Date serviceStartTime = DateUtil.setDate(DateUtil.setDate(new Date(), TimeField.Year.ordinal(), Integer.valueOf(split[0])), TimeField.Month.ordinal(), Integer.valueOf(split[1]));
                    item.setServiceStartTime(serviceStartTime);
                    if(!CommonUtil.isEmpty(values.get(31))){
                        if(CommonUtil.isEmpty(values.get(31))){
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + index + "行数据的公积金服务结束月不能为空");
                        }
                        if(values.get(31).indexOf("-") < 0){
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + index + "行数据的公积金服务结束月格式错误");
                        }
                        String[] split_ = values.get(31).split("-");
                        if(split_.length < 1){
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + index + "行数据的公积金服务结束月格式错误");
                        }
                        Date serviceEndTime = DateUtil.setDate(DateUtil.setDate(new Date(), TimeField.Year.ordinal(), Integer.valueOf(split_[0])), TimeField.Month.ordinal(), Integer.valueOf(split_[1]));
                        item.setServiceEndTime(serviceEndTime);
                    }
                    if(CommonUtil.isEmpty(values.get(32))){
                        throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + index + "行数据的公积金账单起始月不能为空");
                    }
                    if(values.get(32).indexOf("-") < 0){
                        throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + index + "行数据的公积金账单起始月格式错误");
                    }
                    String[] split__ = values.get(32).split("-");
                    if(split__.length < 1){
                        throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + index + "行数据的公积金账单起始月格式错误");
                    }
                    Date billStartTime = DateUtil.setDate(DateUtil.setDate(new Date(), TimeField.Year.ordinal(), Integer.valueOf(split__[0])), TimeField.Month.ordinal(), Integer.valueOf(split__[1]));
                    item.setBillStartTime(billStartTime);
                    // 设置子账单
                    if(CommonUtil.isEmpty(values.get(33))){
                        throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + index + "行数据的公积金子账单不能为空");
                    }
                    Integer sonBill = getSonBill(companySonBills, values.get(33), member.getCompanyId());
                    if(null == sonBill){
                        throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + index + "行数据的公积金子账单不存在");
                    }
                    rfBusiness.setCompanySonBillId(sonBill);
                    item.setIsFirstPay(1);
                    rfBusiness.setMemberBusinessItem(item);
                    businessList.add(rfBusiness);
                }
                // 封装公积金业务 - 结束
                member.setBusinessList(businessList);
//                if(businessList.size() > 0){
//                    member.setStateCooperation(1);
//                }else{
//                    member.setStateCooperation(0);
//                }
                importMemberList.add(member);
                index++;
            }

            // 更新操作
            if(importMemberList.size() > 0){
                // 获取更新前的数据
                List<Member> oldMembers = memberMapper.batchQueryMemberAllField(memberIds);

                // 更新员工信息
                memberMapper.batchUpdateMember(importMemberList);
                // 批量删除员工公积金、社保信息
                memberBusinessItemMapper.batchDelMemberBusinessItemByMember(memberIds);
                // 删除员工业务
                memberBusinessMapper.batDelMemberBusiness(memberIds);
                // 新增现有业务
                List<MemberBusiness> businessList = new ArrayList<MemberBusiness>();
                for (Member member : importMemberList) {
                    for (Business business : member.getBusinessList()) {
                        MemberBusiness mb = new MemberBusiness();
                        mb.setMemberId(member.getId());
                        mb.setBusinessId(business.getId());
                        mb.setMemberBusinessItem(business.getMemberBusinessItem());
                        mb.setCompanySonBillId(business.getCompanySonBillId());
                        businessList.add(mb);
                    }
                }
                if (businessList.size() > 0) {
                    // 新增业务
                    memberBusinessMapper.batchAddMemberBusiness(businessList);
                    List<MemberBusinessItem> itemList = new ArrayList<MemberBusinessItem>();
                    for (MemberBusiness business : businessList) {
                        business.getMemberBusinessItem().setMemberBusinessId(business.getId());
                        itemList.add(business.getMemberBusinessItem());
                    }
                    if(itemList.size() > 0){
                        memberBusinessItemMapper.save(itemList);
                    }
                }
                // 查询这些员工的业务，如果业务为空，则自动更新为离职
                List<Member> newMembers = memberMapper.batchQueryMemberAllField(memberIds);
                List<Member> waitUpdate = new ArrayList<Member>();
                for (Member member : newMembers) {
                    if(null == member.getBusinessList() || member.getBusinessList().size() == 0){
                        Member temp = new Member();
                        temp.setId(member.getId());
                        temp.setStateCooperation(0);
                        waitUpdate.add(temp);
                    }
                    else{
                        Member temp = new Member();
                        temp.setId(member.getId());
                        temp.setStateCooperation(1);
                        waitUpdate.add(temp);
                    }
                }
                if(waitUpdate.size() > 0){
                    memberMapper.batchUpdateMember(waitUpdate);
                }
                // 业务处理完毕，处理增减变开始
                if(null != oldMembers){
                    List<MemberBusinessUpdateRecordItem> itemList = new ArrayList<MemberBusinessUpdateRecordItem>();
                    List<MemberBusinessUpdateRecordItem> itemListUpdate = new ArrayList<MemberBusinessUpdateRecordItem>();
                    List<MemberBusinessUpdateRecord> records = new ArrayList<MemberBusinessUpdateRecord>();
                    for (Member oldMember : oldMembers) {
                        // 从历史数据 - 构建出老员工数据
                        oldMember = ServiceUtil.buildMemberForRecord(oldMember);
                        for (Member nb : newMembers) {
                            if(oldMember.getId().equals(nb.getId())){
                                Map<String, Object> objectMap = ServiceUtil.handleUpdateItem(oldMember, nb);
                                records.addAll ((List<MemberBusinessUpdateRecord> )objectMap.get("recordList"));
                                itemListUpdate.addAll((List<MemberBusinessUpdateRecordItem>)objectMap.get("itemListUpdate"));
                                itemList.addAll((List<MemberBusinessUpdateRecordItem>)objectMap.get("recordItemList"));
                                break;
                            }
                        }
                    }
                    if(records.size() > 0){
                        memberBusinessUpdateRecordMapper.save(records);
                        for (MemberBusinessUpdateRecord record : records) {
                            if(null != record.getMemberBusinessUpdateRecordItems() && record.getMemberBusinessUpdateRecordItems().size() > 0){
                                for (MemberBusinessUpdateRecordItem item : record.getMemberBusinessUpdateRecordItems()) {
                                    item.setMemberBusinessUpdateRecordId(record.getId());
                                    if(null == itemList){
                                        itemList = new ArrayList<MemberBusinessUpdateRecordItem>();
                                    }
                                    itemList.add(item);
                                }
                            }
                        }
                    }
                    if(itemListUpdate.size() > 0){
                        // 批量更新
                        memberBusinessUpdateRecordItemMapper.batchUpdateStatus(itemListUpdate);
                    }
                    if(itemList.size() > 0){
                        memberBusinessUpdateRecordItemMapper.save(itemList);
                        List<MemberBaseChange> changeList = new ArrayList<MemberBaseChange>();
                        for (MemberBusinessUpdateRecordItem item : itemList) {
                            if(null != item.getBaseChangeList() && item.getBaseChangeList().size() > 0){
                                for (MemberBaseChange change : item.getBaseChangeList()) {
                                    change.setRecordItemId(item.getId());
                                    changeList.add(change);
                                }
                            }
                        }
                        if(changeList.size() > 0){
                            memberBaseChangeMapper.batchAdd(changeList);
                        }
                    }
                }
                // 业务处理完毕，处理增减变结束
            }


        }



    }

    private String getStringNumber(String s) {
        if(CommonUtil.isEmpty(s)){
            return "";
        }
        return s.split("\\.")[0];
    }


    @Transactional
    public void importReduceMember(String targetUrl) throws Exception{
        if (CommonUtil.isEmpty(targetUrl)) {
            return;
        }
        int HttpResult; // 服务器返回的状态
        URL url = new URL(targetUrl); // 创建URL
        URLConnection urlConn = url.openConnection();
        urlConn.connect();
        HttpURLConnection httpConn = (HttpURLConnection) urlConn;
        HttpResult = httpConn.getResponseCode();
        if (HttpResult != HttpURLConnection.HTTP_OK)
            throw new InterfaceCommonException(StatusConstant.Fail_CODE, "数据读取失败");
        else {
            Map<Integer, List<String>> map = ExcelReader.readExcelContent(urlConn.getInputStream(), 1,"");
            // 获取所有员工的身份证号
            List<Member> memberList = memberMapper.queryAllMemberIdCard();

            List<Member> feedBack = memberMapper.queryFeeBack(null); // 员工反馈数据

            // 其他周期性服务
            List<BusinessItem> otherService = businessItemMapper.queryBusinessByBusiness(BusinessEnum.shangYeXian.ordinal());
            // 一次性服务
            List<BusinessItem> onceService = businessItemMapper.queryBusinessByBusiness(BusinessEnum.yiXiXingYeWu.ordinal());
            Iterator<BusinessItem> iterator = onceService.iterator();
            while (iterator.hasNext()){
                BusinessItem next = iterator.next();
                if(1 != next.getIsCompany()){
                    iterator.remove();
                }
            }
            List<Member> members = new ArrayList<Member>(); // 待处理的员工列表
            List<String> titles = new ArrayList<String>();
            List<Integer> importMemberIds = new ArrayList<Integer>();
            boolean is = true;
            int i = 1;
            for (Integer cellNum : map.keySet()) {
                List<String> values = map.get(cellNum);// 行数据
                if(is){
                    int col = 4 + (null == otherService ? 0 : otherService.size()) + onceService.size();
                    if(col > values.size()){
                        throw new InterfaceCommonException(StatusConstant.Fail_CODE, "当前模版不是最新模版，请下载最新的模版");
                    }
                    titles.addAll(values);
                    is = false;
                    continue;
                }
                Member member = new Member();
                List<Business> businessList = new ArrayList<Business>();
                // 取数据
                if(CommonUtil.isEmpty(values.get(1))){
                    throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的证件编号不能为空");
                }
                Integer memberId = getMemberId(memberList, values.get(1));
                if(null == memberId){
                    throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的证件编号不存在");
                }
                member.setId(memberId);
                importMemberIds.add(memberId);
                if(CommonUtil.isEmpty(values.get(2)) || "否".equals(values.get(2))){
                    // 判断是否有待反馈数据
                    if(isFeedBack(memberId,feedBack)){
                        throw new InterfaceCommonException(StatusConstant.OBJECT_EXIST,"第" + i + "行数据存在待反馈数据");
                    }
                    // 取消社保
                    Business business = new Business();
                    business.setId(BusinessEnum.sheBao.ordinal());
                    businessList.add(business);
                }
                if(CommonUtil.isEmpty(values.get(3)) || "是".equals(values.get(3))){
                    // 判断是否有待反馈数据
                    if(isFeedBack(memberId,feedBack)){
                        throw new InterfaceCommonException(StatusConstant.OBJECT_EXIST,"第" + i + "行数据存在待反馈数据");
                    }
                    // 取消公积金
                    Business business = new Business();
                    business.setId(BusinessEnum.gongJiJin.ordinal());
                    businessList.add(business);
                }
                // 取消其他周期性服务
                if(null != otherService && otherService.size() > 0){
                    boolean isHave = false;
                    List<BusinessItem> businessItemList = new ArrayList<BusinessItem>();
                    for (int j = 0; j < otherService.size(); j++) {
                        if(CommonUtil.isEmpty(values.get(4 + j)) || "是".equals(values.get(4 + j))){
                            BusinessItem item = new BusinessItem();
                            Integer otherServiceId = getOtherService(otherService, titles.get(4 + j));
                            if(null == otherServiceId){
                                throw new InterfaceCommonException(StatusConstant.Fail_CODE, "第" + i + "行数据的其他周期性业务异常");
                            }
                            item.setId(otherServiceId);
                            item.setBusinessId(BusinessEnum.shangYeXian.ordinal());
                            businessItemList.add(item);
                            isHave = true;
                        }
                    }
                    if(isHave){
                        Business business = new Business();
                        business.setId(BusinessEnum.shangYeXian.ordinal());
                        business.setBusinessItems(businessItemList);
                        businessList.add(business);
                    }
                }
                // 取消其他周期性服务
                if(null != onceService && onceService.size() > 0){
                    boolean isHave = false;
                    List<BusinessItem> businessItemList = new ArrayList<BusinessItem>();
                    int otherServiceSize = null == otherService ? 0 : otherService.size() - 1;
                    for (int j = 0; j < onceService.size(); j++) {
                        if(CommonUtil.isEmpty(values.get(5 + otherServiceSize + j)) ||  "是".equals(values.get(5 + otherServiceSize + j))){
                            BusinessItem item = new BusinessItem();
                            Integer otherServiceId = getOtherService(onceService, titles.get(5 + otherServiceSize + j));
                            if(null == otherServiceId){
                                throw new InterfaceCommonException(StatusConstant.Fail_CODE, "第" + i + "行数据的其他一次性业务异常");
                            }
                            item.setId(otherServiceId);
                            item.setBusinessId(BusinessEnum.shangYeXian.ordinal());
                            businessItemList.add(item);
                            isHave = true;
                        }
                    }
                    if(isHave){
                        Business business = new Business();
                        business.setId(BusinessEnum.yiXiXingYeWu.ordinal());
                        business.setBusinessItems(businessItemList);
                        businessList.add(business);
                    }
                }
                member.setBusinessList(businessList);
                members.add(member);
                i++;
            }
            // 员工减员业务处理    开始
            if(members.size() > 0){

                Iterator<Member> iterator1 = members.iterator();
                // 待新增 减员业务增减变记录的员工
                List<Integer> wMemberIds = new ArrayList<Integer>();
                List<MemberBusinessUpdateRecord> wMembers = new ArrayList<MemberBusinessUpdateRecord>();
                // 待删除的业务
                // 其他业务
                List<MemberBusiness> memberBusinessList = new ArrayList<MemberBusiness>();
                List<Integer> memberIds = new ArrayList<Integer>();
                // 公积金、社保业务
                List<MemberBusiness> gMemberBusinessList = new ArrayList<MemberBusiness>();
                while (iterator1.hasNext()){
                    Member t = iterator1.next();
                    if(null == t.getBusinessList() || t.getBusinessList().size() == 0){
                        iterator1.remove();
                        continue;
                    }
                    for (Business business : t.getBusinessList()) {
                        MemberBusiness memberBusiness = new MemberBusiness();
                        memberBusiness.setBusinessId(business.getId());
                        memberBusiness.setMemberId(t.getId());
                        if(business.getId().equals(BusinessEnum.gongJiJin.ordinal()) ||
                                business.getId().equals(BusinessEnum.sheBao.ordinal())){
                            gMemberBusinessList.add(memberBusiness);
                            wMemberIds.add(t.getId());
                            MemberBusinessUpdateRecord record = new MemberBusinessUpdateRecord();
                            record.setMemberId(t.getId());
                            record.setServiceType(business.getId() == BusinessEnum.gongJiJin.ordinal() ? 1 : 0);
                            wMembers.add(record);
                            continue;
                        }
                        if(null != business.getBusinessItems() && business.getBusinessItems().size() > 0){
                            List<MemberBusinessOtherItem> otherItemList = new ArrayList<MemberBusinessOtherItem>();
                            for (BusinessItem item : business.getBusinessItems()) {
                                MemberBusinessOtherItem otherItem = new MemberBusinessOtherItem();
                                otherItem.setBusinessItemId(item.getId());
                                otherItemList.add(otherItem);
                            }
                            memberBusiness.setOtherItemList(otherItemList);
                            memberBusinessList.add(memberBusiness);
                            memberIds.add(t.getId());
                        }
                    }
                }

                List<Member> oldMembers = null;
                if(importMemberIds.size() > 0){
                    // 查询之前员工的业务
                    oldMembers = memberMapper.batchQueryMember(importMemberIds);
                }

                if(gMemberBusinessList.size() > 0){
                    memberBusinessMapper.batchDel(gMemberBusinessList);
                }
                if(memberBusinessList.size() > 0){
                    List<MemberBusiness> memberBusinesses = memberBusinessMapper.queryMemberBusinessByMember(memberIds);
                    List<MemberBusinessOtherItem> waitDel = new ArrayList<MemberBusinessOtherItem>();
                    List<MemberBusiness> waitMemberBusinesses = new ArrayList<MemberBusiness>();
                    for (MemberBusiness memberBusiness : memberBusinessList) {
                        for (MemberBusiness business : memberBusinesses) {
                            if(memberBusiness.getBusinessId().equals(business.getBusinessId()) && memberBusiness.getMemberId().equals(business.getMemberId())
                                    && null != business.getOtherItemList() && business.getOtherItemList().size() > 0){
                                int k = 0;
                                for (MemberBusinessOtherItem otherItem : memberBusiness.getOtherItemList()) {
                                    for (MemberBusinessOtherItem businessOtherItem : business.getOtherItemList()) {
                                        if(otherItem.getBusinessItemId().equals(businessOtherItem.getBusinessItemId())){
                                            waitDel.add(businessOtherItem);
                                            k++;
                                            break;
                                        }
                                    }
                                }
                                if(k == business.getOtherItemList().size()){
                                    waitMemberBusinesses.add(business);
                                }
                            }
                        }
                    }
                    // 删除
                    if(waitDel.size() > 0){
                        memberBusinessOtherItemMapper.batDel(waitDel);
                    }
                    if(waitMemberBusinesses.size() > 0){
                        memberBusinessMapper.batchDel(waitMemberBusinesses);
                    }
                }
                if(importMemberIds.size() > 0){
                    // 查询这些员工的业务，如果业务为空，则自动更新为离职
                    List<Member> members1 = memberMapper.batchQueryMember(importMemberIds);
                    List<Member> waitUpdate = new ArrayList<Member>();
                    for (Member member : members1) {
                        if(null == member.getBusinessList() || member.getBusinessList().size() == 0){
                            Member temp = new Member();
                            temp.setId(member.getId());
                            temp.setStateCooperation(0);
                            waitUpdate.add(temp);
                        }
                    }
                    if(waitUpdate.size() > 0){
                        memberMapper.batchUpdateMember(waitUpdate);
                    }
                    // 增减变记录
                    if(null != oldMembers){
                        List<MemberBusinessUpdateRecordItem> itemList = new ArrayList<MemberBusinessUpdateRecordItem>();
                        List<MemberBusinessUpdateRecordItem> itemListUpdate = new ArrayList<MemberBusinessUpdateRecordItem>();
                        List<MemberBusinessUpdateRecord> records = new ArrayList<MemberBusinessUpdateRecord>();
                        for (Member oldMember : oldMembers) {
                            // 从历史数据 - 构建出老员工数据
                            oldMember = ServiceUtil.buildMemberForRecord(oldMember);
                            for (Member nb : members1) {
                                if(oldMember.getId().equals(nb.getId())){
                                    Map<String, Object> objectMap = ServiceUtil.handleUpdateItem(oldMember, nb);
                                    records.addAll ((List<MemberBusinessUpdateRecord> )objectMap.get("recordList"));
                                    itemListUpdate.addAll((List<MemberBusinessUpdateRecordItem>)objectMap.get("itemListUpdate"));
                                    itemList.addAll((List<MemberBusinessUpdateRecordItem>)objectMap.get("recordItemList"));
                                    break;
                                }
                            }
                        }
                        if(records.size() > 0){
                            memberBusinessUpdateRecordMapper.save(records);
                            for (MemberBusinessUpdateRecord record : records) {
                                if(null != record.getMemberBusinessUpdateRecordItems() && record.getMemberBusinessUpdateRecordItems().size() > 0){
                                    for (MemberBusinessUpdateRecordItem item : record.getMemberBusinessUpdateRecordItems()) {
                                        item.setMemberBusinessUpdateRecordId(record.getId());
                                        if(null == itemList){
                                            itemList = new ArrayList<MemberBusinessUpdateRecordItem>();
                                        }
                                        itemList.add(item);
                                    }
                                }
                            }
                        }
                        if(itemListUpdate.size() > 0){
                            // 批量更新
                            memberBusinessUpdateRecordItemMapper.batchUpdateStatus(itemListUpdate);
                        }
                        if(itemList.size() > 0){
                            memberBusinessUpdateRecordItemMapper.save(itemList);
                            List<MemberBaseChange> changeList = new ArrayList<MemberBaseChange>();
                            for (MemberBusinessUpdateRecordItem item : itemList) {
                                if(null != item.getBaseChangeList() && item.getBaseChangeList().size() > 0){
                                    for (MemberBaseChange change : item.getBaseChangeList()) {
                                        change.setRecordItemId(item.getId());
                                        changeList.add(change);
                                    }
                                }
                            }
                            if(changeList.size() > 0){
                                memberBaseChangeMapper.batchAdd(changeList);
                            }
                        }
                    }
                }
            }
            // 员工减员业务处理   结束
        }

    }

    private boolean isFeedBack(Integer memberId, List<Member> feedBack) {

        if(null == feedBack || feedBack.size() == 0){
            return false;
        }
        for (Member member : feedBack) {
            if(memberId.equals(member.getId())){
                return true;
            }
        }
        return false;
    }


    private Integer getMemberId(List<Member> memberList,String idCard){

        if(null == memberList || memberList.size() == 0){
            return null;
        }
        for (Member member : memberList) {
            if(idCard.equals(member.getCertificateNum())){
                return member.getId();
            }
        }
        return null;
    }

    /**
     * 导入员工信息 暂 增加员工信息
     *
     * @param targetUrl
     */
    @Transactional
    public void importMemberInfo(String targetUrl) throws Exception {
        if (CommonUtil.isEmpty(targetUrl)) {
            return;
        }
        int HttpResult; // 服务器返回的状态
        URL url = new URL(targetUrl); // 创建URL
        URLConnection urlConn = url.openConnection();
        urlConn.connect();
        HttpURLConnection httpConn = (HttpURLConnection) urlConn;
        HttpResult = httpConn.getResponseCode();
        if (HttpResult != HttpURLConnection.HTTP_OK)
            throw new InterfaceCommonException(StatusConstant.Fail_CODE, "数据读取失败");
        else {
            List<Member> members = new ArrayList<Member>();
            // 获取所有公司
            List<Company> allCompany = companyMapper.queryAllCompany();
            // 获取所有员工的身份证号
            List<Member> memberList = memberMapper.queryAllMemberIdCard();
            // 所有市级行政区
            List<City> cityList = cityMapper.queryCityByLevelImport(2);
            // 所有公司的业务
            List<CompanyBusiness> companyBusinesses = companyBusinessMapper.queryCompanyBusiness();
            // 所有的缴金地
            List<PayPlace> payPlaces = payPlaceMapper.queryAllPayPlaceForImport();
            // 所有的经办机构
            List<Organization> organizations = organizationMapper.queryAllOrganization();
            //所有的办理方
            List<Transactor> transactors = transactorMapper.queryAllTransactor();
            // 所有的档次集合
            List<InsuranceLevel> insuranceLevels = iInsuranceLevelMapper.queryAllInsuranceLevel();
            // 所有档次配置
            List<PayTheWay> payTheWays = payTheWayMapper.queryPayTheWayByLevel(null);
            // 所有公司的缴金地配置
            List<CompanyPayPlace> companyPayPlaces = companyPayPlaceMapper.queryAlCompanyPayPlace();
            // 所有的子账单
            List<CompanySonBill> companySonBills = companySonBillMapper.queryCompanySonBillByCompany(null);
            // 其他周期性服务
            List<BusinessItem> otherService = businessItemMapper.queryBusinessByBusiness(BusinessEnum.shangYeXian.ordinal());
            // 一次性服务
            List<BusinessItem> onceService = businessItemMapper.queryBusinessByBusiness(BusinessEnum.yiXiXingYeWu.ordinal());
            // 获取所有公司的合作方式
            List<Company> companyCooperationMethodList = companyMapper.queryAllCompanyCooperationMethod();
            Iterator<BusinessItem> iterator = onceService.iterator();
            while (iterator.hasNext()){
                BusinessItem next = iterator.next();
                if(1 != next.getIsCompany()){
                    iterator.remove();
                }
            }
            Map<Integer, List<String>> map = ExcelReader.readExcelContent(urlConn.getInputStream(), 1,1);
            List<String> titles = new ArrayList<String>();
            int i = 3;
            boolean isFirst = true;
            for (Integer cellNum : map.keySet()) {
                List<String> values = map.get(cellNum);// 行数据
                if(isFirst){
                    // 第一行为表头
                    titles.addAll(values);
                    isFirst = false;
                    continue;
                }
                // 判断是否为最 新的excel
                int i1 = onceService.size() + otherService.size();
                if(values.size() < 33 + i1){
                    throw new InterfaceCommonException(StatusConstant.Fail_CODE, "当前模版不是最新模版，请下载最新的模版");
                }
                Member member = new Member();
                List<Business> businessList = new ArrayList<Business>();
                List<MemberBusinessItem> memberBusinessItems = new ArrayList<MemberBusinessItem>();
                if(CommonUtil.isEmpty(values.get(0))){
                    continue;
                }
                Integer companyId = getCompany(allCompany, values.get(0));
                if (null == companyId) {
                    throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的公司不存在");
                }
                member.setCompanyId(companyId);

                member.setDepartment(values.get(1)); //部门

                if (CommonUtil.isEmpty(values.get(2))) {
                    throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的姓名不能为空");
                }
                member.setUserName(values.get(2));
                if (CommonUtil.isEmpty(values.get(3))) {
                    throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的证件类型不能为空");
                }
                if ("身份证".equals(values.get(3))) {
                    member.setCertificateType(IdType.identityCard.ordinal());
                } else if ("护照".equals(values.get(3))) {
                    member.setCertificateType(IdType.passport.ordinal());
                } else if ("港澳台通行证".equals(values.get(3))) {
                    member.setCertificateType(IdType.passCard.ordinal());
                } else {
                    throw new InterfaceCommonException(StatusConstant.Fail_CODE, "第" + i + "行数据的证件类型填写错误");
                }
                if (CommonUtil.isEmpty(values.get(4))) {
                    throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的证件编号不能为空");
                }
                if(IdType.identityCard.ordinal() == member.getCertificateType()
                        && !CommonUtil.isIdCard(values.get(4))){
                    throw new InterfaceCommonException(StatusConstant.Fail_CODE, "第" + i + "行数据的证件编号格式错误");
                }
                String idCard = getStringNumber(values.get(4).replaceAll("\'", ""));
                if (isExistOfIdCard(memberList, idCard)) {
                    throw new InterfaceCommonException(StatusConstant.Fail_CODE, "第" + i + "行数据的证件编号已经存在");
                }
                member.setCertificateNum(idCard);
                memberList.add(member);
                if (CommonUtil.isEmpty(values.get(5))) {
                    throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的手机号不能为空");
                }
                member.setPhone(getStringNumber(values.get(5)));
                if (CommonUtil.isEmpty(values.get(6))) {
                    throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的工作地点不能为空");
                }
                Integer cityId = getCity(cityList, values.get(6));
                if (null == cityId) {
                    throw new InterfaceCommonException(StatusConstant.Fail_CODE, "第" + i + "行数据的工作地点填写错误");
                }
                member.setCityId(cityId);
                if (CommonUtil.isEmpty(values.get(7))) {
                    throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的学历不能为空");
                }
                if (null == getEducation(values.get(7))) {
                    throw new InterfaceCommonException(StatusConstant.Fail_CODE, "第" + i + "行数据的学历填写错误");
                }
                member.setEducation(getEducation(values.get(7)));
                if (CommonUtil.isEmpty(values.get(8))) {
                    throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的合作状态不能为空");
                }
                //离职、在职
//                if ("离职".equals(values.get(7))) {
//                    member.setStateCooperation(0);
//                } else if ("在职".equals(values.get(7))) {
//                    member.setStateCooperation(1);
//                } else {
//                    throw new InterfaceCommonException(StatusConstant.Fail_CODE, "第" + i + "行数据的合作状态填写错误");
//                }
                // 合作方式 0：普通 1：派遣  2：外包
                if (CommonUtil.isEmpty(values.get(8))) {
                    throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的合作方式不能为空");
                }
                Integer method = getCompanyCooperationMethod(companyCooperationMethodList, companyId, values.get(8));
                if(null == method){
                    throw new InterfaceCommonException(StatusConstant.Fail_CODE, "第" + i + "行数据的合作方式填写错误");
                }
                member.setWaysOfCooperation(method);

                if(method == 1 || method == 2){
                    // 如果等于外包或者派遣 则合同结束和开始时间必须填写
                    if(CommonUtil.isEmpty(values.get(9),values.get(10))){
                        throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的合同开始/结束日期不能为空");
                    }
                    member.setContractStartTime(Timestamp.parseDate2(values.get(9),"yyyy-MM-dd"));
                    member.setContractEndTime(Timestamp.parseDate2(values.get(10),"yyyy-MM-dd"));
                }

                // 封装社保相关数据
                if (!CommonUtil.isEmpty(values.get(11))) {
                    if (!checkCompanyBusiness(companyBusinesses, member.getCompanyId(), BusinessEnum.sheBao.ordinal())) {
                        throw new InterfaceCommonException(StatusConstant.Fail_CODE, "第" + i + "行数据该公司没有社保业务");
                    }
                    Business memberBusiness = new Business();
                    memberBusiness.setId(BusinessEnum.sheBao.ordinal());

                    MemberBusinessItem item = new MemberBusinessItem();
                    item.setType(0);
                    //代理、托管
                    if ("代理".equals(values.get(11))) {
                        item.setServeMethod(0);
                        // 是否纳入应收
                        if ("是".equals(values.get(12))) {
                            item.setIsReceivable(1);
                        } else {
                            throw new InterfaceCommonException(StatusConstant.Fail_CODE, "第" + i + "行数据的是否纳入应收填写错误");
                        }
                        if (CommonUtil.isEmpty(values.get(13))) {
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的缴金地不能为空");
                        }
                        Integer payPlaceId = getPayPlace(payPlaces, values.get(13), InsuranceType.SheBao.ordinal());
                        if (null == payPlaceId) {
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的缴金地不存在");
                        }
                        item.setPayPlaceId(payPlaceId);

                        if (CommonUtil.isEmpty(values.get(14))) {
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的经办机构不能为空");
                        }
                        Integer organization = getOrganization(organizations, values.get(14), item.getPayPlaceId());
                        if (null == organization) {
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的经办机构不存在");
                        }
                        item.setOrganizationId(organization);

                        if (CommonUtil.isEmpty(values.get(15))) {
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的办理方不能为空");
                        }
                        Integer transactor = getTransactor(transactors, values.get(15), item.getOrganizationId());
                        if (null == transactor) {
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的办理方不存在");
                        }
                        item.setTransactorId(transactor);
                    } else if ("托管".equals(values.get(11))) {
                        item.setServeMethod(1);
                        // 是否纳入应收
                        if ("是".equals(values.get(12))) {
                            item.setIsReceivable(1);
                        }else if("否".equals(values.get(12))){
                            item.setIsReceivable(0);
                        } else {
                            throw new InterfaceCommonException(StatusConstant.Fail_CODE, "第" + i + "行数据的是否纳入应收填写错误");
                        }
                        if (CommonUtil.isEmpty(values.get(13))) {
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的缴金地不能为空");
                        }
                        Integer payPlaceId = getPayPlace(payPlaces, values.get(13), InsuranceType.SheBao.ordinal());
                        if (null == payPlaceId) {
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的缴金地不存在");
                        }

                        if (CommonUtil.isEmpty(values.get(14))) {
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的经办机构不能为空");
                        }
//                        Integer organization = getOrganization(organizations, values.get(12), payPlaceId);
//                        if (null == organization) {
//                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的经办机构不存在");
//                        }
                        if (CommonUtil.isEmpty(values.get(15))) {
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的办理方不能为空");
                        }
                        Integer companyPayPlaceId = getCompanyPayPlace(companyPayPlaces, payPlaceId, null, values.get(15),
                                member.getCompanyId(),InsuranceType.SheBao.ordinal(),values.get(14));
                        if(null == companyPayPlaceId){
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的办理方不存在");
                        }
                        item.setPayPlaceId(companyPayPlaceId);
                    } else {
                        throw new InterfaceCommonException(StatusConstant.Fail_CODE, "第" + i + "行数据的社保服务方式填写错误");
                    }
                    //  设置其他
                    if(!CommonUtil.isEmpty(values.get(11))){
                        if (CommonUtil.isEmpty(values.get(16))) {
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的档次不能为空");
                        }
                        Integer insuranceLevel = getInsuranceLevel(insuranceLevels, values.get(16), getPayPlace(payPlaces, values.get(13), InsuranceType.SheBao.ordinal()));
                        if (null == insuranceLevel) {
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的档次不存在");
                        }
                        item.setInsuranceLevelId(insuranceLevel);
                        Map<String, Object> ways = getPayTheWays(payTheWays, item.getInsuranceLevelId());
                        if(null == ways){
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的档次下不存在基数");
                        }


                        if(CommonUtil.isEmpty(values.get(17))){
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的基数类型不能为空");
                        }
                        Integer baseType = getBaseType(values.get(17));
                        if(null == baseType){
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的基数类型填写错误");
                        }
                        if(baseType == 2 && CommonUtil.isEmpty(values.get(18))){
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的基数填写错误");
                        }
                        //  缴纳基数类型 0：最低  1：最高  2：填写
                        if(baseType == 0){
                            item.setBaseType(0);
                        }
                        else if(baseType == 1){
                            item.setBaseType(1);
                        }
                        else{
                            double min = (Double) ways.get("min");
                            double max = (Double) ways.get("max");
                            item.setBaseType(2);
                            Double baseNumber = Double.valueOf(values.get(18));
                            if(baseNumber < min || baseNumber > max){
                                throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的基数填写错误");
                            }
                            item.setBaseNumber(baseNumber);
                        }

                        if(CommonUtil.isEmpty(values.get(19))){
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的服务起始月不能为空");
                        }
                        if(values.get(19).indexOf("-") < 0){
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的服务起始月格式错误");
                        }
                        String[] split = values.get(19).split("-");
                        if(split.length < 1){
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的服务起始月格式错误");
                        }
                        Date serviceStartTime = DateUtil.setDate(DateUtil.setDate(new Date(), TimeField.Year.ordinal(), Integer.valueOf(split[0])), TimeField.Month.ordinal(), Integer.valueOf(split[1]));
                        item.setServiceStartTime(serviceStartTime);
                        if(!CommonUtil.isEmpty(values.get(20))){
                            if(CommonUtil.isEmpty(values.get(20))){
                                throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的服务结束月不能为空");
                            }
                            if(values.get(20).indexOf("-") < 0){
                                throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的服务结束月格式错误");
                            }
                            String[] split_ = values.get(20).split("-");
                            if(split_.length < 1){
                                throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的服务结束月格式错误");
                            }
                            Date serviceEndTime = DateUtil.setDate(DateUtil.setDate(new Date(), TimeField.Year.ordinal(), Integer.valueOf(split_[0])), TimeField.Month.ordinal(), Integer.valueOf(split_[1]));
                            item.setServiceEndTime(serviceEndTime);
                        }
                        if(CommonUtil.isEmpty(values.get(21))){
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的账单起始月不能为空");
                        }
                        if(values.get(21).indexOf("-") < 0){
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的账单起始月格式错误");
                        }
                        String[] split__ = values.get(21).split("-");
                        if(split__.length < 1){
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的账单起始月格式错误");
                        }
                        Date billStartTime = DateUtil.setDate(DateUtil.setDate(new Date(), TimeField.Year.ordinal(), Integer.valueOf(split__[0])), TimeField.Month.ordinal(), Integer.valueOf(split__[1]));
                        item.setBillStartTime(billStartTime);
                        // 设置子账单
                        if(CommonUtil.isEmpty(values.get(22))){
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的子账单不能为空");
                        }
                        Integer sonBill = getSonBill(companySonBills, values.get(22), member.getCompanyId());
                        if(null == sonBill){
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的子账单不存在");
                        }
                        memberBusiness.setCompanySonBillId(sonBill);
                    }
                    item.setIsFirstPay(1);
                    memberBusiness.setMemberBusinessItem(item);
                    businessList.add(memberBusiness);
                }
                // 封装公积金
                if(!CommonUtil.isEmpty(values.get(23))){
                    if (!checkCompanyBusiness(companyBusinesses, member.getCompanyId(), BusinessEnum.gongJiJin.ordinal())) {
                        throw new InterfaceCommonException(StatusConstant.Fail_CODE, "第" + i + "行数据该公司没有社保业务");
                    }
                    Business memberBusiness = new Business();
                    memberBusiness.setId(BusinessEnum.gongJiJin.ordinal());

                    MemberBusinessItem item = new MemberBusinessItem();
                    item.setType(1);
                    if("代理".equals(values.get(23))){
                        item.setServeMethod(0);
                        // 是否纳入应收
                        if ("是".equals(values.get(24))) {
                            item.setIsReceivable(1);
                        } else {
                            throw new InterfaceCommonException(StatusConstant.Fail_CODE, "第" + i + "行数据的公积金是否纳入应收填写错误");
                        }
                        if (CommonUtil.isEmpty(values.get(25))) {
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的公积金缴金地不能为空");
                        }
                        Integer payPlaceId = getPayPlace(payPlaces, values.get(25), InsuranceType.GongJiJin.ordinal());
                        if (null == payPlaceId) {
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的公积金缴金地不存在");
                        }
                        item.setPayPlaceId(payPlaceId);

                        if (CommonUtil.isEmpty(values.get(26))) {
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的公积金经办机构不能为空");
                        }
                        Integer organization = getOrganization(organizations, values.get(26), item.getPayPlaceId());
                        if (null == organization) {
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的公积金经办机构不存在");
                        }
                        item.setOrganizationId(organization);

                        if (CommonUtil.isEmpty(values.get(27))) {
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的公积金办理方不能为空");
                        }
                        Integer transactor = getTransactor(transactors, values.get(27), item.getOrganizationId());
                        if (null == transactor) {
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的公积金办理方不存在");
                        }
                        item.setTransactorId(transactor);
                    }
                    else if("托管".equals(values.get(23))){
                        item.setServeMethod(1);
                        if ("是".equals(values.get(24))) {
                            item.setIsReceivable(1);
                        }else if("否".equals(values.get(24))){
                            item.setIsReceivable(0);
                        } else {
                            throw new InterfaceCommonException(StatusConstant.Fail_CODE, "第" + i + "行数据的公积金是否纳入应收填写错误");
                        }
                        if (CommonUtil.isEmpty(values.get(25))) {
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的公积金缴金地不能为空");
                        }
                        Integer payPlaceId = getPayPlace(payPlaces, values.get(25), InsuranceType.GongJiJin.ordinal());
                        if (null == payPlaceId) {
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的公积金缴金地不存在");
                        }

                        if (CommonUtil.isEmpty(values.get(26))) {
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的公积金经办机构不能为空");
                        }
                        Integer organization = getOrganization(organizations, values.get(26), payPlaceId);
                        if (null == organization) {
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的公积金经办机构不存在");
                        }
                        item.setOrganizationId(organization);
                        if (CommonUtil.isEmpty(values.get(27))) {
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的公积金办理方不能为空");
                        }
                        Integer companyPayPlace = getCompanyPayPlace(companyPayPlaces, payPlaceId, organization, values.get(27), member.getCompanyId(),
                                InsuranceType.GongJiJin.ordinal(),null);
                        if(null == companyPayPlace){
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的公积金缴金地办理方数据有误");
                        }
                        item.setPayPlaceId(companyPayPlace);

                    }
                    else{
                        throw new InterfaceCommonException(StatusConstant.Fail_CODE, "第" + i + "行数据的公积金服务方式填写错误");
                    }
                    // 设置其他公共
                    if (CommonUtil.isEmpty(values.get(28))) {
                        throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的公积金比例不能为空");
                    }
                    // 判断比例是否在之间
                    if(item.getServeMethod() == 0 && !judgeRation(organizations, item.getOrganizationId(), Double.valueOf(values.get(28)))){
                        throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的公积金比例填写有误");
                    }
                    if(item.getServeMethod() == 1){
                        // 验证比例
                        CompanyPayPlace place = getCompanyPayPlace(companyPayPlaces, item.getPayPlaceId());
                        if(null == place){
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的公积金缴金地办理方数据有误");
                        }
                        else{
                            if(!Double.valueOf(values.get(28)).equals(place.getMePayPrice())){
                                throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的公积金比例填写有误");
                            }
                        }
                    }
                    item.setRatio(Double.valueOf(values.get(28)));
                    if(CommonUtil.isEmpty(values.get(29))){
                        throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的公积金基数不能为空");
                    }
                    Map<String, Object> result = getBaseNumber(organizations, item.getOrganizationId());
                    if(null == result){
                        throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的公积金基数填写错误");
                    }

//                    if(CommonUtil.isEmpty(values.get(29))){
//                        throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的基数类型不能为空");
//                    }
//                    Integer baseType = getBaseType(values.get(29));
//                    if(null == baseType){
//                        throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的基数类型填写错误");
//                    }
//                    if(baseType == 2 && CommonUtil.isEmpty(values.get(30))){
//                        throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的基数填写错误");
//                    }
                    //  缴纳基数类型 0：最低  1：最高  2：填写
//                    if(baseType == 0){
//                        item.setBaseType(0);
//                    }
//                    else if(baseType == 1){
//                        item.setBaseType(1);
//                    }
//                    else{
//                        item.setBaseType(2);
//                        item.setBaseNumber(Double.valueOf(values.get(30)));
//                    }
                    // 客户需求再次变动，不限制公积金的基数，直接默认为填写基数
                    Double baseNumber = Double.valueOf(getStringNumber(values.get(29)));
                    Double min = (Double) result.get("min");
                    Double max = (Double) result.get("max");
                    if(baseNumber < min || baseNumber > max){
                        throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的公积金基数填写错误");
                    }
                    item.setBaseType(2);
                    item.setBaseNumber(baseNumber);

//                    if(baseNumber == min){
//                        item.setBaseType(0);
//                    }
//                    else if(baseNumber == max){
//                        item.setBaseType(1);
//                    }
//                    else{
////                        if(baseNumber < min || baseNumber > max){
////                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的公积金基数填写错误");
////                        }
//                        item.setBaseType(2);
//                        item.setBaseNumber(baseNumber);
//                    }
                    if(CommonUtil.isEmpty(values.get(30))){
                        throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的公积金服务起始月不能为空");
                    }
                    if(values.get(30).indexOf("-") < 0){
                        throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的公积金服务起始月格式错误");
                    }
                    String[] split = values.get(30).split("-");
                    if(split.length < 1){
                        throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的公积金服务起始月格式错误");
                    }
                    Date serviceStartTime = DateUtil.setDate(DateUtil.setDate(new Date(), TimeField.Year.ordinal(), Integer.valueOf(split[0])), TimeField.Month.ordinal(), Integer.valueOf(split[1]));
                    item.setServiceStartTime(serviceStartTime);
                    if(!CommonUtil.isEmpty(values.get(31))){
                        if(CommonUtil.isEmpty(values.get(31))){
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的公积金服务结束月不能为空");
                        }
                        if(values.get(31).indexOf("-") < 0){
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的公积金服务结束月格式错误");
                        }
                        String[] split_ = values.get(31).split("-");
                        if(split_.length < 1){
                            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的公积金服务结束月格式错误");
                        }
                        Date serviceEndTime = DateUtil.setDate(DateUtil.setDate(new Date(), TimeField.Year.ordinal(), Integer.valueOf(split_[0])), TimeField.Month.ordinal(), Integer.valueOf(split_[1]));
                        item.setServiceEndTime(serviceEndTime);
                    }
                    if(CommonUtil.isEmpty(values.get(32))){
                        throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的公积金账单起始月不能为空");
                    }
                    if(values.get(32).indexOf("-") < 0){
                        throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的公积金账单起始月格式错误");
                    }
                    String[] split__ = values.get(32).split("-");
                    if(split__.length < 1){
                        throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的公积金账单起始月格式错误");
                    }
                    Date billStartTime = DateUtil.setDate(DateUtil.setDate(new Date(), TimeField.Year.ordinal(), Integer.valueOf(split__[0])), TimeField.Month.ordinal(), Integer.valueOf(split__[1]));
                    item.setBillStartTime(billStartTime);
                    // 设置子账单
                    if(CommonUtil.isEmpty(values.get(33))){
                        throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的公积金子账单不能为空");
                    }
                    Integer sonBill = getSonBill(companySonBills, values.get(33), member.getCompanyId());
                    if(null == sonBill){
                        throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST, "第" + i + "行数据的公积金子账单不存在");
                    }
                    memberBusiness.setCompanySonBillId(sonBill);
                    item.setIsFirstPay(1);
                    memberBusiness.setMemberBusinessItem(item);
                    businessList.add(memberBusiness);
                }
                // 封装 其他周期性服务
                Business business = new Business();
                List<BusinessItem> businessItemList = new ArrayList<BusinessItem>();
                for (int j = 0; j < otherService.size(); j++) {
                    if(!CommonUtil.isEmpty(values.get(34 + j))){
                        if(values.get(34 + j).indexOf("-") < 0){
                            throw new InterfaceCommonException(StatusConstant.Fail_CODE, "第" + i + "行数据其他周期性服务填写有误");
                        }
                        String[] split = values.get(34 + j).split("-");
                        if(!"是".equals(split[0])){
                            continue;
                        }
                        if(null == business.getId()){
                            business.setId(BusinessEnum.shangYeXian.ordinal());
                        }
                        String s = titles.get(34 + j);
                        Integer id = getOtherService(otherService, s);
                        if(null == id){
                            throw new InterfaceCommonException(StatusConstant.Fail_CODE, "第" + i + "行数据不存在该其他周期性服务");
                        }
                        // 设置子类的子账单
                        Integer sonBill = getSonBill(companySonBills, split[1], companyId);
                        if(null == sonBill){
                            throw new InterfaceCommonException(StatusConstant.Fail_CODE, "第" + i + "行数据的子账单不存在");
                        }
                        BusinessItem item = new BusinessItem();
                        item.setId(id);
                        item.setBusinessId(business.getId());
                        item.setCompanySonBillId(sonBill);
                        businessItemList.add(item);
                    }
                }
                business.setBusinessItems(businessItemList);
                if (null != business.getId()) {
                    businessList.add(business);
                }
                // 封装一次性服务
                Business onceBusiness = new Business();

                List<BusinessItem> onceBusinessItemList = new ArrayList<BusinessItem>();
                for (int j = 0; j < onceService.size(); j++) {
                    int index = 35 + otherService.size() + j - 1;
                    if(index == values.size()){
                        continue;
                    }
                    if(!CommonUtil.isEmpty(values.get(index))){
                        if(null == onceBusiness.getId()){
                            onceBusiness.setId(BusinessEnum.yiXiXingYeWu.ordinal());
                        }
                        if(values.get(index).indexOf("-") < 0){
                            throw new InterfaceCommonException(StatusConstant.Fail_CODE, "第" + i + "行数据的一次性服务填写有误");
                        }
                        String[] split = values.get(index).split("-");
                        if(!"是".equals(split[0])){
                            continue;
                        }
                        String s = titles.get(index);
                        Integer id = getOtherService(onceService, s);
                        if(null == id){
                            throw new InterfaceCommonException(StatusConstant.Fail_CODE, "第" + i + "行数据不存在该其他周期性服务");
                        }
                        // 设置子账单

                        Integer sonBill = getSonBill(companySonBills, split[1], companyId);
                        if(null == sonBill){
                            throw new InterfaceCommonException(StatusConstant.Fail_CODE, "第" + i + "行数据的子账单不存在");
                        }
                        BusinessItem item = new BusinessItem();
                        item.setId(id);
                        item.setBusinessId(onceBusiness.getId());
                        item.setCompanySonBillId(sonBill);
                        onceBusinessItemList.add(item);
                    }
                }
                onceBusiness.setBusinessItems(onceBusinessItemList);
                if (null != onceBusiness.getId()) {
                    businessList.add(onceBusiness);
                }
                member.setMemberBusinessItems(memberBusinessItems);
                member.setBusinessList(businessList);
                if(businessList.size() > 0){
                    member.setStateCooperation(1);
                }else{
                    member.setStateCooperation(0);
                }
                members.add(member);
                i++;
            }
            // 新增操作
            if (members.size() > 0) {
                memberMapper.batchAddMember(members);
                List<MemberBusiness> memberBusinessList = new ArrayList<MemberBusiness>();

                List<MemberCount> memberCountList = new ArrayList<MemberCount>();

                for (Member member : members) {
                    MemberCount memberCount = new MemberCount();
                    memberCount.setMemberId(member.getId());
                    memberCount.setStateCooperation(member.getStateCooperation());
                    memberCount.setCompanyId(member.getCompanyId());

                    if(null != member.getBusinessList() && member.getBusinessList().size() > 0){
                        for (Business business : member.getBusinessList()) {
                            MemberBusiness temp = new MemberBusiness();
                            temp.setBusinessId(business.getId());
                            temp.setMemberId(member.getId());
                            temp.setCompanySonBillId(business.getCompanySonBillId());

                            if((BusinessEnum.gongJiJin.ordinal() == business.getId() || BusinessEnum.sheBao.ordinal() == business.getId())
                                    && null != business.getMemberBusinessItem()){
                                temp.setMemberBusinessItem(business.getMemberBusinessItem());
                                memberBusinessList.add(temp);
                                if(business.getId() == BusinessEnum.sheBao.ordinal()){
                                    if(0 == business.getMemberBusinessItem().getServeMethod()){
                                        // 代理
                                        memberCount.setPayPlaceIdOfSocialSecurity(business.getMemberBusinessItem().getPayPlaceId());
                                    }
                                    else{
                                        memberCount.setPayPlaceIdOfSocialSecurity(companyPayPlaceMapper.info(business.getMemberBusinessItem().getPayPlaceId()).getPayPlaceId());
                                    }

                                }
                                if(business.getId() == BusinessEnum.gongJiJin.ordinal()){
                                    if(0 == business.getMemberBusinessItem().getServeMethod()){
                                        memberCount.setPayPlaceIdOfReservedFunds(business.getMemberBusinessItem().getPayPlaceId());
                                    }
                                    else{
                                        memberCount.setPayPlaceIdOfReservedFunds(companyPayPlaceMapper.info(business.getMemberBusinessItem().getPayPlaceId()).getPayPlaceId());
                                    }
                                }
                            }
                            else{
                                if(null != business.getBusinessItems() && business.getBusinessItems().size() > 0){
                                    List<MemberBusinessOtherItem> otherItemList = new ArrayList<MemberBusinessOtherItem>();
                                    for (BusinessItem businessItem : business.getBusinessItems()) {
                                        MemberBusinessOtherItem other = new MemberBusinessOtherItem();
                                        other.setBusinessItemId(businessItem.getId());
                                        other.setCompanySonBillId(businessItem.getCompanySonBillId());
                                        otherItemList.add(other);
                                    }
                                    temp.setOtherItemList(otherItemList);
                                }
                                memberBusinessList.add(temp);
                            }
                        }
                    }
                    memberCountList.add(memberCount);
                }
                if(memberCountList.size() > 0){
                    // 新增记录
                    memberCountMapper.batchAdd(memberCountList);
                }
                if (memberBusinessList.size() > 0) {
                    memberBusinessMapper.batchAddMemberBusiness(memberBusinessList);
                    List<MemberBusinessItem> itemList = new ArrayList<MemberBusinessItem>();
                    List<MemberBusinessOtherItem> otherItemList = new ArrayList<MemberBusinessOtherItem>();
                    for (MemberBusiness memberBusiness : memberBusinessList) {
                        if (null != memberBusiness.getMemberBusinessItem()) {
                            memberBusiness.getMemberBusinessItem().setMemberBusinessId(memberBusiness.getId());
                            itemList.add(memberBusiness.getMemberBusinessItem());
                        }
                        if(null != memberBusiness.getOtherItemList()){
                            for (MemberBusinessOtherItem item : memberBusiness.getOtherItemList()) {
                                item.setMemberBusinessId(memberBusiness.getId());
                                otherItemList.add(item);
                            }
                        }
                    }
                    if(itemList.size() > 0){
                        memberBusinessItemMapper.save(itemList);
                    }
                    if(otherItemList.size() > 0){
                        memberBusinessOtherItemMapper.batchAddMemberBusinessOtherItem(otherItemList);
                    }
                    // 做增减变记录
                    for (Member member : members) {
                        // 增减变记录
                        Map<String, Object> dataMap = ServiceUtil.handleUpdateItem(null, member);
                        List<MemberBusinessUpdateRecord> records = (List<MemberBusinessUpdateRecord> )dataMap.get("recordList");
                        List<MemberBusinessUpdateRecordItem> recordItemList = (List<MemberBusinessUpdateRecordItem>)dataMap.get("recordItemList");
                        if(records.size() > 0){
                            memberBusinessUpdateRecordMapper.save(records);
                            for (MemberBusinessUpdateRecord record : records) {
                                if(null != record.getMemberBusinessUpdateRecordItems() && record.getMemberBusinessUpdateRecordItems().size() > 0){
                                    for (MemberBusinessUpdateRecordItem item : record.getMemberBusinessUpdateRecordItems()) {
                                        item.setMemberBusinessUpdateRecordId(record.getId());
                                        if(null == recordItemList){
                                            recordItemList = new ArrayList<MemberBusinessUpdateRecordItem>();
                                        }
                                        recordItemList.add(item);
                                    }
                                }
                            }

                            if(recordItemList.size() > 0){
                                memberBusinessUpdateRecordItemMapper.save(recordItemList);
                                List<MemberBaseChange> changeList = new ArrayList<MemberBaseChange>();
                                for (MemberBusinessUpdateRecordItem item : recordItemList) {
                                    if(null != item.getBaseChangeList() && item.getBaseChangeList().size() > 0){
                                        for (MemberBaseChange change : item.getBaseChangeList()) {
                                            change.setRecordItemId(item.getId());
                                            changeList.add(change);
                                        }
                                    }
                                }
                                if(changeList.size() > 0){
                                    memberBaseChangeMapper.batchAdd(changeList);
                                }
                            }

                        }
                    }
                }
            }
        }

    }

    private Integer getBaseType(String s) {
        //  缴纳基数类型 0：最低  1：最高  2：填写
        if(CommonUtil.isEmpty(s)){
            return null;
        }
        if("最高基数".equals(s)){
            return 1;
        }
        else if("最低基数".equals(s)){
            return 0;
        }
        else if("基数填写".equals(s)){
            return 2;
        }
        else{
            return null;
        }
    }

    private boolean judgeRation(List<Organization> organizations, Integer organizationId, Double aDouble) {

        if(null == organizations || organizations.size() == 0){
            return false;
        }
        for (Organization organization : organizations) {
            if(organizationId.equals(organization.getId())){
                if(aDouble >= organization.getMin() && aDouble <= organization.getMax()){
                    return true;
                }
            }
        }
        return false;
    }

    // 获取公司的合作方式
    private Integer getCompanyCooperationMethod(List<Company> companyList,Integer companyId,String cooperationMethodName){
        if(null == companyList || companyList.size() == 0 || null == companyId
                || CommonUtil.isEmpty(cooperationMethodName)){
            return null;
        }
        Integer cooperation = null;
        if ("普通".equals(cooperationMethodName)) {
            cooperation = 0;
        } else if ("派遣".equals(cooperationMethodName)) {
            cooperation = 1;
        } else if ("外包".equals(cooperationMethodName)) {
            cooperation = 2;
        }else{
            return null;
        }

        for (Company company : companyList) {
            if(companyId.equals(company.getId()) && null != company.getCooperationMethodList() && company.getCooperationMethodList().size() > 0){
                for (CompanyCooperationMethod method : company.getCooperationMethodList()) {
                    if(cooperation.equals(method.getCooperationMethodId())){
                        return method.getCooperationMethodId();
                    }
                }
                return null;
            }
        }
        return null;
    }


    private CompanyPayPlace getCompanyPayPlace(List<CompanyPayPlace> payPlaces,Integer companyPayPlaceId){
        if(null == payPlaces || payPlaces.size() == 0){
            return null;
        }
        for (CompanyPayPlace payPlace : payPlaces) {
            if(companyPayPlaceId.equals(payPlace.getId())){
                return payPlace;
            }
        }
        return null;
    }

    private Integer getCompanyPayPlace(List<CompanyPayPlace> payPlaces,Integer payPlaceId,Integer organizationId,
                                       String companyName,Integer companyId,Integer type,String organizationName){
        if(null == payPlaces || payPlaces.size() == 0){
            return null;
        }
        for (CompanyPayPlace payPlace : payPlaces) {

            if(type == InsuranceType.SheBao.ordinal()){

                if(payPlace.getCompanyId().equals(companyId) && payPlace.getPayPlaceId().equals(payPlaceId)
                        && payPlace.getTransactorName().equals(companyName) && payPlace.getType() == type
                        && payPlace.getOrganizationName().equals(organizationName)){
                    return payPlace.getId();
                }

            }
            else if(type == InsuranceType.GongJiJin.ordinal()){
                if(payPlace.getCompanyId().equals(companyId) && payPlace.getPayPlaceId().equals(payPlaceId)
                        && payPlace.getOrganizationId().equals(organizationId) && payPlace.getTransactorName().equals(companyName)
                        && payPlace.getType().equals(type)){
                    return payPlace.getId();
                }
            }
        }
        return null;
    }

    private Integer getOtherService(List<BusinessItem> businessItems,String businessItemName){

        if(null == businessItems || businessItems.size() == 0){
            return null;
        }
        for (BusinessItem businessItem : businessItems) {
            if(businessItem.getItemName().equals(businessItemName)){
                return businessItem.getId();
            }
        }
        return null;
    }


    private Map<String,Object> getBaseNumber(List<Organization> organizations,Integer organizationId){
        if(null == organizations || organizations.size() == 0){
            return null;
        }
        Map<String,Object> data = new HashMap<String, Object>();
        for (Organization organization : organizations) {
            if(organization.getId().equals(organizationId)){
                data.put("min",organization.getMinCardinalNumber());
                data.put("max",organization.getMaxCardinalNumber());
                return data;
            }
        }
        return null;
    }

    private Integer getSonBill(List<CompanySonBill> companySonBills,String companySonBillName,Integer companyId){
        if(null == companySonBills || companySonBills.size() == 0){
            return null;
        }
        for (CompanySonBill companySonBill : companySonBills) {

            if(companySonBillName.equals(companySonBill.getSonBillName()) && companySonBill.getCompanyId().equals(companyId)){
                return companySonBill.getId();
            }
        }
        return null;
    }

    private Map<String, Object>  getPayTheWays(List<PayTheWay> payTheWayList, Integer levelId) {

        if (null == payTheWayList || payTheWayList.size() == 0) {
            return null;
        }
        List<PayTheWay> tempList = new ArrayList<PayTheWay>();
        for (PayTheWay payTheWay : payTheWayList) {
            if (payTheWay.getInsuranceLevelId().equals(levelId)) {
                tempList.add(payTheWay);
            }
        }
        if (tempList.size() == 0) {
            return null;
        }
        Map<String, Object> result = new HashMap<String, Object>();
        Double min = null;
        Double max = null;
        List<Double> minList = new ArrayList<Double>();
        List<Double> maxList = new ArrayList<Double>();
        for (PayTheWay payTheWay : tempList) {
            if (null != payTheWay.getMeMinScope()) {
                minList.add(payTheWay.getMeMinScope());
            }
            if (null != payTheWay.getMeMaxScope()) {
                maxList.add(payTheWay.getMeMaxScope());
            }
        }
        if (minList.size() > 0) {
            Collections.sort(minList);
            min = minList.get(0);
        }
        if (maxList.size() > 0) {
            Collections.sort(maxList);
            max = maxList.get(maxList.size() - 1);
        }
        result.put("min", min);
        result.put("max", max);
        return result;
    }

    private Integer getInsuranceLevel(List<InsuranceLevel> insuranceLevels, String insuranceLevel, Integer payPlaceId) {
        if (null == insuranceLevels && insuranceLevels.size() == 0) {
            return null;
        }
        for (InsuranceLevel level : insuranceLevels) {
            if (insuranceLevel.equals(level.getLevelName()) && level.getPayPlaceId().equals(payPlaceId)) {
                return level.getId();
            }
        }
        return null;
    }

    private Integer getTransactor(List<Transactor> transactors, String transactorName, Integer organizationId) {

        if (null == transactors || transactors.size() == 0) {
            return null;
        }
        for (Transactor transactor : transactors) {
            if (transactorName.equals(transactor.getTransactorName()) && transactor.getOrganizationId().equals(organizationId)) {
                return transactor.getId();
            }
        }
        return null;
    }

    private Integer getOrganization(List<Organization> organizations, String organizationName, Integer payPlaceId) {

        if (null == organizations || organizations.size() == 0) {
            return null;
        }
        for (Organization organization : organizations) {
            if (organizationName.equals(organization.getOrganizationName()) && organization.getPayPlaceId().equals(payPlaceId)) {
                return organization.getId();
            }
        }
        return null;
    }

    private Integer getPayPlace(List<PayPlace> payPlaceList, String payPlaceName, Integer payPlaceType) {
        if (null == payPlaceList || payPlaceList.size() == 0) {
            return null;
        }
        for (PayPlace payPlace : payPlaceList) {
            if (payPlaceName.equals(payPlace.getPayPlaceName()) && payPlace.getType().equals(payPlaceType)) {
                return payPlace.getId();
            }
        }
        return null;
    }

    /**
     * 判断公司是否有此业务
     *
     * @param companyId
     * @param businessId
     * @return
     */
    private boolean checkCompanyBusiness(List<CompanyBusiness> companyBusinessList, Integer companyId, Integer businessId) {

        if (null == companyBusinessList || companyBusinessList.size() == 0) {
            return false;
        }
        for (CompanyBusiness business : companyBusinessList) {
            if (companyId.equals(business.getCompanyId()) && businessId.equals(business.getBusinessId())) {
                return true;
            }
        }
        return false;
    }

    private Integer getEducation(String education) {

        if ("博士".equals(education)) {
            return EducationEnum.Doctor.ordinal();
        } else if ("硕士".equals(education)) {
            return EducationEnum.Master.ordinal();
        } else if ("本科".equals(education)) {
            return EducationEnum.Undergraduate.ordinal();
        } else if ("大专".equals(education)) {
            return EducationEnum.JuniorCollege.ordinal();
        } else if ("高中及以下".equals(education)) {
            return EducationEnum.Senior.ordinal();
        } else {
            return null;
        }

    }


    private Integer getCity(List<City> cityList, String cityName) {
        if (null == cityList || cityList.size() == 0 || CommonUtil.isEmpty(cityName)) {
            return null;
        }
        for (City city : cityList) {
            if (cityName.equals(city.getName())) {
                return city.getId();
            }
        }
        return null;
    }

    private boolean isExistOfIdCard(List<Member> memberList, String idCard) {

        boolean isExist = false;
        if (null == memberList || memberList.size() == 0) {
            return isExist;
        }
        for (Member member : memberList) {
            if (idCard.equals(member.getCertificateNum())) {
                isExist = true;
                break;
            }
        }
        return isExist;
    }

    private Integer getCompany(List<Company> companyList, String companyName) {
        if (CommonUtil.isEmpty(companyName) || null == companyList || companyList.size() == 0) {
            return null;
        }
        for (Company company : companyList) {
            if (companyName.equals(company.getCompanyName())) {
                return company.getId();
            }
        }
        return null;
    }

    /**
     * 银行账务 上传对账表 暂时（工行）
     *
     * @param targetUrl
     * @param bankInfos 包含客服ID
     * @param flag   导入的模版类型 0:工行、1:成都银行、2:华夏银行、3:支付宝
     */
    @Transactional
    public Map<String,Integer> confirmMoneyRecord(String targetUrl, List<BankInfo> bankInfos, User currentUser,Integer flag) throws Exception {
        Map<String,Integer> map = new HashMap<String, Integer>();
        if (CommonUtil.isEmpty(targetUrl)) {
            return map;
        }
        String fileSuffix = null;
        if(targetUrl.endsWith(".xlsx")){
            fileSuffix = "xlsx";
        }
        else if(targetUrl.endsWith(".xls")){
            fileSuffix = "xls";
        }
        else{
            throw new InterfaceCommonException(StatusConstant.Fail_CODE, "文件格式不匹配");
        }
        int HttpResult; // 服务器返回的状态
        URL url = new URL(targetUrl); // 创建URL
        URLConnection urlConn = url.openConnection();
        urlConn.connect();
        HttpURLConnection httpConn = (HttpURLConnection) urlConn;
        HttpResult = httpConn.getResponseCode();
        if (HttpResult != HttpURLConnection.HTTP_OK)
            throw new InterfaceCommonException(StatusConstant.Fail_CODE, "数据读取失败");
        else {
            Set<ConfirmMoneyRecord> recordSet = new HashSet<ConfirmMoneyRecord>(); // 贷
            Set<ConfirmMoneyRecord> recordSetJ = new HashSet<ConfirmMoneyRecord>(); // 借
            List<Integer> companyIds = new ArrayList<Integer>(); // 匹配到的公司

            switch (flag){
                case 0 : // 工商银行
                    Map<Integer, List<String>> icbc = ExcelReader.readExcelContent(urlConn.getInputStream(), 2,fileSuffix,null);
                    buildICBC(currentUser, icbc, recordSet, recordSetJ,2);
                    break;
                case 1 : // 成都银行
                    Map<Integer, List<String>> cdb = ExcelReader.readExcelContent(urlConn.getInputStream(), 4);
                    buildCDB(currentUser, cdb, recordSet, recordSetJ,4);
                    break;
                case 2 : // 华夏银行
                    Map<Integer, List<String>> hxb = ExcelReader.readExcelContent(urlConn.getInputStream(), 1);
                    buildHXB(currentUser, hxb, recordSet, recordSetJ,1);
                    break;
                case 3 : // 支付宝
                    Map<Integer, List<String>> alipay = ExcelReader.readExcelContent(urlConn.getInputStream(), 3);
                    buildAliPay(currentUser, alipay, recordSet, recordSetJ,3);
                    break;
                default:
                    throw new InterfaceCommonException(StatusConstant.ARGUMENTS_EXCEPTION, "参数异常");
            }

            // set to list

            List<ConfirmMoneyRecord> recordList = new ArrayList<ConfirmMoneyRecord>(); // 贷
            List<ConfirmMoneyRecord> recordListJ = new ArrayList<ConfirmMoneyRecord>(); // 借

            Iterator<ConfirmMoneyRecord> iterator = recordSet.iterator();
//            Iterator<ConfirmMoneyRecord> iteratorJ = recordListJ.iterator();
            while (iterator.hasNext()){
                ConfirmMoneyRecord next = iterator.next();
                recordList.add(next);
            }
//            while (iteratorJ.hasNext()){
//                ConfirmMoneyRecord next = iteratorJ.next();
//                recordListJ.add(next);
//            }
            // 借款暂时不做处理，记录即可
            if(recordListJ.size() > 0){
                confirmMoneyRecordMapper.batchAddConfirmMoneyRecord(recordListJ);
            }
            // 通过收款信息 匹配公司
            List<ConfirmMoneyCompany> companyList = new ArrayList<ConfirmMoneyCompany>();
            List<ConfirmMoneyRecord> allRecords = confirmMoneyRecordMapper.queryAll();
            int repetitionCount = 0; // 重复次数
            if(null != allRecords && allRecords.size() > 0){
                Iterator<ConfirmMoneyRecord> iterator1 = recordList.iterator();
                while (iterator1.hasNext()){
                    ConfirmMoneyRecord next = iterator1.next();
                    if(allRecords.contains(next)){
                        iterator1.remove();
                        repetitionCount++;
                    }
                }
            }
            map.put("repetitionCount",repetitionCount);
            map.put("recordListSize",recordList.size());
            if (recordList.size() > 0) {
                confirmMoneyRecordMapper.batchAddConfirmMoneyRecord(recordList);

                List<ConfirmMoneyRecord> updateList = new ArrayList<ConfirmMoneyRecord>();
                for (ConfirmMoneyRecord record : recordList) {
                    List<ConfirmMoneyCompany> tempList = new ArrayList<ConfirmMoneyCompany>();
                    for (BankInfo bankInfo : bankInfos) {
                        if (record.getCompanyName().equals(bankInfo.getAccountName())
                                && record.getBankAccount().equals(bankInfo.getBankAccount())) {
                            ConfirmMoneyCompany moneyCompany = new ConfirmMoneyCompany();
                            moneyCompany.setCompanyId(bankInfo.getCompanyId());
                            moneyCompany.setConfirmMoneyRecordId(record.getId());
                            moneyCompany.setServiceId(bankInfo.getServiceId());
                            moneyCompany.setBankInfoId(bankInfo.getId());
                            companyIds.add(bankInfo.getCompanyId());
                            companyList.add(moneyCompany);
                            tempList.add(moneyCompany);
                        }
                    }
                    if(tempList.size() == 1){
                        // 已认款了
                        record.setStatus(1);
                        updateList.add(record);
                    }
                    record.setConfirmMoneyCompanyList(tempList);
                }
                // 通过匹配到的公司查询该公司下是否有还未认款处理的操作，如果还有未处理的认款，则不允许上传

                if(companyIds.size() > 0){
                    List<ConfirmFund> confirmFunds = confirmFundMapper.queryConfirmFundByHandleMethod(companyIds);
                    if(null != confirmFunds && confirmFunds.size() > 0){
                        StringBuffer sb = new StringBuffer("以下公司的账单还没有处理，无法上传一下公司的流水：");
                        for (ConfirmFund confirmFund : confirmFunds) {
                            sb.append(confirmFund.getCompanyName()+"、");
                        }
                        throw new InterfaceCommonException(StatusConstant.Fail_CODE,sb.toString().substring(0,sb.toString().length() - 1));
                    }
                }

                if (companyList.size() > 0) {
                    confirmMoneyCompanyMapper.batchAddConfirmMoneyCompany(companyList);
                }
                if(updateList.size() > 0){
                    confirmMoneyRecordMapper.update(updateList);
                }
            }
            // 批量查询统计公司下所有的应该认款的账单金额
            List<BillAmountOfCompany> billAmountOfCompanies = null;
            // 查询这些公司之前是否有无认款记录，如果有记录且没有处理方式，则合并账单金额处理
//            List<ConfirmFund> preRecords = null;
            if (companyIds.size() > 0) {
//                billAmountOfCompanies = companyMapper.countBillAmountByCompany(companyIds);
                billAmountOfCompanies = companySonTotalBillService.buildBillAmountOfCompany(companyIds);

            }
            List<ConfirmFundTotalBill> waitUpdate = new ArrayList<ConfirmFundTotalBill>();
            // 匹配完成之后，认款
            // 查看匹配到的公司信息 如果有两个及两个以上的公司被匹配，则不做处理
            // 否则 自动认款
            List<ConfirmFund> confirmFunds = new ArrayList<ConfirmFund>();

            // 判断记录里的所有数据，如果 银行帐号、开户名相同的 则合并成一条进行计算
            Map<String,ConfirmMoneyRecord> cc = new HashMap<String, ConfirmMoneyRecord>();
            for (int i = 0; i < recordList.size(); i++) {
                String key = recordList.get(i).getBankAccount()+"-"+recordList.get(i).getCompanyName();
                if(null != cc.get(key)){
                    ConfirmMoneyRecord record = cc.get(key);
                    record.setAmount(record.getAmount() + recordList.get(i).getAmount());
                    cc.put(key,record);
                }
                else{
                    cc.put(key,recordList.get(i));
                }
            }

            Set<String> keys = cc.keySet();
            recordList = new ArrayList<ConfirmMoneyRecord>();
            for (String key : keys) {
                recordList.add(cc.get(key));
            }
            for (ConfirmMoneyRecord record : recordList) {
                if (null != record.getConfirmMoneyCompanyList() && record.getConfirmMoneyCompanyList().size() > 1) {
                    // 如果有两个及两个以上的公司被匹配，则不做自动认款处理

                } else if (null != record.getConfirmMoneyCompanyList() && record.getConfirmMoneyCompanyList().size() == 1) {
                    // 如果仅有一个的公司被匹配，则做自动认款处理
                    List<Integer> temps = new ArrayList<Integer>();
                    ConfirmMoneyCompany moneyCompany = record.getConfirmMoneyCompanyList().get(0);
                    ConfirmFund confirmFund = new ConfirmFund();
                    confirmFund.setCompanyId(moneyCompany.getCompanyId());
                    confirmFund.setConfirmAmount(record.getAmount());
                    confirmFund.setConfirmMoneyRecordId(record.getId());
                    confirmFund.setConfirmMethod(0);

                    temps.add(moneyCompany.getCompanyId());


                    // 获取公司最新的一条有效追回欠款的记录
                    List<ConfirmFund> newConfirmFunds = confirmFundMapper.queryNewConfirmFundByCompany(temps);
                    List<ConfirmFund> confirmFunds_ = new ArrayList<ConfirmFund>(); // 追回欠款 处理后的数据
                    double debtAmount = 0.0;
                    List<Integer> companySonTotalBillIds = new ArrayList<Integer>();

                    if(null != newConfirmFunds && newConfirmFunds.size() > 0){

                        if (null != billAmountOfCompanies && billAmountOfCompanies.size() > 0) {
                            // 处理公司追回欠款的部分，优先把到款金额扣除
                            for (ConfirmFund newConfirmFund : newConfirmFunds) {
                                debtAmount += newConfirmFund.getBillAmount() - newConfirmFund.getConfirmAmount();
                                // 准备更新之前的账单，完成之前的账单核销差
                                for (ConfirmFundTotalBill bill : newConfirmFund.getConfirmFundTotalBillList()) {
                                    companySonTotalBillIds.add(bill.getCompanySonTotalBillId());
                                }
                                newConfirmFund.setHandleMethod(StatusConstant.HANDLEMETHOD_RECOVER_AMOUNT_HANDLE);
                                confirmFunds_.add(newConfirmFund);// 该条欠款记录已经被处理过
                            }
                            // 扣除上次欠款金额之后 在拿出去计算其他账单
                            confirmFund.setConfirmAmount(confirmFund.getConfirmAmount() - debtAmount);
                        }else{
                            //  如果当期没有账单记录，则通过上次追回欠款的记录重新构建记录
                            List<ConfirmFundTotalBill> fundTotalBills = confirmFundTotalBillMapper.queryConfirmFundTotalBillByCompanyId(companyIds);
                            if(null != fundTotalBills && fundTotalBills.size() > 0){
                                for (ConfirmFundTotalBill totalBill : fundTotalBills) {
                                    if (confirmFund.getCompanyId().equals(totalBill.getCompanyId())) {
                                        BillAmountOfCompany billAmountOfCompany = new BillAmountOfCompany();
                                        billAmountOfCompany.setCompanyId(totalBill.getCompanyId());
                                        billAmountOfCompany.setCompanySonTotalBillId(totalBill.getCompanySonTotalBillId());
                                        billAmountOfCompany.setBillAmount(totalBill.getAmount());
                                        if(null == billAmountOfCompanies){
                                            billAmountOfCompanies = new ArrayList<BillAmountOfCompany>();
                                        }
                                        billAmountOfCompanies.add(billAmountOfCompany);
                                        waitUpdate.add(totalBill);
                                    }
                                }
                            }
                            for (ConfirmFund newConfirmFund : newConfirmFunds) {
                                // 准备更新之前的账单，完成之前的账单核销差
                                newConfirmFund.setHandleMethod(StatusConstant.HANDLEMETHOD_RECOVER_AMOUNT_HANDLE);
                                confirmFunds_.add(newConfirmFund);// 该条欠款记录已经被处理过
                            }
                        }
                    }
                    // 更新处理过的记录
                    if(confirmFunds_.size() > 0){
                        confirmFundMapper.batchUpdateHandleMethod(confirmFunds_);
                    }
                    if(companySonTotalBillIds.size() > 0){
                        // 处理 未核销的总账单
                        List<CompanySonTotalBill> waitUpdateBill = companySonTotalBillMapper.queryCompanySonTotalBillByNo(companySonTotalBillIds);
                        if(null != waitUpdateBill && waitUpdateBill.size() > 0){
                            for (int i = 0; i < waitUpdateBill.size(); i++) {
                                waitUpdateBill.get(i).setBalanceOfCancelAfterVerification(0.0);
                                waitUpdateBill.get(i).setIsBalanceOfCancelAfterVerification(Common.YES.ordinal());
                                waitUpdateBill.get(i).setAfterVerificationTime(new Date());
                            }
                            companySonTotalBillMapper.batchUpdate(waitUpdateBill);
                        }
                    }

                    double totalAmount = 0.0;
                    List<ConfirmFundTotalBill> totalBills = new ArrayList<ConfirmFundTotalBill>();
                    if (null != billAmountOfCompanies && billAmountOfCompanies.size() > 0) {
                        for (BillAmountOfCompany amountOfCompany : billAmountOfCompanies) {
                            if (moneyCompany.getCompanyId().equals(amountOfCompany.getCompanyId())) {
                                totalAmount += amountOfCompany.getBillAmount();
                                ConfirmFundTotalBill bi = new ConfirmFundTotalBill();
                                bi.setCompanySonTotalBillId(amountOfCompany.getCompanySonTotalBillId());
                                bi.setAmount(amountOfCompany.getBillAmount());
                                totalBills.add(bi);
//                                break;
                            }
                        }
                        confirmFund.setConfirmFundTotalBillList(totalBills);
                        confirmFund.setBillAmount(totalAmount);
                    }
                    if (null != confirmFund.getBillAmount() && confirmFund.getBillAmount().equals(confirmFund.getConfirmAmount())) {
                        confirmFund.setHandleMethod(StatusConstant.HANDLEMETHOD_NO_HANDLE);
                        // 更新总账单状态
                        if(totalBills.size() > 0){
                            List<CompanySonTotalBill> totalBillList = new ArrayList<CompanySonTotalBill>();
                            for (ConfirmFundTotalBill totalBill : totalBills) {
                                CompanySonTotalBill temp = new CompanySonTotalBill();
                                temp.setId(totalBill.getCompanySonTotalBillId());
                                temp.setIsBalanceOfCancelAfterVerification(Common.YES.ordinal());
                                temp.setBalanceOfCancelAfterVerification(0.0);
                                totalBillList.add(temp);
                            }
                            // 如果不做处理，更新此阶段的全部总账单
                            // 通过 公司 查询所有待核销总账单 并更新它们
                            List<CompanySonTotalBill> bills = companySonTotalBillMapper.queryTotalBillByCompany(confirmFund.getCompanyId());
                            if(null != bills && bills.size() > 0){
                                for (CompanySonTotalBill bill : bills) {
                                    CompanySonTotalBill temp = new CompanySonTotalBill();
                                    temp.setId(bill.getId());
                                    temp.setIsBalanceOfCancelAfterVerification(Common.YES.ordinal());
                                    temp.setBalanceOfCancelAfterVerification(0.0);
                                    totalBillList.add(temp);
                                }
                            }
                            companySonTotalBillMapper.batchUpdate(totalBillList);
                        }
                        // 自动认款后，自动开票 开始
                        List<Integer> companySonTotalBillIdsOfMakeBill = new ArrayList<Integer>(); // 当前认款后 管理的总账单ID
                        if(null != confirmFund.getConfirmFundTotalBillList() && confirmFund.getConfirmFundTotalBillList().size() > 0){
                            for (ConfirmFundTotalBill bill : confirmFund.getConfirmFundTotalBillList()) {
                                if(null != bill.getCompanySonTotalBillId()){
                                    companySonTotalBillIdsOfMakeBill.add(bill.getCompanySonTotalBillId());
                                }
                            }
                        }
                        if(companySonTotalBillIdsOfMakeBill.size() > 0){
                            List<MakeBill> makeBills = makeBillHelper.getMakeBill(companySonTotalBillIdsOfMakeBill, confirmFund.getId(), new Date());
                            if(makeBills.size() > 0){
                                List<MakeBill> others = new ArrayList<MakeBill>();
                                // 判断开票金额的数量是否大于配置的10W，如果大于等于10W则拆分成两张
                                Iterator<MakeBill> iterator_ = makeBills.iterator();
                                while (iterator_.hasNext()){
                                    MakeBill next = iterator_.next();
                                    if(next.getAmountOfBill() > StatusConstant.BILL_AMOUNT){
                                        List<MakeBill> bills = makeBillHelper.splitMakeBill(next);
                                        if(bills.size() > 0){
                                            others.addAll(bills);
                                        }
                                        iterator_.remove();
                                    }
                                }
                                if(others.size() > 0){
                                    makeBills.addAll(others);
                                }
                                makeBillMapper.batchAddMakeBill(makeBills);
                            }
                        }
                        // 自动认款后，自动开票 开始
                    }
                    confirmFunds.add(confirmFund);
                }
            }
            if (confirmFunds.size() > 0) {
                // 新增
                confirmFundMapper.batchAddConfirmFund(confirmFunds);
                List<ConfirmFundTotalBill> temp = new ArrayList<ConfirmFundTotalBill>();
                for (ConfirmFund confirmFund : confirmFunds) {
                    if (null != confirmFund.getConfirmFundTotalBillList()) {
                        for (ConfirmFundTotalBill bill : confirmFund.getConfirmFundTotalBillList()) {
                            bill.setConfirmFundId(confirmFund.getId());
                            temp.add(bill);
                        }
                    }
                    for (ConfirmFundTotalBill confirmFundTotalBill : waitUpdate) {
                        confirmFundTotalBill.setStatus(Common.YES.ordinal());
                        confirmFundTotalBill.setConfirmFundId(confirmFund.getId());
                    }
                }
                if (temp.size() > 0) {
                    confirmFundTotalBillMapper.batchAddConfirmFundTotalBill(temp);
                }
            }
            if(waitUpdate.size() > 0){
                confirmFundTotalBillMapper.del(waitUpdate);
            }
        }
        return map;
    }

    private void buildAliPay(User currentUser, Map<Integer, List<String>> alipay, Set<ConfirmMoneyRecord> recordList, Set<ConfirmMoneyRecord> recordListJ,int index) {
        for (Integer cellNum : alipay.keySet()) {
            List<String> values = alipay.get(cellNum);// 行数据
            ConfirmMoneyRecord record = new ConfirmMoneyRecord();
            if (CommonUtil.isEmpty(values.get(12))){
                continue;
            }
            record.setBankAccount(values.get(12));
            record.setTransactionTime(DateUtil.stringToDate(values.get(1)));
            record.setDigest(values.get(16));
            record.setCompanyName(values.get(13));
            record.setRemark(values.get(16));
            record.setResource(StatusConstant.ALIPAY);
            record.setCreateUserId(currentUser.getId());
            if(!CommonUtil.isEmpty(values.get(6))){
                record.setAmount(getDouble(values.get(6)));
                recordList.add(record);
            }
            else if(!CommonUtil.isEmpty(values.get(7))){
                // 支出
//                record.setAmount(getDouble(values.get(7)));
//                recordListJ.add(record);
            }
            else{
                throw new InterfaceCommonException(StatusConstant.FIELD_NOT_NULL,"第"+index+"行金额数据错误");
            }
            index++;
        }


    }

    private void buildHXB(User currentUser, Map<Integer, List<String>> hxb, Set<ConfirmMoneyRecord> recordList, Set<ConfirmMoneyRecord> recordListJ,int index) {
        for (Integer cellNum : hxb.keySet()) {
            List<String> values = hxb.get(cellNum);// 行数据
            ConfirmMoneyRecord record = new ConfirmMoneyRecord();
            record.setBankAccount(values.get(7));
            record.setTransactionTime(DateUtil.getDate(values.get(0)));
            record.setDigest(values.get(6));
            record.setCompanyName(values.get(8));
            record.setRemark(values.get(1));
            record.setResource(StatusConstant.HXB);
            record.setCreateUserId(currentUser.getId());
            if(!CommonUtil.isEmpty(values.get(3))){
                record.setAmount(getDouble(values.get(3)));
                recordList.add(record);
            }
            else if(!CommonUtil.isEmpty(values.get(2))){
                // 支出
//                record.setAmount(getDouble(values.get(2)));
//                recordListJ.add(record);
            }
            else{
                throw new InterfaceCommonException(StatusConstant.FIELD_NOT_NULL,"第"+index+"行金额数据错误");
            }
            index++;
        }
    }

    private void buildCDB(User currentUser, Map<Integer, List<String>> map, Set<ConfirmMoneyRecord> recordList, Set<ConfirmMoneyRecord> recordListJ,int index)
        throws Exception{
        for (Integer cellNum : map.keySet()) {
            List<String> values = map.get(cellNum);// 行数据
            ConfirmMoneyRecord record = new ConfirmMoneyRecord();
            record.setBankAccount(getStringNumber(values.get(6)));
            SimpleDateFormat simple = new SimpleDateFormat("yyyyMMdd");
            record.setTransactionTime(simple.parse(values.get(0)));
            record.setDigest(values.get(8));
            record.setCompanyName(values.get(7));
            record.setResource(StatusConstant.CDB);
//            record.setRemark(values.get(11));
            record.setCreateUserId(currentUser.getId());
            if(!CommonUtil.isEmpty(values.get(3))){
                record.setAmount(getDouble(values.get(3)));
                recordList.add(record);
            }
            else if(!CommonUtil.isEmpty(values.get(4))){
                // 支出
//                record.setAmount(getDouble(values.get(4)));
//                recordListJ.add(record);
            }
            else{
                throw new InterfaceCommonException(StatusConstant.FIELD_NOT_NULL,"第"+index+"行金额数据错误");
            }
            index++;
        }
    }

    private void buildICBC(User currentUser, Map<Integer, List<String>> map, Set<ConfirmMoneyRecord> recordList, Set<ConfirmMoneyRecord> recordListJ,int index) throws IOException {

        for (Integer cellNum : map.keySet()) {
            List<String> values = map.get(cellNum);// 行数据
            ConfirmMoneyRecord record = new ConfirmMoneyRecord();
            record.setResource(StatusConstant.ICBC);
            record.setBankAccount(getStringNumber(values.get(2)));
            record.setTransactionTime(DateUtil.stringToDate(values.get(3)));
            record.setDigest(values.get(8));
            record.setCompanyName(values.get(10));
            record.setRemark(values.get(11));
            record.setCreateUserId(currentUser.getId());
            if("贷".equals(values.get(4))){
                // 贷
                if (!CommonUtil.isEmpty(values.get(6))) {
                    record.setAmount(getDouble(values.get(6)));
                    recordList.add(record);
                }
                else{
                    throw new InterfaceCommonException(StatusConstant.FIELD_NOT_NULL,"第"+index+"行金额数据错误");
                }
            }
            else if("借".equals(values.get(4))){
                // 借
//                if (!CommonUtil.isEmpty(values.get(5))) {
//                    record.setAmount(getDouble(values.get(5)));
//                    recordListJ.add(record);
//                }
//                else{
//                    throw new InterfaceCommonException(StatusConstant.FIELD_NOT_NULL,"第"+index+"行金额数据错误");
//                }
            }else{

                if((!CommonUtil.isEmpty(values.get(6)) && !CommonUtil.isEmpty(values.get(5))) ||
                        (CommonUtil.isEmpty(values.get(6)) && CommonUtil.isEmpty(values.get(5)))){
                    throw new InterfaceCommonException(StatusConstant.FIELD_NOT_NULL,"第"+index+"行借贷数据错误");
                }
                if(!CommonUtil.isEmpty(values.get(6))){
                    record.setAmount(getDouble(values.get(6)));
                    recordList.add(record);
                }
                else if(!CommonUtil.isEmpty(values.get(5))){
                    record.setAmount(getDouble(values.get(5)));
                    recordList.add(record);
                }
            }
            index++;
        }
    }


    /**
     * 成都社保实做数据读取 稽核用
     *
     * @param targetUrl 数据URL
     */
    public Map<String, Object> socialSecurityImplements(String targetUrl) throws Exception {
        Map<String, Object> finalResult = new HashMap<String, Object>();
        List<SocialSecurityImportData> list = new ArrayList<SocialSecurityImportData>();
        Map<String, SocialSecurityImportData> result = new HashMap<String, SocialSecurityImportData>();
        Set<Date> month = new HashSet<Date>();
        Set<String> idCardSet = new HashSet<String>();
        if (CommonUtil.isEmpty(targetUrl)) {
            return null;
        }
        int HttpResult; // 服务器返回的状态
        URL url = new URL(targetUrl); // 创建URL
        URLConnection urlConn = url.openConnection();
        urlConn.connect();
        HttpURLConnection httpConn = (HttpURLConnection) urlConn;
        HttpResult = httpConn.getResponseCode();
        if (HttpResult != HttpURLConnection.HTTP_OK)
            throw new InterfaceCommonException(StatusConstant.Fail_CODE, "数据读取失败");
        else {

            Map<Integer, List<String>> map = ExcelReader.readExcelContent(urlConn.getInputStream(), 1);
            List<String> idCards = new ArrayList<String>();

            List<Member> members = memberMapper.queryAllMemberIdCard();

            int index = 1;
            for (Integer cellNum : map.keySet()) {
                List<String> values = map.get(cellNum);// 行数据
                SocialSecurityImportData data = new SocialSecurityImportData();
                String s = getStringNumber(values.get(0));
                if(CommonUtil.isEmpty(s)){
                    throw new InterfaceCommonException(StatusConstant.Fail_CODE, "第"+index+"行的社保编号错误");
                }
                data.setSocialSecurityNumber(s);
                String idCard = getStringNumber(values.get(2));
                if(CommonUtil.isEmpty(idCard)){
                    throw new InterfaceCommonException(StatusConstant.Fail_CODE, "第"+index+"行的证件号错误");
                }
                Member member = getMember(members,idCard);
                if(null == member){
                    index++;
                    continue;
//                    throw new InterfaceCommonException(StatusConstant.Fail_CODE, "第"+index+"行的证件号不存在");
                }
                // 判断有没有社保业务
                if(null == member.getBusinessList() || member.getBusinessList().size() == 0){
                    index++;
                    continue;
//                    throw new InterfaceCommonException(StatusConstant.Fail_CODE, "第"+index+"行的员工没有社保业务");
                }
                boolean isExist = false;
                for (Business business : member.getBusinessList()) {
                    if(BusinessEnum.sheBao.ordinal() == business.getId()){
                        isExist = true;
                    }
                }
                if(!isExist){
                    index++;
                    continue;
//                    throw new InterfaceCommonException(StatusConstant.Fail_CODE, "第"+index+"行的员工没有社保业务");
                }

                data.setIdCard(idCard);
                data.setUserName(values.get(3));
                SimpleDateFormat simple = new SimpleDateFormat("yyyyMM");
                data.setMonth(simple.parse(values.get(5)));
                data.setCompanyCountPrice(getDouble(values.get(7)) + getDouble(values.get(9)) + getDouble(values.get(10)));
                data.setPersonCountPrice(getDouble(values.get(8)));

                data.setProvisionCardinal(getDouble(values.get(13)));
                data.setProvisionCompanyRatio(getDouble(values.get(14)));
                data.setProvisionCompanyPrice(getDouble(values.get(15)) + getDouble(values.get(18)) + getDouble(values.get(19)));
                data.setProvisionSelfRatio(getDouble(values.get(16)));
                data.setProvisionSelfPrice(getDouble(values.get(17)));

                data.setMedicalCardinal(getDouble(values.get(31)));
                data.setMedicalCompanyRatio(getDouble(values.get(32)) );
                data.setMedicalCompanyPrice(getDouble(values.get(33)) + getDouble(values.get(38)) + getDouble(values.get(39)));
                data.setMedicalSelfRatio(getDouble(values.get(36)));
                data.setMedicalSelfPrice(getDouble(values.get(37)));

                data.setBearCardinal(getDouble(values.get(56)));
                data.setBearCompanyRatio(getDouble(values.get(57)));
                data.setBearCompanyPrice(getDouble(values.get(58)) + getDouble(values.get(61)));


                data.setWorkCardinal(getDouble(values.get(22)));
                data.setWorkCompanyRatio(getDouble(values.get(23)));
                data.setWorkCompanyPrice(getDouble(values.get(24)) + getDouble(values.get(27)) + getDouble(values.get(28)));
                data.setWorkPersonRatio(getDouble(values.get(25)));
                data.setWorkPersonPrice(getDouble(values.get(26)));

                data.setInjuryCardinal(getDouble(values.get(50)));
                data.setInjuryCompanyRatio(getDouble(values.get(51)));
                data.setInjuryCompanyPrice(getDouble(values.get(52)) + getDouble(values.get(53)));

//                data.setOrganCardinal(getDouble(values.get(50)));
//                data.setOrganCompanyRatio(getDouble(values.get(51)));
//                data.setOrganCompanyPrice(getDouble(values.get(52)));
//                data.setOrganSelfRatio(getDouble(values.get(53)));
//                data.setOrganSelfPrice(getDouble(values.get(54)));

                data.setIllnessCardinal(getDouble(values.get(42)));
                data.setIllnessCompanyRatio(getDouble(values.get(43)));
                data.setIllnessCompanyPrice(getDouble(values.get(44)) + getDouble(values.get(47)));
                result.put(data.getIdCard() + Timestamp.timesTamp2(data.getMonth(),"yyyy-MM").toString(), data);
                list.add(data);
                idCards.add(data.getIdCard());
                if (null != data.getMonth()) {
                    month.add(data.getMonth());
                }
                index++;
            }
            if(idCards.size() > 0){
                List<MemberBusinessItem> itemList = memberBusinessItemMapper.queryMemberBusinessItemByMembers(idCards);
                if(null != itemList && itemList.size() > 0 && list.size() > 0){
                    for (MemberBusinessItem item : itemList) {
                        for (SocialSecurityImportData data : list) {
                            if(data.getIdCard().equals(item.getCertificateNum())){
                                data.setPayPlaceName(item.getPayPlaceName());
                                data.setOrganizationName(item.getOrganizationName());
                                data.setTransactorName(item.getTransactorName());
                                data.setLevelName(item.getInsuranceLevelName());
                                data.setBaseNumber(item.getBaseNumber());
                                data.setPayPlaceId(item.getPayPlaceId());
                            }
                        }
                    }
                }
                idCardSet.addAll(idCards);
            }
        }

        finalResult.put("idCards", idCardSet);
        finalResult.put("month", month);
        finalResult.put("dataMap", result);
        finalResult.put("dataList", list);
        return finalResult;
    }

    private Member getMember(List<Member> members, String idCard) {

        if(null == members || members.size() == 0){
            return null;
        }
        for (Member member : members) {
            if(idCard.equals(member.getCertificateNum())){
                return member;
            }
        }
        return null;
    }


    /**
     * 社保稽核 通用模版数据读取
     * @param targetUrl
     * @throws Exception
     */
    public Map<String, Object> commonAuditOfSocialSecurity(String targetUrl) throws Exception{
        List<SocialSecurityImportData> dataList = new ArrayList<SocialSecurityImportData>();
        Map<String, Object> finalResult = new HashMap<String, Object>();
        Map<String, SocialSecurityImportData> result = new HashMap<String, SocialSecurityImportData>();
        Set<Date> month = new HashSet<Date>();
        Set<String> idCardSet = new HashSet<String>();
        int HttpResult; // 服务器返回的状态
        URL url = new URL(targetUrl); // 创建URL
        URLConnection urlConn = url.openConnection();
        urlConn.connect();
        HttpURLConnection httpConn = (HttpURLConnection) urlConn;
        HttpResult = httpConn.getResponseCode();
        if (HttpResult != HttpURLConnection.HTTP_OK)
            throw new InterfaceCommonException(StatusConstant.Fail_CODE, "数据读取失败");
        else {
            Map<Integer, List<String>> map = ExcelReader.readExcelContent(urlConn.getInputStream(), 0);
            List<PayPlace> payPlaces = payPlaceMapper.queryPayPlaceByType(InsuranceType.SheBao.ordinal());
            List<Member> members = memberMapper.queryAllMemberIdCard();
            List<String> titles = new ArrayList<String>();
            int index = 2;
            for (Integer cellNum : map.keySet()) {
                List<String> values = map.get(cellNum);// 行数据
                if(titles.size() == 0){
                    titles.addAll(values); // 装配第一行 标题
                }
                SocialSecurityImportData data = new SocialSecurityImportData();
                String s = getStringNumber(values.get(0));
                if(CommonUtil.isEmpty(s)){
                    throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST,"第" + index + "行数据的社保编号错误");
                }
                data.setSocialSecurityNumber(s);

                String idCard = getStringNumber(values.get(1));
                if(CommonUtil.isEmpty(idCard)){
                    throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST,"第" + index + "行数据的证件号码错误");
                }
                Member member = getMember(members,idCard);
                if(null == member){
                    index++;
                    continue;
//                    throw new InterfaceCommonException(StatusConstant.Fail_CODE, "第"+index+"行的证件号不存在");
                }
                // 判断有没有社保业务
                if(null == member.getBusinessList() || member.getBusinessList().size() == 0){
                    index++;
                    continue;
//                    throw new InterfaceCommonException(StatusConstant.Fail_CODE, "第"+index+"行的员工没有社保业务");
                }
                boolean isExist = false;
                for (Business business : member.getBusinessList()) {
                    if(BusinessEnum.sheBao.ordinal() == business.getId()){
                        isExist = true;
                    }
                }
                if(!isExist){
                    index++;
                    continue;
//                    throw new InterfaceCommonException(StatusConstant.Fail_CODE, "第"+index+"行的员工没有社保业务");
                }

                data.setIdCard(idCard);
                if(CommonUtil.isEmpty(values.get(2))){
                    throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST,"第" + index + "行数据的姓名错误");
                }
                data.setUserName(values.get(2));
                if(CommonUtil.isEmpty(values.get(3))){
                    throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST,"第" + index + "行数据的服务起始月错误");
                }
                SimpleDateFormat simple = new SimpleDateFormat("yyyyMM");
                data.setMonth(simple.parse(values.get(3)));

                if(CommonUtil.isEmpty(values.get(5))){
                    // 账单起始月
                    throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST,"第" + index + "行数据的账单起始月错误");
                }
                data.setBillStartMonth(simple.parse(values.get(5)));
                if(CommonUtil.isEmpty(values.get(4))){
                    // 服务结束月
                    data.setServiceEndMonth(simple.parse(values.get(4)));
                }

                if(CommonUtil.isEmpty(values.get(6))){
                    throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST,"第" + index + "行数据的缴金地错误");
                }

                Integer payPlace = getPayPlace(payPlaces, values.get(6), InsuranceType.SheBao.ordinal());
                if(null == payPlace){
                    throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST,"第" + index + "行数据的缴金地不存在");
                }
                data.setPayPlaceId(payPlace);
                data.setPayPlaceName(values.get(6));
                if(CommonUtil.isEmpty(values.get(7))){
                    throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST,"第" + index + "行数据的经办机构错误");
                }
                data.setOrganizationName(values.get(7));
                if(CommonUtil.isEmpty(values.get(8))){
                    throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST,"第" + index + "行数据的办理方错误");
                }
                data.setTransactorName(values.get(8));
                if(CommonUtil.isEmpty(values.get(9))){
                    throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST,"第" + index + "行数据的档次错误");
                }
                data.setLevelName(values.get(9));
                if(CommonUtil.isEmpty(values.get(10))){
                    throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST,"第" + index + "行数据的基数错误");
                }
                data.setBaseNumber(getDouble(values.get(10)));

                // 动态装配险种
                if(titles.size() - 11 > 0){
                    // 有其他险种
                    List<SocialSecurityCommon> commonList = new ArrayList<SocialSecurityCommon>();
                    for (int i = 11; i < titles.size(); i+=2) {
                        SocialSecurityCommon common = new SocialSecurityCommon();
                        common.setInsuranceName(titles.get(i).substring(2,titles.get(i).length()));
                        common.setCompanyPrice(getDouble(values.get(i)));
                        common.setPersonPrice(getDouble(values.get(i+1)));
                        commonList.add(common);
                    }
                    data.setSocialSecurityCommonList(commonList);
                }

//                data.setProvisionCompanyPrice(getDouble(values.get(9)));   // 公司养老
//                data.setMedicalCompanyPrice(getDouble(values.get(10)));    // 公司医疗
//                data.setWorkCompanyPrice(getDouble(values.get(11)));       // 公司失业
//                data.setInjuryCompanyPrice(getDouble(values.get(12)));     // 公司工伤
//                data.setIllnessCompanyPrice(getDouble(values.get(13)));    // 公司大病
//                data.setResidualCompanyPrice(getDouble(values.get(14)));   // 公司残保金
//
//                data.setProvisionSelfPrice(getDouble(values.get(15)));     // 个人养老
//                data.setMedicalSelfPrice(getDouble(values.get(16)));       // 个人医疗
//                data.setWorkPersonPrice(getDouble(values.get(17)));        // 个人失业
//                data.setInjurySelfPrice(getDouble(values.get(18)));        // 个人工伤
//                data.setIllnessSelfPrice(getDouble(values.get(19)));       // 个人大病
//                data.setResidualSelfPrice(getDouble(values.get(20)));      // 个人残保金

                dataList.add(data);
                result.put(data.getIdCard() + Timestamp.timesTamp2(data.getMonth(),"yyyy-MM").toString(), data);
                idCardSet.add(data.getIdCard());
                month.add(data.getMonth());
            }
        }
        finalResult.put("idCards", idCardSet);
        finalResult.put("month", month);
        finalResult.put("dataMap", result);
        finalResult.put("dataList", dataList);
        return finalResult;
    }



    /**
     * 公积金稽核 通用模版数据读取
     * @param targetUrl
     * @throws Exception
     */
    public Map<String, Object> commonAuditOfReservedFunds(String targetUrl) throws Exception{

        int HttpResult; // 服务器返回的状态
        URL url = new URL(targetUrl); // 创建URL
        URLConnection urlConn = url.openConnection();
        urlConn.connect();
        HttpURLConnection httpConn = (HttpURLConnection) urlConn;
        HttpResult = httpConn.getResponseCode();
        List<ReservedFundImportData> dataList = new ArrayList<ReservedFundImportData>();

        Map<String, Object> finalResult = new HashMap<String, Object>();
        Map<String, ReservedFundImportData> result = new HashMap<String, ReservedFundImportData>();
        Set<Date> month = new HashSet<Date>();
        Set<String> idCardSet = new HashSet<String>();

        if (HttpResult != HttpURLConnection.HTTP_OK)
            throw new InterfaceCommonException(StatusConstant.Fail_CODE, "数据读取失败");
        else {
            Map<Integer, List<String>> map = ExcelReader.readExcelContent(urlConn.getInputStream(), 1);
            List<Member> members = memberMapper.queryAllMemberIdCard();
            int index = 2;
            for (Integer cellNum : map.keySet()) {
                List<String> values = map.get(cellNum);// 行数据
                ReservedFundImportData data = new ReservedFundImportData();

                data.setCustomerNumber(getStringNumber(values.get(0)));

                String idCard = getStringNumber(values.get(1));
                if(CommonUtil.isEmpty(idCard)){
                    throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST,"第" + index + "行数据的证件号码错误");
                }
                Member member = getMember(members,idCard);
                if(null == member){
                    throw new InterfaceCommonException(StatusConstant.Fail_CODE, "第"+index+"行的证件号不存在");
                }
                // 判断有没有业务
                if(null == member.getBusinessList() || member.getBusinessList().size() == 0){
                    throw new InterfaceCommonException(StatusConstant.Fail_CODE, "第"+index+"行的员工没有公积金业务");
                }
                boolean isExist = false;
                for (Business business : member.getBusinessList()) {
                    if(BusinessEnum.gongJiJin.ordinal() == business.getId()){
                        isExist = true;
                    }
                }
                if(!isExist){
                    throw new InterfaceCommonException(StatusConstant.Fail_CODE, "第"+index+"行的员工没有公积金业务");
                }
                data.setIdCard(idCard);
                if(CommonUtil.isEmpty(values.get(2))){
                    throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST,"第" + index + "行数据的姓名错误");
                }
                data.setUserName(values.get(2));
                if(CommonUtil.isEmpty(values.get(3))){
                    throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST,"第" + index + "行数据的服务起始月错误");
                }
                data.setServiceMonth(DateUtil.strToDate(values.get(3),"yyyyMM"));

                if(CommonUtil.isEmpty(values.get(5))){
                    // 账单起始月
                    throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST,"第" + index + "行数据的账单起始月错误");
                }
                SimpleDateFormat simple = new SimpleDateFormat("yyyyMM");
                data.setBillStartMonth(simple.parse(values.get(5)));
                if(CommonUtil.isEmpty(values.get(4))){
                    // 服务结束月
                    data.setServiceEndMonth(simple.parse(values.get(4)));
                }


                if(CommonUtil.isEmpty(values.get(6))){
                    throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST,"第" + index + "行数据的缴金地错误");
                }
                data.setPayPlaceName(values.get(6));
                if(CommonUtil.isEmpty(values.get(7))){
                    throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST,"第" + index + "行数据的经办机构错误");
                }
                data.setOrganizationName(values.get(7));
                if(CommonUtil.isEmpty(values.get(8))){
                    throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST,"第" + index + "行数据的办理方错误");
                }
                data.setTransactorName(values.get(8));
                if(CommonUtil.isEmpty(values.get(9))){
                    throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST,"第" + index + "行数据的比例错误");
                }
                data.setPayRatio(getDouble(values.get(9)));
                if(CommonUtil.isEmpty(values.get(10))){
                    throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST,"第" + index + "行数据的基数错误");
                }
                data.setPayCardinalNumber(getDouble(values.get(10)));
                // .. 其他业务
                data.setCompanyTotalPay(getDouble(values.get(11)));
                data.setMemberTotalPay(getDouble(values.get(12)));
                dataList.add(data);
                result.put(data.getIdCard() + Timestamp.timesTamp2(data.getServiceMonth(),"yyyy-MM").toString(), data);
                idCardSet.add(data.getIdCard());
                month.add(data.getServiceMonth());
            }
        }
        finalResult.put("idCards", idCardSet);
        finalResult.put("month", month);
        finalResult.put("dataMap", result);
        finalResult.put("dataList", dataList);
        return finalResult;

    }



    private double getDouble(String s) {
        if (CommonUtil.isEmpty(s)) {
            return 0.0;
        }
        return Double.parseDouble(s.replaceAll(",",""));
    }







    /**
     * 社保反馈表 解析业务
     *
     * @param targetUrl 文件
     * @param companyId 公司ID
     * @param flag      人员参保 0  人员停保  1
     */
    public List<Feedback> socialSecurityFeedbackReport(String targetUrl, Integer companyId, Integer flag) throws Exception {
        List<Feedback> feedbackList = new ArrayList<Feedback>();
        if (CommonUtil.isEmpty(targetUrl) || null == flag) {
            return feedbackList;
        }
        int HttpResult; // 服务器返回的状态
        URL url = new URL(targetUrl); // 创建URL
        URLConnection urlConn = url.openConnection();
        urlConn.connect();

        HttpURLConnection httpConn = (HttpURLConnection) urlConn;
        HttpResult = httpConn.getResponseCode();
        if (HttpResult != HttpURLConnection.HTTP_OK)
            throw new InterfaceCommonException(StatusConstant.Fail_CODE, "数据读取失败");
        else {
            Map<Integer, List<String>> map = ExcelReader.readExcelContent(urlConn.getInputStream(), 1);
            List<List<String>> allData = new ArrayList<List<String>>(); // 整个表的数据
            for (Integer cellNum : map.keySet()) {
                List<String> values = map.get(cellNum);// 行数据
                allData.add(values);
            }

            // 人员参保反馈
            if (allData.size() > 0) {
                for (List<String> values : allData) {
                    // 行数据
                    Feedback feedback = new Feedback();
                    feedback.setCertificateNumber(getStringNumber(values.get(0)));
                    feedback.setUserName(values.get(1));
                    feedback.setServiceDate(DateUtil.getDate(values.get(2)));
                    feedback.setFlag(flag);
                    if (flag == 1) {
                        // 停保
                        if ("处理成功".equals(values.get(4))) {
                            feedback.setIsSuccess(1);
                        } else if ("已申报待上传".equals(values.get(4))) {
                            feedback.setIsSuccess(2);
                        } else if ("已上传待审核".equals(values.get(4))) {
                            feedback.setIsSuccess(3);
                        } else {
                            feedback.setIsSuccess(0);
                        }
                        feedback.setReason(values.get(5));
                    } else {
                        feedback.setReason(values.get(6));
                        if ("处理成功".equals(values.get(7))) {
                            feedback.setIsSuccess(1);
                        } else if ("已申报待上传".equals(values.get(7))) {
                            feedback.setIsSuccess(2);
                        } else if ("已上传待审核".equals(values.get(7))) {
                            feedback.setIsSuccess(3);
                        } else {
                            feedback.setIsSuccess(0);
                        }
                        feedback.setRemark(values.get(9));
                    }
                    feedbackList.add(feedback);
                }
            }
            return feedbackList;
        }
    }



    /**
     * 增减变反馈表 解析业务 通用模版
     *
     * @param targetUrl 文件
     */
    public void commonFeedbackReport(String targetUrl,User user) throws Exception {

        int HttpResult; // 服务器返回的状态
        URL url = new URL(targetUrl); // 创建URL
        URLConnection urlConn = url.openConnection();
        urlConn.connect();
        HttpURLConnection httpConn = (HttpURLConnection) urlConn;
        HttpResult = httpConn.getResponseCode();
        if (HttpResult != HttpURLConnection.HTTP_OK)
            throw new InterfaceCommonException(StatusConstant.Fail_CODE, "数据读取失败");
        else {
            Map<Integer, List<String>> map = ExcelReader.readExcelContent(urlConn.getInputStream(), 1);
            List<List<String>> allData = new ArrayList<List<String>>(); // 整个表的数据
            for (Integer cellNum : map.keySet()) {
                List<String> values = map.get(cellNum);// 行数据
                String s = values.get(16);
                if(!"成功".equals(s) && !"失败".equals(s)){
                    throw new InterfaceCommonException(StatusConstant.Fail_CODE,"办理结果错误");
                }
                allData.add(values);
            }

            List<CommonTransact> commonTransacts = memberBusinessUpdateRecordItemMapper.queryCommonTransactByItem(null);

            List<MemberBusinessUpdateRecordItem> waitUpdate= new ArrayList<MemberBusinessUpdateRecordItem>();
            if(null != commonTransacts && commonTransacts.size() > 0 &&  allData.size() > 0){
                // 数据匹配，以及更新
                for (List<String> values : allData) {
                    for (CommonTransact transact : commonTransacts) {
                        if(values.get(2).equals(transact.getIdCard()) && values.get(5).equals(transact.getServiceType())
                                && values.get(6).equals(transact.getServiceName())){
                            // 证件号、服务类型、服务名称相等
                            MemberBusinessUpdateRecordItem item = new MemberBusinessUpdateRecordItem();
                            item.setId(transact.getMburiId());
                            item.setRemark(values.get(15));
                            item.setCreateUserId(user.getId());
                            String success = values.get(16);
                            if("成功".equals(success)){
                                item.setStatus(StatusConstant.ITEM_SUCCESS);
                            }
                            else{
                                item.setStatus(StatusConstant.ITEM_FAILURE);
                            }
                            if(!CommonUtil.isEmpty(values.get(17))){
                                item.setReason(values.get(17));
                            }
                            waitUpdate.add(item);
                            break;
                        }

                    }
                }
                if(waitUpdate.size() > 0){
                    memberBusinessUpdateRecordItemMapper.updateList(waitUpdate);
                }
            }
        }
    }


}
