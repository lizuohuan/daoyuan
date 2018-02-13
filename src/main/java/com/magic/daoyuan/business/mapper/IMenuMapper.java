package com.magic.daoyuan.business.mapper;



import com.magic.daoyuan.business.entity.Menu;

import java.util.List;

/**
 * 菜单（权限） -- 接口
 * @author lzh
 * @create 2017/4/17 14:16
 */
public interface IMenuMapper {

    /**
     * 查询所有菜单(权限)
     * @return
     */
    List<Menu> findAllMenu();

    /**
     * 获取当前登录人角色的权限
     * @param userId
     * @return
     */
    List<Menu> getRoleMenu(Integer userId);

}
