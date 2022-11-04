package com.smartxin.baselibrary.http;

import com.smartxin.baselibrary.http.base.BaseNetworkApi;

import java.util.List;

import okhttp3.Interceptor;

/**
 * Author: zhengwenxin
 * CreateDate: 2022/2/19 16:59
 * Description: 示例
 *              抽象化基础网络请求类，根据多域名活请求地址创建请求客户端对象
 */

public class ExamplesApi extends BaseNetworkApi {

    private static ExamplesApi mInstance;

    /**
     * 获取模块接口单例对象
     * */
    public static ExamplesApi getInstance(){
        if (mInstance == null){
            synchronized (ExamplesApi.class){
                if (mInstance == null){
                    mInstance = new ExamplesApi();
                }
            }
        }
        return mInstance;
    }

    /**
     * 设置当前接口对应的 请求URL
     * */
    protected ExamplesApi() {
        super("");
    }

    /**
     * 返回拦截器
     * 按需选择 是否需要加密
     * 或添加其他定制化拦截器
     *
     * @return*/
    @Override
    protected List<Interceptor> getInterceptor() {
        return null;
    }

    public static void clearInstance() {
        mInstance = null;
    }
}
