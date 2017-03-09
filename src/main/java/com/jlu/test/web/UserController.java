package com.jlu.test.web;

import com.jlu.test.bean.UserBean;
import com.jlu.test.model.User;
import com.jlu.test.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Wonpang New on 2016/9/6.
 */
@Controller
public class UserController {

    @Autowired
    IUserService userService;

    @RequestMapping(value = "/test")
    @ResponseBody
    public void saveUser(@RequestParam String username) {
        User user = new User();
        user.setTelePhone("13311112222");
        user.setUsername(username);
        userService.saveUser(user);
    }

    @RequestMapping(value = "/getUser")
    @ResponseBody
    public String getUserByName(@RequestParam String username) {
        UserBean userBean =  userService.getUserByName(username);
        return userBean.getUsername() + userBean.getTelePhone();
    }
}
