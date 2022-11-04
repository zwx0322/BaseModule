package com.smartxin.baselibrary.http.interceptor;


import com.smartxin.baselibrary.utils.LogUtils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * Author: zhengwenxin
 * CreateDate  : 2022/1/12 15:25
 * Description: 日志拦截器
 */
public class LogInterceptor implements Interceptor {

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        //请求
        Request request = chain.request();
        RequestBody requestBody = request.body();

        Buffer requestBuffer = new Buffer();
        if (requestBody != null) {
            requestBody.writeTo(requestBuffer);
        }
        String bodyStr = requestBuffer.readUtf8();
        LogUtils.json(bodyStr);
        requestBuffer.close();

        MediaType mMediaType = MediaType.parse("application/json; charset=utf-8");

        //响应
        Response response = chain.proceed(request);
        String responseBodyStr = response.body().string();
        LogUtils.json(responseBodyStr);

        //返回
        return response.newBuilder().body(ResponseBody.create(responseBodyStr, mMediaType)).build();
    }
}
