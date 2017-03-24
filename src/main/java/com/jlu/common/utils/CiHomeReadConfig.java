package com.jlu.common.utils;

import org.springframework.util.PropertyPlaceholderHelper;
import org.springframework.util.SystemPropertyUtils;

import java.util.List;
import java.util.Properties;

/**
 * Created by niuwanpeng on 17/3/24.
 *
 * 系统常量配置工具类
 */
public class CiHomeReadConfig {

    private static Properties props = null;

    public void setProps(List<Properties> lists) {
        Properties Proper= new Properties();
        for(Properties properties:lists){
            Proper.putAll(properties);
        }
        CiHomeReadConfig.props = Proper;

    }

    /**
     * 解析property的placeholder工具
     */
    private static PropertyPlaceholderHelper helper =
            new PropertyPlaceholderHelper(SystemPropertyUtils.PLACEHOLDER_PREFIX,
                    SystemPropertyUtils.PLACEHOLDER_SUFFIX,
                    SystemPropertyUtils.VALUE_SEPARATOR, false);

    /**
     * 根据配置的key
     * @param configKey
     * @return
     */
    public static String getConfigValueByKey(String configKey) {
        if (props == null) {
            return null;
        }
        return getProperty(configKey);
    }

    private static String getProperty(String key){
        return helper.replacePlaceholders(props.getProperty(key), props);
    }
}
