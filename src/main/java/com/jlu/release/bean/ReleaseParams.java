package com.jlu.release.bean;

import com.jlu.branch.bean.BranchType;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by niuwanpeng on 17/4/26.
 *
 * 前端执行发布操作返回给后端的参数
 * 发布参数
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReleaseParams {

    private int pipelineBuildId;

    private int compileBuildId;

    private int moduleId;

    private BranchType branchType;

    private String branchName;

    private String module;

    private String version;

    private String releaseUser;

    private String remarks;

    public int getPipelineBuildId() {
        return pipelineBuildId;
    }

    public void setPipelineBuildId(int pipelineBuildId) {
        this.pipelineBuildId = pipelineBuildId;
    }

    public int getCompileBuildId() {
        return compileBuildId;
    }

    public void setCompileBuildId(int compileBuildId) {
        this.compileBuildId = compileBuildId;
    }

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    public BranchType getBranchType() {
        return branchType;
    }

    public void setBranchType(BranchType branchType) {
        this.branchType = branchType;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getReleaseUser() {
        return releaseUser;
    }

    public void setReleaseUser(String releaseUser) {
        this.releaseUser = releaseUser;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "ReleaseParams{" +
                "pipelineBuildId=" + pipelineBuildId +
                ", compileBuildId=" + compileBuildId +
                ", moduleId=" + moduleId +
                ", branchType=" + branchType +
                ", branchName='" + branchName + '\'' +
                ", module='" + module + '\'' +
                ", version='" + version + '\'' +
                ", releaseUser='" + releaseUser + '\'' +
                '}';
    }
}
