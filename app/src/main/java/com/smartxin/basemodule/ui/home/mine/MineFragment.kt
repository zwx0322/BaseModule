package com.smartxin.basemodule.ui.home.mine

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.smartxin.baselibrary.base.BaseDbFragment
import com.smartxin.basemodule.R
import com.smartxin.basemodule.databinding.FragmentMineBinding


class MineFragment : BaseDbFragment<FragmentMineBinding>() {

    override fun initView() {

    }

    override fun initData() {

    }

    override fun initListener() {

    }

    override fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): Int {
        return R.layout.fragment_mine
    }

}