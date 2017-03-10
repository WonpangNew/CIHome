package com.jlu.branch.service.impl;

import com.jlu.branch.dao.IBranchDao;
import com.jlu.branch.model.CiHomeBranch;
import com.jlu.branch.service.IBranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    }
}
