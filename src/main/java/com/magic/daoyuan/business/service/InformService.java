package com.magic.daoyuan.business.service;

import com.magic.daoyuan.business.entity.Inform;
import com.magic.daoyuan.business.entity.PageArgs;
import com.magic.daoyuan.business.entity.PageList;
import com.magic.daoyuan.business.mapper.IInformMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 通知
 * @author lzh
 * @create 2017/12/21 17:30
 */
@Service
public class InformService {

    @Resource
    private IInformMapper informMapper;

    /**
     * 后台页面 分页获取通知列表
     *
     * @param pageArgs    分页属性
     * @param userId     用户id
     * @return
     */
    public PageList<Inform> list(PageArgs pageArgs , Integer userId, Integer roleId , Date startTime , Date endTime) {
        PageList<Inform> pageList = new PageList<Inform>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("roleId", roleId);
        map.put("userId", userId);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        int count = informMapper.listCount(map);
        if (count > 0) {
            map.put("pageArgs", pageArgs);
            pageList.setList(informMapper.list(map));
        }
        pageList.setTotalSize(count);
        return pageList;
    }


    public void save(Inform inform) {
        informMapper.save(inform);
    }

    public void delete(Integer id) {
        informMapper.delete(id);
    }

}
