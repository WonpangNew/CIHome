package com.jlu.common.utils;

import org.apache.commons.lang.StringUtils;

/**
 * Created by niuwanpeng on 17/5/5.
 */
public class FTPUtils {

    private static final String FTP_DOWNLOAD_PRODUCT = "http://localhost:8999/ftp/api/download?remoteDir=%s&remoteFileName=%s";
    /**
     *
     * @param productPath
     * @return
     */
    public static String getDownloadUrl(String productPath) {
        String remoteDir = StringUtils.substringBeforeLast(productPath,"/");
        String remoteFileName = StringUtils.substringAfterLast(productPath, "/");
        return String.format(FTP_DOWNLOAD_PRODUCT, remoteDir, remoteFileName);
    }
}
