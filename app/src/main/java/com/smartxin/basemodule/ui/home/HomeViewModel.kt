package com.smartxin.basemodule.ui.home

import com.kunminx.architecture.ui.callback.UnPeekLiveData
import com.smartxin.baselibrary.base.BaseViewModel

/**
 *     Author: zhengwenxin
 *     CreateDate: 2022/11/3 16:27
 *     Description:
 */
class HomeViewModel :BaseViewModel() {
    val changeUIEvent: UnPeekLiveData<Any> by lazy {
        UnPeekLiveData.Builder<Any>().setAllowNullValue(true).create() }
}