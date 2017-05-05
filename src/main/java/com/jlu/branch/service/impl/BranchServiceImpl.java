package com.jlu.branch.service.impl;

import com.jlu.branch.dao.IBranchDao;
import com.jlu.branch.model.CiHomeBranch;
import com.jlu.branch.service.IBranchService;
import com.jlu.common.db.sqlcondition.ConditionAndSet;
import com.jlu.common.db.sqlcondition.DescOrder;
import com.jlu.common.db.sqlcondition.OrderCondition;
import com.jlu.github.model.CiHomeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by niuwanpeng on 17/3/10.
 */
@Service
public class BranchServiceImpl implements IBranchService {

    @Autowired
    private IBranchDao branchDao;

    /**
     *  保存模块信息
     * @param ciHomeBranch
     */
    @Override
    public void saveBranch(CiHomeBranch ciHomeBranch) {
        branchDao.save(ciHomeBranch);
    }

    /**
     * 批量保存模块信息
     * @param ciHomeBranches
     */
    @Override
    public void saveBranches(List<CiHomeBranch> ciHomeBranches) {
        branchDao.saveOrUpdateAll(ciHomeBranches);
    }

    /**
     * 根据模块数据获得最新的三位版本号＋1
     * @param ciHomeModule
     * @return
     */
    @Override
    public String getLastThreeVersion(CiHomeModule ciHomeModule) {
        ConditionAndSet conditionAndSet = new ConditionAndSet();
        conditionAndSet.put("moduleId", ciHomeModule.getId());
        List<OrderCondition> orders = new ArrayList<OrderCondition>();
        orders.add(new DescOrder("id"));
        List<CiHomeBranch> branches = branchDao.findHeadByProperties(conditionAndSet, orders, 0, 1);
        if (branches != null && branches.size() != 0) {
            String threeVersion = branches.get(0).getVersion();
            String[] numbers = threeVersion.split("\\.");
            return (Integer.parseInt(numbers[0]) + 1) + ".0.0";
        } else {
            return "2.0.0";
        }
    }

    /**
     * 获得分支信息
     * @param moduleId
     * @param branchName
     * @return
     */
    @Override
    public CiHomeBranch getBranchByModule(int moduleId, String branchName) {
        ConditionAndSet conditionAndSet = new ConditionAndSet();
        conditionAndSet.put("moduleId", moduleId);
        conditionAndSet.put("branchName", branchName);
        List<CiHomeBranch> ciHomeBranches = branchDao.findByProperties(conditionAndSet);
        return ciHomeBranches != null && ciHomeBranches.size() != 0 ? ciHomeBranches.get(0) : null;
    }

}
