package com.jlu.pipeline.service;

import com.jlu.pipeline.bean.BranchesPipelineBean;
import com.jlu.pipeline.bean.CiHomePipelineBean;

import java.util.List;
import java.util.Map;

/**
 * Created by niuwanpeng on 17/4/26.
 */
public interface ICiHomePipelineService {

    /**
     * 获取主干流水线的构建记录
     * @param username
     * @param module
     * @param pipelineBuildId
     * @return
     */
    List<CiHomePipelineBean> getTrunkPipeline(String username, String module, int pipelineBuildId);

    /**
     * 获取单个分支流水线的构建记录
     * @param username
     * @param module
     * @param branchName
     * @param pipelineBuildId
     * @return
     */
    List<CiHomePipelineBean> getBranchPipeline(String username, String module, String branchName, int pipelineBuildId);

    /**
     * 获取全部分支流水线构建记录
     * @param username
     * @param module
     * @param pipelineBuildId
     * @return
     */
    Map<String, List<CiHomePipelineBean>> getBranchesPipeline(String username, String module, int pipelineBuildId);
}
