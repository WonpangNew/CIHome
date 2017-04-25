package com.jlu.release.bean;

/**
 * Created by niuwanpeng on 17/3/10.
 */
public class ReleaseBean {

    private ReleaseStatus releaseStatus;

    private String version;

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
}
