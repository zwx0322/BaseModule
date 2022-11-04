package com.smartxin.basemodule.ui.home.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.smartxin.baselibrary.base.BaseDbFragment
import com.smartxin.baselibrary.utils.LogUtils
import com.smartxin.basemodule.R
import com.smartxin.basemodule.databinding.FragmentNewsChildBinding


class NewsChildFragment : BaseDbFragment<FragmentNewsChildBinding>() {

   companion object{

       @JvmStatic
       fun newInstance(i: Int) =
           NewsChildFragment().apply {
               arguments = Bundle().apply {
                   putInt("index",i)
               }
           }

   }
    override fun initView() {
        LogUtils.e("zwx"," NewsChildFragment : " + arguments?.getInt("index"))
    }

    override fun initData() {

    }

    override fun initListener() {

    }

    override fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): Int = R.layout.fragment_news_child


}