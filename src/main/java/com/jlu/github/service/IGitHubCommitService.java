package com.jlu.github.service;

import com.jlu.github.model.GitHubCommit;

/**
 * Created by niuwanpeng on 17/4/25.
 */
public interface IGitHubCommitService {

    /**
     * save
     * @param gitHubCommit
     */
    void save(GitHubCommit gitHubCommit);

    /**
     * 根据pipelinId获得代码提交信息
     * @param pipelineBuildId
     * @return
     */
    GitHubCommit getGithubCommitByPipelineId(int pipelineBuildId);
}
