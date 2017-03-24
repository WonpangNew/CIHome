package com.jlu.common.utils;

import com.jlu.github.bean.GithubRepoBean;
import net.sf.json.JSONArray;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.type.TypeReference;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by niuwanpeng on 17/3/24.
 */
public class JsonUtils {

    public static ObjectMapper getMapper(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }

    /**
     * 将一个对象转换为json格式的字符串
     * @param <T>
     * @param t
     * @return
     */
    public static <T> String getJsonString(T t){
        try {
            StringWriter writer = new StringWriter();
            getMapper().writeValue(writer, t);
            return writer.getBuffer().toString();
        } catch (Exception e) {
            throw new RuntimeException("生成json数据时发生异常:");
        }
    }

    /**
     * 根据json字符串和给定的class,解析为指定class的一个实例
     * @param <T>
     * @param jsonString
     * @param clazz
     * @return
     */
    public static <T> T getObjectByJsonString(String jsonString, Class<T> clazz){
        if(null==jsonString || null==clazz){
            return null;
        }
        try{
            T obj = getMapper().<T>readValue(jsonString, clazz);
            return obj;
        }catch(Exception e){
            throw new RuntimeException("解析json数据时发生异常:");
        }
    }

    /**
     * 根据json字符串和给定的typeReference,解析为指定class的一个实例
     * @param <T>
     * @param jsonString
     * @param type
     * @return
     */
    public static <T> T getObjectByJsonString(String jsonString, TypeReference<T> type){
        try{
            return getMapper().<T>readValue(jsonString, type);
        }catch(Exception e){
            throw new RuntimeException("解析json数据时发生异常:");
        }
    }

    public static ArrayNode getArrayNode()throws Exception {
        return getMapper().createArrayNode();
    }

    /**
     * 根据JSONObject和给定的typeReference,解析为指定class的一个实例
     * @param <T>
     * @param json
     * @param clazz
     * @return
     */
    public static <T> T getObjectByJsonObject(JSONObject json, Class<T> clazz) {
        if (null == json) {
            return null;
        }
        try {
            return getMapper().<T>readValue(json.toString(), clazz);
        } catch (Exception e) {
            throw new RuntimeException("解析json数据时发生异常:");
        }
    }

    public static JsonNode getJsonTree(String json){
        JsonNode node = null;
        try {
            node = JsonUtils.getMapper().readTree(json);
        } catch (IOException e) {
            throw new RuntimeException("生成json node发生异常:");
        }
        return node;
    }

    /**
     * get JSONObject instance
     * @param json
     * @return
     */
    public static JSONObject getJsonObject(String json) {
        try {
            return new JSONObject(json);
        } catch (JSONException e)  {
            return null;
        }
    }

    /**
     * get JSONObject instance
     * @param t
     * @param <T>
     * @return
     */
    public static <T> JSONObject getJsonObject(T t) {
        if (null == t) {
            return null;
        }
        try {
            return new JSONObject(getJsonString(t));
        } catch (JSONException e)  {
            return null;
        }
    }

    /**
     * 把用来描述对象的map转换成想要的对象，map的key是属性名 value是属性值
     * @param map
     * @param t
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static <T> T convertFieldMapToObject(Map<String, Object> map, Class<T> t) throws InstantiationException, IllegalAccessException {
        T o = (T) t.newInstance();
        for(Map.Entry<String, Object> entry : map.entrySet()) {
            try {
                Field field = t.getDeclaredField(entry.getKey());
                field.setAccessible(true);
                if(field.getType() == Long.class && entry.getValue() instanceof Integer) {
                    entry.setValue(((Integer)entry.getValue()).longValue());
                }
                field.set(o, entry.getValue());
            } catch (NoSuchFieldException e) {
                continue;
            } catch (SecurityException e) {
                continue;
            }
        }
        return o;
    }
}
