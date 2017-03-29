package com.android.ge.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @name JsonParseUtil.java
 */

public class JsonParseUtil {
    public static String jsonToString(String result, String key)
            throws Exception {
        JSONObject jsonObject = new JSONObject(result);

        if (!jsonObject.isNull(key))
            return jsonObject.getString(key);
        return "";
    }

    public static int jsonToInt(String json, String key) throws Exception {
        if (!TextUtils.isEmpty(json)) {
            JSONObject jsonObject = new JSONObject(json);
            if (!jsonObject.isNull(key))
                return jsonObject.getInt(key);
        }
        return -1;
    }

    public static boolean jsonToBoolean(String json, String key)
            throws Exception {

        if (!TextUtils.isEmpty(json)) {
            JSONObject jsonObject = new JSONObject(json);
            if (!jsonObject.isNull(key))
                return jsonObject.getBoolean(key);
        }

        return false;
    }

    public static int jsonToInt(JSONObject jsonObject, String key)
            throws Exception {
        if (!jsonObject.isNull(key))
            return jsonObject.getInt(key);
        return -1;
    }

    public static String jsonToString(JSONObject jsonObject, String key)
            throws Exception {
        if (!jsonObject.isNull(key)) {
            String value = jsonObject.getString(key);
            if (!value.equalsIgnoreCase("null")) {
                return value;
            }
        }
        return "";
    }

    public static boolean jsonToBoolean(JSONObject jsonObject, String key)
            throws Exception {
        if (!jsonObject.isNull(key)) {
            return jsonObject.getBoolean(key);
        }
        return false;
    }

    public static <T> T jsonToBean(String jsonResult, Class<T> clz)
            throws Exception {
        Gson gson = new Gson();
        T t = gson.fromJson(jsonResult, clz);
        return t;
    }

    public static <T> ArrayList<T> jsonToArray(String s, Class<T> clz) {
        Gson gson = new Gson();
        ArrayList<T> t = gson.fromJson(s, new TypeToken<ArrayList<T>>() {
        }.getType());
        return t;
    }



}
