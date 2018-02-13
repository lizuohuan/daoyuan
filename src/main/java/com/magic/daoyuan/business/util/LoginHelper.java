package com.magic.daoyuan.business.util;

import com.magic.daoyuan.business.entity.Company;
import com.magic.daoyuan.business.entity.User;
import com.magic.daoyuan.business.enums.Common;
import com.magic.daoyuan.business.exception.InterfaceCommonException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


public class LoginHelper {


    public static boolean isLogin = false;

    /**
     * SESSION USER
     */
    public static final String SESSION_USER = "admin_user";

    /***
     * hr用户
     */
    public static final String SESSION_HR_USER = "hr_user";



    public static User getCurrentUser() {
        HttpServletRequest req = ((ServletRequestAttributes) (RequestContextHolder.getRequestAttributes())).getRequest();
        User user = (User) req.getSession().getAttribute(SESSION_USER);
        if (null == user) {
            throw new InterfaceCommonException(StatusConstant.NOTLOGIN, "未登录");
        }
        if (Common.NO.ordinal() == user.getIsValid()) {
            throw new InterfaceCommonException(StatusConstant.ACCOUNT_FROZEN, "帐号无效");
        }
        return user;
    }

    public static Company getCurrentHrUser() {
        HttpServletRequest req = ((ServletRequestAttributes) (RequestContextHolder.getRequestAttributes())).getRequest();
        Company company = (Company) req.getSession().getAttribute(SESSION_HR_USER);
        if (null == company) {
            throw new InterfaceCommonException(StatusConstant.NOTLOGIN, "未登录");
        }
        if (Common.NO.ordinal() == company.getIsValid()) {
            throw new InterfaceCommonException(StatusConstant.ACCOUNT_FROZEN, "帐号无效");
        }
        return company;
    }



    public static void clearToken() {
        HttpServletRequest req = ((ServletRequestAttributes) (RequestContextHolder.getRequestAttributes())).getRequest();
        req.getSession().invalidate();
    }


}
