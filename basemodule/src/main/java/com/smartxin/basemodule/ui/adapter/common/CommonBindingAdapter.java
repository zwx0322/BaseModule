package com.smartxin.basemodule.ui.adapter.common;

import android.view.View;

import androidx.databinding.BindingAdapter;

import com.smartxin.basemodule.ui.callback.NoDoubleClickListener;

/**
 * Author: zhengwenxin
 * CreateDate: 2022/2/22 10:52
 * Description: 通用布局绑定适配器
 */

public class CommonBindingAdapter {

    @BindingAdapter(value = {"isVisible"}, requireAll = false)
    public static void isVisible(View view, boolean visible) {
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter(value = {"isGone"}, requireAll = false)
    public static void isGone(View view, boolean visible) {
        view.setVisibility(visible ? View.GONE : View.VISIBLE);
    }

    @BindingAdapter(value = {"isSelected"}, requireAll = false)
    public static void isSelected(View view, boolean select) {
        view.setSelected(select);
    }

    @BindingAdapter(value = {"isEnabled"}, requireAll = false)
    public static void isEnabled(View view, boolean select) {
        view.setEnabled(select);
    }

    @BindingAdapter(value = {"noDoubleClick"}, requireAll = false)
    public static void onClickWithDebouncing(View view, NoDoubleClickListener clickListener) {
        view.setOnClickListener(clickListener);
    }
}
