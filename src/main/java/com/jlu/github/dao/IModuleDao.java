package com.jlu.github.dao;

import com.jlu.common.db.bean.PageBean;
import com.jlu.common.db.dao.IBaseDao;
import com.jlu.github.model.CiHomeModule;

import java.util.List;

/**
 * Created by niuwanpeng on 17/3/10.
 */
public interface IModuleDao extends IBaseDao<CiHomeModule> {

    List<CiHomeModule> getSuggestProductModules(String query, String username, PageBean page);
}
