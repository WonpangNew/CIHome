package com.jlu.github.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jlu.branch.bean.BranchType;
import com.jlu.branch.model.CiHomeBranch;
import com.jlu.branch.service.IBranchService;
import com.jlu.common.utils.CiHomeReadConfig;
import com.jlu.common.utils.DateUtil;
import com.jlu.common.utils.HttpClientAuth;
import com.jlu.common.utils.HttpClientUtil;
import com.jlu.github.bean.GithubBranchBean;
import com.jlu.github.bean.GithubRepoBean;
import com.jlu.github.model.CiHomeModule;
import com.jlu.github.service.IGithubDataService;
import com.jlu.github.service.IModuleService;
import com.jlu.user.bean.UserBean;
import com.jlu.user.model.CiHomeUser;
import com.jlu.user.service.IUserService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by niuwanpeng on 17/3/24.
 */
@Service
public class GithubDataServiceImpl implements IGithubDataService {

    @Autowired
    private IModuleService moduleService;

    @Autowired
    private IBranchService branchService;

    @Autowired
    private IUserService userService;

    private static final Logger LOGGER = LoggerFactory.getLogger(GithubDataServiceImpl.class);
    private static final Gson GSON = new Gson();
    private final static String REGISTER_STATUS = "REGISTER_STATUS";
    private final static String MESSAGE = "MESSAGE";
    private final static String ADD_MODULE_STATUS = "ADD_MODULE_STATUS";

    /**
     * 根据用户注册信息初始化用户
     * @param userBean
     * @return
     */
    @Override
    public Map<String, Object> initUser(UserBean userBean) {
        Map<String, Object> result = new HashMap<>();
        result.put(REGISTER_STATUS, false);
        if (userBean == null) {
            result.put(MESSAGE, "输入信息有误，请重新注册！");
            LOGGER.error("注册失败！The message is userbean:{}", userBean);
        } else {
            try {
                if (userBean.isSyncGithub()) {
                    this.syncReposByUser(userBean.getUsername());
                    List<CiHomeModule> ciHomeModules = moduleService.getModulesByUsername(userBean.getUsername());
                    for (CiHomeModule ciHomeModule : ciHomeModules) {
                        this.creatHooks(userBean.getUsername(), ciHomeModule.getModule(), userBean.getGitHubToken());
                    }
                }
                result.put(REGISTER_STATUS, true);
                CiHomeUser ciHomeUser = this.assembleCiHomeUser(userBean);
                userService.saveUser(ciHomeUser);
            } catch (Exception e) {
                LOGGER.error("注册失败！The message is userbean:{}", userBean);
                result.put(MESSAGE, "不可预知错误，请重新注册！");
            }
        }
        return result;
    }

    /**
     * 根据用户名获得GitHub代码仓库信息并保存
     * @param username
     * @return
     */
    @Override
    public boolean syncReposByUser(String username) {
        String requestRepoUrl = String.format(CiHomeReadConfig.getConfigValueByKey("github.repos"), username);
        String result = HttpClientUtil.get(requestRepoUrl, null);
        List<GithubRepoBean> repoList = GSON.fromJson(result, new TypeToken<List<GithubRepoBean>>(){}.getType());
        List<CiHomeModule> ciHomeModules = this.saveCiHomeModuleByBean(repoList, username);
        for (CiHomeModule ciHomeModule : ciHomeModules) {
            this.initBranch(ciHomeModule, username);
        }
        return true;
    }

    /**
     * 为代码仓库创建hook
     * @param username
     * @param repo
     * @param githubToken
     * @return
     */
    @Override
    public Map<String, Object> creatHooks(String username, String repo, String githubToken) {
        Map<String, Object> result = new HashMap<>();
        String repoUrl
                = String.format(CiHomeReadConfig.getConfigValueByKey("github.all.hooks"), username, repo);
        String resultHook = HttpClientAuth.postForCreateHook(repoUrl, githubToken);
        return result;
    }

