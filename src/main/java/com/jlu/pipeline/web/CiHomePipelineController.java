package com.jlu.pipeline.web;

import com.jlu.pipeline.bean.BranchesPipelineBean;
import com.jlu.pipeline.bean.CiHomePipelineBean;
import com.jlu.pipeline.service.ICiHomePipelineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * CI/CD流水线控制器
 * <p>
 * 负责管理和展示持续集成/持续部署流水线的构建信息。
 * 提供主干分支和功能分支的流水线数据查询接口。
 * </p>
 *
 * @author niuwanpeng
 * @since 2017-04-19
 */
@Controller
@RequestMapping("/pipeline")
public class CiHomePipelineController {

    @Autowired
    private ICiHomePipelineService ciHomePipelineService;

    /**
     * 获取主干分支的流水线构建信息
     * <p>
     * 查询指定模块主干分支的CI/CD流水线构建详情。
     * </p>
     *
     * @param username 用户名
     * @param module 模块名称
     * @param pipelineBuildId 流水线构建ID
     * @return 流水线构建信息列表
     */
    @RequestMapping("/v1/pipelineBuilds")
    @ResponseBody
    public List<CiHomePipelineBean> getTrunkPipeline(@RequestParam("username") String username,
                                                     @RequestParam("module") String module,
                                                     @RequestParam("pipelineBuildId") int pipelineBuildId) {
        return ciHomePipelineService.getTrunkPipeline(username, module, pipelineBuildId);
    }

    /**
     * 获取所有功能分支的流水线构建信息
     * <p>
     * 查询指定模块下所有功能分支的流水线构建数据，以分支名称为键的Map形式返回。
     * </p>
     *
     * @param username 用户名
     * @param module 模块名称
     * @param branchId 分支ID
     * @return 以分支名称为键，流水线构建信息列表为值的Map
     */
    @RequestMapping("/v1/branches/pipelineBuilds")
    @ResponseBody
    public Map<String, List<CiHomePipelineBean>> getBranchesPipeline(@RequestParam("username") String username,
                                                                     @RequestParam("module") String module,
                                                                     @RequestParam("branchId") int branchId) {
        return ciHomePipelineService.getBranchesPipeline(username, module, branchId);
    }

    /**
     * 获取指定功能分支的流水线构建信息
     * <p>
     * 查询特定分支的CI/CD流水线构建详情。
     * </p>
     *
     * @param username 用户名
     * @param module 模块名称
     * @param branchName 分支名称
     * @param pipelineBuildId 流水线构建ID
     * @return 流水线构建信息列表
     */
    @RequestMapping("/v1/branch/pipelineBuilds")
    @ResponseBody
    public List<CiHomePipelineBean> getBranchPipeline(@RequestParam("username") String username,
                                                      @RequestParam("module") String module,
                                                      @RequestParam("branchName") String branchName,
                                                      @RequestParam("pipelineBuildId") int pipelineBuildId) {
        return ciHomePipelineService.getBranchPipeline(username, module, branchName, pipelineBuildId);
    }
}
