package com.jlu.pipeline.bean;

import com.jlu.branch.bean.BranchType;
import com.jlu.compile.bean.BuildStatus;
import com.jlu.compile.bean.CompileBuildBean;
import com.jlu.github.model.GitHubCommit;
import com.jlu.release.bean.ReleaseBean;

/**
 * Created by niuwanpeng on 17/4/25.
 *
 * 流水线bean
 * 包含一条流水线的基本信息
 */
public class CiHomePipelineBean {

    private int pipelineBuildId;

    private int moduleId;

    private int buildNumber;

    private String revision;

    private String module;

    private String branchName;

    private BranchType branchType;

    private CompileBuildBean compileBuildBean;

    private GitHubCommit gitHubCommit;

    private ReleaseBean releaseBean;

    private BuildStatus pipelineStatus;

    public int getPipelineBuildId() {
        return pipelineBuildId;
    }

    public void setPipelineBuildId(int pipelineBuildId) {
        this.pipelineBuildId = pipelineBuildId;
    }

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    public int getBuildNumber() {
        return buildNumber;
    }

    public void setBuildNumber(int buildNumber) {
        this.buildNumber = buildNumber;
    }

    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public BranchType getBranchType() {
        return branchType;
    }

    public void setBranchType(BranchType branchType) {
        this.branchType = branchType;
    }

    public CompileBuildBean getCompileBuildBean() {
        return compileBuildBean;
    }

    public void setCompileBuildBean(CompileBuildBean compileBuildBean) {
        this.compileBuildBean = compileBuildBean;
    }

    public GitHubCommit getGitHubCommit() {
        return gitHubCommit;
    }

    public void setGitHubCommit(GitHubCommit gitHubCommit) {
        this.gitHubCommit = gitHubCommit;
    }

    public ReleaseBean getReleaseBean() {
        return releaseBean;
    }

    public void setReleaseBean(ReleaseBean releaseBean) {
        this.releaseBean = releaseBean;
    }

    public BuildStatus getPipelineStatus() {
        return pipelineStatus;
    }

    public void setPipelineStatus(BuildStatus pipelineStatus) {
        this.pipelineStatus = pipelineStatus;
    }
}
