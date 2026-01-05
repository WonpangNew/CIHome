package com.jlu.jenkins.web;

import com.jlu.common.utils.JenkinsUtils;
import com.jlu.jenkins.bean.JenkinsStartCompileBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Jenkins集成控制器
 * <p>
 * 负责与Jenkins服务器进行交互，处理构建任务的触发和管理。
 * 通过Jenkins API实现持续集成功能。
 * </p>
 *
 * @author niuwanpeng
 * @since 2017-04-17
 */
@Controller
@RequestMapping("/jenkins")
public class JenkinsController {

    /**
     * 触发Jenkins构建任务
     * <p>
     * 根据代码仓库信息触发Jenkins构建流程，启动编译任务。
     * </p>
     *
     * @param repoUrl 代码仓库URL地址
     * @param repoName 代码仓库名称
     * @param compileId 编译任务ID
     * @return Jenkins启动编译结果对象，包含构建状态信息
     */
    @RequestMapping("/build")
    @ResponseBody
    public JenkinsStartCompileBean triggerCompile(String repoUrl, String repoName, int compileId) {
        JenkinsStartCompileBean jenkinsStartCompileBean = JenkinsUtils.triggerCompile(repoUrl, repoName, compileId);
        return jenkinsStartCompileBean;
    }
}
