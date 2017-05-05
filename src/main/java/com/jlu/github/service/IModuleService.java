package com.jlu.github.service;

import com.jlu.github.model.CiHomeModule;

import java.util.List;

/**
 * Created by niuwanpeng on 17/3/10.
 */
public interface IModuleService {

    /**
     *  保存模块信息
     * @param ciHomeModule
     */
    void saveModule(CiHomeModule ciHomeModule);

    /**
     * 删除模块
     * @param ciHomeModule
     */
    void delete(CiHomeModule ciHomeModule);

    /**
     * 批量保存模块信息
     * @param list
     */
    void saveModules(List<CiHomeModule> list);

    /**
     * 搜索模块
     * @param q
     * @param username
     * @param limit
     * @return
     */
    List<CiHomeModule> getSuggestProductModules(String q, String username, int limit);

    /**
     * 通过用户名获得该名下所有模块信息
     * @param username
     * @return
     */
    List<CiHomeModule> getModulesByUsername(String username);

    /**
     * 通过用户名和模块名获得模块信息
     * @param username
     * @param module
     * @return
     */
    CiHomeModule getModuleByUserAndModule(String username, String module);
}
