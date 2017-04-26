package com.jlu.release.model;

import com.jlu.branch.bean.BranchType;
import com.jlu.release.bean.ReleaseStatus;

import javax.persistence.*;

/**
 * Created by niuwanpeng on 17/3/10.
 */
@Entity
@Table(name = "CIHOME_RELEASE")
public class CiHomeRelease {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "PIPELINE_BUILD_ID")
    private int pipelineBuildId;

    @Column(name = "MODULE_ID")
    private int moduleId;

    @Column(name = "RELEASE_STATUS")
    private ReleaseStatus releaseStatus;

    @Column(name = "VERSION")
    private String version;

    @Column(name = "TAG")
    private String tag;

    @Column(name = "PRODUCT_PATH")
    private String productPath;

    @Column(name = "RELEASE_USER")
    private String releaseUser;

    @Column(name = "RELEASE_TIME")
    private String releaseTime;

    @Column(name = "BRANCH_NAME")
    private String branchName;

    @Column(name = "BRANCH_TYPE")
    private BranchType branchType;

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

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    public String getProductPath() {
        return productPath;
    }

    public void setProductPath(String productPath) {
        this.productPath = productPath;
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
}
