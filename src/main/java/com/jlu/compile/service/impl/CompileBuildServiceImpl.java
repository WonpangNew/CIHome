package com.jlu.compile.service.impl;

import com.jlu.common.db.sqlcondition.ConditionAndSet;
import com.jlu.common.utils.CiHomeReadConfig;
import com.jlu.common.utils.DateUtil;
import com.jlu.common.utils.JenkinsUtils;
import com.jlu.compile.bean.BuildStatus;
import com.jlu.compile.dao.ICompileBuildDao;
import com.jlu.compile.model.CompileBuild;
import com.jlu.compile.service.ICompileBuildService;
import com.jlu.github.bean.GitHubCommitBean;
import com.jlu.github.bean.HookRepositoryBean;
import com.jlu.jenkins.bean.JenkinsEndCompileBean;
import com.jlu.jenkins.bean.JenkinsStartCompileBean;
import com.offbytwo.jenkins.model.BuildResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by niuwanpeng on 17/4/15.
 *
 * 编译逻辑处理
 */
@Service
public class CompileBuildServiceImpl implements ICompileBuildService {

    @Autowired
    private ICompileBuildDao compileBuildDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(CompileBuildServiceImpl.class);

    /**
     * 接收到hook信息触发编译
     * @return
     */
    public void hookTriggerCompile(GitHubCommitBean commitBean, HookRepositoryBean repositoryBean) {
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
    public void dealCompileEnd(JenkinsEndCompileBean jenkinsEndCompileBean) {
        CompileBuild compileBuild = compileBuildDao.findById(jenkinsEndCompileBean.getCompileBuildId());
        if (compileBuild == null) {
            LOGGER.info("The compile is not exist! compileBuildId:{}", jenkinsEndCompileBean.getCompileBuildId());
            return;
        }
        BuildStatus buildStatus = judgeBuildResult(jenkinsEndCompileBean);
        compileBuild.setBuildStatus(buildStatus);
        compileBuild.setJenkinsBuildNumber(Integer.valueOf(jenkinsEndCompileBean.getBuildNumber()));
        compileBuild.setProductPath(jenkinsEndCompileBean.getProductPath());
        compileBuild.setEndTime(DateUtil.getNowDateFormat());
        compileBuild.setJeniknsBuildId(jenkinsEndCompileBean.getJenkinsBuildId());
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
        compileBuild.setCommitter(commitBean.getCommitter().getName());
        compileBuild.setCommitterEmail(commitBean.getCommitter().getEmail());
        compileBuild.setCreateTime(DateUtil.getNowDateFormat());
        compileBuildDao.save(compileBuild);
        LOGGER.info("Init CompileBuild is successful! compileBuild:{}", compileBuild.toString());
        return compileBuild;
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

}
