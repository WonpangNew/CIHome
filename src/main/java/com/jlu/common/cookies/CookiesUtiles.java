package com.jlu.common.cookies;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Cookie工具类
 * <p>
 * 提供用户会话Cookie的管理功能，包括添加和删除Cookie。
 * Cookie信息经过加密处理，用于实现用户自动登录和会话保持。
 * </p>
 *
 * @author niuwanpeng
 * @since 2017-03-16
 */
public class CookiesUtiles {

    /**
     * 自动登录超时时间（单位：秒，1小时）
     */
    private final static int AUTO_LOGIN_TIMEOUT = 60*60;

    /**
     * 添加用户登录Cookie
     * <p>
     * 将用户名和密码加密后存储到Cookie中，设置有效期为1小时。
     * Cookie的路径设置为根路径"/"，确保整个应用可访问。
     * </p>
     *
     * @param response HTTP响应对象
     * @param username 用户名
     * @param password 用户密码
     */
    public static void addCookies(HttpServletResponse response, String username, String password) {
        Cookie usernameCookie = new Cookie("loginUsername", EncryUtil.encrypt(username));
        Cookie passwordCookie = new Cookie("loginPassword", EncryUtil.encrypt(password));
        usernameCookie.setMaxAge(AUTO_LOGIN_TIMEOUT);
        passwordCookie.setMaxAge(AUTO_LOGIN_TIMEOUT);
        usernameCookie.setPath("/");
        passwordCookie.setPath("/");
        response.addCookie(usernameCookie);
        response.addCookie(passwordCookie);
    }

    /**
     * 删除用户登录Cookie
     * <p>
     * 通过设置Cookie的MaxAge为0来删除Cookie，同时清除Session中的登录用户信息。
     * 用于用户退出登录时清理会话数据。
     * </p>
     *
     * @param response HTTP响应对象
     * @param request HTTP请求对象
     * @param username 用户名
     */
    public static void deleteCookies(HttpServletResponse response, HttpServletRequest request, String username) {
        Cookie usernameCookie = new Cookie("loginUsername", EncryUtil.encrypt(username));
        usernameCookie.setMaxAge(0);
        usernameCookie.setPath("/");
        response.addCookie(usernameCookie);
        request.getSession().setAttribute("loginUser", null);
    }
}
