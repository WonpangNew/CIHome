package com.jlu.github.web;

import com.jlu.github.service.IGithubDataService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by niuwanpeng on 17/3/24.
 */
@Controller
@RequestMapping("/github")
public class GithubDataController {

    @Autowired
    private IGithubDataService githubDataService;

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

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    @ResponseBody
    public String test(HttpServletRequest request, HttpServletResponse response) {
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
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "ok";
    }
}
