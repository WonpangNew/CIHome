package com.jlu.common.utils;

import com.jlu.jenkins.bean.JenkinsStartCompileBean;
import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Job;
import com.offbytwo.jenkins.model.JobWithDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by niuwanpeng on 17/4/15.
 *
 * jenkins工具类
 */
public class JenkinsUtils {

    private static final String COMPILE_JOB_NAME = "cihome_default_compile";
    private static final Logger LOGGER = LoggerFactory.getLogger(JenkinsUtils.class);

    private static volatile JenkinsServer jenkinsServer;

    private JenkinsUtils() {}

    /**
     * 单例初始化
     * @return
     */
    public static JenkinsServer initJenkinsService() {
        try {
            if (jenkinsServer == null) {
                synchronized(JenkinsUtils.class) {
                    if (jenkinsServer == null) {
                        jenkinsServer = new JenkinsServer(new URI(CiHomeReadConfig.getConfigValueByKey("jenkins.server.host")),
                                CiHomeReadConfig.getConfigValueByKey("jenkins.default.admin"),
                                CiHomeReadConfig.getConfigValueByKey("jenkins.default.password"));
                        LOGGER.info("Init JenkinsService successful!");
                    }
                }
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return jenkinsServer;
    }

    /**
     * 触发编译
     * @param repoUrl 代码仓库地址
     */
    public static JenkinsStartCompileBean triggerCompile(String repoUrl, String repoName, int compileBuildId) {
        JenkinsServer jenkinsServer = initJenkinsService();
        JenkinsStartCompileBean jenkinsStartCompileBean = new JenkinsStartCompileBean();
        try {
            Job job = jenkinsServer.getJob(COMPILE_JOB_NAME);
            JobWithDetails jobWithDetails = job.details();
            jenkinsStartCompileBean.setBuildNumber(jobWithDetails.getNextBuildNumber());
            Map<String, String> params = new HashMap<>();
            params.put("GITHUB_URL", repoUrl);
            params.put("GITHUB_REPO_NAME", repoName);
            params.put("COMPILE_BUILD_ID", String.valueOf(compileBuildId));
            jenkinsStartCompileBean.setRequestStatus(true);
            LOGGER.error("Requesting jenkins'api is successful! Start compiling! GitHub url:{}, compileBuildId:{}",
                    repoUrl, compileBuildId);
        } catch (IOException e) {
            LOGGER.error("Requesting jenkins'api is failed! GitHub url:{}, compileBuildId:{}", repoUrl, compileBuildId);
            jenkinsStartCompileBean.setRequestStatus(false);
        }
        return jenkinsStartCompileBean;
    }
}
