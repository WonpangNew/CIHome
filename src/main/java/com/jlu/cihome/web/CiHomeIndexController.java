package com.jlu.cihome.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by niuwanpeng on 17/3/10.
 *
 * 进入系统首页
 */
@Controller
public class CiHomeIndexController {

    @RequestMapping(value = "/")
    public String hello() {
        return "index";
    }
}
