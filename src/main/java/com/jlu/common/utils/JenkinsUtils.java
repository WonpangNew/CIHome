package com.jlu.common.utils;

import com.jlu.jenkins.bean.JenkinsStartCompileBean;
import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.BuildResult;
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
 * Jenkins工具类
 * <p>
 * 提供与Jenkins服务器交互的核心功能，包括构建任务的触发、状态查询等。
 * 使用单例模式管理JenkinsServer实例，支持多线程安全访问。
 * </p>
 *
 * @author niuwanpeng
 * @since 2017-04-15
 */
public class JenkinsUtils {

    /**
     * Jenkins默认编译任务名称
     */
    private static final String COMPILE_JOB_NAME = "cihome_default_compile";

    private static final Logger LOGGER = LoggerFactory.getLogger(JenkinsUtils.class);

    /**
     * Jenkins服务器实例，使用volatile保证多线程可见性
     */
    private static volatile JenkinsServer jenkinsServer;

    /**
     * 私有构造函数，防止外部实例化
     */
    private JenkinsUtils() {}

    /**
     * 初始化Jenkins服务连接（双重检查锁单例模式）
     * <p>
     * 使用双重检查锁定（Double-Check Locking）实现线程安全的单例模式，
     * 从配置文件中读取Jenkins服务器地址、用户名和密码进行连接。
     * </p>
     *
     * @return Jenkins服务器实例
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
     * 触发Jenkins编译构建任务
     * <p>
     * 向Jenkins服务器发送构建请求，传递GitHub仓库地址、仓库名称和编译构建ID等参数。
     * 构建请求发送成功后，返回Jenkins构建编号等信息。
     * </p>
     *
     * @param repoUrl 代码仓库URL地址
     * @param repoName 代码仓库名称
     * @param compileBuildId 编译构建ID
     * @return Jenkins启动编译结果对象，包含构建编号和请求状态
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
            job.build(params);
            jenkinsStartCompileBean.setRequestStatus(true);
            LOGGER.error("Requesting jenkins'api is successful! Start compiling! GitHub url:{}, compileBuildId:{}",
                    repoUrl, compileBuildId);
        } catch (IOException e) {
            LOGGER.error("Requesting jenkins'api is failed! GitHub url:{}, compileBuildId:{}", repoUrl, compileBuildId);
            jenkinsStartCompileBean.setRequestStatus(false);
        }
        return jenkinsStartCompileBean;
    }

    /**
     * 根据构建编号查询构建状态
     * <p>
     * 通过Jenkins API查询指定构建编号的构建结果状态。
     * 如果查询失败或构建结果为null，则返回FAILURE状态。
     * </p>
     *
     * @param buildNumber Jenkins构建编号
     * @return 构建结果状态（SUCCESS/FAILURE/UNSTABLE等）
     */
    public static BuildResult getBuildStatusByNumber(int buildNumber) {
        JenkinsServer jenkinsServer = initJenkinsService();
        try {
            Job job = jenkinsServer.getJob(COMPILE_JOB_NAME);
            JobWithDetails jobWithDetails = job.details();
            BuildResult buildResult = jobWithDetails.getBuildByNumber(buildNumber).details().getResult();
            return buildResult != null ? buildResult : BuildResult.FAILURE;
        } catch (IOException e) {

        }
        return BuildResult.FAILURE;
    }
}
