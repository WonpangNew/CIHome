package com.jlu.test.service;

import com.jlu.test.bean.UserBean;
import com.jlu.test.model.User;

/**
 * Created by Wonpang New on 2016/9/6.
 */
public interface IUserService {

    void saveUser(User user);

    UserBean getUserByName(String username);
}
