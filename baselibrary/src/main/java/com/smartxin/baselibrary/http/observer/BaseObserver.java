package com.smartxin.baselibrary.http.observer;


import androidx.annotation.NonNull;

import com.smartxin.baselibrary.base.BaseViewModel;
import com.smartxin.baselibrary.http.bean.BaseResponseBean;
import com.smartxin.baselibrary.http.error.ResponseThrowable;
import com.smartxin.baselibrary.utils.StringUtils;
import com.smartxin.baselibrary.utils.ToastUtils;

import io.reactivex.observers.DisposableObserver;


/**
 * Author: zhengwenxin
 * CreateDate  : 2021/7/27 15:35
 * Description: 观察者回调基础封装
 */
public abstract class BaseObserver<T extends BaseResponseBean> extends DisposableObserver<T> {

    private BaseViewModel viewModel;

    public BaseObserver() {
    }

    public BaseObserver(BaseViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void onNext(T t) {
        if (viewModel != null){
            viewModel.dismissDialog();
        }
        if (t.isSuccess()){
            onSuccess(t);
        }else if (t.getCode() == 401) {   //用户token过期
            ToastUtils.showShortSafe("登录验证已过期，请重新登录");
            if (viewModel != null){
                viewModel.getTokenOutTime().setValue(null);
            }
        }else {
            ToastUtils.showShortSafe(StringUtils.isEmpty(t.getMsg()) ? "网络异常" :t.getMsg());
            onError("网络异常");
        }
    }

    @Override
    public void onError(@NonNull Throwable e) {
        if (viewModel != null){
            viewModel.dismissDialog();
        }
        if (e instanceof ResponseThrowable) {
            ToastUtils.showShortSafe(((ResponseThrowable) e).message);
        }else {
            ToastUtils.showShortSafe("网络异常");
        }
        onError(e.toString());
    }

    @Override
    public void onComplete() {

    }

    public abstract void onSuccess(T returnModel);

    public abstract void onError(String msg);

}
