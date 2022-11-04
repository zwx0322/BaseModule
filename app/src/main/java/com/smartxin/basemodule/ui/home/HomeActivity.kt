package com.smartxin.basemodule.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.gyf.immersionbar.ktx.immersionBar
import com.smartxin.baselibrary.base.BaseVMDbActivity
import com.smartxin.baselibrary.ui.adapter.fragment.FragmentListAdapter
import com.smartxin.basemodule.R
import com.smartxin.basemodule.databinding.ActivityHomeBinding
import com.smartxin.basemodule.ui.home.mine.MineFragment
import com.smartxin.basemodule.ui.home.news.NewsFragment
import com.smartxin.basemodule.ui.home.system.SystemFragment

class HomeActivity : BaseVMDbActivity<ActivityHomeBinding,HomeViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }

    override fun initView() {
        immersionBar {
            titleBar(mBinding.tbBase.toolbar)
            init()
        }

        mBinding.tbBase.btnGoMore.visibility = View.VISIBLE
//        var navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
//        var narController = navHostFragment.navController
//        NavigationUI.setupWithNavController(mBinding.bottomNavigation,narController)


        var fragments = mutableListOf<Fragment>() as ArrayList
        fragments.add(NewsFragment())
        fragments.add(SystemFragment())
        fragments.add(MineFragment())

        var listAdapter = FragmentListAdapter(this, fragments)

        mBinding.vpHome.adapter = listAdapter
        mBinding.vpHome.isUserInputEnabled = false
        mBinding.vpHome.offscreenPageLimit = ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT

        mBinding.bottomNavigation.setOnNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.newsFragment -> {
                    mBinding.vpHome.setCurrentItem(0, false)
                    mBinding.bottomNavigation.menu.getItem(0).isChecked = true
                }
                R.id.systemFragment -> {
                    mBinding.vpHome.setCurrentItem(1, false)
                    mBinding.bottomNavigation.menu.getItem(1).isChecked = true
                }
                R.id.mineFragment -> {
                    mBinding.vpHome.setCurrentItem(2, false)
                    mBinding.bottomNavigation.apply {
                        menu.getItem(2).isChecked = true
                        getOrCreateBadge(it.itemId).isVisible = false
                    }
                }
            }
            return@setOnNavigationItemSelectedListener true
        }

        mBinding.bottomNavigation.getOrCreateBadge(R.id.mineFragment).apply {
            backgroundColor = resources.getColor(com.smartxin.baselibrary.R.color.red_400)
            badgeTextColor = resources.getColor(R.color.white)
            number = 99999999
            maxCharacterCount = 3
        }

        mBinding.vpHome.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                mBinding.bottomNavigation.menu.getItem(position).isChecked = true
            }
        })

    }

    override fun initData() {

    }

    override fun initListener() {
        mBinding.tbBase.btnGoMore.setOnClickListener{
            mViewModel.changeUIEvent.value = null
        }
    }

    override fun initContentView(savedInstanceState: Bundle?): Int {
       return R.layout.activity_home
    }

    override fun initViewObservable() {

    }

    override fun initVariableId(): Int {
        return 0
    }
}