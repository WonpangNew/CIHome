package com.jlu.release.web;

import com.jlu.release.bean.ReleaseParams;
import com.jlu.release.bean.ReleaseResponseBean;
import com.jlu.release.bean.ReleaseStatus;
import com.jlu.release.service.IReleaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by niuwanpeng on 17/3/10.
 */
@Controller
@RequestMapping("/release")
public class ReleaseController {

    @Autowired
    private IReleaseService releaseService;

    /**
     * 发布执行
     * @param releaseParams
     * @return
     */
    @RequestMapping(value = "/doRelease", method = RequestMethod.POST)
    @ResponseBody
    public String doRelease(ReleaseParams releaseParams) {
        return releaseService.doRelease(releaseParams);
    }

    /**
     * 开放接口，发布服务回调
     * @return
     */
    @RequestMapping(value = "/callBackFromRelease", method = RequestMethod.GET)
    @ResponseBody
    public void callBackFromRelease(@RequestParam("releaseId") String releaseId,
                                      @RequestParam("module") String module,
                                      @RequestParam("username") String username,
                                      @RequestParam("releaseProductPath") String releaseProductPath,
                                      @RequestParam("releaseStatus") String releaseStatus,
                                      @RequestParam("errMsg") String errMsg) {
        ReleaseStatus releaseStatus1 = null;
        if (releaseStatus.equals("SUCCESS")) {
            releaseStatus1 = ReleaseStatus.SUCCESS;
        } else if (releaseStatus.equals("FAIL")) {
            releaseStatus1 = ReleaseStatus.FAIL;
        }
        ReleaseResponseBean releaseResponseBean = new ReleaseResponseBean(Integer.valueOf(releaseId), module, username,
                releaseProductPath, releaseStatus1, errMsg);
        releaseService.callbackRelease(releaseResponseBean);
    }
}
