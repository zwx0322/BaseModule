package com.smartxin.baselibrary.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.gyf.immersionbar.ImmersionBar;
import com.smartxin.baselibrary.BaseApplication;
import com.smartxin.baselibrary.utils.AppManagerUtils;
import com.smartxin.baselibrary.utils.ToastUtils;
import com.smartxin.baselibrary.widget.LoadingDialog;


/**
 * Author: zhengwenxin
 * CreateDate: 2022/2/18 17:18
 * Description: 基础Activity
 */
public abstract class BaseDbActivity<V extends ViewDataBinding> extends AppCompatActivity implements IBaseView{

    /**
     * 几秒内连续点击返回键是退出
     */
    protected final long QUIT_CLICK_TIME = 2000L;
    /**
     * 上一次点击返回键的时间
     */
    protected long lastClickTime = 0L;

    protected V mBinding;

    private ViewModelProvider mApplicationProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //私有的初始化Databinding和ViewModel方法
        initViewDataBinding(savedInstanceState);
        AppManagerUtils.getAppManagerUtils().addActivity(this);
        ImmersionBar.with(this).init();

        //页面渲染视图
        initView();
        //页面数据初始化方法
        initData();
        //绑定控件点击事件监听的方法
        initListener();
    }

    /**
     * 注入绑定
     */
    protected void initViewDataBinding(Bundle savedInstanceState) {
        mBinding = DataBindingUtil.setContentView(this, initContentView(savedInstanceState));
        //支持LiveData绑定xml，数据改变，UI自动会更新
        mBinding.setLifecycleOwner(this);
    }

    /**
     * 获取application层级ViewModel
     *
     * @param modelClass 需要自行生成，在需要的页面创建并取得监听
     *
     * */
    protected <T extends ViewModel> T getApplicationScopeViewModel(@NonNull Class<T> modelClass) {
        if (mApplicationProvider == null) {
            mApplicationProvider = new ViewModelProvider((BaseApplication) this.getApplicationContext(),
                    getAppFactory());
        }
        return mApplicationProvider.get(modelClass);
    }

    /**
     * 获取 application层级工厂
     *
     * @return
     */
    private ViewModelProvider.Factory getAppFactory() {
        Application application = BaseApplication.getInstance();
        return (ViewModelProvider.Factory) ViewModelProvider.AndroidViewModelFactory.getInstance(application);
    }

    public void showDialog(String title) {
        LoadingDialog.showLoadingDialog(this, title, false);
    }

    public void dismissDialog() {
        LoadingDialog.dismissLoadingDialog();
    }

    /**
     * 跳转页面
     *
     * @param clz 所跳转的目的Activity类
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(this, clz));
    }

    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 关闭页面
     *
     * @param view
     */
    public void finishActivity(View view) {
       finish();
    }

    /**
     * 2秒内点击两次退出
     */
    public void quit() {
        if (System.currentTimeMillis() - lastClickTime <= QUIT_CLICK_TIME) {
            AppManagerUtils.getAppManagerUtils().AppExit();
        } else {
            ToastUtils.showShort("再次点击退出");
            lastClickTime = System.currentTimeMillis();
        }
    }

    /**
     * 隐藏软键盘
     */
    public static void hideSoftKeyboard(Activity activity) {
        hideSoftKeyboard(activity.getCurrentFocus(),activity);
    }

    /**
     * 隐藏软键盘
     */
    public static void hideSoftKeyboard(View view,Activity activity) {
        if (view != null) {
            IBinder token = view.getWindowToken();
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 隐藏软键盘
     */
    public static void hideSoftKeyboard(MotionEvent event, View view, Activity activity){
        try {
            if (view != null && view instanceof EditText) {
                int[] location = {0, 0};
                view.getLocationInWindow(location);
                int left = location[0], top = location[1], right = left
                        + view.getWidth(), bootom = top + view.getHeight();
                // 判断焦点位置坐标是否在空间内，如果位置在控件外，则隐藏键盘
                if (event.getRawX() < left || event.getRawX() > right
                        || event.getY() < top || event.getRawY() > bootom) {
                    // 隐藏键盘
                    hideSoftKeyboard(view,activity);
                    view.clearFocus();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * =====================================================================
     **/

    /**
     * 初始化根布局
     *
     * @return 布局layout的id
     */
    public abstract int initContentView(Bundle savedInstanceState);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ToastUtils.cancel();
        AppManagerUtils.getAppManagerUtils().removeActivity(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                View view = getCurrentFocus();
                hideSoftKeyboard(ev, view, BaseDbActivity.this);//调用方法判断是否需要隐藏键盘
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
