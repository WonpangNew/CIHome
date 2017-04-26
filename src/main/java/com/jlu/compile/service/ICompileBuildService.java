package com.jlu.compile.service;

import com.jlu.compile.model.CompileBuild;
import com.jlu.github.bean.GitHubCommitBean;
import com.jlu.github.bean.HookRepositoryBean;
import com.jlu.jenkins.bean.JenkinsEndCompileBean;

import java.util.List;

/**
 * Created by niuwanpeng on 17/4/15.
 */
public interface ICompileBuildService {

    /**
     * 接收到hook信息触发编译
     * @return
     */
    void hookTriggerCompile(GitHubCommitBean commitBean, HookRepositoryBean repositoryBean);

    /**
     * 接收到编译结束消息，进行写库等操作
     * @return
     */
    void dealCompileEnd(JenkinsEndCompileBean jenkinsEndCompileBean);

    /**
     * 根据pipelineBuildId获得编译信息
     * @param pipelineBuildId
     * @return
     */
    CompileBuild getCompileBuildByPipelineId(int pipelineBuildId);

    /**
     * 获得编译产出路径
     * @param compileBuildId
     * @return
     */
    String getProductPathFor(int compileBuildId);
}
