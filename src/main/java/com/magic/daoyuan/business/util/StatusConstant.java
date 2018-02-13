package com.magic.daoyuan.business.util;

public class StatusConstant {



	public static final Integer NO = 0;
	public static final Integer YES = 1;

	// 错误代码
	/**获取成功*/
	public static final Integer SUCCESS_CODE = 200;
	// 201 备用
	/**获取失败*/
	public static final Integer Fail_CODE = 202;
	/**参数异常*/
	public static final Integer ARGUMENTS_EXCEPTION = 203;

	
	
	/**没有权限 错误代码*/
	public static final Integer NOT_AGREE = 1001;
	/**对象不存在*/
	public static final Integer OBJECT_NOT_EXIST = 1002;
	/**字段不能为空*/
	public static final Integer FIELD_NOT_NULL= 1003;
	/**正在审核*/
	public static final Integer PENDING = 1004;
	/**未登录*/
	public static final Integer NOTLOGIN= 1005;
	/**没有数据*/
	public static final Integer NO_DATA = 1006;
	/**账户被冻结*/
	public static final Integer ACCOUNT_FROZEN = 1007;
	/**订单无效*/
	public static final Integer ORDER_INVALID = 1008;
	/**状态异常*/
	public static final Integer ORDER_STATUS_ABNORMITY = 1009;
	/**对象已经存在*/
	public static final Integer OBJECT_EXIST = 1010;

	/** 用户不存在 */
	public static final Integer USER_DOES_NOT_EXIST = 1050;
	/** 用户名或者密码错误 */
	public static final Integer PASSWORD_ERROR = 1051;

	/** 电话号码已存在 */
	public static final Integer PHONE_NUMBER_THERE = 1052;
	/** 账号已存在 */
	public static final Integer USER_NAME_ALREADY_EXISTS = 1053;
	/** 邮箱已存在 */
	public static final Integer EMAIL_IS_EXISTENCE = 1054;




	// 设备类型
	/**android*/
	public static final Integer ANDROID=0;
	/**ios*/
	public static final Integer IOS = 1;

	/** 支付状态   未支付 */
	public static final Integer NO_PAY = 0;
	/** 支付状态   已支付 */
	public static final Integer YES_PAY = 1;


	/** 未通过 */
	public static final Integer ACCOUNT_NON_APPROVED = 0;

	/** 审核通过 */
	public static final Integer ACCOUNT_APPROVED = 1;

	/** 审核中 */
	public static final Integer ACCOUNT_APPROVED_ING = 2;


	// 导入流水银行类别
	/** 工商银行 */
	public static  Integer ICBC = 0;

	/** 成都银行 */
	public static final Integer CDB = 1;

	/** 华夏银行 */
	public static final Integer HXB = 2;

	/** 支付宝 */
	public static final Integer ALIPAY = 3;

	// 工资类型

	/** 工资 */
	public static final Integer SALARY = 0;

	/** 年终奖 */
	public static final Integer YEAR_END_BONUS = 1;

	/** 劳务所得 */
	public static final Integer LABOR_INCOME = 2;

	/** 离职补偿金 */
	public static final Integer SEPARATION_ALLOWANCE = 3;

	/** 税收起征点 */
	public static final Double THRESHOLD = 3500.0;

	/** 成都公积金 缴纳上限 */
	public static final Double RESERVED_MAX_CD = 1839.9;

	/** 重庆公积金 缴纳上限 */
	public static final Double RESERVED_MAX_CQ = 2022.0;

	// 发票状态
	/** 未开票 */
	public static final Integer BILL_STATUS_UN = 3001;

	/** 已开票 */
	public static final Integer BILL_STATUS_ED = 3002;

	/** 已邮寄 */
	public static final Integer BILL_STATUS_ED_EXPRESS = 3003;

	/** 票据作废 */
	public static final Integer BILL_STATUS_INVALID = 3004;


	/** 残保金固定的ID */
	public static final Integer DISABILITY_ID = 10;

	// 开票商品名称对应
	/** 商品名称- 劳务外保费 */
	public static final Integer SHOPNAMEID_LABOR = 1;

	/** 商品名称- 人事代理费 */
	public static final Integer SHOPNAMEID_STAFFING = 2;

	/** 商品名称- 服务费 */
	public static final Integer SHOPNAMEID_SERVICE = 3;


	// 常用固定城市ID

	/** 成都 */
	public static final Integer CITY_CHENGDU = 510100;

	/** 重庆 */
	public static final Integer CITY_CHONGQING = 500100;


	// 变更内容词汇

	public static final String MSG_SHEBAO = "社保";
	public static final String MSG_GONGJIJIN = "公积金";
	public static final String MSG_PAY_PLACE = "缴金地";
	public static final String MSG_ORGNAIZATION = "经办机构";
	public static final String MSG_TRANSACTOR = "办理方";
	public static final String MSG_RATIO = "比例";

	// 基础字段变更 姓名/证件类型/证件编号/手机号/学历/档次/基数
	//  0:姓名 1:证件类型 2:证件号 3:手机号 4:学历 5:档次 6:基数
	public static final String MSG_NAME = "姓名";
	public static final Integer BASE_NAME = 0;

