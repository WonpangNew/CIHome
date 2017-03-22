package com.jlu.user.web;

import com.google.gson.Gson;
import com.jlu.common.cookies.CookiesUtiles;
import com.jlu.user.model.CiHomeUser;
import com.jlu.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by niuwanpeng on 17/3/16.
 */
@Controller
@RequestMapping("cihome/login")
public class LoginController {

    @Autowired
    private IUserService userService;

    private final static String LOGIN_STATUS = "LOGIN_STATUS";
    private final static String MESSAGE = "MESSAGE";

    @RequestMapping("/loginSystem")
    @ResponseBody
    public String loginSystem(HttpServletResponse response, String username, String password) {
        Map<String, String> result = new HashMap<String, String>();
        result.put(LOGIN_STATUS, "FAIL");
        result.put(MESSAGE, "请重新输入用户名和密码！");
        if (username != null && password != null) {
            CiHomeUser user = userService.getUserByName(username);
            if (user != null) {
                if (user.getPassword().equals(password)) {
                    result.put(LOGIN_STATUS, "SUCC");
                    result.put(MESSAGE, " 登录成功！");
                    CookiesUtiles.addCookies(response, username, password);
                } else {
                    result.put(MESSAGE, "密码输入错误！");
                }
            } else {
                result.put(MESSAGE, "用户不存在,先创建一个？");
            }
        }
        return new Gson().toJson(result);
    }

    @RequestMapping("/exitLogin")
    @ResponseBody
    public boolean exitLogin(HttpServletResponse response, HttpServletRequest request, String username) {
        CookiesUtiles.deleteCookies(response, request, username);
        return true;
    }

    @RequestMapping("/register")
    public String registerToJsp() {
        return "register";
    }

    @RequestMapping("/loginTo")
    public String loginToJsp() {
        return "login";
    }
}
