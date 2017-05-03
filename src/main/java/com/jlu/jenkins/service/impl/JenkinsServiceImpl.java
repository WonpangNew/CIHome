package com.jlu.jenkins.service.impl;

import com.jlu.common.utils.CiHomeReadConfig;
import com.jlu.common.utils.JenkinsUtils;
import com.jlu.jenkins.bean.JenkinsStartCompileBean;
import com.jlu.jenkins.service.IJenkinsService;
import org.springframework.stereotype.Service;

/**
 * Created by niuwanpeng on 17/4/28.
 */
@Service
public class JenkinsServiceImpl implements IJenkinsService {

    /**
     * 触发构建
     * @param compileBuildId
     * @param repo
     * @param owner
     * @return
     */
    public JenkinsStartCompileBean triggerCompile(int compileBuildId, String repo, String owner) {
        String repoUrl = String.format(CiHomeReadConfig.getConfigValueByKey("github.base.repo"),
                owner, repo);
       return JenkinsUtils.triggerCompile(repoUrl, repo, compileBuildId);
    }
}
