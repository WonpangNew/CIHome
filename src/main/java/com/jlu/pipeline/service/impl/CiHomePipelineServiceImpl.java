package com.jlu.pipeline.service.impl;

import com.jlu.branch.bean.BranchType;
import com.jlu.compile.bean.CompileBuildBean;
import com.jlu.compile.model.CompileBuild;
import com.jlu.compile.service.ICompileBuildService;
import com.jlu.github.model.CiHomeModule;
import com.jlu.github.model.GitHubCommit;
import com.jlu.github.service.IGitHubCommitService;
import com.jlu.github.service.IModuleService;
import com.jlu.pipeline.bean.CihomePipelineBean;
import com.jlu.pipeline.model.PipelineBuild;
import com.jlu.pipeline.service.ICiHomePipelineService;
import com.jlu.pipeline.service.IPipelineBuildService;
import com.jlu.release.bean.ReleaseBean;
import com.jlu.release.bean.ReleaseStatus;
import com.jlu.release.model.CiHomeRelease;
import com.jlu.release.service.IReleaseService;
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
 * Created by niuwanpeng on 17/4/26.
 */
@Service
public class CiHomePipelineServiceImpl implements ICiHomePipelineService {

    @Autowired
    private IModuleService moduleService;

    @Autowired
    private IPipelineBuildService pipelineBuildService;

    @Autowired
    private ICompileBuildService compileBuildService;

    @Autowired
    private IGitHubCommitService gitHubCommitService;

    @Autowired
    private IReleaseService releaseService;

    private static final Logger LOGGER = LoggerFactory.getLogger(CiHomePipelineServiceImpl.class);

    /**
     * 获取主干流水线的构建记录
     * @param username
     * @param module
     * @param pipelineBuildId
     * @return
     */
    public List<CihomePipelineBean> getTrunkPipeline(String username, String module, int pipelineBuildId) {
        if (StringUtils.isEmpty(username) && StringUtils.isEmpty(module)) {
            return null;
        }
        CiHomeModule ciHomeModule = moduleService.getModuleByUserAndModule(username, module);
        if (ciHomeModule == null) {
            LOGGER.error("Don't exist this module:{} on username:{}!", module, username);
        }
        List<PipelineBuild> pipelineBuilds =
                getPipelineBuilds(ciHomeModule, BranchType.TRUNK, null, 0, 10);
        Map<Integer, CompileBuildBean> compileBuildBeanMap = getCompileBuildBeans(pipelineBuilds);
        Map<Integer, GitHubCommit> gitHubCommitMap = getGitHubCommit(pipelineBuilds);
        Map<Integer, ReleaseBean> releaseBeanMap = getReleaseBeans(pipelineBuilds);
        return assembleCihomePipeline(ciHomeModule, pipelineBuilds, compileBuildBeanMap, gitHubCommitMap, releaseBeanMap);
    }

    /**
     * 组装pipeline信息返回给前端
     * @param ciHomeModule
     * @param pipelineBuilds
     * @param compileBuildBeanMap
     * @param gitHubCommitMap
     * @param releaseBeanMap
     * @return
     */
    private List<CihomePipelineBean> assembleCihomePipeline(CiHomeModule ciHomeModule, List<PipelineBuild> pipelineBuilds,
                                              Map<Integer, CompileBuildBean> compileBuildBeanMap,
                                              Map<Integer, GitHubCommit> gitHubCommitMap,
                                              Map<Integer, ReleaseBean> releaseBeanMap) {
        // TODO: 17/4/26 组装信息
        List<CihomePipelineBean> cihomePipelineBeanList = new ArrayList<>();
        return cihomePipelineBeanList;
    }

    /**
     * 根据以下信息获得分支或主干下的pipelineBuild记录
     * @param ciHomeModule
     * @param branchType
     * @param branchName
     * @param pipelineBuildId
     * @param limit
     * @return
     */
    private List<PipelineBuild> getPipelineBuilds(CiHomeModule ciHomeModule, BranchType branchType,
                                                  String branchName, int pipelineBuildId, int limit) {
        List<PipelineBuild> pipelineBuilds = null;
        if (BranchType.TRUNK.equals(branchType)) {
            pipelineBuilds = pipelineBuildService.getTrunkPipelineBuilds(ciHomeModule.getId(), pipelineBuildId, limit);
        } else {
            if (StringUtils.isEmpty(branchName)) {
                // TODO: 17/4/26 所有分支构建记录
            } else {
                // TODO: 17/4/26 单个分支构建记录
            }
        }
        return pipelineBuilds;
    }

    /**
     * 根据pipelineBuildId集合获得编译记录
     * @param pipelineBuilds
     * @return
     */
    private Map<Integer, CompileBuildBean> getCompileBuildBeans(List<PipelineBuild> pipelineBuilds) {
        Map<Integer, CompileBuildBean> compileBuildBeanMap = new HashMap<>();
        for (PipelineBuild pipelineBuild : pipelineBuilds) {
            CompileBuild compileBuild = compileBuildService.getCompileBuildByPipelineId(pipelineBuild.getId());
            if (compileBuild == null) {
                continue;
            }
            CompileBuildBean compileBuildBean =
                    new CompileBuildBean(compileBuild.getCreateTime(), compileBuild.getBuildStatus());
            compileBuildBeanMap.put(pipelineBuild.getId(), compileBuildBean);
        }
        return compileBuildBeanMap;
    }

    /**
     * 根据pipelineBuildId集合获得代码提交记录
     * @param pipelineBuilds
     * @return
     */
    private Map<Integer, GitHubCommit> getGitHubCommit(List<PipelineBuild> pipelineBuilds) {
        Map<Integer, GitHubCommit> gitHubCommitMap = new HashMap<>();
        for (PipelineBuild pipelineBuild : pipelineBuilds) {
            GitHubCommit gitHubCommit = gitHubCommitService.getGithubCommitByPipelineId(pipelineBuild.getId());
            if (gitHubCommit == null) {
                continue;
            }
            gitHubCommitMap.put(pipelineBuild.getId(), gitHubCommit);
         }
        return gitHubCommitMap;
    }

    /**
     * 根据pipelineBuildId集合获得发布信息
     * @param pipelineBuilds
     * @return
     */
    private Map<Integer, ReleaseBean> getReleaseBeans(List<PipelineBuild> pipelineBuilds) {
        Map<Integer, ReleaseBean> releaseBeanMap = new HashMap<>();
        for (PipelineBuild pipelineBuild : pipelineBuilds) {
            CiHomeRelease ciHomeRelease = releaseService.getReleaseByPipelineId(pipelineBuild.getId());
            ReleaseBean releaseBean = new ReleaseBean();
            if (ciHomeRelease == null) {
                releaseBean.setReleaseStatus(ReleaseStatus.WAIT);
                releaseBean.setVersion("--");
            } else {
                releaseBean.setReleaseStatus(ciHomeRelease.getReleaseStatus());
                if (releaseBean.getReleaseStatus().equals(ReleaseStatus.WAIT)) {
                    releaseBean.setVersion("--");
                } else {
                    releaseBean.setVersion(ciHomeRelease.getVersion());
                }
            }
            releaseBeanMap.put(pipelineBuild.getId(), releaseBean);
        }
        return releaseBeanMap;
    }
}
