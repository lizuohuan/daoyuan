package com.magic.daoyuan.business.mapper;

import com.magic.daoyuan.business.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Eric Xie on 2017/9/12 0012.
 */
public interface IUserMapper {



    /**
     * 通过用户ID 查询用户
     * @param id
     * @return
     */
    User queryUserById(@Param("id") Integer id);
    /**
     * 新增用户
     * @param user
     * @return
     */
    Integer addUser(User user);

    /**
     * 通过用户ID 更新用户信息
     * @param user
     * @return
     */
    Integer updateUser(User user);

    /**
     * 通过用户ID 更新用户信息
     * @param user
     * @return
     */
    Integer updatePassword(@Param("id") Integer id, @Param("password") String password);

    /**
     * 条件动态查询用户
     * @param params
     * @return
     */
    List<User> queryUserByItems(Map<String,Object> params);

    /**
     * 条件动态查询用户
     * @param params
     * @return
     */
    List<User> queryUserByItems_(Map<String,Object> params);

    /**
     * 条件动态统计用户
     * @param params
     * @return
     */
    Integer countUserByItems(Map<String,Object> params);

    /**
     * 通过帐号查询用户
     * @param account
     * @return
     */
    User queryUserByAccount(@Param("account") String account);


    /**
     * 查找超时未稽核账单的客服
     * @param date
     * @return
     */
    List<User> getAuditUnDispose(@Param("date") Date date);
    /**
     * 查找超时未处理工单的客服
     * @param date
     * @return
     */
    List<User> getWorkOrderUnDispose(@Param("date") Date date);
}
