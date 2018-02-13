package com.magic.daoyuan.business.entity;

import java.io.Serializable;

/**
 * 公司生成账单时生成的业务
 * @author lzh
 * @create 2018/1/29 18:38
 */
public class CompanySonBillBusiness implements Serializable {


    private Integer id;

    private Integer businessId;

    /** 账单月 */
    private Integer billMonth;

    /** 公司id */
    private Integer companyId;

}
