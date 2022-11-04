package com.smartxin.baselibrary.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.smartxin.baselibrary.R;


public class LoadingDialog {

    private static Dialog mLoadingDialog;
    private final static Handler mMainHandler = new Handler(Looper.getMainLooper());
    private static Animation mAnimation;
    private static ImageView mImg;

    public static void showLoadingDialog(Context context, String loadingMessage, boolean canCancel) {
        if (context == null) {
            return;
        }
        if (Looper.myLooper() == Looper.getMainLooper()) {
            if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
                return;
            }
            mLoadingDialog = createLoadingDialog(context, loadingMessage);
            mLoadingDialog.setCancelable(canCancel);
            mLoadingDialog.setCanceledOnTouchOutside(canCancel);
            mLoadingDialog.show();
        } else {
            mMainHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
                        return;
                    }
                    mLoadingDialog = createLoadingDialog(context, loadingMessage);
                    mLoadingDialog.setCancelable(canCancel);
                    mLoadingDialog.setCanceledOnTouchOutside(canCancel);
                    mLoadingDialog.show();
                }
            });
        }
    }

    public static void dismissLoadingDialog() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
                Context context = ((ContextWrapper) mLoadingDialog.getContext()).getBaseContext();
                if (context instanceof Activity) {
                    if (!((Activity) context).isFinishing() && !((Activity) context).isDestroyed()) {
                        if (mAnimation != null) {
                            mAnimation.cancel();
                            mAnimation = null;
                        }
                        if (mImg != null) {
                            mImg.clearAnimation();
                            mImg = null;
                        }
                        mLoadingDialog.dismiss();
                    }
                } else {
                    if (mAnimation != null) {
                        mAnimation.cancel();
                        mAnimation = null;
                    }
                    if (mImg != null) {
                        mImg.clearAnimation();
                        mImg = null;
                    }
                    mLoadingDialog.dismiss();
                }
                mLoadingDialog = null;
            }
        } else {
            mMainHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
                        Context context = ((ContextWrapper) mLoadingDialog.getContext()).getBaseContext();
                        if (context instanceof Activity) {
                            if (!((Activity) context).isFinishing() && !((Activity) context).isDestroyed()) {
                                if (mAnimation != null) {
                                    mAnimation.cancel();
                                    mAnimation = null;
                                }
                                if (mImg != null) {
                                    mImg.clearAnimation();
                                    mImg = null;
                                }
                                mLoadingDialog.dismiss();
                            }
                        } else {
                            if (mAnimation != null) {
                                mAnimation.cancel();
                                mAnimation = null;
                            }
                            if (mImg != null) {
                                mImg.clearAnimation();
                                mImg = null;
                            }
                            mLoadingDialog.dismiss();
                        }
                        mLoadingDialog = null;
                    }
                }
            });
        }
    }

    /**
     * 得到自定义的progressDialog
     *
     * @param context
     * @param msg
     * @return
     */
    public static Dialog createLoadingDialog(Context context, String msg) {

        // 首先得到整个View
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_loading_view, null);
        // 获取整个布局
        ConstraintLayout layout = (ConstraintLayout) view
                .findViewById(R.id.dialog_view);
        // 页面中的Img
        mImg = (ImageView) view.findViewById(R.id.img);
        // 页面中显示文本
        TextView tipText = (TextView) view.findViewById(R.id.tipTextView);

        // 加载动画，动画用户使img图片不停的旋转
        mAnimation = AnimationUtils.loadAnimation(context,
                R.anim.dialog_loading);
        // 显示动画
        mImg.startAnimation(mAnimation);
        // 显示文本
        if (!TextUtils.isEmpty(msg)) {
            tipText.setVisibility(View.VISIBLE);
            tipText.setText(msg);
        } else {
            tipText.setVisibility(View.GONE);
        }
        // 创建自定义样式的Dialog
        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);
        // 设置返回键无效
        loadingDialog.setCancelable(false);
        loadingDialog.setContentView(layout, new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT));
        return loadingDialog;
    }

}
