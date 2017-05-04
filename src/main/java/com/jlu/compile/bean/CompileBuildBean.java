package com.jlu.compile.bean;

/**
 * Created by niuwanpeng on 17/4/26.
 */
public class CompileBuildBean {

    private int id;

    private String startTime;

    private String endTime;

    private String triggerUser;

    private int buildNumber;

    // 用于前端展示的百分比
    private int runPercentage;

    private BuildStatus buildStatus;

    public CompileBuildBean() {

    }

    public CompileBuildBean(int id, String startTime, int buildNumber, int runPercentage, BuildStatus buildStatus) {
        this.id = id;
        this.startTime = startTime;
        this.buildNumber = buildNumber;
        this.runPercentage = runPercentage;
        this.buildStatus = buildStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBuildNumber() {
        return buildNumber;
    }

    public void setBuildNumber(int buildNumber) {
        this.buildNumber = buildNumber;
    }

    public int getRunPercentage() {
        return runPercentage;
    }

    public void setRunPercentage(int runPercentage) {
        this.runPercentage = runPercentage;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public BuildStatus getBuildStatus() {
        return buildStatus;
    }

    public void setBuildStatus(BuildStatus buildStatus) {
        this.buildStatus = buildStatus;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTriggerUser() {
        return triggerUser;
    }

    public void setTriggerUser(String triggerUser) {
        this.triggerUser = triggerUser;
    }
}
