package com.jlu.pipeline.service;

import com.jlu.pipeline.model.PipelineBuild;

/**
 * Created by niuwanpeng on 17/4/19.
 */
public interface IPipelineBuildService {

    /**
     * 写库
     * @param pipelineBuild
     */
    void save(PipelineBuild pipelineBuild);
}
