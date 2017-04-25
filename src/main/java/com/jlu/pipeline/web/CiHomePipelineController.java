package com.jlu.pipeline.web;

import com.jlu.pipeline.bean.CihomePipelineBean;
import com.jlu.pipeline.service.ICiHomePipelineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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
    public List<CihomePipelineBean> getTrunkPipeline(@RequestParam("username") String username,
                                                     @RequestParam("module") String module,
                                                     @RequestParam("pipelineBuildId") int pipelineBuildId) {
        return ciHomePipelineService.getTrunkPipeline(username, module, pipelineBuildId);
    }
}
