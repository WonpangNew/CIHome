package com.jlu.common.cookies;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by niuwanpeng on 17/3/16.
 */
public class CookiesUtiles {

    private static int AUTO_LOGIN_TIMEOUT = 60*60;

    /**
     * 添加cookies
     * @param response
     * @param username
     * @param password
     */
    public static void addCookies(HttpServletResponse response, String username, String password) {
        Cookie usernameCookie = new Cookie("loginUsername", username);
        Cookie passwordCookie = new Cookie("loginPassword", password);
        usernameCookie.setMaxAge(AUTO_LOGIN_TIMEOUT);
        passwordCookie.setMaxAge(AUTO_LOGIN_TIMEOUT);
        usernameCookie.setPath("/");
        passwordCookie.setPath("/");
        response.addCookie(usernameCookie);
        response.addCookie(passwordCookie);
    }

    public static void deleteCookies(HttpServletResponse response, String username, String password) {
        Cookie usernameCookie = new Cookie("loginUsername", username);
        usernameCookie.setMaxAge(0);
        Cookie passwordCookie = new Cookie("loginPassword", password);
        passwordCookie.setMaxAge(0);
        usernameCookie.setPath("/");
        passwordCookie.setPath("/");
        response.addCookie(usernameCookie);
        response.addCookie(passwordCookie);
    }
}
