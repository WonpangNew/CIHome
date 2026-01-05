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
 * 用户登录控制器
 * <p>
 * 负责处理用户登录、登出、注册等认证相关的HTTP请求。
 * 使用Cookie机制维护用户会话状态。
 * </p>
 *
 * @author niuwanpeng
 * @since 2017-03-16
 */
@Controller
@RequestMapping("cihome/login")
public class LoginController {

    @Autowired
    private IUserService userService;

    private final static String LOGIN_STATUS = "LOGIN_STATUS";
    private final static String MESSAGE = "MESSAGE";

    /**
     * 用户登录系统
     * <p>
     * 验证用户名和密码，成功后将用户信息写入Cookie。
     * </p>
     *
     * @param response HTTP响应对象，用于写入Cookie
     * @param username 用户名
     * @param password 密码
     * @return JSON格式的登录结果，包含登录状态和提示信息
     */
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

    /**
     * 用户退出登录
     * <p>
     * 删除用户的Cookie信息，清除会话状态。
     * </p>
     *
     * @param response HTTP响应对象
     * @param request HTTP请求对象
     * @param username 用户名
     * @return 退出成功返回true
     */
    @RequestMapping("/exitLogin")
    @ResponseBody
    public boolean exitLogin(HttpServletResponse response, HttpServletRequest request, String username) {
        CookiesUtiles.deleteCookies(response, request, username);
        return true;
    }

    /**
     * 跳转到注册页面
     * <p>
     * 返回用户注册页面的视图名称。
     * </p>
     *
     * @return 注册页面视图名称
     */
    @RequestMapping("/register")
    public String registerToJsp() {
        return "register";
    }

}
