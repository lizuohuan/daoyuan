package com.magic.daoyuan.business.service;

import com.magic.daoyuan.business.entity.Backlog;
import com.magic.daoyuan.business.entity.Inform;
import com.magic.daoyuan.business.entity.PageArgs;
import com.magic.daoyuan.business.entity.PageList;
import com.magic.daoyuan.business.mapper.IBacklogMapper;
import com.magic.daoyuan.business.mapper.IInformMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 待办
 * @author lzh
 * @create 2017/12/21 17:30
 */
@Service
public class BacklogService {

    @Resource
    private IBacklogMapper backlogMapper;

    /**
     * 后台页面 分页获取待办列表
     *
     * @param pageArgs    分页属性
     * @param roleId     角色id
     * @return
     */
    public PageList<Backlog> list(PageArgs pageArgs , Integer userId, Integer roleId  , Date startTime , Date endTime) {
        PageList<Backlog> pageList = new PageList<Backlog>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("roleId", roleId);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        int count = backlogMapper.listCount(map);
        if (count > 0) {
            map.put("pageArgs", pageArgs);
            pageList.setList(backlogMapper.list(map));
        }
        pageList.setTotalSize(count);
        return pageList;
    }


    public void save(Backlog backlog) {
        backlogMapper.save(backlog);
    }

    public void delete(Integer id) {
        backlogMapper.delete(id);
    }

}
