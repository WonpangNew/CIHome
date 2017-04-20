package com.jlu.github.service;

import net.sf.json.JSONObject;

/**
 * Created by niuwanpeng on 17/4/19.
 */
public interface IGitHubHookService {

    /**
     * 解析hook信息，触发编译
     * @param hookMessage
     */
    void dealHookMessage(JSONObject hookMessage);
}
