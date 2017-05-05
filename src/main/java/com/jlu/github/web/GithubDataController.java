package com.jlu.github.web;

import com.jlu.github.service.IGitHubHookService;
import com.jlu.github.service.IGithubDataService;
import com.jlu.github.service.impl.GithubDataServiceImpl;
import com.jlu.user.bean.UserBean;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * Created by niuwanpeng on 17/3/24.
 */
@Controller
@RequestMapping("/github")
public class GithubDataController {

    @Autowired
    private IGithubDataService githubDataService;

    @Autowired
    private IGitHubHookService gitHubHookService;

    private static final Logger LOGGER = LoggerFactory.getLogger(GithubDataController.class);

    /**
     * 根据用户名获得GitHub代码仓库信息并保存
     * 注册时调用
     * @param username
     * @return
     */
    @RequestMapping("/syncReposByUser")
    @ResponseBody
    public boolean syncReposByUser(@RequestParam(value = "username") String username) {
        return githubDataService.syncReposByUser(username);
    }

    /**
     * 监听代码提交事件（push），触法ci
     * @param request
     * @param response
     */
    @RequestMapping(value = "/webHooks", method = RequestMethod.POST)
    @ResponseBody
    public void monitorWebHooks(HttpServletRequest request, HttpServletResponse response) {
        StringBuffer info = new StringBuffer();
        try {
            InputStream in = request.getInputStream();
            BufferedInputStream buf = new BufferedInputStream(in);
            byte[] buffer = new byte[1024];
            int iRead;
            while ((iRead = buf.read(buffer)) != -1) {
                info.append(new String(buffer,0,iRead,"gbk"));
            }
            if (info != null) {
                final JSONObject paramJson = JSONObject.fromObject(info.toString());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        gitHubHookService.dealHookMessage(paramJson);
                    }
                }).start();
            }
        } catch (IOException e) {
            LOGGER.error("Resolving hook-message is fail! hook:{}", info.toString());
        }
    }

    /**
     * 根据用户注册信息初始化用户
     * @param userBean
     * @return
     */
    @RequestMapping(value = "/initUser", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> initUser(@RequestBody UserBean userBean) {
       return githubDataService.initUser(userBean);
    }

    /**
     * 配置新的模块
     * @param module
     * @param username
     * @return
     */
    @RequestMapping(value = "/addModule", method = RequestMethod.GET)
    @ResponseBody
    public String addModule(@RequestParam(value = "module") String module,
                            @RequestParam(value = "username") String username) {
        return githubDataService.addModule(username, module);
    }
}
