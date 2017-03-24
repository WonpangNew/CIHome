package com.jlu.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by niuwanpeng on 17/3/24.
 */
public class DateUtil {

    private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 获取当前时间格式化输出
     * @return
     */
    public static String getNowDateFormat() {
        return df.format(new Date());
    }
}
