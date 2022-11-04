package com.smartxin.basemodule.ui.home.system

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gyf.immersionbar.ktx.immersionBar
import com.smartxin.baselibrary.base.BaseVMDbFragment
import com.smartxin.basemodule.R
import com.smartxin.basemodule.databinding.FragmentSystemBinding


class SystemFragment : BaseVMDbFragment<FragmentSystemBinding, SystemViewModel>() {

    override fun initView() {
        immersionBar {
            titleBar(mBinding.root)
            init()
        }
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
        return R.layout.fragment_system
    }

    override fun initViewObservable() {

    }

    override fun initVariableId(): Int {
       return 0
    }

}