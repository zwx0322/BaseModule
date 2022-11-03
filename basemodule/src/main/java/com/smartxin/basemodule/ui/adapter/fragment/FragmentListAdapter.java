package com.smartxin.basemodule.ui.adapter.fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

/**
 * Author: zhengwenxin
 * CreateDate  : 2021/7/9 10:29
 * Description: 基础标签页适配器
 */
public class FragmentListAdapter extends FragmentStateAdapter {

    private ArrayList<Fragment> fragmentList;

    public FragmentListAdapter(@NonNull Fragment fragment, ArrayList<Fragment> fragmentList) {
        super(fragment);
        this.fragmentList = fragmentList;
    }

    public FragmentListAdapter(@NonNull FragmentActivity fragmentActivity, ArrayList<Fragment> fragmentList) {
        super(fragmentActivity);
        this.fragmentList = fragmentList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return fragmentList.size();
    }

}
