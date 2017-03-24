package com.jlu.common.utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.jlu.common.utils.bean.TwoResultBean;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

/**
 * Created by niuwanpeng on 17/3/24.
 */
public class HttpClientUtil {
    public static final Logger LOGGER = LoggerFactory.getLogger(HttpClientUtil.class);
    //设置连接超时时间(单位毫秒)
    public static final int CONNECTION_TIME_OUT = 7000;
    //设置读数据超时时间(单位毫秒)
    public static final int SOCKET_TIME_OUT = 30000;

    public static final String HTML_CONTENT_TYPE = "text/html;charset=UTF-8";
    public static final String DEFAULT_CONTENT_TYPE = "application/x-www-form-urlencoded";
    public static final String TEXT_CONTENT_TYPE = "text/plain";
    public static final String APPLICATION_JSON_CONTENT_TYPE = "application/json";

    private static HttpClient getHttpClient(){
        DefaultHttpClient client = new DefaultHttpClient();
        HttpParams httpParams = client.getParams();
        HttpProtocolParams.setContentCharset(httpParams, HTTP.UTF_8);
        HttpConnectionParams.setConnectionTimeout(httpParams, CONNECTION_TIME_OUT);
        HttpConnectionParams.setSoTimeout(httpParams, SOCKET_TIME_OUT);
        return client;
    }

    /**
     * POST请求方式，注：请求参数在request body中
     *
     * @param url
     * @param params
     * @return
     */
    public static String postWithBody(String url,Map<String,String> params){
        return post4StatusAndContent(url,null,params).getMessage();
    }

    /**
     * POST请求方式<br>
     * 默认contentType=application/x-www-form-urlencoded<br>
     * 默认编码格式 <code>Http.UTF_8</code>
     *
     * @param url
     * @param params
     * @return
     */
    public static String post(String url, Map<String,String> params){
        return post4StatusAndContent(url,DEFAULT_CONTENT_TYPE,params).getMessage();
    }

    /**
     * POST请求方式<br>
     * 默认contentType=application/x-www-form-urlencoded<br>
     * 默认编码格式 <code>Http.UTF_8</code>
     *
     * @param url
     * @param params
     * @param socketTimeout
     * @return
     */
    public static String post(String url, Map<String,String> params, int socketTimeout){
        return post4StatusAndContent(url,DEFAULT_CONTENT_TYPE,params,socketTimeout).getMessage();
    }

    public static TwoResultBean<Integer, String> post4StatusAndContent(String url, String contentType, Map<String,String> params){
        return post4StatusAndContent(url, contentType, params, SOCKET_TIME_OUT);
    }

    /**
     * 处理post参数
     *
     * @param contentType
     * @param params
     * @return
     * @throws UnsupportedEncodingException
     */
    public static StringEntity getStringEntity(String contentType, Map<String,String> params) throws UnsupportedEncodingException{

        if(StringUtils.contains(contentType, DEFAULT_CONTENT_TYPE)){
            UrlEncodedFormEntity reqEntity = new UrlEncodedFormEntity(getFormParams(params), HTTP.UTF_8);
            reqEntity.setContentType(DEFAULT_CONTENT_TYPE);
            return reqEntity;
        }else{
            Gson gson = new Gson();
            String paramsStr  = gson.toJson(params);
            StringEntity reqEntity = new StringEntity(paramsStr);
            return reqEntity;
        }
    }


