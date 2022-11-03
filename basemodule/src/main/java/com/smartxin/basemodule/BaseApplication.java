/*
 *
 *  * Copyright 2018-present KunMinX
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *    http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.smartxin.basemodule;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.smartxin.basemodule.utils.AppManagerUtils;
import com.smartxin.basemodule.utils.LogUtils;
import com.smartxin.basemodule.utils.Utils;
import com.smartxin.basemodule.utils.glide.OkHttpUrlLoader;



import java.io.InputStream;

;

/**
 * Author: zhengwenxin
 * CreateDate: 2022/2/19 16:59
 * Description: 基础Application
 **/
public abstract class BaseApplication extends Application implements ViewModelStoreOwner {

    public static String mServerUrl;

    private ViewModelStore mAppViewModelStore;

    private static Application sInstance;

    /**
     * 获得当前app运行的Application
     */
    public static Application getInstance() {
        if (sInstance == null) {
            throw new NullPointerException("please inherit BaseApplication or call setApplication.");
        }
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAppViewModelStore = new ViewModelStore();
        mServerUrl = initServerUrl();
        setApplication(this);
    }

    @NonNull
    @Override
    public ViewModelStore getViewModelStore() {
        return mAppViewModelStore;
    }

    protected abstract String initServerUrl();

    /**
     * 当主工程没有继承BaseApplication时，可以使用setApplication方法初始化BaseApplication
     *
     * @param application
     */
    public static synchronized void setApplication(@NonNull Application application) {
        sInstance = application;
        //初始化基础工具类
        Utils.init(application);
        LogUtils.init(true);
        //Glide加载图片重写新的网络请求方式，忽略证书校验（针对全景监控）
        Glide.get(application).getRegistry().replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory());
        //注册监听每个activity的生命周期,便于堆栈式管理
        application.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {

            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                AppManagerUtils.getAppManagerUtils().addActivity(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {
            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                AppManagerUtils.getAppManagerUtils().removeActivity(activity);
            }
        });
    }
}
