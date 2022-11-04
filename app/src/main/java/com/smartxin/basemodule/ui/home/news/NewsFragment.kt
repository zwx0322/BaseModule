package com.smartxin.basemodule.ui.home.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.smartxin.baselibrary.base.BaseVMDbFragment
import com.smartxin.baselibrary.ui.adapter.fragment.FragmentListAdapter
import com.smartxin.baselibrary.utils.LogUtils
import com.smartxin.baselibrary.utils.ToastUtils
import com.smartxin.basemodule.BR
import com.smartxin.basemodule.R
import com.smartxin.basemodule.databinding.FragmentNewsBinding
import com.smartxin.basemodule.event.EventViewModel
import com.smartxin.basemodule.ui.home.HomeViewModel


class NewsFragment : BaseVMDbFragment<FragmentNewsBinding, NewsViewModel>() {

    var tabs = arrayOf("最新热点", "国际新闻", "娱乐八卦")

    lateinit var mHomeViewModel : HomeViewModel

    lateinit var mEventViewModel: EventViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        LogUtils.e("zwx", "NewsFragment onViewCreated ")
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NewsFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun initView() {

        var fragments = mutableListOf<Fragment>() as ArrayList
        fragments.add(NewsChildFragment.newInstance(1))
        fragments.add(NewsChildFragment.newInstance(2))
        fragments.add(NewsChildFragment.newInstance(3))

        var listAdapter = FragmentListAdapter(this, fragments)

        mBinding.vpNews.adapter = listAdapter
//        mBinding.vpNews.isUserInputEnabled = false
        mBinding.vpNews.offscreenPageLimit = ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT
        mBinding.vpNews.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                mBinding.table.getTabAt(position)?.orCreateBadge?.isVisible = false
            }
        })

        TabLayoutMediator(
            mBinding.table,
            mBinding.vpNews,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                tab.apply {
                    text = tabs[position]
//                    orCreateBadge.isVisible = false
                }
            }).attach()

        mBinding.table.getTabAt(1)?.let {
            it.orCreateBadge.apply {
                backgroundColor = resources.getColor(com.smartxin.baselibrary.R.color.red_400)
                badgeTextColor = resources.getColor(R.color.white)
                number = 6
            }
        }
    }

    override fun initData() {
        mHomeViewModel = getActivityScopeViewModel(HomeViewModel::class.java)

        mEventViewModel = getApplicationScopeViewModel(EventViewModel::class.java)

    }

    override fun initListener() {

    }

    override fun initViewObservable() {
        mHomeViewModel.changeUIEvent.observe(this@NewsFragment){
            ToastUtils.showShort("UI改变  it.hashCode() ： ${ mHomeViewModel.changeUIEvent.hashCode()}" )
        }
    }

    override fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): Int = R.layout.fragment_news

    override fun initVariableId(): Int {
        return 0
    }
}