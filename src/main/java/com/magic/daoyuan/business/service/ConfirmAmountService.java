package com.magic.daoyuan.business.service;

import com.magic.daoyuan.business.entity.*;
import com.magic.daoyuan.business.enums.Common;
import com.magic.daoyuan.business.exception.InterfaceCommonException;
import com.magic.daoyuan.business.mapper.*;
import com.magic.daoyuan.business.util.StatusConstant;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Eric Xie on 2017/10/31 0031.
 */
@Service
public class ConfirmAmountService {

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
    private IMakeBillMapper makeBillMapper;
    @Resource
    private IBankInfoMapper bankInfoMapper;
    @Resource
    private ICompanySonTotalBillMapper companySonTotalBillMapper;
    @Resource
    private IOutlayAmountRecordMapper outlayAmountRecordMapper;
    @Resource
    private IBacklogMapper backlogMapper;
    @Resource
    private ICompanyBillCountMapper companyBillCountMapper;
    @Resource
    private MakeBillHelper makeBillHelper;
    @Resource
    private CompanySonTotalBillService companySonTotalBillService;


    public ConfirmMoneyRecord getConfirMoneyRecord(Integer id){
        return confirmMoneyRecordMapper.queryById(id);
    }

    /**
     * 修改更新认款后的处理方式
     * @param confirmFundId
     * @param handleMethod
     * @param makeBillDate 回款时间
     * @param billAmount 列表回传的账单金额
     */
    @Transactional
    public void updateConfirmFund(Integer confirmFundId,Integer handleMethod,Date makeBillDate,Double billAmount) throws Exception {
        ConfirmFund confirmFund = confirmFundMapper.queryById(confirmFundId);
        if(null == confirmFund){
            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST,"数据不存在");
        }
        ConfirmFund t = new ConfirmFund();
        t.setId(confirmFundId);
        t.setHandleMethod(handleMethod);
        if(null != makeBillDate){
            t.setReturnTime(makeBillDate);
        }
        confirmFundMapper.update(t);
        // 计算核销差额 汇款金额 - 账单金额
        double confirmAmount = confirmFund.getConfirmAmount(); // 汇款金额

        // 如果之前没有总账单ID的匹配，之后才确认的总账单，则查询之后确认的总账单ID
        if(null == confirmFund.getConfirmFundTotalBillList() || confirmFund.getConfirmFundTotalBillList().size() == 0){
            List<Integer> companyIds = new ArrayList<Integer>();
            companyIds.add(confirmFund.getCompanyId());
//            List<BillAmountOfCompany> billOfCompanies = companyMapper.countBillAmountByCompany(companyIds);
            List<BillAmountOfCompany> billOfCompanies = companySonTotalBillService.buildBillAmountOfCompany(companyIds);
            if(null == billOfCompanies || billOfCompanies.size() == 0){
                throw new InterfaceCommonException(StatusConstant.Fail_CODE,"没有获取到有效账单，请刷新账单列表重试");
            }
            double totalBillAmount = 0.0;
            List<ConfirmFundTotalBill> totalBills = new ArrayList<ConfirmFundTotalBill>();
            for (BillAmountOfCompany billOfCompany : billOfCompanies) {
                totalBillAmount += billOfCompany.getBillAmount();
                ConfirmFundTotalBill bi = new ConfirmFundTotalBill();
                bi.setCompanySonTotalBillId(billOfCompany.getCompanySonTotalBillId());
                bi.setAmount(billOfCompany.getBillAmount());
                bi.setConfirmFundId(confirmFundId);
                totalBills.add(bi);
            }
//            if(totalBillAmount != billAmount){
//                throw new InterfaceCommonException(StatusConstant.Fail_CODE,"账单金额有误，请刷新账单列表重试");
//            }
//            confirmFund.setBillAmount(totalBillAmount);
            confirmFund.setBillAmount(billAmount);

            confirmFundTotalBillMapper.batchAddConfirmFundTotalBill(totalBills);
            confirmFund.setConfirmFundTotalBillList(totalBills);

            confirmFundMapper.updateConfirmFundBillAmount(confirmFund.getId(),confirmFund.getBillAmount());
        }


