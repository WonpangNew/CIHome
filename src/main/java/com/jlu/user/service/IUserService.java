package com.jlu.user.service;

import com.jlu.user.model.CiHomeUser;

/**
 * Created by niuwanpeng on 17/3/10.
 */
public interface IUserService {

    void saveUser(CiHomeUser ciHomeUser);

    /**
     * 通过用户民获得密码
     * @param username
     * @return
     */
    CiHomeUser getUserByName(String username);
}
