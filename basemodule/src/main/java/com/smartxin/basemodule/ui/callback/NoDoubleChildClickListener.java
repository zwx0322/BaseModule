package com.smartxin.basemodule.ui.callback;

import android.view.View;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;

/**
 * Author: zhengwenxin
 * CreateDate  : 2021/8/27 16:20
 * Description: RecycleView 列表单元子控件防快速点击
 */
public abstract class NoDoubleChildClickListener implements OnItemChildClickListener {

    private long mLastClickTime;

    private long timeInterval = 1000L;

    @Override
    public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
        long nowTime = System.currentTimeMillis();

        if (nowTime - mLastClickTime > timeInterval) {
            // 点击一次
            onItemChildNoDoubleClick(adapter, view, position);
            mLastClickTime = nowTime;
        }
    }

    public abstract void onItemChildNoDoubleClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position);

}
