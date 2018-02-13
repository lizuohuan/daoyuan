package com.magic.daoyuan.business.service;

import com.magic.daoyuan.business.entity.*;
import com.magic.daoyuan.business.enums.Common;
import com.magic.daoyuan.business.exception.InterfaceCommonException;
import com.magic.daoyuan.business.mapper.*;
import com.magic.daoyuan.business.util.CommonUtil;
import com.magic.daoyuan.business.util.DateUtil;
import com.magic.daoyuan.business.util.ExcelReader;
import com.magic.daoyuan.business.util.StatusConstant;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by Eric Xie on 2017/11/18 0018.
 */
@Service
public class MakeBillService {

    @Resource
    private IMakeBillMapper makeBillMapper;
    @Resource
    private IConfirmFundMapper confirmFundMapper;
    @Resource
    private IExpressInfoMapper expressInfoMapper;
    @Resource
    private IBacklogMapper backlogMapper;
    @Resource
    private IExpressPersonInfoMapper expressPersonInfoMapper;


    /**
     * 邮寄票据
     * @param makeBill
     * @param expressInfo
     */
    @Transactional
    public void expressMakeBill(MakeBill makeBill, ExpressInfo expressInfo,ExpressPersonInfo expressPerson) throws Exception{
        MakeBill m = makeBillMapper.queryMakeBillById(makeBill.getId());
        if(!StatusConstant.BILL_STATUS_ED.equals(m.getStatus())){
            throw new InterfaceCommonException(StatusConstant.ORDER_STATUS_ABNORMITY,"状态异常");
        }
        // 通过邮寄人的信息查询ID， 如果没有则添加
        ExpressPersonInfo info = expressPersonInfoMapper.queryByAllField(expressPerson);
        if(null == info){
            expressPerson.setIsDefault(Common.NO.ordinal());
            expressPersonInfoMapper.addExpressPersonInfo(expressPerson);
            expressInfo.setExpressPersonId(expressPerson.getId());
        }
        else{
            expressInfo.setExpressPersonId(info.getId());
        }

        List<ExpressInfo> expressInfos = expressInfoMapper.queryExpressInfoByContent(expressInfo.getOrderNumber(),
                expressInfo.getExpressPersonId(),expressInfo.getExpressCompanyId());

        if(null != expressInfos && expressInfos.size() > 0){
            for (ExpressInfo sql : expressInfos) {
                if(sql.getOrderNumber().equals(expressInfo.getOrderNumber())){
                    expressInfo.setContent(sql.getContent()+"、"+expressInfo.getContent());
                    expressInfo.setId(sql.getId());
                    break;
                }
            }
        }
        if(null != expressInfo.getId()){
            expressInfoMapper.updateExpressInfo(expressInfo);
        }
        else{
            expressInfoMapper.addExpressInfo(expressInfo);
        }
        makeBill.setExpressInfoId(expressInfo.getId());
        makeBillMapper.updateMakeBill(makeBill);
    }

    public Map<String,Object> importBill(String targetUrl) throws Exception{
        Map<String,Object> data = new HashMap<String, Object>();
        int failure = 0; // 失败记录
        int success = 0;
        if (CommonUtil.isEmpty(targetUrl)) {
            data.put("failure",failure);
            data.put("success",success);
            return data;
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
            String fileSuffix = null;
            if(targetUrl.endsWith(".xlsx")){
                fileSuffix = "xlsx";
            }
            else if(targetUrl.endsWith(".xls")){
                fileSuffix = "xls";
            }
            else {
                throw new InterfaceCommonException(StatusConstant.Fail_CODE, "数据读取失败");
            }
            Map<Integer, List<String>> map = ExcelReader.readExcelContent(urlConn.getInputStream(), 2,fileSuffix,null);
            List<MakeBill> makeBills = new ArrayList<MakeBill>(); // 读取的数据
            int index = 3;
            for (Integer integer : map.keySet()) {
                List<String> values = map.get(integer);
                if ("有效".equals(values.get(14))){
                    // 装配数据
                    MakeBill makeBill = new MakeBill();
                    String s = getStringNumber(values.get(2));
                    if(CommonUtil.isEmpty(s)){
                        throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST,"第"+index+"发票号码不存在");
                    }
                    makeBill.setInvoiceNumber(s);
                    if(CommonUtil.isEmpty(values.get(8),values.get(10))){
                        throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST,"第"+index+"税额、金额不存在");
                    }

                    makeBill.setAmountOfBill(getDouble(values.get(8)) + getDouble(values.get(10)));

                    if(CommonUtil.isEmpty(values.get(11))){
                        throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST,"第"+index+"购方名称不存在");
                    }
                    if(CommonUtil.isEmpty(values.get(12))){
                        throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST,"第"+index+"购方税号不存在");
                    }
                    makeBill.setTitle(values.get(11));
                    makeBill.setTaxNumber(getStringNumber(values.get(12)));
                    makeBills.add(makeBill);
                }
                index++;
            }

            if(makeBills.size() > 0){
                // 获取所有待开票的数据
                Map<String,Object> params = new HashMap<String, Object>();
                params.put("status",StatusConstant.BILL_STATUS_UN);
                List<MakeBill> sqlMakeBills = makeBillMapper.queryMakeBillForExport(params);
                List<MakeBill> successMakeBill = new ArrayList<MakeBill>();
                for (MakeBill makeBill : makeBills) {
                    int sum = 0;
                    for (MakeBill sqlMakeBill : sqlMakeBills) {
                        if(makeBill.getTitle().equals(sqlMakeBill.getBillInfo().getTitle())
                                && makeBill.getTaxNumber().equals(sqlMakeBill.getBillInfo().getTaxNumber())
                                && makeBill.getAmountOfBill().equals(sqlMakeBill.getAmountOfBill())){
                            // 如果 发票抬头、税号、金额 相等， 则认为是一条记录
                            makeBill.setId(sqlMakeBill.getId());
                            makeBill.setStatus(StatusConstant.BILL_STATUS_ED);
                            sum++;
                        }
                    }
                    if(sum != 1){
                        failure++;
                    }
                    else{
                        successMakeBill.add(makeBill);
                        success++;
                    }
                }
                if(successMakeBill.size() > 0){
                    makeBillMapper.batchUpdateMakeBill(successMakeBill);
                    Backlog backlog = new Backlog();
                    backlog.setRoleId(9);
                    backlog.setContent("有新的票据需要邮寄");
                    backlog.setUrl("/page/financeBill/list?roleId=9&status=3002");
                    backlogMapper.save(backlog);
                }
            }
            data.put("failure",failure);
            data.put("success",success);
            return data;
        }
    }
    private String getStringNumber(String s) {
        if(CommonUtil.isEmpty(s)){
            return "";
        }
        return s.split("\\.")[0];
    }

    private double getDouble(String s) {
        return Double.parseDouble(s);
    }

