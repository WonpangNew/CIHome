package com.jlu.github.service.impl;

import com.jlu.common.utils.CiHomeReadConfig;
import com.jlu.common.utils.HttpClientUtil;
import com.jlu.github.service.IGithubDataService;
import org.springframework.stereotype.Service;

/**
 * Created by niuwanpeng on 17/3/24.
 */
@Service
public class GithubDataServiceImpl implements IGithubDataService {

    /**
     * 根据用户名获得GitHub代码仓库信息并保存
     * @param username
     * @return
     */
    public boolean syncReposByUser(String username) {
        String requestUrl = String.format(CiHomeReadConfig.getConfigValueByKey("github.repos"), username);
        String result = HttpClientUtil.get(requestUrl, null);
        return true;
    }
}
