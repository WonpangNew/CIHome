package com.jlu.compile.service.impl;

import com.jlu.common.db.sqlcondition.ConditionAndSet;
import com.jlu.common.db.sqlcondition.DescOrder;
import com.jlu.common.db.sqlcondition.OrderCondition;
import com.jlu.common.utils.CiHomeReadConfig;
import com.jlu.common.utils.DateUtil;
import com.jlu.common.utils.JenkinsUtils;
import com.jlu.compile.bean.BuildStatus;
import com.jlu.compile.dao.ICompileBuildDao;
import com.jlu.compile.model.CompileBuild;
import com.jlu.compile.service.ICompileBuildService;
import com.jlu.github.bean.GitHubCommitBean;
import com.jlu.github.bean.HookRepositoryBean;
import com.jlu.github.model.GitHubCommit;
import com.jlu.github.service.IGitHubCommitService;
import com.jlu.jenkins.bean.JenkinsEndCompileBean;
import com.jlu.jenkins.bean.JenkinsStartCompileBean;
import com.offbytwo.jenkins.model.BuildResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by niuwanpeng on 17/4/15.
 *
 * 编译逻辑处理
 */
@Service
public class CompileBuildServiceImpl implements ICompileBuildService {

    @Autowired
    private ICompileBuildDao compileBuildDao;

    @Autowired
    private IGitHubCommitService gitHubCommitService;

    private static final Logger LOGGER = LoggerFactory.getLogger(CompileBuildServiceImpl.class);

    /**
     * 接收到hook信息触发编译
     * @return
     */
    @Override
    public void hookTriggerCompile(GitHubCommitBean commitBean, HookRepositoryBean repositoryBean) {
        assembleGitHubCommit(commitBean);
        CompileBuild compileBuild = assembleCompileBuild(commitBean);
        // 开始编译
        String repoUrl = String.format(CiHomeReadConfig.getConfigValueByKey("github.base.repo"),
                repositoryBean.getOwner().getName(), repositoryBean.getName());
        JenkinsStartCompileBean jenkinsStartCompileBean
                = JenkinsUtils.triggerCompile(repoUrl, repositoryBean.getName(), compileBuild.getId());
        if (!jenkinsStartCompileBean.isRequestStatus()) {
            compileBuild.setBuildStatus(BuildStatus.FAIL);
            compileBuild.setJenkinsBuildNumber(jenkinsStartCompileBean.getBuildNumber());
            compileBuildDao.update(compileBuild);
            LOGGER.info("Request jenkins build is fail! compileBuildId:{}", compileBuild.getId());
        } else {
            LOGGER.info("Request jenkins build is successful! Starting building!! compileBuildId:{}",
                    compileBuild.getId());
        }
    }

    /**
     * 接收到编译结束消息，进行写库等操作
     * @return
     */
    @Override
    public void dealCompileEnd(JenkinsEndCompileBean jenkinsEndCompileBean) {
        CompileBuild compileBuild = compileBuildDao.findById(Integer.valueOf(jenkinsEndCompileBean.getCompileBuildId()));
        if (compileBuild == null) {
            LOGGER.info("The compile is not exist! compileBuildId:{}", jenkinsEndCompileBean.getCompileBuildId());
            return;
        }
        BuildStatus buildStatus = judgeBuildResult(jenkinsEndCompileBean);
        compileBuild.setBuildStatus(buildStatus);
        compileBuild.setJenkinsBuildNumber(Integer.valueOf(jenkinsEndCompileBean.getBuildNumber()));
        compileBuild.setProductPath(jenkinsEndCompileBean.getProductPath());
        compileBuild.setEndTime(DateUtil.getNowDateFormat());
        compileBuild.setJenkinsBuildId(jenkinsEndCompileBean.getJenkinsBuildId());
        String buildLogUrl = String.format(CiHomeReadConfig.getConfigValueByKey("jenkins.build.log"),
                compileBuild.getJenkinsBuildNumber());
        compileBuild.setBuildLogUrl(buildLogUrl);
        compileBuildDao.update(compileBuild);
        LOGGER.info("Compile is end! compileBuildId:{}", compileBuild.getId());
    }

