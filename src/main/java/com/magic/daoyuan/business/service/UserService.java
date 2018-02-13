package com.magic.daoyuan.business.service;

import com.magic.daoyuan.business.entity.*;
import com.magic.daoyuan.business.enums.Common;
import com.magic.daoyuan.business.exception.InterfaceCommonException;
import com.magic.daoyuan.business.mail.EmailUtil;
import com.magic.daoyuan.business.mapper.IBacklogMapper;
import com.magic.daoyuan.business.mapper.ILogMapper;
import com.magic.daoyuan.business.mapper.IUserMapper;
import com.magic.daoyuan.business.util.CommonUtil;
import com.magic.daoyuan.business.util.StatusConstant;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Eric Xie on 2017/9/12 0012.
 */
@Service
public class UserService {

    @Resource
    private IUserMapper userMapper;
    @Resource
    private IBacklogMapper backlogMapper;
    @Resource
    private ILogMapper logMapper;



    /**
     * 通过角色查询用户
     * @param roleId
     * @return
     */
    public List<User> queryUserByRole(Integer roleId){
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("roleId",roleId);
        return userMapper.queryUserByItems(params);
    }


    /**
     * @param flag
     * @return
     */
    public List<User> queryUserByRoles(Integer flag){
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("flag",flag);
        return userMapper.queryUserByItems_(params);
    }

    public PageList<User> queryUserByItems(Map<String,Object> params, PageArgs pageArgs){
        List<User> userList = new ArrayList<User>();
        int count = userMapper.countUserByItems(params);
        if(count > 0){
            params.put("limit",pageArgs.getPageStart());
            params.put("limitSize",pageArgs.getPageSize());
            userList = userMapper.queryUserByItems(params);
        }
        return new PageList<User>(userList,count);
    }

    public User queryUserByAccount(String account){
        return userMapper.queryUserByAccount(account);
    }

    public User queryUserById(Integer userId){
        return userMapper.queryUserById(userId);
    }

    @Transactional
    public void updateUser(User user,User currentUser){
        User u = userMapper.queryUserById(user.getId());
        if(null == u ){
            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST,"用户不存在");
        }
        userMapper.updateUser(user);
        String msg = "";
        if(null != user.getIsValid() && !u.getIsValid().equals(user.getIsValid())){
            if(Common.NO.ordinal() == user.getIsValid()){
                msg = "修改用户"+user.getUserName()+"为：冻结状态";
            }
            else{
                msg = "修改用户"+user.getUserName()+"为：正常状态";
            }
        }
        if(!u.getUserName().equals(user.getUserName())){
            msg += "修改用户为："+user.getUserName();
        }

        if(null != user.getPwd() && !u.getPwd().equals(user.getPwd())){
            msg += "重置了用户："+user.getUserName() +"的密码";
        }
        if(!CommonUtil.isEmpty(msg)){
            Log log = new Log(currentUser.getId(),StatusConstant.LOG_MODEL_USER,msg,StatusConstant.LOG_FLAG_UPDATE);
            logMapper.add(log);
        }
    }


    @Transactional
    public void addUser(User user,String loginUrl,User currentUser){

        User user1 = userMapper.queryUserByAccount(user.getAccount());
        if (null != user1) {
            throw new InterfaceCommonException(StatusConstant.OBJECT_EXIST,"账号已存在");
        }

        userMapper.addUser(user);
        //待办 前道客服
        Backlog backlog = new Backlog();
        backlog.setRoleId(9);
        backlog.setUserId(user.getId());
        backlog.setContent("您的用户已成功添加，初始密码为111111，请及时登录修改");
        /*backlog.setUrl("/page/user/save");*/
        backlogMapper.save(backlog);
        if (!CommonUtil.isEmpty(user.getEmail())) {
            Email email = new Email(user.getEmail(),"您的道远账号信息已生成,初始密码111111",loginUrl);
            EmailUtil.sendEmail(email);
        }
        // 日志
        Log log = new Log(currentUser.getId(), StatusConstant.LOG_MODEL_USER,"新增用户"+user.getUserName(),StatusConstant.LOG_FLAG_ADD);
        logMapper.add(log);
    }

    public void updatePassword(Integer id, String password){
        userMapper.updatePassword(id, password);
    }


    /**
     * 查找超时未稽核账单的客服
     * @param date
     * @return
     */
    public List<User> getAuditUnDispose(Date date){
        return userMapper.getAuditUnDispose(date);
    }
    /**
     * 查找超时未处理工单的客服
     * @param date
     * @return
     */
    public List<User> getWorkOrderUnDispose(Date date){
        return userMapper.getWorkOrderUnDispose(date);
    }
}
