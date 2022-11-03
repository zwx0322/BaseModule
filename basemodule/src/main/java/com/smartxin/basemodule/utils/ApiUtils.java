package com.smartxin.basemodule.utils;


import com.google.gson.Gson;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ApiUtils {

    public static RequestBody getRequestBody(String obj) {
        return RequestBody.create(obj, MediaType.parse("application/json; charset=utf-8"));
    }

    public static RequestBody getRequestBody(Object model) {
        Gson gson = new Gson();
        String obj = gson.toJson(model);
        return getRequestBody(obj);
    }

    public static RequestBody getRequestBodyBySimpleParam(Object... mReq) {
        String result = "{}";
        if (mReq != null && mReq.length > 0) {
            JSONObject jsonObject = new JSONObject();
            int dataSize = mReq.length;
            try {
                for (int i = 0; i < dataSize; i += 2) {
                    jsonObject.put(mReq[i].toString(), mReq[i + 1]);
                }
                result = jsonObject.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return RequestBody.create(result, MediaType.parse("application/json; charset=utf-8"));
    }

    /**
     * 判断接口地址是否需要进行AES加解密操作
     *
     * @param url
     * @return
     */
    public static boolean isUrlNeedAesOperate(String url) {
        if (url.contains("getman") || url.contains("file/upload")){
            return false;
        }
        return true;
    }

    public static boolean isResponseNeedsAesOperate(String url) {
        if (url.contains("getman") || url.contains("file/upload")) {
            return false;
        }
        return true;
    }
}
