package com.jlu.compile.service.impl;

import com.jlu.common.utils.JenkinsUtils;
import com.jlu.compile.service.ICompileBuildService;
import com.jlu.jenkins.bean.JenkinsStartCompileBean;
import org.springframework.stereotype.Service;

/**
 * Created by niuwanpeng on 17/4/15.
 *
 * 编译逻辑处理
 */
@Service
public class CompileBuildServiceImpl implements ICompileBuildService {

    /**
     * 接收到hook信息触发编译
     * @return
     */
    public String hookTriggerCompile() {
        // TODO: 17/4/15 初始化compile信息
        // 开始编译
        JenkinsStartCompileBean jenkinsStartCompileBean = JenkinsUtils.triggerCompile("", "", 9);
        // TODO: 17/4/15 填充编译开始信息
        return "";
    }

    /**
     * 接收到编译结束消息，进行写库等操作
     * @return
     */
    public String dealCompileEnd() {
        // TODO: 17/4/15 根据compileBuildId对本次编译结果写库
        return "";
    }


}
