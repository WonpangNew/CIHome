package com.jlu.release.service.impl;

import com.jlu.common.db.sqlcondition.ConditionAndSet;
import com.jlu.common.db.sqlcondition.DescOrder;
import com.jlu.common.db.sqlcondition.OrderCondition;
import com.jlu.github.model.GitHubCommit;
import com.jlu.release.dao.IReleaseDao;
import com.jlu.release.model.CiHomeRelease;
import com.jlu.release.service.IReleaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by niuwanpeng on 17/3/10.
 */
@Service
public class ReleaseServiceImpl implements IReleaseService{

    @Autowired
    private IReleaseDao releaseDao;

    /**
     * save
     * @param ciHomeRelease
     */
    public void save(CiHomeRelease ciHomeRelease) {
        if (ciHomeRelease != null) {
            releaseDao.equals(ciHomeRelease);
        }
    }

    /**
     * 通过pipelineId获得发布信息
     * @param pipelineId
     * @return
     */
    public CiHomeRelease getReleaseByPipelineId(int pipelineId) {
        ConditionAndSet conditionAndSet = new ConditionAndSet();
        conditionAndSet.put("pipelineBuildId", pipelineId);
        List<OrderCondition> orders = new ArrayList<OrderCondition>();
        orders.add(new DescOrder("id"));
        List<CiHomeRelease> ciHomeReleases = releaseDao.findByProperties(conditionAndSet, orders);
        return  (null == ciHomeReleases || ciHomeReleases.size() == 0) ? null : ciHomeReleases.get(0);
    }
}
