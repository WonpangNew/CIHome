package com.jlu.release.service;

import com.jlu.release.bean.ReleaseDetailBean;
import com.jlu.release.bean.ReleaseParams;
import com.jlu.release.bean.ReleaseResponseBean;
import com.jlu.release.model.CiHomeRelease;

import java.util.List;

/**
 * Created by niuwanpeng on 17/3/10.
 */
public interface IReleaseService {

    /**
     * save
     * @param ciHomeRelease
     */
    void save(CiHomeRelease ciHomeRelease);

    /**
     * 通过pipelineId获得发布信息
     * @param pipelineId
     * @return
     */
    CiHomeRelease getReleaseByPipelineId(int pipelineId);

    /**
     * 通过pipelineId获得发布信息
     * @param pipelineId
     * @return
     */
    ReleaseDetailBean getReleaseDetailByPipelineId(int pipelineId);

    /**
     * 发布
     * @param releaseParams
     * @return
     */
    String doRelease(ReleaseParams releaseParams);

    /**
     * 对回调服务进行处理
     * @param responseBean
     */
    void callbackRelease(ReleaseResponseBean responseBean);

    /**
     * 获得前三位版本号，和第四位
     * @param moduleId
     * @param branchName
     * @return
     */
    String getThreeVersion(int moduleId, String branchName);

    /**
     * 获取发布记录
     * @param username
     * @param module
     * @param releaseId
     * @return
     */
    List<ReleaseDetailBean> getCiHomeReleaseDetail(String username, String module, int releaseId);
}
