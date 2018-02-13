package com.magic.daoyuan.business.service;

import com.magic.daoyuan.business.dto.MemberBusinessCityDto;
import com.magic.daoyuan.business.dto.MemberBusinessUpdateRecordItemDto;
import com.magic.daoyuan.business.dto.MemberMonthPayBusinessDto;
import com.magic.daoyuan.business.entity.*;
import com.magic.daoyuan.business.enums.BusinessEnum;
import com.magic.daoyuan.business.enums.Common;
import com.magic.daoyuan.business.enums.EducationEnum;
import com.magic.daoyuan.business.exception.InterfaceCommonException;
import com.magic.daoyuan.business.mapper.*;
import com.magic.daoyuan.business.util.*;
import com.magic.daoyuan.business.vo.CommonTransact;
import com.sun.xml.internal.bind.v2.TODO;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.util.*;

/**
 * @author lzh
 * @create 2017/9/26 21:34
 */
@Service
public class MemberService {

    @Resource
    private IMemberMapper memberMapper;
    @Resource
    private IMemberSalaryMapper memberSalaryMapper;
    @Resource
    private IMemberBusinessItemMapper memberBusinessItemMapper;
    @Resource
    private IMemberBusinessMapper memberBusinessMapper;
    @Resource
    private IMemberBusinessOtherItemMapper memberBusinessOtherItemMapper;
    @Resource
    private ICityMapper cityMapper;
    @Resource
    private IBankMapper bankMapper;
    @Resource
    private ISalaryTypeMapper salaryTypeMapper;
    @Resource
    private ISalaryCountMapper salaryCountMapper;
    @Resource
    private ISalaryInfoMapper salaryInfoMapper;
    @Resource
    private ICompanySonBillMapper companySonBillMapper;
    @Resource
    private ICompanyMemberMapper companyMemberMapper;
    @Resource
    private IMemberBusinessUpdateRecordMapper memberBusinessUpdateRecordMapper;
    @Resource
    private IMemberBusinessUpdateRecordItemMapper memberBusinessUpdateRecordItemMapper;
    @Resource
    private IMemberBaseChangeMapper memberBaseChangeMapper;
    @Resource
    private ICompanySonTotalBillMapper companySonTotalBillMapper;
    @Resource
    private IMemberMonthPayBusinessMapper memberMonthPayBusinessMapper;
    @Resource
    private ICompanyMapper companyMapper;
    @Resource
    private IMonthServiceFeeMapper monthServiceFeeMapper;
    @Resource
    private ICompanyCooperationMethodMapper companyCooperationMethodMapper;
    @Resource
    private IMonthServiceFeeDetailMapper monthServiceFeeDetailMapper;
    @Resource
    private ICompanyBusinessMapper companyBusinessMapper;
    @Resource
    private IMemberCountMapper memberCountMapper;
    @Resource
    private ICompanyPayPlaceMapper companyPayPlaceMapper;



    public List<Member> batchQueryMemberAllField(JSONArray idsArr){
        List<Integer> ids = idsArr.toList(idsArr);
        List<Member> members = null;
        if (ids.size() > 0) {
            members = memberMapper.batchQueryMemberAllField(ids);
            for (Member member : members) {
                if(null != member.getBusinessList() && member.getBusinessList().size() > 0){
                    for (Business business : member.getBusinessList()) {
                        if(BusinessEnum.sheBao.ordinal() == business.getId()){
                            member.setSocialSecurityMemberBusinessItem(business.getMemberBusinessItem());
                        }
                        if(BusinessEnum.gongJiJin.ordinal() == business.getId()){
                            member.setReservedFundsMemberBusinessItem(business.getMemberBusinessItem());
                        }
                    }
                }
            }
        }
        return members;
    }




    public List<CommonTransact> getCommonDataForMember(Map<String,Object> map,Integer businessId){
        List<CommonTransact> commonTransacts = memberBusinessUpdateRecordItemMapper.queryCommonTransactByItem(map);
        if(null != commonTransacts && commonTransacts.size() > 0){
            List<Integer> memberIds = new ArrayList<Integer>();
            for (CommonTransact commonTransact : commonTransacts) {
                memberIds.add(commonTransact.getMemberId());
            }
            List<MemberBusinessItem> items = memberBusinessItemMapper.queryMemberBusinessItem(memberIds);
            if(null != items && items.size() > 0){
                Iterator<CommonTransact> iterator = commonTransacts.iterator();
                while (iterator.hasNext()){
                    CommonTransact next = iterator.next();
                    for (MemberBusinessItem item : items) {
                        if(next.getMemberId().equals(item.getMemberId()) &&
                                null != item.getServiceStartTime() && null != item.getServiceEndTime()
                                && null != item.getBillStartTime() && item.getBusinessId().equals(businessId)){
                            // 业务时间判断
                            // 员工的服务结束月小于账单起始月，同时，服务起始月也小于服务结束
                            if(item.getServiceEndTime().getTime() < item.getBillStartTime().getTime()
                                    && item.getServiceStartTime().getTime() <= item.getServiceEndTime().getTime()){
                                iterator.remove();
                                break;
                            }
                        }
                    }
                }
            }
        }
        return commonTransacts;
    }


    /**
     * 获取社保导出 准备实做的表格 增/变 员
     * @param map
     * @return
     */
    public List<ExportUser> getExportUser(Map<String,Object> map){
        List<ExportUser> exportUsers = memberMapper.queryExportUser(map);
        if(null != exportUsers && exportUsers.size() > 0){
            List<Integer> memberIds = new ArrayList<Integer>();
            for (ExportUser exportUser : exportUsers) {
                memberIds.add(exportUser.getMemberId());
            }
            List<MemberBusinessItem> items = memberBusinessItemMapper.queryMemberBusinessItem(memberIds);
            if(null != items && items.size() > 0){
                Iterator<ExportUser> iterator = exportUsers.iterator();
                while (iterator.hasNext()){
                    ExportUser next = iterator.next();
                    for (MemberBusinessItem item : items) {
                        if(next.getMemberId().equals(item.getMemberId()) &&
                                null != item.getServiceStartTime() && null != item.getServiceEndTime()
                                && null != item.getBillStartTime() && item.getBusinessId() == BusinessEnum.sheBao.ordinal()){
                            // 业务时间判断
                            // 员工的服务结束月小于账单起始月，同时，服务起始月也小于服务结束
                            if(item.getServiceEndTime().getTime() < item.getBillStartTime().getTime()
                                    && item.getServiceStartTime().getTime() <= item.getServiceEndTime().getTime()){
                                iterator.remove();
                                break;
                            }
                        }
                    }
                }
            }
        }
        return exportUsers;
    }


    /**
     * 获取社保导出 准备实做的表格 减员
     * @param map
     * @return
     */
    public List<ExportUser> getExportUser_(Map<String,Object> map){

        List<ExportUser> exportUsers = memberMapper.queryExportUser_(map);
        if(null != exportUsers && exportUsers.size() > 0){
            List<Integer> memberIds = new ArrayList<Integer>();
            for (ExportUser exportUser : exportUsers) {
                memberIds.add(exportUser.getMemberId());
            }
            List<MemberBusinessItem> items = memberBusinessItemMapper.queryMemberBusinessItem(memberIds);
            if(null != items && items.size() > 0){
                Iterator<ExportUser> iterator = exportUsers.iterator();
                while (iterator.hasNext()){
                    ExportUser next = iterator.next();
                    for (MemberBusinessItem item : items) {
                        if(next.getMemberId().equals(item.getMemberId()) &&
                                null != item.getServiceStartTime() && null != item.getServiceEndTime()
                                && null != item.getBillStartTime() && item.getBusinessId() == BusinessEnum.sheBao.ordinal()){
                            // 业务时间判断
                            // 员工的服务结束月小于账单起始月，同时，服务起始月也小于服务结束
                            if(item.getServiceEndTime().getTime() < item.getBillStartTime().getTime()
                                    && item.getServiceStartTime().getTime() <= item.getServiceEndTime().getTime()){
                                iterator.remove();
                                break;
                            }
                        }
                    }
                }
            }
        }
        return exportUsers;
    }


    public List<Member> queryVerifyMember(Integer companyId,Integer serviceType,Integer status){
        return memberMapper.queryVerifyMember(companyId,serviceType,status);
    }

