package com.jlu.github.service.impl;

import com.jlu.common.db.bean.PageBean;
import com.jlu.common.db.sqlcondition.ConditionAndSet;
import com.jlu.github.dao.IModuleDao;
import com.jlu.github.model.CiHomeModule;
import com.jlu.github.service.IModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by niuwanpeng on 17/3/10.
 */
@Service
public class ModuleServiceImpl implements IModuleService{

    @Autowired
    private IModuleDao moduleDao;

    /**
     *  保存模块信息
     * @param ciHomeModule
     */
    @Override
    public void saveModule(CiHomeModule ciHomeModule)  {

    }

    /**
     * 搜索模块
     * @param q
     * @param username
     * @param limit
     * @return
     */
    @Override
    public List<CiHomeModule> getSuggestProductModules(String q, String username, int limit) {
        PageBean page = new PageBean(limit);
        return moduleDao.getSuggestProductModules(q, username, page);
    }
}
