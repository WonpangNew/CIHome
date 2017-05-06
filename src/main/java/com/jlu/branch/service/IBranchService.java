package com.jlu.branch.service;

import com.jlu.branch.model.CiHomeBranch;
import com.jlu.github.model.CiHomeModule;

import java.util.List;

/**
 * Created by niuwanpeng on 17/3/10.
 */
public interface IBranchService {

    /**
     *  保存模块信息
     * @param ciHomeBranch
     */
    void saveBranch(CiHomeBranch ciHomeBranch);

    /**
     * 批量保存模块信息
     * @param ciHomeBranches
     */
    void saveBranches(List<CiHomeBranch> ciHomeBranches);

    /**
     * 根据模块数据获得最新的三位版本号＋1
     * @param ciHomeModule
     * @return
     */
    String getLastThreeVersion(CiHomeModule ciHomeModule);

    /**
     * 获得分支信息
     * @param moduleId
     * @param branchName
     * @return
     */
    CiHomeBranch getBranchByModule(int moduleId, String branchName);

    /**
     * 获得branchId之前的10条记录
     * @param ciHomeModule
     * @return
     */
    List<CiHomeBranch> getBranches(CiHomeModule ciHomeModule, int branchId, int limit);

    /**
     * 获得分支名集合
     * @param username
     * @param module
     * @return
     */
    List<String> getBranchesByModule(String username, String module);
}