    /**
     * 导入员工工资
     * @param companyId
     * @param targetUrl
     */
    @Transactional
    public void importMemberSalaryInfo(Integer companyId,String targetUrl) throws Exception{
        if(null == companyId || CommonUtil.isEmpty(targetUrl)){
            return;
        }
        // 判断该公司是否有此业务

        int i1 = companyBusinessMapper.countCompanyBusiness(companyId, BusinessEnum.gongZi.ordinal());
        if (i1 == 0){
            throw new InterfaceCommonException(StatusConstant.Fail_CODE, "该公司没有工资业务");
        }
        int HttpResult; // 服务器返回的状态
        URL url = new URL(targetUrl); // 创建URL
        URLConnection urlConn = url.openConnection();
        urlConn.connect();
        HttpURLConnection httpConn = (HttpURLConnection) urlConn;
        HttpResult = httpConn.getResponseCode();
        if (HttpResult != HttpURLConnection.HTTP_OK)
            throw new InterfaceCommonException(StatusConstant.Fail_CODE, "数据读取失败，请下载最新的模板");
        else {
            Map<Integer, List<String>> map = ExcelReader.readExcelContent(urlConn.getInputStream(),1);
            List<Member> importMembers = new ArrayList<Member>(); // 读取出来的员工
            List<City> cities = cityMapper.queryCityByLevelImport(2);// 查询市级所有城市
//            List<Bank> banks = bankMapper.queryAllBank();// 所有银行
            List<SalaryType> salaryTypes = salaryTypeMapper.queryAllSalaryType();//  所有工资类型
            List<CompanySonBill> companySonBills = companySonBillMapper.queryCompanySonBillByCompany(companyId); // 公司所有子账单
//            List<Member> memberListOfCompany = memberMapper.queryMemberByCompany(companyId);


            // 解析数据
            int index = 1;
            Date date = null;
            List<SalaryInfo> existInfoList = null;

            for (Integer cellNum : map.keySet()) {
                List<String> values = map.get(cellNum);// 行数据
                if(values.size() < 16){
                    throw new InterfaceCommonException(StatusConstant.Fail_CODE,"数据不完整，请下载最新的模板");
                }
                if (null == date) {
                    date = DateUtil.getDate(values.get(13));
                    // 账单确认后新上传工资可以提示账单已确认，不能继续上传该公司的工资账单
                    int i = salaryInfoMapper.countSalaryInfoByTotalBill(companyId, date);
                    if(i > 0){
                        throw new InterfaceCommonException(StatusConstant.Fail_CODE,"该月的工资账单已经存在");
                    }
                }
                else{
                    if(!date.equals(DateUtil.getDate(values.get(13)))){
                        throw new InterfaceCommonException(StatusConstant.Fail_CODE,"第"+index+"行工资月份不正确");
                    }
                }
                if(date.getTime() > new Date().getTime()){
                    throw new InterfaceCommonException(StatusConstant.Fail_CODE,"工资月份不允许填写未来日期");
                }
                if (null == existInfoList) {
                    existInfoList = salaryInfoMapper.querySalaryInfoOfTotalBillByCompany(companyId, date);
                }

                Member member = new Member();
                member.setCompanyId(companyId);
                member.setUserName(values.get(0));
                if(null == getCardId(values.get(2))){ // 证件类型
                    throw new InterfaceCommonException(StatusConstant.Fail_CODE,"第"+index+"行证件类型数据错误");
                }
                member.setCertificateType(getCardId(values.get(2)));
                String s = getStringNumber(values.get(3));
                if (CommonUtil.isEmpty(s)) {
                    throw new InterfaceCommonException(StatusConstant.Fail_CODE,"第"+index+"行证件号数据错误");
                }
                member.setCertificateNum(s); // 证件号

                // 判断 不同的公司有相同的证件号，不能导入提示错误



//                if(importMembers.size() > 0){
//                    for (Member im : importMembers) {
//                        if(member.getCertificateNum().equals(im.getCertificateNum())
//                                && !member.getCompanyId().equals(im.getCompanyId())){
//                            throw new InterfaceCommonException(StatusConstant.Fail_CODE,"第"+index+"行证件号在其他行已经存在，并且公司不相同");
//                        }
//                    }
//                }
                if(existInfoList.size() > 0){
                    for (SalaryInfo salaryInfo : existInfoList) {
                        if(member.getCertificateNum().equals(salaryInfo.getCertificateNumber())){
                            throw new InterfaceCommonException(StatusConstant.Fail_CODE,"第"+index+"行员工当月已经存在工资信息");
                        }
                    }
                }
                String s1 = getStringNumber(values.get(4));
                if(CommonUtil.isEmpty(s1)){
                    throw new InterfaceCommonException(StatusConstant.Fail_CODE,"第"+index+"行手机号数据错误");
                }
                member.setPhone(s1);// 手机号

                if(CommonUtil.isEmpty(values.get(5)) || null == getEducation(values.get(5))){
                    throw new InterfaceCommonException(StatusConstant.Fail_CODE,"第"+index+"行学历数据错误");
                }
                member.setEducation(getEducation(values.get(5)));// 学历

                if(null == getCityId(cities,values.get(6))){ // 所在城市
                    throw new InterfaceCommonException(StatusConstant.Fail_CODE,"第"+index+"行所在城市数据错误");
                }
                member.setCityId(getCityId(cities,values.get(6)));
                Integer waysOfCooperation = getWaysOfCooperation(values.get(7));
                if(null == waysOfCooperation){ // 合作方式
                    throw new InterfaceCommonException(StatusConstant.Fail_CODE,"第"+index+"行合作方式数据错误");
                }
                member.setWaysOfCooperation(waysOfCooperation);

                if(waysOfCooperation != 0){
                    if(CommonUtil.isEmpty(values.get(8),values.get(9))){
                        throw new InterfaceCommonException(StatusConstant.Fail_CODE,"第"+index+"行合同开始结束时间数据错误");
                    }
                }
                if(null != values.get(8)){
                    // 合同执行日期
                    member.setContractStartTime(DateUtil.getDate(values.get(8)));
                }
                if(null != values.get(9)){
                    // 合同结束日期
                    member.setContractEndTime(DateUtil.getDate(values.get(9)));
                }
                // 以下是员工工资信息
                MemberSalary salary = new MemberSalary();

                if(null == getNationality(values.get(1))){ // 员工国籍信息
                    throw new InterfaceCommonException(StatusConstant.Fail_CODE,"第"+index+"行国籍信息数据错误");
                }
                salary.setNationality(getNationality(values.get(1)));
//                if(null == getCityId(cities,values.get(10))){ // 报税地
//                    throw new InterfaceCommonException(StatusConstant.Fail_CODE,"第"+index+"行报税地数据错误");
//                }
                salary.setCityId(getCityId(cities,values.get(10)));
                if(CommonUtil.isEmpty(values.get(11))){ // 银行卡号
                    throw new InterfaceCommonException(StatusConstant.Fail_CODE,"第"+index+"行银行卡号数据错误");
                }
                salary.setBankAccount(values.get(11));
                if(CommonUtil.isEmpty(values.get(12))){ // 绑定银行
                    throw new InterfaceCommonException(StatusConstant.Fail_CODE,"第"+index+"行银行名称数据错误");
                }
                Integer companySonBillId = getCompanySonBillId(companySonBills, getStringNumber(values.get(21)));
                if(null == companySonBillId){ // 子账单ID
                    throw new InterfaceCommonException(StatusConstant.Fail_CODE,"第"+index+"行子账单数据错误");
                }
                salary.setCompanySonBillId(companySonBillId);
                salary.setBankName(values.get(12));


                //  TODO: 设置员工工资信息
                member.setMemberSalary(salary);

                // 发放工资数据封装
                SalaryInfo salaryInfo = new SalaryInfo();
                salaryInfo.setBankName(salary.getBankName()); // 银行ID
                salaryInfo.setBankCard(salary.getBankAccount()); // 银行帐号
                salaryInfo.setCityName(values.get(10)); //报税地
                salaryInfo.setMemberName(values.get(0));
                salaryInfo.setMonth(date);
                if(CommonUtil.isEmpty(values.get(14))){
                    throw new InterfaceCommonException(StatusConstant.Fail_CODE,"第"+index+"行发放工资日期错误");
                }
                salaryInfo.setGrantDate(DateUtil.getDate(values.get(14))); // 工资发放日期
                if(null == getSalaryType(salaryTypes,values.get(15))){ // 工资类型
                    throw new InterfaceCommonException(StatusConstant.Fail_CODE,"第"+index+"行工资类型数据错误");
                }
                salaryInfo.setSalaryTypeId(getSalaryType(salaryTypes,values.get(15)));
                if(null == values.get(16)){ // 应发工资
                    throw new InterfaceCommonException(StatusConstant.Fail_CODE,"第"+index+"行应发工资数据错误");
                }
                salaryInfo.setShouldSendSalary(Double.parseDouble(values.get(16)));

                // 需求变更增加字段
                salaryInfo.setPension(getDouble(values.get(17))); //养老
                salaryInfo.setMedical(getDouble(values.get(18))); // 医疗
                salaryInfo.setUnemployment(getDouble(values.get(19))); // 失业
                salaryInfo.setReserved(getDouble(values.get(20))); // 公积金
                // 计算应扣工资

                // 成都、重庆有公积金上限 故超过上限的按上限扣除
                // 成都：1839.9，重庆：2022
//                if("成都市".equals(salaryInfo.getCityName()) && salaryInfo.getReserved() > 1839.9){
//                    salaryInfo.setReserved(1839.9);
//                }
//                if("重庆市".equals(salaryInfo.getCityName()) && salaryInfo.getReserved() > 2022.0){
//                    salaryInfo.setReserved(2022.0);
//                }
                salaryInfo.setShouldBeDeductPay(salaryInfo.getPension() + salaryInfo.getMedical() +
                        salaryInfo.getUnemployment() + salaryInfo.getReserved());
                salaryInfo.setCreateTime(new Date());

                // 发放工资列表的时候，记录当前所属的子账单ID
                salaryInfo.setCompanySonBillId(companySonBillId);

                // TODO: 设置该列的工资发放明细  并未计算工资
                member.setSalaryInfo(salaryInfo);
                // 加入集合
                importMembers.add(member);
                index ++;
            }
            Set<MemberBusiness> updateMemberBusinesses = new HashSet<MemberBusiness>();
            // 数据对比，计算工资等业务处理
//            List<Member> members = memberMapper.queryMemberByCompany(companyId);
            List<Member> members = memberMapper.queryMemberByCompany(null);
            List<Member> waitAddList = new ArrayList<Member>();
            List<Member> existMember = new ArrayList<Member>();
            List<Member> waitUpdateList = new ArrayList<Member>();
            for (Member importMember : importMembers) {
                if(!members.contains(importMember)){
                    // 如果导入进来的员工在该公司不存在，则新增此员工
                    if (!waitAddList.contains(importMember)) {
                        importMember.setStateCooperation(1);
                        waitAddList.add(importMember);
                    }
                    else{
                        existMember.add(importMember);
                    }
                }
                else {
                    for (Member member : members) {
                        if(importMember.getCertificateNum().equals(member.getCertificateNum())){
                            importMember.setId(member.getId());
                            importMember.getMemberSalary().setMemberId(member.getId());
                            waitUpdateList.add(importMember);
                            MemberBusiness mb = new MemberBusiness();
                            mb.setBusinessId(BusinessEnum.gongZi.ordinal());
                            mb.setMemberId(member.getId());
                            mb.setCompanySonBillId(importMember.getSalaryInfo().getCompanySonBillId());
                            updateMemberBusinesses.add(mb);
                            break;
                        }
                    }
                }
            }
            // 批量新增、批量修改员工信息
            if(waitAddList.size() > 0){
                // 新增员工之后，继续新增工资业务
                memberMapper.batchAddMember(waitAddList);
                if(existMember.size() > 0){
                    for (Member member : existMember) {
                        for (Member member1 : waitAddList) {
                            if(member1.getCertificateNum().equals(member.getCertificateNum())){
                                member.setId(member1.getId());
                                break;
                            }
                        }
                    }
                }
                // 新增工资业务
                List<MemberBusiness> memberBusinesses = new ArrayList<MemberBusiness>();
                List<MemberSalary> memberSalaryList = new ArrayList<MemberSalary>();
                for (Member member : waitAddList) {
                    member.getSalaryInfo().setMemberId(member.getId());
                    MemberBusiness business = new MemberBusiness();
                    business.setBusinessId(5);// 工资
                    business.setMemberId(member.getId());
                    business.setCompanySonBillId(member.getSalaryInfo().getCompanySonBillId());
                    memberBusinesses.add(business);
                    member.getMemberSalary().setMemberId(member.getId());
                    memberSalaryList.add(member.getMemberSalary());
                }
                if(memberBusinesses.size() > 0){
                    memberBusinessMapper.batchAddMemberBusiness(memberBusinesses);
                }
                if(memberSalaryList.size() > 0){
                    memberSalaryMapper.batchAddMemberSalary(memberSalaryList);
                }
            }
            if(waitUpdateList.size() > 0){
                // 修改员工信息，修改工资业务处理
                memberMapper.batchUpdateMember(waitUpdateList);
                List<MemberSalary> memberSalaryList = new ArrayList<MemberSalary>();
                Set<MemberSalary> addMemberSalaryList = new HashSet<MemberSalary>();

                List<Integer> memberIds = new ArrayList<Integer>();
                for (Member member : waitUpdateList) {
                    memberIds.add(member.getId());
                    addMemberSalaryList.add(member.getMemberSalary());
                }

                // 删除员工工资业务

                if (memberIds.size() > 0) {
                    memberSalaryMapper.delMemberSalarys(memberIds);
                }
                if (updateMemberBusinesses.size() > 0) {

                    Iterator<MemberBusiness> iterator = updateMemberBusinesses.iterator();
                    List<MemberBusiness> temp = new ArrayList<MemberBusiness>();
                    while (iterator.hasNext()){
                        MemberBusiness next = iterator.next();
                        temp.add(next);
                    }
                    memberBusinessMapper.batchDel(temp);
                    memberBusinessMapper.batchAddMemberBusiness(temp);
                }


                if (memberSalaryList.size() > 0) {
                    memberSalaryMapper.batchUpdateMemberSalaryByMember(memberSalaryList);
                }
                if(addMemberSalaryList.size() > 0){
                    Iterator<MemberSalary> iterator = addMemberSalaryList.iterator();
                    List<MemberSalary> temp = new ArrayList<MemberSalary>();
                    while (iterator.hasNext()){
                        temp.add(iterator.next());
                    }
                    memberSalaryMapper.batchAddMemberSalary(temp);
                }
            }

            // 工资计算业务处理开始
            List<SalaryInfo> salaryInfos = new ArrayList<SalaryInfo>();
            List<Integer> memberIds = new ArrayList<Integer>();
            for (Member importMember : importMembers) {
                SalaryInfo e = countSalary(importMember.getSalaryInfo(), null, salaryTypes);
                e.setMemberId(importMember.getId());
                e.setCertificateNumber(importMember.getCertificateNum());
                salaryInfos.add(e);
                memberIds.add(importMember.getId());
            }
            // 插入计算后的工资数据
            if(salaryInfos.size() > 0){
                List<SalaryInfo> s = salaryInfoMapper.querySalaryInfoByMembers(memberIds);
                if(null != s && s.size() > 0){
                    for (SalaryInfo salaryInfo : salaryInfos) {
                        for (SalaryInfo info : s) {
                            if(salaryInfo.getMemberId().equals(info.getMemberId()) && StatusConstant.YEAR_END_BONUS.equals(salaryInfo.getSalaryTypeId())
                                    && StatusConstant.YEAR_END_BONUS.equals(info.getSalaryTypeId())){
                                if(salaryInfo.getMonth().getYear() == info.getMonth().getYear()){
                                    throw new InterfaceCommonException(StatusConstant.Fail_CODE,"员工:"+salaryInfo.getMemberName()+"已经发放过年终奖");
                                }
                            }
                        }
                    }
                }
                // 查询 做工资覆盖
                List<SalaryInfo> infos = salaryInfoMapper.querySalaryInfoByMembersAndMonth(memberIds, date);


                List<SalaryInfo> waitSalaryInfo = new ArrayList<SalaryInfo>();
                if(null != infos && infos.size() > 0){

                    // 遍历查询 已经存在的工资信息做覆盖
                    Iterator<SalaryInfo> iterator = salaryInfos.iterator();
                    while (iterator.hasNext()){
                        SalaryInfo next = iterator.next();
                        for (SalaryInfo info : infos) {
                            if(info.getMemberId().equals(next.getMemberId()) && info.getSalaryTypeId().equals(next.getSalaryTypeId())){
                                next.setId(info.getId());
                                waitSalaryInfo.add(next);
                                iterator.remove();
                                break;
                            }
                        }
                    }
                }

                if (salaryInfos.size() > 0) {
                    salaryInfoMapper.batchAddSalaryInfo(salaryInfos);
                }

                if(waitSalaryInfo.size() > 0){
                    salaryInfoMapper.updateList(waitSalaryInfo);
                }
                //记录缴纳
                List<MemberMonthPayBusiness> mmpbList = new ArrayList<MemberMonthPayBusiness>();
                for (SalaryInfo info : salaryInfos) {
                    MemberMonthPayBusiness mmpb = new MemberMonthPayBusiness();
                    mmpb.setBillMonth(Timestamp.parseDate3(new Date(),"yyyy-MM"));
                    mmpb.setMemberId(info.getMemberId());
                    mmpb.setServiceMonth(info.getMonth());
                    mmpb.setBusinessId(5);
                    mmpbList.add(mmpb);
                }

                //需要绑定汇总账单的工资
                List<SalaryInfo> updateList = new ArrayList<SalaryInfo>();
                List<CompanySonTotalBill> companySonTotalBillList = companySonTotalBillMapper.getByBillMonth(new Date(),companyId);
                Company company = companyMapper.queryCompanyById2(companyId);
                //服务费配置集合
                List<CompanyCooperationMethod> methods =
                        companyCooperationMethodMapper.queryCompanyCooperationMethod(companyId);
                //汇总账单的id集合
                Set<Integer> companySonTotalBillIdSet = new HashSet<Integer>();
                for (CompanySonTotalBill bill : companySonTotalBillList) {
                    //获取缴纳方式
                    methods = getCompanyCooperationMethod(company, methods, companySonTotalBillIdSet, bill,new Date());
                    for (int i = 0; i < salaryInfos.size(); i++) {
                        if (bill.getCompanySonBillId().equals(salaryInfos.get(i).getCompanySonBillId())) {
                            salaryInfos.get(i).setCompanySonTotalBillId(bill.getId());
                            // 计算税费开始 start
                            out:for (CompanyCooperationMethod method : methods) {
                                for (Member member : importMembers) {
                                    if (member.getCertificateNum().equals(salaryInfos.get(i).getCertificateNumber()) &&
                                            member.getWaysOfCooperation().equals(method.getCooperationMethodId())) {
                                        salaryInfos.get(i).setTaxPrice(salaryInfos.get(i).getSalaryBeforeTax() * method.getPercent() / 100);
//                                        salaryInfos.get(i).setSalaryBeforeTax(salaryInfos.get(i).getSalaryBeforeTax() * (1 + method.getPercent() / 100));
                                        break out;
                                    }
                                }
                            }
                            // 计算税费结束 end

                            updateList.add(salaryInfos.get(i));
                            salaryInfos.remove(i);
                            i --;
                        }
                    }
                }

                if (updateList.size() > 0) {
                    salaryInfoMapper.updateList(updateList);
                }


                if (mmpbList.size() > 0) {
                    //需要更新的异动量
                    List<MemberBusinessUpdateRecordItem> updateRecordItemList = new ArrayList<MemberBusinessUpdateRecordItem>();
                    memberMonthPayBusinessMapper.saveList(mmpbList);
                    if (companySonTotalBillIdSet.size() > 0) {
                        //删除服务费明细
                        monthServiceFeeDetailMapper.delete(companySonTotalBillIdSet.toArray(new Integer[companySonTotalBillIdSet.size()]));
                        //删除服务费
                        monthServiceFeeMapper.delete(companySonTotalBillIdSet.toArray(new Integer[companySonTotalBillIdSet.size()]));

                        //需要添加的服务费
                        List<MonthServiceFee> addListMSF = new ArrayList<MonthServiceFee>();
                        //需要添加的服务费明细
                        List<MonthServiceFeeDetail> addListMSFD = new ArrayList<MonthServiceFeeDetail>();
                        buildServiceFee(companySonTotalBillList, company, methods,Timestamp.parseDate3(new Date(),"yyyy-MM"),addListMSF,new Date(),updateRecordItemList);
                        if (addListMSF.size() > 0) {
                            monthServiceFeeMapper.save(addListMSF);
                            for (MonthServiceFee fee : addListMSF) {
                                if (null != fee.getMonthServiceFeeDetailList()) {
                                    for (MonthServiceFeeDetail detail : fee.getMonthServiceFeeDetailList()) {
                                        detail.setMonthServiceFeeId(fee.getId());
                                        addListMSFD.add(detail);
                                    }
                                }
                            }
                            if (addListMSFD.size() > 0) {
                                monthServiceFeeDetailMapper.batchAdd(addListMSFD);
                            }
                        }
                    }
                    if (updateRecordItemList.size() > 0) {
                        memberBusinessUpdateRecordItemMapper.updateList(updateRecordItemList);
                    }
                }


            }
        }

    }
    private String getStringNumber(String s) {
        if(CommonUtil.isEmpty(s)){
            return "";
        }
        return s.split("\\.")[0];
    }

    /**
     * 获取服务费缴纳方式
     * @param company
     * @param methods
     * @param companySonTotalBillIdSet
     * @param bill
     * @param month
     * @return
     * @throws ParseException
     */
    public List<CompanyCooperationMethod> getCompanyCooperationMethod(Company company, List<CompanyCooperationMethod> methods,
                                                                      Set<Integer> companySonTotalBillIdSet,
                                                                      CompanySonTotalBill bill,Date month) throws ParseException {
        companySonTotalBillIdSet.add(bill.getId());
        if (bill.getMonthServiceFeeList().size() > 0) {
            methods = com.alibaba.fastjson.JSONArray.parseArray(
                    bill.getMonthServiceFeeList().get(0).getCompanyCooperationMethodJson(),
                    CompanyCooperationMethod.class);
        } else {
            List<MonthServiceFee> msfList = monthServiceFeeMapper.getByDateAndCompanyId(month ,company.getId());
            if (msfList.size() > 0) {
                methods = com.alibaba.fastjson.JSONArray.parseArray(
                        msfList.get(0).getCompanyCooperationMethodJson(),
                        CompanyCooperationMethod.class);
            } else {
                //下一次或者是本次业务账单生成月
                Date billMonth = CommonUtil.getMonth(company.getBusinessStartTime(),company.getBusinessCycle(),1,month).get(0);
                if (Timestamp.parseDate3(billMonth,"yyyy-MM").
                        compareTo(Timestamp.parseDate3(month,"yyyy-MM")) > 0) {
                    List<CompanySonTotalBill> cstbList = company.getCompanySonTotalBillList();
                    //降序排序
                    Collections.sort(cstbList, new Comparator<CompanySonTotalBill>() {
                        public int compare(CompanySonTotalBill o1, CompanySonTotalBill o2) {
                            return o2.getId() - o1.getId();
                        }
                    });
                    out:for (CompanySonTotalBill totalBill : cstbList) {
                        if (totalBill.getIsCreateBillMonth() == 1 &&
                                Timestamp.parseDate3(totalBill.getBillMonth(),"yyyy-MM").
                                        compareTo(Timestamp.parseDate3(month,"yyyy-MM")) <= 0) {
                            for (MonthServiceFee fee : totalBill.getMonthServiceFeeList()) {
                                methods = com.alibaba.fastjson.JSONArray.parseArray(
                                        fee.getCompanyCooperationMethodJson(),
                                        CompanyCooperationMethod.class);
                                break out;
                            }

                        }
                    }

                }
            }
        }
        return methods;
    }

