package com.smartxin.baselibrary.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Author: zhengwenxin
 * CreateDate: 2022/2/18 17:18
 * Description: 基础Activity
 */
public abstract class BaseVMDbActivity<V extends ViewDataBinding, VM extends BaseViewModel> extends BaseDbActivity<V> implements IBaseVM{

    protected VM mViewModel;

    private int viewModelId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //私有的ViewModel与View的契约事件回调逻辑
        registerLiveDataCallBack();

        //页面事件监听的方法，一般用于ViewModel层转到View层的事件注册
        initViewObservable();
    }

    /**
     * 私有的初始化Databinding和ViewModel方法
     */
    protected void initViewDataBinding(Bundle savedInstanceState) {
        super.initViewDataBinding(savedInstanceState);
        viewModelId = initVariableId();
        mViewModel = initViewModel();

        if (mViewModel == null) {
            Class modelClass;
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[1];
            } else {
                //如果没有指定泛型参数，则默认使用BaseViewModel
                modelClass = BaseViewModel.class;
            }
            mViewModel = (VM) createViewModel(this, modelClass);
        }
        //关联ViewModel
        mBinding.setVariable(viewModelId, mViewModel);
    }

    /**
     * 注册ViewModel与View的契约UI回调事件
     */
    protected void registerLiveDataCallBack() {
        //加载对话框显示
        mViewModel.getShowDialogEvent().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String title) {
                showDialog(title);
            }
        });
        mViewModel.getDismissDialogEvent().observe(this, new Observer<Void>() {
            @Override
            public void onChanged(Void unused) {
                dismissDialog();
            }
        });

        mViewModel.getFinishActivity().observe(this, new Observer<Void>() {
            @Override
            public void onChanged(Void unused) {
                finish();
            }
        });
    }

    /**
     * =====================================================================
     **/

    /**
     * 初始化ViewModel的id
     *
     * @return BR的id
     */
    public abstract int initVariableId();

    /**
     * 初始化ViewModel
     *
     * @return 继承BaseViewModel的ViewModel
     */
    public VM initViewModel() {
        return null;
    }

    /**
     * 创建ViewModel
     *
     * @param cls
     * @param <T>
     * @return
     */
    public <T extends ViewModel> T createViewModel(AppCompatActivity activity, Class<T> cls) {
        return new ViewModelProvider(activity).get(cls);
    }
}
