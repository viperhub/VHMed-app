package com.doctris.care.utils;

import org.json.JSONObject;

import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RequestBodyUtil {
    public static RequestBody createRequestBody(Map<String, Object> params) {
        JSONObject jsonObject = new JSONObject(params);
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
    }

}
