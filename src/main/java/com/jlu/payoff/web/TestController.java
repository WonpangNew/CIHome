package com.jlu.payoff.web;

import com.jlu.payoff.service.TestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by niuwanpeng on 2016/8/24.
 */
@Controller
public class TestController {

    @Autowired
    TestServiceImpl testService;

    @RequestMapping(value = "/")
    public String hello() {
        return "top";
    }

    @RequestMapping(value = "/t")
    @ResponseBody
    public String hellt(@RequestParam String string) {
        return testService.test("service" + string);
    }





}
