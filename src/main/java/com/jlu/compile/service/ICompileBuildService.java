package com.jlu.compile.service;

/**
 * Created by niuwanpeng on 17/4/15.
 */
public interface ICompileBuildService {

    /**
     * 接收到hook信息触发编译
     * @return
     */
    String hookTriggerCompile();

    /**
     * 接收到编译结束消息，进行写库等操作
     * @return
     */
    String dealCompileEnd();
}