        // 待更新的总账单
        List<CompanySonTotalBill> waitUpdate = new ArrayList<CompanySonTotalBill>();
        Date billMonth_ = null;
        // 待新增的 未核销数据
        List<ConfirmFundTotalBill> waitAddConfirmFundTotalBills = new ArrayList<ConfirmFundTotalBill>();
        for (int i = 0; i < confirmFund.getConfirmFundTotalBillList().size(); i++) {
            CompanySonTotalBill totalBill = new CompanySonTotalBill();

            double v = confirmAmount - confirmFund.getConfirmFundTotalBillList().get(i).getAmount();
            totalBill.setId(confirmFund.getConfirmFundTotalBillList().get(i).getCompanySonTotalBillId());
            totalBill.setBalanceOfCancelAfterVerification(v >= 0 ? 0 : v);
            if(null == billMonth_){
                // 设置账单月
                CompanySonTotalBill info = companySonTotalBillMapper.info(totalBill.getId());
                billMonth_ = info.getBillMonth();
            }
            if(StatusConstant.HANDLEMETHOD_NEXT_MONTH_BILL.equals(handleMethod)){
                // 纳入次月账单
                totalBill.setIsBalanceOfCancelAfterVerification(Common.YES.ordinal());
                totalBill.setBalanceOfCancelAfterVerification(0.0);
                totalBill.setAfterVerificationTime(new Date());

            }else if(StatusConstant.HANDLEMETHOD_BACK_CUSTOMER.equals(handleMethod)){
                // 如果退给客户的处理方式
                totalBill.setIsBalanceOfCancelAfterVerification(Common.YES.ordinal());
                totalBill.setBalanceOfCancelAfterVerification(0.0);
                totalBill.setAfterVerificationTime(new Date());
                // 生成出款单
            }
            else if(StatusConstant.HANDLEMETHOD_RECOVER_AMOUNT.equals(handleMethod)){
                // 追回尾款的处理方式
                totalBill.setIsBalanceOfCancelAfterVerification(Common.NO.ordinal());
                totalBill.setBalanceOfCancelAfterVerification(v >= 0 ? 0 : v);
                if (v < 0) {
                    // 追回尾款的时候，如果金额小于0 则 创建一条追回尾款未核销的数据，由下次当如的时候，不影响流程 继续计算
                    ConfirmFundTotalBill confirmFundTotalBill = new ConfirmFundTotalBill();
                    confirmFundTotalBill.setAmount(Math.abs(v));
                    confirmFundTotalBill.setCompanySonTotalBillId(totalBill.getId());
                    confirmFundTotalBill.setStatus(Common.NO.ordinal());
                    confirmFundTotalBill.setCompanyId(confirmFund.getCompanyId());
                    waitAddConfirmFundTotalBills.add(confirmFundTotalBill);
                }
            }
            if(i == confirmFund.getConfirmFundTotalBillList().size() - 1 && v != 0
                    && 0 == handleMethod){
                // 如果是最后一个账单核销，计算其 余额
                totalBill.setMonthBalance(v);
            }
            confirmAmount = Math.abs(v);
            waitUpdate.add(totalBill);
        }

        if(StatusConstant.HANDLEMETHOD_BACK_CUSTOMER.equals(handleMethod) || StatusConstant.HANDLEMETHOD_NEXT_MONTH_BILL.equals(handleMethod)){
            // 通过 公司 查询所有待核销总账单 并更新它们
            List<CompanySonTotalBill> bills = companySonTotalBillMapper.queryTotalBillByCompany(confirmFund.getCompanyId());
            if(null != bills && bills.size() > 0){
                for (CompanySonTotalBill bill : bills) {
                    CompanySonTotalBill temp = new CompanySonTotalBill();
                    temp.setId(bill.getId());
                    temp.setIsBalanceOfCancelAfterVerification(Common.YES.ordinal());
                    temp.setBalanceOfCancelAfterVerification(0.0);
                    waitUpdate.add(temp);
                }
            }
        }


