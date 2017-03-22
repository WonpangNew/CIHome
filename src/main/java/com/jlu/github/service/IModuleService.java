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
     * 搜索模块
     * @param q
     * @param username
     * @param limit
     * @return
     */
    List<CiHomeModule> getSuggestProductModules(String q, String username, int limit);
}
