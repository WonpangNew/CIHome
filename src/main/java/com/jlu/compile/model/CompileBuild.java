package com.jlu.compile.model;

import com.jlu.branch.bean.BranchType;
import com.jlu.compile.bean.BuildStatus;

import javax.persistence.*;

/**
 * 编译构建记录实体类
 * <p>
 * 对应数据库表CIHOME_COMPILE_BUILD，用于记录每次代码编译构建的详细信息。
 * 包含构建状态、Jenkins构建信息、代码提交信息、构建产物路径等。
 * 是CI/CD流水线中的核心数据模型。
 * </p>
 *
 * @author niuwanpeng
 * @since 2017-04-15
 */
@Entity
@Table(name = "CIHOME_COMPILE_BUILD")
public class CompileBuild {

    /**
     * 构建记录ID，主键，自增
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 关联的模块ID
     */
    @Column(name = "MODULE_ID")
    private int moduleId;

    /**
     * 关联的流水线构建ID
     */
    @Column(name = "PIPELINE_BUILD_ID")
    private int pipelineBuildId;

    /**
     * 构建的分支名称
     */
    @Column(name = "BRANCH_NAME")
    private String branchName;

    /**
     * 分支类型（主干分支/功能分支等）
     */
    @Column(name = "BRANCH_TYPE")
    private BranchType branchType;

    /**
     * 构建状态（构建中/成功/失败等）
     */
    @Column(name = "BUILD_STATUS")
    private BuildStatus buildStatus;

    /**
     * 构建产物存储路径
     */
    @Column(name = "PRODUCT_PATH")
    private String productPath;

    /**
     * Jenkins构建日志URL地址
     */
    @Column(name = "BUILD_LOG_URL")
    private String buildLogUrl;

    /**
     * 触发构建的用户名
     */
    @Column(name = "TRIGGER_USER")
    private String trigger;

    /**
     * 本次构建包含的代码提交信息
     */
    @Column(name = "COMMITS")
    private String commits;

    /**
     * 构建创建时间
     */
    @Column(name = "CREATE_TIME")
    private String createTime;

    /**
     * 构建结束时间
     */
    @Column(name = "END_TIME")
    private String endTime;

    /**
     * 触发构建用户的邮箱地址
     */
    @Column(name = "TRIGGER_EMAIL")
    private String triggerEmail;

    /**
     * Jenkins构建编号
     */
    @Column(name = "JENKINS_BUILD_NUMBER")
    private int jenkinsBuildNumber;

    /**
     * Jenkins构建ID
     */
    @Column(name = "JENKINS_BUILD_ID")
    private String jenkinsBuildId;

    /**
     * 代码版本号（Git提交SHA）
     */
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
