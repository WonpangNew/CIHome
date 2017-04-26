package com.jlu.release.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by niuwanpeng on 17/4/26.
 *
 * 回到发布接口响应bean
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReleaseResponseBean {

    private int releaseId;

    private String module;

    private String username;

    private String releaseProductPath;

    private ReleaseStatus releaseStatus;

    private String errMsg;

    public ReleaseResponseBean() {

    }

    public ReleaseResponseBean(int releaseId, String module, String username, String releaseProductPath,
                               ReleaseStatus releaseStatus, String errMsg) {
        this.releaseId = releaseId;
        this.module = module;
        this.username = username;
        this.releaseProductPath = releaseProductPath;
        this.releaseStatus = releaseStatus;
        this.errMsg = errMsg;
    }

    public int getReleaseId() {
        return releaseId;
    }

    public void setReleaseId(int releaseId) {
        this.releaseId = releaseId;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getReleaseProductPath() {
        return releaseProductPath;
    }

    public void setReleaseProductPath(String releaseProductPath) {
        this.releaseProductPath = releaseProductPath;
    }

    public ReleaseStatus getReleaseStatus() {
        return releaseStatus;
    }

    public void setReleaseStatus(ReleaseStatus releaseStatus) {
        this.releaseStatus = releaseStatus;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    @Override
    public String toString() {
        return "ReleaseResponseBean{" +
                "releaseId=" + releaseId +
                ", module='" + module + '\'' +
                ", username='" + username + '\'' +
                ", releaseProductPath='" + releaseProductPath + '\'' +
                ", releaseStatus=" + releaseStatus +
                ", errMsg='" + errMsg + '\'' +
                '}';
    }
}
