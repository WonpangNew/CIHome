package com.jlu.github.service;

import com.jlu.user.bean.UserBean;

import java.util.Map;

/**
 * Created by niuwanpeng on 17/3/24.
 *
 * 同 github 交互服务
 */
public interface IGithubDataService {

    /**
     * 根据用户名获得GitHub代码仓库信息并保存
     * @param username
     * @return
     */
    boolean syncReposByUser(String username);

    /**
     * 根据用户注册信息初始化用户
     * @param userBean
     * @return
     */
    Map<String, Object> initUser(UserBean userBean);

    /**
     * 为代码仓库创建hook
     * @param username
     * @param repo
     * @param githubPassword
     * @return
     */
    Map<String, Object> creatHooks(String username, String repo, String githubPassword);

    /**
     * 增加新的模块
     * @param username
     * @param module
     * @return
     */
    String addModule(String username, String module);
}
