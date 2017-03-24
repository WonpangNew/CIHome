package com.jlu.github.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jlu.branch.bean.BranchType;
import com.jlu.branch.model.CiHomeBranch;
import com.jlu.branch.service.IBranchService;
import com.jlu.common.utils.CiHomeReadConfig;
import com.jlu.common.utils.DateUtil;
import com.jlu.common.utils.HttpClientUtil;
import com.jlu.github.bean.GithubBranchBean;
import com.jlu.github.bean.GithubRepoBean;
import com.jlu.github.model.CiHomeModule;
import com.jlu.github.service.IGithubDataService;
import com.jlu.github.service.IModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by niuwanpeng on 17/3/24.
 */
@Service
public class GithubDataServiceImpl implements IGithubDataService {

    @Autowired
    private IModuleService moduleService;

    @Autowired
    private IBranchService branchService;

    private static final Gson GSON = new Gson();

    /**
     * 根据用户名获得GitHub代码仓库信息并保存
     * @param username
     * @return
     */
    public boolean syncReposByUser(String username) {
        String requestRepoUrl = String.format(CiHomeReadConfig.getConfigValueByKey("github.repos"), username);
        String result = HttpClientUtil.get(requestRepoUrl, null);
        List<GithubRepoBean> repoList = GSON.fromJson(result, new TypeToken<List<GithubRepoBean>>(){}.getType());
        List<CiHomeModule> ciHomeModules = this.saveCiHomeModuleByBean(repoList, username);
        for (CiHomeModule ciHomeModule : ciHomeModules) {
            String requestBranchUrl
                    = String.format(CiHomeReadConfig.getConfigValueByKey("github.repo.branches"),
                    username, ciHomeModule.getModule());
            result = HttpClientUtil.get(requestBranchUrl, null);
            List<GithubBranchBean> branchBeans = GSON.fromJson(result, new TypeToken<List<GithubBranchBean>>(){}.getType());
            this.saveCiHomeBranchByBean(branchBeans, ciHomeModule);
        }
        return true;
    }

    /**
     *  保存模块数据
     * @param repoList
     * @param username
     */
    private List<CiHomeModule> saveCiHomeModuleByBean(List<GithubRepoBean> repoList, String username) {
        List<CiHomeModule> ciHomeModules = new ArrayList<>();
        for (GithubRepoBean githubRepoBean : repoList) {
            CiHomeModule ciHomeModule = new CiHomeModule(githubRepoBean.getName(), username, DateUtil.getNowDateFormat());
            ciHomeModules.add(ciHomeModule);
        }
        moduleService.saveModules(ciHomeModules);
        return moduleService.getModulesByUsername(username);
    }

    /**
     * 保存分支数据
     * @param branchBeans
     * @param ciHomeModule
     */
    private void saveCiHomeBranchByBean(List<GithubBranchBean> branchBeans, CiHomeModule ciHomeModule) {
        List<CiHomeBranch> ciHomeBranches = new ArrayList<>();
        for (GithubBranchBean branchBean : branchBeans) {
            BranchType branchType = branchBean.getName().equals("master") ? BranchType.TRUNK : BranchType.BRANCH;
            CiHomeBranch ciHomeBranch
                    = new CiHomeBranch(ciHomeModule.getId(), branchBean.getName(), branchType, DateUtil.getNowDateFormat());
            ciHomeBranches.add(ciHomeBranch);
        }
        branchService.saveBranches(ciHomeBranches);
    }
}
