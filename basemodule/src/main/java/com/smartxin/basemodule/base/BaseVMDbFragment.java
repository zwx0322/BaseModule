package com.smartxin.basemodule.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Author: zhengwenxin
 * CreateDate: 2022/2/18 17:29
 * Description: 基础Fragment
 */
public abstract class BaseVMDbFragment<V extends ViewDataBinding, VM extends BaseViewModel> extends BaseDbFragment<V> implements IBaseVM {

    protected VM mViewModel;

    private int viewModelId;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, initContentView(inflater, container, savedInstanceState), container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //私有的ViewModel与View的契约事件回调逻辑
        registerLiveDataCallBack();
        //页面事件监听的方法，一般用于ViewModel层转到View层的事件注册
        initViewObservable();
    }

    /**
     * 私有的初始化Databinding和ViewModel方法
     */
    protected void initViewDataBinding() {
        super.initViewDataBinding();
        viewModelId = initVariableId();
        mViewModel = initViewModel();
        //  appViewModel = MyApplication.getApplication().getAppViewModelProvider().get(BaseAppViewModel.class);
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
    public <T extends ViewModel> T createViewModel(Fragment fragment, Class<T> cls) {
        return new ViewModelProvider(fragment).get(cls);
    }
}
