package com.jlu.compile.web;

import com.jlu.compile.model.CompileBuild;
import com.jlu.compile.service.ICompileBuildService;
import com.jlu.jenkins.bean.JenkinsEndCompileBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by niuwanpeng on 17/4/15.
 */
@Controller
@RequestMapping("/compileBuild")
public class CompileBuildController {

    @Autowired
    private ICompileBuildService compileBuildService;

    /**
     * 供jenkins插件调用，接收编译结束消息
     * @return
     */
    @RequestMapping(value = "/end", method = RequestMethod.GET)
    @ResponseBody
    public String receiveCompileEndMessage(@RequestParam("BUILD_STATUS") String buildStatus,
                                           @RequestParam("PRODUCT_PATH") String productPath,
                                           @RequestParam("COMPILE_BUILD_ID") String compileBuildId,
                                           @RequestParam("JENKINS_BUILD_ID") String jenkinsBuildId,
                                           @RequestParam("ERR_MSG") String errMsg,
                                           @RequestParam("BUILD_NUMBER") String buildNumber) {
        JenkinsEndCompileBean jenkinsEndCompileBean = new JenkinsEndCompileBean(buildStatus, productPath,
                compileBuildId, jenkinsBuildId, errMsg, buildNumber);
        compileBuildService.dealCompileEnd(jenkinsEndCompileBean);
        return "OK";
    }

    @RequestMapping(value = "/v1/detail", method = RequestMethod.GET)
    @ResponseBody
    public CompileBuild getCompileBuildByPipelineId(@RequestParam("pipelineBuildId") int pipelineBuildId) {
        return compileBuildService.getCompileBuildByPipelineId(pipelineBuildId);
    }

    @RequestMapping(value = "/v1/rebuild", method = RequestMethod.GET)
    @ResponseBody
    public String rebuild(@RequestParam("compileBuildId") int compileBuildId,
                          @RequestParam("module") String module,
                          @RequestParam("username") String username) {
        return compileBuildService.doRebuild(compileBuildId, module, username);
    }

}
