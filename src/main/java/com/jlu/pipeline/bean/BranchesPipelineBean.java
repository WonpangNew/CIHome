package com.jlu.pipeline.bean;

/**
 * Created by niuwanpeng on 17/5/6.
 */
public class BranchesPipelineBean {

    private String branchName;

    private int branchId;

    private CiHomePipelineBean ciHomePipelineBean;

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public CiHomePipelineBean getCiHomePipelineBean() {
        return ciHomePipelineBean;
    }

    public void setCiHomePipelineBean(CiHomePipelineBean ciHomePipelineBean) {
        this.ciHomePipelineBean = ciHomePipelineBean;
    }
}
