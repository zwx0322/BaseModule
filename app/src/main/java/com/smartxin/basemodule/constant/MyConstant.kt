package com.smartxin.basemodule.constant

import com.smartxin.basemodule.bean.BtnGroup

/**
 *     Author: zhengwenxin
 *     CreateDate: 2022/11/3 14:41
 *     Description:
 */
class MyConstant {

private var mainList: MutableList<BtnGroup?>? = null

    fun getMainList(): List<BtnGroup?>? {
        if (mainList == null) {
            mainList = ArrayList()
            mainList?.add(
                BtnGroup(
                    1,
                    " AppBarLayout + CollapsingToolbarLayout + Toolbar + Behavior "
                )
            )
            mainList?.add(
                BtnGroup(
                    2,
                    " AppBarLayout + CollapsingToolbarLayout + TableLayout + ViewPager + Behavior "
                )
            )
            mainList?.add(BtnGroup(3, " Navigation导航功能展示 HomeActivity "))
            mainList?.add(BtnGroup(4, " 测试基础类功能 BaseTestActivity "))
            mainList?.add(BtnGroup(5, " 仿汽车之家 CityActivity "))
            mainList?.add(BtnGroup(6, " 条件选择器 PickerTimeActivity "))
            mainList?.add(BtnGroup(7, " 网络环境配置  NetConfigActivity "))
            mainList?.add(BtnGroup(8, " GET请求 网络接口调试 "))
            mainList?.add(BtnGroup(9, " 集成H5页面 QuickWebActivity "))
            mainList?.add(BtnGroup(10, " 查看图片小功能 ShowPictureActivity "))
            mainList?.add(BtnGroup(11, " VPN VpnTestActivity "))
        }
        return mainList
    }
}