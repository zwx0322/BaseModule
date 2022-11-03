package com.smartxin.basemodule.http.interceptor;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.smartxin.basemodule.http.bean.RequestFinalBean;
import com.smartxin.basemodule.utils.AESSecretUtil;
import com.smartxin.basemodule.utils.LogUtils;

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
 * Description: 加密接口拦截器
 */
public class EncryptionInterceptor implements Interceptor {

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        //请求
        Request request = chain.request();
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        if ("GET".equals(request.method())) {

            request = request.newBuilder()
                    .method(request.method(), request.body())
                    .build();

        }else {
            RequestBody oldRequestBody = request.body();

            Buffer requestBuffer = new Buffer();
            if (oldRequestBody != null) {
                oldRequestBody.writeTo(requestBuffer);
            }
            String oldBodyStr = requestBuffer.readUtf8();
            requestBuffer.close();
            LogUtils.json(oldBodyStr);
            //生成随机AES密钥并用serverPublicKey进行RSA加密
            String newBodyStr = AESSecretUtil.encrypt(oldBodyStr);
            //封装统一请求格式 （根据项目需求是否修改）
            RequestFinalBean finalBean = new RequestFinalBean(newBodyStr);
            Gson gson = new GsonBuilder().disableHtmlEscaping().create();  //Gson解析遇到转义的问题
            String result = gson.toJson(finalBean);
            RequestBody newBody = RequestBody.create(mediaType, result);

            //构造新的request
            request = request.newBuilder()
                    .method(request.method(), newBody)
                    .build();
        }

        //响应
        Response response = chain.proceed(request);

            //获取响应头
            ResponseBody oldResponseBody = response.body();
            String oldResponseBodyStr = oldResponseBody.string();

            //对返回数据进行解密
            String newResponseBodyStr = AESSecretUtil.decrypt(oldResponseBodyStr);

            oldResponseBody.close();
            //构造新的response
            LogUtils.json(newResponseBodyStr);
            ResponseBody newResponseBody = ResponseBody.create(mediaType, newResponseBodyStr);
            response = response.newBuilder().body(newResponseBody).build();

        //返回
        return response;

    }
}
