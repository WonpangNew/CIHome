package com.jlu.branch.web;

import com.jlu.branch.service.IBranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by niuwanpeng on 17/5/4.
 */
@Controller
@RequestMapping("/branch")
public class BranchController {

    @Autowired
    private IBranchService branchService;

    /**
     * 获得分支名
     * @param username
     * @param module
     * @return
     */
    @RequestMapping(value = "/getBranches", method = RequestMethod.GET)
    @ResponseBody
    public List<String> getBranchesByModule(@RequestParam("username") String username,
                                                  @RequestParam("module") String module) {
        return branchService.getBranchesByModule(username, module);
    }

}
