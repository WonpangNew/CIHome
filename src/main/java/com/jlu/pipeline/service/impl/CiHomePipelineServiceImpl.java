package com.jlu.pipeline.service.impl;

import com.jlu.branch.bean.BranchType;
import com.jlu.branch.model.CiHomeBranch;
import com.jlu.branch.service.IBranchService;
import com.jlu.compile.bean.BuildStatus;
import com.jlu.compile.bean.CompileBuildBean;
import com.jlu.compile.model.CompileBuild;
import com.jlu.compile.service.ICompileBuildService;
import com.jlu.github.model.CiHomeModule;
import com.jlu.github.model.GitHubCommit;
import com.jlu.github.service.IGitHubCommitService;
import com.jlu.github.service.IModuleService;
import com.jlu.pipeline.bean.BranchesPipelineBean;
import com.jlu.pipeline.bean.CiHomePipelineBean;
import com.jlu.pipeline.model.PipelineBuild;
import com.jlu.pipeline.service.ICiHomePipelineService;
import com.jlu.pipeline.service.IPipelineBuildService;
import com.jlu.release.bean.ReleaseBean;
import com.jlu.release.bean.ReleaseStatus;
import com.jlu.release.model.CiHomeRelease;
import com.jlu.release.service.IReleaseService;
import org.apache.commons.collections.map.ListOrderedMap;
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

    @Autowired
    private IBranchService branchService;

    private static final Logger LOGGER = LoggerFactory.getLogger(CiHomePipelineServiceImpl.class);

    /**
     * 获取主干流水线的构建记录
     * @param username
     * @param module
     * @param pipelineBuildId
     * @return
     */
    @Override
    public List<CiHomePipelineBean> getTrunkPipeline(String username, String module, int pipelineBuildId) {
        if (StringUtils.isEmpty(username) && StringUtils.isEmpty(module)) {
            return null;
        }
        CiHomeModule ciHomeModule = moduleService.getModuleByUserAndModule(username, module);
        if (ciHomeModule == null) {
            LOGGER.error("Don't exist this module:{} on username:{}!", module, username);
        }
        List<PipelineBuild> pipelineBuilds =
                getPipelineBuilds(ciHomeModule, BranchType.TRUNK, null, pipelineBuildId, 10);
        Map<Integer, CompileBuildBean> compileBuildBeanMap = getCompileBuildBeans(pipelineBuilds);
        Map<Integer, GitHubCommit> gitHubCommitMap = getGitHubCommit(pipelineBuilds);
        Map<Integer, ReleaseBean> releaseBeanMap = getReleaseBeans(pipelineBuilds);
        return assembleCiHomePipeline(ciHomeModule, pipelineBuilds, compileBuildBeanMap, gitHubCommitMap, releaseBeanMap);
    }

    /**
     * 获取单个分支流水线的构建记录
     * @param username
     * @param module
     * @param branchName
     * @param pipelineBuildId
     * @return
     */
    public List<CiHomePipelineBean> getBranchPipeline(String username, String module, String branchName,
                                                      int pipelineBuildId) {
        if (StringUtils.isEmpty(username) && StringUtils.isEmpty(module) && StringUtils.isEmpty(branchName)) {
            return null;
        }
        CiHomeModule ciHomeModule = moduleService.getModuleByUserAndModule(username, module);
        if (ciHomeModule == null) {
            LOGGER.error("Don't exist this module:{} on username:{}!", module, username);
        }
        List<PipelineBuild> pipelineBuilds =
                getPipelineBuilds(ciHomeModule, BranchType.BRANCH, branchName, pipelineBuildId, 10);
        Map<Integer, CompileBuildBean> compileBuildBeanMap = getCompileBuildBeans(pipelineBuilds);
        Map<Integer, GitHubCommit> gitHubCommitMap = getGitHubCommit(pipelineBuilds);
        Map<Integer, ReleaseBean> releaseBeanMap = getReleaseBeans(pipelineBuilds);
        return assembleCiHomePipeline(ciHomeModule, pipelineBuilds, compileBuildBeanMap, gitHubCommitMap, releaseBeanMap);
    }

    /**
     * 获取全部分支流水线构建记录
     * @param username
     * @param module
     * @param branchId
     * @return
     */
    public ListOrderedMap getBranchesPipeline(String username, String module, int branchId) {
        if (StringUtils.isEmpty(username) && StringUtils.isEmpty(module)) {
            return null;
        }
        CiHomeModule ciHomeModule = moduleService.getModuleByUserAndModule(username, module);
        if (ciHomeModule == null) {
            LOGGER.error("Don't exist this module:{} on username:{}!", module, username);
            return null;
        }
        ListOrderedMap cihomePipelines = new ListOrderedMap();
        List<CiHomeBranch> ciHomeBranches = branchService.getBranches(ciHomeModule, 0, 10);
        for (CiHomeBranch ciHomeBranch : ciHomeBranches) {
            List<PipelineBuild> pipelineBuilds =
                    getPipelineBuilds(ciHomeModule, BranchType.BRANCH, ciHomeBranch.getBranchName(), branchId, 1);
            if (pipelineBuilds.size() == 0) {
                continue;
            }
            Map<Integer, CompileBuildBean> compileBuildBeanMap = getCompileBuildBeans(pipelineBuilds);
            Map<Integer, GitHubCommit> gitHubCommitMap = getGitHubCommit(pipelineBuilds);
            Map<Integer, ReleaseBean> releaseBeanMap = getReleaseBeans(pipelineBuilds);
            List<CiHomePipelineBean> ciHomePipelineBeans = assembleCiHomePipeline(ciHomeModule, pipelineBuilds,
                    compileBuildBeanMap, gitHubCommitMap, releaseBeanMap);
            cihomePipelines.put(ciHomeBranch.getBranchName(), ciHomePipelineBeans);
        }
        return cihomePipelines;
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
    private List<CiHomePipelineBean> assembleCiHomePipeline(CiHomeModule ciHomeModule, List<PipelineBuild> pipelineBuilds,
                                                            Map<Integer, CompileBuildBean> compileBuildBeanMap,
                                                            Map<Integer, GitHubCommit> gitHubCommitMap,
                                                            Map<Integer, ReleaseBean> releaseBeanMap) {
        // TODO: 17/4/26 组装信息
        List<CiHomePipelineBean> ciHomePipelineBeanList = new ArrayList<>();
        for (PipelineBuild pipelineBuild : pipelineBuilds) {
            CiHomePipelineBean ciHomePipelineBean = this.assemblePipelineBean(pipelineBuild, ciHomeModule,
                    compileBuildBeanMap, gitHubCommitMap, releaseBeanMap);
            ciHomePipelineBeanList.add(ciHomePipelineBean);
        }
        return ciHomePipelineBeanList;
    }

    /**
     * 组装总体分支流水线返回给前端
     * @param ciHomeModule
     * @param pipelineBuilds
     * @param compileBuildBeanMap
     * @param gitHubCommitMap
     * @param releaseBeanMap
     * @return
     */
    private List<BranchesPipelineBean> assembleBranchesPipeline(CiHomeModule ciHomeModule, List<PipelineBuild> pipelineBuilds,
                                                          Map<Integer, CompileBuildBean> compileBuildBeanMap,
                                                          Map<Integer, GitHubCommit> gitHubCommitMap,
                                                          Map<Integer, ReleaseBean> releaseBeanMap) {
        List<BranchesPipelineBean> branchesPipelineBeans = new ArrayList<>();
        for (PipelineBuild pipelineBuild : pipelineBuilds) {
            CiHomePipelineBean ciHomePipelineBean = this.assemblePipelineBean(pipelineBuild, ciHomeModule,
                    compileBuildBeanMap, gitHubCommitMap, releaseBeanMap);
            BranchesPipelineBean branchesPipelineBean = new BranchesPipelineBean();
            branchesPipelineBean.setBranchId(pipelineBuild.getBranchId());
            branchesPipelineBean.setBranchName(pipelineBuild.getBranchName());
            branchesPipelineBean.setCiHomePipelineBean(ciHomePipelineBean);
            branchesPipelineBeans.add(branchesPipelineBean);
        }
        return branchesPipelineBeans;
    }

    /**
     * 拼装CiHomePipelineBean
     * @param pipelineBuild
     * @param ciHomeModule
     * @param compileBuildBeanMap
     * @param gitHubCommitMap
     * @param releaseBeanMap
     * @return
     */
    private CiHomePipelineBean assemblePipelineBean(PipelineBuild pipelineBuild, CiHomeModule ciHomeModule,
                                                    Map<Integer, CompileBuildBean> compileBuildBeanMap,
                                                    Map<Integer, GitHubCommit> gitHubCommitMap,
                                                    Map<Integer, ReleaseBean> releaseBeanMap) {
        int pipelineBuildId = pipelineBuild.getId();
        CiHomePipelineBean ciHomePipelineBean = new CiHomePipelineBean();
        ciHomePipelineBean.setBranchType(pipelineBuild.getBranchType());
        ciHomePipelineBean.setBranchName(pipelineBuild.getBranchName());
        ciHomePipelineBean.setBranchId(pipelineBuild.getBranchId());
        ciHomePipelineBean.setPipelineBuildId(pipelineBuildId);
        ciHomePipelineBean.setModule(ciHomeModule.getModule());
        ciHomePipelineBean.setModuleId(ciHomeModule.getId());
        ciHomePipelineBean.setCompileBuildBean(compileBuildBeanMap.get(pipelineBuildId));
        ciHomePipelineBean.setGitHubCommit(gitHubCommitMap.get(pipelineBuildId));
        ciHomePipelineBean.setReleaseBean(releaseBeanMap.get(pipelineBuildId));
        ciHomePipelineBean.setRevision(gitHubCommitMap.get(pipelineBuildId).getRevision());
        ciHomePipelineBean.setBuildNumber(compileBuildBeanMap.get(pipelineBuildId).getBuildNumber());
        ciHomePipelineBean.setPipelineStatus(getPipelineStatus(compileBuildBeanMap.get(pipelineBuildId).getBuildStatus(),
                releaseBeanMap.get(pipelineBuildId).getReleaseStatus()));
        return ciHomePipelineBean;
    }

    /**
     * 根据编译状态和发布状态获得流水线状态
     * @param compileStatus
     * @param releaseStatus
     * @return
     */
    private BuildStatus getPipelineStatus(BuildStatus compileStatus, ReleaseStatus releaseStatus) {
        if (!compileStatus.equals(BuildStatus.SUCCESS)) {
            return compileStatus;
        } else {
            if (releaseStatus.equals(ReleaseStatus.WAIT) || releaseStatus.equals(ReleaseStatus.SUCCESS)) {
                return compileStatus;
            } else if (releaseStatus.equals(ReleaseStatus.RUNNING)) {
                return BuildStatus.BUILDING;
            } else  if (releaseStatus.equals(ReleaseStatus.FAIL)) {
                return BuildStatus.FAIL;
            } else {
                return compileStatus;
            }
        }
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
                // 总体分支构建记录
                pipelineBuilds = pipelineBuildService.getBranchesPipelineBuilds(ciHomeModule, pipelineBuildId, limit);
            } else {
                // 单个分支的构建记录
                pipelineBuilds = pipelineBuildService.getBranchPipelineBuilds(ciHomeModule.getId(), branchName,
                        pipelineBuildId, limit);
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
            int runPercentage = compileBuild.getBuildStatus().equals(BuildStatus.BUILDING) ? 50 : 100;
            CompileBuildBean compileBuildBean =
                    new CompileBuildBean(compileBuild.getId(), compileBuild.getCreateTime(), compileBuild.getJenkinsBuildNumber(),
                            runPercentage, compileBuild.getBuildStatus());
            compileBuildBean.setEndTime(compileBuild.getEndTime() == null ? "" : compileBuild.getEndTime());
            compileBuildBean.setTriggerUser(compileBuild.getTrigger());
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
                    releaseBean.setTriggerUser(ciHomeRelease.getReleaseUser());
                    releaseBean.setStartTime(ciHomeRelease.getReleaseTime());
                }
            }
            releaseBeanMap.put(pipelineBuild.getId(), releaseBean);
        }
        return releaseBeanMap;
    }
}
