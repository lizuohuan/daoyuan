package com.magic.daoyuan.business.redis.util;

import com.magic.daoyuan.business.util.DateUtil;

import java.util.Date;

/**
 * Created by Eric Xie on 2017/9/1 0001.
 */
public class CommonUtil {



    protected static boolean isEmpty(Object... obj){
        for (int i = 0; i < obj.length; i++) {
            if(obj[i] instanceof String ){
                if(null == obj[i] || ((String)obj[i]).trim().length() == 0){
                    return true;
                }
            }else {
                if(null == obj[i]){
                    return true;
                }
            }
        }
        return false;
    }

}
