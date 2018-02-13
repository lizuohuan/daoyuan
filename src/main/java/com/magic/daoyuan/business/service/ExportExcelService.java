package com.magic.daoyuan.business.service;

import com.magic.daoyuan.business.entity.*;
import com.magic.daoyuan.business.enums.BusinessEnum;
import com.magic.daoyuan.business.enums.EducationEnum;
import com.magic.daoyuan.business.enums.TimeField;
import com.magic.daoyuan.business.mapper.IBusinessItemMapper;
import com.magic.daoyuan.business.mapper.IInsuranceMapper;
import com.magic.daoyuan.business.util.CommonUtil;
import com.magic.daoyuan.business.util.DateUtil;
import com.magic.daoyuan.business.util.Timestamp;
import com.magic.daoyuan.business.vo.CommonTransact;
import org.apache.bcel.generic.PUSH;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.*;

/**
 * 导出业务
 * Created by Eric Xie on 2017/10/24 0024.
 */
@Service
public class ExportExcelService {

    @Resource
    private IBusinessItemMapper businessItemMapper;
    @Resource
    private IInsuranceMapper insuranceMapper;

    private static Logger logger = Logger.getLogger(ExportExcelService.class);


    public void downCompany(List<Company> companies,HttpServletResponse response){


        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(); //创建excel文件
        HSSFSheet sheet = hssfWorkbook.createSheet();// 创建工作簿



        OutputStream out = null;
        try {
            String name = DateUtil.dateFormat(new Date(),"yyyyMMddHHmmss")+".xls";
            out = response.getOutputStream();
            response.reset();
            response.setHeader("Content-disposition", "attachment; filename="+name+"");
            response.setContentType("application/msexcel");
            hssfWorkbook.write(out);
            out.close();
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }

    }

