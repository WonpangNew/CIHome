package com.jlu.branch.bean;

/**
 * Created by niuwanpeng on 17/3/10.
 */
public enum BranchType {
    TRUNK(1), BRANCH(2);

    private int var;

    private BranchType(int var) {
        this.var = var;
    }
}