        if(StatusConstant.HANDLEMETHOD_BACK_CUSTOMER.equals(handleMethod) || StatusConstant.HANDLEMETHOD_NEXT_MONTH_BILL.equals(handleMethod)){
            // 核销确认数据
            CompanyBillCount companyBillCount = new CompanyBillCount();
            companyBillCount.setCompanyId(confirmFund.getCompanyId());
            companyBillCount.setBillMonth(billMonth_);
            companyBillCount.setType(1); // 核销率
            companyBillCountMapper.add(companyBillCount);
        }

        if(StatusConstant.HANDLEMETHOD_BACK_CUSTOMER.equals(handleMethod) ){
            // 如果退回给客户
            // 获取该公司在当条记录下匹配的银行信息
            BankInfo bankInfo = bankInfoMapper.queryBankInfoByConfirmRecord(confirmFund.getCompanyId(), confirmFund.getConfirmMoneyRecordId());
            if(null == bankInfo){
                throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST,"公司银行信息不存在");
            }
            OutlayAmountRecord record = new OutlayAmountRecord();
            record.setBankInfoId(bankInfo.getId());
            record.setAccountName(bankInfo.getAccountName());
            record.setBankAccount(bankInfo.getBankAccount());
            record.setBankName(bankInfo.getBankName());
            record.setAmount(confirmFund.getConfirmAmount() - confirmFund.getBillAmount());
            record.setIsUrgent(Common.NO.ordinal());
            record.setStatus(StatusConstant.FINANCIAL_AUDITING);
            record.setConfirmFundId(confirmFund.getId());
            record.setType(1); // 非供应商
            outlayAmountRecordMapper.addOutlayAmountRecord(record);

