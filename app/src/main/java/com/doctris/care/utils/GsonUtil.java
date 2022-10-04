package com.doctris.care.utils;

import com.google.gson.Gson;

public class GsonUtil {

    private static Gson gson = null;

    private GsonUtil() {
        gson = new Gson();
    }

    public static String toJson(Object object) {
        return gson.toJson(object);
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }
}