    /**
     * 稽核下载社保通用模版
     * @param response
     */
    public void downSocialSecurityCommonTemplate(HttpServletResponse response){


        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(); //创建excel文件
        HSSFSheet sheet = hssfWorkbook.createSheet();// 创建工作簿
        HSSFCreationHelper helper = hssfWorkbook.getCreationHelper();
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();

        HSSFRow row = sheet.createRow(0); // 第一行

        HSSFCell c0 = row.createCell(0);
        c0.setCellValue("社保编码");
        setHSSFComment(helper,patriarch,c0,"必选");

        HSSFCell c1 = row.createCell(1);
        c1.setCellValue("证件号码");
        setHSSFComment(helper,patriarch,c1,"必选");

        HSSFCell c2 = row.createCell(2);
        c2.setCellValue("姓名");
        setHSSFComment(helper,patriarch,c2,"必选");

        HSSFCell c3 = row.createCell(3);
        c3.setCellValue("服务起始月");
        setHSSFComment(helper,patriarch,c3,"必选 格式：201801");

        HSSFCell c3a = row.createCell(4);
        c3a.setCellValue("服务结束月");
        setHSSFComment(helper,patriarch,c3a,"必选 格式：201801");

        HSSFCell c3b = row.createCell(5);
        c3b.setCellValue("账单起始月");
        setHSSFComment(helper,patriarch,c3b,"必选 格式：201801");

        HSSFCell c4 = row.createCell(6);
        c4.setCellValue("缴金地");
        setHSSFComment(helper,patriarch,c4,"格式：成都市");

        HSSFCell c5 = row.createCell(7);
        c5.setCellValue("经办机构");
        setHSSFComment(helper,patriarch,c5,"必选");


        HSSFCell c6 = row.createCell(8);
        c6.setCellValue("办理方");
        setHSSFComment(helper,patriarch,c6,"必选");

        HSSFCell c7 = row.createCell(9);
        c7.setCellValue("档次");
        setHSSFComment(helper,patriarch,c7,"必选");

        HSSFCell c8 = row.createCell(10);
        c8.setCellValue("基数");
        setHSSFComment(helper,patriarch,c8,"必选");

        // 新增动态列
        List<Insurance> insurances = insuranceMapper.queryAllInsurance();
        if(null != insurances && insurances.size() > 0){
            int cell = 11;
            for (Insurance insurance : insurances) {
                row.createCell(cell).setCellValue("公司"+insurance.getInsuranceName());
                row.createCell(cell + 1).setCellValue("个人"+insurance.getInsuranceName());
                cell += 2;
            }
        }
        OutputStream out = null;
        try {
            String name = "socialSecurity"+DateUtil.dateFormat(new Date(),"yyyyMMddHHmmss")+".xls";
            out = response.getOutputStream();
            response.reset();
            response.setHeader("Content-disposition", "attachment; filename="+name+"");
            response.setContentType("application/msexcel");
            hssfWorkbook.write(out);
            out.close();
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
    }

 /**
     * 稽核下载公积金通用模版
     * @param response
     */
    public void downReservedFundsCommonTemplate(HttpServletResponse response){


        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(); //创建excel文件
        HSSFSheet sheet = hssfWorkbook.createSheet();// 创建工作簿
        HSSFCreationHelper helper = hssfWorkbook.getCreationHelper();
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();

        HSSFRow row = sheet.createRow(0); // 第一行

        HSSFCell c0 = row.createCell(0);
        c0.setCellValue("个人客户号");


        HSSFCell c1 = row.createCell(1);
        c1.setCellValue("证件号码");
        setHSSFComment(helper,patriarch,c1,"必选");

        HSSFCell c2 = row.createCell(2);
        c2.setCellValue("姓名");
        setHSSFComment(helper,patriarch,c2,"必选");


        HSSFCell c3 = row.createCell(3);
        c3.setCellValue("服务起始月");
        setHSSFComment(helper,patriarch,c3,"必选 格式：201801");

        HSSFCell c3a = row.createCell(4);
        c3a.setCellValue("服务结束月");
        setHSSFComment(helper,patriarch,c3a,"必选 格式：201801");

        HSSFCell c3b = row.createCell(5);
        c3b.setCellValue("账单起始月");
        setHSSFComment(helper,patriarch,c3b,"必选 格式：201801");

        HSSFCell c4 = row.createCell(6);
        c4.setCellValue("缴金地");
        setHSSFComment(helper,patriarch,c4,"必选 格式：成都市");

        HSSFCell c5 = row.createCell(7);
        c5.setCellValue("经办机构");
        setHSSFComment(helper,patriarch,c5,"必选 ");

        HSSFCell c6 = row.createCell(8);
        c6.setCellValue("办理方");
        setHSSFComment(helper,patriarch,c6,"必选 ");


        HSSFCell c7 = row.createCell(9);
        c7.setCellValue("比例");
        setHSSFComment(helper,patriarch,c7,"必选 ");

        HSSFCell c8 = row.createCell(10);
        c8.setCellValue("基数");
        setHSSFComment(helper,patriarch,c8,"必选 ");

        row.createCell(11).setCellValue("公司公积金");
        row.createCell(12).setCellValue("个人公积金");

        OutputStream out = null;
        try {
            String name = DateUtil.dateFormat(new Date(),"yyyyMMddHHmmss")+".xls";
            out = response.getOutputStream();
            response.reset();
            response.setHeader("Content-disposition", "attachment; filename="+name+"");
            response.setContentType("application/msexcel");
            hssfWorkbook.write(out);
            out.close();
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
    }



    /**
     * 成都公积金 数据获取
     */
    public Object downLoadChengduReserved(Integer otherFlag){
        Object obj = null;
        switch (otherFlag){

            case 0 : // 0:个人开户

                break;
            case 1 : // 1:个人启封
                break;
            case 2 : // 2:个人封存
                break;
            case 3 : // 3:基数调整
                break;
            case 4 : // 4:新增职工
                break;
            case 5 : // 5:封存职工账户
                break;
            case 6 : // 6:补交登记
                break;
            case 7 : // 7:调整缴存基数
                break;
        }
        return obj;
    }



    /**
     * 下载员工变模版
     * @param response
     */
    public void downLoadChangeUserTemplate(HttpServletResponse response,List<Member> members){

        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(); //创建excel文件
        HSSFSheet sheet = hssfWorkbook.createSheet();// 创建工作簿
        HSSFCreationHelper helper = hssfWorkbook.getCreationHelper();
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();


        HSSFRow row = sheet.createRow(0); // 第一行标题
        HSSFCell cell1 = row.createCell(0);
        cell1.setCellValue("基础信息");
        setHSSFComment(helper,patriarch,cell1,"可选项");
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,10));

        HSSFCell cell11 = row.createCell(11);
        cell11.setCellValue("社保");
        sheet.addMergedRegion(new CellRangeAddress(0,0,11,22));


        HSSFCell cell12 = row.createCell(23);
        cell12.setCellValue("公积金");
        sheet.addMergedRegion(new CellRangeAddress(0,0,23,33));

        HSSFRow row1 = sheet.createRow(1); // 第2行标题
        HSSFCell c0 = row1.createCell(0);
        c0.setCellValue("公司");
        setHSSFComment(helper,patriarch,c0,"");

        HSSFCell c1 = row1.createCell(1);
        c1.setCellValue("部门");
        setHSSFComment(helper,patriarch,c1,"可选项");

        HSSFCell c2 = row1.createCell(2);
        c2.setCellValue("姓名");
        setHSSFComment(helper,patriarch,c2,"必选");

        HSSFCell c3 = row1.createCell(3);
        c3.setCellValue("证件类型");
        setHSSFComment(helper,patriarch,c3,"必选，身份证、护照、通行证");

        HSSFCell c4 = row1.createCell(4);
        c4.setCellValue("证件编号");
        setHSSFComment(helper,patriarch,c4,"必选");

        HSSFCell c5 = row1.createCell(5);
        c5.setCellValue("手机号");
        setHSSFComment(helper,patriarch,c5,"必选");

        HSSFCell c6 = row1.createCell(6);
        c6.setCellValue("工作地点");
        setHSSFComment(helper,patriarch,c6,"格式：成都市");

        HSSFCell c7 = row1.createCell(7);
        c7.setCellValue("学历");
        setHSSFComment(helper,patriarch,c7,"必选，博士、硕士、本科、大专、高中及以下");

        HSSFCell c8 = row1.createCell(8);
        c8.setCellValue("合作方式");
        setHSSFComment(helper,patriarch,c8,"必选，普通、派遣、外包");

        HSSFCell c9 = row1.createCell(9);
        c9.setCellValue("合同开始日期");
        setHSSFComment(helper,patriarch,c9,"");

        HSSFCell c10 = row1.createCell(10);
        c10.setCellValue("合同结束日期");
        setHSSFComment(helper,patriarch,c10,"");

        // 社保
        HSSFCell c11 = row1.createCell(11);
        c11.setCellValue("服务方式");
        setHSSFComment(helper,patriarch,c11,"必选，代理、托管");

        HSSFCell c12 = row1.createCell(12);
        c12.setCellValue("是否纳入应收");
        setHSSFComment(helper,patriarch,c12,"必选，是、否");

        HSSFCell c13 = row1.createCell(13);
        c13.setCellValue("缴金地");
        setHSSFComment(helper,patriarch,c13,"格式：成都市");

        HSSFCell c14 = row1.createCell(14);
        c14.setCellValue("经办机构");
        setHSSFComment(helper,patriarch,c14,"");

        HSSFCell c15 = row1.createCell(15);
        c15.setCellValue("办理方");
        setHSSFComment(helper,patriarch,c15,"");

        HSSFCell c16 = row1.createCell(16);
        c16.setCellValue("档次");
        setHSSFComment(helper,patriarch,c16,"");

        HSSFCell c17 = row1.createCell(17);
        c17.setCellValue("基数类型");
        setHSSFComment(helper,patriarch,c17,"必选：最低基数|最高基数|基数填写");

        HSSFCell c17a = row1.createCell(18);
        c17a.setCellValue("基数");
        setHSSFComment(helper,patriarch,c17a,"");

        HSSFCell c18 = row1.createCell(19);
        c18.setCellValue("服务起始月");
        setHSSFComment(helper,patriarch,c18,"格式：2017-01");

        HSSFCell c19 = row1.createCell(20);
        c19.setCellValue("服务结束月");
        setHSSFComment(helper,patriarch,c19,"格式：2017-01");

        HSSFCell c20 = row1.createCell(21);
        c20.setCellValue("账单起始月");
        setHSSFComment(helper,patriarch,c20,"格式：2017-01");

        HSSFCell c21 = row1.createCell(22);
        c21.setCellValue("所属子账单");
        setHSSFComment(helper,patriarch,c21,"");

        // 公积金
        HSSFCell c22 = row1.createCell(23);
        c22.setCellValue("服务方式");
        setHSSFComment(helper,patriarch,c22,"必选，代理、托管");

        HSSFCell c23 = row1.createCell(24);
        c23.setCellValue("是否纳入应收");
        setHSSFComment(helper,patriarch,c23,"必选，是、否");

        HSSFCell c24 = row1.createCell(25);
        c24.setCellValue("缴金地");
        setHSSFComment(helper,patriarch,c24,"格式：成都市");

        HSSFCell c25 = row1.createCell(26);
        c25.setCellValue("经办机构");
        setHSSFComment(helper,patriarch,c25,"");

        HSSFCell c26 = row1.createCell(27);
        c26.setCellValue("办理方");
        setHSSFComment(helper,patriarch,c26,"");

        HSSFCell c27 = row1.createCell(28);
        c27.setCellValue("比例");
        setHSSFComment(helper,patriarch,c27,"");

        HSSFCell c28 = row1.createCell(29);
        c28.setCellValue("基数");
        setHSSFComment(helper,patriarch,c28,"");

        HSSFCell c29 = row1.createCell(30);
        c29.setCellValue("服务起始月");
        setHSSFComment(helper,patriarch,c29,"格式：2017-01");

        HSSFCell c30 = row1.createCell(31);
        c30.setCellValue("服务结束月");
        setHSSFComment(helper,patriarch,c30,"格式：2017-01");

        HSSFCell c31 = row1.createCell(32);
        c31.setCellValue("账单起始月");
        setHSSFComment(helper,patriarch,c31,"格式：2017-01");

        HSSFCell c32 = row1.createCell(33);
        c32.setCellValue("所属子账单");
        setHSSFComment(helper,patriarch,c32,"");


        if(members.size() > 0){

            for (int i = 0; i < members.size(); i++) {

                Member member = members.get(i);

                HSSFRow temp = sheet.createRow(i + 2);
                HSSFCell t0 = temp.createCell(0);
                t0.setCellValue(member.getCompanyName());

                HSSFCell t1 = temp.createCell(1);
                t1.setCellValue(member.getDepartment());

                HSSFCell t2 = temp.createCell(2);
                t2.setCellValue(member.getUserName());

                HSSFCell t3 = temp.createCell(3);
                String s = "";
                if(member.getCertificateType() == 0){
                    s = "身份证";
                }
                else if(member.getCertificateType() == 1){
                    s = "护照";
                }
                else{
                    s = "通行证";
                }
                t3.setCellValue(s);

                HSSFCell t4 = temp.createCell(4);
                t4.setCellValue(member.getCertificateNum());

                HSSFCell t5 = temp.createCell(5);
                t5.setCellValue(member.getPhone());

                HSSFCell t6 = temp.createCell(6);
                t6.setCellValue(member.getCityName());

                HSSFCell t7 = temp.createCell(7);
                if(EducationEnum.Doctor.ordinal() == member.getEducation()){
                    t7.setCellValue("博士");
                }
                else if(EducationEnum.Master.ordinal() == member.getEducation()){
                    t7.setCellValue("硕士");
                }
                else if(EducationEnum.Undergraduate.ordinal() == member.getEducation()){
                    t7.setCellValue("本科");
                }
                else if(EducationEnum.JuniorCollege.ordinal() == member.getEducation()){
                    t7.setCellValue("大专");
                }
                else if(EducationEnum.Senior.ordinal() == member.getEducation()){
                    t7.setCellValue("高中及以下");
                }

                HSSFCell t8 = temp.createCell(8);
                if(0 == member.getWaysOfCooperation()){
                    t8.setCellValue("普通");
                }else if(1 == member.getWaysOfCooperation()){
                    t8.setCellValue("派遣");
                }
                else{
                    t8.setCellValue("外包");
                }


                HSSFCell t9 = temp.createCell(9);
                if(null != member.getContractStartTime()){
                    t9.setCellValue(Timestamp.DateTimeStamp(member.getContractStartTime(),"yyyy-MM-dd"));
                }

                HSSFCell t10 = temp.createCell(10);
                if(null != member.getContractEndTime()){
                    t10.setCellValue(Timestamp.DateTimeStamp(member.getContractEndTime(),"yyyy-MM-dd"));
                }
                // 社保
                if(null != member.getSocialSecurityMemberBusinessItem()){
                    MemberBusinessItem item = member.getSocialSecurityMemberBusinessItem();
                    HSSFCell t11 = temp.createCell(11);
                    if(0 == item.getServeMethod()){
                        t11.setCellValue("代理");
                    }else{
                        t11.setCellValue("托管");
                    }

                    HSSFCell t12 = temp.createCell(12);
                    if(0 == item.getIsReceivable()){
                        t12.setCellValue("否");
                    }
                    else{
                        t12.setCellValue("是");
                    }

                    HSSFCell t13 = temp.createCell(13);
                    t13.setCellValue(item.getPayPlaceName());

                    HSSFCell t14 = temp.createCell(14);
                    t14.setCellValue(item.getOrganizationName());

                    HSSFCell t15 = temp.createCell(15);
                    t15.setCellValue(item.getTransactorName());

                    HSSFCell t16 = temp.createCell(16);
                    t16.setCellValue(item.getInsuranceLevelName());

                    //
                    HSSFCell t17 = temp.createCell(17);
                    HSSFCell t17a = temp.createCell(18);
                    // 0：最低  1：最高  2：填写
                    if(null != item.getBaseType()){
                        if(0 == item.getBaseType()){
                            t17.setCellValue("最低基数");
                        }
                        else if(1 == item.getBaseType()){
                            t17.setCellValue("最高基数");
                        }
                        else if(2 == item.getBaseType()){
                            t17.setCellValue("基数填写");
                        }
                    }
                    t17a.setCellValue(item.getBaseNumber());

                    HSSFCell t18 = temp.createCell(19);
                    if(null != item.getServiceStartTime()){
                        t18.setCellValue(Timestamp.DateTimeStamp(item.getServiceStartTime(),"yyyy-MM"));
                    }

                    HSSFCell t19 = temp.createCell(20);
                    if(null != item.getServiceEndTime()){
                        t19.setCellValue(Timestamp.DateTimeStamp(item.getServiceEndTime(),"yyyy-MM"));
                    }


                    HSSFCell t20 = temp.createCell(21);
                    if(null != item.getBillStartTime()){
                        t20.setCellValue(Timestamp.DateTimeStamp(item.getBillStartTime(),"yyyy-MM"));
                    }

                    HSSFCell t21 = temp.createCell(22);
                    t21.setCellValue(item.getSonBillName());
                }

                // 公积金
                if(null != member.getReservedFundsMemberBusinessItem()){

                    MemberBusinessItem item = member.getReservedFundsMemberBusinessItem();

                    HSSFCell t22 = temp.createCell(23);
                    if(0 == item.getServeMethod()){
                        t22.setCellValue("代理");
                    }else{
                        t22.setCellValue("托管");
                    }

                    HSSFCell t23 = temp.createCell(24);
                    if(0 == item.getIsReceivable()){
                        t23.setCellValue("否");
                    }
                    else{
                        t23.setCellValue("是");
                    }

                    HSSFCell t24 = temp.createCell(25);
                    t24.setCellValue(item.getPayPlaceName());

                    HSSFCell t25 = temp.createCell(26);
                    t25.setCellValue(item.getOrganizationName());

                    HSSFCell t26 = temp.createCell(27);
                    t26.setCellValue(item.getTransactorName());

                    HSSFCell t27 = temp.createCell(28);
                    t27.setCellValue(item.getRatio());

                    HSSFCell t28 = temp.createCell(29);
                    t28.setCellValue(item.getBaseNumber());

                    HSSFCell t29 = temp.createCell(30);
                    if(null != item.getServiceStartTime()){
                        t29.setCellValue(Timestamp.DateTimeStamp(item.getServiceStartTime(),"yyyy-MM"));
                    }

                    HSSFCell t30 = temp.createCell(31);
                    if(null != item.getServiceEndTime()){
                        t30.setCellValue(Timestamp.DateTimeStamp(item.getServiceEndTime(),"yyyy-MM"));
                    }

                    HSSFCell t31 = temp.createCell(32);
                    if(null != item.getBillStartTime()){
                        t31.setCellValue(Timestamp.DateTimeStamp(item.getBillStartTime(),"yyyy-MM"));
                    }

                    HSSFCell t32 = temp.createCell(33);
                    t32.setCellValue(item.getSonBillName());
                }

            }
        }



        OutputStream out = null;
        try {
            String name = "change"+DateUtil.dateFormat(new Date(),"yyyyMMddHHmmss")+".xls";
            out = response.getOutputStream();
            response.reset();
            response.setHeader("Content-disposition", "attachment; filename="+name+"");
            response.setContentType("application/msexcel");
            hssfWorkbook.write(out);
            out.close();
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
    }


    /**
     * 下载员工减员模版
     * @param response
     */
    public void downLoadSubtractUserTemplate(HttpServletResponse response){

        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(); //创建excel文件
        HSSFSheet sheet = hssfWorkbook.createSheet();// 创建工作簿
        HSSFCreationHelper helper = hssfWorkbook.getCreationHelper();
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();

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

        HSSFRow row = sheet.createRow(0); // 第一行标题
        HSSFCell cell1 = row.createCell(0);
        cell1.setCellValue("姓名");
        setHSSFComment(helper,patriarch,cell1,"可选项");

        HSSFCell cell2 = row.createCell(1);
        cell2.setCellValue("证件号");
        setHSSFComment(helper,patriarch,cell2,"必选");

        HSSFCell cell3 = row.createCell(2);
        cell3.setCellValue("社保");
        setHSSFComment(helper,patriarch,cell3,"值选项：“否” 或者 不填写 表示该项减员");

        HSSFCell cell4 = row.createCell(3);
        cell4.setCellValue("公积金");
        setHSSFComment(helper,patriarch,cell4,"值选项：“否” 或者 不填写 表示该项减员");
        // 合并单元格
        for (int i = 0; i < 4; i++) {
            sheet.addMergedRegion(new CellRangeAddress(0,1,i,i));
        }

        HSSFCell cell5 = row.createCell(4);
        cell5.setCellValue("其他周期性服务");
        setHSSFComment(helper,patriarch,cell5,"值选项：“否” 或者 不填写 表示该项减员");
        // 合并其他周期性服务
        if (null != otherService && otherService.size() > 1) {
            sheet.addMergedRegion(new CellRangeAddress(0,0,4,4 + otherService.size() - 1));
        }

        HSSFCell cell6 = row.createCell(5 + (null == otherService ? 0 : otherService.size()) - 1);
        cell6.setCellValue("一次性服务");
        setHSSFComment(helper,patriarch,cell6,"值选项：“否” 或者 不填写 表示该项减员");
        // 合并一次性服务
        if (onceService.size() > 1) {
            sheet.addMergedRegion(new CellRangeAddress(0,0,5 + (null == otherService ? 0 : otherService.size()) - 1,
                    5 + (null == otherService ? 0 : otherService.size()) + onceService.size() - 1 - 1));
        }
        HSSFRow row2 = sheet.createRow(1); // 第一行标题
        // 其他周期性服务
        if (null != otherService && otherService.size() > 0) {
            for (int i = 0; i < otherService.size(); i++) {
                HSSFCell temp = row2.createCell(4 + i);
                temp.setCellValue(otherService.get(i).getItemName());
//                setHSSFComment(helper,patriarch,temp,"值选项：“否” 或者 不填写 表示该项减员");
            }
        }
        // 一次性服务
        if (onceService.size() > 0) {
            for (int i = 0; i < onceService.size(); i++) {
                HSSFCell temp = row2.createCell(5 + (null == otherService ? 0 : otherService.size()) - 1 + i);
                temp.setCellValue(onceService.get(i).getItemName());
//                setHSSFComment(helper,patriarch,temp,"值选项：“否” 或者 不填写 表示该项减员");
            }
        }
        OutputStream out = null;
        try {
            String name = "employeeTemplate"+DateUtil.dateFormat(new Date(),"yyyyMMddHHmmss")+".xls";
            out = response.getOutputStream();
            response.reset();
            response.setHeader("Content-disposition", "attachment; filename="+name+"");
            response.setContentType("application/msexcel");
            hssfWorkbook.write(out);
            out.close();
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
    }

    /**
     * 下载 新增员工模版
     */
    public void downLoadAddUserTemplate(HttpServletResponse response){

        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(); //创建excel文件
        HSSFSheet sheet = hssfWorkbook.createSheet();// 创建工作簿

        HSSFCellStyle cellStyle = hssfWorkbook.createCellStyle();
        HSSFDataFormat dataFormat = hssfWorkbook.createDataFormat();
        cellStyle.setDataFormat(dataFormat.getFormat("text"));

//        sheet.get

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

        // 设置表头
        HSSFRow row = sheet.createRow(0);
        row.setRowStyle(cellStyle);

        HSSFCreationHelper helper = hssfWorkbook.getCreationHelper();
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();

        row.createCell(0).setCellValue("所属公司");
        row.createCell(1).setCellValue("所属部门");
        row.createCell(2).setCellValue("姓名");
        HSSFCell cardTypeCell = row.createCell(3);
        cardTypeCell.setCellValue("证件类型");
        cardTypeCell.setCellStyle(cellStyle);
        setHSSFComment(helper,patriarch,cardTypeCell,"值选项：身份证、护照、港澳台通行证。 其他值无效");


        row.createCell(4).setCellValue("证件编号");
        row.createCell(5).setCellValue("手机号");
        HSSFCell workPlaceCell = row.createCell(6);
        workPlaceCell.setCellValue("工作地点");
        setHSSFComment(helper,patriarch,workPlaceCell,"格式：xx市(成都市) 其他格式无效");

        HSSFCell educationCell = row.createCell(7);
        educationCell.setCellValue("学历");
        setHSSFComment(helper,patriarch,educationCell,"值选项：博士、硕士、本科、大专、高中及以下。 其他值无效");

//        HSSFCell cooperationStatusCell = row.createCell(7);
//        cooperationStatusCell.setCellValue("合作状态");
//        setHSSFComment(helper,patriarch,cooperationStatusCell,"值选项：离职、在职。 其他值无效");

        HSSFCell cooperationMethodCell = row.createCell(8);
        cooperationMethodCell.setCellValue("合作方式");
        setHSSFComment(helper,patriarch,cooperationMethodCell,"值选项：普通、派遣、外包。 其他值无效");

        HSSFCell contractStartTime = row.createCell(9);
        contractStartTime.setCellValue("合同开始日期");
        setHSSFComment(helper,patriarch,contractStartTime,"格式：2017-01-01。 其他值无效");
        HSSFCell contractEndTime = row.createCell(10);
        contractEndTime.setCellValue("合同结束日期");
        setHSSFComment(helper,patriarch,contractEndTime,"格式：2017-01-01。 其他值无效");

        // 设置社保 需要合并单元格
        HSSFCell socialSecurityCell = row.createCell(11);
        socialSecurityCell.setCellValue("社保");
        // 合并社保单元格
        sheet.addMergedRegion(new CellRangeAddress(0,0,11,22));
        for (int i = 0; i < 11; i++) {
            sheet.addMergedRegion(new CellRangeAddress(0,1,i,i));
        }

        // 设置公积金 需要合并单元格
        HSSFCell reservedFundsCell = row.createCell(23);
        reservedFundsCell.setCellValue("公积金");
        // 合并社保单元格
        sheet.addMergedRegion(new CellRangeAddress(0,0,23,33));

        // 设置其他周期性服务 需要合并单元格
        HSSFCell otherServiceCell = row.createCell(34);
        otherServiceCell.setCellValue("其他周期性服务");
        // 合并其他周期性服务
        if (null != otherService && otherService.size() > 1) {
            sheet.addMergedRegion(new CellRangeAddress(0,0,34,34 + otherService.size() - 1));
        }

        // 设置一次性服务 需要合并单元格
        HSSFCell onceServiceCell = row.createCell(35 + (null == otherService ? 0 : otherService.size()) - 1);
        onceServiceCell.setCellValue("一次性服务");
        // 合并一次性服务
        if (null != onceService && onceService.size() > 1) {
            sheet.addMergedRegion(new CellRangeAddress(0,0,35 + (null == otherService ? 0 : otherService.size()) - 1,
                    34 + (null == otherService ? 0 : otherService.size()) + onceService.size() - 1 - 1));
        }

        HSSFRow row2 = sheet.createRow(1);// 第二行
        HSSFCell cell9 = row2.createCell(11);
        cell9.setCellValue("社保服务方式");
        setHSSFComment(helper,patriarch,cell9,"值选项：代理、托管。 其他值无效,注：填写之后，表示该员工有此业务");
        HSSFCell cell10 = row2.createCell(12);
        cell10.setCellValue("是否纳入应收");
        setHSSFComment(helper,patriarch,cell10,"值选项：是、否。 其他值无效；注：服务方式为“代理”时，必须填写“是”,注：必填");

        HSSFCell cell11 = row2.createCell(13);
        cell11.setCellValue("缴金地");
        setHSSFComment(helper,patriarch,cell11,"格式：xx市(成都市) 其他格式无效；注：服务方式为“代理”时填写");

        HSSFCell cell12 = row2.createCell(14);
        cell12.setCellValue("经办机构");
        setHSSFComment(helper,patriarch,cell12,"注：");

        HSSFCell cell13 = row2.createCell(15);
        cell13.setCellValue("办理方");
        setHSSFComment(helper,patriarch,cell13,"注：必填");

        HSSFCell cell14 = row2.createCell(16);
        cell14.setCellValue("档次");
        setHSSFComment(helper,patriarch,cell14,"注：必填");


        HSSFCell cell16_ = row2.createCell(17);
        cell16_.setCellValue("基数类型");
        setHSSFComment(helper,patriarch,cell16_,"值选项：‘最高基数’|‘最低基数’|‘基数填写’ 必填");


        HSSFCell cell16 = row2.createCell(18);
        cell16.setCellValue("基数填写");
        setHSSFComment(helper,patriarch,cell16,"格式：2000 正整数 必填");


        HSSFCell cell17 = row2.createCell(19);
        cell17.setCellValue("服务起始月");
        setHSSFComment(helper,patriarch,cell17,"格式：2017-10 其他格式无效,注：必填");

        HSSFCell cell18 = row2.createCell(20);
        cell18.setCellValue("服务结束月");
        setHSSFComment(helper,patriarch,cell18,"格式：2017-10 其他格式无效");

        HSSFCell cell19 = row2.createCell(21);
        cell19.setCellValue("账单起始月");
        setHSSFComment(helper,patriarch,cell19,"格式：2017-10 其他格式无效,注：必填");

        HSSFCell cell20 = row2.createCell(22);
        cell20.setCellValue("所属的子账单");
        setHSSFComment(helper,patriarch,cell20,"注：必填");

        // 公积金第二行
        HSSFCell cell21 = row2.createCell(23);
        cell21.setCellValue("公积金服务方式");
        setHSSFComment(helper,patriarch,cell21,"值选项：代理、托管。 其他值无效,注：正确填写后，认定该员工有此业务");

        HSSFCell cell22 = row2.createCell(24);
        cell22.setCellValue("是否纳入应收");
        setHSSFComment(helper,patriarch,cell22,"值选项：是、否。 其他值无效；注：服务方式为“代理”时，必须填写“是”,注：必填");

        HSSFCell cell23 = row2.createCell(25);
        cell23.setCellValue("缴金地");
        setHSSFComment(helper,patriarch,cell23,"格式：xx市(成都市) 其他格式无效");

        HSSFCell cell24 = row2.createCell(26);
        cell24.setCellValue("经办机构");
        setHSSFComment(helper,patriarch,cell24,"注：");

        HSSFCell cell25 = row2.createCell(27);
        cell25.setCellValue("办理方");
        setHSSFComment(helper,patriarch,cell25,"注：必填");

        HSSFCell cell26 = row2.createCell(28);
        cell26.setCellValue("比例");
        setHSSFComment(helper,patriarch,cell26,"格式：小数");


//        HSSFCell cell28_ = row2.createCell(29);
//        cell28_.setCellValue("基数类型");
//        setHSSFComment(helper,patriarch,cell28_,"值选项：‘最高基数’|‘最低基数’|‘基数填写’ 必填");


        HSSFCell cell28 = row2.createCell(29);
        cell28.setCellValue("基数填写");
        setHSSFComment(helper,patriarch,cell28,"格式：2000 正整数 必填");


        HSSFCell cell29 = row2.createCell(30);
        cell29.setCellValue("服务起始月");
        setHSSFComment(helper,patriarch,cell29,"格式：2017-10 其他格式无效,注：必填");

        HSSFCell cell30 = row2.createCell(31);
        cell30.setCellValue("服务结束月");
        setHSSFComment(helper,patriarch,cell30,"格式：2017-10 其他格式无效");

        HSSFCell cell31 = row2.createCell(32);
        cell31.setCellValue("账单起始月");
        setHSSFComment(helper,patriarch,cell31,"格式：2017-10 其他格式无效,注：必填");

        HSSFCell cell32 = row2.createCell(33);
        cell32.setCellValue("所属的子账单");
        setHSSFComment(helper,patriarch,cell32,"注：必填");

        // 其他周期性服务
        if (null != otherService && otherService.size() > 0) {
            for (int i = 0; i < otherService.size(); i++) {
                HSSFCell temp = row2.createCell(34 + i);
                temp.setCellValue(otherService.get(i).getItemName());
                setHSSFComment(helper,patriarch,temp,"格式：是-子账单名称  其他值无效；注：必填");
            }
//            HSSFCell temp = row2.createCell(31 + otherService.size());
//            temp.setCellValue("所属子账单");
//            setHSSFComment(helper,patriarch,temp,"注：如果有此项服务则必填");
        }

        // 一次性服务
        if (null != onceService && onceService.size() > 0) {
            for (int i = 0; i < onceService.size(); i++) {
                HSSFCell temp = row2.createCell(35 + (null == otherService ? 0 : otherService.size()) - 1 + i);
                temp.setCellValue(onceService.get(i).getItemName());
                setHSSFComment(helper,patriarch,temp,"格式：是-子账单名称  其他值无效；注：必填");
            }
        }


        OutputStream out = null;
        try {
            String name = "addEmployeeTemplate"+DateUtil.dateFormat(new Date(),"yyyyMMddHHmmss")+".xls";
            out = response.getOutputStream();
            response.reset();
            response.setHeader("Content-disposition", "attachment; filename="+name+"");
            response.setContentType("application/msexcel");
            hssfWorkbook.write(out);
            out.close();
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }

    }

     /**
     * 设置单元格的批注
     * @param cell 单元格对象
     * @param text 批注文本
     */
    private void setHSSFComment(HSSFCreationHelper helper,HSSFPatriarch patriarch,HSSFCell cell,String text){
//        HSSFComment cellComment = patriarch.createCellComment(helper.createClientAnchor());
        HSSFComment cellComment = patriarch.createCellComment(new HSSFClientAnchor(0,0,0,0,(short)10,10,(short)5,6));
        cellComment.setString(new HSSFRichTextString(text));
        cellComment.setAuthor("admin");
        cell.setCellComment(cellComment);
    }



    public void exportUserCommon(List<CommonTransact> exportUser,HttpServletResponse resp){


        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(); //创建excel文件
        HSSFSheet sheet = hssfWorkbook.createSheet();
        HSSFCreationHelper helper = hssfWorkbook.getCreationHelper();
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
        HSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("姓名");
        row.createCell(1).setCellValue("证件类型");
        row.createCell(2).setCellValue("证件编号");
        row.createCell(3).setCellValue("手机号");
        row.createCell(4).setCellValue("学历");
        row.createCell(5).setCellValue("服务类型");
        row.createCell(6).setCellValue("服务名称");
        row.createCell(7).setCellValue("变更内容");
        row.createCell(8).setCellValue("社保/公积金编号");
        row.createCell(9).setCellValue("缴金地");
        row.createCell(10).setCellValue("经办机构");
        row.createCell(11).setCellValue("办理方");
        row.createCell(12).setCellValue("档次/比例");
        row.createCell(13).setCellValue("基数");
        row.createCell(14).setCellValue("服务起始月");
        row.createCell(15).setCellValue("备注");
        HSSFCell cell = row.createCell(16);
        cell.setCellValue("办理结果");
        setHSSFComment(helper,patriarch,cell,"必选,值选项：成功|失败");

        row.createCell(17).setCellValue("原因");
        if(null != exportUser && exportUser.size() > 0){
            for (int i = 0; i < exportUser.size(); i++) {
                CommonTransact commonTransact = exportUser.get(i);
                HSSFRow r = sheet.createRow(i + 1);
                r.createCell(0).setCellValue(commonTransact.getMemberName());
                r.createCell(1).setCellValue(commonTransact.getIdCardType());
                r.createCell(2).setCellValue(commonTransact.getIdCard());
                r.createCell(3).setCellValue(commonTransact.getPhone());
                r.createCell(4).setCellValue(commonTransact.getEducationName());
                r.createCell(5).setCellValue(commonTransact.getServiceType());
                r.createCell(6).setCellValue(commonTransact.getServiceName());
                r.createCell(7).setCellValue(commonTransact.getContentOfChange());
                r.createCell(8).setCellValue(commonTransact.getSerialNumber());
                r.createCell(9).setCellValue(commonTransact.getPayPlaceName());
                r.createCell(10).setCellValue(commonTransact.getOrgnaizationName());
                r.createCell(11).setCellValue(commonTransact.getTransactName());
                r.createCell(12).setCellValue(commonTransact.getLevelName());
                r.createCell(13).setCellValue(commonTransact.getBaseNumber());
                if (null != commonTransact.getServiceStartMonth()) {
                    r.createCell(14).setCellValue(Timestamp.DateTimeStamp(commonTransact.getServiceStartMonth(),"yyyy-MM"));
                }
                r.createCell(15).setCellValue(commonTransact.getRemark());
//                r.createCell(16).setCellValue("");
//                r.createCell(17).setCellValue("原因");
            }
        }
        OutputStream out = null;
        try {
            out = resp.getOutputStream();
            resp.reset();
            resp.setHeader("Content-disposition", "attachment; filename=detail.xls");
            resp.setContentType("application/msexcel");
            hssfWorkbook.write(out);
            out.close();
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }

    }


    /**
     * 导出社保人员变动表
     * @param flag 1:减少  0 增加
     * @param exportUsers
     */
    public void exportUser(List<ExportUser> exportUsers,Integer flag,HttpServletResponse resp){

        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(); //创建excel文件
        HSSFSheet sheet = hssfWorkbook.createSheet();
        if(flag == 1){
            // 减员列表
            HSSFRow row = sheet.createRow(0);
            row.createCell(0).setCellValue("社保编码");
            row.createCell(1).setCellValue("姓名");
            row.createCell(2).setCellValue("停保时间");
            row.createCell(3).setCellValue("停保原因");
            if(null != exportUsers && exportUsers.size() > 0){
                for (int i = 0; i < exportUsers.size(); i++) {
                    HSSFRow tempRow = sheet.createRow(i + 1);
                    tempRow.createCell(0).setCellValue(CommonUtil.isEmpty(exportUsers.get(i).getCoding()) ? "--" : exportUsers.get(i).getCoding());
                    tempRow.createCell(1).setCellValue(exportUsers.get(i).getUserName());
                    tempRow.createCell(2).setCellValue(CommonUtil.isEmpty(exportUsers.get(i).getCreateTime()) ? "--" :
                            DateUtil.dateFormat(exportUsers.get(i).getCreateTime(),"yyyy-MM-dd"));
                    tempRow.createCell(3).setCellValue(exportUsers.get(i).getStateCooperation() == 1 ? "6301 在职人员解除/终止劳动合同" : "6351 单位调动");
                }
            }
        }
        else if(flag == 0){
            // 增员列表
            HSSFRow row = sheet.createRow(0);
            row.createCell(0).setCellValue("姓名");
            row.createCell(1).setCellValue("身份证号码");
            row.createCell(2).setCellValue("人员类别");
            row.createCell(3).setCellValue("文化程度");
            row.createCell(4).setCellValue("参保时间");
            row.createCell(5).setCellValue("申报工资");
            row.createCell(6).setCellValue("电话号码");

            HSSFCellStyle cellStyleText = hssfWorkbook.createCellStyle();
            HSSFDataFormat format = hssfWorkbook.createDataFormat();
            cellStyleText.setDataFormat(format.getFormat("TEXT"));


            if(null != exportUsers && exportUsers.size() > 0){
                for (int i = 0; i < exportUsers.size(); i++) {

                    HSSFRow tempRow = sheet.createRow( i + 1);
                    tempRow.createCell(0).setCellValue(exportUsers.get(i).getUserName());
                    tempRow.createCell(1).setCellValue(exportUsers.get(i).getCertificateNum());
                    String categoryName = "类别不明";
                    if(null != exportUsers.get(i).getLevelName()){
                        if("一档".equals(exportUsers.get(i).getLevelName())){
                            categoryName = "01 城镇职工";
                        }else if ("二档".equals(exportUsers.get(i).getLevelName())){
                            categoryName = "02 原综保本市户籍劳动者";
                        }else if ("三档".equals(exportUsers.get(i).getLevelName())){
                            categoryName = "03 非本市户籍农民工";
                        }
                    }
                    tempRow.createCell(2).setCellValue(categoryName);
                    String education = "";
                    if(EducationEnum.Doctor.ordinal() == exportUsers.get(i).getEducation()){
                        education = "11 博士研究生";
                    }
                    else if(EducationEnum.Master.ordinal() == exportUsers.get(i).getEducation()){
                        education = "14 硕士研究生";
                    }
                    else if(EducationEnum.Undergraduate.ordinal() == exportUsers.get(i).getEducation()){
                        education = "21 大学本科";
                    }
                    else if(EducationEnum.JuniorCollege.ordinal() == exportUsers.get(i).getEducation()){
                        education = "31 大学专科";
                    }
                    else if(EducationEnum.Senior.ordinal() == exportUsers.get(i).getEducation()){
                        education = "61 普通中学";
                    }
                    tempRow.createCell(3).setCellValue(education);
                    String dateStr = "--";
                    if(!CommonUtil.isEmpty(exportUsers.get(i).getCreateTime())){
                        Date date = DateUtil.setDate(exportUsers.get(i).getCreateTime(), TimeField.Day.ordinal(), 1);
                        dateStr = DateUtil.dateFormat(date,"yyyy-MM-dd");
                    }
                    tempRow.createCell(4).setCellValue(dateStr);
                    tempRow.createCell(5).setCellValue(CommonUtil.isEmpty(exportUsers.get(i).getSalary()) ? "0" : exportUsers.get(i).getSalary().toString());
                    HSSFCell cell = tempRow.createCell(6);
                    cell.setCellStyle(cellStyleText);
                    cell.setCellValue(CommonUtil.isEmpty(exportUsers.get(i).getPhone()) ?  "--" : exportUsers.get(i).getPhone());
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                }
            }
        }
        OutputStream out = null;
        try {
            out = resp.getOutputStream();
            resp.reset();
            resp.setHeader("Content-disposition", "attachment; filename=detail.xls");
            resp.setContentType("application/msexcel");
            hssfWorkbook.write(out);
            out.close();
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }

    }



    public void exportBillDetail(HttpServletResponse resp,Map<String , Object> totalData, List<SocialSecurityInfo> securityInfos,
            List<ReservedFundsInfo> reservedFundsInfos,List<SalaryInfo> salaryInfoList,List<BusinessInsurance> businessInsurances,
                                 List<BusinessYc> businessYcList,Long createTime) throws Exception {

        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(); //创建excel文件
        HSSFSheet totalSheet = hssfWorkbook.createSheet("首页"); // 创建总账单 首页工作簿
        HSSFSheet socialSecuritySheet = hssfWorkbook.createSheet("社保明细"); // 创建社保明细工作簿
        HSSFSheet reservedFundsSheet = hssfWorkbook.createSheet("公积金明细"); // 创建公积金明细工作簿
        HSSFSheet salarySheet = hssfWorkbook.createSheet("工资明细"); // 创建工资明细工作簿
        HSSFSheet specialServiceSheet = hssfWorkbook.createSheet("专项服务明细"); // 创建专项服务明细工作簿

        HSSFCellStyle cellStyle = hssfWorkbook.createCellStyle();
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框

        // 分别构建excel
        // 构建首页工作簿
        buildTotalSheet(totalSheet,totalData,createTime,cellStyle,hssfWorkbook);
        // 构建社保明细工作簿
        buildSocialSecuritySheet(socialSecuritySheet,securityInfos,cellStyle,hssfWorkbook);
        // 构建公积金明细工作簿
        buildReservedFundsSheet(reservedFundsSheet,reservedFundsInfos,cellStyle,hssfWorkbook);
        // 构建工资明细工作簿
        buildSalarySheet(salarySheet,salaryInfoList,cellStyle,hssfWorkbook);
        // 构建专项服务明细工作簿
        buildSpecialServiceSheet(specialServiceSheet,businessInsurances,businessYcList,cellStyle,hssfWorkbook);

        OutputStream out = null;
        try {
            out = resp.getOutputStream();
            resp.reset();
            resp.setHeader("Content-disposition", "attachment; filename=detail.xls");
            resp.setContentType("application/msexcel");
            hssfWorkbook.write(out);
            out.close();
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
    }

    private void buildSpecialServiceSheet(HSSFSheet sheet,List<BusinessInsurance> businessInsurances,
                                          List<BusinessYc> businessYcList,HSSFCellStyle cellStyle,HSSFWorkbook hssfWorkbook) {

        HSSFRow row = sheet.createRow(0);
//        row.createCell(0).setCellValue("序号");
//        row.createCell(1).setCellValue("姓名");
//        row.createCell(2).setCellValue("证件编号");
//        row.createCell(3).setCellValue("服务起始年月");
//        row.createCell(4).setCellValue("本次服务时长");
        setCellStyle(row.createCell(0),cellStyle,"序号");
        setCellStyle(row.createCell(1),cellStyle,"姓名");
        setCellStyle(row.createCell(2),cellStyle,"证件编号");
        setCellStyle(row.createCell(3),cellStyle,"服务起始年月");
        setCellStyle(row.createCell(4),cellStyle,"本次服务时长");
        Set<BusinessInsuranceItem> data = new HashSet<BusinessInsuranceItem>();
        for (BusinessInsurance businessInsurance : businessInsurances) {
            if (null != businessInsurance.getBusinessInsuranceItemList()) {
                data.addAll(businessInsurance.getBusinessInsuranceItemList());
            }
        }
        Iterator<BusinessInsuranceItem> iterator = data.iterator();
        int i = 0;
        while (iterator.hasNext()){
//            row.createCell(5 + i).setCellValue(iterator.next().getBusinessItem().getItemName());
            setCellStyle(row.createCell(5 + i),cellStyle,iterator.next().getBusinessItem().getItemName());
            i++;
        }

        for (int j = 0; j < businessInsurances.size(); j++) {
            HSSFRow temp = sheet.createRow(j + 1);
//            temp.createCell(0).setCellValue(j + 1);
//            temp.createCell(1).setCellValue(businessInsurances.get(j).getUserName());
//            temp.createCell(2).setCellValue(businessInsurances.get(j).getCertificateNum());
//            temp.createCell(3).setCellValue(DateUtil.dateFormat(businessInsurances.get(j).getServiceStartTime(),"yyyy年MM月"));
//            temp.createCell(4).setCellValue(businessInsurances.get(j).getServiceTime());
            setCellStyle(temp.createCell(0),cellStyle,String.valueOf(j + 1));
            setCellStyle(temp.createCell(1),cellStyle,businessInsurances.get(j).getUserName());
            setCellStyle(temp.createCell(2),cellStyle,businessInsurances.get(j).getCertificateNum());
            setCellStyle(temp.createCell(3),cellStyle,DateUtil.dateFormat(businessInsurances.get(j).getServiceStartTime(),"yyyy年MM月"));
            setCellStyle(temp.createCell(4),cellStyle,String.valueOf(businessInsurances.get(j).getServiceTime()));
            for (int k = 0; k < data.size(); k++) {
                double price = 0.0;
                for (BusinessInsuranceItem insuranceItem : businessInsurances.get(j).getBusinessInsuranceItemList()) {
                    if(insuranceItem.getBusinessItem().getItemName().equals(row.getCell(k + 5).getStringCellValue())){
                        price = insuranceItem.getPrice();
                        break;
                    }
                }
//                temp.createCell(k + 5).setCellValue(price);
                setCellStyle(temp.createCell(k + 5),cellStyle,String.valueOf(price));
            }
        }
        // 构建 一次性业务
        int rows = 1 + businessInsurances.size() + 1 + 3;
        for (int k = 0; k < businessYcList.size(); k++) {

            HSSFRow row1 = sheet.createRow(rows);
            row1.createCell(0).setCellValue(businessYcList.get(k).getBusinessItemName());
            if(null != businessYcList.get(k).getBusinessYcItemList()){
                for (int j = 0; j <= businessYcList.get(k).getBusinessYcItemList().size(); j++) {
//                    rows = rows + 1 + j;
                    if(j == 0){
                        rows = rows + 1 + j;
                        HSSFRow temp = sheet.createRow(rows);
//                        temp.createCell(0).setCellValue("序号");
//                        temp.createCell(1).setCellValue("姓名");
//                        temp.createCell(2).setCellValue("证件编号");
//                        temp.createCell(3).setCellValue("金额");
                        setCellStyle(temp.createCell(0),cellStyle,"序号");
                        setCellStyle(temp.createCell(1),cellStyle,"姓名");
                        setCellStyle(temp.createCell(2),cellStyle,"证件编号");
                        setCellStyle(temp.createCell(3),cellStyle,"金额");
                    }else{
                        HSSFRow temp = sheet.createRow(rows);
//                        temp.createCell(0).setCellValue(j);
//                        temp.createCell(1).setCellValue(businessYcList.get(k).getBusinessYcItemList().get(j-1).getUserName());
//                        temp.createCell(2).setCellValue(businessYcList.get(k).getBusinessYcItemList().get(j-1).getCertificateNum());
//                        temp.createCell(3).setCellValue(businessYcList.get(k).getBusinessYcItemList().get(j-1).getPrice());
                        setCellStyle(temp.createCell(0),cellStyle,String.valueOf(j));
                        setCellStyle(temp.createCell(1),cellStyle,businessYcList.get(k).getBusinessYcItemList().get(j-1).getUserName());
                        setCellStyle(temp.createCell(2),cellStyle,businessYcList.get(k).getBusinessYcItemList().get(j-1).getCertificateNum());
                        setCellStyle(temp.createCell(3),cellStyle,String.valueOf(businessYcList.get(k).getBusinessYcItemList().get(j-1).getPrice()));
                    }
                    rows++;
                }
            }
        }
    }

    private void buildSalarySheet(HSSFSheet sheet, List<SalaryInfo> infoList,HSSFCellStyle cellStyle,HSSFWorkbook hssfWorkbook) {
        HSSFRow row = sheet.createRow(0);
//        row.createCell(0).setCellValue("序号");
//        row.createCell(1).setCellValue("姓名");
//        row.createCell(2).setCellValue("国籍");
//        row.createCell(3).setCellValue("证件编号");
//        row.createCell(4).setCellValue("报税地");
//        row.createCell(5).setCellValue("银行卡号");
//        row.createCell(6).setCellValue("开户行");
//        row.createCell(7).setCellValue("所属月份");
//        row.createCell(8).setCellValue("工资类型");
//        row.createCell(9).setCellValue("应发工资");
//        row.createCell(10).setCellValue("应扣工资");
//        row.createCell(11).setCellValue("税前工资");
//        row.createCell(12).setCellValue("个税");
//        row.createCell(13).setCellValue("实发工资");
        setCellStyle(row.createCell(0),cellStyle,"序号");
        setCellStyle(row.createCell(1),cellStyle,"姓名");
        setCellStyle(row.createCell(2),cellStyle,"国籍");
        setCellStyle(row.createCell(3),cellStyle,"证件编号");
        setCellStyle(row.createCell(4),cellStyle,"报税地");
        setCellStyle(row.createCell(5),cellStyle,"银行卡号");
        setCellStyle(row.createCell(6),cellStyle,"开户行");
        setCellStyle(row.createCell(7),cellStyle,"所属月份");
        setCellStyle(row.createCell(8),cellStyle,"工资类型");
        setCellStyle(row.createCell(9),cellStyle,"应发工资");
        setCellStyle(row.createCell(10),cellStyle,"应扣工资");
        setCellStyle(row.createCell(11),cellStyle,"税前工资");
        setCellStyle(row.createCell(12),cellStyle,"个税");
        setCellStyle(row.createCell(13),cellStyle,"实发工资");
        for (int i = 0; i < infoList.size(); i++) {
            HSSFRow row1 = sheet.createRow(i + 1);
//            row1.createCell(0).setCellValue(i + 1);
//            row1.createCell(1).setCellValue(infoList.get(i).getUserName());
//            row1.createCell(2).setCellValue(infoList.get(i).getNationality());
//            row1.createCell(3).setCellValue(infoList.get(i).getCertificateNumber());
//            row1.createCell(4).setCellValue(infoList.get(i).getCityName());
//            row1.createCell(5).setCellValue(infoList.get(i).getBankCard());
//            row1.createCell(6).setCellValue(infoList.get(i).getBankName());
//            row1.createCell(7).setCellValue(DateUtil.dateFormat(infoList.get(i).getMonth(),"yyyy年MM月"));
//            row1.createCell(8).setCellValue(infoList.get(i).getSalaryTypeName());
//            row1.createCell(9).setCellValue(infoList.get(i).getShouldSendSalary());
//            row1.createCell(10).setCellValue(infoList.get(i).getShouldBeDeductPay());
//            row1.createCell(11).setCellValue(infoList.get(i).getSalaryBeforeTax());
//            row1.createCell(12).setCellValue(infoList.get(i).getIndividualIncomeTax());
//            row1.createCell(13).setCellValue(infoList.get(i).getTakeHomePay());
            setCellStyle(row1.createCell(0),cellStyle,String.valueOf(i + 1));
            setCellStyle(row1.createCell(1),cellStyle,infoList.get(i).getUserName());
            setCellStyle(row1.createCell(2),cellStyle,infoList.get(i).getNationality());
            setCellStyle(row1.createCell(3),cellStyle,infoList.get(i).getCertificateNumber());
            setCellStyle(row1.createCell(4),cellStyle,infoList.get(i).getCityName());
            setCellStyle(row1.createCell(5),cellStyle,infoList.get(i).getBankCard());
            setCellStyle(row1.createCell(6),cellStyle,infoList.get(i).getBankName());
            setCellStyle(row1.createCell(7),cellStyle,DateUtil.dateFormat(infoList.get(i).getMonth(),"yyyy年MM月"));
            setCellStyle(row1.createCell(8),cellStyle,String.valueOf(infoList.get(i).getSalaryTypeName()));
            setCellStyle(row1.createCell(9),cellStyle,String.valueOf(infoList.get(i).getShouldSendSalary()));
            setCellStyle(row1.createCell(10),cellStyle,String.valueOf(infoList.get(i).getShouldBeDeductPay()));
            setCellStyle(row1.createCell(11),cellStyle,String.valueOf(infoList.get(i).getSalaryBeforeTax()) );
            setCellStyle(row1.createCell(12),cellStyle,String.valueOf(infoList.get(i).getIndividualIncomeTax()));
            setCellStyle(row1.createCell(13),cellStyle,String.valueOf(infoList.get(i).getTakeHomePay()));
        }
    }

    private void buildReservedFundsSheet(HSSFSheet sheet,List<ReservedFundsInfo> infoList,HSSFCellStyle cellStyle,HSSFWorkbook hssfWorkbook) {
        HSSFRow row = sheet.createRow(0);
//        row.createCell(0).setCellValue("序号");
//        row.createCell(1).setCellValue("姓名");
//        row.createCell(2).setCellValue("证件编号");
//        row.createCell(3).setCellValue("个人客户号");
//        row.createCell(4).setCellValue("缴金地-经办机构");
//        row.createCell(5).setCellValue("比例");
//        row.createCell(6).setCellValue("开始缴纳年月");
//        row.createCell(7).setCellValue("本次服务年月");
//        row.createCell(8).setCellValue("缴纳基数");
//        row.createCell(9).setCellValue("公司缴纳部分");
//        row.createCell(10).setCellValue("个人缴纳部分");
//        row.createCell(11).setCellValue("合计");
        setCellStyle(row.createCell(0),cellStyle,"序号");
        setCellStyle(row.createCell(1),cellStyle,"姓名");
        setCellStyle(row.createCell(2),cellStyle,"证件编号");
        setCellStyle(row.createCell(3),cellStyle,"个人客户号");
        setCellStyle(row.createCell(4),cellStyle,"缴金地-经办机构");
        setCellStyle(row.createCell(5),cellStyle,"比例");
        setCellStyle(row.createCell(6),cellStyle,"开始缴纳年月");
        setCellStyle(row.createCell(7),cellStyle,"本次服务年月");
        setCellStyle(row.createCell(8),cellStyle,"缴纳基数");
        setCellStyle(row.createCell(9),cellStyle,"公司缴纳部分");
        setCellStyle(row.createCell(10),cellStyle,"个人缴纳部分");
        setCellStyle(row.createCell(11),cellStyle,"合计");
        for (int i = 0; i < infoList.size(); i++) {
            HSSFRow row1 = sheet.createRow(i + 1);
//            row1.createCell(0).setCellValue(i + 1);
//            row1.createCell(1).setCellValue(infoList.get(i).getUserName());
//            row1.createCell(2).setCellValue(infoList.get(i).getIdCard());
//            row1.createCell(3).setCellValue(infoList.get(i).getMemberNum());
//            row1.createCell(4).setCellValue(infoList.get(i).getPayPlaceOrganizationName());
//            row1.createCell(5).setCellValue(infoList.get(i).getPayRatio());
//            row1.createCell(6).setCellValue(DateUtil.dateFormat(infoList.get(i).getBeginPayYM(),"yyyy年MM月"));
//            row1.createCell(7).setCellValue(DateUtil.dateFormat(infoList.get(i).getServiceNowYM(),"yyyy年MM月"));
//            row1.createCell(8).setCellValue(infoList.get(i).getPayCardinalNumber());
//            row1.createCell(9).setCellValue(infoList.get(i).getCompanyTotalPay());
//            row1.createCell(10).setCellValue(infoList.get(i).getMemberTotalPay());
//            row1.createCell(11).setCellValue(infoList.get(i).getCompanyTotalPay() + infoList.get(i).getMemberTotalPay());
            setCellStyle(row1.createCell(0),cellStyle,String.valueOf(i + 1));
            setCellStyle(row1.createCell(1),cellStyle,infoList.get(i).getUserName());
            setCellStyle(row1.createCell(2),cellStyle,infoList.get(i).getIdCard());
            setCellStyle(row1.createCell(3),cellStyle,infoList.get(i).getMemberNum());
            setCellStyle(row1.createCell(4),cellStyle,infoList.get(i).getPayPlaceOrganizationName());
            setCellStyle(row1.createCell(5),cellStyle,String.valueOf(infoList.get(i).getPayRatio()));
            setCellStyle(row1.createCell(6),cellStyle,DateUtil.dateFormat(infoList.get(i).getBeginPayYM(),"yyyy年MM月"));
            setCellStyle(row1.createCell(7),cellStyle,DateUtil.dateFormat(infoList.get(i).getServiceNowYM(),"yyyy年MM月"));
            setCellStyle(row1.createCell(8),cellStyle,String.valueOf(infoList.get(i).getPayCardinalNumber()));
            setCellStyle(row1.createCell(9),cellStyle,String.valueOf(infoList.get(i).getCompanyTotalPay()));
            setCellStyle(row1.createCell(10),cellStyle,String.valueOf(infoList.get(i).getMemberTotalPay()));
            setCellStyle(row1.createCell(11),cellStyle,String.valueOf(infoList.get(i).getCompanyTotalPay() + infoList.get(i).getMemberTotalPay()) );
        }


    }

    private void buildSocialSecuritySheet(HSSFSheet sheet,List<SocialSecurityInfo> infoList,HSSFCellStyle cellStyle,HSSFWorkbook hssfWorkbook) throws Exception {

        List<SocialSecurityInfoItem> companyList = null;
        int companyCount = 2; //  公司缴纳的险种数量
        int personCount = 2; // 个人缴纳的险种数量
        Set<String> companyInsurance = new HashSet<String>();
        Set<String> personInsurance = new HashSet<String>();

        for (SocialSecurityInfo socialSecurityInfo : infoList) {
            int tempCompany = 0;
            int tempPerson = 0;
            if(null != socialSecurityInfo.getSocialSecurityInfoItems()){
                for (SocialSecurityInfoItem item : socialSecurityInfo.getSocialSecurityInfoItems()) {
                    if(item.getType() == 0){
                        tempCompany++;
                        companyInsurance.add(item.getInsuranceName());
                    }
                    if(item.getType() == 1){
                        tempPerson++;
                        personInsurance.add(item.getInsuranceName());
                    }
                }
            }
            companyCount = companyCount < tempCompany ? tempCompany : companyCount;
            personCount = personCount < tempPerson ? tempPerson : personCount;
        }

        // 合并单元格
        // part one
        for (int i = 0; i < 9; i++) {
            sheet.addMergedRegion(new CellRangeAddress(0,1,i,i));
        }
        HSSFRow row = sheet.createRow(0);

        setCellStyle(row.createCell(0),cellStyle,"序号");
        setCellStyle(row.createCell(1),cellStyle,"姓名");
        setCellStyle(row.createCell(2),cellStyle,"证件编号");
        setCellStyle(row.createCell(3),cellStyle,"社保编号");
        setCellStyle(row.createCell(4),cellStyle,"缴金地-经办机构");
        setCellStyle(row.createCell(5),cellStyle,"档次");
        setCellStyle(row.createCell(6),cellStyle,"开始缴纳年月");
        setCellStyle(row.createCell(7),cellStyle,"本次服务年月");
        setCellStyle(row.createCell(8),cellStyle,"缴纳基数");



        // part two
        // 公司缴纳部分
        CellRangeAddress cellRangeAddress1 = new CellRangeAddress(0,0,9,9+companyCount - 1);
        sheet.addMergedRegion(cellRangeAddress1);
        row.createCell(9).setCellValue("公司缴纳部分");
        setBorder(cellRangeAddress1,sheet,hssfWorkbook);
        // 第二行
        HSSFRow row1 = sheet.createRow(1);
        List<String> companyInsuranceList = new ArrayList<String>();
        companyInsuranceList.addAll(companyInsurance);
        for (int i = 9; i < 9 + companyCount; i++) {
            if(companyInsuranceList.size() > 0){
                setCellStyle(row1.createCell(i),cellStyle,companyInsuranceList.get(i - 9));
            }
            else{
                setCellStyle(row1.createCell(i),cellStyle,"无");
            }

        }

        // 个人缴纳部门
        CellRangeAddress cellRangeAddress2 = new CellRangeAddress(0,0,9+companyCount,9+companyCount + personCount - 1);
        sheet.addMergedRegion(cellRangeAddress2);
        row.createCell(9+companyCount).setCellValue("个人缴纳部分");
        setBorder(cellRangeAddress2,sheet,hssfWorkbook);
        // 第二行
        List<String> personInsuranceList = new ArrayList<String>();
        personInsuranceList.addAll(personInsurance);
        int index = 0;
        for (int i = 9 + companyCount; i < 9+companyCount + personCount; i++) {
            if(personInsuranceList.size() > 0){
                setCellStyle(row1.createCell(i),cellStyle,personInsuranceList.get(index));
            }
            else{
                setCellStyle(row1.createCell(i),cellStyle,"无");
            }

            index++;
        }
        // 合并汇总
        CellRangeAddress cellRangeAddress3 = new CellRangeAddress(0,0,9+companyCount + personCount ,11+companyCount + personCount);
        sheet.addMergedRegion(cellRangeAddress3);
        setBorder(cellRangeAddress3,sheet,hssfWorkbook);
        setCellStyle(row.createCell(9+companyCount + personCount),cellStyle,"汇总");
        setCellStyle(row1.createCell(9+companyCount + personCount),cellStyle,"公司");
        setCellStyle(row1.createCell(9+companyCount + personCount + 1),cellStyle,"个人");
        setCellStyle(row1.createCell(9+companyCount + personCount + 2),cellStyle,"合计");
        // 设置数据
        for (int i = 0; i < infoList.size(); i++) {
            HSSFRow row3 = sheet.createRow(i + 2);
            SocialSecurityInfo info = infoList.get(i);
            setCellStyle(row3.createCell(0),cellStyle,String.valueOf(i + 1));
            setCellStyle(row3.createCell(1),cellStyle,String.valueOf(info.getUserName()));
            setCellStyle(row3.createCell(2),cellStyle,String.valueOf(null == info.getIdCard() ? "" : info.getIdCard()));
            setCellStyle(row3.createCell(3),cellStyle,String.valueOf(null == info.getSocialSecurityNum() ? "次月可查" : info.getSocialSecurityNum()));
            setCellStyle(row3.createCell(4),cellStyle,String.valueOf(info.getPayPlaceOrganizationName()));
            setCellStyle(row3.createCell(5),cellStyle,String.valueOf(info.getInsuranceLevelName()));
            setCellStyle(row3.createCell(6),cellStyle,String.valueOf(DateUtil.dateFormat(info.getBeginPayYM(),"yyyy年MM月")));
            setCellStyle(row3.createCell(7),cellStyle,String.valueOf(DateUtil.dateFormat(info.getServiceNowYM(),"yyyy年MM月")));
            setCellStyle(row3.createCell(8),cellStyle,String.valueOf(info.getPayCardinalNumber()));
            for (int j = 9; j < 9 + companyCount; j++) {
                if(infoList.size() > 0){
                    for (SocialSecurityInfoItem item : infoList.get(i).getSocialSecurityInfoItems()) {
                        if(item.getInsuranceName().equals(companyInsuranceList.get(j - 9)) && item.getType() == 0){
                            setCellStyle(row3.createCell(j),cellStyle,String.valueOf(item.getPayPrice()));
                            break;
                        }
                    }
                }
            }
            int index_ = 0;
            for (int j = 9 + companyCount; j < 9+companyCount + personCount; j++) {
                if(infoList.size() > 0){
                    for (SocialSecurityInfoItem item : infoList.get(i).getSocialSecurityInfoItems()) {
                        if(item.getInsuranceName().equals(personInsuranceList.get(index_)) && item.getType() == 1){
                            setCellStyle(row3.createCell(j),cellStyle,String.valueOf(item.getPayPrice()));
                            break;
                        }
                    }
                }
                index_ ++;
            }

            setCellStyle(row3.createCell(9+companyCount + personCount),cellStyle,String.valueOf(infoList.get(i).getCompanyTotalPay()));
            setCellStyle(row3.createCell(9+companyCount + personCount + 1),cellStyle,String.valueOf(infoList.get(i).getMemberTotalPay()));
            double d = infoList.get(i).getCompanyTotalPay()
                    + infoList.get(i).getMemberTotalPay();
            setCellStyle(row3.createCell(9+companyCount + personCount + 2),cellStyle,String.valueOf(new BigDecimal(d).setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue()));
        }
    }


    private void buildTotalSheet(HSSFSheet totalSheet,Map<String,Object> totalData,Long createTime,HSSFCellStyle cellStyle,HSSFWorkbook hssfWorkbook) throws Exception {

        // 账户信息
        AccountConfig accountConfig = (AccountConfig) totalData.get("accountConfig");
        HSSFRow row = totalSheet.createRow(0);
        CellRangeAddress cellRangeAddress = new CellRangeAddress(0,0,0,1);
        totalSheet.addMergedRegion(cellRangeAddress);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue(totalData.get("companyName").toString() + "客户"+DateUtil.DateTime(new Date(createTime),"yyyy年MM月")+"账单");
        setBorder(cellRangeAddress, totalSheet, hssfWorkbook);

        HSSFRow oneRow = totalSheet.createRow(1);
        setCellStyle(oneRow.createCell(0),cellStyle,"合计");
        setCellStyle(oneRow.createCell(1),cellStyle,totalData.get("totalPay").toString());


        HSSFRow twoRow = totalSheet.createRow(2);
        setCellStyle(twoRow.createCell(0),cellStyle,"上月余额");
        setCellStyle(twoRow.createCell(1),cellStyle,totalData.get("monthBalance").toString());


        HSSFRow threeRow = totalSheet.createRow(3);
        setCellStyle(threeRow.createCell(0),cellStyle,"本月应到款");
        setCellStyle(threeRow.createCell(1),cellStyle,totalData.get("totalPay").toString());


        HSSFRow fourRow = totalSheet.createRow(4);
        setCellStyle(fourRow.createCell(0),cellStyle,"账单制作人");
        setCellStyle(fourRow.createCell(1),cellStyle,totalData.get("producer").toString());


        HSSFRow fiveRow = totalSheet.createRow(5);
        setCellStyle(fiveRow.createCell(0),cellStyle,"联系方式");
        setCellStyle(fiveRow.createCell(1),cellStyle,totalData.get("phone").toString());

        HSSFRow sixRow = totalSheet.createRow(6);
        setCellStyle(sixRow.createCell(0),cellStyle,"账单制作日期");
        setCellStyle(sixRow.createCell(1),cellStyle,DateUtil.dateFormat(((Date) (totalData.get("createTime"))),"yyyy-MM-dd"));


        HSSFRow sevenRow = totalSheet.createRow(7);
        CellRangeAddress c1 = new CellRangeAddress(7,7,0,1);
        totalSheet.addMergedRegion(c1);
        String msg = "如您对《账单》有任何疑问，请及时联系您的客服："+totalData.get("producer").toString()+"。我们将竭诚为您服务！ ";
        setCellStyle(sevenRow.createCell(0),cellStyle,msg);
        setBorder(c1, totalSheet, hssfWorkbook);


        HSSFRow eightRow = totalSheet.createRow(8);
        CellRangeAddress c2 = new CellRangeAddress(8,8,0,1);
        totalSheet.addMergedRegion(c2);
        setCellStyle(eightRow.createCell(0),cellStyle,"若您确认此账单无疑问，请在下方点击“确认账单”，并按照贵我双方约定的到款日为每月" +
                ""+DateUtil.dateFormat(((Date)totalData.get("payTime")),"dd")+"号，请在此日期之前付款，感谢您对我们的支持！");
        setBorder(c2, totalSheet, hssfWorkbook);

        HSSFRow nineRow = totalSheet.createRow(9);
        CellRangeAddress c3 = new CellRangeAddress(9,9,0,1);
        totalSheet.addMergedRegion(c3);
        setCellStyle(nineRow.createCell(0),cellStyle,"我们的收款信息");
        setBorder(c3, totalSheet, hssfWorkbook);

        HSSFRow tenRow = totalSheet.createRow(10);
        setCellStyle(tenRow.createCell(0),cellStyle,"账户名");
        setCellStyle(tenRow.createCell(1),cellStyle,"四川省道远人力资源管理有限公司");

        HSSFRow elevenRow = totalSheet.createRow(11);
        setCellStyle(elevenRow.createCell(0),cellStyle,"银行账号");
        setCellStyle(elevenRow.createCell(1),cellStyle,"4402 2060 1910 0055 819");

        HSSFRow twelveRow = totalSheet.createRow(12);
        setCellStyle(twelveRow.createCell(0),cellStyle,"开户行");
        setCellStyle(twelveRow.createCell(1),cellStyle,"中国工商银行股份有限公司成都东大街支行");

        HSSFRow thirteenRow = totalSheet.createRow(13);
        setCellStyle(thirteenRow.createCell(0),cellStyle,"联行号");
        setCellStyle(thirteenRow.createCell(1),cellStyle,"102651020607");

        HSSFRow fourteenRow = totalSheet.createRow(14);
        CellRangeAddress c4 = new CellRangeAddress(14,14,0,1);
        totalSheet.addMergedRegion(c4);
        setCellStyle(fourteenRow.createCell(0),cellStyle,"or 支付宝");
        setBorder(c4, totalSheet, hssfWorkbook);

        HSSFRow fifteenRow = totalSheet.createRow(15);
        setCellStyle(fifteenRow.createCell(0),cellStyle,"支付宝名称");
        setCellStyle(fifteenRow.createCell(1),cellStyle,"四川省道远人力资源管理有限公司");

        HSSFRow sixteenRow = totalSheet.createRow(16);
        setCellStyle(sixteenRow.createCell(0),cellStyle,"支付宝账号");
        setCellStyle(sixteenRow.createCell(1),cellStyle,"scdy@cdhro.com");

        HSSFRow eighteenRow = totalSheet.createRow(18);
        CellRangeAddress c5 = new CellRangeAddress(18,18,0,1);
        totalSheet.addMergedRegion(c5);
        eighteenRow.createCell(0).setCellValue("注：其他明细不变，请适当调整单元格大小以便于阅读即可");
    }


    private void setCellStyle(HSSFCell cell,HSSFCellStyle cellStyle,String value){
        cell.setCellValue(value);
        cell.setCellStyle(cellStyle);
    }
    public void setBorder(CellRangeAddress cellRangeAddress, Sheet sheet,
                          Workbook wb) throws Exception {
        RegionUtil.setBorderLeft(1, cellRangeAddress, sheet, wb);
        RegionUtil.setBorderBottom(1, cellRangeAddress, sheet, wb);
        RegionUtil.setBorderRight(1, cellRangeAddress, sheet, wb);
        RegionUtil.setBorderTop(1, cellRangeAddress, sheet, wb);

    }
}



