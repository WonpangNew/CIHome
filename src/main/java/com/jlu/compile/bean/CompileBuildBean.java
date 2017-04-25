package com.jlu.compile.bean;

/**
 * Created by niuwanpeng on 17/4/26.
 */
public class CompileBuildBean {

    private String compileTime;

    private BuildStatus buildStatus;

    public CompileBuildBean() {

    }

    public CompileBuildBean(String compileTime, BuildStatus buildStatus) {
        this.compileTime = compileTime;
        this.buildStatus = buildStatus;
    }

    public String getCompileTime() {
        return compileTime;
    }

    public void setCompileTime(String compileTime) {
        this.compileTime = compileTime;
    }

    public BuildStatus getBuildStatus() {
        return buildStatus;
    }

    public void setBuildStatus(BuildStatus buildStatus) {
        this.buildStatus = buildStatus;
    }
}