	public static final String MSG_CARD_TYPE = "证件类型";
	public static final Integer BASE_CARD_TYPE = 1;

	public static final String MSG_CARD = "证件编号";
	public static final Integer BASE_CARD = 2;

	public static final String MSG_PHONE = "手机号";
	public static final Integer BASE_PHONE = 3;

	public static final String MSG_EDUCATION = "学历";
	public static final Integer BASE_EDUCATION = 4;

	public static final String MSG_LEVEL = "档次";
	public static final Integer BASE_LEVEL = 5;

	public static final String MSG_BASE_BUMBER = "基数";
	public static final Integer BASE_BASE_BUMBER = 6;



	// 变更内容flag：0-社保 1-公积金 2-缴金地 3-经办机构 4-办理方 5-比例  6-/档次/基数

	/** 社保 */
	public static final Integer UPDATE_SOCIAL_SECURITY = 0;

	/** 公积金 */
	public static final Integer UPDATE_RESERVED_FUNDS = 1;

	/** 缴金地 */
	public static final Integer UPDATE_PAY_PLACE = 2;

	/** 经办机构 */
	public static final Integer UPDATE_ORGNAIZATION = 3;

	/** 办理方 */
	public static final Integer UPDATE_TRANSACTOR = 4;

	/** 比例 */
	public static final Integer UPDATE_RATIO = 5;

	/** 基数/基础字段 */
	public static final Integer UPDATE_RATIO_BASENUMBER = 6;


	// 增减变子类 0：待申请 1：待反馈  2：成功  3: 失败 4: 取消

	/** 待申请 */
	public static final Integer ITEM_APPLICATION = 0;

	/** 待反馈 */
	public static final Integer ITEM_FEEDBACKING = 1;

	/** 成功 */
	public static final Integer ITEM_SUCCESS = 2;

	/** 失败 */
	public static final Integer ITEM_FAILURE = 3;

	/** 取消 */
	public static final Integer ITEM_CANCEL = 4;

	// 认款状态

//	/** 处理方式 ：
//	 *  0、大于0；纳入次月账单、退回客户（自动生成出款单）
//	 *  1、小于0；纳入次月账单、追回尾款（线下）
//	 *  2、等于0；足额到款，不做处理。
//	 *
//	 *  值： 0 纳入次月账单  1 退回客户（自动生成出款单）
//	 *      2 追回尾款（线下） 3 不做处理
//	 */

	/** 纳入次月账单 */
	public static final Integer HANDLEMETHOD_NEXT_MONTH_BILL = 0;

	/** 退回客户 */
	public static final Integer HANDLEMETHOD_BACK_CUSTOMER = 1;

	/** 追回尾款 */
	public static final Integer HANDLEMETHOD_RECOVER_AMOUNT = 2;

	/** 不做处理 */
	public static final Integer HANDLEMETHOD_NO_HANDLE = 3;

	/** 追回欠款 已经处理 */
	public static final Integer HANDLEMETHOD_RECOVER_AMOUNT_HANDLE = 4;

	
	
	// 财务出款记录状态
	//  2001：待审核、2002：待出款、2003：已退款、2004：已完成

	/** 待审核 */
	public static final Integer FINANCIAL_AUDITING = 2001;

	/** 待出款 */
	public static final Integer FINANCIAL_PENDING = 2002;

	/** 已退款 */
	public static final Integer FINANCIAL_REFUNDED = 2003;

	/** 已完成 */
	public static final Integer FINANCIAL_FINISH = 2004;


	/** 公司- 先票后款 */
	public static final Integer FIRST_BILL_YES = 1;

	/** 公司- 先款后票 */
	public static final Integer FIRST_BILL_NO = 0;

	
	// 日志模块

	/** 公司/客户模块 */
	public static final Integer LOG_MODEL_COMPANY = 0;

	/** 用户权限配置模块 */
	public static final Integer LOG_MODEL_USER = 1;

	/** 业务配置 */
	public static final Integer LOG_MODEL_BUSINESS = 2;

	/** 账单模块 */
	public static final Integer LOG_MODEL_BILL = 3;

	/** 知识库 */
	public static final Integer LOG_MODEL_REPOSITORY = 4;

	/** 供应商 */
	public static final Integer LOG_MODEL_SUPPLIER = 5;

	/** 服务配置 */
	public static final Integer LOG_MODEL_CONFIG = 6;


	/** 增 */
	public static final Integer LOG_FLAG_ADD = 0;

	/** 删 */
	public static final Integer LOG_FLAG_DEL = 1;

	/** 改 */
	public static final Integer LOG_FLAG_UPDATE = 2;


	/** 开票最大金额， 超过此金额则拆分 */
	public static final double BILL_AMOUNT = 99999.99;

	/** 邮件发送 话术 */
	public static String EMAIL_MSG = "您好： 贵公司{0}账单（{1}），请查看，如有问题您及时联系我：{2} {3}。若核对无误，请于{4}日前转款，谢谢！";
	
	
	
}
