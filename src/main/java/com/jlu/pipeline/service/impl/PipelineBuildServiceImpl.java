package com.jlu.pipeline.service.impl;

import com.jlu.pipeline.dao.IPipelineBuildDao;
import com.jlu.pipeline.model.PipelineBuild;
import com.jlu.pipeline.service.IPipelineBuildService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by niuwanpeng on 17/4/19.
 */
@Service
public class PipelineBuildServiceImpl implements IPipelineBuildService{

    @Autowired
    private IPipelineBuildDao pipelineBuildDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(PipelineBuildServiceImpl.class);

    /**
     * 写库
     * @param pipelineBuild
     */
    public void save(PipelineBuild pipelineBuild) {
        pipelineBuildDao.save(pipelineBuild);
    }
}
