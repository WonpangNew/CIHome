package com.jlu.compile.bean;

import com.jlu.branch.bean.BranchType;

import javax.persistence.Column;

/**
 * Created by niuwanpeng on 17/5/5.
 */
public class CompileDetailBean {

    private int id;

    private int moduleId;

    private int pipelineBuildId;

    private String branchName;

    private BranchType branchType;

    private BuildStatus buildStatus;

    private String productPath;

    private String buildLogUrl;

    private String trigger;

    private String commits;

    private String createTime;

    private String endTime;

    private String triggerEmail;

    private int jenkinsBuildNumber;

    private String jenkinsBuildId;

    private String revision;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    public int getPipelineBuildId() {
        return pipelineBuildId;
    }

    public void setPipelineBuildId(int pipelineBuildId) {
        this.pipelineBuildId = pipelineBuildId;
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

    public BuildStatus getBuildStatus() {
        return buildStatus;
    }

    public void setBuildStatus(BuildStatus buildStatus) {
        this.buildStatus = buildStatus;
    }

    public String getProductPath() {
        return productPath;
    }

    public void setProductPath(String productPath) {
        this.productPath = productPath;
    }

    public String getBuildLogUrl() {
        return buildLogUrl;
    }

    public void setBuildLogUrl(String buildLogUrl) {
        this.buildLogUrl = buildLogUrl;
    }

    public String getTrigger() {
        return trigger;
    }

    public void setTrigger(String trigger) {
        this.trigger = trigger;
    }

    public String getCommits() {
        return commits;
    }

    public void setCommits(String commits) {
        this.commits = commits;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTriggerEmail() {
        return triggerEmail;
    }

    public void setTriggerEmail(String triggerEmail) {
        this.triggerEmail = triggerEmail;
    }

    public int getJenkinsBuildNumber() {
        return jenkinsBuildNumber;
    }

    public void setJenkinsBuildNumber(int jenkinsBuildNumber) {
        this.jenkinsBuildNumber = jenkinsBuildNumber;
    }

    public String getJenkinsBuildId() {
        return jenkinsBuildId;
    }

    public void setJenkinsBuildId(String jenkinsBuildId) {
        this.jenkinsBuildId = jenkinsBuildId;
    }

    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }
}
