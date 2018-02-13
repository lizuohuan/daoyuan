package com.magic.daoyuan.business.entity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 服务费收取明细
 * Created by Eric Xie on 2017/11/24 0024.
 */
public class MonthServiceFeeDetail {


    private Integer id;

    /** 每月总服务费ID */
    private Integer monthServiceFeeId;

    /** 具体收取的服务费 */
    private Double amount;

    /** 员工 */
    private Integer memberId;

    /** 子账单id */
    private Integer companySonBillId;

    /** 合作方式 0：普通 1：派遣  2：外包 */
    private Integer waysOfCooperation;

    /** 服务类别 以逗号隔开 */
    private String businessStr;

    /** 服务类别和地区json */
    private String businessCityJson;


    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /** 获取 每月总服务费ID */
    public Integer getMonthServiceFeeId() {
        return this.monthServiceFeeId;
    }

    /** 设置 每月总服务费ID */
    public void setMonthServiceFeeId(Integer monthServiceFeeId) {
        this.monthServiceFeeId = monthServiceFeeId;
    }

    /** 获取 具体收取的服务费 */
    public Double getAmount() {
        return this.amount;
    }

    /** 设置 具体收取的服务费 */
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    /** 获取 员工 */
    public Integer getMemberId() {
        return this.memberId;
    }

    /** 设置 员工 */
    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    /** 获取 合作方式 0：普通 1：派遣  2：外包 */
    public Integer getWaysOfCooperation() {
        return this.waysOfCooperation;
    }

    /** 设置 合作方式 0：普通 1：派遣  2：外包 */
    public void setWaysOfCooperation(Integer waysOfCooperation) {
        this.waysOfCooperation = waysOfCooperation;
    }

    /** 获取 子账单id */
    public Integer getCompanySonBillId() {
        return this.companySonBillId;
    }

    /** 设置 子账单id */
    public void setCompanySonBillId(Integer companySonBillId) {
        this.companySonBillId = companySonBillId;
    }

    /** 获取 服务类别 以逗号隔开 */
    public String getBusinessStr() {
        return this.businessStr;
    }

    /** 设置 服务类别 以逗号隔开 */
    public void setBusinessStr(String businessStr) {
        this.businessStr = businessStr;
    }

    /**
     * 获取 服务类别和地区json
     *
     * @return businessCityJson 服务类别和地区json
     */
    public String getBusinessCityJson() {
        return this.businessCityJson;
    }

    /**
     * 设置 服务类别和地区json
     *
     * @param businessCityJson 服务类别和地区json
     */
    public void setBusinessCityJson(String businessCityJson) {
        this.businessCityJson = businessCityJson;
    }

    /**
     * 解析 封装服务类别和地区json 转换成集合
     * @return
     */
    public /*List<Map<Object,Object>>*/Map<Integer,List<Object>> getBusinessCityJsonList() throws IOException {
        if (null != businessCityJson) {

            return (Map<Integer, List<Object>>) JSONObject.parse(businessCityJson);

            /*ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(businessCityJson,List.class);*/
        }


        return null;
    }
}
