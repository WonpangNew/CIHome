package com.jlu.user.service.impl;

import com.jlu.common.db.sqlcondition.ConditionAndSet;
import com.jlu.user.dao.IUserDao;
import com.jlu.user.model.CiHomeUser;
import com.jlu.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by niuwanpeng on 17/3/10.
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    IUserDao userDao;

    public void saveUser(CiHomeUser ciHomeUser) {
        userDao.save(ciHomeUser);
    }

    /**
     * 通过用户民获得密码
     * @param username
     * @return
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
