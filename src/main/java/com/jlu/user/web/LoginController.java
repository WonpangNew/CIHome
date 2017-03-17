package com.jlu.user.web;

import com.jlu.common.cookies.CookiesUtiles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by niuwanpeng on 17/3/16.
 */
@Controller
@RequestMapping("cihome/login")
public class LoginController {

    @RequestMapping("/loginSystem")
    public void loginSystem(HttpServletResponse response, String username, String password) {
        CookiesUtiles.addCookies(response, username, password);
    }
}
