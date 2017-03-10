package com.jlu.github.service;

import com.jlu.github.model.CiHomeModule;

/**
 * Created by niuwanpeng on 17/3/10.
 */
public interface IModuleService {

    /**
     *  保存模块信息
     * @param ciHomeModule
     */
    void saveModule(CiHomeModule ciHomeModule);
}
