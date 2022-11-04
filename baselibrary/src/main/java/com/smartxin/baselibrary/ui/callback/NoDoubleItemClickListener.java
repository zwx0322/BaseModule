package com.smartxin.baselibrary.ui.callback;

import android.view.View;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

/**
 * Author: zhengwenxin
 * CreateDate  : 2021/8/27 16:20
 * Description: RecycleView 列表单元防快速点击
 */
public abstract class NoDoubleItemClickListener implements OnItemClickListener {

    private long mLastClickTime;

    private long timeInterval = 1000L;

    @Override
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
        long nowTime = System.currentTimeMillis();

        if (nowTime - mLastClickTime > timeInterval) {
            // 点击一次
            onNoDoubleItemClick(adapter,view,position);
            mLastClickTime = nowTime;
        }
    }

    public abstract void onNoDoubleItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position);
}
