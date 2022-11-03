package com.smartxin.basemodule.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.BasePickerView;

/**
 * Author: zhengwenxin
 * CreateDate  : 2021/11/26 15:52
 * Description: 通用选择器构建模式工具类
 */
public class PickerViewBuildUtils {

    //枚举选择器内容
    public enum Mode {
        YMDHMS, YMDHM, YMDH, YMD, YM, Y, DHM
    }

    private static boolean[] mode2type(Mode mode) {
        switch (mode) {
            case Y:
                return new boolean[]{true, false, false, false, false, false};
            case YM:
                return new boolean[]{true, true, false, false, false, false};
            case YMD:
                return new boolean[]{true, true, true, false, false, false};
            case YMDH:
                return new boolean[]{true, true, true, true, false, false};
            case YMDHM:
                return new boolean[]{true, true, true, true, true, false};
            case DHM:
                return new boolean[]{false, false, false, true, true, true};
            default:
                return new boolean[]{true, true, true, true, true, true};
        }
    }

    /**
     *
     * @param context 上下文环境
     * @param mode 时间选择器 模式
     * @param onTimeSelectListener  选择监听
     * @param onCancelClick  关闭监听
     * @return 构建器
     *
     * 注意：  .setDate()  //当前选择时间
     *        .setRangDate(startDate, endDate)  // 选择器起止范围
     *        均未配置 根据实际需求自行配置  工具类不提供封装
     */
    public static TimePickerBuilder buildTimePicker(Context context,  Mode mode, OnTimeSelectListener onTimeSelectListener, View.OnClickListener onCancelClick) {
        return buildTimePicker(context, "", mode,  onTimeSelectListener, onCancelClick);
    }

    /**
     *
     * @param title 弹窗显示标题
     * @param context 上下文环境
     * @param mode 时间选择器 模式
     * @param onTimeSelectListener  选择监听
     * @param onCancelClick  关闭监听
     * @return  构建器
     *
     * 注意：  .setDate()  //当前选择时间
     *        .setRangDate(startDate, endDate)  // 选择器起止范围
     *        均未配置 根据实际需求自行配置  工具类不提供封装
     */
    public static TimePickerBuilder buildTimePicker(Context context, String title, Mode mode,
                                                     OnTimeSelectListener onTimeSelectListener, View.OnClickListener onCancelClick) {

//        Calendar selectedDate = Calendar.getInstance();
//        Calendar startDate = Calendar.getInstance();
//        Calendar endDate = Calendar.getInstance();
//
//        if (selected == 0) {
//            startDate.set(2000, 1, 1);
//            endDate.set(2100, 11, 30);
//        } else {
//            Date date = new Date(selected);
//            selectedDate.setTime(date);
//            int year = selectedDate.get(Calendar.YEAR);
//            int month = selectedDate.get(Calendar.MONTH);
//            int day = selectedDate.get(Calendar.DAY_OF_MONTH);
//            LogUtils.d("zwx","year = " + year + " month = "+ month + " day = " + day);
//            selectedDate.set(year, month , day, 0, 0, 0);
//            startDate.set(year, month , day, 0, 0, 0);
//            endDate.set(year, month , day, 23, 59, 59);
//        }

        return new TimePickerBuilder(context, onTimeSelectListener)
                .addOnCancelClickListener(onCancelClick)
                .setType(mode2type(mode))// 默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确认")//确认按钮文字
//                .setContentSize(18)//滚轮文字大小
                .setTitleSize(16)//标题文字大小
                .setTitleText(title)//标题文字
                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.parseColor("#0F9AF3"))//确定按钮文字颜色
                .setCancelColor(Color.parseColor("#0F9AF3"))//取消按钮文字颜色
                .setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
                .setBgColor(Color.WHITE)//滚轮背景颜色 Night mode
                .setLabel("", "", "", "", "", "")//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(false);//是否显示为对话框样式
    }

    /**
     * 设置 选择器弹出样式
     * 无配置弹出动画监听
     *
     * @param view
     */
    public static void setTimePickerStyle(BasePickerView view) {
        setTimePickerStyle(view, null);
    }

    /**
     * 设置 选择器弹出样式
     * @param view
     * @param callBack  弹出动画监听
     */
    public static void setTimePickerStyle(BasePickerView view, DialogSimpleCallBack callBack) {
        Dialog mDialog = view.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            view.getDialogContainerLayout().setLayoutParams(params);

            if (callBack != null) {
                mDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        callBack.onSimpleShow();
                    }
                });

                mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        callBack.onSimpleDismiss();
                    }
                });
            }

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
                dialogWindow.setDimAmount(0.3f);
            }
        }
    }

    public interface DialogSimpleCallBack {
        /**
         * 完全显示的时候执行
         */
        void onSimpleShow();

        /**
         * 完全消失的时候执行
         */
        void onSimpleDismiss();
    }
}