    /**
     * 增加新的模块
     * @param username
     * @param module
     * @return
     */
    @Override
    public String addModule(String username, String module) {
        module = StringUtils.substringBeforeLast(module, ".git");
        Map<String, String> result = new HashMap<>();
        result.put(ADD_MODULE_STATUS, "NO");
        CiHomeUser ciHomeUser = userService.getUserByName(username);
        if (ciHomeUser == null) {
            result.put(MESSAGE, "该用户不存在！请联系管理员！");
        } else {
            CiHomeModule module1 = moduleService.getModuleByUserAndModule(username, module);
            if (module1 != null) {
                result.put(MESSAGE, "该模块已存在，不需要再次配置！");
            } else {
                LOGGER.info("Start init module:{} on user:{}", module, username);
                CiHomeModule ciHomeModule = new CiHomeModule(module, username, DateUtil.getNowDateFormat());
                ciHomeModule.setVersion("1.0.0");
                moduleService.saveModule(ciHomeModule);
                try {
                    LOGGER.info("Start init branch on module:{}, user:{}", module, username);
                    this.initBranch(ciHomeModule, username);
                    LOGGER.info("Start create hook on module:{}, user:{}", module, username);
                    this.creatHooks(username, module, ciHomeUser.getGitHubToken());
                    LOGGER.info("Add module is successful! module:{}, user:{}", module, username);
                    result.put(ADD_MODULE_STATUS, "OK");
                    result.put(MESSAGE, "仓库" + module +"配置成功!");
                    result.put("MODULE", module);
                } catch (Exception e) {
                    moduleService.delete(ciHomeModule);
                    result.put(MESSAGE, "该仓库不存在!请检查是否配置正确。");
                }
            }
        }
        return GSON.toJson(result);
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
            ciHomeModule.setVersion("1.0.0");
            ciHomeModules.add(ciHomeModule);
        }
        moduleService.saveModules(ciHomeModules);
        return moduleService.getModulesByUsername(username);
    }

    /**
     * 初始化分支
     * @param ciHomeModule
     * @param username
     */
    private void initBranch(CiHomeModule ciHomeModule, String username) {
        String requestBranchUrl
                = String.format(CiHomeReadConfig.getConfigValueByKey("github.repo.branches"),
                username, ciHomeModule.getModule());
        String result = HttpClientUtil.get(requestBranchUrl, null);
        List<GithubBranchBean> branchBeans = GSON.fromJson(result, new TypeToken<List<GithubBranchBean>>(){}.getType());
        this.saveCiHomeBranchByBean(branchBeans, ciHomeModule);
    }

    /**
     * 保存分支数据
     * @param branchBeans
     * @param ciHomeModule
     */
    private void saveCiHomeBranchByBean(List<GithubBranchBean> branchBeans, CiHomeModule ciHomeModule) {
        List<CiHomeBranch> ciHomeBranches = new ArrayList<>();
        String version = "1.0.0";
        for (GithubBranchBean branchBean : branchBeans) {
            BranchType branchType = branchBean.getName().equals("master") ? BranchType.TRUNK : BranchType.BRANCH;
            CiHomeBranch ciHomeBranch
                    = new CiHomeBranch(ciHomeModule.getId(), branchBean.getName(), branchType,
                    this.getThreeVersion(branchType, version), DateUtil.getNowDateFormat());
            ciHomeBranches.add(ciHomeBranch);
        }
        branchService.saveBranches(ciHomeBranches);
    }

    /**
     * 根据Userbean装配CiHomeUser
     * @param userBean
     * @return
     */
    private CiHomeUser assembleCiHomeUser(UserBean userBean) {
        CiHomeUser ciHomeUser = new CiHomeUser();
        ciHomeUser.setUsername(userBean.getUsername());
        ciHomeUser.setPassword(userBean.getPassword());
        ciHomeUser.setUserEmail(userBean.getEmail());
        ciHomeUser.setCreateTime(DateUtil.getNowDateFormat());
        ciHomeUser.setGitHubToken(userBean.getGitHubToken());
        return ciHomeUser;
    }

    /**
     * 获得三位版本号
     * @param branchType
     * @param version
     * @return
     */
    private String getThreeVersion(BranchType branchType, String version) {
        if (branchType.equals(BranchType.TRUNK)) {
            return version;
        } else {
            String[] numbers = version.split("\\.");
            return (Integer.parseInt(numbers[0]) + 1) + ".0.0";
        }
    }
}
