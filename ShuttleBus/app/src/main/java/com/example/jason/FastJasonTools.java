package com.example.jason;

import android.util.Log;

import com.alibaba.fastjson.*;
import com.alibaba.fastjson.serializer.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by ZHANGLU6 on 1/12/2016.
 */
public class FastJasonTools {

    private static SerializeConfig mapping = new SerializeConfig();

    static {
        mapping.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 用fastjson 将json字符串解析为一个 JavaBean
     *
     * @param jsonString
     * @param cls
     * @return
     */
    public static <T> T getParseBean(String jsonString, Class<T> cls) {
        T t = null;
        try {
            t = JSON.parseObject(jsonString, cls);
        } catch (Exception e) {
            Log.e("FastJasonTools", "getParseBean : " + e.getMessage());
        }
        return t;
    }

    /**
     * 用fastjson 将json字符串 解析成为一个 List<JavaBean> 及 List<String>
     *
     * @param jsonString
     * @param cls
     * @return
     */
    public static <T> List<T> getParseBeanArray(String jsonString, Class<T> cls) {
        List<T> list = new ArrayList<T>();
        try {
            list = JSON.parseArray(jsonString, cls);
        } catch (Exception e) {
            Log.e("FastJasonTools", "getParseBeanArray : " + e.getMessage());
        }
        return list;
    }

    /**
     * 用fastjson 将jsonString 解析成 List<Map<String,Object>>
     *
     * @param jsonString
     * @return
     */
    public static List<Map<String, Object>> getParseListMap(String jsonString) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            // 两种写法
            // list = JSON.parseObject(jsonString, new
            // TypeReference<List<Map<String, Object>>>(){}.getType());

            list = JSON.parseObject(jsonString,
                    new TypeReference<List<Map<String, Object>>>() {
                    });
        } catch (Exception e) {
            Log.e("FastJasonTools", "getParseListMap : " + e.getMessage());
        }
        return list;

    }

    public static String toJSONString(Object object) {
        String jsonString = "";
        try {
            jsonString = JSON.toJSONString(object);
        } catch (Exception e) {
            Log.e("FastJasonTools", "toJSONString : " + e.getMessage());
        }
        return jsonString;
    }
}
