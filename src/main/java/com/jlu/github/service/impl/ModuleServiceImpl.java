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
    public void saveModule(CiHomeModule ciHomeModule) {
        moduleDao.save(ciHomeModule);
    }

    /**
     * 删除模块
     * @param ciHomeModule
     */
    public void delete(CiHomeModule ciHomeModule) {
        moduleDao.delete(ciHomeModule);
    }

    /**
     * 批量保存模块信息
     * @param list
     */
    @Override
    public void saveModules(List<CiHomeModule> list) {
        moduleDao.saveOrUpdateAll(list);
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

    /**
     * 通过用户名获得该名下所有模块信息
     * @param username
     * @return
     */
    @Override
    public List<CiHomeModule> getModulesByUsername(String username) {
        ConditionAndSet conditionAndSet = new ConditionAndSet();
        conditionAndSet.put("username", username);
        return moduleDao.findByProperties(conditionAndSet);
    }

    /**
     * 通过用户名和模块名获得模块信息
     * @param username
     * @param module
     * @return
     */
    @Override
    public CiHomeModule getModuleByUserAndModule(String username, String module) {
        ConditionAndSet conditionAndSet = new ConditionAndSet();
        conditionAndSet.put("username", username);
        conditionAndSet.put("module", module);
        List<CiHomeModule> modules = moduleDao.findByProperties(conditionAndSet);
        if (modules != null && modules.size() != 0) {
            return modules.get(0);
        }
        return null;
    }
}
