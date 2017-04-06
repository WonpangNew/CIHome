package com.jlu.branch.model;

import com.jlu.branch.bean.BranchType;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by niuwanpeng on 17/3/10.
 *
 * 分支实体类, 与用户名关联
 */
@Entity
@Table(name = "CIHOME_BRANCH")
public class CiHomeBranch {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "MODULE_ID")
    private int moduleId;

    @Column(name = "BRANCH_NAME")
    private String branchName;

    @Column(name = "BRANCH_TYPE")
    private BranchType branchType;

    @Column(name = "CREATE_TIME")
    private String createTime;

    @Column(name = "VERSION")
    private String version;

    @Column(name = "REMARKS")
    private String remarks;

    public CiHomeBranch(int moduleId, String branchName, BranchType branchType, String version, String createTime) {
        this.moduleId = moduleId;
        this.branchName = branchName;
        this.branchType = branchType;
        this.version = version;
        this.createTime = createTime;
    }

    public CiHomeBranch() {}

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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
