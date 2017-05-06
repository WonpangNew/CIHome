package com.jlu.pipeline.web;

import com.jlu.pipeline.bean.BranchesPipelineBean;
import com.jlu.pipeline.bean.CiHomePipelineBean;
import com.jlu.pipeline.service.ICiHomePipelineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created by niuwanpeng on 17/4/19.
 */
@Controller
@RequestMapping("/pipeline")
public class CiHomePipelineController {

    @Autowired
    private ICiHomePipelineService ciHomePipelineService;

    @RequestMapping("/v1/pipelineBuilds")
    @ResponseBody
    public List<CiHomePipelineBean> getTrunkPipeline(@RequestParam("username") String username,
                                                     @RequestParam("module") String module,
                                                     @RequestParam("pipelineBuildId") int pipelineBuildId) {
        return ciHomePipelineService.getTrunkPipeline(username, module, pipelineBuildId);
    }

    @RequestMapping("/v1/branches/pipelineBuilds")
    @ResponseBody
    public Map<String, List<CiHomePipelineBean>> getBranchesPipeline(@RequestParam("username") String username,
                                                                     @RequestParam("module") String module,
                                                                     @RequestParam("branchId") int branchId) {
        return ciHomePipelineService.getBranchesPipeline(username, module, branchId);
    }

    @RequestMapping("/v1/branch/pipelineBuilds")
    @ResponseBody
    public List<CiHomePipelineBean> getBranchPipeline(@RequestParam("username") String username,
                                                      @RequestParam("module") String module,
                                                      @RequestParam("branchName") String branchName,
                                                      @RequestParam("pipelineBuildId") int pipelineBuildId) {
        return ciHomePipelineService.getBranchPipeline(username, module, branchName, pipelineBuildId);
    }
}
