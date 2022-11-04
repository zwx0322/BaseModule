package com.smartxin.baselibrary.http.base;

import androidx.annotation.NonNull;

import com.smartxin.baselibrary.constants.BaseConstants;
import com.smartxin.baselibrary.http.error.HttpErrorHandler;
import com.smartxin.baselibrary.http.interceptor.LogInterceptor;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author: zhengwenxin
 * CreateDate: 2022/2/19 15:22
 * Description: RetrofitClient封装单例类, 实现网络请求
 */
public abstract class BaseNetworkApi {

    private String mBaseUrl;

    private OkHttpClient client;

    private static final String TAG = "Retrofit";

    private static HashMap<String, Retrofit> retrofitHashMap = new HashMap<>();

    protected BaseNetworkApi(String mBaseUrl) {
        this.mBaseUrl = mBaseUrl;
    }

    protected Retrofit getRetrofit(Class service) {

        if (retrofitHashMap.get(service.getName()) != null)
            return retrofitHashMap.get(service.getName());

        //支持RxJava2
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                //支持RxJava2
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getOkHttpClient())
                .build();
        retrofitHashMap.put(service.getName(), retrofit);
        return retrofit;
    }

    public <T> T getService(Class<T> service) {
        return getRetrofit(service).create(service);
    }

    private OkHttpClient getOkHttpClient() {
        if (client == null) {
            //添加log拦截器
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            //提供接口 ，按需添加log拦截器

            if (getInterceptor() != null && getInterceptor().size() != 0) {
                for (Interceptor interceptor : getInterceptor()) {
                    builder.addInterceptor(interceptor);
                }
            }

            builder.addInterceptor(new LogInterceptor())
                    .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
                    .connectTimeout(BaseConstants.HTTP_TIMEOUT_LIMIT, TimeUnit.SECONDS)
                    .readTimeout(BaseConstants.HTTP_TIMEOUT_LIMIT, TimeUnit.SECONDS)
                    .writeTimeout(BaseConstants.HTTP_TIMEOUT_LIMIT, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true);
            client = builder.build();
        }

        return client;
    }

    /**
     * 线程变化
     * 捕获异常封装
     */
    public static <T> ObservableTransformer<T, T> applySchedulers() {
        return new ObservableTransformer<T, T>() {
            @NonNull
            @Override
            public ObservableSource<T> apply(@NonNull Observable<T> upstream) {
                Observable<T> observable = upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .onErrorResumeNext(new HttpErrorHandler());  //捕获异常
                return observable;
            }
        };
    }

    /**
     * 提供不同内容的拦截器接口
     */
    protected abstract List<Interceptor> getInterceptor();
}
