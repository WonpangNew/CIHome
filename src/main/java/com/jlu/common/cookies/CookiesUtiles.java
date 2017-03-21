package com.jlu.common.cookies;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by niuwanpeng on 17/3/16.
 */
public class CookiesUtiles {

    private final static int AUTO_LOGIN_TIMEOUT = 60*60;

    /**
     * 添加cookies
     * @param response
     * @param username
     * @param password
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

    public static void deleteCookies(HttpServletResponse response, HttpServletRequest request, String username) {
        Cookie usernameCookie = new Cookie("loginUsername", EncryUtil.encrypt(username));
        usernameCookie.setMaxAge(0);
        usernameCookie.setPath("/");
        response.addCookie(usernameCookie);
        request.getSession().setAttribute("loginUser", null);
    }
}
