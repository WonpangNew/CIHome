package com.jlu.pipeline.service.impl;

import com.jlu.branch.bean.BranchType;
import com.jlu.common.db.sqlcondition.ConditionAndSet;
import com.jlu.common.db.sqlcondition.DescOrder;
import com.jlu.common.db.sqlcondition.LessThanCondition;
import com.jlu.common.db.sqlcondition.OrderCondition;
import com.jlu.pipeline.dao.IPipelineBuildDao;
import com.jlu.pipeline.model.PipelineBuild;
import com.jlu.pipeline.service.IPipelineBuildService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    @Override
    public void save(PipelineBuild pipelineBuild) {
        pipelineBuildDao.save(pipelineBuild);
    }

    /**
     * 获得主干流水线记录
     * @param moduleId
     * @param pipelineBuildId
     * @param limit
     * @return
     */
    @Override
    public List<PipelineBuild> getTrunkPipelineBuilds(int moduleId, int pipelineBuildId, int limit) {
        ConditionAndSet conditionAndSet = new ConditionAndSet();
        conditionAndSet.put("moduleId", moduleId);
        conditionAndSet.put("branchType", BranchType.TRUNK);
        if (pipelineBuildId != 0) {
            conditionAndSet.addCompareCondition(new LessThanCondition("id", pipelineBuildId));
        }
        List<OrderCondition> orders = new ArrayList<OrderCondition>();
        orders.add(new DescOrder("id"));
        List<PipelineBuild> pipelineBuilds =
                pipelineBuildDao.findHeadByProperties(conditionAndSet, orders, pipelineBuildId, limit);
        return null == pipelineBuilds ? new ArrayList<PipelineBuild>() : pipelineBuilds;
    }
}
