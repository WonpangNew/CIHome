package com.jlu.github.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jlu.branch.bean.BranchType;
import com.jlu.common.utils.DateUtil;
import com.jlu.compile.service.ICompileBuildService;
import com.jlu.github.bean.GitHubCommitBean;
import com.jlu.github.bean.HookRepositoryBean;
import com.jlu.github.model.CiHomeModule;
import com.jlu.github.service.IGitHubHookService;
import com.jlu.github.service.IModuleService;
import com.jlu.pipeline.model.PipelineBuild;
import com.jlu.pipeline.service.IPipelineBuildService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by niuwanpeng on 17/4/19.
 */
@Service
public class GitHubHookServiceImpl implements IGitHubHookService{

    @Autowired
    private ICompileBuildService compileBuildService;

    @Autowired
    private IPipelineBuildService pipelineBuildService;

    @Autowired
    private IModuleService moduleService;

    private static final Logger LOGGER = LoggerFactory.getLogger(GitHubHookServiceImpl.class);

    private static final Gson GSON = new Gson();

    /**
     * 解析hook信息，触发编译
     * @param hookMessage
     */
    @Override
    public void dealHookMessage(JSONObject hookMessage) {
        GitHubCommitBean commitBean = null;
        HookRepositoryBean repositoryBean = null;
        try {
            String branchName = StringUtils.substringAfterLast(hookMessage.getString("ref"),"refs/heads/");
            BranchType branchType = branchName.equals("master") ? BranchType.TRUNK : BranchType.BRANCH;
            repositoryBean = GSON.fromJson(hookMessage.getString("repository"),
                    new TypeToken<HookRepositoryBean>(){}.getType());
            CiHomeModule ciHomeModule = moduleService.getModuleByUserAndModule(repositoryBean.getOwner().getName(),
                    repositoryBean.getName());
            if (ciHomeModule == null) {
                LOGGER.info("This module is not exist and ignore compile!user:{}, module:{}",
                        repositoryBean.getOwner().getName(), repositoryBean.getName());
            }
            PipelineBuild pipelineBuild = initPipelineBuild(ciHomeModule, branchName, branchType);
            commitBean = getCommitByHook(hookMessage, pipelineBuild);
            LOGGER.info("解析Json数据成功！commits:{}", commitBean.toString());
        } catch (Exception e) {
            LOGGER.error("解析Json数据失败！hookMessage:{}", hookMessage.toString());
        }
        compileBuildService.hookTriggerCompile(commitBean, repositoryBean);
    }

    /**
     * 解析json数据，获得commit bean
     * @param hookMessage
     * @param pipelineBuild
     * @return
     * @throws Exception
     */
    private GitHubCommitBean getCommitByHook(JSONObject hookMessage, PipelineBuild pipelineBuild) throws Exception {
        GitHubCommitBean commitBean = new GitHubCommitBean();
        JSONArray commitsArray = hookMessage.getJSONArray("commits");
        String commits = commitsArray.getString(0);
        commitBean = GSON.fromJson(commits, new TypeToken<GitHubCommitBean>(){}.getType());
        commitBean.setRef(pipelineBuild.getBranchName());
        commitBean.setBranchType(pipelineBuild.getBranchType());
        commitBean.setModuleId(pipelineBuild.getModuleId());
        commitBean.setPipelineBuildId(pipelineBuild.getId());
        return commitBean;
    }

    /**
     * 初始化pipelineBuild
     * @return
     */
    private PipelineBuild initPipelineBuild(CiHomeModule ciHomeModule, String branchName, BranchType branchType) {
        PipelineBuild pipelineBuild = new PipelineBuild();
        pipelineBuild.setModuleId(ciHomeModule.getId());
        pipelineBuild.setBranchName(branchName);
        pipelineBuild.setCreateTime(DateUtil.getNowDateFormat());
        pipelineBuild.setBranchType(branchType);
        pipelineBuildService.save(pipelineBuild);
        return pipelineBuild;
    }
}
