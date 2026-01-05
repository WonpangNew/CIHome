package com.jlu.jenkins.service.impl;

import com.jlu.common.utils.CiHomeReadConfig;
import com.jlu.common.utils.JenkinsUtils;
import com.jlu.jenkins.bean.JenkinsStartCompileBean;
import com.jlu.jenkins.service.IJenkinsService;
import org.springframework.stereotype.Service;

/**
 * Jenkins服务实现类
 * <p>
 * 提供Jenkins集成相关的业务逻辑实现，负责与Jenkins服务器进行交互，
 * 管理构建任务的触发和执行。
 * </p>
 *
 * @author niuwanpeng
 * @since 2017-04-28
 */
@Service
public class JenkinsServiceImpl implements IJenkinsService {

    /**
     * 触发Jenkins构建任务
     * <p>
     * 根据代码仓库信息构造仓库URL，并通过Jenkins API触发编译构建任务。
     * </p>
     *
     * @param compileBuildId 编译构建ID
     * @param repo 仓库名称
     * @param owner 仓库所有者
     * @return Jenkins启动编译结果对象
     */
    public JenkinsStartCompileBean triggerCompile(int compileBuildId, String repo, String owner) {
        String repoUrl = String.format(CiHomeReadConfig.getConfigValueByKey("github.base.repo"),
                owner, repo);
       return JenkinsUtils.triggerCompile(repoUrl, repo, compileBuildId);
    }
}
