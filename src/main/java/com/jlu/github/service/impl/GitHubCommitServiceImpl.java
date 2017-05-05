package com.jlu.github.service.impl;

import com.jlu.common.db.sqlcondition.ConditionAndSet;
import com.jlu.common.db.sqlcondition.DescOrder;
import com.jlu.common.db.sqlcondition.OrderCondition;
import com.jlu.compile.model.CompileBuild;
import com.jlu.github.dao.IGitHubCommitDao;
import com.jlu.github.model.GitHubCommit;
import com.jlu.github.service.IGitHubCommitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by niuwanpeng on 17/4/25.
 */
@Service
public class GitHubCommitServiceImpl implements IGitHubCommitService {

    @Autowired
    private IGitHubCommitDao gitHubCommitDao;

    /**
     * save
     * @param gitHubCommit
     */
    public void save(GitHubCommit gitHubCommit) {
        if (gitHubCommit != null) {
            gitHubCommitDao.save(gitHubCommit);
        }
    }

    /**
     * 根据pipelinId获得代码提交信息
     * @param pipelineBuildId
     * @return
     */
    public GitHubCommit getGithubCommitByPipelineId(int pipelineBuildId) {
        ConditionAndSet conditionAndSet = new ConditionAndSet();
        conditionAndSet.put("pipelineBuildId", pipelineBuildId);
        List<OrderCondition> orders = new ArrayList<OrderCondition>();
        orders.add(new DescOrder("id"));
        List<GitHubCommit> gitHubCommits = gitHubCommitDao.findHeadByProperties(conditionAndSet, orders, 0, 1);
        return  (null == gitHubCommits || gitHubCommits.size() == 0) ? null : gitHubCommits.get(0);
    }
}
