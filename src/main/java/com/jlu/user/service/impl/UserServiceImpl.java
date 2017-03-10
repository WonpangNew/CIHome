package com.jlu.user.service.impl;

import com.jlu.user.dao.IUserDao;
import com.jlu.user.model.CiHomeUser;
import com.jlu.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by niuwanpeng on 17/3/10.
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    IUserDao userDao;

    @Override
    public void saveUser(CiHomeUser ciHomeUser) {
        userDao.save(ciHomeUser);
    }
}
