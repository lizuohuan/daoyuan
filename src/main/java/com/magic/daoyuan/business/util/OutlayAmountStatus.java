package com.magic.daoyuan.business.util;

/**
 * 财务出款记录 状态
 * 状态  2001：待审核、2002：待出款、2003：已退款、2004：已完成
 * Created by Eric Xie on 2017/11/18 0018.
 */
public class OutlayAmountStatus {

    /** 2001：待审核 */
    public static final Integer pending = 2001;

    /** 2002：待出款 */
    public static final Integer outlayPending = 2002;

    /** 2003：已退款 */
    public static final Integer refunded = 2003;

    /** 2004：已完成 */
    public static final Integer finished = 2004;

    /** 2004：已完成 */
    public static final Integer turnDown = 2005;


}
