package com.jlu.jenkins.web;

import com.jlu.common.utils.JenkinsUtils;
import com.jlu.jenkins.bean.JenkinsStartCompileBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by niuwanpeng on 17/4/17.
 */
@Controller
@RequestMapping("/jenkins")
public class JenkinsController {

    @RequestMapping("/build")
    @ResponseBody
    public JenkinsStartCompileBean triggerCompile(String repoUrl, String repoName, int compileId) {
        JenkinsStartCompileBean jenkinsStartCompileBean = JenkinsUtils.triggerCompile(repoUrl, repoName, compileId);
        return jenkinsStartCompileBean;
    }
}
