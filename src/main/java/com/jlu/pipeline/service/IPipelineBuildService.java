package com.jlu.pipeline.service;

import com.jlu.github.model.CiHomeModule;
import com.jlu.pipeline.model.PipelineBuild;

import java.util.List;

/**
 * Created by niuwanpeng on 17/4/19.
 */
public interface IPipelineBuildService {

    /**
     * 写库
     * @param pipelineBuild
     */
    void save(PipelineBuild pipelineBuild);

    /**
     * 获得主干流水线记录
     * @param moduleId
     * @param pipelineBuildId
     * @param limit
     * @return
     */
    List<PipelineBuild> getTrunkPipelineBuilds(int moduleId, int pipelineBuildId, int limit);

    /**
     * 获得单个分支的流水线记录
     * @param moduleId
     * @param branchName
     * @param pipelineId
     * @param limit
     * @return
     */
    List<PipelineBuild> getBranchPipelineBuilds(int moduleId, String branchName, int pipelineId, int limit);

    /**
     * 获取总体分支信息
     * @param ciHomeModule
     * @param branchId
     * @param limit
     * @return
     */
    List<PipelineBuild> getBranchesPipelineBuilds(CiHomeModule ciHomeModule, int branchId, int limit);
}
