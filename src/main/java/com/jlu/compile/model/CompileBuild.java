package com.jlu.compile.model;

import com.jlu.branch.bean.BranchType;
import com.jlu.compile.bean.BuildStatus;

import javax.persistence.*;

/**
 * Created by niuwanpeng on 17/4/15.
 *
 * 编译记录
 */
@Entity
@Table(name = "CIHOME_COMPILE_BUILD")
public class CompileBuild {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "MODULE_ID")
    private int moduleId;

    @Column(name = "PIPELINE_BUILD_ID")
    private int pipelineBuildId;

    @Column(name = "BRANCH_NAME")
    private String branchName;

    @Column(name = "BRANCH_TYPE")
    private BranchType branchType;

    @Column(name = "BUILD_STATUS")
    private BuildStatus buildStatus;

    @Column(name = "PRODUCT_PATH")
    private String productPath;

    @Column(name = "BUILD_LOG_URL")
    private String buildLogUrl;

    @Column(name = "TRIGGER_USER")
    private String trigger;

    @Column(name = "COMMITS")
    private String commits;

    @Column(name = "CREATE_TIME")
    private String createTime;

    @Column(name = "END_TIME")
    private String endTime;

    @Column(name = "TRIGGER_EMAIL")
    private String triggerEmail;

    @Column(name = "JENKINS_BUILD_NUMBER")
    private int jenkinsBuildNumber;

    @Column(name = "JENKINS_BUILD_ID")
    private String jenkinsBuildId;

    @Column(name = "REVISION")
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

    public String getTrigger() {
        return trigger;
    }

    public void setTrigger(String trigger) {
        this.trigger = trigger;
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

    @Override
    public String toString() {
        return "CompileBuild{" +
                "id=" + id +
                ", moduleId=" + moduleId +
                ", pipelineBuildId=" + pipelineBuildId +
                ", branchName='" + branchName + '\'' +
                ", branchType=" + branchType +
                ", buildStatus=" + buildStatus +
                ", productPath='" + productPath + '\'' +
                ", buildLogUrl='" + buildLogUrl + '\'' +
                ", committer='" + trigger + '\'' +
                ", commits='" + commits + '\'' +
                ", createTime='" + createTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", committerEmail='" + triggerEmail + '\'' +
                '}';
    }
}
