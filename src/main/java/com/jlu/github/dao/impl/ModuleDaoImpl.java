package com.jlu.github.dao.impl;

import com.jlu.common.db.dao.AbstractBaseDao;
import com.jlu.github.dao.IModuleDao;
import com.jlu.github.model.CiHomeModule;
import org.springframework.stereotype.Repository;

/**
 * Created by niuwanpeng on 17/3/10.
 *
 *  模块信息dao实体类
 */
@Repository
public class ModuleDaoImpl extends AbstractBaseDao<CiHomeModule> implements IModuleDao {
}
