package com.jlu.release.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jlu.branch.model.CiHomeBranch;
import com.jlu.branch.service.IBranchService;
import com.jlu.common.db.sqlcondition.ConditionAndSet;
import com.jlu.common.db.sqlcondition.DescOrder;
import com.jlu.common.db.sqlcondition.OrderCondition;
import com.jlu.common.utils.CiHomeReadConfig;
import com.jlu.common.utils.DateUtil;
import com.jlu.common.utils.FTPUtils;
import com.jlu.common.utils.HttpClientUtil;
import com.jlu.compile.service.ICompileBuildService;
import com.jlu.github.model.CiHomeModule;
import com.jlu.github.service.IModuleService;
import com.jlu.release.bean.ReleaseDetailBean;
import com.jlu.release.bean.ReleaseParams;
import com.jlu.release.bean.ReleaseResponseBean;
import com.jlu.release.bean.ReleaseStatus;
import com.jlu.release.dao.IReleaseDao;
import com.jlu.release.model.CiHomeRelease;
import com.jlu.release.service.IReleaseService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by niuwanpeng on 17/3/10.
 */
@Service
public class ReleaseServiceImpl implements IReleaseService{

    @Autowired
    private IReleaseDao releaseDao;

    @Autowired
    private IBranchService branchService;

    @Autowired
    private ICompileBuildService compileBuildService;

