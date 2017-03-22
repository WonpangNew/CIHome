package com.jlu.github.web;

import com.jlu.github.model.CiHomeModule;
import com.jlu.github.service.IModuleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.rmi.MarshalledObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by niuwanpeng on 17/3/21.
 */
@Controller
@RequestMapping("/ajax/module")
public class ModuleController {

    @Autowired
    private IModuleService moduleService;

    @RequestMapping("/query")
    @ResponseBody
    public List<CiHomeModule> getSuggetsModules(@RequestParam(value = "q", required = false) String query,
                                                @RequestParam(value = "username") String username,
                                                @RequestParam Integer limit) {
        List<CiHomeModule> modules = new ArrayList<CiHomeModule>();
        if (StringUtils.isNotBlank(query)) {
            String pathStr = new String(query);
            modules = moduleService.getSuggestProductModules(pathStr, username, limit);
        }
        if (null == modules) {
            modules = new ArrayList<CiHomeModule>();
        }
        Collections.sort(modules, new Comparator<CiHomeModule>() {
            @Override
            public int compare(CiHomeModule o1, CiHomeModule o2) {
                return o1.getModule().compareTo(o2.getModule());
            }
        });
        return modules;
    }
}
