package com.maifeng.fashiongo.util;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JsonUtil {  
	  
    /** 
     * ��һ��map���json�ַ��� 
     * @param map 
     * @return 
     */  
    public static String parseMapToJson(Map<?, ?> map) {  
        try {  
            Gson gson = new Gson();  
            return gson.toJson(map);  
        } catch (Exception e) {  
        }  
        return null;  
    }  
  
    /** 
     * ��һ��json�ַ�����ɶ��� 
     * @param json 
     * @param cls 
     * @return 
     */  
    public static <T> T parseJsonToBean(String json, Class<T> cls) {  
        Gson gson = new Gson();  
        T t = null;  
        try {  
            t = gson.fromJson(json, cls);  
            LogUtil.i(t, "�����ɹ�");
        } catch (Exception e) { 
        	LogUtil.i(t, "����ʧ��");
        }  
        return t;  
    }  
  
    /** 
     * ��json�ַ������map 
     * @param json 
     * @return 
     */  
    public static HashMap<String, Object> parseJsonToMap(String json) {  
        Gson gson = new Gson();  
        Type type = new TypeToken<HashMap<String, Object>>() {  
        }.getType();  
        HashMap<String, Object> map = null;  
        try {  
            map = gson.fromJson(json, type);  
        } catch (Exception e) {  
        }  
        return map;  
    }  
  
    /** 
     * ��json�ַ�����ɼ��� 
     * params: new TypeToken<List<yourbean>>(){}.getType(), 
     *  
     * @param json 
     * @param type  new TypeToken<List<yourbean>>(){}.getType() 
     * @return 
     */  
    public static List<?> parseJsonToList(String json, Type type) {  
        Gson gson = new Gson();  
        List<?> list = gson.fromJson(json, type);  
        return list;  
    }  
  
    /** 
     *  
     * ��ȡjson����ĳ���ֶε�ֵ��ע�⣬ֻ�ܻ�ȡͬһ�㼶��value 
     *  
     * @param json 
     * @param key 
     * @return 
     */  
    public static String getFieldValue(String json, String key) {  
        if (TextUtils.isEmpty(json))  
            return null;  
        if (!json.contains(key))  
            return "";  
        JSONObject jsonObject = null;  
        String value = null;  
        try {  
            jsonObject = new JSONObject(json);  
            value = jsonObject.getString(key);  
        } catch (JSONException e) {  
            e.printStackTrace();  
        }  
        return value;  
    }  
}  
