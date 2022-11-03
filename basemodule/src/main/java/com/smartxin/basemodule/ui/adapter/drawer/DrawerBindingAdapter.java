package com.smartxin.basemodule.ui.adapter.drawer;

import androidx.core.view.GravityCompat;
import androidx.databinding.BindingAdapter;
import androidx.drawerlayout.widget.DrawerLayout;

/**
 * Author: zhengwenxin
 * CreateDate: 2022/2/22 10:52
 * Description: 抽屉布局绑定适配器
 */
public class DrawerBindingAdapter {

    @BindingAdapter(value = {"isOpenDrawer"}, requireAll = false)
    public static void openDrawer(DrawerLayout drawerLayout, boolean isOpenDrawer) {
        if (isOpenDrawer && !drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.openDrawer(GravityCompat.START);
        } else {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    @BindingAdapter(value = {"allowDrawerOpen"}, requireAll = false)
    public static void allowDrawerOpen(DrawerLayout drawerLayout, boolean allowDrawerOpen) {
        drawerLayout.setDrawerLockMode(allowDrawerOpen
                ? DrawerLayout.LOCK_MODE_UNLOCKED //打开手势滑动
                : DrawerLayout.LOCK_MODE_LOCKED_CLOSED);  //关闭手势滑动
    }

    @BindingAdapter(value = {"bindDrawerListener"}, requireAll = false)
    public static void listenDrawerState(DrawerLayout drawerLayout, DrawerLayout.SimpleDrawerListener listener) {
        drawerLayout.addDrawerListener(listener);
    }

}
