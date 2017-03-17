package com.jlu.common.web;

import com.jlu.common.cookies.EncryUtil;
import com.jlu.user.model.CiHomeUser;
import com.jlu.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by niuwanpeng on 17/3/16.
 */
@Service
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private IUserService userService;

    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                             Object handle) throws Exception {

        CiHomeUser loginUser = (CiHomeUser) httpServletRequest.getSession().getAttribute("loginUser");

        if (loginUser == null) {
            String loginCookieUserName = "";
            String loginCookiePassword = "";

            Cookie[] cookies = httpServletRequest.getCookies();
            if (null != cookies) {
                for (Cookie cookie : cookies) {
                    if ("loginUsername".equals(cookie.getName())) {
                        loginCookieUserName = cookie.getValue();
                    } else if ("loginPassword".equals(cookie.getName())) {
                        loginCookiePassword = cookie.getValue();
                    }
                }
                if (!"".equals(loginCookieUserName) && !"".equals(loginCookiePassword)) {
                    loginCookieUserName = EncryUtil.decrypt(loginCookieUserName);
                    loginCookiePassword = EncryUtil.decrypt(loginCookiePassword);
                    CiHomeUser user = userService.getUserByName(loginCookieUserName);
                    if (loginCookiePassword.equals(user.getPassword())) {
                        httpServletRequest.getSession().setAttribute("loginUser", user);
                    }
                } else {
                    httpServletRequest.getRequestDispatcher("/WEB-INF/pages/login.jsp")
                            .forward(httpServletRequest, httpServletResponse);
                }
            } else {
                httpServletRequest.getRequestDispatcher("/WEB-INF/pages/login.jsp")
                        .forward(httpServletRequest, httpServletResponse);
            }
        }
        return true;
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {

    }
}