    /**
     * POST方法--接受jason参数
     * @param url                                请求URL
     * @param contentType                 content类型，默认为DEFAULT_CONTENT_TYPE
     * @param jsonParams                   jason参数
     * @return   结果
     */
    public static String  post4Json(String url,String contentType,String jsonParams){
        String returnContent = "";
        int returnCode = -1;
        HttpClient client = null;
        try {
            client = getHttpClient();
            HttpConnectionParams.setConnectionTimeout(client.getParams(), 60000);
            HttpPost httpPost = new HttpPost(url);
            StringEntity reqEntity = new StringEntity(jsonParams,contentType, "utf-8");
            httpPost.setEntity(reqEntity);
            LOGGER.info("[HTTP POST]:{},[PARAMS]:{} starting...", url, jsonParams);
            HttpResponse response = client.execute(httpPost);
            returnCode = response.getStatusLine().getStatusCode();
            if(returnCode == HttpStatus.SC_OK){
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    returnContent = EntityUtils.toString(entity, "UTF-8");
                }
            }else if(returnCode == 302){
                returnContent = response.getFirstHeader("location").getValue();
            } else {
                LOGGER.error("Failed to post URL: " + url + " with response: " + response.getStatusLine().getReasonPhrase());
            }
            LOGGER.info("[HTTP POST]:{},[PARAMS]:{} end!!!", url, jsonParams);
        }catch (Exception e) {
            LOGGER.error("Exception happened when request the url:" + url,e);
        } finally {
            if(client != null) {
                try {
                    client.getConnectionManager().shutdown();
                } catch (Exception ex) {}
            }
        }
        return returnContent;
    }

    /**
     * 设置HttpPost请求参数
     *
     * @param argsMap
     * @return
     */
    private static List<NameValuePair> getFormParams(Map<String, String> argsMap){

        List<NameValuePair> formParams = new ArrayList<NameValuePair>();
        //设置请求参数
        if (null != argsMap && argsMap.size() > 0) {
            for(Map.Entry<String, String> entry : argsMap.entrySet()){
                formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        return formParams;
    }

    public static String get(String url, Map<String, String> params, int socketTimeout) {

        return getUrlForDefinedTimeout(url, HTTP.UTF_8, params, socketTimeout);
    }

    public static String get(String url, Map<String,String> params){

        return get(url,HTTP.UTF_8,params);
    }

    public static String getWithQueryStr(String url, String... params){

        Map<String, String> paramMap = new HashMap<String, String>();
        if(null != params && params.length >= 2){
            for(int i=0; i<params.length-1; i+=2){
                paramMap.put(params[i], params[i+1]);
            }
        }
        return get(url, HTTP.UTF_8, paramMap);
    }
    /**
     * getWithQueryStr4Time
     * @param url String
     * @param socketTimeout String
     * @param params String...
     * @return String
     */
    public static String getWithQueryStr4Time(String url, int socketTimeout,String... params){
        Map<String, String> paramMap = new HashMap<String, String>();
        if(null != params && params.length >= 2){
            for(int i=0; i<params.length-1; i+=2){
                paramMap.put(params[i], params[i+1]);
            }
        }
        return getUrlForDefinedTimeout(url, HTTP.UTF_8, paramMap, socketTimeout);
    }

    public static String get(String url,String charset,Map<String,String> params){
        return getUrlForDefinedTimeout(url, charset, params, CONNECTION_TIME_OUT);
    }

    public static String getFormatUrl(String url){
        url = StringUtils.defaultString(url);
        if(StringUtils.isBlank(url)){
            return "";
        }
        StringBuffer sbr = new StringBuffer();
        int index = url.indexOf("//");
        if(index>0){
            sbr.append(url.substring(0, index+2));
            StringBuffer sb = new StringBuffer(url.substring(index+2)).append("/");
            String s = sb.toString().replaceAll("/+", "/");
            sbr.append(s);
            return sbr.substring(0,sbr.length()-1).toString();
        }
        return "";
    }

    public static String getUTF8Parameter(String parameter){
        try {
            parameter = java.net.URLEncoder.encode(parameter,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            return parameter;
        }
        return parameter;
    }
    /**
     * http get请求时，允许设置超时时间（自定义超时时间大约默认时间）
     * @param url
     * @param charset
     * @param params
     * @param timeout 超时时间
     * @return
     */
    public static String getUrlForDefinedTimeout(String url, String charset, Map<String,String> params, int socketTimeout){

        String returnContent = "";
        HttpClient client = null;
        try {
            client = getHttpClient();
            if(socketTimeout>SOCKET_TIME_OUT){
                HttpConnectionParams.setSoTimeout(client.getParams(), socketTimeout);
            }
            StringBuilder sb = new StringBuilder(url);
            if(null != params && params.size() > 0){
                sb.append("?");
                for(Map.Entry<String, String> entry :params.entrySet()){
                    sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
                }
                sb.deleteCharAt(sb.length() - 1);
            }
            url = sb.toString();
            if(null!=url){
                url = url.replaceAll(" ", "%20");
            }

            LOGGER.info("[HTTP GET]:{} starting...", url);
            HttpGet httpGet = new HttpGet(url);
            HttpResponse response = client.execute(httpGet);
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    returnContent = EntityUtils.toString(entity, charset);
                }
            }else {
                LOGGER.error("Failed to get URL: " + url + " with response: " + response.getStatusLine().getReasonPhrase());
            }
            LOGGER.info("[HTTP GET]:{} end!!!", url);
        }catch (Exception e) {
            LOGGER.error("Exception happened when request the url:"+url,e);
        } finally {
            if(client != null) {
                try {
                    client.getConnectionManager().shutdown();
                } catch (Exception ex) {}
            }
        }
        return returnContent;
    }

    /**
     * 获取请求
     *
     * @param url
     * @param contentType
     * @param method
     * @param params
     * @return
     * @throws UnsupportedEncodingException
     */
    public static HttpUriRequest getRequest(String url, String contentType, String method,
                                            Map<String, String> params) throws UnsupportedEncodingException{

        HttpUriRequest request = null;
        if(HttpPost.METHOD_NAME.equalsIgnoreCase(method)){
            HttpPost post = new HttpPost(url);
            HttpEntity entity = getStringEntity(contentType, params);
            post.setEntity(entity);
            request = post;
        } else{
            request = new HttpGet(toGetUrl(url, params));
        }
        return request;
    }

    private static String toGetUrl(String url, Map<String, String> params){

        if(StringUtils.isBlank(url)){
            return StringUtils.EMPTY;
        }
        String uri = url;
        String arch = StringUtils.EMPTY;
        int index = url.indexOf("#");
        if(index >= 0){
            arch = StringUtils.substring(url, index);
            uri = StringUtils.substring(url, 0, index);
        }

        StringBuilder sb = new StringBuilder(uri);
        if(null != params && params.size() > 0){
            if(url.indexOf("?") < 0){
                sb.append("?");
            }else{
                sb.append("&");
            }
            for(Map.Entry<String, String> entry :params.entrySet()){
                sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append(arch);
        url = sb.toString();
        if(null!=url){
            url = url.replaceAll(" ", "%20");
        }
        return url;
    }

    /**
     *
     * @param url
     * @param contentType
     * @param params
     * @param socketTimeout
     * @return
     */
    public static TwoResultBean<Integer, String> post4StatusAndContent(
            String url,String contentType,Map<String,String> params,int socketTimeout){
        String returnContent = "";
        int returnCode = -1;
        HttpClient client = null;
        try {
            client = getHttpClient();
            if(socketTimeout > SOCKET_TIME_OUT){
                HttpConnectionParams.setSoTimeout(client.getParams(), socketTimeout);
            }
            HttpPost httpPost = new HttpPost(url);
            Gson gson = new Gson();
            String paramsStr  = gson.toJson(params);
            StringEntity reqEntity = getStringEntity(contentType,params);
            httpPost.setEntity(reqEntity);
            LOGGER.info("[HTTP POST]:{},[PARAMS]:{} starting...", url, paramsStr);
            HttpResponse response = client.execute(httpPost);
            returnCode = response.getStatusLine().getStatusCode();
            if(returnCode == HttpStatus.SC_OK){
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    returnContent = EntityUtils.toString(entity, "UTF-8");
                }
            }else if(returnCode == 302){
                returnContent = response.getFirstHeader("location").getValue();
            }else {
                LOGGER.error("Failed to post URL: " + url +
                        " with response: " + response.getStatusLine().getReasonPhrase());
            }
            LOGGER.info("[HTTP POST]:{},[PARAMS]:{} end!!!", url, paramsStr);
        }catch (Exception e) {
            LOGGER.error("Exception happened when request the url:"+url,e);
            returnContent = e.toString();
        } finally {
            if(client != null) {
                try {
                    client.getConnectionManager().shutdown();
                } catch (Exception ex) {}
            }
        }
        return new TwoResultBean<Integer, String>(returnCode,returnContent);
    }

    /**
     * 状态码是否Ok
     *
     * @param statusCode
     * @return
     */
    public static boolean isOk(int statusCode){

        return HttpStatus.SC_OK == statusCode
                ||HttpStatus.SC_MOVED_TEMPORARILY==statusCode
                ||HttpStatus.SC_MOVED_PERMANENTLY==statusCode;
    }

    public static class BaseResponse {

        private Map<String, String> headers;
        private int statusCode;
        private String result;

        public BaseResponse(){
            super();
        }

        public BaseResponse(int statusCode, String result) {
            super();
            this.statusCode = statusCode;
            this.result = result;
        }

        /**
         * @return the heads
         */
        public Map<String, String> getHeaders() {
            return headers;
        }
        /**
         * @param heads the heads to set
         */
        public void setHeaders(Map<String, String> headers) {
            this.headers = headers;
        }
        /**
         * @return the statusCode
         */
        public int getStatusCode() {
            return statusCode;
        }
        /**
         * @param statusCode the statusCode to set
         */
        public void setStatusCode(int statusCode) {
            this.statusCode = statusCode;
        }
        /**
         * @return the result
         */
        public String getResult() {
            return result;
        }
        /**
         * @param result the result to set
         */
        public void setResult(String result) {
            this.result = result;
        }

        public void putHeader(String key, String value){
            if(null == headers){
                headers = new LinkedHashMap<String, String>();
            }
            headers.put(key, value);
        }

        public String getHeader(String key){
            if(null != headers){
                return headers.get(key);
            }
            return null;
        }

        public boolean isOk(){
            return HttpClientUtil.isOk(statusCode);
        }
    }
}
