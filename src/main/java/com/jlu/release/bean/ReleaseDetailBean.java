package com.jlu.release.bean;

import com.jlu.branch.bean.BranchType;

import javax.persistence.Column;

/**
 * Created by niuwanpeng on 17/5/5.
 */
public class ReleaseDetailBean {

    private int id;

    private int pipelineBuildId;

    private int moduleId;

    private ReleaseStatus releaseStatus;

    private String version;

    private String tag;

    private String productPath;

    private String releaseUser;

    private String releaseTime;

    private String branchName;

    private BranchType branchType;

    private String remarks;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public ReleaseStatus getReleaseStatus() {
        return releaseStatus;
    }

    public void setReleaseStatus(ReleaseStatus releaseStatus) {
        this.releaseStatus = releaseStatus;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getProductPath() {
        return productPath;
    }

    public void setProductPath(String productPath) {
        this.productPath = productPath;
    }

    public String getReleaseUser() {
        return releaseUser;
    }

    public void setReleaseUser(String releaseUser) {
        this.releaseUser = releaseUser;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
