package com.smartxin.baselibrary.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;

/**
 * 常用工具类
 */
public final class Utils {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    public static void init(@NonNull final Context context) {
        Utils.context = context.getApplicationContext();
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getContext() {
        if (context != null) {
            return context;
        }
        throw new NullPointerException("should be initialized in application");
    }

    /**
     * 获取当前本地apk的版本
     *
     * @return
     */
    public static int getVersionCode() {
        int versionCode = 0;
        try {
            versionCode = getContext().getPackageManager().
                    getPackageInfo(getContext().getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 获取版本号名称
     *
     * @return
     */
    public static String getVerName() {
        String verName = "";
        try {
            verName = getContext().getPackageManager().
                    getPackageInfo(getContext().getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;
    }

    /**
     * 根据新的版本号判断是否要显示提示更新弹框
     *
     * @param newVersionCode
     * @return
     */
//    public static boolean newVersionDialogNeedShow(int newVersionCode, boolean newVersionForceUpdate) {
//        int currentVersionCode = getVersionCode();
//        if (newVersionCode > currentVersionCode) {
//            //新版本号比当前版本号大
//            if (newVersionForceUpdate) {
//                //强制更新版本直接弹框
//                return true;
//            }
//            int noHintDbVersionCode = VersionControlDbModel.getVersionCode();
//            return noHintDbVersionCode != newVersionCode;
//        }
//        return false;
//    }
}