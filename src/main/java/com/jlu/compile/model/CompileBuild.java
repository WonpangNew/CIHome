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

    @Column(name = "COMMIT_USER")
    private String committer;

    @Column(name = "COMMITS")
    private String commits;

    @Column(name = "CREATE_TIME")
    private String createTime;

    @Column(name = "END_TIME")
    private String endTime;

    @Column(name = "COMMIT_EMAIL")
    private String committerEmail;

    @Column(name = "JENKINS_BUILD_NUMBER")
    private int jenkinsBuildNumber;

    @Column(name = "JENKINS_BUILD_ID")
    private String jenkinsBuildId;

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

    public String getCommitter() {
        return committer;
    }

    public void setCommitter(String commiter) {
        this.committer = commiter;
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

    public String getCommitterEmail() {
        return committerEmail;
    }

    public void setCommitterEmail(String committerEmail) {
        this.committerEmail = committerEmail;
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

    public void setJeniknsBuildId(String jenkinsBuildId) {
        this.jenkinsBuildId = jenkinsBuildId;
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
                ", committer='" + committer + '\'' +
                ", commits='" + commits + '\'' +
                ", createTime='" + createTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", committerEmail='" + committerEmail + '\'' +
                '}';
    }
}
