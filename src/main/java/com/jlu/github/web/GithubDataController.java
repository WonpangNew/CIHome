package com.jlu.github.web;

import com.jlu.github.service.IGitHubHookService;
import com.jlu.github.service.IGithubDataService;
import com.jlu.user.bean.UserBean;
import net.sf.json.JSONObject;
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

    /**
     * 根据用户名获得GitHub代码仓库信息并保存
     * 注册时调用
     * @param userame
     * @return
     */
    @RequestMapping("/syncReposByUser")
    @ResponseBody
    public boolean syncReposByUser(@RequestParam(value = "username") String userame) {
        return githubDataService.syncReposByUser(userame);
    }

    /**
     * 监听代码提交事件（push），触法ci
     * @param request
     * @param response
     */
    @RequestMapping(value = "/webHooks", method = RequestMethod.POST)
    @ResponseBody
    public void monitorWebHooks(HttpServletRequest request, HttpServletResponse response) {
        JSONObject paramJson = new JSONObject();
        try {
            InputStream in = request.getInputStream();
            BufferedInputStream buf = new BufferedInputStream(in);
            byte[] buffer = new byte[1024];
            int iRead;
            StringBuffer info = new StringBuffer();
            while ((iRead = buf.read(buffer)) != -1) {
                info.append(new String(buffer,0,iRead,"gbk"));
            }
            if (info != null) {
                paramJson = JSONObject.fromObject(info.toString());
                gitHubHookService.dealHookMessage(paramJson);
            }
        } catch (IOException e) {
            e.printStackTrace();
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
}
