package com.jlu.branch.service;

import com.jlu.branch.model.CiHomeBranch;

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
}
