package com.jlu.common.utils;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by niuwanpeng on 17/3/25.
 */
public class HttpClientAuth {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientAuth.class);

    /**
     * 输入用户名、密码，返回一个httpClient对象
     * @param username
     * @param password
     * @return
     */
    public static CloseableHttpClient getHttpClient(String username, String password) {
        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username, password);
        provider.setCredentials(AuthScope.ANY, credentials);
        return  HttpClients.custom().setDefaultCredentialsProvider(provider).build();
    }

    /**
     * 带权限的post请求
     * @param url
     * @param username
     * @param password
     * @return
     */
    public static String post(String url, String username, String password) {
        CloseableHttpClient httpClient = getHttpClient(username, password);
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "application/json");
        String result = "";
        try {
            List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
            httpPost.setEntity(new UrlEncodedFormEntity(urlParameters));
            CloseableHttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity);
            httpClient.close();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 以cmd方式执行curl命令
     * @param cmds
     * @return
     */
    public static String curlByCMD(String []cmds) {
        ProcessBuilder processBuilder = new ProcessBuilder(cmds);
        processBuilder.redirectErrorStream(true);
        StringBuffer result = new StringBuffer();
        try {
            Process process = processBuilder.start();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                result.append("\t"+line);
            }
            bufferedReader.close();
            LOGGER.info("Request cmd:{} successful !", cmds);
        } catch (IOException e) {
           LOGGER.error("Request cmd:{} failed ! password:{}", cmds);
        }
        return result.toString();
    }

    /**
     * 创建webhook
     * @param username
     * @param password
     * @param url
     * @return
     */
    public static String curlCreatHook(String username, String password, String url) {
        String params = "{\"name\": \"web\",\"active\": true,\"events\": [\"push\"],\"config\": {\"url\": \"http://example.com/webhook\",\"content_type\": \"json\"}}";
        String []cmds = {"curl", "-s", "-u", username + ":" + password, "-X", "POST", "--data", params, url};
        return curlByCMD(cmds);
    }

    /**
     * 默认的请求方式
     * @param username
     * @param password
     * @param url
     * @return
     */
    public static String curlDefaultCmd(String username, String password, String url) {
        String []cmds = {"curl", "-s", "-u", username + ":" + password, url};
        return curlByCMD(cmds);
    }

}
