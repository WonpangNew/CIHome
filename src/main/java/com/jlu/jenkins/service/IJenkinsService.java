package com.jlu.jenkins.service;

import com.jlu.jenkins.bean.JenkinsStartCompileBean;

/**
 * Created by niuwanpeng on 17/4/15.
 */
public interface IJenkinsService {

    /**
     * 触发构建
     * @param compileBuildId
     * @param repo
     * @param owner
     * @return
     */
    JenkinsStartCompileBean triggerCompile(int compileBuildId, String repo, String owner);
}