//    private Integer getBillType(String s) {
//
//        if(CommonUtil.isEmpty(s)){
//            return null;
//        }
//        if("普通发票".equals())
//
//
//    }


    /**
     * 自动生成 票据信息
     * @param confirmFundId
     * @throws Exception
     */
    @Transactional
    public void buildBill(Integer confirmFundId) throws Exception{

        ConfirmFund confirmFund = confirmFundMapper.queryById(confirmFundId);
        if(null == confirmFund){
            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST,"认款记录不存在");
        }

    }

    public List<MakeBill> getMakeBillForExport(Map<String,Object> map){
        return makeBillMapper.queryMakeBillForExport(map);
    }


    public MakeBill queryBaseInfo(Integer id){
        return makeBillMapper.queryMakeBillById(id);
    }


    public PageList<MakeBill> queryMakeBillByItems(Map<String,Object> map, PageArgs pageArgs){
        List<MakeBill> dataList = new ArrayList<MakeBill>();
        Integer count = makeBillMapper.countMakeBillByItems(map);
        if(count > 0){
            map.put("limit",pageArgs.getPageStart());
            map.put("limitSize",pageArgs.getPageSize());
            dataList = makeBillMapper.queryMakeBillByItems(map);
        }
        return new PageList<MakeBill>(dataList,count);
    }

    @Transactional
    public void updateMakeBill(MakeBill makeBill){
        makeBillMapper.updateMakeBill(makeBill);
        if (!CommonUtil.isEmpty(makeBill.getStatus()) && makeBill.getStatus().equals(3002)) {
            Backlog backlog = new Backlog();
            backlog.setRoleId(9);
            backlog.setContent("有新的票据需要邮寄");
            backlog.setUrl("/page/financeBill/list?roleId=9&status=3002");
            backlogMapper.save(backlog);
        }
    }

    public void addMakeBill(MakeBill makeBill){
        makeBillMapper.addMakeBill(makeBill);
    }


    public void addMakeBill(List<MakeBill> makeBills){
        makeBillMapper.batchAddMakeBill(makeBills);
    }



    public void exportMakeBill(HttpServletResponse response,List<MakeBill> ordinaryBills,List<MakeBill> specialBill){

        HSSFWorkbook ordinaryHssfWorkbook = new HSSFWorkbook(); //创建excel文件1 普票
        HSSFSheet sheet = ordinaryHssfWorkbook.createSheet();// 创建工作簿

        HSSFRow row = sheet.createRow(0); // 第一行标题
        row.createCell(0).setCellValue("单据号");
        row.createCell(1).setCellValue("购买名称");
        row.createCell(2).setCellValue("购方税号");
        row.createCell(3).setCellValue("地址");
        row.createCell(4).setCellValue("银行账号");
        row.createCell(5).setCellValue("货物名称");
        row.createCell(6).setCellValue("数量");
        row.createCell(7).setCellValue("金额");
        row.createCell(8).setCellValue("税率");
        row.createCell(9).setCellValue("版本");
        row.createCell(10).setCellValue("税收编码");
        row.createCell(11).setCellValue("差额");
        row.createCell(12).setCellValue("备注");
        row.createCell(13).setCellValue("收款人");
        row.createCell(14).setCellValue("复核人");
        // 普票装配数据
        if(null != ordinaryBills && ordinaryBills.size() > 0){
            buildExcelData(ordinaryBills, sheet);
        }

        HSSFWorkbook specialHssfWorkbook = new HSSFWorkbook(); //创建excel文件1 专票
        HSSFSheet specialSheet = specialHssfWorkbook.createSheet();// 创建工作簿

        HSSFRow specialRow = specialSheet.createRow(0); // 第一行标题
        specialRow.createCell(0).setCellValue("单据号");
        specialRow.createCell(1).setCellValue("购买名称");
        specialRow.createCell(2).setCellValue("购方税号");
        specialRow.createCell(3).setCellValue("地址");
        specialRow.createCell(4).setCellValue("银行账号");
        specialRow.createCell(5).setCellValue("货物名称");
        specialRow.createCell(6).setCellValue("数量");
        specialRow.createCell(7).setCellValue("金额");
        specialRow.createCell(8).setCellValue("税率");
        specialRow.createCell(9).setCellValue("版本");
        specialRow.createCell(10).setCellValue("税收编码");
        specialRow.createCell(11).setCellValue("差额");
        specialRow.createCell(12).setCellValue("备注");
        specialRow.createCell(13).setCellValue("收款人");
        specialRow.createCell(14).setCellValue("复核人");
        // 专票装配数据
        if(null != specialBill && specialBill.size() > 0){
            buildExcelData(specialBill, specialSheet);
        }

        OutputStream out = null;
        try {
            response.reset();

            out = response.getOutputStream();
            ZipOutputStream zipOutputStream = new ZipOutputStream(out);
            ZipEntry entry = new ZipEntry("普票.xls");
            zipOutputStream.putNextEntry(entry);
            ordinaryHssfWorkbook.write(zipOutputStream);

            ZipEntry specialEntry = new ZipEntry("专票.xls");
            zipOutputStream.putNextEntry(specialEntry);
            specialHssfWorkbook.write(zipOutputStream);

            response.setHeader("Content-disposition", "attachment; filename=billDetail.zip");
            response.setContentType("application/octet-stream");
            zipOutputStream.flush();
            zipOutputStream.close();
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    private void buildExcelData(List<MakeBill> specialBill, HSSFSheet sheet) {
        for (int i = 0; i < specialBill.size(); i++) {
            MakeBill makeBill = specialBill.get(i);
            HSSFRow dataRow = sheet.createRow(i + 1); // 第一行标题
            String address = makeBill.getBillInfo().getAddress();
            if(!CommonUtil.isEmpty(makeBill.getBillInfo().getPhone())){
                address += " "+makeBill.getBillInfo().getPhone();
            }
            Map<String, String> good = getGoodsName(makeBill.getShopNameId());
            dataRow.createCell(0).setCellValue(CommonUtil.buildBillNumber(makeBill.getId()));
            dataRow.createCell(1).setCellValue(makeBill.getBillInfo().getTitle());
            dataRow.createCell(2).setCellValue(makeBill.getBillInfo().getTaxNumber());
            dataRow.createCell(3).setCellValue(address);
            dataRow.createCell(4).setCellValue(makeBill.getBillInfo().getAccountName() + "  " + makeBill.getBillInfo().getBankAccount());
            dataRow.createCell(5).setCellValue(good.get("name"));
            dataRow.createCell(6).setCellValue("1");
            dataRow.createCell(7).setCellValue(makeBill.getAmountOfBill());
            dataRow.createCell(8).setCellValue(good.get("rate"));
            dataRow.createCell(9).setCellValue("13.0");
            dataRow.createCell(10).setCellValue(good.get("number"));
            dataRow.createCell(11).setCellValue(makeBill.getAmount());
            dataRow.createCell(12).setCellValue(makeBill.getRemark());
            dataRow.createCell(13).setCellValue("覃艳琼");
            dataRow.createCell(14).setCellValue("张晓容");
        }
    }

    private Map<String,String> getGoodsName(Integer shopNameId) {
        Map<String,String> data = new HashMap<String, String>();

        if(StatusConstant.SHOPNAMEID_LABOR.equals(shopNameId)){
            data.put("name","劳务外包费");
            data.put("rate","6%");
            data.put("number","304080399");
        }
        else if(StatusConstant.SHOPNAMEID_SERVICE.equals(shopNameId)){
            data.put("name","服务费");
            data.put("rate","6%");
            data.put("number","304080299");
        }
        else if(StatusConstant.SHOPNAMEID_STAFFING.equals(shopNameId)){
            data.put("name","人事代理费");
            data.put("rate","5%");
            data.put("number","304080299");
        }
        return data;
    }


    public int getMakeBillUnDispose () {
        return makeBillMapper.getMakeBillUnDispose();
    }
}
