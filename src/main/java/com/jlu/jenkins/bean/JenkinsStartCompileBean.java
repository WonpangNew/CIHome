package com.jlu.jenkins.bean;

/**
 * Created by niuwanpeng on 17/4/15.
 *
 * 调取jenkins接口开始编译返回的信息bean
 */
public class JenkinsStartCompileBean {

    private int buildNumber;

    private boolean requestStatus;

    /**
     * 获得构建号
     * @return
     */
    public int getBuildNumber() {
        return buildNumber;
    }

    public void setBuildNumber(int buildNumber) {
        this.buildNumber = buildNumber;
    }

    /**
     * 获得请求接口的状态
     * @return
     */
    public boolean isRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(boolean requestStatus) {
        this.requestStatus = requestStatus;
    }
}
