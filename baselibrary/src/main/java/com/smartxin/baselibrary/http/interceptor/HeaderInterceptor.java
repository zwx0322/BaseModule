package com.smartxin.baselibrary.http.interceptor;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Author: zhengwenxin
 * CreateDate: 2022/4/6 14:16
 * Description: 请求头封装拦截器
 */
public class HeaderInterceptor implements Interceptor {
    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request.Builder builder = originalRequest.newBuilder();
        builder.header("appid", "123124151616");
        builder.header("appkey", "xxxxxxxxx");
        builder.header("signature", "xxxxxxxxxxx");

        Request.Builder requestBuilder = builder.method(originalRequest.method(), originalRequest.body());
        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
