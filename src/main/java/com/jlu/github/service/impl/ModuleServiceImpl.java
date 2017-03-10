package com.jlu.github.service.impl;

import com.jlu.github.dao.IModuleDao;
import com.jlu.github.model.CiHomeModule;
import com.jlu.github.service.IModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