            Backlog backlog = new Backlog();
            backlog.setRoleId(12);
            backlog.setContent("有新的待审核的出款申请");
            backlog.setUrl("/page/outlayAmountRecord/list?roleId=12&status=2001");
            backlogMapper.save(backlog);
        }

        // 更新总账单
        if(waitUpdate.size() > 0){
            companySonTotalBillMapper.batchUpdate(waitUpdate);
        }
        if(waitAddConfirmFundTotalBills.size() > 0){
            confirmFundTotalBillMapper.batchAddConfirmFundTotalBill(waitAddConfirmFundTotalBills);
        }
        // 如果是纳入次月账单的时候，生成票据信息 并且 先款后票，产生退款单
        if((StatusConstant.HANDLEMETHOD_NEXT_MONTH_BILL.equals(handleMethod) || StatusConstant.HANDLEMETHOD_BACK_CUSTOMER.equals(handleMethod)
             && StatusConstant.FIRST_BILL_NO.equals(confirmFund.getIsFirstBill())) && confirmFund.getBillAmount() > 0){
            List<Integer> companySonTotalBillIds = new ArrayList<Integer>(); // 当前认款后 管理的总账单ID
            if(null != confirmFund.getConfirmFundTotalBillList() && confirmFund.getConfirmFundTotalBillList().size() > 0){
                for (ConfirmFundTotalBill bill : confirmFund.getConfirmFundTotalBillList()) {
                    if(null != bill.getCompanySonTotalBillId()){
                        companySonTotalBillIds.add(bill.getCompanySonTotalBillId());
                    }
                }
            }
            if(companySonTotalBillIds.size() > 0){
                List<MakeBill> makeBills = makeBillHelper.getMakeBill(companySonTotalBillIds, confirmFundId, new Date());
                if(makeBills.size() > 0){
                    List<MakeBill> others = new ArrayList<MakeBill>();
                    // 判断开票金额的数量是否大于配置的10W，如果大于等于10W则拆分成两张
                    Iterator<MakeBill> iterator = makeBills.iterator();
                    while (iterator.hasNext()){
                        MakeBill next = iterator.next();
                        if(next.getAmountOfBill() > StatusConstant.BILL_AMOUNT){
                            List<MakeBill> bills = makeBillHelper.splitMakeBill(next);
                            if(bills.size() > 0){
                                others.addAll(bills);
                            }
                            iterator.remove();
                        }
                    }
                    if(others.size() > 0){
                        makeBills.addAll(others);
                    }
                    makeBillMapper.batchAddMakeBill(makeBills);
                }
            }
        }
    }


    private MakeBill createMakeBill(double amount,MakeBill resource) throws Exception{
        if(amount > StatusConstant.BILL_AMOUNT){
            MakeBill m = (MakeBill) resource.clone();
            m.setAmountOfBill(amount - StatusConstant.BILL_AMOUNT);
            return m;
        }
        return null;
    }



    public PageList<ConfirmFund> getConfirmFund(Map<String,Object> map,PageArgs pageArgs){
        int count = confirmFundMapper.countConfirmFundByItems(map);
        List<ConfirmFund> dataList = new ArrayList<ConfirmFund>();
        if(count > 0){
            map.put("limit",pageArgs.getPageStart());
            map.put("limitSize",pageArgs.getPageSize());
            dataList = confirmFundMapper.queryConfirmFundByItems(map);
        }
        return new PageList<ConfirmFund>(dataList,count);
    }


    /**
     * 手动认款
     * @param dataArr JSONArr 数据 格式:
     *              [
     *                   {"companyId" : 1,
     *                  "amount" : 128.2
     *                  }
     *              ]
     * @param confirmMoneyRecordId 到账记录ID
     * @param currentUserId 当前操作者ID
     */
    @Transactional
    public void confirmAmount(String dataArr,Integer confirmMoneyRecordId,Integer currentUserId)throws Exception{
        JSONArray jsonArray = JSONArray.fromObject(dataArr);
        List<ConfirmFund> confirmFunds = new ArrayList<ConfirmFund>();
        List<Integer> companyIds = new ArrayList<Integer>();
        out : for (Object o : jsonArray) {
            JSONObject jsonObject = JSONObject.fromObject(o);
            ConfirmFund fund = new ConfirmFund();
            fund.setCompanyId(jsonObject.getInt("companyId"));
            for (ConfirmFund confirmFund : confirmFunds) {
                if(confirmFund.getCompanyId().equals(fund.getCompanyId())){
                    confirmFund.setConfirmAmount(confirmFund.getConfirmAmount() + jsonObject.getDouble("amount"));
                    continue out;
                }
            }
            fund.setConfirmAmount(jsonObject.getDouble("amount"));
            fund.setConfirmMethod(1);
            fund.setConfirmMoneyRecordId(confirmMoneyRecordId);
            fund.setCreateUserId(currentUserId);
            confirmFunds.add(fund);
            companyIds.add(fund.getCompanyId());
        }


        if(companyIds.size() > 0){
            // 银行信息处理，没有此银行信息的公司，则创建一个银行信息
            ConfirmMoneyRecord record = confirmMoneyRecordMapper.queryById(confirmMoneyRecordId);
            if(null == record){
                throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST,"记录不存在");
            }
            // 更新认款记录状态

            record.setStatus(1);
            confirmMoneyRecordMapper.updateConfirmMoneyRecord(record);

            // 批量获取公司的银行信息
            List<BankInfo> bankInfos = bankInfoMapper.queryBankInfoByCompany(companyIds);
            List<BankInfo> addBankInfo = new ArrayList<BankInfo>(); // 待新增的银行列表

            for (Integer companyId : companyIds) {

                boolean isExist = false;
                for (BankInfo bankInfo : bankInfos) {
                    if(companyId.equals(bankInfo.getCompanyId()) && record.getBankAccount().equals(bankInfo.getBankAccount())
                            && record.getCompanyName().equals(bankInfo.getAccountName())){
                        isExist = true;
                        break;
                    }
                }
                if(!isExist){
                    // 如果不存在 记录匹配的银行信息，自动新增一条
                    BankInfo bankInfo = new BankInfo();
                    bankInfo.setCompanyId(companyId);
                    bankInfo.setAccountName(record.getCompanyName());
                    bankInfo.setBankAccount(record.getBankAccount());
                    bankInfo.setType(StatusConstant.ALIPAY.equals(record.getResource()) ? 1 : 0);
                    addBankInfo.add(bankInfo);
                }
            }
            if(addBankInfo.size() > 0){
                bankInfoMapper.batchAddBankInfo(addBankInfo);
            }



            // 处理未匹配，新增加的公司 处理记录
            // 已经匹配到的公司记录ID
            List<ConfirmMoneyCompany> companyList = confirmMoneyCompanyMapper.queryConfirmMoneyCompanyByRecordId(confirmMoneyRecordId);
            List<Integer> waitAdd = new ArrayList<Integer>();
            // 查询主动添加的公司
            for (Integer companyId : companyIds) {
                boolean isExist = false;
                if(null != companyList && companyList.size() > 0){
                    for (ConfirmMoneyCompany moneyCompany : companyList) {
                        if(companyId.equals(moneyCompany.getCompanyId())){
                            isExist = true;
                            break;
                        }
                    }
                }
                if(!isExist){
                    waitAdd.add(companyId);
                }
            }

            if(waitAdd.size() > 0){
                List<BankInfo> infos = bankInfoMapper.queryBankInfo(record.getBankAccount(), record.getCompanyName(), waitAdd);
                List<ConfirmMoneyCompany> waitAddConfirmCompany = new ArrayList<ConfirmMoneyCompany>();
                for (BankInfo info : infos) {
                    for (Integer integer : waitAdd) {
                        if(info.getCompanyId().equals(integer)){
                            ConfirmMoneyCompany c = new ConfirmMoneyCompany();
                            c.setBankInfoId(info.getId());
                            c.setCompanyId(integer);
                            c.setConfirmMoneyRecordId(confirmMoneyRecordId);
                            c.setServiceId(info.getServiceId());
                            waitAddConfirmCompany.add(c);
                        }
                    }
                }
                if(waitAddConfirmCompany.size() > 0){
                    confirmMoneyCompanyMapper.batchAddConfirmMoneyCompany(waitAddConfirmCompany);
                }
            }

//            List<BillAmountOfCompany> list = companyMapper.countBillAmountByCompany(companyIds);
            List<BillAmountOfCompany> list = companySonTotalBillService.buildBillAmountOfCompany(companyIds);

            // 查询这些公司之前是否有无认款记录，如果有记录且没有处理方式，则合并账单金额处理
//            List<ConfirmFund> preRecords = confirmFundMapper.queryConfirmFundByCompany(companyIds);

            // 获取公司最新的一条有效追回欠款的记录
            List<ConfirmFund> newConfirmFunds = confirmFundMapper.queryNewConfirmFundByCompany(companyIds);

            List<ConfirmFund> confirmFunds_ = new ArrayList<ConfirmFund>(); // 追回欠款 处理后的数据

            List<Integer> companySonTotalBillIds = new ArrayList<Integer>();
            List<ConfirmFundTotalBill> waitUpdate = new ArrayList<ConfirmFundTotalBill>();

            if(null != newConfirmFunds && newConfirmFunds.size() > 0){
                // 处理公司追回欠款的部分，优先把到款金额扣除
                if (list.size() > 0) {
                    for (ConfirmFund confirmFund : confirmFunds) {
                        double debtAmount = 0.0;

                        for (ConfirmFund newConfirmFund : newConfirmFunds) {
                            if(confirmFund.getCompanyId().equals(newConfirmFund.getCompanyId())){
                                debtAmount += newConfirmFund.getBillAmount() - newConfirmFund.getConfirmAmount();
                                // 准备更新之前的账单，完成之前的账单核销差
                                for (ConfirmFundTotalBill bill : newConfirmFund.getConfirmFundTotalBillList()) {
                                    companySonTotalBillIds.add(bill.getCompanySonTotalBillId());
                                }
                                newConfirmFund.setHandleMethod(StatusConstant.HANDLEMETHOD_RECOVER_AMOUNT_HANDLE);
                                confirmFunds_.add(newConfirmFund);// 该条欠款记录已经被处理过
                            }
                        }
                        // 扣除上次欠款金额之后 在拿出去计算其他账单
                        confirmFund.setConfirmAmount(confirmFund.getConfirmAmount() - debtAmount);
                    }
                }else{
                    //  如果当期没有账单记录，则通过上次追回欠款的记录重新构建记录
                    List<ConfirmFundTotalBill> fundTotalBills = confirmFundTotalBillMapper.queryConfirmFundTotalBillByCompanyId(companyIds);
                    if(null != fundTotalBills && fundTotalBills.size() > 0){
                        for (ConfirmFund confirmFund : confirmFunds) {
                            for (ConfirmFundTotalBill totalBill : fundTotalBills) {
                                if (confirmFund.getCompanyId().equals(totalBill.getCompanyId())) {
                                    BillAmountOfCompany billAmountOfCompany = new BillAmountOfCompany();
                                    billAmountOfCompany.setCompanyId(totalBill.getCompanyId());
                                    billAmountOfCompany.setCompanySonTotalBillId(totalBill.getCompanySonTotalBillId());
                                    billAmountOfCompany.setBillAmount(totalBill.getAmount());
                                    list.add(billAmountOfCompany);
                                    waitUpdate.add(totalBill);
                                }
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
                Integer companyId = null;
                Date billMonth_ = null;
                if(null != waitUpdateBill && waitUpdateBill.size() > 0){
                    for (int i = 0; i < waitUpdateBill.size(); i++) {
                        if(null == companyId){
                            companyId = waitUpdateBill.get(i).getCompanyId();
                        }
                        if(null == billMonth_){
                            billMonth_ = waitUpdateBill.get(i).getBillMonth();
                        }
                        waitUpdateBill.get(i).setBalanceOfCancelAfterVerification(0.0);
                        waitUpdateBill.get(i).setIsBalanceOfCancelAfterVerification(Common.YES.ordinal());
                        waitUpdateBill.get(i).setAfterVerificationTime(new Date());
                    }
                    // 新增核销统计数据
                    CompanyBillCount companyBillCount = new CompanyBillCount();
                    companyBillCount.setCompanyId(companyId);
                    companyBillCount.setBillMonth(billMonth_);
                    companyBillCount.setType(1);
                    CompanyBillCount info = companyBillCountMapper.info(companyId, billMonth_, 1);
                    if(null == info){
                        companyBillCountMapper.add(companyBillCount);
                    }
                    companySonTotalBillMapper.batchUpdate(waitUpdateBill);
                }
            }
            // 如果是不做处理 待更新的总帐单
            List<ConfirmFundTotalBill> updateTotalBillList = new ArrayList<ConfirmFundTotalBill>();
            // 处理每一个公司账单
            for (ConfirmFund confirmFund : confirmFunds) {
                List<ConfirmFundTotalBill> totalBillList = new ArrayList<ConfirmFundTotalBill>();
                double totalAmount = 0.0;
                if (null != list && list.size() > 0) {
                    for (BillAmountOfCompany billAmountOfCompany : list) {
                        if(billAmountOfCompany.getCompanyId().equals(confirmFund.getCompanyId())){
                            ConfirmFundTotalBill bill = new ConfirmFundTotalBill();
                            bill.setCompanySonTotalBillId(billAmountOfCompany.getCompanySonTotalBillId());
                            bill.setAmount(billAmountOfCompany.getBillAmount());
                            totalBillList.add(bill);
                            totalAmount += billAmountOfCompany.getBillAmount();
                        }
                    }
                }

                // 设置公司认款后的账单金额
                confirmFund.setBillAmount(totalAmount);
                if(confirmFund.getConfirmAmount().equals(confirmFund.getBillAmount())){
                    // 如果账单金额和认款金额一致，则处理方式修改为 3 不做处理
                    confirmFund.setHandleMethod(StatusConstant.HANDLEMETHOD_NO_HANDLE);
                    updateTotalBillList.addAll(totalBillList);
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
                }
                confirmFund.setConfirmFundTotalBillList(totalBillList);
            }

            if(updateTotalBillList.size() > 0){
                List<CompanySonTotalBill> totalBillList = new ArrayList<CompanySonTotalBill>();
                for (ConfirmFundTotalBill totalBill : updateTotalBillList) {
                    CompanySonTotalBill temp = new CompanySonTotalBill();
                    temp.setId(totalBill.getCompanySonTotalBillId());
                    temp.setIsBalanceOfCancelAfterVerification(Common.YES.ordinal());
                    temp.setBalanceOfCancelAfterVerification(0.0);
                    totalBillList.add(temp);
                }
                // 如果不做处理，更新此阶段的全部总账单
                // 通过 公司 查询所有待核销总账单 并更新它们
//                List<CompanySonTotalBill> bills = companySonTotalBillMapper.queryTotalBillByCompany(confirmFund.getCompanyId());
//                if(null != bills && bills.size() > 0){
//                    for (CompanySonTotalBill bill : bills) {
//                        CompanySonTotalBill temp = new CompanySonTotalBill();
//                        temp.setId(bill.getId());
//                        temp.setIsBalanceOfCancelAfterVerification(Common.YES.ordinal());
//                        temp.setBalanceOfCancelAfterVerification(0.0);
//                        totalBillList.add(temp);
//                    }
//                }
                companySonTotalBillMapper.batchUpdate(totalBillList);
            }

            if (confirmFunds.size() > 0) {
                confirmFundMapper.batchAddConfirmFund(confirmFunds);
                List<ConfirmFundTotalBill> billList = new ArrayList<ConfirmFundTotalBill>();
                for (ConfirmFund confirmFund : confirmFunds) {
                    // 如果是自动认款 不做处理的 产生记录
                    if(null != confirmFund.getConfirmFundTotalBillList() && confirmFund.getConfirmFundTotalBillList().size() > 0){
                        for (ConfirmFundTotalBill bill : confirmFund.getConfirmFundTotalBillList()) {
                            bill.setConfirmFundId(confirmFund.getId());
                            billList.add(bill);
                        }
                    }
                    for (ConfirmFundTotalBill confirmFundTotalBill : waitUpdate) {
                        confirmFundTotalBill.setStatus(Common.YES.ordinal());
                        confirmFundTotalBill.setConfirmFundId(confirmFund.getId());
                    }
                }
                if(billList.size() > 0){
                    confirmFundTotalBillMapper.batchAddConfirmFundTotalBill(billList);
                }
            }
            if(waitUpdate.size() > 0){
                confirmFundTotalBillMapper.del(waitUpdate);
            }
        }
    }






    /**
     * 获取导入的认款记录信息
     * @param map
     * @param pageArgs
     * @return
     */
    public PageList<ConfirmMoneyRecord> getConfirmMoneyRecord(Map<String,Object> map, PageArgs pageArgs){
        List<ConfirmMoneyRecord> dataList = new ArrayList<ConfirmMoneyRecord>();
        int count = confirmMoneyRecordMapper.countConfirmMoneyRecordByItems(map);
        if(count > 0){
            map.put("limit",pageArgs.getPageStart());
            map.put("limitSize",pageArgs.getPageSize());
            dataList = confirmMoneyRecordMapper.queryConfirmMoneyRecordByItems(map);
        }
        return new PageList<ConfirmMoneyRecord>(dataList,count);
    }



    /**
     * 获取超时 的 没有选择核销处理方式
     * @param date
     * @return
     */
    public List<ConfirmFund> getUnDispose(Date date) {
        return confirmFundMapper.getUnDispose(date);
    }


}
