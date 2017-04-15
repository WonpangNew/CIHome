package com.jlu.compile.web;

import com.jlu.compile.service.ICompileBuildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    @RequestMapping(value = "/end", method = RequestMethod.POST)
    @ResponseBody
    public String receiveCompileEndMessage() {
        compileBuildService.dealCompileEnd();
        return "OK";
    }
}
