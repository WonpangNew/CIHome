package com.jlu.branch.web;

import com.jlu.branch.model.CiHomeBranch;
import com.jlu.branch.service.IBranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by niuwanpeng on 17/5/4.
 */
@Controller
@RequestMapping("/branch")
public class BranchController {

    @Autowired
    private IBranchService branchService;

}
