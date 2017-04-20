package com.jlu.jenkins.bean;

/**
 * Created by niuwanpeng on 17/4/20.
 */
public class JenkinsEndCompileBean {

    private String buildStatus;
    private String productPath;
    private String compileBuildId;
    private String jenkinsBuildId;
    private String errMsg;
    private String buildNumber;

    public JenkinsEndCompileBean() {

    }

    public JenkinsEndCompileBean(String buildStatus, String productPath, String compileBuildId,
                                 String jenkinsBuildId, String errMsg, String buildNumber) {
        this.buildStatus = buildStatus;
        this.productPath = productPath;
        this.compileBuildId = compileBuildId;
        this.jenkinsBuildId = jenkinsBuildId;
        this.errMsg = errMsg;
        this.buildNumber = buildNumber;
    }

    public String getBuildStatus() {
        return buildStatus;
    }

    public void setBuildStatus(String buildStatus) {
        this.buildStatus = buildStatus;
    }

    public String getProductPath() {
        return productPath;
    }

    public void setProductPath(String productPath) {
        this.productPath = productPath;
    }

    public String getCompileBuildId() {
        return compileBuildId;
    }

    public void setCompileBuildId(String compileBuildId) {
        this.compileBuildId = compileBuildId;
    }

    public String getJenkinsBuildId() {
        return jenkinsBuildId;
    }

    public void setJenkinsBuildId(String jenkinsBuildId) {
        this.jenkinsBuildId = jenkinsBuildId;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getBuildNumber() {
        return buildNumber;
    }

    public void setBuildNumber(String buildNumber) {
        this.buildNumber = buildNumber;
    }

    @Override
    public String toString() {
        return "JenkinsEndCompileBean{" +
                "buildStatus='" + buildStatus + '\'' +
                ", productPath='" + productPath + '\'' +
                ", compileBuildId='" + compileBuildId + '\'' +
                ", jenkinsBuildId='" + jenkinsBuildId + '\'' +
                ", errMsg='" + errMsg + '\'' +
                ", buildNumber='" + buildNumber + '\'' +
                '}';
    }
}
