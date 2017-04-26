package com.jlu.release.service;

import com.jlu.release.bean.ReleaseParams;
import com.jlu.release.bean.ReleaseResponseBean;
import com.jlu.release.model.CiHomeRelease;

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
}
