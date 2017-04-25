package com.jlu.pipeline.service;

import com.jlu.branch.bean.BranchType;
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
}