    /**
     * 根据commit信息组装compileBuild并写库
     * @param commitBean
     * @return
     */
    private CompileBuild assembleCompileBuild(GitHubCommitBean commitBean) {
        CompileBuild compileBuild = new CompileBuild();
        compileBuild.setBranchType(commitBean.getBranchType());
        compileBuild.setPipelineBuildId(commitBean.getPipelineBuildId());
        compileBuild.setModuleId(commitBean.getModuleId());
        compileBuild.setBranchName(commitBean.getRef());
        compileBuild.setBuildStatus(BuildStatus.BUILDING);
        compileBuild.setCommits(commitBean.getMessage());
        compileBuild.setTrigger(commitBean.getCommitter().getName());
        compileBuild.setTriggerEmail(commitBean.getCommitter().getEmail());
        compileBuild.setCreateTime(DateUtil.getNowDateFormat());
        compileBuild.setRevision(commitBean.getId());
        compileBuildDao.save(compileBuild);
        LOGGER.info("Init CompileBuild is successful! compileBuild:{}", compileBuild.toString());
        return compileBuild;
    }

    /**
     * 根据commit信息组装代码提交信息并写库
     * @param commitBean
     * @return
     */
    private GitHubCommit assembleGitHubCommit(GitHubCommitBean commitBean) {
        GitHubCommit gitHubCommit
                = new GitHubCommit(commitBean.getCommitter().getName(), commitBean.getCommitter().getEmail(),
                commitBean.getMessage(), DateUtil.getNowDateFormat(), commitBean.getAdded().toString(),
                commitBean.getRemoved().toString(), commitBean.getModified().toString(), commitBean.getId(),
                commitBean.getUrl());
        gitHubCommitService.save(gitHubCommit);
        LOGGER.info("Init GitHubCommit is successful! compileBuild:{}", gitHubCommit.getId());
        return gitHubCommit;
    }

    /**
     * 判断本次编译结果
     * 两个判断条件：1.通过编译服务返回的信息 2.通过调取jenkins接口，只有满足两个条件则认为编译成功
     * @param jenkinsEndCompileBean
     * @return
     */
    private BuildStatus judgeBuildResult(JenkinsEndCompileBean jenkinsEndCompileBean) {
        BuildResult buildResult =
                JenkinsUtils.getBuildStatusByNumber(Integer.valueOf(jenkinsEndCompileBean.getBuildNumber()));
        String jenkinsEndCompileResult = jenkinsEndCompileBean.getBuildStatus();

        if (buildResult.equals(BuildResult.SUCCESS) && jenkinsEndCompileResult.equals("SUCC")) {
            LOGGER.info("Compile is successful! compileBuildResult:{}", jenkinsEndCompileBean.toString());
            return BuildStatus.SUCCESS;
        } else {
            LOGGER.error("Compile is fail! buildResult:{}, compileBuildResult:{}",
                    buildResult.toString(), jenkinsEndCompileBean.toString());
            return BuildStatus.FAIL;
        }
    }

    /**
     * 根据pipelineBuildId获得编译信息
     * @param pipelineBuildId
     * @return
     */
    @Override
    public CompileBuild getCompileBuildByPipelineId(int pipelineBuildId) {
        ConditionAndSet conditionAndSet = new ConditionAndSet();
        conditionAndSet.put("pipelineBuildId", pipelineBuildId);
        List<OrderCondition> orders = new ArrayList<OrderCondition>();
        orders.add(new DescOrder("id"));
        List<CompileBuild> compileBuilds = compileBuildDao.findByProperties(conditionAndSet, orders);
        return  (null == compileBuilds || compileBuilds.size() == 0) ? null : compileBuilds.get(0);
    }

}
