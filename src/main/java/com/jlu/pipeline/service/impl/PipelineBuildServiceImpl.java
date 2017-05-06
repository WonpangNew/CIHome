package com.jlu.pipeline.service.impl;

import com.jlu.branch.bean.BranchType;
import com.jlu.branch.model.CiHomeBranch;
import com.jlu.branch.service.IBranchService;
import com.jlu.common.db.sqlcondition.ConditionAndSet;
import com.jlu.common.db.sqlcondition.DescOrder;
import com.jlu.common.db.sqlcondition.LessThanCondition;
import com.jlu.common.db.sqlcondition.OrderCondition;
import com.jlu.github.model.CiHomeModule;
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

    @Autowired
    private IBranchService branchService;

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

    /**
     * 获得单个分支的流水线记录
     * @param moduleId
     * @param branchName
     * @param pipelineId
     * @param limit
     * @return
     */
    @Override
    public List<PipelineBuild> getBranchPipelineBuilds(int moduleId, String branchName, int pipelineId, int limit) {
        ConditionAndSet conditionAndSet = new ConditionAndSet();
        conditionAndSet.put("moduleId", moduleId);
        conditionAndSet.put("branchName", branchName);
        conditionAndSet.put("branchType", BranchType.BRANCH);
        if (pipelineId != 0) {
            conditionAndSet.addCompareCondition(new LessThanCondition("id", pipelineId));
        }
        List<OrderCondition> orders = new ArrayList<OrderCondition>();
        orders.add(new DescOrder("id"));
        List<PipelineBuild> pipelineBuilds =
                pipelineBuildDao.findHeadByProperties(conditionAndSet, orders, pipelineId, limit);
        return null == pipelineBuilds ? new ArrayList<PipelineBuild>() : pipelineBuilds;
    }

    /**
     * 获取总体分支流水线信息
     * @param ciHomeModule
     * @param branchId
     * @param limit
     * @return
     */
    public List<PipelineBuild> getBranchesPipelineBuilds(CiHomeModule ciHomeModule, int branchId, int limit) {
        List<CiHomeBranch> ciHomeBranches = branchService.getBranches(ciHomeModule, branchId, limit);
        List<PipelineBuild> pipelineBuilds = new ArrayList<>();
        for (CiHomeBranch ciHomeBranch : ciHomeBranches) {
            ConditionAndSet conditionAndSet = new ConditionAndSet();
            conditionAndSet.put("moduleId", ciHomeModule.getId());
            conditionAndSet.put("branchType", BranchType.BRANCH);
            List<OrderCondition> orderConditions = new ArrayList<>();
            orderConditions.add(new DescOrder("id"));
            conditionAndSet.put("branchName", ciHomeBranch.getBranchName());
            List<PipelineBuild> tempPipeline
                    = pipelineBuildDao.findHeadByProperties(conditionAndSet, orderConditions, 0, 1);
            if (tempPipeline != null && tempPipeline.size() != 0) {
                pipelineBuilds.add(tempPipeline.get(0));
            }
        }
        return pipelineBuilds;
    }
}
