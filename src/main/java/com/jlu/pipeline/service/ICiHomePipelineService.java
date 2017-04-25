package com.jlu.pipeline.service;

import com.jlu.pipeline.bean.CihomePipelineBean;

import java.util.List;

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
    List<CihomePipelineBean> getTrunkPipeline(String username, String module, int pipelineBuildId);
}
