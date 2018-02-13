package com.magic.daoyuan.business.service;

import com.magic.daoyuan.business.entity.*;
import com.magic.daoyuan.business.enums.Common;
import com.magic.daoyuan.business.mapper.IInformMapper;
import com.magic.daoyuan.business.mapper.ILogMapper;
import com.magic.daoyuan.business.mapper.IRepositoryMapper;
import com.magic.daoyuan.business.util.CommonUtil;
import com.magic.daoyuan.business.util.DateUtil;
import com.magic.daoyuan.business.util.StatusConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 知识库业务
 */
@Service
public class RepositoryService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private IRepositoryMapper repositoryMapper;
    @Resource
    private IInformMapper informMapper;
    @Resource
    private ILogMapper logMapper;
    /**
     * 添加知识库
     * @param repository 知识库实体
     */
    @Transactional
    public void insert(Repository repository,User user) throws Exception{

        String msg = "";
        if (repository.getType() == 0) {
            msg = "HT";
        }
        else if (repository.getType() == 1) {
            msg = "TS";
        }
        else if (repository.getType() == 2) {
            msg = "SOP";
        }
        long l = System.currentTimeMillis();
        Date date = new Date(l);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMM");
        int count = repositoryMapper.countNumber(date);
        repository.setKBID(msg + dateFormat.format(date) + (count + 1));
        if (CommonUtil.isEmpty(repository.getRoleIds())) {
            List<Inform> list = new ArrayList<Inform>();
            String roleIds[] = repository.getRoleIds().split(",");
            for (int i = 0; i < roleIds.length; i++) {
                Inform inform = new Inform();
                inform.setRoleId(Integer.parseInt(roleIds[i]));
                inform.setContent("知识库信息更新 ");
            }
            if (list.size() > 0) {
                informMapper.saveList(list);
            }
        }
        repositoryMapper.insert(repository);
        logMapper.add(new Log(user.getId(), StatusConstant.LOG_MODEL_REPOSITORY,"新增知识库:"+repository.getTitle(),StatusConstant.LOG_FLAG_ADD));
    }

    /**
     * 修改知识库
     * @param repository 知识库实体
     */
    @Transactional
    public void update(Repository repository,User user) throws Exception{
        repositoryMapper.update(repository);
        if (CommonUtil.isEmpty(repository.getRoleIds())) {
            List<Inform> list = new ArrayList<Inform>();
            String roleIds[] = repository.getRoleIds().split(",");
            for (int i = 0; i < roleIds.length; i++) {
                Inform inform = new Inform();
                inform.setRoleId(Integer.parseInt(roleIds[i]));
                inform.setContent("知识库信息更新");
            }
            if (list.size() > 0) {
                informMapper.saveList(list);
            }
        }
        logMapper.add(new Log(user.getId(), StatusConstant.LOG_MODEL_REPOSITORY,"修改知识库:"+repository.getTitle(),
                StatusConstant.LOG_FLAG_UPDATE));

    }

    /**
     * 后台分页查询
     * @param params 筛选条件
     * @param pageArgs 页码
     * @return 知识库集合
     */
    public PageList<Repository> queryRepositoryByItems (Map<String,Object> params, PageArgs pageArgs) {
        List<Repository> list = new ArrayList<Repository>();
        int count = repositoryMapper.countRepositoryByItems(params);
        if (count > 0) {
            params.put("limit",pageArgs.getPageStart());
            params.put("limitSize",pageArgs.getPageSize());
            list = repositoryMapper.queryRepositoryByItems(params);
            if(list.size() > 0){
                int nowStamp = (int)(System.currentTimeMillis() / 1000);
                for (Repository repository : list) {
                    if(nowStamp >= DateUtil.getDateStamp(repository.getStartValidity(),1) && nowStamp <= DateUtil.getDateStamp(repository.getEndValidity(),0)){
                        repository.setIsValid(Common.YES.ordinal());
                    }
                    else{
                        repository.setIsValid(Common.NO.ordinal());
                    }
                }
            }
        }
        return new PageList<Repository>(list, count);
    }

    /**
     * 知识库详情
     * @param id 知识库ID
     * @return
     */
    public Repository findById (Integer id) {
        try {
            return repositoryMapper.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
