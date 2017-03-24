package com.jlu.github.service;

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
}
