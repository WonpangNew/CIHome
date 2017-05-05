package com.jlu.release.web;

import com.jlu.release.bean.ReleaseDetailBean;
import com.jlu.release.bean.ReleaseParams;
import com.jlu.release.bean.ReleaseResponseBean;
import com.jlu.release.bean.ReleaseStatus;
import com.jlu.release.model.CiHomeRelease;
import com.jlu.release.service.IReleaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public String doRelease(@RequestBody ReleaseParams releaseParams) {
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
                                    @RequestParam("releaseProductPath") String releaseProductPath,
                                    @RequestParam("releaseStatus") String releaseStatus) {
        ReleaseStatus releaseStatus1 = null;
        if (releaseStatus.equals("SUCCESS")) {
            releaseStatus1 = ReleaseStatus.SUCCESS;
        } else if (releaseStatus.equals("FAIL")) {
            releaseStatus1 = ReleaseStatus.FAIL;
        }
        ReleaseResponseBean releaseResponseBean = new ReleaseResponseBean(Integer.valueOf(releaseId), module,
                releaseProductPath, releaseStatus1);
        releaseService.callbackRelease(releaseResponseBean);
    }

    /**
     * 查看发布详情
     * @param pipelineBuildId
     * @return
     */
    @RequestMapping(value = "/v1/detail", method = RequestMethod.GET)
    @ResponseBody
    public ReleaseDetailBean getReleaseByPipelineId(@RequestParam("pipelineBuildId") int pipelineBuildId) {
        return releaseService.getReleaseDetailByPipelineId(pipelineBuildId);
    }

    /**
     * 获得前三位版本号，和第四位
     * @param moduleId
     * @param branchName
     * @return
     */
    @RequestMapping(value = "/v1/threeVersion", method = RequestMethod.GET)
    @ResponseBody
    public String getThreeVersion(@RequestParam("moduleId") int moduleId,
                                  @RequestParam("branchName") String branchName) {
        return releaseService.getThreeVersion(moduleId, branchName);
    }

    @RequestMapping(value = "/v1/getReleaseHistory", method = RequestMethod.GET)
    @ResponseBody
    public List<ReleaseDetailBean> getCiHomeRelease(@RequestParam("username") String username,
                                                @RequestParam("module") String module,
                                                @RequestParam("releaseId") int releaseId) {
        return releaseService.getCiHomeReleaseDetail(username, module, releaseId);
    }
}
