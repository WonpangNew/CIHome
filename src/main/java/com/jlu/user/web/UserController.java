package com.jlu.user.web;

import com.jlu.user.model.CiHomeUser;
import com.jlu.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by niuwanpeng on 17/4/5.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     * 注册时判断是否为重复用户名
     * @param username
     * @return
     */
    @RequestMapping(value = "/judgeUsernameRepeat", method = RequestMethod.GET)
    @ResponseBody
    public boolean judgeUsernameRepeat(String username) {
        CiHomeUser ciHomeUser = userService.getUserByName(username);
        return ciHomeUser == null ? false : true;
    }
}