    @Autowired
    private IModuleService moduleService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ReleaseServiceImpl.class);

    private static final Gson GSON = new Gson();

    /**
     * save
     * @param ciHomeRelease
     */
    @Override
    public void save(CiHomeRelease ciHomeRelease) {
        if (ciHomeRelease != null) {
            releaseDao.equals(ciHomeRelease);
        }
    }

    /**
     * 通过pipelineId获得发布信息
     * @param pipelineId
     * @return
     */
    @Override
    public CiHomeRelease getReleaseByPipelineId(int pipelineId) {
        ConditionAndSet conditionAndSet = new ConditionAndSet();
        conditionAndSet.put("pipelineBuildId", pipelineId);
        List<OrderCondition> orders = new ArrayList<OrderCondition>();
        orders.add(new DescOrder("id"));
        List<CiHomeRelease> ciHomeReleases = releaseDao.findByProperties(conditionAndSet, orders);
        return  (null == ciHomeReleases || ciHomeReleases.size() == 0) ? null : ciHomeReleases.get(0);
    }

    /**
     * 通过pipelineId获得发布信息
     * @param pipelineId
     * @return
     */
    @Override
    public ReleaseDetailBean getReleaseDetailByPipelineId(int pipelineId) {
        CiHomeRelease ciHomeRelease = getReleaseByPipelineId(pipelineId);
        ReleaseDetailBean releaseDetailBean = new ReleaseDetailBean();
        BeanUtils.copyProperties(ciHomeRelease, releaseDetailBean);
        if (releaseDetailBean != null) {
            releaseDetailBean.setProductPath(FTPUtils.getDownloadUrl(ciHomeRelease.getProductPath()));
        }
        return releaseDetailBean;
    }

    /**
     * 发布
     * @param releaseParams
     * @return
     */
    @Override
    public String doRelease(ReleaseParams releaseParams) {
        Map<String, String> returnContent = new HashMap<>();
        Boolean checkVersion = checkReleaseParams(releaseParams);
        if (checkVersion) {
            returnContent.put("ERR_MSG", "版本号重复，请更换第四位版本重新发布！");
            returnContent.put("STATUS", "FAIL");
            LOGGER.error("Release is fail because version is duplicate. releaseParams:{}", releaseParams.toString());
            return GSON.toJson(returnContent);
        }
        CiHomeRelease ciHomeRelease = initCiHomeRelease(releaseParams);
        doReleaseForAPI(releaseParams, ciHomeRelease.getId(), returnContent);
        if (returnContent.get("STATUS").equals("FAIL")) {
            ciHomeRelease.setReleaseStatus(ReleaseStatus.FAIL);
            releaseDao.saveOrUpdate(ciHomeRelease);
        }
        return GSON.toJson(returnContent);
    }

    /**
     * 检查版本号是否重复
     * @param releaseParams
     * @return true:版本号重复， false:版本号不重复，可用
     */
    private boolean checkReleaseParams(ReleaseParams releaseParams) {
        ConditionAndSet conditionAndSet = new ConditionAndSet();
        conditionAndSet.put("moduleId", releaseParams.getModuleId());
        conditionAndSet.put("version", releaseParams.getVersion());
        List<CiHomeRelease> ciHomeReleases = releaseDao.findByProperties(conditionAndSet);
        return ciHomeReleases == null || ciHomeReleases.size() == 0 ? false : true;
    }

    /**
     * 根绝发布参数初始化CiHomeRelease并写库
     * @param releaseParams
     * @return
     */
    private CiHomeRelease initCiHomeRelease(ReleaseParams releaseParams) {
        CiHomeRelease ciHomeRelease = new CiHomeRelease();
        ciHomeRelease.setBranchType(releaseParams.getBranchType());
        ciHomeRelease.setReleaseStatus(ReleaseStatus.RUNNING);
        ciHomeRelease.setBranchName(releaseParams.getBranchName());
        ciHomeRelease.setModuleId(releaseParams.getModuleId());
        ciHomeRelease.setPipelineBuildId(releaseParams.getPipelineBuildId());
        ciHomeRelease.setReleaseTime(DateUtil.getNowDateFormat());
        ciHomeRelease.setReleaseUser(releaseParams.getReleaseUser());
        ciHomeRelease.setVersion(releaseParams.getVersion());
        ciHomeRelease.setRemarks(releaseParams.getRemarks());
        releaseDao.save(ciHomeRelease);
        LOGGER.info("Init CiHomeRelease successful, releaseId:{}", ciHomeRelease.getId());
        return ciHomeRelease;
    }

    /**
     * 调取发布服务接口进行发布
     * @param releaseParams
     * @param releaseId
     * @param returnContent
     */
    private void doReleaseForAPI(ReleaseParams releaseParams, int releaseId, Map<String, String> returnContent) {
        String compileProductPath = compileBuildService.getProductPathFor(releaseParams.getCompileBuildId());
        if (compileProductPath == null) {
            LOGGER.error("This pipeline is compile fail and don't have product! compileId:{}, releaseId:{}",
                    releaseParams.getCompileBuildId(), releaseId);
            returnContent.put("ERR_MSG", "发布失败，请重新构建本次流水线在发布！");
            returnContent.put("STATUS", "FAIL");
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("releaseId", String.valueOf(releaseId));
        params.put("module", releaseParams.getModule());
        params.put("version", releaseParams.getVersion());
        params.put("username", releaseParams.getReleaseUser());
        params.put("compileProductPath", compileProductPath);
        String result = HttpClientUtil.post(CiHomeReadConfig.getConfigValueByKey("cihome.release.api"), params);
        ReleaseResponseBean releaseResponesBean = GSON.fromJson(result, new TypeToken<ReleaseResponseBean>(){}.getType());
        if (releaseResponesBean.getReleaseStatus().equals(ReleaseStatus.RUNNING)) {
            returnContent.put("ERR_MSG", "正在发布！");
            returnContent.put("STATUS", "RUNNING");
            LOGGER.info("Release request is successful and running! releaseId:{}", releaseId);
        } else if (releaseResponesBean.getReleaseStatus().equals(ReleaseStatus.FAIL)) {
            returnContent.put("ERR_MSG", "请求失败，请重新发布！");
            returnContent.put("STATUS", "FAIL");
            LOGGER.info("Release request is fail! releaseId:{}", releaseId);
        }
    }

    /**
     * 对回调服务进行处理
     * @param responseBean
     */
    public void callbackRelease(ReleaseResponseBean responseBean) {
        CiHomeRelease ciHomeRelease = releaseDao.findById(responseBean.getReleaseId());
        if (ciHomeRelease == null) {
            LOGGER.error("This release:{} is not exist! callback fail!", responseBean.getReleaseId());
        }
        ciHomeRelease.setReleaseStatus(responseBean.getReleaseStatus());
        ciHomeRelease.setProductPath(responseBean.getReleaseProductPath());
        releaseDao.saveOrUpdate(ciHomeRelease);
        LOGGER.info("Callback release service is successful! Releasing success! responseBean:{}", responseBean);
    }

    /**
     * 获得前三位版本号，和第四位
     * @param moduleId
     * @param branchName
     * @return
     */
    @Override
    public String getThreeVersion(int moduleId, String branchName) {
        ConditionAndSet conditionAndSet = new ConditionAndSet();
        conditionAndSet.put("moduleId", moduleId);
        conditionAndSet.put("branchName", branchName);
        List<OrderCondition> orders = new ArrayList<>();
        orders.add(new DescOrder("id"));
        List<CiHomeRelease> ciHomeReleases = releaseDao.findHeadByProperties(conditionAndSet, orders, 0, 1);
        Map<String, String> result = new HashMap<>();
        result.put("THREE_VERSION", "");
        result.put("FOURTH_VERSION", "");
        if (ciHomeReleases != null && ciHomeReleases.size() != 0) {
            String version = ciHomeReleases.get(0).getVersion();
            String threeVersion = StringUtils.substringBeforeLast(version, ".");
            String fourthVersion = StringUtils.substringAfterLast(version, ".");
            result.put("THREE_VERSION", threeVersion);
            result.put("FOURTH_VERSION", String.valueOf(Integer.valueOf(fourthVersion) + 1));
        } else {
            CiHomeBranch ciHomeBranch = branchService.getBranchByModule(moduleId, branchName);
            if (ciHomeBranch != null) {
                result.put("THREE_VERSION", ciHomeBranch.getVersion());
                result.put("FOURTH_VERSION", "1");
            }
        }
        return GSON.toJson(result);
    }

    /**
     * 获取发布记录
     * @param username
     * @param module
     * @param releaseId
     * @return
     */
    public List<ReleaseDetailBean> getCiHomeReleaseDetail(String username, String module, int releaseId) {
        CiHomeModule ciHomeModule = moduleService.getModuleByUserAndModule(username, module);
        ConditionAndSet conditionAndSet = new ConditionAndSet();
        conditionAndSet.put("moduleId", ciHomeModule.getId());
        List<OrderCondition> orderConditions = new ArrayList<>();
        orderConditions.add(new DescOrder("id"));
        List<CiHomeRelease> ciHomeReleases = releaseDao.findHeadByProperties(conditionAndSet, orderConditions, releaseId, 15);
        List<ReleaseDetailBean> releaseDetails = new ArrayList<>();
        if (ciHomeReleases != null) {
            for (CiHomeRelease ciHomeRelease : ciHomeReleases) {
                ReleaseDetailBean releaseDetail = new ReleaseDetailBean();
                BeanUtils.copyProperties(ciHomeRelease, releaseDetail);
                releaseDetail.setProductPath(FTPUtils.getDownloadUrl(ciHomeRelease.getProductPath()));
                releaseDetails.add(releaseDetail);
            }
        }
        return releaseDetails;
    }

}
