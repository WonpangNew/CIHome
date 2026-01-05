package com.jlu.user.service.impl;

import com.jlu.common.db.sqlcondition.ConditionAndSet;
import com.jlu.user.dao.IUserDao;
import com.jlu.user.model.CiHomeUser;
import com.jlu.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户服务实现类
 * <p>
 * 提供用户管理相关的业务逻辑实现，包括用户的保存、查询等功能。
 * </p>
 *
 * @author niuwanpeng
 * @since 2017-03-10
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    IUserDao userDao;

    /**
     * 保存用户信息
     * <p>
     * 将用户对象持久化到数据库中。
     * </p>
     *
     * @param ciHomeUser 用户实体对象
     */
    public void saveUser(CiHomeUser ciHomeUser) {
        userDao.save(ciHomeUser);
    }

    /**
     * 通过用户名获取用户信息
     * <p>
     * 根据用户名查询数据库，返回完整的用户信息对象。
     * </p>
     *
     * @param username 用户名
     * @return 用户实体对象，不存在则返回null
     */
    public CiHomeUser getUserByName(String username) {
        ConditionAndSet conditionAndSet = new ConditionAndSet();
        conditionAndSet.put("username", username);
        List<CiHomeUser> users = userDao.findByProperties(conditionAndSet);
        if (users != null && users.size() != 0) {
            return users.get(0);
        }
        return null;
    }
}
