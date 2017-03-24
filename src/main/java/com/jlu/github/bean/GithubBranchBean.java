package com.jlu.github.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by niuwanpeng on 17/3/24.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GithubBranchBean {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
