package com.smartxin.baselibrary.base;

import androidx.lifecycle.ViewModel;

import com.kunminx.architecture.ui.callback.UnPeekLiveData;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


/**
 * Author: zhengwenxin
 * CreateDate  : 2021/7/7 11:16
 * Description: 封装抽象ViewModel
 */
public class BaseViewModel extends ViewModel {

    //管理RxJava，主要针对RxJava异步操作造成的内存泄漏
    private CompositeDisposable mCompositeDisposable;

    //请求数据成功，自定义变更UI事件
    private UnPeekLiveData<Void> dataSuccess;

    //请求数据成功,刷新列表展示事件
    private UnPeekLiveData<Boolean> refreshAdapter;

    //显示加载框事件
    private UnPeekLiveData<String> showDialogEvent;

    //关闭加载框事件
    private UnPeekLiveData<Void> dismissDialogEvent;

    private UnPeekLiveData<Void> finishActivity;

    //token过期事件
    private UnPeekLiveData<Void> tokenOutTime;

    public BaseViewModel() {
        this.mCompositeDisposable = new CompositeDisposable();
    }

    protected void addSubscribe(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    public UnPeekLiveData<Void> getDataSuccess() {
        return dataSuccess = createLiveData(dataSuccess);
    }

    public UnPeekLiveData<Boolean> getRefreshAdapter() {
        return refreshAdapter = createLiveData(refreshAdapter);
    }

    public UnPeekLiveData<String> getShowDialogEvent() {
        return showDialogEvent = createLiveData(showDialogEvent);
    }

    public UnPeekLiveData<Void> getDismissDialogEvent() {
        return dismissDialogEvent = createLiveData(dismissDialogEvent);
    }

    public UnPeekLiveData<Void> getFinishActivity() {
        return finishActivity = createLiveData(finishActivity);
    }

    public UnPeekLiveData<Void> getTokenOutTime() {
        return tokenOutTime = createLiveData(tokenOutTime);
    }
    /**
     * 展示加载框
     */
    public void showDialog() {
        showDialog("请稍后");
    }

    /**
     * 展示加载框
     *
     * @param title
     */
    public void showDialog(String title) {
        getShowDialogEvent().postValue(title);
    }

    /**
     * 关闭加载框
     */
    public void dismissDialog() {
        getDismissDialogEvent().setValue(null);
    }

    /**
     * 构建一次性消费的LiveData，防止数据倒灌
     *
     * @param liveData
     * @param <T>
     * @return
     */
    public <T> UnPeekLiveData<T> createLiveData(UnPeekLiveData<T> liveData) {
        if (liveData == null) {
            liveData = new UnPeekLiveData.Builder<T>().setAllowNullValue(true).create();
        }
        return liveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        //ViewModel销毁时会执行，同时取消所有异步任务
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }
}
