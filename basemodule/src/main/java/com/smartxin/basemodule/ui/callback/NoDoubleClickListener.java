package com.smartxin.basemodule.ui.callback;

import android.view.View;

/**
 * Author: zhengwenxin
 * CreateDate  : 2021/8/27 16:20
 * Description: 防快速点击
 */
public abstract class NoDoubleClickListener implements View.OnClickListener {

    private long mLastClickTime;

    private long timeInterval = 1000L;

    public NoDoubleClickListener() {
    }

    @Override
    public void onClick(View view) {
        long nowTime = System.currentTimeMillis();

        if (nowTime - mLastClickTime > timeInterval) {
            // 点击一次
            onNoDoubleClick(view);
            mLastClickTime = nowTime;
        }
    }

    protected abstract void onNoDoubleClick(View view);
}
