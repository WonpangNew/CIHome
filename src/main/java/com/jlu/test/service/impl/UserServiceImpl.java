package com.jlu.test.service.impl;

import com.jlu.common.db.sqlcondition.ConditionAndSet;
import com.jlu.test.bean.UserBean;
import com.jlu.test.dao.IUserDao;
import com.jlu.test.model.User;
import com.jlu.test.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Wonpang New on 2016/9/6.
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    IUserDao userDao;

    @Override
    public void saveUser(User user) {
        userDao.save(user);
    }

    @Override
    public UserBean getUserByName(String username) {
        ConditionAndSet conditionAndSet = new ConditionAndSet();
        conditionAndSet.put("username", username);
        List<User> users = userDao.findByProperties(conditionAndSet);
        return assemableUserBean(users.get(0));
    }

    private UserBean assemableUserBean(User user) {
        UserBean userBean = new UserBean();
        userBean.setUsername(user.getUsername());
        userBean.setTelePhone(user.getTelePhone());
        userBean.setId(user.getId());
        return userBean;
    }
}