    /***
     * 获取服务费
     * @param companySonTotalBillList
     * @param company
     * @param methods
     * @param updateRecordItemList 需要更新的异动量数据
     * @throws ParseException
     * @throws IOException
     */
    public void buildServiceFee(List<CompanySonTotalBill> companySonTotalBillList, Company company,
                                List<CompanyCooperationMethod> methods ,Date month,List<MonthServiceFee> addListMSF,
                                Date serviceMonth,List<MemberBusinessUpdateRecordItem> updateRecordItemList) throws ParseException, IOException {


        //根据公司id和时间 获取员工 包含员工
        List<Member> memberList = memberMapper.getMemberByCompanyIdAndDate(company.getId(),serviceMonth);
        List<Integer> memberIdSet = new ArrayList<Integer>();
        for (int i = 0; i < memberList.size(); i++) {
            memberIdSet.add(memberList.get(i).getId());
        }
        //已缴纳服务费
        List<MonthServiceFee> msfList = monthServiceFeeMapper.getByDateAndCompanyId(serviceMonth ,company.getId());
        //按固定额收费记录已缴纳临时集合
        Set<Integer> memberIdsSetGD = new HashSet<Integer>();
        //按服务区收费记录已缴纳临时集合
        Set<String> memberIdsSetQU = new HashSet<String>();
        //服务类别次数
        int fwlNum = 0;
        //异动量次数
        int ydNum = 0;
        //整体阶梯次数
        int ztjtNum = 0;
        //整体次数
        int ztNum = 0;
        out:for (CompanySonTotalBill bill : companySonTotalBillList) {
            if (Timestamp.parseDate3(bill.getBillMonth(),"yyyy-MM").compareTo(month) == 0) {
                if (null != bill.getCompanySonBillItemList()) {
                        for (CompanyCooperationMethod method : methods) {
                           switch (method.getServiceFeeConfigId()) {
                                case 1 :
                                    //按固定额
                                {
                                    //服务费明细
                                    List<MonthServiceFeeDetail> msfdList = new ArrayList<MonthServiceFeeDetail>();
                                    MonthServiceFee monthServiceFee = new MonthServiceFee();
                                    monthServiceFee.setCompanySonTotalBillId(bill.getId());
                                    monthServiceFee.setServiceFeeConfigId(method.getServiceFeeConfigId());
                                    monthServiceFee.setCompanySonBillId(bill.getCompanySonBillId());
                                    monthServiceFee.setCompanyId(company.getId());
                                    monthServiceFee.setWaysOfCooperation(method.getCooperationMethodId());
                                    //服务费
                                    Double memberFee = 0.0;
                                    if (msfList.size() > 0 ) {
                                        for (MonthServiceFee fee : msfList) {
                                            if (fee.getServiceFeeConfigId().equals(method.getServiceFeeConfigId()) &&
                                                    fee.getCompanySonBillId().equals(bill.getCompanySonBillId()) &&
                                                    null != fee.getMonthServiceFeeDetailList() &&
                                                    fee.getMonthServiceFeeDetailList().size() > 0) {
                                                for (MonthServiceFeeDetail detail : fee.getMonthServiceFeeDetailList()) {
                                                    if (!memberIdSet.contains(detail.getMemberId())) {
                                                        a:for (Member member : memberList) {
                                                            if (member.getId().equals(detail.getMemberId()) &&
                                                                    member.getWaysOfCooperation().equals(method.getCooperationMethodId())) {
                                                                MonthServiceFeeDetail msfd = new MonthServiceFeeDetail();
                                                                msfd.setMemberId(detail.getMemberId());
                                                                msfd.setAmount(method.getServiceFeeList().get(0).getPrice());
                                                                msfd.setCompanySonBillId(bill.getCompanySonBillId());
                                                                msfdList.add(msfd);
                                                                memberFee += method.getServiceFeeList().get(0).getPrice();
                                                                continue a;
                                                            }
                                                        }
                                                    }

                                                }
                                            }
                                        }
                                    } else {
                                        for (Member member : memberList) {
                                            if (null != member.getMemberBusinessSet() && null != member.getMemberMonthPayBusinessList()) {
                                                for (MemberBusiness business : member.getMemberBusinessSet()) {
                                                    for (MemberMonthPayBusiness payBusiness : member.getMemberMonthPayBusinessList()) {
                                                        if (business.getBusinessId().equals(payBusiness.getBusinessId()) &&
                                                                Timestamp.parseDate3(payBusiness.getServiceMonth(),"yyyy-MM").
                                                                        compareTo(Timestamp.parseDate3(serviceMonth,"yyyy-MM")) == 0) {
                                                            if (null != business.getCompanySonBillId() && member.getWaysOfCooperation().equals(method.getCooperationMethodId()) &&
                                                                    business.getCompanySonBillId().equals(bill.getCompanySonBillId()) &&
                                                                    !memberIdsSetGD.contains(member.getId())) {
                                                                MonthServiceFeeDetail msfd = new MonthServiceFeeDetail();
                                                                msfd.setMemberId(member.getId());
                                                                msfd.setAmount(method.getServiceFeeList().get(0).getPrice());
                                                                msfd.setCompanySonBillId(bill.getCompanySonBillId());
                                                                msfd.setWaysOfCooperation(member.getWaysOfCooperation());
                                                                msfdList.add(msfd);
                                                                memberFee += method.getServiceFeeList().get(0).getPrice();
                                                                memberIdsSetGD.add(member.getId());
                                                            }
                                                        }
                                                    }

                                                }
                                            }
                                        }
                                    }

                                    if (method.getIsPercent() == 1) {
                                        memberFee = (1 + method.getPercent() / 100 ) * memberFee;
                                    }

                                    if (null != method.getServiceFeeMax() && memberFee > method.getServiceFeeMax()) {
                                        memberFee = method.getServiceFeeMax();
                                    }
                                    if (null != method.getServiceFeeMin() && memberFee < method.getServiceFeeMin()) {
                                        memberFee = method.getServiceFeeMin();
                                    }
                                    //服务费
                                    monthServiceFee.setServiceFee(memberFee);
                                    //服务月
                                    monthServiceFee.setMonth(Timestamp.parseDate3(serviceMonth,"yyyy-MM"));
                                    monthServiceFee.setCompanyCooperationMethodJson(com.alibaba.fastjson.JSONArray.toJSONString(methods));
                                    monthServiceFee.setMonthServiceFeeDetailList(msfdList);
                                    addListMSF.add(monthServiceFee);
                                }
                                break;
                                case 2 :
                                    //按人数阶梯
                                {
                                    //服务费明细
                                    List<MonthServiceFeeDetail> msfdList = new ArrayList<MonthServiceFeeDetail>();
                                    MonthServiceFee monthServiceFee = new MonthServiceFee();
                                    monthServiceFee.setCompanySonTotalBillId(bill.getId());
                                    monthServiceFee.setServiceFeeConfigId(method.getServiceFeeConfigId());
                                    monthServiceFee.setCompanySonBillId(bill.getCompanySonBillId());
                                    monthServiceFee.setCompanyId(company.getId());
                                    monthServiceFee.setWaysOfCooperation(method.getCooperationMethodId());
                                    //记录人数
                                    Set<Integer> memberCountSet = new HashSet<Integer>();

                                    a:for (Member member : memberList) {
                                        if (null != member.getMemberBusinessSet() && null != member.getMemberMonthPayBusinessList()
                                                && !memberCountSet.contains(member.getId())) {
                                            for (MemberBusiness business : member.getMemberBusinessSet()) {
                                                if (business.getCompanySonBillId().equals(bill.getCompanySonBillId())) {
                                                    for (MemberMonthPayBusiness payBusiness : member.getMemberMonthPayBusinessList()) {
                                                        if (business.getBusinessId().equals(payBusiness.getBusinessId()) &&
                                                                Timestamp.parseDate3(payBusiness.getServiceMonth(),"yyyy-MM").
                                                                        compareTo(Timestamp.parseDate3(serviceMonth,"yyyy-MM")) == 0) {
                                                            if (member.getWaysOfCooperation().equals(method.getCooperationMethodId())) {
                                                                MonthServiceFeeDetail msfd = new MonthServiceFeeDetail();
                                                                msfd.setMemberId(member.getId());
                                                                msfd.setWaysOfCooperation(member.getWaysOfCooperation());
                                                                msfd.setCompanySonBillId(bill.getCompanySonBillId());
                                                                msfdList.add(msfd);
                                                                memberCountSet.add(member.getId());
                                                                continue a;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }

                                    }
                                    //已缴纳
                                    Set<Integer> memberPayCountSet = new HashSet<Integer>();
                                    for (MonthServiceFee fee : msfList) {
                                        if (null != fee.getMonthServiceFeeDetailList() &&
                                                fee.getServiceFeeConfigId().equals(method.getServiceFeeConfigId())) {
                                            for (MonthServiceFeeDetail detail : fee.getMonthServiceFeeDetailList()) {
                                                if (detail.getWaysOfCooperation().equals(method.getCooperationMethodId())) {
                                                    memberPayCountSet.add(detail.getMemberId());
                                                }
                                            }
                                        }
                                    }
                                    //本次应人数
                                    Integer memberCount = memberCountSet.size();

                                    //已缴纳人数
                                    Integer memberPayCount = memberPayCountSet.size();


                                    if (method.getServiceFeeList().size() > 0) {
                                        //升序排序
                                        Collections.sort(method.getServiceFeeList(), new Comparator<CompanyCooperationServiceFee>() {
                                            public int compare(CompanyCooperationServiceFee o1, CompanyCooperationServiceFee o2) {
                                                return o1.getExtent() - o2.getExtent();
                                            }
                                        });
                                    }
                                    //临时服务费
                                    Double memberFee = 0.0;
                                    //已缴纳服务费
                                    Double memberFee2 = 0.0;
                                    {
                                        //临时记录人数
                                        Integer temporaryCount = 0;
                                        memberFee = getServiceFeePersonNumJT(method, msfdList, temporaryCount, memberFee, memberCount,0);
                                    }
                                    //以前是否有缴纳人数
                                    if (memberPayCount > 0 && !memberCount.equals(memberPayCount)) {
                                        //临时记录人数
                                        Integer temporaryCount = 0;
                                        memberFee2 = getServiceFeePersonNumJT(method, msfdList, temporaryCount, memberFee, memberPayCount,1);
                                        //补缴或补差
                                        monthServiceFee.setServiceFee(memberFee-memberFee2);
                                    }
                                    if (memberPayCount < 1) {
                                        if (method.getIsPercent() == 1) {
                                            memberFee = (1 + method.getPercent() / 100 ) * memberFee;
                                        }
                                        if (null != method.getServiceFeeMax() && memberFee > method.getServiceFeeMax()) {
                                            memberFee = method.getServiceFeeMax();
                                        }
                                        if (null != method.getServiceFeeMin() && memberFee < method.getServiceFeeMin()) {
                                            memberFee = method.getServiceFeeMin();
                                        }
                                        //服务费
                                        monthServiceFee.setServiceFee(memberFee);
                                    }

                                    //服务月
                                    monthServiceFee.setMonth(Timestamp.parseDate3(serviceMonth,"yyyy-MM"));
                                    monthServiceFee.setCompanyCooperationMethodJson(com.alibaba.fastjson.JSONArray.toJSONString(methods));
                                    monthServiceFee.setMonthServiceFeeDetailList(msfdList);
                                    addListMSF.add(monthServiceFee);
                                }
                                break;
                                case 3 :
                                    //服务类别
                                if (fwlNum <= methods.size()){
                                    //服务费明细
                                    List<MonthServiceFeeDetail> msfdList = new ArrayList<MonthServiceFeeDetail>();
                                    MonthServiceFee monthServiceFee = new MonthServiceFee();
                                    monthServiceFee.setCompanySonTotalBillId(bill.getId());
                                    monthServiceFee.setServiceFeeConfigId(method.getServiceFeeConfigId());
                                    monthServiceFee.setCompanySonBillId(bill.getCompanySonBillId());
                                    monthServiceFee.setCompanyId(company.getId());
                                    monthServiceFee.setWaysOfCooperation(method.getCooperationMethodId());
                                    //临时服务费
                                    Double memberFee = 0.0;
                                    if (msfList.size() > 0) {
                                        for (MonthServiceFee fee : msfList) {
                                            if (null != fee.getMonthServiceFeeDetailList()) {
                                                a:for (Member member : memberList) {
                                                    if (null == member.getMemberBusinessSet()) {
                                                        List<MemberBusiness> memberBusinessList = new ArrayList<MemberBusiness>(member.getMemberBusinessSet());
                                                        Collections.sort(memberBusinessList, new Comparator<MemberBusiness>() {
                                                            public int compare(MemberBusiness o1, MemberBusiness o2) {
                                                                return o1.getBusinessId().compareTo(o1.getBusinessId());
                                                            }
                                                        });
                                                        Set<Integer> businessIdSet = new HashSet<Integer>();
                                                        for (MemberBusiness business : member.getMemberBusinessSet()) {
                                                            businessIdSet.add(business.getBusinessId());
                                                        }

                                                        if (method.getCooperationMethodId().equals(member.getWaysOfCooperation())) {
                                                            if (null != method.getBusinessServiceFeeList() && null != member.getMonthPayBusinessDtoList()) {
                                                                for (MemberMonthPayBusinessDto dto : member.getMonthPayBusinessDtoList()) {
                                                                    if (null != dto.getMemberMonthPayBusinessStr()) {
                                                                        for (CompanyCooperationBusinessServiceFee fee1 : method.getBusinessServiceFeeList()) {
                                                                            if (dto.getServiceMonth().
                                                                                    compareTo(Timestamp.parseDate3(month,"yyyy-MM")) == 0) {
                                                                                Set<Integer> businessIdSet2 = ClassConvert.strToIntegerSetGather(dto.getMemberMonthPayBusinessStr().split(","));
                                                                                String memberMonthPayBusinessStr = "";
                                                                                int i = 0;
                                                                                for (Integer integer : businessIdSet2) {
                                                                                    if (businessIdSet.contains(integer)) {
                                                                                        if (i == 0) {
                                                                                            memberMonthPayBusinessStr = String.valueOf(integer);
                                                                                        } else {
                                                                                            memberMonthPayBusinessStr += "," + String.valueOf(integer);
                                                                                        }
                                                                                        i ++;
                                                                                    }
                                                                                }
                                                                                if (fee1.getBusinessIds().equals(memberMonthPayBusinessStr)) {
                                                                                    MonthServiceFeeDetail msfd = new MonthServiceFeeDetail();
                                                                                    msfd.setMemberId(member.getId());
                                                                                    msfd.setCompanySonBillId(bill.getCompanySonBillId());
                                                                                    Double amount = fee1.getPrice();
                                                                                    for (MonthServiceFeeDetail detail : fee.getMonthServiceFeeDetailList()) {
                                                                                        if (detail.getMemberId().equals(member.getId())) {
                                                                                            amount = amount - detail.getAmount();
                                                                                        }
                                                                                    }
                                                                                    msfd.setAmount(amount);
                                                                                    msfd.setWaysOfCooperation(member.getWaysOfCooperation());
                                                                                    msfd.setBusinessStr(memberMonthPayBusinessStr);
                                                                                    msfdList.add(msfd);
                                                                                    memberFee += amount;
                                                                                    continue a;
                                                                                }
                                                                            }
                                                                        }
                                                                    }


                                                                }
                                                            }

                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        a:for (Member member : memberList) {
                                            if (null != member.getMemberBusinessSet()) {
                                                List<MemberBusiness> memberBusinessList = new ArrayList<MemberBusiness>(member.getMemberBusinessSet());
                                                if (memberBusinessList.size() > 1) {
                                                    Collections.sort(memberBusinessList, new Comparator<MemberBusiness>() {
                                                        public int compare(MemberBusiness o1, MemberBusiness o2) {
                                                            return o1.getBusinessId().compareTo(o1.getBusinessId());
                                                        }
                                                    });
                                                }
                                                Set<Integer> businessIdSet = new HashSet<Integer>();
                                                if (null != member.getMemberBusinessSet() && null != member.getMemberMonthPayBusinessList()) {
                                                    for (MemberBusiness business : member.getMemberBusinessSet()) {
                                                        for (MemberMonthPayBusiness payBusiness : member.getMemberMonthPayBusinessList()) {
                                                            if (null != business.getCompanySonBillId() &&
                                                                    Timestamp.parseDate3(payBusiness.getServiceMonth(),"yyyy-MM").
                                                                            compareTo(Timestamp.parseDate3(serviceMonth,"yyyy-MM")) == 0) {
                                                                businessIdSet.add(business.getBusinessId());
                                                            }
                                                        }

                                                    }
                                                    if (method.getCooperationMethodId().equals(member.getWaysOfCooperation())) {
                                                        if (null != method.getBusinessServiceFeeList() && null != member.getMonthPayBusinessDtoList()) {
                                                            for (MemberMonthPayBusinessDto dto : member.getMonthPayBusinessDtoList()) {
                                                                if (null != dto.getMemberMonthPayBusinessStr()) {
                                                                    for (CompanyCooperationBusinessServiceFee fee : method.getBusinessServiceFeeList()) {
                                                                        if (dto.getServiceMonth().
                                                                                compareTo(Timestamp.parseDate3(serviceMonth,"yyyy-MM")) == 0) {
                                                                            Set<Integer> businessIdSet2 = ClassConvert.strToIntegerSetGather(dto.getMemberMonthPayBusinessStr().split(","));
                                                                            String memberMonthPayBusinessStr = "";
                                                                            int i = 0;
                                                                            for (Integer integer : businessIdSet2) {
                                                                                if (businessIdSet.contains(integer)) {
                                                                                    if (i == 0) {
                                                                                        memberMonthPayBusinessStr = String.valueOf(integer);
                                                                                    } else {
                                                                                        memberMonthPayBusinessStr += "," + String.valueOf(integer);
                                                                                    }
                                                                                    i ++;
                                                                                }
                                                                            }

                                                                            if (fee.getBusinessIds().equals(memberMonthPayBusinessStr)) {
                                                                                MonthServiceFeeDetail msfd = new MonthServiceFeeDetail();
                                                                                msfd.setMemberId(member.getId());
                                                                                msfd.setCompanySonBillId(bill.getCompanySonBillId());
                                                                                msfd.setAmount(fee.getPrice());
                                                                                msfd.setWaysOfCooperation(member.getWaysOfCooperation());
                                                                                msfd.setBusinessStr(memberMonthPayBusinessStr);
                                                                                msfdList.add(msfd);
                                                                                memberFee += fee.getPrice();
                                                                                continue a;
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }


                                    if (method.getIsPercent() == 1) {
                                        memberFee = (1 + method.getPercent() / 100 ) * memberFee;
                                    }

                                    if (null != method.getServiceFeeMax() && memberFee > method.getServiceFeeMax()) {
                                        memberFee = method.getServiceFeeMax();
                                    }
                                    if (null != method.getServiceFeeMin() && memberFee < method.getServiceFeeMin()) {
                                        memberFee = method.getServiceFeeMin();
                                    }
                                    //服务费
                                    monthServiceFee.setServiceFee(memberFee);
                                    //服务月
                                    monthServiceFee.setMonth(Timestamp.parseDate3(serviceMonth,"yyyy-MM"));
                                    monthServiceFee.setCompanyCooperationMethodJson(com.alibaba.fastjson.JSONArray.toJSONString(methods));
                                    monthServiceFee.setMonthServiceFeeDetailList(msfdList);
                                    addListMSF.add(monthServiceFee);
                                    fwlNum ++;
                                }
                                break;
                                case 4 :
                                    //服务区
                                {
                                    //服务费明细
                                    List<MonthServiceFeeDetail> msfdList = new ArrayList<MonthServiceFeeDetail>();
                                    MonthServiceFee monthServiceFee = new MonthServiceFee();
                                    monthServiceFee.setCompanySonTotalBillId(bill.getId());
                                    monthServiceFee.setServiceFeeConfigId(method.getServiceFeeConfigId());
                                    monthServiceFee.setCompanySonBillId(bill.getCompanySonBillId());
                                    monthServiceFee.setCompanyId(company.getId());
                                    monthServiceFee.setWaysOfCooperation(method.getCooperationMethodId());
                                    //服务费
                                    Double memberFee = 0.0;
                                    //公司交金地集合
                                    List<CompanyCooperationServicePayPlace> payPlaceList = new ArrayList<CompanyCooperationServicePayPlace>(method.getPayPlaceList());

                                    if (msfList.size() > 0) {
                                        for (MonthServiceFee fee : msfList) {
                                            if (fee.getServiceFeeConfigId().equals(method.getServiceFeeConfigId()) &&
                                                    fee.getCompanySonBillId().equals(bill.getCompanySonBillId()) &&
                                                    null != fee.getMonthServiceFeeDetailList() &&
                                                    fee.getMonthServiceFeeDetailList().size() > 0) {
                                                //判断是否已存在缴纳
                                                for (MonthServiceFeeDetail detail : fee.getMonthServiceFeeDetailList()) {
                                                    for (Member member : memberList) {
                                                        Double fee1 = 0.0;
//                                                        List<Map<Object,Object>> mapList = new ArrayList<Map<Object, Object>>();
                                                        Map<Integer,List<Object>> listMap = new HashMap<Integer, List<Object>>();
                                                        if (null != member.getMemberBusinessSet() && null != member.getMemberMonthPayBusinessList()) {
                                                            Set<Integer> businessIdSet = new HashSet<Integer>();
                                                            for (MemberBusiness business : member.getMemberBusinessSet()) {
                                                                for (MemberMonthPayBusiness payBusiness : member.getMemberMonthPayBusinessList()) {
                                                                    if (null != business.getCompanySonBillId() &&
                                                                            Timestamp.parseDate3(payBusiness.getServiceMonth(),"yyyy-MM").
                                                                                    compareTo(Timestamp.parseDate3(serviceMonth,"yyyy-MM")) == 0) {
                                                                        businessIdSet.add(business.getBusinessId());
                                                                    }
                                                                }

                                                            }
                                                            for (MemberBusiness memberBusiness : member.getMemberBusinessSet()) {
                                                                if (null != memberBusiness.getCompanySonBillId() && member.getId().equals(detail.getMemberId()) &&
                                                                        member.getWaysOfCooperation().equals(method.getCooperationMethodId())) {
                                                                    if (null != member.getMemberMonthPayBusinessList()) {
                                                                        if (null != detail.getBusinessCityJsonList()) {
                                                                            a:for (MemberMonthPayBusiness business : member.getMemberMonthPayBusinessList()) {
                                                                                if (business.getBusinessId() == 5) {
                                                                                    continue a;
                                                                                }
                                                                                if (businessIdSet.contains(memberBusiness.getBusinessId())) {
                                                                                    Map<Integer, List<Object>> integerMap = detail.getBusinessCityJsonList() ;
                                                                                    if (null == integerMap.get(business.getBusinessId())) {
                                                                                        List<Object> list = integerMap.get(business.getBusinessId());
                                                                                        if (!list.contains(business.getCityId())) {
                                                                                            for (int i = 0; i < payPlaceList.size(); i++) {
                                                                                                if (payPlaceList.get(i).getCityId().equals(business.getCityId())){
                                                                                                    memberIdsSetQU.add(member.getId().toString() + business.getCityId());
                                                                                                    memberFee += payPlaceList.get(i).getPrice();
                                                                                                    fee1 += payPlaceList.get(i).getPrice();
                                                                                                    List<Object> objectList = new ArrayList<Object>();
                                                                                                    objectList.add(business.getCityId());
                                                                                                    objectList.add(payPlaceList.get(i).getPrice());
                                                                                                    listMap.put(business.getBusinessId(),objectList);
                                                                                                    /*Map<Object,Object> map1 = new HashMap<Object, Object>();
                                                                                                    map1.put(business.getBusinessId(),business.getCityId());
                                                                                                    map1.put("price",payPlaceList.get(i).getPrice());
                                                                                                    mapList.add(map1);*/
                                                                                                    continue a;
                                                                                                }
                                                                                            }
                                                                                        }

                                                                                    }
                                                                                    /*for (Map<Object, Object> integerMap : detail.getBusinessCityJsonList()) {

                                                                                    }*/
                                                                                } else {
                                                                                    for (int i = 0; i < payPlaceList.size(); i++) {
                                                                                        if (payPlaceList.get(i).getCityId().equals(business.getCityId())){
                                                                                            fee1 -= payPlaceList.get(i).getPrice();
                                                                                            memberFee -= payPlaceList.get(i).getPrice();
                                                                                            memberIdsSetQU.add(member.getId().toString() + business.getCityId());
                                                                                            continue a;
                                                                                        }
                                                                                    }
                                                                                }

                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }

                                                        if (listMap.size() > 0) {
                                                            MonthServiceFeeDetail msfd = new MonthServiceFeeDetail();
                                                            msfd.setMemberId(member.getId());
                                                            msfd.setCompanySonBillId(bill.getCompanySonBillId());
                                                            msfd.setAmount(fee1);
                                                            msfd.setWaysOfCooperation(member.getWaysOfCooperation());
                                                            msfd.setBusinessCityJson(JSONArray.fromObject(listMap).toString());
                                                            msfdList.add(msfd);
                                                        }
                                                    }


                                                }
                                            }
                                        }
                                    } else {
                                        for (Member member : memberList) {
                                            Double fee = 0.0;
//                                            List<Map<Object,Object>> mapList = new ArrayList<Map<Object, Object>>();
                                            Map<Integer,List<Object>> listMap = new HashMap<Integer, List<Object>>();
                                            if (null != member.getMemberBusinessSet() && null != member.getMemberMonthPayBusinessList()) {
                                                Set<Integer> businessIdSet = new HashSet<Integer>();
                                                for (MemberBusiness business : member.getMemberBusinessSet()) {
                                                    for (MemberMonthPayBusiness payBusiness : member.getMemberMonthPayBusinessList()) {
                                                        if (null != business.getCompanySonBillId() &&
                                                                Timestamp.parseDate3(payBusiness.getServiceMonth(),"yyyy-MM").
                                                                        compareTo(Timestamp.parseDate3(serviceMonth,"yyyy-MM")) == 0) {
                                                            businessIdSet.add(business.getBusinessId());
                                                        }
                                                    }

                                                }
                                                for (MemberBusiness memberBusiness : member.getMemberBusinessSet()) {

                                                    if (null != member.getMemberMonthPayBusinessList()) {
                                                        a: for (MemberMonthPayBusiness business : member.getMemberMonthPayBusinessList()) {
                                                            if (business.getBusinessId() == 5) {
                                                                continue a;
                                                            }
                                                            if (businessIdSet.contains(memberBusiness.getBusinessId())) {
                                                                if (memberBusiness.getBusinessId().equals(business.getBusinessId())) {
                                                                    if (null != business.getCompanySonBillId() &&
                                                                            member.getWaysOfCooperation().equals(method.getCooperationMethodId()) &&
                                                                            business.getCompanySonBillId().equals(bill.getCompanySonBillId()) ) {
                                                                        for (int i = 0; i < payPlaceList.size(); i++) {
                                                                            if (payPlaceList.get(i).getCityId().equals(business.getCityId())){
                                                                                //如果该地区没有包含 进行服务费叠加
                                                                                if (!memberIdsSetQU.contains(member.getId().toString() + business.getCityId())) {
                                                                                    fee += payPlaceList.get(i).getPrice();
                                                                                    memberFee += payPlaceList.get(i).getPrice();
                                                                                }
                                                                                    /*Map<Object,Object> map1 = new HashMap<Object, Object>();
                                                                                    map1.put(business.getBusinessId(),business.getCityId());
                                                                                    map1.put("price",payPlaceList.get(i).getPrice());
                                                                                    mapList.add(map1);*/
                                                                                List<Object> objectList = new ArrayList<Object>();
                                                                                objectList.add(business.getCityId());
                                                                                objectList.add(payPlaceList.get(i).getPrice());
                                                                                listMap.put(business.getBusinessId(),objectList);
                                                                                memberIdsSetQU.add(member.getId().toString() + business.getCityId());
                                                                                continue a;
                                                                            }
                                                                        }

                                                                       /* if (!memberIdsSetQU.contains(member.getId().toString() + business.getCityId())) {

                                                                        } else {
                                                                            Map<Object,Object> map1 = new HashMap<Object, Object>();
                                                                            map1.put(business.getBusinessId(),business.getCityId());
                                                                            mapList.add(map1);
                                                                        }*/

                                                                    }
                                                                }
                                                            } else {
                                                                for (int i = 0; i < payPlaceList.size(); i++) {
                                                                    if (payPlaceList.get(i).getCityId().equals(business.getCityId())){
                                                                        fee -= payPlaceList.get(i).getPrice();
                                                                        memberFee -= payPlaceList.get(i).getPrice();
                                                                        memberIdsSetQU.add(member.getId().toString() + business.getCityId());
                                                                        continue a;
                                                                    }
                                                                }
                                                            }


                                                        }
                                                    }
                                                }
                                            }

                                            if (listMap.size() > 0) {
                                                MonthServiceFeeDetail msfd = new MonthServiceFeeDetail();
                                                msfd.setMemberId(member.getId());
                                                msfd.setCompanySonBillId(bill.getCompanySonBillId());
                                                msfd.setAmount(fee);
                                                msfd.setWaysOfCooperation(member.getWaysOfCooperation());
                                                msfd.setBusinessCityJson(com.alibaba.fastjson.JSONArray.toJSONString(listMap));
                                                msfdList.add(msfd);
                                            }

                                        }
                                    }

                                    if (method.getIsPercent() == 1) {
                                        memberFee = (1 + method.getPercent() / 100 ) * memberFee;
                                    }
                                    if (method.getIsPercent() == 1) {
                                        memberFee = (1 + method.getPercent() / 100 ) * memberFee;
                                    }
                                    if (null != method.getServiceFeeMax() && memberFee > method.getServiceFeeMax()) {
                                        memberFee = method.getServiceFeeMax();
                                    }
                                    if (null != method.getServiceFeeMin() && memberFee < method.getServiceFeeMin()) {
                                        memberFee = method.getServiceFeeMin();
                                    }
                                    //服务费
                                    monthServiceFee.setServiceFee(memberFee);
                                    //服务月
                                    monthServiceFee.setMonth(Timestamp.parseDate3(serviceMonth,"yyyy-MM"));
                                    monthServiceFee.setCompanyCooperationMethodJson(com.alibaba.fastjson.JSONArray.toJSONString(methods));
                                    monthServiceFee.setMonthServiceFeeDetailList(msfdList);
                                    addListMSF.add(monthServiceFee);
                                }
                                break;
                                case 5 :
                                    //异动量
                                if (ydNum == 0){
                                        List<MonthServiceFeeDetail> msfdList = new ArrayList<MonthServiceFeeDetail>();
                                        MonthServiceFee monthServiceFee = new MonthServiceFee();
                                        monthServiceFee.setCompanySonTotalBillId(bill.getId());
                                        monthServiceFee.setServiceFeeConfigId(method.getServiceFeeConfigId());
                                        monthServiceFee.setCompanySonBillId(bill.getCompanySonBillId());
                                        monthServiceFee.setCompanyId(company.getId());
                                        monthServiceFee.setWaysOfCooperation(method.getCooperationMethodId());
                                        //临时服务费
                                        Double memberFee = 0.0;
//                                        //派遣
//                                        Boolean paiQian = true;
//                                        //外包
//                                        Boolean waiBao = true;
//                                        //普通
//                                        Boolean puTong = true;
                                        Set<Integer> cooperationMethodIdSet = new HashSet<Integer>();
                                        for (Member member : memberList) {
                                            if (method.getCooperationMethodId().equals(member.getWaysOfCooperation())) {
                                                if (!cooperationMethodIdSet.contains(method.getCooperationMethodId())) {
                                                    memberFee += method.getBaseFee();
                                                    cooperationMethodIdSet.add(method.getCooperationMethodId());
                                                }
                                                if (member.getRecordItemDtos().size() > 0) {
                                                    for (MemberBusinessUpdateRecordItemDto item : member.getRecordItemDtos()) {
                                                        MemberBusinessUpdateRecordItem recordItem = new MemberBusinessUpdateRecordItem();
                                                        recordItem.setId(item.getId());
                                                        recordItem.setIsCalculate(1);
                                                        recordItem.setCompanyId(member.getCompanyId());
                                                        recordItem.setBillMonth(month);
                                                        updateRecordItemList.add(recordItem);
                                                    }
                                                    memberFee += member.getRecordItemDtos().size() *
                                                            method.getServiceFeeList().get(0).getPrice();
                                                    MonthServiceFeeDetail msfd = new MonthServiceFeeDetail();
                                                    msfd.setMemberId(member.getId());
                                                    msfd.setAmount(member.getRecordItemDtos().size() * method.getServiceFeeList().get(0).getPrice());
                                                    msfd.setCompanySonBillId(bill.getCompanySonBillId());
                                                    msfdList.add(msfd);
                                                }

                                            }
                                        }
                                        if (method.getIsPercent() == 1) {
                                            memberFee = (1 + method.getPercent() / 100 ) * memberFee;
                                        }
                                        if (memberFee != 0) {
                                            if (method.getIsPercent() == 1) {
                                                memberFee = (1 + method.getPercent() / 100 ) * memberFee;
                                            }
                                            if (null != method.getServiceFeeMax() && memberFee > method.getServiceFeeMax()) {
                                                memberFee = method.getServiceFeeMax();
                                            }
                                            if (null != method.getServiceFeeMin() && memberFee < method.getServiceFeeMin()) {
                                                memberFee = method.getServiceFeeMin();
                                            }
                                            //服务费
                                            monthServiceFee.setServiceFee(memberFee);
                                            //服务月
                                            monthServiceFee.setMonth(Timestamp.parseDate3(serviceMonth,"yyyy-MM"));
                                            monthServiceFee.setCompanyCooperationMethodJson(com.alibaba.fastjson.JSONArray.toJSONString(methods));
                                            monthServiceFee.setMonthServiceFeeDetailList(msfdList);
                                            addListMSF.add(monthServiceFee);

                                    }
                                    ydNum ++;
                                }
                                break;
                                case 6 :
                                    //整体打包
                                if (ztNum <= methods.size()){
                                    Date date = CommonUtil.getMonth(company.getBusinessStartTime(),company.getBusinessCycle(),1,new Date()).get(0);
                                    if (Timestamp.parseDate3(new Date(),"yyyy-MM").compareTo(Timestamp.parseDate3(date,"yyyy-MM")) == 0) {
                                        MonthServiceFee monthServiceFee = new MonthServiceFee();
                                        monthServiceFee.setCompanySonTotalBillId(bill.getId());
                                        monthServiceFee.setServiceFeeConfigId(method.getServiceFeeConfigId());
                                        monthServiceFee.setCompanySonBillId(bill.getCompanySonBillId());
                                        monthServiceFee.setCompanyId(company.getId());
                                        monthServiceFee.setWaysOfCooperation(method.getCooperationMethodId());
                                        Double memberFee = 0.0;
                                        if (msfList.size() < 1) {
                                            if (null != method.getServiceFeeList() && method.getServiceFeeList().size() > 0) {
                                                memberFee = method.getServiceFeeList().get(0).getPrice() * method.getServiceFeeCycle();
                                                if (method.getIsPercent() == 1) {
                                                    memberFee = (1 + method.getPercent() / 100 ) * memberFee;
                                                }
                                            }
                                            if (memberFee != 0) {
                                                if (method.getIsPercent() == 1) {
                                                    memberFee = (1 + method.getPercent() / 100 ) * memberFee;
                                                }
                                                if (null != method.getServiceFeeMax() && memberFee > method.getServiceFeeMax()) {
                                                    memberFee = method.getServiceFeeMax();
                                                }
                                                if (null != method.getServiceFeeMin() && memberFee < method.getServiceFeeMin()) {
                                                    memberFee = method.getServiceFeeMin();
                                                }
                                                //服务费
                                                monthServiceFee.setServiceFee(memberFee);
                                            }
                                        } else {
                                            //是否需要交纳服务费 false:否 true：是否
                                            Boolean flag = true;
                                            for (MonthServiceFee fee : msfList) {
                                                if (method.getCooperationMethodId().equals(fee.getWaysOfCooperation())) {
                                                    flag = false;
                                                    break;
                                                }
                                            }
                                            if (flag) {
                                                if (null != method.getServiceFeeList() && method.getServiceFeeList().size() > 0) {
                                                    memberFee = method.getServiceFeeList().get(0).getPrice() * method.getServiceFeeCycle();
                                                    if (method.getIsPercent() == 1) {
                                                        memberFee = (1 + method.getPercent() / 100 ) * memberFee;
                                                    }
                                                }
                                                if (memberFee != 0) {
                                                    if (method.getIsPercent() == 1) {
                                                        memberFee = (1 + method.getPercent() / 100 ) * memberFee;
                                                    }
                                                    if (null != method.getServiceFeeMax() && memberFee > method.getServiceFeeMax()) {
                                                        memberFee = method.getServiceFeeMax();
                                                    }
                                                    if (null != method.getServiceFeeMin() && memberFee < method.getServiceFeeMin()) {
                                                        memberFee = method.getServiceFeeMin();
                                                    }
                                                    //服务费
                                                    monthServiceFee.setServiceFee(memberFee);
                                                }
                                            }
                                            monthServiceFee.setServiceFee(memberFee);
                                            break;
                                        }
                                        //服务月
                                        monthServiceFee.setMonth(Timestamp.parseDate3(serviceMonth,"yyyy-MM"));
                                        monthServiceFee.setCompanyCooperationMethodJson(com.alibaba.fastjson.JSONArray.toJSONString(methods));
                                        addListMSF.add(monthServiceFee);
                                    }
                                }
                                break;
                                case 7 :
                                    //按人数阶梯式整体
                                if (ztjtNum <= methods.size()) {
                                    Date date = CommonUtil.getMonth(company.getBusinessStartTime(),company.getBusinessCycle(),1,new Date()).get(0);
                                    if (Timestamp.parseDate3(new Date(),"yyyy-MM").compareTo(Timestamp.parseDate3(date,"yyyy-MM")) == 0) {

                                        List<Date> dateList = CommonUtil.getMonth(method.getServiceFeeStartTime(),1,method.getServiceFeeCycle(),new Date());
                                        for (Date date1 : dateList) {
                                            //按人数阶梯
                                            MonthServiceFee monthServiceFee = new MonthServiceFee();
                                            monthServiceFee.setCompanySonTotalBillId(bill.getId());
                                            monthServiceFee.setServiceFeeConfigId(method.getServiceFeeConfigId());
                                            monthServiceFee.setCompanySonBillId(bill.getCompanySonBillId());
                                            monthServiceFee.setCompanyId(company.getId());
                                            monthServiceFee.setWaysOfCooperation(method.getCooperationMethodId());
                                            //临时服务费
                                            Double memberFee = 0.0;
                                            if (msfList.size() < 1) {
                                                //记录人数
                                                Set<Integer> memberCountSet = new HashSet<Integer>();

                                                a:for (Member member : memberList) {
                                                    if (null != member.getMemberBusinessSet() && null != member.getMemberMonthPayBusinessList()) {
                                                        for (MemberBusiness business : member.getMemberBusinessSet()) {
                                                            for (MemberMonthPayBusiness payBusiness : member.getMemberMonthPayBusinessList()) {
                                                                if (business.getBusinessId().equals(payBusiness.getBusinessId())) {
                                                                    if (member.getWaysOfCooperation().equals(method.getCooperationMethodId())) {
                                                                        MonthServiceFeeDetail msfd = new MonthServiceFeeDetail();
                                                                        msfd.setMemberId(member.getId());
                                                                        msfd.setCompanySonBillId(bill.getCompanySonBillId());
                                                                        memberCountSet.add(member.getId());
                                                                        continue a;
                                                                    }
                                                                }
                                                            }

                                                        }
                                                    }
                                                }
                                                //本次应人数
                                                Integer memberCount = memberCountSet.size();
                                                if (memberCount > 0) {
                                                    if (method.getServiceFeeList().size() > 0) {
                                                        //升序排序
                                                        Collections.sort(method.getServiceFeeList(), new Comparator<CompanyCooperationServiceFee>() {
                                                            public int compare(CompanyCooperationServiceFee o1, CompanyCooperationServiceFee o2) {
                                                                return o1.getExtent() - o2.getExtent();
                                                            }
                                                        });
                                                    }
                                                    if (method.getIsPercent() == 1) {
                                                        memberFee = (1 + method.getPercent() / 100 ) * memberFee;
                                                    }
                                                    if (method.getIsPercent() == 1) {
                                                        memberFee = (1 + method.getPercent() / 100 ) * memberFee;
                                                    }
                                                    if (null != method.getServiceFeeMax() && memberFee > method.getServiceFeeMax()) {
                                                        memberFee = method.getServiceFeeMax();
                                                    }
                                                    if (null != method.getServiceFeeMin() && memberFee < method.getServiceFeeMin()) {
                                                        memberFee = method.getServiceFeeMin();
                                                    }
                                                    //服务费
                                                    monthServiceFee.setServiceFee(memberFee);

                                                }
                                            } else {
                                                //是否需要交纳服务费 false:否 true：是否
                                                Boolean flag = true;
                                                for (MonthServiceFee fee : msfList) {
                                                    if (method.getCooperationMethodId().equals(fee.getWaysOfCooperation())) {
                                                        flag = false;
                                                        break;
                                                    }
                                                }
                                                if (flag) {
                                                    //记录人数
                                                    Set<Integer> memberCountSet = new HashSet<Integer>();
                                                    a:for (Member member : memberList) {
                                                        if (null != member.getMemberBusinessSet() && null != member.getMemberMonthPayBusinessList()) {
                                                            for (MemberBusiness business : member.getMemberBusinessSet()) {
                                                                for (MemberMonthPayBusiness payBusiness : member.getMemberMonthPayBusinessList()) {
                                                                    if (business.getBusinessId().equals(payBusiness.getBusinessId())) {
                                                                        if (member.getWaysOfCooperation().equals(method.getCooperationMethodId())) {
                                                                            MonthServiceFeeDetail msfd = new MonthServiceFeeDetail();
                                                                            msfd.setMemberId(member.getId());
                                                                            msfd.setCompanySonBillId(bill.getCompanySonBillId());
                                                                            memberCountSet.add(member.getId());
                                                                            continue a;
                                                                        }
                                                                    }
                                                                }

                                                            }
                                                        }
                                                    }
                                                    //本次应人数
                                                    Integer memberCount = memberCountSet.size();
                                                    if (memberCount > 0) {
                                                        if (method.getServiceFeeList().size() > 0) {
                                                            //升序排序
                                                            Collections.sort(method.getServiceFeeList(), new Comparator<CompanyCooperationServiceFee>() {
                                                                public int compare(CompanyCooperationServiceFee o1, CompanyCooperationServiceFee o2) {
                                                                    return o1.getExtent() - o2.getExtent();
                                                                }
                                                            });
                                                        }
                                                        if (method.getIsPercent() == 1) {
                                                            memberFee = (1 + method.getPercent() / 100 ) * memberFee;
                                                        }
                                                        if (method.getIsPercent() == 1) {
                                                            memberFee = (1 + method.getPercent() / 100 ) * memberFee;
                                                        }
                                                        if (null != method.getServiceFeeMax() && memberFee > method.getServiceFeeMax()) {
                                                            memberFee = method.getServiceFeeMax();
                                                        }
                                                        if (null != method.getServiceFeeMin() && memberFee < method.getServiceFeeMin()) {
                                                            memberFee = method.getServiceFeeMin();
                                                        }
                                                        //服务费
                                                        monthServiceFee.setServiceFee(memberFee);
                                                    }
                                                }
                                                monthServiceFee.setServiceFee(memberFee);
                                            }
                                            //服务月
                                            monthServiceFee.setMonth(Timestamp.parseDate3(date1,"yyyy-MM"));
                                            monthServiceFee.setCompanyCooperationMethodJson(com.alibaba.fastjson.JSONArray.toJSONString(methods));
                                            addListMSF.add(monthServiceFee);
                                        }
                                    }
                                }
                                break;
                            }
                            //整体打包循环次数
                            ztjtNum++;
                            ztNum ++;
                            fwlNum++;
                        }

                }

            }



        }

    }

    /**
     * 获取人数阶梯收服务费
     * @param method
     * @param msfdList
     * @param temporaryCount
     * @param memberFee
     * @param memberCount
     * @param type 0：本次应缴   1：计算补差
     * @return
     */
    public static Double getServiceFeePersonNumJT(CompanyCooperationMethod method, List<MonthServiceFeeDetail> msfdList,
                                           Integer temporaryCount, Double memberFee, Integer memberCount,Integer type) {
        int j = 0;
        for (CompanyCooperationServiceFee fee : method.getServiceFeeList()) {
            if (j == 0 && fee.getExtent() > memberCount) {
                //小于最低人数
                memberFee = memberCount * fee.getPrice();
                for (MonthServiceFeeDetail detail : msfdList) {
                    if (type == 1) {
                        detail.setAmount(detail.getAmount() - fee.getPrice());
                    } else {
                        detail.setAmount(fee.getPrice());
                    }

                }
                break;
            } else if(method.getServiceFeeList().get(method.getServiceFeeList().size() - 1).getExtent() < memberCount) {
                //大于最高人数
                memberFee = method.getServiceFeeList().get(method.getServiceFeeList().size() - 1).getPrice() * memberCount;

                for (MonthServiceFeeDetail detail : msfdList) {
                    if (type == 1) {
                        detail.setAmount(detail.getAmount() - method.getServiceFeeList().get(method.getServiceFeeList().size() - 1).getPrice());
                    } else {
                        detail.setAmount(method.getServiceFeeList().get(method.getServiceFeeList().size() - 1).getPrice());
                    }
                }
                break;
            } else  {
                if (fee.getExtent() > memberCount && memberCount >= temporaryCount) {
                    //两者之间
                    memberFee = method.getServiceFeeList().get(j).getPrice() * memberCount;
                    for (MonthServiceFeeDetail detail : msfdList) {
                        if (type == 1) {
                            detail.setAmount(detail.getAmount() - method.getServiceFeeList().get(j).getPrice());
                        } else {
                            detail.setAmount(method.getServiceFeeList().get(j).getPrice());
                        }
                    }
                    break;
                }
            }
            temporaryCount = fee.getExtent();
            j ++;
        }
        return memberFee;
    }

    /**
     * 获取人数阶梯整体收服务费
     * @param method
     * @param temporaryCount
     * @param memberFee
     * @param memberCount
     * @param type 0：本次应缴   1：计算补差
     * @return
     */
    public static Double getServiceFeePersonNumJTZT(CompanyCooperationMethod method,
                                           Integer temporaryCount, Double memberFee, Integer memberCount,Integer type) {
        int j = 0;
        for (CompanyCooperationServiceFee fee : method.getServiceFeeList()) {
            if (j == 0 && fee.getExtent() > memberCount) {
                //小于最低人数
                memberFee = fee.getPrice();
                break;
            } else if(method.getServiceFeeList().get(method.getServiceFeeList().size() - 1).getExtent() < memberCount) {
                //大于最高人数
                memberFee = method.getServiceFeeList().get(method.getServiceFeeList().size() - 1).getPrice();
                break;
            } else  {
                if (fee.getExtent() > memberCount && memberCount >= temporaryCount) {
                    //两者之间
                    memberFee = method.getServiceFeeList().get(j).getPrice();
                    break;
                }
            }
            temporaryCount = fee.getExtent();
            j ++;
        }
        return memberFee;
    }


    /**
     * 获取人数阶梯收服务费2
     * @param method
     * @param memberCount
     * @return
     */
    public static Double getServiceFeePersonNumJT2(CompanyCooperationMethod method, Integer memberCount) {
        int j = 0;
        int temporaryCount = 0;
        for (CompanyCooperationServiceFee fee : method.getServiceFeeList()) {
            if (j == 0 && fee.getExtent() > memberCount) {
                //小于最低人数
                return memberCount * fee.getPrice();
            } else if(method.getServiceFeeList().get(method.getServiceFeeList().size() - 1).getExtent() < memberCount) {
                //大于最高人数
                return method.getServiceFeeList().get(method.getServiceFeeList().size() - 1).getPrice() * memberCount;
            } else  {
                if (fee.getExtent() > memberCount && memberCount >= temporaryCount) {
                    //两者之间
                    return method.getServiceFeeList().get(j).getPrice() * memberCount;
                }
            }
            temporaryCount = fee.getExtent();
            j ++;
        }
        return 0.0;
    }

    /**
     * 获取人数阶梯整体收服务费2
     * @param method
     * @param memberCount
     * @return
     */
    public static Double getServiceFeePersonNumJTZT2(CompanyCooperationMethod method, Integer memberCount) {
        int j = 0;
        int temporaryCount = 0;
        for (CompanyCooperationServiceFee fee : method.getServiceFeeList()) {
            if (j == 0 && fee.getExtent() > memberCount) {
                //小于最低人数
                return fee.getPrice();
            } else if(method.getServiceFeeList().get(method.getServiceFeeList().size() - 1).getExtent() < memberCount) {
                //大于最高人数
                return method.getServiceFeeList().get(method.getServiceFeeList().size() - 1).getPrice();
            } else  {
                if (fee.getExtent() > memberCount && memberCount >= temporaryCount) {
                    //两者之间
                    return method.getServiceFeeList().get(j).getPrice();
                }
            }
            temporaryCount = fee.getExtent();
            j ++;
        }
        return 0.0;
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


    private double getDouble(String s) {
        if(CommonUtil.isEmpty(s)){
            return 0.0;
        }
        return Double.parseDouble(s);
    }


    /**
     *  获取 子账单ID 通过子账单名称
     * @param companySonBills
     * @param billName
     * @return
     */
    private Integer getCompanySonBillId(List<CompanySonBill> companySonBills,String billName){
        if(null != companySonBills){
            for (CompanySonBill companySonBill : companySonBills) {
                if(companySonBill.getSonBillName().equals(billName)){
                    return companySonBill.getId();
                }
            }
        }
        return null;
    }

    /**
     *  工资税收计算
     * @param salaryInfo
     * @param salaryCounts
     */
    private SalaryInfo countSalary(SalaryInfo salaryInfo,List<SalaryCount> salaryCounts,List<SalaryType> salaryTypes){

        // 税前工资 = 应发工资 - 应扣工资
        salaryInfo.setSalaryBeforeTax(salaryInfo.getShouldSendSalary() - salaryInfo.getShouldBeDeductPay());
         double tax = 0.0; // 个人应缴税收

        // 各种税收计算
        if (!CommonUtil.isEmpty(salaryInfo.getCityName())) {
            // 获取应缴税额金额
            // 成都、重庆有公积金上限 故超过上限的按上限扣除
            // 成都：1839.9，重庆：2022
            double reserved = salaryInfo.getReserved();
            if("成都市".equals(salaryInfo.getCityName()) && salaryInfo.getReserved() > StatusConstant.RESERVED_MAX_CD){
                reserved = StatusConstant.RESERVED_MAX_CD;
            }
            else if("重庆市".equals(salaryInfo.getCityName()) && salaryInfo.getReserved() > StatusConstant.RESERVED_MAX_CQ){
                reserved = StatusConstant.RESERVED_MAX_CQ;
            }
            double taxAmount = salaryInfo.getShouldSendSalary() -
                    (reserved + salaryInfo.getMedical() + salaryInfo.getPension() + salaryInfo.getUnemployment());
            if(StatusConstant.SALARY.equals(salaryInfo.getSalaryTypeId()) &&
                    taxAmount > StatusConstant.THRESHOLD){
                // 工资
                // 应缴税的工资金额 税前工资 - 起征点
                double baseSalary = taxAmount - StatusConstant.THRESHOLD;
                tax = countTax(baseSalary,null);
//                if(salaryInfo.getReserved() > StatusConstant.RESERVED_MAX_CD &&
//                        "成都市".equals(salaryInfo.getCityName())){
//                    tax += countTax(salaryInfo.getReserved() - StatusConstant.RESERVED_MAX_CD,null);
//                }
//                if(salaryInfo.getReserved() > StatusConstant.RESERVED_MAX_CQ &&
//                        "重庆市".equals(salaryInfo.getCityName())){
//                    tax += countTax(salaryInfo.getReserved() - StatusConstant.RESERVED_MAX_CD,null);
//                }
            }
            else if(StatusConstant.YEAR_END_BONUS.equals(salaryInfo.getSalaryTypeId())){
                // 年终奖
                double v = salaryInfo.getSalaryBeforeTax() / 12;// 平均每月应得
                tax = countTax(v,salaryInfo.getSalaryBeforeTax());
            }
            else if(StatusConstant.LABOR_INCOME.equals(salaryInfo.getSalaryTypeId())){
                // 劳务所得
                double baseSalary = 0.0;
                if(salaryInfo.getSalaryBeforeTax() >= 800 && salaryInfo.getSalaryBeforeTax() < 4000){
                    baseSalary = salaryInfo.getSalaryBeforeTax() - 800;
                }
                else if(salaryInfo.getSalaryBeforeTax() >= 4000){
                    baseSalary = salaryInfo.getSalaryBeforeTax() - salaryInfo.getSalaryBeforeTax() * 0.2;
                }
                if(baseSalary <= 20000){
                    tax = baseSalary * 0.2;
                }
                else if(baseSalary > 20000 && baseSalary <= 50000){
                    tax = baseSalary * 0.3 - 2000;
                }
                else if(baseSalary > 50000){
                    tax = baseSalary * 0.4 - 7000;
                }
            }
            else if(StatusConstant.SEPARATION_ALLOWANCE.equals(salaryInfo.getSalaryTypeId())){
                // 离职补偿金
                tax = 0.0;
            }
        }
        salaryInfo.setIndividualIncomeTax(tax);
        salaryInfo.setTakeHomePay(salaryInfo.getSalaryBeforeTax() - salaryInfo.getIndividualIncomeTax());
        return salaryInfo;
    }

    private double countTax(double baseSalary,Double beforeTax) {
        double tax = 0.0;
        if(baseSalary < 1500){
            tax = ((null == beforeTax ? baseSalary : beforeTax) * 0.03 - 0);
        }
        else if(baseSalary >= 1500 && baseSalary < 4500){
            tax = ((null == beforeTax ? baseSalary : beforeTax) * 0.1 - 105);
        }
        else if(baseSalary >= 4500 && baseSalary < 9000){
            tax = ((null == beforeTax ? baseSalary : beforeTax) * 0.2 - 555);
        }
        else if(baseSalary >= 9000 && baseSalary < 35000){
            tax = ((null == beforeTax ? baseSalary : beforeTax) * 0.25 - 1005);
        }
        else if(baseSalary >= 35000 && baseSalary < 55000){
            tax = ((null == beforeTax ? baseSalary : beforeTax) * 0.3 - 2755);
        }
        else if (baseSalary >= 550000 && baseSalary < 80000){
            tax = ((null == beforeTax ? baseSalary : beforeTax ) * 0.35 - 5505);
        }
        else if(baseSalary >= 80000){
            tax = ((null == beforeTax ? baseSalary : beforeTax) * 0.45 - 13505);
        }
        return tax;
    }


    /**
     * 获取 工资类型ID
     * @param salaryTypes
     * @param typeName
     * @return
     */
    private Integer getSalaryType(List<SalaryType> salaryTypes,String typeName){
        if("工资".equals(typeName)){
            return StatusConstant.SALARY;
        }
        else if("年终奖".equals(typeName)){
            return StatusConstant.YEAR_END_BONUS;
        }
        else if("劳务所得".equals(typeName)){
            return StatusConstant.LABOR_INCOME;
        }
        else if("离职补偿金".equals(typeName)){
            return StatusConstant.SEPARATION_ALLOWANCE;
        }
        return null;
    }

    /**
     * 获取银行ID
     * @param banks
     * @param bankName
     * @return
     */
    private Integer getBankId(List<Bank> banks,String bankName){
        if(null != banks){
            for (Bank bank : banks) {
                if(bank.getBankName().equals(bankName)){
                    return bank.getId();
                }
            }
        }
        return null;
    }

    /**
     * 获取员工国籍信息
     * @param nationality
     * @return
     */
    private Integer getNationality(String nationality){

        if("非中国大陆".equals(nationality)){
            return 0;
        }
        else if("中国大陆".equals(nationality)){
            return 1;
        }
        else{
            return null;
        }

    }

    /**
     * 获取合作状态
     * @param stateName
     * @return
     */
    private Integer getStateCooperation(String stateName){

        if("离职".equals(stateName)){
            return 0;
        }
        else if("在职".equals(stateName)){
            return 1;
        }
        else{
            return null;
        }

    }


    /**
     * 通过城市名称 获取 城市ID
     * @param cityList
     * @param cityName
     * @return
     */
    private Integer getCityId(List<City> cityList,String cityName){
        for (City city : cityList) {
            if(cityName.equals(city.getName())){
                return city.getId();
            }
        }
        return null;
    }

    /**
     * 获取合作方式
     * @param ways
     * @return
     */
    private Integer getWaysOfCooperation(String ways){
        if("普通".equals(ways)){
            return 0;
        }
        else if("派遣".equals(ways)){
            return 1;
        }
        else if("外包".equals(ways)){
            return 2;
        }
        else{
            return null;
        }
    }


    /**
     * 获取证件类型
     * @param cardName
     * @return
     */
    private Integer getCardId(String cardName){
        if("身份证".equals(cardName)){
            return 0;
        }
        else if("护照".equals(cardName)){
            return 1;
        }
        else if("港澳台通行证".equals(cardName)){
            return 2;
        }
        else{
            return null;
        }
    }




    /**
     * 新增
     * @param member
     */
    @Transactional
    public void save(Member member,String businessArr) throws Exception {
        if(!CommonUtil.isEmpty(member.getCertificateNum())){
            List<Member> members = memberMapper.queryMemberByIdCard(member.getCertificateNum());
            if(null != members && members.size() > 0){
                throw new InterfaceCommonException(StatusConstant.OBJECT_EXIST,"员工已经存在");
            }
        }
        memberMapper.save(member);
        CompanyMember companyMember = new CompanyMember();
        companyMember.setCompanyId(member.getCompanyId());
        companyMember.setMemberId(member.getId());
        companyMemberMapper.addCompanyMember(companyMember);
        // 业务模块处理 Start
        if(!CommonUtil.isEmpty(businessArr)){
            JSONArray businessJSONArr = JSONArray.fromObject(businessArr);
            // 待处理的业务对象
            MemberSalary memberSalary = null;
            List<MemberBusinessItem> businessItemList = new ArrayList<MemberBusinessItem>();
            // 用户统计数据记录
            MemberCount memberCount = new MemberCount();
            memberCount.setMemberId(member.getId());
            memberCount.setCompanyId(member.getCompanyId());
            memberCount.setStateCooperation(member.getStateCooperation());


            //员工业务增减变
            List<MemberBusinessUpdateRecord> businessUpdateRecords = new ArrayList<MemberBusinessUpdateRecord>();
            List<MemberBusinessOtherItem> otherItemList = new ArrayList<MemberBusinessOtherItem>();
            List<MemberBusiness> memberBusinessList = new ArrayList<MemberBusiness>();
            for (Object o : businessJSONArr) {
                JSONObject jsonObject = JSONObject.fromObject(o);
                int businessId = jsonObject.getInt("id");
                MemberBusiness business = new MemberBusiness();
                business.setBusinessId(businessId);
                business.setMemberId(member.getId());
                if (businessId != 6 && businessId != 7) {
                    business.setCompanySonBillId(jsonObject.getInt("companySonBillId"));
                }
                memberBusinessList.add(business);
                memberBusinessMapper.addMemberBusiness(business);
                if(businessId == 3 || businessId == 4){
                    // 公积金、社保 业务处理
                    JSONObject temp  = jsonObject.getJSONObject("data");
                    MemberBusinessItem item = new MemberBusinessItem();
                    item.setPayPlaceId(temp.getInt("payPlaceId"));
                    item.setServeMethod(temp.getInt("serviceMethod"));
                    if(businessId == 3){
                        if(0 == item.getServeMethod()){
                            // 代理
                            memberCount.setPayPlaceIdOfSocialSecurity(item.getPayPlaceId());
                        }
                        else{
                            memberCount.setPayPlaceIdOfSocialSecurity(companyPayPlaceMapper.info(item.getPayPlaceId()).getPayPlaceId());
                        }

                    }
                    if(businessId == 4){
                        if(0 == item.getServeMethod()){
                            memberCount.setPayPlaceIdOfReservedFunds(item.getPayPlaceId());
                        }
                        else{
                            memberCount.setPayPlaceIdOfReservedFunds(companyPayPlaceMapper.info(item.getPayPlaceId()).getPayPlaceId());
                        }
                    }
                    item.setBaseType(temp.getInt("baseType"));
                    item.setIsReceivable(temp.getInt("isReceivable"));
                    if (null != temp.get("baseNumber")) {
                        item.setBaseNumber(temp.getDouble("baseNumber"));
                    }
                    item.setServiceStartTime(new Date(temp.getLong("serviceStartTime")));
                    item.setServiceEndTime(!CommonUtil.isEmpty(temp.getString("serviceEndTime")) ?
                            new Date(temp.getLong("serviceEndTime")) : null);
                    item.setBillStartTime(new Date(temp.getLong("billStartTime")));

                    int serviceMethod = temp.getInt("serviceMethod");
                    if(serviceMethod == 0){
                        // 代理
                        item.setOrganizationId(temp.getInt("organizationId"));
                        item.setTransactorId(temp.getInt("transactor"));
                    }
                    if(businessId == 3){
                        // 社保
                        item.setInsuranceLevelId(temp.getInt("other"));
                        item.setType(0);

                    }else{
                        item.setType(1);
                        item.setRatio(temp.getDouble("other"));
                    }
                    item.setIsFirstPay(1);
                    item.setMemberBusinessId(business.getId());
                    businessItemList.add(item);

                    // 增减变业务处理
                    MemberBusinessUpdateRecord record = new MemberBusinessUpdateRecord();
                    record.setMemberId(member.getId());
                    record.setStatus(0);
                    record.setPayPlaceId(item.getPayPlaceId());
                    record.setOrganizationId(item.getOrganizationId());
                    record.setTransactorId(item.getTransactorId());
                    if(businessId == BusinessEnum.sheBao.ordinal()){
                        record.setServiceType(0);
                        record.setInsuranceLevelId(item.getInsuranceLevelId());
                    }else if(businessId == BusinessEnum.gongJiJin.ordinal()){
                        record.setServiceType(1);
                    }
                    businessUpdateRecords.add(record);
                }
                else if(businessId == 5){
                    // 工资业务处理
                    JSONObject temp  = jsonObject.getJSONObject("data");
                    memberSalary = new MemberSalary();
                    memberSalary.setBankAccount(temp.getString("bankAccount"));
                    memberSalary.setBankName(temp.getString("bankName"));
                    try {
                        memberSalary.setCityId(temp.getInt("cityId"));
                    } catch (JSONException e) {

                    }
                    memberSalary.setMemberId(member.getId());
                    memberSalary.setNationality(temp.getInt("nationality"));
                    memberSalary.setPhone(temp.getString("phoneNumber"));
                }
                else if(businessId == 6 || businessId == 7){
                    // 商业险、一次性业务 业务处理
                    JSONArray temp  = jsonObject.getJSONArray("data");
                    for (Object o1 : temp) {
                        JSONObject object = JSONObject.fromObject(o1);
                        MemberBusinessOtherItem item = new MemberBusinessOtherItem();
                        item.setBusinessItemId(object.getInt("itemId"));
                        item.setMemberBusinessId(business.getId());
                        item.setCompanySonBillId(object.getInt("companySonBillId"));
                        otherItemList.add(item);
                    }
                }
            }
            // 开始处理业务
            if(memberBusinessList.size() > 0){
                if(null != memberSalary){
                    memberSalaryMapper.delMemberSalary(member.getId());
                    // 增加员工工资信息
                    memberSalaryMapper.addMemberSalary(memberSalary);
                }
                // 增加 公积金/社保
                if(businessItemList.size() > 0){
                    memberBusinessItemMapper.save(businessItemList);
                }
                if(otherItemList.size() > 0){
                    memberBusinessOtherItemMapper.batchAddMemberBusinessOtherItem(otherItemList);
                }

            }
            memberCountMapper.add(memberCount);
            // 查询员工所有数据，判断增减变
            Member info = memberMapper.info(member.getId());
            Map<String, Object> data = ServiceUtil.handleUpdateItem(null, info);
            List<MemberBusinessUpdateRecord> records = (List<MemberBusinessUpdateRecord> )data.get("recordList");
            List<MemberBusinessUpdateRecordItem> itemList = (List<MemberBusinessUpdateRecordItem>)data.get("recordItemList");
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

    /**
     * 更新不为空字段
     * @param member
     */
    @Transactional
    public void update(Member member,String businessArr) throws Exception {

        Member member1 = memberMapper.queryMemberByIdCards(member.getCertificateNum());
        if(null != member1 && !member.getId().equals(member1.getId())){
            throw new InterfaceCommonException(StatusConstant.OBJECT_EXIST,"员工已经存在");
        }

        Member old = memberMapper.info(member.getId());

        memberMapper.update(member);
        CompanyMember companyMember = new CompanyMember();
        companyMember.setCompanyId(member.getCompanyId());
        companyMember.setMemberId(member.getId());
        companyMemberMapper.addCompanyMember(companyMember);

        // 用户统计数据记录
        MemberCount memberCount = new MemberCount();
        memberCount.setMemberId(member.getId());
        memberCount.setCompanyId(member.getCompanyId());
        memberCount.setStateCooperation(member.getStateCooperation());


        if(!CommonUtil.isEmpty(businessArr)){
            JSONArray businessJSONArr = JSONArray.fromObject(businessArr);
            // 待处理的业务对象
            MemberSalary memberSalary = null;
            List<MemberBusinessItem> businessItemList = new ArrayList<MemberBusinessItem>();
            List<MemberBusinessOtherItem> otherItemList = new ArrayList<MemberBusinessOtherItem>();
            List<MemberBusiness> memberBusinessList = new ArrayList<MemberBusiness>();
            //社保是否是第一次缴纳
            Integer sIsFirstPay = 1;
            Date sIsFirstPayMonth = new Date();
            //公积金是否是第一次缴纳
            Integer rIsFirstPay = 1;
            Date rIsFirstPayMonth = new Date();
            //公积金缴纳
            List<MemberBusinessItem> memberBusinessItems = memberBusinessItemMapper.listByMemberId(member.getId());
            for (MemberBusinessItem item : memberBusinessItems) {
                if (item.getType() == 0) {
                    sIsFirstPay = item.getIsFirstPay();
                    sIsFirstPayMonth = item.getFirstPayBillMonth();
                    continue;
                } else {
                    rIsFirstPay = item.getIsFirstPay();
                    sIsFirstPayMonth = item.getFirstPayBillMonth();
                    continue;
                }
            }
            // 开始处理业务
            // 删除公积金/社保
            memberBusinessItemMapper.delMemberBusinessItemByMember(member.getId());
            // 删除工资信息
            memberSalaryMapper.delMemberSalary(member.getId());
            // 删除业务子类
            memberBusinessOtherItemMapper.delMemberBusinessOtherItem(member.getId());
            // 删除之前业务
            memberBusinessMapper.delMemberBusiness(member.getId());


            for (Object o : businessJSONArr) {
                JSONObject jsonObject = JSONObject.fromObject(o);
                int businessId = jsonObject.getInt("id");
                MemberBusiness business = new MemberBusiness();
                business.setBusinessId(businessId);
                business.setMemberId(member.getId());
                if (businessId != 6  && businessId != 7) {
                    business.setCompanySonBillId(jsonObject.getInt("companySonBillId"));
                }
                memberBusinessList.add(business);
                memberBusinessMapper.addMemberBusiness(business);
                if(businessId == 3 || businessId == 4){
                    // 公积金、社保 业务处理
                    JSONObject temp  = jsonObject.getJSONObject("data");
                    MemberBusinessItem item = new MemberBusinessItem();
                    item.setPayPlaceId(temp.getInt("payPlaceId"));
                    item.setServeMethod(temp.getInt("serviceMethod"));
                    item.setBaseType(temp.getInt("baseType"));
                    item.setIsReceivable(temp.getInt("isReceivable"));
                    if (item.getBaseType() == 2 && null != temp.get("baseNumber")) {
                        item.setBaseNumber(temp.getDouble("baseNumber"));
                    }
                    item.setServiceStartTime(new Date(temp.getLong("serviceStartTime")));
                    item.setServiceEndTime(!CommonUtil.isEmpty(temp.getString("serviceEndTime")) ?
                            new Date(temp.getLong("serviceEndTime")) : null);
                    item.setBillStartTime(new Date(temp.getLong("billStartTime")));

                    int serviceMethod = temp.getInt("serviceMethod");
                    if(businessId == 3){
                        if(0 == item.getServeMethod()){
                            // 代理
                            memberCount.setPayPlaceIdOfSocialSecurity(item.getPayPlaceId());
                        }
                        else{
                            memberCount.setPayPlaceIdOfSocialSecurity(companyPayPlaceMapper.info(item.getPayPlaceId()).getPayPlaceId());
                        }

                    }
                    if(businessId == 4){
                        if(0 == item.getServeMethod()){
                            memberCount.setPayPlaceIdOfReservedFunds(item.getPayPlaceId());
                        }
                        else{
                            memberCount.setPayPlaceIdOfReservedFunds(companyPayPlaceMapper.info(item.getPayPlaceId()).getPayPlaceId());
                        }
                    }
                    if(serviceMethod == 0){
                        // 代理
                        item.setOrganizationId(temp.getInt("organizationId"));
                        item.setTransactorId(temp.getInt("transactor"));
                    }
                    if(businessId == 3){
                        // 社保
                        item.setInsuranceLevelId(temp.getInt("other"));
                        item.setType(0);
                        item.setIsFirstPay(sIsFirstPay);
                        item.setFirstPayBillMonth(sIsFirstPayMonth);
                    }else{
                        item.setType(1);
                        item.setRatio(temp.getDouble("other"));
                        item.setIsFirstPay(rIsFirstPay);
                        item.setFirstPayBillMonth(rIsFirstPayMonth);
                    }
                    item.setMemberBusinessId(business.getId());
                    businessItemList.add(item);
                }
                else if(businessId == 5){
                    // 工资业务处理
                    JSONObject temp  = jsonObject.getJSONObject("data");
                    memberSalary = new MemberSalary();
                    memberSalary.setBankAccount(temp.getString("bankAccount"));
                    memberSalary.setBankName(temp.getString("bankName"));
                    memberSalary.setMemberId(member.getId());
                    try {
                        memberSalary.setCityId(temp.getInt("cityId"));
                    } catch (JSONException e) {

                    }
                    memberSalary.setNationality(temp.getInt("nationality"));
                    //memberSalary.setPhone(temp.getString("phoneNumber"));
                }
                else if(businessId == 6 || businessId == 7){
                    // 商业险、一次性业务 业务处理
                    JSONArray temp  = jsonObject.getJSONArray("data");
                    for (Object o1 : temp) {
                        JSONObject object = JSONObject.fromObject(o1);
                        MemberBusinessOtherItem item = new MemberBusinessOtherItem();
                        item.setBusinessItemId(object.getInt("itemId"));
                        item.setMemberBusinessId(business.getId());
                        item.setCompanySonBillId(object.getInt("companySonBillId"));
                        otherItemList.add(item);
                    }
                }
            }



            if(memberBusinessList.size() > 0){

                if(null != memberSalary){
                    // 增加员工工资信息
                    memberSalaryMapper.addMemberSalary(memberSalary);
                }
                // 增加 公积金/社保
                if(businessItemList.size() > 0){
                    memberBusinessItemMapper.save(businessItemList);
                }
                if(otherItemList.size() > 0){
                    memberBusinessOtherItemMapper.batchAddMemberBusinessOtherItem(otherItemList);
                }
//
//                if (businessUpdateRecords.size() > 0) {
//                    memberBusinessUpdateRecordMapper.updateList(businessUpdateRecords);
//                }
            }
        }

        MemberCount info1 = memberCountMapper.info(member.getId(), new Date());
        if(null != info1){
            memberCount.setId(info1.getId());
            memberCountMapper.update(memberCount);
        }

        // 查询员工所有数据，判断增减变
        Member info = memberMapper.info(member.getId());

        // 从历史数据 - 构建出老员工数据
        old = ServiceUtil.buildMemberForRecord(old);


        Map<String, Object> data = ServiceUtil.handleUpdateItem(old, info);
        List<MemberBusinessUpdateRecord> records = (List<MemberBusinessUpdateRecord> )data.get("recordList");
        List<MemberBusinessUpdateRecordItem> itemList = (List<MemberBusinessUpdateRecordItem>)data.get("recordItemList");
        List<MemberBusinessUpdateRecordItem> itemListUpdate = (List<MemberBusinessUpdateRecordItem>)data.get("itemListUpdate");

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

            // 查询该员工是否存在 待反馈的数据，如果有待反馈的数据，则不能变更信息
            List<Member> feeBack = memberMapper.queryFeeBack(member.getId());
            if(null != feeBack && feeBack.size() > 0){
                throw new InterfaceCommonException(StatusConstant.Fail_CODE,"该员工存在待反馈的实做数据");
            }

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


    /**
     * 更新所有字段
     * @param member
     */
    public void updateAll(Member member) {
        memberMapper.updateAll(member);
    }

    /**
     * 详情
     * @param id
     * @return
     */
    public Member info(Integer id) {
        return memberMapper.info(id);
    }


    /**
     * 后台页面 分页获取员工
     * @param pageArgs 分页属性
     * @param certificateNum 证件编号
     * @param userName 员工名
     * @param companyId 所属公司id
     * @param stateCooperation 合作状态 0：离职  1：在职
     * @param waysOfCooperation 合作方式 0：普通 1：派遣  2：外包
     * @param leaveOfficeTimeStartTime 离职时间开始
     * @param leaveOfficeTimeEndTime 离职时间结束
     * @param contractStartTimeStartTime 合同执行时间开始
     * @param contractStartTimeEndTime 合同执行时间结束
     * @param contractEndTimeStartTime 合同结束时间开始
     * @param contractEndTimeEndTime 合同结束时间结束
     * @param cityId 城市id
     * @param certificateType 证件类型
     * @return
     */
    public PageList<Member> list(PageArgs pageArgs , String certificateNum , String userName ,
                                 Integer companyId , Integer stateCooperation , Integer waysOfCooperation ,
                                 Date leaveOfficeTimeStartTime , Date leaveOfficeTimeEndTime ,
                                 Date contractStartTimeStartTime , Date contractStartTimeEndTime ,
                                 Date contractEndTimeStartTime , Date contractEndTimeEndTime ,
                                 Integer cityId , Integer certificateType, String companyName,Integer beforeService) {
        PageList<Member> pageList = new PageList<Member>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("certificateNum", certificateNum);
        map.put("userName", userName);
        map.put("companyId", companyId);
        map.put("stateCooperation", stateCooperation);
        map.put("waysOfCooperation", waysOfCooperation);
        map.put("leaveOfficeTimeStartTime", leaveOfficeTimeStartTime);
        map.put("leaveOfficeTimeEndTime", leaveOfficeTimeEndTime);
        map.put("contractStartTimeStartTime", contractStartTimeStartTime);
        map.put("contractStartTimeEndTime", contractStartTimeEndTime);
        map.put("contractEndTimeStartTime", contractEndTimeStartTime);
        map.put("contractEndTimeEndTime", contractEndTimeEndTime);
        map.put("beforeService", beforeService);
        map.put("cityId", cityId);
        map.put("certificateType", certificateType);
        map.put("companyName", companyName);
        int count = memberMapper.listCount(map);
        if (count > 0) {
            map.put("pageArgs", pageArgs);
            List<Member> list = memberMapper.list(map);
            for (Member member : list) {
                member.setCityName(member.getCityName().replace("中国,","").replace("省",""));
            }
            pageList.setList(list);

        }
        pageList.setTotalSize(count);
        return pageList;
    }

    /**
     * 通过公司ID获取员工
     */
    public List<Member> getCompanyMember (Integer companyId) {
        List<Member> list = new ArrayList<Member>();
        list = memberMapper.getCompanyMember(companyId);
        return list;
    }



//    /***
//     * 获取服务费
//     * @param companySonTotalBillList
//     * @param company
//     * @param methods
//     * @throws ParseException
//     * @throws IOException
//     */
////    public void buildServiceFee2(List<CompanySonTotalBill> companySonTotalBillList, Company company,
////                                List<CompanyCooperationMethod> methods ,Date month,
//                                 List<MonthServiceFee> addListMSF,Date serviceMonth) throws ParseException, IOException {
//
//        List<Date> serviceMonths = new ArrayList<Date>();
//
//
//        //已缴纳服务费
////        List<MonthServiceFee> msfList = monthServiceFeeMapper.getByDateAndCompanyId(serviceMonth ,company.getId());
//        //按固定额收费记录已缴纳临时集合
//        Set<Integer> memberIdsSetGD = new HashSet<Integer>();
//        //按服务区收费记录已缴纳临时集合
//        Set<String> memberIdsSetQU = new HashSet<String>();
//        //服务类别次数
//        int fwlNum = 0;
//        //异动量次数
//        int ydNum = 0;
//        //整体阶梯次数
//        int ztjtNum = 0;
//        //整体次数
//        int ztNum = 0;
//        out:for (CompanySonTotalBill bill : companySonTotalBillList) {
//            if (Timestamp.parseDate3(bill.getBillMonth(),"yyyy-MM").compareTo(month) == 0) {
//                if (null != bill.getCompanySonBillItemList()) {
//                    for (CompanyCooperationMethod method : methods) {
//                        switch (method.getServiceFeeConfigId()) {
//                            case 1 :
//                                //按固定额
//                                for (Date date : serviceMonths) {
//                                    //根据公司id和时间 获取员工 包含员工
//                                    List<Member> memberList = memberMapper.getMemberByCompanyIdAndDate(company.getId(),serviceMonth);
//                                    List<Integer> memberIdSet = new ArrayList<Integer>();
//                                    for (int i = 0; i < memberList.size(); i++) {
//                                        memberIdSet.add(memberList.get(i).getId());
//                                    }
//                                    //已缴纳服务费
//                                    List<MonthServiceFee> msfList = monthServiceFeeMapper.getByDateAndCompanyId(date ,company.getId());
//                                    //服务费明细
//                                    List<MonthServiceFeeDetail> msfdList = new ArrayList<MonthServiceFeeDetail>();
//                                    MonthServiceFee monthServiceFee = new MonthServiceFee();
//                                    monthServiceFee.setCompanySonTotalBillId(bill.getId());
//                                    monthServiceFee.setServiceFeeConfigId(method.getServiceFeeConfigId());
//                                    monthServiceFee.setCompanySonBillId(bill.getCompanySonBillId());
//                                    monthServiceFee.setCompanyId(company.getId());
//                                    //服务费
//                                    Double memberFee = 0.0;
//                                    if (msfList.size() > 0 ) {
//                                        for (MonthServiceFee fee : msfList) {
//                                            if (fee.getServiceFeeConfigId().equals(method.getServiceFeeConfigId()) &&
//                                                    fee.getCompanySonBillId().equals(bill.getCompanySonBillId()) &&
//                                                    null != fee.getMonthServiceFeeDetailList() &&
//                                                    fee.getMonthServiceFeeDetailList().size() > 0) {
//                                                for (MonthServiceFeeDetail detail : fee.getMonthServiceFeeDetailList()) {
//                                                    if (!memberIdSet.contains(detail.getMemberId())) {
//                                                        a:for (Member member : memberList) {
//                                                            if (member.getId().equals(detail.getMemberId()) &&
//                                                                    member.getWaysOfCooperation().equals(method.getCooperationMethodId())) {
//                                                                MonthServiceFeeDetail msfd = new MonthServiceFeeDetail();
//                                                                msfd.setMemberId(detail.getMemberId());
//                                                                msfd.setAmount(method.getServiceFeeList().get(0).getPrice());
//                                                                msfd.setCompanySonBillId(bill.getCompanySonBillId());
//                                                                msfdList.add(msfd);
//                                                                memberFee += method.getServiceFeeList().get(0).getPrice();
//                                                                continue a;
//                                                            }
//                                                        }
//                                                    }
//
//                                                }
//                                            }
//                                        }
//                                    } else {
//                                        for (Member member : memberList) {
//                                            if (null != member.getMemberBusinessSet() && null != member.getMemberMonthPayBusinessList()) {
//                                                for (MemberBusiness business : member.getMemberBusinessSet()) {
//                                                    for (MemberMonthPayBusiness payBusiness : member.getMemberMonthPayBusinessList()) {
//                                                        if (business.getBusinessId().equals(payBusiness.getBusinessId()) &&
//                                                                Timestamp.parseDate3(payBusiness.getServiceMonth(),"yyyy-MM").
//                                                                        compareTo(Timestamp.parseDate3(serviceMonth,"yyyy-MM")) == 0) {
//                                                            if (null != business.getCompanySonBillId() && member.getWaysOfCooperation().equals(method.getCooperationMethodId()) &&
//                                                                    business.getCompanySonBillId().equals(bill.getCompanySonBillId()) &&
//                                                                    !memberIdsSetGD.contains(member.getId())) {
//                                                                MonthServiceFeeDetail msfd = new MonthServiceFeeDetail();
//                                                                msfd.setMemberId(member.getId());
//                                                                msfd.setAmount(method.getServiceFeeList().get(0).getPrice());
//                                                                msfd.setCompanySonBillId(bill.getCompanySonBillId());
//                                                                msfd.setWaysOfCooperation(member.getWaysOfCooperation());
//                                                                msfdList.add(msfd);
//                                                                memberFee += method.getServiceFeeList().get(0).getPrice();
//                                                                memberIdsSetGD.add(member.getId());
//                                                            }
//                                                        }
//                                                    }
//
//                                                }
//                                            }
//                                        }
//                                    }
//
//                                    if (method.getIsPercent() == 1) {
//                                        memberFee = (1 + method.getPercent() / 100 ) * memberFee;
//                                    }
//
//                                    if (null != method.getServiceFeeMax() && memberFee > method.getServiceFeeMax()) {
//                                        memberFee = method.getServiceFeeMax();
//                                    }
//                                    if (null != method.getServiceFeeMin() && memberFee < method.getServiceFeeMin()) {
//                                        memberFee = method.getServiceFeeMin();
//                                    }
//                                    //服务费
//                                    monthServiceFee.setServiceFee(memberFee);
//                                    //服务月
//                                    monthServiceFee.setMonth(Timestamp.parseDate3(date,"yyyy-MM"));
//                                    monthServiceFee.setCompanyCooperationMethodJson(com.alibaba.fastjson.JSONArray.toJSONString(methods));
//                                    monthServiceFee.setMonthServiceFeeDetailList(msfdList);
//                                    addListMSF.add(monthServiceFee);
//                                 }
//                            break;
//                            case 2 :
//                            {
//                                //最大人数
//                                Integer maxMemberNum = 0;
//                                //最大人数的集合 记录明细使用 现 暂不使用
//                                List<Member> maxMemberList = new ArrayList<Member>();
//                                //服务月份集合
//                                Set<Date> dateSMSet = new HashSet<Date>();
//                                //按人数阶梯
//                                for (Date date : serviceMonths) {
//                                    //已缴纳服务费
//                                    List<MonthServiceFee> msfList = monthServiceFeeMapper.getByDateAndCompanyId(date ,company.getId());
//                                    //如果不存在 就进行人数统计 并将计算服务费
//                                    if (msfList.size() == 0) {
//                                        //根据公司id和时间 获取员工 包含员工
//                                        List<Member> memberList = memberMapper.getMemberByCompanyIdAndDate(company.getId(),date);
//                                        if (memberList.size() > 0) {
//                                            //记录人数
//                                            Set<Integer> memberCountSet = new HashSet<Integer>();
//                                            dateSMSet.add(date);
//                                            a:for (Member member : memberList) {
//                                                if (null != member.getMemberBusinessSet() && null != member.getMemberMonthPayBusinessList()
//                                                        && !memberCountSet.contains(member.getId())) {
//                                                    for (MemberBusiness business : member.getMemberBusinessSet()) {
//                                                        if (business.getCompanySonBillId().equals(bill.getCompanySonBillId())) {
//                                                            for (MemberMonthPayBusiness payBusiness : member.getMemberMonthPayBusinessList()) {
//                                                                if (business.getBusinessId().equals(payBusiness.getBusinessId()) &&
//                                                                        Timestamp.parseDate3(payBusiness.getServiceMonth(),"yyyy-MM").
//                                                                                compareTo(Timestamp.parseDate3(serviceMonth,"yyyy-MM")) == 0) {
//                                                                    if (member.getWaysOfCooperation().equals(method.getCooperationMethodId())) {
////                                                                    MonthServiceFeeDetail msfd = new MonthServiceFeeDetail();
////                                                                    msfd.setMemberId(member.getId());
////                                                                    msfd.setWaysOfCooperation(member.getWaysOfCooperation());
////                                                                    msfd.setCompanySonBillId(bill.getCompanySonBillId());
////                                                                    msfdList.add(msfd);
//                                                                        memberCountSet.add(member.getId());
//                                                                        continue a;
//                                                                    }
//                                                                }
//                                                            }
//                                                        }
//                                                    }
//                                                }
//                                            }
//
//                                            if (maxMemberNum < memberCountSet.size()) {
//                                                maxMemberList = new ArrayList<Member>(memberList);
//                                                maxMemberNum = memberCountSet.size();
//                                            }
//                                        }
//                                    }
//                                }
//
//                                //服务费明细
//                                List<MonthServiceFeeDetail> msfdList = new ArrayList<MonthServiceFeeDetail>();
//                                MonthServiceFee monthServiceFee = new MonthServiceFee();
//                                monthServiceFee.setCompanySonTotalBillId(bill.getId());
//                                monthServiceFee.setServiceFeeConfigId(method.getServiceFeeConfigId());
//                                monthServiceFee.setCompanySonBillId(bill.getCompanySonBillId());
//                                monthServiceFee.setCompanyId(company.getId());
//
//                                if (method.getServiceFeeList().size() > 0) {
//                                    //升序排序
//                                    Collections.sort(method.getServiceFeeList(), new Comparator<CompanyCooperationServiceFee>() {
//                                        public int compare(CompanyCooperationServiceFee o1, CompanyCooperationServiceFee o2) {
//                                            return o1.getExtent() - o2.getExtent();
//                                        }
//                                    });
//                                }
//                                //临时服务费
//                                Double memberFee = 0.0;
//                                {
//                                    //临时记录人数
//                                    Integer temporaryCount = 0;
//                                    memberFee = getServiceFeePersonNumJT(method, msfdList, temporaryCount, memberFee, maxMemberNum,0);
//                                }
//
//                                if (method.getIsPercent() == 1) {
//                                    memberFee = (1 + method.getPercent() / 100 ) * memberFee;
//                                }
//                                if (null != method.getServiceFeeMax() && memberFee > method.getServiceFeeMax()) {
//                                    memberFee = method.getServiceFeeMax();
//                                }
//                                if (null != method.getServiceFeeMin() && memberFee < method.getServiceFeeMin()) {
//                                    memberFee = method.getServiceFeeMin();
//                                }
//                                monthServiceFee.setCompanyCooperationMethodJson(com.alibaba.fastjson.JSONArray.toJSONString(methods));
////                                monthServiceFee.setMonthServiceFeeDetailList(msfdList);
//                                //服务费
//                                monthServiceFee.setServiceFee(memberFee);
//                                for (Date date : dateSMSet) {
//                                    //服务月
//                                    monthServiceFee.setMonth(Timestamp.parseDate3(date,"yyyy-MM"));
//                                    addListMSF.add(monthServiceFee);
//                                }
//
//                            }
//                            break;
//                            case 3 :
//                                //服务类别
//                                if (fwlNum == 0){
//                                    for (Date date : serviceMonths) {
//                                        //已缴纳服务费
//                                        List<MonthServiceFee> msfList = monthServiceFeeMapper.getByDateAndCompanyId(date ,company.getId());
//                                        //如果不存在 就进行人数统计 并将计算服务费
//                                        if (msfList.size() == 0) {
//                                            //根据公司id和时间 获取员工 包含员工
//                                            List<Member> memberList = memberMapper.getMemberByCompanyIdAndDate(company.getId(),date);
//                                            if (memberList.size() > 0) {
//
//                                            }
//                                        }
//                                    }
//                                        //服务费明细
//                                    List<MonthServiceFeeDetail> msfdList = new ArrayList<MonthServiceFeeDetail>();
//                                    MonthServiceFee monthServiceFee = new MonthServiceFee();
//                                    monthServiceFee.setCompanySonTotalBillId(bill.getId());
//                                    monthServiceFee.setServiceFeeConfigId(method.getServiceFeeConfigId());
//                                    monthServiceFee.setCompanySonBillId(bill.getCompanySonBillId());
//                                    monthServiceFee.setCompanyId(company.getId());
//                                    //临时服务费
//                                    Double memberFee = 0.0;
//                                    if (msfList.size() > 0) {
//                                        for (MonthServiceFee fee : msfList) {
//                                            if (null != fee.getMonthServiceFeeDetailList()) {
//                                                a:for (Member member : memberList) {
//                                                    if (null == member.getMemberBusinessSet()) {
//                                                        List<MemberBusiness> memberBusinessList = new ArrayList<MemberBusiness>(member.getMemberBusinessSet());
//                                                        Collections.sort(memberBusinessList, new Comparator<MemberBusiness>() {
//                                                            public int compare(MemberBusiness o1, MemberBusiness o2) {
//                                                                return o1.getBusinessId().compareTo(o1.getBusinessId());
//                                                            }
//                                                        });
//                                                        Set<Integer> businessIdSet = new HashSet<Integer>();
//                                                        for (MemberBusiness business : member.getMemberBusinessSet()) {
//                                                            businessIdSet.add(business.getBusinessId());
//                                                        }
//
//                                                        if (method.getCooperationMethodId().equals(member.getWaysOfCooperation())) {
//                                                            if (null != method.getBusinessServiceFeeList() && null != member.getMonthPayBusinessDtoList()) {
//                                                                for (MemberMonthPayBusinessDto dto : member.getMonthPayBusinessDtoList()) {
//                                                                    if (null != dto.getMemberMonthPayBusinessStr()) {
//                                                                        for (CompanyCooperationBusinessServiceFee fee1 : method.getBusinessServiceFeeList()) {
//                                                                            if (dto.getServiceMonth().
//                                                                                    compareTo(Timestamp.parseDate3(month,"yyyy-MM")) == 0) {
//                                                                                Set<Integer> businessIdSet2 = ClassConvert.strToIntegerSetGather(dto.getMemberMonthPayBusinessStr().split(","));
//                                                                                String memberMonthPayBusinessStr = "";
//                                                                                int i = 0;
//                                                                                for (Integer integer : businessIdSet2) {
//                                                                                    if (businessIdSet.contains(integer)) {
//                                                                                        if (i == 0) {
//                                                                                            memberMonthPayBusinessStr = String.valueOf(integer);
//                                                                                        } else {
//                                                                                            memberMonthPayBusinessStr += "," + String.valueOf(integer);
//                                                                                        }
//                                                                                        i ++;
//                                                                                    }
//                                                                                }
//
//                                                                                if (fee1.getBusinessIds().equals(memberMonthPayBusinessStr)) {
//                                                                                    MonthServiceFeeDetail msfd = new MonthServiceFeeDetail();
//                                                                                    msfd.setMemberId(member.getId());
//                                                                                    msfd.setCompanySonBillId(bill.getCompanySonBillId());
//                                                                                    Double amount = fee1.getPrice();
//                                                                                    for (MonthServiceFeeDetail detail : fee.getMonthServiceFeeDetailList()) {
//                                                                                        if (detail.getMemberId().equals(member.getId())) {
//                                                                                            amount = amount - detail.getAmount();
//                                                                                        }
//                                                                                    }
//                                                                                    msfd.setAmount(amount);
//                                                                                    msfd.setWaysOfCooperation(member.getWaysOfCooperation());
//                                                                                    msfd.setBusinessStr(memberMonthPayBusinessStr);
//                                                                                    msfdList.add(msfd);
//                                                                                    memberFee += amount;
//                                                                                    continue a;
//                                                                                }
//                                                                            }
//                                                                        }
//                                                                    }
//
//
//                                                                }
//                                                            }
//
//                                                        }
//                                                    }
//                                                }
//                                            }
//                                        }
//                                    } else {
//                                        a:for (Member member : memberList) {
//                                            if (null != member.getMemberBusinessSet()) {
//                                                List<MemberBusiness> memberBusinessList = new ArrayList<MemberBusiness>(member.getMemberBusinessSet());
//                                                if (memberBusinessList.size() > 1) {
//                                                    Collections.sort(memberBusinessList, new Comparator<MemberBusiness>() {
//                                                        public int compare(MemberBusiness o1, MemberBusiness o2) {
//                                                            return o1.getBusinessId().compareTo(o1.getBusinessId());
//                                                        }
//                                                    });
//                                                }
//                                                Set<Integer> businessIdSet = new HashSet<Integer>();
//                                                if (null != member.getMemberBusinessSet() && null != member.getMemberMonthPayBusinessList()) {
//                                                    for (MemberBusiness business : member.getMemberBusinessSet()) {
//                                                        for (MemberMonthPayBusiness payBusiness : member.getMemberMonthPayBusinessList()) {
//                                                            if (null != business.getCompanySonBillId() &&
//                                                                    Timestamp.parseDate3(payBusiness.getServiceMonth(),"yyyy-MM").
//                                                                            compareTo(Timestamp.parseDate3(serviceMonth,"yyyy-MM")) == 0) {
//                                                                businessIdSet.add(business.getBusinessId());
//                                                            }
//                                                        }
//
//                                                    }
//                                                    if (method.getCooperationMethodId().equals(member.getWaysOfCooperation())) {
//                                                        if (null != method.getBusinessServiceFeeList() && null != member.getMonthPayBusinessDtoList()) {
//                                                            for (MemberMonthPayBusinessDto dto : member.getMonthPayBusinessDtoList()) {
//                                                                if (null != dto.getMemberMonthPayBusinessStr()) {
//                                                                    for (CompanyCooperationBusinessServiceFee fee : method.getBusinessServiceFeeList()) {
//                                                                        if (dto.getServiceMonth().
//                                                                                compareTo(Timestamp.parseDate3(serviceMonth,"yyyy-MM")) == 0) {
//                                                                            Set<Integer> businessIdSet2 = ClassConvert.strToIntegerSetGather(dto.getMemberMonthPayBusinessStr().split(","));
//                                                                            String memberMonthPayBusinessStr = "";
//                                                                            int i = 0;
//                                                                            for (Integer integer : businessIdSet2) {
//                                                                                if (businessIdSet.contains(integer)) {
//                                                                                    if (i == 0) {
//                                                                                        memberMonthPayBusinessStr = String.valueOf(integer);
//                                                                                    } else {
//                                                                                        memberMonthPayBusinessStr += "," + String.valueOf(integer);
//                                                                                    }
//                                                                                    i ++;
//                                                                                }
//                                                                            }
//
//                                                                            if (fee.getBusinessIds().equals(memberMonthPayBusinessStr)) {
//                                                                                MonthServiceFeeDetail msfd = new MonthServiceFeeDetail();
//                                                                                msfd.setMemberId(member.getId());
//                                                                                msfd.setCompanySonBillId(bill.getCompanySonBillId());
//                                                                                msfd.setAmount(fee.getPrice());
//                                                                                msfd.setWaysOfCooperation(member.getWaysOfCooperation());
//                                                                                msfd.setBusinessStr(memberMonthPayBusinessStr);
//                                                                                msfdList.add(msfd);
//                                                                                memberFee += fee.getPrice();
//                                                                                continue a;
//                                                                            }
//                                                                        }
//                                                                    }
//                                                                }
//                                                            }
//                                                        }
//                                                    }
//                                                }
//                                            }
//                                        }
//                                    }
//
//
//                                    if (method.getIsPercent() == 1) {
//                                        memberFee = (1 + method.getPercent() / 100 ) * memberFee;
//                                    }
//
//                                    if (method.getIsPercent() == 1) {
//                                        memberFee = (1 + method.getPercent() / 100 ) * memberFee;
//                                    }
//                                    if (null != method.getServiceFeeMax() && memberFee > method.getServiceFeeMax()) {
//                                        memberFee = method.getServiceFeeMax();
//                                    }
//                                    if (null != method.getServiceFeeMin() && memberFee < method.getServiceFeeMin()) {
//                                        memberFee = method.getServiceFeeMin();
//                                    }
//                                    //服务费
//                                    monthServiceFee.setServiceFee(memberFee);
//                                    //服务月
//                                    monthServiceFee.setMonth(Timestamp.parseDate3(serviceMonth,"yyyy-MM"));
//                                    monthServiceFee.setCompanyCooperationMethodJson(com.alibaba.fastjson.JSONArray.toJSONString(methods));
//                                    monthServiceFee.setMonthServiceFeeDetailList(msfdList);
//                                    addListMSF.add(monthServiceFee);
//                                    fwlNum ++;
//                                }
//                                break;
//                            case 4 :
//                                //服务区
//                            {
//                                //服务费明细
//                                List<MonthServiceFeeDetail> msfdList = new ArrayList<MonthServiceFeeDetail>();
//                                MonthServiceFee monthServiceFee = new MonthServiceFee();
//                                monthServiceFee.setCompanySonTotalBillId(bill.getId());
//                                monthServiceFee.setServiceFeeConfigId(method.getServiceFeeConfigId());
//                                monthServiceFee.setCompanySonBillId(bill.getCompanySonBillId());
//                                monthServiceFee.setCompanyId(company.getId());
//                                //服务费
//                                Double memberFee = 0.0;
//                                //公司交金地集合
//                                List<CompanyCooperationServicePayPlace> payPlaceList = new ArrayList<CompanyCooperationServicePayPlace>(method.getPayPlaceList());
//
//                                if (msfList.size() > 0) {
//                                    for (MonthServiceFee fee : msfList) {
//                                        if (fee.getServiceFeeConfigId().equals(method.getServiceFeeConfigId()) &&
//                                                fee.getCompanySonBillId().equals(bill.getCompanySonBillId()) &&
//                                                null != fee.getMonthServiceFeeDetailList() &&
//                                                fee.getMonthServiceFeeDetailList().size() > 0) {
//                                            //判断是否已存在缴纳
//                                            for (MonthServiceFeeDetail detail : fee.getMonthServiceFeeDetailList()) {
//                                                for (Member member : memberList) {
//                                                    Double fee1 = 0.0;
//                                                    List<Map<Integer,Integer>> mapList = new ArrayList<Map<Integer, Integer>>();
//                                                    if (null != member.getMemberBusinessSet() && null != member.getMemberMonthPayBusinessList()) {
//                                                        Set<Integer> businessIdSet = new HashSet<Integer>();
//                                                        for (MemberBusiness business : member.getMemberBusinessSet()) {
//                                                            for (MemberMonthPayBusiness payBusiness : member.getMemberMonthPayBusinessList()) {
//                                                                if (null != business.getCompanySonBillId() &&
//                                                                        Timestamp.parseDate3(payBusiness.getServiceMonth(),"yyyy-MM").
//                                                                                compareTo(Timestamp.parseDate3(serviceMonth,"yyyy-MM")) == 0) {
//                                                                    businessIdSet.add(business.getBusinessId());
//                                                                }
//                                                            }
//
//                                                        }
//                                                        for (MemberBusiness memberBusiness : member.getMemberBusinessSet()) {
//                                                            if (null != memberBusiness.getCompanySonBillId() && member.getId().equals(detail.getMemberId()) &&
//                                                                    member.getWaysOfCooperation().equals(method.getCooperationMethodId())) {
//                                                                if (null != member.getMemberMonthPayBusinessList()) {
//                                                                    if (null != detail.getBusinessCityJsonList()) {
//                                                                        a:for (MemberMonthPayBusiness business : member.getMemberMonthPayBusinessList()) {
//                                                                            if (businessIdSet.contains(memberBusiness.getBusinessId())) {
//                                                                                for (Map<Integer, Integer> integerMap : detail.getBusinessCityJsonList()) {
//                                                                                    if (null == integerMap.get(business.getBusinessId()) &&
//                                                                                            !integerMap.containsValue(business.getCityId())) {
//                                                                                        for (int i = 0; i < payPlaceList.size(); i++) {
//                                                                                            if (payPlaceList.get(i).getCityId().equals(business.getCityId())){
//                                                                                                memberIdsSetQU.add(member.getId().toString() + business.getCityId());
//                                                                                                memberFee += payPlaceList.get(i).getPrice();
//                                                                                                fee1 += payPlaceList.get(i).getPrice();
//                                                                                                Map<Integer,Integer> map1 = new HashMap<Integer, Integer>();
//                                                                                                map1.put(business.getBusinessId(),business.getCityId());
//                                                                                                mapList.add(map1);
//                                                                                                continue a;
//                                                                                            }
//                                                                                        }
//                                                                                    }
//                                                                                }
//                                                                            } else {
//                                                                                for (int i = 0; i < payPlaceList.size(); i++) {
//                                                                                    if (payPlaceList.get(i).getCityId().equals(business.getCityId())){
//                                                                                        fee1 -= payPlaceList.get(i).getPrice();
//                                                                                        memberFee -= payPlaceList.get(i).getPrice();
//                                                                                        memberIdsSetQU.add(member.getId().toString() + business.getCityId());
//                                                                                        continue a;
//                                                                                    }
//                                                                                }
//                                                                            }
//
//                                                                        }
//                                                                    }
//                                                                }
//                                                            }
//                                                        }
//                                                    }
//
//                                                    if (mapList.size() > 0) {
//                                                        MonthServiceFeeDetail msfd = new MonthServiceFeeDetail();
//                                                        msfd.setMemberId(member.getId());
//                                                        msfd.setCompanySonBillId(bill.getCompanySonBillId());
//                                                        msfd.setAmount(fee1);
//                                                        msfd.setWaysOfCooperation(member.getWaysOfCooperation());
//                                                        msfd.setBusinessCityJson(JSONArray.fromObject(mapList).toString());
//                                                        msfdList.add(msfd);
//                                                    }
//                                                }
//
//
//                                            }
//                                        }
//                                    }
//                                } else {
//                                    for (Member member : memberList) {
//                                        Double fee = 0.0;
//                                        List<Map<Integer,Integer>> mapList = new ArrayList<Map<Integer, Integer>>();
//                                        if (null != member.getMemberBusinessSet() && null != member.getMemberMonthPayBusinessList()) {
//                                            Set<Integer> businessIdSet = new HashSet<Integer>();
//                                            for (MemberBusiness business : member.getMemberBusinessSet()) {
//                                                for (MemberMonthPayBusiness payBusiness : member.getMemberMonthPayBusinessList()) {
//                                                    if (null != business.getCompanySonBillId() &&
//                                                            Timestamp.parseDate3(payBusiness.getServiceMonth(),"yyyy-MM").
//                                                                    compareTo(Timestamp.parseDate3(serviceMonth,"yyyy-MM")) == 0) {
//                                                        businessIdSet.add(business.getBusinessId());
//                                                    }
//                                                }
//
//                                            }
//                                            for (MemberBusiness memberBusiness : member.getMemberBusinessSet()) {
//
//                                                if (null != member.getMemberMonthPayBusinessList()) {
//                                                    a: for (MemberMonthPayBusiness business : member.getMemberMonthPayBusinessList()) {
//
//                                                        if (businessIdSet.contains(memberBusiness.getBusinessId())) {
//                                                            if (memberBusiness.getBusinessId().equals(business.getBusinessId())) {
//                                                                if (null != business.getCompanySonBillId() &&
//                                                                        member.getWaysOfCooperation().equals(method.getCooperationMethodId()) &&
//                                                                        business.getCompanySonBillId().equals(bill.getCompanySonBillId()) ) {
//                                                                    if (!memberIdsSetQU.contains(member.getId().toString() + business.getCityId())) {
//                                                                        for (int i = 0; i < payPlaceList.size(); i++) {
//                                                                            if (payPlaceList.get(i).getCityId().equals(business.getCityId())){
//                                                                                fee += payPlaceList.get(i).getPrice();
//                                                                                memberFee += payPlaceList.get(i).getPrice();
//                                                                                Map<Integer,Integer> map1 = new HashMap<Integer, Integer>();
//                                                                                map1.put(business.getBusinessId(),business.getCityId());
//                                                                                mapList.add(map1);
//                                                                                memberIdsSetQU.add(member.getId().toString() + business.getCityId());
//                                                                                continue a;
//                                                                            }
//                                                                        }
//                                                                    } else {
//                                                                        Map<Integer,Integer> map1 = new HashMap<Integer, Integer>();
//                                                                        map1.put(business.getBusinessId(),business.getCityId());
//                                                                        mapList.add(map1);
//                                                                    }
//
//                                                                }
//                                                            }
//                                                        } else {
//                                                            for (int i = 0; i < payPlaceList.size(); i++) {
//                                                                if (payPlaceList.get(i).getCityId().equals(business.getCityId())){
//                                                                    fee -= payPlaceList.get(i).getPrice();
//                                                                    memberFee -= payPlaceList.get(i).getPrice();
//                                                                    memberIdsSetQU.add(member.getId().toString() + business.getCityId());
//                                                                    continue a;
//                                                                }
//                                                            }
//                                                        }
//
//
//                                                    }
//                                                }
//                                            }
//                                        }
//
//                                        if (mapList.size() > 0) {
//                                            MonthServiceFeeDetail msfd = new MonthServiceFeeDetail();
//                                            msfd.setMemberId(member.getId());
//                                            msfd.setCompanySonBillId(bill.getCompanySonBillId());
//                                            msfd.setAmount(fee);
//                                            msfd.setWaysOfCooperation(member.getWaysOfCooperation());
//                                            msfd.setBusinessCityJson(com.alibaba.fastjson.JSONArray.toJSONString(mapList));
//                                            msfdList.add(msfd);
//                                        }
//
//                                    }
//                                }
//                                if (method.getIsPercent() == 1) {
//                                    memberFee = (1 + method.getPercent() / 100 ) * memberFee;
//                                }
//                                if (method.getIsPercent() == 1) {
//                                    memberFee = (1 + method.getPercent() / 100 ) * memberFee;
//                                }
//                                if (null != method.getServiceFeeMax() && memberFee > method.getServiceFeeMax()) {
//                                    memberFee = method.getServiceFeeMax();
//                                }
//                                if (null != method.getServiceFeeMin() && memberFee < method.getServiceFeeMin()) {
//                                    memberFee = method.getServiceFeeMin();
//                                }
//                                //服务费
//                                monthServiceFee.setServiceFee(memberFee);
//                                //服务月
//                                monthServiceFee.setMonth(Timestamp.parseDate3(serviceMonth,"yyyy-MM"));
//                                monthServiceFee.setCompanyCooperationMethodJson(com.alibaba.fastjson.JSONArray.toJSONString(methods));
//                                monthServiceFee.setMonthServiceFeeDetailList(msfdList);
//                                addListMSF.add(monthServiceFee);
//                            }
//                            break;
//                            case 5 :
//                                //异动量
//                                if (ydNum == 0){
//                                    List<MonthServiceFeeDetail> msfdList = new ArrayList<MonthServiceFeeDetail>();
//                                    MonthServiceFee monthServiceFee = new MonthServiceFee();
//                                    monthServiceFee.setCompanySonTotalBillId(bill.getId());
//                                    monthServiceFee.setServiceFeeConfigId(method.getServiceFeeConfigId());
//                                    monthServiceFee.setCompanySonBillId(bill.getCompanySonBillId());
//                                    monthServiceFee.setCompanyId(company.getId());
//                                    //临时服务费
//                                    Double memberFee = 0.0;
//                                    for (Member member : memberList) {
//                                        if (method.getCooperationMethodId().equals(member.getWaysOfCooperation())) {
//                                            memberFee += member.getRecordItems().size() *
//                                                    method.getServiceFeeList().get(0).getPrice();
//                                            MonthServiceFeeDetail msfd = new MonthServiceFeeDetail();
//                                            msfd.setMemberId(member.getId());
//                                            msfd.setAmount(method.getServiceFeeList().get(0).getPrice());
//                                            msfd.setCompanySonBillId(bill.getCompanySonBillId());
//                                            msfdList.add(msfd);
//                                        }
//                                    }
//                                    if (method.getIsPercent() == 1) {
//                                        memberFee = (1 + method.getPercent() / 100 ) * memberFee;
//                                    }
//                                    if (memberFee != 0) {
//                                        if (method.getIsPercent() == 1) {
//                                            memberFee = (1 + method.getPercent() / 100 ) * memberFee;
//                                        }
//                                        if (null != method.getServiceFeeMax() && memberFee > method.getServiceFeeMax()) {
//                                            memberFee = method.getServiceFeeMax();
//                                        }
//                                        if (null != method.getServiceFeeMin() && memberFee < method.getServiceFeeMin()) {
//                                            memberFee = method.getServiceFeeMin();
//                                        }
//                                        //服务费
//                                        monthServiceFee.setServiceFee(memberFee);
//                                        //服务月
//                                        monthServiceFee.setMonth(Timestamp.parseDate3(serviceMonth,"yyyy-MM"));
//                                        monthServiceFee.setCompanyCooperationMethodJson(com.alibaba.fastjson.JSONArray.toJSONString(methods));
//                                        monthServiceFee.setMonthServiceFeeDetailList(msfdList);
//                                        addListMSF.add(monthServiceFee);
//
//                                    }
//                                    ydNum ++;
//                                }
//                                break;
//                            case 6 :
//                                //整体打包
//                                if (ztjtNum == 0){
//                                    MonthServiceFee monthServiceFee = new MonthServiceFee();
//                                    monthServiceFee.setCompanySonTotalBillId(bill.getId());
//                                    monthServiceFee.setServiceFeeConfigId(method.getServiceFeeConfigId());
//                                    monthServiceFee.setCompanySonBillId(bill.getCompanySonBillId());
//                                    monthServiceFee.setCompanyId(company.getId());
//                                    Double memberFee = 0.0;
//                                    if (msfList.size() < 1) {
//                                        if (null != method.getServiceFeeList() && method.getServiceFeeList().size() > 0) {
//                                            memberFee = method.getServiceFeeList().get(0).getPrice();
//                                            if (method.getIsPercent() == 1) {
//                                                memberFee = (1 + method.getPercent() / 100 ) * memberFee;
//                                            }
//                                        }
//                                        if (memberFee != 0) {
//                                            if (method.getIsPercent() == 1) {
//                                                memberFee = (1 + method.getPercent() / 100 ) * memberFee;
//                                            }
//                                            if (null != method.getServiceFeeMax() && memberFee > method.getServiceFeeMax()) {
//                                                memberFee = method.getServiceFeeMax();
//                                            }
//                                            if (null != method.getServiceFeeMin() && memberFee < method.getServiceFeeMin()) {
//                                                memberFee = method.getServiceFeeMin();
//                                            }
//                                            //服务费
//                                            monthServiceFee.setServiceFee(memberFee);
//                                        }
//                                    } else {
//                                        monthServiceFee.setServiceFee(memberFee);
//                                    }
//                                    //服务月
//                                    monthServiceFee.setMonth(Timestamp.parseDate3(serviceMonth,"yyyy-MM"));
//                                    monthServiceFee.setCompanyCooperationMethodJson(com.alibaba.fastjson.JSONArray.toJSONString(methods));
//                                    addListMSF.add(monthServiceFee);
//                                    ztjtNum ++;
//                                }
//                                break;
//                            case 7 :
//                                //按人数阶梯式整体
//                                if (ztNum == 0){
//                                    //按人数阶梯
//                                    MonthServiceFee monthServiceFee = new MonthServiceFee();
//                                    monthServiceFee.setCompanySonTotalBillId(bill.getId());
//                                    monthServiceFee.setServiceFeeConfigId(method.getServiceFeeConfigId());
//                                    monthServiceFee.setCompanySonBillId(bill.getCompanySonBillId());
//                                    monthServiceFee.setCompanyId(company.getId());
//                                    if (msfList.size() < 1) {
//                                        //临时服务费
//                                        Double memberFee = 0.0;
//
//                                        //记录人数
//                                        Set<Integer> memberCountSet = new HashSet<Integer>();
//
//                                        a:for (Member member : memberList) {
//                                            if (null != member.getMemberBusinessSet() && null != member.getMemberMonthPayBusinessList()) {
//                                                for (MemberBusiness business : member.getMemberBusinessSet()) {
//                                                    for (MemberMonthPayBusiness payBusiness : member.getMemberMonthPayBusinessList()) {
//                                                        if (business.getBusinessId().equals(payBusiness.getBusinessId())) {
//                                                            if (member.getWaysOfCooperation().equals(method.getCooperationMethodId())) {
//                                                                MonthServiceFeeDetail msfd = new MonthServiceFeeDetail();
//                                                                msfd.setMemberId(member.getId());
//                                                                msfd.setCompanySonBillId(bill.getCompanySonBillId());
//                                                                memberCountSet.add(member.getId());
//                                                                continue a;
//                                                            }
//                                                        }
//                                                    }
//
//                                                }
//                                            }
//
//                                        }
//                                        //已缴纳
//                                        Set<Integer> memberPayCountSet = new HashSet<Integer>();
//                                        for (MonthServiceFee fee : msfList) {
//                                            if (null != fee.getMonthServiceFeeDetailList() &&
//                                                    fee.getServiceFeeConfigId().equals(method.getServiceFeeConfigId())) {
//                                                for (MonthServiceFeeDetail detail : fee.getMonthServiceFeeDetailList()) {
//                                                    if (detail.getWaysOfCooperation().equals(method.getCooperationMethodId())) {
//                                                        memberPayCountSet.add(detail.getMemberId());
//                                                    }
//                                                }
//                                            }
//                                        }
//                                        //本次应人数
//                                        Integer memberCount = memberCountSet.size();
//
//                                        //已缴纳人数
//                                        Integer memberPayCount = memberPayCountSet.size();
//
//                                        if (memberCount > 0) {
//                                            if (method.getServiceFeeList().size() > 0) {
//                                                //升序排序
//                                                Collections.sort(method.getServiceFeeList(), new Comparator<CompanyCooperationServiceFee>() {
//                                                    public int compare(CompanyCooperationServiceFee o1, CompanyCooperationServiceFee o2) {
//                                                        return o1.getExtent() - o2.getExtent();
//                                                    }
//                                                });
//                                            }
//                                            {
//                                                //临时记录人数
//                                                Integer temporaryCount = 0;
//                                                memberFee = getServiceFeePersonNumJTZT(method, temporaryCount, memberFee, memberCount,0);
//                                                monthServiceFee.setServiceFee(memberFee);
//                                            }
//                                            //以前是否有缴纳人数
//                                            if (memberPayCount > 0 && !memberCount.equals(memberPayCount)) {
//                                                //临时记录人数
//                                                Integer temporaryCount = 0;
//                                                Double memberFee2 = getServiceFeePersonNumJTZT(method, temporaryCount, memberFee, memberPayCount,1);
//                                                //补缴或补差
//                                                monthServiceFee.setServiceFee(memberFee-memberFee2);
//                                            } else {
//                                                if (method.getIsPercent() == 1) {
//                                                    memberFee = (1 + method.getPercent() / 100 ) * memberFee;
//                                                }
//                                                if (method.getIsPercent() == 1) {
//                                                    memberFee = (1 + method.getPercent() / 100 ) * memberFee;
//                                                }
//                                                if (null != method.getServiceFeeMax() && memberFee > method.getServiceFeeMax()) {
//                                                    memberFee = method.getServiceFeeMax();
//                                                }
//                                                if (null != method.getServiceFeeMin() && memberFee < method.getServiceFeeMin()) {
//                                                    memberFee = method.getServiceFeeMin();
//                                                }
//                                                //服务费
//                                                monthServiceFee.setServiceFee(memberFee);
//                                            }
//
//                                        }
//                                    } else {
//                                        monthServiceFee.setServiceFee(0.0);
//                                    }
//                                    //服务月
//                                    monthServiceFee.setMonth(Timestamp.parseDate3(serviceMonth,"yyyy-MM"));
//                                    monthServiceFee.setCompanyCooperationMethodJson(com.alibaba.fastjson.JSONArray.toJSONString(methods));
//                                    addListMSF.add(monthServiceFee);
//                                    ztNum ++;
//                                }
//                                break;
//                        }
//                    }
//
//                }
//
//            }
//
//
//
//        }
//
//    }

    public List<Member> getMemberList(){
        //根据公司id和时间 获取员工 包含员工
        return memberMapper.getMemberByCompanyIdAndDate(983,new Date());
    }
}
